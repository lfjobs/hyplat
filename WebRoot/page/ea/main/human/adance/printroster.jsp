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
	String filepath = request.getSession().getServletContext()
			.getRealPath("/");
%><html xmlns="http://www.w3.org/1999/xhtml">
<%-- <%
		String rosterlist = session.getAttribute("rosterlist").toString(); 
		System.out.println(rosterlist.toString());
		%>  --%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>打印花名册</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	    var pbasePath = "<%=basePath%>";
</script>
<style>
body {
	text-align: center;
	font-size: 10px;
}
</style>
</head>
<body>
	<h1 style="margin-top:20px">打印花名册信息</h1>
	<table border="1px black solid" id="" cellpadding="0" cellspacing="0"
		style="margin-top:10px;border-collapse: collapse;" width="800px"
		align="center">
		<tr>
			<td>名称</td>
			<td>电话</td>
			<td>邮箱</td>
		</tr>
		<s:iterator value="pageForm.list" var="lists">
			<tr id="${rosterid}">
				<td><span id="name">${name}</span>
				</td>
				<td><span id="phone">${phone}</span>
				</td>
				<td><span id="email">${email}</span> <span
					style="display: none" id="rosterid">${rosterid}</span></td>
			</tr>
		</s:iterator>  
	</table>

	<table border="1px black solid" width="800px" align="center"
		style="margin-top:50px;border-collapse: collapse;">
		<tr id="go">
			<td class="choose">姓名</td>
			<td class="choose">性别</td>
			<td class="choose">部门</td>
			<td class="choose">岗位</td>
			<td class="choose">电话</td>
			<td class="choose">邮箱</td>
		</tr>
	</table>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var url = pbasePath
									+ "ea/soincumbent/sajax_ea_getrosterAll.jspa?aa=aa";
							$.ajax({ //查询在职员工
										url : url,
										type : "get",
										async : false,
										dataType : "json",
										success : function cbf(data) {

											var member = eval("(" + data + ")");
											//处理成功后的回调函数
											var rosterlist = member.rosterlist;

											for ( var i = 0; i < rosterlist.length; i++) {
												$("#go")
														.after(
																"<tr><td class='choose'>"
																		+ ((rosterlist[i][1]) == null ? '无'
																				: rosterlist[i][1])
																		+ "</td><td class='choose'>"
																		+ ((rosterlist[i][2]) == null ? '无'
																				: rosterlist[i][2])
																		+ "</td><td class='choose'>"
																		+ ((rosterlist[i][3]) == null ? '无'
																				: rosterlist[i][3])
																		+ "</td><td class='choose'>"
																		+ ((rosterlist[i][4]) == null ? '无'
																				: rosterlist[i][4])
																		+ "</td><td class='choose'>"
																		+ ((rosterlist[i][5]) == null ? '无'
																				: rosterlist[i][5])
																		+ "</td><td class='choose'>"
																		+ ((rosterlist[i][6]) == null ? '无'
																				: rosterlist[i][6])
																		+ "</td></tr>");
											};
										}

									});
						});
	</script>
</body>
</html>
