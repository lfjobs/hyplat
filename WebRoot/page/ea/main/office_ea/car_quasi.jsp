<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>车辆准载座位管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
</style>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var quasiID = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var carID = '${carquasi.carID}';
   var notoken = 0;
   var carInformation = '${carInformation.carNum}';

</script>
		<script src="<%=basePath%>js/ea/office_ea/carquasi.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/carquasi_edit.js"></script>
	</head>
	<body>
		<form name="carquasiForm" id="carquasiForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div id="main_main" class="main_main">
				<table class="carquasi">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="80" align="center">
								车牌号
							</th>
							<th width="80" align="center">
								车型
							</th>
							<th width="80" align="center">
								车架号
							</th>
							<th width="50" align="center">
								排号
							</th>
							<th width="50" align="center">
								座号
							</th>
							<th width="60" align="center">
								座位状态
							</th>
							<th width="50" align="center">
								准载人数
							</th>
							<th width="50" align="center">
								实载人数
							</th>
							<th width="50" align="center">
								超载人数
							</th>
							<th width="100" align="center">
								车辆所属单位
							</th>
							<th width="100" align="center">
								单位责任人
							</th>
							<th width="100" align="center">
								单位联系方式
							</th>
							<th width="50" align="center">
								驾驶员
							</th>
							<th width="180" align="center">
								手机号
							</th>
							<th width="180" align="center">
								接送起地点
							</th>
							<th width="180" align="center">
								接送止地点
							</th>
							<th width="180" align="center">
								接送起时间
							</th>
							<th width="180" align="center">
								接送止时间
							</th>
							<th width="50" align="center">
								乘坐方式
							</th>
							<th width="50" align="center">
								启程时间
							</th>
							<th width="50" align="center">
								抵达时间
							</th>
							<th width="180" align="center">
								单位
							</th>
							<th width="180" align="center">
								乘坐人姓名
							</th>
							<th width="180" align="center">
								手机号
							</th>
							<th width="180" align="center">
								承办单位
							</th>
							<th width="180" align="center">
								承办单位责任人
							</th>
							<th width="180" align="center">
								承办人联系方式
							</th>
							<th width="180" align="center">
								备注
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody id="tbwid">

						<tr class="td_bg01 saveAjax model2" id="sa" style="display: none;">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${carID}" />
								<input type="hidden" name="quasiKey" id="quasiKey" />
								<input type="hidden" name="quasiID" id="quasiID" />
								<input type="hidden" name="companyID" id="companyID" />
								<input type="hidden" name="organizationID" id="organizationID" />
								<input type="hidden" name="carID" value="${carquasi.carID}"
									id="carID" />
							</td>
							<td>
								
								<span id="carNum">${carquasi.carNum}</span>
							</td>
							<td>
								<span id="carType">${carquasi.carType}</span>
							</td>
							<td>
								<span id="carFrameNum">${carquasi.carFrameNum}</span>
							</td>
							<td>
								<input name="numeral" id="numeral" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="seatNum" id="seatNum" size="10"
									class=" td_bg011" />
							</td>
							<td>
								<s:select list="#{'00':'已坐','01':'未坐'}" class="dis"
									name="seatState"  id="xxx" theme="simple"></s:select>
							</td>
							<td>
								<input name="QuasiLoad" id="QuasiLoad" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="realLoad" id="realLoad" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="overLoad" id="overLoad" size="10"
									class="td_bg01" />
							</td>
							
							<td>
								<input name="carUtil" id="carUtil" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="utilPeople" id="utilPeople" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="utilTel" id="utilTel" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="driver" id="driver" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="driverTel" id="driverTel" size="10"
									class="td_bg01" />
							</td>
							<td>
								<s:select list="addlisyt" listKey="codeID"  id="xxx" listValue="codeValue" name="beganPlace"  theme="simple"></s:select>
							</td>
							<td>
								<input name="endPlace" id="endPlace" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="beganTime" id="beganTime" size="10"
									onfocus="date(this);" class=" td_bg011" />
							</td>
							<td>
								<input name="endTime" id="endTime" size="10"
									onfocus="date(this);" class=" td_bg011" />
							</td>
							<td>
							<s:select list="bywaylist" listKey="codeID"  id="xxx" listValue="codeValue" name="byWay"  theme="simple"></s:select>
							</td>
							<td>
								<input name="departureTime" id="departureTime" size="10"
									onfocus="date(this);" class=" td_bg011" />
							</td>
							<td>
								<input name="arriveTime" id="arriveTime" size="10"
									onfocus="date(this);" class=" td_bg011" />
							</td>
							<td>
								<input name="util" id="util" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="passenger" id="passenger" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="passengerTel" id="passengerTel" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="undertakeUtil" id="undertakeUtil" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="undertakePeo" id="undertakePeo" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="undertakeTel" id="undertakeTel" size="10"
									class="td_bg01" />
							</td>
							<td>
								<input name="remark" id="remark" size="10"
									class="td_bg01" />
							</td>	
						</tr>

						<s:iterator value="pageForm.list">
							<tr id="${quasiID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${quasiID}" />
									<input type="hidden" name="quasiKey" id="quasiKey"
										value="${quasiKey}" />
									<input type="hidden" name="quasiID" id="quasiID"
										value="${quasiID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carquasi.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
									<span id="carNum">${carquasi.carNum}
									</span>

								</td>
								<td class="td_bg01">
									<span id="carType">${carquasi.carType}
									</span>
							
								</td>
								<td class="td_bg01">
									<span id="carFrameNum">${carquasi.carFrameNum}
									</span>
								</td>
								<td class="td_bg01">
									<span id="numeral"><s:property value="numeral" />
									</span>
									<input name="numeral" size="10" class="model1 "
										value="${numeral}" />
								</td>
								<td class="td_bg01">
									<span id="seatNum"><s:property value="seatNum" />
									</span>
									<input name="seatNum" size="10" class="model1"
										value="${seatNum}" />
								</td>
								<td class="td_bg01">
									<span id="seatState"></span>
									<s:select list="#{'00':'已坐','01':'未坐'}" class="dis model1"
									name="seatState"  id="seatState" theme="simple" style="display:none"></s:select>
								</td>
								<td class="td_bg01">
									<span id="QuasiLoad"><s:property value="QuasiLoad" />
									</span>
									<input name="QuasiLoad" size="10" class="model1"
										value="${QuasiLoad}" />
								</td>
								<td class="td_bg01">
									<span id="realLoad"><s:property value="realLoad" />
									</span>
									<input name="realLoad" size="10" class="model1"
										value="${realLoad}" />
								</td>
								<td class="td_bg01">
									<span id="overLoad"><s:property value="overLoad" />
									</span>
									<input name="overLoad" size="10" class="model1"
										value="${overLoad}" />
								</td>
								
								<td class="td_bg01">
									<span id="carUtil"><s:property value="carUtil" />
									</span>
									<input name="carUtil" size="10" class="model1"
										value="${carUtil}" />
								</td>
								<td class="td_bg01">
									<span id="utilPeople"><s:property value="utilPeople" />
									</span>
									<input name="utilPeople" size="10" class="model1"
										value="${utilPeople}" />
								</td>
								<td class="td_bg01">
									<span id="utilTel"><s:property value="utilTel" />
									</span>
									<input name="utilTel" size="10" class="model1"
										value="${utilTel}" />
								</td>
								<td class="td_bg01">
									<span id="driver"><s:property value="driver" />
									</span>
									<input name="driver" size="10" class="model1"
										value="${driver}" />
								</td>
								<td class="td_bg01">
									<span id="driverTel"><s:property value="driverTel" />
									</span>
									<input name="driverTel" size="10" class="model1"
										value="${driverTel}" />
								</td>
								<td class="td_bg01">
								
								<span id=" beganPlace"></span>
           		 	<s:select   list="addlisyt" class="dis model1"  name="beganPlace"   listKey="codeID" listValue="codeValue" style="display:none"  theme="simple" ></s:select>
								</td>
								<td class="td_bg01">
									<span id="endPlace"><s:property value="endPlace" />
									</span>
									<input name="endPlace" size="10" class="model1"
										value="${endPlace}" />
								</td>
								<td class="td_bg01">
									<span id="beganTime"><s:property value="beganTime" />
									</span>
									<input name="beganTime" size="10" class="model1 "
										onfocus="date(this);" value="${beganTime}" />
								</td>
								<td class="td_bg01">
									<span id="endTime"><s:property value="endTime" />
									</span>
									<input name="endTime" size="10" class="model1 "
										onfocus="date(this);" value="${endTime}" />
								</td>
								<td class="td_bg01">
									<span id=" byWay"></span>
           		 	<s:select   list="bywaylist" class="dis model1"  name="byWay"   listKey="codeID" listValue="codeValue" style="display:none"  theme="simple" ></s:select>
								</td>
								<td class="td_bg01">
									<span id="departureTime"><s:property value="departureTime" />
									</span>
									<input name="departureTime" size="10" class="model1 "
										onfocus="date(this);" value="${departureTime}" />
								</td>
								<td class="td_bg01">
									<span id="arriveTime"><s:property value="arriveTime" />
									</span>
									<input name="arriveTime" size="10" class="model1 "
										onfocus="date(this);" value="${arriveTime}" />
								</td>
								<td class="td_bg01">
									<span id="util"><s:property value="util" />
									</span>
									<input name="util" size="10" class="model1"
										value="${util}" />
								</td>
								<td class="td_bg01">
									<span id="passenger"><s:property value="passenger" />
									</span>
									<input name="passenger" size="10" class="model1"
										value="${passenger}" />
								</td>
								<td class="td_bg01">
									<span id="passengerTel"><s:property value="passengerTel" />
									</span>
									<input name="passengerTel" size="10" class="model1"
										value="${passengerTel}" />
								</td>
								<td class="td_bg01">
									<span id="undertakeUtil"><s:property value="undertakeUtil" />
									</span>
									<input name="undertakeUtil" size="10" class="model1"
										value="${undertakeUtil}" />
								</td>
								<td class="td_bg01">
									<span id="undertakePeo"><s:property value="undertakePeo" />
									</span>
									<input name="undertakePeo" size="10" class="model1"
										value="${undertakePeo}" />
								</td>
								<td class="td_bg01">
									<span id="undertakeTel"><s:property value="undertakeTel" />
									</span>
									<input name="undertakeTel" size="10" class="model1"
										value="${undertakeTel}" />
								</td>
								<td class="td_bg01">
									<span id="remark"><s:property value="remark" />
									</span>
									<input name="remark" size="10" class="model1"
										value="${remark}" />
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carquasi/ea_getCarQuasiList.jspa?carquasi.carID=${carquasi.carID}&pageNumber=${pageNumber}">
                </c:param>
            </c:import>
			</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
