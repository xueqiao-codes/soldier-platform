{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalNum},
	<#assign rownum=0 />
	"rows":[
		<#list itemsResult.resultList as item >
		<#if rownum &gt; 0>,</#if>
		{
			"prefixName":"${item.prefixName}",
			<#if item.sliceNum == 0>
			"sliceNum" : "不分表",
			<#else>
			"sliceNum":"${item.sliceNum}",
			</#if>
			<#if item.fillZero >
			"fillZero" : "是",
			<#else>
			"fillZero" : "否",
			</#if>
			"remark":"${item.desc?js_string}",
			"createTime":"${fromUnixTimestamp(item.createTimestamp)}",
			"lastmodifyTime":"${fromUnixTimestamp(item.lastmodifyTimestamp)}"
		}
		<#assign rownum=rownum+1 />
		</#list>
	]
}