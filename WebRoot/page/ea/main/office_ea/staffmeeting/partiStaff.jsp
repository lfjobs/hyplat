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
		<title>参会人员</title>
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
            var partiID = "";
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
            var  meetingName = "${staffMeeting.meetingName}";
            var meetingID = "${meetingID}";
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/staffmeeting/partiStaff.js"></script>

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
							姓名
						</th>
						<th width="180" align="center">
							部门
						</th>
						<th width="150" align="center">
							职务
						</th>
						<th width="150" align="center">
							是否缺席
						</th>
						<th width="180" align="center">
							缺席原因
						</th>
						<th width="80" align="center">
							是否迟到
						</th>
						<th width="60" align="center">
							迟到原因
						</th>

						<th width="80" align="center">
							是否发言
						</th>
						<th width="80" align="center">
							发言内容
						</th>

					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${partiID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${partiID}" />
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>

							<td>
								<span id="organizationName">${organizationName}</span>
							</td>
							<td>
								<span id="post">${post}</span>
							</td>
							<td>
								<span id="isAttend" style="display:none;">${isAttend}</span>
								<s:if test='isAttend=="00"'>否</s:if><s:elseif test='isAttend=="01"'>是</s:elseif>
							</td>
							<td>
								<span id="unAttendCause">${unAttendCause}</span>
							</td>
							<td>
								<span id="isLate" style="display:none;">${isLate}</span>
								<s:if test='isLate=="00"'>否</s:if><s:elseif test='isLate=="01"'>是</s:elseif>
							</td>
							<td>
								<span id="lateCause">${lateCause}</span>
							</td>

							<td>
								<span id="isSpeak" style="display:none;">${isSpeak}</span>
								<s:if test='isSpeak=="00"'>否</s:if><s:elseif test='isSpeak=="01"'>是</s:elseif>

							</td>
							<td>
								<span id="speakContent">${speakContent}</span>
								<span id="partiKey" style="display: none;">${partiKey}</span>
								<span id="partiID" style="display: none;">${partiID}</span>

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
					value="ea/smeeting/ea_getPartiStaffList.jspa?pageNumber=${pageNumber}&search=${search}&meetingID=${meetingID}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss1" style="width:800px;top: 10%"
			id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							参会情况录入
							<div class="close"></div>
						</div>
					</div>
					<table width="800 " height="46" border="0" align="center"
						cellpadding="2" cellspacing="2" style="margin-top: 5px;">
						<tr>

							<td width="85" align="right">
								会议名称：
							</td>
							<td  align="left">
								<span>${staffMeeting.meetingName}</span>
								
								<input type="hidden" id="partiIDs" name="smParticipant.partiID"/>
							</td>
							

						</tr>
						
						
					</table>
					<table width="800"  border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">
						<tr>
							<td height="83" align="right">
								<span class="xx">*</span>会议主题：
							</td>
							<td colspan="8" align="left">
								${staffMeeting.meetingName}
							</td>
						</tr>
                       <tr>
							<td width="100" align="right">
								 是否缺席：
							</td>
							<td align="left" colspan="8">
								<select name="smParticipant.isAttend" class="isAttend" id="isAttend">
									<option value="00" selected="selected">
										否
									</option>
									<option value="01">
										是
									</option>
								</select>
							</td>
							


						</tr>
						<tr style="display:none;" class="unAttendCause">
							<td height="83" align="right">
								<span class="xx">*</span>缺席原因：
							</td>
							<td colspan="8">
								<textarea name="smParticipant.unAttendCause" cols="80" rows="5"
									class="input" id="unAttendCause" style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						
						  <tr class="late">
							
							<td width="100" align="right">
								是否迟到：
							</td>
							<td align="left" colspan="8">
								<select name="smParticipant.isLate" class="isLate" id="isLate" >
									<option value="00" selected="selected">
										否
									</option>
									<option value="01">
										是
									</option>
								</select>
							</td>


						</tr>
						<tr style="display:none;" class="lateCause">
							<td height="83" align="right">
								<span class="xx">*</span>迟到原因：
							</td>
							<td colspan="8">
								<textarea name="smParticipant.lateCause" cols="80" rows="5"
									class="input" id="lateCause" style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						 <tr class="speak">
							
							<td width="100" align="right">
								是否发言：
							</td>
							<td align="left" colspan="8">
								<select name="smParticipant.isSpeak" class="isSpeak" id="isSpeak">
									<option value="00" selected="selected">
										否
									</option>
									<option value="01">
										是
									</option>
								</select>
							</td>


						</tr>
						<tr style="display:none;" class="speakContent">
							<td align="right">
								<span class="xx">*</span>发言内容：
							</td>
							<td colspan="8">
								<textarea name="smParticipant.speakContent" cols="80" rows="5"
									class="input" id="speakContent"
									style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								
							</td>
							<td colspan="8">
								
							</td>
						</tr>

					</table>
					<table width="890" height="20" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td colspan="10" align="center">
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="保存" />
								<input type="button" class="input-button close"
									style="cursor: pointer; width: 80px;" value="取消" />
								<input type="hidden" name="meetingID" value="${meetingID}"/>
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		
		
		
		
		
			<div class="contentbannb jqmWindow jqmWindowcss1" style="width:800px;top: 10%"
			id="jqModelView">
			<form name="viewForm" id="viewForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							查看
							<div class="close"></div>
						</div>
					</div>
					<table width="800 " height="46" border="0" align="center"
						cellpadding="2" cellspacing="2" style="margin-top: 5px;">
						
						<tr>

							<td width="85" align="right">
								会议名称：
							</td>
							<td  align="left">
								<span>${staffMeeting.meetingName}</span>
								
								<input type="hidden" id="partiIDs" name="smParticipant.partiID"/>
							</td>
							<td width="85" align="right">
								姓名：
							</td>
							<td  align="left">
								<span name="staffName" id="staffName"></span>
								
								
							</td>

						</tr>
						
						<tr>

							
							<td width="85" align="right">
								部门：
							</td>
							<td  align="left">
								<span name="organizationName" id="organizationName"></span>
								
							</td>
							<td width="85" align="right">
								职务：
							</td>
							<td  align="left">
								<span name="post" id="post"></span>
								
							</td>
							
						</tr>
						
						
						
					</table>
					<table width="800"  border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">
						<tr>
							<td height="83" align="right">
								<span class="xx">*</span>会议主题：
							</td>
							<td colspan="8" align="left">
								${staffMeeting.meetingName}
							</td>
						</tr>
                       <tr>
							<td width="100" align="right">
								 是否缺席：
							</td>
							<td align="left" colspan="8">
								<select name="smParticipant.isAttend" class="isAttend" id="isAttend">
									<option value="00" selected="selected">
										否
									</option>
									<option value="01">
										是
									</option>
								</select>
							</td>
							


						</tr>
						<tr style="display:none;" class="unAttendCause">
							<td height="83" align="right">
								<span class="xx">*</span>缺席原因：
							</td>
							<td colspan="8">
								<textarea name="smParticipant.unAttendCause" cols="80" rows="5"
									class="input" id="unAttendCause" style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						
						  <tr class="late">
							
							<td width="100" align="right">
								是否迟到：
							</td>
							<td align="left" colspan="8">
								<select name="smParticipant.isLate" class="isLate" id="isLate" >
									<option value="00" selected="selected">
										否
									</option>
									<option value="01">
										是
									</option>
								</select>
							</td>


						</tr>
						<tr style="display:none;" class="lateCause">
							<td height="83" align="right">
								<span class="xx">*</span>迟到原因：
							</td>
							<td colspan="8">
								<textarea name="smParticipant.lateCause" cols="80" rows="5"
									class="input" id="lateCause" style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						 <tr class="speak">
							
							<td width="100" align="right">
								是否发言：
							</td>
							<td align="left" colspan="8">
								<select name="smParticipant.isSpeak" class="isSpeak" id="isSpeak">
									<option value="00" selected="selected">
										否
									</option>
									<option value="01">
										是
									</option>
								</select>
							</td>


						</tr>
						<tr style="display:none;" class="speakContent">
							<td align="right">
								<span class="xx">*</span>发言内容：
							</td>
							<td colspan="8">
								<textarea name="smParticipant.speakContent" cols="80" rows="5"
									class="input" id="speakContent"
									style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								
							</td>
							<td colspan="8">
								
							</td>
						</tr>

					</table>
					<table width="890" height="20" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td colspan="10" align="center">
								
								<input type="button" class="input-button close"
									style="cursor: pointer; width: 80px;" value="取消" />
								<input type="hidden" name="meetingID" value="${meetingID}"/>
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
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
						<td align="right">
							参会人员姓名：
						</td>
						<td>
							<input name="searchStaff.staffName" />
							<input type="hidden" name="meetingID" value="${meetingID}"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>


		<iframe name="hidden" width="100%" scrolling="no" marginwidth="0"
			height="0" marginheight="0" frameborder="0" border="0"
			framespacing="0" noresize="noResize"></iframe>
	</body>
</html>