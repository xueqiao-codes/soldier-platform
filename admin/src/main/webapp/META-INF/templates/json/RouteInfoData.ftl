{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalCount},
	<#assign rownum=0 />
	"rows":[
		<#list itemsResult.resultList as item >
		<#if rownum &gt; 0>,</#if>
		{
			"cmdNum":"${item.routeInfo.serviceKey}",
			"serviceName":"${item.routeInfo.serviceName}",
			"ipList":"${dotListStr(item.routeHostNameOrIPs)}",
			"remark":"${item.routeInfo.desc?js_string}",
			"routeType":"${item.routeInfo.routeType!}",
			"serviceAdmin":"${adminList2Str(item.routeInfo.serviceAdminList)}",
			"idlRelativePath": "${item.routeInfo.idlRelativePath!}",
			"createTime":"${fromUnixTimestamp(item.routeInfo.createTimestamp)}",
			"lastmodifyTime":"${fromUnixTimestamp(item.routeInfo.lastmodifyTimestamp)}"
		}
		<#assign rownum=rownum+1 />
		</#list>
	]
}