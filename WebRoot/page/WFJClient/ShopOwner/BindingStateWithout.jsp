<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/AppParkong.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/makeApp/BindingStateWithout.js"></script>
    <title>停车记录</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var phone = '${phone}';
        var i;
        var bol;
        var eastLongitude = "0";//东经
        var northLatitude = "0";//北纬
        var city;//所在城市;
        var token = 13;
    </script>
</head>

<body style="background:#f6f6f6;">
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
    <h1>停车收费</h1>
    <a href="###" class="head_R park_area"></a>
</header>
<div class="wrap_page park_record_wrap">
    <div class="fixed_wrap">
        <div class="park_tab_wrap clearfix">
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_parkingIsIntroduced.jspa?" class="park_tab">停车场</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_countdown.jspa?" class="park_tab">停车记录</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_viewVehicle.jspa?" class="park_tab park_tab_cur">绑定车牌</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/jinbi/ea_getwfjchongzhi.jspa?user=${tcc.account}&sccid=${tcc.sccId}&khd=0&flag=&identifying=&ccompanyId=&staffid=${tcc.staffid}" class="park_tab">充值金币</a></div>
        </div>
    </div>
    <div class="bind_wrap">
        <form name="bindForm" id="bindForm" method="post">
            <input type="submit" style="display: none" name="submit"/>
        <div class="car_bind_wrap park_list_box">
            <div class="car_bind_h clearfix">
                <span>车牌号码</span>
                <span><input type="text" class="carnum_inp bind_inp" name="carInformation.carNum"></span>
                <i>必填</i>
            </div>
            <div class="car_bind_h clearfix">
                <span>发动机号码</span>
                <span><input type="text" class="enginenum_inp bind_inp" name="carInformation.engineNum"></span>
            </div>
            <div class="ver_wrap clearfix">
                <input type="text" class="ver_inp">
                <button href="javascript:;" class="ver_btn" id="ver_btn" onclick="sendCode(this)">获取验证码</button>
            </div>
        </div>
        </form>
        <a href="javascript:void(0)" class="sure_btn" onclick="determine()">确 定</a>
        <div class="bind_rec_wrap park_list_box">
            <div class="bind_rec_tit">
                绑定记录
            </div>
            <!--js拼接-->
        </div>
    </div>
</div>
<script>
    var clock = '';
    var nums = 60;
    var btn = document.getElementById("ver_btn");

    function sendCode(thisBtn) {
        btn = thisBtn;
        btn.disabled = true; //将按钮置为不可点击
        btn.innerHTML = nums + '秒重新获取';
        clock = setInterval(doLoop, 1000); //一秒执行一次
        btn.className = "ver_btn disable_btn";
        $.ajax({
            cache : true,
            type :"POST",
            url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+phone,
            async :false,
            dataType : "json",
            success :function(data){
                var member = data;
                i = member.returna;
            }
        });
    }

    function doLoop() {
        nums--;
        if (nums > 0) {
            btn.innerHTML = nums + '秒重新获取';
        } else {
            clearInterval(clock); //清除js定时器
            btn.disabled = false;
            btn.innerHTML = '获取验证码';
            nums = 60; //重置时间
            btn.className = "ver_btn";
        }
    }

</script>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

</body>

</html>
