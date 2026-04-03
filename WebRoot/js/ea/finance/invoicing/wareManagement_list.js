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
			if(billStatus=='07'){
			$('.flexme11').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title :billStatus=='07'?"销售出库":"出库管理",
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
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '出库确认',
							bclass : 'edit',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '驳回',
							bclass : 'delete',
							onpress : action
							}, {
							separator : true
						},{
							name : '打印预览',
							bclass : 'printer',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}]
					});
			}else{
				$('.flexme11').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title :billStatus=='07'?"销售出库":"出库管理",
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
			}
			function action(com, grid) {
				switch (com) {
					case '添加' :
						document.location.href = basePath
								+ "/ea/warehousing/ea_addWareManagement.jspa?pageNumber="
								+ pNumber + "&sdate=" + sdate
								+ "&edate=" + edate + "&search=" + search+"&billStatus="+billStatus;
						break;
					case '查看' :
						if (financialgoodid == "") {
							alert("请选择！");
							return;
						}
						var url= "/ea/warehousing/ea_toWareManagement.jspa?pageNumber="+ pNumber + "&financialBillsGood.financialgoodID="+financialgoodid + "&sdate="+ sdate + "&edate=" + edate + "&search="+ search+"&billStatus="+billStatus;
						document.location.href = basePath+url;
						break;
					case '设置每页显示条数' :
						var url = basePath
								+ "/ea/warehousing/ea_getWareManagementList.jspa?search="
								+ search + "&sdate=" + sdate
								+ "&edate=" + edate+"&billStatus="+billStatus;;
						numback(url);
						break;
					case '查询' :
						$("#SearchForm").find("select#staffID option:first").attr("selected","selected");
						$("#SearchForm").find("select#ccompanyRelationship option:first").attr("selected","selected");
						$("#SearchForm").find("select#cstaffRelationship option:first").attr("selected","selected");
						
						$("#jqModelSearch").jqmShow();
						$("input#journalNum").focus();
						break;
					case '出库确认':
						if (financialgoodid == "") {
						alert("请选择！");
						return;
						}
						 if(confirm("确定出库？")){
						 	 document.location.href=basePath+"ea/warehousing/ea_updatback.jspa?financialBillsGood.financialgoodID="+financialgoodid+"&billStatus="+billStatus; 
						    	} 
						var num=$("tr#" + financialgoodid).find("span#status").text();
						if(num == "出库"){
						alert("该物品已出库");
						return;
						}
					
						break;
					case '驳回':
						if (financialgoodid == "") {
						alert("请选择！");
						return;
						}
						var num=$("tr#" + financialgoodid).find("span#status").text();
						if(num == "出库"){
						alert("该物品已出库");
						return;
						}
						if(confirm("确定驳回？")){
				 	  document.location.href=basePath+"ea/warehousing/ea_rebut.jspa?financialBillsGood.financialgoodID="+financialgoodid+"&billStatus="+billStatus; 
				    	}
						break;
					case '打印预览':
                       	if(financialgoodid==""){
                         	alert("请选择！");
                         	return;
                        }
						window.open(basePath + "/ea/warehousing/ea_toWareManagement.jspa?financialBillsGood.financialgoodID="+financialgoodid+"&print=print");
                        break;
				}
			}
			// 这一行的单击事件
			$(".flexme11 tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
				financialgoodid = this.id;
			});
				/*$("tr.xggoods").click(function(event) {
					$(this).find("input.JQuerypersonvalue").attr("checked", "true");
					financialgoodid = $(this).attr("id");
				});*/
			// 查询按钮单击事件
			$("#tosearch").click(function() {
				$("#SearchForm")
						.attr(
								"action",
								basePath
										+ "/ea/warehousing/ea_toSearchWare.jspa?pageNumber="
										+ pNumber+"&billStatus="+billStatus);
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
			+ "/ea/warehousing/ea_getWareManagementList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&search=" + search+ "&sdate=" + sdate + "&edate=" + edate+"&billStatus="+billStatus;
}
