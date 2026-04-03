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
		<title>部门财务管理--入库管理</title>
		<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css" type="text/css"/>
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
	<table  border="0" align="left" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" align="center" rowspan="2"><div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong> 入库管理</strong></div></td>
        <td width="55" align="center"><div class="na_back_img_jt_hx"></div></td>
        <td><table border="0" cellspacing="0" cellpadding="0">
            <tr><%--
              <td ><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/storage/ea_getChooseWarehousingList.jspa'"></div>
							<div class="center_a">
<span>采购入库</span></div></td>

--%><td ><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/newstorage/ea_toInvoicingStorage.jspa'"></div>
							<div class="center_a">
<span>采购入库</span></div></td>
<td ><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/newstorage/ea_toAddBreakRK.jspa'"></div>
							<div class="center_a">
<span>归还入库</span></div></td>
<td ><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/newstorage/ea_toAddTransfersRK.jspa'"></div>
							<div class="center_a">
<span>调拨入库</span></div></td>
            </tr>
        </table></td>
      </tr>
    </table>
</body>
</html>
