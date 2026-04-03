<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/Gold_Pool/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/gold_pool.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/wfj_redPacket.js"></script>
    
    <title>金币红包</title>
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var user="${user}";
    	var t;//计时器
		var pagenumber=0;
		var pagecount=0;
		var sccid="${sccid}";
		var staffid = "${staffid}";			
		var flag = "${flag}";
		var khd="${khd}";
	 	var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
		var ccompanyId="${ccompanyId}";		
	</script>
	
</head>
<body>

	<s:if test="khd==0">
	    <header class="com_head">
	        <a href="javascript:history.go(-1);" class="back"></a>
	        <h1>金币红包</h1>
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
    
    <div class="wrap_page">
       <div class="hb_top">
           <img src="<%=basePath%>images/ea/finance/Gold_Pool/hb_img.png" class="hb_img" alt="">
            <div class="hb_text">我的余额</div>
            <div class="hb_balance"><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }"></fmt:formatNumber></div>
           <a href="<%=basePath%>/ea/jinbi/ea_getwfjchongzhi.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&staffid=${staffid}&identifying=${identifying}&ccompanyId=${ccompanyId}&mark=03" class="hb_pay_btn">充值</a>
       </div>
       <div class="hb_warn">
           	目前只提供一年内的红包金币明细记录
       </div>
       <div class="hb_th clearfix">
           <span>红包获赠</span>
           <span>红包金额</span>
           <span>明细时间</span>
       </div>
       <div id="detail"></div>
    </div>
    
</body>
</html>