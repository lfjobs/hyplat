$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '车辆基本信息',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
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
			case '添加' :
				carID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				$("span#companyNames").text(treeNames);
				$("span#dept").text(treePName);
				$("#jqModel").jqmShow();
				break;
			case '查看' :
				if (carID == "") {
					alert("请选择");
				}
				var url = basePath
						+ "ea/carbaseinfo/ea_getCarBaseInfo.jspa?carInformation.carID="
						+ carID;
				window
						.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');
				break;
			case '查询' :
				$("#jqModelcarSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/car/ea_showExcel.jspa?search=" + search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carbaseinfo/ea_getCarBaseInfoList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	
		// 添加取消	
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});

	// 双击事件
	$(".JQueryflexme tr[id]").dblclick(function() {
				carID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});

	// 单击事件
	$(".JQueryflexme tr[id]").click(function() {
				carID = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + carID);
				}
				carNums = $(this).find("span#carNum").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 搜索窗口
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});

	// 查询事件
	$("#searchCar").click(function() {
		$("#carSearchForm").attr(
				"action",
				basePath + "ea/carbaseinfo/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.carSearchForm.submit.click();
	});

});

// 查询部门事件
$(function() {
	var url = basePath
			+ "ea/corganization/sajax_ea_getOrganizationLists.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"organizationID" : companyID
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var organizationList = member.organizationList;
					var str = "<option value=''>请选择</option>";
					for (var i = 0; i < organizationList.length; i++) {
						var obj = organizationList[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#organizationID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	// 根据公司ID和部门ID查询责任人
	$("#organizationID").change(function() {
		$("option", $("select#person")).remove();
		var organizationIDs = $("select#organizationID  option:selected").val();
		var url = basePath
				+ "ea/car/sajax_ea_getStaffList.jspa?carInformation.organizationID="
				+ organizationIDs;
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var persons = member.stafflist;
						var str = "<option value=''>全部</option>";
						for (var i = 0; i < persons.length; i++) {
							var obj = persons[i];
							str += "<option value='" + obj[0] + "'>" + obj[1]
									+ "</option>";
						}
						$("#person").html(str);
					}
				});
	});

	
	// 添加保存
	$("input.JQuerySubmit").click(function() {
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		if ($("#engineNum", $("#cstaffForm")).text() == '') {
			alert("请选择车辆！");
			return;
		}
		if (carID == "") {
			$("#cstaffForm").attr("target", "hidden").attr(
					"action",
					basePath + "ea/car/ea_saveCarInformation.jspa?pageNumber="
							+ pNumber + "&search=" + search);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 2;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/car/ea_saveCarInformation.jspa?pageNumber="
						+ pNumber + "&search=" + search);
		document.cstaffForm.submit.click();
		token = 2;

	});



	// 物品返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				var numes = "";
				$("#carNum", $("table#searchgood")).attr("value", numes);
				$("#carFrameNum", $("table#searchgood")).attr("value", numes);
				$("#carType", $("table#searchgood")).attr("value", numes);
				$(":input#parms").attr("value", numes);
				$(".jqmWindow", $("#goodsForm")).jqmHide();
			});

	$("#DaoRuFan").click(function() {// 返回
				$("#bankJqm").jqmHide();
			});

	$("#DaoRuFanqd").click(function() {// 选择确定
				var checkopertionID = $("#checkopertionID", $("#bankJqm"))
						.attr("value");
				var checkform = $("#checkform", $("#bankJqm")).attr("value");
				var checkopertionName = $("#checkopertionName", $("#bankJqm"))
						.attr("value");
				var childopertionName = $("#childopertionName", $("#bankJqm"))
						.attr("value");
				var childopertionID = window.frames["daoRu"].opertionID;
				if (childopertionID == "") {
					alert("请选择")
					return;
				}
				var no = window.frames["daoRu"].$('tr#' + childopertionID)
						.find("span#" + checkopertionName).text();
				var childopertionName = window.frames["daoRu"].$('tr#'
						+ childopertionID).find("span#" + childopertionName)
						.text();
				if (checkopertionID != "")
					$("#" + checkopertionID, $("#" + checkform)).attr("value",
							childopertionID).trigger("blur");
				if (checkopertionName != "") {
					$("#" + checkopertionName, $("#" + checkform)).attr(
							"value", childopertionName).trigger("blur");
				}
				if (checkopertionName == "staffName") {
					var final = no + "---" + childopertionName;
					$("#" + checkopertionName, $("#" + checkform)).attr(
							"value", final).trigger("blur");
				}
				$("#daoRu").attr("src", "");
				$("#bankJqm").jqmHide();
			});
});
// 刷新事件
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/carbaseinfo/ea_getCarBaseInfoList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

