<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>部门岗位设置</title>
		<style type="text/css">
.xx {
	color: #FF0000;
	margin-right: 2px;
} 
</style>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script
			src="<%=basePath%>js/ea/human/office/production/orgPosts_list.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var depPostID = '';
  		var search='${search}';
		var notoken = 0;
		var pNumber = '${pageNumber}';
		var token=0;
		var select =1;
		var ogID = '${departmentPost.organizationID}';
		var ogName = '<%=request.getParameter("ogName") %>';
		</script>
		
	</head>
	<body>
		<form name="orgPostForm" id="orgPostForm" method="post">
			<div id="main_main">
			<input type="submit" name="submit" style="display: none" />
				<input type="hidden" id="thisdate" />
				<table class="JQueryflexme">
					<thead>
						<tr class="tablewith">
							<th width="30" align="center">
								选择
							</th>
							<th width="55" align="center">
								序号
							</th>
							<th width="100" align="center">
								职务名称
							</th>
							<th width="55" align="center">
								职务编号
							</th>
							<th width="55" align="center">
								编员人数
							</th>
							<th width="80" align="center">
								岗位定员
							</th>
							<th width="200" align="center">
								岗位职责
							</th>
							<th width="120" align="center">
								任职要求
							</th>
							<th width="100" align="center">
								备注
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody id="tbwid">
						<input type="hidden" id="start" />
						<input type="hidden" id="end" />
						<tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="" />
								<input name="depPostID" type="hidden" value="" />
							</td>
							<td class="td_bg01">
								<input name="" id="" value="自动生成" readonly="readonly"/>
							</td>
							<td class="td_bg01">
								<input name="postName" id="postName"  />
							</td>
							<td class="td_bg01">
								<input name="postNum" id="postNum" value="自动生成" readonly="readonly"/>
							</td>
							<td class="td_bg01">
								<input name="adminNum" id="adminNum" value="自动生成" readonly="readonly"/>
							</td>
							<td class="td_bg01">
								<input name="postSureNum"id="postSureNum"  class="isNaN"/>
							</td>
							<td class="td_bg01">
								<input name="postResponsibility" id="postResponsibility"/>
							</td>
							<td class="td_bg01">
								<input name="responsibilityRequire" id="responsibilityRequire" />
							</td>
							<td class="td_bg01">
								<input name="remark" id="remark" />
							</td>
						</tr>
						<c:forEach var='arr' items="${pageForm.list}">
							<tr id="${arr[0]}" class="td_bg01 saveAjax trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${arr[0]}" />
									<input name="depPostID" type="hidden"value="${arr[0]}" />
								</td>
								<td class="td_bg01">
									<%=number%>
								</td>
								<td class="td_bg01">
									<span id="postName">${arr[1]}</span>
									<input class="model1" value="${arr[1]}" name="postName"/>
								</td>
								<td class="td_bg01">
									<span id="postNum">${arr[2] }</span>
									<input class="model1" value="${arr[2] }" name="postNum" id="postNum" readonly="readonly"/>
								</td>
								<td class="td_bg01">
									<span id="adminNum">${arr[3]==null ? "0" : arr[3]}</span>
										<input class="model1 " value="${arr[3]==null ? '0' : arr[3]}" name="adminNum"  readonly="readonly"/>人
								</td>
								<td class="td_bg01">
									<span id="postSureNum">${arr[4]==null ? "0" : arr[4]}</span>
									<input class="model1 isNaN" value="${arr[4]==null ? '0' : arr[4]}" name="postSureNum" />人
								</td>
								<td class="td_bg01">
									<span id="postResponsibility">${arr[5]}</span>
									<input class="model1" value="${arr[5]}" name="postResponsibility" size="5"/>
								</td>
								<td class="td_bg01">
									<span id="responsibilityRequire">${arr[6] }</span>
									<input class="model1" value="${arr[6] }" name="responsibilityRequire" size="5"/>
									<input name="depPostKey" type="hidden"value="${arr[7]}" />
									<input name="leveloneOrgID" id="leveOID" type="hidden"value="${arr[8]}" />
									<input name="companyID" type="hidden"value="${arr[10]}" />
									<input name="organizationID" type="hidden"value="${arr[11]}" />
								</td>
								<td class="td_bg01">
									<span id="remark">${arr[9]}</span>
									<input class="model1" value="${arr[9]}" name="remark" />
								</td>
							</tr>
							<%
								number++;
							%>
						</c:forEach>
					</tbody>
				</table>
				<c:import url="../../../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/departmentpost/ea_getOrgPostListByOrg.jspa?ogName=${param.ogName}&departmentPost.organizationID=${departmentPost.organizationID }&pageNumber=${pageNumber}">
					</c:param>
				</c:import>
			</div>
		</form>
		<iframe name="hidden" height="0" scrolling="no" frameBorder="0"></iframe> 
		<iframe src="" name="main" scrolling="no" style="width:100%;height:0;"
						marginwidth="0" marginheight="0" frameborder="0"
						id="mainframe" border="0" framespacing="0" noresize="noResize"></iframe> 
		<!-- 查询信息 -->
		<form name="orgPostSearchForm" id="orgPostSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="orgPostSearchTable">
					
					<tr>
						<td style="width:100px;height: 40px;text-align: right;">
							职务名称：
						</td>
						<td>
							<input name="departmentPost.postName" />
						</td>
					</tr>
					<tr>
						<td style="width:100px;text-align: right;">
							职务编号：
						</td>
						<td>
							<input name="departmentPost.postNum" />
						</td>
					</tr>
				</table>
				<div style="text-align: center;">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>