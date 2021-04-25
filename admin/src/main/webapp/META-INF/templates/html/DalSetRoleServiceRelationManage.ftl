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
<script type="text/javascript" src="${base_url}/js/dal_set_role_service_relation_manage.js" ></script>
<title>服务关联关系管理</title>
</head>
<body>
<div>
<table>
<tr>
<th>服务命令字:<input type="text" id="queryServiceKey" name="queryServiceKey"></th>
<th>接口名称:<input type="text" id="queryInterfaceName" name="queryInterfaceName"></th>
<th>角色名:<input type="text" id="queryRoleName" name="queryRoleName"/></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryRoleServiceRelation()"/></th>
</tr>
</table>
</div>

<div id="dialog_div" >
<form autocomplete="off">
<table>
<tr>
    <th>服务命令字:</th>
    <th align="left">
    	<select id="dialog_input_serviceKey" class="input_main_key">
    		<option value="0">无</option>
    		<#list routes as route>
    		<option value="${route.serviceKey?c}">${route.serviceName}</option>
    		</#list>
    	</select>
    </th>
    <th align="left">Daemon程序可不填</th>
</tr>
<tr>
    <th>服务接口名称:</th>
    <th align="left"><input type="text" id="dialog_input_interfaceName" class="input_main_key" value="*"></input></th>
    <th align="left">*表示所有,Daemon必填</th>
</tr>
<tr>
	<th>关联角色:</th>
	<th align="left">
		<select id="dialog_input_roleName" class="input_main_key">
			<option value=""></option>
			<#list roles as role>
			<option value="${role.roleName}">${role.roleName}</option>
			</#list>
		</select>
	</th>
	<th align="left"><b style="color:red">*</b></th>
</tr>
<tr>
    <th>关联用户:</th>
    <th align="left">
    	<select id="dialog_input_userKey">
    		<option></option>
    		<#list users as user>
    			<option value="${user.key}">${user.key}</option>
    		</#list>
    	</select>
    </th>
    <th align="left"><b style="color:red">*</b></th>
</tr>
<tr>
	<th>关联类型:</th>
	<th align="left">
		<select id="dialog_input_relatedType">
			<#list relatedTypes as relatedType>
				<option value="${relatedType}">${relatedType}</option>
			</#list>
		</select>
	</th>
	<th></th>
</tr>
</table>
</form>
</div>

<table id="dal_set_role_service_relation_table" style="display:none"></table>
<script type="text/javascript">
$("#dal_set_role_service_relation_table").flexigrid({
        title:  "服务角色关联",
        url: "${base_url}/json/DalSetRoleServiceRelationData",
        dataType: "json" ,
        height: 500,
        colModel:[
            {
                display:'服务命令字',
                name:'serviceKey',
                width:100,
                sortable:false,
                align:'center',
                iskey:true
            },
            {
            	display:'服务名',
            	name:'serviceName',
            	width:130,
            	sortable:false,
            	align: 'center',
            },
            {
                display:'接口名称',
                name:'interfaceName',
                width:130,
                sortable:false,
                align:'center'
            },
            {
                display:'关联角色名',
                name:'roleName',
                width:130,
                sortable:false,
                align:'center'
            },
            {
                display:'关联用户',
                name:'userKey',
                width:130,
                sortable:false,
                align:'center'
            },
            {
                display:'关联类型',
                name:'relatedType',
                width:70,
                sortable:false,
                align:'center'
            },
            {
                display:'创建时间',
                name:'createTimestamp',
                width:150,
                sortable:false,
                align:'center'
            },
            {
                display:'最近修改时间',
                name:'lastmodifyTimestamp',
                width:150,
                sortable:false,
                align:'center'
            }
        ],
        buttons:[
        	{
        		name:'增加',
        		bclass:'add',
        		onpress: addRoleServiceRelation,
        	},
            {
                name:'删除',
                bclass:'delete',
                onpress: deleteRoleServiceRelation,
            },
            {
                name:'修改',
                bclass:'modify',
                onpress: modifyRoleServiceRelation,
            }
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
        width: 456,
        height: 270,
        modal:true, //蒙层
        title:'DalSet用户管理',
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
});
</script>
</html>