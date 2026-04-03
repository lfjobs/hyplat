<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>单据审核管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
         <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script> 
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
         <script type="text/javascript" src="<%=basePath %>js/ea/mysl/administrative/ReceiptCheck-right.js"></script>
        <script type="text/javascript">
            var basePath = "<%=basePath%>";
            var checkid = "";
            var ppageNumber = ${pageNumber};
            var token=0;
            var auditorstatus="${auditorstatus}";
            var receiptType = "${receiptType}";
            var search="${search}";
            var sdate="${sdate}";
            var edate="${edate}";
            var token=0;
</script>
	</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="150" align="center">单据编号</th>
					<th width="150" align="center">单据类别</th>
					<th width="50" align="center">申请人</th>
					<th width="120" align="center">申请日期</th>
					<th width="50" align="center">审核人</th>
					<th width="120" align="center">审核日期</th>
					<th width="120" align="center">审核状态</th>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list">
					<tr id="${checkid}">
						<td><input type="checkbox" name="chbox" class="chx" value="${checkid}" />
						<input type="hidden" id="id" value="${id}"/></td>
						<td><span id="serialnumber">${serialnumber}</span></td>
						<td><span id="receiptType">${receiptType}</span></td>
						<td><span id="applyername">${applyername}</span></td>
						<td><span id="addtime">${fn:substring(addtime,0,10)}</span></td>
						<td><span id="auditorname">${auditorname}</span></td>
						<td><span id="audittime">${fn:substring(audittime,0,10)}</span></td>
						<td><span id="auditorstatus">
							<c:if test='${auditorstatus=="01"}'>未审核</c:if>
							<c:if test='${auditorstatus=="02"}'>已审核</c:if>
							<c:if test='${auditorstatus=="03"}'>驳回</c:if></span>
							<input type="hidden" value="${lookOverurl}" id="lookOverurl" />
							<input type="hidden" value="${printurl}" id="printurl" />
							<input type="hidden" value="${auditorstatus}" id="status" />
							</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/receiptcheck/ea_getDtMycheckList.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}&auditorstatus=${auditorstatus}&receiptType=${receiptType}">
			</c:param>
		</c:import>
	</div>

<!-- -------------------------------------查询------------------------------------ -->
	<form name="cstaffForm" id="cstaffForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display:none" />
		<div class="contentbannb jqmWindow jqmWindowcss3" style="top: 10%"
			id="jqModelsubmit">
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						查询信息
						<div class="close"></div>
					</div>
				</div>
				<table width="350" border="0" id="stafftable" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td><table width="400" height="196" border="0"
								id="stafftable2" align="center" cellpadding="0" cellspacing="0"
								style="margin-top: 5px; margin-bottom: 5px;">
								<tr>
									<td width="20" height="32" align="right">单据编号：</td>
									<td width="50"><input type="text" id="serialnumber"
										name="dtMycheck.serialnumber" />
									</td>
								</tr>
							<%-- 	<tr>
								<td width="50" align="right" height="32">单据类别：</td>
									<td width="50">
									<select id="receiptType" name="dtMycheck.receiptType">
										<option value="">请选择</option>
										<option value="耗材领用申请单">耗材领用申请单</option>
										<option value="设备借用单">设备借用单</option>
										<option value="设备维修单">设备维修单</option>
										<option value="证书领用单">证书领用单</option>
										<option value="图书借阅单">图书借阅单</option>
										<option value="盖章登记单">盖章登记单</option>
										<option value="出差申请单">出差申请单</option>
										<option value="请假申请单">请假申请单</option>
										<option value="加班申请单">加班申请单</option><!-- 
										<option value="汽车维护检查单">汽车维护检查单</option> -->
										<option value="卫生检查单">卫生检查单</option>
										<option value="用车申请单">用车申请单</option>
										<option value="客餐申请单">客餐申请单</option>
									</select>
									</td>
								</tr> --%>
								<tr>
									<td width="50" height="32" align="right">申请人：</td>
									<td><input type="text" id="applyerName"
										name="dtMycheck.applyername" />
									</td>
								</tr>
								<tr>
									<td width="50" height="32" align="right">申请时间：</td>
									<td><input type="text" id="sdate" onfocus="date(this)" size="10"
										name="sdate" />到<input type="text" id="edate" onfocus="date(this)" size="10"
										name="edate" />
									</td>
								</tr>
								<tr>
									<td height="30" colspan="2" align="center">
									<input type="hidden" name="search" value="search"/>
									<input type="hidden" name="receiptType" value="${receiptType}"/>
									<input type="button"
										class="input-button JQuerySubmit" id="JQuerySubmit"
										style="cursor:pointer;width:80px;" value="查询" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
	</form>
	
	<!-- -----------------------------------审核并继续审核-------------------------------- -->
	<form name="SendForm" id="SendForm" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 290px; right: 20%; top: 10%;"
			id="jqModelSend">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核通过并继续
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="10"
				cellpadding="20">
				<tr class="shows">
					<td align="right" width="20%">审核人公司：</td>
					<td align="left"><select id="auditorcompanyid"
						name="dtMycheck.auditorcompanyid" onchange="changeCompany(this);"
						style="width:200px;">
							<option value="">请选择审核人公司</option>
					</select></td>
				</tr>
				<tr class="shows">
					<td align="right">审核人部门：</td>
					<td align="left"><select id="auditororgid"
						name="dtMycheck.auditororgID" onchange="changeDept(this);"
						style="width: 200px;">
							<option value="">请选择审核人部门</option>
					</select></td>
				</tr>
				<tr class="shows">
					<td align="right">审核人姓名：</td>
					<td align="left"><select name="dtMycheck.auditorid"
						id='auditorid' style="width: 200px;">
							<option value="">请选择审核人</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" name="dtMycheck.comments" id="comments"  class="ckTextLength" maxlength="1000"></textarea>
					
					</td>
				</tr>
			</table>

			<div align="center">
				<input type="button" class="input-button" id="submitResult" value=" 提交 " />
			</div>
			</center>
		</div>
	</form>
	
	<!-- -----------------------------------审核-------------------------------- -->
	<form name="SendForm2" id="SendForm2" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 230px; right: 20%; top: 10%;"
			id="jqModelSend2">
			<input type="submit" name="submit2" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right" width="20%">审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" name="dtMycheck.comments" id="comments"  class="ckTextLength" maxlength="1000"></textarea>
					
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="submitResult2" value=" 提交 " />
					<input type="hidden" id="auditorstatu" value=""/>
			</div>
			</center>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>