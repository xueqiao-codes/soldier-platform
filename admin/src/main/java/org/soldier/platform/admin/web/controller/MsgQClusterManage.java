package org.soldier.platform.admin.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.msgq.dao.MsgQCluster;
import org.soldier.platform.msgq.dao.MsgQClusterList;
import org.soldier.platform.msgq.dao.MsgQTopicList;
import org.soldier.platform.msgq.dao.QueryMsgQClusterOption;
import org.soldier.platform.msgq.dao.QueryMsgQTopicOption;
import org.soldier.platform.msgq.dao.client.MsgQManageDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web_framework.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeInner;
import com.antiy.error_code.ErrorCodeOuter;

public class MsgQClusterManage extends WebAuthController {
	private static List<String> SUPPORTED_MQ_TYPES = new ArrayList<String>();
	
	static {
		SUPPORTED_MQ_TYPES.add("AliyunMQ");
	}
	
	public void showMsgQCluster() throws Exception {
		put("supportClusterTypes", SUPPORTED_MQ_TYPES);
		render("msgq/MsgQCluster.html");
	}
	
	public void clusterListData() throws Exception {
		int pageIndex = parameter("page", 1) ;
		if(pageIndex > 1){
			--pageIndex;
		} else {
			pageIndex = 0;
		}
		int pageSize = parameter("rp", 20);
		if(pageSize <= 0){
			pageSize = 20;
		}
		
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		QueryMsgQClusterOption option = new QueryMsgQClusterOption();
	
		String clusterName = parameter("clusterName", "").trim();
		if (!clusterName.isEmpty()) {
			option.setClusterName(clusterName);
		}
		
		String clusterType = parameter("clusterType", "").trim();
		if (!clusterType.isEmpty()) {
			if (SUPPORTED_MQ_TYPES.contains(clusterType)) {
				option.setClusterType(clusterType);
			}
		}
		
		String clusterBrokers = parameter("clusterBrokers", "").trim();
		if (!clusterBrokers.isEmpty()) {
			option.setClusterBroker(clusterBrokers);
		}
		
		String remark = parameter("remark", "").trim();
		if (!remark.isEmpty()) {
			option.setClusterDesc(remark);
		}
		
		String clusterPropertyKey = parameter("clusterPropertyKey", "").trim();
		if (!clusterPropertyKey.isEmpty()) {
			StringBuilder expression = new StringBuilder(64);
			expression.append(clusterPropertyKey);
			expression.append("=");
			
			String clusterPropertyValue = parameter("clusterPropertyValue", "").trim();
			if (!clusterPropertyValue.isEmpty()) {
				expression.append(clusterPropertyValue);
			}
			
			option.setClusterPropertyExpression(expression.toString());
		}
		
		put("pageIndex", pageIndex);
		put("itemsResult", stub.queryMsgQClusters(RandomUtils.nextInt(), 3000, pageIndex, pageSize, option));
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		render("msgq/json/MsgQClusterData.json");
	}
	
	public void opMsgQCluster() throws Exception {
		ErrorResult result = new ErrorResult();
		doOpMsgQCluster(result);
		echoJson(result);
	}
	
	private MsgQCluster checkMsgQCluster(ErrorResult result) {
		MsgQCluster cluster = new MsgQCluster();
		
		String clusterName = parameter("clusterName", "").trim();
		if (clusterName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("集群名称必须填写");
			return null;
		}
		cluster.setClusterName(clusterName);
		
		String clusterBrokers = parameter("clusterBrokers", "").trim();
		if (clusterBrokers.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("集群地址必须填写");
			return null;
		}
		cluster.setClusterBrokers(clusterBrokers);
		
		String clusterType = parameter("clusterType", "").trim();
		if (clusterType.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("集群类型必须选择");
			return null;
		}
		if (!SUPPORTED_MQ_TYPES.contains(clusterType)) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("不支持的集群类型");
			return null;
		}
		cluster.setClusterType(clusterType);
		
		String clusterDesc = parameter("clusterDesc", "").trim();
		if (clusterDesc.length() < 6) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("备注不得少于6个字");
			return null;
		}
		cluster.setClusterDesc(clusterDesc);
		
		return cluster;
	}
	
	private void doAddMsgQCluster(MsgQCluster cluster, ErrorResult result) throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		QueryMsgQClusterOption option = new QueryMsgQClusterOption();
		option.setClusterName(cluster.getClusterName());
		
		MsgQClusterList clusterList = stub.queryMsgQClusters(RandomUtils.nextInt(), 3000, 0, 10, option);
		if (clusterList.getTotalNum() > 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("集群" + cluster.getClusterName() + "已经存在");
			return ;
		}
		
		stub.addMsgQCluster(RandomUtils.nextInt(), 3000, cluster);
	}
	
	private void doUpdateMsgQCluster(MsgQCluster cluster, ErrorResult result) throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		
		try {
			stub.updateMsgQCluster(RandomUtils.nextInt(), 3000, cluster);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				      .setErrorMsg(cluster.getClusterName() + "不存在");
				return ;
			}
			throw err;
		}
	}
	
	private void doOpMsgQCluster(ErrorResult result) throws Exception{
		MsgQCluster cluster = checkMsgQCluster(result);
		if (cluster == null) {
			return ;
		}
		
		String op = parameter("op", "").trim();
		if (op.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("no op parameter");
			return ;
		}
		
		if (op.equals("add")) {
			doAddMsgQCluster(cluster, result);
		} else if (op.equals("update")) {
			doUpdateMsgQCluster(cluster, result);
		} else {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("op parameter error");
		}
	}
	
	public void deleteMsgQCluster() throws Exception {
		ErrorResult result = new ErrorResult();
		doDeleteMsgQCluster(result);
		echoJson(result);
	}
	
	private void doDeleteMsgQCluster(ErrorResult result) throws Exception{
		String clusterName = parameter("clusterName", "").trim();
		if (clusterName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("clusterName should be set");
			return ;
		}
		
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		QueryMsgQTopicOption option = new QueryMsgQTopicOption();
		option.setTopicCluster(clusterName);
		
		MsgQTopicList relatedTopicList = stub.queryMsgQTopics(RandomUtils.nextInt(), 3000, 0, 10, option);
		if (relatedTopicList.getTotalNum() > 0) {
			result.setErrorCode(ErrorCodeOuter.ILLEGAL_OPEARTION_ERROR.getErrorCode()).setErrorMsg("有主题关联集群，无法删除");
			return ;
		}
		
		try {
			stub.deleteMsgQCluster(RandomUtils.nextInt(), 3000, clusterName);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("记录不存在");
				return ;
			}
			throw err;
		}
	}
}
