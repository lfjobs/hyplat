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
		<title>视频会议管理</title>
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

		<script type="text/javascript">

            var basePath='<%=basePath%>';
            var roomid = "";
            var user = "${user}";


		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/staffmeeting/videoRoom.js"></script>
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
							会议室ID
						</th>
						<th width="180" align="center">
							会议室名称
						</th>
						<th width="180" align="center">
							房间类型
						</th>
						<th width="150" align="center">
							登录校验模型
						</th>
						<th width="150" align="center">
							房间当前用户数
						</th>
						<th width="150" align="center">
							预定开始时间
						</th>
						<th width="180" align="center">
							预定结束时间
						</th>
						<th width="180" align="center">
							房间最大访问人数
						</th>
						
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="list">
						<tr id="${roomId}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${roomId}" />
							</td>
							<td>
							<span id="roomid">${roomId}</span>
							</td>

							<td>
								<span id="roomname">${roomName}</span>
							</td>
							<td>
								<span id="roomtype">${roomType}</span>
							</td>
							<td>
								<span id="verifymode">${verifyMode}</span>
							</td>
							<td>
								<span id="curusercount">${curUserCount}</span>
							</td>
							<td>
								<span id="hopestarttime">${hopeStartTime}</span>
							</td>
							<td>
								<span id="hopeendtime">${hopeEndTime}</span>
							</td>
							<td>
								<span id="maxusercount">${maxUserCount}</span>
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
					value="ea/videoroom/ea_getMeetingRoomList.jspa?pageNumber=${pageNumber}">
				</c:param>
			</c:import>
		</div>
		
		<!--加入会议-->
		<div class="jqmWindow" style="width: 500px; right: 25%;; top: 10%"
			id="jqModelJoin">
			<form name="joinMeeting" id="joinMeeting" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					加入会议
					<div class="close">
					</div>
				</div>
				<table width="100%" id="cataffSearchTable" cellpadding="10"
					cellspacing="10">

					<tr>
						<td align="right" width="30%">
							会议号/房间号：
						</td>
						<td>
						
							<input name="roomid" type="text" id="rom" class="put3"/>
						</td>
					</tr>
					<tr class="psw" style="display:none;">
						<td align="right" width="30%">
							会议密码：
						</td>
						<td>
							
						<input name="meetingpsw" value="" type="password" id="psw" class="put3"/>
						</td>
					</tr>
					
				</table>
				<div align="center">
					<input type="button" class="input-button" id="join"
						value=" 立即加入" />
				</div>
			</form>
		</div>
	</body>
</html>