$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	if (type == "company" || type == "group") {
	$('.registration').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '印章使用日志----印章名称：' + parent.stampName,
				minheight : 80,
				buttons : [{
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
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	}else{
		$('.registration').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '印章使用日志----印章名称：' + parent.stampName,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
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
					name : '全部保存',
					bclass : 'add',
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
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	}
	function action(com, grid) {
		switch (com) {

			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"stampLogmap[" + select + "]." + this.name);

				});
				$p = $("#sa" + select).find(':input:eq(3)');
				$p.attr("id", $p.attr("id") + select);

				var id = $p.attr("id");
				$p.next("a").attr("href", "javascript:importGY('" + id + "');");
				$("#sa" + select).show();
				select++;
				break;
			case '修改' :
				if (stampLogID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + stampLogID);
				$a = $p.find(':input:eq(3)');
				$a.attr("id", $a.attr("id") + select);
				var id = $a.attr("id");
				$a.next("a").attr("href", "javascript:importGY('" + id + "');");
				if ($p.find('span:eq(7)').text() == "自动") {
					alert("只能修改手动方式");
					return;
				}
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"stampLogmap[" + select + "]." + this.name);
				});

				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("a").removeClass("model1");
				$p.find("s:select").attr("disabled", false);
				$p.find("select").show();
				$(this).parent().children("span").show();
				break;
			case '全部保存' :
				if (notoken) {
					return;
				}
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				$("#stampDate", $(".check")).each(function(i, tmp) {
							if (this.value == "") {
								alert("请输入日期");
								$(this).css("background-color", "red");
								re = 1;
							}

						});
				if (re) {
					notoken = 0;
					return;
				}
				$('#staffappraisalForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/stamplog/ea_saveStampLog.jspa?pageNumber="
										+ pNumber);
				document.staffappraisalForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (stampLogID == '') {
					alert("请选择！");
					return;
				}
				if (stampLogID.substring(0, 2) == "sa") {
					if (confirm("是否移除？")) {
						$("#" + stampLogID).remove();
						stampLogID = "";
					}
					return;
				}
				$f = $('#staffappraisalForm');
				if (confirm("是否删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/stamplog/ea_delStampLog.jspa?pageNumber="
											+ pNumber + "&stampLog.stampLogID="
											+ stampLogID);
					document.staffappraisalForm.submit.click();
					$("tr#" + stampLogID).remove();
					stampLogID = "";
					token = 11;

				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/stamplog/ea_getListStampLog.jspa?stampLog.enterpriseStampID="
						+ enterpriseStampID + "&search=" + search+"&gore=g&type="+type;
				numback(url);
				break;
			case '导出' :
				//var sdate = $("#sdate").attr("value");
				//var edate = $("#edate").attr("value");
				var url = basePath + "ea/stamplog/ea_showExcel.jspa?search="
						+ search + "&stampLog.enterpriseStampID="
						+ enterpriseStampID;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
		}
	}
	// 点击选中
	$(".registration tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				stampLogID = this.id;
			});
	$(".registration tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				stampLogID = this.id;
				action("修改");
			});
	// 根据条件查询
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#appraisalForm")
				.attr(
						"action",
						basePath + "ea/stamplog/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.appraisalForm.submit.click();
	});

	$(".scanAttach").click(function() {
				var $scan = $(this).prev("input");
				var scanAttach = $.trim($scan.val());
				var urlReturn = OpenWord(scanAttach, 2);
				$scan.attr("value", urlReturn);

			});
});
function re_load() {
	if (token)
		document.location.reload();// .href=basePath+"ea/stamplog/ea_getListStampLog.jspa?stampLog.enterpriseStampID="+enterpriseStampID+"&pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
}

function importGY(s) { // 打开页面
	$("#daoRu").attr(
			"src",
			basePath + "ea/stamplog/ea_getStaffformalList.jspa?date="
					+ new Date());
	$("#markid").val(s);
	$("#socialJqm").jqmShow();
}

function DaoruConfirm() {// 选择确定
	var childopertionID = window.frames["daoRu"].opertionID;
	if (childopertionID == "") {
		alert("请选择");
		return;
	}

	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();
	var id = $("#markid").val();
	$("#" + id).val(staffName);
	$("#" + id).prev("input").val(childopertionID);
	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}

// 设置word模板打开word
function OpenWord2(submitType, WorkMode, fileTypes, addType) {
	//var url = submitType;
	jQuery.ajaxSetup({
				cache : false
			});
	if (fileTypes == null || fileTypes == "") {
		fileTypes = "W";
	}
	if (addType != "auto") {
		if (submitType != null && $.trim(submitType) != "") {
			open(basePath + "page/ea/common/common_word.jsp?docPath="
					+ submitType + "&fileType=" + fileTypes + "&WorkMode="
					+ WorkMode);
		} else {
			var urll = basePath
					+ "ea/uploadfile/sajax_n_ea_editWord.jspa?fileType="
					+ fileTypes + "&date=" + new Date().toLocaleString();
			$.ajax({
						url : encodeURI(urll),
						type : "get",
						dataType : "json",
						async : false,
						success : function(data) {
							var jsonresult = eval("(" + data + ")");
							var result = jsonresult.result;
							//url = result;
							open(basePath
									+ "page/ea/common/common_word.jsp?docPath="
									+ result + "&fileType=" + fileTypes
									+ "&WorkMode=" + WorkMode);
						},
						error : function(data) {
							alert("\u521b\u5efa\u6a21\u677f\u5931\u8d25");
						}
					});
		}
	} else {
		open(basePath + "page/ea/common/weonlyreadprint.jsp?docPath="
				+ submitType + "&fileType=" + fileTypes + "&WorkMode="
				+ WorkMode);
	}
}