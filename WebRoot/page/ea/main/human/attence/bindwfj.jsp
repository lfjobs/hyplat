<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>绑定账号</title>
	<script type="text/javascript" src="<%=basePath%>js/BuildPlatform/font-size.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/Make_an_appointment.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/human/attence/bindwfj.js"></script>



    <style type="text/css">
        #prompt div{
            width: 70%;
            background: rgba(0,0,0, 0.5);
        }
        .li-name{
          display:block;
        }

    </style>
    <script>
        var basePath="<%=basePath%>";
        var  openid = "${param.openid}";
        var companyId = "${param.companyId}";
        var companyname = "${param.companyname}";
    </script>
</head>
<body>

<header class="header-bdsj">
   <ul class="bdzh">
       <li>
           <a href="javascript:void(0)" onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath%>images/BuildPlatform/left_jt.png"></a>
       </li>

       <li>绑定账号</li>
       <li onclick="bindAccount()"><span></span><p>保存</p></li>
   </ul>
</header>
<div class="content_hidden div-input div-bdsj">
    <div class="content">
        <div class="con confi">
            <ul class="row">
                  <li class="li-name">
                    <p class="left">微分金手机号</p>
                    <input type="number" placeholder="请输入" id="tel">
                   </li>
                    <li class="li-name">
                        <p class="left">验证码</p>
                        <input type="number" placeholder="请输入" id="valnum">
                        <input type="button" id="ver_btn" class="inp-hq" value="获取验证码" onclick="sendCode(this);return false;">
                    </li>

            </ul>
        </div>
        <%--<div class="btn" onclick="bindAccount()">--%>
            <%--<button>确定</button>--%>
        <%--</div>--%>
    </div>
</div>

<%--提示框--%>
<div class="div-ts">
    预约中
</div>
<!-- 提示窗口 -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>

<script>
    $(document).ready(function() {
        $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.2 + "px");
        $("#prompt").find("div").css("height", $(window).height() * 0.1 + "px").css("font-size", $(window).height() * 0.04 + "px").css("color", "#FFFFFF");
        $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");
        $("header").css("height", $(window).height() * 0.08 - 1 + "px");
        $("header").css("line-height", $(window).height() * 0.08 - 1 + "px");
        $("header ul li").css("height", $(window).height() * 0.08 - 1 + "px");
        $("header ul li").css("line-height", $(window).height() * 0.08 - 1 + "px");

    })

</script>


</body>
</html>