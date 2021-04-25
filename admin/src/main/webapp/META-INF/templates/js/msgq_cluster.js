function queryCluster(){
	var params= [
	   {"name" : "clusterName", "value" : $("#queryClusterName").val() },
	   {"name" : "clusterBrokers", "value" : $("#queryClusterBrokers").val() },
	   {"name" : "clusterType", "value" : $("#queryClusterType").val() },
	   {"name" : "clusterPropertyKey", "value": $("#queryClusterPropertyKey").val() },
	   {"name" : "clusterPropertyValue", "value": $("#queryClusterPropertyValue").val() },
	   {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#msgq_cluster_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', msgq_cluster_table).each(function(i) {  
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

function addCluster(){
	$('#dialog_input_clusterName').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function modifyCluster(){
	selected_count=$('.trSelected',msgq_cluster_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_clusterName').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_clusterBrokers').val(rowData[1]);
	$('#dialog_input_clusterType').val(rowData[2]);
	$('#dialog_input_remark').val(rowData[3]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var opMode = "add";
	if($('#dialog_input_clusterName').attr('disabled')) {
		opMode = 'update';
	}
	
	clusterNameInput = $('#dialog_input_clusterName').val();
	clusterBrokersInput = $('#dialog_input_clusterBrokers').val();
	clusterTypeInput = $('#dialog_input_clusterType').val();
	clusterDescInput = $('#dialog_input_remark').val();

	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			clusterName: clusterNameInput,
			clusterBrokers: clusterBrokersInput,
			clusterType: clusterTypeInput,
			clusterDesc: clusterDescInput,
			op:	    opMode
		},
		url : "${base_url}/MsgQClusterManage/opMsgQCluster",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"clusterName", "value": clusterNameInput}
				];
				$("#msgq_cluster_table").flexOptions({params : params, newp : 1}).flexReload();
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

function deleteCluster() {
	selected_count=$('.trSelected',msgq_cluster_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if (confirm("确定删除集群[" + rowData[0] + "]?")) {
    	$.ajax({
    		type : "POST",
    		dataType : "json",
    		timeout : 3000,
    		data:{
    			clusterName: rowData[0],
    			op:	    'delete'
    		},
    		url : "${base_url}/MsgQClusterManage/deleteMsgQCluster",
    		async : false,
    		success : function(result){
    			if(result.errorCode == 0){
    				$("#msgq_cluster_table").flexReload();
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