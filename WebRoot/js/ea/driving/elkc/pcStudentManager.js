$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [/*{
					name : '添加',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						//
					}, {
					separator : true
				},*/{
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
			/*case '添加' :
				addVacation();
				break;
			case '修改' :
				if (etvId == "") {
					alert('请选择!');
					return;
				}
                standard(etvId);
				break;
			case '删除' :
				if (etvId == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
                    delstandard(etvId);
				}
				break;*/
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/coachreserv/ea_pcStudentManager.jspa?tbElycOrderRecord.studentName="+$("#studentName").val()+"&tbElycOrderRecord.teacherName="+$("#teacherName").val()+"&StartTime="+$("#startTime").val()+"&EndTime="+$("#endTime").val()+"&tbElycOrderRecord.status="+$("#status").val();
				numback(url);
				break;
		}
	}


	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
        etoId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});

	//根据时间查询
	$(".input-button").click(function(){
        ppageNumber = 0;
		var url = basePath
            + "/ea/coachreserv/ea_pcStudentManager.jspa?tbElycOrderRecord.studentName="+$("#studentName").val()+"&tbElycOrderRecord.teacherName="+$("#teacherName").val()+"&StartTime="+$("#startTime").val()+"&EndTime="+$("#endTime").val()+"&tbElycOrderRecord.status="+$("#status").val();
		document.location.href = url;
	})

});
