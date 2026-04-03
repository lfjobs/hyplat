$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.draft0').flexigrid({
				height : 340,
				width : 'auto',
				minwidth : 30,
				title : '盖章投诉',
				minheight : 350,
				buttons : [{
					name : '查看',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '返还投诉处理人',
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
			case '查看' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var Id = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						Id = checkinput[i].value;
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				} else if (length > 1) {
					alert("最多选一个");
					return;
				}

				document.viewForm.reset();
				$t = $("table#viewtable");
				$p = $("tr#" + Id);
				$p.find("span[id]").each(function() {
							$t.find("span#" + this.id).text($(this).text());
							if (this.id == "suggestion") {
								$t.find("#" + this.id).text($(this).text());
							}
						});
				$t.find("#hidcontent").val($p.find("input[id=docPath]").val());
				$t.find("img#dealstatus").attr("src",
						$p.find("img#statusPic").attr("src"));

				$("#jqModelView").jqmShow();
				$("#help").hide();
				break;
			case '返还投诉处理人' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var Ids = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						Ids += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#viewForm #hidIs").val(Ids);
				if (confirm("确定继续？")) {
					$("#viewForm").attr("target", "hidden").attr("action",
							basePath + "ea/extralflow/ea_sealDocument.jspa");
					document.viewForm.submit.click();
					token = 2;
				}

				break;
			case '查询' :
				document.searchForm.reset();
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/extralflow/ea_getUnfinishedList.jspa?type=seal&date="
						+ new Date();
				numback(url);
				break;
		}
	}

	$("#tosearch").click(function() {
		$("#searchForm").attr(
				"action",
				basePath + "ea/extralflow/ea_unfinishSearchPrepare.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
	});

	// 查看点击word
	$("#viewtable img#content").click(function() {
				var docPath = $("#viewtable #hidcontent").val();
				OpenWord(docPath);

			});
	$(".draft0 tr[id]").dblclick(function() {
				Id = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});
	$(".close").click(function() {
				$("#help").show();
			});
});
function OpenWord(docPath) {
	open(basePath + "page/ea/common/common_word.jsp?docPath=" + docPath
			+ "&fileType=W&WorkMode=2");
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/extralflow/ea_getUnfinishedList.jspa?type=seal&pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
