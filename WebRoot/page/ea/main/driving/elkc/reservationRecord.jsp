<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
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
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/order_man.css">
    <script src="<%=basePath%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/reservationRecord.js"></script>
    <title>预约记录</title>
</head>

<script>
    var basePath = '<%=basePath%>';
    var http= new XMLHttpRequest();
    var t;
    var studentId = '${tbJpStudentInfo.studentId}';



    var height;
    var pagenumber = 0;
    var pagecount;


</script>


<body>
<header class="com_head">
    <a onclick="javascript: window.history.go(-1);return false;" class="back"></a>
    <h1>预约记录</h1>
</header>
<div class="wrap_page">
    <div class="rec_wrap">
        <!--js拼接-->
    </div>
</div>
<div class="overlay">
    <div class="cancel_popup">
        <div class="c_p_tit">
            <span>提示</span>
            <a href="javascript:;" class="c_p_close"></a>
        </div>
        <ul class="c_p_list">
            <li class="prompting_cname"></li>
            <li class="prompting_ytd"></li>
            <li class="prompting_hour"></li>
            <li class="prompting_subject"></li>
        </ul>
        <a href="javascript:void(0);" class="c_p_btn">
            确认取消
        </a>
    </div>
</div>
<script>
    $(function() {
        setTime();
        //点击取消弹窗
        $(document).on("click",".rec_cancel",function(e) {
            e.stopPropagation();
            e.preventDefault();
            $(".cancel_popup .prompting_cname").html($(this).attr("data-companyname"));
            var time = $(this).parent(".state_ready").find(".ready_display").html().split(" ");;
            $(".cancel_popup .prompting_ytd").html(time[0]);
            $(".cancel_popup .prompting_hour").html(time[1]);
            $(".cancel_popup .prompting_subject").html($(this).parent(".state_ready").find("i").html());
            $(".cancel_popup .c_p_btn").attr("data-etoId",$(this).parent(".state_ready").attr("data-etoId"));
            $(".overlay").addClass("active");
        })
        //关闭弹窗
        function close_popup() {
            $(".overlay").removeClass("active");
        }
        //弹窗-确认取消
        $(document).on("click",".c_p_btn",function(e) {
            close_popup();
        })
        //弹窗-关闭
        $(document).on("click",".c_p_close",function(e) {
            close_popup();
        })
    })
    setInterval("setTime()", 1000);
    //倒计时
    function setTime(){
        $(".ready_time").each(function() {
            var that = $(this);
            var sdata = that.attr("data-startTime");
            var edata = that.attr("data-endTime");
            var n_date = new Date();
            var target_date = new Date(sdata);
            var end_date = new Date(edata);
            if (n_date.getTime() < target_date.getTime()) {
                setStartTime(that, sdata);
            }else if (n_date.getTime() > target_date.getTime() && n_date.getTime()<end_date.getTime()) {
                that.html("已开始")
            }else if (n_date.getTime()>end_date.getTime()) {
                that.html("超时关闭")
            }
        })
    }
    function setStartTime(w, t) {
        var time_start = new Date().getTime(); //设定当前时间
        var time_end = new Date('' + t + '').getTime(); //设定目标时间
        // 计算时间差
        var time_distance = time_end - time_start;
        // 天
        var int_day = Math.floor(time_distance / 86400000)
        time_distance -= int_day * 86400000;
        // 时
        var int_hour = Math.floor(time_distance / 3600000)
        time_distance -= int_hour * 3600000;
        // 分
        var int_minute = Math.floor(time_distance / 60000)
        time_distance -= int_minute * 60000;
        // 秒
        var int_second = Math.floor(time_distance / 1000);
        // 时分秒为单数时、前面加零
        if (int_day < 10) {
            int_day = "0" + int_day;
        }
        if (int_hour < 10) {
            int_hour = "0" + int_hour;
        }
        if (int_minute < 10) {
            int_minute = "0" + int_minute;
        }
        if (int_second < 10) {
            int_second = "0" + int_second;
        }
        w.find(".days").text(int_day);
        w.find(".hours").text(int_hour);
        w.find(".minutes").text(int_minute);
        w.find(".seconds").text(int_second);
    }

</script>
</body>

</html>