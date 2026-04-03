<%@ page import="hy.ea.bo.CAccount" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/stampManage.css?version=20230518"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/stampManage.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">

            <a onclick="javascript: window.history.go(-1);return false;"  target="_self">
            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            签约文件公章
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-search">
        <div class="box clearfix">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
            </label>
            <input type="text" name="" id="search" placeholder="请输入印章名称" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <section class="sec-list">
        <ul class="clearfix">
            <li class="li-add">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/stamp/img_04.png"/>
                </div>
                <p>
                    增加
                </p>
            </li>
            <li class="li-edit">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/stamp/img_06.png"/>
                </div>
                <p>
                    修改
                </p>
            </li>
            <li class="li-shanchu">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/stamp/img_05.png"/>
                </div>
                <p>
                    删除
                </p>
            </li>
          <%--  <li class="li-tingyong">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/stamp/img_03.png"/>
                </div>
                <p>
                    停用/启用
                </p>
            </li>--%>
        </ul>
    </section>
    <ul class="ul-con">
        <c:forEach items="${stamplist}" var="item">

        <li class="clearfix" id="${item.enterpriseStampID}">
            <div class="sex">
                <img class="img-01" src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png">
                <img class="img-02" src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png">
            </div>
            <div class="div-left clearfix">
                <p>${item.stampName}</p>
                <p>责任人：${item.responsibleName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${item.rejectReason}</p>
            </div>
            <div class="div-right">
                <p>${item.stampType}</p>

          <%--<c:choose>--%>
              <%--<c:when test="${item.useStatus=='unuse'}">--%>
                  <%--<p>已停用</p>--%>
              <%--</c:when>--%>
              <%--<c:when test="${item.auditStatus=='01'}">--%>
                  <%--<p>审核中</p>--%>
                <%--</c:when>--%>
                <%--<c:when test="${item.auditStatus=='02'}">--%>
                    <%--<p>审核通过</p>--%>
                <%--</c:when>--%>
              <%--<c:when test="${item.auditStatus=='03'}">--%>
                  <%--<p>驳回</p>--%>
              <%--</c:when>--%>
           <%--</c:choose>--%>


            </div>
            <input type="hidden" value="${item.useStatus}"  class="useStatus" />
        </li>
        </c:forEach>
        <%--<li class="clearfix">--%>
            <%--<div class="div-left clearfix">--%>
                <%--<p>房地产公司文件</p>--%>
                <%--<p>创建人：张明涛</p>--%>
            <%--</div>--%>
            <%--<div class="div-right">--%>
                <%--<p>财务章</p>--%>
                <%--<p>审核中</p>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li class="clearfix">--%>
            <%--<div class="div-left clearfix">--%>
                <%--<p>北京市天宇科技有限公司文件</p>--%>
                <%--<p>创建人：吴静柏</p>--%>
            <%--</div>--%>
            <%--<div class="div-right">--%>
                <%--<p>财务章</p>--%>
                <%--<p class="p-tg">通过</p>--%>
            <%--</div>--%>
        <%--</li>--%>
    </ul>
</div>

<!--确定停用提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="tycontent">您确定要停用吗？</p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right qrty">确定</p>
            </div>
        </div>
    </div>
</div>
<!--确定删除提示-->
<div class="div-shanchu">
    <div class="box">
        <p>温馨提示<img class="close-shanchu" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p>您确定要删除吗？</p>
            <div class="clearfix">
                <p class="left close-shanchu">取消</p>
                <p class="right qrdelete">确定</p>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">

    var   basePath = "<%=basePath%>";
    var  companyID =  "<%=((CAccount)session.getAttribute("account")).getCompanyID()%>";
    if(companyID==null||companyID==""){
        $(".li-edit").show();
    }else{
        $(".li-edit").hide();
    }
var enterpriseStampID = "";
</script>
</html>

