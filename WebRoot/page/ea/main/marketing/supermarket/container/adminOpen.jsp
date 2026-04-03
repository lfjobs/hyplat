<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
<meta charset="UTF-8">
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<title>管理员登录</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/adminOpen.css"/>

<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/comm.js"></script>
<script type="text/javascript" src="<%=basePath%>js/BuildPlatform/setHtmlFont.js"></script>
<style type="text/css">
    .loading {
        display: none;
        background-color: #fff;
        color: #fff;
        font-size: 1rem;
        height: 100%;
        position: fixed;
        left: 0;
        top: 0;
        right: 0;
        bottom: 0;
        padding-top: 35vh;
    }

    .loading .loading-box {
        background-color: rgba(58, 58, 58, .9);
        display: flex;
        align-items: center;
        justify-content: space-around;
        width: 38%;
        margin: 0 auto;
        height: 3rem;
        border-radius: .2rem;
    }

    .loading .loading-box img {
        width: 1rem;
    }

    .loading .loading-box p {
        margin-top: 0 !important;
        font-size: 0.7rem;
    }

    .loading .loading-box p span {

    }

    @media screen and (min-width: 960px) {
        .loading .loading-box {
            width: 11rem;
            height: 4.6rem;
        }

        .loading .loading-box img {
            width: 2rem;
        }

        .loading .loading-box p {
            font-size: 1.2rem;
        }

    }
</style>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png"/>
            </a>
        </li>
        <li>
            管理员登录
        </li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <form id="formData" name="formData">
                <ul class="row">
                    <li>
                        <%--<label for="">
                            <img src="<%=basePath%>images/WFJClient/pc/login/land04.png"/>
                        </label>
                        <input type="text" id="org" placeholder="请输入公司名称" name="companyIdentifier"
                               autocomplete="off"/>--%>
                        <span>货柜号：</span>
                        <div class="hgcode"></div>
                    </li>
                    <li>
                        <label>
                            <img src="<%=basePath%>images/WFJClient/pc/login/land_07.png"/>
                        </label>
                        <input type="text" id="count" placeholder="请输入管理员账号/手机号" name="tEshopCustomer.account"
                               autocomplete="off"/>
                    </li>
                    <li>
                        <label>
                            <img src="<%=basePath%>images/WFJClient/pc/login/land_10.png"/>
                        </label>
                        <input type="password" id="pw_pwd" placeholder="请输入管理员密码" name="tEshopCustomer.password"
                               autocomplete="off"/>
                    </li>
                </ul>
                <input type="submit" style="display: none;" name="submit" id="submit"/>
                <input type="hidden" name="posNum" id="posNum">
                <input type="hidden" name="hgcode" id="hgcode">
                <input type="hidden" name="user" id="user">
            </form>
        </div>
        <div class="btn">
            <button onclick="login()">确定</button>
        </div>
    </div>
</div>

<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
<div class="loading">
    <div class="loading-box">
        <img src="<%=basePath%>images/WFJClient/Newjspim/loading.gif"/>
        <p style="margin-top: 15rem;"><span>加载中...</span></p>
    </div>
</div>
</body>
<script>
    var basePath = "<%=basePath%>";
    var posNum = "${param.posNum}";  // 货柜号
    var user = "${param.user}";      //  用户
    var hgcode = "${param.hgcode}";  //  库房编号
    var toPage = "${param.toPage}";  //  跳转页面 默认或者为空 则跳转库存管理页面  'door'否则跳转开关门页面
    var ntoken = 0;
    var clock = '';

    $(function () {
        if (hgcode == "" || hgcode == null || posNum == "" || posNum == null ) {
            $.ajax({
                url: basePath + "ea/depotmanage/sajax_mobile_getPosDeviceByPosNum.jspa",
                type: "get",
                data: {
                    posNum: (posNum == null || posNum == undefined || posNum == "") ? hgcode : posNum
                },
                dataType: "json",
                success: function cbf(data) {
                    var m = eval("(" + data + ")");
                    var flag = m.flag;
                    if (flag == "0") {

                        $("#hgcode").val(m.pos.hgcode);
                        $(".hgcode").text(m.pos.hgcode);
                        $("#posNum").val(m.pos.posNum);
                        hgcode = m.pos.hgcode;
                        posNum = m.pos.posNum;
                    } else {
                        prompt(m.msg);
                    }
                }
            });
            //http://192.168.110.133:8080/page/ea/main/marketing/supermarket/container/adminOpen.jsp?posNum=26a261b7fe51032df994ec8bf9b107083&user=15801151282&hgcode=hg001
        }else {
            $("#hgcode").val(hgcode);
            $(".hgcode").text(hgcode);
            $("#posNum").val(posNum);
        }

        if (user != "" && user != null) {
            $("#user").val(user);
            if (hgcode != "" && hgcode != null) {
                $(".loading").show();
                $("#hgcode").val(hgcode);
                $(".hgcode").text(hgcode);
                $("#posNum").val(posNum);
                login();
            }
        }

        $(".close-tingyong").click(function () {
            $(".div-tingyong").hide();
        });
    });

    function prompt(c) {
        $(".titlep").text(c);
        $(".div-tingyong").show();
    }

    /**
     * 登录验证
     */
    function login() {
        try {
            if (user == "" || user == null) {
                var count = $("#count").val();
                var password = $("#pw_pwd").val();

                if (count === '') {
                    prompt("请输入账号");
                    $("#count").focus();
                    return false;
                }

                var reg = new RegExp("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
                if (!reg.test(count)) {
                    prompt("手机号格式不正确！");
                    $("#count").focus();
                    return false;
                }

                if (password === '') {
                    prompt("请输入密码");
                    return false;
                }
            }

            var formData = $("#formData").serialize();
            var url = basePath + "/ea/sm/sajax_ea_adminLogin.jspa";
            $(".loading").show();
            $.ajax({
                url: url,
                type: "post",
                data: formData,
                dataType: "json",
                success: function cbf(data) {
                    var m = eval("(" + data + ")");
                    var flag = m.flag;
                    if (flag == "登录成功") {
                        try {
                            if (toPage == "door") {
                                window.location.href = basePath + "page/ea/main/marketing/supermarket/container/selectDoor2.jsp?hgcode=" + hgcode + "&loginMode=admin&staffName=" + getcookie("staffName") + "&posNum=" + posNum;
                            } else {
                                window.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductManageMobileList.jsp";
                            }
                        } catch (e) {
                            $(".loading").hide();
                            prompt(e);
                        }
                    } else {
                        $(".loading").hide();
                        prompt(flag);
                    }
                }, error: function cbf(data) {
                    $(".loading").hide();
                    prompt("出错");
                }
            });
        } catch (e) {
            $(".loading").hide();
            prompt(e);
        }

    }

    /*function pageJump(type) {
        if (type == "open") {
            window.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductManageMobileList.jsp";
        } else {
            prompt("货柜开门失败！");
        }
    }*/
</script>
</html>