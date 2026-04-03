$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '车辆信息',
				minheight : 80,
				buttons : [ {
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
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				pageSize = prompt("输入显示条数", "请输入小于50正整数");
				if (pageSize < 0 || pageSize != parseInt(pageSize)
						|| pageSize > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				window.location.href = basePath
						+ "/ea/safetycheck/ea_car.jspa?search=" + search
						+ "&pageSize=" + pageSize;
				break;
		}
	}
	
   //用于阻止复选框的冒泡行为；
	$("input.JQuerypersonvalue").click(function(event) {
				event.stopPropagation();

			});

	$(".JQueryflexme tr[id]").toggle(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			},function(){
			   $("input.JQuerypersonvalue", $(this))
						.attr("checked", false);
			});


	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "/ea/safetycheck/ea_toSearchCar.jspa?pageNumber="
						+ pageNumber);
		document.postSearchForm.submit.click();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/safetycheck/ea_car.jspa?pageNumber=" + pageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}




