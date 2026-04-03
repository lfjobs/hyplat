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
<title>产品清单</title>
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
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script type="text/javascript">
   var token = 0;
   var search="${search}";
   var basePath = '<%=basePath%>';
   var pNumber=${pageNumber};
   var notoken = 0;
   var sdate="${sdate}";
   var edate="${edate}";
   var type="${type}"
   var xiang="${costSheetBill.journalNum}";
   var people="${costSheetBill.staffID}";
   var treenums="${treeid}";
   var treeID = '<%=session.getAttribute("organizationID")%>';
   var journalNum="";
   var csdID="";
</script>
<script src="<%=basePath%>js/ea/finance/invoicing/all_product.js"></script>
</head>
<body>
	<form name="fundForm" id="fundForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="fund">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="80" align="center">产品名称</th>
						<th width="165" align="center">产品编号</th>
						<th width="60" align="center">部门</th>
						<th width="80" align="center">责任人</th>
						<th width="60" align="center">产品类型</th>
						<th width="50" align="center">产品预算单价</th>
						<th width="50" align="center">产品数量</th>
						<th width="80" align="center">产品预算总价</th>
						<th width="50" align="center">项目编号</th>
						<th width="80" align="center">制单日期</th>
						<th width="80" align="center">实际工作时间</th>
						<th width="80" align="center">产品类别</th>
						<th width="80" align="center">项目状态</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[0]}">
							<td><input type="radio" name="a" class="JQuerypersonvalue" value="${arr[0]}" />
								<input type="hidden" name="csdID" id="csdID" value="${arr[0]}" />
							</td>
							<td><span id="">${arr[1]}</span></td>
							<td><span id="">${arr[2]}</span></td>
							<td><span id="">${arr[3]}</span></td>
							<td><span id="">${arr[4]}</span></td>
							<td><span id="">${arr[5]}</span></td>
							<td><span id="">${arr[6]}</span></td>
							<td><span id="">${arr[7]}</span></td>
							<td><span id="">${arr[8]}</span></td>
							<td><span id="">${arr[9]}</span></td>
							<td><span id="">${arr[10]}</span></td>
							<td><span id="">${arr[11]}</span></td>
							<td><span id="">${arr[12]}</span></td>
							<td><span id="">${arr[13]}</span></td>
					
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath" value="ea/product/ea_getAllList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&treeid=${treeid}"></c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!-- 查询按钮 -->
	<div class="jqmWindow" style="width: 350px; right: 30%; top: 10%" id="jqModelSearch">
		<form name="SearchForm" id="SearchForm" method="post">
		<input type="submit" name="submit" style="display: none" />
		<div class="drag">
			查询信息
			<div class="close">
			</div>
		</div>
		<table  >
			<tr >
				<td align="right" >项目编号：</td>
				<td><input id="journalNum" style="width: 195px" name="costSheetBill.journalNum" /></td>
			</tr>
			<tr>
				<td align="right" >责任人：</td>
				<td>
					<s:select list="%{#request.staffList}" style="width:200px"
						headerKey="" headerValue="请选择" listKey="staffID"
						name="costSheetBill.staffID" listValue="staffName"
						theme="simple">
					</s:select>
				</td>
			</tr>
			<tr>
				<td align="right" >制单日期：</td>
				<td style="width: 200px">
					<input id="sdate" name="sdate" onfocus="date(this);" style="width: 85px" />至<input id="edate" name="edate" onfocus="date(this);" style="width: 85px" />
				</td>
			</tr>
			<tr>
				<td align="right" >单据类型：</td>
				<td style="width: 200px">
					<select name="type" style="width: 125px;" id="type">
						<option value="">
							请选择
						</option>
						<option value="00">
							采购物品预算单
						</option>
						<option value="08">
							项目招标申请单
						</option>
					</select>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" id="tosearch" value=" 查询 "/>
			<input name="search" type="hidden" value="search" />
		</div>
		</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
