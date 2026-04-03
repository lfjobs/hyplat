<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company) session.getAttribute("currentcompany");
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>生产跟踪管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<style type="text/css">
.windowJqm {
	left: 55%;
	width: 850px;
	margin-left: -450px;;
}

.underline {
	text-decoration: underline;
}

.sty {
	padding-left: 5px;
}

#industryClassification {
	width: 150px;
}
</style>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/admin_main.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
       		var token = 0;
            var id = ""; 
            var basePath='<%=basePath%>';
	var ppageNumber = '${pageNumber}';
	var search = '${search}';
	var acceName = ''; //附件查看赋值
	var times = '0';
	var vouch = ''; //凭证号传值
	var type = "${type}";
	var sta="${status}";
	var search="${search}";
	var fiveClear="${fiveClear}";
</script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/production/cprocedure/Ptrack.js"></script>

</head>
<body>
	<div>
		<table class="flexigrid">

			<thead>
				<tr>

					<th width="40" align="center">请选择</th>
					<th width="40" align="center">序号</th>
					<th width="120" align="center">生产批次号</th>
					<th width="100" align="center">产品编号</th>
					<th width="70" align="center">产品名称</th>
					<th width="100" align="center">生产部门</th>
					<th width="90" align="center">项目负责人</th>
					<th width="100" align="center">跟踪日期</th>
					<th width="70" align="center">生产量</th>
					<th width="70" align="center">跟踪员</th>
					<th width="40" align="center">备注</th>
					<th width="40" align="center">状态</th>
				</tr>

			</thead>
			<tbody class="JQueryflexme">

				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${ptrackekey}">
						
						<td><input type="radio" name="a" class="JQuerypersonvalue" />
						</td>
						<td><span id="number"><%=number%></span></td>
						<td><span id="lot">${lot}</span>
						</td>
						<td><span id="productNumber">${productNumber}</span>
						</td>
						<td><span id="productName">${productName}</span></td>
						<td><span id="productionDepartment">${productionDepartment}</span></td>

						<td><span id="projectLeader">${projectLeader}</span></td>
						<td><span id="trackTime">${trackTime}</span></td>
						<td><span id="throughput">${throughput}</span></td>

						<td><span id="trackman">${trackman}</span></td>
						<td><span id="remark">${remark}</span></td>
						<td>
								     
								   <span id="status" style="display:none;" >${status}</span>
								       <s:if test='status=="00"'>未提交审核</s:if>
								       <s:if test='status=="01"'>已提交审核</s:if>
					
								     
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
				value="ea/ptrack/ea_getPtrackList.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}&fiveClear=${fiveClear}">
			</c:param>
		</c:import>
	</div>

			</center>
		</form>
	</div>
	<form id="forms" name="forms" method="post">
		<input type="submit" id="submits" name="submits" style="display: none;">
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>