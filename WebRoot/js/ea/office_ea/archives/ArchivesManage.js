$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	var title = "";
	var titlepart = "";
	var forepart = "";
	if (type == "company" || type == "group") {
		
		if (type == "company") {
			titlepart="公司汇总";
		} else {
			titlepart = "集团汇总";
		}
		if(catemodule=="01"){
			forepart = "劳动合同";
		}if(catemodule=="02"){
			forepart="实习协议"
		}if(catemodule=="03"){
			forepart="劳务协议";
		}
		title = forepart+titlepart;
		$(".hidtr").hide();
		$('.JQueryflexme').flexigrid({
			height : 350,
			width : 'auto',
			minwidth : 30,
			title : title,
			minheight : 80,
			buttons : [{
				name : '查询',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '打印预览',
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
			}, {
				name : '条码：<input size ="15" type="text" id="bc"  onblur="queryByBarCode();"/>',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
			}, {
				separator : true
			}]
		});
	} else {
		if (catemodule != "global") {
			if (catemodule == "01" || catemodule == "02" || catemodule == "03") {
				if (catemodule == "01") {
					title = "劳动合同档案";
				} else if (catemodule == "02") {
					title = "实习协议档案";
				} else {
					title = "劳务协议档案";
				}
				$("td.hidtd").hide();
				$("tr.member").show();
				$("tr.student").hide();
				flexigrid3(title)
			} else {
				if (catemodule == "theory") {
					title = "理论档案管理";
				}
				if (catemodule == "piletest") {
					title = "桩考档案管理";
				}
				if (catemodule == "yard") {
					title = "场地档案管理";
				}
				if (catemodule == "roadtest") {
					title = "路考档案管理";
				}

				$(".uploadfile").hide();
				$("tr.member").hide();
				$("tr.student").show();
				flexigrid3(title);
				$(".hidtd").show();
			}
			$(".hidtr").hide();

		} else {
			flexigrid2("档案管理");

			$(".hidtr").show();
			$(".hidtd").hide();
			$("tr.member").hide();
			$("tr.student").hide();
		}

	}
	//暂时未使用
	function flexigrid1(title) {
		$('.JQueryflexme').flexigrid({
			height : 350,
			width : 'auto',
			minwidth : 30,
			title : title,
			minheight : 80,
			buttons : [{
				name : '修改',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '出库',
				bclass : 'checkout',
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
				name : '盘点',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '存储位置管理',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '打印预览',
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
			}, {
				name : '条码：<input size ="15" type="text" id="bc"  onblur="queryByBarCode();"/>',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
			}, {
				separator : true
			}]
		});
	}
	function flexigrid2(title) {
		$('.JQueryflexme').flexigrid({
			height : 350,
			width : 'auto',
			minwidth : 30,
			title : title,
			minheight : 80,
			buttons : [{
				name : '入库',
				bclass : 'add',
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
				name : '作废',
				bclass : 'delete',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '出库',
				bclass : 'checkout',
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
				name : '盘点',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '档案类别管理',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '存储位置管理',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '打印预览',
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
			}, {
				name : '条码：<input size ="15" type="text" id="bc"  onblur="queryByBarCode();"/>',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
			}, {
				separator : true
			}]
		});
	}
	function flexigrid3(title) {
		$('.JQueryflexme').flexigrid({
			height : 350,
			width : 'auto',
			minwidth : 30,
			title : title,
			minheight : 80,
			buttons : [{
				name : '入库',
				bclass : 'add',
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
				name : '作废',
				bclass : 'delete',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '出库',
				bclass : 'checkout',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '续签',
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
				name : '盘点',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '存储位置管理',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '打印预览',
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
			}, {
				name : '条码：<input size ="15" type="text" id="bc"  onblur="queryByBarCode();"/>',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
			}, {
				separator : true
			}]
		});
	}
	function action(com, grid) {
		switch (com) {
			case '入库' :
				document.postEnterForm.reset();
				$(".disable").attr("disabled", false);
				$("#postEnterForm #upload_content tr:gt(0)").remove();
				$(".JQuerypersonvalue").attr("checked", false);
				$("form .error").remove();
				$("form .corect").remove();
				$("#jqModelEnter #loadcab")
						.attr(
								"src",
								basePath
										+ "page/ea/main/office_ea/archives/loadActiveX.html?code="
										+ Math.random());
				$("#postEnterForm div#title").text("入库");
				$("#jqModelEnter").jqmShow();
				$("tr.exist").hide();

				break;

			case '修改' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var historyID = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						historyID = checkinput[i].value;
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				if (length > 1) {
					alert("只能修改一个");
					return;
				}
				if (!IsCanUpdate(historyID)) {
					alert("已出库或已作废不可修改");
					return;
				}

				document.postEnterForm.reset();
				$("form .error").remove();
				$("form .corect").remove();
				$t = $("table#entertable");
				$p = $("tr#" + historyID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
							
						    if(this.id=="staffname"){
						    	
						    	$("#staffname2").val($(this).text());
						    }
						});

				$t.find("input#historyIDss").val(historyID);
				$("#postEnterForm select#security option[value="
						+ $p.find("span[id=securitylevel]").text() + "]").attr("selected",
						"selected");
				$("#postEnterForm select#location option[value="
						+ $p.find("span[id=locationid]").text() + "]").attr("selected",
						"selected");

				$t.find("input#oldchipids").val($t.find("input#chipids").val());
				
				
				
				var url = basePath
						+ "ea/archive/sajax_ea_getUpdateView.jspa?date="
						+ new Date();
						
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					data : {
						historyID : historyID
					},
					success : function(data) {
						var member = eval("(" + data + ")");
						var attachlist = member.attachlist;
		

						// 显示附件
						$("#postEnterForm  #exitfile tr:gt(0)").remove();
						for (var i = 0; i < attachlist.length; i++) {
							var obj = attachlist[i];
							var filepath = '"' + obj.filepath + '"';
							var attachmentid = '"' + obj.attachmentid + '"';
							$("#postEnterForm #exitfile")
									.html("<tr id='"
											+ obj.attachmentid
											+ "'><td title='"
											+ obj.filename
											+ "'><a href='javascript:load("
											+ filepath
											+ ");'>"
											+ obj.filename
											+ "</a></td><td align='center'>上传成功</td><td align='center'>100%</td><td align='center'><img style='cursor:hand;'src='"
											+ basePath
											+ "swfupload/del.gif' onclick='deleteAttach("
											+ attachmentid + ");'></td></tr>");
						}

					},
					error : function(data) {
						alert("读取失败");
					}

				});
				if ($("#postEnterForm #exitfile tr").length == 0) {
					$("tr.exist").hide();
				} else {
					$("tr.exist").show();
				}

				if (!IsUpdateEndge(historyID)) {// 可修改部分
					$(".disable").attr("disabled", true);
					$(".uploadfile").hide();

				} else {
					$(".disable").attr("disabled", false);
				}
				$("#postEnterForm div#title").text("修改");
				$("#jqModelEnter").jqmShow();
				break;
			case '删除' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var values = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						values += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}

				if (confirm("确定删除")) {
					var url = basePath
							+ "ea/archive/sajax_ea_deleteArchive.jspa?date="
							+ new Date();
					$.ajax({
								url : url,
								type : "get",
								async : false,
								dataType : "json",
								data : {
									historyIDs : values
								},
								success : function(data) {
									var member = eval("(" + data + ")");
									var result = member.result;
									if (result == "") {
										alert("删除成功");

									} else {
										alert("编号：" + result + "不能删除");
									}
									token = 1;
									re_load();

								},
								error : function(data) {

								}
							});
				}
				break;
			case '作废' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var values = "";
				var novalues = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						$t = $("tr#" + checkinput[i].value);
						var state = $t.find("span[id=state]").text();
						if (state == "在库") {
							values += checkinput[i].value + ",";
						} else {
							novalues += $t.find("span[id=archiveCode]").text()
									+ ",";
						}
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				if (novalues != "" && values == "") {
					alert("编号:" + novalues + "已出库不能作废");
					return;
				}

				if (confirm("确定作废")) {
					var url = basePath
							+ "ea/archive/sajax_ea_obsoleteArchive.jspa?date="
							+ new Date();
					$.ajax({
								url : url,
								type : "get",
								async : false,
								dataType : "json",
								data : {
									historyIDs : values
								},
								success : function(data) {
									if (novalues != "") {
										alert("编号:" + novalues + "已出库不能作废");
									} else {
										alert("操作成功");
									}
									token = 1;
									re_load();

								},
								error : function(data) {

								}
							});
				}
				break;
			case '打印预览' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var historyIDs = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						historyIDs += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#postOutDetailForm #printtype").val("库存单");
				$("#postOutDetailForm #historyIDs").val(historyIDs);
				$("#postOutDetailForm").attr("target", "_blank").attr(
						"action",
						basePath + "ea/archive/ea_toPrintArchive.jspa?dats="
								+ new Date());
				document.postOutDetailForm.submit.click();

				break;
			case '查询' :
				$("#jqModelEnter #loadcab")
						.attr(
								"src",
								basePath
										+ "page/ea/main/office_ea/archives/loadActiveX.html?code="
										+ Math.random());
				$("#jqModelSearch").jqmShow();
				break;
			case '续签' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var historyIDs = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						historyIDs += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#postSignForm #historyids").val(historyIDs);
				$("#jqModelSign").jqmShow();
				break;
			case '盘点' :
				document.location.href = basePath
						+ "ea/check/ea_getCheckList.jspa?type=" + type
						+ "&dat=" + new Date;
				break;
			case '出库' :

				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var historyIDs = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						historyIDs += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				var url = basePath
						+ "ea/archive/sajax_ea_getOutLibraryInfoList.jspa?date="
						+ new Date();
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					data : {
						historyIDs : historyIDs
					},
					success : function(data) {
						document.postOutDetailForm.reset();
						var member = eval("(" + data + ")");
						var archivelist = member.archivelist;
						if (archivelist.length != 0) {
							var archive = archivelist[0];
							$("#baseInfo #companyName")
									.text(archive.companyname);
							$("#baseInfo #organization")
									.text(archive.org);
							$("#baseInfo #inuser").text(archive.inusername);
							$("#baseInfo #outtimestr").text(archive.outtimestr);

							var obj;
							var str = "";
							for (var i = 0; i < archivelist.length; i++) {
								obj = archivelist[i];
								var historyID = '"' + obj.historyID + '"';
								var security = "";
								if(obj.securitylevel=='01'){
									security = "一级";
								}else if(obj.securitylevel=='02'){
									security = "二级";
								}else{
									security = "三级";
								}
								str += "<tr id='"
										+ obj.historyid
										+ "'><td align='center' title='"
										+ obj.archivecode
										+ "'>"
										+ obj.archivecode
										+ "</td>"
										+ "<td align='center' title='"
										+ obj.name
										+ "'>"
										+ obj.name
										+ "</td>"
										+ "<td align='center' title='"
										+ obj.categroyname
										+ "'>"
										+ obj.categroyname
										+ "</td>"
										+ "<td align='center' title='"
										+ obj.barcode
										+ "'>"
										+ obj.barcode
										+ "</td>"
										+ "<td align='center' title='"
										+ obj.chipid
										+ "'>"
										+ obj.chipid
										+ "</td>"
										+ "<td align='center' title='"
										+ security
										+ "'>"
										+ security
										+ "</td>"
										+ "<td align='center' title='"
										+ obj.inusername
										+ "'>"
										+ obj.inusername
										+ "</td>"
										+ "<td align='center' title='"
										+ obj.intimestr
										+ "'>"
										+ obj.intimestr
										+ "</td>"
										+ "<td align='center' title='"
										+ obj.locationname
										+ "' >"
										+ obj.locationname
										+ "</td>"
										+ "<td align='center' ><img src="
										+ basePath
										+ "images/admin_images/Delete.gif onclick='deleteTr("
										+ historyID + ");'/></td></tr>";

							}
							$("#tbody").html(str);
						}
						$("#jqModelOutDetail").jqmShow();

					},
					error : function(data) {
						alert("读取失败");
					}

				});

				break;
			case '档案类别管理' :
				document.location.href = basePath
						+ "ea/catalogue/ea_getCatalogueList.jspa?type=" + type
						+ "&dat=" + new Date;
				break;
			case '存储位置管理' :
				document.location.href = basePath
						+ "ea/location/ea_getLocationList.jspa?type=" + type
						+ "&dat=" + new Date;
				break;

			case '设置每页显示条数' :
				pNumber = prompt("输入显示条数", "请输入小于50正整数");
				if (pNumber < 0 || pNumber != parseInt(pNumber) || pNumber > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				document.location.href = basePath
						+ "ea/archive/ea_getArchiveList.jspa?search=" + search
						+ "&type=" + type
						+ "&pageNumber=" + pNumber + "&extensionStaffCoach="
						+ extensionStaffCoach + "&cstaff.staffID=" + studentID;
				break;
		}
	}
	getCatalogueAndLocation(type);

	$("#toCancel").click(function() {// 取消
				$("#jqModelEnter").jqmHide();

			});

	$("#toCancelOut").click(function() {// 取消
				$("#jqModelOut").jqmHide();

			});
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/archive/ea_toSearch.jspa?pageNumber=" + pNumber
						+ "&extensionStaffCoach=" + extensionStaffCoach
						+ "&cstaff.staffID=" + studentID);
		document.postSearchForm.submit.click();
	});

	$(".readchipid").click(function() {

		$("#jqModelEnter #loadcab")
				.attr(
						"src",
						basePath
								+ "page/ea/main/office_ea/archives/loadActiveX.html?code="
								+ Math.random());

	});
	document.onkeydown = function(evt) {// 捕捉回车
		evt = (evt) ? evt : ((window.event) ? window.event : ""); // 兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
		var activeElementId = document.activeElement.id;// 当前处于焦点的元素的id
		if (key == 13 && activeElementId == "bc") {
			queryByBarCode();// 要触发的方法
		}

	};

	$(".JQueryflexme tr").toggle(function() {
				historyID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");

			}, function() {
				historyID = this.id;
				$("input.JQuerypersonvalue", $(this)).attr("checked", false);
			});

	// 用于阻止复选框的冒泡行为；
	$("input.JQuerypersonvalue").click(function(event) {
				event.stopPropagation();

			});

	// 判断芯片重复用的事件
	$("form .chip").bind("blur", function() {
		$input = $(this);
		$parent = $input.parent();
		var inputValue = $input.attr("value");
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($input.is(".chip")) {
			if (inputValue != "") {
				if ($("#postEnterForm oldchipids").val() != inputValue) {
					if (!checkChip(inputValue)) {
						$parent
								.append("<span class=\"error\"><a class=\"tex\">芯片号已使用</a></span>");
						return;
					} else {
						$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
						return;
					}
				}
			} else {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
			}
		}
	});

	// 提交出库
	$("#toOutDetail").click(function() {

		var outuserid = $("#postOutDetailForm #outuserid").val();
		if (outuserid == "") {
			alert("请选择出库接收人");
			return;
		}
		var outstatus = "";
		if (catemodule != "global" && catemodule != "01" && catemodule != "02"
				&& catemodule != "03") {
			outstatus = $("#outstatus option:selected").val();
		}
		var historyIDs = "";
		$("#tbody").find("tr").each(function() {
					historyIDs += $(this).attr("id") + ",";

				});
		if (historyIDs == "") {
			alert("没有选择档案");
			return;
		}
		var outtimestr = $("#baseInfo #outtimestr").text();
		var url = basePath + "ea/archive/sajax_ea_OutLibrary.jspa?date="
				+ new Date();
		$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					data : {
						historyIDs : historyIDs,
						outuserid : outuserid,
						outtimestr : outtimestr,
						outstatus : outstatus
					},
					success : function(data) {
						var member = eval("(" + data + ")");
						var result = member.result;
						if (result == "") {
							alert("操作成功");
						} else {
							alert("编号:" + result + "已在库中");
						}
						token = 1;
						re_load();
					},
					error : function(data) {
						alert("读取失败");
					}

				});

	});
	// 提交入库
	$("#toEnter").click(function() {
		$(".put3").trigger("blur");
		$("#postEnterForm .chip").trigger("blur");
		if (catemodule != "global") {
			if ($("div#title").text() == "修改") {
				if ($("#postEnterForm.error").length) {
					return;
				}
			} else {
				if ($("#postEnterForm .error").length > 1) {

					return;
				}
			}
		} else {
			if (($("form .error").length) >2) {

				return;
			}
		}
		if ($("#divFileProgressContainer tr").length > 0) {
			uploads();
		} else {
			$("#postEnterForm").attr("target", "hidden").attr(
					"action",
					basePath + "ea/archive/ea_addArchives.jspa?pageNumber="
							+ pNumber + "&search=" + search);
			document.postEnterForm.submit.click();
			//document.postEnterForm.reset();
			$("#divFileProgressContainer").empty();
			var checkinput = document.getElementsByName("checkinput");
			var length = 0;
			var historyID = "";
			for (var i = 0; i < checkinput.length; i++) {
				if (checkinput[i].checked) {
					length += 1;
					historyID = checkinput[i].value;
				}
			}
			if (historyID == "") {
				token = 1;
			} else {
				token = 2;
			}
			return;
		}

	});

	$("#toSubmit").click(function() {// 提交位置信息

				if ($("#postAddForm #locationname").val() == "") {
					alert("请添写名称");
					return;
				}
				var url = basePath
						+ "ea/location/sajax_ea_addLocation.jspa?date="
						+ new Date().toLocaleString();
				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : false,
							dataType : "json",
							data : {
								locationname : $("#postAddForm #locationname")
										.val()
							},
							success : function cbf(data) {

								$("#jqModelAdd").jqmHide();
								getCatalogueAndLocation(type);
								$("#jqModelEnter").jqmShow();

							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
						});
				return;
			});

	// 续签提交
	$("#contractSubmit").click(function() {

		$("#postSignForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/archive/ea_renewalDate.jspa?pageNumber="
						+ pNumber);
		document.postSignForm.submit.click();
		token = 2;

	});
	$("#jqModelAdd #toCancell").click(function() {
				$("#jqModelAdd").jqmHide();

			});

	// 打印预览
	$(".JQueryPrint").click(function() { // 打印预览

				var historyIDs = "";
				$("#tbody").find("tr").each(function() {
							historyIDs += $(this).attr("id") + ",";

						});
				if (historyIDs == "") {
					alert("无打印内容");
					return;
				}
				$("#postOutDetailForm #printtype").val("出库单");
				$("#postOutDetailForm #historyIDs").val(historyIDs);
				$("#postOutDetailForm").attr("target", "_blank").attr(
						"action",
						basePath + "ea/archive/ea_toPrintArchive.jspa?dats="
								+ new Date());
				document.postOutDetailForm.submit.click();
			});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/archive/ea_getArchiveList.jspa?pageNumber=" + pNumber
				+ "&type=" + type
				+ "&extensionStaffCoach=" + extensionStaffCoach
				+ "&cstaff.staffID=" + studentID;
}

