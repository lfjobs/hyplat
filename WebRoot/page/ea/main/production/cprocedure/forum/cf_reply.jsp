<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title></title>
    <script type="text/javascript" src="<%=basePath%>/js/BuildPlatform/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/BuildPlatform/swiper-3.3.1.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/production/forum/new_style.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/BuildPlatform/swiper-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/production/cprocedure/forum/new-page.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/production/cprocedure/forum/cf_reply.js"></script>
</head>
<body>

<header>
   <ul>
       <li style="width: 10%;">
           <a href="javascript:fanhui()"><img src="<%=basePath%>/images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 60%;padding-left: 10%;">第${obj[3] }楼</li>
       <li style="width: 20%;font-size: 0.6rem;">
           <a href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${obj[5] }&ccompanyId=${concom.ccompanyID }&type=web&miniSystemJudge=04">查看原帖</a>
       </li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
	<div id="prompt" style="width: 100%; display: none;z-index: 1001">
		<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
		</center>
	</div>
    <div class="content">

        <div class="con">
            <div class="huifu">
                <div class="huifu_top">
                    <img src="<%=basePath%>${obj[7]==null?'/images/WFJClient/PersonalJoining/headimage.png':obj[7]}" class="hf_head">
                    <h4>${obj[6] }</h4>
                </div>
                <div class="txt">
                    <p>${obj[2] }</p>
                    <h5>
                    	<span>第${obj[3] }楼</span>
                    	<span id="time">${obj[4] }</span>
                    	<input type="hidden" value="${obj[0] }" class="pcid" >
                    	<c:if test="${obj[1] eq cuscom.staffid }">
                    		<input type="button" value="删除" class="remove" onclick="del1(this)">
                    	</c:if>
                    </h5>
                </div>
                <ul class="huifu_">
                    <%-- js拼接 --%>
                </ul>
                <p id="more">暂无更多</p>
            </div>

        </div>
        <!--<div class="alert"></div>-->
        <div class="alert_search">
            <div class="top">
                <input type="search" name="" placeholder="搜索" onfocus="this.placeholder=''" onblur="this.placeholder='搜索'" value="" class="sousuo">
                <input type="submit" value="搜索" id="ss">
                <input type="submit" value="取消" id="qx">
            </div>
        </div>
        <div class="hf_ipt">
            <div class="ipt">
                <input type="text" class="txt" placeholder="我也说一句...">
                <input type="button" value="发表" class="btn" onclick="publish(this)">
                <input type="hidden" value="${obj[0] }" class="pcid" >
            </div>
        </div>
    </div>

</div>
<div class="alert_rem">
    <div id="remove_rem">
        <img src="<%=basePath%>/images/ea/production/forum/tx.png" alt="">
        <h4>确认删除该评论</h4>
        <div>
            <p id="quxiao">取消</p>
            <p id="queding">确认</p>
        </div>
    </div>
</div>
<script>
	var basePath = '<%=basePath%>';
	var pageNumber = 0;
	var pageCount;
	var ccompanyid = '${concom.ccompanyID}';
	var staffid = '${cuscom.staffid}';
</script>
<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        $(".hf_ipt").css("height",$(window).height()*0.08-1+"px");

        /*$(".con").css("height",$(window).height()*0.828+"px");*/
        $(".con").css("height",$(window).height()*0.84+"px");

        /*删除*/
        $(document).on("click",".huifu .txt .remove",function(){
            $(".alert_rem").show();
        });
        $(document).on("click",".huifu .huifu_ li .remove",function(){
            $(".alert_rem").show();

        });
        $(document).on("click","#remove_rem div #quxiao",function(){
            $(".alert_rem").hide();
        });

		//2017.2.20 手机端输入法遮盖输入框
		$(".hf_ipt .ipt .txt").focus(function(){
			$(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*1.3+"px");
		})
		$(".hf_ipt .ipt .txt").blur(function(){
            $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        });
    });
    function fanhui(){
    	document.location.href = basePath+"ea/companyforum/ea_forumMessage.jspa?concom.ccompanyID="+ccompanyid+"&commonEssence=02";
    }
</script>



</body>
</html>