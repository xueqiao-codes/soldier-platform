<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${static_url}/CSS/common.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/CSS/route_manage.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/flexigrid/css/flexigrid.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/jquery-ui/jquery-ui-1.9.2.css" type="text/css" />
<script type="text/javascript" src="${static_url}/JS/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="${static_url}/JS/flexigrid/js/flexigrid.js" ></script>
<script type='text/javascript' src='${static_url}/JS/jquery.noty.min.js'></script>
<script type="text/javascript" src="${base_url}/js/idmaker_manage.js" ></script>
<title>IdMaker管理</title>
</head>
<body>
<div>
<table>
<tr>
<th>类型:<input type="text" id="queryType" name="type"/></th>
<th>备注:<input type="text" id="queryRemark" name="remark"></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryIdMaker()"/></th>
</tr>
</table>
</div>
<div>
<table id="idmaker_table" style="display:none"></table>
<div id="dialog_div" >
<form autocomplete="off">
<table>
<tr>
    <th>类型:</th>
    <th><input id="dialog_input_type" type="text" name="type"></th>
    <th><b style="color:red">*</b></th>
</tr>
<tr>
	<th>初始水位:</th>
	<th><input id="dialog_input_id" type="text" name="id"></th>
    <th><b style="color:red">*</b></th>
</tr>
<tr>
    <th>每次分配大小:</th>
    <th><input id="dialog_input_allocSize" type="text" name="allocSize"></th>
    <th><b style="color:red">*</b></th>
</tr>
<tr>
    <th>备注:</th>
    <th><input id="dialog_input_remark" type="text" name="remark"></th>
    <th><b style="color:red">*(不少于6个字符)</b></th>
</tr>
</table>
</form>
</div>
<script>
	$("#idmaker_table").flexigrid({
		title: "IDMAKER管理",
		url:"${base_url}/json/IdMakerInfoData",
		dataType: "json" ,
		height: 500,
		colModel:[
			{
				display:'类型',
				name:'type',
				width:100,
				sortable:false,
				align:'center',
				iskey:true
			},
			{
				display:'目前水位Id',
				name: 'id',
				width: 150,
				sortable: false,
				align:'center',
			},
			{
				display:'每次分配深度',
				name:'allocSize',
				width:200,
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
				onpress:add
			},
			{
				name:'更新',
				bclass:'modify',
				onpress:update
			}
		], 
		showToggleBtn:false,
		useRp:false,
		rp:15,
		usepager:true,
		procmsg: '正在请求服务端数据...',
		nomsg: '没有数据',
		errormsg: '出错啦~~',
		pagestat:'当前显示记录 {from} 到 {to} 条，总 {total} 条',
	});
</script>
</div>
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
        title:'路由管理',
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