function getCatalogueAndLocation(type) {
	var url = basePath
			+ "ea/archive/sajax_ea_getCatalogueAndLocation.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : false,
		dataType : "json",
		data : {
			type : type
		},
		success : function cbf(data) {
			/** **添加类别列表** */
			var member = eval("(" + data + ")");
			var locationlist = member.locationlist;
			var cataList = member.cataloguelist;
			$("#postEnterForm #location").empty();
			$("#postSearchForm #location").empty();
			$("#postEnterForm #catalogue").empty();
			$("#postSearchForm #catalogue").empty();

			var str = "<option value=''>请选择存储位置</option>";
			// 处理存储位置
			for (var i = 0; i < locationlist.length; i++) {
				var bj = locationlist[i];
				str += "<option value=" + bj.locationid + ">" + bj.locationname
						+ "</option>";
			}
			$("#postEnterForm #location").html(str);
			$("#postSearchForm #location").html(str);

			// 处理档案类别
			var data = new Array();
			data[0] = {
				id : "",
				pid : '-1',
				text : '请选择档案类别'
			};
			for (var i = 0; i < cataList.length; i++) {
				data[i + 1] = {
					id : cataList[i].archiveid,
					pid : cataList[i].parent,
					text : cataList[i].name
				};
			}

			ts2 = new TreeSelector($("#postSearchForm #catalogue")[0], data, -1);
			ts2.createTree();

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

}

// 查看附件并提供下载
function showAttach(archiveid) {
	var url = basePath + "ea/archive/sajax_ea_getAttachList.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					archiveid : archiveid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var attachlist = member.attachlist;
					var str = "<tr><th>文件名</th><th>操作</th></tr>";
					for (var i = 0; i < attachlist.length; i++) {
						var obj = attachlist[i];
						var filepath = '"' + obj.filepath + '"';
						str += "<tr><td align='left'>" + obj.filename
								+ "</td><td><a href='javascript:load("
								+ filepath + ");'>下载</a></td></tr>";
					}
					$("#showattach").html(str);
				},
				error : function(data) {
					alert("读取数据失败");
				}

			});
	$("#jqModelAttach").jqmShow();
}
// 附件下载
function load(path) {
	window.open(basePath + "/servlets/render?filename=" + path);
}
// 删除修改时的附件
function deleteAttach(attachmentid) {
	var url = basePath + "ea/archive/sajax_ea_deleteAttach.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					attachmentid : attachmentid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "suc") {
						$("#postEnterForm #exitfile tr#" + attachmentid)
								.remove();
						if ($("#postEnterForm #exitfile tr") == 0) {
							$("tr.exist").hide();
						}
					}
				},
				error : function(data) {

				}

			});
}

