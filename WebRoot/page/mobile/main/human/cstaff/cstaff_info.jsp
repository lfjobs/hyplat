<%@page import="org.apache.struts2.ServletActionContext"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人员列表</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String filepath = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/cstaff_info.js"></script>
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/jquery.form.js"></script>	
		<link href="<%=basePath%>js/jquerypager/pager.css" type="text/css"
			rel="stylesheet"></link>
		<script type="text/javascript">
	var basePath = '<%=basePath%>';
	var pbasePath = '<%=basePath%>';
	var pageNumber = 1;
	/*var ppageNumber = ${pageNumber};*/
	var psearch = '${search}';
	var pstaffID = '${staffID}';
	var retoken = 0;
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
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
			<div class="jqmWindow" style="width: 260px; right: 25%; top: 10%"
				id="jqModelSearch">
				<table id="cataffSearchTable">
					<tr>
						<td align="center" colspan="2">
							查询条件
						</td>
					</tr>
					<tr>
						<td width="60">
							人员姓名：
						</td>
						<td>
							<input name="searchCStaff.staffName" />
						</td>
					</tr>
					<tr>
						<td width="60">
							身份证：
						</td>
						<td>
							<input name="searchCStaff.staffIdentityCard" />
						</td>
					</tr>
					<tr>
						<td height="40">
							家庭地址：
						</td>
						<td class="JQueryaddresssearch">
							<input name="searchCStaff.address" id="address" type="hidden" />
							<input name="searchCStaff.staffAddress" id="staffAddress"
								type="hidden" />
							<select name="addressProvince" id="province" number='0'
								style="width: 110px;"></select>
							<select name="addressCity" id="city" number='1'
								style="width: 110px; display: none"></select>
							<select name="addressCounty" id="county" number='2'
								style="width: 110px; display: none"></select>
							<select name="addressTown" id="addressTown" number='3'
								style="width: 110px; display: none"></select>
							<select name="addressVillage" id="addressVillage" number='4'
								style="width: 110px; display: none"></select>
							<select name="addressCommunity" id="addressCommunity" number='5'
								style="width: 110px; display: none;"></select>
							<select name="addressFloor" id="addressFloor" number='6'
								style="width: 110px; display: none"></select>
							<select name="addressLayer" id="addressLayer" number='7'
								style="width: 110px; display: none"></select>
							<select name="addressSize" id="addressSize" number='8'
								style="width: 110px; display: none"></select>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button" id="searchStaff"
								value=" 查询 " />
							<input type="button" class="input-button JQueryreturn" value="取消" />
							<input name="search" type="hidden" value="search" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		<div>
			<!-- <div style="height:330px;" id="result"> -->
			<div id="result">
			</div>
			<div id="test" class="pageinfo">
				<input type="hidden" id="PageIndex" />
			</div>
		</div>
	</body>
</html>
