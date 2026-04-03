<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>天太世统后台ERP管理系统</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
        body
        {
            padding-bottom: 0px;
            background-color: #ffffff;
            margin: 0px;
            padding-left: 0px;
            padding-right: 0px;
            font-family: "宋体" , Arial, Helvetica, sans-serif;
            color: #666666;
            font-size: 12px;
            padding-top: 0px;
        }
        .wrap
        {
            width: 781px;
            margin: 0 auto;
        }
        .wrapmain
        {
            width: 100%;
            margin: 0 auto;
            margin-top: 10px;
            background-color: #ffffff;
        }
        body, table, tr, td
        {
            font-size: 12px;
        }
        .logo
        {
            width: 781px;
            height: 80px;
            margin-top: 20px;
        }
        .logo img
        {
            width: 80px;
            height: 80px;
            float: left;
        }
        .logotxt
        {
            height: 80px;
            line-height: 80px;
            float: left;
            margin-left: 20px;
            font-family: 微软雅黑;
            font-size: 32px;
            color: #4B78A1;
        }
        .main
        {
            position: relative;
            margin: 0 auto;
        }
        .c_left
        {
            width: 680px;
            height: 484px;
            margin-left: -30px;
        }
        .c_left img
        {
            width: 780px;
            height: 484px;
        }
        .shipin
        {
            width: 400px;
            height: 315px;
            position: absolute;
            left: 430px;
            top: 57px;
            padding-top: 20px;
        }
        .denglu
        {
            width: 320px;
            position: absolute;
            left: 430px;
            top: 30px;
        }
        .ziti
        {
            height: 40px;
            color: #00000;
            width: 75px;
            text-align: right;
            padding-right: 2px;
            font-size: 13px;
        }
        .login
        {
            width: 145px;
            height: 44px;
            border: 0px;
            float: left;
            cursor: pointer;
            background-image: url('<%=basePath%>images/ea/login/login_btn.png');
            background-repeat: no-repeat;
            margin: 0 auto;
            color: White;
            line-height: 44px;
            font-size: 18px;
            font-family: 微软雅黑;
            margin-left: 35px;
        }
        .inputtxt
        {
            border: #c2cbd8 0px solid;
            width: 220px;
            height: 28px;
            margin-top: 8px;
            line-height: 28px;
            margin-left: 3px;
            background-color: #A9CBDA; /*#ACD1E1*/
            color: White;
            font-size: 16px;
            font-family: 微软雅黑;
        }
        .inputtxtcode
        {
            border: #c2cbd8 0px solid;
            width: 80px;
            height: 28px;
            margin-top: 8px;
            line-height: 28px;
            margin-left: 3px;
            background-color: #A9CBDA;
            color: White;
            font-size: 16px;
            font-family: 微软雅黑;
        }
        .notice
        {
            line-height: 26px;
            padding-top: 10px;
            margin: 0 auto;
        }
        TH
        {
            background-image: url('<%=basePath%>images/ea/login/mobilelogin/dhbg.gif');
            cursor: pointer;
            padding: 2px 4px;
            margin-right: 4px;
            border-bottom: 1px solid #999999;
            border-top: 1px solid #999999;
            font-size: 13px;
            color: #b3b3b2;
        }
        .selected
        {
            background: #ffffff;
            font-size: 13px;
            color: #587eab;
        }
        .clearfix
        {
            font-size: 0;
            clear: both;
        }
        /*4A79A5  4B78A1*/
        .b_center
        {
        }
        .tab_title
        {
            display: inline-block;
            width: 100px;
            float: left;
            height: 45px;
            background-image: url('<%=basePath%>images/ea/login/tab2.png');
            background-repeat: no-repeat;
            font-family: 微软雅黑;
            font-size: 16px;
            line-height: 45px;
            cursor: pointer;
            background-color: transparent;
        }
        .selected
        {
            background-image: url('<%=basePath%>images/ea/login/tab1.png');
        }
        /* 登录框 */
        .b_left, .b_right, .b_top, .b_bottom
        {
            position: absolute;
        }
        .b_left
        {
            left: -25px;
            width: 25px;
            height: 368px;
        }
        .b_left img
        {
            width: 25px;
            height: 368px;
        }
        .b_top
        {
            width: 400px;
            height: 36px;
            top: -32px;
        }
        .b_top img
        {
            width: 400px;
            height: 36px;
        }
        .b_right
        {
            width: 25px;
            height: 388px;
            left: 400px;
        }
        .b_right img
        {
            width: 25px;
            height: 388px;
        }
        .b_bottom
        {
            width: 400px;
            height: 25px;
            top: 368px;
        }
        .b_bottom img
        {
            width: 400px;
            height: 25px;
        }
        
        .inputkey
        {
            width: 280px;
            height: 45px;
            margin: 0 auto;
        }
        .input_left
        {
            width: 33px;
            height: 44px;
            float: left;
        }
        .input_right
        {
            width: 6px;
            height: 44px;
            float: left;
            background-image: url('<%=basePath%>images/ea/login/input_right.png');
            background-repeat: no-repeat;
        }
        .input_center
        {
            width: 240px;
            height: 44px;
            float: left;
            background-image: url('<%=basePath%>images/ea/login/input_center.png');
            background-repeat: repeat-x;
        }
        .inputtxt1 .input_left
        {
            background-image: url('<%=basePath%>images/ea/login/i_zj_ico.png');
            background-repeat: no-repeat;
        }
        .inputtxt2 .input_left
        {
            background-image: url('<%=basePath%>images/ea/login/i_account_ico.png');
            background-repeat: no-repeat;
        }
        .inputtxt3 .input_left
        {
            background-image: url('<%=basePath%>images/ea/login/i_pwd_ico.png');
            background-repeat: no-repeat;
        }
        .divcode .input_left
        {
            background-image: url('<%=basePath%>images/ea/login/i_code_ico.png');
            background-repeat: no-repeat;
        }
        .divcode .input_center
        {
            width: 100px;
        }
        .errormsg
        {
            display: none;
            color: Red;
            font-family: 微软雅黑;
            text-align: left;
            width: 280px;
            height: 36px;
            line-height: 44px;
            margin-left: 106px;
            font-size: 14px;
        }
        /* 填补空白 */
        .across
        {
            height: 12px;
            width: 399px;
        }
        #validateCode{
        margin-left: 20px;
        margin-top: 10px;
        }
    </style>
