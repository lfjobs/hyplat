<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../document/docCommon.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<title>位置查询</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/validate.css" />
		<script
			src="<%=basePath%>js/ea/office_ea/docUnfinish/docPositionList.js"></script>


		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />

		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
         var basePath='<%=basePath%>';
         var  pNumber =${pageNumber};  
         var type='${type}';
         var  search='${search}';
         </script>

	</head>
	<body>


		<div id="wsp">
			<table class="draft0">
				<thead>
					<tr>
						<th width="40" align="center">
							序号
						</th>
						<th width="70" align="center">
							公文编号
						</th>
						<th width="200" align="center">
							公文标题
						</th>
						<th width="70" align="center">
							所属人
						</th>
						<th width="200" align="center">
							所在公司
						</th>
						<th width="70" align="center">
							所在部门
						</th>
						<th width="100" align="center">
							所在模块
						</th>
						<th width="100" align="center">
							所在位置
						</th>
						<th width="100" align="center">
							文件
						</th>

					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr class="docs" id="${posId}">
							
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
								<span id="staffName">${staffName}</span>
							</td>
							<td class="td_bg01">

								<span id="companyName">${companyName}</span>
							</td>
							<td class="td_bg01">
								<span id="department">${department}</span>
							</td>
							<td class="td_bg01">
								<span id="module">${module}</span>
							</td>

							<td class="td_bg01">
								<span id="position">${position}</span>
							</td>
							<td class="td_bg01">
								<span ><a href="#" onclick="OpenOffice2('${filePath}','${fileType}',1,'查看')">查看</a></span>
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
					value="ea/docunfinish/ea_getDocumentPosition.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}">
				</c:param>
			</c:import>
		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>
