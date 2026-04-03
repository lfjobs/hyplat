$(function(){
	/*---------------              显示审核公章               ---------------*/
	var tax = $("#taxstatusDu").val();
	if (tax == "10") {
		var str = "<img src='" + basePath
				+ "images/ea/finance/zuofei.png'/>";
		$("#apDiv1").html(str);
	} else if (tax == "04") {
		var str = "<img src='" + basePath
				+ "images/ea/finance/yishen.png'/>";
		$("#apDiv1").html(str);
	} else if (tax == "01" ||tax == "02"||tax == "03"){
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
				var cashierBillsID = $("input#cashierID",$("#cashierTallyForm")).val();
				window.open(basePath + "ea/dutiable/ea_toprinttax.jspa?cashierBillsVO.cashierBillsID="+ cashierBillsID);
	});    
});
function re_load(){
	document.location.href=basePath+"ea/dutiable/ea_getDutiableList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&tsdate="+tsdate+"&tedate="+tedate;
}