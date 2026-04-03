
var pagenumber = 0;
var t;
var pagecount=0;
var cla="appraise";
var status= "return";
var pl = null;
var url;
$(document).ready(function(){
	loaded(status,cla);
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
            status="return";
            loaded(status,cla);
        });
        $(".successful").click(function(){
            $("#successful").css("display","block").siblings().css("display","none");
            cla="successful";
            status="returnend";
            loaded(status,cla);
        });
    })
    
    
    function getHeight(){
	 t=setTimeout("getHeight()",200);
	 if($(".last").length>0){
		 if($(".last").offset().top+$(".last").height()-$(".header").height()*4<$(window).height()){
			 if(pagenumber<pagecount){
				 loaded(status,cla);
			 }
		}		 
	 }
 }  



function loaded(status,cla){
	clearTimeout(t);
 	pagenumber++;
 	if(status == "return"){
 		url = basePath + "ea/seller/sajax_ea_AjaxReceiptReturnOrder.jspa?status=02&status2=05&type=pp";
 	}else if(status == "returnend"){
 		url = basePath  + "ea/seller/sajax_ea_AjaxReceiptReturnOrder.jspa?status=02&status2=05&type=pp";
 	}
	
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dateType : "json",
		data: {
				  "pageNumber": pagenumber,
				  "stypedate": status,
				  "companyid": companyid
		},
		success : function (data)
		{
			var member = eval("("+data+")");
			var pageForm = member.pageForm;
			var mapp = member.mapp;
			var htl = new Array();
			if (pageForm!=null && pageForm.recordCount > 0)
			{
				$(".last").removeClass("last");
					var list = pageForm.list;
					if(list!=null)
					{
						pagenumber = pageForm.pageNumber;
						pagecount = pageForm.pageCount;	
					}
					
					for(var i = 0;i < list.length;i++)
					{
						var pp = list[i];
						htl.push('<div class="grd last">');
						htl.push('<input type="hidden" id="casid" value="' + pp[0] + '">');
						htl.push('<input type="hidden" id="orderBill" value="' + pp[6] + '">');
						htl.push('<input type="hidden" id="trade_no" value="' + pp[7] + '">');
						htl.push('<input type="hidden" id="wfStatus4" value="' + pp[8] + '">');
						htl.push('<input type="hidden" id="wfStatus1" value="'+ pp[10]+ '">');
						htl.push('<ul class="sel_nub">');
						htl.push('<li>订单号：<span>'+pp[6]+'</span></li>');
                        if (pp[5]=='00')
                        {
                            if (pp[9]!='05')
                            {
                                htl.push('<li><span>退款中</span></li>');
                            }
                            else
                            {
                                htl.push('<li><span>退款结束</span></li>');
                            }
                        }
                        else if (pp[5]=='01')
                        {
                            if (pp[9]!='05')
                            {
                                htl.push('<li><span>退货中</span></li>');
                            }
                            else
                            {
                                htl.push('<li><span>退货结束</span></li>');
                            }
                        }
                        htl.push('</ul>');
	                    for ( var j = 0; j < mapp[pp[0]].length; j++)
	                    {
	                    	var goods = mapp[pp[0]][j];
	                    	htl.push('<div class="mil" onclick="OrderDetails(this)"><div class="left">');
	                    	htl.push('<img src="' + basePath + goods[0] + '" alt="">');
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
	                    	htl.push('<h3>' + goods[1] + '</h3>');
	                    	if(goods[8]!=null){
	                    	htl.push('<h3>' + goods[8] + '<span></span><span></span></h3>');
	                    	}
	                    	htl.push('</div>');
	                    	htl.push('<div class="right right2">');
	                    	htl.push('<h3>&yen;<span>' + goods[2] + '</span></h3>');
	                    	htl.push(' <p>x' + goods[3] + '</p>');
	                    	htl.push('</div>');
	                    	htl.push('</div>');
	                    	htl.push('<div class="com_btn com_btn2" align="right">');
	                    	//htl.push('<a ><input type="button" value="联系买家"></a>';
	                    	htl.push('<input type="hidden" id="money" value="' + goods[2] + '">');
				            htl.push('<input type="hidden" id="casidds" value="' + pp[2] + '">');
				            htl.push('<ul class="time2">');
	                    	htl.push('<li>');
	                    	htl.push('<i><img src="' + basePath + 'images/ea/finance/NewPhoneOrders/sellerOrder/ico-time.png"></i>');
	                    	htl.push('<p>订单时间:<span style="padding-left: 0.3rem;">' + pp[4] + '</span></p>');
	                    	htl.push('</li>');
	                    	htl.push('</ul>');
	                    	//00:仅退款  01：退款退货  02：换货 03：维修

                            if (pp[5] == '00')
                            {
                                if (pp[9]=='00')
                                {
                                    htl.push('<a onclick="refusetoReturns(this)"><input type="button" value="拒绝退款" ></a>');
                                    htl.push('<a onclick="agreetoReturns(this)"><input type="button" value="同意退款" ></a>');
                                }
                                else if (pp[9]=='05'){
                                    htl.push('<a onclick="refusetoReturns(this)"><input type="button" value="退款完成" ></a>');
                                }
                                else
                                {
                                    htl.push('<a onclick="refusetoReturns(this)"><input type="button" value="退款中" ></a>');
                                }

                            }
                            else if (pp[5] == '01')
                            {
                                if (pp[9]=='00')
                                {
                                    htl.push('<a onclick="refusetoReturns(this)"><input type="button" value="拒绝退货" ></a>');
                                    htl.push('<a onclick="agreetoReturns(this)"><input type="button" value="同意退货" ></a>');
                                }
                                else
                                {
                                    htl.push('<a onclick="agreetoReturns(this)"><input type="button" value="退货中" ></a>');
                                }
                            }

	                    	htl.push('</div>'); 
				            htl.push('</div>');
						}
						  htl.push('</div>');
					} 
			 }
			 	
				$("#" + cla).append(htl.join(""));
				getHeight(status,cla);
			}
	});
	
}

    
     window.onload = window.onresize = function(){
        var clientWidth = document.documentElement.clientWidth;
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }