function queryIdMaker(){
	var params= [
	    {"name" : "type", "value" : $("#queryType").val() },
	    {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#idmaker_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', idmaker_table).each(function(i) {  
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

function add(){
	$('#dialog_input_type').removeAttr('disabled');
	$('#dialog_input_id').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function update(){
	selected_count=$('.trSelected',idmaker_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时修改一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
	
	$('#dialog_input_type').val(rowData[0]).attr('disabled', 'disabled');
	$('#dialog_input_id').val(rowData[1]).attr('disabled', 'disabled');
	$('#dialog_input_allocSize').val(rowData[2]);
	$('#dialog_input_remark').val(rowData[3]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var typeInput = $('#dialog_input_type').val().replace(/,/g, "");
	var idInput = $('#dialog_input_id').val().replace(/,/g, "");
	var allocSizeInput = $('#dialog_input_allocSize').val().replace(/,/g, "");
	var remarkInput = $('#dialog_input_remark').val();
	var opMode = "add";
	if($('#dialog_input_type').attr('disabled')) {
		opMode = 'update';
	}
	
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			type: typeInput,
			id: idInput,
			allocSize : allocSizeInput,
			remark: remarkInput,
			op:	    opMode
		},
		url : "${base_url}/json/IdMakerOperation",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"type", "value": typeInput}
				];
				$("#idmaker_table").flexOptions({params : params, newp : 1}).flexReload();
				$('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}