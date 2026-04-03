<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>功能</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/funclayergys.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/funclayergys.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        var  basePath = "<%=basePath%>";
         var sccId = "${sccId}";
        var staffID ="${staffID}";
        var companyID ="${companyID}";
        var user  = "${user}";


    </script>
</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    5l5CERP供应商版
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="container">

            <ul class="ul-con">
                <a>

                    <li class="clearfix">
                        <p class="p-title">人事</p>
                        <p class="p-height clearfix">
                            <span onclick="auth()">系统认证</span> <span onclick="recruit()">公司招聘</span> 入职管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>/page/WFJClient/pc/5l5C/office/documentManageNew.jsp">
                    <li class="clearfix">

                        <p class="p-title">办公</p>
                        <p class="p-height">
                          <span onclick="flow('doc')">文书流程</span>
                         <span onclick="flow('contract')">合同签约</span>
                                <span>企业简介</span>   <span>企业文化</span>    <span>企业资讯</span>   <span>小视频</span>

                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>

                        </div>
                    </li>
                </a>
                <a>
                    <li class="clearfix">

                        <p class="p-title">财务</p>
                        <p class="p-height">
                            收支管理     <span onclick="yj()">佣金管理</span>  <span onclick="backcard()">银行卡</span>
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>

                    </li>
                </a>

                <li class="clearfix">
                    <p class="p-title">生产</p>
                    <p class="p-height">
                            商品采购发布 生产库管   <span onclick="order()">订单分拣</span>
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>

                <a>
                    <li class="clearfix">


                        <p class="p-title">营销</p>
                        <p class="p-height">
                            <span>市场调查</span> <span>商品管理</span> <span>团队推广</span> <span>成交客户</span> <span>售后服务</span>
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>

                    </li>
                </a>

            </ul>
        </div>
        <div class="footer div-bottom">
            <ul class="clearfix">
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
                    </div>
                    <p>
                        消息
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
                    </div>
                    <p>
                        通讯
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
                    </div>
                    <p>
                        数字
                    </p>
                </li>
                <li class="active">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
                    </div>
                    <p>
                        5L5C
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                    </div>
                    <p>
                        我的
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    //计算中间区域宽度
    $(".p-height").each(function () {
        var pWth = $(".pc-box").width() - $(this).prev().width() - 100;
        $(this).width(pWth + "px")
    })
    //计算列表高度
    $(".p-height").each(function () {
//				console.log($(this).outerHeight())
//				console.log($(this).parent().outerHeight())
        var pHei = $(this).parent().outerHeight() - 51;
        $(this).parent().find(".p-title").css('line-height', pHei + "px");
        $(this).parent().find(".div-more").css('line-height', pHei + 50 + "px");
    })
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".container").addClass("pc-bottom");
    }
</script>
</body>
</html>
