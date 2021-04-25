package org.soldier.platform.admin.controller.json;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TException;
import org.soldier.platform.admin.controller.AjaxOpException;
import org.soldier.platform.admin.controller.CJsonAjaxOpController;
import org.soldier.platform.dal_set.dao.DalSetTable;
import org.soldier.platform.dal_set.dao.DalSetTableList;
import org.soldier.platform.dal_set.dao.QueryDalSetTableOption;
import org.soldier.platform.dal_set.dao.QueryRoleTableRelationOption;
import org.soldier.platform.dal_set.dao.RoleTableRelationList;
import org.soldier.platform.dal_set.dao.client.DalSetDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.antiy.error_code.ErrorCodeOuter;

public class DalSetTableOperation extends CJsonAjaxOpController {
	
	private String checkrefixName() throws AjaxOpException {
		String prefixName = parameter("prefixName", "").trim();
		if (prefixName.isEmpty()) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"表名前缀未填写");
		}
		return prefixName;
	}
	
	private int checkSliceNum() throws AjaxOpException {
		int sliceNum = parameter("sliceNum", -1);
		if (sliceNum < 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"分表述填写错误");
		}
		return sliceNum;
	}
	
	private boolean checkFillZero() throws AjaxOpException {
		String fillZero = parameter("fillZero", "").trim();
		if (fillZero.equalsIgnoreCase("false")) {
			return false;
		} else if (fillZero.equalsIgnoreCase("true")) {
			return true;
		} else {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"填充0参数错误");
		}
	}
	
	private String checkDesc() throws AjaxOpException {
		String desc = parameter("remark", "").trim();
		if (desc.length() < 6) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"备注长度不得小于6个字符");
		}
		return desc;
	}
	
	@Override
	protected void doAdd() throws AjaxOpException, ErrorInfo, TException {
		String prefixName = checkrefixName();
		int sliceNum = checkSliceNum();
		boolean fillZero = checkFillZero();
		String desc = checkDesc();
		
		QueryDalSetTableOption option = new QueryDalSetTableOption();
		option.setPrefixName(prefixName);
		
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetTableList tableList = stub.queryDalSetTables(RandomUtils.nextInt(),1500, 0, 10, option);
		if (tableList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"重复的表名前缀，请重新选择");
		}
		
		DalSetTable table = new DalSetTable();
		table.setPrefixName(prefixName);
		table.setSliceNum(sliceNum);
		table.setFillZero(fillZero);
		table.setDesc(desc);
		
		stub.addDalSetTable(RandomUtils.nextInt(), 1500, table);
	}

	@Override
	protected void doUpdate() throws AjaxOpException, ErrorInfo, TException {
		String prefixName = checkrefixName();
		int sliceNum = checkSliceNum();
		boolean fillZero = checkFillZero();
		String desc = checkDesc();
		
		QueryDalSetTableOption option = new QueryDalSetTableOption();
		option.setPrefixName(prefixName);
		
		DalSetDaoStub stub = new DalSetDaoStub();
		
		DalSetTableList tableList = stub.queryDalSetTables(RandomUtils.nextInt(),1500, 0, 10, option);
		if (tableList.totalNum == 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"操作表名前缀不存在");
		}
		
		DalSetTable table = new DalSetTable();
		table.setPrefixName(prefixName);
		table.setSliceNum(sliceNum);
		table.setFillZero(fillZero);
		table.setDesc(desc);
		stub.updateDalSetTable(RandomUtils.nextInt(), 1500, table);
	}
	
	@Override
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		String prefixName = checkrefixName();
		QueryRoleTableRelationOption option = new QueryRoleTableRelationOption();
		option.setTablePrefixName(prefixName);
		
		DalSetDaoStub stub = new DalSetDaoStub();
		RoleTableRelationList relationList = stub.queryTableRoleRelations(prefixName.hashCode(),
				2000, 0, 10, option);
		if (relationList.getTotalNum() > 0) {
			throw new AjaxOpException(ErrorCodeOuter.PARAM_ERROR.getErrorCode(),
					"表存在角色关联，无法删除");
		}
		
		stub.deleteDalSetTable(RandomUtils.nextInt(), 1500, prefixName);
	}

}
