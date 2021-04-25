{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalCount},
	<#assign rownum=0 />
	"rows":[
		<#list itemsResult.resultList as item >
		<#if rownum &gt; 0>,</#if>
		{
			"type":"${item.type}",
			"id":"${item.id}",
			"allocSize":"${item.allocSize}",
			"remark":"${item.desc}",
			"createTime":"${fromUnixTimestamp(item.createTimestamp)}",
			"lastmodifyTime":"${fromUnixTimestamp(item.lastmodifTimestamp)}"
		}
		<#assign rownum=rownum+1 />
		</#list>
	]
}