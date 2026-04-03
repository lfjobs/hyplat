<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company) session.getAttribute("currentcompany");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>审核流程定义</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	
		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />

		<script type="text/javascript">
           	var treeid = "<%=c.getCompanyID()%>";
           	var companyName = "<%=c.getCompanyName()%>";
          	var token = 0;
            var basePath="<%=basePath%>";
            var ppageNumber="${pageNumber}";
            var psearch='${search}';
            var proId = "";

		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/audits/procedure.js"></script>

	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="180" align="center">
							流程名称
						</th>
						<th width="80" align="center">
							审核的表单
						</th>	
						<th width="180" align="center">
							启用时间
						</th>
						<th width="150" align="center">
							停用时间
						</th>
						<th width="150" align="center">
							创建时间
						</th>
						<th width="180" align="center">
							创建人
						</th>
						<th width="180" align="center">
							部门
						</th>
					
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${proId}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${proId}" />
							</td>
							<td>
								<span id="proName">${proName}</span>
							</td>
                             <td>
								<span id="forms">${forms}</span>
							</td>
							<td>
								<span id="startDate">${startDate}</span>
							</td>
							<td>
								<span id="endDate">${endDate}</span>
							</td>
							<td>
								<span id="createDate">${fn:substring(createDate,0,19)}</span>
							</td>
						     <td>
								<span id="creatorID">${creatorID}</span>
							</td>
								<td>
								<span id="organizationName">${organizationName}</span>
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
					value="ea/procedure/ea_getProcedureList.jspa?pageNumber=${pageNumber}&search=${search}&meetingID=${meetingID}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 800px; top: 10%" id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							审核流程定义
							<div class="close"></div>
						</div>
					</div>
					<table width="800 " height="46" border="0" align="center"
						cellpadding="10" cellspacing="10" style="margin-top: 5px;">
						<tr>

							<td align="right">
								流程命名：
							</td>
							<td align="left" colspan="3">

                                <input type="hidden" id="proIds" name="procedure.proId"/>
								<input type="text" id="" name="procedure.proName" size="25" />
							</td>



						</tr>
						<tr>

							<td align="right">
								审核表单：
							</td>
							<td align="left" colspan="3">
								<input type="checkbox" id="" value="工资级别升降级单" name="procedure.forms" />
								工资级别升降级单
								<input type="checkbox" id="" value="员工级别变更单" name="procedure.forms" />
								员工级别变更单
								<input type="checkbox" id="" value="员工加班申请单"name="procedure.forms" />
								员工加班申请单
								<input type="checkbox" id="" value="人事调令单" name="procedure.forms" />
								人事调令单
								<br />
								<input type="checkbox" id="" value="通知单" name="procedure.forms" />
								通知单
								<input type="checkbox" id="" value="请假单" name="procedure.forms" />
								请假单
								<input type="checkbox" id="" value="销假单" name="procedure.forms" />
								销假单
								<input type="checkbox" id="" value="派车单" name="procedure.forms" />
								派车单
								<input type="checkbox" id="" value="奖罚单" name="procedure.forms" />
								奖罚单
							</td>



						</tr>

						<tr>

							<td align="right">
								开始时间：
							</td>
							<td align="left">


								<input type="text" id="" onfocus="date();" name="procedure.startDate" size="25" />
							</td>
							<td align="right">
								结束时间：
							</td>
							<td align="left">


								<input type="text" id="" onfocus="date();" name="procedure.endDate" size="25" />
							</td>



						</tr>


					</table>
					<table width="800" border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">
						<tr>
							<td width="20%" height="30" align="right">
								<span class="xx">*</span>第<span class="numaudit" id="numauditid">1</span>审核阶段名称：
							  
							    <input type="hidden"  name="phase.orderNum" id="orderNum" value="1"/>
							
							</td>
							<td align="left" height="30">
								<input type="text" size="25" name="phase.phaseName"  id="phaseName"/>
							</td>
						</tr>

						<tr>
							<td width="20%" height="30" align="right">
								<span class="xx">*</span>第<span class="numaudit">1</span>审核阶段类型：
							</td>
							<td align="left">
								<select id="auditType"  name="phase.auditType">
									<option value="01">
										单一审核人
									</option>
									<option value="02">
										多人审核任一人同意/驳回即同意/驳回
									</option>
									<option value="03">
										多人审核全部同意/驳回即同意/驳回
									</option>
								</select>
							</td>
						</tr>

                       <tr>
                       <td align="right">第<span class="numaudit">1</span>审核阶段审核人：</td>
                       <td>
                         
                       
                         <textarea style="overflow-y:auto;"cols="80" rows="5" id="auditarea" readonly></textarea>&nbsp;<a href="#" onclick="selectAuditor();">选择</a></td>
                         <input type="hidden"  value=""   id="strid" name="strid"/>
                         <input type="hidden"  value=""   id="auditorName" name="auditor.auditorName"/>
                       </tr>



					</table>
					<table width="890" height="20" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td colspan="10" align="center">
							    <input type="button" style="display:none;" class="input-button PreAudit"
									style="cursor: pointer; width: 150px;" value=" 前一个审核阶段 " />
								<input type="button" class="input-button NextAudit"
									style="cursor: pointer; width: 150px;" value="  保存并添加下级审核阶段" />
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 90px;" value=" 保存最终审核 " />
								<input type="button" class="input-button deletePhase"
									style="cursor: pointer; width: 125px;" value="   删除当前审核阶段   " />
								
								<input type="hidden" name="meetingID" value="${meetingID}" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>





		<div class="jqmWindow" id="zj"
			style="width: 800px; height: 450px; left: 20%; top: 1%">
			<div>
				<div class="contentbannb">
					<div class="drag">
						组织机构树

					</div>
				</div>
			</div>
			<table style="width: 100%; height: 450px;" cellpadding="0"
				cellspacing="0" style="margin-top: 2px;">
				<tr>
					<td width="40%" align="left" valign="top">
						<div id="tree1" style="height:400px;overflow:auto;background:#ffffff;"></div>
					</td>
					<td width="70%" align="left" valign="top">
						<table style="width: 450px; height: 350px;" align="center"
							cellpadding="0" cellspacing="2"
							style="margin-top: 5px; margin-bottom: 5px;">
							<tr>
								<td width="200" height="20" class="txt01">
									备选人员
								</td>
								<td width="50">
									&nbsp;
								</td>
								<td width="200" align="left" class="txt01">
									已选人员
								</td>
							</tr>
							<tr>
								<td height="137">
									<select name="leftfields" multiple="multiple" id="leftfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>

								</td>
								<td width="250" align="center">
									<div>
										<input type="button" class="input-button" id="query_add"
											value=" 添加 " />
									</div>
									<div>
										<input type="button" class="input-button" id="query_delete"
											value=" 删除 " />
									</div>
								</td>
								<td>
									<select name="rightfields" multiple="multiple" id="rightfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>
								</td>
								<td width="100">
									&nbsp;
								</td>
							</tr>


							<tr>
								<td height="30" colspan="3" align="center">
									<br />
									<input type="button" class="input-button" id="confirm"
										value=" 确定 " onclick="submit();" />
									<input type="button" class="input-button" id="closed"
										value=" 关闭 " onclick="closed();" />
									<a href="#" id="ttttt" target="_self"></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>









		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">

					<tr>
						<td align="right">
							参会人员姓名：
						</td>
						<td>
							<input name="searchStaff.staffName" />
							<input type="hidden" name="meetingID" value="${meetingID}" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>


		<iframe name="hidden" width="100%" scrolling="no" marginwidth="0"
			height="0" marginheight="0" frameborder="0" border="0"
			framespacing="0" noresize="noResize"></iframe>
	</body>
</html>