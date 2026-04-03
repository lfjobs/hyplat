<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <script type="text/javascript" src="<%=basePath%>js/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/sfshop/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/sfshop/sfm.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script src="<%=basePath%>js/restaurant/sfshop/clientShopDetail.js"></script>

    <title>客户购物车</title>
    <style>
        .radio{
            padding: 0.5rem 0;
            font-size: 0.638rem;
            overflow: hidden;
        }
        .radio label{
            overflow: hidden;
        }
        .radio label input{
            width: 0.5rem;
            height: 0.5rem;
            -webkit-appearance: radio;
            float: left;
            margin: 0.1rem;
        }
        .radio label p{
            height: 0.7rem;
            line-height: 0.7rem;
            float: left;
            padding-right: 0.5rem;
        }

    </style>

<script type="text/javascript">
    var basePath="<%=basePath%>";
    var callurl = "${callurl}";
    var token = 0;
</script>
</head>
<body>
<header class="com_head">
    <a></a>
    <h1>客户购物车</h1>

</header>
<form id="dcForm" name = "dcForm" method="post">
    <input type="submit" style="display:none;" name="submit"/>
    <input type="hidden" name="companyId" value="${companyId}"/>
    <input type="hidden" name="coID" value="${coID}"/>
    <input type="hidden" name="cater.ID" value="${clientOrder[10]}" class="cateID"/>
    <input type="hidden" name="cater.boardNo" value="${param.pl!=null?param.pl:clientOrder[2]}" class="boardNo"/>
<div class="wrap_page">
    <a href="javascript:;" class="sele_tn tn_act">
        <div class="qr_btn o_logo saomiao" style="display: inline-block;width: 1.6rem;height: 1.6rem;border-radius: 0;background-size: 1.2rem;"></div>
        <span class="o_s_name">桌号</span>
        <span class="o_tn">${param.pl!=null?param.pl:clientOrder[2]==null?"请选择":clientOrder[2]}</span>
    </a>

    <div class="menu_wrap">
        <div class="menu_part">
            <div class="menu_name">
                <img src="<%=basePath%>${staff.headimage!=null?staff.headimage:'images/WFJClient/PersonalJoining/logo@2x.png'}" class="menu_img" alt="">
                <span>${staff.staffName}</span>
            </div>

            <div class="radio">
                <label><input type="radio" value="堂食" name="eatType" checked="checked" style="-webkit-appearance: radio;"/><p>堂食</p></label>
                <label><input type="radio" value="打包" name="eatType" style="-webkit-appearance: radio;"/><p>打包</p></label>
            </div>


            <ul class="menu_box">
                <c:forEach items="${addGoodslist}" var="item">
                <li>
                    <span>${item[0]}<c:if test="${item[3]!=null&&item[3]!='默认规格'}">(${item[3]})</c:if></span>
                    <span>x${item[1]}</span>
                    <span>x${item[2]}</span>
                </li>
                </c:forEach>
            </ul>

            <div class="o_time">
                备注：
            </div>
            <p class="o_remark">
               <input type="text" name="remark" value="" placeholder="填写备注信息" style="border:none;border-bottom:1px solid #eeeeee;width:100%;"/>
            </p>
        </div>
        <c:forEach items="${clientDetaillist}" var="item">
            <div class="menu_part yid">
                <div class="menu_name">
                    <img src="<%=basePath%>${item[4]!=null?item[4]:'images/WFJClient/PersonalJoining/logo@2x.png'}" class="menu_img" alt="">
                    <span>${item[5]}</span>
                </div>
                <span>就餐形式：${item[6]}</span>
                <ul class="menu_box">
                    <c:forEach items="${clientGoodslist}" var="subitem">

                        <c:if test="${item[0] eq subitem.codID}">
                            <li>
                                <span>${subitem.goodsName}<c:if test="${subitem.standard!=null&&subitem.standard!='默认规格'}">(${subitem.standard})</c:if></span>
                                <span>x${subitem.quantity}</span>
                                <span>x${subitem.price}</span>
                            </li>
                        </c:if>


                    </c:forEach>
                </ul>
                <div class="o_time">
                    下单时间：${fn:substring(item[2],0,19)}
                </div>
                <p class="o_remark">
                    备注：${item[3]}
                </p>
            </div>
        </c:forEach>
    </div>
    <div class="menu_total clearfix">
        <div class="order_pirce">￥${money}</div>
        <div class="order_operation">

            <a href="javascript:submitOrder();" class="m_js">提交订单</a>
        </div>
    </div>

</div>
</form>
<div class="nest_page select_table">
    <div class="nest_hd">
       <%-- <a href="javascript:;" class="nest_back"></a>--%>
        <span>选择餐桌号</span>
    </div>
    <div class="nest_bd">
        <div class="table_wrap">


            <div class="table_part">
                <%--<div class="table_type">5人桌</div>--%>
                <div class="table_num clearfix">
                    <c:forEach items="${catelist}" var="item">
                    <label class="table_box">
                        <input type="radio" name="table_num_inp" class="tn_inp">
                        <span>${item.boardNo}</span>
                        <span style="display:none;" id="cateID">${item.ID}</span>
                    </label>

                    </c:forEach>
                </div>
            </div>

        </div>

    </div>
</div>

</body>
</html>