<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/set/setlist.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/comm.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>webView/jweixin-1.5.0.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>webView/uni.webview.1.5.2.js" type="text/javascript" charset="utf-8"></script>
    <title>我的</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            设置
        </li>
    </ul>
</header>

<div class="content">
    <section>
        <a href="<%=basePath%>ea/mycenter/ea_getAccountInfo.jspa">
            <div class="cts-div"><label>账号与密码</label>
                <div class="img-right"><img src="<%=basePath
%>images/WFJClient/pc/newimg/img_39.png"></div>
            </div>
        </a>
        <div class="cts-div" id="rz"><label>实名认证</label>
            <div class="img-right"><img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png"></div>
            <span class="rz-
span">${rzstatus eq "rz"?"已认证":"未认证"}</span></div>
        <a href="<%=basePath%>page/WFJClient/pc/my/set/shsitelist.jsp">
            <div class="cts-div"><label>收货地址</label>
                <div class="img-right"><img src="<%=basePath
%>images/WFJClient/pc/newimg/img_39.png"></div>
            </div>
        </a>
    </section>

    <section>
        <a href="<%=basePath%>/ea/wfjshop/ea_getNewsList.jspa?query=query&cate=使用帮助">
            <div class="cts-div">
                <label>使用帮助</label>
                <div class="img-right">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png">
                </div>
            </div>
        </a>
        <%--<div class="cts-div"><label>意见反馈</label><div class="img-right"><img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png"></div></div>--%>
        <a href="<%=basePath%>/page/WFJClient/pc/my/set/officialcustomer.jsp">
            <div class="cts-div">
                <label>官方客服</label>
                <div class="img-right">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png">
                </div>
            </div>
        </a>
        <a href="<%=basePath%>page/WFJClient/privacyPolicy/pric.html">
            <div class="cts-div">
                <label>数字地球隐私政策协议</label>

                <div class="img-right">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png">
                </div>
            </div>
        </a>
        <%--		<a href="<%=basePath%>/ea/wfjshop/ea_getNewsList.jspa?query=query&cate=关于数字地球"><div class="cts-div"><label>关于数字地球</label></div></a>--%>
        <a href="<%=basePath%>page/WFJClient/privacyPolicy/pricUser.html">
            <div class="cts-div"><label>用户协议</label>
                <div class="img-right">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png">
                </div>

            </div>
        </a>
        <a href="<%=basePath%>page/WFJClient/privacyPolicy/pricSale.html">
            <div class="cts-div"><label>消费者权益保障措施说明</label>
                <div class="img-right">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png">
                </div>
            </div>
        </a>
        <a href="<%=basePath%>page/WFJClient/privacyPolicy/pricBusi.html">
            <div class="cts-div"><label>商户入驻平台协议</label>
                <div class="img-right">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png">
                </div>
            </div>
        </a>
        <a>
            <div class="cts-div"><label class="switch">
                <input type="checkbox" id="sound_check">
                <span class="slider"></span>
            </label>
                <span>语音助手</span></div>
        </a>

        <a>
            <div class="cts-div" id="logoff">注销</div>
        </a>
        <!-- 遮罩层 -->
        <div id="modalOverlay" class="modal-overlay">
            <div class="modal">
                <div class="modal-title">注销后将无法使用 App</div>
                <div class="modal-buttons">
                    <button class="btn cancel" onclick="closeModal()">取消</button>
                    <button class="btn confirm" onclick="confirmLogout()">确认注销</button>
                </div>
            </div>
        </div>


    </section>


    <section class="tc-div" id="exitLogin">
        <%--<div class="cts-div">切换账号</div>--%>
        <a>
            <div class="cts-div">退出登录</div>
        </a>
    </section>

</div>
</body>
<style>
    /* 遮罩层 */
    .modal-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1000;
        display: none;
    }

    /* 弹窗 */
    .modal {
        background: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
        text-align: center;
        width: 300px;
    }

    .modal-title {
        font-size: 18px;
        margin-bottom: 15px;
    }

    .modal-buttons {
        display: flex;
        justify-content: space-between;
        margin-top: 15px;
    }

    .btn {
        padding: 8px 16px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
    }

    .btn.cancel {
        background: #ccc;
        color: black;
    }

    .btn.confirm {
        background: red;
        color: white;
    }
</style>
<script type="text/javascript">
    // 小程序隐藏注销
    document.addEventListener('UniAppJSBridgeReady', function () {
        let currentEnvironment = "";
        uni.getEnv(function (res) {
            // console.log(JSON.stringify(res));
            currentEnvironment = Object.keys(res)[0]
        })
        console.log(currentEnvironment)
        if (currentEnvironment == "miniprogram") {
            $("#logoff").hide()
        }
    })
    document.getElementById('logoff').addEventListener('click', function () {
        showModal();
    })

    function showModal() {
        document.getElementById('modalOverlay').style.display = 'flex';
    }

    function closeModal() {
        document.getElementById('modalOverlay').style.display = 'none';
    }

    function confirmLogout() {
        document.location.href = "<%=basePath%>ea/mycenter/ea_setLogOff.jspa";
        // 注销点击
        localStorage.removeItem('autoLoginUser')
        localStorage.setItem('setLogOff', 'setLogOff')
    }
</script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var rzstatus = "${rzstatus}";
    const checkbox = document.getElementById('sound_check');
    document.addEventListener('DOMContentLoaded',function () {
        if (typeof Android !== 'undefined'){
            let soundPermission = Android.getSoundPermission();
            if (soundPermission){
                checkbox.checked=true
            }else {
                checkbox.checked=false
            }
            checkbox.addEventListener('change', function() {
                if (this.checked) {
                    Android.initPermission();

                } else {
                    Android.destroySpeech();
                    Android.setSoundPermission(false);
                }
            });
        }else {

        }
    })


    $(function () {


        //认证
        $("#rz").click(function () {
            if (rzstatus == "wrz") {
                //说明没有认证去认证
                document.location.href = basePath + "ea/mycenter/ea_getInfo.jspa";
            } else {
                //查看信息
                document.location.href = basePath + "ea/mycenter/ea_getInfo.jspa?op=view";
            }
        });
    })

    // 退出点击
    document.addEventListener('UniAppJSBridgeReady', function () {
        document.getElementById('exitLogin').addEventListener('click', function () {
            let currentEnvironment = "";
            uni.getEnv(function (res) {
                console.log(JSON.stringify(res));
                currentEnvironment = Object.keys(res)[0]
            })

            function setcookie(name, value) {
                var Days = 30;
                var exp = new Date();
                exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
                document.cookie = "user.autologin" + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            }

            localStorage.removeItem('autoLoginUser');
            setcookie("user", "");
            setcookie("pw", "");
            if (currentEnvironment == "miniprogram") {
                uni.reLaunch({url: '/pages/login/login?webview=webviewClear'});
                return;
            } else {
                document.location.href = "<%=basePath%>/ea/mycenter/ea_exitLogin.jspa";
                return;
            }
        })
    });


</script>

</html>
