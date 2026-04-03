<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/qd.css">
    <title>抢单人信息</title>
</head>
<body>
<header class="com_head">
    <a href="javascript:;" class="back" onclick="javascript:history.back(-1);"></a>
    <h1>抢单人信息</h1>
</header>
<div class="wrap_page">
    <div class="head_img_wrap">
        <c:if test="${details[0] != null && details[0] != ''}">
            <img src="<%=basePath%>${details[0]}" class="head_img" alt="">
        </c:if>
        <c:if test="${details[0] == null || details[0] == ''}">
            <img src="<%=basePath%>images/ea/edmandServe/head_img.jpg" class="head_img" alt="">
        </c:if>
    </div>
    <ul class="jl_info_list">
        <li>姓名：${details[1]}</li>
        <%--<li>绑定账号：${details[2]}</li>
        <li>联系电话：${details[3]}</li>--%>
        <li>公司名称：${details[4]}</li>
        <c:if test="${details[5] != null && details[5] != ''}">
            <li>行业分类：${details[5]}</li>
        </c:if>
        <c:if test="${details[6] != null && details[6] != ''}">
            <li>职位工种：${details[6]}</li>
        </c:if>
    </ul>
    <a href="" class="sure_btn">确定抢单人</a>
</div>
<script>
    let basePath='<%=basePath%>';
    let sccid = "${details[8]}";
    let dlSccid = "${dlsccid}";
    let type = "${type}";
    $("a.sure_btn").click(function(){
        var flag = "";

        if(type == "服务平台"){
            var url=basePath+"ea/dserve/sajax_ea_paduan.jspa?";
            $.ajax({
                url : url,
                type: "get",
                async:false,
                dataType : "json",
                data:{
                    "sccid":sccid,
                    "dlSccid":dlSccid
                },
                success : function cbf(data){
                    var member=eval("("+data+")");
                    flag = member.flag;
                    if(flag == ""){
                        prompt("操作有误");
                    }else if(flag == "正常"){
                        window.location.href=basePath+"/ea/dserve/ea_insureQdPerson.jspa?sccid=${details[8]}&account=${details[2]}&dsid=${details[7]}&dlsccid=${dlsccid}";
                        window.event.returnValue=false;
                    }else {
                        prompt(flag);
                    }
                }
            });
        }else {
            window.location.href=basePath+"/ea/dserve/ea_isOKDemand.jspa?sccid=${details[8]}&account=${details[2]}&dsid=${details[7]}&dlsccid=${dlsccid}";
            window.event.returnValue=false;
        }

    })
</script>
<jsp:include page="/page/prompt.jsp"/>
</body>
</html>
