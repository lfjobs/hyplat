<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/lottery/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/act_draw.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <title>获奖记录</title>
</head>

<body>
    <div class="com_head">
        <a href="<%=basePath%>ea/lottery/ea_goLottery.jspa?model.activityId=${model.activityId}&ccompanyId=${ccompanyId}&selectType=${selectType}" class="back"></a>
        <h1>获奖记录</h1>
    </div>
    <div class="wrap_page" style="background:#ffe532;height:100%;padding:2.16rem 0 1rem;margin-top:0;">
        <img src="<%=basePath%>images/ea/lottery/record_img02.jpg" class="bg_img record_top" alt="">
        <div class="record_con">
            <i class="top_ico"></i>
            <i class="bottom_ico"></i>
            <img src="<%=basePath%>images/ea/lottery/con_top.png" class="bg_img con_top" alt="">
            <ul class="record_list">
            </ul>
        </div>
    </div>
</body>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var pagenumber = 0;
    var pagecount = 0;
    var manualPageTimer;
    var autoPageTimer;
    var activityId = '';
    var type = '';

    jQuery(window).load(function() {
        const urlParams = new URLSearchParams(window.location.search);
        activityId = urlParams.get('model.activityId');
        type = urlParams.get('type');

        jQuery('.record_list').empty();
        loaded()

        if("allCustomer" == type){
            autoPageTimer = setInterval(function() {autoPage();}, 5000);
        }

        var h = $(".wrap_page").height() - $(".record_top").outerHeight();
        $(".record_con").height(h);
        $(".record_list").height(h - $(".con_top").height() - 10).show();

        if("allCustomer" != type){
            jQuery(document).on("click",".record_list li",function () {
                if (jQuery(this).find("span").eq(2).text() == '未领取')
                {
                    var ppId = jQuery(this).find("#ppId").val();
                    var recordId = jQuery(this).find("#recordId").val();
                    document.location.href = basePath +  "ea/wfjshop/ea_gm.jspa?ppid=" + ppId + "&count=1&morre=0&recordId=" + recordId;
                }
            });
        }

    });

    function autoPage() {
        if(pagenumber > pagecount) {
            pagenumber = 0;
        }
        loaded();
    }

    function getHeight()
    {
        manualPageTimer = setTimeout("getHeight()",200);
        if(jQuery(".last").length > 0)
        {
            if(jQuery(".last").offset().top + jQuery(".last").height() - jQuery(".com_head").height()  < jQuery(window).height())
            {
                if(pagenumber < pagecount)
                {
                    loaded();
                }
            }
        }
    }

    function loaded () {
        if("allCustomer" != type){
            clearTimeout(manualPageTimer);
        }
        pagenumber++;
        jQuery.ajax({
            url: basePath + "ea/lottery/sajax_ea_ajaxLotteryRecord.jspa?",
            type: "post",
            async: true,
            dataType : "JSON",
            data : {
                "pageForm.pageNumber" : pagenumber,
                "pageForm.pageSize" : 12,
                "model.activityId" : activityId,
                "type" : type
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                var str = new Array();
                if (pageForm != null && pageForm.recordCount > 0) {
                    pagenumber = pageForm.pageNumber;
                    pagecount = pageForm.pageCount;
                    if(pagenumber <= pagecount){
                        if("allCustomer" == type){
                            jQuery('.record_list').empty();
                        }
                    }
                    var list = pageForm.list;
                    jQuery(".last").removeClass("last");
                    for (var i = 0; i < list.length; i++) {
                        var entity = list[i];
                        if(i == list.length - 1){
                            str.push('<li class="last">');
                        }else{
                            str.push('<li>');
                        };
                        if("allCustomer" == type){
                            str.push('<span>' + entity[5] +'</span>');
                        }
                        str.push('<span>' + entity[1] +'</span>');
                        str.push('<span>' + timeStamp2String(entity[4].time) + '</span>');
                        if("allCustomer" != type){
                            if (entity[3] == '0')
                            {
                                str.push('<span class="no_get">未领取</span>');
                            }
                            else
                            {
                                str.push('<span>已领取</span>');
                            }
                        }
                        str.push('<input type="hidden" value="' + entity[2] + '" id="ppId"/>');
                        str.push('<input type="hidden" value="' + entity[0] + '" id="recordId"/>');
                        str.push('</li>');
                    }
                }
                if("allCustomer" != type){
                    jQuery('.record_list').append(str.join(""));
                }else if(pagenumber <= pagecount){
                    jQuery('.record_list').append(str.join(""));
                }
                if("allCustomer" != type){
                    getHeight();
                }
            }
        });
    }

    //timestamp转换成datetime
    function timeStamp2String (time)
    {
        var datetime = new Date();
        datetime.setTime(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1;
        var date = datetime.getDate();
        var hour = datetime.getHours();
        var minute = datetime.getMinutes();
        var second = datetime.getSeconds();
        var mseconds = datetime.getMilliseconds();

        if(hour < 10){
            hour = "0" + hour;
        }
        if(minute < 10){
            minute = "0" + minute;
        }
        if(second < 10){
            second = "0" + second;
        }
        if("allCustomer" != type){
            return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
        }else {
            return year + "-" + month + "-" + date;
        }
    }
</script>

</html>
