<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <script type="text/javascript" src="<%=basePath%>js/ea/danche/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/danche/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/danche/ck.css">

    <script src="<%=basePath%>js/jquery.min.js"></script>
    <title>创客宝</title>
</head>
<body>
<header class="com_head">
    <a href="javascript: window.history.go(-1);return false;" class="back" target="_self"  id="ret" ></a>
    <h1>创客宝</h1>
</header>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var jinbishu="${jinbishu}";
    var yajin="${yajin}";
</script>
<div class="wrap_page">
    <div class="ckb_top">
        <span class="balance_tit">我的余额（金币）</span>
        <span class="balance">${jinbishu}</span>
        <span><a class="balance_detail" href="###">余额明细>></a></span>
    </div>
    <a class="recharge_btn" href="<%=basePath%>ea/jinbi/ea_getwfjchongzhi.jspa?user=${te.account}&sccid=${te.sccId}&khd=0&flag=&identifying=&ccompanyId=&staffid=${te.staffid}">立即充值</a>
    <a class="earnings" href="###">我的单车收益</a>
    <div class="deposit_box">
        <!--已交押金-->
        <!--<span>押金199元<a href="###" class="deposit_refund">押金退款</a></span>-->
        <!--未交押金-->

        <span>押金${yajin}元<a href="##" class="deposit_pay">去充押金</a></span>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function(){
        var ret = 'ret';
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if(isAndroid==true){
            var obj = document.getElementById("ret");
            obj.setAttribute("href","#");
            obj.setAttribute("onclick", "retAndroid()");
        }else if(isiOS==true){
            var obj = document.getElementById("ret");
            obj.setAttribute("href","#");
            obj.setAttribute("onclick", "retIOS()");
        }
    });
    //安卓，苹果返回
    function retAndroid(){
        try{
            Android.callAndroidjianli();
        }catch(err){
            $(".back").removeAttr("onclick");
            $(".back").attr("href",basePath+"ea/consignee/ea_toVipCenter.jspa");
        }
    }
    function retIOS(){
        try{
            if (ret == 'ret')
            {
                $(".back").removeAttr("onclick");
                $(".back").attr("href",basePath+"ea/consignee/ea_toVipCenter.jspa");
            }
            var url= "func=" + 'calliosOrder';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
        catch(err){
            $(".back").removeAttr("onclick");
            $(".back").attr("href",basePath+"ea/consignee/ea_toVipCenter.jspa");
        }
    }

</script>
</body>
</html>
