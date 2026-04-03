$(function(){
	$(".wfj12_007_bottom").click(function(){
		var z1="([\u4e00-\u9fa5])";   //判断中文
		var z2="[a-zA-Z]";				//判断字母
		var z3="[0-9]";						//判断数字
		var z4=/[\s`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]/;  //判断特殊字符
		var z5= /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;   //判断邮箱
		if($("#name").val()=="")
			prompt("姓名不可为空！");
		else if(!new RegExp(z1+"|"+z2).test($("#name").val()))
			prompt("姓名只能为中文或字母！");
		else if($("#name").val().length>4)
			prompt("姓名输入错误！");
		else if($("#birthday").val()=="")
			prompt("生日不可为空！");
		else if($("#localAreaValue").val()=="")
			prompt("详细地址不可为空！");
		else if(z4.test($("#localAreaValue").val())&&$("#localAreaValue").val())
			prompt("详细地址存在特殊符号！");
		else if($("#phone").val()=="")
			prompt("电话不可为空！");
		else if(!new RegExp(z3+"{11}").test($("#phone").val())&&$("#phone").val())
			prompt("电话格式错误！");
		else if($("#weixin").val()=="")
			prompt("微信不可为空！");
		else if(!new RegExp(z2+"|"+z3).test($("#weixin").val())&&$("#weixin").val())
			prompt("微信格式错误！");
		else if($("#job").val()=="")
			prompt("职位不可为空！");
		else if(z4.test($("#job").val()))
			prompt("职位存在特殊符号！");
		else if($("#trade").val()=="")
			prompt("行业不可为空！");
		else if(z4.test($("#trade").val()))
			prompt("行业存在特殊符号！");
		else if($("#referenceOrganization").val()=="")
			prompt("邮箱不可为空！");
		else if(!new RegExp(z5).test($("#referenceOrganization").val())&&$("#referenceOrganization").val())
			prompt("邮箱格式错误！");
		else if($("#referenceCode").val()=="")
			prompt("QQ号码不可为空！");
		else if(!new RegExp(z3+"{5,10}").test($("#referenceCode").val())&&$("#referenceCode").val())
			prompt("QQ号码格式错误！");
		else{
			var address=$("#location_p option:selected").val()+"/"+$("#location_c option:selected").val()+"/"+$("#location_a  option:selected").val();
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_SaveBusinessCard.jspa?editType="+editType+"&staffVo.cstaff.provinceAddress="+address);
			document.form.submit.click();
			token = 2;
		}
	});
	$(".position_body").attr("style","width:"+(window.innerWidth*0.9+17)+"px;height:"+(window.innerHeight*0.97)+"px;overflow-y:scroll");
	$("#trade").click(function(){
		$("#position").show();
		industry("scode20150815wygb79q82p0000000005");
		$(this).addClass("code");
	});
	$("#job").click(function(){
		$("#position").show();
		industry("scode20160616493hsghahy0000000002");
		$(this).addClass("code");
	});
	$(document).on("click","#educationValue",function(){
		$("#position").show();
		industry("scode20100331mk6yn5b5f60000000008");
		$(this).addClass("code");
	});
	$(document).on("click",".position_body div",function(){
		if(!industry($(this).attr("id"))){
			$(".code").val($(this).text()).removeClass("code");
			$("#position").hide();
		}
	});
	$(".image").click(function(){
		var file='<input type="file" accept="image/*" id="file'+select+'" class="file" name="pictureList" style="display: none;"/>';
		$(".content").append(file);
		$("#file"+select).click();
		select++;
	});
	$(document).on("change",'.file',function(){
		$(".file").each(function(){
			if($(this).attr("id")!="file"+(select-1)){
				$(this).remove();
			}
		});
		getImgUrl($(this));
	});
	$(".wfj12_007_top").find("a").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
	$(".determine").click(function(){
		$("#intercept").css("display","none");
		jcrop_api.destroy();
	});
	$(document).on("focus",".time",function(){
		if(editType=="00"){
		timePlugin($(this),"yyyy-MM-dd");
		//$(this).css("color","#8A8A8A");
		}
	});
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
    $("#operation").find("img").attr("width",$(window).width()*0.08+"px").attr("height",$(window).width()*0.08+"px");
   
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
		updatePreview("cancel");
		$("#intercept").find("#interceptDiv").width(dw).height(dh);
		$("#interceptImg").width(dw).height(dh);
		$("#intercept").find("#interceptDiv").css("left",($(window).width()-$("#intercept").find("#interceptDiv").width())/2+"px");
		$("#interceptImg").attr("src",da);
		$("#exhibition").find("img").eq(0).attr("src",da);
		$("#intercept").css("display","block");
		$("#wheelbase").val(1+"-"+1+"-"+dw+"-"+dh+"-"+rate);
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
	$("#intercept").css("display","none");
	jcrop_api.destroy();
}
function updatePreview(c){ 
  if(c=="cancel"){
	  $(".wfj12_007_pos").find("img").eq(0).attr("style","width:100%;height:100%;");
	  return;
  }
  if (parseInt(c.w) > 0)
  {
    var rx = $("#exhibition").width() / c.w;
    var ry = $("#exhibition").height()/ c.h;
    $(".content").find("img").eq(0).css({
      width: Math.round(rx * boundx) + 'px',
      height: Math.round(ry * boundy) + 'px',
      marginLeft: '-' + Math.round(rx * c.x) + 'px',
      marginTop: '-' + Math.round(ry * c.y) + 'px'
    });
    $("#wheelbase").val(c.x+"-"+c.y+"-"+c.w+"-"+c.h+"-"+rate);
  }
};

function industry(codeID){
	$.ajax({
		url:basePath+"ea/perinfor/sajax_ea_getListCCodeByPID.jspa?codeID="+codeID,
		type:"post",
		async : false,
		success:function(data){
			var member = eval("(" + data + ")");
			var codeList=member.codeList;
			if(codeList==null||codeList.length<1)
				boo=false;
			else
				boo=true;
			var tr="";
			for(var i=0;i<codeList.length;i++){
				tr+="<div id='"+codeList[i].codeID+"'>"+codeList[i].codeValue+"</div>";
				tr+="<hr/>";
			}
			if(tr!=""){
				$(".position_body").html(tr);
				$(".position_body").find("div").attr("style","height:"+(window.innerHeight*0.05)+"px;font-size:"+(window.innerHeight*0.03)+"px;line-height:"+(window.innerHeight*0.06)+"px;");
			}
		},error:function(data){
			alert("数据获取失败");
		}
	});
	return boo;
}

function re_load(){
	window.location.href=basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
}