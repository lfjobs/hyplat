<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>佣金设置盖章</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no"/>
</head>
<body>
<div class="div-content">
    <table class="table1" cellspacing="8" cellpadding="8" align="center">
        <tr>
            <td colspan="2" align="center" style="font-weight:bold;font-size:25px;">
                ${pRetail[6]}
                <c:choose>
                    <c:when test="${yjtype=='wholesale-li'}">
                        批发价
                    </c:when>
                    <c:when test="${yjtype=='activity-li'}">
                        活动价
                    </c:when>
                    <c:when test="${yjtype=='special-li'}">
                        特价
                    </c:when>
                    <c:when test="${yjtype=='vip-li'}">
                        VIP价
                    </c:when>
                    <c:otherwise>
                        零售价
                    </c:otherwise>
                </c:choose>设置
            </td>

        </tr>
        <tr>
            <td>
                产品条码: ${pRetail[7]}
            </td>
            <td>
                产品名称:${pRetail[6]}
            </td>
        </tr>
        <tr>
            <td>
                添加人员:${pRetail[8]}
            </td>
            <td>
                添加时间: ${pRetail[10]}
            </td>
        </tr>
        <tr>
            <td colspan="2">
                公司:${pRetail[9]}
            </td>
        </tr>
    </table>
    <table class="table2" cellspacing="5" cellpadding="15" align="center" border="1px"
           style="border-collapse: collapse;">
        <tr>
            <td>成本价</td>
            <td>￥${pRetail[2]}</td>
        </tr>
        <c:forEach items="${beanList}" var="p">
            <tr>
                <td>${p[3]}</td>
                <td>￥${p[1]}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>业务佣金</td>
            <td>￥${pRetail[3]}</td>
        </tr>
        <tr>
            <td>投资设备类型</td>
            <td>
                <c:choose>
                    <c:when test="${pRetail[4]=='01'}">
                        教练
                    </c:when>
                    <c:when test="${pRetail[4]=='02'}">
                        创客单车
                    </c:when>
                    <c:when test="${pRetail[4]=='03'}">
                        超市
                    </c:when>
                    <c:otherwise>
                        无
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>系统单价</td>
            <td>￥${pRetail[1]}</td>
        </tr>
        <c:if test="${totalPct>0}">
            <tr>
                <td>加消费红包最终售价</td>
                <td>￥${pRetail[1]*1+totalPct}(系统单价+${totalPct}%)</td>
            </tr>
        </c:if>
    </table>

</div>
</body>
</html>
<style>

    html {
        font-size: 25px;

    }
</style>





