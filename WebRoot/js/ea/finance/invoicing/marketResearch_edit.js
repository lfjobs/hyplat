$(function(){
//为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
	       modal: true, 
	       overlay: 20  
	       }).jqmAddClose('.close');
	
	$(".JQueryPrint").click(function(){ //打印预览
		var financialbillID = $("#cashierID").val();
		window.open(basePath + "/ea/purchase/ea_toPrintMarketResearch.jspa?pageNumber="+pNumber+"&financialBill.financialbillID="+financialbillID);
    });
	      
	$(".JQueryClose").click(function(){ //返回
		re_load();
    });
});

function re_load(){
	document.location.href=basePath+"/ea/purchase/ea_getMarketResearchList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&type=04"+"&search="+search+"&sdate="+sdate+"&edate="+edate;
}
