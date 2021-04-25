package org.soldier.platform.admin.web.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.msgq.dao.MsgQClusterList;
import org.soldier.platform.msgq.dao.MsgQComsumerList;
import org.soldier.platform.msgq.dao.MsgQProducerList;
import org.soldier.platform.msgq.dao.MsgQTopic;
import org.soldier.platform.msgq.dao.MsgQTopicList;
import org.soldier.platform.msgq.dao.QueryMsgQClusterOption;
import org.soldier.platform.msgq.dao.QueryMsgQConsumerOption;
import org.soldier.platform.msgq.dao.QueryMsgQProducerOption;
import org.soldier.platform.msgq.dao.QueryMsgQTopicOption;
import org.soldier.platform.msgq.dao.client.MsgQManageDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web_framework.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeInner;
import com.antiy.error_code.ErrorCodeOuter;

public class MsgQTopicManage extends WebAuthController {
	
	public void showMsgQTopic() throws Exception {
		MsgQClusterList clusterList = new MsgQManageDaoStub().queryMsgQClusters(
				RandomUtils.nextInt(), 3000
				, 0, 100, new QueryMsgQClusterOption());
		
		put("clusterItems", clusterList.getClusterList());
		
		render("msgq/MsgQTopic.html");
	}
	
	public void topicListData() throws Exception {
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
		QueryMsgQTopicOption option = new QueryMsgQTopicOption();
	
		String topicName = parameter("topicName", "").trim();
		if (!topicName.isEmpty()) {
			option.setTopicName(topicName);
		}
		
		String topicCluster = parameter("topicCluster", "").trim();
		if (!topicCluster.isEmpty()) {
			option.setTopicCluster(topicCluster);
		}
		
		String topicDesc = parameter("remark", "").trim();
		if (!topicDesc.isEmpty()) {
			option.setTopicDesc(topicDesc);
		}
		
		String topicPropertyKey = parameter("topicPropertyKey", "").trim();
		if (!topicPropertyKey.isEmpty()) {
			StringBuilder expression = new StringBuilder(64);
			expression.append(topicPropertyKey);
			expression.append("=");
			
			String topicPropertyValue = parameter("topicPropertyValue", "").trim();
			if (!topicPropertyValue.isEmpty()) {
				expression.append(topicPropertyValue);
			}
			
			option.setTopicPropertyExpression(expression.toString());
		}
		
		put("pageIndex", pageIndex);
		put("itemsResult", stub.queryMsgQTopics(RandomUtils.nextInt(), 3000, pageIndex, pageSize, option));
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		render("msgq/json/MsgQTopicData.json");
	}
	
	public void opMsgQTopic() throws Exception {
		ErrorResult result = new ErrorResult();
		doOpMsgQTopic(result);
		echoJson(result);
	}
	
	public void deleteMsgQTopic() throws Exception {
		ErrorResult result = new ErrorResult();
		doDeleteMsgQTopic(result);
		echoJson(result);
	}
	
	private void doDeleteMsgQTopic(ErrorResult result) throws Exception {
		String topicName = parameter("topicName", "").trim();
		if (topicName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("请选择删除的主题");
			return ;
		}
		
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		
		QueryMsgQConsumerOption queryConsumerOption = new QueryMsgQConsumerOption();
		queryConsumerOption.setTopicName(topicName);
		
		MsgQComsumerList relateConsumerList = stub.queryMsgQConsumers(RandomUtils.nextInt()
				, 3000, 0, 10, queryConsumerOption);
		if (relateConsumerList.getTotalNum() > 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("主题有关联消费者,无法删除");
			return ;
		}
		
		QueryMsgQProducerOption queryProducerOption = new QueryMsgQProducerOption();
		queryProducerOption.setTopicName(topicName);
		
		MsgQProducerList relateProducerList = stub.queryMsgQProducerList(RandomUtils.nextInt()
				, 3000, 0, 10, queryProducerOption);
		if (relateProducerList.getTotalNum() > 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("主题有关联生产者,无法删除");
			return ;
		}
		
		try {
			stub.deleteMsgQTopic(RandomUtils.nextInt(), 3000, topicName);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("记录不存在");
				return ;
			}
			throw err;
		}
	}
	
	private MsgQTopic checkMsgQTopic(ErrorResult result) throws Exception {
		MsgQTopic topic = new MsgQTopic();
		String topicName = parameter("topicName", "").trim();
		if (topicName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("主题名称必须填写");
			return null;
		}
		topic.setTopicName(topicName);
		
		String topicCluster = parameter("topicCluster", "").trim();
		if (topicCluster.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须选择部署集群");
			return null;
		}
		
		QueryMsgQClusterOption queryClusterOption = new QueryMsgQClusterOption();
		queryClusterOption.setClusterName(topicCluster);
		if (new MsgQManageDaoStub().queryMsgQClusters(RandomUtils.nextInt()
				, 3000, 0, 10, queryClusterOption).getTotalNum() <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("部署集群不存在");
			return null;
		}
		topic.setTopicCluster(topicCluster);
		
		String topicDesc = parameter("topicDesc", "").trim();
		if (topicDesc.length() < 6) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("备注不能少于6个字");
			return null;
		}
		topic.setTopicDesc(topicDesc);
		
		return topic;
	}
	
	private void doAddMsgQTopic(MsgQTopic topic, ErrorResult result) throws Exception {
		QueryMsgQTopicOption option = new QueryMsgQTopicOption();
		option.setTopicName(topic.getTopicName());
		
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		MsgQTopicList topicList = stub.queryMsgQTopics(RandomUtils.nextInt(), 3000, 0, 10, option);
		if (topicList.getTotalNum() > 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("主题名称已经存在");
			return ;
		}
		stub.addMsgQTopic(RandomUtils.nextInt(), 3000, topic);
	}
	
	private void doUpdateMsgQTopic(MsgQTopic topic, ErrorResult result) throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		
		try {
			stub.updateMsgQTopic(RandomUtils.nextInt(), 3000, topic);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode())
				      .setErrorMsg(topic.getTopicName() + "不存在");
				return ;
			}
			throw err;
		}
	}
	
	private void doOpMsgQTopic(ErrorResult result) throws Exception {
		MsgQTopic topic = checkMsgQTopic(result);
		if (topic == null) {
			return ;
		}
		
		String op = parameter("op", "").trim();
		if (op.equals("add")) {
			doAddMsgQTopic(topic, result);
		} else if (op.equals("update")) {
			doUpdateMsgQTopic(topic, result);
		} else {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("op parameter error!");
		}
	}
}
