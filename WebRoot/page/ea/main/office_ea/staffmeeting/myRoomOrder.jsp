<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company) session.getAttribute("currentcompany");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>我的会议室预约</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script type="text/javascript">
           	var treeid = "<%=c.getCompanyID()%>";
           	var companyName = "<%=c.getCompanyName()%>";
          	var token = 0;
          	var mroomoID = "";
            var type="${type}";
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
            var result='';
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/staffmeeting/myRoomOrder.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="180" align="center">
							会议名称
						</th>
						<th width="80" align="center">
							会议主题
						</th>
						<th width="150" align="center">
							开会时间
						</th>
						<th width="150" align="center">
							结束时间
						</th>
						<th width="180" align="center">
							会议室
						</th>
						<th width="180" align="center">
							会议室地点
						</th>
						<th width="180" align="center">
							容纳人数
						</th>
						<th width="180" align="center">
							管理员
						</th>
						<th width="180" align="center">
							管理员联系方式
						</th>
						<th width="180" align="center">
							说明
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<c:forEach  items="${pageForm.list}" var="order">
						<tr id="${order[0]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${order[0]}" />
							</td>
							<td>
								<span id="mroomoID" style="display: none;">${order[0]}</span>
								<span id="mroomID" style="display: none;">${order[13]}</span>
							    <span id="mroomoKey" style="display: none;">${order[14]}</span>
								<span id="meetingName">${order[1]}</span>
							</td>
							<td>
								<span id="meetingTheme">${order[2]}</span>
							</td>
							<td>
								<span id="startDate" style="display: none;">${fn:substring(order[3],0,10)}</span>
								<span id="startTime" style="display: none;">${order[5]}</span>
								<span id="startD">${fn:substring(order[3],0,10)} ${order[5]}</span>
							</td>
							<td>
								<span id="endDate" style="display: none;">${fn:substring(order[4],0,10)}</span>
								<span id="endTime" style="display: none;">${order[6]}</span>
								<span id="endD">${fn:substring(order[4],0,10)} ${order[6]}</span>
							</td>
							<td>
								<span id="roomName">${order[7]}</span>
							</td>
							<td>
								<span id="zone">${order[8]}</span>
							</td>
							<td>
								<span id="capacity">${order[9]}</span>
							</td>
							<td>
								<span id="adminName">${order[10]}</span>
							</td>
							<td>
								<span id="adminTel">${order[11]}</span>
							</td>
							<td>
								<span id="remark">${order[12]}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/meetingroom/ea_getMyRoomOrder.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}">
				</c:param>
			</c:import>
		</div>




		<div class="contentbannb jqmWindow jqmWindowcss1" style="top: 12%"
			id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							新增预约
							<div class="close"></div>
						</div>
					</div>
					<table width="890" height="295" border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">
						<tr>

							<td align="right">
								会议名称：
							</td>
							<td align="left" colspan="8">
								<input name="mroomOrder.meetingName" class="input put3"
									id="meetingName" size="30" />
							</td>

						</tr>
						<tr>

							<td width="100" align="right">
								会议室：
							</td>
							<td width="300" align="left" colspan="8">
								<s:select list="%{#request.roomlist}" style="width:200px"
									id="mroomIDs" headerKey="" headerValue="请选择" listKey="mroomID"
									name="mroomOrder.mroomID" listValue="roomName" theme="simple">
								</s:select>

							</td>

						</tr>

						<tr>
							<td align="right">
								开会时间：
							</td>
							<td align="left" colspan="8">
								<input name="startDates" class="input put3 startdate" id="startDate"
									onfocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(dp){getstat(); return false;}})"
									size="12" />
								<s:select list="%{#request.timelist}" style="width:130px" 
									name="mroomOrder.startTime" id="starttime" theme="simple">
								</s:select>
								<!--<input type="checkbox" value="full" />
								全天
							-->
							</td>
						</tr>
						<tr>
							<td align="right">
								结束时间：
							</td>
							<td align="left" colspan="8">
								<input name="endDates" class="input put3 enddate" id="endDate"
									onfocus="WdatePicker({minDate:'%y-%M-{%d}',minDate:'#F{$dp.$D(\'startDate\')}',onpicked:function(dp){getstat(); return false;}})"
									size="12" />
								<select name="mroomOrder.endTime" id="endtime" 
									style="width: 130px"></select>
							</td>

						</tr>
						<tr>
							<td height="83" align="right">
								<span class="xx">*</span>会议主题：
							</td>
							<td colspan="8">
								<textarea name="mroomOrder.meetingTheme" cols="80" rows="5"
									class="input put3" id="meetingTheme" style="margin-left: 2px;"></textarea>
							</td>
						</tr>


					</table>
					<table width="890" height="20" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">

						<tr>
							<td colspan="10" align="center">
								<input type="hidden" name="mroomOrder.mroomoID" id="mroomoID"  class="mroomoID"/>
								<input type="hidden" name="mroomOrder.dates" value="${dates}"
									id="datessss" />
								<input type="hidden" name="mroomOrder.mroomoKey" id="mroomoKey" />
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input type="button" class="input-button close"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>




		<div class="contentbannb jqmWindow jqmWindowcss1" style="top: 12%"
			id="jqModelConflict">
			<form name="conflictForm" id="conflictForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							预约冲突
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" class="table" style="background: #FFFFFF;">
						<thead>
							<tr>
								<th>
									会议名称
								</th>
								<th>
									预约人
								</th>
								<th>
									开会时间
								</th>
								<th>
									结束时间
								</th>
							</tr>
						</thead>
						<tbody id="confictorder">
						</tbody>


					</table>
					<table width="890" height="20" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">

						<tr>
							<td colspan="10" align="center">

								<input type="button" class="input-button back"
									style="cursor: pointer; width: 80px;" value="返回" />
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>










		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							会议名称：
						</td>
						<td width="261">
							<input name="mroomOrder.meetingName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							会议室名称：
						</td>
						<td>
							<s:select list="%{#request.roomlist}" style="width:132px"
								id="roomselect" headerKey="" headerValue="请选择" listKey="mroomID"
								name="mroomOrder.mroomID" listValue="roomName" theme="simple">
							</s:select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input name="type" type="hidden" value="${type}" />
				</div>
			</form>
		</div>









		<iframe name="hidden" width="100%" scrolling="no" marginwidth="0"
			height="0" marginheight="0" frameborder="0" border="0"
			framespacing="0" noresize="noResize"></iframe>
	</body>
</html>