<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>往来单位管理</title>
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
			src="<%=basePath%>js/jquerypager/contactconnection.js"></script>
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
		<div class="jqmWindow" id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<table style="font-size: 10px;">
					<tr>
						<td align="center" style="font-size: 14px;" colspan="2">
							查询往来单位
						</td>
					</tr>
					<tr>
						<td align="right">
							单位名称：
						</td>
						<td>
							<input name="cview.companyName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单位责任人：
						</td>
						<td>
							<input name="cview.cresponsible" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单位地址：
						</td>
						<td>
							<input name="cview.companyAddr" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单位往来关系：
						</td>
						<td>
							<s:select list="%{#request.connectionlist}"
								id="contactConnections" listKey="codeValue"
								listValue="codeValue" name="cview.contactConnections"
								theme="simple" headerKey="" headerValue="全部"></s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							行业类别：
						</td>
						<td>
							<s:select list="%{#request.typelist}" id="industryType"
								listKey="codeValue" listValue="codeValue"
								name="cview.industryType" theme="simple" headerKey=""
								headerValue="全部"></s:select>
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
			<!-- <div style="height:170px;" id="result"> -->
			<div id="result">
			</div>
			<div id="test" class="pageinfo">
				<input type="hidden" id="PageIndex" />
			</div>
		</div>
	</body>
</html>