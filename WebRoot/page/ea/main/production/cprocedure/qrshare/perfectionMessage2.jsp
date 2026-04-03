<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

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
<script
	src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/officemanage/cropper.min.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/officemanage/base.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/officemanage/manage_5L5C.css">
<script type="text/javascript"
	src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
<title>基本资料认证</title>
</head>
<script type="text/javascript">
	var basePath = '<%=basePath%>';
	var auditSkip = '${auditSkip}';
	var companyID = '${caccount.companyID}';
	var staffID = '${caccount.staffID}';
	var authState = '${contactCompany.authState}';
	if(auditSkip==""){
	      auditSkip='<%=session.getAttribute("auditSkip")==null?"":(String)session.getAttribute("auditSkip") %>';
	        }
	
    window.addEventListener("popstate", function(e) {
//        alert("我监听到了浏览器的返回按钮事件啦");//根据自己的需求实现自己的功能
        document.location.href = basePath+"/mobile/office/mobileoffice_fastApplication.jspa";
    }, false);
    
    function back(){
    if(auditSkip=="01"){
        window.history.go(-1);return false;
       //  document.location.href = basePath+"/ea/5l5c/ea_work5L5C.jspa?companyID="+companyID+"&staffID="+staffID;
    
    }else{
     
       document.location.href = basePath+"/mobile/office/mobileoffice_fastApplication.jspa";
   
    }
    }

</script>

<body>
	<!-- header 开始  -->
	<header class="com_head">
		<a href="javascript:back();" class="back"></a>
		<h1>公司认证</h1>

		<a href="<%=basePath%>/ea/merch/ea_getApplyTypeInfo.jspa?companyID=${caccount.companyID}&caccount.staffID=${caccount.staffID}" class="head_R" style="display: none;">修改资料</a>
		<!--只有已通过状态显示head_R-->
	</header>
	<!--  header 结束  -->
	<!-- 页面内容 开始  -->
	<div class="wrap_page">
		<!--已通过 开始-->
		<div class="pass" style="display: none;">
			<div class="state_ico pass_ico"></div>
			<div class="state_text">资料认证已通过</div>
		</div>
		<!--已通过 结束-->
		<!--未通过 开始-->
		<div class="no_pass" style="display: none;">
			<div class="state_ico nopass_ico"></div>
			<div class="state_text">资料认证未通过</div>
			<c:if test="${auditComment ne null&&auditComment ne ''}">

				<div class="state_text">${auditComment}</div>
			</c:if>



			<c:if test="${auditComment.indexOf('补充材料')!=-1||auditComment.indexOf('特殊资质')!=-1}">
				<a href="<%=basePath%>/ea/merch/ea_getApplyBcInfo.jspa?companyID=${caccount.companyID}&staffID=${caccount.staffID}&auditComment=${auditComment}" class="again_up">补充上传</a>
			</c:if>
			<c:if test="${auditComment.indexOf('补充材料')==-1&&auditComment.indexOf('特殊资质')==-1}">
				<a href="<%=basePath%>/ea/merch/ea_getApplyTypeInfo.jspa?companyID=${caccount.companyID}&staffID=${caccount.staffID}&reAudit=1" class="again_up">重新上传</a>
			</c:if>

		</div>
		<!--未通过 结束-->
		<!--正在审核 开始-->
		<div class="ing" style="display: none;">
			<div class="state_ico ing_ico"></div>
			<div class="state_text">
				资料已上传<br>审核结果将于1-5个工作日内完成
			</div>
		</div>
		<!--正在审核 结束-->
	</div>
	<!--  页面内容 结束 -->

</body>

<script type="text/javascript">
	$(document).ready(function() {
		if(authState=='01'){
			$(".ing").show();
			
		}else if(authState=='02'){
			$(".head_R").show();
			$(".pass").show();
		}else if(authState=='03'){
			$(".no_pass").show();
		}
	})


</script>

</html>
