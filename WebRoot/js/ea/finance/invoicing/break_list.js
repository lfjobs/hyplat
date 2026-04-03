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
						title :"报损管理",
						minheight : 80,
						buttons : [  {
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
						}, {
							name : '报损确认',
							bclass : 'edit',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '报损驳回',
							bclass : 'delete',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}]
					});
			function action(com, grid) {
				switch (com) {
					/*case '查看' :
						if (financialgoodid == "") {
							alert("请选择！");
							return;
						}
						var url= "/ea/break/ea_tobreak.jspa?pageNumber="+ pNumber + "&financialBillsGood.financialgoodID="+financialgoodid+ "&search="+ search;
						document.location.href = basePath+url;
						break;*/
					case '设置每页显示条数' :
						var url = basePath
								+ "/ea/break/ea_getbreakList.jspa?search="+ search;
						numback(url);
						break;
					case '查询' :
						$("#jqModelSearch").jqmShow();
						break;
					case '报损确认':
						if (inventoryID == "") {
						alert("请选择！");
						return;
						}
						 if(confirm("报损确认？")){
				 	 document.location.href=basePath+"ea/break/ea_updatbreak.jspa?inventoryParam.inventoryID="+inventoryID; 
				    	}
						break;
					case '报损驳回':
						if (inventoryID == "") {
						alert("请选择！");
						return;
						}
						if(confirm("确定驳回？")){
				 	  document.location.href=basePath+"ea/break/ea_breakrebut.jspa?inventoryParam.inventoryID="+inventoryID; 
				    	}
						break;
				}
			}
			// 这一行的单击事件
			$(".flexme11 tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
				inventoryID = this.id;
			});
			// 查询按钮单击事件
			$("#tosearch").click(function() {
				$("#SearchForm")
						.attr(
								"action",
								basePath
										+ "/ea/break/ea_toSearchWare.jspa?pageNumber="+ pNumber);
				document.SearchForm.submit.click();
			});
			// 这一行的双击事件
			$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
				inventoryID = this.id;
				action("查看");
			});

		});

function re_load() {
	document.location.href = basePath+ "/ea/warehousing/ea_getbreakList.jspa?pageNumber="+ pNumber + "&pageForm.pageNumber="+ $("#pageNumber").attr("value") + "&search=" + search;
}
