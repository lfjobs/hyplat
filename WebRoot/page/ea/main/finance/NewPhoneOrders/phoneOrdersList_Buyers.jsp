<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>我的订单-买家</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/>
	<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/WFJClient/ap.js"></script>

	<script type="text/javascript" src="<%=basePath %>js/ea/finance/NewPhoneOrders/phoneOrders.js"></script>

</head>
<script type="text/javascript">
    var basePath='<%=basePath%>';
    var t;
    var pagenumber=0;
    var pagecount=0;
    var pl="${pl}";
    var staid="${staid}";
    var sccId="${sccId}";
    var etype = "${etype}";
    var cbid='';
    var zffs="1";
    var flag='order';
    var cashierID="";
    var jum="";
    var morre="";
    var goodsName="";
    var status = "";
    var status4 = "";
    var notocen=1;
    var token=0;
	var companyName="";

    var search="";
    var zhifuMark="${param.zhifuMark}";
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';

    $(function () {
        if(zhifuMark=="00"){
            $(".backli").html("&nbsp;");

        }


    })

    function back(){

        if(etype=="elu"){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if(isAndroid==true){
                Android.finishWeb();
            }else if(isiOS==true){
                var url= "func=" + 'ioscallback';
                window.webkit.messageHandlers.Native.postMessage(url);

            }

        }else {
            history.back();
          //  document.location.href = basePath+"ea/vipcenter/ea_orderManage.jspa?sccid="+sccId+"&ret=ret";
        }
    }


</script>

<script type="text/javascript">
    $(function(){
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if (!isWeixin) { //不是微信
            if(ua.indexOf("browser")!=-1){
                $(".wechat").hide();
                $(".alert_download").show();

            }else{
                $(".wechat").show();
                $(".alert_download").hide();

            }

        }else{
            $(".alert_download").show();
		}


    });

</script>

<body>
<div class="loading" style="display:none;">
	<img src="<%=basePath%>images/WFJClient/Newjspim/loading.gif"/>
	<p><span>加载中...</span></p>
</div>
<div class="header">
	<ul>
		<li style="width: 10%;" class="backli"><a href="javascript:back()" target="_self"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
		<li style="width: 80%;text-align: center;">我的订单</li>
		<li style="width: 10%"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/search.png" alt=""></li>
	</ul>
</div>

<div class="header">
	<ul class="rec_head order_head">
		<li class="active all_order" id="wfj_dd_">全部订单</li>
		<li class="obligation" id="wfj_dd_01">待付款</li>
		<li class="overhang" id="wfj_dd_00">待发货</li>
		<li class="pra" id="wfj_dd_02">待收货</li>
	</ul>
</div>
<div class="content_hidden">
	<div class="content rec_content">
		<div class="rec_con">
			<!--全部订单-->
			<div class="rec_eva" id="all_order">
			</div>
			<!--待付款-->
			<div class="rec_eva" id="obligation">
			</div>
			<!--待发货-->
			<div class="rec_eva" id="overhang">
			</div>
			<!--待收货-->
			<div class="rec_eva" id="pra">
			</div>
		</div>
	</div>
	<div class="wfj12_014_hidden_buy pays" style="display:none;">
		<table id="pays" width="100%">
			<tr>
				<td width="60%" style="padding-left:4%;"><span class="xzf">需支付：</span>
				</td>
				<td align="right" style="padding-right:4%;" width="50%"><span
						id="money" style="color:#F74C31;"></span>
				</td>
			</tr>
		</table>
		<div class="wfj12_014_buy_width">
			<table>
				<tr>
					<td colspan="2">选择支付方式</td>
				</tr>
				<tr class="wfj12_014_choice">
					<td class="wfj12_014_pay"><img
							src="<%=basePath %>images/ea/finance/BenDis/all_pay_01.png" />
					</td>
					<td class="second" align="right"><img width="24" name="1"
														  height="24"
														  src="<%=basePath %>images/ea/finance/BenDis/choice_01.png" />
					</td>
				</tr>
				<%--<tr class="wfj12_014_choice">--%>
					<%--<td class="wfj12_014_pay"><img--%>
							<%--src="<%=basePath %>images/ea/finance/BenDis/all_pay_02.png" />--%>
					<%--</td>--%>
					<%--<td class="second" align="right"><img width="24" name="2"--%>
														  <%--height="24"--%>
														  <%--src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />--%>
					<%--</td>--%>
				<%--</tr>--%>
<tr class="wfj12_014_choice wechat" >
    <td class="wfj12_014_pay"><img
            src="<%=basePath %>images/ea/finance/BenDis/all_pay_03.png" />
    </td>
    <td class="second" align="right"><img width="24" name="3"
                                          height="24"
                                          src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
    </td>
</tr>
<tr class="wfj12_014_choice gd">
    <td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png" />
    </td>
    <td class="second" align="right" ><img width="24" name="4"
                                           height="24"
                                           src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
    </td>
</tr>
<tr class="wfj12_014_choice gd">
    <td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/moneybox.png" />
    </td>
    <td class="second" align="right" ><img width="24" name="5"
                                           height="24"
                                           src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
    </td>
