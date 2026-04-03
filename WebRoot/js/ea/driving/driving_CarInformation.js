$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 140,
				width : 'auto',
				minwidth : 30,
				title : '车辆信息管理',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, 
					{
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
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getCarInformationList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}	
	
	//单击事件
	$(".JQueryflexme tr[id]").click(function() {
				carID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	//搜索窗口
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	//查询事件
	$("#searchCar").click(function() {	
		$("#carSearchForm").attr("action",
				basePath + "ea/driving/ea_toSearchChe.jspa?pageNumber="+pNumber);
		document.carSearchForm.submit.click();
	});	
});
