<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>票务管理</title> 
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
  </head>
  
  <body>
    <table>
            <tr>
              <td rowspan="2"><div class="na_back_img_ks"></div><div class="center_a"><strong>
             	票务管理</strong></div></td>
              <td><div class="na_back_img_jt_hx"></div></td>
              <td>
                <table >
                  <tr>
                   <td>
                      <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/piaowuManager/ea_getListpiaowu.jspa?aa=zz'"></div>
                      <div class="center_a">票务管理</div>
                      </td>
                      <td>
                      <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/piaowuManager/ea_getListpiaowu.jspa?aa=bb'"></div>
                      <div class="center_a">票务报表管理</div>
                      </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
  </body>
</html>
