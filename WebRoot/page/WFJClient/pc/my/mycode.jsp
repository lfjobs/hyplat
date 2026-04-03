<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link href="<%=basePath%>css/WFJClient/pc/my/mycode.css?version=20250406" rel="stylesheet"
          type="text/css"/>
    <!-- 引入 Material Icons 字体 -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>js/ea/human/office/scanPay/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.qrcode.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/utf.js"></script>
    <script src="<%=path%>/js/qrcode2.js"></script>
    <script src="<%=basePath%>js/comm.js" type="text/javascript" charset="utf-8"></script>

    <!-- 引入 Material Design Web Component（按钮 + 图标） -->

    <!-- 引入 Material Icons 图标库 -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>&lrm;</title>
</head>

<body class="bgcolorFFF">
<div class="wfj12_024">

    <!-- 中联园区头部 -->
    <div class="wfj_top">
        <ul style="display: flex; justify-content: space-between; align-items: center; padding: 10px;">
            <!-- 返回按钮 -->
            <li>
                <a onclick="javascript: window.history.go(-1); return false;" target="_self">
                    <img src="<%=basePath%>images/contacts/Network/wfj_return_01.png" alt="返回"/>
                </a>
            </li>
            <!-- 中间图片，绝对定位 -->
            <li style="position: absolute; left: 50%; transform: translateX(-50%);">
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_55.png" alt="">
            </li>
            <!-- 分享按钮靠右 -->
            <li style="margin-left: auto;" id="share_button">
                <button class="share-button" onclick="shareToAndroid()">
                    <span class="material-icons">share</span>
                    <span class="share-text">分享</span>
                </button>
            </li>
        </ul>
    </div>

    <!--中联园区头部 end-->
    <div class="wfj12_024_content">
        <div class="wfj12_024_hidden">

            <div class="wfj12_024_con">
                <div class="div-img div-img0"><img src="<%=basePath%>images/WFJClient/pc/my/rgzn.jpg"/></div>

                <p class="p-1">✪ 红包、积分、数据资产、引流锁客抢单财务平台!</p>
                <p class="p-2">
                    当今数字化时代、竞争力从宏观经济、变成微观经济。新的数字地球数字化入驻、即享受消费红包和积分,再次消费,获得红包或获得全返更大的红包，并且还有更多应用等着你。</p>
                <p class="p-2">
                    电子合同避免劳资风险、智能化数字工资计算、共享移动办公、源头智能化售货机等多智能应用终端并有超级5L5CERP、电子商务等应用,使用更简单!</p>
                <p class="p-2 p-0">请点击登录网址:<a href="<%=basePath%>page/WFJClient/pc/pc_login.jsp"><%=basePath%>
                    page/WFJClient/pc/pc_login.jsp</a></p>
                <div class="div-img"><img src="<%=basePath%>images/WFJClient/pc/my/hb.jpg"/></div>

                <div class="wfj12_024_width">
                    <table width="100%">
                        <td width="40%" rowspan="3" class="td-0"><img class="wfj12_024_icon"
                                                                      src="<%=basePath%>${userinfo[3]}"
                                                                      onerror='this.src="<%=basePath%>images/WFJClient/pc/my/pcimg_07.png"'/>
                        </td>
                        <td width="60%">${userinfo[1]}</td>
                        </tr>
                        <tr>
                            <td>${userinfo[2]}</td>
                        </tr>
                        <tr>
                            <td>${userinfo[6]}</td>
                        </tr>
                    </table>
                    <div class="qr_img" id="qrcode">
                        <%-- <img id="QR" src="images/QR_img.jpg" alt="">--%>
                    </div>
                    <%--<div class="wfj12_024_ewm"><img src="<%=basePath%>images/contacts/Network/ewm.jpg" /></div>--%>
                    <div class="wfj12_024_bottom">
                        <ul>
                            <li>扫一扫或长按识别加入数字地球</li>

                        </ul>
                    </div>
                </div>

                <div class="div-img1"><img src="<%=basePath%>images/WFJClient/pc/my/stsy.jpg"/></div>


            </div>
        </div>
    </div>


</div>

<script>
    window.addEventListener("DOMContentLoaded", function() {
        if (typeof Android==='undefined') {
            document.getElementById("share_button").style.display = "none";
        }
    })
    function shareToAndroid() {
        if (typeof Android!=='undefined')
        {

            Android.showShare("欢迎加入数字地球", "数字地球", "<%= request.getRequestURL() + (request.getQueryString() == null ? "" : "?" + request.getQueryString()) %>",
                "http://www.impf2010.com/images/WFJClient/logo2x.png", "1");
        }
    }
</script>
<style>
    .share-button {
        display: flex;
        align-items: center;
        background-color: #ff0d5d;
        color: white;
        border: none;
        border-radius: 20px;
        padding: 6px 12px;
        cursor: pointer;
        font-size: 14px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        transition: background-color 0.2s, transform 0.2s;
    }

    .share-button:hover {
        background-color: #ff0d5d;
        transform: scale(1.05);
    }

    .share-button .material-icons {
        font-size: 20px;
        margin-right: 4px;
    }

    .share-button .share-text {
        font-weight: 500;
    }
</style>
<script type="text/javascript">
    var headimg = "${userinfo[3]}";
    var basePath = "<%=basePath%>";
    var sccid = "${userinfo[5]}";
    $(function () {

        jQuery("#qrcode").qrcode({
            render: "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
            text: basePath + "ea/wfjshop/ea_getjspzc.jspa?sccid=" + sccid,
            width: "200",               //二维码的宽度
            height: "200",              //二维码的高度
            background: "#ffffff",       //二维码的后景色
            foreground: "#000000",        //二维码的前景色
            src: basePath + headimg    //二维码中间的图片
        })

        setTimeout(function () {
            var canvas = $('#qrcode canvas')[0]; // 获取 canvas 元素
            var imgSrc = canvas.toDataURL("image/png"); // 将 canvas 转换为 data URL
            $('#qrcode').empty(); // 清空原有的 canvas 元素
            $('<img>').attr('src', imgSrc).appendTo('#qrcode');
        }, 1000);

    })

</script>
</body>
</html>

