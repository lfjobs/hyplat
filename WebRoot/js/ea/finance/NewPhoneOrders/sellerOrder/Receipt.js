var pagenumber = 0;
var t;
var pagecount=0;
var cla="appraise";
var sta= "00";
var pl = null;
var url;
$(document).ready(function(){
	loaded(sta,cla);
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92-$(".header").height()+"px");
        $(".content").css("height",$(window).height()*0.92-$(".header").height()+"px");

        $(".header .rec_head li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
            $(".rec_eva").empty();
            pagenumber=0;   
        });

        $(".appraise").click(function(){
            $("#appraise").css("display","block").siblings().css("display","none");
            cla="appraise";
            sta="00";
            loaded(sta,cla);
        });
        $(".successful").click(function(){
            $("#successful").css("display","block").siblings().css("display","none");
            cla="successful";
            sta="11";
            loaded(sta,cla);
        });
    })
 function getHeight(){
	 t=setTimeout("getHeight()",200);
	 if($(".last").length>0){
		 if($(".last").offset().top+$(".last").height()-$(".header").height()*4<$(window).height()){
			 if(pagenumber<pagecount){
				 loaded(sta,cla);
			 }
		}		 
	 }
 }  
     
    
function loaded(sta,cla){
	clearTimeout(t);
 	pagenumber++;
	url = basePath +"ea/seller/sajax_ea_AjaxReceiptReturnOrder.jspa?type=Receipt";
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dateType : "json",
			data:{						
				  "pageNumber":pagenumber,
				  "sta":sta,
				  "companyid":companyid
				},
		success : function (data) {
			var member = eval("("+data+")");
			var pageForm = member.pageForm;
			var mapp = member.mapp;
			var htl = new Array();
			if(pageForm!=null&&pageForm.recordCount>0){
					$(".last").removeClass("last");
					var list = pageForm.list;
					if(list!=null){
						pagenumber = pageForm.pageNumber;
						pagecount = pageForm.pageCount;
					}
					
					for(var i = 0;i<list.length;i++){
						var pp =list[i];
							htl.push('<div class="grd last">');
			                htl.push('<ul class="sel_nub">');
			                htl.push('<li>订单号：<span>'+pp[3]+'</span></li>');
		               if(sta=="00"){
		            	   htl.push('<li><span>待评价</span></li>');   
		               }else{
		            	   htl.push('<li><span>交易成功</span></li>');   
		               }
			               htl.push('<input type="hidden" id="casid" value="'+pp[0]+'">');
			               htl.push('<input type="hidden" id="orid" value="'+pp[3]+'">');
			               htl.push('</ul>');
	                    for ( var j = 0; j < mapp[pp[0]].length; j++) {
	                    	var goods=mapp[pp[0]][j];
	                    	htl.push('<div class="mil" onclick="CashBill(this)"><div class="left">');
	                    	htl.push('<img src="'+basePath+goods[0]+'" alt="">');
                            if (goods[9] != null && goods[9] != "" && goods[9] == "1") {//批发

                            } else if (goods[9] != null && goods[9] != "" && goods[9] == "2") {//VIP
                                htl.push('<span class="sp vip"><i></i></span>');
                            } else if (goods[9] != null && goods[9] != "" && goods[9] == "3") {//普通活动
                                htl.push('<span class="sp cx"><i></i></span>');
                            } else if (goods[9] != null && goods[9] != "" && goods[9] == "4") {//特价活动
                                htl.push('<span class="sp tj"><i></i></span>');
                            } else {//零售
                            }
	                    	htl.push('</div>');
	                    	htl.push('<div class="right">');
	                    	htl.push('<h3>'+goods[1]+'</h3>');
	                    	htl.push('<h3>产品规格：<span></span><span></span></h3>');
	                    	htl.push('<h3>备注：</h3>');
	                    	htl.push('</div>');
	                    	htl.push('<div class="right right2">');
	                    	htl.push('<h3>&yen;<span>'+goods[3]+'</span></h3>');
	                    	htl.push(' <p>x'+goods[2]+'</p>');
	                    	htl.push('</div>');
	                    	htl.push('</div>');
	                    	htl.push('<ul class="time2">');
	                    	htl.push('<li>');
	                    	htl.push('<i><img src="'+basePath+'images/ea/finance/NewPhoneOrders/sellerOrder/ico-time.png"></i>');
	                    	htl.push('<p>订单时间:<span style="padding-left: 0.3rem;">'+pp[6]+'</span><span style="padding-left: 0.3rem;"></span></p>');
	                    	htl.push('</li>');
	                    	htl.push('</ul>');
	                    	htl.push('<div class="com_btn" align="right">');
	                    	htl.push('</div>');
				            htl.push('</div>');
						}
	                    
						htl.push('</div>');
					} 
			 }
			 	
				$("#"+cla).append(htl.join(""));
				getHeight(sta,cla);
			}
	});
	
}
    
//订单详情
function CashBill(onb){
	var casid = $(onb).parent(".grd").find(".sel_nub #casid").val();
	var oaBillId = $(onb).parent(".grd").find(".sel_nub #orid").val();
	var url = basePath+"ea/seller/ea_getCashBill.jspa?parameter=BILL&partem=Receipt&cashierBillsID="+casid+"&sta="+sta+"&oaBillId="+oaBillId;
	document.location.href=url;
} 
    
 
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }