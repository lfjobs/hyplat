$(function() {
	self.opener.location.reload(); //刷新父窗口
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
	// 
	}).jqmAddClose('.close');//
	
	//生成销售单
	$(".generate").click(function(){
		if(confirm("确定生成销售出库单？")){
		$("#CostSheetForm").attr("target","hidden").attr("action",basePath
								+ "/ea/cashier/ea_generateSaleSheet.jspa?");
		  document.CostSheetForm.submit.click();
		  token = 2;
		  
		}
		
	});
	

   if(status=="40"&&billsType=="收款单"){
	   $(".JQuerySubmitgd").attr("disabled",false).removeClass("grey");
	   
   }else if(status=="04"&&billsType=="收款单"){
	   $(".audit").attr("disabled",false).removeClass("grey");
	   $("#aa").show();
	   $(".sfk").hide();
	   $(".verify").attr("disabled",false);
	   $("input[type=text]").attr("readonly","readonly");
	   $("#remark").attr("readonly",false);
	   $("#spappstyle").show();
	   $("#appstyle").hide();
   }

   if(sta=="view"){
  		$(".menubtn").attr("disabled",true).addClass("grey");
  		$(".closewindow").attr("disabled",false).removeClass("grey");
  	}

   $(".aduittr .inputbottom").each(function(){
	   if($(this).val()!=""){
		   $(".generate").attr("disabled",false).removeClass("grey");
		  
	   }
	   
   });
	
   
   	// 保存预算
	$("input.JQuerySubmitgd").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		$(".notnull", $("#CostSheetForm")).trigger("blur");
		if ($("form .error").length) {
			alert("请填完所有必填项");
			return;
		}
		$("#CostSheetForm").attr("target","hidden")
				.attr(
						"action",
						basePath
								+ "/ea/splitbill/ea_saveCashierBills.jspa?");
		document.CostSheetForm.submit.click();
		token = 2;
	});


	//菜单起效
     //关闭
	 $(".closewindow").click(function(){
		 if(confirm("确定要关闭添加窗口？")){
			 self.opener.location.reload(); //刷新父窗口
			 window.close();
		 }
		 
	 });
	
	// 整个添加页面 获得焦点带边框，失去焦点不带边框
	 $("#CostSheetForm input.xiaoguo").blur(function(){
		 if($(this).attr("class").indexOf("model1")==-1){
			 $(this).addClass("model1");
		 }
	 }).focus(function(){
		 if($(this).attr("class").indexOf("model1")!=-1){
			 $(this).removeClass("model1");
		 }
	 });

	//物品新增一行
	
	$(".addline").click(function(){
		var max = 1;
		$("#goodtable").find("tr").each(function(){
			var trid=$(this).attr("id").substring(6);
			if(trid!=""){
				if(trid>max){
					max=trid;
				}
			}
		});
		 $("#kelong").before($("#kelong").clone(true).attr("id","kelong"+ (max+1)).addClass("checkgoods"));
		 $("#kelong"+(max+1)).show();
	});
	
	$("#goodtable").find("tr").click(function(){
		$("#line").text($(this).attr("id"));
	});
	
	//物品新增一行
	$(".deleteline").click(function(){
		$("#"+$("#line").text()).remove();
	});
	
	
	// 物品表格单击样式问题
	$("#goodtable :input").click(function() {
		$("#goodtable td").find("div").each(function() {
			$(this).css("border", "none");
			$(this).find(".caz").hide();

		});
		$(this).parent("div").css("border", "1px solid #000000");
		$(this).parent("div").find(".fou").focus();
		$(this).parent("div").find(".caz").show();

	});
	
	$("#goodtable td").click(function() {
		$("#goodtable td").each(function() {
			$(this).find("div").css("border", "none");
			$(this).find(".caz").hide();
		});

		$(this).find("div").css("border", "1px solid #000000");
		$(this).find(".fou").focus();
		$(this).find(".caz").show();

	});
	
	 
	//打印
	 $(".print").click(function(){
		 if(billsType=="收款单"){
			 $("#jqModelsbill").jqmShow();
		 }else{
			 window.open(basePath
						+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
						+ $("#cashierBillsID").val());
		 }
	 });
	 
	 $("#printprev").click(function(){
		 window.open(basePath
					+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
					+ $("#cashierBillsID").val()+"&radioType="+$('input:radio[name="dy"]:checked').val());
	});


	// 责任人,项目名称，银行选中查询结果
	$("#goodsQuery tr[id]").live("click", function(event) {
		var types = $("#types").val();
		var $trs = $("#goodsQuery tr#" + this.id);
		if (types == "projectName"||types=="staffname"||types=="bankaccount") {
			$trs.find("td").each(function(){
				if(this.id!=""){
				 $("#table3").find("#"+this.id).val($(this).text());
				 if(this.id=="xmtype"){
					 $("#table3").find("#xmtype2").val($(this).text());
				 }
				}
			});
		}

		var kelongid = $("#line").text();
		var id = kelongid.substring(6);
		
		if (types == "goodsNum"||types=="goodsName"||types=="ccompanyName"||types=="connectName") {
			$trs.find("td").each(function(){
				 $("tr#"+kelongid).find("#"+this.id).val($(this).text());
		        	if (this.title == "ava"&& $(this).text().length != 0) {
						$op = $("<option />");
						$op.attr("value",$(this).text()).text(($(this).text() == "null" || $(this).text() == null) ? null: $(this).text());
						$("select#goodsVariableID",$("#"+kelongid)).append($op);
				 }
			});
			if (types == "goodsNum"||types=="goodsName"){
				//修改当前行的所有input的name
				$("#kelong"+ id).find(':input[name]').each(function() {
			           $(this).attr("name","logbookmap["+ id+ "]."+ this.name);
			    });
			}
		}

		$("input.rauser", $(this)).attr("checked", "checked");
		$("#goodsQuery").hide();
	});
	
	// 键入时模糊查询项目分类
	$("#moc").click(function() {

		getCate($("#inputmoc").val());

	});

	// 各个弹出框的关闭功能
	$(".JQueryreturns").click(function() {
		$("#ntype").val("");
		$("#body_num").empty();
		notoken = 0;
		$(".jqmWindow").jqmHide();
	});
	
	// 添加页面的返回功能；
	$(".JQueryClose").click(function() {
		notoken = 0;
		re_load();
	});
	
	//审核
	$(".verify").click(function(){
		$("#shyj").jqmShow();
		verid=$(this).attr("id");
	});
	//审核
	$("#submitResult2").click(function(){
		if ($.trim($("#SendForm2 #comments").val()) == "") {
			alert("请填写审核意见");
			return;
		}
		$(".ckTextLength").trigger("blur");
		if ($("#SendForm .error").length) {
			notoken = 0;
			return;
		}
		if(confirm("你确定要通过审核此单据吗？")){
			var delurl = basePath
					+ "ea/splitbill/sajax_ea_verifyBills.jspa?cashierBills.cashierBillsID="
					+ $("#cashierBillsID").val()+"&comments="+$("#comments").val()+"&deptpost="+verid+ "&date="
					+ new Date().toLocaleString();
			$.ajax({
				url : encodeURI(delurl),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var staffName = member.staffName;
					alert("通过审核！");
					$("." +verid).val(staffName);
					$("#remark").val($("#remark").val()+staffName+":"+$("#SendForm2 #comments").val()+";");
					$(".generate").attr("disabled",false).removeClass("grey");
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
		}
		$("#shyj").jqmHide();
		$("comments").val("");
	});

	// 计算金额
	$(".jisuan").live("keyup",function(event) {
		var bnum=0;
		if (this.value != "") {
			var dj=0;
			var price=0;
			var jk=0;
			var jhj=0;
			var jine=0;
			var ys=0;
			var yus=0;
			if (isNaN(this.value)) {
				return;
			} else {
				dj = $(this).parent().parent().find(":input#quantity").val(); //数量
				jk=$(this).parent().parent().find(":input#rebate").val(); //折扣
				price = $(this).parent().parent().find(":input#price").val(); //单价
				ys=$(this).parent().parent().find(":input#alreadyMoney").val(); //已收
				yus=$(this).parent().parent().find(":input#money").val(); //预算
				if (!isNaN(dj) && !isNaN(price)&&!isNaN(jk)) {
					jhj=price*(jk/10);
					jhj = Math.round(jhj * 100) / 100;
					$(this).parent().parent().find(":input#rebatePrice").attr("value", jhj);
					jine = dj * (price*(jk/10));
					jine = Math.round(jine * 100) / 100;
					
					if(yus-ys-jine<0){
						bnum=1;
					}else{
						$(this).parent().parent().find(":input#realMoney").attr("value", jine.toFixed(2)); //未收款
						$(this).parent().parent().find(":input#futureMoney").attr("value", (yus-ys-jine).toFixed(2)); //未收款
					}
				}
			}
		}
		if(bnum){
			alert("超出预算!");
			return;
		}
	});

	$(".jisuan").blur(function() {
		var title = "";
		if ($(this).attr("id") == "quantity") {
			title = "数量";
		}
		if ($(this).attr("id") == "price") {
			title = "单价";
		}
		if ($(this).attr("id") == "rebate") {
			title = "折扣";
		}

		if ($(this).val() == "") {
			alert(title + "不能为空");
			return;
		}

		if (isNaN($(this).val())) {
			alert(title + "必须为数字");
			$(this).attr("value", "");
			return;
		}
		var jj = parseFloat($(this).val());
		if (jj < 0) {
			alert(title + "不能为负数");
			$(this).attr("value", "");
			return;
		}

	});
	
	/**************************************************加载项目、物品****************************************/
	getProjectByParent(proID,$("inputbottom").val(),cashierBillsID);
	
	
	function getProjectByParent(ppID,parentName,cashierBillsID){
		$("#xmstbl").html("");
		var url = basePath+"/ea/splitbill/sajax_ea_getProductAndGoodsAjax.jspa?date="
					+ new Date().toLocaleString();
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{
			  "cuid":ppID,
			  "caid":cashierBillsID
			},
			success : function (data) {
				var member = eval("(" + data + ")");
				var oList = member.productList;
				var data1 = new Array();
				for (var i = 0; i < oList.length; i++) {
					data1[i] = {
							id : oList[i][0],
							pid : oList[i][1],
							text : oList[i][2]
					};
				}
				var ts = new TreeSelector($("ul#xmul"),
						data1, null);
				ts.createTree();
				$("#xmul").treeview({
					persist: "location",
		            collapsed: false,
		            unique: false
		        });
				var goodsList=member.goodsList;
				var goodsnum=0;
				for ( var i = 0; i < oList.length; i++) {
					var goodstr = "";
					for ( var j = 0; j < goodsList.length; j++) {
						var goodsbill=goodsList[j];
						if(oList[i][0]==goodsbill.ppID){
							goodstr+="<tr id='kelong"+goodsnum+"' class='checkgoods'>" +
							"<!-- 产品编号 -->"+
							"<td align='center' id='goodsNum'>"+
							"<input type='hidden' value='"+goodsbill.goodsBillsID+"' name='goodsmap["+goodsnum+"].goodsBillsID' id='goodsBillsID'/>"+
							"<span>"+goodsbill.goodsNum+"</span>"+
							"</td>"+
							"<!-- 产品名称 -->"+
							"<td align='center'>"+
							"<span>"+goodsbill.goodsName+"</span>"+
							"</td>"+
							"<!-- 款源日期 -->"+
							"<td align='center'>"+
							"<span>"+"</span>"+
							"<input name='goodsmap["+goodsnum+"].startDate' onfocus='date(this)' size='10' style='width:95%;display: inline;' value='"+(goodsbill.startDate==''||goodsbill.startDate==null?"":goodsbill.startDate)+"' class='model1 xiaoguo fou notnull' />"+
							"</td>"+
							"<!-- 报开学号 -->"+
							"<td class='baokx' align='center'>"+
							 "<span>"+ goodsbill.studentCode+"</span>"+
							"</td>"+
							"<!-- 学员期数 -->"+
							"<td class='baokx'  height='30' align='center'>"+
							"<span>"+ goodsbill.studentPeriods+"</span>"+
							"</td>"+
							"<!-- 报开学时间 -->"+
							"<td class='baokx'  height='30' align='center'>"+
							"<span>"+ goodsbill.schoolopendate+"</span>"+
							"</td>"+
							"<!-- 单位 -->"+
							"<td align='center'>"+
							"<span id='goodsVariableID'>"+ goodsbill.goodsVariableID+"</span>"+
							"</td>"+
							"<!-- 数量 -->"+
							"<td align='left'>"+
							"<input type='text'  id='quantity'  class='model1 fou jisuan'  size='7' name='goodsmap["+goodsnum+"].quantity' style='width:96%; display:inline;' value='"+goodsbill.quantity+"'/>"+
							"</td>"+
							"<!-- 单价管理 -->"+
							"<td align='center'>"+
							"<span>"+ goodsbill.priceManage+"</span>"+
							"</td>"+
							"<!-- 单价 -->"+
							"<td align='center'>"+
							"<input type='text'  id='price'  class='model1 fou jisuan'  size='7' name='goodsmap["+goodsnum+"].price' style='width:96%; display:inline;' value='"+goodsbill.price+"'/>"+
							"<span>"+"</span>"+
							"</td>"+
							"<!-- 折扣 -->"+
							"<td align='left'>"+
							"<input type='text'  id='rebate'  class='model1 fou jisuan'  size='7' name='goodsmap["+goodsnum+"].rebate' style='width:73%; display:inline;' value='"+(goodsbill.rebate==''||goodsbill.rebate==null?10:goodsbill.rebate)+"'/>折"+
							"</td>"+
							"<!-- 折后价 -->"+
							"<td align='left'>"+
							"<input type='text'  id='rebatePrice'  class='model1 fou'  size='7' readonly='readonly' name='goodsmap["+goodsnum+"].rebatePrice' style='width:97%; display:inline;' value='"+(goodsbill.rebatePrice==''||goodsbill.rebatePrice==null?goodsbill.price:goodsbill.rebatePrice)+"'/>"+
							"</td>"+
							"<!-- 预算金额 -->"+
							"<td align='center'>"+
							"<input type='text'  id='money'  class='model1 fou'  size='7' readonly='readonly' name='goodsmap["+goodsnum+"].money' style='width:97%; display:inline;' value='"+goodsbill.money+"'/>"+
							/*"<span id='money'>"+goodsbill.money+"</span>"+*/
							"</td>"+
							"<!-- 本次实收金额 -->"+
							"<td align='left'>"+
							"<input type='text'  id='realMoney'  class='model1 fou'  size='7' readonly='readonly' name='goodsmap["+goodsnum+"].realMoney' style='width:97%; display:inline;' value='"+(goodsbill.realMoney==''||goodsbill.realMoney==null?goodsbill.money:goodsbill.realMoney)+"'/>"+
							"</td>"+
							"<!-- 已收款 -->"+
							"<td align='left'>"+
							"<input type='text'  id='alreadyMoney'  class='model1 fou'  size='7' readonly='readonly' name='goodsmap["+goodsnum+"].alreadyMoney' style='width:97%; display:inline;' value='"+goodsbill.alreadyMoney+"'/>"+
							/*"<span id='alreadyMoney'>"+goodsbill.alreadyMoney+"</span>"+*/
							"</td>"+
							"<!-- 未收款 -->"+
							"<td align='left'>"+
							"<input type='text'  id='futureMoney'  class='model1 fou'  size='7' readonly='readonly' name='goodsmap["+goodsnum+"].futureMoney' style='width:97%; display:inline;' value='"+(goodsbill.futureMoney==''||goodsbill.futureMoney==null?goodsbill.money:goodsbill.futureMoney)+"'/>"+
							"</td>"+
							"<td align='left'>"+
							"<span>"+ goodsbill.ccompanyName+"</span>"+
							"</td>"+
							"<td align='left'>"+
							"<span>"+ goodsbill.connectName+"</span>"+
							"</td>"+
							"<td align='center'>无</td>" ;
							goodsnum++;
						}
					}
					if(goodstr!=""){
						goodstr="<table id='"+oList[i][0]+"tbl' class='datatbl'><tr><td><table class='table bg auto_arrange .goodtable2' id='goodtable2'>" +
						"<tr>" +
						"<th align='center' bgcolor='#E4F1FA' width='80px' >"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>品名编号"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='60px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>品名名称"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='80px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>"+"<span class='xx'>*</span>款源日期"+
					"</th>"+
					"<th class='baokx' align='center' bgcolor='#E4F1FA' width='80px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>报开学号"+
					"</th>"+
					"<th class='baokx' align='center' bgcolor='#E4F1FA' width='80px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>学员期数"+
					"</th>"+
						"<th class='baokx' align='center' bgcolor='#E4F1FA' width='90px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>报开学时间"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='50px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span> 单位"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='50px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>"+"<span class='xx'></span>数量"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='60px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span> 单价管理"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='50px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>"+"<span class='xx'>*</span>单价"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='60px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>"+"<span class='xx'>*</span>折扣"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='70px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>"+"<span class='xx'>*</span>折后价"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='70px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>预算金额"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='75px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>"+"<span class='xx'>*</span>本次实收款"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='60px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>"+"<span class='xx'>*</span>已收款"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='60px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>"+"<span class='xx'>*</span>未收款"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='100px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>往来公司"+
					"</th>"+
					"<th align='center' bgcolor='#E4F1FA' width='90px'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span>往来个人"+
					"</th>"+
					"<th align='center' width='50px' bgcolor='#E4F1FA'>"+
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);' onMouseMove='MouseMoveToResize(this);' onMouseUp='MouseUpToResize(this);'>"+
						"<img src='<%=basePath%>+/images/header_bg.gif' width='1' height='22' />"+
						"</span> 操作"+
					"</th></tr>"+goodstr;
						goodstr+="</table>";
						$("li#"+oList[i][0]).find("span").eq(0).after(goodstr);
					}
				}				
			},error:function(data){
				alert("获取项目失败");
			}
			});
	}
		
});

