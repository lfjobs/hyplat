$(function() {
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#newdistrict").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '中介调查',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
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
					name : '修改',
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
				intermediaryResearchID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				LiuZhongYaoDeShaGuaDiZhi("");
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (intermediaryResearchID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + intermediaryResearchID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				LiuZhongYaoDeShaGuaDiZhi($p.find("span#address").text());
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (intermediaryResearchID == "") {
					alert('请选择！');
					return
				}
				if (confirm("确定删除？")) {
					$f = $('#cstaffForm');
					$f.find(':input#intermediaryResearchID')
							.val(intermediaryResearchID);
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/intermediaryresearch/ea_delIntermediaryResearch.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.cstaffForm.submit.click();
					$("tr#" + intermediaryResearchID).remove();
					intermediaryResearchID = "";
					token = 11;
				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/intermediaryresearch/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/intermediaryresearch/ea_getIntermediaryResearchList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				intermediaryResearchID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/intermediaryresearch/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.cstaffSearchForm.submit.click();
	});

	$("input.JQuerySubmit").click(function() {// 保存
				if (notoken)
					return;
				notoken = 1;
				$(".notnull").trigger("blur");
				if ($("form .error").length) {
					notoken = 0;
					return;
				}
				var addr = "";
				$(".JQueryaddress").find("select")
						.find("option[value]:selected").each(function() {
							if ($(this).text() != '--新增--'
									&& $(this).text() != '--请选择--')
								addr = addr + $(this).text();
						});

				$td.find("input#companyAddress").val(addr);

				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/intermediaryresearch/ea_saveIntermediaryResearch.jspa?pageNumber="
										+ pNumber);
				if (intermediaryResearchID == "") {
					document.cstaffForm.submit.click();
					$("#cstaffForm").find(":input[name]").val("");
					token = 1;
					return;
				}
				document.cstaffForm.submit.click();
				token = 2;
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$("input.JQueryreturn1").click(function() {// 城市添加取消
				retoken = 0;
				$("#newdistrict").jqmHide();
				$("#jqModel").jqmShow();
			});
	// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	var PID='';// 当点新曾时,上一级被选中项的id
	var rovince='';// 被改变的那个的id
	var districtPID='';
	function LiuZhongYaoDeShaGuaDiZhi(address) {
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$(".notnull").addClass("model3");
		$(".notnull").css("background-color", "#ffffff");
		$(".IdentityCard").css("background-color", "#ffffff");
		// 非空验证End
		$td = $("td.JQueryaddress");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddress");
		$('td.JQueryaddress input[name=changes]').show();
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
					+ "&date01=" + new Date();
			$.ajax({
						url : url,
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
							var distinctlist = member.distinctlist;
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province", $td).append($op);
							}
							retoken = 0;
						},
						error : function cbf(data) {
							retoken = 0;
							alert("数据获取失败！");
						}
					});
			return;
		}
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ encodeURI(DistrictID) + "&date02=" + new Date();
		$.ajax({
			url : urldistrict,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlistSaved = member.distinctlistSaved;
				var list = member.list;
				$select = "<option selected='selected'>--请选择--</option>";
				retoken = 0;
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

	$('td.JQueryaddress select[number]').change(function() {

		if (retoken)
			return;
		retoken = 1;
		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddress");
		rovince = "#" + province;
		$('#newdistrict', $td).hide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		var D = $(rovince, $td).children("option:selected").attr("class");
		if (D == 'add') {
			PID = $(rovince, $td).children("option:selected").val();
			$('#districtNames').attr("title", number).attr("value", "");
			$("#jqModel").jqmHide();
			$("#newdistrict").jqmShow();
			retoken = 0;
			return;
		}
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			if (number != "0") {
				var nu = parseInt(number) - 1;
				districtPID = $("select[number=" + nu + "]", $td).val();
			} else {
				districtPID = "";
			}
			$td.find('input#address').val(districtPID);
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ encodeURI(districtPID) + "&date03=" + new Date();
		$.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlist = member.distinctlist;
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				if (distinctlist.length) {
					for (var i = 0; i < distinctlist.length; i++) {
						$op = $("<option/>");
						$op.attr("value", distinctlist[i].districtID).attr(
								"id", distinctlist[i].districtID)
								.text(distinctlist[i].districtName);
						$(rovince, $td).next().append($op);
					}
				}
				$add = "<option class='add'  value = '" + districtPID
						+ "' >--新增--</option>";
				$(rovince, $td).next().append($add);
				$td.find('input#address').val(districtPID);
				retoken = 0;
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});

	});

	$('input#savedistrict').click(function() {
		if (retoken)
			return;
		retoken = 1;
		$td = $("td.JQueryaddress");
		number = $('input#districtNames').attr('title');
		districtName = $('input#districtNames').val();
		$td.children('select:gt(' + number + ')').empty();
		if ('' == districtName) {
			alert("请填写城市名称");
			retoken = 0;
			return;
		}
		var lastname = districtName.substring(districtName.length - 1,
				districtName.length);
		if (number == 1) {
			var tfname = true;
			var arrname = ["市", "县", "区", "盟"];
			$.each(arrname, function(key, value) {
						if (lastname == value) {
							tfname = false;
						}
					});
			if (tfname) {
				alert("输入错误!请填写以市、县、区、盟为结尾的地址名称");
				$('input#districtNames').val("");
				retoken = 0;
				return;
			}
		}
		if (number == 2) {
			var tfname = true;
			var arrname = ["区", "县", "乡", "镇", "港", "巷", "旗", "路", "环", "街",
					"湾", "号"];
			$.each(arrname, function(key, value) {
						if (lastname == value) {
							tfname = false;
						}
					});
			if (tfname) {
				alert("输入错误!请填写以区, 县,乡,镇,港,巷,旗,路,环,街为结尾的地址名称");
				$('input#districtNames').val("");
				retoken = 0;
				return;
			}

		}
		$("#newdistrict").jqmHide();
		$("#jqModel").jqmShow();
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_saveDistrict.jspa?district.districtPID="
				+ encodeURI(PID) + "&district.districtName="
				+ encodeURI(districtName) + "&date04=" + new Date();
		$.ajax({
			url : urldistrict,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var sdistrict = member.sdistrict;
				$op1 = $("<option selected='selected'/>").attr("value",
						sdistrict.districtID).attr("id", sdistrict.districtID)
						.text(sdistrict.districtName);
				$("#" + sdistrict.districtID, $td).remove();
				$(rovince, $td).append($op1);
				districtPID = sdistrict.districtID;
				//var params = {
				//	"districtPID" : districtPID
				//};
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				$add = "<option class='add'  value = '" + districtPID
						+ "' >--新增--</option>";
				$(rovince, $td).next().append($add);
				$td.find('input#address').val(districtPID);
				retoken = 0;
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});

	});
		// 保存新地址...............
		// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================END!
});

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/intermediaryresearch/ea_getIntermediaryResearchList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}