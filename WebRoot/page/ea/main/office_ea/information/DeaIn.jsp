<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>经营范围</title>
<%@ page language="java" pageEncoding="UTF-8" %> 
		<%
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
</head>
	<body>
		<div id="main_main" class="main_main">
			<table class="registration">
				<thead>
					<tr align="center">						
						<div>经营范围</div>						
					</tr>
				</thead>
                <tbody>
					<tr id="sa"  class="td_bg01 saveAjax model2">
						<td align="center">							
							<textarea rows="12" cols="70" readonly="readonly"><%=request.getParameter("dealIn")%></textarea>										
						</td>
					</tr>
				</tbody>
			</table>
		
	</body>
</html>
