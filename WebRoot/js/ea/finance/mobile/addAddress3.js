$(document).ready(function(e) {

			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
          //图片显示，文字提示
            $(".wfj12_014_top_width").attr("style","top:"+$(window).height()*0.02+"px;");
            $(".wfj12_014_top_font2").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
            $(".wfj12_014_top_font2").find("a").attr("style","font-size:"+$(window).height()*0.02+"px;padding:"+$(window).height()*0.01+"px "+$(window).height()*0.02+"px;letter-spacing:"+$(window).height()*0.01+"px;border-bottom:"+$(window).height()*0.002+"px solid #FFF; border-top:"+$(window).height()*0.002+"px solid #FFF;");
            $(".wfj12_014_top_font1").attr("style","width:"+$(".wfj12_014_top_font2").find("a").width()+"px;text-align:center;font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
            $(".wfj12_014_top_width").find("font").attr("style","font-size:"+$(window).height()*0.03+"px;");
            
            $(".wfj12_013_con").find("table#tab").attr("style","margin-top:15px;height:"+$(window).height()*0.5+"px;");
            $(".wfj12_013_con").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.045+"px;");
            $(".wfj12_013_con").find("td").find("input").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.03+"px; border:"+$(window).height()*0.002+"px solid #999;");
            $(".wfj12_013_con").find("td").find("textarea").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.08+"px;width:"+$(window).width()*0.565+"px; border:"+$(window).height()*0.002+"px solid #999;");
			
            
            $(".wfj12_013_bottom").find("div").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;width:"+$(window).height()*0.2+"px; margin:"+$(window).height()*0.3+"px "+ $(window).width()*0.001+"px;");
            
           
            $("#add").click(function(){
            	  if(status=="06"){
            		  $("form#addForm")
            		  .attr("target","hidden")
            		  .attr("action",basePath+"ea/bdbill/ea_refundApply.jspa?id="+id+"&staid="+staid);
            	  }
            	  if(status=="11"){
            		  $("form#addForm")
            		  .attr("target","hidden")
            		  .attr("action",basePath+"ea/refund/ea_applyAfterSale.jspa?id="+id+"&staid="+staid);
            	  }
            		
            		
            		document.addForm.submit.click();
            		token=2;
            	
            	
   
            		
            });
            
    		
        });
function re_load(){
	window.location.href=basePath+"ea/hypb/ea_getCashBill.jspa?staid="+id+"&id="+staid;
}