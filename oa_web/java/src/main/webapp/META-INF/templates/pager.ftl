<#macro pager pageNo pageSize toURL recordCount>  
  <#-- 定义局部变量pageCount保存总页数 -->
  <#assign pageCount=((recordCount + pageSize - 1) / pageSize)?int>  
	<#if recordCount==0><#return/></#if>
<#-- 输出分页样式 -->
<style type="text/css">
.pagination {padding: 5px;float:right;font-size:12px;}
.pagination a, .pagination a:link, .pagination a:visited {padding:2px 5px;margin:2px;border:1px solid #aaaadd;text-decoration:none;color:#006699;}
.pagination a:hover, .pagination a:active {border: 1px solid #ff0000;color: #000;text-decoration: none;}
.pagination span.current {padding: 2px 5px;margin: 2px;border: 1px solid #ff0000;font-weight: bold;background-color: #ff0000;color: #FFF;}
.pagination span.disabled {padding: 2px 5px;margin: 2px;border: 1px solid #eee; color: #ddd;}
</style>
<#-- 页号越界处理 -->
  <#if (pageNo > pageCount)>
    <#assign pageNo=pageCount>
  </#if>
  <#if (pageNo < 1)>
    <#assign pageNo=1>
  </#if>
<#-- 输出分页表单 -->
<div class="pagination">
<form method="post" action="" name="qPagerForm">
<#if queryOptions??>
<#list queryOptions?keys as key> 
<input type="hidden" name="${key}" value="${queryOptions[key]}" />
</#list>
</#if>
<input type="hidden" name="pageNo" value="${pageNo}"/>
<input type="hidden" name="pageSize" value="${pageSize}" />
<#-- 上一页处理 -->
  <#if (pageNo == 1)>
<span class="disabled">&laquo;&nbsp;上一页</span>
  <#else>
<a href="javascript:void(0)" onclick="turnOverPage(${pageNo - 1})">&laquo;&nbsp;上一页</a>
  </#if>
<#-- 如果前面页数过多,显示... -->
	<#assign start=1>
	<#if (pageNo > 4)>
    <#assign start=(pageNo - 1)>
<a href="javascript:void(0)" onclick="turnOverPage(1)">1</a>
<a href="javascript:void(0)" onclick="turnOverPage(2)">2</a>&hellip;
	</#if>
<#-- 显示当前页号和它附近的页号 -->
	<#assign end=(pageNo + 1)>
	<#if (end > pageCount)>
		<#assign end=pageCount>
	</#if>
  <#list start..end as i>
    <#if (pageNo==i)>
<span class="current">${i}</span>
		<#else>
<a href="javascript:void(0)" onclick="turnOverPage(${i})">${i}</a>      
    </#if>
  </#list>
<#-- 如果后面页数过多,显示... -->
  <#if (end < pageCount - 2)>
&hellip;  
  </#if>
  <#if (end < pageCount - 1)>
<a href="javascript:void(0)" onclick="turnOverPage(${pageCount - 1})">${pageCount-1}</a>
  </#if>
  <#if (end < pageCount)>
<a href="javascript:void(0)" onclick="turnOverPage(${pageCount})">${pageCount}</a>
  </#if>
<#-- 下一页处理 -->
  <#if (pageNo == pageCount)>
<span class="disabled">下一页&nbsp;&raquo;</span>
  <#else>
<a href="javascript:void(0)" onclick="turnOverPage(${pageNo + 1})">下一页&nbsp;&raquo;</a>
  </#if>
</form>
<script language="javascript">
  function turnOverPage(no){
    var qForm=document.qPagerForm;
    if(no>${pageCount}){no=${pageCount};}
    if(no<1){no=1;}
    qForm.pageNo.value=no;
    qForm.action="${toURL}";
    qForm.submit();
  }
</script>
</div> 
</#macro>  