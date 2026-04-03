$(function(){
	/*---------------              显示审核公章               ---------------*/
	if (taxstatusDu == "10") {
		var str = "<img src='" + basePath
				+ "images/ea/finance/zuofei.png'/>";
		$("#apDiv1").html(str);
	} else if (taxstatusDu == "04") {
		var str = "<img src='" + basePath
				+ "images/ea/finance/yishen.png'/>";
		$("#apDiv1").html(str);
	} else {
		var str = "<img src='" + basePath
				+ "images/ea/finance/daishen.png'/>";
		$("#apDiv1").html(str);
	}
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
				var cashierBillsID = $("input#cashierID",$("#taxsummaryForm")).val();
				window.open(basePath + "/ea/rejectbills/ea_toprinttax.jspa?cashierBillsVO.cashierBillsID="+ cashierBillsID);
	}); 
});
function re_load() {
	var url = basePath
			+ "/ea/rejectbills/ea_getCashierBillsList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber=" + pNumber + "&search="
			+ search +"&level="+level;
	document.location.href = encodeURI(url);
}