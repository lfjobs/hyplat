<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>合同台账</title>
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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>


		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>

		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script
			src="<%=basePath%>js/ea/office_ea/archives/contractAccountList.js"></script>


		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber ='${pageNumber}';  
         var  search='${search}';
         var  token=0;
         var locationid= "";
         var type = '${type}';
         var pageForm='${pageForm}';
         alert(pNumber)
         
		</script>

		<style type="text/css">
.table td {
	white-space: nowrap;
	border-right: none;
}

a {
	text-decoration: none;
}
</style>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="60" align="center">
							序号
						</th>
						<th width="90" align="center">
							姓名
						</th>
						<th width="90" align="center">
							性别
						</th>
						<th width="200" align="center">
							身份证号
						</th>
						<th width="150" align="center">
							入职日期
						</th>
						<th width="150" align="center">
							合同签订日期
						</th>
						<th width="90" align="center">
							合同编号
						</th>
						<th width="150" align="center">
							合同首签起日期
						</th>
						<th width="150" align="center">
							合同首签止日期
						</th>
						<th width="150" align="center">
							合同续签、终止日期
						</th>
						<th class="dismiss" width="150" align="center">
							解除、终止合同日期
						</th>
						<th class="dismiss" width="150" align="center">
							离职类型
						</th>


					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator  value="pageForm.list">
						<tr>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span>${staffname}</span>
							</td>
							<td>
								<span>${sex}</span>
							</td>
							<td>
								<span>${staffidentitycard}</span>
							</td>
							<td>
								<span>${fn:substring(registerdate,0,10)}</span>
							</td>
							<td>
								<span>${fn:substring(contractsigndate,0,10)}</span>
							</td>
							<td>
								<span>${contractcode}</span>
							</td>
							<td>
								<span>${fn:substring(startvalidity,0,10)}</span>
							</td>
							<td>
								<span>${fn:substring(endvalidity,0,10)}</span>
							</td>
							<td>
								<span>${fn:substring(renewaldate,0,10)}</span>
							</td>
							<td class="dismiss">
								<span>${dimissiondate}</span>
							</td>
							<td class="dismiss">
								<span>${dimissionstatus}</span>
							</td>



						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/archive/ea_getContractParamList.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}">
				</c:param>
			</c:import>
		</div>


		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="100%" cellpadding="5" cellspacing="10"
					id="cataffSearchTable">
					<tr>
						<td align="right">
							员工姓名：
						</td>
						<td align="left">
							<input type="text" name="staffName" id="staffname" readonly="readonly"/>
							<input type="hidden" name="archiveTemp.staffID" id="staffID"/>
							<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
								onclick="importGY('ea/documentcommon/ea_getSocialInfoList.jspa')" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button" id="tosearch"
								value=" 查询 " />
							<input name="search" type="hidden" value="search" />
							<input name="type" type="hidden" value="${type}" />
						</td>

					</tr>
				</table>
			</form>
		</div>
			<div id="socialJqm" class="jqmWindow"
			style="width: 75%; height: 250px; absolute; display: none; left: 15%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="210px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 28px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>