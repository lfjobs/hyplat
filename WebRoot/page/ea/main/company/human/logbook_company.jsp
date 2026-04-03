<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@page import="hy.ea.bo.Company"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	 Company c = (Company)session.getAttribute("currentcompany");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>集团--公司工作日志汇总列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
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
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
<script src="<%=basePath%>js/ea/company/human/logbook_company.js">
		</script>
<script type="text/javascript"
	src="<%=basePath%>js/common/organizationTree.js"></script>

<script type="text/javascript">
    var treeID = '<%=session.getAttribute("organizationID")%>';
    var basePath='<%=basePath%>';
	var search = '${search}';
	var pNumber = ${pageNumber};
	var comID = '<%=c.getCompanyID()%>';
	var comName = '<%=c.getCompanyName()%>';
</script>
</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="200" align="center">公司名称</th>
					<th width="100" align="center">员工编号</th>
					<th width="100" align="center">员工姓名</th>
					<th width="100" align="center">当天日期</th>
					<th width="100" align="center">起时间</th>
					<th width="100" align="center">止时间</th>
					<th width="150" align="center">工作地点</th>
					<th width="150" align="center">完成工作内容</th>
					<th width="100" align="center">得分类别</th>
					<th width="100" align="center">得分</th>
					<th width="100" align="center">附件类别及名称</th>
					<th width="100" align="center">主管签字</th>
					<th width="100" align="center">事主管管理</th>
					<th width="100" align="center">公司经理</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${logBookID}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${logBookID}" /></td>
						<td><span id="companyName">${companyName}</span></td>
						<td><span id="staffCode">${staffCode}</span></td>
						<td><span id="staffName">${staffName}</span></td>
						<td><span id="todaydate">${fn:substring(todaydate, 0,
								10)}</span></td>
						<td><span id="startdate">${startdate}</span></td>
						<td><span id="enddate">${enddate}</span></td>
						<td><span id="jobLocation">${jobLocation}</span></td>
						<td><span id="jobContent">${jobContent}</span></td>
						<td><span id="scoreSortName">${scoreSortName}</span></td>
						<td><span id="bisect">${bisect}</span></td>
						<td><span id="attachment" style="display:none">${attachment}</span>
							<s:if test="attachment==null||attachment==''">无</s:if> <s:else>
								<span id="look" onclick="lookImage('${attachment}');"><a
									href="#">查看</a>
								</span>
							</s:else></td>
						<td><span id="supervisor">${supervisor}</span></td>
						<td><span id="staffingManage">${staffingManage}</span></td>
						<td><span id="manager">${manager}</span></td>
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/logbookcompany/ea_getLogBookList.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}">
			</c:param>
		</c:import>
	</div>

	<form name="cstaffForm" id="cstaffForm" method="post"
		enctype="multipart/form-data">
		<div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%"
			id="jqModel">

			<div class="drag">
				公司工作日志汇总
				<div class="close"></div>
			</div>
			<input type="submit" name="submit" style="display:none" />
			<div class="content">
				<div class="contentbannb"></div>
				<table width="695" border="0" id="stafftable" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top:5px;margin-bottom:5px;">
					<tr>
						<td>
							<table width="693" border="0" align="center" cellpadding="0"
								cellspacing="0" id="stafftable2"
								style="margin-top:5px;margin-bottom:5px;">
								<tr>
									<td width="100" height="40" align="right">员工编号：</td>
									<td><input name="staffCode" type="text" id="staffCode"
										size="20" readonly />
									</td>
									<td width="150" align="right">员工姓名：</td>
									<td><input name="staffName" type="text" id="staffName"
										size="20" readonly/>
									</td>
									<td rowspan="4" align="center"><img width="99"
										height="135" id="attachment" readonly/></td>
								</tr>
								<tr>
									<td height="40" align="right">起时间：</td>
									<td><input id="startdate" type="text" name="startdate"
										class="input" size="20" readonly/>
									</td>
									<td align="right">止时间：</td>
									<td><input id="enddate" type="text" name="enddate"
										class="input" size="20" readonly/>
									</td>
								</tr>
								<tr>
									<td height="40" align="right">工作地点：</td>
									<td><input id="jobLocation" type="text"
										name="jobLocation" class="input" size="20" readonly/>
									</td>
									<td align="right">得分类别：</td>
									<td><input name="scoreSort" type="text" class="input"
										id="scoreSortName" size="20" readonly/>
									</td>
								</tr>
								<tr>
									<td height="40" align="right">得分：</td>
									<td><input id="bisect" type="text" name="bisect"
										class="input" size="20" readonly/>
									</td>
									<td align="right">当天日期：</td>
									<td><input name="todaydate" type="text" class="input"
										id="todaydate" size="20" readonly/>
									</td>
								</tr>
								<tr>
									<td height="40" align="right">主管签字：</td>
									<td><input id="supervisor" type="text" name="supervisor"
										class="input" size="20" readonly/>
									</td>
									<td align="right">人事主管管理：</td>
									<td><input id="staffingManage" type="text"
										name="staffingManage" class="input" size="20" readonly/>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right" rowspan="2">完成工作内容：</td>
									<td><textarea rows="2" cols="25" id="jobContent"
											name="jobContent" class="input"readonly></textarea></td>
									<td height="40" align="right">公司经理：</td>
									<td><input id="manager" type="text" name="manager"
										class="input" size="20" readonly/>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
	</form>

	<!--搜索窗口 -->

	<form name="postSearchForm" id="postSearchForm" method="post">
		<div class="jqmWindow jqmWindowcss3" style="width: 400px; right: 25%; top: 10%"
			id="jqModelSearch">
			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				查询信息
				<div class="close"></div>
			</div>
			<table id="cataffSearchTable">
				<tr>
					<td width="122" align="right">员工编号：</td>
					<td><input name="logbooksummary.staffCode" /></td>
				</tr>
				<tr>
					<td align="right">员工姓名：</td>
					<td><input name="logbooksummary.staffName" /></td>
				</tr>
				<tr>
					<td align="right">公司名称：</td>
					<td>
					<select id="companyID" name="logbooksummary.companyID">
						<option value="">
									请选择公司
								</option>
							</select>
					</td>
				</tr>
				<tr>
					<td align="right">得分类别：</td>
					<td><s:select list="%{#request.scoreSortlist}"
							listKey="codeID" listValue="codeValue" headerKey=""
							headerValue="全部" name="logbooksummary.scoreSort" theme="simple"></s:select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch" value=" 查询 " /><input
					name="search" type="hidden" value="search" />
			</div>
		</div>
	</form>
</body>
</html>