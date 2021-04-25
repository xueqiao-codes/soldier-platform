{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalNum},
    <#assign rownum=0 />
    "rows":[
        <#list itemsResult.resultList as item >
        <#if rownum &gt; 0>,</#if>
        {
            "roleName":"${item.roleName?js_string}",
            "tablePrefixName":"${item.tablePrefixName?js_string}",
            "createTimestamp":"${fromUnixTimestamp(item.createTimestamp)}"
        }
        <#assign rownum=rownum+1 />
        </#list>
    ]
}