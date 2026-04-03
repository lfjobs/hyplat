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
						title : "收货管理",
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
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						},{
							name : '确认收货',
							bclass : 'edit',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}]
					});

			function action(com, grid) {
				switch (com) {
					case '查看' :
						if (financialbillID == "") {
							alert("请选择！");
							return;
						}
						var uri = "/ea/purchase/ea_toAccpt.jspa?pageNumber="
								+ pNumber + "&financialBill.financialbillID="
								+ financialbillID + "&type=02";
						document.location.href = basePath + uri;
						break;
					case '设置每页显示条数' :
						var url = basePath
								+ "/ea/purchase/ea_getPurchaseList.jspa?search="
								+ search + "&sdate=" + sdate + "&edate="
								+ edate + "&type=00";
						numback(url);
						break;
					case '查询' :
						$("#jqModelSearch").jqmShow();
						$("input#journalNum").focus();
						break;
					case '确认收货' :
						if (financialbillID == "") {
							alert("请选择！");
							return;
						}
						var uri = "/ea/purchase/ea_toAccpt.jspa?pageNumber="
								+ pNumber + "&financialBill.financialbillID="
								+ financialbillID + "&type=02";
						document.location.href = basePath + uri;
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
				$("#SearchForm")
						.attr(
								"action",
								basePath
										+ "/ea/purchase/ea_toAccptSearch.jspa?pageNumber="
										+ pNumber);
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
	document.location.href = basePath
			+ "/ea/purchase/ea_getPurchaseList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search + "&sdate=" + sdate + "&edate=" + edate;
}

function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '01') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}
