package org.soldier.platform.admin.controller;

import java.util.Map;

import org.apache.thrift.TException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

/**
 *  简单Ajax操作返回{"errorCode":XXX, "errorMsg":""}的抽象实现类
 * @author Xairy
 */
public abstract class CJsonAjaxOpController extends CAjaxController{
	protected void doAjax(Map<Object, Object> dataModel) 
			throws AjaxOpException, ErrorInfo, TException {
		String op = parameter("op", "add");
		if (op.equals("add")) {
			doAdd();
		} else if (op.equals("update")) {
			doUpdate();
		} else if (op.equals("delete")) {
			doDelete();
		} else {
			throw new AjaxOpException(500, "OP is not supported!");
		}
	}
	
	@Override
	public String getTemplatePath(String controllerPath){
		return "json/AjaxOpResult.ftl";
	}
	
	protected abstract void doAdd() throws AjaxOpException, ErrorInfo, TException;
	protected abstract void doUpdate() throws AjaxOpException,  ErrorInfo, TException;
	protected void doDelete() throws AjaxOpException, ErrorInfo, TException {
		throw new AjaxOpException(500, "Not Supported!");
	}
}
