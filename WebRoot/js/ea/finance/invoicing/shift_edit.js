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
			window.open(basePath +"/ea/stock/ea_toShiftPrint.jspa?pageNumber="+ pNumber + "&financialBill.financialbillID="+ financialbillID);
	});
});

function re_load(){
	document.location.href=basePath+"/ea/stock/ea_getShiftList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&type=06&search="+search+"&sdate="+sdate+"&edate="+edate;	
}
