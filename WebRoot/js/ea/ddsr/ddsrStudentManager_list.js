$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '学员信息管理',
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
			case '设置每页显示条数':
				var url = basePath + "ea/coachreservationrecordcontent/ref_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList&search=turnpage";
				numback(url);
				break;			
		}
	}
	$(".JQueryflexme tr[id]").click(function() {
				stud_key = this.id;		
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySearchSubmit").click(function(){
		$("#jqModelSearch").jqmHide();
		$("#searchForm").attr("action",
				basePath + "ea/coachreservationrecordcontent/ref_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList");
		document.searchForm.submit.click();
	});
	$(".JQuerySearchReturn").click(function(){
		$("#jqModelSearch").jqmHide();
	});
});