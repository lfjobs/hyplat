$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '库房管理',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
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
				},{
					name : '物品借出',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/personal/ea_getInventoryManagementList.jspa?search="
						+ search;
				numback(url);
				break;
			case '物品借出':
				if (inventoryID == "") {
					alert("请选择！");
					return;
				}
				$("tr#" + inventoryID).find("span[id]").each(function() {
						$("#jqModeljiechu").find("input#" + this.id).val($(this).text());
				});
				$("#jqModeljiechu").jqmShow();
				break;
		}
	}
	// 这一行的单击事件
		$(".address tr[id]").click(function() {
			$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
			inventoryID = this.id;
		});
	
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "/ea/personal/ea_toSearchInventoryManagement.jspa?pageNumber="
								+ pNumber);
		document.postSearchForm.submit.click();
	});
	$("#tojiechu").click(function() {
		var r = /^[0-9]*[1-9][0-9]*$/; // 正整数正则
		if (!r.test($.trim($("input#storageQuantity1").val()))
				|| $.trim($("input#storageQuantity1").val()) == "") {
			alert("必须为正整数或不能为空");
			return
		}
		var num = parseInt($.trim($("tr#" + inventoryID)
				.find("span#storageQuantity").text()));// 为入库数量
		var num1 = parseInt($.trim($("input#storageQuantity1").val()));// 入库数量
		if (num1 > num) {
			alert("入库数量必须小于未入库数量");
			return
		}
		if($("#staffname").val()==""){
			alert("请填写物品使用人");
			return;
		}
		$("#tojiechuForm").attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/personal/ea_getjiechu.jspa?pageNumber="
								+ pNumber);
		document.tojiechuForm.submit.click();
		token = 13;
	});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
});

function re_load() {
		document.location.href = basePath
				+ "/ea/personal/ea_getInventoryManagementList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
