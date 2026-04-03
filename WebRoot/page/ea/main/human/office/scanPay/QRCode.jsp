<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/11 0011
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/human/office/scanPay/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/base.css"><%--
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/mem_center.css">--%>
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/QR_gathering.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/human/office/scanPay/jquery.min.js"></script>
    <title>二维码收款</title>
    <script type="text/javascript" src="<%=basePath%>js/jquery.qrcode.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/utf.js"></script>
    <script src="<%=path%>/js/qrcode2.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var h_img = new Image();

        window.onload = function () {
            var qz = $("#yj").val();

            $("#yj").val(qz * 10000);
            var qrcode;
            var content;
            var size;
            var yj;
            var falg =${param.falg};
            var companyId = $("#companyId").val();
            //var companyId=${param.companyId};

            if (companyId == "" || companyId == null) {
                $("#noqrcode").show();
                $("#qrcode").hide();
                $("#yincang").hide();
                //alert("请升级成公司用户！");
                $(".overlay").addClass("active").find(".popup_box").show();
                return;
            }
            /*if (qz != ""&&qz!=null) {

            }else {
                $("#noqrcode").show();
                $("#qrcode").hide();
            }*/

            $("#noqrcode").hide();
            dizhi();
            content = $("#content").val();
            if (content != "") {

                jQuery("#qrcode").qrcode({
                    render: "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
                    text: $("#content").val(),
                    width: "200",               //二维码的宽度
                    height: "200",              //二维码的高度
                    background: "#ffffff",       //二维码的后景色
                    foreground: "#000000",        //二维码的前景色
                    src: $("#logo").attr("src")    //二维码中间的图片
                });

                setTimeout(function () {
                    var canvas = $('#qrcode canvas')[0]; // 获取 canvas 元素
                    var imgSrc = canvas.toDataURL("image/png"); // 将 canvas 转换为 data URL
                    $('#qrcode').empty(); // 清空原有的 canvas 元素
                    $('<img>').attr('src', imgSrc).appendTo('#qrcode');
                }, 1000);
        }else{
            $("#noqrcode").show();
            $("#qrcode").hide();
        }
        if (falg == 2) {
            //$(".back").hide();
            $(".nest_back").hide();
        }
        // 设置点击事件
        document.getElementById("send").onclick = function () {
            // 获取内容
            content = document.getElementById("content").value;
            content = content.replace(/(^\s*)|(\s*$)/g, "");
            yj = document.getElementById("yj").value;
//                var a=document.getElementById("qrcode");
//                a.removeChild(a.children[0]);

            // 检查佣金/^([1-9]\d*|[0]{1,1})$/
//                if (!/^([1-9]\d*|[0]{1,1})$/.test(yj)) {
//                    alert('请输入正整数');
//                    return false;
//                }

            // 使用正则表达式移除超过两位小数的部分
            var regex = /^\d+(\.\d{0,2})?$/;
            if (!regex.test(yj)) {
                // 如果不符合正则表达式，则只保留前两位小数
                yj = yj.replace(/(\.\d\d).*$/, '$1');
                $("#yj").val(yj);
            }
            if (yj < 0 || yj >= 80) {
                alert('佣金的范围在0～80');
                return false;
            }
            if (yj != "") {
                yongjin();
            }
//                // 清除上一次的二维码
//                if (qrcode) {
//                    qrcode.clear();
//                }
//                jQuery("#qrcode").qrcode({
//                    render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
//                    text : $("#content").val(),
//                    width : "200",               //二维码的宽度
//                    height : "200",              //二维码的高度
//                    background : "#ffffff",       //二维码的后景色
//                    foreground : "#000000",        //二维码的前景色
//                    src:$("#logo").attr("src")    //二维码中间的图片
//
//                })


        }

        var QRinp = document.getElementById("yj");

        QRinp.addEventListener("input", function () {
            var str = this.value.trim();
            if (str.indexOf(".") > -1) {
                if (str.length == 1) {
                    this.value = "0" + ".";
                } else if (str.length > 1) {
                    var inp_arr = str.split(".");
                    var part_arr = inp_arr[1].split('');
                    if (part_arr.length > 2) {
                        var b = part_arr.join('').substring(0, 2);
                        inp_arr[1] = b;
                        this.value = inp_arr.join('.');
                    }
                }

            }
            if (str.indexOf("-") > -1) {
                this.value = 0;

            }
            var t = Number(this.value.trim());

        })

        function yongjin() {

            var data = $("#form1").serialize();
            $.ajax({
                cache: true,
                type: "POST",
                url: basePath + "ea/productslaunch/sajax_ea_productserweima2.jspa",
                data: data,
                async: false,
                success: function (result) {
                    var member = eval("(" + result + ")");
                    $("#sccId").val(member.sccId);
                    $("#goodsId").val(member.goodsId);
                    $("#ppId").val(member.ppId);
                    $("#content").val(basePath + "ea/restaurant/ea_scancode.jspa?scancode=01" + member.ppId + "&tj=" + member.sccId);
                }
            });
        }

        var setComm = document.getElementById("setComm_btn");
        var imgCanvas = document.getElementById("save_img");
        var cxt = imgCanvas.getContext("2d");
        var QR = h_img;
        var account = $(".qr_text").val();
        var save_btn = document.getElementById("save_btn");
        var QR_size = 360; //二维码白框大小(宽高一样)
        var c_W = imgCanvas.width;
        var c_Ht = imgCanvas.height * (4 / 5);
        var c_Hb = imgCanvas.height * (1 / 5);
        var ql = (c_W - QR_size) / 2;
        var qt = (c_Ht - QR_size) / 2;
        var margin = 20;
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        layout();
        //drawQR();
        drawText();
        drawLogo();
        save_btn.addEventListener("click", function () {
            var b64 = document.getElementById("qrcode").children[0].toDataURL("image/jpeg");
            h_img.src = b64;
            drawQR();
            var a = toImage();
            // console.log(a);
            if (isAndroid == true) {
                Android.callSaveImage(a);
            } else if (isiOS == true) {
                var str = a.replace(/=/g, "$")
                var url = "func=" + 'iossavephotos';
                params = {'saveImage': str};
                for (var i in params) {
                    url = url + "&" + i + "=" + params[i];
                    // console.log(url);
                }
                return;
            }
            h_img.onload = function () {
                drawQR();
                var a = toImage();
                if (isAndroid == true) {
                    Android.callSaveImage(a);
                } else if (isiOS == true) {
                    var str = a.replace(/=/g, "$");
                    var url = "func=" + 'iossavephotos';
                    params = {'saveImage': str};
                    for (var i in params) {
                        url = url + "&" + i + "=" + params[i];
                        // console.log(url);
                    }
                    //   console.log(url);
                    window.webkit.messageHandlers.Native.postMessage(url);
                }
            };


        });

        //绘制布局
        function layout() {
            //上矩形
            cxt.fillStyle = "#ff6e31";
            cxt.fillRect(0, 0, c_W, c_Ht);
            //下矩形
            cxt.fillStyle = "#ffffff";
            cxt.fillRect(0, c_Ht, c_W, c_Hb);
            //二维码白框
            cxt.fillStyle = "#ffffff";
            cxt.fillRect(ql, qt, QR_size, QR_size);
        }

        //绘制二维码
        function drawQR() {
            cxt.drawImage(QR, ql + margin, qt + margin, QR_size - (margin * 2), QR_size - (margin * 2));
        }

        //绘制文字
        function drawText() {
            cxt.font = "30px microsoft yahei";
            cxt.textAlign = "center";
            cxt.fillText("推荐使用微分金扫码支付", c_W / 2, 80, QR_size);

            cxt.font = "26px microsoft yahei";
            cxt.textAlign = "center";
            cxt.fillText(account, c_W / 2, qt + QR_size + 78, QR_size)
        }

        //绘制logo
        function drawLogo() {
            var logo = new Image();
            logo.src = "<%=path%>/images/ea/office/scanPay/logo.png";
            logo.onload = function () {
                cxt.drawImage(logo, (c_W - 134) / 2, c_Ht + ((c_Hb - 100) / 2), 134, 100);
            }
        }

        //生成图片(base64编码)
        function toImage() {
            var image = new Image();
            image.src = imgCanvas.toDataURL("image/jpeg");
            //console.log(image.src);
            var _src = image.src.split(",");
            return _src[1];
        }

        setComm.addEventListener("click", function () {
            document.querySelector(".comm_page").style.display = "block";
        });
        document.querySelector(".nest_back").addEventListener("click", function () {
            document.querySelector(".comm_page").style.display = "none";
        });

        function dizhi() {
            var ppId = $("#ppId").val();
            var goodsId = $("#goodsId").val();
            var sccId = $("#sccId").val();
            if (ppId==null||ppId==""){
                ppId=companyId;
            }
            console.log(companyId)
            if (sccId != "") {
                $("#content").val(basePath + "ea/restaurant/ea_scancode.jspa?scancode=01" + ppId + "&tj=" + sccId);
                //$("#content").val(basePath + "ea/wfjshop/ea_getcity.jspa?state=0&companyId="+companyId+"&returnPage=01")
            }
        }
        }

    </script>

