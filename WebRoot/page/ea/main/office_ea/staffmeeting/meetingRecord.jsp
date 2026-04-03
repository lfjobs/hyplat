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
		<title>会议记录页面</title>
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
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
		<script type="text/javascript">
           	var treeid = "<%=c.getCompanyID()%>";
           	var companyName = "<%=c.getCompanyName()%>";
          	var token = 0;
            var meetingID = "";
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
            var result='';
            var meetingID = "${meetingID}";
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/staffmeeting/staffMeeting.js"></script>

<style type="text/css">

.hid{
 border:0px;

}

</style>

	</head>
	<body>
		
		
		
		    <!--会议记录-->
		<div class="contentbannb" 
			id="jqModelRecord">
			<form name="postRecordForm" id="postRecordForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							会议记录
							<div class="close"></div>
						</div>
					</div>
					<table width="890 " border="0" align="center"
						cellpadding="2" cellspacing="2" style="margin-top: 5px;" id="newtbl">
						<tr>

							<td width="100" align="right">
								会议名称：
							</td>
							<td width="300" align="left">
								<span>${staffMeeting.meetingName}</span>
							</td>
							<td align="right">
								附件：
							</td>
								<td align="left">
								<input name="staffMeeting.mrecordAttach" class="fileNum"
									type="hidden" id="mrecordAttach" size="15" />
								<input name="photo" contentEditable="false" type="file"
									class="input" size="15" />
							</td>
							
					</table>
					<table width="890" border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">

						<tr>
							<td align="right">
								<span class="xx">*</span>会议记录：
							</td>
							<td colspan="8">
								<textarea name="staffMeeting.meetingRecord" cols="100" rows="7"
									class="input hid" id="meetingRecord" style="margin-left: 2px;">${staffMeeting.meetingRecord}</textarea>
							</td>
						</tr>
						

					</table>
					<table width="890"  border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">

						<tr>
							<td align="right">
								<span class="xx">*</span>会议纪要：
							</td>
							<td colspan="8">
								<textarea name="staffMeeting.meetingMinutes" cols="100" rows="7"
									class="input meetingMinutes hid" id="meetingMinutes" style="margin-left: 2px;">${staffMeeting.meetingMinutes}</textarea>
							</td>
						</tr>
						

					</table>
					<table width="890"  border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">

						<tr>
							<td align="right">
								<span class="xx">*</span>会议简报：
							</td>
							<td colspan="8">
								<textarea name="staffMeeting.meetingBrief" cols="100" rows="8"
									class="input hid" id="meetingBrief" style="margin-left: 2px;">${staffMeeting.meetingBrief}</textarea>
							</td>
						</tr>
						

					</table>
					<table width="890"  border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">

						<tr>
							<td align="right">
								<span class="xx">*</span>会议总结：
							</td>
							<td colspan="8">
								<textarea name="staffMeeting.meetingSummary" cols="100" rows="8"
									class="input hid" id="meetingSummary"  style="margin-left: 2px;">${staffMeeting.meetingSummary}</textarea>
							</td>
						</tr>
						

					</table>
					<table width="890"  border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td colspan="10" align="center">
								<input type="hidden" name="staffMeeting.meetingID" value="${staffMeeting.meetingID}" />
								<input type="button" class="input-button" id="saverecord"
									style="cursor: pointer; width: 80px;" value="保存" />
								<input type="button" class="input-button back"
									style="cursor: pointer; width: 80px;" value="返回" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>


		<iframe name="hidden" width="100%" scrolling="no" marginwidth="0"
			height="0" marginheight="0" frameborder="0" border="0"
			framespacing="0" noresize="noResize"></iframe>
		
	</body>
</html>