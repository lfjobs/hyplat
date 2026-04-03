$(function() {
	var title = "<form id='ylForm' method='post'>合格率&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品编号："
			+ "<input  style='width:80px;' name='dcheck.itemNumber'  style='width:150px;'/>"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品名称："
			+ "<input name='dcheck.goodName'style='width:80px;'/>" + "&nbsp;&nbsp;&nbsp;"
			+ "<input type='submit' id='yl' class='input-button' style='margin:0px;margin-left:5px;' value='查询' /></form>";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话

	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.flexigrid').flexigrid({
		height : 355,
		width : 'auto',
		minwidth : 30,
		title : title,
		minheight : 80,
		buttons : ([{
			name : '导出',
			bclass : 'excel',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '打印',
			bclass : 'printer',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} , {
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} ])
	});
	function action(com, grid) {
		switch (com) {
		
		case '导出':
			url = basePath + "ea/dcheck/ea_exportByYield.jspa?type="+Type+"&status="+status+"&show="+show;
			open(url);
			break;
		case '打印':
			
			url = basePath
					+ "ea/dcheck/ea_toPrintPreviewByYield.jspa?type="+Type+"&status="+status+"&show="+show;
			open(url);
			break;
		case '设置每页显示条数':
			var url = basePath + "ea/dcheck/ea_getDCheckyieldList.jspa?type="+Type+"&status="+status+"&show="+show;
					
			numback(url);
			break;

		}
	}
    
	$(".number").each(function(){
		var num=parseFloat($(this).text());
		var str=parseInt(num.toFixed(2)*100) + "%";
		$(this).text(str);
		
	});
	//获取
	$(".JQueryflexme tr[id]").click(function() {
		id = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	/**
	 * 查询
	 */
	$("#yl").click(
			function() {
				var url=basePath + "ea/dcheck/ea_toSearchByYield.jspa?show=01";
				$("form#ylForm").attr("action",
						url);
			});

});

function re_load() {
	if (pageNumber == 1) {
		pageNumber = 0; // 找不到问题 暂时之使用这个方式 原因 不做任何操作 pageNumber 自动 变成 1
	}
	if (token)
		document.location.href = basePath + "ea/dcheck/ea_getDCheckyieldList.jspa?type="+Type
				+ "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

