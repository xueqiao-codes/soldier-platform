function showMsg(msg) {
	noty({text : msg, timeout : 1500});
}

function syncDalSetConfig() {
	$('#dalSetConfig').val("");
	$.ajax({
		type : "POST",
		dataType : "json",
		timeout : 5000,
		url : "${base_url}/json/SyncDalSetConfig",
		async : false,
		success : function(result){
			if(result.errorCode == 0){
				showMsg("同步成功");
				$('#dalSetConfig').val(Base64.decode(result.dalSetConfig));
			} else {
				showMsg("同步失败," + result.errorMsg);
			}
		},
		error : function (XMLHttpRequest, textStatus, errorThrown) {
			showMsg(textStatus);
		}
	});
	
}