function queryTable(){
	var params= [
	   {"name" : "prefixName", "value" : $("#queryTablePrefixName").val() },
	   {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#dal_set_table_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', dal_set_table_table).each(function(i) {  
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

function addTable(){
	$('#dialog_input_prefixName').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}


function modifyTable(){
	selected_count=$('.trSelected',dal_set_table_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_prefixName').val(rowData[0]).attr('disabled', 'disabled');
	if (rowData[1] == '不分表') {
		$('#dialog_input_sliceNum').val("0");
	} else {
		$('#dialog_input_sliceNum').val(rowData[1]);
	}
	if (rowData[2] == "是") {
		$('#dialog_input_fillZero').val("true");
	} else {
		$('#dialog_input_fillZero').val("false");
	}
	
	$('#dialog_input_remark').val(rowData[3]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var opMode = "add";
	if($('#dialog_input_prefixName').attr('disabled')) {
		opMode = 'update';
	}
	
	prefixNameInput = $('#dialog_input_prefixName').val();
	sliceNumInput = $('#dialog_input_sliceNum').val().replace(/,/g, "");
	fillZeroInput = $('#dialog_input_fillZero').val();
	remarkInput = $('#dialog_input_remark').val();

	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			prefixName: prefixNameInput,
			sliceNum: sliceNumInput,
			fillZero: fillZeroInput,
			remark: remarkInput,
			op:	    opMode
		},
		url : "${base_url}/json/DalSetTableOperation",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"prefixName", "value": prefixNameInput}
				];
				$("#dal_set_table_table").flexOptions({params : params, newp : 1}).flexReload();
				$('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}

function deleteTable() {
	selected_count=$('.trSelected',dal_set_table_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if (confirm("确定删除表[" + rowData[0] + "]?")) {
    	$.ajax({
    		type : "POST",
    		dataType : "json",
    		timeout : 3000,
    		data:{
    			prefixName: rowData[0],
    			op:	    'delete'
    		},
    		url : "${base_url}/json/DalSetTableOperation",
    		async : false,
    		success : function(result){
    			if(result.errorCode == 0){
    				$("#dal_set_table_table").flexReload();
    			} else {
    				showMsg(result.errorMsg);
    			}
    		}
    	});
    }
}

function addRoleTableRelation() {
	selected_count=$('.trSelected',dal_set_table_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时关联一条记录');  
        return;  
    }  
    var rowData = getSelectedRowData();
    $('#relation_input_prefixName').val(rowData[0]).attr('disabled', 'disabled');
    $('#table_role_relation').dialog('open');
}

function doAddRoleTableRelation() {
	var tablePrefixNameInput =  $('#relation_input_prefixName').val();
	var roleNameInput = $('#relation_input_roleName').val();
	
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 5000,
		data:{
			tablePrefixName: tablePrefixNameInput,
			roleName: roleNameInput,
			op:	    "add",
		},
		url : "${base_url}/json/DalSetRoleTableRelationOperation",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				$('#table_role_relation').dialog('close');
				showMsg("关联成功");
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}