<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>往来个人管理</title>
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
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/jquery.form.js"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/contactuser.js"></script>
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
		<div id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<table style="font-size: 10px;">
					<tr>
						<td align="center" style="font-size: 14px;" colspan="2">
							查询往来个人
						</td>
					</tr>
					<tr>
						<td align="right">
							姓名：
						</td>
						<td>
							<input name="contactUser.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							编号：
						</td>
						<td>
							<input name="contactUser.staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							个人身份证号：
						</td>
						<td>
							<input name="contactUser.staffIdentityCard" />
						</td>
					</tr>
					<tr>
						<td align="right">
							个人地址：
						</td>
						<td>
							<input name="contactUser.staffAddress" />
						</td>
					</tr>
					<tr>
						<td align="right">
							个人电话：
						</td>
						<td>
							<input name="contactUser.reference" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来关系：
						</td>
						<td>
							<s:select list="codeRelationList" listKey="codeValue"
								listValue="codeValue" name="contactUser.relation" theme="simple"
								headerKey="" headerValue="全部"></s:select>
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
			<!-- <div style="height:290px;" id="result"> -->
			<div id="result">
			</div>
			<div id="test" class="pageinfo">
			</div>
		</div>
	</body>
</html>