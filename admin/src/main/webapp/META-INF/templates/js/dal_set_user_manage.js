function queryUser(){
	var params= [
	   {"name" : "queryUserId", "value" : $("#queryUserId").val() },
	   {"name" : "queryUserName",     "value" : $("#queryUserName").val() },
	   {"name" : "queryRemark", "value" : $("#queryRemark").val() }
	];
	$("#dal_set_user_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', dal_set_user_table).each(function(i) {  
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

function addUser(){
	$('#dialog_input_userId').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function deleteUser(){
	selected_count=$('.trSelected',dal_set_user_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条记录');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if(confirm("确定删除用户[" + rowData[0] + "]?")) {
    	$.ajax({
    		type : "POST",
    		dataType : "json",
    		timeout : 3000,
    		data:{
    			userId: rowData[0],
    			op:	    'delete'
    		},
    		url : "${base_url}/json/UpdateDalSetUser",
    		async : false,
    		success : function(result){
    			if(result.errorCode == 0){
    				$("#dal_set_user_table").flexReload();
    				$('#dialog_div').dialog('close');
    			} else {
    				showMsg(result.errorMsg);
    			}
    		}
    	});
    }
}

function modifyUser(){
	selected_count=$('.trSelected',dal_set_user_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_userId').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_userName').val(rowData[1]);
	$('#dialog_input_userPasswd').val("");
	$('#dialog_input_userPasswd_repeat').val("");
	$('#dialog_input_remark').val(rowData[3]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var opMode = "add";
	if($('#dialog_input_userId').attr('disabled')) {
		opMode = 'update';
	}
	var userIdInput = $('#dialog_input_userId').val();
	if(userIdInput.length == 0) {
		showMsg('用户标识未填写');
		return ;
	}
	var userNameInput = $('#dialog_input_userName').val();
	if(userNameInput.length == 0) {
		showMsg('用户名未填写');
		return ;
	}
	var userPasswdInput = $('#dialog_input_userPasswd').val();
	if(opMode == 'add') {
		if(userPasswdInput.length < 6) {
			showMsg('请至少填写6位长度的密码');
			return ;
		}
	} else {
		if(userPasswdInput.length > 0) {
			if(userPasswdInput.length < 6){
				showMsg('如果修改, 请至少填写6位长度的密码');
				return ;
			}
		}
	}
	var userPasswdRepeatInput = $('#dialog_input_userPasswd_repeat').val();
	var remarkInput = $('#dialog_input_remark').val();
	if(userPasswdInput != userPasswdRepeatInput) {
		showMsg('密码填写不一致');
		return ;
	}
	
	
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			userId: userIdInput,
			userName: userNameInput,
			userPasswd: userPasswdInput,
			remark: remarkInput,
			op:	    opMode
		},
		url : "${base_url}/json/UpdateDalSetUser",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"queryUserId", "value": userIdInput}
				];
				$("#dal_set_user_table").flexOptions({params : params, newp : 1}).flexReload();
				$('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}