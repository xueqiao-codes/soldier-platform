{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalNum},
	<#assign rownum=0 />
	"rows":[
		<#list itemsResult.resultList as item >
		<#if rownum &gt; 0>,</#if>
		{
			"userId":"${item.key}",
			"userName":"${item.name}",
			"userPassword" : "${item.password}",
			"remark":"${item.desc?js_string}",
			"createTime":"${fromUnixTimestamp(item.createTimestamp)}",
			"lastmodifyTime":"${fromUnixTimestamp(item.lastmodifyTimestamp)}"
		}
		<#assign rownum=rownum+1 />
		</#list>
	]
}