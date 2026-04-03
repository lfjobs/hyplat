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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectCompany.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/human/staff/selectCompany.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            选择分配人
        </li>
    </ul>
</header>
<div class="content">
    <c:if test="${typee ne 'xr'}">
        <section class="headNav">
            <ul>
                <li><p><a  href="javascript:nav('1','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png">集团内部</a></p></li>
                <li><p><a href="javascript:nav('2','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">往来公司</a></p></li>
                <li><p><a href="javascript:nav('3','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">往来个人</a></p></li>

            </ul>
        </section>
    </c:if>
    <section class="sec-search">
        <label for="">
            <img src="<%=basePath%>images/ea/office/contract/selectp/img_01.png"/>
        </label>
        <input type="text" placeholder="搜索" value="" id="search"/>
    </section>
    <section class="sec-ul">
        <ul class="ul-list">
        </ul>
    </section>
</div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var  docId = "${param.docId}";
    var typee = "${param.typee}";
    function nav(c,docId,typee) {
        if(c=="1"){
            document.location.replace("<%=basePath%>page/WFJClient/pc/5l5C/office/config/selectCompany.jsp?type=fp&docId="+docId+"&typee="+typee);
        }else if(c=="2"){
            document.location.replace("<%=basePath%>page/WFJClient/pc/5l5C/office/config/selectWldw.jsp?type=fp&docId="+docId+"&typee="+typee);

        }
        else if(c=="3"){
            document.location.replace("<%=basePath%>page/WFJClient/pc/5l5C/office/config/selectWlgr.jsp?type=fp&docId="+docId+"&typee="+typee);

        }

    }
</script>
</html>

