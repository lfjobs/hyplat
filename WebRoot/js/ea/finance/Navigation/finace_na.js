$(document).ready(function() {  

	$("#navigation").treeview();  
	$("#zzgldd").treeview({
		 persist: "location",
	      collapsed: true,
	      unique: true
	}); 
	
	 var a=0;
	 var b=false;
	 var ss;
	    $("#zz").click(function(event){
	    	if(b||a==0){
	    		ss=$("#zzul").detach();
	    		a=1;
	 		   var url1=basePath+"/ea/csbjects/sajax_ea_ajaxStartTime.jspa?date="+new Date().toLocaleString();
	 	       $.ajax({
	 	                url: encodeURI(url1),
	 	                type: "get",
	 	                async: false,
	 	                dataType: "json",
	 	                success: function cbf(data){
	 		              var member = eval("(" + data + ")");
	 		              var nologin = member.nologin;
	 		              if(nologin){
	 		                  document.location.href =basePath+"page/ea/not_login.jsp";
	 							}
	 							var c = member.count;
	 							if (c > 0) {
	 								$("#zz").append(ss);
	 							}else{
	 								b=true;
	 								alert("没有财务初始化，总账管理模块功能未开启");
	 							}
	 						},
	 						error : function cbf(data) {
	 							alert("数据获取失败！");
	 						}
	 			});
	    	}
	       /*if(b){
		    	alert(23)
		    	if(a==1){
		    		return;
		    	}
		    	$("#zzul").detach();
		    	a=1;
		    }*/
	    });
	    
});
function zz(){
	 var a=0;
	 var b=false;
	 var ss;
	    	/*if(a==0){
	    		ss=$("#zzul").detach();
	    	}
	    	a=1;*/
		   var url1=basePath+"/ea/csbjects/sajax_ea_ajaxStartTime.jspa?date="+new Date().toLocaleString();
	       $.ajax({
	                url: encodeURI(url1),
	                type: "get",
	                async: false,
	                dataType: "json",
	                success: function cbf(data){
		              var member = eval("(" + data + ")");
		              var nologin = member.nologin;
		              if(nologin){
		                  document.location.href =basePath+"page/ea/not_login.jsp";
							}
							var c = member.count;
							if (c > 0) {
								$("#zz").append(ss);
							}else{
								b=true;
								alert("没有财务初始化，总账管理模块功能未开启");
							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
	       });
}
function tonclick(id) {
	/****************************************资金申请管理************************************/
	if (id == "zjszdz") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/cashapplybills/ea_toCash.jspa?other=other&level=organization");
	}
	if (id == "wbxjsq") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/cashapplybills/ea_toCash.jspa?weibokuan=weibokuan&level=organization");
	}

	if (id == "ybxjsq") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/cashapplybills/ea_toCash.jspa?other=other&level=organization&cother=all");
	}
	if (id == "fycgmx") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/purchase1/ea_getPurchaseList.jspa?type=00");

	}
	
	/****************************************资金使用管理************************************/
	
	if (id == "xjrjz") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=06&level=organization");
	}
	if (id == "xjsrgl") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/statement/ea_toPage.jspa?zz=00");
	}
	if (id == "xjzcgl") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/statement/ea_toPage.jspa?zz=03");
	}
	if (id == "yhriz") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/bankcashdayquery/ea_showAccountList.jspa?innerAction=showAccountList");
	}
	if (id == "yhsr") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/bankaccountInOutManager/ea_showBankAccountList.jspa?innerAction=in&actionFlag=showAccountList");
	}
	if (id == "yhzc") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/bankaccountInOutManager/ea_showBankAccountList.jspa?innerAction=out&actionFlag=showAccountList");
	}
	if (id== "yhye") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/bankaccountBalance/ea_getAccountBalance.jspa?innerAction=accountList");
	}
	if (id == "yhjymx") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/bankaccountTransDetails/ea_getAccountTransDetails.jspa?innerAction=accountList");
	}
	
	/****************************************凭证管理************************************/
	
	/************************收支凭证管理***********************/
	if (id == "szpzgl") {
		$("#mainframe").attr(
				"src",
				basePath + "/ea/splitbill/ea_getCashierBillsList.jspa?zz=21&level=organization&sztype=s'");
	}
	if (id == "szmxgl") {
		$("#mainframe").attr(
				"src",
				basePath + "/ea/splitgoodsbill/ea_OsearchGood.jspa?zz=09&level=organization&sztype=s");
	}
	if (id == "szyegl") {
		window.open(basePath+ "/ea/splitbill/ea_toSprins.jspa?level=organization&zz=06");
	}
	if (id == "szys") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/earnbudget/ea_toCompeptePage.jspa?comp=budget&sztype=");
	}
	if (id == "sztz") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/earnbudget/ea_toCompeptePage.jspa?comp=tiao&sztype=");
	}
	
	/************************收入凭证管理***********************/
	if (id == "srpzgl") {
		$("#mainframe").attr(
				"src",
				basePath + "/ea/splitbill/ea_getCashierBillsList.jspa?zz=07&level=organization&sztype=s");
	}
	if (id == "srmxgl") {
		$("#mainframe").attr(
				"src",
				basePath + "/ea/splitgoodsbill/ea_OsearchGood.jspa?zz=07&level=organization&sztype=s");
	}
	if (id == "sryegl") {
		window.open(basePath+ "/ea/splitbill/ea_toSprins.jspa?level=organization&zz=07");
	}
	if (id == "srys") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/earnbudget/ea_toCompeptePage.jspa?comp=budget&sztype=s");
	}
	if (id == "srtz") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/earnbudget/ea_toCompeptePage.jspa?comp=tiao&sztype=s");
	}
	
	/************************支出凭证管理***********************/
	if (id == "zcpzgl") {
		$("#mainframe").attr(
				"src",
				basePath + "/ea/splitbill/ea_getCashierBillsList.jspa?zz=08&level=organization&sztype=z");
	}
	if (id == "zcmxgl") {
		$("#mainframe").attr(
				"src",
				basePath + "/ea/splitgoodsbill/ea_OsearchGood.jspa?zz=08&level=organization&sztype=s");
	}
	if (id == "zcyegl") {
		window.open(basePath+ "/ea/splitbill/ea_toSprins.jspa?level=organization&zz=07");
						
	}
	if (id == "zcys") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/earnbudget/ea_toCompeptePage.jspa?comp=budget&sztype=z");
	}
	if (id == "zctz") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/earnbudget/ea_toCompeptePage.jspa?comp=tiao&sztype=z");
	}
	
	/****************************************资金申请管理************************************/
	
	if (id == "zjszdz") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/cashapplybills/ea_toCash.jspa?other=other&level=organization");
	}
	
	if (id == "wbxjsq") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/cashapplybills/ea_toCash.jspa?weibokuan=weibokuan&level=organization");
	}

	if (id == "ybxjsq") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/cashapplybills/ea_toCash.jspa?other=other&level=organization&cother=all");
	}
	
	if (id == "fycgmx") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/purchase1/ea_getPurchaseList.jspa?type=00");

	}
	
	/****************************************总账明细************************************/
	
	/************************总账管理***********************/
	
	/*********************基础信息管理******************/
	/*if (id == "kmqcgl") {
		$("#mainframe").attr(
				"src",
				basePath + "/page/ea/main/finance/invoicing/voucher/csubejst_manger.jsp");
	
	}
	if (id == "wpgl") {
		$("#mainframe").attr(
				"src",
				basePath + "/ea/splitgoodsbill/ea_OsearchGood.jspa?zz=09&level=organization&sztype=s");
	
	}*/
	
	/***********************凭证管理*********************/
	if(id=="pzlb"){
		$("#mainframe")
			.attr("src",basePath+"/ea/category/ea_getVoucherCategory.jspa");
		
	}
	if (id == "pzlr") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/voucher/ea_toVoucherInput.jspa?otype=pzlr");
	
	}
	if (id == "pzsh") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/voucher/ea_getVoucherExamineList.jspa?otype=pzsh");
	}
	if (id == "pzjz") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/voucher/ea_getVoucherExamineList.jspa?otype=pzjz");
	}
	if (id == "pzyj") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "/ea/mco/ea_getSerch.jspa?");
	}
	/************************相关报表***********************/
	if (id == "pzly") {
		$("#mainframe")
				.attr(
						"src",basePath+"/page/ea/main/finance/invoicing/voucher/printing_list.jsp");
	}
	if (id == "ssb") {
		$("#mainframe")
				.attr(
						"src",basePath+"/page/ea/main/finance/invoicing/voucher/trial_list.jsp");		
	}
	if (id == "xjlsz") {
		$("#mainframe")
				.attr(
						"src",basePath+"/page/ea/main/finance/invoicing/voucher/moneyAccount_list.jsp");
	}
	if (id == "yhlsz") {
		$("#mainframe")
		        .attr(
				        "src",basePath+"/page/ea/main/finance/invoicing/voucher/bankAccount_list.jsp");
	}
	/************************总账管理***********************/
	/*if (id == "zzgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/vaccount/ea_getVaccountList.jspa?zz=00");
	
	}
	if (id == "mxzgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/vaccount/ea_getVaccountList.jspa?zz=01'");
	
	}
	if (id == "xsz") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/vsequence/ea_getVsequenceList.jspa");
	
	}
	if (id == "kmyeb") {
		$("#mainframe")
		.attr("src",basePath+ "/ea/csbjects/ea_toPage.jspa");
	
	}*/
	
	/************************库存账管理***********************/
	if (id == "shgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/purchase1/ea_getPurchaseList.jspa?type=01");
	
	}
	if (id == "yhgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/purchase1/ea_toInspectAddDan.jspa?print=danframe");
	
	}
	if (id == "rkgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/page/ea/main/navigation/finace_storage.jsp");
	
	}
	if (id == "ckgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/page/ea/main/navigation/finace_out.jsp");
	
	}
	if (id == "kcgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/page/ea/main/navigation/finace_inventory.jsp");
	
	}
	if (id == "bsgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/break/ea_getbreakList.jspa?");
	
	}
	
	/************************固定资产***********************/
	/*if (id == "gdzc") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=11&level=organization");
	
	}
	if (id == "zcbsgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=20&level=organization");
	
	}
	if (id == "zczj") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=12&level=organization");
	
	}
	if (id == "acjs") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=13&level=organization");
	
	}
	if (id == "zcbb") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getBillsDetailList.jspa?dtype=asset&level=organization");
	
	}*/
	
	/************************应付应收管理***********************/
	/*if (id == "ysgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=04&level=organization");
	
	}
	if (id == "yfgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=05&level=organization");
	
	}
	if (id == "ysmx") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getBillsDetailList.jspa?dtype=ysd");
	
	}
	if (id == "yfmx") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getBillsDetailList.jspa?dtype=yfd");
	
	}*/
	
	/************************工资管理***********************/
	/*if (id == "yfgz") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=09&level=organization");
	
	}
	if (id == "yfgz") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=10&level=organization");
	
	}
	if (id == "gzbb") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getBillsDetailList.jspa?dtype=salary");
	
	}
	if (id == "gzft") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=13&level=organization");
	
	}
	if (id == "jjgz") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getBillsDetailList.jspa?dtype=asset");
	
	}*/
	
	/************************销售管理***********************/
	/*if (id == "khgl") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=16&level=organization");
	
	}
	if (id == "xsdh") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=16&level=organization");
	
	}
	if (id == "xsfh") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=17&level=organization");
	
	}
	if (id == "xsth") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=18&level=organization");
	
	}
	if (id == "xsdb") {
		$("#mainframe")
		.attr(
				"src",basePath+ "/ea/splitbill/ea_getCashierBillsList.jspa?zz=19&level=organization");
	
	}*/
	//会计年度设定	
	if(id=="kjnd"){
	$("#mainframe")
		.attr(
				"src",basePath+ "/ea/fisperiod/ea_getFiscalPeriodList.jspa");
	}
	//年度结转	
	if(id=="ndjz"){
	$("#mainframe")
		.attr(
				"src",basePath+ "/ea/dycoturn/ea_toDyCoTurn.jspa");
	}
	//资产负债损益表内容设定
	if(id=="zcfzbnrsd"){
	  $("#mainframe")
		.attr(
				"src",basePath+ "/page/ea/main/finance/invoicing/voucher/assetstree.jsp");
	

    }
	//资产负债损益表
	if(id=="zcfzb"){
	  $("#mainframe")
		.attr("src",basePath+ "/ea/ccpbsgl/ea_toPage.jspa");
	
	  ///ea/ccpbsgl/ea_zfshowExcel.jspa
    }
}
