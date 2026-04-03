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
		<title>酒店信息</title> 
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
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var notoken = 0;
	var hotID = "";
	var search='${search}';
</script>
		<script src="<%=basePath%>js/ea/finance/company/hotil_list.js"></script>
	</head>
	<body>
		<form name="hotForm" id="hotForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div id="main_main" class="main_main">
			<input type="hidden" id="thisdate" />
				<table class="hot">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="60" align="center">
								序号
							</th>
							<th width="200" align="center">
								酒店名称
							</th>
							<th width="100" align="center">
								星级
							</th>
							<th width="100" align="center">
								房间类别
							</th>
							<th width="100" align="center">
								入住人员
							</th>
							<th width="80" align="center">
								楼层
							</th>
							<th width="80" align="center">
								房间号
							</th>
							<th width="80" align="center">
								标价(RMB)
							</th>
							<th width="80" align="center">
								折扣价(RMB)
							</th>
							<th width="80" align="center">
								协议价(RMB)
							</th>
							<th width="180" align="center">
								备注
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[10] }" >
								<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[10] }" />
							</td>
							<td class="td_bg01">
								<span id=""><%=number%></span>
							</td>
							<td class="td_bg01">
								<span id="conorgname">${arr[0] }</span>
							</td>
							<td class="td_bg01">
								<span id="txtRow0">${arr[1] }</span>
							</td>
							<td class="td_bg01">
								<span id="jobname">${arr[2] }</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[3] }</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[4] } 层</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[5] }</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[6] } 元</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[7] } 元</span>
							</td>
							<td class="td_bg01">
								<span id="">${arr[8] } 元</span>
							</td>
							<td class="td_bg01">
								<span id="remarks">${arr[9] }</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</c:forEach>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/accommodhot/ea_toSeach.jspa?pageNumber=${pageNumber}">
					</c:param>
				</c:import>
			</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
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
						<td width="80" align="right">
							入住人员：
						</td>
						<td width="261">
							<input name="accHot.staffID" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							酒店名称：
						</td>
						<td width="261">
							<select id="addhotname" name="accHot.accommodID"  style="width: 155px">
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
							房间号：
						</td>
						<td>
							<input name="accHot.roomNum" />
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
		
	</body>
</html>
