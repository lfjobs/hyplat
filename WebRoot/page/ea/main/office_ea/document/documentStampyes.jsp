<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="docCommon.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>已盖章公文</title>
		<script
			src="<%=basePath%>js/ea/office_ea/document/documentStampyes.js"></script>
	</head>
	<body>
		<!--已盖章公文 -->

		<div id="wsp">
			<table class="wspdoc">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="30" align="center">
							序号
						</th>
						<th class="thnum" width="70" align="center">
							<span>公文编号</span>
						</th>
						<th width="170" align="center">
							正式编号
						</th>
						<th class="thtitle" width="200" align="center">
							<span>文件标题</span>
						</th>
						<th width="70" align="center">
							主题词
						</th>
						<th width="70" align="center">
							跟踪
						</th>
						<th width="130" align="center">
							公文类型
						</th>
						<th width="70" align="center">
							缓急
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
							申报单位名称
						</th>
						<th width="170" align="center">
							签发(审批)单位
						</th>
						<th width="100" align="center">
							签发(审批)部门
						</th>
						<th width="70" align="center">
							签发(审批)人
						</th>
						<th class="trtip" width="150" align="center">
							合同有效期
						</th>
						<th class="trtip" width="300" align="center">
							甲方
						</th>
						<th class="trtip" width="300" align="center">
							乙方
						</th>
						<th width="120" align="center">
							处理结果
						</th>

						<th width="150" align="center">
							盖章时间
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr class="docs" id="${docid}">
							<td class="td_bg01">
								<input type="radio" name="radioGroup" class="JQuerypersonvalue"
									id="docId" value="${docid}" />
							</td>
							<td class="td_bg01">
								<span><%=number%></span>
							</td>

							<td class="td_bg01">
								<span id="docNum">${docnum}</span>
							</td>
							<td class="td_bg01">
								<span id="formalnum">${formalnum}</span>
							</td>
							<td class="td_bg01">
								<span id="title">${title}</span>
							</td>
							<td class="td_bg01">
								<span id="theme">${theme}</span>
							</td>
							<td class="td_bg01">
								<img src="<%=basePath%>images/ea/office/document/time16.png"
									style="cursor: hand;" width="16" height="16"
									onclick="getDocTrackRecord(this,'${docid}')" />
							</td>
							<td class="td_bg01">
								<c:choose>
									<c:when test='${doctype=="aa"}'>董事会会议决定文件</c:when>
									<c:when test='${doctype=="bb"}'>董事长办公室文件</c:when>
									<c:when test='${doctype=="cc"}'>总裁办公室文件</c:when>
									<c:when test='${doctype=="dd"}'>总部人事处文件</c:when>
									<c:when test='${doctype=="ee"}'>总部办公室文件</c:when>
									<c:when test='${doctype=="ff"}'>总部财务处文件</c:when>
									<c:when test='${doctype=="gg"}'>总部教务(生产)处文件</c:when>
									<c:when test='${doctype=="hh"}'>总部营销处文件</c:when>
									<c:when test='${doctype=="jj"}'>总部教务部文件</c:when>
									<c:otherwise>总部服务(创收)平台</c:otherwise>
								</c:choose>
							</td>
							<td class="td_bg01">
								<c:choose>
									<c:when test='${emergencytype=="j"}'>急件</c:when>
									<c:when test='${emergencytype=="p"}'>普通</c:when>
									<c:when test='${emergencytype=="t"}'>特急</c:when>
									<c:otherwise>${emergencytype}</c:otherwise>
								</c:choose>

							</td>

							<td class="td_bg01">
								<span id="drafterName">${draftername}</span>
							</td>
							<td class="td_bg01">
								<span id="postName">${postname}</span>
							</td>
							<td class="td_bg01">
								<span id="deptNameOfDraft">${deptnameofdraft}</span>
							</td>
							<td class="td_bg01">
								<span id="companyName">${companyname}</span>
							</td>
							<td class="td_bg01">
								<span id="comNameofSub">${comnameofsub}</span>
							</td>
							<td class="td_bg01">
								<span id="deptNameofSub">${deptnameofsub}</span>
							</td>
							<td class="td_bg01">
								<span id="subscriberName">${subscribername}</span>
							</td>
							<td class="trtip">
								<s:if test="startvalidity==null||startvalidity==''">
							   无指定
							 </s:if>
								<s:else>
									<span>${fn:substring(startvalidity,
										0,10)}至${fn:substring(endvalidity, 0, 10)}</span>
								</s:else>
							</td>
							<td class="trtip">
								
							   <span id="partyaname">${partyaname}</span>
                               <span id="partyastaffnames">${partyastaffnames}</span>
                               <s:if test="staffidentitycarda!=null">
							      <span id="staffidentitycarda">(${staffidentitycarda})</span>
							   </s:if>
							</td>
							<td class="trtip">
								
							   <span id="partybname">${partybname}</span>
							   <span id="partybstaffnames">${partybstaffnames}</span>
                                <s:if test="staffidentitycardb!=null">
							      <span id="staffidentitycardb">(${staffidentitycardb})</span>
							   </s:if>
							</td>
							<td class="td_bg01">
								<c:choose>
									<c:when test='${outcome=="to Publish"}'>发至分发人</c:when>
									<c:when test='${outcome=="to Not Publish"}'>存档</c:when>
								</c:choose>
							</td>
			
							<td class="td_bg01">
								<span id="sealtime">${fn:substring(sealtime,0,19)}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/documentflow/ea_getSealDocList.jspa?pageNumber=${pageNumber}&search=${search}&finishType=sealyes">
				</c:param>
			</c:import>
		</div>
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>
