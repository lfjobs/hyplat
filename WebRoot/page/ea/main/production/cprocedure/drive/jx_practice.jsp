<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>预约教练确认订单</title>
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/drivestyle12.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/drive.css"/>

<script type="text/javascript">


var basePath="<%=basePath%>";	

</script>
</head>

<body>
<div class="wfj12_002">
    <div class="wfj12_002_top">
        <ul id="tops">
            <li><a href="在线学习.html" target="_self"><img src="<%=basePath%>images/ea/production/drive/wfj_return_01.png" /></a></li>
            <li>随机学习
            </li>
            <!--<li><a href="javascript:;"><img src="<%=basePath%>images/ea/production/drive/top_nav_05.png" /></a></li>-->
        </ul>
    </div>
    <div class="sunxu_main">
        <p class="sunxu_main_header">驾驶这种机动车上路行驶属于什么行为？[单选]</p>
        <div class="shunxu_img"><img src="<%=basePath%>images/ea/production/drive/xuexi_03.jpg" alt=""/></div>
        <ul class="sunxu_main_daan">
            <li><span>A.</span><span>违规行为</span></li>
            <li><span>B.</span><span>违规行为</span></li>
            <li><span>C.</span><span>违规行为</span></li>
            <li><span>D.</span><span>违规行为</span></li>
        </ul>
    </div>
</div>

<script>
    $(function(){

        $("body").css("height",$(window).height());
        $("body").css("height",$(window).height());

        /*顶部*/
        $("#tops").find("li").attr("style","float:left;");
        $("#tops").find("li").eq(0).attr("style","width:15%;");
        $("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
        $("#tops").find("li").eq(1).attr("style","width:70%; text-align:center;position:relative; font-size:"+$(window).height()*0.025+"px;color:#FFF;");
        $("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
        $("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");

        $(".wfj12_002_top").css("height",$(window).height()*0.08+"px");
        $(".wfj12_002_top").css("lineHeight",$(window).height()*0.08+"px");
        $(".wfj12_002_top_img").attr("style","margin-top:"+$(window).height()*0.01+"px;");
        $("#tops").find("li").eq(1).find("p").attr("style","float:right;line-height:"+$(".wfj12_002_top").height()+"px")

        $("#tops").find("li").eq(1).find("img").attr("style","vertical-align:middle;height:"+$(window).height()*0.03+"px")
        /*顶部 end*/

    })
</script>
<script>
    $(function(){
        //$(".gonggao").css({"height":$(window).height()*0.06+"px"})
//        $("#FontScroll").css({"height":$(".gonggao_tu").height()+"px","lineheight":$(".gonggao_tu").height()+"px"})
//        $("#FontScroll ul li").css({"height":$(".gonggao").height()+"px","lineheight":$(".gonggao").height()+"px"})
        $(".gonggao_tu p").css({"line-height":$(".gonggao").height()+"px"})
        $(".gonggao_wen ul li").css({"height":$(".gonggao_tu").height()+"px"})
//        alert($(".gonggao_wen ul li").height())
//            function tim(){
//                    $(".gonggao_wen ul").css({"top":-$(".gonggao_wen ul li").height()*2+"px"})
////                alert("asdf")
//
//            }
//            setInterval(tim,2000)
//            if()

    })
</script>
</body>
</html>