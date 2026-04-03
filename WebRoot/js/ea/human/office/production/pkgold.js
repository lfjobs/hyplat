$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.pkgold').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : 'PK金管理',
				minheight : 80,
				buttons : [  {
					name : '返回',
					bclass : 'restore',
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '返回'	:
				document.location.href = basePath + "ea/pkgoldpool/ea_getList.jspa?t=" + new Date();
			break;
			case 'PK金支出':
				$("#jqModelSave").jqmShow();
//				if (confirm("是否生成当月pk金？")) { 
//					var url = basePath +"ea/pkgold/sajax_ea_saveAllPKGoldPool.jspa?d=" + new Date();
//					$.ajax({
//						url : encodeURI(url),
//						type : "get",
//						async : true,
//						dataType : "json",
//						success : function cbf(data) {
//							var member = eval("(" + data + ")");
//							var suc = member.suc;
//							re_load();
//						},
//						error : function cbf(data) {
//							retoken = 0;
//							alert("数据获取失败！");
//						}
//					});
////				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/pkgold/ea_getList.jspa?search="
						+ search;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/pkgold/ea_showExcel.jspa?startdate="
						+ pstartdate + "&enddate=" + penddate + "&search="
						+ search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
		}
	}
	// 点击选中

	$(".pkgold tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				pkgoldID = this.id;
			});
	$(".pkgold tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				pkgoldID = this.id;

			});
	// 根据条件查询
	$("#tosearch").click(function() {
		$("#searchForm")
				.attr(
						"action",
						basePath
								+ "ea/pkgold/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.searchForm.submit.click();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/pkgold/ea_getList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}