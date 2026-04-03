<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>未阅读公文查看</title>
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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/doc.css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />

		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/office_ea/document/ReadnoView.js"></script>

	</head>
	<body>
		<div class="operate">
			<input type="button" value="<<返回" class="greyBtn" id="noapprove"
				onclick="history.back();" />
			&nbsp;|&nbsp;
			<input type="button" value="标记已读" class="greyBtn"
				onclick="markReaded();" />
		</div>
		<div id="wspv">

			<form name="wviewForm" method="post" action="" id="wviewForm">
				<div style="height: 1px;"></div>

				<input type="submit" name="submit" style="display: none" />

				<div style="padding-left: 45%; padding-bottom: 10px;">
					${document.title}
				</div>

				<table class="table" cellspacing="0" cellpadding="5" id="wspView">
					<tr>
						<td class="intitle" align="right" style="width: 15%;">
							<span>文件标题</span>：
						</td>
						<td align="left" colspan="2">
							<span id="title">${document.title} </span>
							<input type="hidden" value="${document.docId}"
								name="document.docId" />
						</td>

					</tr>
					<tr>
						<td align="right">
							文件编号：
						</td>
						<td align="left">
							<span id="docNum">${document.docNum}</span>

						</td>
						<td align="left">
							主题词：
							<span id="title">${document.theme}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							公文类型：
						</td>
						<td>
							<c:choose>
								<c:when test='${document.docType=="aa"}'>董事会会议决定文件</c:when>
								<c:when test='${document.docType=="bb"}'>董事长办公室文件</c:when>
								<c:when test='${document.docType=="cc"}'>总裁办公室文件</c:when>
								<c:when test='${document.docType=="dd"}'>总部人事处文件</c:when>
								<c:when test='${document.docType=="ee"}'>总部办公室文件</c:when>
								<c:when test='${document.docType=="ff"}'>总部财务处文件</c:when>
								<c:when test='${document.docType=="gg"}'>总部教务(生产)处文件</c:when>
								<c:when test='${document.docType=="hh"}'>总部营销处文件</c:when>
								<c:when test='${document.docType=="jj"}'>总部教务部文件</c:when>
								<c:otherwise>总部服务(创收)平台</c:otherwise>
							</c:choose>

						</td>
						<td align="left">
							缓&nbsp;&nbsp;&nbsp;&nbsp;急：
							<c:choose>
								<c:when test='${document.emergencyType=="j"}'>急件</c:when>
								<c:when test='${document.emergencyType=="p"}'>普通</c:when>
								<c:otherwise>特急</c:otherwise>
							</c:choose>

						</td>


					</tr>
					<tr class="trtip">
						<td align="right">
							合同有效期：
						</td>
						<td colspan="2" align="left">
							<span>${fn:substring(document.startValidity, 0,
								10)}至${fn:substring(document.endValidity, 0, 10)}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							拟稿人公司：
						</td>
						<td align="left">
							<span id="companyName">${document.companyName}</span>

						</td>

						<td align="left">
							拟稿人部门：
							<span id="drafterID">${document.deptNameOfDraft}</span>

						</td>

					</tr>
					<tr>
						<td align="right">
							拟&nbsp;&nbsp;稿&nbsp;人：
						</td>
						<td align="left">
							<span id="drafterID">${document.drafterName}</span>

						</td>
						<td align="left">
							拟稿人岗位：
							<span id="postName">${document.postName}</span>

						</td>

					</tr>

					<tr>
						<td align="right">
							审批人公司：
						</td>
						<td align="left">
							<span id="comNameofSub">${document.comNameofSub}</span>

						</td>

						<td align="left">
							审批人部门：
							<span id="deptNameofSub">${document.deptNameofSub}</span>

						</td>


					</tr>
					<tr>
						<td align="right">
							审&nbsp;批&nbsp;人：
						</td>
						<td align="left" colspan="2">
							<span id="subscriberName">${document.subscriberName}</span>

						</td>

					</tr>
					<tr>
						<td align="right">
							分发人公司：
						</td>
						<td align="left">
							<span id="comNameofPublisher">${document.comNameofPublisher}</span>

						</td>

						<td align="left">
							分发人部门：
							<span id="deptNameofPublisher">${document.deptNameofPublisher}</span>

						</td>


					</tr>
					<tr>
						<td align="right">
							分&nbsp;发&nbsp;人：
						</td>
						<td align="left" colspan="2">
							<span id="publisherName">${document.publisherName}</span>

						</td>

					</tr>
					<tr>
						<td align="right">
							拟稿人意见：
						</td>
						<td colspan="2">
							<span id="drafterComment">${document.drafterComment}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							审批人意见：
						</td>
						<td colspan="2">
							<span id="subscriberComment">${document.subscriberComment}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							盖章人意见：
						</td>
						<td colspan="2">
							<span id="sealerComment">${document.sealerComment}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							分发人意见：
						</td>
						<td colspan="2">
							<span id="publisherComment">${document.publisherComment}</span>

						</td>
					</tr>

					<tr>
						<td align="right">
							<img src="<%=basePath%>images/ea/office/document/attach1.png"
								width="16" height="16" />
							正文附件：
						</td>
						<td colspan="2">
							<s:iterator id="doc" value="attachlist">
								<a href="#"
									onclick="ViewOffice('${filePath}','${fileType}','${fileShowName}','${document.docId}')">${fileShowName}.${ext}</a>
							</s:iterator>
							<br>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<iframe name="hidden" width="0" height="0"></iframe>

		<script type="text/javascript">
         var basePath='<%=basePath%>';
     	 
        </script>

	</body>
</html>
