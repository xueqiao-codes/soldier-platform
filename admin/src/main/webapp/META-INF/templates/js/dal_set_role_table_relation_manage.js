function queryRoleTableRelation(){
	var params= [
	   {"name" : "roleName", "value" : $("#queryRoleName").val() },
	   {"name" : "tablePrefixName",     "value" : $("#queryTablePrefixName").val() }
	];
	$("#dal_set_role_table_relation_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', dal_set_role_table_relation_table).each(function(i) {  
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


function deleteRoleTableRelation(){
	selected_count=$('.trSelected', dal_set_role_table_relation_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if (confirm("确认删除关联关系(" + rowData[0] + "->" + rowData[1] + ")?")) {
    	doDeleteRoleTableRelation(rowData[0], rowData[1]);
    }
}

function doDeleteRoleTableRelation(r, t){
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			roleName: r,
			tablePrefixName : t,
			op : "delete",
		},
		url : "${base_url}/json/DalSetRoleTableRelationOperation",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				$("#dal_set_role_table_relation_table").flexReload();
				showMsg("删除成功");
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}