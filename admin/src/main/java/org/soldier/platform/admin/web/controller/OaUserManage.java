package org.soldier.platform.admin.web.controller;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.oa.user.OaUser;
import org.soldier.platform.oa.user.QueryUserOption;
import org.soldier.platform.oa.user.ao.client.OaUserAoStub;

public class OaUserManage extends WebAuthController {
	
	public static class OaUserListResult {
		private List<OaUser> data;

		public List<OaUser> getData() {
			return data;
		}

		public void setData(List<OaUser> data) {
			this.data = data;
		}
	}
	
	public void select2UserList() throws Exception {
		String query = parameter("query", "").trim();
		int limit = parameter("limit", 10);
		if (limit <= 0) {
			limit = 10;
		}
		
		QueryUserOption option = new QueryUserOption();
		option.setUserNamePartical(query);
		
		OaUserAoStub stub = new OaUserAoStub();
		OaUserListResult result = new OaUserListResult();
		result.setData(stub.queryUserByPage(RandomUtils.nextInt(), 2000, option, 0, limit).getPageRecords());
		echoJson(result);
	}
}
