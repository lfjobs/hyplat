$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "岗位薪水调查",
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					// 设置分割线
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
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
				}, {
					name : '导入',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '添加' :
				payResearchID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModelAdd").jqmShow();
				document.addForm.reset();
				break;
			case '删除' :
				if (payResearchID == "") {
					alert('请选择！');
					return
				}
				$f = $('#addForm');
				$f.find(':input#payResearchID').val(payResearchID);
				if (confirm("确定继续？")) {
					$("#addForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/payresearch/ea_del.jspa?pageNumber="
									+ ppageNumber);
					document.addForm.submit.click();
					$("tr#" + payResearchID).remove();
					payResearchID == '';
					token = 11;
				}
				break;
			case '修改' :
				if (payResearchID == "") {
					alert('请选择！');
					return
				}
				document.addForm.reset();
				$form = $("#addForm");
				$tr = $("tr#" + payResearchID).find("span[id]").each(
						function() {
							$(":input#" + this.id, $form).val($(this).text());
						});
				$("#jqModelAdd").jqmShow();
				break;

			case '查询' :
				$("#jqModelSerch").jqmShow();
				break;

			case '导出' :
				url = basePath + "ea/payresearch/ea_showExcel.jspa?1=1";
				open(url);
				break;
			case '导入' :
				$("#jqModelDaoRu").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/payresearch/ea_getCOPayResearchList.jspa?1=1";
				numback(url);
				break;
		}
	}

	// 新加内内容开始 target 指向页面隐藏Hidden 连续添加和删除
	$("input.JQuerySubmit").click(function() {// 保存
				if ($("form .error").length) {
					return;
				}
				if (payResearchID == "") {
					$("#addForm").attr("target", "hidden").attr(
							"action",
							basePath
									+ "ea/payresearch/ea_save.jspa?pageNumber="
									+ ppageNumber);
					document.addForm.submit.click();
					document.addForm.reset();
					token = 1;
					return;
				}
				$("#addForm").attr("target", "hidden").attr(
						"action",
						basePath + "ea/payresearch/ea_save.jspa?pageNumber="
								+ ppageNumber);
				document.addForm.submit.click();
				token = 2;
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModelAdd").jqmHide();
				re_load();
			});
	$("input.JQueryDaoRu").click(function() {// 导入预览
				var varCurValue = $("#DaoRu").attr("value");
				var varSufType = varCurValue.substring(varCurValue
								.lastIndexOf(".")
								+ 1, varCurValue.length);
				if (varSufType.toLowerCase() != "xls"
						&& varSufType.toLowerCase() != "xlsx") {
					alert("文件格式不正确");
					return;
				} else {
					$("#daoRuForm")
							.attr("target", "daoRu")
							.attr(
									"action",
									basePath
											+ "ea/payresearch/ea_excelImport.jspa?excelImport.importPath=ea/payresearch/ea_saveExcelImport.jspa&excelImport.exclePath=ea/payresearch/ea_excelImport.jspa");
					document.daoRuForm.submit.click();
					$("#jqModelDaoRu").jqmHide();
					$("#jqmWindow2").jqmShow();
					token = 2;
				}
			});

	$(".close").click(function() {// 取消
				$("#jqModelAdd").jqmHide();
				re_load();

			});
	$("#DaoRuFan").click(function() {// 返回
				$("#jqmWindow2").jqmHide();
				re_load();

			});

	// 查询相关操作
	$("#searchPay").click(function() {
		$("#jqModelSerchForm").attr(
				"action",
				basePath + "ea/payresearch/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.jqModelSerchForm.submit.click();
		$("#jqModelSerchForm").find(":input[name]").val("");
	});

	// 新加内容结束
	$(".flexme11 tr[id]").click(function() {
				payResearchID = this.id;
				$("input.JQuerypersonvalue", $(this)).attr("checked", "true");
			});
	$(".flexme11 tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});

});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/payresearch/ea_getCOPayResearchList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}