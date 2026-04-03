<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>群发短信</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/visitorMessagesSend1.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/visitorMessagesSend.es1.js" type="module"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            群发短信
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-nav sec-hide">
        <ul class="clearfix center-items">
            <li>
                <div class="item">
                    <i class="layui-icon" style="font-size: 40px">&#xe60a;</i>
                    <p class="massSending">群发记录</p>
                </div>
            </li>
            <li>
                <div class="item">
                    <i class="layui-icon" style="font-size: 32px">&#xe611;</i>
                    <p class="massText">群发短信</p>
                </div>
            </li>
        </ul>
    </section>
    <div class="input-box1">
        <input id="searchIn" class="input-11" type="text" value="${crmShortMessagingTemplatePO.templateHeadline}"
               name="crmShortMessagingTemplatePO.templateHeadline" placeholder="请在这里输入短信模板标题">
    </div>
    <section class="sec-list">
        <div class="textarea-container">
    <textarea
            id="templateText"
            name="crmShortMessagingTemplatePO.templateText"
            placeholder="选择玩短信模板后，这里会显示短信模板内容。"
            maxlength="200"
            minlength="2"
            required
    >${crmShortMessagingTemplatePO.templateText}</textarea>
            <div class="char-counter"></div>
        </div>
    </section>
    <div class="tips-box">
        <label style="display: flex"> <span style="color: red">* </span>
            <p>超过70字短信每次算长短信，长短信的收费是普通短信的2倍。</p></label>
        <label style="display: flex"> <span style="color: red">* </span>
            <p>发送成功的短信才会扣除相应的费用，费用不足时未发送的用户则会被标记未发送失败。</p></label>
    </div>
    <%--客户身份--%>
    <section class="sec-list1">
        <div class="div-sec-data1">

        </div>
    </section>

    <section class="sec-list">
        <div class="div-sec-data">
            <div class="data-title1">
            </div>
            <%--弹框--%>
            <div id="myModal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <div id="modal-data-list" class="data-list"></div>
                </div>
            </div>
        </div>
    </section>
</div>

<script>
</script>
</body>
</html>