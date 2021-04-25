{
	"page": ${pageIndex + 1},
	"total": ${itemsResult.totalNum},
    <#assign rownum=0 />
    "rows":[
        <#list itemsResult.resultList as item >
        <#if rownum &gt; 0>,</#if>
        {
            "roleName":"${item.roleName?js_string}",
            "serviceKey":"${item.serviceKey?c}",
            <#if item.serviceKey != 0 >
            	<#if routeMap[item.serviceKey?c]?? >
            		"serviceName" : "${routeMap[item.serviceKey?c].serviceName}",
            	<#else>
            		"serviceName" : "NO SUCH SERVICE",
            	</#if>
            <#else>
            	"serviceName" : "",
            </#if>
            <#if item.interfaceName=="" >
            	"interfaceName" : "*",
            <#else>
            	"interfaceName":"${item.interfaceName}",
            </#if>
            "userKey":"${item.userKey}",
            "relatedType" : "${item.relatedType}",
            "createTimestamp":"${fromUnixTimestamp(item.createTimestamp)}",
            "lastmodifyTimestamp": "${fromUnixTimestamp(item.lastmodifyTimestamp)}"
        }
        <#assign rownum=rownum+1 />
        </#list>
    ]
}