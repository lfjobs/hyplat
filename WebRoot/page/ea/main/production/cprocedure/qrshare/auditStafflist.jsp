<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<script type="text/javascript"
	src="<%=basePath%>js/ea/finance/setHtmlFont.js"></script>
<link rel="stylesheet" href="<%=basePath%>css/WFJClient/base.css">
<link rel="stylesheet" href="<%=basePath%>css/WFJClient/zy_sys.css">
<script type="text/javascript"
	src="<%=basePath%>js/ea/finance/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/production/cprocedure/qrshare/auditStaff.js"></script>
<title>审批人</title>

<script>
	var basePath = "<%=basePath%>";
	var comment = "${param.comment}";
</script>
</head>
<body>
	<header class="com_head"> <a href="javascript:history.back(-1);" class="back"></a>
	<h1>审批人</h1>
	<a href="javascript:;" class="head_R">完成</a> </header>
	<div class="wrap_page">
		<div class="add_search">
			<div class="tinfo_wrap">
				<input type="text" class="taskinfo_search">
				<div class="tinfo_over">
					<i class="tinfo_ico"></i> 搜索
				</div>
			</div>
		</div>
		<div class="department">
			<input type="hidden" id="comID" value="${param.companyID}" /> <input
				type="hidden" id="companyName" value="${param.companyName}" /> <input
				type="hidden" id="orgID" value="${param.orgID}" /> <input
				type="hidden" id="orgName" value="${param.orgName}" /> <input
				type="hidden" id="auid" value="${param.auid}" />
				
			<div class="dep_nav clearfix">
				<span><a href="###" class="dep_name">${param.companyName}</a></span>
				<span class="dep_arr"></span> <span>${param.orgName}</span>
			</div>
			<c:forEach items="${stafflist}" var="item">
				<div class="dep_list staffdiv"
					id="${item[0]}">
					<input type="hidden" class="staffID" value="${item[0]}"/>
                 <input type="hidden" class="staffname" value="${item[1]}"/>
                 <input type="hidden" class="position" value="${item[2]}"/>
                <div class="dep_box clearfix">
                    <img src="<%=basePath%>${item[3]}" class="dep_img" alt="">
                    <div class="dep_text">${item[1]}(${item[2]})</div>
                    <i></i>
                </div> 
            </div>
            </c:forEach>
        </div>
        <div class="person_list">
            <div class="p_search clearfix">
               <!--  <img src="images/head_img.png" class="p_img" alt="">
                <div class="p_text">
                    <div class="p_name">孙海舰</div>
                    <div class="p_info">北京天太世统科技有限公司-人事部</div>
                </div>
                <i></i> -->
            </div>
        </div>
    </div>
</body>

</html>
