$(function(){

/*---------------              显示审核公章               ---------------*/
		var st = $("#cashierstatus").val();
		if (st != "00") {
			if (st == "10") {
				var str = "<img src='" + basePath
						+ "images/ea/finance/zuofei.png'/>";
				$("#apDiv1").html(str);
			} else if (st == "07") {
				var str = "<img src='" + basePath
						+ "images/ea/finance/yishen.png'/>";
				$("#apDiv1").html(str);
			} else {
				var str = "<img src='" + basePath
						+ "images/ea/finance/daishen.png'/>";
				$("#apDiv1").html(str);
			}

		} else {
			var str = "<img src='" + basePath
					+ "images/ea/finance/zuofei.png'/>";
			$("#apDiv1").html(str);
		}


//为弹出框准备下拉表内容
	 $(".jqmWindow").jqm({
                    modal: true,// 
                    overlay: 20 // 
                }).jqmAddClose('.close');//
       $(".JQueryClose").click(function(){
       document.location.href=basePath+"ea/cashiersummarycompany/ea_getCashierList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
    });
});
