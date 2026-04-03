$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '资产管理明细流水表',
				minheight : 80,
				buttons : [{
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
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				break;
			case '导出' :
				var url = basePath
						+ "ea/goodsmanage/ea_showExcelAssetsWater.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/goodsmanage/ea_getAssetsWaterList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
		}
	}
	$("#tosearch").click(function() {
		$("#SearchForm")
				.attr(
						"action",
						basePath
								+ "ea/goodsmanage/ea_toSearchAssetsWater.jspa?pageNumber="
								+ ppageNumber + "&pageForm.pageNumber="
								+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
		$("#tosearch").attr("disabled", "disabled");
	});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();

			});
});

/** *******************************取得部门下拉************************************ */
$(function() {
	var treeName = treeNames;
	var url = basePath
			+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath
							+ "page/ea/not_login.jsp";
				}
				var oList = member.organizationlist;
				var data = new Array();
				data[0] = {
					id : treeID,
					pid : '-1',
					text : treeName
				};
				for (var i = 0; i < oList.length; i++) {
					data[i + 1] = {
						id : oList[i].organizationID,
						pid : oList[i].organizationPID,
						text : oList[i].organizationName
					};
				}
				$t = $("table#SearchTable");
				var ts = new TreeSelector($t
						.find("select#departmentID")[0], data, -1);
				ts.createTree();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
	});
});