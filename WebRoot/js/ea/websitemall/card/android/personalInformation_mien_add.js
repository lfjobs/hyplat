$(function(){
	$(".wfj01_001_con_right").click(function(){
		var file=$("#file").clone(true).attr("id","file"+select).
		attr("name","pictureList").addClass("pictureFile");
		var wheelbase=$("#wheelbase").clone(true).attr("id","wheelbase"+select).attr("name","wheelbases");
		
		$this=$("#picture").clone(true).attr("id","picture"+select).css("display","block").attr("name",select);
		$this.css("height",$(window).height()*0.092+"px");
		$this.css("width",$(window).width()*0.28203+"px");
		$this.css("marginRight",$(window).width()*0.03125+"px");
		$("#pictureDiv").append(wheelbase);
		$("#pictureDiv").append(file);
		select++;
		file.click();
	});
	$(".pictureFile").live("change",function(){
		getImgUrl($(this));
	});
	$(".wfj01_001_deleteimg").live("click",function(){
		$(this).parents(".wfj01_001_con_left").remove();
	});
	$(".wfj01_001_bottom_width").click(function(){
		$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_addMienData.jspa?editType="+editType);
		document.form.submit.click();
		token = 2;
	});
	$("#superior").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getMienList.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
	$("#determine").click(function(){
		if($(".wfj01_001_con_left").length>=3)
			$(".wfj01_001_con_right").css("display","none");
		$(".wfj01_001_con_right").before($this);
		$("#intercept").css("display","none");
		jcrop_api.destroy();
	});
	$("#cancel").click(function(){ 
		$("#intercept").css("display","none");
		jcrop_api.destroy();
	});
	$(".edit").click(function(){
		 if($(".wfj01_001_con_left").find(".wfj01_001_deleteimg").find("img").css("display")=="none")
			 $(".wfj01_001_con_left").find(".wfj01_001_deleteimg").find("img").css("display","block");
		 else
			 $(".wfj01_001_con_left").find(".wfj01_001_deleteimg").find("img").css("display","none");
	 });
	$(".wfj01_001_ewm").click(function(){
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
				$(".wfj01_001").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
				$(".wfj01_001_addph").attr("style","width:"+$(window).width()*0.7+"px;display:none;");
				$(".wfj01_001_popimg").attr("style","width:"+$(window).width()*0.7+"px;");
			}else{
				$(".wfj01_001").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
				$(".wfj01_001_addph").attr("style","width:"+$(window).width()+"px;display:none;");
				$(".wfj01_001_popimg").attr("style","width:"+$(window).width()+"px;");
			}
			
			$(".wfj01_001_title").attr("style","height:"+$(window).height()*0.1+"px;line-height:"+$(window).height()*0.1+"px; margin-top:"+$(window).height()*0.0015+"px;");
			$(".wfj01_001_title").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_001_title").find("div").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px; border-top-left-radius:"+$(window).height()*0.02+"px; border-bottom-left-radius:"+$(window).height()*0.02+"px;");
			$(".wfj01_001_hyimg").attr("style","height:"+$(window).height()*0.08+"px; width:"+$(window).height()*0.08+"px;margin:"+$(window).height()*0.01+"px auto;border-radius:50%");
			$(".wfj01_001_ewm").attr("style","height:"+$(window).height()*0.02+"px; width:auto;");
			$(".wfj01_001_con_title").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj01_001_con_title").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_001_con_title").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
			$(".wfj01_001_con_left").attr("style"," margin-bottom:"+$(window).height()*0.015+"px;");
			$(".wfj01_001_con_right").attr("style"," margin-bottom:"+$(window).height()*0.015+"px;");
			$(".wfj01_001_bottom").attr("style"," margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj01_001_bottom_width").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");
            $("#operation").find("img").attr("width",$(window).width()*0.08+"px").attr("height",$(window).width()*0.08+"px");

			$(".wfj01_001_popimg").find("li").find("p").attr("style","font-size:"+$(window).height()*0.02+"px; height:"+$(window).height()*0.075+"px; line-height:"+$(window).height()*0.025+"px; bottom:"+$(window).height()*0.075+"px;");
			$(".wfj01_001_con_right").find("img").eq(0).css("height",$(window).height()*0.092+"px");
			
		    $(".wfj01_001_addph").find("li").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; border-bottom:"+$(window).height()*0.005+"px solid #F0F0F0;");
			$(".wfj01_001_addph").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.02+"px;");
				
			$(".wfj01_001_depict").find("textarea").attr("style"," height:"+$(window).height()*0.09+"px; line-height:"+$(window).height()*0.03+"px;font-size:"+$(window).height()*0.02+"px;");
			$("#QRCodeDiv").css("width",window.innerWidth*0.6+"px");
			$("#QRCodeDiv").css("height",window.innerWidth*0.6+"px");
		
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
	
			 var flag = false;
			 $(".wfj01_001_bigimg").live("mousedown",function() {
				var $this=$(this);
				var stop = setTimeout(function() {//down 1s，才运行。
					flag = true;
					$this.parent().find(".wfj01_001_deleteimg").find("img").css("display","block");
				},1500);
				 $(".wfj01_001_bigimg").live("mouseup",function() {//鼠标up时，判断down了多久，不足一秒，不执行down的代码。
					if (flag) {
						$(this).parent().find(".wfj01_001_deleteimg").find("img").css("display","block");
					}
					window.clearTimeout(stop);
				});
			 });
	
			$(".wfj01_001_content").attr("style"," width:"+$(".wfj01_001").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj01_001_title").height()-$(window).height()*0.0015)+"px;overflow:hidden;");
			
			var h = $(".wfj01_001_con").height()+$(".wfj01_001_bottom").height()+$(window).height()*0.03;
			
			if(h>$(".wfj01_001_content").height()){
				$(".wfj01_001_hidden").attr("style"," width:"+parseInt($(".wfj01_001_content").width()+17)+"px;height:"+$(".wfj01_001_content").height()+"px;overflow:auto;");
			}else{
				$(".wfj01_001_hidden").attr("style"," width:"+$(".wfj01_001_content").width()+"px;height:"+$(".wfj01_001_content").height()+"px;overflow:auto;");
			}		
        });

