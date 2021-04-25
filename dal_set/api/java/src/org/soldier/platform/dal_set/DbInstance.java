package org.soldier.platform.dal_set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.soldier.base.Assert;

/**
 *  DB实例描述
 * @author Xairy
 */
public class DbInstance {
	public enum DbType{ Master, Slave, NoType};
	public enum DbDrive{
		Mysql{
			@Override
			ConnectionConfig createConnectionConfig() {
				return new MysqlConnectionConfig();
			}
		};
		
		abstract ConnectionConfig createConnectionConfig();
	}
	
	private String host = "";
	private String name = "";
	private int port = 3306;
	private DbType type;
	private DbDrive drive;
	private DbInstance nodeMaster;   // 指向自己的master实例
	private List<DbInstance> nodeSlave 
				= new ArrayList<DbInstance>(2);    // 指向自己的Slave实例
	private int slaveIndex = -1;       // 如果自身角色是slave，表示自身slave的Index
	private Map<String, TableInfo> tablesMap;
	private int weight =1;
	
	public DbInstance(){
		this.type = DbType.NoType;
		this.setDrive(DbDrive.Mysql);
		this.setNodeMaster(this);
		tablesMap = new HashMap<String, TableInfo>();
	}
	
	/**
	 *  获取主机Host
	 */
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		Assert.True(host != null && !host.isEmpty());
		this.host = host;
	}
	
	/**
	 *  获取端口
	 */
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		Assert.True(port > 0 && port < 65535);
		this.port = port;
	}

	/**
	 *  获取DB实例名称
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		Assert.True(name != null && !name.isEmpty());
		this.name = name;
	}
	
	/**
	 *  获取实例类型
	 * @return DbType.Master 或者 DbType.Slave
	 */
	public DbType getType() {
		return type;
	}
	public void setType(DbType type) {
		Assert.True(type != null);
		this.type = type;
	}

	public DbInstance getNodeMaster() {
		return nodeMaster;
	}
	public void setNodeMaster(DbInstance nodeMaster) {
		this.nodeMaster = nodeMaster;
	}

	public DbInstance getNodeSlave(int index) {
		return nodeSlave.get(index);
	}

	public void addNodeSlave(DbInstance nodeSlave) {
		Assert.True(nodeSlave != null);
		this.nodeSlave.add(nodeSlave);
	}
	
	public int getSlaveNum(){
		return nodeSlave.size();
	}

	public DbDrive getDrive() {
		return drive;
	}

	public void setDrive(DbDrive drive) {
		Assert.True(drive != null);
		this.drive = drive;
	}
	
	public final TableInfo getTableInfo(String tableNamePrefix){
		Assert.True(tableNamePrefix != null && !tableNamePrefix.isEmpty());
		return tablesMap.get(tableNamePrefix);
	}
	
	public void addTableInfo(final TableInfo table){
		Assert.True(table != null);
		Assert.True(table.getNamePrefix() != null && !table.getNamePrefix().isEmpty());
		tablesMap.put(table.getNamePrefix(), table);
	}

	public int getSlaveIndex() {
		return slaveIndex;
	}

	/**
	 *  设置slave节点在部署树中的位置， 应该在初始化的时候被配置
	 * @param slaveIndex
	 */
	public void setSlaveIndex(int slaveIndex) {
		Assert.True(slaveIndex >= 0 && slaveIndex < nodeMaster.nodeSlave.size());
		this.slaveIndex = slaveIndex;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		if (weight < 0) {
			return ;
		}
		this.weight = weight;
	}
}
