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
		<title>工程项目总价表</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_printlist.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';

        	var basePath="<%=basePath%>";
        	var proID="${projectManage.proID}";

        </script>
		<style type="text/css">
.tables {
	color: #000000;
	font-size: 9pt;
	border: 1px solid #000000;
	border-collapse: collapse;
}

.tables tr,td,th {
	border: 1px solid #000000;
}

p {
	font-size: 10pt;
}
</style>
	</head>
	<body >
		<div style="padding-top: 40px;width:950px; overflow: auto;margin:auto;text-align: center;">
            <h2>工程项目总价表</h2>
            <div style="margin-left:10px;text-align:left;">
			<p>
				合同编号：
			</p>
			<p>
				工程名称：${productPack.goodsName}
			</p>
			</div>
			<table class="tables" style="margin:100px auto auto auto;" border="0" 
				cellpadding="5">
				<thead>
					<tr>
						<th align="center">
							序号
						</th>

						<th align="center" width="500">
							工程项目名称
						</th>
						<th align="center" width="100">
							金额
						</th>
					</tr>
				</thead>
				<tbody>
				<% 
				   int number = 1;
				
				  %>
				<s:iterator value="mapaccount" id="column"> 
					<tr>
						<td><%=number%></td>
						<td><s:property value="key"/></td>
						<td><s:property value="value"/></td>
					</tr>
					
				
				<% 
			    
			      number++;
			    %>
			    </s:iterator>
			    

				</tbody>


			</table>
		</div>
	</body>
</html>
