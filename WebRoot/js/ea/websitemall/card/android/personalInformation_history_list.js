$(function(){
	if(select==0)
		select=1;
	else
		$("#default").remove();
	
	$(".originalTime").each(function(){
		$(this).val($(this).val().split(" ")[0]);
	});
	$(".wfj01_001_bottom_width").click(function(){
		var bbrs=false;
		$(".wfj01_001_body_layer").each(function(){
			if($(this).attr("id")!="clones"){
				$(this).find("input").each(function(){
						if($(this).val()==""){
							bbrs=true;
							return false;
						}
				});
				if(bbrs)
					return false;
			}
		});
		if(bbrs){
			prompt("请填写所有选项");
		}else{
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_addPersonalHistoryData.jspa?select="+select+"&editType="+editType);
			document.form.submit.click();
			token = 2;
		}
	});
	$(".return").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
	$(".post").click(function(){
		$(this).addClass("postClick");
		$(".wfj01_001_popup").show();
	});
	$(".positionSelection").click(function(){
		$(".postClick").find("input").val($(this).text()).css("color","#8A8A8A");
		$(".postClick").removeClass("postClick");
		$(".wfj01_001_popup").hide();
	});
	$(".addMore").click(function(){
		var $div=$("#clones").clone(true).attr("id","").css("display","block");
		$div.find(".wfj01_001_body_layer_top").find("span").eq(0).text("履历"+(select+1));
		$div.find("input").each(function(){
			$(this).attr("name","staffVo.record['"+select+"']."+$(this).attr("name"));
		});
		$("#clones").before($div);
		select++;
	});
	$(".wfj01_001_body_layer_top").click(function(){
		if($(this).parent().find(".wfj01_001_body_layer_subject").css("display")=="none")
			$(this).find("img").attr("src",$(this).find("img").attr("name")+"1.png");
		else
			$(this).find("img").attr("src",$(this).find("img").attr("name")+"2.png");
		$(this).parent().find(".wfj01_001_body_layer_subject").slideToggle(500);
	});
	$(".wfj01_001_popup").click(function(){
		$(".wfj01_001_popup").hide();
	});
	$("input").on("input propertychange",function(){
		$(this).css("color","#8A8A8A");
	});
	$(".time").on("focus",function(){
		if(editType=="00"){
		timePlugin($(this),"yyyy-MM-dd");
		$(this).css("color","#8A8A8A");
		}
	});
});
function prompt(obj){
	if($("#prompt").css("display")!="none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function(){
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}
$(document).ready(function(e) {
		//中联园区头部
        $(".wfj_top").attr("style","height:"+window.innerHeight*0.058+"px;line-height:"+window.innerHeight*0.058+"px;");
        $(".wfj_top").find("li").attr("style","width:15%;margin-top:-"+window.innerHeight*0.0035+"px");
        $(".wfj_top").find("li").find("img").attr("style","height:"+window.innerHeight*0.03+"px;");
        $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+window.innerHeight*0.028+"px;");

		$(".wfj01_001_hyimg").attr("style","height:"+window.innerHeight*0.08+"px; width:auto;margin:"+window.innerHeight*0.03+"px auto;border-radius:50%");
		$(".wfj01_001_ewm").attr("style","height:"+window.innerHeight*0.02+"px; width:auto;");
		
		$(".wfj01_001_bottom").attr("style","position:absolute;top:"+window.innerHeight*0.9+"px;");
		$(".wfj01_001_bottom_width").attr("style","height:"+window.innerHeight*0.05+"px; line-height:"+window.innerHeight*0.05+"px; font-size:"+window.innerHeight*0.025+"px; border-radius:"+window.innerHeight*0.01+"px;");
				
		$("#prompt").css("position","absolute").css("top",$(window).height()*0.15+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
		
		$(".wfj01_001_body").attr("style","width:"+window.innerWidth+"px;height:"+window.innerHeight*0.81+"px;overflow:hidden;");
		$(".wfj01_001_body_hidden").attr("style","width:"+(window.innerWidth+17)+"px;height:"+window.innerHeight*0.81+"px;overflow:auto;");

		$(".wfj01_001_body_layer").css("fontSize",window.innerWidth*0.045+"px");
		$(".wfj01_001_body_layer").css("width",window.innerWidth*0.8+"px");
		$(".wfj01_001_body_layer").find("div").attr("style","border-radius:"+window.innerWidth*0.02+"px;padding:"+window.innerWidth*0.008+"px;");
		$(".wfj01_001_body_layer").find("input").attr("style","height:"+window.innerHeight*0.04+"px;width:"+window.innerWidth*0.4625+"px;");
		$(".wfj01_001_body_layer_top").attr("style","width:"+window.innerWidth+"px;margin-left:-"+window.innerWidth*0.1+"px;border:0px;background-color:#D1D1D1;cursor:pointer;");
		$(".wfj01_001_body_layer_top").find("img").attr("style","height:"+window.innerWidth*0.078+"px;width:"+window.innerWidth*0.078+"px;margin-left:"+window.innerWidth*0.5+"px;");
		$(".wfj01_001_body_layer_subject").attr("style","border:0px;background-color:#E8E8E8;");
		$(".typeface").css("fontSize",window.innerHeight*0.0265+"px");
		
		$(".fill").find("input").css("height",window.innerHeight*0.022+"px");
		$(".fill").find(".about").css("fontSize",window.innerWidth*0.0375+"px");
		$(".fill").find(".works").css("height",(window.innerHeight*0.0235*2)+"px");
		if(editType!="00"){
			$(".display").css("display","none");
			$("input").attr("readonly","readonly");
		}
        });
function re_load(){
	window.location.href=basePath+"ea/perinfor/ea_getPersonalHistoryData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
}