$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '产品价格管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				address = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (address == '') {
					alert("请选择！");
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + address);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (address == '') {
					alert("请选择！");
					return;
				}

				$f = $('#addressForm');
				if (confirm("确定继续？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/productPrice/ea_delProductPrice.jspa?pageNumber="
											+ ppageNumber
											+ "&productPrice.productpriceID="
											+ address);
					document.addressForm.submit.click();
					$("tr#" + address).remove();
					address == '';
					token = 11;
				}

				break;
			case '导出' :
				var url = basePath
						+ "/ea/productPrice/ea_showProductPriceExcel.jspa?pageNumber="
						+ ppageNumber + "&search=" + psearch;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + address);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/productPrice/ea_getProductPriceList.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}
	// 新加内内容开始 target 指向页面隐藏Hidden
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if (address == "") {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/productPrice/ea_saveProductPrice.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					token = 1;
					return;
				}
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/productPrice/ea_saveProductPrice.jspa?pageNumber="
										+ ppageNumber);
				document.cstaffForm.submit.click();
				token = 2;
			});

	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();

			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				cultureID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 新加内容结束
	$(".address tr[id]").click(function() {
				address = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				basePath + "/ea/productPrice/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	// 新加内内容开始
	$(".address tr[id]").dblclick(function() {
				action("查看");
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/productPrice/ea_getProductPriceList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
// 新加内容结束
