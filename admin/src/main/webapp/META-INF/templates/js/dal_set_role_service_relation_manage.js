function queryRoleServiceRelation(){
	var params= [
	   {"name" : "roleName", "value" : $("#queryRoleName").val() },
	   {"name" : "interfaceName", "value" : $("#queryInterfaceName").val() },
	   {"name" : "serviceKey", "value" : $("#queryServiceKey").val() }
	];
	$("#dal_set_role_service_relation_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', dal_set_role_service_relation_table).each(function(i) {  
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

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function addRoleServiceRelation(){
	$(".input_main_key").removeAttr("disabled");
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function modifyRoleServiceRelation() {
	selected_count=$('.trSelected',dal_set_role_service_relation_table).length;  
    if (selected_count==0) {  
    	showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
    	showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$(".input_main_key").attr('disabled', 'disabled');
	$('#dialog_input_serviceKey').val(rowData[0]);
	$('#dialog_input_interfaceName').val(rowData[2]);
	$("#dialog_input_roleName").val(rowData[3]);
	$("#dialog_input_userKey").val(rowData[4]);
	$("#dialog_input_relatedType").val(rowData[5]);
	
	$('#dialog_div').dialog('open');
}

function deleteRoleServiceRelation(){
	selected_count=$('.trSelected',dal_set_role_service_relation_table).length;  
    if (selected_count==0) {  
    	showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
    	showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();

	if(confirm("确定删除关联+[" + rowData[1] +", " + rowData[2] + "," + rowData[3] + "]?")){  
		$.ajax({
			type : "POST",
			dataType : "json",
			timeout : 5000,
			data:{
				serviceKey: rowData[0],
				interfaceName: rowData[2],
				roleName: rowData[3],
				op:	"delete",
			},
			url : "${base_url}/json/DalSetRoleServiceRelationOperation",
			async : false,
			success : function(result){
				if(result.errorCode == 0){
					$("#dal_set_role_service_relation_table").flexReload();
					$('#dialog_div').dialog('close');
				} else {
					showMsg(result.errorMsg);
				}
			}
		});
    } 
}

function doUpdate(){
	var opMode = "add";
	if($('#dialog_input_serviceKey').attr('disabled')) {
		opMode = 'update';
	}
	var serviceKeyInput = $('#dialog_input_serviceKey').val();
	var interfaceNameInput = $('#dialog_input_interfaceName').val();
	if (interfaceNameInput == "") {
		showMsg("请填写接口名");
		return ;
	}
	var roleNameInput = $('#dialog_input_roleName').val();
	if(roleNameInput.length == 0) {
		showMsg('角色名称未填写');
		return ;
	}
	var userKeyInput = $('#dialog_input_userKey').val();
	if(userKeyInput.length == 0) {
		showMsg('请选择关联用户');
		return ;
	}
	var relatedTypeInput = $('#dialog_input_relatedType').val();
	if(relatedTypeInput.length == 0) {
		showMsg('请选择关联类型');
		return ;
	}
	
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 5000,
		data:{
			serviceKey: serviceKeyInput,
			interfaceName: interfaceNameInput,
			roleName: roleNameInput,
			userKey: userKeyInput,
			relatedType: relatedTypeInput,
			op:	    opMode
		},
		url : "${base_url}/json/DalSetRoleServiceRelationOperation",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name": "serviceKey", "value" : serviceKeyInput},
				    {"name": "interfaceName", "value" : interfaceNameInput},
				    {"name":"roleName", "value": roleNameInput}
				];
				$("#dal_set_role_service_relation_table").flexOptions({params : params, newp : 1}).flexReload();
				 $('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}