$(document).ready(function(){
	 loaded();
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
         pl="00";//退货中
         loaded();
     });
     $(".successful").click(function(){
         $("#successful").css("display","block").siblings().css("display","none");      
         pl="01";//退货成功
         loaded();
     });

});
    
    function getHeight(){
   	 t=setTimeout("getHeight()",200);
   	 if($(".last").length>0){
   		 if($(".last").offset().top+$(".last").height()-$(".header").height()*4<$(window).height()){
   			 if(pagenumber<pagecount){
   				 loaded();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
   			 }
   		}		 
   	 }
    }
    
	function loaded(){
		
		clearTimeout(t);
	 	pagenumber++;
	 	
	 	var url = basePath+"ea/refundMoney/sajax_getAjax.jspa?";
		$.ajax({
			url : url,
			type : "POST",
			async : false,
			dataType : "json",
			data : {
				"pageNumber":pagenumber,
				"type":type,
				"staffid":staffid,
				"state":state,
				"pl": pl
			},			
			success : function (data){
				var member = eval("("+data+")");				
				var pageForm = member.pageForm;
				var mapg = member.mapg;
				var maps = member.maps;
				var str = new Array();
				
				if(pageForm != null&&pageForm.recordCount>0){
					
					$(".last").removeClass("last");
					var refundlist = pageForm.list;					
					pagenumber = pageForm.pageNumber;
					pagecount = pageForm.pageCount;
					
					for(var i = 0;i<refundlist.length;i++){
						var pp = refundlist[i];
						
						if(i==refundlist.length-1){
							str.push('<div class="grd last">');
						}else{
							str.push('<div class="grd ">');
						}							
		                str.push('<ul class="com_name">');
		                str.push('<li><div class="head"><img src="'+basePath+pp[7]+'" alt=""></div></li>');
		                str.push('<li>'+pp[6]+'<a href="#;"><img src="'+basePath +'images/ea/finance/NewPhoneOrders/rigth.png" alt=""></a></li>');		                
		                
		                if(pp[8]=="00"){//仅退款
		                	if(pp[4]==05 ){
			                	str.push('<li><span>退款成功</span></li></ul>');
			                }else if(pp[4]==02){
			                	str.push('<li><span>拒绝退款</span></li></ul>');
			                	/*str.push('<li><img src="'+basePath +'images/ea/finance/NewPhoneOrders/refund.png" alt=""></a><span>拒绝退货</span></li>');*/
			                }else{
			                	str.push('<li><span>退款中</span></li></ul>');
			                }
		                }else{
		                	if(pp[4]==05 ){
			                	str.push('<li><span>退货成功</span></li></ul>');
			                }else if(pp[4]==02){
			                	str.push('<li><span>拒绝退货</span></li></ul>');
			                	/*str.push('<li><img src="'+basePath +'images/ea/finance/NewPhoneOrders/refund.png" alt=""></a><span>拒绝退货</span></li>');*/
			                }else{
			                	str.push('<li><span>退货中</span></li></ul>');
			                }
		                }		           
		                //填充商品		                
		                var goods = mapg[pp[0]];
		                var sales = maps[pp[0]];
		                for(var j = 0;j<goods.length;j++){
		                	var goodslist = goods[j];
		                	str.push('<div class="mil"><a href="javascript:;" onclick="refund(\''+pp[0]+'\')">');
	 		                str.push('<div class="left">');	   
	 		                str.push('<img src="'+basePath+goodslist[6]+'" alt="">');
                            if (goodslist[14] != null && goodslist[14] != "" && goodslist[14] == "1") {//批发

                            } else if (goodslist[14] != null && goodslist[14] != "" && goodslist[14] == "2") {//VIP
                                str.push("<span class='sp vip'><i></i></span>")

                            } else if (goodslist[14] != null && goodslist[14] != "" && goodslist[14] == "3") {//普通活动
                                str.push("<span class='sp cx'><i></i></span>")

                            } else if (goodslist[14] != null && goodslist[14] != "" && goodslist[14] == "4") {//特价活动
                                str.push("<span class='sp tj'><i></i></span>")

                            } else {//零售

                            }
	 		                str.push('</div>');
	 		                str.push('<div class="right">');		              
	 		                str.push('<h3>'+goodslist[5]+'</h3>');
	 		                str.push('<h3>产品规格：<span>'+(goodslist[11]==null?"":goodslist[11])+'</span></h3></div>');			                		                	               
	 		                str.push('<div class="right right2">');		              
	 		                str.push('<h3>&yen;<span>'+goodslist[3]+'</span></h3>');
	 		                str.push('<p>x'+goodslist[2]+'</p></div></a></div>');
		                }
		                
		                //填充促销品
		                if(sales!=null&&sales.length>0){
		                	for(var s = 0; s<sales.length;s++){
		                		var saleslist = sales[s];
		                		str.push("<div class='mil'><a href='#'>");
		                		str.push("<div class='left'><img src='"+basePath+saleslist[1]+"' alt=''></div>");
		                		str.push("<div class='right'>");
		                		str.push("<h3>"+saleslist[2]+"</h3>");
		                		str.push("<h3>产品规格："+(saleslist[5]==null?"":saleslist[5])+"</span></h3></div>");				       
		                		str.push("<div class='right right2'>");			                
		                		str.push("<p>x1</p></div></a><img src='"+basePath+"images/ea/finance/NewPhoneOrders/ico-cu.png' class='cu'></div>")
		                	}
		                }		                		              
		                str.push('<ul class="com_total">');		              
		                str.push('<li><p>共计<span>'+goods[0][2]+'</span>件商品 合计：&yen;<span>'+pp[9]+'</span>（含运费&yen;<span>0.00</span>）</p></li>');
		                str.push('</ul>');		                
		                str.push('<div class="com_btn com_btn2" align="right">');
		                
		                if(pp[8]=="00"){//仅退款
		                	str.push('<input type="button" value="退款进度" onclick="ref(\''+pp[0]+'\')"">');
		                }else{
			                str.push('<input type="button" value="退货进度" onclick="ret(\''+pp[0]+'\')"">');
		                }
		                
		                //str.push('<a href="#;"><input type="button" value="评价"></a>');
		                str.push('</div>');
		                str.push('</div>');						
					}
					
				}
				if(pl=="01"){
					$("#successful").append(str.join(""));//退货成功和拒绝退货
				}else{
					$("#appraise").append(str.join(""));//退货中
				}				
				getHeight();
			}		
		});		 			
	}	
	
//退货(退款)详情
function refund(cashid){
	
	document.location.href=basePath+"/ea/pobuy/ea_getCashBill.jspa?cbid="+cashid+"&refund=1"+"&staid="+staffid;	
	
	}			

//退货进度
function ret(cashid){
	window.location.href=basePath+"ea/refundMoney/ea_details.jspa?cashId="+cashid+"&tp=01";	

}
//退款进度
function ref(cashid){
	window.location.href=basePath+"ea/refundMoney/ea_details.jspa?cashId="+cashid+"&tp=00";	

}









