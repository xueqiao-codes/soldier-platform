<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${static_url}/CSS/login.css" />
<script type="text/javascript" src="${static_url}/JS/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${static_url}/JS/md5.js"></script>
<script type="text/javascript" src="${static_url}/JS/jquery.cookie.js"></script>
<body>
<div>
    <div id="TopSpace"></div>
    <div id="bd">
    <div id="LoginInput" >
        <table align="center">
            <tbody>
            <tr>
                <td>用户名:</td>
                <td><input id="username" name="username" type="text" ></input></td>
                <td rowspan="2"><img id="submit" src="${static_url}/IMAGE/login-button.jpg"  /><td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input id="password" name="password" type="password"></input></td>
            </tr>
            <tbody>
        </table>
        <div><p style="text-align:center;" id="tips"></p></div>
    </div>
    <div>
</div>
<script type="text/javascript">
var LOGIN = "login...";
var FREE = "free";
var state = "logining";
var loginSuccessGoToUrl = "${from}";

function setTips(msg){
    $('#tips').text(msg);
}

$().ready(
    function(){
        $('#submit').click(function(){
        	var userNameInput = $('#username').val();
        	if (userNameInput == "") {
        		setTips("请输入用户名");
        		return ;
        	}
        	var passwordInput = $('#password').val();
        	if (passwordInput == "") {
        		setTips("请输入密码");
        		return ;
        	}
        	passwordInput = hex_md5(passwordInput);
        
            if(state == LOGIN){
                return ;
            }
            setTips('登录中...');
            $.ajax({
                type : "POST",
                dataType : "json",
                timeout : 10000,
                data : {
                	username : userNameInput,
                	password : passwordInput,
                },
                url : "${base_url}/json/LoginResult",
                async : true,
                success : function(msg){
                 	state = FREE;
                    if (msg.errorCode == 0) {
                    	$.cookie('user_platform', userNameInput, {path: "/"});
                    	$.cookie('session_platform', msg.session, {path: "/"});
                    	top.location.href=loginSuccessGoToUrl;
                    } else {
                    	setTips(msg.errorMsg);
                    }
                },
                error: function(jqXHR, textStatus){
                    setTips('登录失败,' + textStatus);
                    state = FREE;
                }
            });
        });
    }
);
</script>
</body>
</html>