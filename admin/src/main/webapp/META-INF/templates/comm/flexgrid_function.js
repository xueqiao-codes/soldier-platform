<#macro flexgrid js_name table_name> 

var ${js_name} = {
	getSelectedRowData : function() {
		var data = new Array();  
		$('.trSelected td', ${table_name}).each(function(i) {  
			data[i] = $(this).children('div').text();  
		});
		return data;
	} ,

	getSelectedRowCount : function() {
		return $('.trSelected', ${table_name}).length;
	} ,
	
	reload : function(params) {
		$("#${table_name}").flexOptions({params : params, newp : 1}).flexReload();
	}
}

</#macro>