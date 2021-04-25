package org.soldier.platform.admin.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.soldier.platform.dal_set.dao.DalSetHost;
import org.soldier.platform.dal_set.dao.DalSetRole;
import org.soldier.platform.dal_set.dao.DalSetTable;
import org.soldier.platform.dal_set.dao.DalSetUser;
import org.soldier.platform.dal_set.dao.RoleServiceRelation;
import org.soldier.platform.dal_set.dao.RoleSetRelation;
import org.soldier.platform.dal_set.dao.RoleTableRelation;
import org.soldier.platform.dal_set.dao.TypeInSet;

public class DalSetXmlBuilder {
	private Map<String, DalSetHost> hostsMap = new HashMap<String, DalSetHost>();
	private Map<String, DalSetUser> usersMap = new HashMap<String, DalSetUser>();
	private Map<String, DalSetRole> rolesMap = new HashMap<String, DalSetRole>();
	private Map<String, DalSetTable> tablesMap = new HashMap<String, DalSetTable>();
	
	private Map<String, List<RoleTableRelation>> roleTableRelationMap
				= new HashMap<String, List<RoleTableRelation>>();
			
	private Map<String, Map<Integer, List<RoleSetRelation>>> roleSetsRelationMap
				= new HashMap<String, Map<Integer, List<RoleSetRelation>>>();
	
	private List<RoleServiceRelation> roleServiceRelationList;
	private boolean hasDescription;
	
	public DalSetXmlBuilder() {
	}
	
	public void setHostsList(List<DalSetHost> hostsList) {
		for (DalSetHost host : hostsList) {
			hostsMap.put(host.getName(), host);
		}
	}
	
	public void setUsersList(List<DalSetUser> usersList) {
		for (DalSetUser user : usersList) {
			usersMap.put(user.getKey(), user);
		}
	}
	
	public void setRolesList(List<DalSetRole> rolesList) {
		for (DalSetRole role : rolesList) {
			rolesMap.put(role.getRoleName(), role);
		}
	}
	
	public void setTablesList(List<DalSetTable> tablesList) {
		for (DalSetTable table : tablesList) {
			tablesMap.put(table.getPrefixName(), table);
		}
	}
	
	public void setRoleTableRelationList(List<RoleTableRelation> roleTableRelationList) {
		for (RoleTableRelation relation : roleTableRelationList) {
			List<RoleTableRelation> relationList = roleTableRelationMap.get(relation.getRoleName());
			if (relationList == null) {
				relationList = new ArrayList<RoleTableRelation>();
				roleTableRelationMap.put(relation.getRoleName(), relationList);
			}
			relationList.add(relation);
		}
	}
	
	public void setRoleSetRelationList(List<RoleSetRelation> roleSetRelationList) {
		for (RoleSetRelation relation : roleSetRelationList) {
			Map<Integer, List<RoleSetRelation>> setsMap = roleSetsRelationMap.get(relation.getRoleName());
			if (setsMap == null) {
				setsMap = new HashMap<Integer, List<RoleSetRelation>>();
				roleSetsRelationMap.put(relation.getRoleName(), setsMap);
			}
			
			List<RoleSetRelation> relationList = setsMap.get(relation.getSetIndex());
			if (relationList == null) {
				relationList = new ArrayList<RoleSetRelation>();
				setsMap.put(relation.getSetIndex(), relationList);
			}
			if (relation.getTypeInSet() == TypeInSet.Master) {
				relationList.add(0, relation);
			} else {
				relationList.add(relation);
			}
			
		}
	}
	
	public void setRoleServiceRelationList(List<RoleServiceRelation> roleServiceRelationList) {
		this.roleServiceRelationList = roleServiceRelationList;
	}
	
	public void setHasDescription(boolean hasDescription) {
		this.hasDescription = hasDescription;
	}
	
	private void addHosts(DOMElement rootElement) {
		DOMElement hostsElement = new DOMElement("hosts");
		rootElement.add(hostsElement);
		for (DalSetHost host : hostsMap.values()) {
			DOMElement hostElement = new DOMElement("host");
			hostElement.setAttribute("name", host.getName());
			hostElement.setAttribute("ip", host.getDomain());
			hostElement.setAttribute("port", String.valueOf(host.getPort()));
			if (hasDescription) {
				hostElement.setAttribute("desc", host.getDesc());
			}
			hostsElement.add(hostElement);
		}
	}
	
	private void addUsers(DOMElement rootElement, boolean usePlainPassword) {
		DOMElement usersElement = new DOMElement("users");
		rootElement.add(usersElement);
		for (DalSetUser user : usersMap.values()) {
			DOMElement userElement = new DOMElement("user");
			userElement.setAttribute("id", user.getKey());
			userElement.setAttribute("name", user.getName());
			if (usePlainPassword) {
				userElement.setAttribute("password", user.getPlainPassword());
			} else {
				userElement.setAttribute("password", user.getPassword());
			}
			if (hasDescription) {
				userElement.setAttribute("desc", user.getDesc());
			}
			
			usersElement.add(userElement);
		}
	}
	
