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
		<title>车辆证件信息</title>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<style type="text/css">
		.xx{
			color:#FF0000;
			margin-right:2px;} 
		</style>
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var certifiID = '';
		var search='${search}';
		var personurl = "";
		var notoken = 0;
		var pNumber = ${pageNumber};
		var carID=parent.carID;
		var token=0;
		var type='${type}';
		</script>
	</head>
	<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/CertificateaTable.js"></script>
	<body>
	<form name="carCertificateaForm" id="carCertificateaForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="70" align="center">
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
							<th width="80" align="center">
								证件及资料名称
							</th>
							<th width="60" align="center">
								发证日期
							</th>
							<th width="60" align="center">
								有效日期
							</th>
							<th width="60" align="center">
								证件号
							</th>
							<th width="60" align="center">
								发证机关
							</th>
							<th width="60" align="center">
								证件资料文号	
							</th>
							<th width="80" align="center">
								有无复印件
							</th>
							<th width="80" align="center">
								审核人
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${certifiID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${certifiID}" />
									<input type="hidden" name="certifikey" id="certifikey"
										value="${certifikey}" />
									<input type="hidden" name="certifiID" id="certifiID"
										value="${certifiID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${certificateatable.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
									<span id="carNum">${carNum}</span>
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
									<span id="certificateaName">${certificateaName}</span>
								</td>
								<td class="td_bg01">
									<span id="receivedate">${fn:substring(receivedate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="effectivedate">${fn:substring(effectivedate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="certificateaNumber">${certificateaNumber}</span>
								</td>
								<td class="td_bg01">
									<span id="giveorgan">${giveorgan}</span>
								</td>
								<td class="td_bg01">
									<span id="certificateaISBN">${certificateaISBN}</span>
								</td>
								<td class="td_bg01">
									<span id="copies">${copies=='无'?'无':'有'}</span>
								</td>
								<td class="td_bg01">
									<span id="verifierPeople">${verifierPeople}</span>
									<span id="certifikey" style="display:none">${certifikey}</span>
                                 	<span id="certifiID"  style="display:none">${certifiID}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
					</table>
			<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/certificateatable/ea_getCertificateaTableList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
            <div style="width: 100%">
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="268px" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
			</div>
		</form>
		<!-- 相关证件添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 55%; top: 15%" id="jqModel">
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
							<td style="width: 13%;" align="right">
								<span class="xx">*</span>证件及资料名称：
							</td>
							<td style="width: 33%;">
								<input name="certificateatable.certificateaName"
									id="certificateaName" class="certificateaName isremove put3"
									size="20" />
							</td>
							<td style="width: 19%;" align="right">
								<span class="xx">*</span>发证日期：
							</td>
							<td>
								<input name="certificateatable.receivedate" id="receivedate"
									onfocus="date(this);" class="receivedate isremove put3"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>有效日期：
							</td>
							<td>
								<input name="certificateatable.effectivedate" id="effectivedate"
									onfocus="date(this);" class="effectivedate isremove put3"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>证件号：
							</td>
							<td>
								<input name="certificateatable.certificateaNumber"
									id="certificateaNumber" size="20"
									class="certificateaNumber isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>发证机关：
							</td>
							<td>
								<input name="certificateatable.giveorgan" id="giveorgan"
									size="20" class="giveorgan isremove put3" />
							</td>
							<td align="right">
								<span class="xx">*</span>证件资料文号 ：
							</td>
							<td>
								<input name="certificateatable.certificateaISBN"
									id="certificateaISBN" size="20"
									class="certificateaISBN isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								有无复印件：
							</td>
							<td>
								<select name="certificateatable.copies" id="copies">
									<option value="无">
										无
									</option>
									<option value="有">
										有
									</option>
								</select>
							</td>
							<td align="right">
								<span class="xx">*</span>审核人：
							</td>
							<td>
								<input name="certificateatable.verifierPeople"
									id="verifierPeople" class="verifierPeople isremove put3"
									size="20" />
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
		<!-- 车辆相关子证件的修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 55%; top: 15%" id="jqModelup">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftableupdate"
						cellpadding="5" cellspacing="5">
						<tr>
							<td style="width: 13%;" align="right">
								证件及资料名称：
							</td>
							<td style="width: 33%;">
								<input name="certificateatable.certificateaName"
									id="certificateaName"
									value="${certificateatable.certificateaName }" size="20" />
							</td>
							<td style="width: 19%;" align="right">
								发证日期：
							</td>
							<td>
								<input name="certificateatable.receivedate" id="receivedate"
									onfocus="date(this);" value="${certificateatable.receivedate }"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								有效日期：
							</td>
							<td>
								<input name="certificateatable.effectivedate" id="effectivedate"
									onfocus="date(this);"
									value="${certificateatable.effectivedate }" size="20" />
							</td>
							<td align="right">
								证件号：
							</td>
							<td>
								<input name="certificateatable.certificateaNumber"
									id="certificateaNumber" size="20"
									value="${certificateatable.certificateaNumber }" />
							</td>
						</tr>
						<tr>
							<td align="right">
								发证机关：
							</td>
							<td>
								<input name="certificateatable.giveorgan" id="giveorgan"
									size="20" value="${certificateatable.giveorgan }" />
							</td>
							<td align="right">
								证件资料文号 ：
							</td>
							<td>
								<input name="certificateatable.certificateaISBN"
									id="certificateaISBN" size="20"
									value="${ certificateatable.certificateaISBN}" />
							</td>
						</tr>
						<tr>
							<td align="right">
								有无复印件：
							</td>
							<td>
								<select name="certificateatable.copies" id="copies"
									value="${certificateatable.copies}">
									<option value="无">
										无
									</option>
									<option value="有">
										有
									</option>
								</select>
							</td>
							<td align="right">
								审核人：
							</td>
							<td>
								<input name="certificateatable.verifierPeople"
									id="verifierPeople"
									value="${certificateatable.verifierPeople }" size="20" />
							</td>
						</tr>
						<tr>
						<tr>
							<td colspan="4" align="center">
								<input name="certificateatable.certifikey" id="certifikey"
									type="hidden" class="input" size="20" />
								<input name="certificateatable.certifiID" id="certifiID"
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
				<table id="carSearchTable" width="100%;" cellpadding="5" cellspacing="5">
					<tr>
						<td align="right" >
							查询条件 
						</td>
					</tr>
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="certificateatable.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="certificateatable.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="certificateatable.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td >
							<input name="certificateatable.carPeople" />
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
