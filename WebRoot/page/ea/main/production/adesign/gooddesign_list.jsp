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
<title>物品设计</title>


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
<script src="<%=basePath%>js/ea/production/adesign/gooddesign_list.js"
	type="text/javascript"></script>




<script type="text/javascript">

var basePath="<%=basePath%>";
var search = "${search}";
var  ppageNumber = "${pageNumber}";
var goodsID = "";
var  fiveClear="${fiveClear}";

</script>
</head>
<body>
    
	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query"><form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>物品设计 &nbsp;&nbsp;&nbsp;物品编号：<input type="text"
			style="width:90px;height:18px;" name="goodsManage.goodsCoding"/>&nbsp;物品名称：<input type="text" name="goodsManage.goodsName" style="width:90px;height:18px;" />
		&nbsp;物品条码：<input type="text" name="goodsManage.barCode" style="width:90px;height:18px;" />
		<input
			type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;" />
			<input
			type="hidden" value="search" name="search"/>
			<input
			type="hidden" id="goodsID" name="goodsManage.goodsID"/>
		</form>
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="100" align="center">行业分类</th>
					<th width="100" align="center">物品类别</th>
					<th width="100" align="center">物品条码</th>
					<th width="100" align="center">物品编号</th>
					<th width="100" align="center">物品名称</th>
					<th width="100" align="center">品牌</th>
					<th width="100" align="center">物品规格</th>
					<th width="100" align="center">型号</th>
					<th width="100" align="center">单位</th>
					<th width="180" align="center">创建时间</th>
					

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${goodsID}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${goodsID}" /></td>

						<td><%=number%></td>
						<td><span id="tradeCode">${tradeCode}</span></td>

						<td><span id="typeID">${typeID}</span></td>

						<td><span id="barCode">${barCode}</span></td>
						<td><span id="goodsCoding">${goodsCoding}</span></td>
						<td><span id="goodsName">${goodsName}</span></td>
		                <td><span id="brand">${brand}</span></td>
                        <td><span id="standard">${standard}</span></td>
                        <td><span id="model">${model}</span></td>
						<td><span id="variableID">${variableID}</span></td>
						<td><span id="createdate">${fn:substring(createdate,0,19)}</span></td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/gooddesign/ea_getGoodDesignList.jspa?pageNumber=${pageNumber}&search=${search}&fiveClear=${fiveClear}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>