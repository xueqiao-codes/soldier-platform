function queryRole(){
	var params= [
	   {"name" : "roleName", "value" : $("#queryRoleName").val() },
	   {"name" : "dbName",     "value" : $("#queryDbName").val() },
	   {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#dal_set_role_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', dal_set_role_table).each(function(i) {  
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

function addRole(){
	$('#dialog_input_roleName').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function deleteRole(){
	selected_count=$('.trSelected',dal_set_role_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if (confirm("确定删除角色[" + rowData[0] + "]?")) {
    	$.ajax({
    		type : "POST",
    		dataType : "json",
    		timeout : 3000,
    		data:{
    			roleName: rowData[0],
    			op:	    'delete'
    		},
    		url : "${base_url}/json/DalSetRoleOperation",
    		async : false,
    		success : function(result){
    			if(result.errorCode == 0){
    				$("#dal_set_role_table").flexReload();
    				$('#dialog_div').dialog('close');
    			} else {
    				showMsg(result.errorMsg);
    			}
    		}
    	});
    }
    
}

function modifyRole(){
	selected_count=$('.trSelected',dal_set_role_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_roleName').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_dbName').val(rowData[1]);
	$('#dialog_input_dbType').attr("value", rowData[2]);
	$('#dialog_input_remark').val(rowData[3]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var opMode = "add";
	if($('#dialog_input_roleName').attr('disabled')) {
		opMode = 'update';
	}
	var roleNameInput = $('#dialog_input_roleName').val();
	if(roleNameInput.length == 0) {
		showMsg('角色名称未填写');
		return ;
	}
	var dbNameInput = $('#dialog_input_dbName').val();
	if(dbNameInput.length == 0) {
		showMsg('db名称未填写');
		return ;
	}
	var dbTypeInput = $('#dialog_input_dbType').val();
	if(dbTypeInput.length == 0) {
		showMsg('db类型未选择');
		return ;
	}
	var remarkInput = $('#dialog_input_remark').val();
	
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			roleName: roleNameInput,
			dbName: dbNameInput,
			dbType: dbTypeInput,
			remark: remarkInput,
			op:	    opMode
		},
		url : "${base_url}/json/DalSetRoleOperation",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"roleName", "value": roleNameInput}
				];
				$("#dal_set_role_table").flexOptions({params : params, newp : 1}).flexReload();
				 $('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}