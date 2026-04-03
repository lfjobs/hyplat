<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <title>会员开通成功</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/supermarket/selfService_sfly.js" type="text/javascript"></script>
    <script>
        var basePath = "<%=basePath%>";
        var cardNum = "${param.cardNum}";
        var account = "${param.account}";
    </script>
</head>
<body>
<section class="code-pay hy">
    <!--会员开通成功内容-->
    <article>
        <!--头部-->
        <header>
            <img src="<%=basePath%>images/supermarket/zc.png" alt="">
            <h4>开通成功</h4>
            <p>您已成功开通购物卡，方便您后期使用！</p>

        </header>
        <p class="p"></p>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <div class="text">
                <h4>成为商城业主会员，能省钱更能赚钱！</h4>
                <p>请选择会员等级，等级越高，赚得越多！</p>
            </div>
            <div class="tab">
                <div class="tab-box">
                    <table>
                        <tbody>
                        <c:forEach items="${beans}" var="list">
                            <tr>
                                <td class="name">${list[0]}</td>
                                <td class="money">&yen;<span>${list[2]}</span></td>
                                <td style="display:none;">
                                    <input type="hidden" value="${list[1]}"  name="ppid"/>
                                    <input type="hidden" value="${list[5]}"  name="goodsid"/>
                                    <input type="hidden" value="${list[6]}"  name="companyId"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="btn">
                <input type="button" value="立即加入" id="join">
                <input type="button" value="暂不加入" id="notJion">
                <iframe name="hidden" width="0" height="0" />
            </div>

        </figure>
        <!--内容 end-->
    </article>
    <!--会员开通成功 end-->
</section>

</body>
</html>
