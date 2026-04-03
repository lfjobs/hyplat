$(function() {
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
				title : '岗位',
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
				if (responsibilitiesID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + responsibilitiesID);
				$t.find("span#abc").remove();// ////////////lwt
				$t.find("#pic").attr("src", "");
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var photo = $p.find("span#photo").text();
				if (photo.length != 0) {
					$t.find("#pic").attr("src", pbasePath + photo);
				}
				$s = $t.find("#staffID");// //
				$span = $('<span id="abc"/>').text($p.find("span#staffName")
						.text()).insertAfter($s);// ///
				$s.hide();// //////
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = pbasePath
						+ "ea/responsibilitiessummary/ea_showExcel.jspa?search="
						+ psearch;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/responsibilitiessummary/ea_getResponsibilitiesList.jspa?search="
						+ psearch;
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
				responsibilitiesID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#cstaffForm").find("#staffID").change(function() {
		var staffID = $(this).children("option:selected").val();
		if (!staffID) {
			staffID = tlist;
		}
		//var params = {
		//	'staffID' : staffID
		//};
		var url = pbasePath
				+ "ea/responsibilities/sajax_n_ea_getcstaffByID.jspa?date1="
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
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var staff = member.staff;
				$("#cstaffForm").find("#staffCode").val(staff.staffCode);
				$("#cstaffForm").find("#pic").attr("src",
						pbasePath + staff.photo);
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});

	});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();

			});
	$("input.JQueryPrint").click(function() {// 打印预览
				var responsibilitiesID = $("table#stafftable")
						.find("#responsibilitiesID").val();
				window
						.open(pbasePath
								+ "ea/responsibilities/ea_getStaffRespons.jspa?staffResponsibilities.responsibilitiesID="
								+ responsibilitiesID);

			});
	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						pbasePath
								+ "ea/responsibilitiessummary/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
});
$(function() {
			// /////////////////////////////////////
			var url = pbasePath
					+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date2="
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
							$t = $("table#stafftable");

							var ts3 = new TreeSelector($("#deptID")[0], data1,
									-1);
							ts3.createTree();
							var ts = new TreeSelector($t
											.find("select#departmentID")[0],
									data1, -1);
							ts.createTree();
							var ts1 = new TreeSelector($t
											.find("select#organizationPID")[0],
									data1, -1);
							ts1.createTree();
							var ts2 = new TreeSelector($t
											.find("select#organizationCID")[0],
									data1, -1);

							ts2.createTree();
							//var s = "2010-9-9";
							//var d = new Date(Date.parse(s.replace(/-/g, "/")));
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

		});