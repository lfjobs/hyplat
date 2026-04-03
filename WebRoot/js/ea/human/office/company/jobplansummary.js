$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '项目工作计划汇总',
				minheight : 80,
				buttons : [{
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
				if (select == '01') {
					$("#sa").show();
				}
				break;
			case '导出' :
				var url = basePath
						+ "ea/jobplan/ea_showExcelCompanySummary.jspa?search="
						+ search + "&date1=" + new Date();
				open(url);
				break;
			case '查询' :

				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				if (addressID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + addressID);
				$p.find("span[id]").each(function() {
					$t.find(":input#" + this.id).val($(this).text()).attr(
							"readonly", "readonly");
				});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/jobplan/ea_getCompanyJobPlanListSummary.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
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
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/jobplan/ea_toSearchCompanySummary.jspa?pageNumber="
								+ pNumber);
		document.postSearchForm.submit.click();
	});

	$(".address tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
});
$(function() {

	var url = basePath + "ea/company/sajax_n_ea_getCompanyList.jspa?date2="
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
						id : comID,
						pid : '-1',
						text : comName
					};
					for (var i = 0; i < companylist.length; i++) {
						data1[i + 1] = {
							id : companylist[i].companyID,
							pid : companylist[i].companyPID,
							text : companylist[i].companyName
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
					var ts3 = new TreeSelector($("#deptID")[0], data1, -1);
					ts3.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("#deptID").change(function() {
		$("option", $("#orgID")).remove();

		var url = basePath
				+ "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
				+ this.value + "&date3=" + new Date();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var oList = member.organizationlist;
						var data2 = new Array();
						data2[0] = {
							id : $("#deptID").attr("value"),
							pid : '-1',
							text : '全部'
						};
						for (var i = 0; i < oList.length; i++) {
							data2[i + 1] = {
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
						ts = new TreeSelector($("#orgID")[0], data2, -1);
						ts.createTree();
						$("option[value=" + this.value + "]", $("#orgID"))
								.val("");
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});

	});
});