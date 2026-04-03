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
	<link rel="stylesheet" type="text/css"href="<%=basePath %>css/contacts/style12.css" />
	<script src="<%=basePath%>js/ea/finance/Gold_Pool/wfj_goldCoin.js"></script>
	
	<script type="text/javascript">
		var basePath="<%=basePath%>";		
    	var user="${user}";
    	var sccid="${sccid}";
    	var jum="";
		var morre="";
		var zffs="1";
		var money=0;
		var staffid = "${staffid}";				
		var flag = "${flag}";
		var khd="${khd}";
	 	var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
		var ccompanyId="${ccompanyId}";
		var mark = "${mark}";//标识    
		var coin = "coin";
		var isflag = "gold"
		var object = new Array();
	</script>
<script type="text/javascript">
$(function(){
	var ua = navigator.userAgent.toLowerCase();
	var isWeixin = ua.indexOf('micromessenger') != -1;
	if (!isWeixin) {
	   if(ua.indexOf("browser")!=-1){
		$(".wechat").hide();
	    }else{
		   $(".wechat").show();
	   }
	}
	
	
});

</script>
<style type="text/css">
	#prompt div{
		width: 70%;
		background: rgba(0,0,0, 0.5);
	}
</style>

<title>金币充值</title>
</head>

<body style="background:#fff;">
<div class="loading" style="display:none;">
	<img src="<%=basePath%>images/WFJClient/Newjspim/loading.gif"/>
	<p><span>加载中...</span></p>
</div>
	<div id="alert"></div>	
	
	
	
	<s:if test="khd==0">
	    <header class="com_head">
	    	<!--<c:choose>
	    		<c:when test="${mark == '03'}">
	    		    <a href="javascript:history.go(-1);" class="back"></a>
	    		</c:when>
	    		<c:otherwise>
	    		    <a href="<%=basePath%>/ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid }&khd=${khd}&identifying=${identifying}&flag=${flag}&ccompanyId=${ccompanyId}" class="back"></a>
	    		</c:otherwise>
	    	</c:choose>-->
            <a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
			<h1>金币充值</h1>
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
        <div class="g_num_wrap">
            <div class="g_num_box">
                <div class="g_num_tit">现有金币</div>
                <div class="g_num"><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }" groupingUsed="true"/></div>
            </div>
        </div>
        <div class="inp_box">
            <input type="text" class="gold_inp" placeholder="请输入充值金币数">
        </div>
        <a href="javascript:;" class="exchange">确认充值<span></span></a>
        <div class="hint_tit">······温馨提示······</div>
        <p class="hint_con">
            	一元可兑换100金币，金币可用于交易使用。
        </p>
    </div>
    
     <!-- 弹窗 -->
     <div id="prompt" style="width: 100%;display: none;">
        <center>
	       	<div>
	       		<span style="position: relative;top: 19.8%;"></span>
	       	</div>
       	</center>
     </div>
    
    <!-- 支付方式 -->
    <div class="wfj12_014_hidden_buy" style="display:none;">
			<table id="pays" width="100%">
				<tr>
					<td width="50%" style="padding-left:4%;"><span>需支付：</span>
					</td>
					<td align="right" style="padding-right:4%;" width="50%"><span
						id="money2" style="color:#F74C31;"></span>
					</td>
				</tr>
			</table>
			<div class="wfj12_014_buy_width">
				<table>
					<tr>
						<td colspan="2">选择支付方式</td>
					</tr>
					<tr>
						<td class="wfj12_014_pay"><img
							src="<%=basePath %>images/ea/finance/BenDis/all_pay_01.png" />
						</td>
						<td align="right" class="wfj12_014_choice"><img width="24" name="1"
							height="24"
							src="<%=basePath %>images/ea/finance/BenDis/choice_01.png" />
						</td>
					</tr>
					 <%--<tr>--%>
						<%--<td class="wfj12_014_pay"><img--%>
							<%--src="<%=basePath %>images/ea/finance/BenDis/all_pay_02.png" />--%>
						<%--</td>--%>
						<%--<td align="right" class="wfj12_014_choice"><img width="24" name="2"--%>
							<%--height="24"--%>
							<%--src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />--%>
						<%--</td>--%>
					<%--</tr>--%>
<tr class="wechat">
    <td class="wfj12_014_pay"><img
        src="<%=basePath %>images/ea/finance/BenDis/all_pay_03.png" />
    </td>
    <td align="right" class="wfj12_014_choice"><img width="24" name="3"
        height="24"
        src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
    </td>
</tr>
</table>
</div>

<div class="wfj12_014_bottom_commit">
<div id="paycommit" onclick="zf()">确认支付</div>
</div>
</div>

<form name="formsutm" id="formsutm" method="post">
<s:hidden name="journalNum" id="journalNum"></s:hidden>
<s:hidden name="baseUrl" id="baseUrl"></s:hidden>
<s:hidden name="morre" id="morre"></s:hidden>
<s:hidden name="staffid" id="staffid"></s:hidden>
<s:hidden name="sccid" id="sccid"></s:hidden>
<s:hidden name="isflag" id="isflag"></s:hidden>
<input type="submit" style="display: none" name="submit" id="submit" />
</form>

</body>
</html>
