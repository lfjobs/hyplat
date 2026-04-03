<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>个人工作日志</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
			<link rel="stylesheet" type="text/css" href="styles.css">
			<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		-->
		<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/signApp/mobilelogbook.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/signApp/mobilelogbook.css">
		<script type="text/javascript">
			var select = 1;
			var token = 0;
			var logBookID = '';
			var today=new Date();
			var month = today.getMonth()+1;
			var toda=today.getYear()+"-"+month+"-"+today.getDate();
			var pbasePath='<%=basePath%>';
			var ppageNumber='${pageNumber}';
			var logbookstaffID='${logbook.staffID}';
			var psdate='${sdate}';
			var pedate='${edate}';
			var staffName='${staffName}';
			var scoreSort = '${scoreSort}'
			var status = '${status}';
			var notoken = 0;
		</script>
	</head>
	<body>
		<div>
			<form action="" name="logFrom" id="logFrom" method="post">
				<s:token></s:token>
				<input type="submit" name="submit" style="display: none" />
			</form>
			<form name="logbookForm" enctype="multipart/form-data"
				id="logbookForm" method="post">
				<s:token></s:token>
				<input type="submit" name="submit" style="display: none" />
				<div id="titleDiv">
					<table id="title" cellspacing="1">
						<tbody>
						<tr>
							<td class="in" colspan="4" height="25">
								<span class="titlefont">工作日志----当前人员${staffName}</span>
							</td>
						</tr>
						<tr>
							<td class="in" id="append">
								<img src="<%=basePath%>/js/jqModal/css/images_blue/add.png"><span>添加</span>
							</td>
							<td class="in" id="edit">
								<img src="<%=basePath%>/js/jqModal/css/images_blue/edit.png"><span>修改</span>
							</td>
							<td class="in" id="save">
								<img src="<%=basePath%>/js/jqModal/css/images_blue/add.png"><span>全部保存</span>
							</td>
							<td class="in" id="delete">
								<img src="<%=basePath%>/js/jqModal/css/images_blue/close.png"><span>删除</span>
							</td>
						</tr>
						</tbody>
					</table>
				</div>
				<div id="showLogDiv">
					<table class="address">
						<tbody>
						<tr id="sa" style="display: none">
							<td class="in">
								<table class="tablefont" cellspacing="3">
									<tbody>
									<tr>
										<td class="in" width="32" height="22">
											<input type="radio" name="a" class="JQuerypersonvalue" id="selected"></td>
										<td colspan="3">
											<table class="tablefont" cellspacing="0">
												<tbody>
												<tr>
													<td class="in" width="32" height="22"><span>日期</span></td>
													<td colspan="2" class="in" width="68" height="22">
														<input type="text" name="todaydate" id="todaydate"
															size="12" readonly="true" onfocus="date(this)"></td>
												</tr>
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td class="in" width="32" height="22"><span>录入时间</span></td>
										<td class="in" colspan="3" width="68" height="22"><span class="font">系统自动生成</span></td>
									</tr>
									<tr>
										<td class="in" width="32" height="22"><span>起时间</span></td>
										<td class="in" width="68" height="22">
											<input type="text" name="startdate" id="startdate" size="8"
												onkeyup="(this.val=function(){
										 this.value=this.value.replace(/[^0-9:]+/,'');}).call(this)"></td>
										<td class="in" width="32" height="22"><span>止时间</span></td>
										<td class="in" width="68" height="22">
											<input type="text" name="enddate" id="enddate" size="8"
												onkeyup="(this.val=function(){
										 this.value=this.value.replace(/[^0-9:]+/,'');}).call(this)"></td>
									</tr>
									<tr>
										<td class="in" width="32" height="22"><span>工作地点</span></td>
										<td class="in" colspan="3" width="180" height="22">
											<input type="text" name="jobLocation" id="jobLocation" size="24"></td>
									</tr>
									<tr>
										<td class="in" width="32" height="22"><span>完成<br>工作内容</span></td>
										<td class="in" colspan="3" width="180" height="22">
											<input type="text" name="jobContent" id="jobContent" size="24"></td>
									</tr>
									<tr>
										<td class="in" width="32" height="22"><span>得分类别</span></td>
										<td class="in" width="68" height="22"><span class="font">基本积分</span>
											<input type="hidden" name="scoreSort" id="scoreSort"
												value="scode201007306kdf8m76me0000000002" /></td>
										<td class="in" width="32" height="22"><span>得分</span></td>
										<td class="in" width="68" height="22">
											<input type="text" name="bisect" id="bisect" size="8"
												onkeyup="(this.val=function(){
											this.value=this.value.replace(/[^0-9.]+/,'');}).call(this)">
											<input type="hidden" name="staffID" id="staffID" value="${logbook.staffID}" />
											<input type="hidden" name="logBookKey" id="logBookKey" />
											<input type="hidden" name="logBookID" id="logBookID" /></td>
									</tr>
									<tr>
										<td class="in" width="32" height="22"><span>附件</span></td>
										<td class="in" colspan="3" width="180" height="22">
											<input type="file" name="photo" id="photo" size="15" contentEditable="false" /></td>
									</tr>
									<tr>
										<td class="in" width="32" height="22"><span>主管签字</span></td>
										<td class="in" width="68" height="22">
											<input type="text" name="supervisor" id="supervisor" size="8"></td>
										<td class="in" width="32" height="22"><span>人事<br>主管管理</span></td>
										<td class="in" width="68" height="22">
											<input type="text" name="staffingManage" id="staffingManage" size="8"></td>
									</tr>
									<tr>
										<td class="in" width="32" height="22"><span>公司经理</span></td>
										<td class="in" width="68" height="22">
											<input type="text" name="manager" id="manager" size="8">
										</td>
										<td class="in" width="32" height="22"><span>状态</span></td>
										<td class="in" width="68" height="22">
											<input type="text" name="status" readonly="true" size="8"></td>
									</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<s:iterator value="pageForm.list">
							<tr id="${logBookID}">
								<td class="in">
									<table id="tablefont" class="tablefont" cellspacing="3">
										<tbody>
										<tr>
											<td class="in" height="20">
												<input type="radio" name="a" class="JQuerypersonvalue"
													id="selected" value="${logBookID}"></td>
											<td colspan="3">
												<table class="tablefont" cellspacing="0">
													<tr>
														<td class="in" width="25" height="22"><span>日期</span></td>
														<td class="in ${logBookID} font" colspan="2" height="22">
															<span class="font todaydate">${todaydate}</span>
															<input type="text" name="todaydate" class="model"
																id="todaydate" value="${todaydate}" size="12"
																readonly="true" onfocus="date(this)"></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td class="in" width="32" height="22"><span>录入时间</span></td>
											<td class="in ${logBookID} font" colspan="3" height="22">
												<span class="font">${inputDate}</span>
												<input type="text" name="inputDate" class="model"
													id="inputDate" value="${inputDate}" size="24" readonly="true"></td>
										</tr>
										<tr>
											<td class="in" width="32" height="22"><span>起时间</span></td>
											<td class="in ${logBookID} font" width="68" height="22">
												<span class="font">${startdate}</span>
												<input type="text" name="startdate" class="model"
													id="startdate" value="${startdate}" size="8"
													onkeyup="(this.val=function(){
												this.value=this.value.replace(/[^0-9:]+/,'');}).call(this)"></td>
											<td class="in" width="32" height="22"><span>止时间</span></td>
											<td class="in ${logBookID} font" width="68" height="22">
												<span class="font">${enddate}</span>
												<input type="text" name="enddate" class="model" id="enddate"
													value="${enddate}" size="8" onkeyup="(this.val=function(){
												this.value=this.value.replace(/[^0-9:]+/,'');}).call(this)"></td>
										</tr>
										<tr>
											<td class="in" width="32" height="22"><span>工作地点</span></td>
											<td class="in ${logBookID} font" colspan="3" width="180"
												height="22"><span class="font">${jobLocation}</span>
												<input type="text" name="jobLocation" class="model"
													id="jobLocation" value="${jobLocation}" size="24"></td>
										</tr>
										<tr>
											<td class="in" width="32" height="22"><span>完成<br>工作内容</span></td>
											<td class="in ${logBookID} font" colspan="3" width="180"
												height="22"><span class="font">${jobContent}</span>
												<input type="text" name="jobContent" class="model"
													id="jobContent" value="${jobContent}" size="24"></td>
										</tr>
										<tr>
											<td class="in" width="32" height="22"><span>得分类别</span></td>
											<td class="in" width="68" height="22">
												<span class="font">${scoreSortName}</span></td>
											<td class="in" width="32" height="22"><span>得分</span></td>
											<td class="in ${logBookID} font" width="68" height="22">
												<span class="font">${bisect}</span>
												<input type="text" name="bisect" class="model" id="bisect"
													value="${bisect}" size="8" onkeyup="(this.val=function(){
												this.value=this.value.replace(/[^0-9.]+/,'');}).call(this)">
												<input type="hidden" name="status" value="${status}" />
												<input type="hidden" name="staffID" value="${staffID}" />
												<input type="hidden" name="logBookKey" value="${logBookKey}" />
												<input type="hidden" name="logBookID" value="${logBookID}" />
												<input type="hidden" name="scoreSort" value="${scoreSort}" /></td>
										</tr>
										<tr>
											<td class="in" width="32" height="22"><span>附件</span></td>
											<td class="in ${logBookID} font" colspan="3" width="180"
												height="22"><span><s:if test="attachment==null">无</s:if></span>
												<s:else><span id="photo" onclick="lookfileNum('${attachment}');">
												<a href="#">查看</a> </span></s:else>
												<input type="file" name="photo" class="model" size="15" contentEditable="false" />
												<input type="hidden" name="attachment" value="${attachment}" class="model1" /></td>
										</tr>
										<tr>
											<td class="in" width="32" height="22"><span>主管签字</span></td>
											<td class="in ${logBookID} font" width="68" height="22">
												<span class="font">${supervisor}</span>
												<input type="text" name="supervisor" class="model"
													id="supervisor" value="${supervisor}" size="8"></td>
											<td class="in" width="32" height="22"><span>人事<br>主管管理</span></td>
											<td class="in ${logBookID} font" width="68" height="22">
												<span class="font">${staffingManage}</span>
												<input type="text" name="staffingManage" class="model"
													id="staffingManage" value="${staffingManage}" size="8"></td>
										</tr>
										<tr>
											<td class="in" width="32" height="22"><span>公司经理</span></td>
											<td class="in ${logBookID} font" width="68" height="22">
												<span class="font">${manager}</span>
												<input type="text" name="manager" class="model" id="manager"
													value="${manager}" size="8"></td>
											<td class="in" width="32" height="22"><span>状态</span></td>
											<td class="in ${logBookID} font" width="68" height="22">
												<input type="hidden" id="status" value="${status}">
												<span>${status=='01'?'已加锁':'未加锁'}</span></td>
										</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</s:iterator>
						</tbody>
					</table>
				</div>
			</form>
		</div>
		<iframe name="hidden" class="model" width="100%" height="0"></iframe>
	</body>
</html>
