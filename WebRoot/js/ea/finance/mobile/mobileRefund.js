$(function() {
	  
		//中联园区头部
        $(".wfj_top").attr("style","height:"+$(window).height()*0.090+"px;line-height:"+$(window).height()*0.08+"px;position: absolute;");
        $(".wfj_top").find("li").attr("style","width:15%;");
        $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
        $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
		
		//中联园区搜索
		$(".wfj_search").attr("style","height:"+$(window).height()*0.04+"px;margin:"+$(window).height()*0.01+"px auto;padding-top:"+$(window).height()*0.090+"px;");
		$(".wfj_search").find("div").attr("style","height:"+$(window).height()*0.04+"px; border-radius:"+$(window).height()*0.01+"px;");
		$(".wfj_search").find("li").eq(0).attr("style","width:88%;margin-left:2%;");
		$(".wfj_search").find("input").attr("style","height:"+$(window).height()*0.04+"px;font-size:"+$(window).height()*0.02+"px;");
		$(".wfj_search").find("li").eq(1).attr("style","width:8%;margin-right:2%;");
		$(".wfj_search").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
		 	
		//内容
		$(".wfj12_009_con").find("table").attr("style","border-bottom:"+window.innerHeight*0.003+"px solid #E6E5E5;");
		$(".wfj12_009_con").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
		$(".wfj12_009_con").find("span").attr("style","font-size:"+$(window).height()*0.02+"px;");
		$(".wfj12_009_con").find(".wfj12_009_div").attr("style","font-size:"+$(window).height()*0.02+"px;border:"+$(window).height()*0.003+"px solid #E6E5E5; border-radius:"+$(window).height()*0.01+"px; padding:"+$(window).height()*0.003+"px "+$(window).height()*0.01+"px;");
		//隐藏滚动条
		$(".wfj12_009_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj_search").height()-$(window).height()*0.03)+"px;");
		
		$(".wfj12_009_hidden").attr("style","height:"+parseInt($(".wfj12_009_content").height())+"px;");
		
		$(".wfj12_009_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj_search").height()-$(window).height()*0.03)+"px;");
		var h = $(".wfj12_009_con").find("table").height()*$(".wfj12_009_con").find("table").length+$(".wfj12_009_con").find("table").length*$(window).height()*0.003;
		if(h < $(".wfj12_009_content").height()){
			$(".wfj12_009_hidden").css("width",$(".wfj12_009_content").width()+"px");
		}else{
			$(".wfj12_009_hidden").css("width",parseInt($(".wfj12_009_content").width()+17)+"px");
		}
		//查询
		$("#img").click(function(){
			var params=$("#params").val();
			if(params=="退货单号、物品名称、日期"){
				alert("请输入相关信息");
				return;
			}
			window.location.href=basePath+"/ea/refund/ea_getRefundSheetList.jspa?type=mobile&params="+params+"&user="+user;
		});
      if(tt==""){
    	  $(".money").hide(); 
      }else{
    	  $(".edit").hide();
      }
     //判断价格是否为正则表达式
      $(".price").each(function(){
    	  var price=$(this).text();
    	  
    	  var dot = price.indexOf(".");
    	  if(dot ==-1){
	    	      var r = /^[0-9]*[1-9][0-9]*$/;     
	              if(r.test(price)){
	        	  price+=".00";
	        	  $(this).text(price);
	               }
             }else{
            	  var dotCnt = price.substring(dot+1,price.length);
                  if(dotCnt.length == 1){
                	  price+="0";
                	  $(this).text(price);
                  }  
              }
    	  
          
          
         
      
      });

	
      //查看详情
	$(".refundss").click(function(){

		var text=$(this).parents("tr").find("input#status").val();
		var id=$(this).parents("tr").attr("id");
		var risd=$(this).parents("tr").find("input#risd").val();
		var photo=$(this).parents("tr").find("input#phono").val();
		alert(text);
			if(role=="buyer"){
			
	        	  if(text=="03"||text=="08"){
	      			$("form#forms")
	      		    .attr("target","hidden")
	      		    .attr("action",basePath+"/ea/refund/ea_confirmRefund.jspa?type=mobile&refundSheet.rsid="+risd+"&photo="+photo+"&user="+user+"&status="+text);
	      		document.forms.submit.click();
	      		token=2;
	      		    }
	        	  if(text=="04"){
	      		    	alert("确认收货");
	      		    }
	        	  if(text=="01"){
	      		    	alert("同意退货");
	      		    }
	        	  if(text=="00"||text=="06"){
	  			   window.location.href=basePath+"/ea/refund/ea_getEditOrPrintPage.jspa?id="+id+"&type=mobile&photo="+photo;

	             }
	        	  
	          }
		
	});

	
});

function re_load(){
		window.location.href=basePath+"/ea/refund/ea_getRefundSheetList.jspa?stype=01&type=mobile&user="+user+"&role="+role;

}

