$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	var title = "";
	if (type == "group") {
		title = "接待个人记录集团汇总";
	} else {
		title = "接待个人记录";
	}
	$('.JQueryflexme').flexigrid({
				height : 240,
				width : 'auto',striped:false,
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [{
					name : '查看',
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
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '删除' :
				if (telMessageID == "") {
					alert('请选择接待个人记录');
					return;
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#id').val(telMessageID);
					var url = basePath
							+ "ea/tel/tel_delVisit.jspa?visitRecord.id="
							+ telMessageID + "&pageNumber=" + pNumber;
					$f.attr("target", "hidden").attr("action", url);
					telMessageID = "";
					document.cstaffForm.submit.click();
					token = 2;
				}
				break;
			case '查看' :
				if (telMessageID == "") {
					alert('请选择接待个人记录');
					return
				}
				document.cstaffForm.reset();
				lookMSG(telMessageID);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '问题处理' :
				if (telMessageID == "") {
					alert('请选择接待个人记录');
					return
				}
				document.cstaffForm.reset();
                $("#telInRecordId").val(telMessageID);
				var deal = $.trim($("#" + telMessageID).children(".isDeal")
						.text());
				if (deal == "是") {
					$("#btnDeal").hide();
					$("#dealComment").show();
				} else {
					$("#dealComment").show();
					$("#btnDeal").show();
				}
				lookMSG(telMessageID);
				var dealContents = $("#" + telMessageID)
						.children(".dealContent").text();
				$("#dealContent").text($.trim(dealContents));
				break;
			/*
			 * case '导出': url =
			 * basePath+"ea/telmessage/ea_showTelMessageExcel.jspa?search="+search;
			 * open(url); break;
			 */
			case '设置每页显示条数' :
				var pageSize = prompt("输入显示条数", "请输入小于50正整数");
				if (pageSize < 0 || pageSize != parseInt(pageSize)
						|| pageSize > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				var url = basePath + "ea/tel/tel_personRecords.jspa?search="
						+ search + "&type=" + type + "&pageSize=" + pageSize;
				window.location.href = url;
				break;
		}
	}

	$("#btnDeal").click(function() {
		var dealContent = $("#dealContent").val();
		if (dealContent) {
			$f = $('#cstaffForm');
			var url = basePath + "ea/tel/tel_dealTel.jspa?pageNumber=" + pNumber;

			$f.find('#id').val(telMessageID);
			$f.attr("target", "hidden").attr("action", url);
			telMessageID = "";
			document.cstaffForm.submit.click();
			token = 2;
		}
	});
	$(".JQueryflexme tr[id]").dblclick(function() {
				telMessageID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				telMessageID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
			});

	// 查询相关操作
	$("#searchStaff").click(function() {
		var begin = $("#beginDate").val();
		var to = $("#endDate").val();
		var DateFrom = "";
		var DataTo = "";

		// 日期校验
		if (begin) {
			var t = begin.split("-");
			DateFrom = new Date(t[0], t[1], t[2]);
		}

		if (to) {
			var t = to.split("-");
			DataTo = new Date(t[0], t[1], t[2]);
		}

		if (begin && to && (DateFrom >= DataTo)) {
			alert("请选择正确的时间段。");
			return;
		}

		$("#postSearchForm")
				.attr(
						"action",
						basePath + "ea/tel/tel_searchPerson.jspa?pageNumber="
								+ pNumber);
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/tel/tel_personRecords.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
	}
}

function lookMSG(msgID) {
	document.cstaffForm.reset();
	$temp = $("tr#" + msgID);
	$("#recordType").val($.trim($temp.find(".recodeType").attr("sid")));
	$("#customName").val($.trim($temp.find(".customName > div").text()));
	$("#customTel").val($.trim($temp.find(".customTel > div").text()));
	$("#recodeDate").val($.trim($temp.find(".recodeDate > div").text()));
	$("#recordContent").val($.trim($temp.find(".recordContent > div").text()));
	$("#jqModel").jqmShow();
}