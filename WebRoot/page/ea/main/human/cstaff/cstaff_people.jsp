<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta http-equiv="cache-control" content="no-cache"/>
<title>个人客户需求资料</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<link href="../css/admin_main.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.3.1.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		 <script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>	
		 <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/cstaff_info.js"></script>
<style type="text/css">
        .xie{
 border: #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; border-bottom-width: 1px;";
}
        </style>
<script type="text/javascript">
   var basePath= '<%=basePath%>';
   var pbasePath = '<%=basePath%>';
   var pNumber='${pageNumber}';
   var treeid;
   var treename;
   var tree;
   var date;
  var token =0;
var retoken = 0;
var notoken = 0;
var aa="${aa}";
var personvalue = "";
var staffID = '<%=request.getParameter("staffID") %>';
</script>
</head>
  <body>
    <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="40" align="center" style="font-size:16px; font-weight:bold; color:#FF0000; line-height:30px">个人客户需求资料</td>
  </tr>
</table>

<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="6">1、员工基本信息</td>
    <td rowspan="8" height="220px" width="160px"><img src="<%=basePath%>${basic[0][8]}" width="160px" height="220" /></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td width="100" height="26">姓 名 </td>
    <td height="26">${basic[0][1]}</td>
    <td width="100">曾用名</td>
    <td>${basic[0][2]}</td>
    <td width="100">出生日期</td>
    <td>${basic[0][3]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" >
    <td height="26">籍 贯</td>
    <td>${basic[0][4]}</td>
    <td>民 族</td>
    <td align="center">${basic[0][5]}</td>
    <td>身份证号码</td>
    <td>${basic[0][6]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">家庭住址</td>
    <td colspan="5">${basic[0][7]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">常住地址</td>
    <td colspan="5">${address[0][0]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="6">2、联系方式</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">家庭电话</td>
    <td colspan="2">${basic[0][9]}</td>
    <td width="100" align="center">移动电话</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">E-mail</td>
    <td colspan="2">${basic[0][10]}</td>
    <td align="center">QQ</td>
    <td colspan="2">${basic[0][11]}</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">3、学历子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">入学时间</td>
    <td>毕业时间</td>
    <td>毕业学校及单位</td>
    <td>所学专业</td>
    <td>学历</td>
    <td>学习形式</td>
    <td>教育类别</td>
    <td>证明人</td>
  </tr>
	<c:forEach var="objs" items="${listDegree}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">4、个人履历子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td width="100" height="26">起时间</td>
    <td width="100">止时间</td>
    <td>工作单位</td>
    <td width="60">职 务</td>
    <td width="200">工作内容</td>
    <td width="60">岗位名称</td>
    <td>证明人</td>
  </tr>
	<c:forEach var="objs" items="${listResume}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">个人特长</td>
    <td colspan="6"> <input type="text" name="" size="60" class="xie" id="techang"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">个人需求</td>
    <td colspan="6"> <input type="text" name="" size="60" class="xie" id="xuqiu"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">发展目标</td>
    <td colspan="6"> <input type="text" name="" size="60" class="xie" id="fazhan"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">证件名称</td>
    <td colspan="3"><input type="text" name="" size="30" class="xie" id="zhengjian"></td>
    <td>证件编号</td>
    <td colspan="3"><input type="text" name="" size="30" class="xie" id="bianhao"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">付款方式</td>
    <td colspan="6">交款方式
     <input type="checkbox" name="cbox" value=" 现金支付 ">  现金支付  
     <input type="checkbox" name="cbox" value=" 银行汇款 ">  银行汇款  
     <input type="checkbox" name="cbox" value="现金支付">  现金支付
     <input type="checkbox" name="cbox" value="邮局汇款">  邮局汇款
     </td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">客户意见</td>
    <td colspan="3"> <input type="text" name="" size="30" class="xie" id="idea"></td>
    <td>客户签名</td>
    <td colspan="3"></td>
  </tr>
</table>
<div align="center">
	<input type="button" class="input-button JQueryreturn5" value="打印预览" />
	<input type="button" class="input-button JQueryreturn4" value="取消" />
</div>
  </body>
</html>
