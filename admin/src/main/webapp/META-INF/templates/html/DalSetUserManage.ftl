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
<script type="text/javascript" src="${base_url}/js/dal_set_user_manage.js" ></script>
<title>DB用户管理</title>
</head>
<body>
<div>
<table>
<tr>
<th>唯一标识:<input type="text" id="queryUserId" name="queryUserId"/></th>
<th>用户名:<input type="text" id="queryUserName" name="queryUserName" /></th>
<th>备注:<input type="text" id="queryRemark" name="queryRemark"></th>
<th><input class="operation_menu" type="button" value="查询" onclick="queryUser()"/></th>
</tr>
</table>
</div>
<table id="dal_set_user_table" style="display:none"></table>
<div id="dialog_div" >
<form autocomplete="off">
<table>
<tr>
    <th>用户标识:</th>
    <th><input id="dialog_input_userId" type="text" name="userId"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>用户名:</th>
    <th><input id="dialog_input_userName" type="text" name="userName"></th>
    <th><b style="color:red">*</b></th>
    <th></th>
</tr>
<tr>
    <th>密码:</th>
    <th><input id="dialog_input_userPasswd" type="password" name="userPasswd"></th>
    <th><b style="color:red">至少6位</b></th>
    <th id="dialog_input_passwd_info">为空表示不修改</th>
</tr>
<tr>
    <th>重复密码:</th>
    <th><input id="dialog_input_userPasswd_repeat" type="password" name="userPasswd_repeat"></th>
    <th><b style="color:red">至少6位</b></th>
    <th></th>
</tr>
<tr>
    <th>备注:</th>
    <th><input id="dialog_input_remark" type="text" name="remark"></th>
    <th><b style="color:red">至少6个字符</b></th>
    <th></th>
</tr>
</table>
</form>
</div>
<script type="text/javascript">
$("#dal_set_user_table").flexigrid({
        title: "DB用户管理",
        url:"${base_url}/json/DalSetUserData",
        dataType: "json" ,
        height: 500,
        colModel:[
            {
                display:'唯一标识',
                name:'userId',
                width:150,
                sortable:false,
                align:'center',
                iskey:true
            },
            {
                display:'用户名',
                name:'userName',
                width:150,
                sortable:false,
                align:'center'
            },
            {
            	display:'密码',
            	name:'userPassword',
            	width:150,
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
                onpress:addUser
            },
            {
                name:'修改',
                bclass:'modify',
                onpress:modifyUser
            },
            {
            	name:'删除',
            	bclass:'delete',
            	onpress:deleteUser
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
        width: 400,
        height: 300,
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