$(document).ready(function() {
	var height = $("#tableprint").height();

	   /*---------------              显示审核公章               ---------------*/
	var tax = taxstatusDu;
	if(tax!="00"){
		$(".botoom").hide();
		
		
		var str = '';
		if (tax == "01") {
			str = "<img src='" + basePath
			+ "images/ea/finance/yishen.png'/>";
			$("#apDiv1").html(str);
		} else if (tax == "02") {
			str = "<img src='" + basePath
			+ "images/ea/finance/reject.png'/>";
			$("#apDiv1").html(str);
		} else {
			str = "<img src='" + basePath
			+ "images/ea/finance/daishen.png'/>";
			$("#apDiv1").html(str);
		}
		$("#apDiv1").html(str);
	    $("#apDiv1").css({'top':height-190}); 
	}
	
	

	// 提交保存
	$(".save").click(function() {
		$("#cstatus").val($(this).attr("id"));
		$("#mainForm").attr("target", "hidden").attr("action",
				basePath + "ea/purchasebids/ea_examinePurchaseSheet.jspa");

		document.mainForm.submit.click();
		token = 2;
		    
	});

	

});

function re_load() {
	window.opener.location.href = window.opener.location.href;
	window.close();

}


