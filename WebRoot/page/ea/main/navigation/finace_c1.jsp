<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门财务管理1</title>
<!-- <link href="<%=basePath%>css/navegate.css" rel="stylesheet" type="text/css" /> -->
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
<style>
table tr td {
	height: 50px;
}
</style>
<script type="text/javascript">
$(function(){
$("#voucher").click(function(){
window.parent.document.getElementById("bgMainContent").setAttribute("cols","0,10,*");
window.parent.document.getElementById("leftFrame").setAttribute("style","display: none;")

document.location.href="<%=basePath%>page/ea/main/navigation/finace_pzgl.jsp";
})
})
</script>
</head>  
  <body>
    <br />
    <div align="center" style="padding-top: 15%">
    <table width="90%" cellspacing="0" cellpadding="5" align="center"> 
    <tr>
    <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/strategic_plan_budget_sx.jsp'"></div>
				<div class="center_a"><strong>项目计划预算管理</strong></div>
			</td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>page/ea/main/navigation/finace_zjsq.jsp'"></div>
				<div  class="center_a"><strong>资金申请管理</strong></div>
			</td>
			<td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>page/ea/main/navigation/finace_zjsy.jsp'"></div>
				<div  class="center_a"><strong>收支凭据管理</strong></div>
			</td>
			<td><div class="na_back_img"  id="voucher"></div>
				<div  class="center_a"><strong>凭证管理</strong></div>
			</td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>page/ea/main/navigation/finace_zzmz.jsp'"></div>
				<div  class="center_a"><strong>总账明细账</strong></div>
			</td>
			
    </tr>
    </table>
    </div>
  </body>
</html>
