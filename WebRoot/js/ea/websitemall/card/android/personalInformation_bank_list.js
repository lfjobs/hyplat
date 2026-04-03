$(function(){
	
	
	$("#addBank").click(function(){
		
		window.location.href=basePath+"ea/perinfor/ea_getaddBankCardInformation.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl+"&flag="+flag; 
		
		/*if(upgrade = "00"){
			alert("系统升级，暂不能添加银行卡！");
		}else{
			window.location.href=basePath+"ea/perinfor/ea_getaddBankCardInformation.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl; 
		}*/		
	});
	
	
	$(".wfj_return").find("a").click(function(){
		if(flag=='sys'){
			window.location.href=basePath+"mobile/office/mobileoffice_fastApplication.jspa?";
		}else{
			window.location.href=basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
		}
	});
	$(".wfj01_001_popup").click(function(){
		$(".wfj01_001_popup").hide();
	});
	$(".wfj01_007_edit").click(function(){
		$(".wfj01_001_popup").show();
	});
	$(".wfj01_001_popup_body").find("div").click(function(){
		$(".wfj01_007_addcard").attr("class","defaultOption").css("backgroundColor","#FCAE5A").removeClass("wfj01_007_addcard");
	});
	$(".wfj01_007_addcard").live("click",function(){
		window.location.href=basePath+"ea/perinfor/ea_getaddBankCardInformation.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl+"&bankID="+$(this).find("#bankId").val()+"&flag="+flag; 
	});
	$(".defaultOption").live("click",function(){
		if(confirm("是否设置该银行卡为默认银行卡")){
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_setTheDefaultBankCard.jspa?bankAccountID="+$(this).find("#bankId").val()+"&editType="+editType+"&flag="+flag);
			document.form.submit.click();
			token = 2;
		}
	});
	$(".wfj01_001_cardType_body").click(function(){
		var cardType=$("#cardType").val();
		window.location.href = basePath+ "ea/perinfor/ea_getBankCardInformation.jspa?staffId="+ $("#staffId").val()+"&editType="+editType+"&backurl="+backurl+"&flag="+flag+"&cardType="+(cardType=="00"?"01":"00");
	});
});

$(document).ready(function(e) {
         	$("body").css("height",window.innerHeight) ;
         	$("body").css("width",window.innerWidth) ;
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+window.innerHeight*0.03+"px;padding-left:"+window.innerHeight*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+window.innerHeight*0.025+"px;color:#000;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+window.innerHeight*0.03+"px; width:auto; vertical-align:middle;");
			$(".wfj01_007_top").css("height",window.innerHeight*0.058+"px");
			$(".wfj01_007_top").css("lineHeight",window.innerHeight*0.058+"px");
			
			
			
			$(".wfj01_007_bottom").find("div").attr("style","font-size:"+window.innerHeight*0.03+"px; border-radius:"+window.innerHeight*0.01+"px;height:"+window.innerHeight*0.05+"px;line-height:"+window.innerHeight*0.05+"px;");
	 		
			
			
			//银行信息
			$(".wfj01_007_ca").click(function(){
				open("wfj01_008.html","_self");
			});
			var t;
			if($(".txts").eq(0).val()){
				$(".txts").each(function(){
					if($(this).val().length==19)
						t=/^\d{15}(\d{4})$/;
					else
						t=/^\d{12}(\d{4})$/;
					$(this).val($(this).val().replace(t,"**** **** **** $1"));
				});
			}
			$(".wfj01_007_ca").attr("style"," height:"+window.innerHeight*0.06+"px; line-height:"+window.innerHeight*0.06+"px; margin-top:"+window.innerHeight*0.01+"px;");
			$("#title").find("li").attr("style","font-size:"+window.innerHeight*0.025+"px;");
			
			$(".wfj01_007_addcard").each(function(index, element) {
				$(this).attr("style","height:"+window.innerHeight*0.11+"px;line-height:"+window.innerHeight*0.03+"px;margin-top:"+window.innerHeight*0.01+"px;");
				$(this).find("ul").eq(0).attr("style","height:"+window.innerHeight*0.03+"px;line-height:"+window.innerHeight*0.03+"px;padding-top:"+window.innerHeight*0.02+"px;");
				$(this).find("li").eq(0).attr("style","border-bottom:1px solid #F74C31;font-size:"+window.innerHeight*0.025+"px;");
				$(this).find("li").eq(1).attr("style","font-size:"+window.innerHeight*0.02+"px;padding-top:"+window.innerHeight*0.008+"px;padding-left:"+window.innerHeight*0.01+"px;");
				$(this).find("li").eq(2).attr("style","padding-top:"+window.innerHeight*0.01+"px;");
				$(this).find("li").find("input").attr("style","font-size:"+window.innerHeight*0.02+"px;width:"+window.innerWidth*0.4+"px;");
				$(this).find(".right").attr("style","font-size:"+window.innerHeight*0.02+"px;");
				$(this).find(".cardType").attr("style","font-size:"+window.innerHeight*0.02+"px;");
            });
			
			
			$(".typeface").find("div").css("fontSize",window.innerHeight*0.03+"px");
			//隐藏滚动条
			$(".wfj01_007_content").attr("style"," width:"+window.innerWidth+"px;height:"+window.innerHeight*0.82+"px;");
			$(".wfj01_007_hidden").attr("style","width:"+parseInt($(".wfj01_007_content").width()+17)+"px;height:"+parseInt($(".wfj01_007_content").height())+"px;");
			$(".wfj01_007_profile").attr("style","width:"+window.innerWidth+"px;");
			
			//银行信息--隐藏滚动条
			$(".wfj01_007_card").attr("style","width:"+window.innerWidth+"px;height:"+parseInt(window.innerHeight-$(".wfj01_007_top").height()-$(".wfj01_007_ca").height()-$(".wfj01_007_bottom").height()-window.innerHeight*0.02)+"px; overflow:hidden;");
			$(".wfj01_007_hid").attr("style","height:"+$(".wfj01_007_card").height()+"px;overflow:auto;");
			
			var h=$(".wfj01_007_addcard").height()*$(".wfj01_007_addcard").length+window.innerHeight*0.01*$(".wfj01_007_addcard").length;
			if(h < $(".wfj01_007_card").height()){
				$(".wfj01_007_hid").css("width",$(".wfj01_007_card").width()+"px");
			}else{
				$(".wfj01_007_hid").css("width",parseInt($(".wfj01_007_card").width()+17)+"px");
			}
			
			if(window.innerWidth>window.innerHeight){
				$(".wfj01_007").attr("style","width:"+window.innerWidth*0.7+"px;height:"+window.innerHeight+"px;");
				$(".wfj01_007_bottom").attr("style","width:"+window.innerWidth*0.7+"px;height:"+window.innerHeight*0.05+"px;line-height:"+window.innerHeight*0.05+"px;");
			}else{
				$(".wfj01_007").attr("style","width:"+window.innerWidth+"px;height:"+window.innerHeight+"px;");
				$(".wfj01_007_bottom").attr("style","width:"+window.innerWidth+"px;height:"+window.innerHeight*0.05+"px;line-height:"+window.innerHeight*0.05+"px;");
			}
        });
function re_load(){
	window.location.href=window.location.href;
}