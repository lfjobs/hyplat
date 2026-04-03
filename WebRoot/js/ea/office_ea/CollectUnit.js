$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$(".jqmreturn").click(function() {
				notoken = 0;
				$("#documentsjqModel").jqmHide();
				$("#previewjqModel").jqmHide();
				showDocument = false;
			});

	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "收集单位",
				minheight : 80,
				buttons : [{
                    name: '返回',
                    bclass: 'prev',
                    onpress: action//当点击调用方法
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
                    separator: true
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
			case '导出' :
				url = basePath + "/ea/collectunit/ea_showExcel.jspa?search=" + search
						+ "&sdate=" + sdate + "&edate=" + edate +"&staffname=" + staffname;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/collectunit/ea_getTracklist.jspa?search=" + search
						+ "&sdate=" + sdate + "&edate=" + edate +"&staffname=" + staffname;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				break;
			case '返回':
				document.location.href=basePath+"/ea/companytrack/ea_getCompanyList.jspa?&search="+search+"&sdate=" + sdate + "&edate=" + edate+"&pageNumber="+pNumber;
				break;
		}
	}
	// 一行单击事件
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				status = $("tr#" + cashierBillsID).find("span#status").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 查询按钮单据事件
	$("#tosearch").click(function() {
		$("#SearchForm").attr("action",
				basePath + "/ea/collectunit/ea_toSearch.jspa?pageNumber=" + pNumber);
		document.SearchForm.submit.click();
	});
	// 一行双击事件
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			});

});
function re_load() {
	document.location.href = basePath
			+ "/ea/collectunit/ea_getTracklist.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search + "&sdate=" + sdate + "&edate=" + edate
			+ "&staffname=" + staffname;
}