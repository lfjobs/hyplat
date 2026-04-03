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
		<title>车牌号管理</title>
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
   var token = 0;
   var select = 1;
   var cnID = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   //var carID = '${carNumber.carID}';
   var carID = parent.carID;
   var carNum = parent.carNum;
   var staffID = parent.staffID;
   var staffName = parent.staffName;
   var notoken = 0;
   var carInformation = '${carInformation.carNum}';
   var type='${type}';
</script>
		<script src="<%=basePath%>js/ea/office_ea/carNumber.js"></script>
	</head>
	<body>
		<form name="carRoadForm" id="carRoadForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div id="main_main" class="main_main">
				<table class="carroad">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="100" align="center">
								部门
							</th>
							<th width="200" align="center">
								责任人
							</th>
							<th width="100" align="center">
								上牌时间
							</th>
							<th width="140" align="center">
								车牌号
							</th>
							<th width="100" align="center">
								上牌车管单位
							</th>
							<th width="100" align="center">
								车管单位地址
							</th>
							<th width="100" align="center">
								车牌废止时间
							</th>
							<th width="100" align="center">
								车牌废止部门
							</th>
							<th width="180" align="center">
								车牌废止责任人
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<c:forEach var='carnum' items="${pageForm.list}">
						<tr id="${carnum[10]}" >
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"  value="${carnum[10] }"/>
							</td>
							<td class="td_bg01">
								<span id="organizationname">${carnum[1]}</span>
							</td>
							<td class="td_bg01">
								<span id="staffname">${carnum[2]}</span>
							</td>
							<td class="td_bg01">
								<span id="brandDate">${carnum[3]}</span>
							</td>
							<td class="td_bg01">
								<span id="carNum">${carnum[4]}</span>
							</td>
							<td class="td_bg01">
								<span id="Unit">${carnum[5]}</span>
							</td>
							<td class="td_bg01">
								<span id="address">${carnum[6]}</span>
							</td>
							<td class="td_bg01">
								<span id="fzDate">${carnum[7]}</span>
							</td>
							<td class="td_bg01">
								<span id="fzOrgizationID">${carnum[8]}</span>
							</td>
							<td class="td_bg01">
								<span id="fzStaffID">${carnum[9]}</span>
								<span id="cnkey" style="display:none">${carnum[0]}</span>
                                <span id="cnID" style="display:none">${carnum[10]}</span>
                                <span id="companyID" style="display:none">${carnum[12]}</span>
                                <span id="carID" style="display:none">${carnum[11]}</span>
                                <span id="organizationID" style="display:none">${carnum[13]}</span> 
							</td>
						</tr>
					</c:forEach>
						
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="/ea/carnum/ea_getListCarByCompanyID.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}$type=${type}">
                </c:param>
               </c:import>
               <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

			</div>
		</form>
		
			<!-- 车牌号管理添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1" style="width: 400px; left: 80%; top: 10%;"
			id="jqModel">
			<form name="addForm" id="addForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							车牌号管理添加
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" cellpadding="5" cellspacing="5">

						<tr>
							<td align="right" style="width:30%;">
								<span class="xx">*</span>上牌时间：
							</td>
							<td>
								<input name="carNumber.brandDate" id="brandDate"
									onfocus="date(this);" class="invoiceDate isremove put3"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								上牌车管单位：
							</td>
							<td>
								<input name="carNumber.Unit" id="Unit" size="20" />
							</td>
						</tr>

						<tr>
							<td align="right">
								车管单位地址：
							</td>
							<td>
								<input name="carNumber.address" id="address" size="20" />
							</td>

						</tr>
						<tr>
							<td align="right">
								车牌废止时间：
							</td>
							<td>
								<input name="carNumber.fzDate" id="fzDate" size="20"
									onfocus="date(this);" class="invoiceDate isremove" />
							</td>
						</tr>
						<tr>
							<td align="right">
								车牌废止部门：
							</td>
							<td>
								<input name="carNumber.fzOrgizationID" id="fzOrgizationID"
									size="20" />
							</td>

						</tr>

						<tr>

							<td align="right">
								废止责任人：
							</td>
							<td colspan="3">
								<input name="carNumber.fzStaffID" id="fzStaffID" size="20"
									class="brandModel isremove" />
								<input name="carNumber.carID" type="hidden"  id="numCarID"/>
								<input name="carNumber.carNum" type="hidden"  id="numCarNum"/>
								<input name="carNumber.staffID" type="hidden"  id="numstaffID"/>
								<input name="carNumber.staffName" type="hidden"  id="numstaffName"/>
							</td>
						</tr>
						<tr>
							<td  colspan="6" align="center">
								<input name="carInformation.carKey" id="carKey" type="hidden"
									class="input" size="20" />
								<input name="carInformation.carID" id="contactUserID"
									type="hidden" class="input" size="20" />
								<input name="contactCompany.ccompanyID" id="ccompanyID"
									type="hidden" class="input" size="20" />
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
		<!-- 车牌号管理修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 400px; left: 80%; top: 10%;" id="updatejqModel">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							车牌号管理修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable2" cellpadding="5" cellspacing="5">
						<tr>
							<td width="30%" align="right">
								上牌时间：
							</td>
							<td>
								<input onfocus="date(this);" class="invoiceDate isremove put3"
									size="20" id="brandDate" name="carNumber.brandDate" />
							</td>

						</tr>
						<tr>
							<td align="right">
								车牌车管单位：
							</td>
							<td >
								<input id="Unit" name="carNumber.Unit" />
							</td>

						</tr>
						<tr>
							<td align="right">
								车管单位地址：
							</td>
							<td>
								<input type="text" id=address name="carNumber.address" />
							</td>
						</tr>
						<tr>
							<td align="right">
								车牌废止时间：
							</td>
							<td>
								<input id="fzDate" onfocus="date(this);"
									class="invoiceDate isremove put3" size="20"
									name="carNumber.fzDate" />
							</td>

						</tr>
						<tr>
						<tr>

							<td align="right">
								车牌废止部门：
							</td>
							<td>
								<input id="fzOrgizationID" name="carNumber.fzOrgizationID" />
							</td>

							<td>
								<input name="carNumber.cnID" id="UpdatecnID" type="hidden" />
							</td>
						</tr>
						<tr>
						<tr>

							<td align="right">
								车牌废止责任人：
							</td>
							<td>
								<input name="carNumber.fzStaffID" id="fzStaffID" />
							</td>
						</tr>
						<tr>

							<td colspan="6" align="center">
								<input name="carInformation.carKey" id="carKey" type="hidden"
									class="input" size="20" />
								<input name="carInformation.carID" id="contactUserID"
									type="hidden" />
								<input name="contactCompany.ccompanyID" id="ccompanyID"
									type="hidden" />
								<input type="button" class="input-button JQueryupdateSubmit"
									style="cursor: pointer; width: 80px;" value="提交"
									id="JQueryupdateSubmit" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryupdatereturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>

		<!-- 车牌号信息查询信息 -->
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
							责任人：
						</td>
						<td>
							<input name="carNumber.staffName"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carNumber.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车牌废止责任人：
						</td>
						<td>
							<input name="carNumber.fzStaffID" />
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
