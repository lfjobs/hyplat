$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.address').flexigrid({
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : '系统消息提醒',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
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
				}/*, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}*/, {
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
			case '添加' :
				sremindID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			/*case '修改' :
				if (sremindID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + sremindID);
				var state=$p.find("span#sremindStatus").text();
				if (state != "未发送") {
					alert('只能修改未发送的信息!');
					return;
				}
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				
				var scircularType=$("input#scircularType").val();
				if(scircularType=="待阅")
				{$("select#scircularType").get(0).selectedIndex = 0;}
				else if(scircularType=="待办")
				{$("select#scircularType").get(0).selectedIndex = 1;}
				
				var sremindType=$("input#sremindType").val();
				if(sremindType=="页面弹框")
				{$("select#sremindType").get(0).selectedIndex = 0;}
				else if(sremindType=="电脑客户端")
				{$("select#sremindType").get(0).selectedIndex = 1;}
				else if(sremindType=="手机客户端提醒")
				{$("select#sremindType").get(0).selectedIndex = 2;}
				break;*/
			case '删除' :
				if (sremindID == "") {
					alert('请选择！');
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#sremindID').val(sremindID);
				if (confirm("确定继续？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/systemremind/ea_delremind.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.cstaffForm.submit.click();
					$("tr#" + sremindID).remove();
					sremindID = "";
				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/systemremind/ea_getListRemind.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$("table tr[id]").click(function() {
		        sremindID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	//条件查询消息
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/systemremind/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
	});
	
	/*$(".address tr[id]").dblclick(function() {
				action("修改");
			});*/
	// 取消
	$(".close").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	
	//添加、修改消息
	$("input#tosave").click(function() {
		var receiveDate = $("input#sreceiveDate").val();
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		//判断添加消息
		if (sremindID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/systemremind/ea_addsremind.jspa?receiveDate="+receiveDate+"&pageNumber="
									+ pNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 2;
			return;
		}
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/systemremind/ea_getListRemind.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
