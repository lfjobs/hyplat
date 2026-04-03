$(function() {
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector
			$("#jqModelSearch").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector
			$('.JQueryflexme').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title : '集团--员工工资级别汇总',
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
					case '导出' :
						url = basePath
								+ "ea/payscalecompany/ea_showExcel.jspa?search="
								+ search;
						open(url);
						break;
					case '查看' :
						if (payScaleID == "") {
							alert('请选择!');
							return;
						}
						document.cstaffForm.reset();
						$t = $("table#stafftable");
						$p = $("tr#" + payScaleID);
						$p.find("span[id]").each(function() {
									$t.find(":input#" + this.id).val($(this)
											.text()).attr("readonly","readonly");
								});
						$("#jqModel").jqmShow();
						break;
					case '设置每页显示条数' :
						var url = basePath
								+ "ea/payscalecompany/ea_getPayScaleList.jspa?search="
								+ search;
						numback(url);
						break;
				}
			}
			$("#tosearch").bind("click", function() {
				$("form#appraisalForm")
						.attr(
								"action",
								basePath
										+ "ea/payscalecompany/ea_toSearch.jspa?pageNumber="
										+ ppageNumber);
				document.appraisalForm.submit.click();
			});

			$(".JQueryflexme tr[id]").click(function() {
				payScaleID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
			$(".JQueryflexme tr[id]").dblclick(function() {
						action('查看');
					});
			$("input.JQueryreturn").click(function() {
						$("#jqModel").jqmHide();
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