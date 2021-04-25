<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${static_url}/CSS/common.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/jquery-ui/jquery-ui-1.9.2.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/jsTree/themes/default/style.css" type="text/css"/>
<script type="text/javascript" src="${static_url}/JS/jquery-2.1.0.min.js" ></script>
<script type="text/javascript" src="${static_url}/JS/flexigrid/js/flexigrid.js" ></script>
<title>服务关联关系管理</title>
</head>
<body>
<div>
<table>
<tr>
<th>角色名:<input type="text" id="queryRole" name="role"/></th>
<th>服务名称:<input type="text" id="queryUserName" name="userName" /></th>
<th>备注:<input type="text" id="queryRemark" name="remark"></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryUser()"/></th>
</tr>
</table>
</div>
<table id="dal_set_role" style="display:none"></table>
<script type="text/javascript">
$("#dal_set_role").flexigrid({
        title: "角色管理",
        url:"${base_url}/json/DalSetRelationsData",
        dataType: "json" ,
        height: 500,
        colModel:[
            {
                display:'角色名称',
                name:'roleName',
                width:100,
                sortable:false,
                align:'center',
                iskey:true
            },
            {
                display:'服务名',
                name:'serviceName',
                width:100,
                sortable:false,
                align:'center'
            },
            {
                display:'关联用户ID',
                name:'userId',
                width:100,
                sortable:false,
                align:'center'
            },
            {
                display:'关联DB类型',
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
                name:'createTime',
                width:200,
                sortable:false,
                align:'center'
            },
            {
                display:'最近修改时间',
                name:'lastmodifyTime',
                width:200,
                sortable:false,
                align:'center'
            }
        ],
        buttons:[
        	{
        		name:'增加',
        		bclass:'add',
        		onpress:null,
        	},
            {
                name:'删除',
                bclass:'delete',
                onpress:null
            },
            {
                name:'修改',
                bclass:'modify',
                onpress:null
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
</html>