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
    <script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/contactcompany/contactcompany.js"></script>
    <style type="text/css">
        .xie{
 border: #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-left-width: 0px; border-bottom-width: 1px;";
}
        </style>
        <script type="text/javascript">
		 var basePath='<%=basePath%>'; 
		 var ccompanyID = '<%=request.getParameter("contactCompany.ccompanyID") %>';
		 var flexbutton = '';  //contactcompany.js中的判断定义
</script>
  </head>
  
  <body>
    <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="40" align="center" style="font-size:16px; font-weight:bold; color:#FF0000; line-height:30px">单位需求资料</td>
  </tr>
</table>

<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">

  <tr align="center" bgcolor="#FAFAF1">
    <td width="100" height="26">单位名称 </td>
    <td height="26" colspan="3">${basic[0][0]}</td>
    <td width="100">单位电话</td>
    <td>${basic[0][1]}</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td width="100" height="26">单位负责人</td>
    <td width="100" height="26" >${basic[0][2]}</td>
    <td width="100" height="26">负责人电话</td>
    <td height="26" >${basic[0][3]}</td>
    <td width="100">所属行业</td>
    <td>${basic[0][4]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">经营地址</td>
    <td colspan="5">${basic[0][5]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="6">联系方式</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">移动电话</td>
    <td colspan="2"><input type="text" name="" size="30" class="xie" id="tel"></td>
    <td width="100" align="center">Q Q</td>
    <td colspan="2"><input type="text" name="" size="30" class="xie" id="qq"></td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">E-mail</td>
    <td colspan="2"><input type="text" name="" size="30" class="xie" id="emal"></td>
    <td align="center">网 址</td>
    <td colspan="2"><input type="text" name="" size="30" class="xie" id="wang"></td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">所属企业</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">单位名称</td>
    <td>责任人</td>
    <td>电话</td>
    <td>网 址</td>
    <td>Email</td>
    </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26"><input type="text" name="" size="15" class="xie" id="wen1"></td>
    <td><input type="text" name="" size="15" class="xie" id="wen2"></td>
    <td><input type="text" name="" size="15" class="xie" id="wen3"></td>
    <td><input type="text" name="" size="15" class="xie" id="wen4"></td>
    <td><input type="text" name="" size="15" class="xie" id="wen5"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26"><input type="text" name="" size="15" class="xie" id="wen6"></td>
    <td><input type="text" name="" size="15" class="xie" id="wen7"></td>
    <td><input type="text" name="" size="15" class="xie" id="wen8"></td>
    <td><input type="text" name="" size="15" class="xie" id="wen9"></td>
    <td><input type="text" name="" size="15" class="xie" id="wen0"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">今年的营业额</td>
    <td colspan="4"><input type="text" name="" size="60" class="xie" id="ying"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">今年的利润额</td>
    <td colspan="4"><input type="text" name="" size="60" class="xie"  id="li"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">我的一年目标</td>
    <td colspan="4"><input type="text" name="" size="60" class="xie" id="mys"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">我的三年目标</td>
    <td colspan="4"><input type="text" name="" size="60" class="xie" id="tress"></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">资源情况</td>
    <td colspan="2">我所缺少的资源 <br><textarea name="" rows="5" cols="30" id="shao"></textarea></td>
    <td colspan="2">我可以分享的资源 <br><textarea name="" rows="5" cols="30" id="share"></textarea></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">付款方式</td>
    <td colspan="4">交款方式、
    <input type="checkbox" name="cbox" value="现金支付">  现金支付  
     <input type="checkbox" name="cbox" value="银行汇款">  银行汇款  
     <input type="checkbox" name="cbox" value="现金支付">  现金支付
     <input type="checkbox" name="cbox" value="邮局汇款">  邮局汇款
     </td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">客户意见</td>
    <td><input type="text" name="" size="30" class="xie" id="idea"></td>
    <td>客户签名</td>
    <td colspan="2"></td>
  </tr>
</table>
<div align="center">
	<input type="button" class="input-button JQueryreturn5" value="打印预览" />
	<input type="button" class="input-button JQueryreturn4" value="取消" />
</div>
  </body>
</html>
