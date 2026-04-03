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
		<title>考勤折扣设定</title>
		<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css" type="text/css"/>
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<style type="text/css">
		
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 12px;
}

</style>
<style type="text/css">
<!--

.table03 {
	font-size: 14px;
	line-height:17px;
	font-weight:bold;
	margin:10px 0 0 10px；
}

.table03 th {
	font-size: 14px;
	line-height:17px;
	font-weight:bold;
}
.table03 td {
	font-size: 14px;
	line-height:17px;
	font-weight:bold;
}


body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
	</head>
	<body>
		<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" rowspan="2"><div class="na_back_img_ks"></div><div class="center_a"><strong>
         考勤折扣设定</strong></div></td>
        <td width="55" align="center"><div class="na_back_img_jt_xs"></div></td>
        <td>
			<table border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>迟到</span></div></td>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>早退</span></div></td>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>旷工</span></div></td>            
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>病假</span></div></td>
	               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>事假</span></div></td>
	               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>产假</span></div></td>
	               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>婚假</span></div></td>
	            </tr>
	        </table>
        </td>
      </tr>
      <tr>
       <td width="55" align="center"><div class="na_back_img_jt_xx"></div></td>
      	<td>
      	<table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>丧假</span></div></td>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>年假</span></div></td>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>工伤</span></div></td>            
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>出差</span></div></td>
               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>脱岗</span></div></td>
               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>值班设定</span></div></td>
               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>探亲假设定</span></div></td>
            </tr>
        </table>	
      	</td>
      </tr>
    </table>
	</body>
</html>
