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
		<title>客户调查</title>
	<!-- 	<link href="<%=basePath %>css/navegate.css" rel="stylesheet" type="text/css" /> -->
    <link href="<%=basePath%>css/navigation_a.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
	<body>
	<table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td rowspan="2">
          <div class="na_back_img_ks"></div>
          <div class="center_a"><strong>供应商调查</strong></div>
      </td>
        <td><div class="na_back_img_jt_hx"></div></td>
        <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <div class="na_back_img" ></div>
                <div class="center_a">单位供应商</div>
              </td>
              <td>
                <div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/academicadmin/ea_getCompanyListForIncumbent.jspa?'"></div>
                <div class="center_a">个人供应商</div>
               </td>
            </tr>
        </table></td>
      </tr>
    </table>
</body>
</html>