/******************************************银行账号*********************************************/
//选择往来个人
$(document).ready(function() {
		var bankID = "";
		var registrationID = "";		
		var bunid="";
		
		$("table#Banktable tr[id]").live("click",function(event) {
			bankID = this.id;
			registrationID = this.title;
			$("input.ra", $(this)).attr("checked","checked");
		});
		
		// 选择银行账号
		$(".nwlgr").click(function() {
			bunid=$(this).attr("id");
			$(".jqmWindow", $("#selectnumForm")).jqmShow();
			$("#ntype").val($(this).parent().prev().prev().find("input[type=hidden]").val());
			cnwlgr("numName=&numtype="+$("#cutype").val()+"&cuid="+$("#ntype").val());
		});
		

							// 添加所选中的往来个人
					$("#qdnum").click(function() {
							if (bankID != "") {
								$("td." + bunid).find("input:eq(0)").val($("tr#" + bankID).find("#bankaccount").text());
								$(".jqmWindow", $("#selectnumForm")).jqmHide();
								$(".wlgr").parent().removeClass("rwl");
								$("#ntype").val("");
								$("#body_num").empty();
							} else {
								alert("请选择！");
							}
					});

		// 根据输入的往来个人名称查询
		$("input#searchnum").click(
				function() {
					bankID = "";
					registrationID = "";	
					var typeName = $("input#numID",
							$("table#searchnum")).val();
					cnwlgr("numName=" + typeName
							+ "&type=" + $("#cutype").val()+"&cuid="+$("#ntype").val());
		});

		// 上一页
		$("#nrsy").click(
				function() {
					var sy = $("#grsy").attr("title");
					if (sy != 0) {
						cuID = "";
						userOrCompanyID = "";
						var typeName = $("input#numID",
								$("table#searchnum")).val();
						cnwlgr("numName=" + typeName
								+ "&type=" + $("#cutype").val()+"&cuid="+$("#ntype").val()+ "&pageForm.pageNumber=" + sy);
					} else {
						alert("已是首页！");
					}
				});

		// 下一页
		$("#nrxy").click(
				function() {
					var xy = $("#grxy").attr("title");
					if (xy != 0) {
						cuID = "";
						userOrCompanyID = "";
						var typeName = $("input#numID",
								$("table#searchnum")).val();
						cnwlgr("numName=" + typeName
								+ "&type=" + $("#cutype").val()+"&cuid="+$("#ntype").val()+ "&pageForm.pageNumber=" + sy);
					} else {
						alert("已是尾页！");
					}
				});
		
		// ajax查询往来个人列表
		function cnwlgr(typeCN) {
			if (notoken) {
				alert("正在获取数据！请稍等");
				notoken=0;
				return;
			}
			$("#nrsy").attr("title", 0);
			$("#nrxy").attr("title", 0);
			$("#nrzy").attr("title", 0);
			notoken = 1;
			
			$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"100%"}).appendTo("#companyjqModel");     
		    $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载。。。").appendTo("#companyjqModel").css({display:"block",left:($("#companyjqModel").outerWidth(true)-100),top:($("#companyjqModel").outerHeight(true) - 45) / 2});
			var searchurl = basePath+ "ea/cashapplybills/sajax_ea_getBankAccountList.jspa?";
			$.ajax({
				url : encodeURI(searchurl + typeCN+ "&date="+ new Date().toLocaleString()),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}

					var pageForm = member.pageForm;
					if (pageForm == null) {
						$("#companyjqModel").find("div.datagrid-mask-msg").remove();
			            $("#companyjqModel").find("div.datagrid-mask").remove();
						alert("没有数据");
						notoken = 0;
						return;
					}

					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#nrsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#nrxy").attr("title", dqy + 1);
					}
					$("span#grzycount").text(zys);
					var tabletr = "<table width='99%' align='center' id='Banktable' cellpadding='0'  cellspacing='0' class='table'>";
					tabletr += "<tr><th height='21' align='center' width='30' bgcolor='#E4F1FA'>选择</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='30' >序号</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='70' >账号编码</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='120' >银行名称</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='70' >开户账号</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='100' >开户时间</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='90' >责任人</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='120' >开户登记证明号</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='100' >发证日期</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='100' >账户性质</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='100' >注销时间</th>"
							+ "<th align='center' bgcolor='#E4F1FA' width='120' >开户银行地址</th>"
							+ "</tr>";
					for ( var i = 0; i < pageForm.list.length; i++) {
						tabletr += "<tr style='cursor: hand;' title="
							+ pageForm.list[i].bankAccount
							+ " id = "
							+ pageForm.list[i].registrationID
							+ ">";
						tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
								+ pageForm.list[i].registrationID
								+ " name='checkradio'/></td>";
						tabletr += "<td align='center'>"
								+ (i + 1) + "</td>";
						tabletr += "<td id='accountNum' align='center'>"
								+ pageForm.list[i].accountNum
								+ "</td>";
						tabletr += "<td id='bankName' align='center'>"
								+ pageForm.list[i].bankName
								+ "</td>";
						tabletr += "<td id='bankaccount' align='center'>"
								+ pageForm.list[i].bankAccount
								+ "</td>";
						tabletr += "<td id='openAccountDate'  align='center'>"
								+ pageForm.list[i].openAccountDate
								+ "</td>";
						tabletr += "<td id='conPerson' align='center'>"
								+ pageForm.list[i].conPerson
								+ "</td>";
						tabletr += "<td id='bankRegistrationID' align='center'>"
								+ pageForm.list[i].bankRegistrationID
								+ "</td>";
						tabletr += "<td id='registrationDate'  align='center'>"
								+ pageForm.list[i].registrationDate
								+ "</td>";
						tabletr += "<td id='accountNature'  align='center'>"
								+ pageForm.list[i].accountNature
								+ "</td>";
						tabletr += "<td id='cancellationDate'  align='center'>"
								+ pageForm.list[i].cancellationDate
								+ "</td>";
						tabletr += "<td id='bankAddr'  align='center'>"
								+ pageForm.list[i].bankAddr
								+ "</td>";
						tabletr += " </tr>";
					}
					tabletr += " </table>";
					$("#body_num").html(tabletr);
					$("#body_num").show();
					// alert("数据加载成功")
					notoken = 0;
					window.status = "数据加载成功";
					$("#companyjqModel").find("div.datagrid-mask-msg").remove();
		            $("#companyjqModel").find("div.datagrid-mask").remove();
				},
				error : function cbf(data) {
					$("#companyjqModel").find("div.datagrid-mask-msg").remove();
		            $("#companyjqModel").find("div.datagrid-mask").remove();
					notoken = 0;
					alert("数据获取失败！");
				}
			});
		}
	});