// 追踪
function trackArchive(historyID) {
	var url = basePath + "ea/archive/sajax_ea_getTrackList.jspa?date="
			+ new Date();
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data : {
			historyID : historyID
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var historyList = member.historyList;
			var str = "<tr><th>序号</th><th>时间</th><th>操作</th><th>接收人</th></tr>";
			var num = 1;
			for (var i = 0; i < historyList.length; i++) {
				var obj = historyList[i];

				str += "<tr><td align='center'>" + num
						+ "</td><td align='center'>" + obj.intimestr
						+ "</td><td align='center'>入库</td><td align='center'>"
						+ obj.inuser + "</td></tr>";
				if (obj.outuser != "") {
					num++;
					str += "<tr><td align='center'>"
							+ num
							+ "</td><td align='center'>"
							+ obj.outtimestr
							+ "</td><td align='center'>出库</td><td align='center'>"
							+ obj.outuser + "</td></tr>";
				}
				num++;
			}

			$("#postTrackForm #tracktable").html(str);
		},
		error : function(data) {
			alert("读取数据失败");
		}

	});

	$("#jqModelTrack").jqmShow();
}

// 选择人员
function importGY(url) { // 打开页面
	$("#daoRu")
			.attr("src", basePath + url + "?date=" + new Date() + "&hid=hid");

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
			
   var recordCode = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#recordCode").text();

	$("#postOutDetailForm #outusername2").val(staffName);
	$("#postOutDetailForm #outuserid").val(childopertionID);

	$("#postEnterForm #staffname2").val(staffName);
	$("#postEnterForm #staffID").val(childopertionID);
    $("#postEnterForm #archiveCode").val(recordCode);
    
    $("#postEnterForm #stuname").val(staffName);


	$("#postSearchForm #staffname").val(staffName);
	$("#postSearchForm #staffID").val(childopertionID);

	var name = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#name").text();
	$("#postEnterForm #catalogue").val(name);
	$("#postEnterForm #catalogueid").val(childopertionID);

	$("#postEnterForm select#catalogue option[value=" + childopertionID + "]")
			.attr("selected", "selected");
	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}

