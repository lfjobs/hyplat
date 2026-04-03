<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>


<body>


<%-- <div class="tjlh">
<!-- 01待付款 -->
<c:if test="${param.fkStatus=='01'}">
<a href="#;"><input type="button" value="联系买家"></a>

</c:if>
<!-- 00代发货状态 -->
<c:if test="${param.fkStatus=='00'}">
<a href="#;"><input type="button" value="联系买家"></a>
<a onclick="shareAddress(this)"><input type="button" value="更换地址"></a>
<!-- <a ><input type="button" value="发货" onclick="cheg(this)"></a> -->
</c:if>
<!-- 02代收货状态 -->
<c:if test="${param.fkStatus=='02'}">
<a href="#;"><input type="button" value="联系买家"></a>
<a href="#;"><input type="button" value="查看物流" onclick="wuliu('this')"></a>
</c:if>
<!-- 05申请退货 -->
<c:if test="${param.fkStatus=='05'}"> 
<a onclick="orderReturn(this)"><input type="button" value="同意退货"></a>
<a href="#;"><input type="button" value="拒绝退货" ></a>
</c:if>
<!-- 16申请退款 -->
<c:if test="${param.fkStatus=='16'}"> 
<a onclick="orderReturn(this)"><input type="button" value="同意退款"></a>
<a href="#;"><input type="button" value="拒绝退款" ></a>
            
</c:if>
</div> --%>
<div class="tjlh">

    <div class="clearfix">
        <c:if test="${(pb.paystatus=='02'||pb.paystatus=='03')&&(pb.fkStatus=='01'||pb.fkStatus=='00')}">
            <c:if test="${pb.supplierstatus=='00'||pb.supplierstatus==null}">
                <p onclick="delivery(this)">
                    提交拣货
                </p>
            </c:if>
        </c:if>
        <c:if test="${pb.fkStatus=='01'}">
            <p>联系买家</p>
        </c:if>
        <!-- 00代发货状态 -->
        <c:if test="${pb.fkStatus=='00'}">
            <p>联系买家</p>
            <p onclick="shareAddress(this)">更改地址</p>
        </c:if>
        <!-- 02代收货状态 -->
        <c:if test="${pb.fkStatus=='02'}">
            <p>联系买家</p>
            <p onclick="wuliu(this)">查看物流</p>
        </c:if>
        <!-- 05申请退货 -->
        <c:if test="${pb.fkStatus=='05'}">
            <p onclick="orderReturn(this)">同意退货</p>
            <p>拒绝退货</p>
        </c:if>
        <!-- 16申请退款 -->
        <c:if test="${pb.fkStatus=='16'}">
            <p onclick="orderReturn(this)">同意退款</p>
            <p>拒绝退款</p>
        </c:if>
        <!-- 付款 -->
        <c:if test="${pb.fkStatus=='16'}">
            <p onclick="orderReturn(this)">同意退款</p>
            <p>拒绝退款</p>
        </c:if>
    </div>
</div>
</body>
</html>
