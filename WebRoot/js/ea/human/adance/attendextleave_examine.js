$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.extleavetable').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '加班请假审核管理' ,
				minwidth : 30,
				minheight : 80,
				buttons : [{
					name : '审核通过',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '驳回',
					bclass : 'edit',
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
			case '审核通过':
				if(extleaveId == ""){
					alert("请选择！");
					return;
				}
				$('#extleaveForm')
				.attr("target", "hidden")
				.attr("action",
						basePath
								+ "ea/attendextleave/ea_upExtleaveExam.jspa?extleaveId="+extleaveId + "&esastate=01");
				document.extleaveForm.submit.click();
				token = 2;
				break;
			case '驳回':
				if(extleaveId == ""){
					alert("请选择！");
					return;
				}
				$('#extleaveForm')
				.attr("target", "hidden")
				.attr("action",
						basePath
								+ "ea/attendextleave/ea_upExtleaveExam.jspa?extleaveId="+extleaveId + "&esastate=02");
				document.extleaveForm.submit.click();
				token = 2;
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/attendextleave/ea_getExtleaveExam.jspa?1=1";
				numback(url);
				break;
		}
	}

	$(".extleavetable tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		extleaveId = this.id;
	});
	// 取消
	$("input.JQueryreturn").click(function() {
				$("#jqModelAdd").jqmHide();
				re_load();
			});
	
});
function re_load() {
	if (token){
		document.location.href = basePath
				+ "ea/attendextleave/ea_getExtleaveExam.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}
