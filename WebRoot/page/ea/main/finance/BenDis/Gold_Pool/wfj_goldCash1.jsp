<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String backurl=request.getParameter("backurl");

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
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/wfj_goldCash.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>

  
    <script type="text/javascript">
	    var basePath="<%=basePath%>";
        var user="${user}";
        var sccid="${sccid}";
        var staffid="${staffid}";
        var khd="${khd}";
        var tocen=0;
        var flag = "${flag}";
        var mark = "${mark}";//标识
        var wfjJifenId="${jifen.wfjJifenId}";
        var jifenscore= Math.floor(${jifen.wfjJifenScore});
        var gold_no;//红包金币数
        var avagold;//可提现金币数
        var bankId= "${bankId}";
        var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
        var ccompanyId="${ccompanyId}";
        var state="${state}"; //金币池   用户身份是   个人  还是平台   1是个人   2 是平台
        var object = new Array();
        var type = "${type}" //提现方式
        var jum=${jum};
        var rdate="${rDate}";//当前用户上次提现时间
        var rtype="${rType}";//当前用户上次提现状态
        var SysSecond;
        var InterValObj;
        var bs=0;

	</script>
	
    <title>现金兑换</title>
       
</head>

<body>
<c:if test="${mark=='01' }">
    <script language="javascript">location.replace(basePath+"/ea/perinfor/ea_getBankCardInformation.jspa?khd=0&flag=&identifying=&ccompanyId=&staffId="+staffid+"&sccid="+sccid+"&user="+user+"&editType=00&mark=01")</script>
</c:if>
	<s:if test="khd==0">
	    <header class="com_head">
	        <a href="<%=basePath%>/ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid }&khd=${khd}&identifying=${identifying}&flag=${flag}&ccompanyId=${ccompanyId}" class="back"></a>
	        <h1>现金提现</h1>
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
        <%--<div class="g_num_wrap">--%>
            <%--<div class="g_num_box">--%>
                <%--<div class="g_num_tit">可用金币</div>--%>
                <%--<div class="g_num"><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }" groupingUsed="true"/></div>--%>
                <%--<div class="g_num_tit">可提现金币</div>--%>
                <%--<div class="g_num_no"><fmt:formatNumber value="${gold_no==null?0:jifen.wfjJifenScore-gold_no }" groupingUsed="true"/></div>--%>
            <%--</div>--%>
        <%--</div>--%>

            <div class="jifen">
                <ul>
                    <li>
                        总金额（分）：<span class="g_num"><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }" groupingUsed="true"/></span>
                    </li>
                    <li>
                        可提现金额（分）：<span class="g_num_no"></span>
                        <input type="hidden" value="${jifen.wfjJifenScore}" id="wfjjb"/>

                    </li>
                </ul>
            </div>

            <!-- 选择银行卡 -->
            <a href="###" class="select_bank clearfix card" id="clearfix">
                <img src="<%=basePath%>images/ea/finance/Gold_Pool/ny.png" class="sele_img" alt="">
                <div class="sele_t">
                    <input id="sele_id" type="hidden"/>
                    <div class="sele_name"></div>
                    <div class="sele_num"></div>
                    <%--<div id="branchName" style="display:none;"></div>--%>
                    <input type="hidden" id="branchName" value=""/>
                </div>
            </a>


        
        <div class="inp_box">
            <input type="text" class="gold_inp" placeholder="请输入兑换金额数（分）">
        </div>
        <a href="javascript:;" class="exchange" id="exchange">立即兑换<span></span></a>
        <%--<s:if test="#request.purview==1"><a href="javascript:;" class="exchange" id="exchange">立即兑换<span></span></a></s:if>--%>
        <div class="hint_tit">······温馨提示······</div>
        <p class="hint_con">
            限制一个小时可进行一次提现操作。<br />
            100金币可兑换1元，每次兑换需要收取${poundage}元手续费。<br />
            金币不够10000个，不能兑换金币，请尽快集齐金币再来兑现吧 。<br />
            微信提现:单笔单日限额5000元。<br />
            支付宝提现:个人支付宝账户单笔限额5万元；企业支付宝账户单笔限额10万元；未实名账户单日限额1000元。<br />
            银行卡提现:单笔单日限额2万元。<br />
        </p>
                     
    </div>
                     
    <div class="overlay">
        <div class="popup_pay">
            <div class="pay_hd clearfix">
                <span>请输入支付密码</span>
                <a href="javascript:;" class="close_btn"></a>
            </div>
            <div class="pay_bd">
                <div>
                    <span class="rmb_ico">￥</span><span class="sum_num"></span>
                </div>
                <div class="pay_text">兑换金额</div>
            </div>
            <div class="pay_fd">
                <ul class="pay_label clearfix">
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                </ul>
                <input type="tel" maxlength="6" class="pay_inp">
            </div>
        </div>
    </div>
        
     <!-- 弹窗 -->
     <div id="prompt" style="width: 100%;display: none;">
        <center>
	       	<div>
	       		<span style="position: relative;top: 19.8%;"></span>
	       	</div>
       	</center>
     </div>

<!--正在加载/正在发布 遮罩层 开始-->
<div class="overlay_text" style="background-color:rgba(0,0,0,0.3)">
    <span>正在兑现，请稍候……</span>
</div>
<!--正在加载/正在发布 遮罩层 结束-->
</body>
<script src="<%=basePath%>js/ea/finance/Gold_Pool/wfj_goldCash.js"></script>
</html>
