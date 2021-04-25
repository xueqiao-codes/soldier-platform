function queryProducer(){
	var params= [
	   {"name" : "producerKey", "value" : $("#queryProducerKey").val() },
	   {"name" : "topicName", "value" : $("#queryTopicName").val() },
	   {"name" : "hasSync", "value" : $("#queryHasSync").val() },
	   {"name" : "producerPropertyKey", "value": $("#queryProducerPropertyKey").val() },
	   {"name" : "producerPropertyValue", "value": $("#queryProducerPropertyValue").val() },
	   {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#msgq_producer_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', msgq_producer_table).each(function(i) {  
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

function addProducer(){
	$('#dialog_input_producerKey').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function modifyProducer(){
	selected_count=$('.trSelected',msgq_producer_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_producerKey').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_topicName').val(rowData[1]);
	$('#dialog_input_remark').val(rowData[2]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var opMode = "add";
	if($('#dialog_input_producerKey').attr('disabled')) {
		opMode = 'update';
	}
	
	producerKeyInput = $('#dialog_input_producerKey').val();
	topicNameInput = $('#dialog_input_topicName').val();
	producerDescInput = $('#dialog_input_remark').val();

	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			producerKey: producerKeyInput,
			topicName: topicNameInput,
			producerDesc: producerDescInput,
			op:	    opMode
		},
		url : "${base_url}/MsgQProducerManage/opMsgQProducer",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"producerKey", "value": producerKeyInput}
				];
				$("#msgq_producer_table").flexOptions({params : params, newp : 1}).flexReload();
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

function deleteProducer() {
	selected_count=$('.trSelected',msgq_producer_table).length;  
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
    			producerKey: rowData[0],
    			op:	    'delete'
    		},
    		url : "${base_url}/MsgQProducerManage/deleteMsgQProducer",
    		async : false,
    		success : function(result){
    			if(result.errorCode == 0){
    				$("#msgq_producer_table").flexReload();
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