$(function() {
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		//	.jqDrag('.drag');// 添加拖拽的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("#jqMode2").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
		$("#jqModelSend").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '选择通知面试',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
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
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '面试通知',
					bclass : 'notification',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '短信通知',
					bclass : 'message',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : 'QQ通知',
					bclass : 'qqchat',
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
			case '查看' :

				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + auditionID);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				break;
			case '面试通知' :
				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				$p = $("tr#" + auditionID);
				if ($p.find("span#status").text() == "已通知") {
					alert('人员已通知');
					return
				}
				$t = $("#employmentForm");
				document.employmentForm.reset();
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});
				$("#jqMode2").jqmShow();
				break;
			case '短信通知':
				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				if ($p.find("span#status").text() == "已通知") {
					alert('人员已通知');
					return
				}
				url = basePath + "/ea/telmessage/ea_goMessageIndex.jspa?type=1";
				open(url);
				//$("#jqModelSend").jqmShow();
				break;
			case 'QQ通知':
			if (auditionID == "") {
					alert('请选择人员');
					return
				}
				$z = $("tr#" + auditionID);
							if($z.find("span#referenceCode").html()==""){
								alert("无QQ信息，无法发起会话！");
								return;
							}
				if(confirm("您确定要发起会话吗？")){
					var url = 'tencent://message/?uin='+$z.find("span#referenceCode").html()+'&Site=youself&Menu=yes';
					window.open(url);
					}else{
						return;
					}
				break;
			case '删除' :
				if (auditionID == "") {
					alert('请选择人员');
					return;
				}
				$("#cstaffForm")
						.attr(
								"action",
								basePath
										+ "ea/saudition/t_ea_delAudition.jspa?pageNumber="
										+ pNumber
										+ "&status=0&audition.auditionID="
										+ auditionID);
				document.cstaffForm.submit();
				break;
			case '导出' :
				url = basePath + "ea/saudition/ea_showExcel.jspa?status=0";
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/saudition/ea_getauditionList.jspa?status=0";
				numback(url);
				break;
		}
	}

	$("input.JQueryreturn").click(function() {// 取消
				$("#jqMode2").jqmHide();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				auditionID = this.id;
				$("input.JQueryauditionID", $(this)).attr("checked", "checked");
			});
	$("#auditionBC").click(function() {
		$("#employmentForm").attr(
				"action",
				basePath + "ea/saudition/ea_saveAllAudition.jspa?pageNumber="
						+ pNumber + "&status=1");
		document.getElementById("employmentForm").submit();
	});
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "ea/saudition/ea_toSearchCStaff.jspa?pageNumber="
						+ pNumber + "&status=0");
		document.getElementById("cstaffSearchForm").submit();
		$("#cataffSearchTable").find(":input[name]").val("");
	});

});

//子窗口调用
function refresh(){
    window.location.reload();
} 
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/saudition/ea_getauditionList.jspa?status=0&search="
				+ psearch + "&pageNumber=" + ppageNumber + "&aa=" + aa
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
