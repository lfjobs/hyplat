<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>微分金自助收银机登陆</title>
<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/keyboard/vk_loader.js?vk_layout=CN%20Chinese%20Simpl.%20Pinyin&vk_skin=flat_gray" ></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/keyboard2.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
        var basePath="<%=basePath%>";
        $(function(){
            var tz = "${param.tip}";
            var ts = "${tip}";

            if(ts!=""||tz!=""){
                $(".error").show();
                if(tz!=""){
                    $(".error").text(tz);
                }
            }else{

                $(".error").hide();
            }

			/*登录验证回车按键*/
//            $(document).keyup(function(event){
//                if(event.keyCode ==13){
//                    $("#login").trigger("click");
//
//                }
//            });

		});


        function check(){

            VirtualKeyboard.close();
            $(".error").text("");
            var companyIdentifier = $.trim($("#companyIdentifier").val());
            var accountEmail = $.trim($("#accountEmail").val());
            var pwd = $.trim($("#pwd").val());
            var tip = "";
            if(companyIdentifier ==  null || companyIdentifier == ''){
              tip = "请输入商城名称";
            }else if(accountEmail ==  null || accountEmail == ''){
                tip = "请输入账号";
			}else if(pwd ==  null || pwd == ''){
                tip = "请输入密码";
            }
            if(tip!=""){
			    $(".error").show().text(tip);

			    return false;
			}
            $(".error").hide();
            return true;
        }
        function test(obj){
            var bottom= 16;
            var ww = window.innerWidth;




            VirtualKeyboard.toggle($(obj).attr("id"), 'softkey');
            $("#kb_langselector,#kb_mappingselector,#copyrights").css("display", "none");
			if (ww>=1366){
                $("#softkey").attr("style","bottom:3%;margin-left:-338px;");
			}else {
                $("#softkey").attr("style","bottom:"+bottom+"%;");
			}
        }
	</script>


</head>
<body style="zoom: 1;" class="body-login">


<section class="login">
	<!--登录框-->
	<article>
		<form action="<%=basePath%>ea/sm/ea_login.jspa" method="post"  onsubmit="return check()">
			<ul>
				<li>
					<p>商城</p>
					<input type="text" placeholder="请输入超市名称" id="companyIdentifier" name="companyIdentifier" value="${companyIdentifier}" onclick='test(this);'>
				</li>
				<li>
					<p>账户</p>
					<input type="text" placeholder="请输入账户" id="accountEmail" name="account.accountEmail" value="${account.accountEmail}" onclick='test(this);'>
					<input type="hidden"  name="scaleNo" value="${param.scaleNo}">
				</li>
				<li>
					<p>密码</p>
					<input type="password" placeholder="请输入密码" id="pwd" name="account.accountPassword" onclick='test(this);'>
				</li>
			</ul>
			<input type="submit" value="登录" id="login">
			<%--<p class="login_alert login_alert2" style="display: none;">超市名称不正确</p>--%>
			<p class="login_alert error" style="display: none;">${tip}</p>
		</form>
	</article>
	<div id="softkey" class="sof2"></div>
	<!--登录框 end-->
</section>
</body>
</html>