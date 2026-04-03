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
			
            $(".wfj12_013_con").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;color:black; height:"+$(window).height()*0.045+"px;font-weight: bold;");
            $(".wfj12_013_con").find("span.aa").attr("style","float:right;");
            $(".wfj12_013_con").find("span").find("input").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.03+"px; border:none;font-weight: bold; text-align:right;margin-top:5px; ");
            $(".wfj12_013_con").attr("style","height:"+$(window).height()+"px; ");
            $(".wfj12_013_con").find("span").find("input#checkbox").attr("style","height:"+$(window).height()*0.04+"px;width:"+$(window).width()*0.04+"px;");
            $(".wfj12_013_bottom").find("div#add").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;width:"+$(window).height()*0.2+"px; float:right;");
            
           
            $("#add").click(function(){
            	var type="";
            	if($("input[name='row_checkbox']").attr("checked")==true){
            		type=1;
            	}
            	  if(type==1){
            		  $("form#addForm")
           		   .attr("target","hidden")
           		   .attr("action",basePath+"ea/refund/ea_refundAddress.jspa?companyId="+companyId+"&type=1"+"&refundSheet.rsid="+rsid+"&key="+key);
            		  document.addForm.submit.click();
            		  token=2;
            	  }/*else{
            		  param2=param2+param4;
            		  window.location.href=basePath
      				+ "ea/refund/ea_approveOrejectPage.jspa?refundSheet.rsid="
      				+ rsid+"&state=11&type=mobile&param3="+param3+"&param1="+param1+"&param2="+param2+"&photo="+photo;
            		  
            		  }*/
            		
      
   
            		
            });
       
    		
        });
function re_load(){
	window.location.href=basePath+"/ea/refund/ea_refundAddressList.jspa?id="+id+"&type=mobile&companyId="+companyId;
}