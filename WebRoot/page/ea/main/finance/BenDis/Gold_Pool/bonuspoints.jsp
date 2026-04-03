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
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/bonuspoints.js"></script>
    <script src="<%=basePath%>js/title.js"></script>
    <title>积分数</title>
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var user="${account}";
		var sccid="${sccid}";
		var staffid="${staffid}";
	    var khd="${khd}";
    	var t;//计时器
		var pagenumber=0;
		var pagecount=0;
		var flag = "${flag}";		
		var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
		var ccompanyId="${ccompanyId}";	
		var object = new Array(); 
	</script>
    
    
</head>
<body>
	<s:if test="khd==0">
	    <header class="com_head">
	        <a href="<%=basePath%>/ea/jinbi/ea_gethyjifen.jspa?user=${account}&sccid=${sccid }&khd=${khd}" class="back"></a>
	        <h1>积分数</h1>
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
       <div class="hb_top jifen_top">
            <div class="hb_text">我的积分数</div>
            <div class="hb_balance jifen_num"><fmt:formatNumber value="${bp.bonusPointScore==null?0:bp.bonusPointScore }"></fmt:formatNumber></div>
            <a href="<%=basePath%>/ea/bonuspoints/ea_getWfjJifen.jspa?user=${account}&sccid=${sccid }&khd=${khd}" class="hb_pay_btn jifen_btn" id="goRecharge" >去充值</a>
           <div class="jifen_text">积分可通过购买产品获得，同时购买产品时，也可用积分支付！</div>
       </div>
       <div class="hb_warn">
           	目前只提供一年内的积分明细记录
       </div>
       <div class="hb_th clearfix">
           <span>积分获赠</span>
           <span>积分数量</span>
           <span>明细时间</span>
       </div>
       <div id="jifen"></div>
    </div>
</body>
</html>