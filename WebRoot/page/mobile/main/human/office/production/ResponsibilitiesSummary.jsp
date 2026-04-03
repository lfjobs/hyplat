<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>岗位职责汇总列表</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
			src="<%=basePath%>js/jquerypager/ResponsibilitiesSummary.js"></script>
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/jquery.form.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>	
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>js/jquerypager/pager.css" type="text/css"
			rel="stylesheet"></link>
		<script type="text/javascript">
   			function InitPager(RecordCount, PageIndex) {
    			$("#test").setPager({ RecordCount: RecordCount, PageIndex: PageIndex, buttonClick: PageClick });
   			};
			PageClick = function(RecordCount, PageIndex) {
    			InitPager(RecordCount, PageIndex);
   			};
        </script>	
		<script>
			var basePath = "<%=basePath%>";
			pageNumber = 1;
			var psearch = '${search}';
			var responsibilitiesID = "";
			var treeID = '<%=session.getAttribute("organizationID")%>';
			var tlist = "${stafflist[0].staffID}";
		</script>
	</head>
	<body>
		<div class="jqmWindow" id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<table id="cataffSearchTable">
					<tr>
						<td align="center" colspan="2">
							查询信息
						</td>
					</tr>
					<tr>
						<td align="right">
							员工编号：
						</td>
						<td>
							<input name="staffResponsibilities.staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							员工姓名：
						</td>
						<td>
							<input name="staffResponsibilities.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							岗位名称：
						</td>
						<td>
							<input name="staffResponsibilities.postName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td>
							<select id="deptID" name="staffResponsibilities.departmentID">
							</select>
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
			</form>
		</div>
		<div>
			<div id="result"></div>
			<div id="test" class="pageinfo"></div>
		</div>
	</body>
</html>