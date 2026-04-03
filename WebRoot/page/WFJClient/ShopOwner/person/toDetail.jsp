<%@ page language="java"  pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String backurl = request.getParameter("backurl");
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title>卖家地址管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="description" content="卖家地址管理">
    <link rel="stylesheet" type="text/css" href="css/WFJClient/wfjapp.css"/>
    <link rel="stylesheet" type="text/css" href="css/ea/websitemall/card/default_address.css"/>
    <script type="text/javascript" src="js/jquery-1.6.1.min.js"></script>
    <script type="text/javascript" src="js/ea/websitemall/card/android/region_select.js"></script>
    <script type="text/javascript" src="js/ea/marketing/supermarket/font-size.js"></script>
    <script type="text/javascript" src="js/WFJClient/toDetail.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var editType = "${editType}";
        var backurl = "<%=backurl%>";
        var area = "${refundAddress.rarea}";
        var source = "${param.source}";
        var inventoryID = "${param.inventoryID}";
        var count = "${param.count}";
        var intf = "${param.intf}";
        var backurls = "${param.backurls}";//mz

        var companyId = "${refundAddress.companyId}";
        var type = "${refundAddress.rtype}";
        var flag = "${param.flag}";
    </script>
</head>

<body>
<div class="del_c">
    <section>
        <p class="ctip">是否删除该${type eq 0?"退货地址":type eq 1?"发货地址":"收货地址"}</p>
        <div>
            <p id="no">否</p>
            <p id="yes">是</p>
        </div>
    </section>
</div>
<div class="del_d">
    <section>
        <p>操作成功</p>
        <p>确定</p>
    </section>
</div>

<form id="form" name="form" method="post">
    <input type="submit" id="submit" name="submit" style="display: none;">
    <input type="hidden" value="11" name="message">
    <input type="hidden" name="user" value="${param.user}">
    <input type="hidden" name="type" value="${param.type}">
    <input type="hidden" name="refundAddress.rtype" value="${refundAddress.rtype}">
    <input type="hidden" name="refundAddress.companyId" value="${refundAddress.companyId}" id="companyId">
    <input type="hidden" name="refundAddress.raddressId" value="${refundAddress.raddressId}">
    <iframe name="hidden" style="display: none;"></iframe>
    <div class="main">
        <div class="top">
            <ul>
                <li class="arrow"><a href="javascript:;" target="_self"><img src="images/ea/websitemall/card/arrow.png"/></a></li>
                <li>${type eq 0?"退货地址":type eq 1?"发货地址":"收货地址"}</li>
                <a href="javascript:;" id="edit">
                    <li>修改</li>
                </a>
                <div class="clear"></div>
            </ul>
        </div>
        <div class="add">
            <button type="button" class="default">设置默认${type eq 0?"退货地址":type eq 1?"发货地址":"收货地址"}</button>
        </div>
        <div id="prompt" style="width: 100%;display: none;	">
            <center>
                <div>
                    <span style="position: relative;top: 19.8%;"></span>
                </div>
            </center>
        </div>
        <div class="line"></div>
        <div class="content">
            <ul class="data ul">
                <li><h5>${type eq 0 or type eq 2?"收货":"发货"}人</h5>
                    <input readonly="readonly" value="${refundAddress.rname}"
                                                           name="refundAddress.rname"/></li>
                <div class="xt"></div>
                <li>
                    <h5>手机号码</h5>
                    <input readonly="readonly" value="${refundAddress.rtel}"
                                        name="refundAddress.rtel"/>
                </li>
                <div class="xt"></div>
                <li id="p" class="address"><h5>省</h5><input readonly="readonly" value=""/></li>
                <div class="xt"></div>
                <li id="c" class="address"><h5>市</h5><input readonly="readonly" value=""/></li>
                <div class="xt"></div>
                <li id="a" class="address"><h5>区</h5><input readonly="readonly" value=""/></li>
                <div class="xt"></div>
                <li>
                    <h5>详细地址</h5>
                    <input readonly="readonly" value="${refundAddress.rstreet}"
                                        name="refundAddress.rstreet" id="address"/>
                </li>
                <div class="xt"></div>
                <%-- <li id="postcode" style="display: none;"><h5>邮政编码</h5><input readonly="readonly" value="${addres.postcode}"name="staffVo.receiptAddress['0'].postcode" type="tel" /></li><div class="xt" ></div>--%>
                <div class="clear"></div>

            </ul>
            <div class="del">
                <p>
                    删除${type eq 0?"退货地址":type eq 1?"发货地址":"收货地址"}
                </p>
            </div>
        </div>
    </div>


    <div class="alert">
        <div class="alert_bg"></div>
        <div class="alert_con">
            <p id="jg">是否确定删除</p>
        </div>
    </div>
</form>
</body>
</html>
