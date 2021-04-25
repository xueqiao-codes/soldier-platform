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
<script type="text/javascript" src="${base_url}/js/dal_set_role_table_relation_manage.js" ></script>
<title>DB角色表关联管理</title>
</head>
<body>
<div>
<table>
<tr>
<th>角色名:<input type="text" id="queryRoleName" name="queryRoleName"/></th>
<th>表名前缀:<input type="text" id="queryTablePrefixName" name="queryTablePrefixName" /></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryRoleTableRelation()"/></th>
</tr>
</table>
</div>

<div id="dialog_div">
</div>

<table id="dal_set_role_table_relation_table" style="display:none"></table>
<script type="text/javascript">
$("#dal_set_role_table_relation_table").flexigrid({
        title: "角色表关联管理",
        url:"${base_url}/json/DalSetRoleTableRelationData",
        dataType: "json" ,
        height: 500,
        colModel:[
            {
                display:'角色名称',
                name:'roleName',
                width:260,
                sortable:false,
                align:'center',
                iskey:true
            },
            {
                display:'表名前缀',
                name:'tablePrefixName',
                width:260,
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
        ],
        buttons:[
            {
                name:'删除',
                bclass:'delete',
                onpress: deleteRoleTableRelation,
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
</html>