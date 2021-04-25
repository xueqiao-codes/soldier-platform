function queryHost(){
	var params= [
	   {"name" : "hostName", "value" : $("#queryName").val() },
	   {"name" : "hostDomain",     "value" : $("#queryDomain").val() },
	   {"name" : "hostPort", "value": $("#queryPort").val() },
	   {"name" : "remark", "value" : $("#queryRemark").val() }
	];
	$("#dal_set_host_table").flexOptions({params : params, newp : 1}).flexReload();
}

function getSelectedRowData(){
	var data = new Array();  
	$('.trSelected td', dal_set_host_table).each(function(i) {  
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

function addHost(){
	$('#dialog_input_hostName').removeAttr('disabled');
	$('#dialog_div').dialog('open').children('form')[0].reset();
}

function deleteHost(){
	selected_count=$('.trSelected',dal_set_host_table).length;  
    if (selected_count==0) {  
        showMsg('请选择一条记录');  
        return;  
    }  
    if(selected_count>1){  
        showMsg('抱歉只能同时删除一条');  
        return;  
    }  
    var rowData = getSelectedRowData();

	if(confirm("确定删除机器[" + rowData[0] + "]?")){  
		$.ajax({
			type : "POST",
			dataType : "json",
			timeout : 3000,
			data:{
				hostName: rowData[0],
				op:	    'delete'
			},
			url : "${base_url}/json/UpdateDalSetHost",
			async : false,
			success : function(result){
				if(result.errorCode == 0){
					$("#dal_set_host_table").flexReload();
					$('#dialog_div').dialog('close');
				} else {
					showMsg(result.errorMsg);
				}
			}
		});
    } 
}

function modifyHost(){
	selected_count=$('.trSelected',dal_set_host_table).length;  
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
	$('#dialog_input_hostDomain').val(rowData[1]);
	$('#dialog_input_hostPort').val(rowData[2]);
	$('#dialog_input_remark').val(rowData[3]);
	$('#dialog_div').dialog('open');
}

function doUpdate(){
	var hostNameInput = $('#dialog_input_hostName').val();
	if(hostNameInput.length == 0) {
		showMsg('实例名称未填写');
		return ;
	}
	var hostDomainInput = $('#dialog_input_hostDomain').val();
	if(hostDomainInput.length == 0) {
		showMsg('机器域名或者IP未填写');
		return ;
	}
	var hostPortInput = $('#dialog_input_hostPort').val().replace(/,/g, "");
	if(hostPortInput.length == 0) {
		showMsg('主机端口未正确填写');
		return ;
	}
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
			hostDomain: hostDomainInput,
			hostPort: hostPortInput,
			remark: remarkInput,
			op:	    opMode
		},
		url : "${base_url}/json/UpdateDalSetHost",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				var params = [
				    {"name":"hostName", "value": hostNameInput}
				];
				$("#dal_set_host_table").flexOptions({params : params, newp : 1}).flexReload();
				$('#dialog_div').dialog('close');
			} else {
				showMsg(result.errorMsg);
			}
		}
	});
}