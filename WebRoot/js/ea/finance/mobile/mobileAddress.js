 $(document).ready(function(e) {
            $("body").css("height",$(window).height()) ;
			$(".wfj11_016_content").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()*0.9+"px;overflow:hidden;");
			$(".wfj11_016_hidden").attr("style","width:"+parseInt($(".wfj11_016_content").width()+17)+"px;height:"+parseInt($(".wfj11_016_content").height()*0.94+17)+"px;overflow:auto;");
			//修改字体大小
				$("#tops").find("li").attr("style","float:left;");
				$("#tops").find("li").eq(0).attr("style","width:15%;");
				$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
				$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
				$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
				$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
				
			$(".wfj11_016_top").css("height",$(window).height()*0.08+"px");
			$(".wfj11_016_top").css("lineHeight",$(window).height()*0.08+"px");

			$(".wfj11_016_bottom_address").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			$(".wfj11_016_bottom_address").find("div").attr("style","font-size:"+$(window).height()*0.03+"px;border-radius:"+$(window).height()*0.01+"px;cursor:pointer;");
			$(".wfj11_016_consignee").find("table").attr("style","border-bottom:"+window.innerHeight*0.003+"px solid #E6E5E5;");
			$(".wfj11_016_consignee").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.03+"px;");
			$(".wfj11_016_consignee").attr("style","padding:"+$(window).height()*0.02+"px 0; ");
			$(".changes").attr("style","position:relative;top:"+$(window).height()*0+"px;");
			$(".changes").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.05+"px;height:"+$(window).height()*0.05+"px;");
			$("#tables").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.03+"px;color:red;");
			
			$(".wfj11_016_bottom_address").find("div").click(function(){
				 window.location.href=basePath+"/ea/refund/ea_addAddress.jspa?companyId="+companyId+"&id="+id;
			});
			
			$(".tables").click(function(){
				
				var param3=$(this).find("span#rname").text();
        	    var param1=$(this).find("span#rtel").text();
        	    var param2=$(this).find("span#area").text();
        	    var param4=$(this).find("span#rstreet").text();
        	    
				$(".tables").find(".changeimg").attr("src",basePath+"js/jqModal/css/images_blue/choice_02.png");
				$(this).attr("src",basePath+"js/jqModal/css/images_blue/choice_01.png");
				 window.location.href=basePath+"/ea/refund/ea_approveOrejectPage.jspa?type=mobile&state=11&companyId="+companyId+"&refundSheet.rsid="+rsid+"&photo="+photo+"&param3="+param3+"&param1="+param1+"&param2="+param2+"&param4="+param4;
			});
			$(".changeimg").click(function(event){
				event.stopPropagation();
			
            	var type=1;
            	if(type==1){
            		var param3=$(this).parents("table").find("span#rname").text();
            	    var param1=$(this).parents("table").find("span#rtel").text();
            	    var param2=$(this).parents("table").find("span#area").text();
            	    var param4=$(this).parents("table").find("span#rstreet").text();
            	    var key=$(this).parents("table").attr("id");
            	 
            	
				
				 window.location.href=basePath+"/ea/refund/ea_refundDefault.jspa?companyId="+companyId+"&refundSheet.rsid="+rsid+"&photo="+photo+"&param3="+param3+"&param1="+param1+"&param2="+param2+"&param4="+param4+"&key="+key;
				 }
            	
				
			
              
            });
			
        });
       