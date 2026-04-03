$(document).ready(function() {
	// 为弹出框准备下拉表内容
	var url = basePath + "ea/cstaff/sajax_n_ea_getSelectLists.jspa?date3="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var codeSexList = member.codeSexList;
					var codeNationalityList = member.codeNationalityList;
					var codeNationList = member.codeNationList;
					var codeNativePlaceList = member.codeNativePlaceList;
					$t = $("table#stafftable");
					$sex = $t.find("select#sex");// 性别拉框
					for (var i = 0; i < codeSexList.length; i++) {
						$op = $("<option/>");
						$op.val(codeSexList[i].codeValue)
								.text(codeSexList[i].codeValue);
						$sex.append($op);
					}
					$nationality = $t.find("select#nationality");// 国籍拉框
					for (var i = 0; i < codeNationalityList.length; i++) {
						$op = $("<option/>");
						$op.val(codeNationalityList[i].codeValue)
								.text(codeNationalityList[i].codeValue);
						$nationality.append($op);
					}

					$nation = $t.find("select#nation");// 民族拉框
					for (var i = 0; i < codeNationList.length; i++) {
						$op = $("<option/>");
						$op.val(codeNationList[i].codeValue)
								.text(codeNationList[i].codeValue);
						$nation.append($op);
					}

					$nativePlace = $t.find("select#nativePlace");// 籍贯拉框
					for (var i = 0; i < codeNativePlaceList.length; i++) {
						$op = $("<option/>");
						$op.val(codeNativePlaceList[i].codeValue)
								.text(codeNativePlaceList[i].codeValue);
						$nativePlace.append($op);
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("#postsjqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '正式员工管理',
				minheight : 80,
				buttons : [{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'mysearch',
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
					name : '工作日志',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '项目工作计划',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '工作目标任务',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},
				/* {
					name : '岗位职责',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},*/ 
				{ 
					name : '调离', //如果将调离方法删除，请将后台涉及到的方法也删除了
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
			case '工作日志' :
				// 工作日志列表
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				personurl = basePath
						+ "ea/logbook/ea_getListLogBook.jspa?pageNumber="
						+ ppageNumber + "&logbook.staffID=";
				$("#mainframe").css({"height":"auto"})
						.attr(
								"src",
								basePath
										+ "ea/logbook/ea_getListLogBook.jspa?pageNumber="
										+ ppageNumber + "&logbook.staffID="
										+ personvalue);
				$(window).resize();
				break;
			case '项目工作计划' :
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				personurl = basePath
						+ "ea/jobplan/ea_getJobPlanList.jspa?pageNumber="
						+ ppageNumber + "&staffID=";
				$("#mainframe").css({"height":"auto"})
						.attr(
								"src",
								basePath
										+ "ea/jobplan/ea_getJobPlanList.jspa?pageNumber="
										+ ppageNumber + "&staffID="
										+ personvalue);
				$(window).resize();
				break;
			case '工作目标任务' :
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				personurl = basePath
						+ "ea/jobtask/ea_getJobTaskList.jspa?pageNumber="
						+ ppageNumber + "&staffID=";
				$("#mainframe").css({"height":"auto"})
						.attr(
								"src",
								basePath
										+ "ea/jobtask/ea_getJobTaskList.jspa?pageNumber="
										+ ppageNumber + "&staffID="
										+ personvalue);
				$(window).resize();
				break;
			case '调离' :
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				if (confirm("确定将所选人员调离本部门？")) {
					var url = basePath
							+ "ea/soincumbent/sajax_n_ea_changeStaffPost.jspa?staffID="
							+ personvalue + "&date=" + new Date();
					$.ajax({

						url : encodeURI(url),

						type : "get",

						async : true,

						dataType : "json",

						success : function cbf(data) {

							var member = eval("(" + data + ")");

							var result = member.result;

							if (result == "success") {

								document.location.reload();
							}else if (result == 'changejsp') {
								var staffname = $("tr#" + personvalue)
										.find("span#staffName").text();
								var url = basePath
										+ "page/ea/main/human/office/production/staff_post.jsp?pageNumber="
										+ ppageNumber + "&staffName="
										+ staffname + "&staffID=" + personvalue+"&pagetype=02";
								url = encodeURI(url);
								document.location.href = url;
							}

						}

					});

				}

				break;
			case '岗位职责' :
				getOrg();
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				personurl = basePath
						+ "ea/responsibilities/ea_getResponsibilitiesList.jspa?pageNumber="
						+ ppageNumber + "&staffID=";
				$("#mainframe").css({"height":"auto"})
						.attr(
								"src",
								basePath
										+ "ea/responsibilities/ea_getResponsibilitiesList.jspa?pageNumber="
										+ ppageNumber + "&staffID="
										+ personvalue);
				$(window).resize();
				break;
			case '修改' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				var url = basePath
						+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
						+ personvalue;
				window
						.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');
				break;
			case '查看' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				var url = basePath
						+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
						+ personvalue;
				window.open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				search = true;
				break;
			case '导出' :
				url = basePath + "ea/cosincumbent/ea_showExcel.jspa?search="
						+ psearch + "&searchValue=searchValue";
				open(url);
				break;
			case '设置每页显示条数' :
				var url  = basePath
						+ "ea/cosincumbent/ea_getStaffList.jspa?search="
						+ psearch + "&searchValue=searchValue";
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				personvalue = this.id;
				opertionID = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + personvalue);
				}
				staffName = $(this).find("span#staffName").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "ea/cosincumbent/ea_toSearchCStaff.jspa?pageNumber="
						+ ppageNumber + "&searchValue=searchValue");
		document.cstaffSearchForm.submit.click();
	});
	$(".JQuerySubmitpost").click(function() {
		$("#postForm").attr("target", "main");
		$("#postForm")
				.attr(
						"action",
						basePath
								+ "ea/responsibilities/ea_saveResponsibilities.jspa?pageNumber="
								+ ppageNumber);
		document.postForm.submit.click();
		alert("保存成功！");
		$("#postsjqModel").jqmHide();

	});
	$("input.JQueryreturnpost").click(function() {// 取消
				$("#postsjqModel").jqmHide();
			});
	$("input.JQueryPrint").click(function() {// 打印预览
				var responsibilitiesID = $("table#posttable")
						.find("#responsibilitiesID").val();
				if (responsibilitiesID.length == 0) {
					alert("请保存后打印！");
					return;
				}
				window
						.open(basePath
								+ "ea/responsibilities/ea_getStaffRespons.jspa?staffResponsibilities.responsibilitiesID="
								+ responsibilitiesID);

			});
	// 查询相关操作END

	// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	//var PID;// 当点新曾时,上一级被选中项的id
	//var rovince;// 被改变的那个的id
	function LiuZhongYaoDeShaGuaDiZhi(address) {
		// 非空验证还原
		$(".notnull").addClass("model3");
		$(".notnull").css("background-color", "#ffffff");
		// 非空验证End
		$td = $("td.JQueryaddress");
		$td.children('select').attr("disabled", false).empty().show();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$('#newdistrict', $td).hide();

		$td = $("td.JQueryaddress");
		$('td.JQueryaddress input[name=changes]').show();
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
					+ "&date2=" + new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var distinctlist = member.distinctlist;
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province", $td).append($op);
							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
			return;
		}
		var urldistrict = basePath
				+ "/ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ DistrictID + "&date01=" + new Date();
		$.ajax({
			url : encodeURI(urldistrict),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var distinctlistSaved = member.distinctlistSaved;
				var list = member.list;
				$select = "<option selected='selected'>--请选择--</option>";
				for (var i = 0; i < distinctlistSaved.length; i++) {
					if (i == 9) {
						return;
					}
					$td.children('select:eq(' + i + ')').empty();
					$td.children('select:eq(' + i + ')').append($select);
					for (var j = 0; j < list[i].length; j++) {
						$op = $("<option />");
						$op.attr("value", list[i][j].districtID).attr("id",
								list[i][j].districtID)
								.text(list[i][j].districtName);
						$td.children('select:eq(' + i + ')').append($op);
					}
					$opp = $("<option  selected='selected'/>");
					$opp.attr("value", distinctlistSaved[i].districtID).attr(
							"id", distinctlistSaved[i].districtID)
							.text(distinctlistSaved[i].districtName);
					$td.children('select:eq(' + i + ')').append($opp);
					$add = "<option class='add'  value = '"
							+ distinctlistSaved[i].districtPID
							+ "' >--新增--</option>";
					$td.children('select:eq(' + i + ')').append($add);
				}
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($select);
				for (var z = 0; z < list[distinctlistSaved.length].length; z++) {
					$op = $("<option />");
					$op
							.attr(
									"value",
									list[distinctlistSaved.length][z].districtID)
							.attr(
									"id",
									list[distinctlistSaved.length][z].districtID)
							.text(list[distinctlistSaved.length][z].districtName);
					$td.children('select:eq(' + distinctlistSaved.length + ')')
							.append($op);
				}
				$addd = "<option class='add'  value = '"
						+ distinctlistSaved[distinctlistSaved.length - 1].districtID
						+ "' >--新增--</option>";
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($addd);
				// num = distinctlistSaved.length -1 ;
				// $td.children('select:gt(' + num + ')').hide();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	}

});
function getOrg() {
	// /////////////////////////////////////

	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date1="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var oList = member.organizationlist;
					var data = new Array();
					data[0] = {
						id : treeID,
						pid : '-1',
						text : treeName
					};
					var data1 = new Array();
					data1[0] = {
						id : treePID,
						pid : '-1',
						text : treePName
					};
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
						data1[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
					$t = $("table#posttable");
					var ts = new TreeSelector(
							$t.find("select#departmentID")[0], data, -1);
					ts.createTree();
					var ts1 = new TreeSelector($t
									.find("select#organizationPID")[0], data1,
							-1);
					ts1.createTree();
					var ts2 = new TreeSelector($t
									.find("select#organizationCID")[0], data1,
							-1);
					ts2.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}