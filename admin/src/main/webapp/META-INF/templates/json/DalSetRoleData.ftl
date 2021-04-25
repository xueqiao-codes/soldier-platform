{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalNum},
    <#assign rownum=0 />
    "rows":[
        <#list itemsResult.resultList as item >
        <#if rownum &gt; 0>,</#if>
        {
            "roleName":"${item.roleName?js_string}",
            "dbName":"${item.dbName?js_string}",
            "dbType": "${item.dbType}",
            "remark":"${item.desc?js_string}",
            "createTimestamp":"${fromUnixTimestamp(item.createTimestamp)}",
            "lastmodifyTimestamp":"${fromUnixTimestamp(item.lastmodifyTimestamp)}"
        }
        <#assign rownum=rownum+1 />
        </#list>
    ]
}