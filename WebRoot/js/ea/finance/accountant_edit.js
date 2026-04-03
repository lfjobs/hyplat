$(function() {
	self.opener.location.reload(); //刷新父窗口 
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
	// 
	}).jqmAddClose('.close');//
	
	 if(status=="41"){
		 $(".audit").attr("disabled",true).addClass("grey");
		 $(".JQuerySubmitgd").attr("disabled",false).removeClass("grey");
		 $("#spappstyle").hide();
		 $("#appstyle").show();
	 }else if(status=="42"){
		 $("#audittbl").find(":input").each(function(){
			if(this.value==staffname){
				$(".pass").attr("disabled",false).removeClass("grey");
				$(".nopass").attr("disabled",false).removeClass("grey");
			}
		 });
		 $(".audit").attr("disabled",false).removeClass("grey");
		 $(".JQuerySubmitgd").attr("disabled",true).addClass("grey");
		 $("#add").hide();
		 $("#audittbl").show();
		 $(".sfk").hide();
		 $(".verify").attr("disabled",false);
		 $("input[type=text]").attr("readonly","readonly");
		 $("#spappstyle").show();
		 $("#appstyle").hide();
	 }else if(status=="09"){
		 $(".JQuerySubmitgd").attr("disabled",true).addClass("grey");
		 $(".qrsk").attr("disabled",false).removeClass("grey");
		 $(".generate").attr("disabled",true).addClass("grey");
		 $("#spappstyle").show();
		 $("#appstyle").hide();
	 }else if(status=="45"||status=="46"){
		 $(".JQuerySubmitgd").attr("disabled",true).addClass("grey");
		 $(".qrsk").attr("disabled",true).addClass("grey");
		 $(".generate").attr("disabled",false).removeClass("grey");
		 $("#spappstyle").show();
		 $("#appstyle").hide();
	 }
		if(sta=="view"){
	   		$(".menubtn").attr("disabled",true).addClass("grey");
	   		$(".closewindow").attr("disabled",false).removeClass("grey");
	   	}
	$("#titlestyle").find("span").text(billsType=="拨款单"&&status=="41"?"项目支出预算单":billsType);
	$("#titlestyle").find("input").val(billsType);
	
	 //关闭
	 $(".closewindow").click(function(){
		 if(confirm("确定要关闭添加窗口？")){
			 window.close();
		 }
		 
	 });
	 
	//打印
	 $(".print").click(function(){
		 alert(billsType);
		 if(billsType=="预入款单"){
		     $("#jqModelbill").jqmShow();
		 }else if(billsType=="收款单"){
			 $("#jqModelsbill").jqmShow();
		 }else{
		 window.open(basePath
					+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
					+ cashierBillsID);
		 }
	 });
	 
	 $("#printprev").click(function(){
			var billTypese = $("#selectbilltype").val();
			if(billTypese=="请选择"){
				alert("请选择要生成的单据类别");
				return;
			}
			var cashierBillsID =  $("#CashApplyBillsform").find("#cashierBillsID").val()
			var url = basePath+"/ea/cashier/sajax_ea_IsGenerate.jspa?cashierBillsID="+cashierBillsID;
         	$("#printprev").attr({disabled:"disabled"});//防止重复点击重复生成
			$.ajax({
				url:encodeURI(url),
				type:"Get",
				dataType:"json",
				async:true,
				success:function (data) {
					var member = eval("("+data+")");
					var count = member.count;
					if(count == 0 || billTypese!="采购单"){
                        if(confirm("确定生成"+billTypese+"吗？")){
                            $("#CashApplyBillsform").attr("target","hidden")
                                .attr(
                                    "action",
                                    basePath
                                    + "/ea/cashier/ea_generateSaleSheet.jspa?billTypese="+billTypese);
                            document.CashApplyBillsform.submit.click();
                            token = 2;
                        }
					}else {
						alert("已生成过的不能再次生成！")
					}
                }
			})

			/* window.open(basePath
						+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
						+ cashierBillsID+"&billTypese="+billTypese);*/
		});
	 
	//生成销售单
		$(".generate").click(function(){
			$("#jqModelbill").jqmShow();
		});
	 
	 //拨款
	 $("#pass").click(function(){
		if(notoken){
			alert("正在提交数据，请稍等·····");
			return;
		}
		notoken = 1;
		if ($("#CashApplyBillsform .error").length) {
			$("input.posnum").each(function(){
				if($.trim($(this).val())==''){
					$(this).css("background-color","red");
				}
			});
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		var bj=0;
		$("tr[id]",$("#CashApplyBillsform")).each(function(){
			var sqje=parseFloat($.trim($(this).find("#money").text()));
			var pzje=parseFloat($.trim($(this).find("#appropriationMoney").attr("value")));
			if(pzje>sqje){
				$(this).find("#appropriationMoney").css("color","red");
				$(this).find("#appropriationMoney").css("background-color","blue");
				bj++;
			}
		});
		if(bj){
			alert("批准金额不得大于申请金额！！");
			notoken = 0;
			return;
		}
		title="";
		$("#CashApplyBillsform").attr("target","hidden")
			.attr(
					"action",
					basePath
							+ "ea/cashapplybills/ea_savecash.jspa?");
		document.CashApplyBillsform.submit.click();
	    bokuan="";
		token = 2;
	});
		
	//暂不拨款
	$("#nopass").click(function(){
		if(notoken){
			alert("正在提交数据，请稍等·····");
			return;
		}
		notoken=1;
		$("#CashApplyBillsform").attr("target","hidden")
			.attr("action",basePath
							+ "ea/cashapplybills/ea_saveNoCash.jspa");
		document.CashApplyBillsform.submit.click();
		notoken=0;
	    bokuan="";
		token = 2;
	});
	
	$("#qrsk").click(function(){
		$("#CashApplyBillsform").attr("target","hidden")
		.attr("action",basePath
 						+ "/ea/splitbill/ea_confirmReceivables.jspa");
		document.CashApplyBillsform.submit.click();
	    bokuan="";
		token = 2;
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
		$(".pass").attr("disabled",false).removeClass("grey");
		$(".nopass").attr("disabled",false).removeClass("grey");
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
	
	//处理单价管理
	$(".dis").each(function(){
		var kelongid = $(this).parent().parent().parent().attr("id");
		var id = "";
		if(kelongid!=""&&kelongid.length>6){
			id=kelongid.substring(6);
			$(this).attr("name","logbookmap["+id+"].priceManage");
			$(this).val($(this).parent().find("span").text());
		}
	});

	
	$(".datatbl").find("tr").live("click",function(){
		$("#line").val($(this).attr("id"));
	});
	
	// 物品表格单击样式问题
	$(".datatbl :input").live("click",function() {
		$(".datatbl td").find("div").each(function() {
			$(this).css("border", "none");
			$(this).find(".caz").hide();
		});
		$(this).parent("div").css("border", "1px solid #000000");
		$(this).parent("div").find(".fou").focus();
		$(this).parent("div").find(".caz").show();
		
	});
	
	$(".datatbl td").live("click",function() {
		$(".datatbl td").each(function() {
			$(this).find("div").css("border", "none");
			$(this).find(".caz").hide();
		});
		$(this).find("div").css("border", "1px solid #000000");
		$(this).find(".fou").focus();
		$(this).find(".caz").show();

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
		var kelongid = $("#line").val();
		if (types == "goodsNum"||types=="goodsName"||types=="ccompanyName"||types=="connectName") {
			$trs.find("td").each(function(){
				 $("tr#"+kelongid).find("#"+this.id).val($(this).text());
			});
		}
		$("input.rauser", $(this)).attr("checked", "checked");
		$("#goodsQuery").hide();
	});

	// 键入时查询项目分类
	$("#xmtypename").focus(function() {
		getCate($(this));
	}).keyup(function() {
		getCate($(this));
	});

	// 各个弹出框的关闭功能
	$(".JQueryreturns").click(function() {
		notoken = 0;
		$(".jqmWindow").jqmHide();
	});
	
	// 添加页面的返回功能；
	$(".JQueryClose").click(function() {
		notoken = 0;
		re_load();
	});

	// 选择物品弹出带有物品分类的弹出框
	$("#shuju").click(function() {
		$(".jqmWindow", $("#goodsForm")).jqmShow();
	});

	

	$("select#depotType").change(function() {
		var kufang = $(this).val();
		var jie = $(this).parent().parent().find(":input#forLoan");
		var dai = $(this).parent().parent().find(":input#loan");
		var qita = $(this).parent().parent().find(":input#username5");
		var fangxiang = $(this).parent().parent().find("input#direction");
		var qitajine = $(this).parent().parent().find(":input#otherAmount");
		if ($(jie).val() != '') {
			jj(kufang, fangxiang, dai, jie, qita, $(jie).val(), qitajine);
		} else if ($(dai).val() != '') {
			jj(kufang, fangxiang, dai, jie, qita, $(dai).val(), qitajine);
		} else if ($(qita).val() != '') {
			jj(kufang, fangxiang, dai, jie, qita, $(qita).val(), qitajine);
		} else if ($(qitajine).val() != '') {
			jj(kufang, fangxiang, dai, jie, qita, $(qitajine).val(), qitajine);
		} else {
			jj(kufang, fangxiang, "", "", "", "", "");
		}
	});

	// 迭代的商品删除
	$(".ajaxsc")
			.click(
					function() {
						var taxstatus = $("#cashiertaxstatus").val();
						if (taxstatus != "00") {
							alert("已归档不能删除该条数据！");
							return;
						}
						if (confirm("是否删除？")) {
							var goodsBillsID = $(this).parent().find(
									"input#goodsBillsID").val();
							var delurl = basePath
									+ "ea/cashierbills/sajax_ea_delGoodsBills.jspa?typeID="
									+ goodsBillsID + "&date="
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
									var typeID = member.typeID;
									alert("删除成功！");
									$("tr#" + typeID).remove();
								},
								error : function cbf(data) {
									alert("数据获取失败！");
								}
							});
						}
					});

	// 克隆的商品删除
	$(".klsc").click(function() {
		$(this).parent().parent().remove();
		docNull -= 1;
	});

	// 保存预算
	$("input.JQuerySubmitgd").click(function() {
		if(notoken){
			alert("正在提交数据，请稍等···");
			return;
		}
		notoken = 1;
		if ($("#CashApplyBillsform .error").length) {
			$("input.posnum").each(function(){
				if($.trim($(this).val())==''){
					$(this).css("background-color","red");
				}
			});
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		var bj=0;
		$("tr[id]",$("#CashApplyBillsform")).each(function(){
			var sqje=parseFloat($.trim($(this).find("#money").text()));
			var pzje=parseFloat($.trim($(this).find("#appropriationMoney").attr("value")));
			if(pzje>sqje){
				$(this).find("#appropriationMoney").css("color","red");
				$(this).find("#appropriationMoney").css("background-color","blue");
				bj++;
			}
		});
		if(bj){
			alert("批准金额不得大于申请金额！！");
			notoken = 0;
			return;
		}
		title="";
		$("#CashApplyBillsform").attr("target","hidden")
			.attr(
					"action",
					basePath
							+ "ea/cashapplybills/ea_SaveCashApplyBills.jspa?");
		document.CashApplyBillsform.submit.click();
		notoken=0;
	    bokuan="";
		token = 2;
	});
	
	// 生成收款单
	$("input.shengcheng").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		document.location.href=basePath+ "ea/splitbill/ea_generateNewBill.jspa?cashierBills.cashierBillsID="+ cashierBillsID;
		return;
	});
	
	/**************************************************加载项目、物品****************************************/
	getProjectByParent(proID,$("inputbottom").val(),cashierBillsID);
	
	
	function getProjectByParent(ppID,parentName,cashierBillsID){
		$("#xmstbl").html("");
		var url = basePath+"ea/cashapplybills/sajax_ea_getProductAndGoodsAjax.jspa?date="
					+ new Date().toLocaleString();
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{
			  "cuid":ppID,
			  "str":cashierBillsID
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
						if(oList[i][0]==goodsList[j][30]){
							var arr=goodsList[j];
							goodstr+="<tr id='kelong"+goodsnum+"' class='checkgoods'>" +
								" <input name='cash["+goodsnum+"].goodsBillsID' type='hidden' value='"+(arr[0]==null?"":arr[0])+"'/>" +
								"<!-- 序号 -->" +
								" <td align='center' bgcolor='#FFFFFF'>" +
								"<span>"+(arr[1]==null?"":arr[1])+"</span>" +
								" </td>" +
								"<!-- 产品名称 -->" +
								"<td align='center' bgcolor='#FFFFFF'>" +
								"<span>"+(arr[2]==null?"":arr[2])+"</span>" +
								"</td>" +
								"<td height='30' align='center' bgcolor='#FFFFFF' width='50px'>" +
								"<input name='cash["+goodsnum+"].appropriationDate' onfocus='date(this)' size='10' style='width:95%;display: inline;' value='"+(arr[3]==null?(new Date()).toLocaleDateString() :arr[3])+"' class='model1 xiaoguo fou' />" +
								"</td>" +
								"<!-- 单位 -->" +
								"<td align='center' bgcolor='#FFFFFF'>" +
								"<span id='goodsVariableID'>"+(arr[4]==null?"":arr[4])+"</span>" +
								"</td>" +
								"<!-- 数量 -->" +
								"<td align='left' bgcolor='#FFFFFF'>" +
								"<span>"+(arr[5]==null?"":arr[5])+"</span>" +
								" </td>" +
								"<td align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[6]==null?"":arr[6])+"</span>" +
								"</td>" +
								"<!-- 单价 -->" +
								"<td align='center' bgcolor='#FFFFFF'>" +
								"<span>"+(arr[7]==null?"":arr[7])+"</span>" +
								"</td>" +
								"<!-- 总金额 -->" +
								"<td align='center' bgcolor='#FFFFFF'>" +
								"<span>"+(arr[8]==null?"":arr[8])+"</span>" +
								"</td>" +
								"<!-- 拨款金额 -->" +
								"<td><input type='text'" +
								"name='cash["+goodsnum+"].appropriationMoney' id='appropriationMoney' size='5' value='"+(arr[9]==null?arr[8]:arr[9])+"'  style='border: 0;width: 100%;height: 100%;color: red;background-color: rgb(213, 239, 252)' />" +
								"<!-- ${weibokuan=='weibokuan'?'disabled':''} -->" +
								"</td>" +
								"<!-- 拨款方 -->" +
								"<td align='center' class='appC'>" +
								"<input type='hidden' id='appropriationcompanyID' name='cash["+goodsnum+"].appropriationcompanyID'  value='"+(arr[10]==null?"":arr[10])+"'/>" +
								"<input type='text'  id='appropriationcompanyName'  class='querys model1 fou'  size='7'" +
								"name='cash["+goodsnum+"].appropriationcompanyName' style='width:70%; display:inline;' value='"+(arr[11]==null?"":arr[11])+"'/>" +
								"<input type='button' class='wlgr querybtn caz'  id='appC'/>" +
								"</td>" +
								"<!-- 拨款帐号 -->" +
								"<td class='anum'><input type='text' name='cash["+goodsnum+"].appropriationNum' value='"+(arr[12]==null?"":arr[12])+"' size='5' style='border: 0;width:75%; height: 100%;' id='appropriationNum' class='bankaccount'/>" +
								"<input type='hidden' name='cash["+goodsnum+"].appropriateStatus' value='"+(arr[13]==null?"":arr[13])+"' readonly='readonly' />" +
								"<input type='button' class='nwlgr querybtn caz'  id='anum'/>" +
								"</td>" +
								"<!-- 收款方 -->" +
								"<td class='rece'><input type='hidden' id='ReceivablesID' name='cash["+goodsnum+"].ReceivablesID'  value='"+(arr[14]==null?"":arr[14])+"'/>" +
								"<input type='text'  id='ReceivablesName'  class='querys model1 fou'  size='7'" +
								"name='cash["+goodsnum+"].ReceivablesName' style='width:70%; display:inline;' value='"+(arr[15]==null?"":arr[15])+"'/>" +
								"<input type='button' class='wlgr querybtn caz'  id='rece'/>" +
								"</td>" +
								"<!-- 收款帐号 -->" +
								"<td align='center' class='rnum'><input type='text'" +
								"name='cash["+goodsnum+"].recropriationNum' size='5'" +
								"value='"+(arr[16]==null?"":arr[16])+"' style='border: 0;width: 75%;height: 100%;' id='recropriationNum' class='bankaccount'/>" +
								"<input type='button' class='nwlgr querybtn caz'  id='rnum'/>" +
								"</td>" +
								"<!-- 目标起时间 -->" +
								"<td align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[17]==null?"":arr[17])+"</span>" +
								"</td>" +
								"<!-- 目标止时间 -->" +
								"<td align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[18]==null?"":arr[18])+"</span>" +
								"</td>" +
								"<!-- 目标部门-->" +
								"<td align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[20]==null?"":arr[20])+"</span>" +
								"</td>" +
								"<!-- 目标业务员 -->" +
								"<td align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[22]==null?"":arr[22])+"</span>" +
								"</td>" +
								"<td class='baokx'  align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[23]==null?"":arr[23])+"</span>" +
								"</td>" +
								"<td class='baokx'  align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[24]==null?"":arr[24])+"</span>" +
								"</td>" +
								"<td class='baokx'  align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[25]==null?"":arr[25])+"</span>" +
								"</td>" +
								"<!-- 往来部门 -->" +
								"<td align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[27]==null?"":arr[27])+"</span>" +
								"</td>" +
								"<!-- 往来单位 -->" +
								"<td align='center' bgcolor='#FFFFFF' >" +
								"<span>"+(arr[29]==null?"":arr[29])+"</span>" +
								"</td>";
							goodsnum++;
						}
					}
					if(goodstr!=""){
						goodstr="<table id='"+oList[i][0]+"tbl' class='datatbl'><tr><td><table class='table bg auto_arrange .goodtable2' id='goodtable2'>" +
						"<tr>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px' >" +
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>品名编号" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>品名名称" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='100px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img style='vertical-align:middle;'" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>  <span class='xx'>*</span>款源日期" +
						"</th>" +
						" <th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span> 单位" +
						" </th>" +
						" <th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>预算数量" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span> 单价管理" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>预算单价" +
						"</th>" +
						" <th align='center' bgcolor='#E4F1FA' width='90px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>预算金额" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>  <span class='xx'>*</span>拨款金额" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='120px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>  <span class='xx'>*</span>付款方" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='90px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>  <span class='xx'>*</span>付款方帐号" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='90px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>  <span class='xx'>*</span>收款方" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='90px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>  <span class='xx'>*</span>收款方帐号" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>目标起时间" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>目标止时间" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>目标部门" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>目标业务员" +
						"</th>" +
						"<th class='baokx' align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>报开学号" +
						"</th>" +
						" <th class='baokx' align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>学员期数" +
						"</th>" +
						"<th class='baokx' align='center' bgcolor='#E4F1FA' width='90px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>报开学时间" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='180px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>往来公司" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='23' /> </span>往来个人" +
						"</th>" +goodstr;
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
		var clicktr = $("#line").val();
		var numindex=$("tr#" + clicktr).find("td."+bunid).index()-2;
		$("#ntype").val($("tr#" + clicktr).find("td:eq("+numindex+")").find("input[type=hidden]").val());
		cnwlgr("numName=&numtype=" + $("#cutype").val()+"&cuid="+$("#ntype").val());
	});
		

	// 添加所选中的往来个人
	$("#qdnum").click(function() {
		if (bankID != "") {
			var clicktr = $("#line").val();
			$("tr#" + clicktr).find("td." + bunid).find("input:eq(0)").val($("tr#" + bankID).find("#bankaccount").text());
			$(".jqmWindow", $("#selectnumForm")).jqmHide();
			$(".wlgr").parent().removeClass("rwl");
			return;
		} else {
			alert("请选择！");
		}
	});

		// 根据输入的往来个人名称查询
		$("input#searchnum").click(
				function() {
					cuID = "";
					userOrCompanyID = "";
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
		$("#nrxy").click(function() {
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
		
		// ajax查询银行帐号列表
		function cnwlgr(typeCN) {
			if (notoken) {
				alert("正在获取数据！请稍等");
				notoken=0;
				return;
			}
			notoken = 1;
			$("#nrsy").attr("title", 0);
			$("#nrxy").attr("title", 0);
			$("#nrzy").attr("title", 0);
			
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

/** **********************************往来个人**************************************** */
// 选择往来个人
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
		// 选择往来个人、往来单位
		$(".wlgr").click(function() {
			bunid=$(this).attr("id");
			$(".jqmWindow", $("#selectuserForm")).jqmShow();
			cxwlgr("contactUser.staffName=&contactUser.relation=","");
		});
		
		// 添加所选中的往来个人、往来单位
		$("#qduser").click(function() {
			if (cuID != "") {
				var clicktr = $("#line").val();
				$("td."+bunid).children("input").each(function(){
					if($(this).attr("id")=="appropriationcompanyID"||$(this).attr("id")=="ReceivablesID"){
						$("tr#" + clicktr).find("#"+$(this).attr("id")).val(userOrCompanyID);
					}else if($(this).attr("id")=="appropriationcompanyName"||$(this).attr("id")=="ReceivablesName"){
						$("tr#" + clicktr).find("#"+$(this).attr("id")).val($("tr#" + cuID).find("#UserOrComName").text());
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
				cxwldw(typeCN,relation);
			}
			if(cutype=="users"){
				var typeCN= "contactUser.staffName="
					+ typeName
					+ "&contactUser.relation="
					+ relation;
				cxwlgr(typeCN,relation);
			}
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
					cxwldw(typeCN,relation);
				}
				if(cutype=="users"){
					var typeCN= "contactUser.staffName="
						+ typeName
						+ "&contactUser.relation="
						+ relation
						+ "&pageForm.pageNumber=" + sy;
					cxwlgr(typeCN,relation);
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
					cxwldw(typeCN,relation);
				}
				if(cutype=="users"){
					var typeCN= "contactUser.staffName="
						+ typeName
						+ "&contactUser.relation="
						+ relation
						+ "&pageForm.pageNumber=" + xy;
					cxwlgr(typeCN,relation);
				}
			} else {
				alert("已是尾页！");
			}
		});
	});

// ajax查询往来个人列表
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
	            $("#gouserstable").remove();
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

// ajax查询往来单位列表
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
	            $("#gouserstable").remove();
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
	/*if (token) {
		var url = basePath +
		"ea/cashapplybills/ea_toCash.jspa?str="+ cashierBillsID;
		parent.document.location.href = url;
	}*/
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

// 生成凭证号
function getBillID() {
	var url = basePath + "ea/costsheet/sajax_ea_getBillIDs.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var billID = member.billID;
			$("#pbillID").val(billID);
			$("#journalNum").val(billID);
		},
		error : function(data) {
			alert("获取超级父项目凭证号失败");
		}
	});

}


