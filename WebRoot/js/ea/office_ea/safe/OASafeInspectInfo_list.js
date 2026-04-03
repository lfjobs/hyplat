$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "安全检测单列表",
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					// 设置分割线
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
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
				document.location.href = basePath
						+ "ea/safeinspect/ea_toSaveOASafeInspectInfo.jspa?pageNumber="
						+ pNumber + "&search=" + search;
				break;
			case '删除' :
				$form = $("#myform");
				if (inspectID == "") {
					alert("请选择！");
					return;
				}
				if (confirm("确定删除？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/safeinspect/ea_delSafeInspectBill.jspa?search="
											+ search + "&pageNumber=" + pNumber);
					$form.find("input#inspectID").val(inspectID);
					document.myform.submit.click();
					$("tr#" + inspectID).remove();
					inspectID = "";
					token = 11;
				}
				break;
			case '查看' :
				if (inspectID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "ea/safeinspect/ea_toSaveOASafeInspectInfo.jspa?pageNumber="
						+ pNumber + "&entityVO.inspectID=" + inspectID
						+ "&search=" + search + "&pagepageNumber="
						+ $("#pageNumber").attr("value");
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/safeinspect/ea_getSafeInspectList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
		}
	}
	$(".flexme11 tr[id]").click(function() {
				inspectID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/safeinspect/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				inspectID = this.id;
				action("查看");
			});

	/** *******************************取得部门下拉************************************ */
	var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
	var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
	var treePName = parent.frames["leftFrame"].tree.getItemText(treePID);
	$("span#companyNames").text(treePName);
	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
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
 * this._data[i], this._data[j]); } } } } TreeSelector.prototype.createSubOption =
 * function(len, current, next){ var blank = ' '; if (next.pid == current.id) {
 * intLevel = 0; var intlvl = this.getLevel(this._data, this._rootId, current);
 * for(var a = 0; a < intlvl; a++ ) blank += " "; blank += "├";
 * this._item.options.add(new Option(blank + next.text, next.id)); for (var j =
 * 0; j < len; j++) { this.createSubOption(len, next, this._data[j]); } } }
 * TreeSelector.prototype.getLevel = function(datasources, topId, currentitem){
 * var pid = currentitem.pid; if (pid != topId) { for (var i = 0; i <
 * datasources.length; i++) { if (datasources[i].id == pid) { intLevel++;
 * this.getLevel(datasources, topId, datasources[i]); } } } return intLevel; }
 */
					var ts = new TreeSelector(
							$("select#tsiDept", "#SearchForm")[0], data, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
		// //////////////////////////////////////////////////

});
function re_load() {
	document.location.href = basePath
			+ "ea/safeinspect/ea_getSafeInspectList.jspa?search=" + search
			+ "&pageNumber=" + pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&sdate=" + sdate + "&edate="
			+ edate;
}