</head>
<body style="text-align: center;">
    <div class="wrap">
        <div class="logo">
            <img src="<%=basePath%>images/ea/login/logo.png" alt="" />
            <div class="logotxt">
                五层五清孵化管理体系---5L5C系统平台</div>
        </div>
    </div>
    <div class="wrapmain">
        <div class="wrap">
            <div class="main">
                <div class="c_left">
                    <img src="<%=basePath%>images/ea/login/content_bg.png" alt="" />
                </div>
                <div class="denglu">
                    <form name="loginForm" method="post" id="loginForm" action="">
                    <input type="submit" name="submit" style="display: none">
                    <!-- 隐藏的公司类型 -->
                    <input type="text" value="00" name="companyType" style="display: none" id="companyType">
                    <div class="b_left">
                        <img src="<%=basePath%>images/ea/login/b_left.png" alt="" /></div>
                    <div class="b_right">
                        <img src="<%=basePath%>images/ea/login/b_right.png" alt="" /></div>
                    <div class="b_top">
                        <img src="<%=basePath%>images/ea/login/b_top.png" alt="" /></div>
                    <div class="b_bottom">
                        <img src="<%=basePath%>images/ea/login/b_bottom.png" alt="" /></div>
                    <div class="b_center" style="width: 400px; height: 434px;">
                        <!--tab_title-->
                        <div style="width: 400px; height: 45px;">
                            <div class="tab_title selected" onclick="changeMenu(1)">
                                管理平台</div>
                            <div class="tab_title" onclick="changeMenu(2)">
                                移动平台</div>
                            <div class="tab_title" onclick="changeMenu(3)">
                                客户平台</div>
                            <div class="tab_title" onclick="changeMenu(1)">
                                产品视频</div>
                        </div>
                        <div style="width: 400px; height: 324px; background-image: url('<%=basePath%>images/ea/login/tab_bg.png');" class="aa">
                            <div class="across" style="height: 36px;">
                                <div class="errormsg">
                                    组织机构输入不能为空</div>
                            </div>
                            <!--组织机构-->
                            <div class="inputkey inputtxt1">
                                <!--文本框左边缘-->
                                <div class="input_left ">
                                </div>
                                <!--文本框-->
                                <div class="input_center">
                                    <input type="text" name="companyIdentifier" id="strj1" class="inputtxt" value="${companyIdentifier==null?'点击输入组织机构名':companyIdentifier}" />
                                </div>
                                <!--文本框右边缘-->
                                <div class="input_right">
                                </div>
                            </div>
                            <div class="across ">
                            </div>
                            <!--用户名-->
                            <div class="inputkey inputtxt2">
                                <!--文本框左边缘-->
                                <div class="input_left ">
                                </div>
                                <!--文本框-->
                                <div class="input_center">
                                    <input type="text" name="account.accountEmail" class="inputtxt" id="username" value="${account.accountEmail==null?'点击输入用户名':account.accountEmail}" />
                                </div>
                                <!--文本框右边缘-->
                                <div class="input_right">
                                </div>
                            </div>
                            <div class="across">
                            </div>
                            <!--密码-->
                            <div class="inputkey inputtxt3">
                                <!--文本框左边缘-->
                                <div class="input_left ">
                                </div>
                                <!--文本框-->
                                <div class="input_center">
                                	<c:if test="${account.accountPassword!=null}">
                                	<input type="password" name="account.accountPassword" class="inputtxt memory" id="password"
                                        value="${account.accountPassword}"  />
                                	</c:if>
                                	<c:if test="${account.accountPassword==null}">
                                	<input type="text" class="inputtxt pwd" value="点击输入密码" />
                                	<input type="password" name="account.accountPassword" class="inputtxt" id="password"
                                        value="${account.accountPassword}" style="dcolor:#000;display: none;" />
                                	</c:if>
                                </div>
                                <!--文本框右边缘-->
                                <div class="input_right">
                                </div>
                            </div>
                            <div class="across">
                            </div>
                            <!--验证码-->
                            <div class="inputkey divcode">
                                <!--文本框左边缘-->
                                <div class="input_left ">
                                </div>
                                <!--文本框-->
                                <div class="input_center">
                                    <input type="text" name="validateCode" class="inputtxtcode" id="validate" value="验证码" />
                                    <input type="hidden" name="activityLogin"  value="" />
                                </div>
                                <!--文本框右边缘-->
                                <div class="input_right">
                                	<img border="0" src="<%=basePath%>page/ea/security_code.jsp?abcd=0.9140284241695912" onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abcd='+Math.random()" name="validateImage" id="validateCode"/>
                                </div>
                            </div>
                            <div class="across">
                            </div>
                            <!--<input onclick="login()" type="button" value="" class="login">-->
                            <!--登录按钮-->
                            <div onclick="login()" class="login">
                                登&nbsp;&nbsp;录
                                <!--<img src="images/login_btn.png" class="login" onclick="login()" alt="" />-->
                            </div>
                            <div onclick="vlogin()" class="login " style="">
                                个人注册
                                <!--<img src="images/login_btn.png" class="login" onclick="login()" alt="" />-->
                            </div>
                        </div>
                    </div>
                    </form>
                </div>
                <div class="shipin" style="display: none;">
                    <param name="allowScriptAccess" value="always" />
						<embed pluginspage="http://www.macromedia.com/go/getflashplayer"
							src="http://you.video.sina.com.cn/api/sinawebApi/outplayrefer.php/vid=89157528_1284722721_bkO9TXBtXGDK+l1lHz2stqkP7KQNt6nki2KwvFCmIgxYQ0/XM5GRa9wC5yzUCNkEqDhATJE+cPkj1RU/s.swf"
							type="application/x-shockwave-flash" name="ssss"
							allowFullScreen="true" allowScriptAccess="always" width="400px"
							height="315px"></embed>
                </div>
                <div class="clearfix">
                </div>
            </div>
        </div>
    </div>
    <div class="wrap">
        <div class="notice">
            版权所有 北京天太世统科技有限公司 服务热线：010-64164005&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=basePath%>images/ea/login/knet.png"
                style="vertical-align: middle;"/>
        </div>
    </div>
    <script type="text/javascript">
        $(document).ready(function () {
            // login_btn.click 在鼠标左键摁下让按钮下移一像素，鼠标叹气再恢复原位，使其在点击时产生动态效果。
            $(".login").mousedown(function () {
                $(this).css("margin-top", "1px");
            }).mouseup(function () {
                $(this).css("margin-top", "0px");
            });
            
            // 文本框默认文字在当前文本框得到光标时消失，如果文本框失去光标时，当前文本框 value 为空则显示默认文字。
            $(".inputtxt").focus(function () {
                var thistxt = $(this).val();
                if (thistxt == "点击输入组织机构名" || thistxt == "点击输入用户名" || thistxt == "点击输入密码") {
                    if ($(this).hasClass("pwd")) {
                        $(this).hide();
                        $("#password").show();
                        $("#password").focus();
                    }
                    else {
                        $(this).val("");
                    }
                    $(this).css("color", "#000");
                }
            }).blur(function () {
				if ($(this).hasClass("memory")) {
                        return;
                    }	
                if ($(this).val() == "") {
                    $(this).css("color", "White");
                    var inputname = $(this).attr("name");
                    switch (inputname) {
                        case "companyIdentifier": $(this).val("点击输入组织机构名"); break;
                        case "account.accountEmail": $(this).val("点击输入用户名"); break;
                        case "account.accountPassword":
                            if ($("#password").val() == "") { $("#password").hide().css("color", "#000"); $(".pwd").show(); $(".pwd").css("color", "White"); } break;
                        default: break;
                    }
                }
            });
            // 验证码输入框输入判断
            $("#validate").focus(function () {
                if ($(this).val() == "验证码") {
                    $(this).val("").css("color", "#000");
                }
            }).blur(function () {
                if ($(this).val() == "") {
                    $(this).val("验证码").css("color", "white");
                }
            });

            $("#loginForm").attr("action", "<%=basePath%>ea/login.jspa?");
            $("div.shipin").hide();
            var $div_li = $(".tab_title");
            $div_li.click(function () {
                $(this).addClass("selected")
                //当前<th>元素高亮 修改样式，并且修改显示图片
				   .siblings().removeClass("selected");  //去掉其它同辈<th>元素的高亮   
                if ($(this).text().indexOf("管理平台") > -1) {
                    $(".aa").show();
                    $("#companyType").val("00");
                    $("div.shipin").hide();
                } else if ($(this).text().indexOf("移动平台") > -1) {
                    $(".aa").show();
                    $("#companyType").val("01");
                    $("div.shipin").hide();
                } else if ($(this).text().indexOf("客户平台") > -1) {
                    $(".aa").show();
                    $("#companyType").val("02");
                    $("div.shipin").hide();
                } else if ($(this).text().indexOf("产品视频") > -1) {
                    $(".aa").hide();
                    $("div.shipin").show();
                }
            });
        });
        function changeMenu(opn) {
            if (opn == 1) {
                $("#companyType").val("00");
                $("#loginForm").attr("action", "<%=basePath%>ea/login.jspa?");
            }else if (opn == 2) {
                $("#companyType").val("01");
                $("#loginForm").attr("action", "<%=basePath%>ea/loginwebkit.jspa?");
            }else if (opn == 3) {
                $("#companyType").val("02");
                 $("#loginForm").attr("action", "<%=basePath%>ea/login.jspa?");
            } 
        }
        // 登录按钮点击触发事件，判断文本框输入是否为空，是否合法
        function login() {
            var png = $("#daohang").attr("src");
            var orgainze = $("#strj1").val();
            var username = $("#username").val();
            var password = $("#password").val();
            var validate = $("#validate").val();
            if (orgainze == "点击输入组织机构名") {
                $(".errormsg").html("组织名称不能为空").show();
                return false;
            }
            if (username == "点击输入用户名") {
                $(".errormsg").html("用户名不能为空").show();
                return false;
            }
            if ($(".pwd").css("display") == "inline-block" && $("#password").val() == "") {
                $(".errormsg").html("密码不能为空").show();
                return false;
            }
            if (validate == "验证码") {
                $(".errormsg").html("验证码不能为空").show();
                return false;
            }
            document.loginForm.submit.click();
        }
        var result = "${result}";
        var error = "";
        if (result == "1") {
            error = "登录企业不存在!";
            alert(error);
            // $(".errormsg").html(error);
        } else if (result == "2") {
            error = "登录所在公司状态不正常！";
            alert(error);
            // $(".errormsg").html(error);
        } else if (result == "4") {
            error = "登录帐号被停用！";
            // alert(error);
            $(".errormsg").html(error);
        } else if (result == "3" || result == "5") {
            error = "登录账号或密码不正确!";
            alert(error);
            // $(".errormsg").html(error);
        } else if (result == "99") {
            error = "验证码不正确!";
            alert(error);
            // $(".errormsg").html(error);
        } else if (result == "98") {
            error = "验证码不能为空!";
            alert(error);
            // $(".errormsg").html(error);
        } else if (result == "97") {
            error = "用户名和密码不能为空!";
            alert(error);
            // $(".errormsg").html(error);
        } else if (result == "6") {
            error = "禁止多开登录!";
            alert(error);
            // $(".errormsg").html(error);
        }
        document.onkeydown = function (evt) {//捕捉回车   
            evt = (evt) ? evt : ((window.event) ? window.event : ""); //兼容IE和Firefox获得keyBoardEvent对象
            var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
            if (key == 13) { //判断是否是回车事件。
                login();
            }

        };
         function vlogin() {
         	document.location.href = "<%=basePath%>page/ea/vlogin.jsp";
         }
    </script>
</body>

</html>
