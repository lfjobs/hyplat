<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>岗位薪水调查</title>
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
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript">
         var payResearchID="";
            var basePath = "<%=basePath%>";
            var ppageNumber=${pageNumber};
            var psearch='${search}'; 
            var token=0;       
        </script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/office/SersonnelSystem/payresearch_list.js"></script>
		<style>
table {
	font-size: 10px;
}

.in {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

div { /*width:auto;*/
	width: 100%;
	height: 198px;
	position: absolute; <%--
	overFlow-x: hidden;
	overFlow-y: scroll; --%>
	overFlow-x: hidden;
	overFlow-y: auto;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
	</head>
	<body>
		<div>
			<table>
				<s:iterator value="pageForm.list">
					<tr>
						<td width="232">
							<table width="232" border="1">
								<tr>
									<td width="42">
										行业类别
									</td>
									<td colspan="3">
										<span id="industryCategory"><s:property
												value="industryCategory" /> </span>
									</td>
								</tr>
								<tr>
									<td>
										岗位
									</td>
									<td colspan="3">
										<span id="post"> <s:property value="post" /> </span>
									</td>
								</tr>
								<tr>
									<td width="42">
										工薪范围
									</td>
									<td width="60">
										<span id="salaryRange"> <s:property value="salaryRange" />
										</span>
									</td>
									<td width="42">
										工作年限
									</td>
									<td width="60">
										<span id="workingTenure"> <s:property
												value="workingTenure" /> </span>
									</td>
								</tr>
								<tr>
									<td>
										工作要求
									</td>
									<td colspan="3">
										<span id="jobRequirement"><s:property
												value="jobRequirement" /> </span>
									</td>
								</tr>
								<tr>
									<td>
										备注
									</td>
									<td colspan="3">
										<span id="remarks"><s:property value="remarks" /> </span>
										<span id="payResearchKey" style="display: none"><s:property
												value="payResearchKey" /> </span>
										<span id="payResearchID" style="display: none"><s:property
												value="payResearchID" /> </span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</body>
</html>

