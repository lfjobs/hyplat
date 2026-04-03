<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>打印公文列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 16px;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}
.table th {
	border: 1px solid #000000;
	color: #000000;
	background: #E4F1FA;
	white-space: nowrap;
	align: center;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}

body,td,th {
	font-size: 16px;
}

body {
	margin-left: 15px;
}
</style>
	</head>
	<body>

		<div id="tableprint" align="center">
			<form id="printform">
				<table width="620" border="0" cellpadding="0" cellspacing="0"
					style="background: #FFFFFF;">
					<tr>
						<td height="25" align="center" style="font-weight: bold">
							公文汇总
						</td>
					</tr>
				</table>

				<table style="widht:630px;" cellpadding="0" cellspacing="0" class="table">
					<tr>
						<th width="30" align="center">
							序号
						</th>
						<th width="70" align="center">
							公文编号
						</th>
						<th width="100" align="center">
							正式编号
						</th>
						<th width="100" align="center">
							文件标题
						</th>
						<th width="70" align="center">
							主题词
						</th>
						<th width="70" align="center">
							申报人
						</th>
						<th width="70" align="center">
							申报人岗位
						</th>
						<th width="70" align="center">
							申报人部门
						</th>
						<th width="170" align="center">
							申报人单位
						</th>
						<th width="200" align="center">
							公文类型
						</th>
						<th width="70" align="center">
							缓急
						</th>
						<th width="100" align="center">
							公文签发状态
						</th>
						<th width="150" align="center">
							起草时间
						</th>
					</tr>
					<%
						int number = 1;
					%>
					<s:iterator value="doclist">
						<tr>
							<td>
								<span><%=number%></span>
							</td>

							<td>
								<span id="docNum">${docNum}</span>
							</td>
							<td>
								<span id="formalNum">${formalNum}</span>

							</td>
							<td>
								<span id="title">${title}</span>
							</td>
							<td>
								<span id="theme">${theme}</span>
							</td>
							<td>
								<span id="drafterName">${drafterName}</span>
							</td>
							<td>
								<span id="postName">${postName}</span>
							</td>

							<td>
								<span id="deptNameOfDraft">${deptNameOfDraft}</span>
							</td>
							<td>
								<span id="companyName">${companyName}</span>
							</td>

							<td>
								<c:choose>
									<c:when test='${docType=="aa"}'>董事会会议决定文件</c:when>
									<c:when test='${docType=="bb"}'>董事长办公室文件</c:when>
									<c:when test='${docType=="cc"}'>总裁办公室文件</c:when>
									<c:when test='${docType=="dd"}'>总部人事处文件</c:when>
									<c:when test='${docType=="ee"}'>总部办公室文件</c:when>
									<c:when test='${docType=="ff"}'>总部财务处文件</c:when>
									<c:when test='${docType=="gg"}'>总部教务(生产)处文件</c:when>
									<c:when test='${docType=="hh"}'>总部营销处文件</c:when>
									<c:when test='${docType=="jj"}'>总部教务部文件</c:when>
									<c:otherwise>总部服务(创收)平台</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test='${emergencyType=="j"}'>急件</c:when>
									<c:when test='${emergencyType=="p"}'>普通</c:when>
									<c:otherwise>特急</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test='${status=="I"}'>拟稿中</c:when>
									<c:when test='${status=="R"}'>返回修改</c:when>
									<c:when test='${status=="S"}'>审批中</c:when>
									<c:when test='${status=="A"}'>盖章中</c:when>
									<c:when test='${status=="U"}'>不批准</c:when>
									<c:when test='${status=="F"}'>盖章人归档</c:when>
									<c:when test='${status=="P"}'>待群发</c:when>
									<c:when test='${status=="O"}'>已群发</c:when>
									<c:when test='${status=="T"}'>审批中</c:when>
									<c:when test='${status=="G"}'>已归档</c:when>
									<c:when test='${status=="Z"}'>传至信息平台</c:when>
								</c:choose>
							</td>
							<td>
								<span id="draftTime">${fn:substring(draftTime,0,19)}</span>
							</td>

						</tr>
						<%
							number++;
							if(number%5==0){
							
						%>
						<p class="webjxcom"></p><% } %>
						
						
					</s:iterator>
					<%
						if (number < 5) {
							for (int i = 0; i < 4; i++) {
					%>

					<tr class="blank">
						<%
							for (int j = 0; j < 13; j++) {
						%>

						<td align="center">
							&nbsp;
						</td>
						<%
							}
						%>

					</tr>

					<%
						}
						 }
					%>
				</table>
			</form>
		</div>
	</body>
</html>