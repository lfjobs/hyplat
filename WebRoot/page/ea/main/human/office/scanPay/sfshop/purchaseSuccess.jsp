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
    <title>购买成功</title>
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/sfshop/new_style.css">
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/new-page.js"></script>


</head>
<body>
<%--<header>--%>
    <%--<ul>--%>
        <%--<li style="width: 10%;">--%>
            <%--<a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/office/scanPay/sfshop/left_jt.png"></a>--%>
        <%--</li>--%>
        <%--<li style="width: 80%;">购买成功</li>--%>
        <%--<li style="width: 10%;"></li>--%>
    <%--</ul>--%>
<%--</header>--%>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="cancel">
                <img src="<%=basePath%>/images/ea/office/scanPay/sfshop/ico-cancel.png" class="gou">
                <p>购买成功</p>
                <p class="txt">验证手机下载微分金APP</p>
                <p class="txt">查询订单、兑换现金、买卖产品更方便</p>
            </div>
            <c:if test='${param.bd!="1"&&mealNum!="-1"}'>
            <div class="Take">
                <p class="ta">取餐号<span id="meid">${mealNum}</span></p>
                <p class="ke">商家叫号后，请凭取餐号取餐</p>
            </div>
            </c:if>
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
                    <li>
                        <p>密码</p>
                        <div class="ipt">
                            <input type="text" value="" name="password" placeholder="请填密码" id="password">
                            <input type="hidden" value="" name="attach" value="${attach}">
                        </div>
                    </li>
                </ul>
                <input type="submit" value="确定" id="confirm">
            </form>
        </div>
    </div>
</div>

<script>
    var basePath = "<%=basePath%>";
    var ddid = "${ddid}";
    var  bd = "${param.bd}";
    var mealNum = "${mealNum}";
    var sccid = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    if(sccid==""){
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if (isWeixin) {
            //跳转后台微信授权
            var url = window.location.href;

            window.location.href = basePath+"/ea/wfjlogin/ea_wxlogin.jspa?redirectUrl=" + encodeURIComponent(url);
        }
    }

    if(bd=="1"){
        mealNum = "-1";
    }
    if(mealNum==null||mealNum==""){
        $("#meid").text("自动生成中请稍后...");
        ajaxMealNum();

    }
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".con").css("height",$(window).height()*0.92+"px");






    });
    function  ajaxMealNum(){

        $.ajax({
            type :"get",
            url : basePath+"/ea/wfjshop/sajax_ea_ajaxMealNum.jspa",
            async :true,
            dataType : "json",
            data:{
                ddid:ddid
            },
            success :function(data){
                var member = eval("(" + data + ")");
                var result = member.result;
                if(result==null||result==""){
                    ajaxMealNum();
                }else{
                     $("#meid").text(result);
                }

            }
        });

    }

    var nums = 60;
    var basePath = "<%=basePath%>";
    var tel = document.getElementById("tel");
    var btn = document.getElementById("gain");
    var confirm=document.getElementById("confirm");
    var password=document.getElementById("password");
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
    //手机验证格式
    tel.addEventListener("input",function () {
        var Sreg= /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(!Sreg.test(tel.value)){
            //console.log("格式不正确，不提交");
            if(tel.value.length == 11){
                alert("请输入正确格式");
                tel.value="";
                tel.focus();
            }
        }else {
            isRegister();
        }
    });
    //手机号验证
    function ver_phone() {
        var Sreg= /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
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
    //验证码失去焦点验证
    valnum.addEventListener("blur",verCode);
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

    // 判断手机号是否注册
    function isRegister(){
        if(tel.value != ''&& ver_phone()){
            $.ajax({
                cache : true,
                type :"POST",
                url : basePath+"/ea/android/sajax_ea_isacounnt.jspa?pahe="+tel.value,
                async :false,
                dataType : "json",
                success :function(data){
                    var member = eval("(" + data + ")");
                    if(member.result==0){
                        d=1;
                        console.log("未注册，可以使用");
                    }
                    else{
                        alert("已被注册,请更换手机号码！");
                        tel.value="";
                        tel.focus();
                        d=2;
                        return;
                    }
                }
            });
        }else{
            tel.value="";
        }
    }
    //点击注册
    confirm.addEventListener("click",function () {
        if(tel.value ==""){
            alert("手机号不能为空");
            return false;
            tel.focus();
        }
        if(!ver_phone()){
            return false;
            tel.focus();
        }
        if(!ver_password()){
            return false;

        }
        $("#myform").attr("action",basePath+"ea/assicode/ea_PurchaseSuccess.jspa?mealNum="+mealNum);
        $("#myform").submit();
    })
    //密码
    function ver_password() {

        var flag = false;
        var message = "";

        if(password.value == ''){
            alert("请输入密码！");
            return false;
        }else if(password.value.length<6){
            alert("密码长度不安全");
            return false;
        }else{
            return true;
        }
    }
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