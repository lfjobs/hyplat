<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String companyID = request.getParameter("companyID");
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>营销部</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/market/market_index.css">

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var companyID="${param.companyID}";
</script>
</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <div class="div-header">
            5L5C管理系统
        </div>
        <ul class="ul-header clearfix">
            <li>
                <a onclick="window.history.go(-1);return false;" target="_self">
                    <img src="<%=basePath%>images/WFJClient/pc/5l5c/return.png"/>
                </a>
            </li>
            <li>
                营销
            </li>
            <li>

            </li>
        </ul>
        <%--<div class="container">
            <ul class="ul-con">
                <li class="clearfix">
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/marketdc_index.jsp">
                    <p class="p-title">市场调查管理</p>
                    <p class="p-height">
                        社会人力管理  社会往来单位  客户调 查  供应商调查  往来单位网址管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                    </div>
                    </a>
                </li>
                <li class="clearfix">
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/productfb_index.jsp">
                    <p class="p-title">产品发布设计</p>
                    <p class="p-height">
                        营销处产品设计  招聘信息发布  已发布产品信息汇总  投资设备绑定设置  业务佣金百分比设置  零售产品佣金设计  批发产品佣金设计  VIP产品佣金设计  普通活动产品佣金设计  特价活动产品佣金设计  网络推广  户外广告媒体宣传  会议宣传
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                    </div>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/customerzx_index.jsp">
                    <p class="p-title">客户咨询管理</p>
                    <p class="p-height">
                        单位客户咨询管理  个人客户咨询管理客户注册单位  内部企业注册单位  客户咨询内容  项目咨询  项目咨询汇总购物卡管理  生产管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                    </div>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/cjproduct_index.jsp">
                    <p class="p-title">成交产品服务</p>
                    <p class="p-height">
                        客户建档管理  成交客户网络服务  教务生产跟踪服务  营销合同流转  营销合同查询  企业商城  会员中心
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                    </div>
                    </a>
                </li>
                <li class="clearfix">
               <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/gzproduct_index.jsp">

                    <p class="p-title">跟踪产品客户服务</p>
                    <p class="p-height">
                        跟踪服务  问题解决  增值服务  成交增值  投诉处理  内部纠纷  外部纠纷售后跟踪客户网络服务
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                    </div>
                    </a>
                </li>
                <!--<li class="clearfix">
                    <p class="p-title">销售报表</p>
                    <p class="p-height">
                        毛利润报表  商品销售成本报表   销售 订单报表  销售收入报表毛利润报表  商品销售成本报表
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                    </div>
                </li>-->
            </ul>
        </div>--%>
        <%--<div class="container">
            <ul class="ul-con">
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/marketdc_index.jsp">
                        <p class="p-1">调查项目</p>
                        <p class="p-2">调查市场</p>
                        <p class="p-2">商品管理</p>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/productfb_index.jsp">
                        <p class="p-1">团队营销</p>
                        <p class="p-2">团队分工</p>
                        <p class="p-2">团队分类</p>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/cjproduct_index.jsp">
                        <p class="p-1">推广方式</p>
                        <p class="p-2">线上方式</p>
                        <p class="p-2">线下方式</p>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/customerzx_index.jsp">
                        <p class="p-1">客户成交</p>
                        <p class="p-2">客户管理</p>
                        <p class="p-2">成交管理</p>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/gzproduct_index.jsp">
                        <p class="p-1">售后服务</p>
                        <p class="p-2">客户实施</p>
                        <p class="p-2">客户服务</p>
                        <p class="p-2">客户意见</p>
                    </a>
                </li>
                <!--<li class="clearfix">
                    <p class="p-title">销售报表</p>
                    <p class="p-height">
                        毛利润报表  商品销售成本报表   销售 订单报表  销售收入报表毛利润报表  商品销售成本报表
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/more.png"/>
                    </div>
                </li>-->
            </ul>
        </div>--%>
        <div class="container">
            <ul class="ul-con">
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/marketdc_index.jsp?companyID=${param.companyID}">
                        <p class="p-1">调查项目</p>
                        <ul class="ul-con2">
                            <li class="clearfix">
                                <p class="p-2" style="line-height: 3rem;">调查市场</p>
                                <a href="<%=basePath%>WebRoot/page/WFJClient/pc/5l5C/market/wldw_index.jsp?companyID=${param.companyID}">
                                    <p class="p-3">单位管理</p>
                                </a>
                                <p class="p-3">个人管理</p>
                                <p class="p-3">市场调查</p>
                                <p class="p-3">同行调查</p>
                                <p class="p-3">项目管理</p>
                            </li>
                            <li class="clearfix">
                                <p class="p-2">商品管理</p>
                                <p class="p-3">商品设计</p>
                                <p class="p-3">商品佣金</p>
                                <p class="p-3">商品上下</p>
                            </li>
                        </ul>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/productfb_index.jsp">
                        <p class="p-1">团队营销</p>
                        <p class="p-2">团队分工</p>
                        <p class="p-2">人员业绩</p>
                        <p class="p-2">团队分类</p>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/cjproduct_index.jsp">
                        <p class="p-1">推广方式</p>
                        <ul class="ul-con2">
                            <li class="clearfix">
                                <p class="p-2" style="line-height: 3rem;">线上方式</p>
                                <p class="p-3">数字地球</p>
                                <p class="p-3">微信推广</p>
                                <p class="p-3">百度推广</p>
                                <p class="p-3">抖音推广</p>
                                <p class="p-3">邮箱推广</p>
                            </li>
                            <li class="clearfix">
                                <p class="p-2">线下方式</p>
                                <p class="p-3">地面推广</p>
                                <p class="p-3">dm推广</p>
                                <p class="p-3">名片推广</p>
                                <p class="p-3">渠道推广</p>
                            </li>
                        </ul>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/customerzx_index.jsp">
                        <p class="p-1">客户成交</p>
                    <ul class="ul-con2">
                        <li class="clearfix">
                            <p class="p-2">客户管理</p>
                            <p class="p-3">代理商客户</p>
                            <p class="p-3">业务员客户</p>
                            <p class="p-3">vip客户</p>
                            <p class="p-3">普通客户</p>
                        </li>
                        <li class="clearfix">
                            <p class="p-2">粉丝管理</p>
                            <p class="p-3">商品展示</p>
                            <p class="p-3">注册粉丝</p>
                            <p class="p-3">未注册粉丝</p>
                        </li>
                        <li class="clearfix">
                                <p class="p-2">成交管理</p>
                            <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/makeOrder.jsp?companyID=<%=companyID%>">
                                <p class="p-3">成交订单</p>
                            </a>
                            <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/gatheringManage.jsp?companyID=<%=companyID%>">
                                <p class="p-3">收款管理</p>
                            </a>
                                <p class="p-3">发货管理</p>
                                <p class="p-3">交易客户</p>
                        </li>
                    </ul>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/gzproduct_index.jsp">
                        <p class="p-1">售后服务</p>
                        <p class="p-2">客户实施</p>
                        <p class="p-2">客户服务</p>
                        <p class="p-2">客户意见</p>
                    </a>
                </li>
                <li class="clearfix">
                    <a href="<%=basePath%>ea/lottery/ea_prizeActivityHomepage.jspa?">
                        <p class="p-1">发布活动</p>
                        <p class="p-2">抽奖</p>
                        <p class="p-2">签到</p>
                        <p class="p-2">会议</p>
                    </a>
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
    //计算列表高度
    $(".p-height").each(function () {
        $(this).parent().find(".p-title").css('line-height', $(this).height() / 2 + "px");
        $(this).parent().find(".div-more").css('line-height', $(this).height() + "px");
    })
</script>
</body>
</html>
