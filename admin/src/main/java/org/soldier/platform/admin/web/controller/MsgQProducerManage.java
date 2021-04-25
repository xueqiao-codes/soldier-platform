package org.soldier.platform.admin.web.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.soldier.platform.msgq.dao.MsgQProducer;
import org.soldier.platform.msgq.dao.MsgQProducerList;
import org.soldier.platform.msgq.dao.MsgQTopic;
import org.soldier.platform.msgq.dao.QueryMsgQProducerOption;
import org.soldier.platform.msgq.dao.QueryMsgQTopicOption;
import org.soldier.platform.msgq.dao.client.MsgQManageDaoStub;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.web_framework.freemarker.UnixTimestampConverter;
import org.soldier.platform.web_framework.model.ErrorResult;

import com.antiy.error_code.ErrorCodeInner;
import com.antiy.error_code.ErrorCodeOuter;

public class MsgQProducerManage extends WebAuthController {
	
	public void showMsgQProducers() throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		put("topicItems", stub.queryMsgQTopics(RandomUtils.nextInt(), 3000, 0, 200, new QueryMsgQTopicOption()).getTopicList());
		render("msgq/MsgQProducer.html");
	}

	public void producerListData() throws Exception {
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
		QueryMsgQProducerOption option = new QueryMsgQProducerOption();
		
		String producerKey = parameter("producerKey", "").trim();
		if (!producerKey.isEmpty()) {
			option.setProducerKey(producerKey);
		}
		
		String topicName = parameter("topicName", "").trim();
		if (!topicName.isEmpty()) {
			option.setTopicName(topicName);
		}
		
		String producerDesc = parameter("remark", "").trim();
		if (!producerDesc.isEmpty()) {
			option.setProducerDesc(producerDesc);
		}
		
		String producerPropertyKey = parameter("producerPropertyKey", "").trim();
		if (!producerPropertyKey.isEmpty()) {
			StringBuilder expression = new StringBuilder(64);
			expression.append(producerPropertyKey);
			expression.append("=");
			
			String producerPropertyValue = parameter("producerPropertyValue", "").trim();
			if (!producerPropertyValue.isEmpty()) {
				expression.append(producerPropertyValue);
			}
			
			option.setProducerPropertyExpression(expression.toString());
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
		put("itemsResult", stub.queryMsgQProducerList(RandomUtils.nextInt(), 3000, pageIndex, pageSize, option));
		put("fromUnixTimestamp", UnixTimestampConverter.getInstance());
		
		render("msgq/json/MsgQProducerData.json");
	}
	
	public void opMsgQProducer() throws Exception {
		ErrorResult result = new ErrorResult();
		doOpMsgQProducer(result);
		echoJson(result);
	}
	
	private MsgQProducer checkMsgQProducer(ErrorResult result) throws Exception {
		MsgQProducer producer = new MsgQProducer();
		
		String producerKey = parameter("producerKey", "").trim();
		if (producerKey.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须具备消费者Key");
			return null;
		}
		producer.setProducerKey(producerKey);
		
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
		producer.setTopicName(topicName);
		
		String producerDesc = parameter("producerDesc", "").trim();
		if (producerDesc.length() < 6) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("备注不得少于6个字");
			return null;
		}
		producer.setProducerDesc(producerDesc);
		
		return producer;
	}
	
	private void doAddMsgQProducer(MsgQProducer producer, ErrorResult result) throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		
		QueryMsgQProducerOption option = new QueryMsgQProducerOption();
		option.setProducerKey(producer.getProducerKey());
		
		MsgQProducerList producerList = stub.queryMsgQProducerList(RandomUtils.nextInt(), 3000, 0, 10, option);
		if (producerList.getTotalNum() > 0 ) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("生产者Key已经存在");
			return ;
		}
		
		stub.addMsgQProducer(RandomUtils.nextInt(), 3000, producer);
	}
	
	private void doUpdateMsgQProducer(MsgQProducer producer, ErrorResult result) throws Exception {
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		
		QueryMsgQProducerOption option = new QueryMsgQProducerOption();
		option.setProducerKey(producer.getProducerKey());
		
		try {
			stub.updateMsgQProducer(RandomUtils.nextInt(), 3000, producer);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg(producer.getProducerKey() + "不存在");
				return ;
			}
			
			throw err;
		}
	}
	
	private void doOpMsgQProducer(ErrorResult result) throws Exception {
		MsgQProducer producer = checkMsgQProducer(result);
		if (producer == null) {
			return ;
		}
		
		String op = parameter("op", "").trim();
		if (op.equals("add")) {
			doAddMsgQProducer(producer, result);
		} else if (op.equals("update")) {
			doUpdateMsgQProducer(producer, result);
		} else {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("op parameter error!");
		}
	}
	
	public void deleteMsgQProducer() throws Exception {
		ErrorResult result = new ErrorResult();
		doDeleteMsgQProducer(result);
		echoJson(result);
	}
	
	private void doDeleteMsgQProducer(ErrorResult result) throws Exception {
		String producerKey = parameter("producerKey", "").trim();
		if (producerKey.isEmpty()) {
			result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg("必须选择生产者Key");
			return ;
		}
		
		MsgQManageDaoStub stub = new MsgQManageDaoStub();
		try {
			stub.deleteMsgQProducer(RandomUtils.nextInt(), 3000, producerKey);
		} catch (ErrorInfo err) {
			if (err.getErrorCode() == ErrorCodeInner.RECORD_NOT_FOUND.getErrorCode()) {
				result.setErrorCode(ErrorCodeOuter.PARAM_ERROR.getErrorCode()).setErrorMsg(producerKey + "不存在");
				return ;
			}
			
			throw err;
		}
	}
}
