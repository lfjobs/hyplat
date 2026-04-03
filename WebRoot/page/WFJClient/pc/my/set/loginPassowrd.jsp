<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
	<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
     <title>&lrm;</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/my/set/loginPassword.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.3.min.js"></script>
	<script>
		var basePath='<%=basePath%>';

	</script>
</head>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath%>/images/ea/office/contract/selectp/return.png"></a></li>
        <li style="width: 80%;text-align: center;">修改登录密码</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<div class="content">
    <form id="myform" method="post" name = "myform" autocomplete="off">
        <input type="password" name="yspsw" placeholder="请输入原始登录密码" autocomplete="off" id="yspsw">
        <input type="password" name="newpsw" placeholder="请输入新的登录密码" autocomplete="off" id="password">
        <input type="password"  placeholder="再次输入新的登录密码" autocomplete="off"  id="password1">
        <input type="button" value="确定" id="zc2">
    </form>
</div>
<div class="wjmm"><span>忘记密码?</span></div>

<!-- 弹窗 -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;"></span>
        </div>
    </center>
</div>

<script type="text/javascript">


    $(function(){
        //弹出层
        $("#prompt").css("position","absolute").css("top",$(window).height()*0.50+"px").css("z-index","999999");
        $("#prompt").find("div").css("line-height",$(window).height()*0.1+"px").css("height",$(window).height()*0.1+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
        $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

        $("#zc2").click(function(){

            if($.trim($("#yspsw").val())==""){
                prompt("请输入原始登录密码");
                return false;
            }
            if($.trim($("#password").val())==""){
                prompt("请输入新的登录密码");
                return false;
            }

            if($.trim($("#password").val())==$.trim($("#yspsw").val())){
                prompt("旧密码和新密码不能相同");
                return false;
            }
            if($.trim($("#password").val()).length<6){
                prompt("密码不能小于6位");
                return false;
            }
            if($.trim($("#password1").val())==""){
                prompt("再次输入新的登录密码");
                return false;
            }


            if($.trim($("#password").val())!=$.trim($("#password1").val())){
                prompt("两次输入的新密码不一致");
                return false;
            }
            $.ajax({
                type: "POST",
                url: basePath+"ea/mycenter/sajax_ea_updateLoginPsw.jspa",
                dataType: "json",
                data: {
                    yspsw:$.trim($("#yspsw").val()),
                    newpsw:$.trim($("#password").val())

                },
                success: function (data) {

                    var m = eval("("+data+")");
                    var result = m.re;
                    if (result=="2") {

                         //原始密码不正确
                        prompt("原始登录密码不正确");
                    }else  if (result=="0") {

                        prompt("密码修改成功");
                         document.myform.reset();
                    }
                }
            });
        });

        //忘记密码
        $(".wjmm").find("span").click(function(){

          document.location.href = basePath+"page/WFJClient/pc/my/set/forgetPassword.jsp?pswtype=登录";

        });
    });
    function prompt(obj){
        if($("#prompt").css("display")!="none")
            return;
        $("#prompt").find("span").text(obj);
        $("#prompt").fadeIn(500);
        setTimeout(function(){
            $("#prompt").fadeOut(500);
            $("#prompt").find("span").text("");
        }, 3000);
    }

</script>
</body>
</html>