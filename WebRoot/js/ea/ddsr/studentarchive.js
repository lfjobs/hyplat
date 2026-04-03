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
				title : '学员归档管理',
				minheight : 80,
				buttons : [{
					name : '完善档案',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
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
			case '完善档案' :
				if (studentarchiveID == "") {
					alert('请选择学员！');
					return
				}
				var url = basePath + "ea/studentarchive/ea_toSTUarvhive.jspa?stua.studentarchivesid="+studentarchiveID;
				open(url);
				break;
			case '查询' :
				document.SearchForm.reset();
				$("#jqModelSerch").jqmShow();
				break;			
			case '设置每页显示条数' :
				var url = basePath + "ea/studentarchive/ea_getListSTU.jspa?search=" + search;
				numback(url);
				break;			
		}
	}
//	$(".JQueryflexme tr[id]").dblclick(function() {
//				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
//			});
	$(".JQueryflexme tr[id]").click(function() {
		studentarchiveID = this.id;				
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#SearchForm").attr("action",
				basePath + "ea/studentarchive/ea_toSearch.jspa?pageNumber=" + ppageNumber);
		document.SearchForm.submit.click();
		
	});
	$(".JQuerySubmit").click(function() {		
		$(".put3", $("table#stafftable")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr("action",
				basePath + "ea/subjectManager/ea_doSubjectManagerAction.jspa?innerAction=updateSubject&pageNumber=" + pNumber);		
		if (subjKey == "") {			
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			token = 1;
			return;
		}		
		document.cstaffForm.submit.click();
		token = 2;		
	});
	
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});	
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/studentarvhive/ea_getListSTU.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");		
	}
}