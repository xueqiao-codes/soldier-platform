/*
 * generate_type_info.h
 *
 *  Created on: 2012-12-1
 *      Author: Xairy
 */

#ifndef GENERATE_TYPE_INFO_H_
#define GENERATE_TYPE_INFO_H_

#include <map>
#include <string>

#include "t_type.h"
#include "t_base_type.h"

class t_generator_info{
public:
	t_generator_info()
		:m_hasList(false), m_hasMap(false), m_hasSet(false)
		 ,m_hasBinary(false){
	}

	void setHasList(){
		m_hasList = true;
	}
	bool hasList(){
		return m_hasList;
	}

	void setHasMap(){
		m_hasMap = true;
	}
	bool hasMap(){
		return m_hasMap;
	}

	void setHasSet(){
		m_hasSet = true;
	}
	bool hasSet(){
		return m_hasSet;
	}

	void setHasBinary(){
		m_hasBinary = true;
	}
	bool hasBinary(){
		return m_hasBinary;
	}

	void handleType(t_type* type, 
		const std::string& service_program_name);

	void handleFields(const std::vector<t_field*>& members, const std::string& service_program_name){
		for(std::vector<t_field*>::const_iterator fieldIt = members.begin();
				fieldIt != members.end(); ++fieldIt){
			handleType(const_cast<t_type*>((*fieldIt)->get_type()), service_program_name);
		}
	}
	
	const std::map<std::string, bool>& getNoneBaseTypes() {
		return m_noneBaseTypes;
	}

private:
	void addNoneBaseType(t_type* type, const std::string& service_program_name);

	bool m_hasList;
	bool m_hasMap;
	bool m_hasSet;
	bool m_hasBinary;
	std::map<std::string, bool> m_noneBaseTypes;
};

#endif /* GENERATE_TYPE_INFO_H_ */
