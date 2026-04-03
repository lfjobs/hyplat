$(function() {
	var staffID = "";

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
		height : 350,
		width : 'auto',
		minwidth : 30,
		title : '员工工资列表    <<工资时间段：' + startdate + "-------------" + enddate
				+ ">>",
		minheight : 80,
		buttons : [{
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
			case '查看' :
				if (staffID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + staffID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/logbooksummary/ea_showcompanyExcel.jspa?staffName="
						+ staffName + "&sdate=" + startdate + "&edate="
						+ enddate + "&search=" + search + "&companyID="
						+ pcompanyID+"&arg="+arg+"&staffcategoryid="+staffcategoryid;;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/logbooksummary/ea_getcompanyIntegral.jspa?staffName="
						+ staffName + "&sdate=" + startdate + "&edate="
						+ enddate + "&search=" + search + "&companyID="
						+ pcompanyID+"&arg="+arg+"&staffcategoryid="+staffcategoryid;;
				numback(url);
				break;
		}
	}
	$(".menu00").click(function() {
				$(this).hide();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				staffID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();

			});
	$("#tosearch").click(function() {
		$(".put3").trigger("blur");
		if ($("#postSearchForm .error").length) {
			alert("请填完所有必填项");
			return;
		}
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/logbooksummary/ea_getcompanyIntegral.jspa?pageNumber="
								+ pNumber);
		document.postSearchForm.submit.click();
	});
});

$(function() {
			var url = basePath
					+ "ea/company/sajax_n_ea_getCompanyList.jspa?date1="
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
							var ts3 = new TreeSelector($("#companyID")[0],
									data1, -1);
							ts3.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

		});
