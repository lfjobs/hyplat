<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>约车配置</title>
	<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/reserveConfig.js"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/Validform_v5.3.2.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/ea/driving/elkc/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" />
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerMessageBox.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/custom/window.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/ligerui.min.js"></script>
	<link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/reseveConfig.css">

	<script type="text/javascript">

        var basePath='<%=basePath%>';

	</script>

</head>
<body>
<form id="saveForm" name="saveForm" method="post" action="<%=basePath%>ea/comconfig/ea_saveCompanyConfig.jspa">
	<input type="hidden" id="ccId" name="tbElycCompanyConfig.ccId" value="${tbElycCompanyConfig.ccId}" />

	<div class="main">

	<div class="head">
		<div>&nbsp;&nbsp;&nbsp;约车配置</div>
	</div>
    <div class="content">
		<div class="left_cont">
			<div class="left_head">&nbsp;&nbsp;&nbsp;约车配置是预约练车的前提，请谨慎修改。以下为配置详细介绍
			</div>
			<div class="configuration-cont">
				<ul>
					<li class="configuration-cont-right">预约记录提前生成天数<a>*</a>：</li>
					<li class="configuration-cont-left"><input type="text" id="genday" name="tbElycCompanyConfig.genday" class="textBox-10" value="${tbElycCompanyConfig.genday==null?7:tbElycCompanyConfig.genday}" datatype="n" nullmsg="请填写数字">&nbsp;&nbsp;(天)</li>
				</ul>
				<div class="configuration-cont-reg1 "id="err_genday">
					<span style="color:green;">建议填写一个小于10的天数，以便显示美观</span>
				</div>
				<ul>
					<li class="configuration-cont-right">学员提前预约天数<a>*</a>：</li>
					<li class="configuration-cont-left"><input type="text" id="studentay" name="tbElycCompanyConfig.studentay" class="textBox-10" value="${tbElycCompanyConfig.studentay==null?"5":tbElycCompanyConfig.studentay}" datatype="n" nullmsg="请填写数字">&nbsp;&nbsp;(天)</li>
				</ul>
				<div class="configuration-cont-reg1 " id="err_studentay">
					<span style="color:green;">表示学员在网上进行自助约车时，可以提前的天数，应小于等于预约记录提前生成天数。</span>
				</div>

				<ul>
					<li class="configuration-cont-right">学员每天可约车时段<a>*</a>：</li>
					<li class="configuration-cont-left">

						从&nbsp;&nbsp;<input type="text" id="startTime" onfocus="WdatePicker({dateFmt:'HH:mm:ss',minDate:'1:00:00',maxDate:'23:00:00'})" name="tbElycCompanyConfig.startTime" class="textBox-10" value="${tbElycCompanyConfig.startTime==null?"08:00:00":tbElycCompanyConfig.startTime}" datatype="*">&nbsp;&nbsp;到&nbsp;&nbsp;
						<input type="text" id="endTime" onfocus="WdatePicker({dateFmt:'HH:mm:ss',minDate:'1:00:00',maxDate:'23:00:00'})" name="tbElycCompanyConfig.endTime" class="textBox-10" value="${tbElycCompanyConfig.endTime==null?"20:00:00":tbElycCompanyConfig.endTime}" datatype="*">

					</li>
				</ul>
				<div class="configuration-cont-reg1" id="err_startTime">
					<span style="color:green;">用于学员在网上自助约车时使用，学员在该时间段内可以约车，否则不能约车。</span>
				</div>
				<ul>
					<li class="configuration-cont-right">学员取消约车时间<a>*</a>：</li>
					<li class="configuration-cont-left">
						&nbsp;&nbsp;提前&nbsp;&nbsp;
						<input type="text" id="cancelDetail" class="textBox-10" onblur="checkCancel()" name="tbElycCompanyConfig.cancelDetail" value="${tbElycCompanyConfig.cancelDetail==null?"4":tbElycCompanyConfig.cancelDetail}"/>

						&nbsp;&nbsp;<span id="firstSpan">小时</span>&nbsp;&nbsp;



					</li>
				</ul>
				<div class="configuration-cont-reg1" id="err_cancelDetail">
					<span style="color:green;">例如：在练车时间前5小时。本配置项只限制学员自主约车。</span>
				</div>

				<ul>
					<li class="configuration-cont-right">学员是否可约当天<a>*</a>：</li>
					<li class="configuration-cont-left">

						<select id="studentCanBookingToday" name="tbElycCompanyConfig.studentCanBookingToday" class="textBox-10"  selectedvalue="1"  datatype="*">
							<option value="1">可约</option>
							<option value="0">不可约</option>
						</select>
					</li>
				</ul>
				<ul>
					<li class="configuration-cont-right">学员每天可练车小时数限制<a>*</a>：</li>
					<li class="configuration-cont-left"><input type="text" id="studentLeaveTime" name="tbElycCompanyConfig.studentLeaveTime" class="textBox-10" value="${tbElycCompanyConfig.studentLeaveTime==null?4:tbElycCompanyConfig.studentLeaveTime}" datatype="n" nullmsg="请填写数字"></li>
				</ul>
				<div class="configuration-cont-reg1" id="err_studentLeaveTime" >
					<span style="color:green;">表示学员在网上进行自助约车时，可以提前的天数，应小于等于预约记录提前生成天数。</span>
				</div>
			</div>
		</div>

	</div>
	<div class="maintenance-button">
		<input type="submit" value="&nbsp;&nbsp;&nbsp;保存修改" class="configuration-saveBtn">
		<input type="button" value="&nbsp;&nbsp;&nbsp;初始化约车时段" id="createDetail" class="configuration-closeBtn">
	</div>
</div>
</form>
</body>


</html>
