<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人考勤管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"type="text/css"/>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>/js/ea/validate.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/accifr/js/accift.js"></script>
<script src="<%=basePath%>js/ea/human/adance/attendlog.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var workcalendarid = "";
	var seaDate = '${seaDate}';
	var stus = '${stus}';
</script>
<style>
.rili_cent{
	width:120px;
	height: 80px;
	background:#eee;
}
.r_riqi{
	width:120px;
	height:80px;
	float:left;
}
.r_riqi p{
	width:100%;
	height:40px;
	line-height:50px;
	font-size:16px;
	font-weight:bold;
	text-align:center;
}
.r_riqi .rq{
	font-size:12px;
}
.attendlogTable{
	width: 885px;
}
.tablehead{
	height:40px;
	background:#eee;
}
</style>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">

	<form name="attendlogForm" id="attendlogForm" method="post">
		<s:token></s:token>
		<div class="right">
    	<div class="qh_gg_nav">&nbsp;考勤核算周期预设</div> 
		<input type="submit" name="submit" style="display:none" />
			<table class="attendlogTable" >
				<tr>
					<th align="center" colspan="7">
						<table width="100%">
							<tr>
								<c:forEach var="ls" items="${listls}" varStatus="step">
									<th align="center" class="tablehead">
										${ls[0] }
									</th>
								</c:forEach>
							</tr>
							<tr>
								<c:forEach var="ls" items="${listls}" varStatus="step">
									<td align="center" class="tablehead">
										${ls[1] }
									</td>
								</c:forEach>
							</tr>
						</table>
					</th>
				</tr>
				<tr>
					<th align="center" colspan="2">
						<input type="button" class="input-button button" onclick="sign('01')" value="签到"/>
						<input type="button" class="input-button button" onclick="sign('02')" value="签退"/>
					</th>
					<th width="30" align="center" colspan="3">
						<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'});" id="seaDate" name="seaDate" value="${seaDate }" onchange="foc(seaDate)" readonly="readonly"/>
					</th>
					<th align="center" colspan="2">
						<input type="button" class="input-button button" onclick="extleave('01')" value="加班申请"/>
						<input type="button" class="input-button button" onclick="extleave('02')" value="请假申请"/>
					</th>
				</tr>
				<tr>
					<th class="tablehead">星期一</th>
					<th class="tablehead">星期二</th>
					<th class="tablehead">星期三</th>
					<th class="tablehead">星期四</th>
					<th class="tablehead">星期五</th>
					<th class="tablehead">星期六</th>
					<th class="tablehead">星期日</th>
				</tr>
				<c:forEach var="mapl" items="${mapwc}" varStatus="step">
					<tr>
						 <c:forEach	items="${mapl}" var="l"  varStatus="step2">
						 <td>
						 	<div class="rili_cent dbcil" id="${l.workcalendarid }" onmouseover="mouo(this)" onmouseout="moum(this)" style="border:#e6e7e8 2px solid">
								<div class="r_riqi">
								<c:if test="${l != null }">
									<p class="rq">
										<span id="" <c:if test="${l.status==01 }"> style="color:red"</c:if>>${fn:substring(l.days,0,10)}</span>
									</p>
									<p><span id="">
										<c:if test="${l.listLog== null}">
											<c:if test="${l.status==00 }">工作日</c:if>
											<c:if test="${l.status==01 }"><span style="">休息日</span></c:if>
										</c:if>
										<c:if test="${ l.listLog!= null}">
											${l.listLog.status }
										</c:if>
									</span>
									<div class="jqmWindow"  style="width: 200px; position:fixed; top:30%; right:45%; background-color: #8cb0e4;">
										<div class="drag">
											${l.days}
											<div class="close">
											</div>
										</div>
										<table width="100%" >
												<tr align="center">
													<td width="100px;">签到时间</td>
													<td>
														<c:if test="${l.listLog.signcome != null }">${l.listLog.signcome }</c:if>
														<c:if test="${l.listLog.signcome == null }"><a href="#" onclick="signa('${l.days}')">补签</a></c:if>
													</td>
												</tr>
												<tr align="center">
													<td>签退时间</td>
													<td>
														<c:if test="${l.listLog.signgo != null }">${l.listLog.signgo }</c:if>
														<c:if test="${l.listLog.signgo == null }"><a href="#" onclick="signa('${l.days}')">补签</a></c:if>
													</td>
												</tr>
											<c:forEach	items="${l.listStus}" var="ls"  varStatus="step2">
												<c:if test="${ls.alStatus != '正常' }">
													<tr align="center">
														<td>${ls.alStatus }</td>
														<td>${ls.altime }</td>
													</tr>
												</c:if>
											</c:forEach>
										</table>
									</div>
								</c:if>
							   </div>
							</div>
						 </td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>
	</form>



	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!--加班请假窗口 -->
	<div class="jqmWindow" style="width: 450px; right: 35%; top: 20%"
			id="jqModelExt1">
			<form name="jqModelExtForm" id="jqModelExtForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					加班请假申请
					<div class="close">
					</div>
				</div>
				<table width="100%" id="ExtTable" >
					<tr align="center">
						<td style="width:150px;">
						<font style="color:red">*</font>审批人:
						</td>
						<td align="left">
							<input name="approveName" id="approveName" class="put3" readonly="readonly"/>
							<input type="hidden" name="approveId" id="approveId" />
							<a href="#" onclick="javascript:accift('03')">选择</a>
						</td>
					</tr>
					<tr align="center">
						<td style="width:150px;"><font style="color:red">*</font>项目类型</td>
						<td align="left">
							<s:select list="logstusList" headerKey="" headerValue="请选择项目类型" listKey="confname" listValue="confname" name="leaveWork" id="leaveWork" style="width:155px;"></s:select>
						</td>
					</tr>
					<tr align="center">
						<td><font style="color:red">*</font>开始时间:</td>
						<td align="left">
							<input name="begin" id="beginTime" class="put3" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
							<span id="beginTime" style="color:red"></span>
						</td>
					</tr>
					<tr align="center">
						<td><font style="color:red">*</font>结束时间</td>
						<td align="left">
							<input name="end" id="endTime" class="put3" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
							<span id="endTime" style="color:red"></span>
						</td>
					</tr>
					<tr align="center">
						<td>时长:</td>
						<td align="left">
							<input name="sumTime" id="sumTime" readonly="readonly" />小时
						</td>
					</tr>
					<tr align="center">
						<td>原因:</td>
						<td align="left">
							<textarea rows="3" cols="30" name="remark" class="ckTextLength" maxlength="250"></textarea>
							<input type="hidden" name="state" id="state" value=""/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="save"
						value=" 申请 " />
				</div>
			</form>
		</div>
	<!--补签到签退窗口 -->
	<div class="jqmWindow" style="width: 500px; right: 35%; top: 20%"
			id="jqModelTrim">
			<form name="jqModelTrimForm" id="jqModelTrimForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					补签到签退窗申请
					<div class="close">
					</div>
				</div>
				<table width="100%" id="TrimTable" >
					<tr align="center">
						<td>补签    状态:</td>
						<td align="left">
							<select name="status" style="width:155px;"><option value="00">签到</option><option value="01">签退</option></select>
						</td>
					</tr>
					<tr align="center">
						<td style="width:150px;">
						修改前项目:
						</td>
						<td align="left">
							<input name="oldStus" value="未签" readonly="readonly"/>
						</td>
					</tr>
					
					<tr align="center">
						<td style="width:150px;">
						修改后项目:
						</td>
						<td align="left">
							<select name="newStus" style="width:155px;"><option value="补签到">补签到</option><option value="补签退">补签退</option></select>
						</td>
					</tr>
					<tr align="center">
						<td>修改后时间:</td>
						<td align="left">
							<input name="newtime" onclick="WdatePicker({dateFmt:'HH:mm'});" value="${conf.playtime }"/>
						</td>
					</tr>
					<tr align="center">
						<td>备注:</td>
						<td align="left">
							<textarea rows="3" cols="30" name="remark" class="ckTextLength" maxLength="250"></textarea>
							<input type="hidden" name="days" id="days"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="savetrim"
						value=" 申请 " />
				</div>
			</form>
		</div>
	
</body>
</html>
