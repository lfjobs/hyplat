$(function(){
	$('.status_img').click(function(){
		if(editType=="00"){
		$("#"+$(this).attr("name")).click();
		$this=$(this);}
	});
	$(".return").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getPapersData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
	$(".file").change(function(){
		getImgUrl($(this));
	});
	$(".skip").click(function(){
		if(!$(".positive").attr("class")&&$("#originalPositive").val()=="")
			prompt("请上传证件正面照片");
		else if(!$(".back").attr("class")&&$("#originalBack").val()=="")
			prompt("请上传证件背面照片");
		else{
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_addPapersData.jspa?editType="+editType);
			document.form.submit.click();
			token = 2;
		}
	});
	$(".wfj01_006_ewm").click(function(){
		$("#QRCode").css("display","block");
	});
	$("#QRCode").click(function(){
		$("#QRCode").css("display","none");
	});
});


$(function(){
	//中联园区头部
	if($(window).width()>$(window).height()){
		$("#wrap").attr("style","width:70%;");
	}else{
		$("#wrap").attr("style","width:100%;");
	}
	if($(window).width()>$(window).height()){
		$(".photo").attr("style","width:70%;");
	}else{
		$(".photo").attr("style","width:100%;");
	}
	$("#QRCodeDiv").css("width",window.innerWidth*0.6+"px");
	$("#QRCodeDiv").css("height",window.innerWidth*0.6+"px");
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.145+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
	
	$(".pre").find("img").attr("style","height:"+$(window).height()*0.07+"px; width:"+$(window).height()*0.07+"px;");
	if(editType!="00"){
		$(".display").css("display","none");
	}
	$(".jqmOverlay").live("click",function(){
		$(".photo").fadeOut();
		$("#occlusion2").jqmHide();
	});
	$(".jqmWindow").jqm({
		modal : true, 
		overlay : 20
	}).jqmAddClose('.close');
});

function getImgUrl($t){
	var img=new Image();
	var dw=$this.width(),dh=$this.height();
	var f=$t.prop("files")[0];
	if(f.type.match('image.*')){
		var r = new FileReader();
		r.onload = function(e){
			img.setAttribute('src',e.target.result);
	    };
		r.readAsDataURL(f);
	}
	img.onload=function(){
		ih=img.height,iw=img.width;
		if(iw/ih > dw/dh && iw > dw){
			ih=ih/iw*dw;
			iw=dw;
		}else if(ih > dh){
			iw=iw/ih*dh;
			ih=dh;
		}
		var cv = document.createElement('div');
		cv.innerHTML="<canvas></canvas>";
		var rc = cv.children[0];
		var ct = rc.getContext('2d');
		rc.width=iw;
		rc.height=ih;
		ct.drawImage(img,0,0,iw,ih);
		var da=rc.toDataURL();
		$this.html("<img src="+da+" "+($this.attr("name")=="positive"?"class=positive":"class=back")+">");
	};
}
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
function re_load(){
	window.location.href=basePath+"ea/perinfor/ea_getPapersData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
}