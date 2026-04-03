<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://example.com/phone" prefix="phone" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css">
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/receive_list.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/edit.js"></script>
    <title>接单记录</title>
    <script type="text/javascript">
        var basePath='<%=basePath%>';
        var t;
        var pagenumber=0;
        var pagecount=0;
        var search="";
        var sccid="${sccid}";
        var originPage = '${param.originPage}';
        var type = '${param.type}';
    </script>
</head>

<body style="background: #f4f4f4;">
<s:if test="tle==1">
<header class="com_head">
    <a href="javascript:;" class="back" onclick="javascript:history.back(-1);"></a>
    <h1>接单记录</h1>
</header>
</s:if>
<div class="wrap_page" style="background: #f4f4f4;<s:if test='tle==null||tle==0'>margin-top:0;</s:if>">
    <div class="rec_tab_wrap clearfix" <s:if test='tle==null||tle==0'>style="top: 0;" </s:if><s:else>style="top: 2.3rem;" </s:else>>
        <a href="javascript:;" class="rec_tab rec_tab_cur">接单中</a>
        <a href="javascript:ajax();" class="rec_tab">接单记录</a>
    </div>
    <div class="rec_con_wrap">
        <div class="rec_con qd_con">
            <s:iterator value="#request.ObjectList" var="ol" >
            <a href="javascript:xp('${ol[0]}');" class="qd_box qd_rec" id="${ol[0]}">
                <div class="qd_top qd_two clearfix">
                    <span>${phone:hide(ol[1]==null||ol[1]==""?"--":ol[1])}</span>
                    <span>${ol[2]==null||ol[2]==""?"--":ol[2]}</span>
                </div>
                <div class="qd_bottom qd_two clearfix">
                    <span>${ol[3]==null||ol[3]==""?"--":ol[3]}</span>
                    <span>${ol[4]==null||ol[4]==""?"--":ol[4]}</span>
                </div>
            </a>
            </s:iterator>
            <s:if test="#request.ObjectList.size()>0">
            <div class="qd_tip">恭喜您！抢单成功，等待用户确认</div>
            </s:if>
        </div>
        <div class="rec_con qd_con jdjl">
        </div>
    </div>
</div>
<%--<div class="nest_page">
    <div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>联营商城会员抢单</span>
    </div>
    <div class="nest_bd"></div>
</div>--%>
</body>
</html>
