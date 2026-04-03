function bodyScale() {
	var devicewidth = document.documentElement.clientWidth;
	var scale = devicewidth / 750;
	document.body.style.zoom = scale;
}
window.onload = window.onresize = function() {
	bodyScale();
};
$(function() {
	//页面提示框属性
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
	
	var x = 0;
	var timer1 = null;
	$(document).on('click','.nav li',function() {
		clearInterval(timer1);
		x = $('.nav li').index(this);
		$('.nav li').removeClass('active');
		$('.nav li').eq(x).addClass('active');
		$('.con li').eq(x).fadeIn().siblings().hide();
		autoMove();
	})
	function autoMove() {
		timer1 = setInterval(function() {
			x++;
			if (x >= $('.con img').length) {
				x = 0;
			}
			;
			$('.nav li').removeClass('active');
			$('.nav li').eq(x).addClass('active');
			$('.con li').eq(x).fadeIn().siblings().hide();
		}, 2000);
	}
	autoMove();// 进入页面执行
	// 提交保存到产品促销品关系表
	$("#submit")
	.click(
			function() {
				ppid = $("#ppId").val();//主产品ppid
				user = $("#user").val();//用户
				companyId = $("#companyId").val();//主产品公司id
				var myarr = new Array();
				var myarr1 = new Array();
				$(".PtppId").each(function () {
		            myarr.push($(this).val());
		        });
				if(myarr.length>0){
				/*	for ( var i = 0; i < myarr.length; i++) {
						if(myarr[i]==ppid){
							return prompt("不允许绑定自己");
						}
					}*/
					salesPromotionId = myarr.join(",");//促销产品ppid
					$(".PCompanyId").each(function () {
			            myarr1.push($(this).val());
			        });
					salesPromotionCompanyId = myarr1.join(",");//促销产品公司ppid
				}
                var surl1 = basePath
                    + "ea/productslaunch/ea_addSalesPromotion.jspa?promotion.ppId="
                    + ppid + "&promotion.companyId=" + companyId
                    + "&promotion.ptppId=" + salesPromotionId
                    + "&promotion.ptcompanyId="
                    + salesPromotionCompanyId
                    +"&user="+user;
                //判断是否会员产品
                var url = basePath+"ea/productslaunch/sajax_ea_checkVip.jspa";

                $.ajax({
                    url : url,
                    type : "get",
                    asycn : false,
                    dataType : "json",
                    data : {
                        ppId:salesPromotionId
                    },
                    success : function(data) {
                        var member = eval("(" + data + ")");
                        var r = member.r;
                        if(r=="1"){
                            alert("会员只能选择一种");
                        }else{
                            document.location.href = surl1;
                        }


                    },error:function(data){

                    }

                });





			});
	//查询公司详情
	$(".buy2").click(function() {
		companyname=$(this).find(".companyName").html();
		companyid=$(this).find(".PCompanyId").val();
		ccompanyid=$(this).find(".CCompanyId").val();
		$(".biao_subproject_lis2").removeAttr("onclick");
		var surl1 = basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyid;
		document.location.href = surl1;
	});
});
//页面提示框
function prompt(obj){
	if($("#prompt").css("display")!="none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function(){
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}
//查询商品详情
function Description(obj){
	var parms=new Array();
	parms.push("ppid="+$(obj).find(".PtppId").val());
	parms.push("&goodsid="+$(obj).find(".goodsid").val());
	parms.push("&companyId="+$(obj).find(".PCompanyId").val());
	parms.push("&ccompanyId="+$(obj).find(".CCompanyId").val());
	var judge="Promotion";
	user=$("#user").val();
	var url=basePath+"ea/wfjshop/ea_doodsDetail.jspa?"+parms.join("")+"&judge="+encodeURI(encodeURI(judge))+"&user="+user;
	document.location.href = url;
}

//跳转页面查询全部产品
function giftProducts() {
		ppid = $("#ppId").val();
		user = $("#user").val();
		var myarr = new Array();
		$(".PtppId").each(function () {
            myarr.push($(this).val());
        });
		salesPromotionId = myarr.join(",");//促销产品ppid
		var surl1 = basePath
				+ "page/WFJClient/ProductsLaunch/ChoosePromotionalProducts.jsp?ppID="+ppid+"&user="+user+"&salesPromotionId="+salesPromotionId;
		document.location.href = surl1;
}
//删除
function del(obj) {
	$(".zengjia").removeAttr("onclick");
	$(obj).parent().parent().remove();
}