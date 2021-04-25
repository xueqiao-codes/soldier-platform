/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

#include "t_generator.h"
using namespace std;

/**
 * Top level program generation function. Calls the generator subclass methods
 * for preparing file streams etc. then iterates over all the parts of the
 * program to perform the correct actions.
 *
 * @param program The thrift program to compile into C++ source
 */
void t_generator::generate_program() {
  //modified by wileywang
	if (program_->get_name().find("-") != program_->get_name().npos) {
		failure("the idl file name should not contains - for its name");
	}
  // modified end

  // Initialize the generator
  init_generator();

  // Generate enums
  vector<t_enum*> enums = program_->get_enums();
  vector<t_enum*>::iterator en_iter;
  for (en_iter = enums.begin(); en_iter != enums.end(); ++en_iter) {
    generate_enum(*en_iter);
  }

  // Generate typedefs
  vector<t_typedef*> typedefs = program_->get_typedefs();
  vector<t_typedef*>::iterator td_iter;
  for (td_iter = typedefs.begin(); td_iter != typedefs.end(); ++td_iter) {
    generate_typedef(*td_iter);
  }

  // Generate structs, exceptions, and unions in declared order
  vector<t_struct*> objects = program_->get_objects();
  vector<t_struct*>::iterator o_iter;
  for (o_iter = objects.begin(); o_iter != objects.end(); ++o_iter) {
    if ((*o_iter)->is_xception()) {
      generate_xception(*o_iter);
    } else {
      generate_struct(*o_iter);
    }
  }

  // Generate constants
  vector<t_const*> consts = program_->get_consts();
  generate_consts(consts);

  // Generate services
  vector<t_service*> services = program_->get_services();
  if (services.size() > 1) {
	  failure("Only one service can be declared in a file");
  }

  vector<t_service*>::iterator sv_iter;
  for (sv_iter = services.begin(); sv_iter != services.end(); ++sv_iter) {
    service_name_ = get_service_name(*sv_iter);
	
	// modified by wileywang
    for(std::vector<t_function*>::const_iterator funcIt = (*sv_iter)->get_functions().begin();
    	funcIt != (*sv_iter)->get_functions().end(); ++funcIt){

    	bool platformArgsCorrected = false;
    	bool exceptionArgsCorrected = false;
    	t_struct* arglist = (*funcIt)->get_arglist();
    	pdebug("function %s", (*funcIt)->get_name().c_str());

    	if(arglist != NULL && arglist->get_members().size() >= 1) {
    	  t_type* first_parameter = arglist->get_members()[0]->get_type();
		  if(NULL != first_parameter->get_program()){
			pdebug("first parameter program=%s", first_parameter->get_program()->get_name().c_str());
			pdebug("first parameter material=%s", first_parameter->get_fingerprint_material().c_str());
			pdebug("first parameter name=%s", first_parameter->get_name().c_str());
			if( first_parameter->get_program()->get_name() == "comm"
    		  && first_parameter->get_name() == "PlatformArgs"){
				t_field* first_field = arglist->get_members()[0];
				if (first_field->get_key() == 1) {
					platformArgsCorrected = true;
				}
			}
		  }
        }
    	if(!platformArgsCorrected){
    		 failure("service %s function %s first parameter must be 1:comm.PlatformArgs parameters",
    				 service_name_.c_str(), (*funcIt)->get_name().c_str());
    	}

    	t_struct* exceptions = (*funcIt)->get_xceptions();
    	if (exceptions != NULL && exceptions->get_members().size() >= 1) {
    	    t_type* first_exception = exceptions->get_members()[0]->get_type();
    	    if (NULL != first_exception) {
    	        pdebug("first exception program=%s", first_exception->get_program()->get_name().c_str());
    	        pdebug("first exception material=%s", first_exception->get_fingerprint_material().c_str());
    	        pdebug("first exception name=%s", first_exception->get_name().c_str());

    	        if (first_exception->get_program()->get_name() == "comm"
    	                && first_exception->get_name() == "ErrorInfo") {
    	            t_field* first_field = exceptions->get_members()[0];
    	            if (first_field->get_key() == 1) {
    	                exceptionArgsCorrected = true;
    	            }
    	        }
    	    }
    	}
    	if (!exceptionArgsCorrected) {
    	    failure("service %s function %s first throws must be 1:comm.ErrorInfo err",
    	            service_name_.c_str(), (*funcIt)->get_name().c_str());
    	}

    	(*sv_iter)->get_generator_info().handleType(((*funcIt)->get_returntype()), (*sv_iter)->get_program()->get_name());
    	(*sv_iter)->get_generator_info().handleFields((*funcIt)->get_arglist()->get_members(), (*sv_iter)->get_program()->get_name());
    }
	
    generate_service(*sv_iter);
//	generate_adaptor(*sv_iter);
//    generate_client_impl(*sv_iter);
//    generate_server_impl(*sv_iter);
//    generate_async_client_impl(*sv_iter);
    generate_project(*sv_iter);
  }

  // Close the generator
  close_generator();
}

