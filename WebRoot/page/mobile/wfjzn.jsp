<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <title>微分金数字地球</title>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <%--<link rel="stylesheet" href="<%=basePath%>css/WFJClient/xinzhuce/base.css">--%>
    <%--<link rel="stylesheet" href="<%=basePath%>css/WFJClient/xinzhuce/download_app.css">--%>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/xinzhuce/register_new.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/NEWjsp/xinzhuce.js"></script>
    <script>
        var basePath = "<%=basePath%>";
        var times=59;
        var i;
        var c=1;
        var d=1;
        var q=0;
        var ss = 1;
        var sccid = "${param.sccid}";
        $(function () {
            var vs = "${param.vs}";
            if(vs!=null&&vs!=""){
                sccid = "${record.sccid}";
                $("#sccid").val(sccid);
                $("#tel").val("${record.telphone}");
            }

        })

        function loadwx(){
            var winHeight = $(window).height();
            function is_weixin() {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    return true;
                } else {
                    return false;
                }
            }
            var isWeixin = is_weixin();
            if(isWeixin){
                $(".weixin-tip").css("height",winHeight);
                $(".weixin-tip").show();
            }

        }

    </script>
    <style type="text/css">
        *{margin:0; padding:0;}
        a{text-decoration: none;}
        img{max-width: 100%; height: auto;}
        .weizhi{width:200px;height:200px;}

        .weixin-tip{display: none; position: fixed; left:0; top:0; bottom:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80);  height: 100%; width: 100%; z-index: 100;}
        .weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;}
        .spans{position: relative;left:30%;color: red;}
    </style>

</head>

<body class="hy">
<div class="weixin-tip">
    <p>
        <img src="<%=basePath%>/images/contacts/loadsite/live_weixin.png" alt="微信打开"/>
    </p>
</div>

<header>
    <ul class="clearfix">
        <%--<a href="javascript: window.history.go(-1);" class="back">--%>
            <li>
                <a href="javascript: window.history.go(-1);" class="back">
                <img src="<%=basePath%>images/WFJClient/zhuce/register_return.png"/>
                </a>
            </li>
        <%--</a>--%>

        <li>
            <span class="active" data-value="c1">注册登陆认领</span><span data-value="c2">验证码认领</span>
        </li>
    </ul>
</header>

<jsp:include page="/page/mobile/gaoDePosition.jsp"/>



<div class="content">
    <form id="myform"  method="post" name="myform">
        <input type="hidden" name="weidiantype" value="${weidiantype }"/>
        <input type="hidden" name="user" value="${user}"/>
        <input type="hidden" name="ccompanyId" value="${ccompanyId}"/>
        <input type="hidden" name="sccid" value="${sccid}" id="sccid"/>
        <input type="hidden" name="identify" value="${identify}"/>
        <input type="hidden" name="vs" value="${param.vs}"/>
    <p class="p_first">
        请填写以下注册信息
    </p>
    <menu>
        <li class="c1">
            <label for="">
                <img class="lab_img_01" src="<%=basePath%>images/WFJClient/zhuce/pic_001.png"/>
            </label>
            <input type="text"  placeholder="请输入姓名" id="name"  name="staff.staffName"/>
        </li>
        <li>
            <label for="">
                <img class="lab_img_02" src="<%=basePath%>images/WFJClient/zhuce/pic_002.png"/>
            </label>
            <input type="number" maxlength="11"  placeholder="请输入手机号" id="tel" name="phones" />
        </li>
        <li>
            <label for="">
                <img class="lab_img_03" src="<%=basePath%>images/WFJClient/zhuce/pic_003.png"/>
            </label>
            <input type="number" name="" placeholder="请输入验证码" autocomplete="off" id="valnum"  />
            <p class="send_btn" onclick="sendCode(this);return false;" id="ver_btn">获取验证码</p>
        </li>
        <li class="c1">
            <label for="">
                <img  class="lab_img_04" src="<%=basePath%>images/WFJClient/zhuce/pic_004.png"/>
            </label>
            <input type="password"  placeholder="请设置密码" id="password" name="intf" />
        </li>
        <li class="c1">
            <label for="">
                <img class="lab_img_04" src="<%=basePath%>images/WFJClient/zhuce/pic_004.png"/>
            </label>
            <input type="password"  placeholder="请再次设置密码" id="confirmPassword" />
        </li>
    </menu>
    <div class="ljzc">
        <p class="submit_btn" id="submit_btn">注 册</p>
    </div>
        <input type="hidden" name="sbhk" value="<%=session.getAttribute("sbhk")%>"/>
    </form>
    <p class="p_xz">
        已注册用户   可  <a class="div_xz" href="javascript:;">直接下载</a>
    </p>
    <div class="d_xz">
        <p>
            <img class="div_xz" src="<%=basePath%>images/WFJClient/zhuce/6895_03.png"/>
        </p>
        <div class="div_i" class="clearfix">
            <%--<a href="https://a.app.qq.com/o/simple.jsp?pkgname=com.xiaofeng.androidframework&ckey=pybnRePxIn5iTwvS"><img src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/></a>--%>
             <%--<a href="http://www.impf2010.com:80/upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk" onclick="loadwx()"><img src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/></a>--%>
                <a href="https://sj.qq.com/search?q=%E6%95%B0%E5%AD%97%E5%9C%B0%E7%90%83" onclick="loadwx()"><img src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/></a>
            <a href="https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en">
                <img src="<%=basePath%>images/WFJClient/zhuce/6895_09.png"/></a>
        </div>
    </div>
