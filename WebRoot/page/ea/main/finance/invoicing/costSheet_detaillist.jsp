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
		<title>项目明细管理</title>
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
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_detaillist.js"></script>
		
		<script type="text/javascript">

        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var cashierBillsID="";
        	var goodsBillsID="";
        	var pNumber="${pageNumber}";
        	var token=0;
        	var type="${type}";
    
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
					<th width="30" align="center">
						序号
					</th>
					<th width="165" align="center">
						公司
					</th>
					<th width="70" align="center">
					   部门
					</th>
					<th width="90" align="center">
						责任人
					</th>
					<th width="150" align="center">
						项目编号
					</th>
					<th width="100" align="center">
						项目名称
					</th>

					<th width="200" align="center">
						项目分类
					</th>
					<th width="200" align="center">
						单据凭证号
					</th>
				
					<th width="90" align="center">
						物品编号
					</th>
					<th width="90" align="center">
						物品名称
					</th>
					<th width="150" align="center">
						型号/规格
					</th>
					<th width="70" align="center">
						单位
					</th>
					<th width="90" align="center">
						数量
					</th>
					<th width="200" align="center">
						单价
					</th>
		
					<th width="90" align="center">
						金额
					</th>
					<th width="90" align="center">
						单据类别
					</th>
				
					<th width="150" align="center">
						预算收入
					</th>
					<th width="70" align="center">
						预算支出
					</th>
					<th width="70" align="center">
						预算余额
					</th>
					<th width="70" align="center">
						往来单位
					</th>
					<th width="70" align="center">
						往来个人
					</th>
				</tr>
			</thead>
			<tbody>		
			<%  
			 int number = 1;
			%>	
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[18]}">
						<td>
						   
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[18]}" />
								<span id="cashierBillsID" style="display:none;">${arr[17]}</span>
						
						</td>
						<td>
							<span><%=number%></span>
						</td>
						<td>
							<span id="companyName">${arr[0]}</span>
						</td>
						<td>
							<span id="orgname">${arr[1]}</span>
						</td>
						<td>
							<span id="staffName">${arr[2]}</span>
						</td>
						
						<td>
							<span id="projectCode">${arr[3]}</span>
						</td>
						<td>
							<span id="projectName">${arr[4]}</span>
						</td>
						<td>
							<span id="xmtypename">${arr[5]}</span>
						</td>
						<td>
							<span id="journalNum">${arr[6]}</span>
						</td>

						<td>
							<span id="goodsNum">${arr[7]}</span>
						</td>
						<td>
							<span id="goodsName">${arr[8]}</span>
						</td>
						<td>
							<span id="boxStandard">${arr[9]}</span>
							
						</td>
						<td>
							<span id="goodsVariableID">${arr[10]}</span>
						</td>
						<td>
							<span id="quantity">${arr[11]}</span>
						</td>
						<td>
							<span id="price">${arr[12]}</span>
						</td>
						<td>
							<span id="money">${arr[13]}</span>
						</td>
						<td>
							<span id="billsType">${arr[14]}</span>
						</td>
						<td>
							<span id="loan"><c:if test='${arr[14]=="项目收入预算单"}'>${arr[13]}</c:if></span>
							
						</td>
						<td>
							<span id="forLoan"><c:if test='${arr[14]=="项目支出预算单"}'>${arr[13]}</c:if></span>
						</td>
						<td>
							<span id="balance">${arr[13]}</span>
						</td>
						<td>
							<span id="ccompanyName">${arr[15]}</span>
						</td>
						<td>
							<span id="connectName">${arr[16]}</span>
							
						</td>
					</tr>
					<%  
			  number++;
			%>	
				</c:forEach>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/costsheet/ea_getProjectDetailList.jspa?search=${search}&pageNumber=${pageNumber}&type=${type}">
			</c:param>
		</c:import>
		

		
		
		
		<iframe name="hidden" border="0" framespacing="0" height="0"></iframe>
		
	  <!-- 查询项目分类 -->
	<div id="jqModel">
		<div id="treeBoxs"></div>
	</div>

	</body>
</html>
