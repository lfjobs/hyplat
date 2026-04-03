$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.draft0').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '归档',
				minheight : 350,
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
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '转移位置',
					bclass : 'transfer',
					onpress : action
						// 当点击调用方法
					}, {
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
				}, {
					name : '查询',
					bclass : 'mysearch',
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
				},{
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
				
			case '查看附件' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				OpenOffice(docId,"1","已归档");
				break;
			case '转移位置' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				$("#positionForm #changeid").val(docId+",");
				$("#positionForm #type1").val("guidang");
				$("#jqModelPosition").jqmShow();

				break;
			case '放入回收站' :
				if (docId == "") {
					alert("请选择");
					return;
				}

				//if (confirm("确定放入回收站？")) {
					putRecycleBin(docId+",", "guidang");
			//	}
				break;
			case '查询' :
				showQueryContent("archive");
				break;
			 case '导出' :
				url = basePath + "/ea/documentinfo/ea_showExcel.jspa?search="
						+ search+"&searchType=archive&date="
						+ new Date();;
				open(url);
				break;
			 case '审批跟踪登记表':
					document.location.href=basePath+"ea/documentinfo/ea_getArchivedList.jspa?track=00&searchType=archive";
					break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentinfo/ea_getArchivedList.jspa?search="
						+ search + "&searchType=" + searchType + "&date="
						+ new Date();
				numback(url);
				break;

		}
	}

	$("#tosearch").click(function() {
		$("#searchForm #searchType").val("archive");
		$("#searchForm").attr(
				"action",
				basePath + "ea/documentinfo/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
	});
	$(".draft0 tr").click(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".draft0 tr").dblclick(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});

});

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentinfo/ea_getArchivedList.jspa";

	}
}


