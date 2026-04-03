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
<title>金币兑现-切换平台</title>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
	<script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
	<script src="<%=basePath%>js/ea/finance/Gold_Pool/goldChangePlatform.js"></script>
	
</head>

<body>

	<s:if test="khd==0">
	    <!-- header start  -->
	    <header class="mem_header">
	        <div class="back"></div>
	        <h1>切换平台</h1>
	    </header>    
    </s:if>
    <s:else>
    	<style type='text/css'>
			.wrap_page{
				margin-top:0;
				padding-top:0;
			}
		</style>
    </s:else>
    
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <section class="plam_list">
        	<c:forEach items="${list }" var="list" varStatus="st">
        		<div class="plam_box">
	        		<input id="sccid" value="${list[0] }" style="display:none" />
	                <div class="m_list_L sign_blue"></div>
	                <div class="m_list_M flex_1">
						<span>${list[5]}</span>		                
						所在平台：
						<c:if test="${list[3]==null }">
	                    	<small>软件网络计算机联营平台</small>
	                    </c:if>
	                    <c:if test="${list[3]!='' }">
	                    	<small>${list[3] }</small>
	                    </c:if>
	                </div>
	                <c:if test="${list[0] ==cuscom.sccId}">
	                	<div class="m_list_R plam_cur">&nbsp;</div>
	                </c:if>               
            	</div>
        	</c:forEach>
        </section>
    </div>
    <!--  页面内容 end -->
    <script>
    	var basePath='<%=basePath%>';
    	var sccid="${sccid }";
    	var staffid="${staffid }";
    	var type="${type }";
    	var flag = "${flag}";
    	var khd="${khd}";
    	var user = "${user}";
    	var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
    	var miniSystemJudge='${param.miniSystemJudge}';
    	var ccompanyId="${ccompanyId}";
    	var object = new Array();
    	
    	 window.onload = window.onresize = function() {
             //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
             //获取窗口的尺寸
             var clientWidth = document.documentElement.clientWidth;
             //通过屏幕宽度去设置不同的后台根字体的大小
             document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
         }
    </script>	   
</body>
</html>