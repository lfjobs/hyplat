<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
			Company c = (Company)session.getAttribute("currentcompany");
		%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>合格率</title>
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
	<script type="text/javascript">
       		var treeID = "${department[1]}";
	        var treePID = '<%=c.getCompanyID()%>';
	        var treePName = '<%=c.getCompanyName()%>';
       		var token = 0;
            var id = ""; 
            var basePath='<%=basePath%>';
            var ppageNumber='${pageNumber}';
            var search='${search}';
            var acceName = '';  //附件查看赋值
            var times = '0';
            var vouch = '';  //凭证号传值
       	    var Type="${type}";
             var show="${show}";
	        

     
		</script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/production/dcheck/Yield.js"></script>
</head>
<body>
	<div>
		<table class="flexigrid">

			<thead>
				<tr>

					<th width="40" align="center">请选择</th>
					<th width="40" align="center">序号</th>
					<th width="100" align="center">行业分类</th>
					<th width="110" align="center">产品编号</th>
					<th width="100" align="center">产品条码</th>
					<th width="100" align="center">产品名称</th>
					<th width="100" align="center">产品规格</th>
					<th width="100" align="center">考核数量</th>
					<th width="100" align="center">合格数量</th>
					<th width="100" align="center">合格率</th>
				</tr>

			</thead>
			<tbody class="JQueryflexme">

				<%
						int number = 1;
					%>
				<s:iterator value="pageForm.list" var="list">
					<tr id="${list[0]}">
						<td><input type="radio" name="a" class="JQuerypersonvalue" />
						</td>
						<td><span id="number"><%=number%></span></td>
						<td><span id="tradecode">${list[4]}</span>
						</td>
						<td><span id="itemNumber">${list[5]}</span></td>
						<td><span id="goodBar">${list[6]}</span></td>

						<td><span id="goodName">${list[7]}</span></td>
						<td><span id="goodStandard">${list[8]}</span></td>
						<td><span id="btnumber" >${list[9]}</span></td>
						<td><span id="percent" >${list[10]}</span></td>
						<td><span id="yield" class="number">${list[2]}</span></td>

					</tr>
					<%
							number++;
						%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/dcheck/ea_getDCheckyieldList.jspa?pageNumber=${pageNumber}&type=${type}&show=${show}">
			</c:param>
		</c:import>
	</div>

	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>