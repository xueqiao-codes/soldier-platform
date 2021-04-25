package org.soldier.platform.dal_set;

import org.soldier.base.Assert;

/**
 *  dal的关联关系
 * @author Xairy
 *
 */
public class DalRelation {
	private String serviceName = "";
	private String roleName = "";
	private String userId = "";
	private DbInstance.DbType dbType = DbInstance.DbType.Master;
	private int slaveIndex = -1;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		Assert.NotNull(serviceName);
		this.serviceName = serviceName;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		Assert.NotNull(roleName);
		this.roleName = roleName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		Assert.NotNull(userId != null);
		this.userId = userId;
	}
	
	public DbInstance.DbType getDbType() {
		return dbType;
	}
	public void setDbType(DbInstance.DbType dbType) {
		Assert.NotNull(dbType);
		this.dbType = dbType;
		if(this.dbType == DbInstance.DbType.Master){
			this.slaveIndex = -1;
		}
	}
	
	public int getSlaveIndex() {
		return slaveIndex;
	}
	public void setSlaveIndex(int slaveIndex) {
		Assert.True(slaveIndex >= 0);
		this.slaveIndex = slaveIndex;
	}
}
