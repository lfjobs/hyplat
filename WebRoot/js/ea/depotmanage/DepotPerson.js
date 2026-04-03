$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.registration').flexigrid({
				height : 115,
				width : 'auto',
				minwidth : 30,
				title : '库房责任人----当前库房：' + parent.depotName,
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
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"depotPersonmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				break;
			case '修改' :
				if (depotPersonID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + depotPersonID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"depotPersonmap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
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
				$("#SinceTime", $(".check")).each(function(i, tmp) {
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
										+ "ea/depotperson/ea_saveDepotPerson.jspa?pageNumber="
										+ pNumber);
				document.staffappraisalForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (depotPersonID == '') {
					alert("请选择！");
					return;
				}
				if (depotPersonID.substring(0, 2) == "sa") {
					if (confirm("是否移除？")) {
						$("#" + depotPersonID).remove();
						depotPersonID = "";
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
											+ "ea/depotperson/ea_delDepotPerson.jspa?pageNumber="
											+ pNumber
											+ "&depotPerson.depotPersonID="
											+ depotPersonID);
					document.staffappraisalForm.submit.click();
					$("tr#" + depotPersonID).remove();
					depotPersonID = "";
					token = 11;

				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/depotperson/ea_getListDepotPerson.jspa?depotPerson.depotID="
						+ depotID + "&search=" + search;
				numback(url);
				break;
			case '导出' :
				var url = basePath + "ea/depotperson/ea_showExcel.jspa?search="
						+ search + "&depotPerson.depotID=" + depotID;
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
				depotPersonID = this.id;
			});
	$(".registration tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				depotPersonID = this.id;
				action("修改");
			});
	// 根据条件查询
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#appraisalForm").attr(
				"action",
				basePath + "ea/depotperson/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.appraisalForm.submit.click();
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/depotperson/ea_getListDepotPerson.jspa?depotPerson.depotID="
				+ depotID + "&pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
$(function() {
			// /////////////////////////////////////
			var treePID = cID;
			var treePName = cName;

			var url = basePath
					+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
					+ new Date().toLocaleString();
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
							var data1 = new Array();
							data1[0] = {
								id : treePID,
								pid : '-1',
								text : treePName
							};
							for (var i = 0; i < oList.length; i++) {
								data1[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
							}
							function TreeSelector(item, data, rootId) {
								this._data = data;
								this._item = item;
								this._rootId = rootId;
							}

							TreeSelector.prototype.createTree = function() {
								var len = this._data.length;
								for (var i = 0; i < len; i++) {
									if (this._data[i].pid == this._rootId) {
										for (var m = 0; m < this._item.length; m++) {
											this._item[m].options
													.add(new Option(
															""
																	+ this._data[i].text,
															this._data[i].id));
										}
										for (var j = 0; j < len; j++) {
											this.createSubOption(len,
													this._data[i],
													this._data[j]);
										}
									}
								}
							};

							TreeSelector.prototype.createSubOption = function(
									len, current, next) {
								var blank = '　';
								if (next.pid == current.id) {
									intLevel = 0;
									var intlvl = this.getLevel(this._data,
											this._rootId, current);
									for (var a = 0; a < intlvl; a++)
										blank += "　";
									blank += "├";
									for (var m = 0; m < this._item.length; m++) {
										if (this._item[m].options.value != next.id) {
											this._item[m].options
													.add(new Option(
															blank + next.text,
															next.id));
										}
									}

									for (var j = 0; j < len; j++) {
										this.createSubOption(len, next,
												this._data[j]);
									}
								}
							};
							TreeSelector.prototype.getLevel = function(
									datasources, topId, currentitem) {
								var pid = currentitem.pid;
								if (pid != topId) {
									for (var i = 0; i < datasources.length; i++) {
										if (datasources[i].id == pid) {
											intLevel++;
											this.getLevel(datasources, topId,
													datasources[i]);
										}
									}
								}
								return intLevel;
							};

							var ts3 = new TreeSelector(
									$("select[name=organizationID]"), data1, -1);
							ts3.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

		});