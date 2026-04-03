$(function() {
	var staffID = "";

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 340,
				width : 'auto',
				minwidth : 30,
				title : '集团--员工工资列表     <<工资时间段：' + sdate + "-------------" + edate
						+ ">>",
				minheight : 80,
				buttons : [{
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
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					}	, {
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
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/staffpaycompany/ea_getStaffPayList.jspa?staffName="
						+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
						+ "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg+"&comID="+comID;
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
						pbasePath
								+ "ea/staffpaycompany/ea_getStaffPayList.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
});
$(function() {
    		var url = pbasePath
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
								id : comIDa,
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
							var ts3 = new TreeSelector($("select#companyID")[0],
									data1, -1);
							ts3.createTree();

						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
});