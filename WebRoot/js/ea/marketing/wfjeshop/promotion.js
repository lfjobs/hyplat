/**
 * 促销品搭配
 */
$(document).ready(function(e) {

         	$("body").css("height",$(window).height()) ;
         	$("body").css("width",$(window).width()) ;
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.04+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.03+"px;color:#000;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
			

			$(".wfj11_013_top").css("height",$(window).height()*0.08+"px");
			$(".wfj11_013_top").css({"lineHeight":$(window).height()*0.08+"px","border-bottom":"1px solid #e3e3e3"});
			$(".wfj11_013_hidden").css("overflow","hidden");
			$(".wfj11_013_auto").css("overflow","auto");

			$(".wfj11_013_referral").find("li").attr("style","font-size:"+$(window).height()*0.02+"px; line-height:"+$(window).height()*0.03+"px;");
			$(".wfj11_013_referral").find("h2").attr("style","font-size:"+$(window).height()*0.03+"px; padding-top:"+$(window).height()*0.01+"px;");
			$(".wfj11_013_bottom").css("padding-bottom",$(window).height()*0.1+"px");

			$(".wfj11_013_hidden").attr("style","height:"+$(window).height()*0.84+"px;");
			$(".wfj11_013_auto").attr("style","height:"+$(window).height()*0.84+"px; overflow:auto");
			$(".wfj11_013_auto").attr("style","width:"+parseInt($(".wfj11_013_hidden").width()+17)+"px;height:"+$(".wfj11_013_hidden").height()+"px;overflow:auto;");

			//修改文字及高度自适应--底部通用
			$(".wfj11_013_bottoms").attr("style","height:"+$(window).height()*0.09+"px;");
			$(".wfj11_013_bottoms").find("li").attr("style"," line-height:"+$(window).height()*0.08+"px;");
	        $(".wfj11_013_bottoms").find("li:nth-child(1)").attr("style"," line-height:"+$(window).height()*0.04+"px;");
	        $(".wfj11_013_bottoms").find("li:nth-child(2)").attr("style"," line-height:"+$(window).height()*0.07+"px;");
	        $(".wfj11_013_bottoms").find("li").find("a").attr("style","font-size:"+$(window).height()*0.025+"px;color:#000;");
			$(".wfj11_013_bottoms").find("li").find("div").attr("style","font-size:"+$(window).height()*0.025+"px;");


	        $(".gou").click(function(){
	
	            $(this).toggleClass("gou2");
	     
	        });
			
			
			$(document).on("click",".items label",function(){

				$(this).parent().find("label").css({"background":"#fff" ,"color":"#737373"});
				$(this).parent().find("label").removeClass("xz");
				$(this).css({"background":"#FF6507", "color":"#fff"});
				$(this).addClass("xz");
			});	
            //选择属性
				$(document).on("click",".co label",function(){					
                    $(".box2 .summary2 .stock p span:nth-child(1)").text("添加");
                    $(".box2 .summary2 .sback ").css("right","10px");
                    $(".box2 .summary2 .stock p span").css("padding","0");               
                    $("#color").text($(this).text());
                });
				$(document).on("click",".guige label",function(){				
                    $(".box2 .summary2 .stock p span:nth-child(1)").text("添加");
                    $(".box2 .summary2 .sback ").css("right","10px");
                    $(".box2 .summary2 .stock p span").css("padding","0");              
                    $("#guige").text($(this).text());
                });

                
            //确定后购属性返回值
           $(".choose_size2 .bottom_button2 button").click(function(){
                var shop_zhi=$("#color").text()+"，"+$("#guige").text();
                    $(".flag button span").text(shop_zhi);
                    $(".flag").attr("class","shop_txt");
                    $(".shop_txt button").css("color","#000");
                    $(".shop_txt button img").css("margin","0.5rem 0 0 0");               
                    $(".box2").css("display","none") //弹框隐藏                    
                });

            //选择弹框

            $(".shop_txt button").click(function(){
            	$(this).parent().attr("class","shop_txt flag");
            	var url=basePath+"ea/productslaunch/sajax_ea_getAttr.jspa?";
            	var companyId=$(this).find("input").eq(0).val();
            	var ppId=$(this).parent().find("input").eq(0).val();
           	$.ajax({
            		url:url,
            		type:"get",
            		async: false,
            		dataType:"json",
            		data:{
            			"companyId":companyId,
            			"ppId":ppId
            		},
            		success: function cbf(data){
            			var member=eval("("+data+")");
            			var list=member.attrlist;
            			$(".color").html("");
            			if(list!=null&&list.length>0){
            				$(".box2").find(".img").find("img").attr("src",basePath+list[0][3]);
            				$(".box2").find(".pricer").text("￥"+list[0][2]);
            				$(".box2").find(".pricer").find("span").text("(库存"+list[0][7]+"件 )");
            				$.each(list,function(i,e){
            					if(e[8]!=null){
            						if(e[9]=='1'){
            							$(".co").append("<label>"+e[8]+"</label>");
            							$(".co").parent().find("h2").text(e[10]);
            						}else{
            							$(".guige").append("<label>"+e[8]+"</label>");
            							$(".guige").parent().find("h2").text(e[10]);
            						}
            						$(".box").css("display","block");
                    	            $("body .wfj11_013").css("overflow","hidden");
                    	            $(".bottom_button1").css("display","none");
                    	            $(".bottom_button2").css("display","block");                  	          
            					}else{
            						alert("此产品暂时没有商品属性");
            						 $(".flag button span").text("暂无商品属性");
            		                 $(".flag").attr("class","shop_txt");
            					}           					
            				});
            				if($(".co").parent().parent().find("input").length<1){
              				  $(".co").parent().parent().append("<input type='hidden' value='"+list[0][1]+"'/>");
            				}
            			}
            		}
            	});
               
                
            });
            //点击关闭
            $(".sback").click(function(){
            	 $(".flag").attr("class","shop_txt");
            	 $(".box2").css("display","none");
            });
            //立即购买
            $(document).on("click","#buynow",function(){
            	var ptppid="";           	          	
            	$("#ppid").val($(".shop_txt").eq(0).find("input").eq(0).val());
            	$("#count").val(count);
            	var t=$(".shop-mach_grd").eq(0).find(".shop_txt button span").text()
            	if(t==""){
            		$("#standard").val("");            		
            	}else if (t.indexOf("选择商品属性")!=-1){
            		alert("请选择主产品属性");
            		return;
            	}else {
            		$("#standard").val(t);
            	}
          
            	if($(".shop-mach_grd").find(".gou2").length==0){
            		alert("请选择促销品！");
            		return;          		
            	}
            	if($(".shop-mach_grd").find(".gou2").length>2){
            		alert("促销产品只可以选择2种！");
            		return;
            	}
            	if($(".shop-mach_grd").find(".gou2").length>0){
            		var temp =$(".shop-mach_grd").find(".gou2").parent().parent().parent().find(".shop_txt").find("button span").text();
            		if(temp!="undefined"&&temp.indexOf("选择商品属性")==0){
            			alert("请选择促销品属性");
            			return;
            		}
            	} 
                $(".shop-mach_grd").each(function(){
               		if($(this).find(".gou2").length>0){      		
               		var temp=$(this).find(".shop_txt button span").text();
               		ptppid+=$(this).find(".shop_txt").find("input").eq(0).val()+",";
               		ptstandard+=(temp==""?"无":temp)+",,";
               		}	            
               });
     
            
           /*   $("#standard").val($(".shop_txt span").eq(0).text()); 
          	if($(".shop-mach_grd").find(".gou2").length>0){
            		var str=$(".shop-mach_grd").find(".gou2").parent().parent().parent().find(".shop_txt");                   
                    if(!isContains(str)){
                    	alert("请选择促销商品属性");
                    	return;
                    }	         		                 		
                    else{
                    	$.each(str,function(i,e){
                    		ptstandard+=($(e).find("span").text()=='undefined'?'无':$(e).text())+",,";
                    	});                	
                    }          		
            	}	*/           
            	$("#ptstandard").val(ptstandard);
                $("#ptppid").val(ptppid);              
            	document.orderForm.submit.click();
            });
		//滚动 监听

		$(".wfj11_013_auto").scroll(function(){
			var htmlH = document.getElementById("wfj11_013_auto").scrollHeight
			var bod=parseInt($("body").height()-$(".wfj11_013_bottoms").height()-$(".sctop").height());
			if(parseInt($(".sctop").offset())==bod){
			$("div.wfj11_013_referral_con").slideDown()
            if(parseInt($(".sctop").offset())>bod){
            	$(".sctop").css("display","none")
			     }
			   }
			});
			
});//加载完毕

   function isContains(){
	   var flag=true;
       $(".shop-mach_grd").each(function(){
       		if($(this).find(".gou2").length>0){      		
       		var temp=$(this).find(".shop_txt button span").text();
       		ptstandard+=(temp=="undefined"?"无":temp)+",,";
       			if(temp.indexOf("选择商品属性")){
       			alert("请选择促销商品属性");
       			flag=false;
       			return false;
       			}
       		}	            
       });
	   return flag;
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
