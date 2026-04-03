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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/consult/consultview.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/jQuery.print.min.js" type="text/javascript" charset="utf-8"></script>



    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
           查看详情
        </li>
    </ul>
</header>


    <div class="content" >
        <div class="div-name">
            <label for="">姓名：</label>
            <input type="text"  value="${consult.consultantName}" readonly/>
        </div>

        <div class="div-name">
            <label for="">电话：</label>
            <a href="tel:${consult.consultantPhone}"><input type="text"  value="${consult.consultantPhone}" readonly /></a>
        </div>
        <div class="div-name">
            <label for="">咨询时间：</label>
            <input type="text"  value="${fn:substring(consult.consultingDate,0,19)}" readonly/>
        </div>
        <div class="div-name">
            <label for="">咨询内容：</label>
            <input type="text"  value="${consult.consultantContent}" readonly />
        </div>

        <div class="div-name">
            <label for="">来源：</label>
            <c:if test="${consult.ppid ne null&&consult.ppid ne ''}">
                <a  href = "<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${consult.ppid}&ccompanyId=${consult.companyId}&type=time&miniSystemJudge=03"><span id="goodsname">${consult.goodsname}</span></a>
            </c:if>
            <c:if test="${consult.ppid eq null||consult.ppid eq ''}">
                <a href = "<%=basePath%>ea/industry/ea_getCompanyHome.jspa?ccompanyId=${consult.ccompanyId}&industryType=&etype=">公司网站</a>
            </c:if>
        </div>

        <div class="div-name">
            <label for="">是否回访：</label>
            <input type="text"  value="${consult.returnVisit eq '00'?'否':'是'}" readonly />

        </div>
        <div class="div-name">
            <label for="">是否接听：</label>
            <input type="text"   value="${consult.islisten eq '00'?'否':'是'}" readonly  />
        </div>

        <div class="div-name">
            <label for="">回访客服：</label>
            <input type="text"   value="${consult.visitStaffName}" readonly id="companyName"/>
        </div>
        <div class="div-name">
            <label for="">回访时间：</label>
            <input type="text"   value="${fn:substring(consult.visitDate,0,19)}" readonly/>
        </div>
        <div class="div-name">
            <label for="">客户类型：</label>
            <c:choose>
                <c:when test="${consult.clientType eq '01'}">
                    <input type="text"  value="公司" readonly/>

                </c:when>

                <c:when test="${consult.clientType eq '02'}">
                    <input type="text"  value="个人" readonly/>

                </c:when>
            </c:choose>
        </div>
        <div class="div-name">
            <label for="">合作意向：</label>
            <c:choose>
                <c:when test="${consult.isIntentCustomer eq '00'}">
                    <input type="text"  value="不感兴趣" readonly/>

                </c:when>

                <c:when test="${consult.isIntentCustomer eq '01'}">
                    <input type="text"  value="有意向" readonly/>

                </c:when>

                <c:when test="${consult.isIntentCustomer eq '02'}">
                    <input type="text"  value="成交客户" readonly/>

                </c:when>
                <c:otherwise>
                    <input type="text"  value="" readonly/>



                </c:otherwise>
            </c:choose>
        </div>
        <div class="div-name div-area">
            <label for="">通话记录：</label>
            <div class="area">${consult.visitContent}</div>

        </div>


    </div>


</body>

<script type="text/javascript">
    var basePath = "<%=basePath%>";



</script>
</html>
