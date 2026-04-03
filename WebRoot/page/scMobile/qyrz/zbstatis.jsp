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
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/zbstatis.css"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/scMobile/qyrz/zbstatis.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=358dde761a483ba6780ee510803f6108"></script><!--高德地图API-->
    <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>
    <script src="<%=basePath%>js/Mdate/iScroll.js"></script>
    <script src="<%=basePath%>js/Mdate/Mdate.js"></script>
    <link rel="stylesheet" href="<%=basePath%>js/Mdate/needcss/Mdate.css">
    <script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>

    <script  type="text/javascript">

        var dwLnglatX = "${param.dwLnglatX}";
        var dwLnglatY = "${param.dwLnglatY}";

        var basePath = "<%=basePath%>";
        var gdcate  = "${param.gdcate}";
        var pageNumber=${pageForm ==null?0:pageForm.pageNumber};
        var pageCount=${pageForm ==null?0:pageForm.pageCount};
        var busiManagerID = "${busiManagerID}";
    </script>




    <title>周边商家</title>
</head>
<body>
<div id="allmap"></div>
<%--<header>--%>
    <%--<ul  class="clearfix">--%>
        <%--<li onclick="retAndroid()">--%>
            <%--<img src="<%=basePath%>images/scMobile/qyrz/img-1.png" >--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--周边商家--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</header>--%>
<div class="content">
    <div class="div-p-box clearfix">
        <p class="p-dz">
            <span>切换</span><span class="span-img"><img src="<%=basePath%>images/scMobile/qyrz/change.png"/></span><span class="pos-dz">定位中</span>
        </p>
        <div class="div-img-rl">
            <input type="text" id="dateSelectorOne">
            <img src="<%=basePath%>images/scMobile/qyrz/img_03.png"/>
        </div>
    </div>
    <section class="sec-search clearfix">
        <div>
            <img src="<%=basePath%>images/scMobile/qyrz/pic_03.png" >
            <input type="text" name="" id="search" value="" placeholder="搜索商家名称或电话" />
        </div>
    </section>


    <ul class="ul-nav clearfix">
        <li>
            <img src="<%=basePath%>images/scMobile/qyrz/quanbu.png" alt="" />
            <p>
                全部
            </p>
        </li>
        <li>
            <img src="<%=basePath%>images/scMobile/qyrz/cate03.png" alt="" />
            <p>
                餐饮
            </p>
        </li>
        <li>
            <img src="<%=basePath%>images/scMobile/qyrz/gouwu.png" alt="" />
            <p>
                购物
            </p>
        </li>
        <li>
            <img src="<%=basePath%>images/scMobile/qyrz/yule.png" alt="" />
            <p>
               生活
            </p>
        </li>
        <li>
            <a href = "<%=basePath%>page/scMobile/qyrz/cate.jsp?dwLnglatX=116.40387397&dwLnglatY=39.91488908&head=show&tj=1">
            <img src="<%=basePath%>images/scMobile/qyrz/gengduo.png" alt="" />
            <p>
                更多
            </p>
            </a>
        </li>
    </ul>
    <section class="sec-tab clearfix">
        <section class="sec-nav">
            <ul class="clearfix">
                <li class="active">
                    周边商家已认领 <span class="rlnum">${pageForm.recordCount}</span>
                </li>
                <li>
                    周边商家未认领
                </li>
            </ul>
        </section>
    </section>
    <ul class="ul-tab clearfix" style="display: none;">
        <li class="active">
            已发信息商家
        </li>
        <li>
            未发信息商家
        </li>
        <li class="bh" style="display: none;">
            自动拨号
        </li>
    </ul>
    <ul class="ul-list ul-list1" id="complete">
        <%--<li class="clearfix">--%>
            <%--<div class="div-img">--%>
                <%--<img src="<%=basePath%>images/scMobile/qyrz/pic_09.png" >--%>
            <%--</div>--%>
            <%--<div class="div-txt">--%>
                <%--<p>怪兽充电（安然之安纳米汗蒸馆...</p>--%>
                <%--<p>东城区东华门大街66号(故宫东门旁)</p>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li class="clearfix">--%>
            <%--<div class="div-img">--%>
                <%--<img src="<%=basePath%>images/scMobile/qyrz/pic_12.png" >--%>
            <%--</div>--%>
            <%--<div class="div-txt">--%>
                <%--<p>安然纳米汗蒸馆（凤翔影店）</p>--%>
                <%--<p>东城区东华门大街66号(故宫东门旁)</p>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li class="clearfix">--%>
            <%--<div class="div-img">--%>
                <%--<img src="<%=basePath%>images/scMobile/qyrz/pic_14.png" >--%>
            <%--</div>--%>
            <%--<div class="div-txt">--%>
                <%--<p>厨语</p>--%>
                <%--<p>东城区东华门大街66号(故宫东门旁)</p>--%>
            <%--</div>--%>
        <%--</li>--%>
            <c:forEach items="${pageForm.list}" var="item" varStatus="state">

                <li id="${item[0]}" class="clearfix <c:if test="${state.index+1 eq fn:length(pageForm.list)}"> last</c:if>">
                    <div class="div-img">
                        <img src="<%=basePath%>${item[2]}" onerror="this.src='<%=basePath%>images/ea/production/forum/reportAnError.png'" >
                    </div>
                    <div class="div-txt">
                        <p>${item[1]}</p>
                        <p>${item[3]}</p>
                    </div>
                </li>


            </c:forEach>

    </ul>
    <%--已发送--%>
    <ul class="ul-list ul-list2" style="display: none;">

        <%--<li class="clearfix">--%>
            <%--<div class="div-img">--%>
                <%--<img src="<%=basePath%>images/scMobile/qyrz/pic_12.png" >--%>
            <%--</div>--%>
            <%--<div class="div-txt">--%>
                <%--<p>安然纳米汗蒸馆（凤翔影店）</p>--%>
                <%--<p>东城区东华门大街66号(故宫东门旁)</p>--%>
            <%--</div>--%>
            <%--<div class="div-right">--%>
                <%--<p>请认领</p>--%>
                <%--<p>845km</p>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li class="clearfix">--%>
            <%--<div class="div-img">--%>
                <%--<img src="<%=basePath%>images/scMobile/qyrz/pic_14.png" >--%>
            <%--</div>--%>
            <%--<div class="div-txt">--%>
                <%--<p>厨语</p>--%>
                <%--<p>东城区东华门大街66号(故宫东门旁)</p>--%>
            <%--</div>--%>
            <%--<div class="div-right">--%>
                <%--<p>请认领</p>--%>
                <%--<p>85km</p>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li class="clearfix">--%>
            <%--<div class="div-img">--%>
                <%--<img src="<%=basePath%>images/scMobile/qyrz/pic_16.png" >--%>
            <%--</div>--%>
            <%--<div class="div-txt">--%>
                <%--<p>红星美凯龙写字楼C栋</p>--%>
                <%--<p>东城区东华门大街66号(故宫东门旁)</p>--%>
            <%--</div>--%>
            <%--<div class="div-right">--%>
                <%--<p>请认领</p>--%>
                <%--<p>875km</p>--%>
            <%--</div>--%>
        <%--</li>--%>
    </ul>
    <%--未发送--%>
    <ul class="ul-list ul-list3">


    </ul>
