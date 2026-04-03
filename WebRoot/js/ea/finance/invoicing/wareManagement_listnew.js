$(function() {
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
			
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector
           var query = "<form name='SearchForm' id='SearchForm' method='post'>"
			+ "销售出库&nbsp;<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:10px;\">单据编号：</span><input type='text' style=\"width: 100px\" name='cashierBillsVO.journalNum'/>&nbsp;&nbsp;"
			+ "责任人：<input name=\"cashierBillsVO.staffname\" style=\"width: 90px\" />&nbsp;&nbsp;"
			+ "制单日期：<input id=\"sdate\" name=\"sdate\" onfocus=\"date(this);\" style=\"width: 85px\" />至<input id=\"edate\" name=\"edate\" onfocus=\"date(this);\" style=\"width: 85px\" />&nbsp;&nbsp;"
			+ "单据状态：<select id=\"taxstatus\" style=\"width:65px\"  name=\"cashierBillsVO.status\">" 
			+ "<option value=\"22\" selected=\"selected\" >草稿</option>" 
			+ "<option value=\"05\">审核中</option><option value=\"16\">已出库</option>"
			+ "<option value=\"10\">驳回</option></select>"
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";	
			
			if(billStatus=='07'){
			$('.flexme11').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title : query,
						minheight : 80,
						buttons : [{
							name : '填写出库单',
							bclass : 'add',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '修改',
							bclass : 'edit',
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
						}, {
							name : '出库确认',
							bclass : 'edit',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, /*{
							name : '驳回',
							bclass : 'delete',
							onpress : action
							}, {
							separator : true
						},*/{
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
							name : '填写出库单',
							bclass : 'add',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '查看审核',
							bclass : 'edit',
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
					case '填写出库单' :
					    var url=basePath
								+ "/ea/newsales/ea_addWareManagement.jspa?pageNumber="
								+ pNumber + "&sdate=" + sdate
								+ "&edate=" + edate + "&search=" + search
								+"&billStatus="+billStatus+"&curDateTime="+curDateTime+"&titletype=添加";
					    window.open(url);
						break;
				   case '修改' :
				        if (cashierbillsID == "") {
							alert("请选择！");
							return;
						}
						var status=$("tr#" + cashierbillsID).find("span#statuss").text();
						if (status != "草稿") {
							alert("只有“草稿”状态单据才能修改！");
							return;
						}
						var status="";
						var URL = basePath
						+ "ea/purchase1/sajax_ea_getcashierBillstatus.jspa?cashierBillsVO.cashierBillsID="
						+ cashierbillsID + "&date=" + new Date().toLocaleString();
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
								+ "/ea/newsales/ea_getWareManagementList.jspa?billStatus=07";
							return;
						}
						    
						var url1=basePath
								+ "/ea/newsales/ea_addWareManagement.jspa?pageNumber="
								+ pNumber + "&sdate=" + sdate
								+ "&edate=" + edate + "&search=" + search+"&billStatus="+billStatus
								+ "&cashierBills.cashierBillsID="+cashierbillsID+"&curDateTime="+curDateTime+"&titletype=修改";
						window.open(url1);
						break;
					case '查看审核' :
						if (cashierbillsID == "") {
							alert("请选择！");
							return;
						}
						var url= "/ea/newsales/ea_toWareManagement.jspa?pageNumber="+ pNumber 
						+ "&cashierBills.cashierBillsID="+cashierbillsID + "&sdate="+ sdate 
						+ "&edate=" + edate + "&search="+ search+"&billStatus="+billStatus;
						window.open(basePath+url);
						//document.location.href = basePath+url;
						break;
					case '设置每页显示条数' :
						var url = basePath
								+ "/ea/newsales/ea_getWareManagementList.jspa?search="
								+ search + "&sdate=" + sdate
								+ "&edate=" + edate+"&billStatus="+billStatus;;
						numback(url);
						break;
					case '出库确认':
						if (cashierbillsID == "") {
						alert("请选择！");
						return;
						}
						var num=$("tr#" + cashierbillsID).find("span#statuss").text();
						if(num != "审核中"){
						alert("只能是”审核中”的单据才可以确认出库！");
						return;
						}
						//判断出库的物品是不是报损了。
						$.ajax({
							type:"post",
							url :"ea/purchase1/sajax_ea_AuditAcquisition.jspa",
							data:{"cashierBills.cashierBillsID":cashierbillsID},
							dataType : "json",
							success:function(data){
								var member = eval("(" + data + ")");
								var list=member.list;
								if(list.length!=0){
									if(confirm("确定出库？")){
									 	 document.location.href=basePath+"ea/newsales/ea_updatback.jspa?cashierBills.cashierBillsID="+cashierbillsID+"&billStatus="+billStatus; 
									}
								}else{
									alert("该物品未审核");
								}
								
							},
							error:function(data){
								alert(">>>");
							}
							
						});
	
						break;
					/*case '驳回':
						if (cashierbillsID == "") {
						alert("请选择！");
						return;
						}
						var num=$("tr#" + cashierbillsID).find("span#statuss").text();
						if(num != "已出库"){
						alert("只能是”已出库”的单据才可以驳回！");
						return;
						}
						if(confirm("确定驳回？")){
				 	      document.location.href=basePath+"ea/newsales/ea_rebut.jspa?cashierBills.cashierBillsID="+cashierbillsID+"&billStatus="+billStatus; 
				    	}
						break;*/
					case '打印预览':
                       	if(cashierbillsID==""){
                         	alert("请选择！");
                         	return;
                        }
						window.open(basePath + "/ea/newsales/ea_toWareManagement.jspa?cashierBills.cashierBillsID="+cashierbillsID+"&print=print");
                        break;
				}
			}
			// 这一行的单击事件
			$(".flexme11 tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
				cashierbillsID = this.id;
			});
			// 这一行的双击事件
			$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierbillsID = this.id;
				action("查看审核");
			});
				/*$("tr.xggoods").click(function(event) {
					$(this).find("input.JQuerypersonvalue").attr("checked", "true");
					cashierbillsID = $(this).attr("id");
				});*/
			// 查询按钮单击事件
			$("#tosearch").click(function() {
				$("#SearchForm")
						.attr(
								"action",
								basePath
										+ "/ea/newsales/ea_toSearchWare.jspa?pageNumber="
										+ pNumber+"&billStatus="+billStatus);
				document.SearchForm.submit.click();
			});
			
		});

function re_load() {
	document.location.href = basePath
			+ "/ea/warehousing/ea_getWareManagementList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&search=" + search+ "&sdate=" + sdate + "&edate=" + edate+"&billStatus="+billStatus;
}
