<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script id="jsID" src="<%=basePath%>js/jquery-1.3.2.js" type="text/javascript"></script>
	
	<title>${listmenu[0][0] }</title>
	<style>
		/*reset*/
		*{margin:0;padding:0;font-size:12px; line-height:16px;}
		li,dl,dt,dd{list-style:none;}
		img{border:none 0; cursor:pointer;}
		a{text-decoration:none;color:#333;}
		body{background:#e6e6e6;}
		/*head*/
		.head{width:100%;height:48px; background-color:#43abe1; text-align:center; color:#fff; font-family:"微软雅黑"; font-size:18px; line-height:48px;}
		.content{margin:auto;margin-top:10px;width:90%; background-color:#fff; border:#c8c8c8 solid 1px;}
		.text{width:90%;margin:auto;}
		.text p{ font-family:"宋体"; font-size:14px; color:#636363;margin:auto; line-height:24px;}
		.text .second{margin-top:15px;}
		.pic{margin:auto;width:298px;height:316px;margin-top:30px;}
	</style>
	<script type="text/javascript" >
var winHeight;
var winWidth;
                       
if (window.innerWidth)
    winWidth = window.innerWidth;
else if ((document.body) && (document.body.clientWidth))
    winWidth = document.body.clientWidth;
// 获取窗口高度
if (window.innerHeight)
    winHeight = window.innerHeight;
else if ((document.body) && (document.body.clientHeight))
    winHeight = document.body.clientHeight;
    // 通过深入 Document 内部对 body 进行检测，获取窗口大小
if (document.documentElement && document.documentElement.clientHeight  && document.documentElement.clientWidth){
    winHeight = document.documentElement.clientHeight;
    winWidth = document.documentElement.clientWidth;
}
                    
	function init(){
    // 先获取想要改变的所有图片对象（集合）
    var obj=document.getElementsByTagName("img");
    for(var i=0;i<obj.length;i++){
        var width = obj[i].width;
        //alert(width +"=="+winWidth);
        // 判断图片宽度是否大于屏幕宽度
        
       if(width > winWidth){
    	   width = winWidth-50;
           obj[i].style.width=width;
       }
    }
   	
}
</script>
</head>
<body  onload="init();">
	<div class="content" style="padding:20px;">
		<div class="title" style="font-size: 20px;font-weight: 700;line-height: 24px;word-wrap: break-word; border-bottom: 1px solid #ccc;">
			${listmenu[0][0] }
		</div>
		<div class="text" style=" color:#8c8c8c; width:100%; padding-top:10px;padding-bottom:10px;">
			<fmt:formatDate value="${listmenu[0][1] }"  pattern="yyyy-MM-dd" />&nbsp;&nbsp;<span style=" color:#607fa6; width:100%;  cursor: pointer;">${ listmenu[0][3]}</span>
		</div>
		<div class="text">
	    	<jsp:include page="../../../upload_files/wechatmenu/${listmenu[0][2]}"  flush="true" />
	    </div>
	</div>
</body>
</html>