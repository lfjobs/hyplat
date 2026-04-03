<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>住宿管理</title> 
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>

		<script type="text/javascript">
   var token = 0;
   var select = 1;
   var accommodID = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var notoken = 0;

</script>
		<script src="<%=basePath%>js/ea/finance/company/accommod_list.js"></script>
	</head>
	<body>
		<form name="accommodForm" id="accommodForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div id="main_main" class="main_main">
				<input type="hidden" id="thisdate" />
				<table class="accommod">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="30" align="center">
								序号
							</th>
							<th width="200" align="center">
								酒店名称
							</th>
							<th width="80" align="center">
								星级
							</th>
							<th width="100" align="center">
								房间类别
							</th>
							<th width="100" align="center">
								价格(RMB)
							</th>
							<th width="80" align="center">
								楼层
							</th>
							<th width="100" align="center">
								床位总数
							</th>
							<th width="100" align="center">
								未住床位
							</th>
							<th width="180" align="center">
								备注
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
						<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[9] }" >
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[9] }" />
								<!-- <input type="hidden" name="accommodID" id="accommodID"
									value="${arr[0] }" />
								<input type="hidden" name="companyID" id="companyID"
									value="${arr[1] }" />
								<input type="hidden" name="organizationID" id="organizationID"
									value="${arr[2] }" /> -->
							</td>
							<td class="td_bg01">
								<span><%=number%></span>
							</td>

							<td class="td_bg01">
								<span id="">${arr[0] }</span>
								
							</td>
							<td class="td_bg01">
								<span id="">${arr[1] }</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[2] }</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[3] } 元</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[4] } 层楼</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[5] } 张床</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[5]-arr[6] } 张床</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[7] }</span>
							</td>
							


							<%
								number++;
							%>
						</tr>
					</c:forEach>

				</table>
				<c:import url="../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/accommod/ea_getAllList.jspa?pageNumber=${pageNumber}">
					</c:param>
				</c:import>
			</div>
		</form>


		<!--         查询         -->
		<div class="jqmWindow" style="width: 300px; right: 25%;; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询住宿信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					<tr>
						<td width="123" align="right">
							酒店名称：
						</td>
						<td width="261">
							<select id="addhotname" name="accommod.hotelName"  style="width: 155px">
									<option value="" checked ="checked">--请选择--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							酒店星级：
						</td>
						<td>
							<select id="addstart" name="accommod.stars"  style="width: 155px">
								<option value="" checked ="checked">--请选择--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							房间类别：
						</td>
						<td>
							<select id="addRoomtype" name="accommod.roomType"  style="width: 155px">
								<option value="" checked ="checked">--请选择--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							楼层：
						</td>
						<td>
							<input name="accommod.floor" />
						</td>
					</tr>
					<tr>
						<td align="right">
							价格：
						</td>
						<td>
							<input name="accommod.roomPrice" />
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


		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
