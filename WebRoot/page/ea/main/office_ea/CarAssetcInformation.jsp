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
		<title>车辆资产信息</title>
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
		  var token = 0;
   		  var select = 1;
          var assetcID = '';
          var basePath = '<%=basePath%>';
          var pNumber = ${pageNumber};
          var notoken = 0;
          var carInformation = '${carInformation.carNum}';
	      var carID=parent.carID;
	      var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/CarAssetc_edit.js"></script>
	</head>
	<body>
		<form name="carAssetcForm" id="carAssetcForm" method="post">
			
			<div id="main_main" class="main_main">
			<input type="submit" name="submit" style="display: none" />
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="100" align="center">
								资产名称
							</th>
							<th width="100" align="center">
								规格
							</th>
							<th width="100" align="center">
							           数量
							</th>
							<th width="140" align="center">
								单价
							</th>
							<th width="100" align="center">
								金额
							</th>
							<th width="130" align="center">
								交接时间
							</th>
							<th width="100" align="center">
								交接责任人
							</th>
							<th width="100" align="center">
								凭证号
							</th>
							<th width="180" align="center">
								移动单号
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${assetcID}" class="td_bg01 saveAjax">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${assetcID}" />
									<input type="hidden" name="CarAssetcKey" id="CarAssetcKey"
										value="${CarAssetcKey}" />
									<input type="hidden" name="assetcID" id="assetcID" value="${assetcID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carassetcinformation.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
									<span id="propertyname">${propertyname}</span>
								</td>
								<td class="td_bg01">
								<span id="standard">${standard}</span>
								</td>
								<td class="td_bg01">
									<span id="count">${count}</span>
								</td>
								<td class="td_bg01">
									<span id="price">${price}</span>
								</td>
								<td class="td_bg01">
									<span id="totleCount">${totleCount}</span>
								</td>
								<td class="td_bg01">
									<span id="connectTime">${fn:substring(connectTime , 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="connectPeople">${connectPeople}</span>
								</td>
								<td class="td_bg01">
									<span id="certificatenum">${certificatenum}</span>
								</td>
								<td class="td_bg01">
									<span id="trackNumber">${trackNumber}</span>
									<span id="CarAssetcKey" style="display:none">${CarAssetcKey}</span>
                                 	<span id="assetcID"  style="display:none">${assetcID}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carassetcinformation/ea_getCaraffetcinformationList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden"  frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe  name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
		</form>
		<!-- 车辆资产添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1" style="width: 700px; left: 65%; top: 15%"
			id="jqModel">
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
					<table width="100%" border="0" id="stafftable" cellpadding="5" cellspacing="5">

						<tr>
							<td style="width:13%;" align="right">
								<span class="xx">*</span>资产名称：
							</td>
							<td style="width:33%;">
								<input name="carassetcinformation.propertyname"
									id="propertyname" class="refitName isremove put3" size="20" />
							</td>
							<td style="width:19%;" align="right">
								<span class="xx">*</span>规格：
							</td>
							<td>
								<input name="carassetcinformation.standard" id="standard"
									class="refitPhone isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>数量：
							</td>
							<td>
								<input name="carassetcinformation.count" id="count"
									class="refitContact isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>单价：
							</td>
							<td>
								<input name="carassetcinformation.price" id="price"
									class="refitCompany isremove put3" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								<span class="xx">*</span>金额：
							</td>
							<td>
								<input name="carassetcinformation.totleCount" id="totleCount"
									size="20" class="refitLicenseCode isremove put3" />
							</td>
							<td align="right">
								<span class="xx">*</span>交接时间：
							</td>
							<td>
								<input name="carassetcinformation.connectTime" id="connectTime"
									class="refitDate isremove put3" onfocus="date(this);" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>交接责任人：
							</td>
							<td>
								<input name="carassetcinformation.connectPeople"
									id="connectPeople" size="20" class="refitAgoFuel isremove put3" />
							</td>
							<td align="right">
								<span class="xx">*</span>凭证号：
							</td>
							<td>
								<input name="carassetcinformation.certificatenum"
									id="certificatenum" class="refitAfterFuel isremove put3"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>移动单号：
							</td>
							<td colspan="3">
								<input name="carassetcinformation.trackNumber" id="trackNumber"
									class="cylinderType isremove put3" size="20" />
							<input name="carInformation.carID" type="hidden"  id="numCarID"/>
							</td>

						</tr>
						<tr>
							<td colspan="4" align="center">
								
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		<!-- 车辆资产修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 700px; left: 65%; top: 10%" id="jqModelUpdate">
			<form name="UpdateForm" id="UpdateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable2" cellpadding="5"
						cellspacing="5">
						<tr>
							<td align="right">
								<span class="xx">*</span>资产名称：
							</td>
							<td>
								<input name="carassetcinformation.propertyname"
									id="propertyname" class="refitName isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>规格：
							</td>
							<td>
								<input name="carassetcinformation.standard" id="standard"
									class="refitPhone isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>数量：
							</td>
							<td>
								<input name="carassetcinformation.count" id="count"
									class="refitContact isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>单价：
							</td>
							<td>
								<input name="carassetcinformation.price" id="price"
									class="refitCompany isremove put3" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								<span class="xx">*</span>金额：
							</td>
							<td>
								<input name="carassetcinformation.totleCount" id="totleCount"
									size="20" class="refitLicenseCode isremove put3" />
							</td>
							<td align="right">
								<span class="xx">*</span>交接时间：
							</td>
							<td>
								<input name="carassetcinformation.connectTime" id="connectTime"
									class="refitDate isremove put3" onfocus="date(this);" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>交接责任人：
							</td>
							<td>
								<input name="carassetcinformation.connectPeople"
									id="connectPeople" size="20" class="refitAgoFuel isremove put3" />
							</td>
							<td align="right">
								<span class="xx">*</span>凭证号：
							</td>
							<td>
								<input name="carassetcinformation.certificatenum"
									id="certificatenum" class="refitAfterFuel isremove put3"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>移动单号：
							</td>
							<td colspan="3">
								<input name="carassetcinformation.trackNumber" id="trackNumber"
									class="cylinderType isremove put3" size="20" />
							</td>

						</tr>
						<tr>
							<td colspan="4" align="center">
								<input name="carInformation.carKey" id="carKey" type="hidden"
									class="input" size="20" />
								<input name="carInformation.carID" id="contactUserID"
									type="hidden" class="input" size="20" />
								<input type="button" class="input-button JQueryUpdateSubmit"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<input type="button" class="input-button JQueryUpdateReturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
	
		<!-- 车辆资产信息查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable" cellpadding="5" cellspacing="5">
					<tr>
						<td align="right">
							资产名称：
						</td>
						<td>
							<input name="carassetcinformation.propertyname"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							凭证号：
						</td>
						<td>
							<input name="carassetcinformation.certificatenum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							移动单号：
						</td>
						<td>
							<input name="carassetcinformation.trackNumber" />
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
