#include <stdio.h>
#include "t_type.h"
#include "t_typedef.h"

#include "md5.h"
#include "t_program.h"
#include "t_generator_info.h"

void t_type::generate_fingerprint() {
  std::string material = get_fingerprint_material();
  md5_state_t ctx;
  md5_init(&ctx);
  md5_append(&ctx, (md5_byte_t*)(material.data()), (int)material.size());
  md5_finish(&ctx, (md5_byte_t*)fingerprint_);
}

t_type* t_type::get_true_type() {
  t_type* type = this;
  while (type->is_typedef()) {
    type = ((t_typedef*)type)->get_type();
  }
  return type;
}

void t_generator_info::handleType(t_type* type, const std::string& service_program_name){
	if(type == NULL){
		return ;
	}
	if(type->is_list()){
		setHasList();
		handleType(((t_list*)(type))->get_elem_type(), service_program_name);
	} else if(type->is_map()) {
		setHasMap();
		handleType(((t_map*)(type))->get_key_type(), service_program_name);
		handleType(((t_map*)(type))->get_val_type(), service_program_name);
	} else if(type->is_set()) {
		setHasSet();
		handleType(((t_set*)(type))->get_elem_type(), service_program_name);
	} else if(type->is_base_type() && ((t_base_type*)type)->is_binary()) {
		setHasBinary();
	} else if (type->is_struct() || type->is_enum()) {
		addNoneBaseType(type, service_program_name);
	} else if (type->is_typedef()) {
		addNoneBaseType(const_cast<t_type*>(type)->get_true_type(), service_program_name);
	}
}

void t_generator_info::addNoneBaseType(t_type* type, const std::string& service_program_name) {
	if (type->get_program()->get_name() == service_program_name) {
		//printf("add none base type %s for service %s\n", type->get_name().c_str(), service_program_name.c_str());
		m_noneBaseTypes[type->get_name()] = true;
	}
}
