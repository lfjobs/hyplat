<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=8" />
		<title>公文流转单</title>
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script src="<%=basePath%>js/ea/office_ea/docUnfinish/docunfinish.js"></script>
		<script type="text/javascript">
   
             var basePath='<%=basePath%>';     
             var type = "${type}";
         </script>
	</head>
	<body>

		<div id="jqModelSearch">
			<form id="searchForm" name="searchForm">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					公文查询

				</div>

				<center>
					<table cellspacing="20" style="margin-top: 50px;">
						<!--  <tr>
							<td align="right">
								公文编号：
							</td>
							<td align="left">
								<input type="text" name="docSearchInfo.docNum"
									style="width: 180px;" />

							</td>
						</tr>-->
						<tr>
							<td align="right">
								公文标题：
							</td>
							<td align="left">
								<input type="text" id="titleinput" name="docSearchInfo.title"
									style="width: 180px; " class="menual" />
								
							</td>
						</tr>
						
						
					</table>
					    <div style="align:center;margin-top:50px;" >
					 
								<input type="button" value="   查询   " class="input-button"
									id="tosearch" />
							   <input type="hidden" value="search" name="search"/>
							   <input type="hidden" value="${type}" name="type"/>
					</div>
				</center>

			</form>
		</div>
	</body>
</html>
