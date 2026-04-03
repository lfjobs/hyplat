<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>预算管理-比价管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_list_parity.js"></script>

		<script type="text/javascript">

        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var cashierBillsID="";
        	var pNumber="${pageNumber}";
			var jumptype = "${jumptype}";

     
        </script>
        <style type="text/css">
        
          #jqModel {
	display: none;
	overflow: auto;
	border: 1px solid #a8c7ce;
	width: 300px;
	height: 350px;
	position: absolute;
	top: 31%;
	left: 25%;
	z-index: 999999;
	background-color: #e1ecfc;
	filter: Alpha(opacity = 100);
}

#jqModel a{

color:#0066CC;
text-decoration:none;
}
        </style>
	</head>
	<body>
	<table class="flexme11">
			<thead>
				<tr>
					<th width="30" align="center">
						选择
					</th>
					<th width="200" align="center">
						公司名称
					</th>
					<th width="150" align="center">
						项目编号
					</th>
					<th width="150" align="center">
						项目名称
					</th>
					<th width="150" align="center">
						项目分类
					</th>
					<th width="150" align="center">
						单据凭证号
					</th>
					<th width="150" align="center">
						单据类别
					</th>
					<th width="100" align="center">
						责任人部门
					</th>
					<th width="70" align="center">
						责任人
					</th>
					<th width="70" align="center">
						制单人
					</th>
					<th width="90" align="center">
						制单日期
					</th>
					<th width="130" align="center">
						单据状态
					</th>
				</tr>
			</thead>
			<tbody>			
				<s:iterator value="pageForm.list">
					<tr id="${cashierBillsID}">
					<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${cashierBillsID}" />
						</td>
						<td>
							<span id="companyName">${companyName}</span>
						</td>
						<td>
							<span id="projectCode">${projectCode}</span>
						</td>
						<td>
							<span id="projectName">${projectName}</span>
						</td>
						<td>
							<span id="xmtypename">${xmtypename}</span>
						</td>
						<td>
							<span id="journalNum">${journalNum}</span>
						</td>
						<td>
							<span id="billsType">${billsType}</span>
						</td>
						<td>
							<span id="departmentName">${departmentName}</span>
						</td>
						<td>
							<span id="staffName">${staffName}</span>
						</td>
						<td>
							<span id="inputName">${inputName}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(cashierDate,0,19)}</span>
						</td>

						<td>
							<span id="status" style="display:none;">${status}</span>
							
							<s:if test="status=='03'">招标比价中</s:if>
							
						</td>
						
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/costsheet/ea_getCostSheetList.jspa?search=${search}&pageNumber=${pageNumber}&jumptype=${jumptype}">
			</c:param>
		</c:import>
		
	  <!-- 查询项目分类 -->
	<div id="jqModel">
		<div id="treeBoxs"></div>
	</div>
	
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
