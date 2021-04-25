function queryProducer(){
	var params= [
	   {"name" : "consumerKey", "value" : $("#queryConsumerKey").val() },
	   {"name" : "topicName", "value" : $("#queryTopicName").val() },
	   {"name" : "consumerMode", "value" : $("#queryConsumerMode").val() },
	   {"name" : "hasSync", "value" : $("#queryHasSync").val() },
	   {"name" : "consumerPropertyKey", "value": $("#queryConsumerPropertyKey").val() },
	   {"name" : "consumerPropertyValue", "value": $("#queryConsumerPropertyValue").val() },
	   {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#msgq_consumer_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', msgq_consumer_table).each(function(i) {  
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

function addConsumer(){
	$('#dialog_input_consumerKey').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function modifyConsumer(){
	selected_count=$('.trSelected',msgq_consumer_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_consumerKey').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_topicName').val(rowData[1]);
	$('#dialog_input_consumerMode').val(rowData[2]);
	$('#dialog_input_remark').val(rowData[4]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var opMode = "add";
	if($('#dialog_input_consumerKey').attr('disabled')) {
		opMode = 'update';
	}
	
	consumerKeyInput = $('#dialog_input_consumerKey').val();
	topicNameInput = $('#dialog_input_topicName').val();
	consumerModeInput = $('#dialog_input_consumerMode').val();
	consumerDescInput = $('#dialog_input_remark').val();

	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			consumerKey: consumerKeyInput,
			topicName: topicNameInput,
			consumerDesc: consumerDescInput,
			consumerMode: consumerModeInput,
			op:	    opMode
		},
		url : "${base_url}/MsgQConsumerManage/opMsgQConsumer",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"consumerKey", "value": consumerKeyInput}
				];
				$("#msgq_consumer_table").flexOptions({params : params, newp : 1}).flexReload();
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

function deleteConsumer() {
	selected_count=$('.trSelected',msgq_consumer_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if (confirm("确定删除生产者[" + rowData[0] + "]?")) {
    	$.ajax({
    		type : "POST",
    		dataType : "json",
    		timeout : 3000,
    		data:{
    			consumerKey: rowData[0],
    			op:	    'delete'
    		},
    		url : "${base_url}/MsgQConsumerManage/deleteMsgQConsumer",
    		async : false,
    		success : function(result){
    			if(result.errorCode == 0){
    				$("#msgq_consumer_table").flexReload();
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