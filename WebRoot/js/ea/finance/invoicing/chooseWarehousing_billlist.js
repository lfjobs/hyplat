/**
 * 待入库单据js
 */
$(function() {
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector
			var query = "<form name='SearchForm' id='SearchForm' method='post'>"
			+ "采购入库&nbsp;<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:10px;\">单据编号：</span><input type='text' style=\"width: 100px\" name='financialBill.journalNum'/>&nbsp;&nbsp;"
			+ "制单人：<input name=\"financialBill.billStaffName\" style=\"width: 90px\" />&nbsp;&nbsp;"
			+ "制单日期：<input id=\"sdate\" name=\"sdate\" onfocus=\"date(this);\" style=\"width: 85px\" />至<input id=\"edate\" name=\"edate\" onfocus=\"date(this);\" style=\"width: 85px\" />&nbsp;&nbsp;"
			+ "单据类型：<select id=\"taxstatus\" style=\"width:65px\"  name=\"financialBill.billsType\"><option value=\"验货单\" selected=\"selected\" >验货单</option><option value=\"采购入库单\">采购入库单</option></select>"
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";	
			
			$('.flexme11').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title : query,
						minheight : 80,
						buttons : [{
							name : '填写入库单',
							bclass : 'add',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '查看审核',
							bclass : 'see',
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
							name : '打印预览',
							bclass : 'printer',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						},{
							name : '确认入库',
							bclass : 'examine',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}]
					});

			function action(com, grid) {
				switch (com) {
					case '填写入库单' :
						if (financialbillID == "") {
							alert("请选择！");
							return;
						}
						var status="";
						var URL = basePath
						+ "ea/newstorage/sajax_ea_getcashierBillstatus.jspa?financialBill.financialbillID="
								+ financialbillID + "&date=" + new Date().toLocaleString();
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
					    var billsType=$("tr#"+financialbillID).find("#billsType").text();
						if(billsType=="验货单"){
						   if (status == "05") {
							var uri = "/ea/newstorage/ea_toExamineGoodsAddDan.jspa?financialBill.financialbillID="
								+ financialbillID;
						    window.open(basePath + uri);
						    //document.location.href = basePath + uri;
							}else {
							   alert("只有审核中的验货单据才能填写入库单！");
							   re_load();
							   return;
							}
						}else if(billsType=="采购入库单"){
						   alert("请选择 验货单关联 进行操作！");
						   return;
						}
						
						break;
					case '查看审核' :
						if (financialbillID == "") {
							alert("请选择！");
							return;
						}
						var url= basePath
								+ "/ea/newstorage/ea_seeExamineGoodsBill.jspa?pageNumber="
								+ pNumber + "&financialBill.financialbillID="
								+ financialbillID+"&search="+search;
					    window.open(url);
						break;
					case '设置每页显示条数' :
						var url = basePath
								+ "/ea/newstorage/ea_toExamineGoodsBillList.jspa?search="
								+ search + "&sdate=" + sdate + "&edate="
								+ edate+"&xmtype="+xmtype;
						numback(url);
						break;
					case '查询' :
						$("#SearchForm").find("select#staffID option:first").attr("selected","selected");
						$("#SearchForm").find("select#ccompanyRelationship option:first").attr("selected","selected");
						$("#SearchForm").find("select#cstaffRelationship option:first").attr("selected","selected");
						
						$("#jqModelSearch").jqmShow();
						$("input#journalNum").focus();
						break;
					case '打印预览':
                       	if(financialbillID==""){
                         	alert("请选择！");
                         	return;
                        }
						window.open(basePath + "/ea/newstorage/ea_toPrintExamineGoodsBill.jspa?financialBill.financialbillID="+financialbillID);
                        break;
                    case '确认入库':
                       	if(financialbillID==""){
                         	alert("请选择！");
                         	return;
                        }
                        var status=$("tr#"+financialbillID).find("#billStatus").text();
					    var billsType=$("tr#"+financialbillID).find("#billsType").text();
						if(billsType=="采购入库单"){
						   if (status == "审核中") {
						   	   if(confirm("是否确认入库？")){
						   	    var uri = "/ea/newstorage/ea_updateWareManagement.jspa?financialBill.financialbillID="
									+ financialbillID;
							    document.location.href = basePath + uri;
						   	  }
								
							}else {
							   alert("只有“审核中”的  采购入库单据 才能确认入库！");
							   return;
							}
						}else if(billsType=="验货单"){
						   alert("请选择 “采购入库单” 进行操作！");
						   return;
						}
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
						basePath + "/ea/newstorage/ea_toSearchEGB.jspa?pageNumber="
								+ pNumber+"&xmtype="+xmtype);
				document.SearchForm.submit.click();
			});
			// 这一行的双击事件
			$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				financialbillID = this.id;
				action("查看审核");
			});

		});
//跳转到验货单列表
function re_load() {
	document.location.href = basePath
			+ "/ea/newstorage/ea_toExamineGoodsBillList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search + "&sdate=" + sdate + "&edate=" + edate;
}
