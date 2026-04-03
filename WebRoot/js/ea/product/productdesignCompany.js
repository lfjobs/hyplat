$(document).ready(function() {
		$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '产品设计-公司汇总',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '导出',
					bclass : 'excel',
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
			case '导出' :
				url = basePath + "ea/productdesignC/ea_showExcel.jspa?search=" + search;
				open(url);
				break;
		}
	}
	//模糊查询
	
	$("#tosearch").click(function() {
	$("#postSearchForm").attr(
			"action",
			basePath + "/ea/productdesignC/ea_toSearch.jspa?pageNumber="
					+ pNumber);
	document.postSearchForm.submit.click();
	
});

	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();

			});
});

$(function() {
	//获得公司下所有部门
	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
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

					var data1 = new Array();
					data1[0] = {
						id : treePID,
						pid : '-1',
						text : treePName
					};
					for (var i = 0; i < oList.length; i++) {
						data1[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}

					$t = $("table#SearchTable");

					var ts3 = new TreeSelector($("#departmentID")[0], data1, -1);
					ts3.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/productdesignC/ea_getListProductdesign.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}