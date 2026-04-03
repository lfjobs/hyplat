$(function(){
	$("#mien_add").live("click",function(){
		window.location.href=basePath+"ea/perinfor/ea_getAddMienData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
	$(".wfj12_025_deleteimg").click(function(){
		if($("#mien_remove").val()=="")
			$("#mien_remove").val($(this).parent().find(".materialID").val());
		else
			$("#mien_remove").val($("#mien_remove").val()+","+$(this).parent().find(".materialID").val());
		$(this).parent().parent().remove();
	});
	$("#mien_save").live("click",function(){
		if($("#mien_remove").val()=="")
			location.reload();
		else{
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_removeMienData.jspa?editType="+editType);
			document.form.submit.click();
			token = 2;
		}
	});
	$(".edit").click(function(){
		 if($(".wfj12_025_con_data").find(".wfj12_025_deleteimg").find("img").css("display")=="none"){
			 $(".wfj12_025_con_data").find(".wfj12_025_deleteimg").find("img").css("display","block");
			 $("#mien_add").text("保存当前所展示风采");
			 $("#mien_add").attr("id","mien_save");
		 }else{
			 $(".wfj12_025_con_data").find(".wfj12_025_deleteimg").find("img").css("display","none");
			 $("#mien_save").text("添加展示我的风采");
			 $("#mien_save").attr("id","mien_add");
		 }
	 });
	$(".wfj12_025_ewm").click(function(){
		$("#QRCode").css("display","block");
	});
	$("#QRCode").click(function(){
		$("#QRCode").css("display","none");
	});
});
$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
			
			if($(window).width()>$(window).height()){
				$(".wfj12_025").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
				$(".wfj12_025_popimg").attr("style","width:"+$(window).width()*0.7+"px;");
				$(".wfj12_025_deleteimg").find("img").attr("style"," top:-"+$(window).height()*0.01+"px; right:"+$(window).height()*0.03+"px;");
			}else{
				$(".wfj12_025").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
				$(".wfj12_025_popimg").attr("style","width:"+$(window).width()+"px;");
				$(".wfj12_025_deleteimg").find("img").attr("style"," top:-"+$(window).height()*0.01+"px; right:"+$(window).height()*0.01+"px;");
			}
			
			$(".wfj12_025_title").attr("style","height:"+$(window).height()*0.1+"px;line-height:"+$(window).height()*0.1+"px; margin-top:"+$(window).height()*0.0015+"px;");
			$(".wfj12_025_title").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_025_title").find("div").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px; border-top-left-radius:"+$(window).height()*0.02+"px; border-bottom-left-radius:"+$(window).height()*0.02+"px;");

			$(".wfj12_025_hyimg").attr("style","height:"+$(window).height()*0.08+"px; width:auto;");
			$(".wfj12_025_ewm").attr("style","height:"+$(window).height()*0.02+"px; width:auto;");
			$(".wfj12_025_con_title").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
			$(".wfj12_025_con_left").attr("style"," margin-bottom:"+$(window).height()*0.015+"px;");
			$(".wfj12_025_con_right").attr("style"," margin-bottom:"+$(window).height()*0.015+"px;");
			$(".wfj12_025_bottom").attr("style"," margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj12_025_bottom_width").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");


			$(".wfj12_025_popimg").find("li").find("p").attr("style","font-size:"+$(window).height()*0.02+"px; height:"+$(window).height()*0.075+"px; line-height:"+$(window).height()*0.025+"px; bottom:"+$(window).height()*0.075+"px;");
			$("#photo").attr("style","border-radius:50%;width:"+$(".wfj12_025_hyimg").height()+"px;height:"+$(".wfj12_025_hyimg").height()+"px;overflow:hidden;margin:"+$(window).height()*0.01+"px auto;");

			$(".wfj12_025_bigimg").find("img").attr("style","width:"+$(window).width()*0.41+"px;height:"+$(window).height()*0.127+"px;");
			
			$("#QRCodeDiv").css("width",window.innerWidth*0.6+"px");
			$("#QRCodeDiv").css("height",window.innerWidth*0.6+"px");
			
			 var flag = false;
			 $(".wfj12_025_bigimg").mousedown(function() {
				var $this=$(this);
                var stop = setTimeout(function() {//down 1s，才运行。
                	if(editType=="00"){
	                    flag = true;
						$this.parent().find(".wfj12_025_deleteimg").find("img").css("display","block");
						$("#mien_add").text("保存当前所展示风采");
						$("#mien_add").attr("id","mien_save");
					}
                },1500);
				 $(".wfj12_025_bigimg").mouseup(function() {//鼠标up时，判断down了多久，不足一秒，不执行down的代码。
				    if (!flag) {
                        $("#occlusion2").css("z-index",$(".wfj12_018").css("z-index")+1);
						$("#occlusion2").jqmShow();
						$(".wfj12_025_popimg").css("z-index",$("#occlusion2").css("z-index")+1);
						$(".wfj12_025_popimg").find("img").attr("src",$(this).find("img").attr("src"));
						$(".wfj12_025_popimg").find("p").text($(this).parent().find("input").val());
						$(".wfj12_025_popimg").fadeIn(1000);
                    }else{
                    	if(editType=="00"){
                    		$(this).parent().find(".wfj12_025_deleteimg").find("img").css("display","block");
                    	}
					}
					window.clearTimeout(stop);
                });
             });

			$(".jqmOverlay").live("click",function(){
				$(".wfj12_025_popimg").fadeOut(1000);
				$("#occlusion2").jqmHide();
			});
			
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
			
			
			
			$(".wfj12_025_content").attr("style"," width:"+$(".wfj12_025").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj12_025_title").height()-$(window).height()*0.0015)+"px;overflow:hidden;");
			
			var h = $(".wfj12_025_con").height()+$(".wfj12_025_bottom").height()+$(window).height()*0.03;
			
			if(h>$(".wfj12_025_content").height()){
				$(".wfj12_025_hidden").attr("style"," width:"+parseInt($(".wfj12_025_content").width()+17)+"px;height:"+$(".wfj12_025_content").height()+"px;overflow:auto;");
			}else{
				$(".wfj12_025_hidden").attr("style"," width:"+$(".wfj12_025_content").width()+"px;height:"+$(".wfj12_025_content").height()+"px;overflow:auto;");
			}
			
			
			$(".wfj_return").click(function(){
				window.location.href=basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
			});
			if(editType!="00")
				$(".display").css("display","none");
			
        });
function re_load(){
	window.location.href=basePath+"ea/perinfor/ea_getMienList.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
}