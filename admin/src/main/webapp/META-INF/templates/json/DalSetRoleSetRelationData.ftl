{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalNum},
    <#assign rownum=0 />
    "rows":[
        <#list itemsResult.resultList as item >
        <#if rownum &gt; 0>,</#if>
        {
            "roleName":"${item.roleName?js_string}",
            "setIndex":"${item.setIndex?c}",
            "typeInSet":"${item.typeInSet}",
            "hostName":"${item.hostName}",
            "weight" : "${item.weight?c}",
            "createTimestamp":"${fromUnixTimestamp(item.createTimestamp)}",
            "lastmodifyTimestamp": "${fromUnixTimestamp(item.lastmodifyTimestamp)}"
        }
        <#assign rownum=rownum+1 />
        </#list>
    ]
}