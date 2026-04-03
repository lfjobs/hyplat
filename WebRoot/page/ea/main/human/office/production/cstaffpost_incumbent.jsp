<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人事生产-人员列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/ea/human/staff_info.js">
		</script>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script
			src="<%=basePath%>js/ea/human/office/production/cstaff_incumbent.js">
		</script>
		<script type="text/javascript">
            var basePath = "<%=basePath%>";
            var ppageNumber = '${pageNumber}';
            var staffName='${searchCStaff.staffName}';
            var psearch = '${search}';
            var retoken = 0;
            var token = 0;
            var notoken = 0;
        </script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexmepost">
				<thead>
					<tr class="tablewith">
					<!--  
						<th width="40" align="center">
							选择
						</th>
					-->
						<th width="100" align="center">
							人员编号
						</th>
						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							部门名称
						</th>
						<th width="100" align="center">
							岗位名称
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="80" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="80" align="center">
							国籍
						</th>
						<th width="80" align="center">
							籍贯
						</th>
						<th width="80" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<!--  
						<th width="200" align="center">
							身份证地址
						</th>
						<th width="100" align="center">
							电话
						</th>
						<th width="100" align="center">
							qq
						</th>
						<th width="100" align="center">
							邮箱
						</th>
						<th width="100" align="center">
							录入时间
						</th>
						<th width="100" align="center">
							政治面貌
						</th>
						<th width="100" align="center">
							文化程度
						</th>
						<th width="100" align="center">
							婚姻状况
						</th>
						<th width="100" align="center">
							健康状况
						</th>
						<th width="100" align="center">
							银行帐号
						</th>
						<th width="100" align="center">
							备注
						</th>
						-->
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<c:forEach var="arr" items="${pageForm.list}">
						<!--  
						<tr id="${arr[0]}"">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[0]}" />
							</td>
						-->
						<tr>
							<td>
								<span id="staffCode">${arr[1]}</span>
							</td>
							<td>
								<span id="recordCode">${arr[2]}</span>
							</td>
							<td>
								<span id="staffName">${arr[3]}</span>
							</td>
							<td>
								<span id="organizationName">${arr[4]}</span>
							</td>
							<td>
								<span id="postName">${arr[5]}</span>
							</td>
							<td>
								<span id="usedNmae">${arr[6]}</span>
							</td>
							<td>
								<span id="sex">${arr[7]}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${arr[8]}</span>
							</td>
							<td>
								<span id="nationality">${arr[9]}</span>
							</td>
							<td>
								<span id="nativePlace">${arr[10]}</span>
							</td>
							<td>
								<span id="nation">${arr[11]}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${arr[12]}</span>
								<span style="display: none" id="staffID">${arr[0]}</span>
							</td>
							<!--  
							<td>
								<span id="staffAddress">${arr[13]}</span>
							</td>
							<td>
								<span id="reference">${arr[14]}</span>
							</td>
							<td>
								<span id="referenceCode">${arr[15]}</span>
							</td>
							<td>
								<span id="referenceOrganization">${arr[16]}</span>
							</td>
							<td>
								<span id="verifyTime" class="datas">${arr[17]}</span>
							</td>
							<td>
								<span id="politicsStatus">${arr[18]}</span>
							</td>
							<td>
								<span id="culturalDegree">${arr[19]}</span>
							</td>
							<td>
								<span id="marriage">${arr[20]}</span>
							</td>
							<td>
								<span id="health">${arr[21]}</span>
							</td>
							<td>
								<span id="bankNum">${arr[22]}</span>
							</td>
							<td>
								<span id="staffDesc">${arr[23]}</span>
							</td>
							-->
						</tr>
						<%
							number++;
						%>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/soincumbent/ea_historyOfPost.jspa?pageNumber=${pageNumber}&staffID=${staffID}&searchCStaff.staffName=${searchCStaff.staffName}">
				</c:param>
			</c:import>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>