<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>物品设计预览</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>


<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<script src="<%=basePath%>js/ea/production/adesign/gooddesign_prev.js"
	type="text/javascript"></script>



<script type="text/javascript">

var basePath="<%=basePath%>";
</script>

</head>
<body>
	<div class="main">
		<div class="top" style="width:40%;">物品预览</div>
		<div class="body" style="width:40%;">

			<div class="baseinfo">
				<div class="mainpic">
				<img src="<%=basePath%>${goodsManage.photoPath}" width="100%" height="100%"/>
				
				</div>
				<div>
					<span>${goodsManage.goodsName}</span>
				</div>
				<span></span>

			</div>


			<div class="detailinfo">
				<div class="ttab">
					<ul class="tab detail">
						<li id="showdetail" class="selected left" style="border-left:1px solid #ccc;">图文详情</li>
						<li id="showparam">物品参数</li>
					</ul>

				</div>

				<div class="showdetail" style="overflow:auto;">
				 <% int number=1; %>
                 <c:forEach var="items" items="${functionlist}" varStatus="status">
				  ${items.name}</br>
				   <c:forEach var="funs" items="${maplist}" varStatus="status1">
				        <c:if test="${funs.key==status.count}">
				        ${funs.value}
				        </c:if>
				    </c:forEach>
				</c:forEach>
				</div>
				<div class="showparam hidcontent">

					<table cellpadding="8">
						<tr>
							<td align="right" width="30%">物品条码：</td>
							<td align="left">${goodsManage.barCode}</td>
						</tr>
						<tr>
							<td align="right">物品编号：</td>
							<td align="left">${goodsManage.goodsCoding}</td>
						</tr>

						<tr>
							<td align="right">物品名称：</td>
							<td align="left">${goodsManage.goodsName}</td>
						</tr>


						<tr>
							<td align="right">品牌：</td>
							<td align="left">${goodsManage.standard}</td>
						</tr>


						<tr>
							<td align="right">型号管理：</td>
							<td align="left">${goodsManage.model}</td>
						</tr>


						<tr>
							<td align="right">物品规格：</td>
							<td align="left">${goodsManage.acquiesceStandard}</td>
						</tr>

						<tr>
							<td align="right">单位：</td>
							<td align="left">${goodsManage.goodsvariable}</td>
						</tr>

					</table>
				</div>

			</div>
		</div>
	</div>
	</div>
	

</body>
</html>