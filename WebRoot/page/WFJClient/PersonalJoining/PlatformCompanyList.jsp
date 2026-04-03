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
<title>平台入驻公司</title>
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
       	<li style="width: 80%;">申请加入联营经济平台会员</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content">
    <ul class="enter-com"></ul>
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
    var url = "ea/wfjplatform/sajax_ea_AjaxCompany.jspa?ppid="+ppid;
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":pagenumber,
		  "pageForm.pageSize":15
		},
		success : function (data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			if (pageForm == null) {
				return;
			} else {
				pagecount=pageForm.pageCount;	
				pagenumber=pageForm.pageNumber;
				var list=pageForm.list;
				var htl = new Array();
				for ( var int = 0; int < list.length; int++) {
					var company = list[int];
        			htl.push("<li class=''><div><img src='"+basePath+company[1]+"'></div>");
					htl.push("<h1>"+company[0]+"</h1></li><div class='clearfix'></div>");
					htl.push("<hr style='border-top: 1px solid #ddd;width: 95%;margin: 0.5rem auto;'>");
				}
				if(pagenumber == pagecount){
					htl.push("<div class='more'>更多公司正在入驻</div>");
				}
				$(".enter-com").append(htl.join(""));
				$(".more").attr("style","text-align:center;font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.05+"px;color:#373737;")
				getHeight(".enter-com",".content","loaded()");
			}
		},
		error:function(data){
			alert("获取项目失败");
		}
	});
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