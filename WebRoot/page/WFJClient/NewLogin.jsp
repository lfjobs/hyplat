<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>选择登录注册</title>
	    <%--<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_login.css">--%>
        <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_login_new.css">
	    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.3.min.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/Login/NewLogin.js"></script>
        <script type="text/javascript">
            $(function(){

                var ua = navigator.userAgent.toLowerCase();
                var isWeixin = ua.indexOf('micromessenger') != -1;
                if (!isWeixin) {
                    try {
                        var posNum = Android.forAndroidDeviceId();

                        if (posNum != "" && posNum != null) {
                            posNum = isExistPosNum(posNum);

                        }
                    } catch (error) {
                        if ($(window).width() == 1080 && $(window).height() == 1546) {
                            posNum = 123;

                        }
                    }
                  //  if (posNum != null && posNum != "") {
                  //      window.location.href = "<%=basePath%>page/WFJClient/downLoadCode.jsp?redirectUrl=" + encodeURIComponent(document.referrer);
                 //   } else {
                        try {
                            Android.jumpToLoginActivity();
                        } catch (e) {
                            window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";

                        }
                   // }
                }else{
                    window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
//                    //跳转后台微信授权
//                    var url = window.location.href;
//                        if (url.indexOf("NewLogin.jsp") != -1) {
//                            url = document.referrer;
//                        } else if (url.indexOf("gm.jspa") != -1) {
//                            url = document.referrer;
//                        }else if(url.indexOf("ea_myIndex.jspa?user=") != -1){
//                            url =  basePath + "ea/mycenter/ea_myIndex.jspa";
//                        }
//                        window.location.href = basePath + "/ea/wfjlogin/ea_wxlogin.jspa?redirectUrl=" + encodeURIComponent(url);


                }
            });
            //判断是否是终端机器
            function isExistPosNum(posNum){
                var url = "<%=basePath%>ea/smg/sajax_sm_isExistPosNum.jspa";
                $.ajax({
                    url : url,
                    type : "get",
                    dataType : "json",
                    async:false,
                    data : {
                        posNum:posNum
                    },
                    success : function(data) {
                        var m = eval("(" + data + ")");
                        var result = m.result;
                        if(result!="0"){
                            posNum = "";
                        }

                    },
                    error : function(data) {
                        // alert("验证失败");
                    }

                });
                return posNum;
            }
        </script>
	</head>
    <body class="hy">
    <header>
        <ul class="clearfix">
            <li>
                <a href="javascript:history.back(-1)" >
                <img src="<%=basePath%>/images/WFJClient/Login/register_return.png"/>
                    </a>
            </li>
            <li>
                共享资源 共享金
            </li>
        </ul>
    </header>
    <div class="content">
        <div class="bg_img">
            <img src="<%=basePath%>/images/WFJClient/Login/g.png"/>
        </div>
        <div class="_top">
            <p></p>
        </div>
        <div class="con">
            <h2>数字地球</h2>
            <h3>成就未来不是梦</h3>
        </div>
        <div class="_bottom">
            <p></p>
        </div>
        <div class="dl" onclick="toLogin()">
            <p>登 录</p>
        </div>
        <div class="zc" onclick="toRegister()">
            <p>注 册</p>
        </div>
    </div>
    </body>

<script>
    $(document).ready(function(){
    	$(".header").css("height",$(window).height()*0.08+"px");
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");

        $(".content").css("height",$(window).height()*0.92-1+"px");


        // var num1=num2=num3=0
        window.onload = window.onresize = function(){
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //console.log(clientWidth);
            //通过屏幕宽度去设置不同的后台根字体的大小
            //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
        }
    });
</script>

<script>
	var basePath="<%=basePath%>";
	var sccid = "${param.sccid}";
	var user = "${param.user}";
   	var ccompanyId = "${param.ccompanyId}";
</script>
</html>