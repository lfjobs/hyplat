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
		<title>员工会议管理</title>
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
		
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />

		<script type="text/javascript">
           	var companyName = "<%=c.getCompanyName()%>";
          	var token = 0;
            var mroomID = "";
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
            var result='';
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/staffmeeting/meetingRoom.js"></script>
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
							会议室名称
						</th>
						<th width="180" align="center">
							会议室地点
						</th>
						<th width="150" align="center">
							容纳人数
						</th>
						<th width="150" align="center">
							管理员
						</th>
						<th width="150" align="center">
							管理员电话
						</th>
						<th width="180" align="center">
							备注
						</th>
						
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${mroomID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${mroomID}" />
							</td>
							<td>
								<span id="roomName">${roomName}</span>
								<span id="mroomID" style="display:none;">${mroomID}</span>
								<span id="mroomKey" style="display:none;">${mroomKey}</span>
								<span id="companyID" style="display:none;">${companyID}</span>
							</td>

							<td>
								<span id="zone">${zone}</span>
							</td>
							<td>
								<span id="capacity">${capacity}</span>
							</td>
							<td>
								<span id="adminName">${adminName}</span>
							</td>
							<td>
								<span id="adminTel">${adminTel}</span>
							</td>
							<td>
								<span id="remark">${remark}</span>
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
					value="ea/meetingroom/ea_getMeetingRoomList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>



		<!--会议室添加 -->
		<div class="jqmWindow" style="width: 450px; right: 25%;; top: 10%"
			id="jqModelRoom">
			<form name="postRoomForm" id="postRoomForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					会议室管理
					<div class="close">
					</div>
				</div>
				<table width="100%" id="cataffSearchTable" cellpadding="10"
					cellspacing="10">

					<tr>
						<td align="right" style="width:30%">
							会议室名称：
						</td>
						<td>
							<input type="text" name="meetingRoom.roomName" size="25" id="roomName" class="put3"/>
						</td>
					</tr>

					<tr>
						<td align="right">
							会议室地点：
						</td>
						<td>
							<input type="text" name="meetingRoom.zone" size="25" id="zone" class="put3"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							容纳人数(人)：
						</td>
						<td>
							<input type="text" name="meetingRoom.capacity" id="capacity" size="25" class="put3"/>
						</td>
					</tr>


                    <tr>
						<td align="right">
							管理员：
						</td>
						<td>
							<input type="text" name="meetingRoom.adminName"  id="adminName" size="25" />
							<input type="hidden" name="meetingRoom.adminID" id="adminTel" size="25" />
							<input type="hidden" name="meetingRoom.adminTel" id="adminTel" size="25" />
						</td>
					</tr>

					<tr>
						<td align="right">
							备注：
						</td>
						<td>
							<input type="text" name="meetingRoom.remark" id="remark" size="25"/>
						</td>
					</tr>

				</table>
				<div align="center">
					<input type="button" class="input-button" id="toSaveRoom"
						value=" 确定 " />
					<input type="button" class="input-button close" 
						value=" 关闭 " />
					<input type="hidden" name="meetingRoom.mroomID" id="mroomID"/>
					<input type="hidden" name="meetingRoom.mroomKey" id="mroomKey"/>
					<input type="hidden" name="meetingRoom.companyID" id="companyID" size="25" />
				</div>
			</form>
		</div>



	<!--会议室查询 -->
		<div class="jqmWindow" style="width: 450px; right: 25%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询
					<div class="close">
					</div>
				</div>
				<table width="100%" id="cataffSearchTable" cellpadding="10"
					cellspacing="10">

					<tr>
						<td align="right" style="width:30%">
							会议室名称：
						</td>
						<td>
							<input type="text" name="meetingRoom.roomName" size="25"/>
						</td>
					</tr>
              </table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input type="hidden" name="search" value="search" />
				</div>
			</form>
		</div>





		<iframe name="hidden" width="100%" scrolling="no" marginwidth="0"
			height="0" marginheight="0" frameborder="0" border="0"
			framespacing="0" noresize="noResize"></iframe>
	</body>
</html>