string t_generator::escape_string(const string &in) const {
  string result = "";
  for (string::const_iterator it = in.begin(); it < in.end(); it++) {
    std::map<char, std::string>::const_iterator res = escape_.find(*it);
    if (res != escape_.end()) {
      result.append(res->second);
    } else {
      result.push_back(*it);
    }
  }
  return result;
}

void t_generator::generate_consts(vector<t_const*> consts) {
  vector<t_const*>::iterator c_iter;
  for (c_iter = consts.begin(); c_iter != consts.end(); ++c_iter) {
    generate_const(*c_iter);
  }
}

void t_generator::generate_docstring_comment(ofstream& out,
                                             const string& comment_start,
                                             const string& line_prefix,
                                             const string& contents,
                                             const string& comment_end) {
  if (comment_start != "") indent(out) << comment_start;
  stringstream docs(contents, ios_base::in);
  while (!docs.eof()) {
    char line[1024];
    docs.getline(line, 1024);

    // Just prnt a newline when the line & prefix are empty.
    if (strlen(line) == 0 && line_prefix == "" && !docs.eof()) {
        out << std::endl;
    } else if (strlen(line) > 0 || !docs.eof()) {  // skip the empty last line
      indent(out) << line_prefix << line << std::endl;
    }
  }
  if (comment_end != "") indent(out) << comment_end;
}


void t_generator_registry::register_generator(t_generator_factory* factory) {
  gen_map_t& the_map = get_generator_map();
  if (the_map.find(factory->get_short_name()) != the_map.end()) {
    failure("Duplicate generators for language \"%s\"!\n", factory->get_short_name().c_str());
  }
  the_map[factory->get_short_name()] = factory;
}

t_generator* t_generator_registry::get_generator(t_program* program,
                                                 const string& options) {
  string::size_type colon = options.find(':');
  string language = options.substr(0, colon);

  map<string, string> parsed_options;
  if (colon != string::npos) {
    string::size_type pos = colon+1;
    while (pos != string::npos && pos < options.size()) {
      string::size_type next_pos = options.find(',', pos);
      string option = options.substr(pos, next_pos-pos);
      pos = ((next_pos == string::npos) ? next_pos : next_pos+1);

      string::size_type separator = option.find('=');
      string key, value;
      if (separator == string::npos) {
        key = option;
        value = "";
      } else {
        key = option.substr(0, separator);
        value = option.substr(separator+1);
      }

      parsed_options[key] = value;
    }
  }

  gen_map_t& the_map = get_generator_map();
  gen_map_t::iterator iter = the_map.find(language);

  if (iter == the_map.end()) {
    return NULL;
  }

  return iter->second->get_generator(program, parsed_options, options);
}

t_generator_registry::gen_map_t& t_generator_registry::get_generator_map() {
  // http://www.parashift.com/c++-faq-lite/ctors.html#faq-10.12
  static gen_map_t* the_map = new gen_map_t();
  return *the_map;
}

t_generator_factory::t_generator_factory(
    const std::string& short_name,
    const std::string& long_name,
    const std::string& documentation)
  : short_name_(short_name)
  , long_name_(long_name)
  , documentation_(documentation)
{
  t_generator_registry::register_generator(this);
}
