<%@ page import="com.opensymphony.xwork2.ActionContext" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/order_period.css">
    <script src="<%=basePath %>js/BuildPlatform/jquery-1.9.1.min.js"></script>
    <title>学时查询</title>
</head>

<body>
<script>
    var basePath = "<%=basePath %>";
    var idCard= "${idCard}";
    var studentId = <%=ActionContext.getContext().get("studentId")%>;
    $(function () {
        if($("#studentId").val()==null ||$("#studentId").val() == ""){
            alert("没有学时信息！");
            history.back();
        }
    })
    function periodDetails(subject) {
        var studentId = $("#studentId").val();
        var studentName = $("#studentName").text();
        var url = basePath+"mobile/office/mobileoffice_periodDetails.jspa?studentId="+studentId+"&subject="+subject+"&studentName="+studentName;
        window.location.href = url;
//        $.ajax({
//            url : encodeURI(url),
//            type : "post",
//            processData:false,
//            data : $("#studentForm").serialize(),
//            success : function (data) {
//
//            }
//        })
    }
</script>

<header class="com_head">
    <%--<a href="javascript:history.go(-1)" class="back"></a>--%>
    <h1>学时查询</h1>
</header>
<div class="wrap_page">
    <div class="xs_wrap">
        <div class="xs_name">
            <span id="studentName"><s:property value="#name"/></span>
            <input id="studentId" type="hidden" value="<s:property value="#studentId"/>"/>
            <a style="float: right;padding-right: 5px" onclick="buyPeriod()">购买学时</a>
        </div>
        <dl class="xs_con">
            <dt>科目一：<a href="###" class="xs_det" onclick="periodDetails(1)">详细</a></dt>
            <dd>理论：已学学时[<s:property value="#LLLearnTime1"/>] 剩余学时[<span><s:property value="#LLSurplusTime1"/></span>]</dd>
        </dl>
        <dl class="xs_con">
            <dt>科目二：<a href="###" class="xs_det" onclick="periodDetails(2)">详细</a></dt>
            <dd>理论：已学学时[0.0] 剩余学时[<span>0.0</span>]</dd>
            <dd>实操：已学学时[<s:property value="#SelearnTime2"/>] 剩余学时[<span><s:property value="#SCSurplusTime2"/></span>]</dd>
        </dl>
        <dl class="xs_con">
            <dt>科目三：<a href="###" class="xs_det" onclick="periodDetails(3)">详细</a></dt>
            <dd>理论：已学学时[<s:property value="#LLLearnTime3"/>] 剩余学时[<span><s:property value="#LLSurplusTime3"/></span>]</dd>
            <dd>实操：已学学时[<s:property value="#SelearnTime3"/>] 剩余学时[<span><s:property value="#SCSurplusTime3"/></span>]</dd>
            <dd>实操：练车总公里数[<span><s:property value="#K3XSLCS"/>公里</span>]</dd>
            <dd>实操：应练车里程数[<span><s:property value="#K3XSLCS"/>公里</span>]</dd>
        </dl>
    </div>
</div>
</body>
<script type="text/javascript">


    function buyPeriod() {
        var studentId = $("#studentId").val()
        $.ajax({
            type : "GET",
            url : basePath +"mobile/office/sajax_ea_buyPeriod.jspa?idCard="+idCard,
            async : false,
            dataType : "json",
            success : function(data) {
                var json = eval('(' + data + ')');
                var detailsParameter = json.detailsParameter;
                window.location.href = basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+detailsParameter[0]+"&goodsid="+detailsParameter[1]+"&companyId="+detailsParameter[2]+"&ccompanyId="+detailsParameter[3];
            }
        })
    }
</script>
</html>
