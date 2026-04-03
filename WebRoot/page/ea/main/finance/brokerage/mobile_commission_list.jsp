<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<base href="<%=basePath%>">

<title>移动端佣金设置</title>
<meta charset="utf-8"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/brokerage/mobile_commission_list.css">
<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.11.3.js"></script>
<%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>--%>
<script type="text/javascript" charset="utf-8"
        src="<%=basePath%>js/ea/finance/brokerage/mobile_commission_list.js"></script>

<script type="text/javascript">
    let basePath = "<%=basePath%>";
    let companyID = "${param.companyID}";
</script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li id="titleName">
            佣金设置
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="container" id="all_order">
    <div class="pos-top">
        <div class="box-fh">
            <ul class="ul-tab ul-tab-fh clearfix">
                <li id="retail-li">
                    <p>零售商品</p>
                </li>
                <li id="wholesale-li">
                    <p>批发商品</p>
                </li>
                <li id="activity-li">
                    <p>活动商品</p>
                </li>
                <li id="special-li">
                    <p>特价商品</p>
                </li>
                <li id="vip-li">
                    <p>VIP商品</p>
                </li>
            </ul>
            <ul class="ul-tab2 ul-tab-fh clearfix">
                <li id="all-li" class="all-li">
                    <p>全部</p>
                </li>
                <li id="agent-li">
                    <p>代理佣金</p>
                </li>
                <li id="service-li">
                    <p>业务佣金</p>
                </li>
                <li id="expend-li">
                    <p>红包佣金</p>
                </li>
                <li class="all-li">
                    <p>已审</p>
                </li>
                <li class="all-li">
                    <p>未审</p>
                </li>
            </ul>
        </div>
        <div class="box-fh2">
            <div class="content">
                <section class="sec-nav sec-hide">
                    <!--sec-hide-->
                    <ul class="clearfix">
                        <li class="clearfix ">
                            <p class="add-p"><img src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>添加</p>
                        </li>
                        <li class="clearfix ">
                            <p class="update-p"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>修改</p>
                        </li>
                        <li class="clearfix ">
                            <p class="examine-p"><img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png"/>审核
                            </p>
                        </li>
                        <li class="clearfix">
                            <p class="view-p"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
                        </li>
                        <li class="clearfix">
                            <p class="query-p"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>查询</p>
                        </li>
                        <li class="clearfix">
                            <p class="print-p"><img src="<%=basePath%>images/ea/websitemall/card/eco_06.png"/>打印</p>
                        </li>
                    </ul>
                </section>
                <section class="sec-list" id="pc-sec">
                    <ul class="ul"></ul>
                </section>
            </div>
            <!--表单提示-->
            <div class="div-tingyong">
                <div class="box">
                    <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png"
                                alt=""/></p>
                    <div class="div-box">
                        <p class="tittle-p"></p>
                        <div class="clearfix">
                            <p class="right close-tingyong">确定</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="div-chaxun">
                <div class="div-box">
                    <p class="p-top">
                        请输入查询信息
                    </p>
                    <p class="p-inp clearfix">
                        <label>商品条码</label>
                        <input type="number" placeholder="请填写商品条码" name="ln" id="ln" min="1"
                               oninput="this.value=this.value.replace(/\D/g);" value="${ln}"/>
                    </p>
                    <p class="p-inp clearfix">
                        <label>商品名称</label>
                        <input type="text" placeholder="请填写商品名称" name="staffName" id="staffName" value="${staffName}"/>
                    </p>
                    <p class="p-bottom">
                        查询
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

