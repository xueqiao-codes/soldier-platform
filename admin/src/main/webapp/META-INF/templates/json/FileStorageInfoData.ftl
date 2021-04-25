{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalNum},
	<#assign rownum=0 />
	"rows":[
		<#list itemsResult.resultList as item >
		<#if rownum &gt; 0>,</#if>
		{
			"storageKey":"${item.storageKey}",
			"accessAttribute":"${item.accessAttribute}",
			"domain":"${item.domain}",
			"remark":"${item.desc}",
			"createTimestamp":"${fromUnixTimestamp(item.createTimestamp)}",
			"lastmodifyTimestamp":"${fromUnixTimestamp(item.lastmodifyTimestamp)}"
		}
		<#assign rownum=rownum+1 />
		</#list>
	]
}