</head>
<body style="background:#fafafa;">
<header class="com_head">
    <a href="javascript:history.back(-1);" class="back"></a>
    <h1>二维码收款</h1>
</header>
<div id="yincang">
    <div class="qr_wrap">
        <div class="qr_img" id="qrcode">
            <%-- <img id="QR" src="images/QR_img.jpg" alt="">--%>
        </div>
        <div class="qr_img" id="noqrcode">
            <img id="noset" src="<%=path%>/images/ea/office/scanPay/noset2x.png" alt="">
        </div>
        <div class="qr_text">
            ${tcc.pseudoCompanyName}
        </div>
    </div>
    <%--<div class="qr_btn_wrap clearfix">
        <div class="qr_btn_box">
            <a href="###" id="setComm_btn">设置佣金比例</a>
        </div>
        <div class="qr_btn_box">
            <a href="javascript:;" id="save_btn">保存收款码</a>
        </div>
    </div>--%>
</div>
<!--设置佣金比例 开始-->
<div class="comm_page nest_page">
    <div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>设置佣金比例</span>
    </div>
    <form action="" id="form1" style="text-align: center;">
        <div class="nest_bd">
            <div class="wrap_page comm_wrap">
                <div class="commission_wrap">
                    <div class="comm_tit">收款佣金比例</div>
                    <input type="hidden" id="falg" name="falg" value="${param.falg}"/>
                    <input type="hidden" id="companyId" name="companyId" value="${companyId}"/>
                    <input type="hidden" id="goodsId" name="goodsManage.goodsID" value="${productPackaging.goodsID}"/>
                    <input type="hidden" id="ppId" name="productPackaging.ppID" value="${productPackaging.ppID}"/>
                    <input type="hidden" id="sccId" name="tcc.sccId" value="${tcc.sccId}"/>
                    <input type="hidden" id="content" value=""/>
                    <img src="<%=basePath%>${path[0]}" id="logo" style="display:none;">
                    <input type="number" step="0.000001" id="yj" name="setup.brokerage" value="${setup.brokerage}"
                           class="comm_inp">
                    <button id="send" class="sure_btn">确 定</button>
                </div>
            </div>
        </div>
    </form>
