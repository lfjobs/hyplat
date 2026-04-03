$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.JQueryflexme').flexigrid({
				height : 168,
				width : 'auto',
				minwidth : 30,
				title : '仓库管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						//
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
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (id == "") {
					alert("请选择!");
					return;
				}
				$t = $("#SearchForm");
				if (confirm("是否删除？")) {
					$t
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/warehouse/ea_deleteWareHouse.jspa?search="
											+ search + "&warehouse.wareID="
											+ id + "&pageNumber=" + ppageNumber);
					document.SearchForm.submit.click();
					$("tr#" + id).remove();
					id = "";
					token = 11;
				}
				break;
			case '导出' :
				var url = basePath + "ea/warehouse/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/warehouse/ea_getListWareHouse.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				id = this.id;
			});
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/warehouse/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.SearchForm.submit.click();
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/warehouse/ea_getListWareHouse.jspa?pageNumber="
				+ ppageNumber + "&search=" + search;
}
