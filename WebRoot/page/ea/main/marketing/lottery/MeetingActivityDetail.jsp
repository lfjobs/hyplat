<!DOCTYPE html>
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
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>活动详情</title>
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/bootstrap.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/MeetingActivityList.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/qrshare/qr_share.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
</head>
<body>

<header>
    <ul>
        <li style="width: 13%;">
            <a href="<%=basePath%>page/ea/main/marketing/lottery/MeetingActivityList.jsp"><img src="<%=basePath%>images/ea/lottery/left_jt.png"></a>
        </li>
        <li style="width: 74%;">活动详情</li>
        <li style="width: 13%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <img src="<%=basePath%>${md[1] }" width="100%">
        <div class="det_con">
            <div class="det_con_had">
                <h3>${md[2] }</h3>
                <%--<span>浏览7350次</span><span>收藏789次</span>--%>
            </div>
            <hr style="border-top: 1px solid #ddd;margin: 0;">
            <div class="det_con_mil">
                <h3><img src="<%=basePath%>images/ea/lottery/time.png">${md[6] }<span>-${md[7] }</span></h3>
                <hr style="border-top: 1px solid #ddd;width: 87%;float: right;margin: 0;clear: both;">
                <h3><img src="<%=basePath%>images/ea/lottery/location.png"><span>${md[3] }</span><img src="<%=basePath%>images/ea/lottery/right.png" class="right_j"></h3>
                <hr style="border-top: 1px solid #ddd;width: 87%;float: right;margin: 0;clear: both;">
                <h3 id="ticket"><img src="<%=basePath%>images/ea/lottery/money.png"><span>${md[8] }</span><img src="<%=basePath%>images/ea/lottery/right.png" class="right_j"></h3>
                <hr style="border-top: 1px solid #ddd;width: 87%;float: right;margin: 0;clear: both;">
                <h3><img src="<%=basePath%>images/ea/lottery/contact.png"><span>联系主办方</span><img src="<%=basePath%>images/ea/lottery/right.png" class="right_j"></h3>
            </div>
            <hr style="border-top: 10px solid #ddd;width: 100%;margin: 0;">
            <div class="det_con_mil det_con_mil2">
                <h3><div><img src="<%=basePath%>images/ea/lottery/det_head.png"></div>${md[4] }<span>主办</span></h3>
            </div>
            <hr style="border-top: 10px solid #ddd;width: 100%;margin: 0;">
            <div  class="details">
                ${md[5] }
            </div>
           <%-- <div class="details">
                <p>十个行业，十个专家，这将是今年最全面的新媒体基于盘点2016年，内容创业进入深水区，泛资讯类内容严重饱和，不但竞争激烈，而且已经无利可图，另一方面，垂直、细分行业的吸金能力和资本关注度已经被诸多事实认证，呈露三代核电撒</p>
            </div>
            <div class="more">
                <h3>更多详情<img src="<%=basePath%>images/ea/lottery/right.png"></h3>
            </div>
            <hr style="border-top: 10px solid #ddd;width: 100%;margin: 0;">
            <div class="interest">
                <h3>你可能感兴趣</h3>
            </div>
            <div class="det_con_mil3">
                <div class=" det_con_mil3_d">
                    <div class="china_mil">
                        <a href="#;">
                            <img src="<%=basePath%>images/ea/lottery/news1.jpg" alt="">
                            <div class="china_mil_txt">
                                <h4>新三版上市，到底该何去何从，内容创业：2016年中巡礼+“新每体”圈行业深蹲盘点</h4>
                            </div>
                            <div class="clearfix"></div>
                            <p>北京朝阳 <span>07/23</span></p>
                        </a>
                    </div>
                </div>
                <div class=" det_con_mil3_d">
                    <div class="china_mil">
                        <a href="#;">
                            <img src="<%=basePath%>images/ea/lottery/news2.jpg" alt="">
                            <div class="china_mil_txt">
                                <h4>新三版上市，到底该何去何从，内容创业：2016年中巡礼+“新每体”圈行业深蹲盘点</h4>
                            </div>
                            <div class="clearfix"></div>
                            <p>北京朝阳 <span>07/23</span></p>
                        </a>
                    </div>
                </div>
                <div class=" det_con_mil3_d">
                    <div class="china_mil">
                        <a href="#;">
                            <img src="<%=basePath%>images/ea/lottery/news1.jpg" alt="">
                            <div class="china_mil_txt">
                                <h4>新三版上市，到底该何去何从，内容创业：2016年中巡礼+“新每体”圈行业深蹲盘点</h4>
                            </div>
                            <div class="clearfix"></div>
                            <p>北京朝阳 <span>07/23</span></p>
                        </a>
                    </div>
                </div>
                <div class=" det_con_mil3_d">
                    <div class="china_mil">
                        <a href="#;">
                            <img src="<%=basePath%>images/ea/lottery/news2.jpg" alt="">
                            <div class="china_mil_txt">
                                <h4>新三版上市，到底该何去何从，内容创业：2016年中巡礼+“新每体”圈行业深蹲盘点</h4>
                            </div>
                            <div class="clearfix"></div>
                            <p>北京朝阳 <span>07/23</span></p>
                        </a>
                    </div>
                </div>
                <hr style="border-top: 10px solid #ddd;width: 100%;margin: 0;">
            </div>--%>
        </div>
    </div>
</div>
<div class="det_foot">
    <div class="left">
        <div class="det_foot_left">
            <div class="img">
                <img src="<%=basePath%>images/ea/lottery/shoucang.png" class="shouc">
                <img src="<%=basePath%>images/ea/lottery/shoucang_.png" class="shouc_">
            </div>
            <p>收藏</p>
        </div>
        <div class="det_foot_right">
            <img src="<%=basePath%>images/ea/lottery/issue.png">
            <p>发布</p>
        </div>
    </div>
    <button id="baoming">立刻报名</button>
</div>
<script>
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content_hidden").attr("style",";overflow: auto;");
        $(".content").attr("style",";overflow: auto");
        $(".content").css("height",$(window).height()*0.84-1+"px");
        $(".det_foot").css("height",$(window).height()*0.08+"px");
        $(".det_foot button").css("height",$(window).height()*0.08+"px");
        $(".det_foot div .det_foot_left").css("height",$(window).height()*0.08+"px");
        $(".det_foot div .det_foot_right").css("height",$(window).height()*0.08+"px");


        $(".det_foot_left").click(function(){
            $(".det_foot_left img:nth-child(1)").toggleClass("shouc_");
            $(".det_foot_left img:nth-child(2)").toggleClass("shouc2");
        });

        $("#ticket").click(function(){
            window.location.href="<%=basePath%>ea/lottery/ea_TicketList.jspa?&activityId=${activityId}";
        })

        $("#baoming").click(function(){
            window.location.href="<%=basePath%>ea/lottery/ea_TicketList.jspa?&activityId=${activityId}";
        })

    });
</script>
<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>