function getImgUrl($t){
	var img=new Image();
	var dw=$("#intercept").width()*0.8,dh=$("#intercept").height()*0.4;
	var f=$t.prop("files")[0];
	if(f.type.match('image.*')){
		var r = new FileReader();
		r.onload = function(e){
			img.setAttribute('src',e.target.result);
	    };
		r.readAsDataURL(f);
	}
	img.onload=function(){
		var cv = document.createElement('div');
		cv.innerHTML="<canvas></canvas>";
		var rc = cv.children[0];
		var ct = rc.getContext('2d');
		if(img.height/(img.width/dw)>dh)
			rate=img.height/dh;
		else
			rate=img.width/dw;
		
		rc.width=dw=img.width/rate;
		rc.height=dh=img.height/rate;
		ct.drawImage(img,0,0,dw,dh);
		var da=rc.toDataURL();
		$("#intercept").find("#interceptDiv").width(dw).height(dh);
		$("#interceptImg").width(dw).height(dh);
		$("#intercept").find("#interceptDiv").css("left",($(window).width()-$("#intercept").find("#interceptDiv").width())/2+"px");
		$("#interceptImg").attr("src",da);
		$this.find(".wfj01_001_bigimg").find("img").attr("src",da);
		$("#intercept").css("display","block");
		$("#wheelbase"+(select-1)).val(1+"-"+1+"-"+dw+"-"+dh+"-"+rate);
		$("#interceptImg").Jcrop({
		      onChange: updatePreview,
		      onDblClick:confirmClipping
		      },function(){
		        var bounds = this.getBounds();
		        boundx = bounds[0];
		        boundy = bounds[1];
		        jcrop_api = this;
		      });

	};
}

function confirmClipping(){
	if($(".wfj01_001_con_left").length>=3)
		$(".wfj01_001_con_right").css("display","none");
	$(".wfj01_001_con_right").before($this);
	$("#intercept").css("display","none");
	jcrop_api.destroy();
}
function updatePreview(c)
{
  if (parseInt(c.w) > 0)
  {
    var rx = $(".wfj01_001_con_right").find("img").width() / c.w;
    var ry = $(".wfj01_001_con_right").find("img").height() / c.h;
    $this.find(".wfj01_001_bigimg").find("img").css({
      width: Math.round(rx * boundx) + 'px',
      height: Math.round(ry * boundy) + 'px',
      marginLeft: '-' + Math.round(rx * c.x) + 'px',
      marginTop: '-' + Math.round(ry * c.y) + 'px'
    });
    $("#wheelbase"+(select-1)).val(c.x+"-"+c.y+"-"+c.w+"-"+c.h+"-"+rate);
  }
};
function re_load(){
	window.location.href=basePath+"ea/perinfor/ea_getMienList.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
}