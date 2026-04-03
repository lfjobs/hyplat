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
				$('.JQueryflexme').flexigrid({
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '集团--正式员工信息汇总',
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
			
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				var url = basePath
						+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
						+ personvalue;
				window.open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/staffcoscompany/ea_getStaffCosList.jspa?search="
						+ search ;
				numback(url);
				break;	
			case '导出' :
				url = basePath + "ea/staffcoscompany/ea_showExcel.jspa?search=" + search;
				open(url);
				break;
		}
	}
	
	$(".JQueryflexme tr[id]").dblclick(function() {
		action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});

	
	$(".JQueryflexme tr[id]").click(function() {
		personvalue = this.id;
		if (personurl) {
			$("#mainframe").attr("src", personurl + personvalue);
		}
		staffName = $(this).find("span#staffName").text();
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "ea/staffcoscompany/ea_toSearch.jspa?pageNumber="
						+ ppageNumber );
		document.cstaffSearchForm.submit.click();
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
							var ts3 = new TreeSelector($("select#companyID")[0],
									data1, -1);
							ts3.createTree();

						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

		});