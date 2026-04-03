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
		<title>售前服务管理-网络推广营销公司汇总</title>	
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/navigation_a.css"/>	
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
<body>
<table width="450" border="0">
  <tr>
    <td>
          <div class="na_back_img_ks"></div>
          <div class="center_a"><strong>网络推广办</strong></div>
    </td>
	  <td><div class="na_back_img_jt_hx"></div></td>
    
    <td>
      <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/telmessage/ea_goMessageIndex.jspa?type=current&orgDetail=market'"></div>
      <div class="center_a">短信推广管理</div>
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">邮件推广管理</div>
    
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">博客推广管理</div>
    
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">微信推广管理</div>
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">QQ推广管理</div>
    </td>
    <td>
      <div class="na_back_img" ></div>
      <div class="center_a">网站推广管理</div>
    </td>
  </tr>
</table>
</body>
</html>