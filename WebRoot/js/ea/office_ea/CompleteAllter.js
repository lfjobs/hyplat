$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '整改通知单',
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
				completeAllterID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (completeAllterID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				$p = $("tr#" + completeAllterID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (completeAllterID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#completeAllterID').val(completeAllterID);
				if (confirm("是否删除？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/completeAllter/ea_delCompleteAllter.jspa?pageNumber="
											+ ppageNumber + "&search="
											+ psearch);
					document.cstaffForm.submit.click();
					$("tr#" + completeAllterID).remove();
					completeAllterID = "";
					token = 11;
				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/completeAllter/ea_showExcel.jspa?search="
						+ psearch;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/completeAllter/ea_getCmpleteAllterList.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				completeAllterID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if (completeAllterID == "") {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/completeAllter/ea_saveCompleteAllter.jspa?pageNumber="
											+ ppageNumber + "&search="
											+ psearch);
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
										+ "ea/completeAllter/ea_saveCompleteAllter.jspa?pageNumber="
										+ ppageNumber);
				document.cstaffForm.submit.click();
				document.cstaffForm.reset();
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
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/completeAllter/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});

});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/completeAllter/ea_getCmpleteAllterList.jspa?search="
				+ psearch + "&pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
$(function() {
			// /////////////////////////////////////
			var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
			var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
			var treePName = parent.frames["leftFrame"].tree
					.getItemText(treePID);
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
							/**
							 * function TreeSelector(item, data, rootId){
							 * this._data = data; this._item = item;
							 * this._rootId = rootId; }
							 * 
							 * TreeSelector.prototype.createTree = function(){
							 * var len = this._data.length; for (var i = 0; i <
							 * len; i++) { if (this._data[i].pid ==
							 * this._rootId) { this._item.options.add(new
							 * Option("" + this._data[i].text,
							 * this._data[i].id)); for (var j = 0; j < len; j++) {
							 * this.createSubOption(len, this._data[i],
							 * this._data[j]); } } } }
							 * 
							 * TreeSelector.prototype.createSubOption =
							 * function(len, current, next){ var blank = ' '; if
							 * (next.pid == current.id) { intLevel = 0; var
							 * intlvl = this.getLevel(this._data, this._rootId,
							 * current); for(var a = 0; a < intlvl; a++ ) blank += " ";
							 * blank += "├"; this._item.options.add(new
							 * Option(blank + next.text, next.id)); for (var j =
							 * 0; j < len; j++) { this.createSubOption(len,
							 * next, this._data[j]); } } }
							 * TreeSelector.prototype.getLevel =
							 * function(datasources, topId, currentitem){ var
							 * pid = currentitem.pid; if (pid != topId) { for
							 * (var i = 0; i < datasources.length; i++) { if
							 * (datasources[i].id == pid) { intLevel++;
							 * this.getLevel(datasources, topId,
							 * datasources[i]); } } } return intLevel; }
							 */
							$t = $("div#jqModel");
							$p = $("div#jqModelSearch");
							var ts = new TreeSelector(
									$t.find("select#deptID")[0], data, -1);
							ts.createTree();
							var t1 = new TreeSelector(
									$p.find("select#deptID")[0], data, -1);
							t1.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

		});