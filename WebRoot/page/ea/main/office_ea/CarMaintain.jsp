<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>车辆维护信息</title>
		<style type="text/css">
		.xx{
			color:#FF0000;
			margin-right:2px;} 
		</style>
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
	<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript">
		 var basePath = '<%=basePath%>';
		  var maintainID = '';
		 var search='${search}';
		 var personurl = "";
		 var notoken = 0;
		 var pNumber = ${pageNumber};
		 var carID=parent.carID;
		 var token=0;
		 var type='${type}';
		</script>
	</head>
	<SCRIPT type="text/javascript" src="<%=basePath%>js/ea/office_ea/CarMaintain.js"></SCRIPT>
	<body>
		<form name="carMaintainForm" id="carMaintainForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="60" align="center">
								车牌号
							</th>
							<th width="60" align="center">
								车型
							</th>
							<th width="60" align="center">
								车架号
							</th>
							<th width="100" align="center">
								发动机号
							</th>
							<th width="200" align="center">
								负责人
							</th>
							<th width="60" align="center">
								保养人
							</th>
							<th width="60" align="center">
								保养地点
							</th>
							<th width="60" align="center">
								保养类型
							</th>
							<th width="60" align="center">
								保养合同编号
							</th>
							<th width="60" align="center">
								出厂里程标识值
							</th>
							<th width="60" align="center">
								质量检验员
							</th>
							<th width="60" align="center">
								承保养单位
							</th>
							<th width="60" align="center">
								进厂日期
							</th>
							<th width="60" align="center">
								出厂日期
							</th>
							<th width="60" align="center">
								保养方接车人
							</th>
							<th width="60" align="center">
								接车日期
							</th>
							<th width="80" align="center">
								保养内容
							</th>
							<th width="60" align="center">
								保养费用
							</th>
							<th width="80" align="center">
								备注
							</th>

					</thead>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr class="td_bg01 saveAjax" id="${maintainID}">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${maintainID}" />
								</td>
								<td class="td_bg01">
									<span id="carnumber">${carnumber}</span>
								</td>
								<td class="td_bg01">
									<span id="carType">${carType}</span>
								</td>
								<td class="td_bg01">
									<span id="frameNumber">${frameNumber}</span>
								</td>
								<td class="td_bg01">
									<span id="engineNum">${engineNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carPeople">${carPeople}</span>
								</td>
								<td class="td_bg01">
									<span id="repairpeople" >${repairpeople}</span>
								</td>
								<td class="td_bg01">
									<span id="maintenanceAddress">${maintenanceAddress}</span>
								</td>
								<td class="td_bg01">
									<span id="maintainType">${maintainType}</span>
								</td>
								<td class="td_bg01">
									<span id="maintainISBN">${maintainISBN}</span>
								</td>
								<td class="td_bg01">
									<span id="totalmileage">${totalmileage}</span>
								</td>
								<td class="td_bg01">
									<span id="qualitysurveyor">${qualitysurveyor}</span>
								</td>
								<td class="td_bg01">
									<span id="repaireCompany">${repaireCompany}</span>
								</td>
								<td class="td_bg01">
									<span id="intodate">${fn:substring(intodate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="outdate">${fn:substring(outdate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="connectcarpeople">${connectcarpeople}</span>
								</td>
								<td class="td_bg01">
									<span id="takebackdate">${fn:substring(takebackdate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="maintainContext">${maintainContext}</span>
								</td>
								<td class="td_bg01">
									<span id="maintainCost">${maintainCost}</span>
								</td>
								<td class="td_bg01">
									<span id="remark">${remark}</span>
									<span id="maintainKey" style="display:none">${maintainKey}</span>
                                 	<span id="maintainID"  style="display:none">${maintainID}</span>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carmaintain/ea_getListCarMaintain.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
		</form>
		<!-- 车辆维护信息的添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 680px; left: 65%; top: 15%" id="jqModel">
			<form name="addForm" id="addForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							添加
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" cellpadding="5"
						cellspacing="5">
						<tr>
							<td style="width:15%;" align="right">
								<span class="xx">*</span>保养人：
							</td>
							<td style="width:33%;">
								<input name="carMaintain.repairpeople" id="repairpeople"
									class="repairpeople isremove put3" size="20" />
							</td>
							<td style="width:19%;" align="right">
								<span class="xx">*</span>保养地点：
							</td>
							<td>
								<input name="carMaintain.maintenanceAddress"
									id="maintenanceAddress"
									class="maintenanceAddress isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>保养类型：
							</td>
							<td>
								<input name="carMaintain.maintainType" id="maintainType"
									class="maintainType isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>保养合同编号：
							</td>
							<td>
								<input name="carMaintain.maintainISBN" id="maintainISBN"
									size="20" class="maintainISBN isremove put3" />
							</td>
						</tr>
						<tr>

							<td align="right">
								出厂里程标识值：
							</td>
							<td>
								<input name="carMaintain.totalmileage" id="totalmileage"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>质量检验员：
							</td>
							<td>
								<input name="carMaintain.qualitysurveyor" id="qualitysurveyor"
									size="20" class="qualitysurveyor isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>承保养单位：
							</td>
							<td>
								<input name="carMaintain.repaireCompany" id="repaireCompany"
									class="repaireCompany isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>进厂日期：
							</td>
							<td>
								<input name="carMaintain.intodate" id="intodate"
									onfocus="date(this);" class="intodate isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>出厂日期：
							</td>
							<td>
								<input name="carMaintain.outdate" id="outdate"
									onfocus="date(this);" class="outdate isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>保养方接车人：
							</td>
							<td>
								<input name="carMaintain.connectcarpeople" id="connectcarpeople"
									class="connectcarpeople isremove put3" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								<span class="xx">*</span>接车日期：
							</td>
							<td>
								<input name="carMaintain.takebackdate" id="takebackdate"
									onfocus="date(this);" class="takebackdate isremove put3"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>保养费用：
							</td>
							<td>
								<input name="carMaintain.maintainCost" id="maintainCost"
									class="maintainCost isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								保养内容：
							</td>
							<td colspan="3">
								<input name="carMaintain.maintainContext" id="maintainContext"
									size="50" />
							</td>
							
						</tr>
						
						<tr>
	
							<td align="right">
								备注：
							</td>
							<td colspan="3">
								<input name="carMaintain.remark" id="remark" size="50" />
								<input name="carInformation.carID" type="hidden"  id="numCarID"/>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		<!-- 车辆维护修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 65%; top: 15%" id="jqModelup">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							车辆维护管理修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftableupdate"
						cellpadding="5" cellspacing="5">
						<tr>
							<td style="width: 15%" align="right">
								保养人：
							</td>
							<td style="width: 33%">
								<input name="carMaintain.repairpeople" id="repairpeople"
									value="${carMaintain.repairpeople}" size="20" />
							</td>
							<td style="width: 19%" align="right">
								保养地点：
							</td>
							<td>
								<input name="carMaintain.maintenanceAddress"
									id="maintenanceAddress"
									value="${carmaintain.maintenanceAddress }" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								保养类型：
							</td>
							<td>
								<input name="carMaintain.maintainType" id="maintainType"
									value="${carMaintain.maintainType }" size="20" />
							</td>
							<td align="right">
								保养合同编号：
							</td>
							<td>
								<input name="carMaintain.maintainISBN" id="maintainISBN"
									value="${carMaintain.maintainISBN }" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								出厂里程标识值：
							</td>
							<td>
								<input name="carMaintain.totalmileage" id="totalmileage"
									value="${ carMaintain.totalmileage}" size="20" />
							</td>
							<td align="right">
								质量检验员：
							</td>
							<td>
								<input name="carMaintain.qualitysurveyor" id="qualitysurveyor"
									value="${ carMaintain.qualitysurveyor}" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								承保养单位：
							</td>
							<td>
								<input name="carMaintain.repaireCompany" id="repaireCompany"
									value="${ carMaintain.repaireCompany}" size="20" />
							</td>
							<td align="right">
								进厂日期：
							</td>
							<td>
								<input name="carMaintain.intodate" id="intodate"
									onfocus="date(this);" value="${carMaintain.intodate }"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								出厂日期：
							</td>
							<td>
								<input name="carMaintain.outdate" id="outdate"
									onfocus="date(this);" value="${carMaintain.outdate }" size="20" />
							</td>
							<td align="right">
								保养方接车人：
							</td>
							<td>
								<input name="carMaintain.connectcarpeople" id="connectcarpeople"
									value="${carMaintain.connectcarpeople }" size="20" />
							</td>

						</tr>
						<tr>

							<td align="right">
								接车日期：
							</td>
							<td>
								<input name="carMaintain.takebackdate" id="takebackdate"
									value="${ carMaintain.takebackdate}" onfocus="date(this);"
									size="20" />
							</td>
							<td align="right">
								保养费用：
							</td>
							<td>
								<input name="carMaintain.maintainCost" id="maintainCost"
									value="${ carMaintain.maintainCost}" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								保养内容：
							</td>
							<td colspan="3">
								<input name="carMaintain.maintainContext" id="maintainContext"
									value="${ carMaintain.maintainContext}" size="50" />
							</td>
						</tr>
						<tr>
							<td align="right">
								备注
							</td>
							<td colspan="3">
								<input name="carMaintain.remark" value="${carMaintain.remark }"
									id="remark" size="50" />
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<input name="carMaintain.maintainID" id="maintainID"
									type="hidden" class="input" size="20" />
								<input name="carMaintain.maintainKey" id="maintainKey"
									type="hidden" class="input" size="20" />
								<input type="button" class="input-button JQuerySubmits"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryreturns"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		
		<!-- 车辆保养查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable" width="100%" cellpadding="5" cellspacing="5">

					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carMaintain.carnumber" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carMaintain.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carMaintain.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td >
							<input name="carMaintain.carPeople" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchCar"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input name="carInformation.carID" type="hidden" id="carIDs" />
					<input name="type" type="hidden" value="${type}" />
				</div>
			</div>
		</form> 
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>
