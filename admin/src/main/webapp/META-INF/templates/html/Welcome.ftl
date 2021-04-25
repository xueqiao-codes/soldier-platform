<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta HTTP-EQUIV="Cache-Control" CONTENT="max-age=0">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="expires" content="0">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script src="${static_url}/JS/jquery-1.7.2.min.js" type="text/javascript" ></script>
<script type="text/javascript">
function fitFrame(){
	if($(window).width() <= 1280) {
		$('#main-window').attr('cols', '0,1280,*');
	} else {
		var leftWidth = ($(window).width() - 1280) / 2;
		$('#main-window').attr('cols', leftWidth+',1280,*');
	}
}
</script>
<title>平台组件管理后台</title>
</head>
<frameset id="main-window" cols="0,1280,*" frameborder="0" framespacing="0" onload="fitFrame()">
	<frame ></frame>
	<frameset rows="50,*" frameborder="0" framespacing="0">
		<frame id="menu_top" name="menu_top" noResize scrolling=no src="${base_url}/html/Top" />
		<frameset cols="180px,*" frameborder="0" framespacing="0" style="border:0">
  			<frame id="menu_left" name="menu_left" src="${base_url}/html/Menu" noResize />
  			<frame id="content_center" name="content_center" src="" />
		</frameset>
	</frameset>
	<frame></frame>
</frameset>
</html>