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
.dialogWindow {
	font-size: 12px;
	position: absolute;
	background: #DAE7F6 repeat top;
	color: #333;
	border: 1px solid #99bbe8;
	width: 100%;
}

.containerTableStyle {
	position: static;
	overflow: auto;
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
							会议名称
						</th>
						<th width="180" align="center">
							开会地点
						</th>
						<th width="150" align="center">
							开会时间
						</th>
						<th width="150" align="center">
							结束时间
						</th>
						<th width="180" align="center">
							会议类型
						</th>
						<th width="80" align="center">
							会议主题
						</th>
						<th width="60" align="center">
							会议要求
						</th>

						<th width="80" align="center">
							责任人
						</th>
						<th width="80" align="center">
							通知状态
						</th>
						<th width="80" align="center">
							附件
						</th>
						<th width="80" align="center">
							会议录音
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${meetingID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${meetingID}" />
							</td>
							<td>
								<span id="meetingName">${meetingName}</span>
							</td>

							<td>
								<span id="meetingPlace">${meetingPlace}</span>
							</td>
							<td>
								<span id="startDate">${fn:substring(startDate,0,19)}</span>
							</td>
							<td>
								<span id="endDate">${fn:substring(endDate,0,19)}</span>
							</td>
							<td>
								<span id="meetingType" style="display: none;">${meetingType}</span>
								<s:if test='meetingType=="00"'>现场会议</s:if>
								<s:else>视频会议</s:else>
							</td>
							<td>
								<span id="meetingTheme">${meetingTheme}</span>
							</td>
							<td>
								<span id="meetingRequire">${meetingRequire}</span>
							</td>

							<td>
								<span id="principal">${principal}</span>

							</td>
							<td>
								<span id="noticeType" style="display: none">${noticeType}</span>
								<s:if test='noticeType=="00"'>未通知</s:if>
								<s:if test='noticeType=="01"'>已通知</s:if>

							</td>
							<td>
								<s:if test="accessory==null||accessory==''">无</s:if>
								<s:else>
									<span id="look" onclick="lookImage('${accessory}');"><a
										href="#">查看</a> </span>
								</s:else>
								<span id="meetingID" style="display: none">${meetingID}</span>
								<span id="meetingKey" style="display: none">${meetingKey}</span>
							</td>

							<td>
								<s:if test="recordfile==null||recordfile==''">无</s:if>
								<s:else>
									<span id="look" onclick="playVideo('${accessory}');"><a
										href="#">播放</a> </span>
								</s:else>
								<span id="meetingID" style="display: none">${meetingID}</span>
								<span id="meetingKey" style="display: none">${meetingKey}</span>
								<span id="mroomoID" style="display: none">${mroomoID}</span>
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
					value="ea/smeeting/ea_getStaffMeeingList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss1" style="top: 1%"
			id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							新增会议
							<div class="close"></div>
						</div>
					</div>
					<table width="890 " height="46" border="0" align="center"
						cellpadding="2" cellspacing="2" style="margin-top: 5px;" id="newtbl">
						<tr>

							<td width="100" align="right">
								会议名称：
							</td>
							<td width="300" align="left">
								<input name="staffMeeting.meetingName" class="input meetingName"
									id="meetingName" size="30" readonly/>&nbsp;<a href="#" onclick="importGY('ea/meetingroom/ea_getMyRoomOrder.jspa');">选择</a>
							</td>
							<td align="right">
								附件：
							</td>
							<td align="left">
								<input name="staffMeeting.accessory" class="fileNum"
									type="hidden" id="accessory" size="15" />
								<input name="photo" contentEditable="false" type="file"
									class="input" size="15" />
							</td>

						</tr>
						<tr>
							<td width="100" align="right">
								开会地点：
							</td>
							<td align="left">
								<input name="staffMeeting.meetingPlace" class="input meetingPlace"
									id="meetingPlace" size="30" readonly/>
							</td>
							<td width="100" align="right">
								会议类型：
							</td>
							<td align="left">
								<select name="staffMeeting.meetingType">
									<option value="00" selected="selected">
										现场会议
									</option>
									<option value="01">
										视频会议
									</option>
								</select>
							</td>


						</tr>
						<tr>


							<td align="right">
								开会时间：
							</td>
							<td align="left">
								<input name="startDates" class="input startDate" id="startDate"
									 size="20" readonly/>
							</td>
							<td align="right">
								结束时间：
							</td>
							<td align="left">
								<input name="endDates" class="input endDate" id="endDate"
									 size="20" readonly/>
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
									class="input meetingTheme" id="meetingTheme" style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>会议要求：
							</td>
							<td colspan="8">
								<textarea name="staffMeeting.meetingRequire" cols="80" rows="5"
									class="input put3" id="meetingRequire"
									style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>参会人员：
							</td>
							<td colspan="8">
								<input type="hidden" value="" name="smParticipant.staffID"
									id="smpid" />
								<textarea name="smParticipant.staffName" cols="80" rows="5"
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
									style="cursor: pointer; width: 80px;" value="保存" />
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="取消" />
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
						<td width="123" align="right">
							会议名称：
						</td>
						<td width="261">
							<input name="staffMeeting.meetingName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							是否通知：
						</td>
						<td>
							<select name="staffMeeting.noticeType"><option value="">全部</option><option value="01">已通知</option><option value="00">未通知</option></select>
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



		<!--通知 -->
		<div class="jqmWindow" style="width: 400px; right: 25%;; top: 10%"
			id="jqModelNotice">
			<form name="postNoticeForm" id="postNoticeForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					通知
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable" cellpadding="10"
					cellspacing="10">

					<tr>
						<td align="right">
							通知方式：
						</td>
						<td>
							<input type="checkbox" name="staffMeeting.smsNotice"
								checked="checked" id="sms" />
							短信通知
							<input type="checkbox" name="staffMeeting.emailNotice"
								checked="checked" id="email" />
							邮件通知
					

						</td>
					</tr>

				</table>
				<div align="center">
					<input type="button" class="input-button" id="tonotice"
						value=" 提交 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>




		<!--会议时间调整-->
		<div class="jqmWindow" style="width: 500px; right: 25%;; top: 10%"
			id="jqModelTime">
			<form name="postTimeForm" id="postTimeForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					会议时间调整
					<div class="close">
					</div>
				</div>
				<table width="100%" id="cataffSearchTable" cellpadding="10"
					cellspacing="10">

					<tr>
						<td align="right" width="30%">
							原定会议时间：
						</td>
						<td>
							<span id="meetingtime">${staffMeeting.startDate}</span>
							<input name="staffMeeting.meetingID" type="hidden"
								id="tmeetingid" />
						</td>
					</tr>
					<tr>
						<td align="right">
							时间调整为：
						</td>
						<td>
							开会时间：
							<input name="startDates" class="input put3" id="startDate"
								onfocus="daytime(this);" size="20" />


						</td>
					</tr>
					<tr>
						<td align="right">

						</td>
						<td>

							结束时间：
							<input name="endDates" class="input put3" id="endDate"
								onfocus="daytime(this);" size="20" />

						</td>
					</tr>

					<tr>
						<td align="right">
							通知方式：
						</td>
						<td>
							<input type="checkbox" 
								checked="checked" id="smsTime" />
							短信通知
							<input type="checkbox" 
								checked="checked" id="emailTime" />
							邮件通知
							<input type="checkbox" 
								checked="checked" id="qqTime" />
							QQ通知
                           <input type="hidden" name="type" value="Time" />
							
						</td>
					</tr>

				</table>
				<div align="center">
					<input type="button" class="input-button" id="toTiaoTime"
						value=" 提交 " />
				</div>
			</form>
		</div>


		<!--会议地点调整-->
		<div class="jqmWindow" style="width: 500px; right: 25%;; top: 10%"
			id="jqModelPlace">
			<form name="postPlaceForm" id="postPlaceForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					会议地点调整
					<div class="close">
					</div>
				</div>
				<table width="100%" id="cataffSearchTable" cellpadding="10"
					cellspacing="10">

					<tr>
						<td align="right" width="30%">
							原定会议地点：
						</td>
						<td>
							<span id="meetingPlaces"></span>
							<input name="staffMeeting.meetingID" type="hidden"
								id="tmeetingids" />
						</td>
					</tr>
					<tr>
						<td align="right">
							地点更改为：
						</td>
						<td>

							<input name="staffMeeting.factPlace" class="input put3" size="20" id="factPlaces"/>


						</td>
					</tr>
					<tr>
						<td align="right">
							通知方式：
						</td>
						<td>
							<input type="checkbox" 
								checked="checked" id="smsPlace" />
							短信通知
							<input type="checkbox" 
								checked="checked" id="emailPlace" />
							邮件通知
							<input type="checkbox" 
								checked="checked" id="qqPlace" />
							QQ通知
                         <input type="hidden" name="type" value="Place" />
						</td>
					</tr>

				</table>
				<div align="center">
					<input type="button" class="input-button" id="toTiaoPlace"
						value=" 提交 " />
				</div>
			</form>
		</div>



       <!--会议取消-->
		<div class="jqmWindow" style="width: 500px; right: 25%;; top: 10%"
			id="jqModelCancel">
			<form name="postCancelForm" id="postCancelForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					会议取消
					<div class="close">
					</div>
				</div>
				<table width="100%" id="cataffSearchTable" cellpadding="10" 
					cellspacing="10">

					
					<tr>
						<td align="center" colspan="2">
							<span style="font-size:16px;font-weight:bold;">确定取消《<span id="mn"></span>》会议？</span>
						</td>
						
					</tr>
					
					<tr>
						<td align="center" colspan="2">
							<input type="checkbox" name="cancelType" />同时取消会议室预约
						</td>
						
					</tr>
					<tr class="notice">
		
						<td  align="center"  colspan="2">
							<input type="checkbox" 
								checked="checked" id="smsCancel" />
							短信通知
							<input type="checkbox" 
								checked="checked" id="emailCancel" />
							邮件通知

                         <input type="hidden" name="type" value="Cancel" />
                          <input type="hidden" name="staffMeeting.meetingID" value=""  id="meetingIDca"/>
						</td>
					</tr>

				</table>
				<div align="center">
					<input type="button" class="input-button" id="toCancel"
						value=" 确定 " />
					<input type="button" class="input-button close" 
						value=" 关闭 " />
				</div>
			</form>
		</div>
		
		
		  <!--会议录音-->
		<div class="jqmWindow" style="width: 500px; right: 25%;; top: 10%"
			id="jqModelVideo">
			<form name="postVideoForm" id="postVideoForm" method="post" enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					会议录音上传
					<div class="close">
					</div>
				</div>
				<table width="100%" id="cataffSearchTable" cellpadding="10" 
					cellspacing="10">

					
					<tr>
						<td align="right" >
							会议名称：
						</td>
						<td align="left" >
						  <span id="mnr"></span>
						</td>
					</tr>
				
                  <tr>
                  <td align="right">
								上传录音：
				 </td>
								<td align="left">
								<input name="staffMeeting.recordfile" class="fileNum"
									type="hidden" id="recordfile" size="15" />
								<input name="photo" contentEditable="false" type="file"
									class="input" size="15" />
							</td>
                  </tr>
				</table>
				<div align="center">
					<input type="hidden" name="staffMeeting.meetingID" id="meetingIDv"/>
					<input type="button" class="input-button" id="uploadVideo"
						value=" 上传 " />
					<input type="button" class="input-button close" 
						value=" 关闭 " />
				</div>
			</form>
		</div>
		
		


		<iframe name="hidden" width="100%" scrolling="no" marginwidth="0"
			height="0" marginheight="0" frameborder="0" border="0"
			framespacing="0" noresize="noResize"></iframe>
			
			<!-- 显示人员 -->
		
		<div id="socialJqm" class="jqmWindow"
			style="width: 75%; height: 350px; absolute; display: none; left: 13%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="300px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 28px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 300px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>