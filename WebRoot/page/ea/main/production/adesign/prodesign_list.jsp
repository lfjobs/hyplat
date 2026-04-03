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
	<title>产品设计</title>


	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
		  type="text/css" />
	<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/production/adesign/productdesign_list.js"
			type="text/javascript"></script>




	<script type="text/javascript">

        var basePath="<%=basePath%>";
        var search = "${search}";
        var  ppageNumber = "${pageNumber}";
        var ppID = "";
        var fiveClear="${fiveClear}";
        var category="${category}";
	</script>
</head>
<body>

<div style="margin-top:10px;margin-left:10px;display:none;"
	 class="query">

	<form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>
		产品设计&nbsp;&nbsp;&nbsp; 产品编号：<input type="text"
										   style="width:90px;height:18px;" name="productPackaging.productCode"/>&nbsp;产品名称：<input type="text" name="productPackaging.goodsName" style="width:90px;height:18px;" />
		&nbsp;产品条码：<input type="text" name="productPackaging.barCode" style="width:90px;height:18px;" />
		<input
				type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;"/>
		<input
				type="hidden" value="search" name="search"/>
		<input
				type="hidden" id="ppID" name="productPackaging.ppID"/>
		<input
				type="hidden" id="category" name="category" value="1"/>
	</form>

</div>
<div class="main_main">
	<table class="JQueryflexme">
		<thead>
		<tr class="tablewith">
			<th width="40" align="center">选择</th>
			<th width="40" align="center">序号</th>
			<th width="100" align="center">行业分类</th>
			<th width="100" align="center">产品条码</th>
			<th width="150" align="center">产品编号</th>
			<th width="180" align="center">产品名称</th>
			<th width="100" align="center">产品品牌</th>
			<th width="100" align="center">产品规格</th>
			<th width="100" align="center">型号</th>
			<th width="100" align="center">科目</th>
			<th width="100" align="center">单价</th>
			<th width="100" align="center">数量</th>
			<th width="100" align="center">金额</th>
			<th width="100" align="center">备注</th>
			<th width="100" align="center">生产状态</th>

		</tr>
		</thead>
		<tbody>
		<%
			int number = 1;
		%>
		<s:iterator value="pageForm.list">
			<tr id="${ppID}">
				<td><input type="radio" name="a" class="JQuerypersonvalue"
						   value="${ppID}" /></td>

				<td><%=number%></td>
				<td><span id="tradeCode">${tradeCode}</span></td>
				<td><span id="barCode">${barCode}</span></td>
				<td><span id="productCode">${productCode}</span></td>
				<td><span id="goodsName">${goodsName}</span></td>
				<td><span id="brand">${brand}</span></td>
				<td><span id="standard">${standard}</span></td>
				<td><span id="model">${model}</span></td>
				<td><span id="subjectName">${subjectName}</span></td>
				<td><span id="price">${price}</span></td>
				<td><span id="quantity">${quantity}</span></td>
				<td><span id="money"></span>${money}</td>
				<td><span id="remark">${remark}</span></td>
				<td><span id="productstate" style="display:none;">${productstate}</span>
					<s:if test='productstate=="00"'>尚未模拟测试</s:if>
					<s:if test='productstate=="01"'>模拟测试中</s:if>
					<s:if test='productstate=="02"'>测试合格</s:if>
					<s:if test='productstate=="03"'>测试不合格</s:if>

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
				 value="ea/prodesign/ea_getProductDesignList.jspa?pageNumber=${pageNumber}&search=${search}&fiveClear=${fiveClear}&type=01&category=1">
		</c:param>
	</c:import>
</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>