/** **********************************往来个人、往来单位**************************************** */
/** **********************************往来个人**************************************** */
//选择往来个人
$(document).ready(function() {
	var getcodeurl = basePath
		+ "ea/cashapplybills/sajax_ea_AjaxgetCompanyAndStaff.jspa?date="
		+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(getcodeurl),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath
						+ "page/ea/not_login.jsp";
			}
			var StaffList = member.StaffList;
			var typeName = $("input#comUserID",
					$("table#searchuser")).val();
			var result="<ul id='browser' class='filetree'>";
			result+="<li><a href='#'><span class='folder' onclick='javascript:cxwlgr(\"contactUser.staffName="+typeName+"&contactUser.relation=\",\"\")'>往来个人</span></a>";
			
			if (null != StaffList) {
			   result+="<ul id='browser2' class='filetree'>";
            for (var i = 0; i < StaffList.length; i++) {  
         	   result+="<li id='"+StaffList[i].codeID+"'><a href='#'><span class='file' onclick='javascript:cxwlgr(\"contactUser.staffName="+typeName+"&contactUser.relation="+StaffList[i].codeValue+"\",\""+StaffList[i].codeValue+"\")'>"+StaffList[i].codeValue+"</span></a></li>";  
            }
            result+="</ul></li>";
			}
			var ComanyList = member.ComanyList;
			result+="<li><a href='#'><span class='folder' onclick='javascript:cxwldw(\"contactCompany.companyName="+typeName+"&cconnection.contactConnections=\",\"\")'>往来单位</span></a>";
			if (null != ComanyList) {
			   result+="<ul id='browser2' class='filetree'>";
            for (var i = 0; i < ComanyList.length; i++) {
         	   result+="<li id='"+ComanyList[i].codeID+"'><a href='#'><span class='file' onclick='javascript:cxwldw(\"contactCompany.companyName="+typeName+"&cconnection.contactConnections="+ComanyList[i].codeValue+"\",\""+ComanyList[i].codeValue+"\")'>"+ComanyList[i].codeValue+"</span></a></li>";  
            }
            result+="</ul></li>";
			}
			result+="</ul>";
			$(result).appendTo("#dwTree");
         $("#browser").treeview({
         	persist: "location",
   	      	collapsed: true
         });
//         browser="";
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

		var cuID = "";
		var userOrCompanyID = "";
		$("table#gouserstable tr[id]").live("click",function(event) {
			cuID = this.id;
			userOrCompanyID = this.title;
			$("input.rauser", $(this)).attr("checked","checked");
		});
		
		var bunid="";
		// 选择往来个人
		$(".wlgr").click(function() {
			bunid=$(this).attr("id");
			$(".jqmWindow", $("#selectuserForm")).jqmShow();
			cxwlgr("contactUser.staffName=&contactUser.relation=","");
		});
		
		// 添加所选中的往来个人
		$("#qduser").click(function() {
			if (userOrCompanyID != "") {
				$("td."+bunid).children("input").each(function(){
					if($(this).attr("id")=="appropriationcompanyID"||$(this).attr("id")=="ReceivablesID"){
						$("#"+$(this).attr("id")).val(userOrCompanyID);
					}else if($(this).attr("id")=="appropriationcompanyName"||$(this).attr("id")=="ReceivablesName"){
						$("#"+$(this).attr("id")).val($("tr#" + cuID).find("#UserOrComName").text());
					}
				});
				$(".jqmWindow", $("#selectuserForm")).jqmHide();
				$(".wlgr").parent().removeClass("rwl");
				return;
			} else {
				alert("请选择！");
			}
		});

		// 根据输入的往来个人名称查询
		$("input#searchuu").click(function() {
			notoken=0;
			cuID = "";
			userOrCompanyID = "";
			var typeName = $("input#comUserID",
					$("table#searchuser")).val();
			var relation = $(":input#grparms").val();
			var cutype=$("#cutype").val();
			if(cutype=="com"){
				var typeCN ="contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections="
					+ relation;
				cxwldw(typeCN,typeName);
			}
			if(cutype=="users"){
				var typeCN= "contactUser.staffName="
					+ typeName
					+ "&contactUser.relation="
					+ relation;
				cxwlgr(typeCN,typeName);
			}
			$("input#comUserID",
					$("table#searchuser")).val("");
			
		});

		// 上一页
		$("#grsy").click(function() {
			var sy = $("#grsy").attr("title");
			if (sy != 0) {
				cuID = "";
				userOrCompanyID = "";
				var typeName = $("input#comUserID",
						$("table#searchuser")).val();
				var relation = $(":input#grparms").val();
				var cutype=$("#cutype").val();
				if(cutype=="com"){
					var typeCN ="contactCompany.companyName=" + typeName
						+ "&cconnection.contactConnections="
						+ relation+ "&pageForm.pageNumber="
						+ sy;
					cxwldw(typeCN,typeName);
				}
				if(cutype=="users"){
					var typeCN= "contactUser.staffName="
						+ typeName
						+ "&contactUser.relation="
						+ relation
						+ "&pageForm.pageNumber=" + sy;
					cxwlgr(typeCN,typeName);
				}
			} else {
				alert("已是首页！");
			}
		});

		// 下一页
		$("#grxy").click(function() {
			var xy = $("#grxy").attr("title");
			if (xy != 0) {
				cuID = "";
				userOrCompanyID = "";
				var typeName = $("input#comUserID",
						$("table#searchuser")).val();
				var relation = $(":input#grparms").val();
				var cutype=$("#cutype").val();
				if(cutype=="com"){
					var typeCN ="contactCompany.companyName=" + typeName
						+ "&cconnection.contactConnections="
						+ relation+ "&pageForm.pageNumber=" + xy;
					cxwldw(typeCN,typeName);
				}
				if(cutype=="users"){
					var typeCN= "contactUser.staffName="
						+ typeName
						+ "&contactUser.relation="
						+ relation
						+ "&pageForm.pageNumber=" + xy;
					cxwlgr(typeCN,typeName);
				}
			} else {
				alert("已是尾页！");
			}
		});
	});

