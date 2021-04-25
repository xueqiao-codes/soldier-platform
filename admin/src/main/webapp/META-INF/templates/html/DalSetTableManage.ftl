<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${static_url}/CSS/common.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/flexigrid/css/flexigrid.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/jquery-ui/jquery-ui-1.9.2.css" type="text/css" />
<script type="text/javascript" src="${static_url}/JS/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${static_url}/JS/flexigrid/js/flexigrid.js" ></script>
<script type='text/javascript' src='${static_url}/JS/jquery.noty.min.js'></script>
<script type="text/javascript" src="${base_url}/js/dal_set_table_manage.js" ></script>
<script type="text/javascript" src="${static_url}/JS/jquery-ui/jquery-ui-1.9.2.js" ></script>
<title>DB表管理</title>
</head>
<body>
<div>
<table>
<tr>
<th>表名前缀:<input type="text" id="queryTablePrefixName" name="queryTablePrefixName"/></th>
<th>备注:<input type="text" id="queryRemark" name="queryRemark"></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryTable()"/></th>
</tr>
</table>
</div>
<table id="dal_set_table_table" style="display:none"></table>
<div id="dialog_div" >
<form autocomplete="off">
<table>
<tr>
    <th>表名前缀:</th>
    <th><input id="dialog_input_prefixName" type="text" name="prefixName"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>分表数:</th>
    <th><input id="dialog_input_sliceNum" type="text" name="sliceNum"></th>
    <th><b style="color:red">*</b></th>
    <th>0表示不分表</th>
</tr>
<tr>
	<th>是否补0:</th>
	<th align="left">
		<select id="dialog_input_fillZero" type="text" name="fillZero">
			<option value="true">是</option>
			<option value="false" selected="selected">否</option>
		</select>
	</th>
	<th>
</tr>
<tr>
    <th>备注:</th>
    <th><input id="dialog_input_remark" type="text" name="remark"></th>
    <th><b style="color:red"></b></th>
    <th align="left">至少6个字符</th>
</tr>
</table>
</form>
</div>
<div id="table_role_relation">
<form autocomplete="off">
<table>
<tr>
    <th>表名前缀:</th>
    <th align="left"><input id="relation_input_prefixName" type="text" name="prefixName"></th>
    <th><b style="color:red">*</b></th>
</tr>
<tr>
    <th>角色名:</th>
    <th align="left">
    	<select id="relation_input_roleName" >
    		<#list roleList as role >
    		<option value="${role.roleName}">${role.roleName}</option>
    		</#list>
    	</select>
    </th>
    </th>
    </th>
</tr>
</form>
</div>
<script type="text/javascript">
$("#dal_set_table_table").flexigrid({
        title: "DB表管理",
        url:"${base_url}/json/DalSetTableData",
        dataType: "json" ,
        height: 500,
        colModel:[
            {
                display:'表名前缀',
                name:'prefixName',
                width:150,
                sortable:false,
                align:'center',
                iskey:true
            },
            {
                display:'分表数',
                name:'sliceNum',
                width:150,
                sortable:false,
                align:'center'
            },
            {
            	display:'是否补0',
            	name:'fillZero',
            	width:100,
            	sortable:false,
            	align:'center',
            },
            {
                display:'备注',
                name:'remark',
                width:230,
                sortable:false,
                align:'center'
            },
            {
                display:'创建时间',
                name:'createTime',
                width:180,
                sortable:false,
                align:'center'
            },
            {
                display:'最近修改时间',
                name:'lastmodifyTime',
                width:180,
                sortable:false,
                align:'center'
            }
        ],
        buttons:[
            {
                name:'增加',
                bclass:'add',
                onpress:addTable
            },
            {
                name:'修改',
                bclass:'modify',
                onpress:modifyTable
            },
            {
            	name: '删除',
            	bclass: 'delete',
            	onpress: deleteTable,
            },
             {  
            separator: true  
            },  
            {
                name:'关联角色',
                bclass:'relate',
                onpress: addRoleTableRelation
            },
        ], 
        showToggleBtn:false,
        useRp:false,
        rp:20,
        usepager:true,
        procmsg: '正在请求服务端数据...',
        nomsg: '没有数据',
        errormsg: '出错啦~~',
        pagestat:'当前显示记录 {from} 到 {to} 条，总 {total} 条'
    });
</script>
</body>
<script>
$(function(){
    $('#dialog_div').dialog({
        hide:'',    //点击关闭是隐藏,如果不加这项,关闭弹窗后再点就会出错.
        autoOpen:false,
        width: 400,
        height: 240,
        modal:true, //蒙层
        title:'DalSet表管理',
        overlay: {
            opacity: 0.5,
            background: "black"
        },
        buttons:{
            '提交':function(){
                doUpdate();
            },
            '取消':function(){
                $(this).dialog("close");
            },
        }
    });
    
    $('#table_role_relation').dialog({
        hide:'',    //点击关闭是隐藏,如果不加这项,关闭弹窗后再点就会出错.
        autoOpen:false,
        width: 310,
        height: 180,
        modal:true, //蒙层
        title:'DalSet表关联',
        overlay: {
            opacity: 0.5,
            background: "black"
        },
        buttons:{
            '提交':function(){
            	doAddRoleTableRelation();
            },
            '取消':function(){
                $(this).dialog("close");
            },
        }
    });
});
</script>
</html>