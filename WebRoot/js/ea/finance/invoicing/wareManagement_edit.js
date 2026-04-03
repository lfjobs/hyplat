$(function(){
//为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
	       modal: true,// 
	       overlay: 20 // 
	       }).jqmAddClose('.close');//
	      
	$(".JQueryClose").click(function(){
			re_load();
    });
	// 打印预览
	$("input.JQueryprint").click(function() {
		var financialgoodid = $("input#cashierID",
				$("#purchaseForm")).val();
		window.open(basePath + "/ea/warehousing/ea_toWareManagement.jspa?financialBillsGood.financialgoodID="+financialgoodid+"&print=print");
	});
});

function re_load(){
	document.location.href=basePath+"/ea/warehousing/ea_getWareManagementList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&billStatus="+billStatus;
}
