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
			var query ="";
			if(type=="00"){
			query = "<form name='SearchForm' id='SearchForm' method='post'>"
			+ "采购单管理&nbsp;<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:20px;\">单据编号：</span><input type='text' style=\"width: 160px\" id='journalNum' name='cashierBillsVO.journalNum'/>&nbsp;&nbsp;"
			+ "&nbsp;&nbsp;" 
			+ "制单日期：<input id=\"sdate\" name=\"sdate\" onfocus=\"date(this);\" style=\"width: 85px\" />至<input id=\"edate\" name=\"edate\" onfocus=\"date(this);\" style=\"width: 85px\" />&nbsp;&nbsp;" 
			/*+ "单据状态：<select id=\"taxstatus\" style=\"width:90px\"  name=\"cashierBillsVO.status\">" +
					"<option value=\"22\" selected=\"selected\" >草稿</option>" +
					"<option value=\"05\">审核中</option>" +
					"<option value=\"13\">已收货</option>" +
					"<option value=\"14\">已验货</option>" +
					"</select>"*/
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";
			$('.flexme11').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title : query,
						minheight : 80,
						buttons : [
//						           {
//							name : '添加',
//							bclass : 'add',
//							onpress : action
//								// 当点击调用方法
//							}, {
//							separator : true
//						},
						{
							name : '修改',
							bclass : 'edit',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						},{
							name : '查看审核',
							bclass : 'see',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						},{
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
			
			}else if(type=="01"){
			query = "<form name='SearchForm' id='SearchForm' method='post'>"
			+ "收货管理&nbsp;<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:20px;\">单据编号：</span><input type='text' style=\"width: 160px\" id='journalNum' name='cashierBillsVO.journalNum'/>&nbsp;&nbsp;"
			+ "制单人：<input name=\"cashierBillsVO.inputName\" style=\"width: 60px\" />&nbsp;&nbsp;" 
			+ "制单日期：<input id=\"sdate\" name=\"sdate\" onfocus=\"date(this);\" style=\"width: 85px\" />至<input id=\"edate\" name=\"edate\" onfocus=\"date(this);\" style=\"width: 85px\" />&nbsp;&nbsp;" 
			+ "<select id=\"taxstatus\" style=\"width:90px;display:none;\"  name=\"cashierBillsVO.status\">" +
					"<option value=\"05\">审核中</option>" +
					"</select>"
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";
			$('.flexme11').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title : query,
						minheight : 80,
						buttons : [{
							name : '确认收货',
							bclass : 'edit',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						},{
							name : '打印预览',
							bclass : 'printer',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						},{
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
					  //制单时间
						var d = new Date(); 
						var year=d.getFullYear(); 
						var month = d.getMonth()+1; 
						var date = d.getDate(); 
						var day = d.getDay(); 
						var hours = d.getHours(); 
						var minutes = d.getMinutes(); 
						var seconds = d.getSeconds(); 
						var ms = d.getMilliseconds();   
						var curDateTime= year+"-";
						if(month>9)
						 curDateTime = curDateTime +month+"-";
						else
						 curDateTime = curDateTime +"0"+month+"-";
						if(date>9)
						 curDateTime = curDateTime +date;
						else
						 curDateTime = curDateTime +"0"+date;
						//window.open();
						document.location.href =basePath
								+ "/ea/purchase1/ea_toSavePurchase.jspa?pageNumber="
								+ pNumber+"&curDateTime="+curDateTime;
						break;
				    case '修改' :
				        if (cashierBillsID == "") {
							alert("请选择！");
							return;
						}
						var status="";
						var URL = basePath
						+ "ea/purchase1/sajax_ea_getcashierBillstatus.jspa?cashierBillsVO.cashierBillsID="
						+ cashierBillsID + "&date=" + new Date().toLocaleString();
						$.ajax({
							url : encodeURI(URL),
							type : "get",
							async : false,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var nologin = member.nologin;
								if (nologin) {
									document.location.href = basePath
											+ "page/ea/not_login.jsp";
								}
							    status = member.getstatus;
							    
							},
							error : function cbf(data) {
								notoken = 0;
								alert("数据获取失败！");
							}
						});
						if (status != "22") {
							alert("只有“草稿”状态单据才能修改！");
							document.location.href =basePath
								+ "ea/purchase1/ea_getPurchaseList.jspa?type=00";
							return;
						    }
						window.open(basePath
								+ "/ea/purchase1/ea_toSavePurchase.jspa?pageNumber="
								+ pNumber+"&curDateTime="+curDateTime+ "&cashierBills.cashierBillsID="
								+ cashierBillsID);
					    break;
					case '查看审核' :
						if (cashierBillsID == "") {
							alert("请选择！");
							return;
						}
						window.open(basePath
								+ "/ea/purchase1/ea_toPurchase.jspa?pageNumber="
								+ pNumber + "&cashierBills.cashierBillsID="
								+ cashierBillsID+"&search="+search+"&type="+type);
						//document.location.href = ;
						break;
					case '设置每页显示条数' :
						var url = basePath
								+ "/ea/purchase1/ea_getPurchaseList.jspa?search="
								+ search + "&sdate=" + sdate + "&edate="
								+ edate + "&type="+type;
						numback(url);
						break;
					/*case '查询' :
						$("#SearchForm").find("select#staffID option:first").attr("selected","selected");
						$("#SearchForm").find("select#ccompanyRelationship option:first").attr("selected","selected");
						$("#SearchForm").find("select#cstaffRelationship option:first").attr("selected","selected");
						
						$("#jqModelSearch").jqmShow();
						$("input#journalNum").focus();
						break;*/
					case '确认收货' :
						if (cashierBillsID == "") {
							alert("请选择！");
							return;
						}
						var status="";
						var URL = basePath
						+ "ea/purchase1/sajax_ea_getcashierBillstatus.jspa?cashierBillsVO.cashierBillsID="
						+ cashierBillsID + "&date=" + new Date().toLocaleString();
						$.ajax({
							url : encodeURI(URL),
							type : "get",
							async : false,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var nologin = member.nologin;
								if (nologin) {
									document.location.href = basePath
											+ "page/ea/not_login.jsp";
								}
							    status = member.getstatus;
							    
							},
							error : function cbf(data) {
								notoken = 0;
								alert("数据获取失败！");
							}
						});
						if (status != "05") {
							alert("选中单据已经收货确认，不能重复！");
							document.location.href =basePath
								+ "ea/purchase1/ea_getPurchaseList.jspa?type=01";
							return;
						}
				         var uri = "/ea/purchase1/ea_toAccpt.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID + "&type="+type+"&type1=qrsh";
				        window.open(basePath + uri);
						break;
					case '打印预览':
                       	if(cashierBillsID==""){
                         	alert("请选择！");
                         	return;
                        }
						window.open(basePath + "/ea/purchase1/ea_toPrintPurchase.jspa?cashierBills.cashierBillsID="+cashierBillsID);
                        break;
				}
			}
			// 这一行的单击事件
			$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
			
			// 查询按钮单击事件
			$("#tosearch").click(function() {
				$("#SearchForm").attr(
						"action",
						basePath + "/ea/purchase1/ea_toSearch.jspa?pageNumber="
								+ pNumber+"&type="+type+"&xmtype="+xmtype);
				document.SearchForm.submit.click();
			});
			// 这一行的双击事件
			$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看审核");
			});

		});

function re_load() {
	document.location.href = basePath
			+ "/ea/purchase1/ea_getPurchaseList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search + "&sdate=" + sdate + "&edate=" + edate;
}
