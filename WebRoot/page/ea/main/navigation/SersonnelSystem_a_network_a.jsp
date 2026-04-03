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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>售后跟踪客户网络服务</title>	
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/navigation_a.css"/>	
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
<body>
<table width="450" border="0">
  <tr>
    <td>
          <div class="na_back_img_ks"></div>
          <div class="center_a"><strong>售后跟踪客户网络服务</strong></div>
    </td>
	  <td><div class="na_back_img_jt_hx"></div></td>
  <td>
      <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/telmessage/ea_goMessageIndex.jspa?type=1&orgDetail=saleh'"></div>
      <div class="center_a">短信售后服务</div>
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">邮件售后服务</div>
    
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">博客售后服务</div>
    
    </td>
    <td>
      <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/microletter/microletter_tree.jsp'"></div>
      <div class="center_a">微信售后服务</div>
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">QQ售后服务</div>
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">网站售后服务</div>
    </td>
	<td>
		<div class="na_back_img"
			onclick="document.location.href='<%=basePath%>ea/extralflow/ea_showExtralDocModule.jspa'"></div>
		<div class="center_a">网站投诉售后服务</div></td>
   
  </tr>
</table>
</body>
</html>