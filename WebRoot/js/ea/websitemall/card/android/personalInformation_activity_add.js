$(function(){
	if(type=="02"||type=="03")
		$(".wfj01_004_shareLink").css("display","block");

		if($(".wfj01_001_con_left").length>=4)
			$(".wfj01_001_con_right").css("display","none");
		$(".wfj01_001_con_left").css("height",$(window).height()*0.092+"px");
		$(".wfj01_001_con_left").find(".wfj01_001_con_img").css("height",$(window).height()*0.092+"px");
	$(".wfj01_001_con_right").click(function(){
		var file=$("#file").clone(true).attr("id","file"+select).
				attr("name","pictureList").addClass("pictureFile");
		var wheelbase=$("#wheelbase").clone(true).attr("id","wheelbase"+select).attr("name","wheelbases");
		
		$this=$("#picture").clone(true).attr("id","picture"+select).css("display","block").attr("name",select);
		$this.css("height",$(window).height()*0.092+"px");
		$("#pictureDiv").append(wheelbase);
		$("#pictureDiv").append(file);
		select++;
		file.click();
	});
	$(".pictureFile").live("change",function(){
		getImgUrl($(this));
	});
	$(".wfj01_001_deleteimg").live("click",function(){
		if($(this).attr("id"))
			fileID+=((fileID==""?"":",")+$(this).attr("id"));
		$("#file"+$(this).parents(".wfj01_001_con_left").attr("name")).remove();
		$("#wheelbase"+$(this).parents(".wfj01_001_con_left").attr("name")).remove();
		$(this).parents(".wfj01_001_con_left").remove();
		$(".wfj01_001_con_right").css("display","block");
	});
	$(".wfj01_001_bottom_width").click(function(){
		$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_addPersonalActivityList.jspa?type="+type+"&editType="+editType+"&fileID="+fileID);
		document.form.submit.click();
		token = 2;
	});
	$("#superior").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityList.jspa?staffId="+$("#staffId").val()+"&type="+type+"&editType="+editType+"&backurl="+backurl;
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
	 var shareLinkJudge = false;
	 $(".wfj01_004_shareLink").live("mousedown",function() {
		var stop = setTimeout(function() {//down 1s，才运行。
			shareLinkJudge = true;
			$(".wfj01_004_shareLink").find("a").attr("style","display:none");
			$(".wfj01_004_shareLink").find("#textarea2").css("display","block");
		},1500);
		 $(".wfj01_004_shareLink").live("mouseup",function() {//鼠标up时，判断down了多久，不足一秒，不执行down的代码。
			if (!shareLinkJudge&&$(".wfj01_004_shareLink").find("a").attr("id")) {
				window.location.href="http://"+$(".wfj01_004_shareLink").find("a").find("font").text();
			}
			window.clearTimeout(stop);
		});
	 });
	 $(".edit").click(function(){
		 if($(".wfj01_001_con_left").find(".wfj01_001_deleteimg").find("img").css("display")=="none")
			 $(".wfj01_001_con_left").find(".wfj01_001_deleteimg").find("img").css("display","block");
		 else
			 $(".wfj01_001_con_left").find(".wfj01_001_deleteimg").find("img").css("display","none");
	 });
});
$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
            $(".wfj01_004_date").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
            $(".wfj01_004_title").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        	$(".wfj01_004_title").find("input").attr("style","height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
        	if($(".wfj01_004_cons").find("a").find("font").text()){
        		$(".wfj01_004_cons").find("textarea").attr("style","font-size:"+$(window).height()*0.03+"px;overflow:hidden;");
        		$(".wfj01_004_shareLink").find("textarea").attr("style","font-size:"+$(window).height()*0.03+"px;overflow:hidden;display:none;");
        	}else{
        		$(".wfj01_004_cons").find("textarea").attr("style","font-size:"+$(window).height()*0.03+"px;overflow:hidden;");
        	}
        	$(".wfj01_004_shareLink").find("a").find("font").attr("style","font-size:"+$(window).height()*0.03+"px;");
            $("#operation").find("img").attr("width",$(window).width()*0.08+"px").attr("height",$(window).width()*0.08+"px");
			$(".wfj01_001_con_left").css("marginBottom",$(window).height()*0.015+"px;");
			$(".wfj01_001_con_right").css("marginBottom",$(window).height()*0.015+"px;");
			$(".wfj01_001_bottom").attr("style"," margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj01_001_bottom_width").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");

			$(".wfj01_001_popimg").find("li").find("p").attr("style","font-size:"+$(window).height()*0.02+"px; height:"+$(window).height()*0.075+"px; line-height:"+$(window).height()*0.025+"px; bottom:"+$(window).height()*0.075+"px;");
			$(".wfj01_001_con_right").find("img").eq(0).css("height",$(window).height()*0.092+"px");

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
			var text = document.getElementById("textarea");
			autoTextarea($("#textarea").height(),text);// 调用
			var text2 = document.getElementById("textarea2");
			autoTextarea($("#textarea2").height(),text2);// 调用
			if(editType!="00"){
				$(".display").css("display","none");
				$("input").attr("readonly","readonly");
				$("textarea").attr("readonly","readonly");
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
function updatePreview(c){
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


function  autoTextarea (he,elem, extra, maxHeight) {
	extra = extra || 0;
	var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
	isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
			addEvent = function (type, callback) {
					elem.addEventListener ?
							elem.addEventListener(type, callback, false) :
							elem.attachEvent('on' + type, callback);
	},
	getStyle = elem.currentStyle ? function (name) {
		var val = elem.currentStyle[name];
		if (name === 'height' && val.search(/px/i) !== 1) {
			var rect = elem.getBoundingClientRect();
			return rect.bottom - rect.top -
					parseFloat(getStyle('paddingTop')) -
					parseFloat(getStyle('paddingBottom')) + 'px';        
			};
 
			return val;
		} : function (name) {
			return getComputedStyle(elem, null)[name];
		},
		minHeight = parseFloat(getStyle('height'));

	elem.style.resize = 'none';
 
		var change = function () {
			var scrollTop, height,
					padding = 0,
					style = elem.style;

			if (elem._length === elem.value.length) return;
			elem._length = elem.value.length;

			if (!isFirefox && !isOpera) {
				padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
		};
		scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
		if (elem.scrollHeight > minHeight) {
			elem.style.height=he+'px';
			if (maxHeight && elem.scrollHeight > maxHeight) {
				height = maxHeight - padding;
				style.overflowY = 'auto';
			} else {
				height = elem.scrollHeight - padding;
				style.overflowY = 'hidden';
			};
			style.height = height + extra + 'px';
				scrollTop += parseInt(style.height) - elem.currHeight;
				document.body.scrollTop = scrollTop;
									
				document.documentElement.scrollTop = scrollTop;
				elem.currHeight = parseInt(style.height);
					
			};
		};
 
		addEvent('propertychange', change);
	addEvent('input', change);
	addEvent('focus', change);
	change();
};

function re_load(){
	window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityList.jspa?staffId="+$("#staffId").val()+"&type="+type+"&editType="+editType+"&backurl="+backurl;
}