</div>
<div class="div_img">
    <img src="<%=basePath%>images/WFJClient/zhuce/6895_13.png"/>
</div>
<!-- 弹窗 -->
<div id="prompt" style="width: 100%;display: none">
    <center>
        <div style="width: 70%; background: rgba(0,0,0, 0.5);">
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>
<script type="text/javascript">
    var ntoken = 0;
    var clock = '';
    var nums = 60;
    var btn = document.getElementById("ver_btn");
    var tel = document.getElementById("tel");
    var _name = document.getElementById("name");
    var valnum = document.getElementById("valnum");
    var submit_btn=document.getElementById("submit_btn");
    var confirmPassword = document.getElementById("confirmPassword");
    var password = document.getElementById("password");
    function sendCode(thisBtn) {

        var c = $("header ul li .active").attr("data-value");
        if(c=="c1") {
            if (_name.value == "") {
                prompt("姓名不能为空");
                return false;
            }
        }
        if(tel.value ==""){
            prompt("手机号不能为空");
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
            data:$('#myform').serialize(),
            dataType : "json",
            success :function(data){
                /* var member = eval("(" + data + ")");
                 i = member.returna;*/
                var member = data;
                i = member.returna;

            }
        });
        btn = thisBtn;
        btn.disabled = true; //将按钮置为不可点击
        btn.innerHTML = nums + '秒';
        clock = setInterval(doLoop, 1000); //一秒执行一次

    }
    //手机验证格式
    tel.addEventListener("input",function () {
        var Sreg= /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
        if(!Sreg.test(tel.value)){
            //console.log("格式不正确，不提交");
            if(tel.value.length == 11){
                prompt("请输入正确格式");
                tel.value="";
                tel.focus();
            }
        }else {
            isRegister();
        }
    });

    //验证码失去焦点验证
   valnum.addEventListener("blur",verCode);

    //处理浏览器输入法遮挡
    var screenH = window.innerHeight;
    window.onresize = function() {
        var t = window.innerHeight;
        console.log(t);
        console.log(screenH);
        var inp = $("input:focus")[0];
        if (t < screenH) {
            inp.scrollIntoView(false);
        }
    };
    //点击注册
    submit_btn.addEventListener("click",function () {

        var c = $("header ul li .active").attr("data-value");
        if(c=="c1"){
            if(_name.value == ""){
                prompt("姓名不能为空");
                return false;
                name.focus();
            }
        }

        if(tel.value ==""){
            prompt("手机号不能为空");
            return false;
            tel.focus();
        }
        if(!ver_phone()){
            return false;
            tel.focus();
        }
        if(!verCode()){
            return false;
            valnum.focus();
        }
        if(c=="c1") {
            if (!ver_password()) {
                return false;

            }
        }
        if(ntoken==1){
            return false;
        }
        ntoken = 1;
        $("#myform").attr("action",basePath+"/ea/wfjlogin/ea_seves.jspa");
        $("#myform").submit();
    })
     $(".div_xz").click(function(){
        if($(".div_i").is(":hidden")){
            $(".div_i").show();
        }

        /* else{
            $(".div_i").hide();
        } */
    })


    $("header ul li span").click(function () {
        $("header ul li .active").removeClass("active");

        $(this).addClass("active");
        var c  =$("header ul li .active").attr("data-value");
        document.myform.reset();
        if(c=="c2"){
            $(".c1").hide();
        }else{
            $(".c1").show();
        }


    });



</script>
</body>
</html>