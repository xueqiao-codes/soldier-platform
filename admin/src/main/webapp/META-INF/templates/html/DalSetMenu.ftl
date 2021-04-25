<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script src="${static_url}/JS/jquery-1.7.2.min.js" type="text/javascript" ></script>
<style type="text/css">
body{
    margin: 0px
}

div.menu {
 width: auto;
 vertical-align: middle;
 font-size:80%;
 background-color:#003366;
 padding: 1em 1em 1em 1em;
 border-style:solid; border-width:1px; border-color:#E5E5E5;
 text-align: left;
 color:#FFFFFF;
}

.menu-a-clicked{
    color:#EE0000;
}

.menu-a-unclicked{
    color:#FFFFFF;
}
</style>
</head>
<body >

<div class="menu">
<table>
	<th><a href="${base_url}/html/DalSetView" target="dal_set_content">DALSET视图</a></th>
    <th><a href="${base_url}/html/DalSetHostManage" target="dal_set_content">DB机管理</a></th>
    <th><a href="${base_url}/html/DalSetUserManage" target="dal_set_content">DB用户管理</a></th>
    <th><a href="${base_url}/html/DalSetTableManage" target="dal_set_content">DB表管理</a></th>
    <th><a href="${base_url}/html/DalSetRoleManage" target="dal_set_content">角色管理</a></th>
    <th><a href="${base_url}/html/DalSetRoleTableRelationManage" target="dal_set_content">角色表关联</a></th>
    <th><a href="${base_url}/html/DalSetRoleSetRelationManage" target="dal_set_content">角色SET部署</a></th>
    <th><a href="${base_url}/html/DalSetRoleServiceRelationManage" target="dal_set_content">服务角色关联</a></th>
</table>
</div>


<script type="text/javascript">
$(document).ready(
    function(){
        var menu_buttons = $('.menu a');
        menu_buttons.addClass('menu-a-unclicked');
        menu_buttons.click(function(){
            $('.menu a').removeClass('menu-a-clicked');
            $('.menu a').addClass('menu-a-unclicked');
            $(this).removeClass('menu-a-unclicked');
            $(this).addClass('menu-a-clicked');
            window.open($(this).attr("href"), $(this).attr("target")); 
        });
        
        $('.menu a').first().click();
    }
);
</script>

</body>
</html>
