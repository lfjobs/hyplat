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
<title>停车位管理</title>

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
<script src="<%=basePath%>js/ea/office_ea/carmanage/parkingSpaceList.js"
	type="text/javascript"></script>

<script type="text/javascript">
var basePath="<%=basePath%>";
	var ppageNumber = "${pageNumber}";
	var parksId = "";
	var search = "${search}";
</script>
</head>
<body>

	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="60" align="center">停车位编号</th>
					<th width="150" align="center">停车位名称</th>
					<th width="60" align="center">场地编号</th>
					<th width="150" align="center">场地名称</th>
					<th width="80" align="center">适用车型</th>
					<th width="100" align="center">停车位面积</th>
					<th width="80" align="center">创建人</th>
					<th width="130" align="center">创建时间</th>
					<th width="100" align="center">状态</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list" var="c">
					<tr id="${c[0]}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${c[0]}" />
						</td>

						<td><%=number%></td>
						<td><span id="parkingCode">${c[1]}</span> <span id="parksId"
							style="display:none;">${c[0]}</span>
						</td>
						<td><span id="parkingName">${c[2]}</span>
						</td>
						<td><span id="siteNumber">${c[3]}</span>
						</td>
						<td><span id="siteName">${c[4]}</span>
						</td>
						<td><span id="carType" style="display:none;">${c[5]}</span> <span>
								<c:choose>
									<c:when test="${c[5] eq '01' }">
                                                                  小车
								   </c:when>
									<c:when test="${c[5] eq '02' }">
								  大车
								   </c:when>

								</c:choose> </span></td>
						<td><span id="parkingLength">${c[6]}</span>X<span
							id="parkingWidth">${c[7]}</span>
						</td>
						<td><span id="staffName">${c[8]}</span>
						</td>
						<td><span id="createDate">${fn:substring(c[9],0,19)}</span>
						</td>

						<td><span id="status" style="display:none;">${c[10]}</span> <c:choose>
								<c:when test="${c[10] eq '01' }">
									<span style="color:#F00"> 已占用 </span>
								</c:when>
								<c:when test="${c[10] eq '00' }">
									<span> 未使用 </span>
								</c:when>
								<c:otherwise>
									<span> 已弃用 </span>
								</c:otherwise>
							</c:choose></td>
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/carmanage/ea_parkingSpaceList.jspa?pageNumber=${pageNumber}&search=${search}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>

	<!--添加窗口 -->
	<form name="addForm" id="addForm" method="post">
		<div class="jqmWindow" style="width:550px;right: 30%;top:10%"
			id="jqModeladd">
			<input type="submit" name="submit" style="display:none" />

			<div class="drag">
				停车位添加
				<div class="close"></div>
			</div>

			<table width="100%" id="addTable" cellspacing="10" cellpadding="10">
				<tr>
					<td align="right">停车位编号：</td>
					<td><input id="parkingCode" style="width:195px" 
						name="parkingSpace.parkingCode" maxlength="50" class="parkingCode" />
					</td>

				</tr>
				<tr>
					<td align="right">停车位名称：</td>
					<td><input id="parkingName" style="width:195px"
						name="parkingSpace.parkingName" class="put3" maxlength="50" /> <input
						id="parksId" type="text" style="display:none"
						name="parkingSpace.parksId" /></td>

				</tr>

				<tr>
					<td align="right">所在场地：</td>
					<td><select id="siteId" name="parkingSpace.siteId"
						class="site">

					</select></td>

				</tr>
				<tr>
					<td align="right">适用车型：</td>
					<td><select id="carType" name="parkingSpace.carType">
							<option value="01">小车</option>
							<option value="02">大车</option>


					</select></td>
				</tr>
				<tr>
					<td align="right">停车位长度：</td>

					<td><input id="parkingLength"
						name="parkingSpace.parkingLength" maxlength="10" />&nbsp;米</td>


				</tr>
				<tr>
					<td align="right">停车位宽度：</td>

					<td><input id="parkingWidth" name="parkingSpace.parkingWidth"
						maxlength="10" />&nbsp;米</td>


				</tr>
				<tr>
					<td align="right">使用状态：</td>
					<td><select id="status" name="parkingSpace.status">
							<option value="00">未使用</option>
							<option value="01">已占用</option>
							<option value="02">已弃用</option>
					</select></td>

				</tr>

			</table>
			<div align="center">
				<input type="button" class="input-button" id="save" value=" 保存 "
					style="margin: 10px;" />
			</div>
		</div>
	</form>

	<!--批量初始化停车位-->
	<form name="batForm" id="batForm" method="post">
		<div class="jqmWindow" style="width:550px;right: 30%;top:10%"
			id="jqModelBat">
			<input type="submit" name="submit" style="display:none" />

			<div class="drag">
				批量初始化停车位
				<div class="close"></div>
			</div>

			<table width="100%" cellspacing="10" cellpadding="10">
				<tr>
					<td align="right">停车位编号范围：</td>
					<td><input style="width:90px" id="startNum" name="startNum"
						maxlength="50" class="put3 positiveNum" /> ~ <input
						style="width:90px" id="endNum" name="endNum" maxlength="50"
						class="put3 positiveNum" />
					</td>

				</tr>


				<tr>
					<td align="right">所在场地：</td>
					<td><select id="siteId" name="parkingSpace.siteId"
						class="site">

					</select></td>

				</tr>
				<tr>
					<td align="right">适用车型：</td>
					<td><select id="carType" name="parkingSpace.carType">
							<option value="01">小车</option>
							<option value="02">大车</option>
					</select></td>
				</tr>
              <tr>
					<td align="right">停车位长度：</td>

					<td><input id="parkingLength"
						name="parkingSpace.parkingLength" maxlength="10" />&nbsp;米</td>


				</tr>
				<tr>
					<td align="right">停车位宽度：</td>

					<td><input id="parkingWidth" name="parkingSpace.parkingWidth"
						maxlength="10" />&nbsp;米</td>


				</tr>

			</table>
			<div align="center">
				<input type="button" class="input-button" id="batSave" value=" 保存 "
					style="margin: 10px;" />
			</div>
		</div>
	</form>
	<!--修改停车位状态 -->
	<form name="updateForm" id="updateForm" method="post">
		<div class="jqmWindow" style="width:300px;right: 30%;top:10%"
			id="jqModelupdate">
			<input type="submit" name="submit" style="display:none" />

			<div class="drag">
				修改停车位
				<div class="close"></div>
			</div>

			<table width="100%" cellspacing="10" cellpadding="10">
				<tr>
					<td align="right">使用状态：</td>
					<td>
					<input
						id="parksId" type="text" style="display:none"
						name="parkingSpace.parksId" />
					
					<select id="status" name="parkingSpace.status">
							<option value="00">未使用</option>
							<option value="01">已占用</option>
							<option value="02">已弃用</option>
					</select></td>

				</tr>

			</table>
			<div align="center">
				<input type="button" class="input-button" id="update" value=" 保存 "
					style="margin: 10px;" />
			</div>
		</div>
	</form>
	<form name="searchForm" id="searchForm" method="post">
		<input type="submit" name="submit" style="display:none" />
		<input type="hidden" value="search" name="search" />
		<input type="hidden" id="parkingCode" name="parkingSpace.parkingCode" />
		<input type="hidden" id="siteId" name="parkingSpace.siteId" />
		<input type="hidden" id="status" name="parkingSpace.status" />


	</form>

</body>
</html>