<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String star = request.getParameter("star");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>培训记录登记表打印</title>
<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.table{
		border:#cccccc 1px solid;
		cellpadding:0;
		cellspacing:0;
	}
	.td{
		border:#cccccc 1px solid;
	}
</style>
</head>
<body>
	<table width="800" border="0" align="center" >
		<tr>
			<td align="center" colspan="6">
				<h1>培训记录登记表</h1></td>
		</tr>
	</table>
	<table width="800" align="center" class="table">
		<tr align="center" height="22">
			<td height="26" colspan="6">1、员工基本信息</td>
			<td rowspan="8" height="220px" width="160px">&nbsp;</td>
		</tr>
		<tr align="center" >
			<td width="100" height="26">姓 名</td>
			<td height="26">&nbsp;</td>
			<td width="100">曾用名</td>
			<td>&nbsp;</td>
			<td width="100">出生日期</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td height="26">籍 贯</td>
			<td>&nbsp;</td>
			<td>民 族</td>
			<td align="center">&nbsp;</td>
			<td>身份证号码</td>
			<td width="150">&nbsp;</td>
		</tr>
		<tr align="center" height="22">
			<td height="26">家庭住址</td>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr align="center"  height="22">
			<td height="26">常住地址</td>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr align="center" height="22">
			<td height="26" colspan="6">2、联系方式</td>
		</tr>
		<tr align="center"  height="22">
			<td height="26">家庭电话</td>
			<td colspan="2">&nbsp;</td>
			<td width="100" align="center">移动电话</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr align="center"  height="22">
			<td height="26">E-mail</td>
			<td colspan="2">&nbsp;</td>
			<td align="center">QQ</td>
			<td colspan="2">&nbsp;</td>
		</tr>
	</table>
	<table width="800" align="center"  class="table">
		<tr align="center" height="22">
			<td height="26" colspan="9">3、培训记录</td>
		</tr>
		<tr align="center">
			<td height="26">科一</td>
			<td>教学内容</td>
			<td>起时间</td>
			<td>止时间</td>
			<td>学时</td>
			<td>学员学习意见</td>
			<td>老师意见</td>
			<td>教练</td>
			<td>完成/否</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>1.机械尝试</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>2.法律法规</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>3.安全文明</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td height="26">科二</td>
			<td>教学内容</td>
			<td>起时间</td>
			<td>止时间</td>
			<td>学时</td>
			<td>学员学习意见</td>
			<td>老师意见</td>
			<td>教练</td>
			<td>完成/否</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>1.倒车入库</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>2.侧方移位</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>3.单边桥</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>4.直角转弯</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>5.S路</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>6.坡道起步</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td height="26">科三</td>
			<td>教学内容</td>
			<td>起时间</td>
			<td>止时间</td>
			<td>学时</td>
			<td>学员学习意见</td>
			<td>老师意见</td>
			<td>教练</td>
			<td>完成/否</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>1.加减挡</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>2.靠边停车</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>3.过红绿灯</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>4.超车</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>5.灯光使用</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>6.窄路掉头</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="left">
			<td height="26">&nbsp;</td>
			<td>7.超实线</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td height="26" colspan="2">缴费情况</td>
			<td>已收</td>
			<td>&nbsp;</td>
			<td>欠款</td>
			<td>&nbsp;</td>
			<td colspan="3">未清  /  交清</td>
		</tr>
		<tr align="center">
			<td height="56" colspan="2">学员科二、<br>科三学习意见</td>
			<td colspan="2">&nbsp;</td>
			<td colspan="2">学员签字</td>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr align="center">
			<td height="56" colspan="2">老师科二、<br>科三学习意见</td>
			<td colspan="2">&nbsp;</td>
			<td colspan="2">学员签字</td>
			<td colspan="3">&nbsp;</td>
		</tr>
	</table>
</body>
</html>
