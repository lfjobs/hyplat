$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : '工资级别升降级单',
				minheight : 80,
				buttons : [{
					name : '添加',
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
					name : '删除',
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
				// if (select == '01') {
				// $("#sa").show();
				// }
				addressID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (addressID == "") {
					alert('请选择!');
					return;
				}
				action("查看");
				break;
			case '删除' :

				if (addressID == "") {
					alert('请选择!');
					return;
				}
				$f = $('#addressForm');
				if (confirm("确定删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/payGradeModulation/ea_del.jspa?payGradeModulation.pgmID="
											+ addressID + "&pageNumber="
											+ ppageNumber);
					document.addressForm.submit.click();
					$("tr#" + addressID).remove();
					addressID = '';
					token = 11;
				}
				break;
			case '导出' :
				var url = basePath
						+ "ea/payGradeModulation/ea_showExcel.jspa?search="
						+ psearch;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("#cstaffForm");
				$p = $("tr#" + addressID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/payGradeModulation/ea_getListForPage.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}

	// 新加内内容开始 target 指向页面隐藏Hidden
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}

				if (addressID == "") {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/payGradeModulation/ea_save.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					token = 1;
					return;
				}
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/payGradeModulation/ea_save.jspa?pageNumber="
										+ ppageNumber);
				document.cstaffForm.submit.click();
				token = 2;
			});

	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();

			});

	// 新加内容结束
	$("span.list_remove").click(function() {// 删除点击事件
				$p = $(this).parent().parent().parent();
				$f = $('#addressForm');
				$p.find(':input').appendTo($f);
				$f
						.attr(
								"action",
								basePath
										+ "/ea/payGradeModulation/t_ea_del.jspa?pageNumber="
										+ ppageNumber);
				document.addressForm.submit.click();
			});
	$("table tr[id]").click(function() {
				addressID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				basePath
						+ "/ea/payGradeModulation/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});

	$(".address tr[id]").dblclick(function() {
				action("查看");
			});
	$("span.toedit").click(function() {
				document.cstaffForm.reset();
				$t = $("#cstaffForm");
				$p = $(this).parent().parent().parent();
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
			});

	$("input.box1").click(function() {
				$("input#classes0", $("#cstaffForm")).val(this.value);
			});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span />").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/payGradeModulation/ea_getListForPage.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

$(function() {
			// /////////////////////////////////////
			var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
			var url = basePath
					+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date01="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(cc) {
							var member = eval("(" + cc + ")");
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
							for (var i = 0; i < oList.length; i++) {
								data[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
							}
/**
 * function TreeSelector(item, data, rootId){ this._data = data; this._item =
 * item; this._rootId = rootId; }
 * 
 * TreeSelector.prototype.createTree = function(){ var len = this._data.length;
 * for (var i = 0; i < len; i++) { if (this._data[i].pid == this._rootId) {
 * this._item.options.add(new Option("" + this._data[i].text,
 * this._data[i].id)); for (var j = 0; j < len; j++) { this.createSubOption(len,
 * this._data[i], this._data[j]); } } } }
 * 
 * TreeSelector.prototype.createSubOption = function(len, current, next){ var
 * blank = ' '; if (next.pid == current.id) { intLevel = 0; var intlvl =
 * this.getLevel(this._data, this._rootId, current); for(var a = 0; a < intlvl;
 * a++ ) blank += " "; blank += "├"; this._item.options.add(new Option(blank +
 * next.text, next.id)); for (var j = 0; j < len; j++) {
 * this.createSubOption(len, next, this._data[j]); } } }
 * TreeSelector.prototype.getLevel = function(datasources, topId, currentitem){
 * var pid = currentitem.pid; if (pid != topId) { for (var i = 0; i <
 * datasources.length; i++) { if (datasources[i].id == pid) { intLevel++;
 * this.getLevel(datasources, topId, datasources[i]); } } } return intLevel; }
 */
							$t = $("div#jqModel");
							var ts = new TreeSelector(
									$t.find("select#deptID")[0], data, -1);
							ts.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

		});