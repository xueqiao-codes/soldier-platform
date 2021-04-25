<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${static_url}/CSS/common.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/CSS/route_manage.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/flexigrid/css/flexigrid.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/jquery-ui/jquery-ui-1.9.2.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/bootstrap3/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/select2/select2.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/CSS/select2-bootstrap.css" type="text/css" />
<script type="text/javascript" src="${static_url}/JS/jquery-2.1.0.min.js" ></script>
<script type="text/javascript" src="${static_url}/JS/jquery-migrate-1.4.1.min.js"></script>
<script type="text/javascript" src="${static_url}/JS/flexigrid/js/flexigrid.js" ></script>
<script type="text/javascript" src="${static_url}/JS/jquery.noty.min.js"></script>
<script type="text/javascript" src="${static_url}/JS/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${static_url}/JS/select2/select2.min.js"></script>
<script type="text/javascript" src="${base_url}/js/select2_portal.js"></script>
<script type="text/javascript" src="${base_url}/js/route_manage.js" ></script>
<title>路由管理</title>

</head>
<body>
<div>
<table>
<tr>
<th>命令号:<input type="text" id="queryCmdNum" name="cmdnum"/></th>
<th>服务名:<input type="text" id="queryServiceName" name="serviceName"/></th>
<th>机器IP:<input type="text" id="queryIp" name="ip" /></th>
<th>备注:<input type="text" id="queryRemark" name="remark"></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryRoute()"/></th>
<th><input class="operation_menu" type="button" value="同步路由" onclick="syncRoute()"></th>
</tr>
</table>
</div>
<div>
<table id="route_table" style="display:none"></table>
<div id="dialog_div" >
<form autocomplete="off" >
<table style="width:450px;border-collapse:separate; border-spacing:0px 5px;">
<tr>
    <th width="15%">命令号:</th>
    <th width="50%"><input id="dialog_input_cmdnum" type="text" name="cmdNum" class="form-control" /></th>
    <th width="5%"><b style="color:red" >*</b></th>
    <th width="15%" align="right">(1-2000)</th>
</tr>
<tr>
	<th >服务名称:</th>
	<th ><input id="dialog_input_serviceName" type="text" name="serviceName" class="form-control" /></th>
    <th ><b style="color:red">*</b></th>
    <th ></th>
</tr>
<tr>
    <th >机器列表:</th>
    <th ><input id="dialog_input_ipList" type="text" name="ipList" class="form-control" /></th>
    <th ><b style="color:red">*</b></th>
    <th >(逗号分割)</th>
</tr>
<tr>
	<th>路由方式:</th>
	<th align="left"><select id="dialog_input_routeType" name="routeType" class="form-control">
	<#list routeTypes as routeType>
	<option value="${routeType}">
	<#if routeType=="Mod">按模(Mod)
	<#elseif  routeType=="RR">轮询(RR)
	<#elseif  routeType=="XL">最优心跳(XL)
	<#elseif  routeType=="K8SSrv">K8S无状态路由(K8SSrv)
	<#elseif  routeType=="K8SMod">K8S按模路由(K8SMod)
	</#if>
	</option>
	</#list>
	</select>
	<th><b style="color:red">*</b></th>
	<th></th>
	</th>
</tr>
<tr>
	<th>责任人:</th>
	<th><input class="form-control" id="dialog_input_serviceAdmin" value="" ></input></th>
	<th ></th>
	<th></th>
</tr>
<tr>
	<th>IDL路径:</th>
	<th><input class="form-control" id="dialog_input_idlRelativePath" value=""></input></th>
	<th ><b style="color:red"></b></th>
	<th></th>
</tr>
<tr>
    <th>备注:</th>
    <th><input id="dialog_input_remark" type="text" name="remark" class="form-control"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
</table>
</form>
</div>
<script>
	$("#route_table").flexigrid({
		title: "路由配置管理",
		url:"${base_url}/json/RouteInfoData",
		dataType: "json" ,
		height: 600,
		colModel:[
			{
				display:'命令号',
				name:'cmdNum',
				width:100,
				sortable:false,
				align:'center',
				iskey:true
			},
			{
				display:'服务名',
				name: 'serviceName',
				width: 150,
				sortable: false,
				align:'center',
			},
			{
				display:'机器列表',
				name:'ipList',
				width:200,
				sortable:false,
				align:'center'
			},
			{
				display:'路由方式',
				name:'routeType',
				width:100,
				sortable:false,
				align:'center',
			},
			{
				display:'责任人',
				name:'serviceAdmin',
				width:150,
				sortable:false,
				align:'center'
			},
			{
				display:'IDL相对路径',
				name:'idlRelativePath',
				width:150,
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
				onpress:addRoute
			},
			{
				name:'修改',
				bclass:'modify',
				onpress:modifyRoute
			},
			{
				name:'删除',
				bclass:'delete',
				onpress: deleteRoute
			},
		], 
		showToggleBtn:false,
		useRp:false,
		rp:15,
		usepager:true,
		procmsg: '正在请求服务端数据...',
		nomsg: '没有数据',
		errormsg: '出错啦~~',
		pagestat:'当前显示记录 {from} 到 {to} 条，总 {total} 条',
		multisel : false,
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
        width: 500,
        height: 400,
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