// 快速条码查询
function queryByBarCode() {
	$("#postSearchForm #barcodes").val($("#bc").val());
	$("#postSearchForm").attr(
			"action",
			basePath + "ea/archive/ea_toSearch.jspa?pageNumber="
					+ pNumber);
	document.postSearchForm.submit.click();
}
// 暂时停用
function mydate() {
	var myDate = new Date();
	var month = (myDate.getMonth() + 1) < 10
			? ("0" + (myDate.getMonth() + 1))
			: (myDate.getMonth() + 1);
	var day = myDate.getDate() < 10 ? ("0" + myDate.getDate()) : myDate
			.getDate();
	var hour = myDate.getHours();
	var minites = myDate.getMinutes();
	var seconds = myDate.getSeconds();
	$("#outtime", $("#postOutForm ")).val(myDate.getFullYear() + "-" + month
			+ "-" + day + " " + hour + ":" + minites + ":" + seconds);
}

function addLocation() {
	$("#jqModelAdd").jqmShow();
};

// 删除出库信息
function deleteTr(historyID) {

	$("#tbody tr#" + historyID).remove();
}

// 判断是否可以修改
function IsCanUpdate(historyID) {

	var bool = true;
	// var fail;
	var url = basePath + "ea/archive/sajax_ea_IsCanUpdate.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					historyID : historyID
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "fail") {
						bool = false;
					} else {
						bool = true;
					}

				},
				error : function(data) {
					alert("读取数据失败");
				}

			});

	return bool;

}

// 判断芯片号是否重复
function checkChip(chipid) {
	if ($("#postEnterForm #oldchipids").val() == chipid) {
		return true;
	}
	var bool = null;
	var url = basePath + "ea/archive/sajax_ea_IsChipRepeat.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					chipid : chipid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "fail") {// 重复
						bool = false;
					} else {
						bool = true;// 不重复
					}

				},
				error : function(data) {
					alert("读取数据失败");
				}

			});

	return bool;
}

function IsUpdateEndge(historyID) {
	var bool = null;
	var url = basePath + "ea/archive/sajax_ea_IsUpdateEndge.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					historyID : historyID
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "fail") {
						bool = false;
					} else {
						bool = true;
					}

				},
				error : function(data) {
					alert("读取数据失败");
				}

			});
	return bool;

}