</tr>
<tr class="wfj12_014_choice integral_">
    <td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/jifen.png" />
        <span style="font-size: 12px"></span>
    </td>
    <td class="second" align="right" ><img width="24" name="7"
                                           height="24"
                                           src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
    </td>
</tr>
<tr class="wfj12_014_choice gold">
    <td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/gold.png" />
        <span style="font-size: 12px"></span>
    </td>
    <td class="second" align="right" ><img width="24" name="6"
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
</div>
<form name="formsutm" id="formsutm" method="post">
<s:hidden name="ddid" id="ddid"></s:hidden>
<s:hidden name="baseUrl" id="baseUrl"></s:hidden>
<s:hidden name="morre" id="morre"></s:hidden>
<input type="submit" style="display: none" name="submit" id="submit"/>
</form>
<!--搜索窗口 -->
<form name="addForm" id="addForm" method="post">
<div id="alert" style="width: 100%;height: 100%;position: absolute;top: 0;background: rgba(0, 0, 0, 0.3);display:none;z-index: 99;"></div>

<div class="wfj12_014_hidden_buy" style="display:none;" id="jqModeladd">
<table id="SearchTable" width="100%">
<tr>
<td width="50%" style="padding-left:6%;"><span><font color="#660000" size="5%">学员信息</font></span></td>
<td width="50%" style="padding-right:6%;" align="right" id="back"><span><a href="javascript:"><img  style="height: 40%" src="<%=basePath %>images/ea/finance/BenDis/x.png"></a></span></td>
</tr>
</table>

<div class="wfj12_014_buy_width wfj12_014_buy_width2">
<table>
<tr  class="wfj12_014_choice">
    <td class="wfj12_014_pay" align="right" >订单编号：</td>
    <td>
        <input type="hidden" id="joinput" style="width: 195px" name="jo" />
        <span id="nu"></span>
    </td>
</tr>
<tr class="wfj12_014_choice">
    <td class="wfj12_014_pay" align="right">
        学员姓名：
    </td>
    <td>
        <input id="noviceName" class="novice" style="width: 195px" name="noviceName" />
    </td>
</tr>
<tr class="wfj12_014_choice">
    <td class="wfj12_014_pay" align="right">
        学员电话：
    </td>
    <td>
        <input id="novicePhone" class="novice" style="width: 195px" name="novicePhone" />
    </td>
</tr>
<tr class="wfj12_014_choice">
    <td class="wfj12_014_pay" align="right">
        学员身份证号：
    </td>
    <td>
        <input id="noviceCode" class="novice" style="width: 195px" name="noviceCode" />
    </td>
</tr>
<tr class="wfj12_014_choice">
    <td class="wfj12_014_pay" align="right">
        学员地址：
    </td>
    <td>
        <input id="noviceAddress" class="novice" style="width: 195px" name="noviceAddress" />
    </td>
</tr>
</table>
</div>
<div class="wfj12_014_bottom_commit wfj12_014_bottom_commit2">
<div onclick="tosave()">保存
<input type="hidden" id="prices"/>
</div>
</div>
</div>
</form>

<div class="alert_" id="shanchu">
<div class="alert">
<h3>确认删除订单吗？</h3>
<div>
<p>取消</p>
<p id="isDel">确认</p>
</div>
</div>
</div>
<div class="wfj12_010_jqm">
<div class="wfj12_010_btnum">
<ul>
<li id="jian">-</li>
<li><input type="text" value="1" id="zhi" />
</li>
<li id="jia">+</li>
</ul>
</div>
<div class="wfj12_010_bttit">
<ul>
<li><span>最多延长三天</span>
</li>
</ul>
<ul>
<li id="estimatedTime"></li>
</ul>
</div>
<div class="wfj12_010_btcommit">
<ul>
<li id="jqmOverlay">取消</li>
<li id="qr">确认</li>
</ul>
</div>
</div>
<div class="alert_" id="quxiao">
<div class="alert">
<h3>确认取消订单吗？</h3>
<div>
<p>取消</p>
<p>确认</p>
</div>
</div>
</div>
<div class="alert_2">
<div class="alert2">
<p>复制成功！</p>
</div>
</div>
<div class="alert123">
</div>
<div class="alert_search">
<div class="top">
<input type="search" name="" placeholder="订单号或产品名称" onfocus="this.placeholder=''" onblur="this.placeholder='订单号或产品名称'" value="" class="sousuo">
<input type="submit" value="搜索" id="ss">
<input type="submit" value="取消" id="qx">
</div>
</div>
<!--下载弹框-->
<div class="alert_download" style="display: none;">
<img src="<%=basePath%>images/WFJClient/Newjspim//ico.png" class="ico">
<div class="txt">
<h4>数字地球APP</h4>
<p>共享资源共享金 成功从这里开始</p>
</div>
<input type="button" value="点击下载" class="downapp">
<img src="<%=basePath%>images/WFJClient/Newjspim//ico-x.png" class="x">
</div>

<%@ include file="/page/WFJClient/ShopOwner/payCodeCommon.jsp"%>

<script>
$(function () {
$(".x").click(function () {
$(".alert_download").hide();
})

$(".downapp").click(function () {
document.location.href = basePath + "ea/wfjshop/ea_getjspzc.jspa?sccid=";
})


})
</script>
<!--下载弹框 end-->
<script>
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

