<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>绑定手机号</title>
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/login/bindTel.css?version=20250528">
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/jquery-1.9.1.min.js"></script>



</head>
<body>

<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="cancel">
                <img src="<%=basePath%>images/WFJClient/pc/login/tel.png" class="gou">
                <p>绑定手机号</p>
                <p class="txt"></p>
            </div>

            <form class="cancel_con" id="myform" method="post">
                <ul>
                    <li>
                        <p>手机号</p>
                        <div class="ipt">
                            <input type="tel" maxlength="11" placeholder="请填写手机号" name="account" id="tel">
                        </div>
                    </li>
                    <li class="verification">
                        <p>验证码</p>
                        <div class="ipt">
                            <input type="text" value="" placeholder="请填验证码" id="valnum">
                        </div>
                        <button  onclick="sendCode(this);return false;" id="gain">获取验证码</button>
                    </li>
                </ul>
                <input type="button" value="确定" id="confirm">
            </form>
        </div>
    </div>
</div>

<script>
    var basePath = "<%=basePath%>";


    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".con").css("height",$(window).height()*0.92+"px");


    });

    var nums = 60;
    var basePath = "<%=basePath%>";
    var tel = document.getElementById("tel");
    var btn = document.getElementById("gain");
    var confirm=document.getElementById("confirm");
    var valnum=document.getElementById("valnum");
    function sendCode(thisBtn) {
        if(tel.value ==""){
            alert("手机号不能为空");
            return false;
        }
        if(!ver_phone()){
            return false;
        }

        //发送短信
        $.ajax({
            cache : true,
            type :"POST",
            url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+tel.value,
            async :false,
            dataType : "json",
            success :function(data){
                //  var member = eval("(" + data + ")");
                i = data.returna;
            }
        });
        btn = thisBtn;
        btn.disabled = true; //将按钮置为不可点击
        btn.innerHTML = nums + '秒重新获取';
        clock = setInterval(doLoop, 1000); //一秒执行一次
        btn.className = "send_btn disable_btn";

    }

    //手机号验证
    function ver_phone() {
        var Sreg= /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
        if(tel.value ==""){
          alert("手机号不能为空");
            return false;
        }else if(!Sreg.test(tel.value)){
          alert("请输入正确格式电话号！");
            return false;
        }else {
            return true;
        }
    };

    function doLoop() {
        nums--;
        if (nums > 0) {
            btn.innerHTML = nums + '秒重新获取';
        } else {
            clearInterval(clock); //清除js定时器
            btn.disabled = false;
            btn.innerHTML = '获取验证码';
            nums = 60; //重置时间
            btn.className = "send_btn";
        }
    }


    //点击注册
    confirm.addEventListener("click",function () {
        if(tel.value ==""){
            alert("手机号不能为空");

            tel.focus();

        }else if(!ver_phone()){

            tel.focus();

        }else if(!verCode()){

            valnum.focus();

        }else {

            $.ajax({
                url: basePath + "/ea/wfjlogin/sajax_ea_bindTel.jspa",
                type: "POST",
                async: false,
                dataType: "json",
                data:{
                    openid:"${openid}",
                    nickName:"${nickName}",
                    tel:tel.value
                },
                success: function (data) {
                    var  m = eval("("+data+")");
                    var r = m.result;
                    if (r=="1") {
                        alert("该手机号已绑定其他微信请更换手机号");
                        i = "";
                        d = 2;
                        clearInterval(clock); //清除js定时器
                        btn.disabled = false;
                        btn.innerHTML = '获取验证码';
                        nums = 60; //重置时间
                        btn.className = "send_btn";
                        return;

                    } else {
                        var preUrl = "<%=(String) session.getAttribute("preUrl")%>";
                        var url = "<%=(String) session.getAttribute("url")%>";
                        if (preUrl != null && preUrl!="" && preUrl != "null") {

                            url = preUrl;

                        }
                        if (url == null || url==""|| url == "null") {
                            url = basePath+"ea/earth/ea_earthIndex.jspa";
                        }

                        document.location.href =  url;

                    }
                }
            });
        }

    })

    //验证码验证
    function verCode() {
        if(valnum.value==""){
            alert("请填写验证码");
            return false;
        } else if(valnum.value!=i){
            alert("验证码不正确");
            return false;
        }else {
            return true;
        }
    }


</script>

</body>
</html>