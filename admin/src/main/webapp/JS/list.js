/***
 *	SHOP++ Register JavaScript
 *
 *	http://www.shopxx.net
 *
 *	Copyright (c) 2008 SHOP++. All rights reserved.
 **/

$().ready( function() {

	var $allCheck = $(".list input.allCheck");// 全选复选框
	var $idsCheck = $(".list input[name='ids']");// ID复选框
	var $deleteButton = $(".list input.deleteButton");// 删除按钮
	
	var $listForm = $(".list form");// 列表表单
	var $searchButton =  $("#searchButton");// 查询按钮
	var $pageNumber = $("#pageNumber");// 当前页码
	var $pageSize = $("#pageSize");// 每页显示数
	var $sort = $(".list .sort");// 排序
	var $orderBy = $("#orderBy");// 排序方式
	var $order = $("#order");// 排序字段
	
	// 全选
	$allCheck.click( function() {
		var checked = $(this).attr("checked");
		if (checked == "checked" || checked == true) {
			$idsCheck.attr("checked", true);
			$deleteButton.attr("disabled", false);
		} else {
			$idsCheck.attr("checked", false);
			$deleteButton.attr("disabled", true);
		}
	});
	
	// 无复选框被选中时,删除按钮不可用
	$idsCheck.click( function() {
		var $idsChecked = $(".list input[name='ids']:checked");
		if ($idsChecked.size() > 0) {
			$deleteButton.attr("disabled", false);
		} else {
			$deleteButton.attr("disabled", true);
		}
	});
	
	// 批量删除
	$deleteButton.click( function() {
		var url = $(this).attr("url");
		var $idsCheckedCheck = $(".list input[name='ids']:checked");
		if (confirm("您确定要删除吗？") == true) {
			$.ajax({
				url: url,
				data: $idsCheckedCheck.serialize(),
				dataType: "json",
				async: true,
				beforeSend: function(data) {
					$deleteButton.attr("disabled", true);
				},
				success: function(data) {
					$deleteButton.attr("disabled", false);
					if (data.status == "success") {
						$idsCheckedCheck.parent().parent().remove();
					}
					$.message(data.status, data.message);
				},
				error:function(p1, textStatus, p3){
					alert("删除失败,请立即手动刷新");
				}
			});
		}
	});

	// 查找
	$searchButton.click( function() {
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 每页显示数
	$pageSize.change( function() {
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 排序
	$sort.click( function() {
		var $currentOrderBy = $(this).attr("orderBy");
		if ($orderBy.val() == $currentOrderBy) {
			if ($order.val() == "") {
				$order.val("0");
			} else if ($order.val() == "1") {
				$order.val("0");
			} else if ($order.val() == "0") {
				$order.val("1");
			}
		} else {
			$orderBy.val($currentOrderBy);
			$order.val("0");
		}
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 排序图标效果
	sortStyle();
	function sortStyle() {
		var orderByValue = $orderBy.val();
		var orderValue = $order.val();
		if(orderValue == "0"){
			orderValue = "asc";
		} else {
			orderValue = "desc";
		}
		if (orderByValue != "" && orderValue != "") {
			$(".sort[orderBy='" + orderByValue + "']").after('<span class="' + orderValue + 'Sort">&nbsp;</span>');
		}
	}
	
	// 页码跳转
	$.gotoPage = function(id) {
		$pageNumber.val(id);
//		alert("go to page:" + id);
		$listForm.submit();
	}
	
	//提示
	$.message = function(){
		if (arguments.length == 1) {
			messageType = "warn";
			messageText = arguments[0];
		} else {
			messageType = arguments[0];
			messageText = arguments[1];
		}
		var alertMessage;
		if (messageType == "success") {
			alertMessage = "成功\n" + messageText;
		} else if (messageType == "error") {
			alertMessage = "提示\n" + messageText;
		} else {
			alertMessage = "错误\n" + messageText;
		}
		alert(alertMessage);
	}
});