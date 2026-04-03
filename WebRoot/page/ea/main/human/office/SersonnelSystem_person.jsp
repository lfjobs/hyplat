<%@ page language="java" pageEncoding="UTF-8" %>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<title>个人办公室-办公</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
<style>
table tr td {
	height: 50px;
}
</style>
</head>
<body>
    <div align="center" style="padding-top: 15%">
    <table width="90%" cellspacing="0" cellpadding="5" align="center">
    <tr>
            <%--<td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/logbook/ea_getListLogBook.jspa?serachType=normal'"></div>--%>
				<%--<div class="center_a"><strong>个人日志</strong></div>--%>
			<%--</td>--%>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/email/ea_getEmailList.jspa'"></div>
				<div  class="center_a"><strong>个人邮件</strong></div>
			</td>
			<td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/jobplan/ea_getJobPlanLists.jspa'"></div>
				<div  class="center_a"><strong>得到个人工作计划</strong></div>
			</td>
			<td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/jobtask/ea_getJobTaskList_a.jspa'"></div>
				<div  class="center_a"><strong>个人目标任务</strong></div>
			</td>
			<td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/personal/ea_getInventoryManagementList.jspa'"></div>
				<div  class="center_a"><strong>个人仓库</strong></div>
			</td>
    </tr>
    </table>
    </div>
</body>
</html>
