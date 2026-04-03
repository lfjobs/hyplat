<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="docCommon.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>回收站</title>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />

		<script
			src="<%=basePath%>js/ea/office_ea/document/docRecycleBinList.js"></script>
       

	</head>
	<body>
		<!--回收站 -->

		<div id="wsp">
			<table class="wspdoc" id="tablerecyle">
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
						<th width="200" align="center">
							删除时间
						</th>
						<th width="70" align="center">
							删除位置
						</th>
						<th width="70" align="center">
							跟踪
						</th>

					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator id="list" value="pageForm.list">
						<tr class="docs" id="${list[5]}">
							<td class="td_bg01">
								<input type="radio" name="radioGroup" class="JQuerypersonvalue"
									id="delId" value="${list[5]}" />
								<input type="hidden" value="${list[0]}" id="docId" />
								<input type="hidden" value="${list[6]}" id="delkey" />
							</td>

							<td class="td_bg01">
								<span><%=number%></span>
							</td>

							<td class="td_bg01">
								<span id="docNum">${list[8]}${list[1]}</span>
							</td>
							<td class="td_bg01">
								<span id="title">${list[2]}</span>
							</td>
							<td class="td_bg01">
								<span id="theme">${list[3]}</span>
							</td>
							<td class="td_bg01">
								<span id="operateTime">${fn:substring(list[4],0,19)}</span>

							</td>
							<td class="td_bg01">
								<c:choose>
									<c:when test='${list[7]=="examine"}'>已审批</c:when>
									<c:when test='${list[7]=="seal"}'>已盖章</c:when>
									<c:when test='${list[7]=="publish"}'>已分发</c:when>
									<c:when test='${list[7]=="read"}'>已阅读</c:when>
									<c:when test='${list[7]=="draft"}'>个人拟稿箱</c:when>
									<c:when test='${list[7]=="cg"}'>草稿箱</c:when>
									<c:when test='${list[7]=="reject"}'>驳回列表</c:when>
									<c:when test='${list[7]=="yessend"}'>已发送</c:when>
									<c:when test='${list[7]=="guidang"}'>已归档</c:when>
									<c:when test='${list[7]=="share"}'>共享池</c:when>
								</c:choose>
							</td>
							<td class="td_bg01">
								<img src="<%=basePath%>images/ea/office/document/time16.png"
									style="cursor: hand;" width="16" height="16" onclick="getDocTrackRecord(this,'${list[0]}')" />
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
					value="ea/documentcommon/ea_getRecycelBinList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>
