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
    <title>停车记录</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var i;
        var phone = '${tcc.account}';
        var bol;
    </script>
</head>

<body style="background:#f6f6f6;">
<header class="com_head">
    <a href="javascript:;" class="back"></a>
    <h1>停车收费</h1>
</header>
<div class="wrap_page park_record_wrap" style="padding-top: 0rem;">
    <div class="bind_wrap">
        <div class="car_bind_wrap park_list_box">
            <div class="car_bind_h clearfix">
                <span>车牌号码</span>
                <span><input type="text" class="carnum_inp bind_inp"></span>
                <i>必填</i>
            </div>
            <div class="car_bind_h clearfix">
                <span>发动机号码</span>
                <span><input type="text" class="enginenum_inp bind_inp"></span>
            </div>
            <div class="ver_wrap clearfix">
                <input type="text" class="ver_inp">
                <button href="javascript:;" class="ver_btn" id="ver_btn" onclick="sendCode(this)">获取验证码</button>
            </div>
        </div>
        <a href="javascript:void(0)" class="sure_btn" onclick="determine()">确 定</a>
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

    //实时监听
    $('.carnum_inp').bind('input propertychange', function() {
        var s = $(this).val();
        if(s.length==7){
            var url = basePath +"/ea/mappointment/sajax_ea_validation.jspa?";
            $.ajax({
                url : encodeURI(url),
                type : "post",
                data : {
                    "carInformation.carNum" : s
                },
                dataType : "json",
                async : false,
                success : function(data) {
                    var member = eval("(" + data + ")");
                    bol = member.bl;
                }
            });
        }
    });

    function determine(){
        var carNum = $(".carnum_inp").val();
        var engineNum = $(".enginenum_inp").val();
        var n = $(".ver_inp").val();
        if ($.trim(carNum)!="" && carNum.length==7){
            if(bol){
                if(n==i){
                    var url = basePath +"/ea/mappointment/sajax_ea_registeredAddVehicle.jspa?";
                    $.ajax({
                        url : encodeURI(url),
                        type : "post",
                        data : {
                            "carInformation.carNum" : carNum,
                            "carInformation.engineNum":engineNum,
                            "tcc.account":phone
                        },
                        dataType : "json",
                        async : false,
                        success : function(data) {
                            var member = eval("(" + data + ")");
                            var bl = member.bl;
                            if(bl){
                                alert("添加成功");
                                document.location.href = basePath +"/page/mobile/zcok.jsp";
                            }else{
                                alert("添加失败")
                            }
                        }
                    });
                }else{
                    alert("验证码不正确")
                }
            }else{
                alert("该车牌已被绑定")
            }
        }else{
            alert("请填写正确的车牌号")
        }
    }

</script>
</body>

</html>
