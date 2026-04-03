<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/funclayer.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/login/l_zoom.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/5l5c_index.js" type="text/javascript" charset="utf-8"></script>
</head>
<body id="">
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
                    5L5CERP大型企业版
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="container">
            <%-- <section class="clearfix">
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_02.png"/>
                </div>
                <p>
                    白静雨
                </p>
            </section> --%>
            <ul class="ul-con">
                <li class="clearfix">
                    <p class="p-title">股东会</p>
                    <p class="p-height clearfix"></p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">董事会</p>
                    <p class="p-height clearfix"></p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">监事会</p>
                    <p class="p-height clearfix"></p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">工会</p>
                    <p class="p-height clearfix"></p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">顾问会</p>
                    <p class="p-height clearfix"></p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">董事长室</p>
                    <p class="p-height clearfix"></p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">总经理室</p>
                    <p class="p-height clearfix"></p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/human/humanindex.jsp?companyID=${param.companyID}&staffID=${param.staffID}">

                    <li class="clearfix">
                        <p class="p-title">人事办</p>
                        <p class="p-height clearfix">
                            组织系统 招聘管理 入在离管理 培训管理 社会人力管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>

                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/officeindexNew.jsp?companyID=${param.companyID}&staffID=${param.staffID}">
                    <li class="clearfix">

                        <p class="p-title">办公室</p>
                        <p class="p-height">
                            规划管理 行政建设 信息管理 后勤管理 督查审批
                            <%-- <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/zlplanmanage.jsp">规划管理</a>
                            <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/xzmanage.jsp">行政管理</a>
                            <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/infomanage.jsp">信息管理</a>
                            <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/hqinmanage.jsp">后勤管理</a>
                            <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/auditmanage.jsp"> 督察（审核）管理</a>  --%>
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>

                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/finance_indexNew.jsp">
<%--                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/finance_index.jsp">--%>
                    <li class="clearfix">

                        <p class="p-title">财务办</p>
                        <p class="p-height">
<%--                            项目计划预算管理 资金申请管理 收支凭据管理 凭证管理 总账明细账--%>
                            项目设计 调价发标 收标预算 比价审批 申请账单 收付管理 项目凭据 明细凭证 总账管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>

                    </li>
                </a>

                <li class="clearfix">
                    <p class="p-title">生产办</p>
                    <p class="p-height">
                        <a href="<%=basePath%>page\WFJClient\ProductsLaunch\ProductManage.jsp?companyID=${param.companyID}&staffID=${param.staffID}">
                            生产流程
                        </a>
                            人事汇总 办公室汇总 财务汇总 生产汇总 营销汇总
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>

                <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/market_index.jsp?companyID=${param.companyID}">
                    <li class="clearfix">
                        <p class="p-title">营销办</p>
                        <p class="p-height">
                            市场调查管理 产品发布设计 客户咨询管理 成交产品服务 跟踪产品客户服务 发布活动
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>

                    </li>
                </a>
                <li class="clearfix">
                    <p class="p-title">创收办</p>
                    <p class="p-height clearfix"></p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>

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
        var pHei = $(this).parent().outerHeight() - 20;
        $(this).parent().find(".p-title").css('line-height', pHei + "px");
        $(this).parent().find(".div-more").css('line-height', pHei + 20 + "px");
    })
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".container").addClass("pc-bottom");
    }
</script>
</body>
</html>
