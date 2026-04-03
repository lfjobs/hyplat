$(function() {
	//显示规章
	var str = '';
           if(st!="00" && st!='')
            {
             if(st=="10"){
                 str ="<img src='"+basePath+"images/ea/finance/jizhang.png'/>";
                 }
	          else if(st=="01"){
	               str ="<img src='"+basePath+"images/ea/finance/yishen.png'/>";
	             }
	            else{
	             	 str ="<img src='"+basePath+"images/ea/finance/daishen.png'/>";
	             }
            } else if(st=="00")
	             {
	                str ="<img src='"+basePath+"images/ea/finance/daishen.png'/>";
	             }
           $("#apDiv1").html(str);
           $("#apDiv1").css({'top':330}); 
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
		//添加页面返回
	$(".JQueryClose").click(function() {
		notoken = 0;
		re_load();
	});
		//打印预览
	$(".JQueryprint").click(function(){
		window.open( basePath+"/ea/voucher/ea_toPrint.jspa?pageNumber="+pNumber+"&search="+search+"&voucher.voucherID="+billid);
	})
	
});
function re_load() {
	document.location.href = basePath+ "ea/voucher/ea_getVoucherList.jspa?pageNumber="+ pNumber + "&pageForm.pageNumber="+ $("#pageNumber").attr("value")+"&aa="+aa;
}

