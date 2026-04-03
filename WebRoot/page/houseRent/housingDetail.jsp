<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>

    <meta charset="UTF-8">
    <title>房源详情</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/hqinmanage.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">


</head>


<body>

<div class="pc-box">
    <div class="div-box">

        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    房源详情
                </li>
                <li>

                </li>
            </ul>
        </header>

        <div id="myCarousel" class="carousel slide">
            <!-- 轮播（Carousel）指标 -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
            </ol>
            <!-- 轮播（Carousel）项目 -->
            <div class="carousel-inner">
                <div class="item active">
                    <img style="width: 100%" src="/images/houseRent/1.jpg" alt="First slide">
                </div>
                <div class="item">
                    <img style="width: 100%" src="/images/houseRent/2.jpg" alt="Second slide">
                </div>
                <div class="item">
                    <img style="width: 100%" src="/images/houseRent/3.jpg" alt="Third slide">
                </div>
            </div>
            <!-- 轮播（Carousel）导航 -->
            <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>

        <h4>西河地铁口骡马市市二医院万象城成都大学青龙湖华新职业学院</h4>

        <h3 style="color: orangered">1800元/月</h3>


        <div class="row" style="text-align: center;margin-top: 10px;background-color: rgb(249,249,249);padding: 20px;">
            <div class="col-xs-3">
                <h5>一室一厅</h5>
                <p class="typeTxt">房型</p>
            </div>
            <div class="col-xs-3">
                <h5> 50平米</h5>
                <p class="typeTxt">面积</p>
            </div>
            <div class="col-xs-3">
                <h5> 12楼</h5>
                <p class="typeTxt">楼层</p>
            </div>
            <div class="col-xs-3">
                <h5> 东</h5>
                <p class="typeTxt">朝向</p>
            </div>
        </div>
    </div>
</div>

<script src="/js/jQuery1.12.4.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>

</body>

<style>
    .content {
        padding: 6px;
    }
    .carousel .item {
        height: 400px;
        /*background-color: #777;*/
    }
    .carousel-inner > .item > img {
        /*position: absolute;*/
        /*top: 0;*/
        /*left: 0;*/
        /*min-width: 100%;*/
        height: 400px;
    }
    .typeTxt {
        color: #888;
    }
</style>

<script type="text/javascript">
    var pageNumber = 1;
    var pageCount;
    var basePath = "<%=basePath%>";
    var htl;
    var j = 1;
    $(function () {
        // $("#dateShow").text(getDate());

        $('.houseType a').click(function () {
            var getTxt = $(this).text();
            // $('.houseType button').text(getTxt);
            $('.houseType button').html("" + getTxt + " <span class='caret'></span>");
        });


        $('.orientation a').click(function () {
            var getTxt = $(this).text();
            // $('.houseType button').text(getTxt);
            $('.orientation button').html("" + getTxt + " <span class='caret'></span>");
        });

        $('.feature a').click(function () {
            var getTxt = $(this).text();
            // $('.houseType button').text(getTxt);
            $('.feature button').html("" + getTxt + " <span class='caret'></span>");
        });

    })

</script>
</html>
