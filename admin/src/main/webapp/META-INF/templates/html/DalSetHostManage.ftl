<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${static_url}/CSS/common.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/flexigrid/css/flexigrid.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/jquery-ui/jquery-ui-1.9.2.css" type="text/css" />
<script type="text/javascript" src="${static_url}/JS/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${static_url}/JS/flexigrid/js/flexigrid.js" ></script>
<script type="text/javascript" src="${static_url}/JS/util.js"></script>
<script type='text/javascript' src='${static_url}/JS/jquery.noty.min.js'></script>
<script type="text/javascript" src="${base_url}/js/dal_set_host_manage.js" ></script>
<title>DB机管理</title>
</head>
<body>
<div>
<table>
<tr>
<th>实例名称:<input type="text" id="queryName" name="hostName"/></th>
<th>域名或者机器IP:<input type="text" id="queryDomain" name="hostDomain" /></th>
<th>服务端口:<input type="text" id="queryPort" name="hostPort"></th>
<th>备注:<input type="text" id="queryRemark" name="remark"></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryHost()"/></th>
</tr>
</table>
</div>
<table id="dal_set_host_table" style="display:none"></table>
<div id="dialog_div" >
<form autocomplete="off">
<table>
<tr>
    <th>实例名称:</th>
    <th><input id="dialog_input_hostName" type="text" name="hostName"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>机器域名或者IP:</th>
    <th><input id="dialog_input_hostDomain" type="text" name="hostDomain"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>服务端口:</th>
    <th><input id="dialog_input_hostPort" type="text" name="hostPort" value="3306"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>备注:</th>
    <th><input id="dialog_input_remark" type="text" name="remark"></th>
    <th></th>
    <th></th>
</tr>
</table>
</form>
</div>
<script type="text/javascript">
$("#dal_set_host_table").flexigrid({
        title: "DB机器管理",
        url:"${base_url}/json/DalSetHostData",
        dataType: "json" ,
        height: 500,
        colModel:[
            {
                display:'实例机名称',
                name:'hostName',
                width:150,
                sortable:false,
                align:'center',
                iskey:true
            },
            {
                display:'机器域名或者IP',
                name:'hostDomain',
                width:150,
                sortable:false,
                align:'center'
            },
            {
                display:'服务端口',
                name:'hostPort',
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
                onpress:addHost
            },
            {
                name:'修改',
                bclass:'modify',
                onpress:modifyHost
            },
            {
            	name : '删除',
            	bclass: 'delete',
            	onpress: deleteHost
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
        width: 370,
        height: 230,
        modal:true, //蒙层
        title:'DalSet实例管理',
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