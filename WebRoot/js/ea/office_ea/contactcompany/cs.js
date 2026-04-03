$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	window.parent.document.getElementById("mainframe5").style.height = 160 + len * 27 + "px";
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.JQueryflexme').flexigrid({
				height : 'auto',
				width : 'auto',
				allDouble : true,
				minwidth : 30,
				title : '社会单位人员列表',
				minheight : 80,
				buttons : [{
					name : '添加人员',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
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
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加人员' :
				$(".jqmWindowcss1").jqmShow();
				 var heis = parent.document.getElementById("mainframe5").offsetHeight + 200 + "px";
				parent.document.getElementById("mainframe5").style.height = heis;
				break;
			case '删除' :
				if (csid == "") {
					alert("请选择！");
					return;
				}
				if (confirm("确定删除？")) {
					$form = $("form#csform");
					$form.attr("target", "hidden").attr(
							"action",
							basePath + "ea/cs/ea_delPerson.jspa?search="
									+ search + "&pageNumber=" + pNumber);
					$form.find("input#personID").val(csid);
					document.csform.submit.click();
					document.csform.reset();
					$("tr#" + csid).remove();
					csid = "";
					token = 11;
				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/cs/ea_showExcel.jspa?companyID="
						+ ccompanyID + "&companyName=" + companyName;
				open(encodeURI(url));
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/cs/ea_getPerson.jspa?companyID=" + ccompanyID
						+ "&search=" + search;
				numback(url);
				break;
		}

	}

	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/cs/ea_toSearch.jspa?companyID=" + ccompanyID
						+ "&pageNumber=" + pNumber);
		document.SearchForm.submit.click();
	});

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				csid = this.id;
			});
	$("input.JQueryreturn2").click(function() {
				retoken = 0;
				$(".jqmWindow").jqmHide();
				$("#jqModel").jqmShow();
			});
	// 添加所选中的人员staffmap
	$("input#selectPerson").click(function() {
		if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					// 选中一行克隆一行
					select++;
				//	var parms = "";
					// 克隆一行alert()
					$("#kelong").after($("#kelong").clone(true).attr("id",
							"kelong" + select).addClass("checkgoods"));
					// 修改文本框的name
					$("#kelong" + select).find('input').each(function() {
						$(this).attr("name",
								"staffmap[" + select + "]." + this.name);
					});
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "staffID") {
							$("input#staffID", $("#kelong" + select)).attr(
									"value", $(this).text());
						}
						if (this.id == "staffName") {
							$("input#staffName", $("#kelong" + select)).attr(
									"value", $(this).text());
						}

					});
					$(this).attr("checked", false);
				}
			});
			var url = basePath
					+ "ea/cs/ea_addPerson.jspa?companyID=" + ccompanyID + "&companyName="
					+ companyName+"&date="+new Date();
			$("form#myform").attr("action",encodeURI(url));
			document.myform.submit.click();
			document.myform.reset();
			$(".jqmWindow", $("#personForm")).jqmHide();
		} else {
			alert("请选择物品！");
		}
	});
	// 双击选中物品
	$("table#gotable tr[id]").live("click", function(event) {
				var b = $("input.ragood", $(this)).attr("checked");
				$("input.ragood", $(this)).attr("checked", !b);
			});
	// 复选框选中物品
	$(".ragood").live("click", function(event) {
				var b = $(this).attr("checked");
				$(this).attr("checked", !b);
			});
	// 根据输入的物品编号或物品名称查询
	$("input#searchPerson").click(function() {
				cx("");
			});
	// 上一页
	$("#wpsy").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx(typeCN);
				} else {
					alert("已是首页！");
				}
			});
	// 下一页
	$("#wpxy").click(function() {
				var xy = $("#wpxy").attr("title");
				if (xy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					cx(typeCN);
				} else {
					alert("已是尾页！");
				}
			});
	// ajax查询物品列表
	function cx(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy").attr("title", 0);
		$("#wpxy").attr("title", 0);
		$("#wpzy").attr("title", 0);
		var searchurl = basePath + "ea/cs/sajax_ea_getStaffList.jspa?companyID=" + ccompanyID;
		$.ajax({
			url : encodeURI(searchurl + typeCN)+"&"+$("#personForm").serialize()+"&date=" + new Date().toLocaleString(),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#wpsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy").attr("title", dqy + 1);
				}
				$("span#dqycount").text(dqy);
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>人员名称</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>性别</th><th align='center' bgcolor='#E4F1FA'>身份证号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>入账日期</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].staffID + ">";
					tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
							+ pageForm.list[i].staffID + " name='check'/></td>";
					tabletr += "<td id='staffName' align='center'>"
							+ pageForm.list[i].staffName + "</td>";
					tabletr += "<td id='sex'  align='center'>"
							+ pageForm.list[i].sex + "</td>";
					tabletr += "<td id='staffIdentityCard'  align='center'>"
							+ pageForm.list[i].staffIdentityCard + "</td>";
					tabletr += "<td id='verifyTime' align='center'>"
							+ pageForm.list[i].verifyTime + "</td>";
					tabletr += "<td id='staffID' align='center' style='display:none'>"
							+ pageForm.list[i].staffID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02").html(tabletr);
				$("#body_02").show();
				alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
			});
		// //////////////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================END!
});
// 两个function不能放在里面一起 超级大错误
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/cs/ea_getPerson.jspa?companyID=" + ccompanyID
				+ "&pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
