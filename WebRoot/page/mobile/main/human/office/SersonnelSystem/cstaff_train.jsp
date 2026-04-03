<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>入职管理</title>
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
		<script src="<%=basePath%>js/ea/human/cstaff.js">
		</script>
		<script
			src="<%=basePath%>js/ea/human/office/SersonnelSystem/cstaff_train.js"></script>
		<script>
            var ppageNumber = ${pageNumber};
               var basePath = "<%=basePath%>";
      var auditionID="";
      var  staffIdentityCard="${audition.staffIdentityCard}";
               var  sName="${audition.staffName}";
               var  search="${search}";
               var session_val = '${session_value}';
        </script>
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
	height: 466px;
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
				<s:if test="pageForm.list!=null">
					<s:iterator value="pageForm.list">
						<tr>
							<td width="236">
								<table border="1">
									<tr>
										<td width="44">
											人员姓名
										</td>
										<td colspan="3">
											<span id="staffName">${staffName}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											身份证
										</td>
										<td colspan="3">
											<span id="staffIdentityCard">${staffIdentityCard}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											状态
										</td>
										<td width="60">
											<span id="status">${status=='21'?'未报到入职':'已入职'}</span>
											<span></span>
										</td>
										<td width="44">
											应聘方向
										</td>
										<td width="60">
											<span id="auditionDirection">${auditionDirection}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											应聘岗位
										</td>
										<td colspan="3">
											<span id="auditionPost">${auditionPost}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											工作经验
										</td>
										<td colspan="3">
											<span id="experience">${experience}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											面试地点
										</td>
										<td colspan="3">
											<span id="place">${place}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											面试考官
										</td>
										<td>
											<span id="examiner">${examiner}</span>
											<span></span>
										</td>
										<td>
											面试时间
										</td>
										<td>
											<span id="auditionDate">${fn:substring(auditionDate,
												0, 10)}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											面试部门
										</td>
										<td>
											<span id="auditionDept">${auditionDept}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											评语
										</td>
										<td colspan="3">
											<span id="commention">${commention}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											分数
										</td>
										<td>
											<span id="auditionPoint">${auditionPoint}</span>
											<span></span>
										</td>
										<td>
											入职时间
										</td>
										<td>
											<span id="registerDate">${fn:substring(registerDate,
												0, 10)}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											报道地点
										</td>
										<td colspan="3">
											<span id="auditionPlace">${auditionPlace}</span>
											<span></span>
										</td>
									</tr>
									<tr>
										<td>
											备注
										</td>
										<td colspan="3">
											<span id="remark">${remark}</span>
											<span style="display: none" id="auditionID">${auditionID}</span>
											<span style="display: none" id="auditionKey">${auditionKey}</span>
											<span style="display: none" id="staffID">${staffID}</span>
											<span style="display: none" id="photo">${photo}</span>
											<span></span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<span>没有数据</span>
				</s:else>
			</table>
		</div>
	</body>
</html>
