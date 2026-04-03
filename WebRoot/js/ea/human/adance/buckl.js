$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.buckl').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '奖扣记录汇总' ,
				minwidth : 30,
				minheight : 80,
				buttons : [{
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
				$("#jqModelSerch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/buckl/ea_getAwarBuckl.jspa?1=1";
				numback(url);
				break;
		}
	}

	$(".buckl tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		bucklid = this.id;
	});
	// 查询相关操作
	$("#searchBuckl").click(function() {
		$("#jqModelSerchForm").attr(
				"action",
				basePath + "ea/buckl/ea_getAwarBuckl.jspa?pageNumber="
						+ ppageNumber);
		document.jqModelSerchForm.submit.click();
	});
	$("input.JQueryreturn").click(function() {
				$("#jqModelAdd").jqmHide();
				re_load();
			});
});
function re_load() {
	if (token){
		document.location.href = basePath
				+ "ea/buckl/ea_getAwarBuckl.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}
