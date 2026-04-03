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
		<title>学员档案</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/driving/signup/driving_student_archive.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
   var relationID = '';
   var  treeID='${organizationID}'
   var basePath='<%=basePath%>';           
   var pNumber =${pageNumber};  
   var token = 0;
   var notoken = 0;
   var search='${search}';
   var roomtpID = "";
   var staffID = "";
   var roomtype = "";
   var deitnumid = "";
   
   var companyID='${account.companyID}';
   var companyName='${account.companyName}';
   var organizationID='${organizationID}';	
   var organizationName='${organizationName}';
   
   var module_title='${param.module_title}';
   var companyGroupLogo='${param.companyGroupLogo}';
   var dangan='${dangan}';
</script>
	</head>
	<body onunload="closePort()">
		<div class="main_main" >
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							序号
						</th>

						<th width="100" align="center">
							人员编号
						</th>

						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="100" align="center">
							电话
						</th>
						<th width="100" align="center">
							Email
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${staffid}" data-staffID="${staffid}" data-companyID="${companyid}">
							<td>
								<input type="radio" class="chx JQuerypersonvalue"
									value="${staffid}" title="${staffid}" name="chbox" id="relationID"/>
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="staffCode">${staffcode}</span>
							</td>
							<td>
								<span id="recordCode">${recordcode}</span>
							</td>
							<td>
								<span id="staffName">${staffname}</span>
							</td>
							<td>
								<span id="usedNmae">${usednmae}</span>
							</td>
							<td>
								<span id="reference">${reference}</span>
							</td>
							<td>
								<span id="contactWay">${contactWay}</span>
							</td>
							<td>
								<span id="sex">${sex}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${birthday}</span>
							</td>
							<td>
								<span id="nativePlace">${nativeplace}</span>
							</td>
							<td>
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffidentitycard}</span>
								<span id="staffID" style="display: none">${staffid}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/clinch/ea_getListStudentArchive.jspa?pageNumber=${pageNumber}&search=${search}&module_Identifier=customer_Deal&view_Identifier=educational_train&module_title=${param.module_title}&companyGroupLogo=${param.companyGroupLogo}&dangan=${dangan}">
				</c:param>
			</c:import>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<%--******************************************招生点查询****************************************--%>
		<div  id="jqModelSearch">
		<form name="searchFormByAdmissions" id="searchFormByAdmissions" method="post" enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
				
					招生点：<select class="model1" id="organizationID" name="dtDrivingAllInformation.organizationID" ></select>
					招生时间：
							<input type="text" style="width: 80px" name="dtDrivingAllInformation.searchStaDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>
							&nbsp;至&nbsp;<input type="text" style="width: 80px" name="dtDrivingAllInformation.searchEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>
						招生员：
						<input type="text" id="referrer" /><input type="hidden" id="referrerID" name="dtDrivingAllInformation.referrerID" readonly="readonly"/><a href="#" id="xzry" class="xzry model1">选择</a>
						学员：
						<input  name="dtDrivingPrincipal.studentname"/>
				<input type="button" class="input-button" id="toSearchByAdmissions" value=" 查询 " />
           		<input name="search" type="hidden" value="search" />
           		<input name="module_Identifier" type="hidden" value="customer_Deal"/>
                <input name="companyGroupLogo" type="hidden" value="${param.companyGroupLogo}"/>
                <input name="view_Identifier" type="hidden" value="educational_train"/>
		</form>
		</div>
		<%--******************************************人员选择****************************************--%>
		<form name="Staffform" id="Staffform" method="post">
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 320px; absolute; display: none; left: 2.5%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;z-index: 10000">
			<iframe name="daoRu" id="daoRu" width="100%" height="270px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
			
		</div>
		</form>
		
	<%--******************************************物品选择****************************************--%>
		<form name="goodsForm" id="goodsForm" method="post" enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择物品
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="120" align="right">
								条码号/身份证号：
							</td>
							<td width="80">
								<input name="recordCode" class="input" id="recordCode" size="25"
									style="margin-left: 2px;" />
							</td>
							<td >
							  <nobr>
							 &nbsp;&nbsp;
							    <input type="button" value="手动输入" class="btn02 manual"/>
								<input style="display:none;"type="button" value="扫描输入" class="btn02 scan"/>
								<input type="button" class="btn02" id="searchGood"
									name="button7" value="查询" style="display:none;"/>
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							  <iframe width="0" height = "0" id="loadcabs" name="loadcabs"></iframe>
							</nobr>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="83%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 300px; width: 100%; overflow: auto;">
									<table width='98%' height='26' align='center' cellspacing='0'
										cellpadding='1' style='font-size: 12px;' class='bannb_01'>
										<tr>
											<td height='24' align='left' valign='top' class='txt01'>
												&nbsp;点击选择物品
											</td>
										</tr>
									</table>
									<table width='99%' align='center' id='gotable' cellpadding='0'
										cellspacing='0' class='table'>
										<thead>
										<tr>
											<th height='21' align='center' bgcolor='#E4F1FA'>
												选择
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												人员编码
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												档案编号
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												姓名
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												曾用名
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												性别
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												出生日期
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												国籍
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												籍贯
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												民族
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												身份证
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												查看详细
											</th>
										</tr>

                                   </thead>
                                   <tbody id="tbody">
                                   
                                   </tbody>
									</table>


								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
  
	</body>
</html>