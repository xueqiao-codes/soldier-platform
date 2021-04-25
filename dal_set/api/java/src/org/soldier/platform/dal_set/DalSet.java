package org.soldier.platform.dal_set;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.Assert;

/**
 *  DAL SET接口实现类
 * @author Xairy
 */
public class DalSet {
	private IConnectionPool connectionPool = null;
	private Map<String, DbUser> usersMap = null;
	private Map<String, DbSets> roleDbSets = null;
	private Map<String, List<DalRelation> > relationMap = null;
	
	public DalSet(final IConnectionPool connectionPool,
				  final Map<String, DbUser> usersMap,
				  final Map<String, DbSets> roleDbSets,
				  final  Map<String, List<DalRelation> > relationMap){
		Assert.True(connectionPool != null);
		Assert.True(usersMap != null);
		Assert.True(roleDbSets != null);
		Assert.True(relationMap != null);
		this.connectionPool = connectionPool;
		this.usersMap = usersMap;
		this.roleDbSets = roleDbSets;
		this.relationMap = relationMap;
	}
	
	public ConnectionConfig getConnectionConfig(final String roleName,
												final String serviceName,
												final boolean isReadOnly,
												final long setKey)throws SQLException{
		Assert.True(roleName != null && serviceName != null);
		Assert.True(!roleName.isEmpty() && !serviceName.isEmpty());
		
		// 查找关联关系
		List<DalRelation> relationList= relationMap.get(serviceName);
		if(relationList == null){
			// 去除中间杠的约束，寻找命令号对应的关联
			int endIndex = serviceName.indexOf('_');
			if(-1 != endIndex ){
				relationList = relationMap.get(serviceName.substring(0, endIndex));
			}
			if(relationList == null){
				throw new SQLException("service " + serviceName +
						" has no relation");
			}	
		}
		
		DalRelation relation = null;
		for( int index = 0; index < relationList.size(); ++index){
			if(relationList.get(index).
					getRoleName().equalsIgnoreCase(roleName)){
				relation = relationList.get(index);
				break;
			}
		}
		if(relation == null){
			throw new SQLException("service " + serviceName + 
					" for role "+ roleName + " relation not found");
		}	
				
		DbSets dbSets = getDbSets(roleName);
		// 找到对应的实例
		DbInstance instance = dbSets.getMasterInstance(setKey);
		if(relation.getSlaveIndex() >= 0 &&
				relation.getSlaveIndex() < instance.getSlaveNum()){
			// 说明是slave节点
			Assert.True(relation.getDbType() == DbInstance.DbType.Slave);
			instance = instance.getNodeSlave(relation.getSlaveIndex());
		}else if(relation.getSlaveIndex() >= instance.getSlaveNum()){
			throw new SQLException("error slave index " + relation.getSlaveIndex());
		}else{
			if (relation.getDbType() == DbInstance.DbType.NoType) {
				if (isReadOnly) {
					instance = getInstanceByWeight(roleName, instance);
				}
			} else {
				Assert.True(relation.getDbType() == DbInstance.DbType.Master);
			}
		}
		
		DbUser user = usersMap.get(relation.getUserId());
		if(user == null){
			throw new SQLException("error no user related for user id " + 
					relation.getUserId());
		}
				
		ConnectionConfig config = instance.getDrive().createConnectionConfig();
		config.setHost(instance.getHost());
		config.setPort(instance.getPort());
		config.setDbName(instance.getName());
		config.setUserName(user.getUserName());
		config.setPasswd(user.getUserPasswd());
		
		return config;
	}	
	
	/**
	 * 获取一个连接
	 * @param roleName    角色名称
	 * @param serviceName 服务名称, 可以根据服务名称关联master 或者 slave， 
	 * 				从而分离读写(由服务命令号+服务函数名称组成）
	 * @param setKey      分set的Key，例如用户系统很可能是用户的id 
	 * @return 返回一个DB实例的连接
	 */
	public Connection getConnection(
						final String roleName,
						final String serviceName,
						final boolean isReadOnly,
						final long setKey)throws SQLException{
		return connectionPool.getConnection(
				getConnectionConfig(roleName, serviceName, isReadOnly, setKey));
	}
	
	/**
	 *  获取表名
	 * @param roleName
	 * @param tablePrefix
	 * @param tableKey
	 * @return
	 * @throws SQLException 
	 */
	public String getTableName(
						final String roleName, 
						final String tableNamePrefix,
						final long tableKey) throws SQLException{
		if (roleName == null || roleName.isEmpty()) {
			throw new SQLException("roleName should not be null or empty!");
		}
		if (tableNamePrefix == null || tableNamePrefix.isEmpty()) {
			throw new SQLException("tableNamePrefix should not be null or empty!");
		}
		return getTable(roleName, tableNamePrefix).GetTableName(tableKey);
	}
	
	/**
	 *  获取Set的个数
	 * @throws SQLException 
	 */
	public int getSetNum(final String roleName) throws SQLException{
		return getDbSets(roleName).getSetsNum();
	}
	
	/**
	 *  获取分表个数
	 * @param roleName
	 * @return
	 * @throws SQLException
	 */
	public int getTableSliceNum(final String roleName, 
						final String tableNamePrefix) throws SQLException{
		if (roleName == null || roleName.isEmpty()) {
			throw new SQLException("roleName should not be null or empty!");
		}
		if (tableNamePrefix == null || tableNamePrefix.isEmpty()) {
			throw new SQLException("tableNamePrefix should not be null or empty!");
		}
		
		return getTable(roleName, tableNamePrefix).getSliceNum();
	}
	
	private DbSets getDbSets(final String roleName)throws SQLException{
		DbSets dbSets = roleDbSets.get(roleName);
		if(dbSets == null){
			throw new SQLException("role " + roleName + "has no related db sets");
		}
		return dbSets;
	}
	
	private TableInfo getTable(final String roleName, 
						final String tableNamePrefix) throws SQLException{
		DbInstance instance = getDbSets(roleName).getMasterInstance(0);
		TableInfo table =  instance.getTableInfo(tableNamePrefix);
		if(table == null){
			throw new SQLException("no table " + tableNamePrefix + 
					" found for role " + roleName);
		}
		return table;
	}
	
	private DbInstance getInstanceByWeight(String roleName, DbInstance masterInstance)
			throws SQLException {
		List<Integer> zone = new ArrayList<Integer>(masterInstance.getSlaveNum() + 1);
		int totalWeight = masterInstance.getWeight();
		zone.add(totalWeight);
		for (int index = 0; index < masterInstance.getSlaveNum(); ++index) {
			totalWeight += masterInstance.getNodeSlave(index).getWeight();
			zone.add(totalWeight);
		}
		
		int selectValue = 0;
		if (totalWeight == 0) {
			throw new SQLException(
					"role " + roleName + " relation Sets TotalWeight is error");
		} else {
			// random the value
			selectValue = 1+ ( RandomUtils.nextInt() %  totalWeight );
			Assert.True(selectValue >= 1 && selectValue <= totalWeight);
		}
		
		int lastZoneValue = 0;
		DbInstance result = null;
		for (int index = 0; index < zone.size(); lastZoneValue=zone.get(index), ++index) {
			if (lastZoneValue == zone.get(index).intValue()) {
				continue ;
			}
			
			if (zone.get(index).intValue() >= selectValue) {
				if (0 == index) {
					result = masterInstance;
				} else {
					result = masterInstance.getNodeSlave(index - 1);
				}
				break;
			}
		}
		Assert.True(result != null);
		return result;
	}
}
