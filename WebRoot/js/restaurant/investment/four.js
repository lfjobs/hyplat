$(document).ready(function(){
	//basePath+"ea/wfjshop/ea_addAddress.jspa?stype=stype&user=15330227796&inventory.inventoryID="+inventoryID+"&ppid="+ppid+"&count="+count+"&intf="+intf+"&companyId="+companyId+"&ccompanyId="+ccompanyId+"&stype="+stype);
			
		$(".wfj11_015_buy_commit").css({"position":"initial","margin-bottom":"5rem"})
		$("#paycommit").css({"width":"50%","margin":"0 auto","background":"#f85d1e","color":"#fff","line-height":"1.8rem"})
	//返回
	 $(".arrow").click(function() {history.go(-1)})
	$(".top").attr(
				"style",
				"height:" + $(window).height() * 0.07 + "px;line-height:"
						+ $(window).height() * 0.07 + "px;");
		$(".top").find("li").attr("style",
				"width:15%;font-size:0.8rem;");
		$(".top").find("li").find("img").attr("style",
				"height:" + $(window).height() * 0.04 + "px;");
		$(".top").find("li").eq(1).attr(
				"style",
				"width:70%;font-size:0.8rem; letter-spacing:4px; ");

		$(".header_hide").css("height", $(".top").height() + "px")
		$(".main_hide").css(
				{
					"height" : $(window).height() - $(".top").height()
							- $(".footer").height() + "px",
					"overflow" : "auto","width":"100%"
				})
		$(".biaot").css({
			"line-height" : $(".ico1").height() + "px",
			"color" : "#f75311"
		});
		$(".wfj11_015_choice").click(function(){
		$(".wfj11_015_choice").find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_02.png");
		$(this).find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_01.png");
		zffs=$(this).find(".second").find("img").attr("name");
});
	
	});
	
	
	
	function zf() {
	address();
	getAddress();
	ajaxsut();
	var liname = $(".wfj11_015_mo").find("li.on").text();
	
	if (zffs == null) {
		alert("请选择支付方式");
		return false;
	} else {

		if (zffs == '1') {
			
			var par = new Array();
			par.push(basePath);
			par.push("page/WFJClient/GoodsShow/wfjAlipay.jsp?");
			par.push("WIDout_trade_no=" + ddid);
			par.push("&WIDtotal_fee="+ $("#morre").val());
			
			par.push("&WIDbody=''");//订单描述
			par.push("&WIDit_b_pay=''");//超时时间
			par.push("&WIDextern_token=''");//钱包
			window.location.href = encodeURI(par.join(""));
		} else if (zffs == '3') {
			alert("微信支付暂无法使用");
			return false;
		}else{
			if(token!=0){
				return;
			}
			token=1;
			$.ajax({
				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
				type:"get",
				data:"fenlei=03&ddid="+ddid,
				success:function suc(data){
					var mb=eval("("+data+")");
					var succ=mb.succ;
					var threeNo=mb.threeNo;
					if(succ=="success"){
						window.location.href=basePath+"page/WFJClient/suc/xxzf.jsp?threeNo="+threeNo;
							
					};
				}
				
			});
			
		}
	}
}
function address(){

var addressDetailed = $("#addressDetailed").val();//收货人详细地址
var phone = $("#phone").val();//收货人电话
var area = $("#area").val();//收货人所在区
var consignee = $("#consignee").val();//收货人
var postCode = $("#postCode").val();//收货人邮编

var url = basePath+"/ea/industry/sajax_ea_address.jspa?addressDetailed="+addressDetailed+"&phone="+phone+"&area="+area+"&consignee="+consignee+"&postCode="+postCode;
	//var url = basePath+"/ea/industry/sajax_ea_getAddress.jspa?addressDetailed="+addressDetailed;
	  $.ajax({
        type : "GET",
        url : url,
        async : false,
  error : function(data) {
	
}
}); 	

}

function getAddress(){
var phone = $("#phone").val();//收货人电话
//var url = basePath+"/ea/industry/sajax_ea_address.jspa?addressDetailed="+addressDetailed+"&phone="+phone+"&area="+area+"&consignee="+consignee+"&postCode="+postCode;
	var url = basePath+"/ea/industry/sajax_ea_getAddress.jspa?phone="+phone;
	  $.ajax({
        type : "GET",
        url : url,
        async : false,
        dataType : "json",
        success : function(data) {
	 	var m = eval("("+data+")");
        var result = m.obj;
        $("#staffAddress").val(result[0]);
    },
  error : function(data) {
	alert("失败 ");
}
}); 	


}
// 确认订单 生成订单方法
function ajaxsut() {
	var formData = $("#formsutm").serialize();
	formData = decodeURIComponent(formData,true);
	
	var ulp = basePath
	+ "/ea/wfjshop/sajax_ea_Shopping.jspa?";
   $.ajax({
        type : "GET",
        url : ulp+formData,
        async : false,
        dataType : "json",
        success : function(data) {
	
	   if (data == null) {
		   alert("数据提交失败。请重试");
	   } else {
		    ddid = data;
			$(".wfj11_015_bottom").css("display","none");
			$("#occlusion2").css("z-index",$(".wfj11_015").css("z-index")+1);
			$("#occlusion2").jqmShow();
			$(".wfj11_015_buy_commit").css("z-index",$("#occlusion2").css("z-index")+1);
			$(".wfj11_015_buy_commit").fadeIn(1000);
	}
    },
  error : function(data) {
	alert("提交订单失败");
}
});
}
 var ccompantId="contactCompany20101230UB4U5884S30000000176";
     function joinVip(ppid,ccompanyId){
         var url = basePath+"/ea/wfjshop/sajax_ea_validatecanBuy.jspa";
         $.ajax({
         url:url,
         type:"get",
         dataType:"json",
         data:{
         ppid:ppid,
         ccompanyId:ccompanyId
         },
         success:function(data){
            var m = eval("("+data+")");
            var result = m.result;
          	var mk = m.mk;
			if (result != "success") {
				alert(result);
				return;
			} else {
				if(mk=="nomk"){
					$(".mk").show();
				}
                document.location.href=basePath+"/ea/wfjshop/ea_gm.jspa?ppid="+ppid+"&ccompanyId="+ccompanyId+"&count=1&mk="+mk;
             }
         },
         error:function(data){
         alert("验证失败");
         }
         
         });
         }