<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>工作计划汇总</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/jobplansummary.js"></script>
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/jquery.form.js"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>js/jquerypager/pager.css" type="text/css"
			rel="stylesheet"></link>
		<SCRIPT type="text/javascript">
			var addressID = '';
			var basePath = '<%=basePath%>';
			var pageNumber = 1;
			var psearch = '${search}';
			var pstaffID= '${staffID}';
		</SCRIPT>
		<script type="text/javascript">
   			function InitPager(RecordCount, PageIndex) {
    			$("#test").setPager({ RecordCount: RecordCount, PageIndex: PageIndex, buttonClick: PageClick });
   			};
			PageClick = function(RecordCount, PageIndex) {
    			InitPager(RecordCount, PageIndex);
   			};
        </script>
	</head>
	<body>
		<form name="postSearchForm" id="postSearchForm" method="post">
			<div id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<table id="cataffSearchTable">
					<tr>
						<td align="center" colspan="2">
							查询信息
						</td>
					</tr>
					<tr>
						<td align="right">
							人员姓名：
						</td>
						<td>
							<input name="jobPlanVO.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							起始日期：
						</td>
						<td>
							<input name="jobPlanVO.startDate" class="put3"
								onfocus="date(this);" />
						</td>
					</tr>
					<tr>
						<td align="right">
							结束日期：
						</td>
						<td>
							<input name="jobPlanVO.endDate" class="put3"
								onfocus="date(this);" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button" id="tosearch"
								value=" 查询 " />
							<input name="search" type="hidden" value="search" />
						</td>
					</tr>
				</table>
			</div>
			<div>
				<div id="result"></div>
				<div id="test" class="pageinfo"></div>
			</div>
	</body>
</html>
