<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>公司项目计划预算汇总</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
			<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<style type="text/css">
		.xx{
			color:#FF0000;
			margin-right:2px;} 
			.table-d table{ background:#99bbe8}
.table-d table td{ background:#FFF}
		</style>
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var pNumber =${pageNumber}; 
		var token=0;
		</script>
		<script src="<%=basePath%>js/ea/office_ea/projectplanbudget.js"></script>
	</head>
	<body>
		<form name="bottleForm" id="bottleForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="200" align="center">
								项目名称
							</th>
							<th width="200" align="center">
								公司名称
							</th>
							<th width="150" align="center">
								部门名称
							</th>
							<th width="100" align="center">
								负责人
							</th>
							<th width="100" align="center">
								项目内容
							</th>
							<th width="100" align="center">
								项目开始时间
							</th>
							<th width="100" align="center">
								项目结束时间
							</th>
							
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<c:forEach var='arr' items="${pageForm.list}" >
							<tr id="${arr[0]}" title = "${arr[1]}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${arr[0]}" />
								</td>
								<td class="td_bg01">
									<span id="projectName">${arr[2]}</span>
								</td>
								<td class="td_bg01">
									<span id="comapnyID">${arr[3]}</span>
								</td>
								<td class="td_bg01">
									<span id="DepName">${arr[4]}</span>
								</td>
								<td class="td_bg01">
									<span id="responsiblePeople">${arr[5]}</span>
								</td>
								<td class="td_bg01">
									<span id="startdate">${arr[7]}</span>
								</td>
								<td class="td_bg01">
									<span id="enddate">${arr[8]}</span>
								</td>
								<td class="td_bg01">
									<span id="context">${arr[9]}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/budget/ea_getprojectPlanbudget.jspa?">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
		</form>
		
		
		<!-- 项目计划预算查看 -->
		<form name="budgetSearchForm" id="budgetSearchForm" method="post">
			<div class="jqmWindow" style="width: 700px; right: 20%; top: 10%"
				id="jqModelbudgetSearch">
				<div class="drag">
					项目计划预算详情
					<div class="close">
					</div>
				</div>
				<div class="table-d">
				<table id="budgetSearchTable" cellspacing="1" cellpadding="0" border="0px" width="100%">

					<tr>
						<td align="right">
							公司名称：
						</td>
						<td>
						<span id="companyname"></span>
						</td>
						<td>
							项目名称：
						</td>
						<td><span id="projectname"></span></td>
						<td>
							单据类型：
						</td>
						<td><span id="billtype"></span></td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td><span id="orgname"></span></td>
						<td>
							责任人：
						</td>
						<td><span id="zerenren"></span></td>
						<td>
							项目内容：
						</td>
						<td><span id="procontext"></span></td>
					</tr>
					<tr>
						<td align="right">
							项目开始时间：
						</td>
						<td><span id="starttime"></span></td>
						<td>
							项目结束时间：
						</td>
						<td><span id="endtime"></span></td>
						<td>
							项目类型：
						</td>
						<td><span id="projectType"></span></td>
					</tr>
					<tr>
						<td align="right">
							所属项目编号：
						</td>
						<td><span id="procode"></span></td>
						<td>
							制单人：
						</td>
						<td><span id="madebill"></span></td>
						<td>
							制单人部门：
						</td>
						<td><span id="madebilldep"></span></td>
					</tr>
					<tr>
						<td align="right">
							制单人公司：
						</td>
						<td><span id="madebillcom"></span></td>
						<td>
							制单日期：
						</td>
						<td><span id="madedate"></span></td>
						<td>
							审核人：
						</td>
						<td><span id="shenhe"> </span></td>
					</tr>
					<tr>
						<td colspan="2"  align="center">
							单据状态：
						</td>
						<td ><span id="status"></span></td>
						<td colspan="2">负责人：</td>
						<td><span id = "cresponsible"></span></td>
					</tr>
				</table>
				</div>
			</div>
		</form>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>
