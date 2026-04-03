<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>零售价格设置</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no"/>
    <script>
        setFont();
        window.onload = window.onresize = setFont;

        function setFont() {
            var clientWidth = document.documentElement.clientWidth;
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    </script>
    <style>
        body,
        html {
            -moz-user-select: none;
            -khtml-user-select: none;
            user-select: none;
            -webkit-overflow-scrolling: touch
        }

        body {
            margin: 0 auto;
            background-color: #fff;
            width: 100%
        }

        body,
        div,
        html,
        p {
            margin: 0 0;
            font-family: '微软雅黑'
        }

        .content {
            padding: 0 0.8rem;
        }

        .content .title {
            height: 3rem;
            line-height: 3rem;
            font-size: 0.9rem;
            text-align: center;
        }

        .content .header div {
            display: flex;
        }

        .content .header p {
            font-size: 0.55rem;
            height: 1.7rem;
            line-height: 1.7rem;
        }

        .content .header .first {
            width: 53%;
        }

        .list {
            padding-top: 0.5rem;
        }

        .list div {
            width: 70%;
            display: flex;
            justify-content: space-between;
        }

        .list div p {
            font-size: 0.55rem;
            height: 1.1rem;
            line-height: 1.1rem;
        }

        .list .last {
            padding-top: 0.625rem;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="content">
    <p class="title">${pRetail[6]}零售价格设置</p>
    <div class="header">
        <div>
            <p class="first">产品条码: ${pRetail[7]}</p>
            <p>产品名称:${pRetail[6]}</p>
        </div>
        <div>
            <p class="first">添加人员:${pRetail[8]}</p>
            <p>添加时间: ${pRetail[10]}</p>
        </div>
        <p>公司:${pRetail[9]}</p>
    </div>
    <div class="list">
        <div>
            <p>成本价</p>
            <p>￥${pRetail[2]}</p>
        </div>
        <c:forEach items="${beanList}" var="p">
            <div>
                <p>${p[3]}</p>
                <p>￥${p[1]}</p>
            </div>
        </c:forEach>
        <div>
            <p>业务佣金</p>
            <p>￥${pRetail[3]}</p>
        </div>
        <div>
            <p>投资设备类型</p>
            <p>
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
            </p>
        </div>
        <div>
            <p>系统单价</p>
            <p>￥${pRetail[1]}</p>
        </div>
        <div class="last">
            <p>加消费红包最终售价</p>
            <p>￥11</p>
            <p>(系统单价+1%)</p>
        </div>
    </div>
</div>
</body>
</html>

