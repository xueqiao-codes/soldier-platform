package org.soldier.platform.dal_set;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 *  
 * 构建Dal Set实例的配置的
 */
public interface IDalSetBuilder {
	public void doBuild(InputStream input, 
			List<String> serviceNameList)throws Exception;
	
	public Map<String, DbUser> getUsersMap();
	public Map<String, DbSets> getRolesDbSet();
	public Map<String, List<DalRelation> > getRelationMap();
}