//ajax查询往来个人列表
function cxwlgr(typeCN,cutype) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	$("#cutype").val("users");
	$("input#grparms",$("table#searchuser")).val(cutype);
	notoken = 1;
	$("#grsy").attr("title", 0);
	$("#grxy").attr("title", 0);
	$("#grzy").attr("title", 0);
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"100%"}).appendTo("#companyjqModel2");     
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载。。。").appendTo("#companyjqModel2").css({display:"block",left:($("#companyjqModel2").outerWidth(true)-100),top:($("#companyjqModel2").outerHeight(true) - 45) / 2});
	var searchurl = basePath+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";
	$.ajax({
		url : encodeURI(searchurl + typeCN+ "&date="+ new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath
						+ "page/ea/not_login.jsp";
			}

			var pageForm = member.pageForm;
			if (pageForm == null) {
				$("#companyjqModel2").find("div.datagrid-mask-msg").remove();
	            $("#companyjqModel2").find("div.datagrid-mask").remove();
				alert("没有数据");
				notoken = 0;
				return;
			}

			var dqy = pageForm.pageNumber;// 当前页
			var zys = pageForm.pageCount;// 总页数
			if (dqy > 1) {
				$("#grsy").attr("title", dqy - 1);
			}
			if (dqy < zys) {
				$("#grxy").attr("title", dqy + 1);
			}
			$("span#grzycount").text(zys);
			var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
			tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th><th align='center' bgcolor='#E4F1FA'>家庭地址</th></tr>";
			for ( var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i].relationID
						+ " title= "
						+ pageForm.list[i].staffID
						+ ">";
				tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
						+ pageForm.list[i].relationID
						+ " name='checkradio'/></td>";
				tabletr += "<td id='UserOrComName' align='center'>"
						+ pageForm.list[i].staffName
						+ "</td>";
				tabletr += "<td id='phone' align='center'>"
						+ pageForm.list[i].relation
						+ "</td>";
				tabletr += "<td id='tel' align='center'>"
						+ pageForm.list[i].reference
						+ "</td>";
				tabletr += "<td id='staffIdentityCard' align='center'>"
						+ pageForm.list[i].staffIdentityCard
						+ "</td>";
				tabletr += "<td id='userQq'  align='center'>"
						+ pageForm.list[i].referenceCode
						+ "</td>";
				tabletr += "<td id='email'  align='center'>"
						+ pageForm.list[i].referenceOrganization
						+ "</td>";
				tabletr += "<td id='userAddr'  align='center'>"
						+ pageForm.list[i].staffAddress
						+ "</td>";
				tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
						+ pageForm.list[i].staffID
						+ "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02cu").html(tabletr);
			$("#body_02cu").show();
			// alert("数据加载成功")
			notoken = 0;
			window.status = "数据加载成功";
			$("#companyjqModel2").find("div.datagrid-mask-msg").remove();
            $("#companyjqModel2").find("div.datagrid-mask").remove();
		},
		error : function cbf(data) {
			$("#companyjqModel2").find("div.datagrid-mask-msg").remove();
            $("#companyjqModel2").find("div.datagrid-mask").remove();
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}