</div>
<!--设置佣金比例 结束-->
<style>
    .popup_box {
        width: 12rem;
        border: .15rem solid #1b1b1b;
        border-radius: .25rem;
        position: relative;
        display: none;
    }

    .popup_bd {
        height: 6rem;
        background-color: #f93c3d;
        background-size: 60%;
        background-position: center;
        background-repeat: no-repeat;
        border-top-left-radius: .2rem;
        border-top-right-radius: .2rem;
    }

    .popup_fd {
        background: #ffffff;
        padding: .8rem .6rem .6rem;
    }

    .popup_fd > span {
        display: block;
        text-align: center;
        font-size: .683rem;
        color: #1b1b1b;
        line-height: 1rem;
    }

    /* .sure_btn{display: block;width: 4rem;margin: .6rem auto 0;border: .1rem solid #1b1b1b;background: #e5cc1a;color: #1b1b1b;text-align: center;font-size: .7rem;line-height: 1.2rem;border-radius: .15rem;}*/
    .popup_text span {
        color: #bf0e0e;
    }

    /* .close_btn{position: absolute;width: 1rem;height: 1rem;background: url(../../../images/ea/lottery/close.png) no-repeat center;background-size: contain;right: .5rem;top: .4rem;}*/
</style>
<div class="overlay">
    <div class="popup_box">
        <div class="popup_fd">
            <span class="popup_text">只有公司会员才能设置收款码</span>
            <a href="<%=basePath%>ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany"
               class="sure_btn">立即升级公司会员</a>
        </div>
    </div>
</div>
<canvas id="save_img" width="480" height="800"></canvas>
</body>
</html>
