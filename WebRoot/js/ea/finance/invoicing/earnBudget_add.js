$(function() {
	if(sztype==""&&$("#billsType").val()!=""){
		if($("#billsType").val()=="收入预算单"){
			$("#szbillstype").val("s");
			$("#sz").text("收入预算单据");
			
		}else{
			$("#szbillstype").val("z");
			$("#sz").text("支出预算单据");
		}
		
		if(type=="01"){
			$("#szbillstype").hide();
			$("#szj").show();
			$("#szj").text($("#sz").text());
		}
	}
	
	$("#szbillstype").change(function(){
		if($(this).val()=="s"){
		$("#sz").text("收入预算单据");
		}else{
	  $("#sz").text("支出预算单据");
		}
		
		
	});
   
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	$(".JQueryreturns").click(function() {
				quzhi="";
				notoken = 0;
				$(".jqmWindow").jqmHide();
			});
	$(".JQueryClose").click(function() {
				notoken = 0;
				re_load();
			});

       //设置查看
		if(toSee=="see"){
			$(".see").hide();
		}else{
			$(".see").show();
		}
		
		//设置打印预览显示
		if(addType=="add"||addType=="edit"){
			$(".JQueryprint").hide();
		}else{
			
			$(".JQueryprint").show();
		}
		// 设置调整预算显示
	if (type == "01") {
		$(".budget").addClass("bordernone");
		$(".budget").attr("readonly", true);
		$(".budget").removeClass("put3").removeClass("isNaN");
		$(".tiao").show();
		$(".see").hide();
		$(".JQuerySubmit").show();
		$("#yearspan").show();
		$("#year").hide();
		$("#budgetName").attr("readonly",true);
		if (toSee == "see") {
			$("#shuju").hide();
			$(".operate").hide();
			$(".JQuerySubmit").hide();
		} else {
			$("#shuju").show();
			$(".operate").show();
			$(".JQuerySubmit").show();
		}
	} else {
		$(".budget").removeClass("bordernone");
		$(".tiao").hide();
		$(".budget").attr("readonly", false);
		$(".budget").addClass("put3").addClass("isNaN");
		$("#yearspan").hide();
		$("#year").show();
		$("#budgetName").attr("readonly",false);
	}

	$("input.JQueryunitret").click(function() {// 重置往来单位
				$t = $("table#table4");
				$t.find("span.qk").each(function() {
							$(this).text("");
						});
				$t.find("select").each(function() {
							$(this).empty();
							$(this).attr("style", "display:none");
						});
				$t.find(":input").each(function() {
							$(this).attr("value", "");
						});
			});
	$("input.JQuerypersonret").click(function() {// 重置往来个人
				$t = $("table#table5");
				$t.find("span.qk").each(function() {
							$(this).text("");
						});
				$t.find("select").each(function() {
							$(this).empty();
							$(this).attr("style", "display:none");
						});
				$t.find(":input").each(function() {
							$(this).attr("value", "");
						});
			});


	
	// 打印预览				
	$("input.JQueryprint").click(function() {
								
			var ebbID = $("input#ebbIDs",
						$("#earnBudgetForm")).val();
				window.open(basePath + "/ea/earnbudget/ea_toprintcsb.jspa?earnBudgetBill.ebbID="+ebbID+"&type="+type);
			});
	// 更改部门事件 清空银行帐号
	$("select#departmentID", "table#table3").change(function() {
				$("input#bankNum", "table#table3").attr("value", "");
			});
	$(".jisuan").focus(function(){
	
		var year =  $("#year").val();
		var month =  $(this).parent().parent().find("#month").val();
		if(year==""){
			alert("请先填写预算年份");
			$(".jisuan").blur();
			return;
		}
		if(month==""){
			alert("请先填写月份");
			$(".jisuan").blur();
			return;
		}
		
		
		
	});	
	
	//年
	
	$(".year").blur(function() {
		var year = $("#year").val();
		
		

	});
	
	// 计算预算金额
	$(".jisuan").live("keyup", function(event) {
	    var year =  $("#year").val();
		var month =  $(this).parent().parent().find("#month").val();
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(this).parent().parent().find(":input#bdquantity")
						.val();
		
				var price = $(this).parent().parent().find(":input#bunitPrice")
						.val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
                    
					$(this).parent().parent().find("input#bdamount").val(dj*price/DayNumOfMonth(year,month));
					$(this).parent().parent().find("input#bwamount").val(dj*price*7);
					$(this).parent().parent().find("input#bmamount").val(dj*price);//月预算金额
					//季度
					//该行的产品编号，若改动该行说明只是该产品对应的行的季度改变
					var productNum = $(this).parent().parent().find("input#productNum").val();
					var season = 0;
					$(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					  var month1 = $(this).find("input#month").val();
					  var bmamount = $(this).find("input#bmamount").val();
					  if(productNum==productNum1){
					  	 if(month<=3){
					  	 	if(month1<=3){
					  	 		season+= Number(bmamount);
					  	 	}
					  	 }else if(month<=6){
					  	 	if(month1<=6&&month1>3){
					  	 		season+= Number(bmamount);
					  	 	}
					  	 }else if(month<=9){
					  	 	if(month1<=9&&month1>6){
					  	 		season+= Number(bmamount);
					  	 	}
					  	 }else{
					  	 	if(month1<=12&&month1>9){
					  	 		season+= Number(bmamount);
					  	 	}
					  	 }
					  }
					  
						
						
					});
					
					
                $(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					  var month1 = $(this).find("input#month").val();

					  if(productNum==productNum1){
					  	 if(month<=3){
					  	 	if(month1<=3){
					  	 		$(this).find("input#bsamount").val(season);
					  	 	}
					  	 }else if(month<=6){
					  	 	if(month1<=6&&month1>3){
					  	 		$(this).find("input#bsamount").val(season);
					  	 	}
					  	 }else if(month<=9){
					  	 	if(month1<=9&&month1>6){
					  	 		$(this).find("input#bsamount").val(season);
					  	 	}
					  	 }else{
					  	 	if(month1<=12&&month1>9){
					  	 		$(this).find("input#bsamount").val(season);
					  	 	}
					  	 }
					  }	
						
					});
		            //年
				$(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					  var bmamount =  $(this).find("input#bmamount").val();
					  var years=0;
					  $(this).parent().parent().parent().find("tr").each(function(){
					  	     var productNum2 = $(this).find("input#productNum").val();
					  	      if(productNum2 ==productNum){
					  	         years += Number($(this).find("input#bmamount").val());
					  	     }
					  	 });
					  if(productNum==productNum1){
					  	 
					  	 $(this).find("input#byamount").val(years);
					  }	
						
					});
					//$(this).parent().parent().find("input#byamount").val(dj*price*365);
				}
			}
		}
	});
	
	
		// 计算预算金额
	$(".jisuan").live("keydown", function(event) {
		 var year =  $("#year").val();
		var month =  $(this).parent().parent().find("#month").val();
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(this).parent().parent().find(":input#bdquantity")
						.val();
		
				var price = $(this).parent().parent().find(":input#bunitPrice")
						.val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
                    
					$(this).parent().parent().find("input#bdamount").val(dj*price/DayNumOfMonth(year,month));
					$(this).parent().parent().find("input#bwamount").val(dj*price*7);
					$(this).parent().parent().find("input#bmamount").val(dj*price);//月预算金额
					//季度
					//该行的产品编号，若改动该行说明只是该产品对应的行的季度改变
					var productNum = $(this).parent().parent().find("input#productNum").val();
					var season = 0;
					$(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					  var month1 = $(this).find("input#month").val();
					  var bmamount = $(this).find("input#bmamount").val();
					  if(productNum==productNum1){
					  	 if(month<=3){
					  	 	if(month1<=3){
					  	 		season+= Number(bmamount);
					  	 	}
					  	 }else if(month<=6){
					  	 	if(month1<=6&&month1>3){
					  	 		season+= Number(bmamount);
					  	 	}
					  	 }else if(month<=9){
					  	 	if(month1<=9&&month1>6){
					  	 		season+= Number(bmamount);
					  	 	}
					  	 }else{
					  	 	if(month1<=12&&month1>9){
					  	 		season+= Number(bmamount);
					  	 	}
					  	 }
					  }
					  
						
						
					});
					
					
                $(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					  var month1 = $(this).find("input#month").val();

					  if(productNum==productNum1){
					  	 if(month<=3){
					  	 	if(month1<=3){
					  	 		$(this).find("input#bsamount").val(season);
					  	 	}
					  	 }else if(month<=6){
					  	 	if(month1<=6&&month1>3){
					  	 		$(this).find("input#bsamount").val(season);
					  	 	}
					  	 }else if(month<=9){
					  	 	if(month1<=9&&month1>6){
					  	 		$(this).find("input#bsamount").val(season);
					  	 	}
					  	 }else{
					  	 	if(month1<=12&&month1>9){
					  	 		$(this).find("input#bsamount").val(season);
					  	 	}
					  	 }
					  }	
						
					});
		            //年
				$(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					 // var bmamount =  $(this).find("input#bmamount").val();
					  var years=0;
					  $(this).parent().parent().parent().find("tr").each(function(){
					  	     var productNum2 = $(this).find("input#productNum").val();
					  	      if(productNum2 ==productNum){
					  	         years += Number($(this).find("input#bmamount").val());
					  	     }
					  	 });
					  if(productNum==productNum1){
					  	 
					  	 $(this).find("input#byamount").val(years);
					  }	
						
					});
					//$(this).parent().parent().find("input#byamount").val(dj*price*365);
				}
			}
		}
	});
	
	// 计算调整金额
	$(".jisuant").live("keyup", function(event) {
		var year =  $("#year").val();
		var month =  $(this).parent().parent().find("#month").val();
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(this).parent().parent().find(":input#tdquantity")
						.val();
		
				var price = $(this).parent().parent().find(":input#tunitPrice")
						.val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;

					
					$(this).parent().parent().find("input#tdamount").val(dj*price/DayNumOfMonth(year,month));
					$(this).parent().parent().find("input#twamount").val(dj*price*7);
					$(this).parent().parent().find("input#tmamount").val(dj*price);//月预算金额
					//季度
					//该行的产品编号，若改动该行说明只是该产品对应的行的季度改变
					var productNum = $(this).parent().parent().find("input#productNum").val();
					var season = 0;
					$(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					  var month1 = $(this).find("input#month").val();
					  var tmamount = $(this).find("input#tmamount").val();
					  if(productNum==productNum1){
					  	 if(month<=3){
					  	 	if(month1<=3){
					  	 		season+= Number(tmamount);
					  	 	}
					  	 }else if(month<=6){
					  	 	if(month1<=6&&month1>3){
					  	 		season+= Number(tmamount);
					  	 	}
					  	 }else if(month<=9){
					  	 	if(month1<=9&&month1>6){
					  	 		season+= Number(tmamount);
					  	 	}
					  	 }else{
					  	 	if(month1<=12&&month1>9){
					  	 		season+= Number(tmamount);
					  	 	}
					  	 }
					  }
					  
						
						
					});
					
					
                $(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					  var month1 = $(this).find("input#month").val();

					  if(productNum==productNum1){
					  	 if(month<=3){
					  	 	if(month1<=3){
					  	 		$(this).find("input#tsamount").val(season);
					  	 	}
					  	 }else if(month<=6){
					  	 	if(month1<=6&&month1>3){
					  	 		$(this).find("input#tsamount").val(season);
					  	 	}
					  	 }else if(month<=9){
					  	 	if(month1<=9&&month1>6){
					  	 		$(this).find("input#tsamount").val(season);
					  	 	}
					  	 }else{
					  	 	if(month1<=12&&month1>9){
					  	 		$(this).find("input#tsamount").val(season);
					  	 	}
					  	 }
					  }	
						
					});
		            //年
				$(this).parent().parent().parent().find("tr").each(function(){
					  var productNum1 = $(this).find("input#productNum").val();//迭代出每一个产品编号和前者对比
					//  var tmamount =  $(this).find("input#tmamount").val();
					  var years=0;
					  $(this).parent().parent().parent().find("tr").each(function(){
					  	     var productNum2 = $(this).find("input#productNum").val();
					  	      if(productNum2 ==productNum){
					  	         years += Number($(this).find("input#tmamount").val());
					  	     }
					  	 });
					  if(productNum==productNum1){
					  	 
					  	 $(this).find("input#tyamount").val(years);
					  }	
						
					});
					//$(this).parent().parent().find("input#byamount").val(dj*price*365);
				}
			}
		}
	});
	
	
		// 计算调整金额
	$(".jisuant").live("keydown", function(event) {
		var year =  $("#year").val();
		var month =  $(this).parent().parent().find("#month").val();
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(this).parent().parent().find(":input#tdquantity")
						.val();
		
				var price = $(this).parent().parent().find(":input#tunitPrice")
						.val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;

					$(this).parent().parent().find("input#tdamount").val(dj*price/DayNumOfMonth(year,month));
					$(this).parent().parent().find("input#twamount").val(dj*price/DayNumOfMonth(year,month)*7);
					$(this).parent().parent().find("input#tmamount").val(dj*price);//月调整金额
					$(this).parent().parent().find("input#tsamount").val(dj*price*30*3);
					$(this).parent().parent().find("input#tyamount").val(dj*price*365);
				}
			}
		}
	});




	// 迭代的商品删除
	$(".ajaxsc").click(function() {
		var billStatus = $("#billStatus").val();

		if (confirm("是否删除？")) {
			var ebdID = $(this).parent().find("input#ebdID")
					.val();
			var delurl = basePath
					+ "ea/earnbudget/sajax_ea_delGoodsBill.jspa?date=" + new Date().toLocaleString();
			$.ajax({
						url : encodeURI(delurl),
						type : "get",
						async : false,
						dataType : "json",
						data:{
							
							"earnBudgetDetail.ebdID":ebdID,
							"earnBudgetBill.billStatus":billStatus
						},
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							var typeID = member.typeID;
							alert("删除成功！");
							$("tr#" + ebdID).remove();
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
			});
	// 提交保存草稿
	$("input.JQuerySubmit").click(function() {
		
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		$(".put3", $("table#table3")).trigger("blur");
		$(".put3", $("tr.checkgoods")).trigger("blur");

		$(".isNaN", $("tr.checkgoods")).trigger("blur");
		$(".jisuan", $("tr.checkgoods")).trigger("blur");
		if ($("#earnBudgetForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		if(sztype==""){
			if(type=="00"){
				$("#sztype").val($("#szbillstype").val());
			}else{
				if($("#billsType").val()=="收入预算单"){
					$("#sztype").val("s");
				}else{
					$("#sztype").val("z");
				}
				
			}
		 
		}
		if (csbID == "") {
			$("#earnBudgetForm #billStatus").val("00");
			$("#earnBudgetForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/earnbudget/ea_saveEarnBudget.jspa?pageNumber="
									+ pNumber);
			document.earnBudgetForm.submit.click();
			document.earnBudgetForm.reset();
			$("tr.checkgoods").remove();
			token = 1;
			return;
		}
		$("#earnBudgetForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/earnbudget/ea_saveEarnBudgettoEdit.jspa?pageNumber="
								+ pNumber);
		document.earnBudgetForm.submit.click();
		token = 2;
	});

	// 提交确认预算
	$("input.JQuerySubmitgd").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		
		$("#earnBudgetForm #billStatus").val("01");

		$(".put3", $("table#table3")).trigger("blur");
		$(".put3", $("tr.checkgoods")).trigger("blur");

		$(".isNaN", $("tr.checkgoods")).trigger("blur");
		$(".jisuan", $("tr.checkgoods")).trigger("blur");
		if ($("#earnBudgetForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		
		if (csbID == "") {
			$("#earnBudgetForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/earnbudget/ea_saveEarnBudget.jspa?pageNumber="
									+ pNumber);
			document.earnBudgetForm.submit.click();
			document.earnBudgetForm.reset();
			$("tr.checkgoods").remove();
			token = 1;
			return;
		}
		$("#earnBudgetForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/earnbudget/ea_saveEarnBudgettoEdit.jspa?pageNumber="
								+ pNumber);
		document.earnBudgetForm.submit.click();
		token = 2;
	});




	/** ************************************库房管理********************************** */
	// //////////////库房管理////////////////////
	$(".todepotID").click(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$td = $("td.depot");
		$td.children('select').empty();
		$td.children('select:gt(0)').hide();
		var depoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		depotID = "";
		depotName = "";
		$.ajax({
			url : encodeURI(depoturl + "001&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var depotManagelist = member.depotManagelist;
				$td = $("td.depot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(0)').append($op);
				}
				notoken = 0;
				$add = "<option class='add'  value ='001' >--新增--</option>";
				$td.children('select:eq(0)').append($add);
				$td.children('select:eq(0)').show();
				$("#selectdepot").jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});

	});
	$("#savedepot").click(function() {
				if (depotName != "--请选择--" && depotName != "--新增--") {
					$("#discountMoney").attr("value", depotID);
					$("#afterDiscount").attr("value", depotName);
				} else {
					$("#discountMoney").attr("value", "");
					$("#afterDiscount").attr("value", "");
				}
				notoken = 0;
				$("#selectdepot").jqmHide();
			});

	// 库房管理
	$('td.depot select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.depot");
		$td.children('select:gt(' + num + ')').empty();
		depotPID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		depotID = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		depotName = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			depotPID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			$("#selectdepot").jqmHide();
			$("input#depotPID", $("#cstaffForm")).val(depotPID);
			$("input#treenum", $("#cstaffForm")).val(num);
			$("input#depotPID", $("#cstaffForm")).attr("title", "selectdepot");
			$("#jqModelkf").jqmShow();
			return;
		}
		if (depotID == "") {
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			notoken = 0;
			return;
		}
		var depoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		$.ajax({
			url : encodeURI(depoturl + depotID + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var depotManagelist = member.depotManagelist;
				$td = $("td.depot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$add = "<option class='add'  value =" + depotID
						+ " >--新增--</option>";
				$td.children('select:eq(' + number + ')').append($add);
				$td.children('select:eq(' + number + ')').show();
				notoken = 0;
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	/** ************************************************************** */
	$(".JQuerySubmitkf").click(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$(".put3", $("#cstaffForm")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		var formData = $("#cstaffForm").serialize();
		var depotsaveurl = basePath
				+ "ea/depotmanage/sajax_ea_saveDepotByAjax.jspa?" + formData
				+ "&date=" + new Date().toLocaleString();
		$.ajax({
			url : depotsaveurl,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var depotManage = member.depotManage;
				var divid = $("input#depotPID", $("#cstaffForm")).attr("title");
				$op1 = $("<option selected='selected'/>").attr("value",
						depotManage.depotID).attr("id", depotManage.depotID)
						.text(depotManage.depotName);
				var treenum = $("input#treenum", $("#cstaffForm")).val();
				var num = parseInt(treenum);
				$("select:eq(" + num + ")", $("#" + divid)).append($op1);
				$select = "<option selected='selected'>--请选择--</option>";
				var number = num + 1;
				$("select:eq(" + number + ")", $("#" + divid)).append($select);
				$add = "<option class='add'  value = '" + depotManage.depotID
						+ "' >--新增--</option>";
				$("select:eq(" + number + ")", $("#" + divid)).append($add);
				$("select:eq(" + number + ")", $("#" + divid)).show();
				depotID = depotManage.depotID;
				depotName = depotManage.depotName;
				notoken = 0;
				alert("添加成功！");
				document.cstaffForm.reset();
				$("#jqModelkf").jqmHide();
				$("#" + divid).jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});

	$(".JQueryreturnkf").click(function() {
				notoken = 0;
				document.cstaffForm.reset();
				var divid = $("input#depotPID", $("#cstaffForm")).attr("title");
				$("#jqModelkf").jqmHide();
				$("#" + divid).jqmShow();
			});

	/** ***************************************************************** */
	/** ************************************资料库房管理********************************** */
	// //////////////库房管理////////////////////
	$(".todatadepotID").click(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$td = $("td.datadepot");
		$td.children('select').empty();
		$td.children('select:gt(0)').hide();
		var datadepoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		depotID = "";
		depotName = "";
		$.ajax({
			url : encodeURI(datadepoturl + "002&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var depotManagelist = member.depotManagelist;
				$td = $("td.datadepot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(0)').append($op);
				}
				$add = "<option class='add'  value = '002' >--新增--</option>";
				$td.children('select:eq(0)').append($add);
				$td.children('select:eq(0)').show();
				notoken = 0;
				$("#selectdatadepot").jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});

	});
	$("#savedatadepot").click(function() {
				if (depotName != "--请选择--" && depotName != "--新增--") {
					$("#dataDepotID").attr("value", depotID);
					$("#dataDepotName").attr("value", depotName);
				} else {
					$("#dataDepotID").attr("value", "");
					$("#dataDepotName").attr("value", "");
				}
				notoken = 0;
				$("#selectdatadepot").jqmHide();
			});

	// 库房管理
	$('td.datadepot select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.datadepot");
		$td.children('select:gt(' + num + ')').empty();
		depotPID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		depotID = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		depotName = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			depotPID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			$("#selectdatadepot").jqmHide();
			$("input#depotPID", $("#cstaffForm")).val(depotPID);
			$("input#treenum", $("#cstaffForm")).val(num);
			$("input#depotPID", $("#cstaffForm")).attr("title",
					"selectdatadepot");
			$("#jqModelkf").jqmShow();
			return;
		}
		if (depotID == "") {
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			notoken = 0;
			return;
		}
		var datadepoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		$.ajax({
			url : encodeURI(datadepoturl + depotID + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var depotManagelist = member.depotManagelist;
				$td = $("td.datadepot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$add = "<option class='add'  value =" + depotID
						+ " >--新增--</option>";
				$td.children('select:eq(' + number + ')').append($add);
				$td.children('select:eq(' + number + ')').show();
				notoken = 0;
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	/** ************************************银行库房管理********************************** */
	// //////////////银行库房管理////////////////////
	$(".tobankdepotID").click(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$td = $("td.banksdepot");
		$td.children('select').empty();
		$td.children('select:gt(0)').hide();
		var bankdepoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		depotID = "";
		depotName = "";
		$.ajax({
			url : encodeURI(bankdepoturl + "003&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var depotManagelist = member.depotManagelist;
				$td = $("td.banksdepot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(0)').append($op);
				}
				notoken = 0;
				$add = "<option class='add'  value ='003' >--新增--</option>";
				$td.children('select:eq(0)').append($add);
				$td.children('select:eq(0)').show();
				$("#selectbankdepot").jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});

	});
	$("#savebankdepot").click(function() {
				if (depotName != "--请选择--" && depotName != "--新增--") {
					$("#bankDepotID").attr("value", depotID);
					$("#bankDepotName").attr("value", depotName);
				} else {
					$("#bankDepotID").attr("value", "");
					$("#bankDepotName").attr("value", "");
				}
				notoken = 0;
				$("#selectbankdepot").jqmHide();
			});

	// 库房管理
	$('td.banksdepot select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.banksdepot");
		$td.children('select:gt(' + num + ')').empty();
		depotPID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		depotID = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		depotName = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			depotPID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			$("#selectbankdepot").jqmHide();
			$("input#depotPID", $("#cstaffForm")).val(depotPID);
			$("input#treenum", $("#cstaffForm")).val(num);
			$("input#depotPID", $("#cstaffForm")).attr("title",
					"selectbankdepot");
			$("#jqModelkf").jqmShow();
			return;
		}
		if (depotID == "") {
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			notoken = 0;
			return;
		}
		var bankdepoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		$.ajax({
			url : encodeURI(bankdepoturl + depotID + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var depotManagelist = member.depotManagelist;
				$td = $("td.banksdepot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$add = "<option class='add'  value =" + depotID
						+ " >--新增--</option>";
				$td.children('select:eq(' + number + ')').append($add);
				$td.children('select:eq(' + number + ')').show();
				notoken = 0;
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});

});
function re_load() {
	var url = basePath
			+ "ea/earnbudget/ea_earnBudgetList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber=" + pageNumber + "&search="
			+ search+"&type="+type+"&sztype="+sztype;
	document.location.href = encodeURI(url);
}

function toCCode(codePID, selectID, formID) {
	$(".jqmWindow").jqmHide();
	$("#codePID").attr("value", codePID);
	$("#selectID").attr("value", selectID);
	$("#formID").attr("value", formID);
	$("#ccodevalue").attr("value", "");
	$("#newccode").jqmShow();
}
function saveCCode() {
	var codePID = $("#codePID").attr("value");
	var codeValue = $("#ccodevalue").attr("value");
	var selectID = $("#selectID").attr("value");
	var formID = $($("#formID").attr("value"));
	var url = basePath + "ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="
			+ codePID + "&code.codeValue=" + codeValue + "&date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
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
					var code = member.code;
					$("#newccode").jqmHide();
					$op = $("<option/>");
					$op.attr("value", code.codeValue).text(code.codeValue);
					$(selectID, formID).append($op);
					alert("操作成功！");
					$(".jqmWindow", formID).jqmShow();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}
$(document).ready(function() {
			$("input.JQueryreturn1").click(function() {// 取消
						var formID = $($("#formID").attr("value"));
						$("#newccode").jqmHide();
						$(".jqmWindow", formID).jqmShow();
					});

		});
/** **********************************选择产品**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	var ccID = "";// ccompanyID
   // 双击选中物品
	$("table#gostable3 tr[id]").live("click", function(event) {
		var b = $("input.rap", $(this)).attr("checked");
		$("input.rap", $(this)).attr("checked", !b);
	});
	
	// 复选框选中物品
	$(".rap").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	// 选择产品
	$("#shuju").click(function() {
				$("input#parameter", $("table#searchproduct")).val("");
				
				$(".jqmWindow", $("#selectproductForm")).jqmShow();
				var parameter=$("#parameter").val();
				cp("parameter="+parameter,null);
			});
	// 新增产品
	$(".pxzdw").click(function() {
		window.open(basePath
				+ "ea/productdesign/ea_getListProductdesign.jspa");
	});
	// 添加所选中的产品
		// 添加所选中的物品到物品单
	$("#addproduct").click(function() {
		if ($("[name='checkboxp']").is(':checked')) {
			$("input[name='checkboxp']").each(function() {
				if ($(this).is(':checked')) {
					// 选中一行克隆一行
					select++;
					// 克隆一行并修改文本框的name
					$("#kelong").before(
					
					$("#kelong").clone(true).attr("id",
							"kelong" + select).addClass("checkgoods")
							
					);
					//修改input标签Id为goodsNomber的值
					$("input#goodsNomber",$("#kelong" + select)).val(select-1+$("tr.xggoods").length);
					
//					alert($("tr.xggoods").length);
					//修改当前行的所有input的name
					$("#kelong" + select).find(':input').each(function() {
						$(this).attr("name","logbookmap[" + select + "]." + this.name);
			
					});
					
					//遍历Id为$(this).val()的tr里说以的td
				    var amount="";
				    var price="";
					$("tr #" + $(this).val()).children("td").each(function() {

						if (this.id == "goodsID") {

							$("input#goodsID", $("#kelong" + select)).attr(
									"value", $(this).text());
						} else {
							$("span#" + this.id, $("#kelong" + select))
									.text($(this).text());
							if (this.id == "manual"
									|| this.id == "productPublicity"
									|| this.id == "companyFile") {

								$("input#" + this.id, $("#kelong" + select))
										.val($(this).find("span").text());
								if ($(this).find("span").text() == null
										|| $(this).find("span").text() == "null") {
									$("input#" + this.id, $("#kelong" + select))
											.parent().append("无");
								} else {
									$("input#" + this.id, $("#kelong" + select))
											.parent()
											.append("<a href='#' onclick=\"viewAttach('"
													+ $(this).find("span")
															.text()
													+ "');\">查看</a>");
								}
							} else {
								$("input#" + this.id, $("#kelong" + select))
										.val($(this).text());
							}

							if (this.id == "unitPrice") {
								if (type == "01") {
									$("input#t" + this.id,
											$("#kelong" + select)).val($(this)
											.text());
								} else {
									$("input#b" + this.id,
											$("#kelong" + select)).val($(this)
											.text());
								}
							}
						}

						if (this.id == "dquantity") {
							amount = $(this).text();
						}
						if (this.id == "unitPrice") {
							price = $(this).text();
						}
//						if (type == "00") {
//							$("input#bdamount", $("#kelong" + select))
//									.val(amount * price/DayNumOfMonth($("#year").val(),2));
//							$("input#bwamount", $("#kelong" + select))
//									.val(amount * price * 7);
//							$("input#bmamount", $("#kelong" + select))    //月预算金额
//									.val(amount * price);
//
//							$("input#bsamount", $("#kelong" + select))
//									.val(amount * price * 30 * 3);
//							$("input#byamount", $("#kelong" + select))
//									.val(amount * price * 365);
//						}
//
//						if (type == "01") {
//							$("input#tdamount", $("#kelong" + select))
//									.val(amount * price);
//							$("input#twamount", $("#kelong" + select))
//									.val(amount * price * 7);
//							$("input#tmamount", $("#kelong" + select)) //月调整金额
//									.val(amount * price);
//
//							$("input#tsamount", $("#kelong" + select))
//									.val(amount * price * 30 * 3);
//							$("input#tyamount", $("#kelong" + select))
//									.val(amount * price * 365);
//						}

					});
					$("tr#kelong" + select).show();
					$(this).attr("checked", false);
				}
			});
			$(".jqmWindow", $("#selectproductForm")).jqmHide();
		} else {
			alert("请选择产品！");
		}
	});
	
	// 根据输入的产品名称或编号查询
	$("input#searchpp").click(function() {
	    var parameter=$("#parameter").val();
		cp("parameter="+parameter,"search");
	});
	// 上一页
	$("#pdwsy").click(function() {
		var sy = $("#pdwsy").attr("title");
		if (sy != 0) {
			var parameter=$("#parameter").val();
			var typeCN = "parameter=" + parameter
					+ "&pageForm.pageNumber=" + sy;
			cp(typeCN,search);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#pdwxy").click(function() {
		var xy = $("#pdwxy").attr("title");
		if (xy != 0) {
			var parameter=$("#parameter").val();
			var typeCN = "parameter=" + parameter
					+ "&pageForm.pageNumber=" + xy;
			cp(typeCN,search);
		} else {
			alert("已是尾页！");
		}
	});
	// ajax查询往来单位列表
	function cp(typeCN,search) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#pdwsy").attr("title", 0);
		$("#pdwxy").attr("title", 0);
		$("#pdwzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/earnbudget/sajax_ea_getListProductList.jspa?";
		$.ajax({
			url : encodeURI(searchurl+typeCN+"&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			data:{
				search:search
				
				
			},
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				var connectionlist = member.connectionlist;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				
		
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#pdwsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#pdwxy").attr("title", dqy + 1);
				}
				$("span#pzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostable3' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>产品编号</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>产品名称</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>产品重量</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>产品单位</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>产品规格</th>"
				tabletr += " <th align='center' bgcolor='#E4F1FA'>产品单价</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>产品数量</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>产品部门</th>"
				tabletr += " <th align='center' bgcolor='#E4F1FA'>责任人</th>"
				tabletr += " <th align='center' bgcolor='#E4F1FA'>说明书</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>产品宣传</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>公司文件</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' title="
							+ pageForm.list[i][6] + " id = "
							+ pageForm.list[i][0] + ">";
					tabletr += "<td id='checkpp' align='center'>" +
							"<input type ='checkbox'  class='rap' value="
							+ pageForm.list[i][0]
							+ " name='checkboxp'/></td>";
					tabletr += "<td id='productNum' align='center'>"
							+ pageForm.list[i][5] + "</td>";
					tabletr += "<td id='productName' align='center'>"
							+ pageForm.list[i][6] + "</td>";
	
					tabletr += "<td id='weight'  align='center'>"
							+ (pageForm.list[i][10]==null?"":pageForm.list[i][10]) + "</td>";
							
				
					tabletr += "<td id='productUnit' align='center'>"
							+ (pageForm.list[i][7]==null?"":pageForm.list[i][7]) + "</td>";
					tabletr += "<td id='productStandard' align='center'>"
							+ (pageForm.list[i][8]==null?"":pageForm.list[i][8]) + "</td>";
					tabletr += "<td id='unitPrice' align='center'>"
							+ pageForm.list[i][11]+ "</td>";
					tabletr += "<td id='dquantity' align='center'>"
							+ pageForm.list[i][9] + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i][2] + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i][3] + "</td>";
                    tabletr += "<td id='manual' align='center'>" +
					(pageForm.list[i][13]==null?"无":"<a onclick=\"viewAttach('"
							+ pageForm.list[i][13].replace(/\\/gm,"/") + "');\">查看</a>") +
							"<span style='display:none;'>"+(pageForm.list[i][13]==null?null:(pageForm.list[i][13].replace(/\\/gm,"/")))+"</span>"+
							
							"</td>";

					
					tabletr += "<td id='productPublicity' align='center'>" +
					(pageForm.list[i][14]==null?"无":"<a onclick=\"viewAttach('"
							+ pageForm.list[i][14].replace(/\\/gm,"/") + "');\">查看</a>") +
							"<span style='display:none;'>"+(pageForm.list[i][14]==null?null:(pageForm.list[i][14].replace(/\\/gm,"/")))+"</span>"+
							"</td>";
							
					tabletr += "<td id='companyFile' align='center'>" +
					(pageForm.list[i][15]==null?"无":"<a onclick=\"viewAttach('"
							+ pageForm.list[i][15].replace(/\\/gm,"/") + "');\">查看</a>") +
							"<span style='display:none;'>"+(pageForm.list[i][15]==null?null:(pageForm.list[i][15].replace(/\\/gm,"/")))+"</span>"+
							"</td>";		
		
                    tabletr += "<td id='goodsID' style='display:none' align='center'>"
							+ pageForm.list[i][16] + "</td>";
					
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02pr").html(tabletr);
				$("#body_02pr").show();
				//alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});



/** **********************************选择设备**************************************** */
$(document).ready(function() {
	tree = new dhtmlXTreeObject("aadTree2", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	var getcodeurl = basePath
			+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date="
			+ new Date().toLocaleString();
	tree.insertNewChild(0, "scode20101014v5zed7cukk0000000002", "物品树", 0, 0, 0,
			0);
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
					var codeList = member.codeList;
					if (null == codeList) {
						return;
					}
					for (var i = 0; i < codeList.length; i++) {
						tree.insertNewChild(
								"scode20101014v5zed7cukk0000000002",
								codeList[i].codeID, codeList[i].codeValue, 0,
								0, 0, 0);
						tree.setUserData(codeList[i].codeID, "codeNumber",
								codeList[i].codeNumber);
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	tree.setOnClickHandler(function() {
				oldtreeid = treeid;
				treeid = tree.getSelectedItemId();
				treename = tree.getItemText(treeid);
				if (oldtreeid != treeid) {
					if (treeid != "scode20101014v5zed7cukk0000000002") {
						$(":input#parms2").val("typeID=" + treename);
						cx2("typeID=" + treename);
						return;
					}
				}
			});
	$("#newG").click(function() {
				$(".jqmWindow", $("#goodsForm")).jqmShow();
			});
	// 双击选中物品
	$("table#gotable2 tr[id]").live("click", function(event) {
				var b = $("input.ragood", $(this)).attr("checked");
				$("input.ragood", $(this)).attr("checked", !b);
			});
			
			
	


	// 上一页
	$("#wpsy2").click(function() {
				var sy = $("#wpsy2").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms2").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx2(typeCN);
				} else {
					alert("已是首页！");
				}
			});
	// 下一页
	$("#wpxy2").click(function() {
				var xy = $("#wpxy2").attr("title");
				if (xy != 0) {
					var typeName = $(":input#parms2").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					cx2(typeCN);
				} else {
					alert("已是尾页！");
				}
			});
	// 新增物品
	$(".xzwp2").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
			});
	$("#selectGood2").click(function() {
		$("input[name='check']").each(function() {
			if ($(this).is(':checked')) {
				$("tr #" + $(this).val()).children("td").each(function() {
					$("input#" + this.id, $("table#goods")).attr("value",
							$(this).text());
				});
			}
		});
		$(".jqmWindow", $("#goodsForm")).jqmHide();
	});

	// 根据输入的物品编号或物品名称查询
	$("input#searchGood2").click(function() {
				var typeName = $("#parameter2", $("table#searchgood2")).val();
				$(":input#parms2").val("parameter=" + typeName);
				cx2("parameter=" + typeName);
			});

	// ajax查询物品列表
	function cx2(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy2").attr("title", 0);
		$("#wpxy2").attr("title", 0);
		$("#wpzy2").attr("title", 0);
		var searchurl = basePath
				+ "ea/cashierbills/sajax_ea_getGoodsManageByTypeID.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				if (pageForm == null) {
					alert("没有您要查的数据");
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#wpsy2").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy2").attr("title", dqy + 1);
				}
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable2' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th><th align='center' bgcolor='#E4F1FA'>统一分类条码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th><th align='center' bgcolor='#E4F1FA'>类型</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>型号</th><th align='center' bgcolor='#E4F1FA'>换算单位</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th><th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].goodsID + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='ragood' value="
							+ pageForm.list[i].goodsID + " name='check'/></td>";
					tabletr += "<td id='goodsCoding' align='center'>"
							+ pageForm.list[i].goodsCoding + "</td>";
					tabletr += "<td id='goodsName'  align='center'>"
							+ pageForm.list[i].goodsName + "</td>";
					tabletr += "<td id='defaultStorage'  align='center'>"
							+ pageForm.list[i].defaultStorage + "</td>";
					tabletr += "<td id='mnemonicCode' align='center'>"
							+ pageForm.list[i].mnemonicCode + "</td>";
					tabletr += "<td id='typeID' align='center'>"
							+ pageForm.list[i].typeID + "</td>";
					tabletr += "<td id='model' align='center'>"
							+ pageForm.list[i].model + "</td>";
					tabletr += "<td id='variableID'  align='center'>"
							+ pageForm.list[i].goodsvariable + "</td>";
					tabletr += "<td id='acquiesceStandard' align='center'>"
							+ pageForm.list[i].acquiesceStandard + "</td>";
					tabletr += "<td id='goodsID' style='display:none' align='center'>"
							+ pageForm.list[i].goodsID + "</td>";
					tabletr += "<td id='standard' align='center'>"
							+ pageForm.list[i].standard + "</td>";
					tabletr += "<td id='variableID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variableID + "</td>";
					tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable1ID + "</td>";
					tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable2ID + "</td>";
					tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable3ID + "</td>";
					tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable4ID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_022").html(tabletr);
				$("#body_022").show();
				// alert("数据加载成功")
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});
/** **********************************往来单位**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	var ccID = "";// ccompanyID
	$("table#gostable tr[id]").live("click", function(event) {
				contactcID = this.id;
				ccID = this.title;
				$("input.ra", $(this)).attr("checked", "checked");
			});
	// 选择往来单位
	$("#xzwlaw").click(function() {
				$("input#ccompanyID", $("table#searchcompany")).val("");
				$("select#contactConnections", $("table#searchcompany"))
						.val("全部");
				$(".jqmWindow", $("#selectcompanyForm")).jqmShow();
				cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
			});
	// 新增往来单位
	$(".xzdw").click(function() {
		window.open(basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});
	// 添加所选中的往来单位
	$("#qdcompany").click(function() {
		if (contactcID != "") {
			var RegistrationURL = basePath
					+ "ea/contactcompany/sajax_ea_getListRegistration.jspa?contactCompany.ccompanyID="
					+ ccID + "&date=" + new Date().toLocaleString();
			$.ajax({
				url : encodeURI(RegistrationURL),
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
					var bankList = member.bankList;
					$se = $("select#aNum", $("table#table4"));
					$se.empty();
					$se
							.append("<option selected='selected' value = ''>--请选择--</option>");
					for (var i = 0; i < bankList.length; i++) {
						$op = $("<option />");
						$op.attr("value", bankList[i].bankAccount)
								.text(bankList[i].bankName + "---"
										+ bankList[i].bankAccount);
						$se.append($op);
					}
					$("span#accountNum", $("#table4")).remove();
					$("input#accountNum", $("#table4")).remove();
					$se.attr("name", "earnBudgetBill.accountNum");
					$se.show();
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
			$("tr #" + contactcID).children("td").each(function() {
				if (this.id == "ccompanyID") {
					$("input#ccompanyID", $("table#table4"))
							.val($(this).text());

				}else if (this.id == "ccompanyname") {
					$("input#ccompanyname", $("table#table4")).val($(this).text());
					$("span#" + this.id, $("table#table4")).text($(this).text());
				} else if (this.id == "contactConnections") {
					$(
							"select#contactConnections option[value="
									+ $(this).text() + "]", $("table#table4"))
							.remove();
					$("select#contactConnections", $("table#table4"))
							.append("<option selected='selected' value = "
									+ $(this).text() + ">" + $(this).text()
									+ "</option>").show();
					$("span#contactConnections", $("table#table4")).hide();
				} else {
					$("span#" + this.id, $("table#table4"))
							.text($(this).text());
					$("input#" + this.id, $("table#table4"))
					.val($(this).text());
				}
			});
			$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
		} else {
			alert("请选择往来单位！");
		}
	});
	// 根据输入的往来单位名称查询
	$("input#searchcc").click(function() {
		contactcID = "";
		var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
		var contactConnections = $("select#contactConnections",
				$("table#searchcompany")).val();
		quzhi = contactConnections;
		cxwldw("contactCompany.companyName=" + typeName
				+ "&cconnection.contactConnections=" + contactConnections);
	});
	// 上一页
	$("#dwsy").click(function() {
		var sy = $("#dwsy").attr("title");
		if (sy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			var contactConnections = "";
			if (quzhi != "") {
				contactConnections = quzhi;
			} else {
				contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
			}
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + sy;
			cxwldw(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#dwxy").click(function() {
		var xy = $("#dwxy").attr("title");
		if (xy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			var contactConnections = "";
			if (quzhi != "") {
				contactConnections = quzhi;
			} else {
				contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
			}
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + xy;
			cxwldw(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// ajax查询往来单位列表
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#dwsy").attr("title", 0);
		$("#dwxy").attr("title", 0);
		$("#dwzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				var connectionlist = member.connectionlist;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#contactConnections",
						$("table#searchcompany"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < connectionlist.length; i++) {
					$op = $("<option />");
					$op.attr("value", connectionlist[i].codeValue)
							.text(connectionlist[i].codeValue);
					$se.append($op);
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#dwsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#dwxy").attr("title", dqy + 1);
				}
				$("span#zycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' title="
							+ pageForm.list[i].ccompanyID + " id = "
							+ pageForm.list[i].contactConnectionID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
							+ pageForm.list[i].contactConnectionID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='ccompanyname' align='center'>"
							+ pageForm.list[i].companyName + "</td>";
					tabletr += "<td id='contactConnections' align='center'>"
							+ pageForm.list[i].contactConnections + "</td>";
					tabletr += "<td id='industryType' align='center'>"
							+ pageForm.list[i].industryType + "</td>";
					tabletr += "<td id='companyTel'  align='center'>"
							+ pageForm.list[i].companyTel + "</td>";
					tabletr += "<td id='cresponsible' align='center'>"
							+ pageForm.list[i].cresponsible + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i].responsibleTel + "</td>";
					tabletr += "<td id='companyAddr'  align='center'>"
							+ pageForm.list[i].companyAddr + "</td>";
					tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
							+ pageForm.list[i].ccompanyID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cc").html(tabletr);
				$("#body_02cc").show();
				//alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});

// 选择往来个人
$(document).ready(function() {
	var cuID = "";
	var userstaffID = "";
	$("table#gouserstable tr[id]").live("click", function(event) {
				cuID = this.id;
				userstaffID = this.title;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 选择往来个人
	$("#xzwlgr").click(function() {
				$("input#contactUserID", $("table#searchuser")).val("");
				$("select#relation", $("table#searchuser")).val("全部");
				$(".jqmWindow", $("#selectuserForm")).jqmShow();
				cxwlgr("contactUser.staffName=&contactUser.relation=");
			});
	// 新增往来个人
	$(".xzgr").click(function() {
				window.open(basePath
						+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
			});

	// 添加所选中的往来个人
	$("#qduser").click(function() {
		if (cuID != "") {
			var RegistrationuserURL = basePath
					+ "ea/contactuser/sajax_ea_getListRegistrationUser.jspa?contactUser.staffID="
					+ userstaffID + "&date=" + new Date().toLocaleString();
			$.ajax({
				url : encodeURI(RegistrationuserURL),
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
					var bankList = member.bankList;
					$se = $("select#userNum");
					$se.empty();
					$se
							.append("<option selected='selected' value = ''>--请选择--</option>");
					for (var i = 0; i < bankList.length; i++) {
						$op = $("<option />");
						$op.attr("value", bankList[i].bankAccount)
								.text(bankList[i].bankName + "---"
										+ bankList[i].bankAccount);
						$se.append($op);
					}
					$("span#userAccountNum").remove();
					$("input#userAccountNum").remove();
					$se.attr("name", "earnBudgetBill.userAccountNum");
					$se.show();
					//qq
					var qqList=member.qqList;
					$se1 = $("select#referenceCode");
					$se1.empty();
					$se1
							.append("<option selected='selected' value = ''>--请选择--</option>");
					for (var j = 0; j < qqList.length; j++) {
						$op1 = $("<option />");
						$op1.attr("value", qqList[j].contactWay).text(qqList[j].contactWay);
						$se1.append($op1);
					}
					$("span#userQq").remove();
					$("input#userQq").remove();
					$se1.attr("name", "earnBudgetBill.referenceCode");
					$se1.show();
					//邮箱
					var emailList=member.emailList;
					$se2 = $("select#referenceOrganization");
					$se2.empty();
					$se2
							.append("<option selected='selected' value = ''>--请选择--</option>");
					for (var n = 0; n < emailList.length; n++) {
						$op2 = $("<option />");
						$op2.attr("value", emailList[n].contactWay)
								.text(emailList[n].contactWay);
						$se2.append($op2);
					}
					$("span#email").remove();
					$("input#email").remove();
					$se2.attr("name", "earnBudgetBill.referenceOrganization");
					$se2.show();
					//地址addressList
					var addlist=member.Arraylist;
					$se0 = $("select#staffAddress");
					$se0.empty();
					$se0
							.append("<option selected='selected' value = ''>--请选择--</option>");
					if(addlist!= undefined){	
						for (var m = 0;m < addlist.length; m++) {
							$op0 = $("<option />");
							$op0.attr("value", addlist[m])
									.text(addlist[m]);
							$se0.append($op0);
						}
					}
					$("span#userAddr").remove();
					$("input#userAddr").remove();
					$se0.attr("name", "earnBudgetBill.staffAddress");
					$se0.show();
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
			$("tr #" + cuID).children("td").each(function() {
				if (this.id == "contactUserID") {
					$("input#contactUserID", $("table#table5")).val($(this)
							.text());

				} else if (this.id == "contactUserName") {
					$("input#contactUserName", $("table#table5")).val($(this)
							.text()); 
					$("span#" + this.id, $("table#table5"))
					.text($(this).text());
				}else if (this.id == "phone") {

					$("select#phone option[value=" + $(this).text() + "]",
							$("table#table5")).remove();
					$("select#phone", $("table#table5"))
							.append("<option selected='selected' value = "
									+ $(this).text() + ">" + $(this).text()
									+ "</option>").show();
					$("span#phone", $("table#table5")).hide();
				} else {
					$("span#" + this.id, $("table#table5"))
							.text($(this).text());
					$("input#" + this.id, $("table#table5"))
					.val($(this).text());
				}
			});
			$(".jqmWindow", $("#selectuserForm")).jqmHide();
		} else {
			alert("请选择往来个人！");
		}
	});
	// 根据输入的往来个人名称查询
	$("input#searchuu").click(function() {
		cuID = "";
		userstaffID = "";
		var typeName = $("input#contactUserID", $("table#searchuser")).val();
		var relation = $("select#relation", $("table#searchuser")).val();
		quzhi = relation;
		cxwlgr("contactUser.staffName=" + typeName + "&contactUser.relation="
				+ relation);
	});
	// 上一页
	$("#grsy").click(function() {
		var sy = $("#grsy").attr("title");
		if (sy != 0) {
			cuID = "";
			userstaffID = "";
			var typeName = $("input#contactUserID", $("table#searchuser"))
					.val();
			var relation = "";
			if (quzhi != "") {
				relation = quzhi;
			} else {
				relation = $("select#relation", $("table#searchuser"))
						.val();
			}
			var typeCN = "contactUser.staffName=" + typeName
					+ "&contactUser.relation=" + relation
					+ "&pageForm.pageNumber=" + sy;
			cxwlgr(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#grxy").click(function() {
		var xy = $("#grxy").attr("title");
		if (xy != 0) {
			cuID = "";
			userstaffID = "";
			var typeName = $("input#contactUserID", $("table#searchuser"))
					.val();
			var relation = "";
			if (quzhi != "") {
				relation = quzhi;
			} else {
				relation = $("select#relation", $("table#searchuser"))
						.val();
			}
			var typeCN = "contactUser.staffName=" + typeName
					+ "&contactUser.relation=" + relation
					+ "&pageForm.pageNumber=" + xy;
			cxwlgr(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// ajax查询往来个人列表
	function cxwlgr(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#grsy").attr("title", 0);
		$("#grxy").attr("title", 0);
		$("#grzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}

				var pageForm = member.pageForm;
				var codeRelationList = member.codeRelationList;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#relation", $("table#searchuser"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < codeRelationList.length; i++) {
					$op = $("<option />");
					$op.attr("value", codeRelationList[i].codeValue)
							.text(codeRelationList[i].codeValue);
					$se.append($op);
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
						+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].relationID + " title= "
							+ pageForm.list[i].staffID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
							+ pageForm.list[i].relationID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='contactUserName' align='center'>"
							+ pageForm.list[i].staffName + "</td>";
					tabletr += "<td id='phone' align='center'>"
							+ pageForm.list[i].relation + "</td>";
					tabletr += "<td id='tel' align='center'>"
							+ pageForm.list[i].reference + "</td>";
					tabletr += "<td id='staffIdentityCard' align='center'>"
							+ pageForm.list[i].staffIdentityCard + "</td>";
					/*tabletr += "<td id='userQq'  align='center'>"
							+ pageForm.list[i].referenceCode + "</td>";
					tabletr += "<td id='email'  align='center'>"
							+ pageForm.list[i].referenceOrganization + "</td>";
					tabletr += "<td id='userAddr'  align='center'>"
							+ pageForm.list[i].staffAddress + "</td>";*/
					tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
							+ pageForm.list[i].staffID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cu").html(tabletr);
				$("#body_02cu").show();
				//alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
	
	
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").jqmHide();
	}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");
		var checkform =$("#checkform",$("#bankJqm")).attr("value");
		var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
		var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");
		var childopertionID = window.frames["daoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择")
			return;
		}
		var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
		var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();
		if(checkopertionID != "")
			$("#"+checkopertionID,$("#"+checkform)).attr("value",childopertionID).trigger("blur");
		if(checkopertionName != ""){
			$("#"+checkopertionName,$("#"+checkform)).attr("value",childopertionName).trigger("blur");
		}
		if(checkopertionName =="partnerName"){
		$("#staffName",$("#"+checkform)).attr("value",no);
		$("#staffCode",$("#"+checkform)).attr("value",childopertionName);
			var final = no + "---" + childopertionName;
			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
		}
		 $("#daoRu").attr("src","");
         $("#bankJqm").jqmHide();
   });
	
});


function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
		 
		 if(checkopertionName=="bankNum"){
		 	var departmentID =  $("#departmentID").attr("value");
		 	url = url + "?departmentID="+departmentID;
		 }
		 if(checkopertionName=="partnerName"){
		 	url = url + "?title1=title1";
		 }
		 $("#checkopertionID",$("#bankJqm")).attr("value",checkopertionID);
		 $("#checkform",$("#bankJqm")).attr("value",attachSearchTable);
		 $("#checkopertionName",$("#bankJqm")).attr("value",checkopertionName);
		 $("#childopertionName",$("#bankJqm")).attr("value",childopertionName);
	   $("#bankJqm #daoRu").attr("src",basePath+url);
	   $("#bankJqm").jqmShow();
}

function MouseDownToResize(obj) {
	setTableLayoutToFixed();
	obj.mouseDownX = event.clientX;
	obj.pareneTdW = obj.parentElement.offsetWidth;
	obj.pareneTableW = goodtable.offsetWidth;
	obj.setCapture();
}
function MouseMoveToResize(obj) {
	if (!obj.mouseDownX)
		return false;
	var newWidth = obj.pareneTdW * 1 + event.clientX * 1 - obj.mouseDownX;
	if (newWidth > 0) {
		obj.parentElement.style.width = newWidth;
		goodtable.style.width = obj.pareneTableW * 1 + event.clientX * 1
				- obj.mouseDownX;
	}
}
function MouseUpToResize(obj) {
	obj.releaseCapture();
	obj.mouseDownX = 0;
}
function setTableLayoutToFixed() {
	if (goodtable.style.tableLayout == 'fixed')
		return;
	var headerTr = goodtable.rows[0];
	for (var i = 0; i < headerTr.cells.length; i++) {
		headerTr.cells[i].styleOffsetWidth = headerTr.cells[i].offsetWidth;
	}

	for (var i = 0; i < headerTr.cells.length; i++) {
		headerTr.cells[i].style.width = headerTr.cells[i].styleOffsetWidth;
	}
	goodtable.style.tableLayout = 'fixed';
} 
		

//打开附件只读
function viewAttach(docPath){
	if(docPath!=null||docPath!=""){
		
		window.open(basePath+"page/ea/common/weonlyreadprint.jsp?docPath="+docPath+"&WorkMode=2&fileType=W");
	}
	
	
	
}


//根据年份和月份返回天数
function DayNumOfMonth(Year,Month)
{
    var d = new Date(Year,Month,0);
    return d.getDate();
}

