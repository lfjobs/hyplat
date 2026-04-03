<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>公司证件信息添加</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycard.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}

</style>
</head>
<body>
<!--网站商城名片头部-->
<form id="companycertificates" method="post" enctype="multipart/form-data">
<input type="hidden" name="certificatetype" value="${certificatetype}"/>
<input type="hidden" name="ccompanyId" value="${ccompanyId }" />
<input type="hidden" id="certificateID" value="${certificateID }"/>
<input type="submit" id="submit" style="display:none;"/>
<section id="wrap">
	<header id="head">
    	<a href="<%=basePath %>ea/industry/ea_CompanyCertificates.jspa?ccompanyId=${ccompanyId}" target="_self" class="return"><img src="<%=basePath%>images/WFJClient/PersonalJoining/wfj_return_01.png" /></a>
        <div class="title">${contactCompany.companyName }</div>
        <a href="javascript:;" class="menu"></a>
    </header>
    <div class="personage">
        <a href="#" class="pre"><img src="<%=basePath%>${contactCompany.logoPath}"/></a>
        <p>${contactCompany.cresponsible }</p>
        <a href="#" class="ewm"><img src="<%=basePath%>images/WFJClient/PersonalJoining/ico_wx.png"/></a>
        <div class="designat"><c:if test="${company.ccomtype eq 0 }">大型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 1 }">中型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 2 }">小型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 3 }">微型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 4 }">供应商企业平台管理商城</c:if>      
        </div>
    </div>
    <div id="prompt" style="width: 100%;display: none;">
        			<center>
        				<div>
        					<span style="position: relative;top: 19.8%;"></span>
        				</div>
        			</center>
    </div> 
		<div class="status">				
				<p>证件信息</p>
				<div class="status_img" id="sfile1">
					<c:choose>
						<c:when
							test="${certificate.certificateAccessory eq ''||certificate.certificateAccessory==null }">
							<img class="add_img" id="add_img"
								src="<%=basePath%>images/WFJClient/PersonalJoining/wfj_addimg_01.png" />
						</c:when>
						<c:otherwise>
							<img class="add_img" id="yulan_img" src="<%=basePath%>${certificatephoto }" />
						</c:otherwise>
					</c:choose>
					<p>上传${certificatetype }正面照</p>
				</div>
				<div class="status_img" id="sfile2">
					<c:choose>
						<c:when
							test="${certificate.certificateAccessory eq ''||certificate.certificateAccessory==null }">
							<img  class="add_img" id="add_img"
								src="<%=basePath%>images/WFJClient/PersonalJoining/wfj_addimg_01.png" />
						</c:when>
						<c:otherwise>
							<img class="add_img" id="yulan_img" src="<%=basePath%>${certificatephoto1 }" />
						</c:otherwise>
					</c:choose>
					<p>上传${certificatetype }背面照</p>
				</div>
		</div>
		<div class="skip">
        <p class="bc"><a href="#">保存信息</a></p>
    	</div>
		<div class="more" id="tfile1">
			<ul>
				<input type="hidden" value="0"/>
				<li><a class="verification" href="javascript:;"><input class="pic" id="p3" type="file" name="pics" accept="image/*" capture="camera"/>拍照</a></li>
				<li><a class="verification" href="javascript:;"><input
						class="pic" id="p1" type="file" name="pics" />从本地上传</a></li>
				<li><a href="javascript:;">取消</a></li>
			</ul>
		</div>
		<div class="more" id="tfile2">
			<ul>
				<input type="hidden" value="1"/>
				<li><a class="verification" href="javascript:;"><input class="pic" id="p4" type="file" name="pics" accept="image/*" capture="camera"/>拍照</a></li>
				<li><a class="verification" href="javascript:;"><input
						class="pic" id="p2" type="file" name="pics" />从本地上传</a></li>
				<li><a href="javascript:;">取消</a></li>
			</ul>
		</div>
	</section></form>

<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>

