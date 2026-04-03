<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="docCommon.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="no-cache" />
		<title>未审批公文</title>
		<script
			src="<%=basePath%>js/ea/office_ea/document/documentApproveno.js?version=20210220"></script>


	</head>
	<body>
		<!--未审批公文 -->

		<div id="wspv">
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

						<th class="trtip" width="170" align="center">
							合同有效期
						</th>
						<th class="trtip" width="300" align="center">
							甲方
						</th>
						<th class="trtip" width="300" align="center">
							乙方
						</th>	

						<th width="150" align="center">
							报送时间
						</th>
						<th width="90" align="center">
							发件人
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator id="doc" value="pageForm.list">
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
								<span id="title">${title}</span>
							</td>
							<td class="td_bg01">
								<span id="theme">${theme}</span>
							</td>
							<td class="td_bg01" style="">
								<img src="<%=basePath%>images/ea/office/document/time16.png"
									style="cursor: hand;" width="16" height="16" onclick="getDocTrackRecord(this,'${docid}')" />
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
								<span id="docId" style="display: none">${docId}</span>
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
								<span id="updatetime">${fn:substring(updatetime,0,19)}</span>
							</td>
							<td class="td_bg01">
								<span id="fromMember">${fromname}</span>
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
					value="ea/documentflow/ea_getExamineDocList.jspa?pageNumber=${pageNumber}&search=${search}&finishType=examine">
				</c:param>
			</c:import>
		</div>
		

		<form id="examineForm" name="examineForm">
			<div class="jqmWindow" id="jqModelexamine">
				<input type="submit" name="submit" style="display: none" />
			    <input type="hidden" name="document.docId" id = "docID" value=""/>
			    <input type="hidden" name="jump" id = "jump2" value=""/>
                 
			</div>
		</form>
		


		<iframe name="hidden" width="0" height="0"></iframe>
		<!-- 显示人员 -->
		<div id="socialJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		
		
		
	 	<div class="jqmWindow" id="jqModelseal"
				style="display: none; width: 250px; height: 100px; right: 30%; top: 10%;"
				id="jqModelPosition">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					提示
					<div class="close">
					</div>
				</div>
				<center>
				<p>确定转至自己盖章？</p>
				<input  type="button" class="confirm input-button" value=" 确定 "/>
			    <input  type="button" class="cancel  input-button" value=" 取消   "/>
			    </center>

			</div>
	</body>
</html>
