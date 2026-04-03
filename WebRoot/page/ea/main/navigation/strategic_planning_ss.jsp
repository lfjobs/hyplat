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
		<title>办公室-集团战略管理处</title>
	<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
	<body>
	<br/>
	<table>
	<tr>
		<td>
	<table>
      <tr>
        <td align="center">
          <div class="na_back_img_ks"></div>
          <div class="center_a"><strong>集团战略规划管理</strong></div>
        </td>
        <td><div class="na_back_img_jt_hx"></div></td>
        <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td >
                <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=cg&type=group&date=Math.random()'"></div>
                <div class="center_a">集团公司规划管理</div>
              </td>
              <td>
                <div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=dg&type=group&date=Math.random()'"></div>
                <div class="center_a">集团部门规划管理</div>
              </td>
              <td>
                 <div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=pg&type=group&date=Math.random()'"></div>
                <div class="center_a">集团个人规划管理</div>
              </td>            
              <td> 
                 <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=jg&type=group&date=Math.random()'"></div>
                <div class="center_a">集团职业规划管理</div>
              </td>
            </tr>
        </table></td>
      </tr>
    </table>
	</td></tr></table>
	</body>
</html>
