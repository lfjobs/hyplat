$(function(){
    //为弹出框准备下拉表内容
    $(".jqmWindow").jqm({
        modal: true,// 
        overlay: 20 // 
    }).jqmAddClose('.close');//
    $(".JQueryClose").click(function(){
        notoken = 0;
        re_load();
    });
    
    /***************显示公章******************/
    var st = $("#cashierstatus").val();
	if (st != "00" && st!='') {
		if (st == "10") {
			var str = "<img src='" + basePath
					+ "images/ea/finance/zuofei.png'/>";
			$("#apDiv1").html(str);
		} else if (st == "03") {
			var str = "<img src='" + basePath
					+ "images/ea/finance/yishen.png'/>";
			$("#apDiv1").html(str);
		} else {
			var str = "<img src='" + basePath
					+ "images/ea/finance/daishen.png'/>";
			$("#apDiv1").html(str);
		}
	} 
    
    /** *************************word文档编辑********************************* */
	$(".accessoriesUrl1").click(function() {
		var accessoriesUrl = $.trim($(this).next().next().val());
		OpenWord(accessoriesUrl, 2);
	});
    
	/***************人事审核通过******************/
	$("input.JQuerySubmit").click(function(){
	    if(hostStatus =="hostStatus"){
         	alert("历史数据不可更改");
         	return ;
	    }
        $form =$("#cashierTallyForm"); 
        if(confirm("确定审核通过？")){
            $form.attr("target","hidden").attr("action", basePath+"ea/humanExamine/ea_updateHuman.jspa?pageNumber="+pNumber);
            $form.find("input#cashierstatus").val("03");
            document.cashierTallyForm.submit.click();
            cashierBillsID = "";
            token = 2;
        }
        return;
	});
	
	/***************驳回******************/
	$("input.JQuerySubmitbh").click(function(){
	    if(hostStatus =="hostStatus"){
         	alert("历史数据不可更改");
         	return ;
        }
        $form =$("#cashierTallyForm"); 
        if (confirm("确定驳回？")){
            $form.attr("target","hidden").attr("action", basePath+"ea/humanExamine/ea_updateHuman.jspa?pageNumber="+pNumber);
            $form.find("input#cashierstatus").val("10");
            document.cashierTallyForm.submit.click();
            cashierBillsID = "";
            token = 2;
        }
        return;
	});
});

function re_load(){
    document.location.href = basePath
			+ "ea/humanExamine/ea_getHumanList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&sdate="+sdate+"&edate="+edate;
}