//ajax查询往来单位列表
function cxwldw(typeCN,cutype) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	$("#cutype").val("com");
	$("input#grparms",$("table#searchuser")).val(cutype);
	notoken = 1;
	$("#grsy").attr("title", 0);
	$("#grxy").attr("title", 0);
	$("#grzy").attr("title", 0);
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"100%"}).appendTo("#companyjqModel2");     
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载。。。").appendTo("#companyjqModel2").css({display:"block",left:($("#companyjqModel2").outerWidth(true)-100),top:($("#companyjqModel2").outerHeight(true) - 45) / 2});
	var searchurl = basePath+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
	$.ajax({
		url : encodeURI(searchurl + typeCN+ "&date="+ new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath
						+ "page/ea/not_login.jsp";
			}
			var pageForm = member.pageForm;
			if (pageForm == null) {
				$("#companyjqModel2").find("div.datagrid-mask-msg").remove();
	            $("#companyjqModel2").find("div.datagrid-mask").remove();
				alert("没有数据");
				notoken = 0;
				return;
			}

			var dqy = pageForm.pageNumber;// 当前页
			var zys = pageForm.pageCount;// 总页数
			if (dqy > 1) {
				$("#grsy").attr("title", dqy - 1);
			}
			if (dqy < zys) {
				$("#grxy").attr("title", dqy + 1);
			}
			$("span#grzycount").text(zys);
			var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
			tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
			tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
			tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
			tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
			tabletr += " <th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
			for ( var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' title="
						+ pageForm.list[i].ccompanyID
						+ " id = "
						+ pageForm.list[i].contactConnectionID
						+ ">";
				tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
						+ pageForm.list[i].contactConnectionID
						+ " name='checkradio'/></td>";
				tabletr += "<td id='UserOrComName' align='center'>"
						+ pageForm.list[i].companyName
						+ "</td>";
				tabletr += "<td id='contactConnections' align='center'>"
						+ pageForm.list[i].contactConnections
						+ "</td>";
				tabletr += "<td id='industryType' align='center'>"
						+ pageForm.list[i].industryType
						+ "</td>";
				tabletr += "<td id='companyTel'  align='center'>"
						+ pageForm.list[i].companyTel
						+ "</td>";
				tabletr += "<td id='cresponsible' align='center'>"
						+ pageForm.list[i].cresponsible
						+ "</td>";
				tabletr += "<td id='responsibleTel' align='center'>"
						+ pageForm.list[i].responsibleTel
						+ "</td>";
				tabletr += "<td id='companyAddr'  align='center'>"
						+ pageForm.list[i].companyAddr
						+ "</td>";
				tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
						+ pageForm.list[i].ccompanyID
						+ "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02cu").html(tabletr);
			$("#body_02cu").show();
			// alert("数据加载成功");
			notoken = 0;
			window.status = "数据加载成功";
			$("#companyjqModel2").find("div.datagrid-mask-msg").remove();
            $("#companyjqModel2").find("div.datagrid-mask").remove();
		},
		error : function cbf(data) {
			$("#companyjqModel2").find("div.datagrid-mask-msg").remove();
            $("#companyjqModel2").find("div.datagrid-mask").remove();
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}
function re_load() {
	if(token){
		self.opener.location.reload(); //刷新父窗口
		 window.close();
	}
	// var url = basePath +
	// "/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=00&type=00";
	// parent.document.location.href = url;
}
function showGood() {
	$("#body_02").html("");
	$("#selectType").val("projects");
	$(".jqmWindow", $("#goodsForm")).jqmShow();
}

function close3() {
	$("#jqModel3").hide();
	$("#query").html("");
}
