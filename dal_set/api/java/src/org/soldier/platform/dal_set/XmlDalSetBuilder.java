package org.soldier.platform.dal_set;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.soldier.base.Assert;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlDalSetBuilder extends DefaultHandler implements IDalSetBuilder {
	private Map<String, DbUser> usersMap = new HashMap<String, DbUser>();
	private Map<String, DbSets> roleDbSets = new HashMap<String, DbSets>();
	private Map<String, List<DalRelation>> relationMap 
					= new HashMap<String, List<DalRelation>>();
	private Map<String, DbHost> hostMap = new HashMap<String, DbHost>();
	
	private static class Node{
		public String nodeName = "";
	}
	
	private static class DbNode extends Node{
		public String roleName = "";
		public String dbName = "";
		public DbInstance.DbDrive dbDrive = DbInstance.DbDrive.Mysql;
		public List<DbInstance> setsList = new ArrayList<DbInstance>();
	}
	
	private static class DbSetNode extends Node{
		public int index = 0;
	}
	
	private Stack<Node> parseNodeStack = new Stack<Node>();
	
	private static enum ElementHandler{
		host(new String[]{"hosts"}){
			@Override
			Node startElement(XmlDalSetBuilder builder, Attributes attributes)
					throws SAXException {
				String hostName = builder.getValueString("name", attributes);
				if(hostName == null){
					throw new SAXException("no host name");
				}
				String hostIp = builder.getValueString("ip", attributes);
				if(hostIp == null){
					throw new SAXException(super.error("no host ip"));
				}
				int hostPort = builder.getValueInt("port", attributes);
				if(hostPort <= 0){
					throw new SAXException(
							super.error("no host port or value erorr"));
				}
				
				if(builder.relationMap.containsKey(hostName)){
					throw new SAXException(
							super.error("duplicate entry for host[name=" + 
										hostName + "]"));
				}
				DbHost host = new DbHost();
				host.setIp(hostIp);
				host.setPort(hostPort);
				builder.hostMap.put(hostName, host);
				
				return null;
			}

			@Override
			void endElement(XmlDalSetBuilder builder) throws SAXException {
			}
		},
		
		db(new String[]{"dbInstances"}){
			@Override
			Node startElement(XmlDalSetBuilder builder, Attributes attributes)
					throws SAXException {
				String dbName = builder.getValueString("name", attributes);
				if(dbName == null || dbName.isEmpty()){
					throw new SAXException(super.error("no name found"));
				}
				
				DbInstance.DbDrive dbDrive = builder.getDrive(
						builder.getValueString("drive", attributes));
				if(dbDrive == null){
					throw new SAXException(super.error("no drive found"));
				}
				String roleName = builder.getValueString("rolename", attributes);
				if(roleName == null){
					throw new SAXException(super.error("no rolename found"));
				}
				
				DbNode resultNode = new DbNode();
				resultNode.dbName = dbName;
				resultNode.roleName = roleName;
				resultNode.dbDrive = dbDrive;
				return resultNode;
			}

			@Override
			void endElement(XmlDalSetBuilder builder) throws SAXException {
				DbNode dbNode = (DbNode)builder.parseNodeStack.peek();
				DbSets dbSets = new DbSets();
				dbSets.setRoleName(dbNode.roleName);
				if(!dbNode.setsList.isEmpty()){
					dbSets.setDbInstances(
							(DbInstance[])dbNode.setsList.toArray(new DbInstance[0]));
				}//else{
//					throw new SAXException(this.name() + " ends, but no sets found");
//				}
				builder.roleDbSets.put(dbNode.roleName, dbSets);
			}
		},
		
		set(new String[]{"dbInstances", "db", "sets"}){
			@Override
			Node startElement(XmlDalSetBuilder builder, Attributes attributes)
					throws SAXException {
				DbNode dbSets = (DbNode)super.getNode(builder, 1);
				int index = builder.getValueInt("index", attributes);
				if(index < 0){
					throw new SAXException(super.error("no index found or value error"));
				}
				if(index != dbSets.setsList.size()){
					throw new SAXException(super.error("index value is not expected, " +
							"please make the order"));
				}
				DbSetNode resultNode = new DbSetNode();
				resultNode.index = index;
				return resultNode;
			}

			@Override
			void endElement(XmlDalSetBuilder builder) throws SAXException {
			}
		},
		
		master(new String[]{"dbInstances", "db", "sets", "set"}){
			@Override
			Node startElement(XmlDalSetBuilder builder, Attributes attributes)
					throws SAXException {
				String hostName = builder.getValueString("host", attributes);
				if(hostName == null){
					throw new SAXException(super.error("no host attribute found"));
				}
				DbHost host = builder.hostMap.get(hostName);
				if(host == null){
					throw new SAXException(super.error("no DbHost found for " + hostName));
				}
				
				DbNode dbNode = (DbNode)super.getNode(builder, 1);
				DbInstance instance = new DbInstance();
				instance.setDrive(dbNode.dbDrive);
				instance.setHost(host.getIp());
				instance.setName(dbNode.dbName);
				instance.setPort(host.getPort());
				instance.setNodeMaster(instance);
				instance.setType(DbInstance.DbType.Master);
				instance.setWeight(builder.getWeight(attributes));
				dbNode.setsList.add(instance);
				return null;
			}

			@Override
			void endElement(XmlDalSetBuilder builder) throws SAXException {
			}
		},
		
		slave(new String[]{"dbInstances", "db", "sets", "set"}){
			@Override
			Node startElement(XmlDalSetBuilder builder, Attributes attributes)
					throws SAXException {
				DbNode dbNode = (DbNode)super.getNode(builder, 1);
				DbSetNode dbSetNode = (DbSetNode)super.getNode(builder, 3);
				if(dbNode.setsList.size() <= dbSetNode.index){
					throw new SAXException(
							super.error("slave node declare before the master node"));
				}
				DbInstance master = dbNode.setsList.get(dbSetNode.index);
				
				String hostName = builder.getValueString("host", attributes);
				if(hostName == null){
					throw new SAXException(super.error("no host attribute found"));
				}
				DbHost host = builder.hostMap.get(hostName);
				if(host == null){
					throw new SAXException(super.error("no DbHost found for " + hostName));
				}
				
				int slaveIndex = builder.getValueInt("index", attributes);
				if(slaveIndex < 0 ){
					throw new SAXException(
							super.error("index attrbutes not found or error"));
				}
				if(slaveIndex != master.getSlaveNum()){
					throw new SAXException("slave index error, please check the slave index");
				}
				
				DbInstance instance = new DbInstance();
				instance.setDrive(dbNode.dbDrive);
				instance.setHost(host.getIp());
				instance.setName(dbNode.dbName);
				instance.setNodeMaster(master);
				instance.setPort(host.getPort());
				instance.setType(DbInstance.DbType.Slave);
				instance.setWeight(builder.getWeight(attributes));
				master.addNodeSlave(instance);
				instance.setSlaveIndex(slaveIndex);
				
				return null;
			}

			@Override
			void endElement(XmlDalSetBuilder builder) throws SAXException {
			}
			
		},
		
		table(new String[]{"dbInstances", "db", "tables"}){
			@Override
			Node startElement(XmlDalSetBuilder builder, Attributes attributes)
					throws SAXException {
				DbNode dbNode = (DbNode)super.getNode(builder, 1);
				if(dbNode.setsList.isEmpty()){
					throw new SAXException("no sets declare before");
				}
				
				String tablePrefix = builder.getValueString("prefix", attributes);
				if(tablePrefix == null || tablePrefix.isEmpty()){
					throw new SAXException("prefix attribute not found or empty");
				}
				int tableSliceNum = builder.getValueInt("sliceNum", attributes);
				if(tableSliceNum < 0){
					throw new SAXException("sliceNum attribute not found or error");
				}
				String tableFillZero = builder.getValueString("fillZero", attributes);
				boolean bFillZero = false;
				if(tableFillZero != null){
					if(tableFillZero.equalsIgnoreCase("true")){
						bFillZero = true;
					}else if(tableFillZero.equalsIgnoreCase("false")){
						bFillZero = false;
					}else{
						throw new SAXException("fillZero attribute value error");
					}
				}
				
				TableInfo table = new TableInfo();
				table.setNamePrefix(tablePrefix);
				table.setSliceNum(tableSliceNum);
				table.setFillZero(bFillZero);
				
				dbNode.setsList.get(0).addTableInfo(table);
				
				return null;
			}

			@Override
			void endElement(XmlDalSetBuilder builder) throws SAXException {
			}
		},
		
		user(new String[]{"users"}){
			@Override
			Node startElement(XmlDalSetBuilder builder, Attributes attributes)
					throws SAXException {
				String userId = builder.getValueString("id", attributes);
				if(userId == null || userId.isEmpty()){
					throw new SAXException("id attribute not found or empty");
				}
				String userName = builder.getValueString("name", attributes);
				if(userName == null || userName.isEmpty()){
					throw new SAXException("name attribute not found or empty");
				}
				String passwd = builder.getValueString("password", attributes);
				if(passwd == null){
					passwd = "";
				}
				
				DbUser user = new DbUser();
				user.setUserId(userId);
				user.setUserName(userName);
				user.setUserPasswd(passwd);
				
				builder.usersMap.put(userId, user);
				return null;
			}

			@Override
			void endElement(XmlDalSetBuilder builder) throws SAXException {
			}
		},
		
		relation(new String[]{"relations"}){
			@Override
			Node startElement(XmlDalSetBuilder builder, Attributes attributes)
					throws SAXException {
				String serviceName = builder.getValueString("serviceName", attributes);
				if(serviceName == null || serviceName.isEmpty()){
					throw new SAXException("serviceName attribute not found or empty");
				}
				String roleName = builder.getValueString("roleName", attributes);
				if(roleName == null || roleName.isEmpty()){
					throw new SAXException("roleName attribute not found or empty");
				}
				if(!builder.roleDbSets.containsKey(roleName)){
					throw new SAXException("roleName " + roleName + " not existed ");
				}
				
				String userId = builder.getValueString("userId", attributes);
				if(userId == null || userId.isEmpty()){
					throw new SAXException("userId attribute not found or empty");
				}
				if(!builder.usersMap.containsKey(userId)){
					throw new SAXException("userId " + userId + " is not existed");
				}
				
				String dbTypeStr = builder.getValueString("dbType", attributes);
				if(dbTypeStr == null || dbTypeStr.isEmpty()){
					throw new SAXException("dbType attribute not found or empty");
				}
				
				DalRelation relation = new DalRelation();
				relation.setRoleName(roleName);
				relation.setServiceName(serviceName);
				relation.setUserId(userId);
				relation.setDbType(DbInstance.DbType.NoType);
				if(dbTypeStr.equalsIgnoreCase(DbInstance.DbType.Master.name())){
					relation.setDbType(DbInstance.DbType.Master);
				}else if(dbTypeStr.toLowerCase().startsWith(
							DbInstance.DbType.Slave.name().toLowerCase())){
					relation.setDbType(DbInstance.DbType.Slave);
					try{
						relation.setSlaveIndex(Integer.parseInt(dbTypeStr.substring(5)));
					}catch(NumberFormatException e){
						throw new SAXException("dbType " + dbTypeStr + 
								" number format error");
					}
					DbInstance masterInstance = 
							builder.roleDbSets.get(roleName).getMasterInstance(0);
					if(relation.getSlaveIndex() < 0 || 
					   relation.getSlaveIndex() > masterInstance.getSlaveNum() ){
						throw new SAXException("dbType " + dbTypeStr + " unexpected slave index");
					}
				} else{
					if (!dbTypeStr.equalsIgnoreCase(DbInstance.DbType.NoType.name())) {
						throw new SAXException("dbType " + dbTypeStr + "is not correct");
					}
				}
				
				List<DalRelation> relationList = builder.relationMap.get(serviceName);
				if(relationList == null){
					relationList = new ArrayList<DalRelation>();
					builder.relationMap.put(serviceName, relationList);
				}
				relationList.add(relation);
				return null;
			}

			@Override
			void endElement(XmlDalSetBuilder builder) throws SAXException {
			}
			
		};
//		
		abstract Node startElement(XmlDalSetBuilder builder, 
						Attributes attributes) throws SAXException;
		abstract void endElement(XmlDalSetBuilder builder)throws SAXException;
		
		public final String[] getNodePath(){
			return this.nodePath;
		}
		
		private ElementHandler(final String[] nodePath){
			Assert.True(nodePath != null);
			this.nodePath = nodePath;
		}
		private final String error(final String msg){
			return "[" + this.name() + "] " + msg;
		}
		
		private final Node getNode(XmlDalSetBuilder builder, final int pathIndex){
			Assert.True(pathIndex >= 0 && pathIndex < nodePath.length);
			Node node = builder.parseNodeStack.get( 
					builder.parseNodeStack.size() - (nodePath.length - pathIndex));
			Assert.True(node.nodeName.equalsIgnoreCase(nodePath[pathIndex]));
			return node;
		}
		
		private String[] nodePath;
	}
	
	@Override
	public void doBuild(InputStream input, List<String> serviceNameList)
			throws Exception {
		Assert.NotNull(input);
		Assert.NotNull(serviceNameList);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(input, this);
		if(getUsersMap() == null){
			throw new ParseException("no users found", 0);
		}
		if(roleDbSets == null){
			throw new ParseException("no dbsets found", 0);
		}
		if(relationMap == null){
			throw new ParseException("no relation found", 0);
		}
	}

	@Override
	public Map<String, DbUser> getUsersMap() {
		return this.usersMap;
	}

	@Override
	public Map<String, DbSets> getRolesDbSet() {
		return this.roleDbSets;
	}

	@Override
	public Map<String, List<DalRelation>> getRelationMap() {
		return this.relationMap;
	}
	
	@Override  
    public void startElement(String uri, String localName,
    		String qName, Attributes attributes) throws SAXException {
		if(parseNodeStack.isEmpty()){
			// 根节点名称必须是dalset
			if(!qName.equalsIgnoreCase("dalset")){
				throw new SAXException("error root, should be dalset");
			}
		}
		
		Node node = null;
		ElementHandler handler = findElementHandler(qName);
		if(handler != null){
			// 每个节点进来前必须检查路径
			boolean checkPath = true;
			if(handler.getNodePath().length == parseNodeStack.size() - 1){
				int index = 1;
				for(String nodeName : handler.getNodePath()){
					if(!parseNodeStack.get(index).nodeName.equalsIgnoreCase(nodeName)){
						checkPath = false;
						break;
					}
					++index;
				}
			}else{
				checkPath = false;
			}
			
			if(!checkPath){
				StringBuffer currentPathBuffer = new StringBuffer(56);
				for(Node stackNode : parseNodeStack){
					currentPathBuffer.append(stackNode.nodeName);
					currentPathBuffer.append("->");
				}
				
				throw new SAXException("node " + qName + 
						" has unexpected position must under [dalset->" +  
						nodePathToString(handler.getNodePath()) + "] but current is [" +
						currentPathBuffer.toString() + "]");
			}
			
			node  = handler.startElement(this, attributes);
		}
		
		if(node == null){
			node = new Node();
		}
		if(node.nodeName == null || node.nodeName.isEmpty()){
			node.nodeName = qName;
		}
		
		parseNodeStack.push(node);
    } 
	
	@Override
	public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
		ElementHandler handler = findElementHandler(qName);
		if(handler != null){
			handler.endElement(this);
		}
		parseNodeStack.pop();
	}
	
	private ElementHandler findElementHandler(final String qName){
		for(ElementHandler handler : ElementHandler.values()){
			if(handler.name().equalsIgnoreCase(qName)){
				return handler;
			}
		}
		return null;
	}
	
	private String nodePathToString(final String[] nodePath){
		StringBuffer buffer = new StringBuffer(128);
		for(String node : nodePath){
			if(node != nodePath[0]){
				buffer.append("->");
			}
			buffer.append(node);
		}
		return buffer.toString();
	}
	
	private DbInstance.DbDrive getDrive(final String driveName){
		if(driveName == null || driveName.isEmpty()){
			return DbInstance.DbDrive.Mysql;
		}
		for(DbInstance.DbDrive drive : DbInstance.DbDrive.values()){
			if(drive.name().equalsIgnoreCase(driveName)){
				return drive;
			}
		}
		return null;
	}
	
	private int getValueInt(final String key, final Attributes attributes){
		String strValue = getValueIfVariable(attributes.getValue(key));
		if(strValue == null){
			return -1;
		}
		int result = 0;
		try{
			result = Integer.parseInt(strValue);
		}catch(NumberFormatException e){
			return -1;
		}
		return result;
	}
	
	private String getValueString(final String key, final Attributes attributes) {
		return getValueIfVariable(attributes.getValue(key));
	}
	
	private String getValueIfVariable(final String value) {
		if (value == null) {
			return null;
		}
		String trimValue = value.trim();
		if (trimValue.startsWith("${") && trimValue.endsWith("}")) {
			return VariableFactory.getInstance().getProvider().getVariable(
					trimValue.substring(2, trimValue.length() - 1));
		}
		return value;
	}
	
	private int getWeight(final Attributes attributes) throws SAXException{
		String weightStr = getValueString("weight", attributes);
		int weightValue = 1;
		if (weightStr != null) {
			try {
				weightValue = Integer.parseInt(weightStr.trim());
				if (weightValue < 0) {
					throw new SAXException("weight value must not < 0");
				}
			} catch(NumberFormatException e) {
				throw new SAXException("weight value is not right!");
			}
		}
		return weightValue;
	}
}
