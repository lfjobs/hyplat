<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/lottery/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/act_manger.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <title>活动管理列表</title>
</head>
<body>
    <header class="com_head">
        <a href="<%=basePath%>ea/lottery/ea_prizeActivityHomepage.jspa?companyId=${model.companyId}" class="back"></a>
        <h1>活动管理</h1>
        <s:if test="flag eq 2">
            <a href="<%=basePath%>ea/lottery/ea_meetingActivity.jspa?flag=${flag}&model.companyId=${model.companyId}" class="head_R act_add">添加</a>
        </s:if>
        <s:elseif test="flag eq 0" >
            <a href="#" onclick="addActivity()" class="head_R act_add">添加</a>
        </s:elseif>
        <s:else>
            <a href="#" class="head_R act_add" onclick="signPrizeActivity()">添加</a>
        </s:else>
    </header>
    <div class="wrap_page">
       <div class="act_list_wrap">
       </div>

    </div>
    <jsp:include page="/page/prompt.jsp"/>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var flag = '${flag}';
        var companyId = '${model.companyId}';
        var pagenumber = 0;
        var pagecount = 0;
        var t;

        jQuery(function ()
        {
            loaded();

//            //左滑动，右滑动
//            Zepto(document).on("swipeLeft",".act_list_box",function () {
//                $(this).addClass("swipe_L").siblings().removeClass("swipe_L");
//            });
//            Zepto(document).on("swipeRight",".act_list_box",function(){
//                $(this).removeClass("swipe_L");
//            })
            var count = 0; //判断用户是否第一次进行touchmove操作
            var startX, startY;
            var endX, endY;
            var distanceX, distanceY;
            $('body').on('touchstart', function(event) {
                count = 0; //每次开始点击时清零
                var e = event.originalEvent;
                startX = e.targetTouches[0].clientX;
                startY = e.targetTouches[0].clientY;
            }).on('touchmove', function(event) {
                if (count === 0) { //如果是第一次滑动
                    var e = event.originalEvent;
                    endX = e.changedTouches[0].clientX;
                    endY = e.changedTouches[0].clientY;
                    distanceX = Math.abs(startX - endX);
                    distanceY = Math.abs(startY - endY);
                    if (distanceX > distanceY) { //如果X绝对距离大于Y绝对距离
                        event.preventDefault();
                    }
                }
                count++;
            }).on('touchend', function(event) {
                var e = event.originalEvent;
                endX = e.changedTouches[0].clientX;
                endY = e.changedTouches[0].clientY;
                distanceX = Math.abs(startX - endX);
                distanceY = Math.abs(startY - endY);
                var $target = $(e.target).parents(".act_list_box");
                if (distanceX > distanceY) {
                    startX - endX > 0 ? swipeLeft($target) : swipeRight($target);
                }
            });
            //左滑
            function swipeLeft(t) {
                //console.log("左滑");
                t.addClass("swipe_L").siblings().removeClass("swipe_L");
            }
            //右滑
            function swipeRight(t) {
                //console.log("右滑");
                t.removeClass("swipe_L");
            }
            //删除
            $(document).on("click",".posi_R",function () {
                var activityId = $(this).attr("id");
                var that=$(this);
                $.ajax({
                    url : basePath + "ea/lottery/sajax_ea_delActivity.jspa?",
                    type : "post",
                    async : false,
                    dataType : "json",
                    data : {"model.activityId":activityId},
                    success : function (data) {
                        if (data == 'true')
                        {
                            prompt("删除成功！");
                            that.parent().remove();
                        }else
                        {
                            prompt("删除失败！");
                        }
                    }
                });
            });

            //编辑
            $(document).on("click",'.act_list_box',function () {
                if (!$(this).hasClass("swipe_L"))
                {
                    var activityId = $(this).find(".posi_R").attr("id");
                    if(flag == "2"){
                        document.location.href = basePath + "ea/lottery/ea_prizeActivityAdd.jspa?flag=" + flag
                            + "&model.activityId=" + activityId + "&model.companyId=" + companyId + "&sign=edit";
                    }else
                    {
                        document.location.href = basePath + "ea/lottery/ea_prizeActivityAdd.jspa?flag=" + flag
                            + "&model.activityId=" + activityId + "&model.companyId=" + companyId + "&sign=edit";
                    }
                }
            });

        });

        function getHeight()
        {
            t = setTimeout("getHeight()",200);
            if($(".last").length > 0)
            {
                if($(".last").offset().top + $(".last").height() - $("header").height() * 4 < $(window).height())
                {
                    if(pagenumber < pagecount)
                    {
                        loaded();
                    }
                }
            }
        }
        function loaded ()
        {
            clearTimeout(t);
            pagenumber++;
            $.ajax({
                url : basePath + "ea/lottery/sajax_ea_ajaxPrizeActivityList.jspa?",
                type : "post",
                async : false,
                dataType : "JSON",
                data : {
                    "pageForm.pageNumber" : pagenumber,
                    "pageForm.pageSize" : 10,
                    "flag" : flag,
                    "model.companyId" : companyId
                },
                success : function (data) {
                    var member = eval("("+data+")");
                    var pageForm = member.pageForm;
                    var str = new Array();
                    if (pageForm != null && pageForm.recordCount> 0)
                    {
                        pagenumber = pageForm.pageNumber;
                        pagecount = pageForm.pageCount;
                        var list = pageForm.list;
                        $(".last").removeClass("last");
                        for (var i = 0;i < list.length;i++)
                        {
                            var entity = list[i];
                            if(i==list.length - 1){
                                str.push('<div class="act_list_box last">');
                            }else{
                                str.push('<div class="act_list_box">');
                            };
                            str.push('<a href="javascript:;" class="posi_L clearfix">');
                            str.push('<img src="' +basePath + entity.activityImg + '" class="act_img" alt="">');
                            str.push('<div class="act_text">');
                            str.push('<div class="act_tit">'+ entity.activityName + '</div>');
                            str.push('<div class="act-state">' + timeStamp2String(entity.startingTime.time)+ '</div></div>');
                            str.push('</a><a href="javascript:;" class="posi_R" id="'+ entity.activityId + '"></a></div>');
                        }
                        $(".act_list_wrap").append(str.join(""));
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
            //return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second+"."+mseconds;
            return year + "-" + month + "-" + date;
        }
        function  reload() {
            document.location.href = basePath + "ea/lottery/ea_prizeActivityList.jspa?"
        }
        function addActivity() {
            $.ajax({
                url: basePath + "ea/lottery/sajax_ea_prizeActivity.jspa?",
                type: "post",
                async: false,
                //dataType: "JSON",
                data: {
                    "flag": flag,
                    "model.companyId": companyId
                },
                success: function (data) {
                    var member = eval("("+data+")");
                   if(member.isflag==true){
                       alert("您添加的上一个活动还没有结束，不能添加新的活动");
                   }else {
                       document.location.href = basePath + "ea/lottery/ea_prizeActivityAdd.jspa?flag=${flag}&model.companyId=${model.companyId}";
                   }
                }
            });
        }
            function signPrizeActivity() {
                $.ajax({
                    url: basePath + "ea/lottery/sajax_ea_signPrizeActivity.jspa?",
                    type: "post",
                    async: false,
                    //dataType: "JSON",
                    data: {
                        "flag": flag,
                        "model.companyId": companyId
                    },
                    success: function (data) {
                        var member = eval("("+data+")");
                        if(member.isflag==true){
                            alert("您添加的上一个活动还没有结束，不能添加新的活动");
                        }else {
                            document.location.href = basePath+"ea/lottery/ea_prizeActivityAdd.jspa?flag=${flag}&model.companyId=${model.companyId}";
                        }
                    }
                });
        }
    </script>
</body>
</html>