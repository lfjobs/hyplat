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
		<title>部门财务管理--出库管理</title>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<script>
function clickAction(action,parater){
if(parater == '1'){
var treeID = '<%=session.getAttribute("organizationID")%>';
 window.location.href= action+treeID;
 return;
 }
 window.location.href= action;
}

</script>
	</head>

	<body>
	<table>
      <tr>
        <td><div class="na_back_img_ks"></div><div class="center_a"><strong>
        出库管理</strong></div></td>
     <td><div class="na_back_img_jt_hx"></div></td>
              <%--<td ><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/sales/ea_getWareManagementList.jspa?billStatus=07'"></div><div class="center_a">销售出库</div></td>
             --%><td ><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/newsales/ea_getWareManagementList.jspa?billStatus=07'"></div><div class="center_a">销售出库</div></td>
             <td style="display:none;"><div class="na_back_img" onclick="document.location.href='#'"></div><div class="center_a">领用出库</div></td>
             <td style="display:none;"><div class="na_back_img" onclick="document.location.href='#'"></div><div class="center_a">调拨出库</div></td>
             <%--  <td ><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/warehousing/ea_getWareManagementList.jspa?'"></div><div class="center_a">异动出库</div></td>  --%>  
      </tr>
    </table>
</body>
</html>
