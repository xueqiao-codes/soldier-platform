package org.soldier.platform.dal_set;

import org.soldier.base.Assert;

/**
 *  Db Set配置, 每一组DB set具备一个角色名称
 * @author Xairy
 */
public class DbSets {
	private static final DbInstance[] emptySet = new DbInstance[0]; 
	private DbInstance[] setInstances;
	private String roleName;
	
	public DbSets(){
		this.setInstances = emptySet;
		this.roleName = "";
	}
	
	public void setDbInstances(final DbInstance[] instances){
		Assert.True(instances != null);
		this.setInstances = instances;
	}
	
	/**
	 *  返回setKey对于master的集合
	 * @param setKey
	 * @return
	 */
	public DbInstance getMasterInstance(final long setKey){
		Assert.True(setInstances.length > 0);
		return setInstances[(int)(setKey % setInstances.length)];
	}
	
	/**
	 *  返回set的数量
	 * @return
	 */
	public int getSetsNum(){
		return setInstances.length;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		Assert.True(roleName != null && !roleName.isEmpty());
		this.roleName = roleName;
	}
}
