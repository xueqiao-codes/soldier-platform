function queryTopic(){
	var params= [
	   {"name" : "topicName", "value" : $("#queryTopicName").val() },
	   {"name" : "topicCluster", "value" : $("#queryTopicCluster").val() },
	   {"name" : "topicPropertyKey", "value": $("#queryTopicPropertyKey").val() },
	   {"name" : "topicPropertyValue", "value": $("#queryTopicPropertyValue").val() },
	   {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#msgq_topic_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', msgq_topic_table).each(function(i) {  
		data[i] = $(this).children('div').text();  
	});
	return data;
}

function setSubmitButtonDisabled(disabled){
	var button = $(".ui-dialog-buttonpane button").eq(0);
	if(disabled == true){
		button.attr('disabled', 'disabled');
	} else {
		button.removeAttr('disabled');
	}
}

function addTopic(){
	$('#dialog_input_topicName').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function modifyTopic(){
	selected_count=$('.trSelected',msgq_topic_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_topicName').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_topicCluster').val(rowData[1]);
	$('#dialog_input_remark').val(rowData[2]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var opMode = "add";
	if($('#dialog_input_topicName').attr('disabled')) {
		opMode = 'update';
	}
	
	topicNameInput = $('#dialog_input_topicName').val();
	topicClusterInput = $('#dialog_input_topicCluster').val();
	topicDescInput = $('#dialog_input_remark').val();

	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			topicName: topicNameInput,
			topicCluster: topicClusterInput,
			topicDesc: topicDescInput,
			op:	    opMode
		},
		url : "${base_url}/MsgQTopicManage/opMsgQTopic",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"topicName", "value": topicNameInput}
				];
				$("#msgq_topic_table").flexOptions({params : params, newp : 1}).flexReload();
				$('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		},
		error : function(request, textStatus, errorThrown) {
			if (request.status >= 500) {
				showMsg('服务器繁忙，请重试');
			} else {
				showMsg('连接服务器失败, 请检查网络');
			}
		}
	});
}

function deleteTopic() {
	selected_count=$('.trSelected',msgq_topic_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if (confirm("确定删除主题[" + rowData[0] + "]?")) {
    	$.ajax({
    		type : "POST",
    		dataType : "json",
    		timeout : 3000,
    		data:{
    			topicName: rowData[0],
    			op:	    'delete'
    		},
    		url : "${base_url}/MsgQTopicManage/deleteMsgQTopic",
    		async : false,
    		success : function(result){
    			if(result.errorCode == 0){
    				$("#msgq_topic_table").flexReload();
    			} else {
    				showMsg(result.errorMsg);
    			}
    		},
    		error : function(request, textStatus, errorThrown) {
    			if (request.status >= 500) {
    				showMsg('服务器繁忙，请重试');
    			} else {
    				showMsg('连接服务器失败, 请检查网络');
    			}
    		}
    	});
    }
}