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
		<title>工资管理</title>
		<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css" type="text/css"/>
	</head>
	<body>
		<table border="0" align="left" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" rowspan="3"><div class="na_back_img_ks"></div><div class="center_a"><strong>
        工资管理</strong></div></td>
        <td align="center"><div class="na_back_img_jt_xs"></div></td>
        <td>
			<table border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>级别编号管理</span></div></td>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>岗位职能登记编号</span></div></td>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>岗位职能等级设定</span></div></td>            
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>基本工资设定</span></div></td>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>日考评设定</span></div></td>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>周考评设定</span></div></td>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>月考评设定</span></div></td>
	              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>季考评设定</span></div></td>
	            </tr>
	        </table>
        </td>
      </tr>
       <tr>
       <td align="center"></td>
      	<td>
      	<table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>年考评设定</span></div></td>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>任务考核设定</span></div></td>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>特殊人才设定</span></div></td>            
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>保密薪酬设定</span></div></td>
               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>计件设定</span></div></td>
             <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>生活设定</span></div></td>
             <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>交通设定</span></div></td>
             <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>降温设定</span></div></td>
            </tr>
        </table>	
      	</td>
      </tr>
      <tr>
       <td align="center"><div class="na_back_img_jt_xx"></div></td>
      	<td>
      	<table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>防寒设定</span></div></td>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>通信设定</span></div></td>
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>学历设定</span></div></td>            
              <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>红白喜事礼品设定</span></div></td>
               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>生日礼物设定</span></div></td>
               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>孝道设定</span></div></td>
               <td><div class="na_back_img"></div>
							<div class="center_a">
								<span>工龄设定</span></div></td>
               <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/salary_manage_aa.jsp?'"></div>
							<div class="center_a">
								<span>折扣设定</span></div></td>
            </tr>
        </table>	
      	</td>
      </tr>
    </table>
	</body>
	
</html>