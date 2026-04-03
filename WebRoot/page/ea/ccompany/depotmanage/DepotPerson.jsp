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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>库房责任人</title>
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
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/ccompany/depotmanage/DepotPerson.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
	
	<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber = '${pageNumber}';  
         var  search ='${search}';  
         var  depotPersonID = "";
         var  token=0;
         var  depotID='${depotPerson.depotID}';
         var  select =1;
         var cID='${com.companyID}';
         var cName='${com.companyName}';
         var notoken=0;
		</script>  
		
		</head>
	<body>
		<form name="staffappraisalForm" enctype="multipart/form-data" id="staffappraisalForm" method="post">
			<s:token></s:token>
			<input type="hidden" name="depotPerson.depotID" value="${depotPerson.depotID}"></input>
			
			<input type="submit" name="submit" style="display: none" />	
		<div id="main_main" class="main_main">
			<table class="registration">
				<thead>
					<tr>
						<th width="40" align="center">
							选择
						</th>
						<th width="150" align="center">
							起时间
						</th>
						<th width="150" align="center">
							止时间
						</th>
						<th width="250" align="center">
							部门
						</th>
						<th width="150" align="center">
							责任人
						</th>
						<th width="150" align="center">
							责任人编号
						</th>
						<th width="150" align="center">
							责任人电话
						</th>
					</tr>
				</thead>
                <tbody id="tbwid">
					<tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue" value="${depotPersonID}" />
						</td>
						<td class="td_bg01">
							<input name="SinceTime" id="SinceTime"  onfocus="date(this);"/></td>
						<td class="td_bg01">
							<input name="StopTime" id="StopTime" onfocus="date(this); "/>
						</td>
						<td class="td_bg01">
							<select name="organizationID" id="organizationID" ></select>
						</td>
						<td class="td_bg01">
							<input name="responsible" id="responsible" />
						</td>
						<td class="td_bg01">
							<input name="responsibleCode" id="responsibleCode" />
						</td>
						<td class="td_bg01">
							<input name="responsiblePhone" id="responsiblePhone" />
							<input type="hidden" name="depotPersonKey" id="depotPersonKey" />
							<input type="hidden" name="depotPersonID" id="depotPersonID" />
						</td>
					</tr>
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${depotPersonID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"  value="${depotPersonID}" />
							</td>
							<td class="td_bg01">
								<span id="SinceTime" >${fn:substring(SinceTime, 0, 10)}</span>
								<input class="model1" value="${fn:substring(SinceTime, 0, 10)}" name="SinceTime" onfocus="date(this);" />
							</td>
							<td class="td_bg01" >							                                        
								<span id="StopTime" class="datas">${fn:substring(StopTime, 0, 10)}</span>
								<input class="model1" value="${fn:substring(StopTime, 0, 10)}"
									name="StopTime"  onfocus="date(this);"/>
							</td>
							<td class="td_bg01">
								<span id="corganizationName">${corganizationName}</span>
								<span id="organizationID" style="display:none">${organizationID}</span>
								<select name="organizationID" style="display:none" id="organizationID" >
								<option value="${organizationID}" selected>${corganizationName}</option>
								</select>
							</td>
							<td class="td_bg01">
								<span id="responsible">${responsible}</span>
								<input class="model1" value="${responsible}"
									name="responsible" />
							</td>
							<td class="td_bg01">
								<span id="responsibleCode">${responsibleCode}</span>
								<input class="model1" value="${responsibleCode}"
									name="responsibleCode" />
							</td>
							<td class="td_bg01">
								<span id="responsiblePhone">${responsiblePhone}</span>
								<input class="model1" value="${responsiblePhone}" name="responsiblePhone" />
									<input type="hidden" name="depotPersonKey" value="${depotPersonKey}" />
								<input type="hidden" name="depotPersonID" value="${depotPersonID}" />
								<input type="hidden" name="depotID" value="${depotID}" id="depotID"/>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/cdepotperson/ea_getListDepotPerson.jspa?depotPerson.depotID=${depotPerson.depotID}&pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
		</div>
			</form>
		<!--搜索窗口 -->
		<form name="appraisalForm" id="appraisalForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							责任人：
						</td>
						<td>
							<input name="depotPerson.responsible" class="put3"  />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input type="hidden" name="depotPerson.depotID" value="${depotPerson.depotID}"></input>
					
				</div>
			</div>
		</form>
		 <iframe name="hidden"  width="100%" height="0" ></iframe>
	</body>
</html>
