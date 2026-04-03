<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>离职员工管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
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
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/cstaff_dimission.js"></script>
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/jquery.form.js"></script>
		<link href="<%=basePath%>js/jquerypager/pager.css" type="text/css"
			rel="stylesheet"></link>
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			var pageNumber = 1;
        </script>
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
		<form name="cstaffDimForm" id="cstaffDimForm" method="post">
		<input type="submit" name="submit" style="display:none"/>
		<div id="jqModelSearch">
			<table>
				<tr>
					<td>
						人员编号：
					</td>
					<td>
						<input type="text" name="codi.staffCode" />
					</td>
				</tr>
				<tr>
					<td>
						人员姓名：
					</td>
					<td>
						<input type="text" name="codi.staffName" />
					</td>
				</tr>
				<tr>
					<td>
						身份证：
					</td>
					<td>
						<input type="text" name="codi.staffIdentityCard" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="input-button" id="searchDim"
							value="查询" />
						<!-- <input type="button" class="input-button JQueryreturn" value="取消" /> -->
						<input name="search" type="hidden" value="search" />
					</td>
				</tr>
			</table>
		</div>
		</form>
		<div>
			<div id="result"></div>
			<div id="test" class="pageinfo"></div>
		</div>
	</body>
</html>
