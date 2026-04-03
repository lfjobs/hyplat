
<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>办公室办公-</title>

		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
		<script type="text/JavaScript">
addEvent(window, 'load', initCorners);
function initCorners() {
var setting = {
tl: { radius: 6 },
tr: { radius: 6 },
bl: { radius: 6 },
br: { radius: 6 },
antiAlias: true
}
curvyCorners(setting, ".roundedCorners");
}</script>
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
	<table >
  <tr>
    <td><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_9.png");'></div><div align="center"><strong>人事督察审核管理</strong></div></td>
     <td><div class="na_back_img_jt_hx"></div></td>
    <td> <div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_9_01.png");' onclick="document.location.href='<%=basePath%>/ea/humanExamine/ea_getHumanList.jspa?'"></div><div class="center_a">人事督察管理</div></td>
    <td> <div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_9_02.png");' onclick="document.location.href='<%=basePath%>/ea/publicreceipts/ea_getListPublicreceipts.jspa?labelTag=00'"></div><div class="center_a">主管审核管理</div></td>
    <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_9_03.png");' onclick="document.location.href='<%=basePath%>/ea/publicreceipts/ea_getListPublicreceipts.jspa?labelTag=01'"></div><div class="center_a"> 人事审核管理</div></td>
  </tr>
  <tr>
    <td><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_10.png");'></div><div align="center"><strong> 办公室督察审核管理</strong></div></td>
     <td><div class="na_back_img_jt_hx"></div></td>
    <td><div class="na_back_img"  style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_10_01.png");'
    onclick="document.location.href='<%=basePath%>/ea/responsible/ea_getResponsibleList.jspa?'"></div><div class="center_a">办公室督察管理</div></td>
  </tr>
  <tr>
    <td><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_11.png");'></div><div align="center">
   <strong> 财务督察审核管理</strong></div></td>
    <td><div class="na_back_img_jt_hx"></div></td>
    <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_11_01.png");'
    onclick="document.location.href='<%=basePath%>/ea/accountant/ea_getAccountantList.jspa'"></div><div class="center_a"> 会计审核管理</div></td>
    <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_11_02.png");'
    onclick="document.location.href='<%=basePath%>ea/cashier/ea_getCashierList.jspa?'"></div><div class="center_a">出纳审核管理</div></td>
  </tr>
  <tr>
    <td><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_13.png");'></div><div align="center">
    <strong>生产督察审核管理</strong></div></td>
    <td><div class="na_back_img_jt_hx"></div></td>
    <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_13_01.png");'></div><div class="center_a">生产督察管理</div></td>
  </tr>
  <tr>
   <td><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_14.png");'></div><div  align="center">
   <strong> 营销督察审核管理</strong></div></td>
    <td><div class="na_back_img_jt_hx"></div></td>
    <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_14_01.png");'
    onclick="document.location.href='<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa?'"></div><div class="center_a"> 营销督察管理</div></td>
  </tr>
</table>
	</body>
</html>
