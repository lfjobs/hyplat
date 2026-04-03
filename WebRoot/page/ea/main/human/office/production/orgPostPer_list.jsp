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
		<title>部门岗位--部门人员</title>
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
			src="<%=basePath%>js/ea/human/office/production/orgPostPer_list.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var depPostID = '${departmentPost.depPostID}';
  		var search='${search}';
		var notoken = 0;
		var pNumber = '${pageNumber}';
		var token=0;
		var starPer = '${starPer}';
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
							<th width="55" align="center">
								序号
							</th>
							<th width="100" align="center">
								职务名称
							</th>
							<th width="80" align="center">
								员工编号
							</th>
							<th width="80" align="center">
								员工姓名
							</th>
							<th width="80" align="center">
								岗位状态
							</th>
							<th width="50" align="center">
								性别
							</th>
							<th width="80" align="center">
								籍贯
							</th>
							<th width="80" align="center">
								国籍
							</th>
							<th width="150" align="center">
								身份证
							</th>
							<th width="100" align="center">
								电话
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody id="tbwid">
						<c:forEach var='arr' items="${pageForm.list}">
							<tr id="${arr[0]}" class="td_bg01 saveAjax trclass">
								<td class="td_bg01">
									<%=number%>
								</td>
								<td class="td_bg01">
									<span id="postName">${arr[1]}</span>
								</td>
								<td class="td_bg01">
									<span id="">${arr[2] }</span>
								</td>
								<td class="td_bg01">
									<span id="">${arr[3]}</span>
								</td>
								<td class="td_bg01">
									<span id="">${arr[4]=='00' ? "兼岗" : "专岗"}</span>
								</td>
								<td class="td_bg01">
									<span id="">${arr[5]}</span>
								</td>
								<td class="td_bg01">
									<span id="">${arr[6] }</span>
								</td>
								<td class="td_bg01">
									<span id="">${arr[7] }</span>
								</td>
								<td class="td_bg01">
									<span id="">${arr[8] }</span>
								</td>
								<td class="td_bg01">
									<span id="">${arr[9]}</span>
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
						value="ea/departmentpost/ea_getListPerson.jspa?departmentPost.depPostID=${departmentPost.depPostID}&pageNumber=${pageNumber}&search=${search}&starPer=${starPer }">
					</c:param>
				</c:import>
			</div>
		</form>
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
							员工编号：
						</td>
						<td>
							<input name="staff.staffCode" />
						</td>
					</tr>
					<tr>
						<td style="width:100px;text-align: right;">
							员工姓名：
						</td>
						<td>
							<input name="staff.staffName" />
						</td>
					</tr>
					<tr>
						<td style="width:100px;text-align: right;">
							岗位状态：
						</td>
						<td>
							<select name="starPer" style="width:100px;text-align: right;">
								<option value="">---请选择---</option>
								<option value="01">专岗</option>
								<option value="00">兼岗</option>
							</select>
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