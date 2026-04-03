$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 200,
				width : 'auto',
				minwidth : 30,
				title : '任务通知',
				minheight : 80,
				buttons : [{
					name : '新建',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
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
			case '新建' :
				taskid = '';
				document.location.href = basePath
						+ "ea/taskmanage/ea_addNewTaskPage.jspa?myproject.proid="+proid;

				break;
			case '修改' :
				if (taskid == "") {
					alert('请选择!');
					return;
				}
				
				var distributeid = $("tr#"+taskid).find("#distributeid").text();
				if(distributeid!=currentstaffid){
					alert("只有派发人可以修改");
					return;
				}
				document.location.href = basePath
						+ "ea/taskmanage/ea_addNewTaskPage.jspa?mytask.taskid="
						+ taskid+"&myproject.proid="+proid+"&update=update";
				break;
			case '查看' :
				 if (taskid == "") {
				 alert('请选择!');
				 return;
				 }
				document.location.href = basePath
						+ "ea/taskmanage/ea_seeTaskPage.jspa?mytask.taskid="+taskid;
				break;

			
			case '查询' :
				$("#jqModel").jqmShow();

				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/taskmanage/ea_getTaskNoticeList.jspa?myproject.proid="+proid;
				numback(url);
				break;
		}
	}


	// 新加内容结束

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				taskid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	// 查询
	$("#search").click(function() {
		$("#searchForm").attr("action",
				basePath + "ea/taskmanage/ea_toSearchByTaskNotice.jspa");
		document.searchForm.submit.click();

	});

});

function re_load() {
	if (token) {

		document.location.href = basePath
				+ "ea/taskmanage/ea_getTaskNoticeList.jspa?myproject.proid="+proid;

	}
}