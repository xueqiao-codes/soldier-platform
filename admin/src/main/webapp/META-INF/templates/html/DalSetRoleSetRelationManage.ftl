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
<script type="text/javascript" src="${base_url}/js/dal_set_role_set_relation_manage.js" ></script>
<title>角色SET部署</title>
</head>
<body>
<div>
<table>
<tr>
<th>角色名:<input type="text" id="queryRoleName" name="queryRoleName"/></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryRoleSetRelation()"/></th>
</tr>
</table>
</div>
<table id="dal_set_role_set_relation_table" style="display:none"></table>
<div id="dialog_div" >
<form autocomplete="off">
<table>
<tr>
    <th>角色名:</th>
    <th align="left">
    	<select id="select_role_name">
    		<option value=""></option>
    		<#list roles as role>
    		<option value="${role.roleName}">${role.roleName}</option>
    		</#list>
    	</select>
    </th>
    <th align="left"><b style="color:red">*</b></th>
</tr>
<tr>
    <th>SET序号:</th>
    <th align="left">
    	<select id="select_set_index">
    	</select>
    </th>
    <th align="left"><b style="color:red">*</b></th>
</tr>
<tr>
	<th>部署类型:</th>
	<th align="left">
		<select id="select_type_in_set">
			<option value=""></option>
			<option value="Master">Master</option>
			<option value="Slave">Slave</option>
		</select>
	</th>
	<th align="left"><b style="color:red">*</b></th>
</tr>
<tr>
    <th>主机名称:</th>
    <th align="left">
    	<select id="select_host_name">
    		<option></option>
    		<#list hosts as host>
    			<option value="${host.name}">${host.name}</option>
    		</#list>
    	</select>
    </th>
    <th align="left"><b style="color:red">*</b></th>
</tr>
<tr>
	<th>权重:</th>
	<th><input id="input_weight" type="text" name="weight"/></th>
	<th> >= 0(默认为1)</th>
</tr>
</table>
</form>
</div>

<div id="dialog_modify_div">
<form autocomplete="off">
<table>
<tr>
	<th>权重值</th>
	<th><input id="input_modify_weight" type="text"/></th>
	<th> >= 0</th>
</tr>
</table>
</form>
</div>
<script type="text/javascript">
$("#dal_set_role_set_relation_table").flexigrid({
        title: "角色SET部署",
        url:"${base_url}/json/DalSetRoleSetRelationData",
        dataType: "json" ,
        height: 500,
        colModel:[
            {
                display:'角色名',
                name:'roleName',
                width:150,
                sortable:false,
                align:'center',
                iskey:true
            },
            {
                display:'SET序号',
                name:'setIndex',
                width:100,
                sortable:false,
                align:'center'
            },
            {
            	display:'部署类型',
            	name:'typeInSet',
            	width:100,
            	sortable:false,
            	align:'center',
            },
            {
                display:'主机名',
                name:'hostName',
                width:150,
                sortable:false,
                align:'center'
            },
            {
            	display : '权重',
            	name:'weight',
            	width : 80,
            	sortable:false,
            	align : 'center',
            },
            {
                display:'创建时间',
                name:'createTimestamp',
                width:180,
                sortable:false,
                align:'center'
            },
            {
            	display: '最近修改时间',
            	name: 'lastmodifyTimestamp',
            	width:180,
            	sortable:false,
            	align:'center',
            },
        ],
        buttons:[
            {
                name:'增加',
                bclass:'add',
                onpress: addRoleSetRelation,
            },
            {
            	name: '删除',
            	bclass: 'delete',
            	onpress: deleteRoleSetRelation,
            },
            {  
            separator: true  
            }, 
            {
            	name: '修改',
            	bclass : 'modify',
            	onpress : modifyRoleSetRelation,
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
<script type="text/javascript" src="${static_url}/JS/jquery-ui/jquery-ui-1.9.2.js" ></script>
<script>
$(function(){
    $('#dialog_div').dialog({
        hide:'',    //点击关闭是隐藏,如果不加这项,关闭弹窗后再点就会出错.
        autoOpen:false,
        width: 400,
        height: 240,
        modal:true, //蒙层
        title:'角色SET部署',
        overlay: {
            opacity: 0.5,
            background: "black"
        },
        buttons:{
            '提交':function(){
              	doAddRoleSetRelation();
            },
            '取消':function(){
                $(this).dialog("close");
            },
        }
    });
    
    $('#dialog_modify_div').dialog({
    	hide:'',    //点击关闭是隐藏,如果不加这项,关闭弹窗后再点就会出错.
        autoOpen:false,
        width: 300,
        height: 150,
        modal:true, //蒙层
        title:'修改权重',
        overlay: {
            opacity: 0.5,
            background: "black"
        },
        buttons:{
            '提交':function(){
              	doModifyRoleSetRelation();
            },
            '取消':function(){
                $(this).dialog("close");
            },
        }
    });
});
</script>
</html>