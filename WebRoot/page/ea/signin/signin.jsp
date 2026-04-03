<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/dayjs/dayjs.min.js"></script>
    <script src="<%=basePath%>js/dayjs/locale/zh-cn.js"></script>
    <script>
        dayjs.locale('zh-cn')
    </script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/signin/signin.js"></script>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/signin/base.css"/>
    <link rel="stylesheet/less" type="text/css" href="<%=basePath%>css/ea/signin/signin.less"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/signin/signin.css"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyId = "${company.companyID}";
        var companyname = "${company.companyName}";
    </script>
    <title>签到</title>
    <style type="text/css">
        #prompt div {
            width: 70%;
            background: rgba(0, 0, 0, 0.5);
        }
    </style>
</head>
<body>

<header>
    <ul class="clearfix">
        <li onclick="window.history.back();">
            <img src="<%=basePath%>images/home/img-1.png">
        </li>
        <li>人脸识别考勤打卡</li>
    </ul>
</header>
<div class="signin-content">
    <div class="logo">
        <img src="<%=basePath%>images/home/img-001.png">
    </div>
    <button class="signface" id="signface">
        签到
    </button>
    <div class="company-info">
        <div>
            <img src="<%=basePath%>images/home/img-04.png"/>
            签到公司：${company.companyName}
        </div>
        <div>
            <img src="<%=basePath%>images/home/img-05.png"/>
            当前时间：<span class="date"></span>
        </div>
    </div>
    <div class="device-info">
        签到设备号：${posNum}
    </div>
</div>

<!-- 提示窗口 -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>

<dialog id="faceRecognitionDialog">
    <div class="dialog-wrapper">
        <div class="toolbar">
            <div class="title">
                <img src="<%=basePath%>/images/ea/main/logo_01.png"/>
                安全刷脸验证
            </div>
            <button class="close" onclick="closeFaceRecognitionDialog()">X</button>
        </div>
        <div class="content">
            <div id="tips">正在处理中...</div>
            <div class="detect-container">
                <video id="input-video" autoplay muted playsinline></video>

                <canvas id="detected-box-overlay"></canvas>

                <div class="stats">
                    <span id="time"></span>
                    <span id="fps"></span>
                </div>

                <%-- 处理进度条展示、 拍摄到的图片展示、安全盾牌图标展示--%>
                <div class="detected-face-preview-overlay">
                    <div class="content">
                        <img class="face-preview"/>
                        <div style="position: absolute; width: 100%; height: 100%; background: transparent; backdrop-filter: blur(10px);">
                        </div>
                        <img class="shell" src="<%=basePath%>/images/ea/main/shell.png"/>
                        <div class="loading-indicator">
                            <div class="circle"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <span style="position: absolute; bottom: 0; font-size: .3rem; color: #ececec; text-align: center" id="log"></span>
</dialog>

<dialog id="companiesPickerDialog">
    <div class="dialog-wrapper">
        <div class="toolbar">
            <div class="title">
                选择签到公司
            </div>
            <button class="close" onclick="closeCompaniesPickerDialog()">X</button>
        </div>
        <div class="content">
        </div>
    </div>
</dialog>
<dialog id="bindDialog">
    <div class="dialog-wrapper">
        <div class="toolbar">
            <div class="title">
                绑定账号
            </div>
            <button class="close" onclick="closeBindDialog()">X</button>
        </div>
        <ul>
            <li>
                <label for="mobile">微分金手机号</label>
                <input type="number" placeholder="请输入微分金手机号" id="mobile">
            </li>
            <li>
                <label for="captcha">验证码</label>
                <div class="captcha">
                    <input type="number" placeholder="请输入验证码" id="captcha">
                    <button type="button" id="sendCaptchaButton" onclick="sendCaptcha();">获取验证码</button>
                </div>
            </li>
            <li>
                <button type="button" onclick="submitBind();">确认绑定</button>
            </li>
        </ul>
    </div>
</dialog>
<dialog id="successDialog">
    <div class="dialog-wrapper">
        <div class="toolbar">
            <div class="title">签到成功</div>
            <button class="close" onclick="closeSuccessDialog()">X</button>
        </div>
        <div class="content">

        </div>
    </div>
</dialog>
<dialog id="failedDialog">
    <div class="dialog-wrapper">
        <div class="toolbar">
            <div class="title">签到失败</div>
            <button class="close" onclick="closeFailedDialog()">X</button>
        </div>
        <div class="content">
            <img class="big-icon" src="<%=basePath%>images/home/img-3.png">
            <p class="title">签到失败</p>
            <p class="subtitle"></p>
        </div>
    </div>
</dialog>

<dialog id="loadingDialog">
    <div class="dialog-wrapper">
        <div class="content">
            模型加载中，请稍候...
        </div>
    </div>
</dialog>

</body>
<script src="<%=basePath%>js/ea/signin/less.js" type="text/javascript" charset="utf-8">

</script>
</html>