$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$(".jqmreturn").click(function() {
				notoken = 0;
				$("#jqmAdd").jqmHide();
				showDocument = false;
			});
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : zz=="00"?"现金日记账报表":"银行日记账报表",
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '生成报表',
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
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '生成报表' :
				$("#FormsSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/statement/ea_getArchivesList.jspa?zz="+zz+"&search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
		}
	}
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#SearchForm")
				.attr(
						"action",
						basePath
								+ "/ea/statement/ea_toSearch.jspa?zz="+zz+"&pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();
	});
	$("#toFormsSearch").click(function() {
		
		window.open(basePath + "/ea/statement/ea_toStatements.jspa?zz="+zz+"&sdate="+$("#sdate").val()+"&edate="+$("#edate").val()+"&pageNumber="+ pNumber);
		$("#FormsSearch").jqmHide();
	});

});
		

$(document).ready(function() {
	
	// 这一行的单击事件
	$("table#gostable tr[id]").live("click", function(event) {
				cashierBillsID = this.id;
				$("input.ra", $(this)).attr("checked", "checked");
			});
});
function re_load() {
	document.location.href = basePath
			+ "/ea/statement/ea_getArchivesList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="	+ $("#pageNumber").attr("value")
			+ "&sdate=" + sdate + "&edate="+ edate+"&search="+ search+"&zz="+zz;
}

