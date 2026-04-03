<!DOCTYPE html>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");

%>
<html>
<head>
    <title>盘库商品</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/inventory/addInventory.css">
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/Mdate/iScroll.js" type="text/javascript" charset="utf-8"></script>
    <%--<script src="<%=basePath %>js/scMobile/payBudget/inventory/Mdate/Mdate.js" type="text/javascript" charset="utf-8"></script>--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/inventory/Mdate/needcss/Mdate.css">
    <style>
        .content {
            background: url(<%=basePath %>images/scMobile/payBudget/inventory/bg_01.png) no-repeat left top;
            background-size: 100%
        }
    </style>
    <script>
        var basePath = "<%=basePath %>";
        var pagecount;//总页面数（选择往来页分也用）
        var count;//总条数（选择往来页分也用）
        var pageSize;//每页多少条（选择往来页分也用）
        var pagenumber;//第几页（选择往来页分也用）
        var timer;//接收定时器用
    </script>
</head>
<body class="hy">
<%--往来公司（商家）--%>
<div class="div-name div_wlgs_name">
    <div>
        <section class="clearfix header">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_03.png"/>
            <input type="text" id="ttsw_wlgs_search_id" class="" placeholder="请录入往来公司信息" name="" value="" />
            <input type="button" value="查询" onclick="initWlGsInfo();"/>
        </section>
        <div class="div_table" id="div_wlgs_table">
            <table border="0" cellspacing="0" cellpadding="0" id="ttsw_wlgs_tk_id">
            </table>
        </div>
        <section class="button fixed_bottom">
            <p id="p_wlgs_add">
                发布
            </p>
        </section>
    </div>
</div>
<%--往来个人--%>
<div class="div-name div_wlgr_name">
    <div>
        <section class="clearfix header">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_03.png"/>
            <input type="text" id="ttsw_wlgr_search_id" class="" placeholder="请录入往来个人信息" name="" value="" />
            <input type="button" value="查询" onclick="initWlGrInfo();"/>
        </section>
        <div class="div_table" id="div_wlgr_table">
            <table border="0" cellspacing="0" cellpadding="0" id="ttsw_wlgr_tk_id">
            </table>
        </div>
        <section class="button fixed_bottom">
            <p id="p_wlgr_add">
                发布
            </p>
        </section>
    </div>
</div>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/inventory/register_return.png"/>
        </li>
        <li>
            盘库商品
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="con_co">
        <c:if test="${empty goodBeanslist}">
            <ul>
                <li class="clearfix">
                    <p class="noData">
                        未查询到产品信息，请添加产品后在尝试
                    </p>
                </li>
            </ul>
        </c:if>
        <c:if test="${!empty goodBeanslist}">
            <c:forEach items="${goodBeanslist}" var="entity">
                <ul>
                    <li class="clearfix">
                        <p class="left_name"><span>*</span>类别</p>
                        <input type="text" id="ttsw_lb" name="addBean.goodsTypeId" placeholder="自动获取/选择确定"  value="${entity[1].typeID}" readonly/>
                    </li>
                    <li class="clearfix">
                        <p class="left_name"><span>*</span>条形码</p>
                        <input type="text" id="ttsw_txm" name="addBean.goodsBarCode" placeholder="自动获取" value="${entity[1].barCode}" readonly/>
                    </li>
                    <li class="clearfix">
                        <p class="left_name"><span>*</span>品名编号</p>
                        <input type="text" id="ttsw_pmbh" name="addBean.goodsGoodsCoding" placeholder="自动获取"  value="${entity[1].goodsCoding}" readonly/>
                    </li>
                    <li class="clearfix">
                        <p class="left_name"><span>*</span>品名名称</p>
                        <input type="text"  id="ttsw_pmmc" name="addBean.goodsGoodsName" placeholder="自动获取"  value="${entity[1].goodsName}" readonly/>
                    </li>
                    <li class="clearfix">
                        <p class="left_name"><span>*</span>规格</p>
                        <input type="text" id="ttsw_gg" name="addBean.goodsStandard" placeholder="自动获取"  value="${entity[1].standard}" readonly/>
                    </li>
                    <li class="clearfix">
                        <p class="left_name"><span>*</span>单位</p>
                        <input type="text" id="ttsw_dw" name="addBean.goodsVariableId" placeholder="自动获取"  value="${entity[1].variableID}" readonly/>
                    </li>
                    <li class="clearfix">
                        <p class="left_name"><span>*</span>库存数量</p>
                        <input type="text"  id="ttsw_kcsl" name="addBean.invInvenQuantity" placeholder="数据库有库存数自动获取" value="${entity[0].quantity}" readonly/>
                    </li>
                    <li class="clearfix">
                        <p class="left_name"><span>*</span>预算数量</p>
                        <input type="text" id="ttsw_yssl" name="addBean.budgetNumber" placeholder="请输入" value="${entity[0].tiaoQuantity}" readonly />
                    </li>
                    <li class="clearfix li_right_l">
                        <p class="left_name"><span>*</span>预算单价</p>
                        <input type="text" id="ttsw_ysdj" name="addBean.budgetUnitPrice" placeholder="请输入" value="${entity[0].price}" readonly/>
                    </li>
                    <li class="clearfix li_right_l">
                        <p class="left_name"><span>*</span>预算金额</p>
                        <input type="text" id="ttsw_ysje" name="addBean.budgetMoney" placeholder="请输入" value="${entity[0].money}" readonly/>
                    </li>
                    <li class="clearfix li_left_l">
                        <p class="left_name"><span>*</span>往来公司（商家）</p>
                        <div id="ttsw_wlgs_show">
                            <p id="ttsw_wlgs_text" class="txt" >${entity[0].ccompanyName}</p>
                        </div>
                    </li>
                    <li class="clearfix li_left_l">
                        <p class="left_name"><span>*</span>往来个人（商家责任人）</p>
                        <div id="ttsw_wlgr_show">
                            <p id="ttsw_wlgr_text" class="txt">${entity[0].connectName}</p>
                        </div>
                    </li>
                </ul>
            </c:forEach>
        </c:if>
    </section>
</div>
</body>
<script>
    //后退
    function toBack() {
        window.location.href = basePath + "ea/scBudget/ea_toDetail.jspa?showFlag=${showFlag}&departmentID=${departmentID}&cashierBillsId=${cashierBillsId}";

    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });
</script>
</html>
