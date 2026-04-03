$(function() {
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
				title : '公司正式员工汇总',
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
				break;
			case '导出' :
				url = basePath
						+ "ea/soincumbent/ea_showcompanyExcel.jspa?search="
						+ ssearch;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/soincumbent/ea_getCompanyListForIncumbent.jspa?search="
						+ ssearch;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModelSearch").jqmHide();
			});
	$(".JQueryflexme tr[id]").click(function() {
				personvalue = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + personvalue);
				}
				staffName = $(this).find("span#staffName").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/soincumbent/ea_toSearchcompanyCStaff.jspa?pageNumber="
								+ pNumber);
		document.getElementById("cstaffSearchForm").submit();
		$("#cataffSearchTable").find(":input[name]").val("");
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
		// 设置Ajax事件同步调用
		$.ajaxSetup({
					async : false
				});
		$td = $("td.JQueryaddress");
		$('td.JQueryaddress input[name=changes]').show();
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
					+ '0' + "&date04=" + new Date();
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
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ DistrictID + "&date03=" + new Date();
		$.ajax({
			url : encodeURI(url),
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
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	}
});
$(function() {
			// 为弹出框准备下拉表内容
			var url = basePath
					+ "ea/cstaff/sajax_n_ea_getSelectLists.jspa?date02="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
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
							alert("数据获取失败1！");
						}
					});
		});
$(function() {

			var url = basePath
					+ "ea/company/sajax_n_ea_getCompanyList.jspa?date01="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var companylist = member.companylist;
							var data1 = new Array();

							data1[0] = {
								id : treeid,
								pid : '-1',
								text : treename
							};
							for (var i = 0; i < companylist.length; i++) {
								data1[i + 1] = {
									id : companylist[i].companyID,
									pid : companylist[i].companyPID,
									text : companylist[i].companyName
								};
							}
							var ts3 = new TreeSelector($("#companyID")[0],
									data1, -1);
							ts3.createTree();
						},

						error : function cbf(data) {
							alert("机构数据获取失败！");
						}
					});

		});
$(document).ready(function() {
	// 图片预览
	$('#staffphoto').change(function() {
				$t = $("table#stafftable");
				$t.find('img#photo').attr("src", this.value).show();
			});
		// 图片预览END
	});