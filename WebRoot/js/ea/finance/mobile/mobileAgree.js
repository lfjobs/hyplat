
$(document).ready(function(e) {

			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
            $(".wfj12_012_title").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
            $(".wfj12_012_title").find("li").attr("style","font-size:"+$(window).height()*0.025+"px; padding-right:"+$(window).height()*0.01+"px;");
            $(".wfj12_012_title").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto;");
            $(".wfj12_012_product").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_012_product").find("td").find("span").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_012_address").find("div").attr("style","font-size:"+$(window).height()*0.025+"px;");
            $(".wfj12_012_address").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
            $(".wfj12_012_address").find("span").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_012_address").find("td").find("img").attr("style","height:"+$(window).height()*0.025+"px;");
            $(".wfj12_012_address").find(".addresses").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
			
			if(param3!=""){
				$("#receiverName").text(param3);
			}else{
				param3=$("#receiverName").text();
			}
			if(param1!=""){
				$("#receiverTel").text(param1);
			}else{
				param1=$("#receiverTel").text();
			}
			if(param2!=""&&param4!=""){
				param2=param2+param4;
				$("#refundAddress").text(param2);
			}else{
				param2=$("#refundAddress").text();
			}
            $(".wfj12_012_bottom").find("div").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;margin-top:"+$(window).height()*0.01+"px;");
			
			
            $(".wfj12_012_address").find("table").click(function(){
				window.location.href=basePath+"/ea/refund/ea_refundAddressList.jspa?companyId="+companyId+"&photo="+photo+"&refundSheet.rsid="
					+ rsid+"&refundSheet.cashierBillsID="+cashid;
				
			});
            
            $(".wfj12_012_bottom").click(function(){
            	alert(status);
				window.location.href=basePath+"/ea/refund/ea_mobileApproveOrReject.jspa?type=mobile&refundSheet.refundstate="+status+"&refundSheet.rsid="
				+ rsid+"&param3="+param3+"&param1="+param1+"&param2="+param2+"&role=buyer";
				
			});
            
            
        });