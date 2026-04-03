<!DOCTYPE html>
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
<body>
<div class="company">
    <div class="left img">
        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/ico-com.png" alt="">
    </div>
    <div class="txt">
        <span>订单详情</span><a><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/right2.png" alt=""></a>
    </div>
    <%-- <c:if test="${param.fkStatus=='01'}">
    <p>待付款</p>
    </c:if>
    <c:if test="${param.fkStatus=='00'}">
    <p>待发货</p>
    </c:if>
    <c:if test="${param.fkStatus=='02'}">
    <p>待收货</p>
    </c:if>
    <c:if test="${param.fkStatus=='03'}">
    <p>用户已收货</p>
    </c:if>
    <c:if test="${param.fkStatus=='06'}">
    <p>同意退货</p>
    </c:if>
    <c:if test="${param.fkStatus=='07'}">
    <p>退货中</p>
    </c:if>
    <c:if test="${param.fkStatus=='16'}">
    <p>申请退款</p>
    </c:if>
    <c:if test="${param.fkStatus=='17'}">
    <p>同意退款</p>
    </c:if>
    <c:if test="${param.fkStatus=='18'}">
    <p>退款（退款退货）结束</p>
    </c:if> --%>
    <p>
        <c:if test="${pb.paystatus=='01'}">
            未付款
        </c:if>
        <c:if test="${pb.paystatus=='02'}">
            欠款
        </c:if>
        <c:if test="${pb.paystatus=='03'}">
            已付款
        </c:if>
        <c:if test="${pb.supplierstatus =='00'||pb.supplierstatus ==null}">
            /未拣货
        </c:if>
        <c:if test="${pb.supplierstatus =='01'}">
            /拣货中
        </c:if>
        <c:if test="${pb.supplierstatus =='02'}">
            /待发货
        </c:if>
        <c:if test="${pb.supplierstatus =='03'}">
            /待送货
        </c:if>
        <c:if test="${pb.supplierstatus =='04'}">
            /送货中
        </c:if>
        <c:if test="${pb.supplierstatus =='05'}">
            /待收货
        </c:if>
        <c:if test="${pb.supplierstatus =='06'}">
            /待检货
        </c:if>
        <c:if test="${pb.supplierstatus =='07'}">
            /待入库
        </c:if>
        <c:if test="${pb.supplierstatus =='09'}">
            /订单完成
        </c:if>
    </p>
</div>
</body>
</html>
