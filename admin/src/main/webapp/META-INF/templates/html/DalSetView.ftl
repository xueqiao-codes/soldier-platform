<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>角色SET部署</title>
<link rel="stylesheet" href="${static_url}/CSS/common.css" type="text/css" />
<link rel="stylesheet" href="${static_url}/JS/jsTree/themes/default/style.min.css" type="text/css" />
</head>
<body>
<div style="width:50%;float:left">
	<div>
		<input class="operation_menu" type="button" value="同步配置" onclick="syncDalSetConfig()"></input>
	</div>
	<div>
		<textarea id="dalSetConfig" style="width:90%;overflow:auto;margin:5px" rows="30" readonly="true"></textarea>
	</div>
</div>
<div style="width:50%;float:left">
<br/>
	<p>树形部署图</p>
	<br/>
<div id="role_instance_relation" >
	<ul>
	<#list roleSetRelations?keys as role >
		<li>${role}
		<#assign setsMap=roleSetRelations[role] >
		<#if setsMap?size &gt; 0>
			<ul>
			<#list setsMap?keys as setIndex >
				<li>${setIndex}
					<#assign relationList=setsMap[setIndex] >
					<#if relationList?size &gt; 0>
					<ul>
						<#list relationList as relation>
						<li>
						<#if hosts[relation.hostName]??>
							<#assign host=hosts[relation.hostName] >
							[${relation.typeInSet}] ${relation.hostName} [${host.domain}:${host.port?c}]
						<#else>
							unexpected!
						</#if>
						</li>
						</#list>
					</ul>
					</#if>
				</li>
			</#list>
			</ul>
		</#if>
		</li>
	</#list>
	</ul>
</div>
</div>
</body>
<script src="${static_url}/JS/jquery-2.1.0.min.js"></script>
<script src="${static_url}/JS/jsTree/jstree.min.js"></script>
<script>
$(function() {
	$('#role_instance_relation').jstree();
});
</script>
<script type="text/javascript" src="${static_url}/JS/base64.js"></script>
<script type='text/javascript' src='${static_url}/JS/jquery.noty.min.js'></script>
<script type="text/javascript" src="${base_url}/js/dal_set_view.js"></script>
</html>