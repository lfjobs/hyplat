<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../office_ea/document/docCommon.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>项目合同管理</title>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/doc.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_contract.js"></script>

		<script type="text/javascript">
         var basePath='<%=basePath%>';
         var  pNumber ="${pageNumber}";  
         var  search='${search}';
         var docId = "";
         var module = '<%=session.getAttribute("module")%>';  
         var projectCode= '${productPack.ppID}';
         var projectName="${param.projectName}";
        
         var type = "${type}";
         var ghua = "${param.ghua}";
         
         </script>

		<style type="text/css">
.track {
	display: none;
	height: auto;
	width: 200px;
	white-space: normal;
}

#socialJqm {
	width: 80%;
}
</style>

	</head>
	<body>

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
						<th width="70" align="center">
							公文编号
						</th>
						<th width="170" align="center">
							正式编号
						</th>
						<th class="thtitle" width="200" align="center">
							文件标题
						</th>
						<th width="70" align="center">
							主题词
						</th>
						<th width="70" align="center">
							跟踪
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
						<th width="130" align="center">
							公文类型
						</th>
						<th width="70" align="center">
							缓急
						</th>
						<th class="trtip1" width="170" align="center">
							合同有效期
						</th>	
						<th class="trtip1" width="170" align="center">
							甲方
						</th>
						<th class="trtip1" width="170" align="center">
							乙方
						</th>	
						<th width="100" align="center">
							公文签发状态
						</th>
						<th width="150" align="center">
							起草时间
						</th>
						
						
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr class="docs" id="${docId}">
							<td class="td_bg01">
								<input type="radio" name="checkGroup" class="JQuerypersonvalue"
									 value="${docId}" />
							</td>

							<td class="td_bg01">
								<span><%=number%></span>
							</td>

							<td class="td_bg01">
								<span id="docNum">${docNum}</span>
							</td>
							<td class="td_bg01">
								<span id="formalNum">${formalNum}</span>

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
									<c:otherwise>特急</c:otherwise>
								</c:choose>
							</td>
							<td class="trtip1">
								<s:if test="startValidity==null||startValidity==''">
							   无指定
							 </s:if>
								<s:else>
									<span>${fn:substring(startValidity,
										0,10)}至${fn:substring(endValidity, 0, 10)}</span>
								</s:else>

							</td>	
							<td class="trtip1">
								
							   <span id="partyAName">${partyAName}</span>
                               <span id="partyAstaffname">${partyAstaffname}</span>
                               <span id="partyAstaffnames">${partyAstaffnames}</span>

							</td>
							<td class="trtip1">
								
							   <span id="partyBName">${partyBName}</span>
							   <span id="partyBstaffname">${partyBstaffname}</span>
							   <span id="partyBstaffnames">${partyBstaffnames}</span>

							</td>
							<td class="td_bg01">
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
							<td class="td_bg01">
								<span id="draftTime">${fn:substring(draftTime,0,19)}</span>
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
					value="ea/promanage/ea_getContractByProject.jspa?pageNumber=${pageNumber}&search=${search}&productPack.ppID=${projectManage.projectCode}&type=${type}">
				</c:param>
			</c:import>
		</div>
		<form id="searchForms" name="searchForms">
			<div class="jqmWindow" style="width:500px; right: 25%; top: 10%"
				id="jqModelSearch1">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					<div class="search">
						查询
					</div>
					
					<div class="close">
					</div>
				</div>
				<center>
					<table width="100%" cellspacing="10" id="templateSearchtab">
						<tr>
							<td align="right">
								公文标题：
							</td>
							<td align="left">
								<input type="text" name="document.title" style="width: 175px;" id="titless"/>
							
							</td>
						</tr>
						
						
						<tr>

							<td colspan="4" align="center">
								<input type="button" value="  查询  " class="input-button search"
									id="tosearch" />
								
								<input name="search" type="hidden" value="search" />
								<input name="productPack.ppID" type="hidden" value="${projectManage.projectCode}" />
								<input name="type" type="hidden" value="${type}" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>
				<iframe name="hidden" width="100%" height="0"></iframe>
		
		<!-- 显示人员 -->
		
		<div id="socialJqm" class="jqmWindow"
			style="width: 75%; height: 350px; absolute; display: none; left: 20%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="type" value="" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="300px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 28px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 300px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>

	</body>
</html>
