$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
   var query = "<form name='postSearchForm' id='postSearchForm' method='post'>"
			+ "进销存明细&nbsp;<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:10px;\">物品类别：</span><input type='text' style=\"width: 100px\" name='stockinvParam.goodsType'/>&nbsp;&nbsp;"
			+ "物品名称：<input name=\"stockinvParam.goodsName\" style=\"width: 90px\" />&nbsp;&nbsp;"
			+ "日期：<input id=\"sdate\" name=\"sdate\" onfocus=\"daytime(this);\" style=\"width: 140px\" />至<input id=\"edate\" name=\"edate\" onfocus=\"daytime(this);\" style=\"width: 140px\" />&nbsp;&nbsp;"
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";	
	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [/*{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, */{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '微分金导出',
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
				//$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/warehousing/ea_getInventoryPoolList.jspa?search="
						+ search;
				numback(url);
				break;
			case '打印预览':
				var url =basePath
				+ "page/ea/main/finance/invoicing/Inventory_frame.jsp?search="+search
				+ "&pageNumber="+pNumber+"&pageCount="+pageCount+"&one=one";
		         open(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/warehousing/ea_poolShowExcel.jspa?pageNumber=" + pNumber
						+ "&search=" + search;
				open(url);
				break;
			case '微分金导出' :
				var url = basePath
						+ "ea/warehousing/ea_WFJExcel.jspa?pageNumber=" + pNumber
						+ "&search=" + search;
				open(url);
				break;
		}
	}	
	// 这一行的单击事件
	$(".address tr[id]").click(function() {
		financialbillID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	//单击事件
	$("tr[id]").live("click", function(event) {
				seedID = this.id;
				$("input.ragood", $(this)).attr("checked", "checked");
			});

	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr("action",basePath+ "ea/warehousing/ea_toSearchInventoryPool.jspa?pageNumber="
								+ pNumber);
		document.postSearchForm.submit.click();
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
	if (token)
		document.location.href = basePath
				+ "ea/warehousing/ea_getInventoryPoolList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
