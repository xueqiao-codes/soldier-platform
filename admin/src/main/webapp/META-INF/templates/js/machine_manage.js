function queryMachine(){
	var params= [
	    {"name" : "hostName", "value" : $("#queryHostName").val() },
	    {"name" : "hostInnerIP", "value" : $("#queryHostInnerIP").val() },
	    {"name" : "hostAdmin", "value" : $("#queryHostAdmin").val() },
	    {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#machine_manage_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', machine_manage_table).each(function(i) {  
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

function addMachine(){
	$('#dialog_input_hostName').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function deleteMachine(){
	selected_count=$('.trSelected',machine_manage_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	if(confirm("确认删除机器[" + rowData[0] + "]?")){  
		$.ajax({
			type : "POST",
			dataType : "json",
			timeout : 3000,
			data:{
				hostName: rowData[0].replace(/,/g, ""),
				op : "delete"
			},
			url : "${base_url}/MachineManage/deleteMachine",
			async : false,
			success : function(result){
				if(result.errorCode == 0){
					$("#machine_manage_table").flexReload();
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

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function modifyMachine(){
	selected_count=$('.trSelected',machine_manage_table).length;  
    if (selected_count==0) {  
    	showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_hostName').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_hostInnerIP').val(rowData[1]);
	$('#dialog_input_hostAdmin').val(rowData[2]);
	$('#dialog_input_remark').val(rowData[3]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var hostNameInput = $('#dialog_input_hostName').val().replace(/,/g, "");
	var hostInnerIPInput = $('#dialog_input_hostInnerIP').val();
	var hostAdminInput = $('#dialog_input_hostAdmin').val();
	var remarkInput = $('#dialog_input_remark').val();
	var opMode = "add";
	if($('#dialog_input_hostName').attr('disabled')) {
		opMode = 'update';
	}
	
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			hostName: hostNameInput,
			hostInnerIP : hostInnerIPInput,
			hostAdmin: hostAdminInput,
			remark: remarkInput,
			op:	    opMode
		},
		url : "${base_url}/MachineManage/opMachine",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"hostNameWhole", "value": hostNameInput}
				];
				$("#machine_manage_table").flexOptions({params : params, newp : 1}).flexReload();
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

function updateMachineMonitor() {
	selected_count=$('.trSelected',machine_manage_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时更新监控一台机器');  
        return;  
    }  
    
    var rowData = getSelectedRowData();
    var hostNameInput =  rowData[0].replace(/,/g, "");
    if(confirm("确认更新机器[" + rowData[0] + "]监控信息?")){  
		$.ajax({
			type : "POST",
			dataType : "json",
			timeout : 3000,
			data:{
				hostName: hostNameInput
			},
			url : "${base_url}/MachineManage/updateMachineMonitor",
			async : false,
			success : function(result){
				if(result.errorCode == 0){
					var params = [
					    { "name":"hostNameWhole", "value": hostNameInput }
					];
					$("#machine_manage_table").flexOptions({params : params, newp : 1}).flexReload();
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
