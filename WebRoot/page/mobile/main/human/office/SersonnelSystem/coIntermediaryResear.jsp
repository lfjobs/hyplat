<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中介调查列表</title>
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
		<script
			src="<%=basePath%>js/ea/human/office/SersonnelSystem/coIntermediaryResear.js"></script>
		<script type="text/javascript">
   var intermediaryResearchID = "";
   var  basePath='<%=basePath%>';           
   var  search='${search}';  
   var  pNumber =${pageNumber};  
   var  token = 0; 
   var notoken = 0;
var retoken=0;     	
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
	height: 236px;
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
		<script LANGUAGE="JavaScript">
    </script>
		<div>
			<table>
				<s:iterator value="pageForm.list">
					<tr>
						<td>
							<table border="1" class="in">
								<tr>
									<td width="44">
										单位名称
									</td>
									<td colspan="3">
										<span id="companyName">${companyName}</span>
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										单位地点
									</td>
									<td colspan="3">
										<span id="companyAddress">${companyAddress}</span>
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										招聘岗位
									</td>
									<td colspan="3">
										<span id="invitePost">${invitePost}</span>
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										上班时间
									</td>
									<td width="60">
										<span id="StartworkDate" class="datas">${StartworkDate}</span>
										<span></span>
									</td>
									<td width="44">
										下班时间
									</td>
									<td width="60">
										<span id="offdutyDate" class="datas">${offdutyDate}</span>
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										主要人才
									</td>
									<td colspan="3">
										<span id="mainTalents">${mainTalents}</span>
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										备注
									</td>
									<td colspan="3">
										<span id="remark">${remark}</span>
										<span style="display: none" id="address">${address}</span>
										<span style="display: none" id="intermediaryResearchID">${intermediaryResearchID}</span>
										<span style="display: none" id="intermediaryResearchKey">${intermediaryResearchKey}</span>
										<span></span>
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