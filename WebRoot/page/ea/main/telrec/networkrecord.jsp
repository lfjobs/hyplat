<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>远程信息记录</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<style type="text/css">
a {
	text-align: left; 
	text-decoration : none;
	color: black;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
	text-align: left;
	color: red;
}
#operate{
background-color:gray;
margin-top:10%;
margin-left:70%;
}
#serachtext{
width: 50px;
}
</style>

	</head>

	<body style="text-align: center">
	<span>查询结果</span><br><br><br>
		<table bordercolor="gray"
			style="border: 1px solid gray; border-collapse: collapse; width: 100%;">
			<tr>
				<td>
					控制时间
				</td>
				<td>
					姓名
				</td>
				<td>
					地址
				</td>
				<td>
					联系方式
				</td>
			</tr>
			<tr><br>
				<td>
					2011-05-03
				</td>
				<td>
					张三
				</td>
				<td>
					北京市海淀区甲29号
				</td>
				<td>
					13689545556
				</td>
			</tr>
			<tr>
				<td>
					2012-07-16
				</td>
				<td>
					利索
				</td>
				<td>
					北京市西城区花园街56号
				</td>
				<td>
					13456412556
				</td>
			</tr>
			<tr>
				<td>
					2011-05-12
				</td>
				<td>
					裘千仞
				</td>
				<td>
					山西省赣州市
				</td>
				<td>
					15689854558
				</td>
			</tr>
			<tr>
				<td>
					2012-06-18
				</td>
				<td>
					张飞
				</td>
				<td>
					广东省佛山29号
				</td>
				<td>
					15584844554
				</td>
			</tr>
			<tr>
				<td>
					2011-03-18
				</td>
				<td>
					郭靖
				</td>
				<td>
					北京市香山植物园南门
				</td>
				<td>
					13558652585
				</td>
			</tr>
			<tr>
				<td>
					2012-06-23
				</td>
				<td>
					宁杰
				</td>
				<td>
					北京市西城区玄武门大街
				</td>
				<td>
					18659554255
				</td>
			</tr>
		</table>
	<div id="operate">
	<a href="#">上一页</a>
		<select id="serachtext"  name="seach"> 
			<option>01</option>
			<option>02</option>
			<option>03</option>
			<option>04</option>
			<option>05</option>
			<option>06</option>
			<option>07</option>
			<option>08</option>
			<option>09</option>
			<option>10</option>
			<option>11</option>
		</select>
		&nbsp;
		<input type="button" value="go"/>
		<a href="#">下一页</a>
	</div>
		
	</body>
</html>
