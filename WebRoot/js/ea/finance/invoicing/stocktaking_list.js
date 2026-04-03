$(function() {
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector

			$(".jqmreturn").click(function() {
				notoken = 0;
				$("#documentsjqModel").jqmHide();
				$("#previewjqModel").jqmHide();
				$("#journalNumAjax").attr("value", "");
				$("#taxDateAjax").attr("value", "");
				showDocument = false;
			});

			$('.flexme11').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title : "盘库单管理",
						minheight : 80,
						buttons : [{
							name : '添加',
							bclass : 'add',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '查看',
							bclass : 'see',
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
							name : '打印预览',
							bclass : 'printer',
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
						document.location.href = basePath+ "/ea/stock/ea_addStocktaking.jspa?";
						break;
					case '查看' :
						if (financialbillID == "") {
							alert("请选择！");
							return;
						}
						document.location.href = basePath
								+ "/ea/stock/ea_toStocktaking.jspa?pageNumber="
								+ pNumber + "&financialBill.financialbillID="
								+ financialbillID;
						break;
					case '打印预览' :
						if (financialbillID == "") {
							alert("请选择！");
							return;
						}
						window.open(basePath + "/ea/stock/ea_toStocktakingPrint.jspa?pageNumber="
								+ pNumber + "&financialBill.financialbillID="
								+ financialbillID);
						break;
					case '设置每页显示条数' :
						var url = basePath
								+ "/ea/stock/ea_getStocktakingList.jspa?search="
								+ search + "&sdate=" + sdate + "&edate="
								+ edate;
						numback(url);
						break;
					case '查询' :
						$("#jqModelSearch").jqmShow();
						$("input#journalNum").focus();
						break;
				}
			}
			// 这一行的单击事件
			$(".flexme11 tr[id]").click(function() {
				financialbillID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
			
			// 查询按钮单击事件
			$("#tosearch").click(function() {
				$("#SearchForm").attr(
						"action",
						basePath + "/ea/stock/ea_toStocktakingSearch.jspa?pageNumber="
								+ pNumber+"&type=05");
				document.SearchForm.submit.click();
			});
			// 这一行的双击事件
			$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				financialbillID = this.id;
				action("查看");
			});

		});

function re_load() {
	alert(search);
	document.location.href = basePath
			+ "/ea/stock/ea_getStocktakingList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")+"&type=05"
			+ "&search=" + search + "&sdate=" + sdate + "&edate=" + edate;
}