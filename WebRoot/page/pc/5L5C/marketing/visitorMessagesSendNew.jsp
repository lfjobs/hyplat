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
    <div class="input-box1">
        <input id="searchIn" class="input-11" type="text" value="${crmShortMessagingTemplatePO.templateHeadline}"
               name="crmShortMessagingTemplatePO.templateHeadline" placeholder="请在这里输入模板名称">
    </div>
    <section class="sec-nav sec-hide">
        <ul class="clearfix center-items">
            <li>
                <div class="item item1">
                    <i class="layui-icon" style="font-size: 40px">&#xe60a;</i>
                    <p class="massSending">短信模版</p>
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
    <div class="num">
        <div class="num1">免费短信：</div>
        <div class="num2">积分余额：</div>
    </div>
    <%--客户身份--%>
    <section class="sec-list1">
        <div class="div-sec-data1">
        </div>
    </section>
    <section class="sec-list" style=" overflow: hidden auto">
        <div class="div-sec-data">
            <div class="data-title1">

            </div>
            <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden">
            </div>
        </div>
    </section>
    <section class="sec-list">
        <div class="div-sec-data">
            <div class="data-title1">
            </div>
            <%--弹框--%>
            <div id="myModal" class="modal">
                <div class="modal-content">
                    <%--                    <span class="close">&times;</span>--%>
                    <div class="title">
                        <div style="font-size: 18px;">确认消息内容</div>
                        <div class="money">每条消耗积分：</div>
                    </div>
                    <div style="border-bottom: 1px solid #c3bbbb;">
                        <div class="substance"></div>
                    </div>
                    <div class="modal-footer">
                        <div id="modal-btn-cancel" class="btn-cancel">取消</div>
                        <div class="separator"></div>
                        <div id="modal-btn-confirm" class="btn-confirm">确定</div>
                    </div>
                </div>
            </div>
            <%--确认弹框--%>
            <div id="btnModal" class="modal">
                <div class="modal-content1">
                    <%--                    <span class="close">&times;</span>--%>
                    <div class="title">
                        <div style="font-size: 18px;">提示</div>
                    </div>
                    <div style="border-bottom: 1px solid #c3bbbb;">
                        <div class="substance1">选择发送方式</div>
                    </div>
                    <div class="modal-footer1">
                        <div id="modal-btn-cancel1" class="btn-cancel">本地联系人</div>
                        <div class="separator"></div>
                        <div id="modal-btn-confirm1" class="btn-confirm">网络联系人</div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<script>
</script>
</body>
</html>