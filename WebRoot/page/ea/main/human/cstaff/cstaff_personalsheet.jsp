<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人事档案库存</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/cstaff/cstaff_personalsheet.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>

		<script type="text/javascript">
	     var archivebID = '';
	     var basePath='<%=basePath%>';
	     var pNumber = '${pageNumber}';
         var token = 1;
         var search = '${search}';
         var pabillID = "";
        </script>

	</head>

	<body>
		<div id="main_main">
		<table class="JQueryflexmepost">
			<thead>
				<tr>
					<th width="30" align="center">
						选择
					</th>
					<th width="150" align="center">
						凭证号
					</th>
					<th width="100" align="center">
						单据类型
					</th>
				
					<th width="80" align="center">
						责任人
					</th>
					<th width="80" align="center">
						制单人
					</th>
					<th width="90" align="center">
						制单日期
					</th>
					<th width="90" align="center">
						公司银行账号
					</th>
					<th width="90" align="center">
						往来单位
					</th>					
					<th width="90" align="center">
						单位往来关系
					</th>
					<th width="90" align="center">
						单位往来电话
					</th>
					<th width="90" align="center">
						往来个人
					</th>
					
				</tr>
			</thead>
			<tbody>	
						<%
						int number = 1;
					%>

					<s:iterator value="pageForm.list" var="lists">
					<tr id="${pabillID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${pabillID}" />
						</td>
						<td>
							<span id="journalNum">${journalNum}</span>
						</td>
						<td>
							<span id="billsType">${billsType}</span>
						</td>
						<td>
							<span id="billStaffName">${billStaffName}</span>
						</td>
						<td>
							<span id="billStaffName">${billStaffName}</span>
						</td>
						<td>
							<span id="billsDate">${billsDate}</span>
						</td>
						<td>
							<span id="companyBankNum">${companyBankNum}</span>
						</td>
						<td>
							<span id="ccompanyName">${ccompanyName}</span>
						</td>
						<td>
							<span id="ccompanyRelationship">${ccompanyRelationship}</span>
						</td>
						<td>
							<span id="ccompanyTel">${ccompanyTel}</span>
						</td>
						<td>
							<span id="cstaffName">${cstaffName}</span>
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
				value="/ea/personalarchive/ea_getPArchiveSheetList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&type=00">
			</c:param>
		</c:import>
		</div>
		
	
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<center>
				<table width="396" cellpadding="0" cellspacing="5"
					id="cataffSearchTable">

					<tr>
						<td align="right">
							责任人：
						</td>
						<td align="left">
							<input type="text" name="paBill.billStaffName" id="billStaffName"
								style="width: 150px;" />
						</td>
					</tr>
					<tr>
						
						<td align="center"colspan="2">
							<input type="button"  id="tosearch" value="   查询  " class="input-button" />
						    <input type="hidden" name="search" value="search"/>
						</td>
					</tr>
					
			    	</table>
				</center>
			</form>
		</div>

		


		<iframe name="hidden" frameborder="0" height="0"></iframe>
	</body>
</html>