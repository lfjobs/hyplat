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
		<title>车辆年审信息</title>
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
		var areviewID = '';
  		var search='${search}';
		var personurl = "";
		var notoken = 0;
		var pNumber = ${pageNumber};
		var carID = parent.carID;
		var token=0;
		var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carAReview_edit.js"></script>
	</head>
	<body>
		<form name="carAReviewForm" id="carAReviewForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div id="main_main" class="main_main">
			<input type="hidden" id="thisdate" />
				<table class="JQueryflexme">
					<thead>
						<tr class="tablewith">
							<th width="30" align="center">
								选择
							</th>
							<th width="60" align="center" >
								车牌号
							</th>
							<th width="60" align="center" >
								车型
							</th>
							<th width="100" align="center" >
								发动机号
							</th>
							<th width="200" align="center">
								责任人
							</th>
							<th width="80" align="center">
								登记日期
							</th>
							<th width="80" align="center">
								年审时间
							</th>
							<th width="80" align="center">
								年审有效期
							</th>
							<th width="80" align="center">
								二保单
							</th>
							<th width="80" align="center">
								年审档案盒
							</th>
							<th width="100" align="center">
								备注
							</th>
						</tr>
					</thead>
					<tbody>
						<input type="hidden" id="start" />
						<input type="hidden" id="end" />
						<s:iterator value="pageForm.list">
							<tr id="${areviewID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${areviewID}" />
									<input type="hidden" name="areviewKey" id="areviewKey"
										value="${areviewKey}" />
									<input type="hidden" name="areviewID" id="areviewID"
										value="${areviewID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carAReview.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
								<span id="carNum">${carNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carType">${carType}</span>
								</td>
								<td class="td_bg01">
									<span id="engineNum">${engineNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carPeople">${carPeople}</span>
								</td>
								<td class="td_bg01">
									<span id="registrationDate">${fn:substring(registrationDate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="areviewDate">${fn:substring(areviewDate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="areviewValid">${fn:substring(areviewValid, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="policyCode">${policyCode}</span>
								</td>
								<td class="td_bg01">
									<span id="areviewCode">${areviewCode}</span>
								</td>
								<td class="td_bg01">
									<span id="remarks">${remarks}</span>
									<span id="areviewID" style="display:none">${areviewID}</span>
                                 	<span id="areviewKey"  style="display:none">${areviewKey}</span>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carareview/ea_getCarAReviewList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
            <iframe src="" name="main" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
             </iframe>
			</div>
		</form>
		<!-- 车辆年审信息的添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2" style="width: 400px; left: 80%; top: 10%;"
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
					<table width="100%" border="0" id="stafftable" cellpadding="5"
						cellspacing="5">
						<tr>
							<td align="right" style="width:30%;">
								<span class="xx">*</span>登记日期：
							</td>
							<td>
								<input name="carAReview.registrationDate" size="20"
									id="registrationDate" class="registrationDate isremove put3"
									onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'thisdate\')}'})" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>年审时间：
							</td>
							<td>
								<input name="carAReview.areviewDate" size="20"
									class="newStartTime isremove put3"
									onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end\')}'})"
									id="newStartTime" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>年审有效期：
							</td>
							<td>
								<input name="carAReview.areviewValid" size="20"
									class="newEndTime isremove put3"
									onfocus="WdatePicker({minDate:'#F{$dp.$D(\'start\')}'})"
									id="newEndTime" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>二保单：
							</td>
							<td>
								<input name="carAReview.policyCode" id="policyCode"
									class="policyCode isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>年审档案盒：
							</td>
							<td>
								<input name="carAReview.areviewCode" id="areviewCode"
									class="areviewCode isremove put3" size="10" />
							</td>
						</tr>
						<tr>
							<td align="right">
								备注：
							</td>
							<td colspan="2">
								<input name="carAReview.remarks" id="remarks" size="30" />
								<input name="carInformation.carID" type="hidden"  id="numCarID"/>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
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
		<!-- 车辆年审信息的修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 400px; left: 80%; top: 10%;" id="jqModelup">
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
							<td style="width:30%;" align="right">
								登记日期：
							</td>
							<td>
								<input name="carAReview.registrationDate" size="20"
									value="${carAReview.registrationDate}" onfocus="date(this);"
									id="registrationDate" />
							</td>

						</tr>
						<tr>
							<td align="right">
								年审时间：
							</td>
							<td>
								<input name="carAReview.areviewDate" id="areviewDate"
									value="${carAReview.areviewDate }" onfocus="date(this);"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								年审有效期：
							</td>
							<td>
								<input name="carAReview.areviewValid" size="20"
									value="${carAReview.areviewValid }" onfocus="date(this);"
									id="areviewValid" />
							</td>
						</tr>
						<tr>
							<td align="right">
								二保单：
							</td>
							<td>
								<input name="carAReview.policyCode" id="policyCode"
									value="${carAReview.policyCode }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								年审档案盒：
							</td>
							<td>
								<input name="carAReview.areviewCode" id="areviewCode"
									value="${carAReview.areviewCode }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								备注：
							</td>
							<td>
								<input name="carAReview.remarks" id="remarks"
									value="${carAReview.remarks}" size="20" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input name="carAReview.areviewID" id="areviewID" type="hidden"
									class="input" size="20" />
								<input name="carAReview.areviewKey" id="areviewKey"
									type="hidden" class="input" size="30" />
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
		
		<!-- 车辆年审信息查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="100%" id="carSearchTable" cellpadding="5" cellspacing="5">
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carAReview.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carAReview.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carAReview.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td >
							<input name="carAReview.carPeople" />
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
