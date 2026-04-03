<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/base.css"/>
    <link rel="stylesheet/less" type="text/css" href="<%=basePath%>css/scMobile/qyrz/zb.less" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/zb.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>page/scMobile/qyrz/package/css/swiper.css"/>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/zhaopin.css" />
    <link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/my_css.css" />

    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
    <script src="<%=basePath%>page/scMobile/qyrz/package/js/swiper.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=358dde761a483ba6780ee510803f6108"></script><!--高德地图API-->
    <script type="text/javascript" src="<%=basePath%>js/scMobile/qyrz/gdLocation.js"></script>
    <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>
    <script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var cate = "${param.cate}";
        var  dwLnglatX = "${param.dwLnglatX}";
        var  dwLnglatY = "${param.dwLnglatY}";

        //定位商家和未定位商家
        $(document).on("click",".center-block li",function(){
            myLocation($(this));
            if($(this).index()==0){
                //alert('定位商家');
            }
            if($(this).index()==1){
                //alert('未定位商家');
            }
        })
       /* var tabs = function(tab, con){
            tab.click(function() {
                var indx = tab.index(this);
                tab.removeClass('current');
                $(this).addClass('current');
                con.hide();
                con.eq(indx).show();
                var select = $(".current").find("p").text();
                if (select == "定位商家") {
                   alert(定位商家)
                } else {
                    alert(未定位商家)
                }
                pagenumber = 1;
            });
        };
        tabs($(".zhao_nav li"), $('.zhao_main ul'));*/

        //已认领商家和未认领商家
        $(document).on("click",".sec-02 li",function(){
            my($(this));
            if($(this).index()==0){
                getPOIs(type);
            }
            if($(this).index()==1){
                getPOIs(type);
            }
        })

        //tab切换2
        function my(m){
            $(m).parent().find("li").removeClass("active");
            $(m).addClass("active");
        }
        function myLocation(m){
            $(m).parent().find("li").removeClass("current");
            $(m).addClass("current");
        }
    </script>
    <title>周边经济</title>
</head>
<body>
<div id="btn_gwc" style=" opacity: 1;display:none;">
    <p></p>
</div>
<div id="allmap"></div>

<%--<header class="head" style="display: none;">--%>
    <%--<ul  class="clearfix">--%>
        <%--<li onclick="javascript: window.history.go(-1);return false;">--%>
           <%--<img src="<%=basePath%>images/scMobile/qyrz/img-1.png" >--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--周边经济--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</header>--%>
<nav class="zhao_nav">
    <ul class="center-block">
        <li class="nav_zhao pull-left col-xs-6 text-center current">
            <p>定位商家</p>
        </li>
        <li class="nav_zhao pull-left col-xs-6 text-center">
            <p>未定位商家</p>
        </li>
    </ul>
</nav>
<div class="content">
    <section class="sec-search">
        <div>
                <img src="<%=basePath%>images/scMobile/qyrz/pic_03.png">
                <input type="search" name="" id="ttsw_search" value="" placeholder="搜索" />


        </div>
    </section>
    <div class="swiper-container">
        <div class="swiper-wrapper" id="news">
        </div>
        <!-- 如果需要分页器 -->
        <div class="swiper-pagination"></div>
    </div>
    <!--新增模块-->
    <section class="sec-list">
        <ul class="clearfix">

            <li onclick="getPOIs('餐饮服务')">
                <p>
                    <img src="<%=basePath%>images/scMobile/qyrz/chuxing.png"/>
                </p>
                <p o>
                    餐饮
                </p>
            </li>
            <li onclick="getPOIs('购物服务')">
                <p>
                    <img src="<%=basePath%>images/scMobile/qyrz/gouwu.png"/>
                </p>
                <p >
                    购物
                </p>
            </li>
            <li onclick="getPOIs('生活服务')">
                <p>
                    <img src="<%=basePath%>images/scMobile/qyrz/yule.png"/>
                </p>
                <p>
                    生活
                </p>
            </li>
            <li onclick="getPOIs('体育休闲服务')">
                <p>
                    <img src="<%=basePath%>images/scMobile/qyrz/jiudian.png"/>
                </p>
                <p>
                    体育休闲
                </p>
            </li>
            <li class="morre">
                <p>
                    <img src="<%=basePath%>images/scMobile/qyrz/gengduo.png"/>
                </p>
                <p>
                    更多
                </p>
            </li>
        </ul>
    </section>
    <div class="div-tab">
        <div class="div-01">
            <section class="sec-02">
                <ul class="clearfix">
                    <li name="claim" style="font-size: 0.7rem">
                        已认领商家
                    </li>
                    <li name="notClaim" class="active" style="font-size: 0.7rem">
                        未认领商家
                    </li>
                </ul>
            </section>
        </div>
    </div>
    <ul class="ul-list" id="ttsw_zbsj_list">
    </ul>
</div>

</body>

</html>

