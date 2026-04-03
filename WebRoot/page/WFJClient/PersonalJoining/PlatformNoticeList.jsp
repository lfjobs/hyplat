<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>平台公告</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_platform.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/toucher.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
</head>

<body>
<header>
    <ul class="pub_top1">
        <li style="width: 10%;">
            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
        </li>
        <li style="width: 80%;">公告</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content">
    <div class="pub_banner">
        <img width="100%" src="<%=basePath%>/images/WFJClient/PersonalJoining/pub_banner.png">
    </div>
    <ul class="pub_con">
    </ul>
</div>
<script>
	var basePath="<%=basePath%>";
	var ppid = "${ppid }"
	var pagenumber = 0;
	var height = 0;
	var t;
	var pagecount;
	
function loaded () {
	clearTimeout(t);
	pagenumber += 1;
	var url = basePath + "ea/wfjplatform/sajax_ea_AjaxNotice.jspa?ppid="+ppid;
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":pagenumber
		},
		success : function (data) {
			var member = eval("(" + data + ")");
			var list = member.list;
			if (list == null) {
				return;
			} else {
				pagecount=member.pagecount;	
				var htl = new Array();
				for ( var int = 0; int < list.length; int++) {
						var notice = list[int];
						htl.push("<li><div class='pub_top pub_detail' id='"+notice[3]+"'><div class='pub_top_txt' align='left'>");
						htl.push("<h3 align='center'>"+notice[0]+"</h3><span class='pub_txt' limit='40'>"+notice[4]+"</span></div></div>");
						htl.push("<div class='pub_bottom'><div><div class='pub_detail' id='"+notice[3]+"'>");
						if(notice[5]=="1"){
							htl.push("<img src='"+basePath+"/images/WFJClient/PersonalJoining/eye2.png'><span>已读</span>");
						}else{
							htl.push("<img src='"+basePath+"/images/WFJClient/PersonalJoining/eye.png'><span>查看</span>");
						}
						htl.push("</div><p>"+notice[1].substring(0,10)+"</p></div></div></li>");
				}
				if(pagenumber == pagecount){
					htl.push("<div style='text-align:center;font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.05+"px;color:#373737;'>更多公告正在添加中</div>");
				}
				$(".pub_con").append(htl.join(""));
				getHeight(".pub_con",".content","loaded()");
				$(".pub_txt").find("img").css("display","none");
			}
		},
		error:function(data){
			alert("获取项目失败");
		}
	});
	
	jQuery.fn.limit=function(){
	    var self = $("[limit]");
	    self.each(function(){
		    var objString = $(this).text();
		    var objLength = $(this).text().length;
		    var num = $(this).attr("limit");
		    if(objLength > num){
		    $(this).attr("title",objString);
		    objString = $(this).text(objString.substring(0,num) + "...");
		    }
		    })
	    }
	$(function(){
	    $("[limit]").limit();
    })
}
	
	$(".pub_top1").find("li").eq(0).click(function(){
		document.location.href=basePath+"ea/wfjplatform/ea_getPlatformByPpid.jspa?ppid="+ppid+"&type=summary";
	});
</script>

<script>
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");
		loaded();
		//点击查看详细公告
		$(document).on("click",".pub_detail",function(){
			var goodsid = $(this).attr("id");
			document.location.href=basePath+"ea/wfjplatform/ea_noticeDetail.jspa?goodsid="+goodsid+"&ppid="+ppid;
		});
    });
</script>
<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>