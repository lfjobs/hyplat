$(document).ready(function(e) {
  
	//中联园区头部
    $(".wfj_top").attr("style","height:"+window.innerHeight*0.06+"px;line-height:"+window.innerHeight*0.06+"px;");
    $(".wfj_top").find("li").attr("style","width:15%;");
    $(".wfj_top").find("li").find("img").attr("style","height:"+window.innerHeight*0.03+"px;");
    $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+window.innerHeight*0.025+"px;");
	
    $("#body").attr("style","height:"+window.innerHeight+"px;width:"+window.innerWidth+"px;");
    $(".wfj12_011_shouinfo").attr("style"," margin-bottom:"+window.innerHeight*0.015+"px; padding:"+window.innerHeight*0.01+"px 0;");
    $(".wfj12_011_shouinfo").find("li").attr("style","font-size:"+window.innerHeight*0.02+"px; line-height:"+window.innerHeight*0.04+"px;");
	
    $(".wfj12_011_proinfo").attr("style"," padding:"+window.innerHeight*0.015+"px 0; margin-bottom:"+window.innerHeight*0.01+"px;");
    $(".wfj12_011_proinfo").find("td").attr("style","font-size:"+window.innerHeight*0.02+"px;");
	
    $(".wfj12_011_tuiinfo").attr("style"," padding:"+window.innerHeight*0.015+"px 0; margin-bottom:"+window.innerHeight*0.01+"px;");
    $(".wfj12_011_tuiinfo").find("td").attr("style","font-size:"+window.innerHeight*0.02+"px; line-height:"+window.innerHeight*0.03+"px;");
	
    $(".wfj12_011_bottom").find("div").attr("style"," font-size:"+window.innerHeight*0.025+"px; height:"+window.innerHeight*0.05+"px;line-height:"+window.innerHeight*0.05+"px;");
	
    $(".wfj12_011_title").attr("style","font-size:"+window.innerHeight*0.025+"px;height:"+window.innerHeight*0.04+"px; line-height:"+window.innerHeight*0.04+"px;");
	
	/*
	//隐藏滚动条
	$(".wfj12_011_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt(window.innerHeight-$(".wfj_top").height()-$(".wfj_state").height()-window.innerHeight*0.003)+"px;");
	
	$(".wfj12_011_hidden").attr("style","height:"+parseInt($(".wfj12_011_content").height())+"px;");
	
	$(".wfj12_011_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt(window.innerHeight-$(".wfj_top").height()-$(".wfj_state").height()-window.innerHeight*0.003)+"px;");
	
	var h = $(".wfj12_011_title").height()*$(".wfj12_011_title").length+$(".wfj12_011_product").height()*$(".wfj12_011_product").length;
	if(h < $(".wfj12_011_content").height()){
		$(".wfj12_011_hidden").css("width",$(".wfj12_011_content").width()+"px");
	}else{
		$(".wfj12_011_hidden").css("width",parseInt($(".wfj12_011_content").width()+17)+"px");
	}
*/
				$("#returns").click(function(){
					document.location.href=basePath+"page/ea/main/finance/mobile/returnShop.jsp";
								+ "ea/refund/ea_approveOrejectPage.jspa?refundSheet.rsid="
								+ rsid+"&state="+refundstate+"&type=pc";
				});
				$("#right").click(function(){
					
					if (refundType == "00") {
						alert("仅退款,请直接退款转给买家");
						return;
					}

					/*if (refundstate == "01") {
						alert("不可重复操作");
						return;
					}
*/
					document.location.href=basePath
							+ "ea/refund/ea_approveOrejectPage.jspa?refundSheet.rsid="
							+ rsid+"&state="+refundstate+"&type=mobile&photo="+photo;
				});
				
				//弹出层初始化
				$(".jqmWindow").jqm({
					modal : true, 
					overlay : 20
				}).jqmAddClose('.close');

				
            });