function importGY(attachSearchTable, checkopertionID, checkopertionName,
		childopertionName, url) { // 打开页面
	if (checkopertionName == "bankNum") {
		var departmentID = $("#departmentID").attr("value");
		url = url + "?departmentID=" + departmentID + "&title1=title1";
	}
	url = url + "?title1=title1";
	$("#checkopertionID", $("#bankJqm")).attr("value", checkopertionID);
	$("#checkform", $("#bankJqm")).attr("value", attachSearchTable);
	$("#checkopertionName", $("#bankJqm")).attr("value", checkopertionName);
	$("#childopertionName", $("#bankJqm")).attr("value", childopertionName);
	$("#daoRu").attr("src", basePath + url);
	$("#bankJqm").jqmShow();
}

/** **********************************选择物品**************************************** */
$(document).ready(function() {
	var cuID = "";
	$("table#gotable tr[id]").live("click", function(event) {
				cuID = this.id;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 导入数据（选择物品）
		$("#newG").click(function() {
				cx("");
				$(".jqmWindow", $("#goodsForm")).jqmShow();
			});
	// 根据车牌号查询
	$("#chaxun").click(function() {
				var typeName = $("#carNum", $("table#searchgood")).val();
				var typeJia=$("#carFrameNum", $("table#searchgood")).val();
				var typeType=$("#carType", $("table#searchgood")).val();
				$(":input#parms").val("parameter=" + typeName+"&typeJia="+typeJia+"&typeType="+typeType);
				cx("parameter=" + typeName+"&typeJia="+typeJia+"&typeType="+typeType);
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
		$("#qdcar").click(function() {
		if (cuID != "") {
			$("tr #" + cuID).children("td").each(function() {
				if (this.id == "contactUserID") {
					$("input#contactUserID", $("table#stafftable")).val($(this)
							.text());
				} else {
					$("span#" + this.id, $("table#stafftable"))
							.text($(this).text());
				}
			});
			cuID="";
			$(".jqmWindow", $("#goodsForm")).jqmHide();
		} else {
			alert("请选择车辆！");
		}
	});
	
	function cx(typeCN){
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy").attr("title", 0);
		$("#wpxy").attr("title", 0);
		$("#wpzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/car/sajax_ea_getcarnull.jspa?";
		$.ajax({
			url : encodeURI(searchurl+ typeCN+ "&date="
					+ new Date().toLocaleString()),
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
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车牌号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>发动号</th><th align='center' bgcolor='#E4F1FA'>车辆类型</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车架号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>注册日期</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].carID + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
							+ pageForm.list[i].carID + " name='check'/></td>";
					tabletr += "<td id='carNum' align='center'>"
							+ pageForm.list[i].carNum + "</td>";
					tabletr += "<td id='engineNum'  align='center'>"
							+ pageForm.list[i].engineNum + "</td>";
					tabletr += "<td id='carType'  align='center'>"
							+ pageForm.list[i].carType + "</td>";
					tabletr += "<td id='carFrameNum' align='center'>"
							+ pageForm.list[i].carFrameNum + "</td>"	;
					tabletr += "<td id='registrationDate'  align='center'>"
							+ pageForm.list[i].registrationDate + "</td>";
					tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
							+ pageForm.list[i].carID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02").html(tabletr);
				$("#body_02").show();
				
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}

});