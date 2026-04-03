<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/8 0008
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/customerCashingAudit.css">
</head>
<body>
<div>
    <h2>
        客户兑现审核
    </h2>
    <section>
        <menu>
            <input type="hidden"  value="${wday.wdaID}" id="wdaID">
            <li>
                币种：<span>${wday.currency}</span>
            </li>
            <li>
                日期：<span><fmt:formatDate value="${wday.applyDate}"></fmt:formatDate></span>
            </li>
            <li>
                明细标志：<span>${wday.detailmark}</span>
            </li>
            <li>
                顺序号：<span>${wday.orderNum}</span>
            </li>
        </menu>
    </section>
    <div class="xian"></div>
    <section>
        <menu>
            <li>
                付款账号开户行：
                <span>${wday.payOpenAccountBank}</span>
            </li>
            <li>
                付款账号/卡号：
                <span>${wday.payCardAccount}</span>
            </li>
            <li>
                付款账号名称/卡名字：
                <span>${wday.payCardName}</span>
            </li>
            <li>
                收款账号开户行：
                <span>${wday.recevOpenAccountBank}</span>
            </li>
            <li>
                收款账号省份：
                <span>${wday.recevCardProvince}</span>
            </li>
            <li>
                收款账号地市：
                <span>${wday.recevCardCity}</span>
            </li>
            <li>
                收款账号地区码：
                <span>${wday.receCardDCode}</span>
            </li>
            <li>
                收款账号：
                <span>${wday.recevCardAccount}</span>
            </li>
            <li>
                收款账号名称：
                <span>${wday.recevCardName}</span>
            </li>
            <li>
                金额：
                <span class="color_red">${wday.money}元</span>
            </li>
            <li>
                汇款用途：
                <span>${wday.payurpose}</span>
            </li>
            <li>
                备注信息：
                <span>${wday.remark}</span>
            </li>
            <li>
                汇款方式：
                <span>${wday.payMode}</span>
            </li>
            <li>
                收款账户短信通知手机号码：
                <span>${wday.recevTel}</span>
            </li>
            <li>
                自定义序号：
                <span>${wday.userDefined}</span>
            </li>
            <li>
                审核意见：<span>${wday.auditOpinion}</span>
            </li>
            <li class="quer">
                审核状态：
                <span><c:if test="${wday.payState=='00'}">初始状态</c:if>
                <c:if test="${wday.payState=='01'}">通过</c:if>
                <c:if test="${wday.payState=='02'}">不通过</c:if>
                <c:if test="${wday.payState=='03'}">已打款</c:if></span>
            </li>
            <li>
                审核人：<span>${wday.payOperatorName}</span>
            </li>
                <li>
                    回执单号：
                    <span>${wday.tradeCode}</span>
                </li>
                <li>
                    操作人：
                    <span>${wday.receiptOprName}</span>
                </li>
        </menu>
    </section>
</div>
</body>
</html>

