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
<script type="text/javascript" src="${base_url}/js/dal_set_role_manage.js" ></script>
<title>DB机管理</title>
</head>
<body>
<div>
<table>
<tr>
<th>角色名:<input type="text" id="queryRoleName" name="queryRoleName"/></th>
<th>关联DB名称:<input type="text" id="queryDbName" name="queryDbName" /></th>
<th>备注:<input type="text" id="queryRemark" name="queryRemark"></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryRole()"/></th>
</tr>
</table>
</div>
<table id="dal_set_role_table" style="display:none"></table>
<div id="dialog_div" >
<form autocomplete="off">
<table>
<tr>
    <th>角色名称:</th>
    <th><input id="dialog_input_roleName" type="text" name="roleName"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>角色DB名称:</th>
    <th><input id="dialog_input_dbName" type="text" name="dbName"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>DB类型:</th>
    <th>
    <select id="dialog_input_dbType" style="float:left">
        <#list supportDbTypes as dbType >
        <option value="${dbType}">${dbType}</option>
        </#list>
    </select>
    </th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>备注:</th>
    <th><input id="dialog_input_remark" type="text" name="remark"></th>
    <th></th>
    <th>不得少于6个字符</th>
</tr>
</table>
</form>
</div>

<script type="text/javascript">
$("#dal_set_role_table").flexigrid({
        title: "角色DB管理",
        url:"${base_url}/json/DalSetRoleData",
        dataType: "json" ,
        height: 500,
        colModel:[
            {
                display:'角色名称',
                name:'roleName',
                width:150,
                sortable:false,
                align:'center',
                iskey:true
            },
            {
                display:'角色DB名称',
                name:'dbName',
                width:150,
                sortable:false,
                align:'center'
            },
            {
                display:'DB类型',
                name:'dbType',
                width:100,
                sortable:false,
                align:'center'
            },
            {
                display:'备注',
                name:'remark',
                width:200,
                sortable:false,
                align:'center'
            },
            {
                display:'创建时间',
                name:'createTimestamp',
                width:200,
                sortable:false,
                align:'center'
            },
            {
                display:'最近修改时间',
                name:'lastmodifyTimestamp',
                width:200,
                sortable:false,
                align:'center'
            }
        ],
        buttons:[
            {
                name:'增加',
                bclass:'add',
                onpress:addRole
            },
            {
                name:'修改',
                bclass:'modify',
                onpress:modifyRole
            },
            {
                name:'删除',
                bclass:'delete',
                onpress:deleteRole
            },
            {  
            separator: true  
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
        width: 370,
        height: 250,
        modal:true, //蒙层
        title:'DalSet角色管理',
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