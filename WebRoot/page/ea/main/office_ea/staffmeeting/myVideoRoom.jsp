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
        <script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
			<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
		<script type="text/javascript">
    	    var treeid = "<%=c.getCompanyID()%>";
 		    var companyName = "<%=c.getCompanyName()%>";
 		      var ppageNumber=${pageNumber};
            var basePath='<%=basePath%>';
            var roomid = "";
            var user = "${user}";
            var result='';
            var sccid = "${sccid}";
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/staffmeeting/myVideoRoom.js"></script>
			
				<style type="text/css">

.containerTableStyle {
	position: static;
	overflow: auto;
}

#tree1 {
	height: 380px;
	width: 250px;
	overflow: auto;
	background:#FFFFFF;
	white-space:nowrap;
}
</style>
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
							会议开始时间
						</th>
						<th width="180" align="center">
							会议结束时间
						</th>
						<th width="150" align="center">
							会议主题
						</th>
					
						<th width="150" align="center">
							创建人
						</th>
						<th width="150" align="center">
							创建时间
						</th>
						<th width="150" align="center">
							会议密码
						</th>
						
						
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${roomid}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${roomid}" />
							</td>
							<td>
							<span id="roomid">${roomid}</span>
							<span id="meetingID" style="display:none;">${meetingID}</span>
							</td>

							<td>
								<span id="meetingName">${meetingName}</span>
							</td>
						
							<td>
								<span id="startDate">${fn:substring(startDate,0,19)}</span>
							</td>
							<td>
								<span id="endDate">${fn:substring(endDate,0,19)}</span>
							</td>
							<td>
								<span id="meetingTheme">${meetingTheme}</span>
							</td>
						
							<td>
								<span id="principal">${principal}</span>
							</td>
							<td>
								<span id="updateTime">${fn:substring(updateTime,0,19)}</span>
							</td>
							<td>
								<span id="meetingpsw">${meetingpsw}</span>
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
					value="ea/videoroom/ea_myRoomMeeting.jspa?pageNumber=${pageNumber}&user=${user}&sccid=${sccid}">
				</c:param>
			</c:import>
		</div>


       <div class="contentbannb jqmWindow jqmWindowcss1" style="top: 1%"
			id="jqModel">
			  <form name="cstaffForm" id="cstaffForm" method="post">
				<input type="submit" name="submit" style="display: none" />
			   	<div class="content">
					<div class="contentbannb">
						<div class="drag">
							创建会议
							<div class="close"></div>
						</div>
					</div>
					<table width="890 " height="46" border="0" align="center"
						cellpadding="2" cellspacing="2" style="margin-top: 5px;" id="newtbl">
						<tr>

							<td width="100" align="right">
								会议名称：
							</td>
							<td width=250 align="left">
								<input name="staffMeeting.meetingName" class="input meetingName put3"
									 size="20" />
							</td>
								<td align="right">
								会议室密码：
							</td>
							<td align="left">
								<input name="staffMeeting.meetingpsw"
									 size="20"  type="password" placeHolder="会议密码4-32位（选填）"/>
							</td>
								<td align="right">
								主席密码：
							</td>
							<td align="left">
								<input name="staffMeeting.chairpsw"
									 size="20"  type="password" placeHolder="主席密码4-32位（选填）"/>
							</td>

						</tr>
						<tr>
							<td align="right">
								开会时间：
							</td>
							<td align="left">
								<input name="startDates" class="input startDate put3" id="startDate"  onfocus="daytime(this)"
									 size="20"/>
							</td>
							<td align="right" class="premode">
								结束时间：
							</td>
							<td align="left" class="premode" colspan="2">
								<input name="endDates" class="input endDate put3" id="endDate" onfocus="daytime(this)"
									 size="20"/>
							</td>
						</tr>
					</table>
					<table width="890" height="295" border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">

						<tr>
							<td height="83" align="right">
								<span class="xx">*</span>会议主题：
							</td>
							<td colspan="8">
								<textarea name="staffMeeting.meetingTheme" cols="80" rows="5"
									class="input meetingTheme put3" id="meetingTheme" style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						<tr>
							<td height="83" align="right">
								<span class="xx">*</span>参会人员：
							</td>
							<td colspan="8">
								<input type="hidden" value="" name="chID"
									id="smpid" />
									<input type="hidden" value="${sccid}" name="sccid"/>
								<textarea cols="80" rows="5"
									id="smp" class="input put3"
									style="margin-left: 2px;" readonly></textarea>
								<a href="#" onclick="selectPeople();">选择人员</a>
								<a href="#" onclick="clearPeople();">清空</a>
							</td>
						</tr>

					</table>
					<table width="890" height="20" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td colspan="10" align="center">
								<input type="hidden" name="staffMeeting.meetingID" id="meetingID" />
								<input type="hidden" name="staffMeeting.meetingKey" id="meetingKey" />
								<input type="hidden" name="staffMeeting.mroomoID" class="mroomoID" id="mroomoID" />
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="保存并通知" />
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
<div class="jqmWindow" id="zj"
			style="width: 800px; height: 450px; left: 20%; top: 1%">
			<div>
				<div class="contentbannb">
					<div class="drag">
						组织机构树

					</div>
				</div>
			</div>
			<table style="width: 100%; height: 450px;" cellpadding="0"
				cellspacing="0" style="margin-top: 2px;">
				<tr>
					<td width="30%" align="left" valign="top">
						<div id="tree1"></div>
					</td>
					<td width="70%" align="left" valign="top">
						<table style="width: 450px; height: 350px;" align="center"
							cellpadding="0" cellspacing="2"
							style="margin-top: 5px; margin-bottom: 5px;">
							<tr>
								<td width="200" height="20" class="txt01">
									备选人员
								</td>
								<td width="50">
									&nbsp;
								</td>
								<td width="200" align="left" class="txt01">
									已选人员
								</td>
							</tr>
							<tr>
								<td height="137">
									<select name="leftfields" multiple="multiple" id="leftfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>

								</td>
								<td width="250" align="center">
									<div>
										<input type="button" class="input-button" id="query_add"
											value=" 添加 " />
									</div>
									<div>
										<input type="button" class="input-button" id="query_delete"
											value=" 删除 " />
									</div>
								</td>
								<td>
									<select name="rightfields" multiple="multiple" id="rightfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>
								</td>
								<td width="100">
									&nbsp;
								</td>
							</tr>


							<tr>
								<td height="30" colspan="3" align="center">
									<br />
									<input type="button" class="input-button" id="confirm"
										value=" 确定 " onclick="submit();" />
									<input type="button" class="input-button" id="closed"
										value=" 关闭 " onclick="closed();" />
									<a href="#" id="ttttt" target="_self"></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>


		<iframe name="hidden" width="100%" scrolling="no" marginwidth="0"
			height="0" marginheight="0" frameborder="0" border="0"
			framespacing="0" noresize="noResize"></iframe>
	</body>
</html>