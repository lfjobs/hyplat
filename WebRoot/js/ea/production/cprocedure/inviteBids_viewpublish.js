$(document).ready(function() {
	
	var height = $("#tableprint").height();
	var str = '';
	   /*---------------              显示审核公章               ---------------*/
	var tax = taxstatusDu;
	if(tax=="01"){
		$(".p").hide();
		$(".c").show();
		
		if (tax == "01") {
			str = "<img src='" + basePath
			+ "images/ea/finance/publishyes.png'/>";
			$("#apDiv1").html(str);
		} else {
			str = "<img src='" + basePath
			+ "images/ea/finance/selectbids.png'/>";
			$("#apDiv1").html(str);
		}
		$("#apDiv1").html(str);
	    $("#apDiv1").css({'top':height-200}); 
	}else{
		
		$(".p").show();
		$(".c").hide();
	}
	
	

	// 提交保存
	$(".save").click(function() {
		var id=$(this).attr("id");
		$("#cstatus").val(id);
		$("#mainForm").attr("target", "hidden").attr("action",
				basePath + "ea/purchasebids/ea_publishInviteBids.jspa");

		document.mainForm.submit.click();
		token = 2;
		    
	});

	

});

function re_load() {
	window.opener.location.href = window.opener.location.href;
	window.close();

}