	private void addDbInstances(DOMElement rootElement) {
		DOMElement dbInstancesElement = new DOMElement("dbInstances");
		rootElement.add(dbInstancesElement);
		for (DalSetRole role : rolesMap.values()) {
			DOMElement dbElement = new DOMElement("db");
			dbElement.setAttribute("name", role.getDbName());
			dbElement.setAttribute("drive", role.getDbType().name());
			dbElement.setAttribute("rolename", role.getRoleName());
			if (hasDescription) {
				dbElement.setAttribute("desc", role.getDesc());
			}
			
			addSets(role.getRoleName(), dbElement);
			addTables(role.getRoleName(), dbElement);
			
			dbInstancesElement.add(dbElement);
		}
	}
	
	private void addSets(String roleName, DOMElement dbElement) {
		DOMElement setsElement = new DOMElement("sets");
		dbElement.add(setsElement);
		
		Map<Integer, List<RoleSetRelation>> setsMap = roleSetsRelationMap.get(roleName);
		if (setsMap != null) {
			for (int index = 0; index < setsMap.size(); ++index) {
				DOMElement setElement = new DOMElement("set");
				setElement.setAttribute("index", String.valueOf(index));
				
				List<RoleSetRelation> relationList = setsMap.get(index);
				int slaveIndex = 0;
				for (RoleSetRelation relation : relationList) {
					DOMElement element = null;
					if (relation.getTypeInSet() == TypeInSet.Master) {
						element = new DOMElement("master");
					} else {
						element = new DOMElement("slave");
						element.setAttribute("index", String.valueOf(slaveIndex));
						++slaveIndex;
					}
					element.setAttribute("host", relation.getHostName());
					element.setAttribute("weight", String.valueOf(relation.getWeight()));
					
					setElement.add(element);
				}
				
				setsElement.add(setElement);
			}
		}
	}
	
	private void addTables(String roleName, DOMElement dbElement) {
		DOMElement tablesElement = new DOMElement("tables");
		dbElement.add(tablesElement);
		
		List<RoleTableRelation> roleTableRelationList = roleTableRelationMap.get(roleName);
		if (roleTableRelationList != null) {
			for (RoleTableRelation relation : roleTableRelationList) {
				DalSetTable table = tablesMap.get(relation.getTablePrefixName());
				if (table != null) {
					DOMElement element = new DOMElement("table");
					
					element.setAttribute("prefix", table.getPrefixName());
					element.setAttribute("sliceNum", String.valueOf(table.getSliceNum()));
					element.setAttribute("fillZero", table.isFillZero() ? "true" : "false");
					if (hasDescription) {
						element.setAttribute("desc", table.getDesc());
					}
					
					tablesElement.add(element);
				}
			}
		}
	}
	
	private void addRoleServiceRelation(DOMElement rootElement) {
		DOMElement relationsElement = new DOMElement("relations");
		rootElement.add(relationsElement);
		for (RoleServiceRelation relation : roleServiceRelationList) {
			DOMElement relationElement = new DOMElement("relation");
			
			StringBuffer serviceNameBuffer = new StringBuffer(64);
			if (relation.getServiceKey() != 0) {
				serviceNameBuffer.append(relation.getServiceKey());
			}
			if (!relation.getInterfaceName().isEmpty()) {
				if (relation.getServiceKey() != 0) {
					serviceNameBuffer.append("_");
				}
				serviceNameBuffer.append(relation.getInterfaceName());
			}
			relationElement.setAttribute("serviceName", serviceNameBuffer.toString());
			relationElement.setAttribute("roleName", relation.getRoleName());
			relationElement.setAttribute("userId", relation.getUserKey());
			relationElement.setAttribute("dbType", relation.getRelatedType().name());
			
			relationsElement.add(relationElement);
		}
	}
	
	public String getXml(boolean usePlainPassword, boolean prettyPrint) {
		DOMDocument doc = new DOMDocument();
		
		DOMElement dalSetRootElement = new DOMElement("dalset");
		doc.add(dalSetRootElement);
		
		addHosts(dalSetRootElement);
		addUsers(dalSetRootElement, usePlainPassword);
		addDbInstances(dalSetRootElement);
		addRoleServiceRelation(dalSetRootElement);
		
		OutputFormat xmlFormat = new OutputFormat();
		xmlFormat.setEncoding("UTF-8");
		if (prettyPrint) {
			xmlFormat.setIndent(true);
			xmlFormat.setNewlines(true);
		} 
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLWriter xmlWriter = null;
		try {
			xmlWriter = new XMLWriter(baos, xmlFormat);
			xmlWriter.write(doc);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (xmlWriter != null) {
				try {
					xmlWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos.toString();
	}
	
}
