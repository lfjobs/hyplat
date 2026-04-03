$(document).ready(function() {

	
	
	
	//点击投标
	$(".toubiao").click(function(){
        if($(".days").text()=="已截止"){
              alert("已截止");
              return ;
         }
        
        var url = basePath+"ea/purchasebids/sajax_ea_ajaxValidate.jspa";
        $.ajax({
        	url:url,
        	type:"get",
        	async:false,
        	dataType:"json",
        	data:{
        		valtype:"tb"
        		
        	},
        	success:function(data){
        		var mem = eval("("+data+")");
        		var login = mem.login;
			/*	if (login == "login") {
					document.location.href = basePath
							+ "page/WFJClient/NewLogin.jsp?loginPage=login";
					return false;
				}
        		var result = mem.result;
        		
        		if(result=="1"){
        		
        			$(".tanshow").show();
        			$("#bg").show();
        			return;
        		}*/
        		window.open(basePath+"ea/purchasebids/ea_enterBids.jspa?bidsinfo.goodsID="+goodsID+"&inviteBids.cashierBillsID="+cashierBillsID+"&mainpid="+ppid,"_self");
			
				},error:function(data){
        		   alert("验证失败");
        	    }
        	
        });

     });
	
	
	//关闭提示框
	$("#bg").click(function(){
		
		$(".tanshow").hide();
		$("#bg").hide();
	});
	
	//马上升级会员
	$(".tan_kuang_text_bot p").click(function(){

		open(basePath + "/ea/wfjshop/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&zlyq=1",
				"_self");
	});
	
	
	
	//点击返回
     $(".arrar").click(function(){
        document.location.href = basePath+"ea/purchasebids/ea_viewMainProduct.jspa?ppid="+ppid+"&bidsinfo.goodsID="+goodsID+"&inviteBids.cashierBillsID="+cashierBillsID;
     });
     
     //点击购买
     $(".buy").click(function(){
     var pid = $(this).find(".pid").val();
     var tgoodsID = $(this).find(".tgoodsID").val();
     var companyId = $(this).find(".companyid").val();
     var ccompanyid = $(this).find(".ccompanyid").val();
     document.location.href=basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+pid+"&goodsid="+tgoodsID+"&companyId="+companyId+"&ccompanyId="+ccompanyid;
     });
     
     computeDays();
     
});

//计算剩余时间
function computeDays() {
	 if($(".days").find("input").val()!=""&&$(".days").find("input").val()!=undefined){
	var str = $(".days").find("input").val().split(" ");

	var ar_ds = str[0].split('-');
	var ar_ts = str[1].split(':');
	var ds = new Date(ar_ds[0], ar_ds[1] - 1, ar_ds[2], ar_ts[0],
			ar_ts[1], ar_ts[2]);

	var cur = new Date();
	var result = ds.getTime()-cur.getTime();

	//计算出相差天数  
	var days = Math.floor(result / (24 * 3600 * 1000));

	//计算出小时数  

	var leave1 = result % (24 * 3600 * 1000); //计算天数后剩余的毫秒数  
	var hours = Math.floor(leave1 / (3600 * 1000));
	//计算相差分钟数  
	var leave2 = leave1 % (3600 * 1000); //计算小时数后剩余的毫秒数  
	var minutes = Math.floor(leave2 / (60 * 1000));
	var day = minutes < 0 ? "已截止" : "剩";
	if (days > 0) {
		day += days + "天";
	}
	if (hours > 0) {
		day += hours + "小时";
	}
	if (minutes > 0) {
		day += minutes + "分钟";
	}
	$(".days").text(day);
	 }else{
		 $(".days").text("不限");
	 }
}