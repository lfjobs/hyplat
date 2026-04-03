$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '会议室管理',
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'restore',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '添加',
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
					name : '删除',
					bclass : 'delete',
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
			case '返回' :
				document.location.href=basePath+"ea/meetingroom/ea_getMyRoomOrder.jspa";
				break;
			case '添加' :
				$("input.JQuerypersonvalue").attr("checked", false);
				document.postRoomForm.reset();
				$("#jqModelRoom").jqmShow();
				break;
			case '修改' :
				if (mroomID == "") {
					alert('请选择!');
					return;
				}
				document.postRoomForm.reset();
				$t = $("div#jqModelRoom");
				$p = $("tr#" + mroomID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModelRoom").jqmShow();
				break;
			case '删除' :
				if (mroomID == "") {
					alert('请选择！');
					return
				}
				$f = $('#postRoomForm');
				$f.find(':input#mroomID').val(mroomID);
				
				var url = basePath+"ea/meetingroom/sajax_ea_checkDelRoom.jspa";
				$.ajax({
				url:url,
				type:"get",
				async:false,
				dataType:"json",
				data:{
				   "meetingRoom.mroomID":mroomID
				},
				success:function(data){
					var mem = eval("("+data+")");
					var result = mem.result;
					if(result=="suc"){
						if (confirm("是否删除？")) {
				 	$("#postRoomForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/meetingroom/ea_delMeetingRoom.jspa?pageNumber="
											+ ppageNumber + "&search="
											+ psearch);
					document.postRoomForm.submit.click();
					$("tr#" + mroomID).remove();
					mroomID = "";
					token = 11;
				   }
					}else{
						alert("该会议室已被预约不可删除");
						return;
					}
				},
				error:function(data){
					alert("检查是否删除失败");
				}
				
				
				});
				
				
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
				
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/meetingroom/ea_getMeetingRoomList.jspa?search="
						+ psearch;
				numback(url);
				break;

		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				mroomID = this.id;
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				mroomID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/meetingroom/ea_toSearchRoom.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});

	// 提交
	$("#toSaveRoom").click(function() {
		$("#postRoomForm").attr("target", "hidden").attr("action",
				basePath + "ea/meetingroom/ea_saveMeetingRoom.jspa");
		document.postRoomForm.submit.click();
		token = 2;
	});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/meetingroom/ea_getMeetingRoomList.jspa?search=" + psearch
				+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
