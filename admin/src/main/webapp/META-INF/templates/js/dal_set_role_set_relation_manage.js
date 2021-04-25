function queryRoleSetRelation(){
	var params= [
	   {"name" : "roleName", "value" : $("#queryRoleName").val() },
	];
	$("#dal_set_role_set_relation_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', dal_set_role_set_relation_table).each(function(i) {  
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

function resetInptForm(form) {
	form.reset();
	$('#select_set_index option').remove();
}

function addRoleSetRelation() {
	var form=$('#dialog_div').dialog('open').children('form')[0];
	resetInptForm(form);
	$('#select_role_name').change(function() {
		var selectRoleName=$("#select_role_name").val();
		if(selectRoleName == "") {
			form.reset();
		} else {
			$.ajax({
				type : "POST",
				dataType : "json",
				data : {
					roleName : selectRoleName,
				},
				url : "${base_url}/json/DalSetRoleSetRelationInputInfo",
				async : false,
				success : function(result) {
					$('#select_set_index option').remove();
					for(var index = 0; index <= result.maxSetIndex; ++index) {
						$('#select_set_index').append('<option value="' + index +'">' + index + "</option>" );
					}
				},
				error : function() {
					resetInptForm(form);
					showMsg("获取数据失败");
				}
			});
		}
	});
}


function postOperation(operation, 
		roleNameInput,
		setIndexInput, 
		typeInSetInput,
		hostNameInput, 
		weightInput) {
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 5000,
		data:{
			roleName: roleNameInput,
			setIndex: setIndexInput,
			typeInSet : typeInSetInput,
			hostName : hostNameInput,
			weight : weightInput,
			op:	   operation,
		},
		url : "${base_url}/json/DalSetRoleSetRelationOperation",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params= [
						     {"name" : "roleName", "value" : roleNameInput },
				];
				if (operation == 'delete') {
					$("#dal_set_role_set_relation_table").flexReload();
				} else {
					$("#dal_set_role_set_relation_table").flexOptions({params : params, newp : 1}).flexReload();
				}
				if (operation == 'update') {
					$('#dialog_modify_div').dialog('close');
				} else {
					$('#dialog_div').dialog('close');
				}
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}

function doAddRoleSetRelation() {
	var roleNameInput = $('#select_role_name').val();
	if (roleNameInput == "") {
		showMsg("请选择角色名称");
		return ;
	}
	var setIndexInput = $('#select_set_index').val();
	if (setIndexInput == "") {
		showMsg("请选择SET序");
		return;
	}
	var typeInSetInput = $('#select_type_in_set').val();
	if (typeInSetInput == "") {
		showMsg("请选择部署类型");
		return ;
	}
	var hostNameInput = $('#select_host_name').val();
	if (hostNameInput == "") {
		showMsg("请选择主机");
		return ;
	}
	var weightInput = $('#input_weight').val();
	if (weightInput == "") {
		weightInput = 1;
	}
	
	postOperation("add", roleNameInput, setIndexInput, typeInSetInput, hostNameInput, weightInput);
}

function deleteRoleSetRelation() {
	selected_count=$('.trSelected', dal_set_role_set_relation_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('只能选择一条记录');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if (confirm("确认删除机器(" + rowData[0] + "," + rowData[1] + "," + rowData[3]+ ")?")) {
    	postOperation("delete",
    			rowData[0], rowData[1], rowData[2], rowData[3], rowData[4]);
    }
}

function modifyRoleSetRelation() {
	selected_count=$('.trSelected', dal_set_role_set_relation_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('只能选择一条记录');  
        return;  
    }  
    
    $('#dialog_modify_div').dialog('open');
}

function doModifyRoleSetRelation() {
	selected_count=$('.trSelected', dal_set_role_set_relation_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('只能选择一条记录');  
        return;  
    } 
    
    var rowData = getSelectedRowData();
    var weightInput = $('#input_modify_weight').val();
	if (weightInput == "") {
		showMsg("权重必须输入");
		return ;
	}
	
	postOperation("update",
			rowData[0], rowData[1], rowData[2], rowData[3], weightInput);
}

