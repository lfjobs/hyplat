<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
    <title>购物卡</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
    <link href="<%=basePath%>css/ea/supermarket/app_gift.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/ea/marketing/supermarket/appGiftCard.js" type="text/javascript"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";

    </script>
</head>
<body>
<div class="shelter_layer">
    <section>
        <menu>
            <li>微分金提示</li>
            <li class="message">你输入的有误!</li>
        </menu>
    </section>
</div>

<div class="content">
    <form id="regform"  method="post">
        <input type="hidden" name="sccid" value="${sccid}" />
        <p>
            <label for="shopping_card_number">
                购物卡号：
            </label>
            <input type="text" placeholder="" name="giftCards.cardNumber" id="shopping_card_number" value="${cardNumber}" />
        </p>
        <p>
            <label for="name">
                姓名：
            </label>
            <input type="text" placeholder="" name="staff.staffName" id="name" value="" />
        </p>
        <p>
            <label for="cell_phone_number">
                手机号：
            </label>
            <input type="text" placeholder="" name="staff.reference" id="cell_phone_number" value="" />
        </p>
        <p>
            <label for="invitation_code">
                邀请码：
            </label>
            <input type="text" placeholder="必填" required name="iphone" id="invitation_code" value="${account}" />
        </p>
        <p>
            <label for="password">
                密码：
            </label>
            <input type="password" placeholder="" name="password" id="password" value="" />
        </p>
        <p>
            <label for="confirm_password">
                确认密码：
            </label>
            <input type="password" placeholder="" name="" id="confirm_password" value="" />
        </p>
        <p>
            <label for="paymentCode">
                支付密码：
            </label>
            <input type="password" placeholder="" name="paymentCode" id="paymentCode" value="" />
        </p>
        <p>
            <label for="consignee">
                收货人：
            </label>
            <input type="text" placeholder="" name="staffAddress.consignee" id="consignee" value="" />
        </p>
        <p>
            <!--.not选中不变红-->
            <label>
                地图地址：
            </label>
            <input type="hidden" id="area" value="" name="staffAddress.area" />
            <input type="text" placeholder="" readonly="true" class="not" name="" id="map_address" value="" />
        </p>
        <p>
            <label for="detailed_address">
                详细地址：
            </label>
            <input type="text" placeholder="" name="staffAddress.addressDetailed" id="detailed_address" value="" />
        </p>
        <input type="button" name="" id="registeredSubmit"  value="提交"/>
    </form>
</div>
</body>
</html>
