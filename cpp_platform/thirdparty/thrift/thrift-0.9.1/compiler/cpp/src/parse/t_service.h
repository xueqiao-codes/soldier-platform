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

#ifndef T_SERVICE_H
#define T_SERVICE_H

#include "t_function.h"
#include "t_generator_info.h"
#include <vector>

class t_program;

/**
 * A service consists of a set of functions.
 *
 */
class t_service : public t_type {
 public:
  t_service(t_program* program) :
    t_type(program),
    extends_(NULL),
	servicekey_(0){}

  bool is_service() const {
    return true;
  }

  void set_extends(t_service* extends) {
    extends_ = extends;
  }

  void add_function(t_function* func) {
    std::vector<t_function*>::const_iterator iter;
    for (iter = functions_.begin(); iter != functions_.end(); iter++) {
      if (func->get_name() == (*iter)->get_name()) {
        throw "Function " + func->get_name() + " is already defined";
      }
      if (func->get_number() != -1 && func->get_number() == (*iter)->get_number()) {
        throw "Function " + func->get_name() + " same unique number with Function " + (*iter)->get_name();
      }
    }
    functions_.push_back(func);
  }

  const std::vector<t_function*>& get_functions() const {
    return functions_;
  }

  t_service* get_extends() {
    return extends_;
  }

  virtual std::string get_fingerprint_material() const {
    // Services should never be used in fingerprints.
    throw "BUG: Can't get fingerprint material for service.";
  }

  inline const int get_servicekey()const{ return servicekey_;}
  inline void set_servicekey(int servicekey){ servicekey_ = servicekey; }
  
  t_generator_info& get_generator_info() { return generator_info_; }

 private:
  std::vector<t_function*> functions_;
  t_service* extends_;
  int servicekey_;
  t_generator_info generator_info_;
};

#endif
