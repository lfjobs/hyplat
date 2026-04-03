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
	<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/reservationModeAdd.js"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/Validform_v5.3.2.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/ea/driving/elkc/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" />
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerMessageBox.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/custom/window.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/ligerui.min.js"></script>
	<%--<link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/reseveConfig.css">--%>
	<link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/public.css"  type="text/css" media="screen" />
	<link rel="stylesheet" href="<%=basePath%>/css/ea/elkc/main1.css" type="text/css" media="screen" />



	<title>预约模式维护</title>
	<script type="text/javascript" >
        var v_detail = "${fn:length(detailList)}";
        var basePath = "<%=basePath%>";


	</script>
</head>

<body>
<form id="saveForm" name="saveForm" action="<%=basePath%>/ea/reservmode/sajax_ea_saveReservMode.jspa" method="post">
	<div class="Reservation_information-main">
		<!--head头部-->
		<div class="Reservation_information-head">
			<div>&nbsp;&nbsp;&nbsp;预约模式维护</div>
		</div>
		<!--head头部结束-->
		<!--content内容-->
		<input type="hidden" id="id" name="reservationMode.reservationModeId" value="${reservationMode.reservationModeId}" />
		<s:if test="reservationMode.rmKey!=null">
			<input type="hidden" id="rmKey" name="reservationMode.rmKey" value="${reservationMode.rmKey}" />
			<input type="hidden" id="companyId" name="reservationMode.companyId" value="${reservationMode.companyId}" />
			<input type="hidden" id="createdate" name="reservationMode.createdate" value="${reservationMode.createdate}" />
			<input type="hidden" id="createperson" name="reservationMode.createperson" value="${reservationMode.createperson}" />

		</s:if>

		<div class="Reservation_mode_maintenance-content">
			<!--left_cont-->
			<div class="Reservation_mode_maintenance-left_cont">
				<div class="Reservation_mode_maintenance-head">&nbsp;&nbsp;&nbsp;预约模式基本信息</div>
				<div class="Reservation_mode_maintenance-name">
					<ul>
						<li>模式名称</li>
						<li class="Reservation_mode_maintenance-name-li">
							<input type="text" id="modeName" name="reservationMode.modeName" class="textBox-10" value="${reservationMode.modeName}" datatype="z2-20" focusMsg="请输入模式名称" nullmsg="请输入模式名称" errormsg="模式名称应为2至20个没有空格的汉字！"/>
						</li>
					</ul>
					<ul>
						<li class="Reservation_mode_maintenance-name-reg" id="err_modeName"></li>
					</ul>
				</div>
				<div class="Reservation_mode_maintenance-State">
					<ul>
						<li>状态</li>
						<li class="Reservation_mode_maintenance-State-li">
							<select id="status" name="reservationMode.status" selectedValue="${reservationMode.status}" datatype="*" nullmsg="请选择模式状态" style="height:28px;">
								    <option value="">请选择</option>
									<option value="00">未启用</option>
								    <option value="01">启用</option>

							</select>
							<span id="err_status"></span>
						</li>
					</ul>
				</div>
			</div>
			<!--left_cont结束-->
			<!--right_cont-->
			<div class="Reservation_mode_maintenance-right_cont">
				<div class="Reservation_mode_maintenance-head">&nbsp;&nbsp;&nbsp;预约详细时间段设置</div>
				<div class="Reservation_mode_maintenance-Prompt">&nbsp;&nbsp;&nbsp;提示：请填写各个可预约时间段的开始时间和结束时间，点击“+”添加一行可预约时间段，点击删除按钮进行删除。</div>
				<div class="One-button-configuration">
					<table width="100%" border="0">
						<tr>
							<td class="oneBtn"><input id="createDetail" type="button" class="one-button" value="一键生成" /></td>
							<td align="left"><span class="blue">[一键生成：即根据"约车配置"中"校本部每天练车时间"生成约车时间段，您可以根据需求增加或删除时间段，但时间段不能重叠]</span></td>
						</tr>
					</table>
				</div>
				<s:if test="detailList.size()!=0" >
					<s:iterator value="detailList" var="item" status="detail">
						<div class="add_time" id="content_${detail.index}">
							<ul>
								<li class="Btime">开始时间</li>
								<li><input type="text" id="startTime${detail.index}" onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'1:00',maxDate:'23:00'})" name="detailList[${detail.index}].startTime" class="textBox-10" value="${item.startTime}" datatype="*" nullmsg="请选择开始时间"/></li>
								<li class="Otime">结束时间</li>
								<li><input type="text" id="endTime${detail.index}" onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'1:00',maxDate:'23:00'})" name="detailList[${detail.index}].endTime" class="textBox-10" value="${item.endTime}" datatype="*" nullmsg="请选择结束时间"/></li>

								<li><img src="<%=basePath%>images/ea/driving/elkc/delete.png" width="23" onclick="deleteLine('${detail.index}')"/></li>
							</ul>
						</div>
						<div class="add_time" id="tip_${detail.index}">
							<ul>
								<li class="add_time-reg" id="err_startTime${detail.index}"></li>
								<li class="add_time-reg1" id="err_endTime${detail.index}"></li>

							</ul>
						</div>
					</s:iterator>
				 </s:if>
					<s:else>
						<div class="add_time" id="content_0">
							<ul>
								<li class="Btime">开始时间</li>
								<li><input type="text" id="startTime0" onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'1:00',maxDate:'23:00'})" name="detailList[0].startTime" class="textBox-10" value="" datatype="*" nullmsg="请选择开始时间"/></li>
								<li class="Otime">结束时间</li>
								<li><input type="text" id="endTime0" onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'1:00',maxDate:'23:00'})" name="detailList[0].endTime" class="textBox-10" value="" datatype="*" nullmsg="请选择结束时间"/></li>
								<li><img src="<%=basePath%>images/ea/driving/elkc/delete.png" width="23" onclick="deleteLine('0')"/></li>
							</ul>
						</div>
						<div class="add_time" id="tip_0">
							<ul>
								<li class="add_time-reg" id="err_startTime0"></li>
								<li class="add_time-reg1" id="err_endTime0"></li>
								<li class="add_time-reg2" id="err_lcPrice0"></li>
							</ul>
						</div>
					</s:else>
				<div class="Reservation_mode_maintenance-right_cont-but" id="changeMain">
					<ul>
						<li><input type="button" onclick="addLine()"/></li>
						<li>点击“+”添加可预约时间段</li>
					</ul>
				</div>
			</div>

			<!--right_cont结束-->
		</div>
		<!--content内容结束-->
		<div class="Reservation_mode_maintenance-button">
			<input type="submit" value="&nbsp;&nbsp;&nbsp;保存" id="saveMode" class="Reservation_mode_maintenance-saveBtn" />
			<input type="button" value="返回" class="Reservation_mode_maintenance-closeBtn" onclick="window.location.href='<%=basePath%>ea/reservmode/ea_findReservationModeList.jspa'"/>
		</div>
	</div>
	<%--<script type="text/javascript" src="${base}/lib/common/js/selectedValue.js"></script>--%>
</form>
</body>
</html>
