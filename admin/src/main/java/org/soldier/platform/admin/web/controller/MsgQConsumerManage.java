package org.soldier.platform.admin.web.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.msgq.dao.ConsumerMode;
import org.soldier.platform.msgq.dao.MsgQComsumerList;
import org.soldier.platform.msgq.dao.MsgQConsumer;
import org.soldier.platform.msgq.dao.QueryMsgQConsumerOption;
import org.soldier.platform.msgq.dao.QueryMsgQTopicOption;
import org.soldier.platform.msgq.dao.client.MsgQManageDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web_framework.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeInner;
import com.antiy.error_code.ErrorCodeOuter;

public class MsgQConsumerManage extends WebAuthController {
	
	public void showMsgQConsumers() throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		put("topicItems", stub.queryMsgQTopics(RandomUtils.nextInt(), 3000, 0, 200, new QueryMsgQTopicOption()).getTopicList());
		put("supportConsumerModes", ConsumerMode.values());
		render("msgq/MsgQConsumer.html");
	}
	
	public void consumerListData() throws Exception {
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
		QueryMsgQConsumerOption option = new QueryMsgQConsumerOption();
		
		String consumerKey = parameter("consumerKey", "").trim();
		if (!consumerKey.isEmpty()) {
			option.setConsumerKey(consumerKey);
		}
		
		String consumerMode = parameter("consumerMode", "").trim();
		if (!consumerMode.isEmpty()) {
			ConsumerMode mode = ConsumerMode.valueOf(consumerMode);
			if (mode != null) {
				option.setMode(mode);
			}
		}
		
		String topicName = parameter("topicName", "").trim();
		if (!topicName.isEmpty()) {
			option.setTopicName(topicName);
		}
		
		String consumerDesc = parameter("remark", "").trim();
		if (!consumerDesc.isEmpty()) {
			option.setConsumerDesc(consumerDesc);
		}
		
		String consumerPropertyKey = parameter("consumerPropertyKey", "").trim();
		if (!consumerPropertyKey.isEmpty()) {
			StringBuilder expression = new StringBuilder(64);
			expression.append(consumerPropertyKey);
			expression.append("=");
			
			String consumerPropertyValue = parameter("consumerPropertyValue", "").trim();
			if (!consumerPropertyValue.isEmpty()) {
				expression.append(consumerPropertyValue);
			}
			
			option.setConsumerPropertyExpression(expression.toString());
		}
		
		String hasSync = parameter("hasSync", "").trim();
		if (!hasSync.isEmpty()) {
			if (hasSync.equals("1")) {
				option.setHasSync((short)1);
			} else {
				option.setHasSync((short)0);
			}
		}
		
		put("pageIndex", pageIndex);
		put("itemsResult", stub.queryMsgQConsumers(RandomUtils.nextInt(), 3000, pageIndex, pageSize, option));
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		render("msgq/json/MsgQConsumerData.json");
	}
	
	public void opMsgQConsumer() throws Exception {
		ErrorResult result = new ErrorResult();
		doOpMsgQConsumer(result);
		echoJson(result);
	}
	
	public void deleteMsgQConsumer() throws Exception {
		ErrorResult result = new ErrorResult();
		doDeleteMsgQConsumer(result);
		echoJson(result);
	}
	
	private void doDeleteMsgQConsumer(ErrorResult result) throws Exception {
		String consumerKey = parameter("consumerKey", "").trim();
		if (consumerKey.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须选择消费者Key");
			return ;
		}
		
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		try {
			stub.deleteMsgQConsumer(RandomUtils.nextInt(), 3000, consumerKey);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg(consumerKey + "不存在");
			}
			throw err;
		}
	}
	
	private MsgQConsumer checkMsgQConsumer(ErrorResult result) throws Exception {
		MsgQConsumer consumer = new MsgQConsumer();
		
		String consumerKey = parameter("consumerKey", "").trim();
		if (consumerKey.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须具备消费者Key");
			return null;
		}
		consumer.setConsumerKey(consumerKey);
		
		String topicName = parameter("topicName", "").trim();
		if (topicName.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须选择关联主题");
			return null;
		}
		QueryMsgQTopicOption option = new QueryMsgQTopicOption();
		if (new MsgQManageDaoStub().queryMsgQTopics(RandomUtils.nextInt(), 3000, 0, 10, option).getTotalNum() <= 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("关联主题不存在");
			return null;
		}
		consumer.setTopicName(topicName);
		
		String consumerMode = parameter("consumerMode", "").trim();
		if (consumerMode.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须选择消费类型");
			return null;
		}
		ConsumerMode mode = ConsumerMode.valueOf(consumerMode);
		if (mode == null){
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("不支持的消费类型");
			return null;
		}
		consumer.setMode(mode);
		
		String consumerDesc = parameter("consumerDesc", "").trim();
		if (consumerDesc.length() < 6) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("备注不得少于6个字");
			return null;
		}
		consumer.setConsumerDesc(consumerDesc);
		
		return consumer;
	}
	
	private void doAddMsgQConsumer(MsgQConsumer consumer, ErrorResult result) throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		
		QueryMsgQConsumerOption option = new QueryMsgQConsumerOption();
		option.setConsumerKey(consumer.getConsumerKey());
		MsgQComsumerList consumerList = stub.queryMsgQConsumers(RandomUtils.nextInt(), 3000, 0, 10, option);
		if(consumerList.getTotalNum() > 0) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("消费者Key已经存在");
			return ;
		}
		
		stub.addMsgQConsumer(RandomUtils.nextInt(), 3000, consumer);
	}
	
	private void doUpdateMsgQConsumer(MsgQConsumer consumer, ErrorResult result) throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		
		try {
			stub.updateMsgQConsumer(RandomUtils.nextInt(), 3000, consumer);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg(consumer.getConsumerKey() + "不存在");
				return ;
			}
			throw err;
		}
	}
	
	private void doOpMsgQConsumer(ErrorResult result) throws Exception {
		MsgQConsumer consumer = checkMsgQConsumer(result);
		if (consumer == null) {
			return ;
		}
		
		String op = parameter("op", "").trim();
		if (op.equals("add")) {
			doAddMsgQConsumer(consumer, result);
		} else if(op.equals("update")) {
			doUpdateMsgQConsumer(consumer, result);
		} else{
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("op parameter error!");
		}
	}
}
