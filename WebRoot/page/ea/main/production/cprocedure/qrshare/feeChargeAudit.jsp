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
    <title>停车收费标准审核</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no"/>
</head>
<body>

<div class="div-content">
    <table class="table1" cellspacing="8" cellpadding="8" align="center">
        <tr>
            <td colspan="2" align="center" style="font-weight:bold;font-size:25px;">
                停车收费标准
            </td>

        </tr>
        <tr>
            <td>
                产品名称: ${prolist[0][2]}
            </td>
            <td>
                场地: ${vf.siteName}
            </td>

        </tr>
        <tr>
            <td>
                车类型:<c:if test="${feeScale.carType eq 'c'}">教练车</c:if><c:if test="${feeScale.carType eq 'p'}">私家车</c:if>
            </td>
            <td colspan="2">
                时间单位:
                <c:choose>
                    <c:when test="${feeScale.timeUnits eq '0'}">
                       小时
                    </c:when>
                    <c:when test="${feeScale.timeUnits eq '1'}">
                      包天
                    </c:when>
                    <c:when test="${feeScale.timeUnits eq '2'}">
                        包月
                    </c:when>
                    <c:when test="${feeScale.timeUnits eq '3'}">
                       包年
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${feeScale.timeType eq '0'}">
                        <span>-当天(00点结束)</span>
                    </c:when>
                    <c:when test="${feeScale.timeType eq '24'}">
                        <span>-24小时制</span>
                    </c:when>
                    <c:when test="${feeScale.timeType eq '8'}">
                        <span>-8小时制</span>
                    </c:when>

                </c:choose>

            </td>
        </tr>
        <tr>
            <td>
                免费时长:${feeScale.feeMini}
            </td>
            <td>
                责任人:${feeScale.staffName}
            </td>
        </tr>


    </table>
    <table class="table2" cellspacing="5" cellpadding="15" align="center" border="1px"
           style="border-collapse: collapse;width:80%;">
        <tr>
            <td>系统销售价格</td>
            <td>￥${prolist[0][8]}</td>
        </tr>
        <c:if test="${dlyj!=null}">
            <c:forEach items="${dlyj}" var="dl" varStatus="idxStatus">
        <tr>
        <td>${dl[1]}佣金</td>

        <td>￥${dl[2]}</td>
        </tr>
            </c:forEach>
        </c:if>

        <tr>
            <td>业务佣金</td>
            <td>￥${prolist[0][9]}</td>
        </tr>


        <tr>
            <td>投资设备类型</td>
            <td>
                ${prolist[0][12]=='01'?'教练车':prolist[0][12]=='02'?'创客单车':prolist[0][12]=='03'?'超市':"无"}
            </td>
        </tr>
        <tr>
            <td>初始盘库库存</td>
            <td>${prolist[0][6]==null||prolist[0][6]==""?w_weight:prolist[0][6]}</td>
        </tr>
        <c:if test="${totalPct>0}">
            <tr>
                <td>加消费红包最终售价</td>
                <td>￥${prolist[0][8]*1+totalPct}(系统单价+${totalPct}%)</td>
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





