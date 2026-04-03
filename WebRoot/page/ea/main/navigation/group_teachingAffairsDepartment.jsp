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
<title>集团教务管理</title>
	<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		
</head>
<body>
    <div>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table height="90" border="0" align="left" cellpadding="0"
							cellspacing="0" class="table03" style="margin-top: 5px">
                    <tr>
                      <td width="98" rowspan="2" align="center">
                      	<div class="na_back_img_ks"></div>
                      	<div class="center_a"><strong>集团教务管理</strong></div>
                      </td>
                      <td width="80" height="62" align="center">
                      	<div class="na_back_img_jt_hx"></div>
					  </td>
                      <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0"
										height="132px">
                          <tr>
                             <td width="100" align="center">
                             	<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/teachingAffairsDepartment-a.jsp'"></div>
                      			<div class="center_a"><span>教务(生产)一项</span></div>
							 </td>
                             <td width="100" align="center"> 
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)二项</span></div>
							 </td>
                             <td width="100" align="center">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)三项</span></div>
							 </td>
                             <td width="100" align="center">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)四项</span></div>
							 </td>
                             <td width="100" align="center">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)五项</span></div>
							 </td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td width="80" align="center"><img src="<%=basePath%>images/jiatou_02.gif" width="56"
										height="51" border="0" /> </td>
                    </tr>
                </table></td>
        </tr>
      </table>
    </div>
</body>
</html>