</div>
</body>
<script type="text/javascript">
    //日历
    var myDate = new Date();
    var day = myDate.getDate();
    var month = myDate.getMonth() + 1;
    if(myDate.getMonth() < 10) {
        month = "0" + (myDate.getMonth() + 1);
    }
    var day = myDate.getDate();
    if(myDate.getDate() < 10) {
        day = "0" + myDate.getDate();
    }
    var datew = myDate.getFullYear() + "-" + month + "-" + day;
    var datew = datew.toString();
    //data-year="2020" data-month="1" data-day="1"
    $("#dateSelectorOne").val(datew);
    $("#dateSelectorOne").attr("data-year", myDate.getFullYear());
    $("#dateSelectorOne").attr("data-month", month);
    $("#dateSelectorOne").attr("data-day", day);
    $("#dateSelectorTwo").val(datew);
    $("#dateSelectorTwo").attr("data-year", myDate.getFullYear());
    $("#dateSelectorTwo").attr("data-month", month);
    $("#dateSelectorTwo").attr("data-day", day);
    var sDate=myDate.getFullYear()-1;
    var eDate=myDate.getFullYear()+1;
    new Mdate("dateSelectorOne", {
        //"dateShowBtn"为你点击触发Mdate的id，必填项
        acceptId: "dateSelectorOne",
        //此项为你要显示选择后的日期的input，不填写默认为上一行的"dateShowBtn"
        beginYear: sDate,
        //此项为Mdate的初始年份，不填写默认为2000
        beginMonth: "1",
        //此项为Mdate的初始月份，不填写默认为1
        beginDay: "1",
        //此项为Mdate的初始日期，不填写默认为1
        endYear: eDate,
        //此项为Mdate的结束年份，不填写默认为当年
        endMonth: "12",
        //此项为Mdate的结束月份，不填写默认为当月
        endDay: "31",
        //此项为Mdate的结束日期，不填写默认为当天
        format: "-"
        //此项为Mdate需要显示的格式，可填写"/"或"-"或".",不填写默认为年月日
    })

</script>
</html>
