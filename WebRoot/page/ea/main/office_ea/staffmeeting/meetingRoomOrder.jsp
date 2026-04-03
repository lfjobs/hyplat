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
		<title>预约会议室</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/staffmeeting/meetingRoomOrder.js"></script>
		<script type="text/javascript">
   
             var basePath='<%=basePath%>';   
         </script>
		<style type="text/css">
.ordertbl th,.ordertbl tr,.ordertbl td {
	border: 1px solid snow;
	border-collapse: separate;
	cursor: pointer;
}
</style>




	</head>
	<body>

		<div id="jqModelSearch">
			<form id="searchForm" name="searchForm">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">

					预约会议室
				</div>
				<div
				
					style="text-align: center;margin-top: 5px; margin-bottom: 10px;">
					<input type="button" style=""
						class="input-button" value="  返回  " onclick="back();" />
					<input type="button" style="margin-right: 60px;"
						class="input-button" value="  前一天  " onclick="jump('last');" />
					
					<span style="font-weight: bold;vertical-align:middle;">预约日期: </span>
					<input type="text" id="dates" style="height: 16px;"
						onfocus="WdatePicker({onpicked:function(dp){jump('cur'); return false;}})" name="dates" value="${dates}" />
                    
                    <input type="button" style="margin-left: 50px;"
						class="input-button" value="  后一天  " onclick="jump('next');" />
					<input type="hidden" id="datess" value="${dates}" />
				</div>
				<div style="overflow: auto;">
					<table style="width: 100%;" cellpadding="0" cellspacing="0"
						class="ordertbl" style="margin-bottom: 30px; cursor: pointer;">
						
							<tr style="background-color: lightblue; height: 35px;">
								<th>
									预约时间
								</th>

								<%
									int i = 0;
								%>

								<s:iterator var="per" value="roomlist">
									<th id="${mroomID}" class="room">
								
										   <s:property value="#per.roomName"/>
										
									</th>
									<%
										i++;
									%>
									

								</s:iterator>
								
	


							</tr>
						
						
							<%
								int hour = 9;
								String m = "00";
								int j = 1;
								for (j = 1; j < 28; j++) {

									if (j % 2 == 0) {
										m = "30";

									} else {
										m = "00";
										if (j != 1) {
											hour++;
										}
									}
							%>

							<tr>
								<td style="width: 6%; background-color: lightblue;" class="time"
									align="center" title = "<%=j-1%>" id="<%
										if (hour == 9) {
									%>0<%
										}
									%><%=hour%><%=m%>">
									<%
										if (hour == 9) {
									%>0<%
										}
									%><%=hour%>:<%=m%></td>
								<%
									for (int k = 0; k < i; k++) {
								%><td class="preOrder" title="1">

								</td>
								<%
									}
								%>
							</tr>
							<%
								}
							%>
							<tr>

							</tr>
						

					</table>
				</div>

			</form>
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

							<td  align="right">
								会议名称：
							</td>
							<td  align="left" colspan="8">
								<input name="mroomOrder.meetingName" class="input put3"
									id="meetingName" size="30" />
							</td>

						</tr>
						<tr>

							<td align="right">
								会议室：
							</td>
							<td  align="left" colspan="8">
								<s:select list="%{#request.roomlist}" style="width:200px"
									id="roomselect" headerKey="" headerValue="请选择"
									listKey="mroomID" name="mroomOrder.mroomID"
									listValue="roomName" theme="simple">
								</s:select>
								

							</td>

						</tr>

						<tr>
							<td align="right">
								开会时间：
							</td>
							<td align="left" colspan="8">
								<input name="startDates" class="input put3" id="startDate"
									onfocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(dp){getstat(); return false;}})" size="12" />
								<s:select list="%{#request.timelist}" style="width:130px"
									name="mroomOrder.startTime" id="starttime" theme="simple">
								</s:select>
								<!--<input type="checkbox" value="full" />
								全天
							--></td>
						</tr>
						<tr>
							<td align="right">
								结束时间：
							</td>
							<td align="left" colspan="8">
								<input name="endDates" class="input put3" id="endDate"
									onfocus="WdatePicker({minDate:'%y-%M-{%d}',minDate:'#F{$dp.$D(\'startDate\')}',onpicked:function(dp){getstat(); return false;}})"
									size="12"/>
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
								<input type="hidden" name="mroomOrder.mroomoID" id="mroomoID" />
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
					<table width="100%"  border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">
						<thead>
						<tr>
                           <th>会议名称</th>
                           <th>预约人</th>
                           <th>开会时间</th>
                           <th>结束时间</th>
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
		<iframe name="hidden" width="100%" scrolling="no" marginwidth="0"
			height="0" marginheight="0" frameborder="0"></iframe>
	</body>
</html>
