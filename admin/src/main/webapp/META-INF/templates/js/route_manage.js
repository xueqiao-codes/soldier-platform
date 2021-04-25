function queryRoute(){
	var params= [
	    {"name" : "cmdnum", "value" : $("#queryCmdNum").val() },
	    {"name" : "serviceName", "value" : $("#queryServiceName").val() },
	    {"name" : "ip",     "value" : $("#queryIp").val() },
	    {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#route_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', route_table).each(function(i) {  
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

function addRoute(){
	$('#dialog_input_cmdnum').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
	$('#dialog_input_routeType').val('K8SSrv');
	
	open_service_admin_select2('#dialog_input_serviceAdmin');
	open_hosts_select2('#dialog_input_ipList');
}

function deleteRoute(){
	selected_count=$('.trSelected',route_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	if(confirm("确认删除路由服务[" + rowData[0] + "(" + rowData[1] + ")]?")){  
		$.ajax({
			type : "POST",
			dataType : "json",
			timeout : 3000,
			data:{
				cmdNum: rowData[0].replace(/,/g, ""),
				op : "delete"
			},
			url : "${base_url}/json/UpdateRoute",
			async : false,
			success : function(result){
				if(result.errorCode == 0){
					$("#route_table").flexReload();
				} else {
					showMsg(result.errorMsg);
				}
			}
		});
    } 
}

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function modifyRoute(){
	selected_count=$('.trSelected',route_table).length;  
    if (selected_count==0) {  
    	showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_cmdnum').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_serviceName').val(rowData[1]);
	$('#dialog_input_ipList').val(rowData[2]);
	$('#dialog_input_routeType').val(rowData[3]);
	$('#dialog_input_serviceAdmin').val(rowData[4]);
	$('#dialog_input_idlRelativePath').val(rowData[5]);
	$('#dialog_input_remark').val(rowData[6]);
	$('#dialog_div').dialog('open');
	
	open_service_admin_select2('#dialog_input_serviceAdmin');
	open_hosts_select2('#dialog_input_ipList');
}

function doUpdate(){
	var cmdNumInput = $('#dialog_input_cmdnum').val().replace(/,/g, "");
	var serviceNameInput = $('#dialog_input_serviceName').val();
	var ipListInput = "";
	
	$('#dialog_input_ipList').select2('data').forEach(function (obj) {
		if (obj.hostName != "") {
			if (ipListInput != "") {
				ipListInput = ipListInput + ",";
			}
			ipListInput = ipListInput + obj.hostName;
		}
	});
	var remarkInput = $('#dialog_input_remark').val();
	var routeTypeInput = $('#dialog_input_routeType').val();
	
	var serviceAdminInput = "";
	$('#dialog_input_serviceAdmin').select2('data').forEach(function (obj) {
		if (obj.userName != "") {
			if (serviceAdminInput != "") {
				serviceAdminInput = serviceAdminInput + ",";
			}
			serviceAdminInput = serviceAdminInput + obj.userName;
		}
	});
	
	var idlRelativePathInput = $('#dialog_input_idlRelativePath').val();
	var opMode = "add";
	if($('#dialog_input_cmdnum').attr('disabled')) {
		opMode = 'update';
	}
	
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			cmdNum: cmdNumInput,
			serviceName : serviceNameInput,
			ipList: ipListInput,
			serviceAdmin : serviceAdminInput,
			idlRelativePath : idlRelativePathInput,
			remark: remarkInput,
			op:	    opMode,
			routeType : routeTypeInput
		},
		url : "${base_url}/json/UpdateRoute",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"cmdnum", "value": cmdNumInput}
				];
				$("#route_table").flexOptions({params : params, newp : 1}).flexReload();
				$('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}

function syncRoute() {
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		url : "${base_url}/json/SyncRoute",
		async : false,
		data : {
			op : "update",
		},
		success : function(result){
			if(result.errorCode == 0){
				showMsg("同步成功！");
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}
