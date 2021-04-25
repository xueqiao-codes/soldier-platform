function queryStorageInfoList(){
	var params= [
	    {"name" : "storageKey", "value" : $("#queryStorageKey").val() },
	    {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#filestorage_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', filestorage_table).each(function(i) {  
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

function addStorage() {
	$(".input_main_key").removeAttr("disabled");
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function deleteStorage() {
	selected_count=$('.trSelected',filestorage_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
    if (confirm("确定删除存储Key[" + rowData[0] + "]?")) {
    	$.ajax({
			type : "POST",
			dataType : "json",
			timeout : 3000,
			data:{
				storageKey: rowData[0],
				op : "delete"
			},
			url : "${base_url}/json/FileStorageOperation",
			async : false,
			success : function(result){
				if(result.errorCode == 0){
					$("#filestorage_table").flexReload();
				} else {
					showMsg(result.errorMsg);
				}
			}
		});
    }
}

function updateStorage() {
	selected_count=$('.trSelected',filestorage_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条');  
        return;  
    }  
    var rowData = getSelectedRowData();
    
	$(".input_main_key").attr('disabled', 'disabled');
	$("#dialog_input_storageKey").val(rowData[0]);
	$('#dialog_input_accessAttribute').val(rowData[1]);
	$('#dialog_input_remark').val(rowData[3]);
	
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var storageKeyInput = $('#dialog_input_storageKey').val();
	if (storageKeyInput == "") {
		showMsg("请输入存储Key值");
		return ;
	}
	var accessAttributeInput = $('#dialog_input_accessAttribute').val();
	var remarkInput = $('#dialog_input_remark').val();
	if (remarkInput.length < 6) {
		showMsg("请至少输入6个字");
		return ;
	}
	
	var opMode = "add";
	if($('#dialog_input_storageKey').attr('disabled')) {
		opMode = 'update';
	}
	
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 3000,
		data:{
			storageKey: storageKeyInput,
			accessAttribute: accessAttributeInput,
			remark: remarkInput,
			op:	    opMode
		},
		url : "${base_url}/json/FileStorageOperation",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"storageKey", "value": storageKeyInput}
				];
				$("#filestorage_table").flexOptions({params : params, newp : 1}).flexReload();
				$('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}