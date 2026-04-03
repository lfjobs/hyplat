<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="hy.ea.util.CookieUtil" %>
<%@ page import="org.apache.struts2.ServletActionContext" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/login/pc_login.css?version=20240223">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/login/l_zoom.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/localforage.min.js"></script>
    <script src="<%=basePath%>js/l_zoom.min.js"></script>
    <script src="<%=basePath%>js/l_drag.min.js"></script>
    <script src="<%=basePath%>js/comm.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/pc_login.js?version=20250528" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>webView/jweixin-1.5.0.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>webView/uni.webview.1.5.2.js" type="text/javascript" charset="utf-8"></script>

</head>
<body id="">
<form id="form" method="post">
    <div class="pc-box">
        <div class="div-box">
            <div class="div-img-logo">
                <img src="<%=basePath%>images/WFJClient/pc/login/login_bc.png"/>
            </div>
            <ul class="ul-con">
                <li>
                    <label for="">
                        <img src="<%=basePath%>images/WFJClient/pc/login/land_07.png"/>
                    </label>
                    <input type="text" id="count" value="${customer.account}" placeholder="请输入账号/手机号" name="customer.account" autocomplete="off"/>
                </li>
                <li>
                    <label for="">
                        <img src="<%=basePath%>images/WFJClient/pc/login/land_10.png"/>
                    </label>
                    <input type="password" id="pw_pwd" value="${customer.password}" placeholder="请输入密码" name="customer.password" autocomplete="off"/>
                </li>
            </ul>
            <section class="sec-land">
                <p class="p-button" onclick="login()">
                    登录
                </p>
                <p class="p-xy">
                    <img src="<%=basePath%>images/WFJClient/pc/login/icon_cb_yinsi_no.png" class="xy xy_no"/>
                    <img src="<%=basePath%>images/WFJClient/pc/login/icon_cb_yinsi_yes.png" class="xy xy_yes"
                         style="display:none;"/>
                    <span class="xy">我已同意</span><span><a
                        href="<%=basePath%>/page/WFJClient/privacyPolicy/pricweb.html">隐私政策和用户协议</a></span>
                </p>
                <div class="div-button clearfix">
                    <a href="<%=basePath%>/ea/wfjshop/ea_getjspzc.jspa?vs=${param.vs}"><p class="left">免费注册></p></a>
                    <a href="<%=basePath%>/ea/consignee/ea_forgetPassWord.jspa?sccid=&user=&ccompanyId="><p
                            class="right">忘记密码？</p></a>
                </div>
                <p class="p-mdl" onclick="mlogin()">
                    免 登 录
                </p>
            </section>
            <section class="sec-land2">
                <p class="p-header">第三方登录方式</p>
                <ul>
                    <li class="clearfix">
                        <p>
                            <img src="<%=basePath%>images/WFJClient/pc/login/land_14.png"/>
                        </p>
                        <p>QQ</p>
                    </li>
                    <li class="clearfix" href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxed2a1187d180aeb6
                    &redirect_uri=你的回调地址&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect">
                        <p>
                            <img src="<%=basePath%>images/WFJClient/pc/login/land_16.png"/>
                        </p>
                        <p>微信</p>
                    </li>
                    <%--<li class="clearfix">--%>
                    <%--<p>--%>
                    <%--<img src="<%=basePath%>images/WFJClient/pc/login/land_18.png"/>--%>
                    <%--</p>--%>
                    <%--<p>微博</p>--%>
                    <%--</li>--%>
                </ul>
            </section>
        </div>
    </div>
</form>
<!-- 弹窗 -->
<div id="prompt" style="width: 100%; position: absolute; top: 75%; z-index: 999999;display: none;">
    <center>
        <div>
            <span style="position: relative;"></span>
        </div>
    </center>
</div>

<div id="download-app" style="position: absolute; bottom: 0; left: 0; right: 0; width: 100%; display: none; background: white;">
    <p style="position: absolute; right: 16px;text-align: center; color: gray; font-size: 0.8rem" onclick="closeHelp()">
        关闭</p>
    <div style="padding-bottom: 24px">
        <p style="text-align: center; color: gray; font-size: 1.0rem">下载APP使用更方便</p>
        <div style="display: flex; justify-content: center">
            <%--<a href="http://www.impf2010.com:80/upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk">
                <img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/>
            </a>--%>
            <a href="https://sj.qq.com/search?q=%E6%95%B0%E5%AD%97%E5%9C%B0%E7%90%83">
                <img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/>
            </a>
            <a href="https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en">
                <img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_09.png"/>
            </a>
        </div>
    </div>
</div>

<script>
    function isApp() {
        let deviceId = "";
        try {
            if (isAndroid) {
                deviceId = Android.forAndroidDeviceId()
            }
            if (isiOS) {
                deviceId = "-"
            }
        } catch (e) {
            deviceId = ""
        }
        return deviceId !== ""
    }

    function closeHelp() {
        document.getElementById("download-app").style.display = "none"
    }

    document.getElementById("download-app").style.display = isApp() ? "none" : "block"
</script>

<script type="text/javascript">

    var basePath = "<%=basePath%>";
    var jumpType = "${jumpType}";//跳转页面类型  inspect:安全巡查页面
    var parameter = "${parameter}";
    //打开一个新窗口并设置其大小

</script>

<script type="text/javascript">
    var autoLoginUser = localStorage.getItem("autoLoginUser");

    // 注销后 跳转小程序登录
    document.addEventListener('UniAppJSBridgeReady', function () {
        const setLogOff=localStorage.getItem('setLogOff')
        if(setLogOff=="setLogOff"){
            uni.reLaunch({
                url:'/pages/login/login'
            })
        }
    })
    // 注销后 跳转小程序登录结束

    if (autoLoginUser != null && autoLoginUser.length > 0) {
        var [account, password] = autoLoginUser.split('|');
        // 自动登录用户
        console.log("填充账号密码")
        $("#count").val(account);
        $("#pw_pwd").val(password);
        $(".xy_no").hide();
        $(".xy_yes").show();
      //  login()
    }

    // 小程序自动登录
    document.addEventListener('UniAppJSBridgeReady', function() {
        let currentEnvironment="";
        uni.getEnv(function(res) {
            // console.log(JSON.stringify(res));
            currentEnvironment=Object.keys(res)[0]
        })
        console.log(currentEnvironment)
        if(currentEnvironment=="miniprogram"){
            login()
        }
    })


    var preUrl="<%=(String) session.getAttribute("preUrl")%>";


</script>

</body>
</html>
