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
	
	//审核通过
	$("input.JQuerySubmit").click(function(){
		 if(taxstatusDu == "taxstatusDu"){
	         alert("历史数据不能修改");
	         return;
	     }
		 $form =$("#Auditform"); 
		 if (confirm("确定审核通过？")){
			 $form.attr("target","hidden").attr("action", basePath+"/ea/audit/ea_updateResponsible.jspa?search="+search+"&pageNumber="+pNumber);
			 $form.find("input#taxstatusDu").val("04");
			 document.Auditform.submit.click();
			 cashierBillsID = "";
			 token = 2;
	     }
	     return;
	});


	//驳回
	$("input.JQuerySubmitbh").click(function(){
		 if(taxstatusDu == "taxstatusDu"){
	         alert("历史数据不能修改");
	         return;
	     }
		 $form =$("#Auditform"); 
		 if (confirm("确定驳回？")){
		     $form.attr("target","hidden").attr("action", basePath+"/ea/audit/ea_updateResponsible.jspa?search="+search+"&pageNumber="+pNumber);
		     $form.find("input#taxstatusDu").val("10");
		     document.Auditform.submit.click();
		     cashierBillsID = "";
		     token = 2;
	     }
	     return;
	});
	
	
	// 打印预览
	$("input.JQueryprint").click(function() {
				var cashierBillsID = $("input#cashierID",$("#Auditform")).val();
				window.open(basePath + "ea/dutiable/ea_toprinttax.jspa?cashierBillsVO.cashierBillsID="+ cashierBillsID);
	}); 
	
});
function re_load(){
		    document.location.href=basePath+"/ea/audit/ea_getDutiableList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&tsdate="+tsdate+"&tedate="+tedate+"&taxstatusDu="+taxstatusDu;
	     }