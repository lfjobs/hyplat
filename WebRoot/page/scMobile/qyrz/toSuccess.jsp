<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/base.css"/>
    <link rel="stylesheet/less" type="text/css" href="<%=basePath%>css/scMobile/qyrz/khcg.less" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/khcg.css"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>
    <title>认领入驻成功</title>



</head>
<body>
<div class="weixin-tip">
    <p>
        <img src="<%=basePath%>images/contacts/loadsite/live_weixin.png" alt="微信打开"/>
    </p>
</div>
<%--<header>--%>
    <%--<ul class="clearfix">--%>
        <%--<li>--%>

        <%--</li>--%>
        <%--<li>--%>
            <%--认领入驻成功--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</header>--%>
<div class="content">
    <section class="sec-01">

        <h2 class="sucr">
            已成功认领并入驻
        </h2>
        <ul class="sucrz">
            <li>请进入5L5CERP->人事办</li>
            <li>->组织系统->企业认证</li>
            <li>功能处完成后续流程</li>
            <%--<li>电脑pc端：5L5C管理系统</li>
            <li>网址：www.impf2010.com</li>
            <li>组织机构：${(param.comBz eq null||param.comBz eq '')?comBz:param.comBz}</li>
            <li>账号：sa</li>
            <li>初始密码：123456</li>--%>
            <%--<li class="load">数字地球app初始密码：123456</li>--%>
            <%--<li class="load">数字地球app登录账号：17606547830</li>--%>

        </ul>
        <p class="rz" >
            为了给您提供更好的服务 请选择立即认证
        </p>
        <p class="load">
            为了给您提供更好的服务 请下载数字地球APP
        </p>
        <input type="button"  value="立即认证"  class="rz" onclick="rz()"/>

       <p class="sec-012 rz">
           <input type="button" value="暂不认证 立即体验"  onclick="ty()"/>
        </p>

        <div class="clearfix load">
            <a href="<%=basePath%>/upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk" onclick="loadwx()">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/img-02.png" >
                安卓下载
            </p>
            </a>
            <a href="https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/img-03.png" >
                苹果下载
            </p>
            </a>
        </div>

    </section>
</div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    $(function(){




        var jump = "${jump}";
        if(jump==""||jump==null){
            jump = "${param.jump}"
        }
         if(jump=="rz"){
             $(".rz").show();
             $(".load").hide();
         }else{
             $(".rz").hide();
             $(".load").show();
         }

        var ddid = "${param.ddid}";
        if(ddid!=""&&ddid!=null){
            $(".sucrz").hide();
            $(".rz").hide();
            $(".sucr").text("正在查询支付结果...");
            serverQuery(ddid);


        }

    });
    //服务商模式H5支付查询订单状态
    function serverQuery(ddid) {

        if (ddid == "") {
            return false;
        }
        $.ajax({
            url: basePath + "ea/wfjshop/sajax_ea_serverQuery.jspa",
            type: "POST",
            async: false,
            dataType: "json",
            data: {
                journalNum: ddid
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var trade_state = member.trade_state;

                if (trade_state == "SUCCESS") {

                    $(".sucr").text("已成功认领并入驻，以下是您的账户信息");
                    $(".sucrz").show();

                } else {

                    $(".sucr").text("您尚未成功支付");
                    $(".sucrz").hide();
                    $(".rz").hide();
                }

            },
            error: function (data) {
                $(".sucr").text("您尚未成功支付");
                $(".sucrz").hide();
                $(".rz").hide();
                console.log("查询订单失败");
            }
        });

    }
    //后退
    function toBack() {
        window.location.href = basePath + "/ea/qyrz/ea_toPeriphery.jspa";
    }
    function loadwx(){
        var winHeight = $(window).height();
        function is_weixin() {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        }
        var isWeixin = is_weixin();
        if(isWeixin){
            $(".weixin-tip").css("height",winHeight);
            $(".weixin-tip").show();
        }

    }
    //立即体验
    function ty() {
        window.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
    }

    //企业认证
    function rz() {
        window.location.href = basePath + "/ea/qrshare/ea_queryState.jspa?auditSkip=03";
    }




    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });

</script>
</html>