<script type="text/javascript">
var basePath='<%=basePath%>';
var flag='';
var certificateID='${certificateID}';
$(function(){
	
	//加载公司风格
	var url=basePath+"ea/industry/sajax_ea_CompanyStyle.jspa?ccompanyId=${ccompanyId}";
	$.ajax({
		url : url,
		type : "get",
		async : false,
		success : function cbf(data){
			var member=eval("("+data+")");
			var activities=member.activities;
			if(activities!=null){
				$("#head").css("background",activities.describe);
				$(".personage").css("background",activities.describe);
				$(".bc").css("background",activities.describe);
			}
		},
		error : function cbf(data){
			alert("公司风格加载失败！");
		}
	});
	
	if($(window).width()>$(window).height()){
		$("#wrap").attr("style","width:70%;");
	}else{
		$("#wrap").attr("style","width:100%;");
	}
	if($(window).width()>$(window).height()){
		$(".more").attr("style","width:70%;");
	}else{
		$(".more").attr("style","width:100%;");
	}
    $(".more").find("li").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; border-bottom: 1px solid #F1F1F1;");
	$(".more").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.02+"px;");
	
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.15+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
	$(".designat").css("width",$(window).width()*0.38+"px");
	$('#sfile1').click(function(){
		$("#occlusion2").css("z-index",$("#wrap").css("z-index")+1);
		$("#occlusion2").jqmShow();
		$('#tfile1').css("z-index",$("#occlusion2").css("z-index")+1);
		$('#tfile1').fadeIn(100);	
	}); 
	$('#sfile2').click(function(){
		$("#occlusion2").css("z-index",$("#wrap").css("z-index")+1);
		$("#occlusion2").jqmShow();
		$('#tfile2').css("z-index",$("#occlusion2").css("z-index")+2);
		$('#tfile2').fadeIn(100);	
	}); 
/* 	$('.status_img').toggle(
	function(){
		$(this).prop("id","pic"+select);
		$("#occlusion2").css("z-index",$("#wrap").css("z-index")+1);
		$("#occlusion2").jqmShow();
		$('.more').css("z-index",$("#occlusion2").css("z-index")+1);
		$('.more').fadeIn(100);
	},
	function(){
		if($(this).find("input").length>0){
			$(this).find("input").remove();
		}
		$("#occlusion2").css("z-index",$("#wrap").css("z-index")+1);
		$("#occlusion2").jqmShow();
		$('.more').css("z-index",$("#occlusion2").css("z-index")+1);
		$('.more').fadeIn(100);
	}
	); */
	
	$(".jqmOverlay").live("click",function(){
		$(".more").fadeOut();
		$("#occlusion2").jqmHide();
	});
	$(".jqmWindow").jqm({
		modal : true, 
		overlay : 20
	}).jqmAddClose('.close');

});
function getImgUrl($t){
	var img=new Image();
	var dw=$(".add_img").width(),dh=$(".add_img").height();
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
		rc.width=dw;
		rc.height=dh;
		ct.drawImage(img,0,0,dw,dh);
		var da=rc.toDataURL();
		if($t.attr("id")=='p1'||$t.attr("id")=='p3'){
			$('#sfile1').find("img").attr("src",da);
		}else{
			$('#sfile2').find("img").attr("src",da);
		}
	};
}
//图片预览
	$(".pic").live("change",function(){
		var picPath=$(this);
		getImgUrl(picPath);
		flag=$(this).parent().parent().parent().find("input[type='hidden']").val();
		$(".more").fadeOut();
		$("#occlusion2").jqmHide();
	});
$("input[type='file']").click(function(event){
	event.stopPropagation();
});

//取消
$("#tfile1").find("li").eq(2).click(function(){
	$("#tfile1").fadeOut();
	$("#occlusion2").jqmHide();
});
$("#tfile1").find("li").eq(1).click(function(){
	$("#p1").click();
});
$("#tfile1").find("li").eq(0).click(function(){
	$("#p3").click();
});

$("#tfile2").find("li").eq(2).click(function(){
	$("#tfile2").fadeOut();
	$("#occlusion2").jqmHide();
});
$("#tfile2").find("li").eq(1).click(function(){
	$("#p2").click();
});
$("#tfile2").find("li").eq(0).click(function(){
	$("#p4").click();
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
$(".bc").click(function(){
	var input= new Array();
	var img=new Array();
	$("input[type='file']").each(function(){
		if($(this).val()!=''){
			input.push($(this).val());
		}
	});
	$(".status").find("img[id='yulan_img']").each(function(){
		if($(this).attr("src")!='')
		img.push($(this).attr("src"));
	});
	if(img.length==0&&input.length!=2)
		prompt("图片信息不完整！"); 
	else if(img.length!=2&&input.length==0)
		prompt("图片信息不完整！");
	else{
		var url=basePath+"ea/industry/ea_saveCompanyCertificates.jspa?certificateID="+certificateID+"&flag="+flag;
		$("#companycertificates").attr("action",url);
		$("#submit").click();
	}
});
</script>
</body>
</html>
