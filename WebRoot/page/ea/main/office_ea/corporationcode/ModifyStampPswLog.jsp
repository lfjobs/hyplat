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
		<title>印章修改秘密管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  pswLog = "";
    $(document).ready(function() {
          $('.log').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '印章密码修改日志----印章名称：' + parent.stampName,
				minheight : 80,
				buttons : [ {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
     case '设置每页显示条数':
       var enterpriseStampID = '${pswLog.enterpriseStampID}';
				var url = basePath
						+ "ea/enterprisestamp/ea_getStampPswLog.jspa?enterpriseStampID="
						+ enterpriseStampID;
				numback(url);
				break;
			}
		}
		
});
      
	</script>
		<style type="text/css">
a {
	text-decoration: none;
}
</style>

	</head>



	<body>
		<form name="pswLogForm" enctype="multipart/form-data" id="pswLogForm"
			method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="log">
					<thead>
						<tr>
							<th width="30" align="center">
								序号
							</th>

							<th width="150" align="center">
								修改时间
							</th>
							<th width="100" align="center">
								修改人
							</th>
						</tr>
					</thead>
					<tbody>
						<%
							int number = 1;
						%>
						<s:iterator value="pageForm.list">
							<tr class="td_bg01 saveAjax" id="${pswLogId}">
								<td class="td_bg01">
									<span><%=number%></span>
								</td>
								<td class="td_bg01">
									<span id="modifyTime">${fn:substring(modifyTime, 0, 19)}</span>

								</td>
								<td class="td_bg01">
									<span id="modifyUserName">${modifyUserName}</span>
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
						value="ea/enterprisestamp/ea_getStampPswLog.jspa?pageNumber=${pageNumber}&enterpriseStampID=${pswLog.enterpriseStampID}"></c:param>
				</c:import>
			</div>
		</form>
	</body>
</html>
