<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>投资设备绑定设置</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/finance/InvestDeviceBind/investDeviceBind.js"></script>




<script type="text/javascript">

    var basePath="<%=basePath%>";
	var search = "${search}";
	var ppageNumber = "${pageNumber}";
	var token;
	var carNum = "${carNum}";
	var deviceStatu = "${deviceStatu}";
	var tzName = "${tzName}";
	var tzAccount = "${tzAccount}";
</script>

</head>
<body>

	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">

		<form id="SearchForm" name="SearchForm" method="post">
			<input type="submit" name="submit" style="display:none;" />
			<table>
				<tr>
					<td><strong>投资设备绑定设置</strong></td>
					<td  align="right">
						车牌号：
					</td>
					<td >
						<input name="carNum" class="input" id="carNum" 
							style="margin-left: 2px;" size="7"/>
					</td>
					<td  align="right">
						设备状态：
					</td>
					<td >
						<select name="deviceStatu" id="deviceStatu">
							<option value='' selected="selected">设备状态</option>
							<option value="01">公司投资设备</option>
							<option value="00">挂靠设备</option>
						</select>
					</td>
					<td  align="right">
						投资人名称：
					</td>
					<td >
						<input name="tzName" class="input" id="tzName" 
							style="margin-left: 2px;" size="7"/>
					</td>
					<td  align="right">
						投资人账号：
					</td>
					<td >
						<input name="tzAccount" class="input" id="tzAccount" 
							style="margin-left: 2px;" size="10"/>
					</td>
					<td><input type="button" class="input-button" value="  查询  " style="margin:0px;margin-left:5px;"
						id="tosearch" /> <input type="hidden" value="search" name="search" />
						<input type="hidden" id="riId" name="recruitInfo.riId" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="main_main">
		<table class="JQueryflexme" id="deviceBind">
			<thead>
				<tr class="tablewith">
					<th width="30" align="center">
						选择
					</th>
					<th width="30" align="center">
						编号
					</th>
					<th width="50" align="center">
						二维码
					</th>
					<th width="100" align="center">
						条码
					</th>
					<th width="100" align="center">
					     芯片号
					</th>
					<th width="90" align="center">
						车牌号
					</th>
					<th width="100" align="center">
						投资类别
					</th>
					<th width="100" align="center">
						设备编号
					</th>
					<th width="100" align="center">
						设备名称
					</th>
					<th width="70" align="center">
						公司
					</th>
					<th width="130" align="center">
						部门
					</th>
				   
					<th width="90" align="center">
						投资责任人
					</th>
					 <th width="70" align="center">
						管理责任人
					</th>
					<th width="150" align="center">
						投资人的微分金账号
					</th>
					<th width="70" align="center">
						文件
					</th>
					<th width="90" align="center">
						设备
					</th>
				</tr>
			</thead>
			<tbody>
			<% int number=1; %>
			<c:forEach items="${pageForm.list }" var="x">
					<tr id="${x[10] }">
					    <td>
							<input type="radio" name="deviceBindId" class="JQuerypersonvalue"
								value="${x[10] }" />
						</td>
						<td>
							<span><%=number%></span>
						</td>
						<td>
							<span id="twoCode">${x[0] }</span>
						</td>
						<td>
							<span id="barCode">${x[1] }</span>
						</td>
						<td>
							<span id="xpCode"></span>
						</td>
						<td>
							<span id="carNumber">${x[12] }</span>
						</td>
						<td>
							<span id="investType" >${x[2]=='01'?'教练车':x[2]=='02'?'单车':null }</span>
						</td>
						<td>
							<span id="deviceCode">${x[3] }</span>
						</td>
						<td>
							<span id="deviceName">${x[4] }</span>
						</td>
						<td>
							<span id="companyName">${x[5] }</span>
						</td>
						<td>
							<span id="department">${x[6] }</span>
						</td>
						<td>
							<span id="ivestment">${x[7] }</span>
						</td>
						<td>
							<span id="management">${x[8] }</span>
						</td>
						<td>
							<span id="investAccount">${x[9] }</span>
						</td>
						<td>
						</td>
						<td>
							<span id="device" >${x[11]=='01'?'公司投资设备':'挂靠设备' }</span>
						</td>
					</tr>
					<%
						number++;
					%>
				</c:forEach>
			</tbody> 
		</table>
		<c:import url="../../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/devicebind/ea_selDeviceBind.jspa?carNum=${carNum}&deviceStatu=${deviceStatu}&tzName=${tzName}&tzAccount=${tzAccount}">
			</c:param>
		</c:import>
	</div>
	<div class="jqmWindow" id="jqModel"
		style="display: none; width: 250px; height: 100px; right: 30%; top: 10%;"
		id="jqModelPosition">
		<input type="submit" name="submit" style="display: none" />
		<div class="drag">
			提示
			<div class="close"></div>
		</div>
		<center>
		<p class="tip">tip</p>
		<input type="button" class="confirm input-button" value=" 确定 " /> <input
			type="button" class="input-button close" value=" 取消   " /> </center>

	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>