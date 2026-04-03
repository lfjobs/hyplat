$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	if (module == "contract") {
		$(".trtip").show();

	} else {
		$(".trtip").hide();
	}
	$('.yspdoc').flexigrid({
				height : 349,
				width : 'auto',
				minwidth : 30,
				title : '已审批',
				minheight : 349,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
						name : '查看附件',
							bclass : 'attach',
							onpress : action
						},{
							separator : true
						}, {
					name : '放入回收站',
					bclass : 'litter',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '审批跟踪登记表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					       name : '导出',
					       bclass : 'excel',
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
				if (docId == "") {
					alert('请选择!')
					return;
				}
				window.location.href = basePath
						+ "ea/documentinfo/ea_getViewDocument.jspa?docId="
						+ docId + "&type=toFinishedView&date=" + new Date();

				break;
			case '放入回收站' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				//if (confirm("确定放入回收站？")) {
					putRecycleBin(docId+",", "examine");
			//	}
				break;
				
			case '查看附件' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				OpenOffice(docId,"1","已审批");
				break;
			case '审批跟踪登记表':
				document.location.href=basePath+"/ea/documentflow/ea_getExamineDocList.jspa?finishType=examineyes&track=00";
				break;
			case '查询' :
				showQueryContent("yesexamine");
				break;
			case '导出' :
				url = basePath + "/ea/documentflow/ea_showExcel.jspa?search="
						+ search+"&finishType=examineyes&date="
						+ new Date();;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examineyes&search="+search+"&date="
						+ new Date();
				numback(url);
				break;

		}
	}

	$("#tosearch").click(function() {
		$("#searchForm #finishType").val("examineyes");

		$("#searchForm").attr(
				"action",
				basePath
						+ "ea/documentflow/ea_toSearchexamine.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
		token = 2;
	});

	$(".yspdoc tr").click(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".yspdoc tr").dblclick(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});

});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examineyes";

	}
}
