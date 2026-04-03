$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.orkcalendar').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '工作记录时间表' ,
				minwidth : 30,
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
				},{
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.location.href = basePath
				+ "ea/workcalendar/ea_toAdd.jspa?seaDate=";
				break;
			case '查询' :
				$("#jqModelSerch").jqmShow();
				break;
			case '导入' :
				$("#jqModelDaoRu").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/workcalendar/ea_getAdanceList.jspa?1=1";
				numback(url);
				break;
		}
	}

	$(".adance tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		adanceid = this.id;
	});
	
	// 导入预览
	$("input.JQueryDaoRu").click(function() {
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
											+ "ea/adance/ea_excelImport.jspa?excelImport.importPath=ea/adance/ea_saveExcelImport.jspa&excelImport.exclePath=ea/adance/ea_excelImport.jspa");
					document.daoRuForm.submit.click();
					$("#jqModelDaoRu").jqmHide();
					$("#jqmWindow2").jqmShow();
					token = 2;
				}
			});
	// 查询相关操作
	$("#searchAda").click(function() {
		$("#jqModelSerchForm").attr(
				"action",
				basePath + "ea/adance/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.jqModelSerchForm.submit.click();
		$("#jqModelSerchForm").find(":input[name]").val("");
	});
	// 取消
	$("input.JQueryreturn").click(function() {
				$("#jqModelAdd").jqmHide();
				re_load();
			});
	// 预览返回
	$("a#DaoRuFan").click(function() {
				$("#jqmWindow2").jqmHide();
				re_load();
			});	
	
});
function re_load() {
	if (token){
		document.location.href = basePath
				+ "ea/adance/ea_getAdanceList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}
