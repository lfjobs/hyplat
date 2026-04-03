<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="docCommon.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>已发送拟稿列表</title>
		<script
			src="<%=basePath%>js/ea/office_ea/document/documentYesSendList.js"></script>
	</head>
	<body>

		<!--拟稿 -->

		<div id="draft">
			<table class="draft0">
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
						<th width="90" align="center">
							申报人部门
						</th>
						<th width="170" align="center">
							申报单位名称
						</th>
						<th width="170" align="center">
							签发(审批)单位
						</th>
						<th width="90" align="center">
							签发(审批)部门
						</th>
						<th width="70" align="center">
							签发(审批)人
						</th>

						<th class="trtip" width="170" align="center">
							合同有效期
						</th>
						<th class="trtip" width="300" align="center">
							甲方
						</th>
						<th class="trtip" width="300" align="center">
							乙方
						</th>	
						<th width="120" align="center">
							签发(审批)状态
						</th>
						
						<th width="150" align="center">
							报送时间
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator id="docList" value="pageForm.list">
						<tr class="docs" id="${docId}">
							<td class="td_bg01">
								<input type="radio" name="radioGroup" class="JQuerypersonvalue"
									value="${docId}" />
							</td>
							<td class="td_bg01">
								<span><%=number%></span>
							</td>
							<td class="td_bg01">
								<span id="docNum">${docNum}</span>
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
									onclick="getDocTrackRecord(this,'${docId}')" />
							</td>
							<td class="td_bg01">
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
							<td class="td_bg01">
								<c:choose>
									<c:when test='${emergencyType=="j"}'>急件</c:when>
									<c:when test='${emergencyType=="p"}'>普通</c:when>
									<c:when test='${emergencyType=="t"}'>特急</c:when>
									<c:otherwise>${emergencyType}</c:otherwise>
								</c:choose>

							</td>
							<td class="td_bg01">
								<span id="drafterName">${drafterName}</span>
							</td>
							<td class="td_bg01">
								<span id="postName">${postName}</span>
							</td>
							<td class="td_bg01">
								<span id="deptNameOfDraft">${deptNameOfDraft}</span>
							</td>
							<td class="td_bg01">
								<span id="companyName">${companyName}</span>
							</td>
							<td class="td_bg01">
								<span id="companyIDofSubscriber">${comNameofSub}</span>
							</td>
							<td class="td_bg01">
								<span id="deptIDofSubscriber">${deptNameofSub}</span>
							</td>
							<td class="td_bg01">
								<span id="subscriberID">${subscriberName}</span>
							</td>
							
							<td class="trtip">
								<s:if test="startValidity==null||startValidity==''">
							   无指定
							 </s:if>
								<s:else>
									<span>${fn:substring(startValidity,
										0,10)}至${fn:substring(endValidity, 0, 10)}</span>
								</s:else>

							</td>
							<td class="trtip">
								
							   <span id="partyAName">${partyAName}</span>
                               <span id="partyAstaffnames">${partyAstaffnames}</span>
                               <s:if test="staffIdentityCardA!=null">
							      <span id="staffIdentityCardA">(${staffIdentityCardA})</span>
							   </s:if>
                               

							</td>
							<td class="trtip">
								
							   <span id="partyBName">${partyBName}</span>
							   <span id="partyBstaffnames">${partyBstaffnames}</span>
							   <s:if test="staffIdentityCardB!=null">
							      <span id="staffIdentityCardB">(${staffIdentityCardB})</span>
							   </s:if>

							</td>
							<td class="td_bg01">
								<c:choose>

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
									<c:when test='${status=="I"}'>传阅中</c:when>
								</c:choose>
							</td>
			
							<td class="td_bg01">

								<span id="updateTime">${fn:substring(updateTime,0,19)}</span>
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
					value="ea/documentinfo/ea_getSendedDocList.jspa?pageNumber=${pageNumber}&search=${search}&searchType=${searchType}">
				</c:param>
			</c:import>
		</div>


		<form name="suggesForm" id="suggesForm" method="post">
			<div class="jqmWindow"
				style="width: 360px; height: 400px; right: 15%; top: 10%;"
				id="jqModelSugg">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					评论
					<div class="close">
					</div>
				</div>
				<div
					style="width: 360px; height: 380px; overflow: auto; margin-top: 10px;">

					<table width="350" id="tablesug" cellpadding="5">
					</table>
					<input type="text" id="htt" />

				</div>
		</form>
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>
