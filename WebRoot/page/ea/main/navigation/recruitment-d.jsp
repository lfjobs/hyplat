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
		<title>人事-招聘管理-外部招聘</title>
		<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css" type="text/css"/>
	</head>
<body>

<br/>
	<table border="0" align="left" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center"><div class="na_back_img_ks"></div><div class="center_a"><strong>外部招聘</strong></div>
        </td>
        <td align="center"><div class="na_back_img_jt_hx"></div></td>
        <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>现场招聘</span></div></td>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>网络招聘</span></div></td>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>校园招聘</span></div></td>            
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>中介招聘</span></div></td>
            </tr>
        </table></td>
      </tr>
    </table>
</body>
</html>	