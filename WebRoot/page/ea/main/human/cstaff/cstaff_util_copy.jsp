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
      .xies{
		border-left-style:none;border-right-style:none;border-top-style:none;BORDER-bottom-STYLE: none;
		}
        </style>
        <style type="text/css">
		.table {
			border-collapse: collapse;
			border: 1px solid #000000;
		}
		
		.table th {
			border: 1px solid #000000;
			color: #000000;
		}
		
		.table td {
			border: 1px solid #000000;
			color: #000000;
		}
		body {
			margin-left: 15px;
		}
		
		#textDate {
			text-decoration: underline;
		}
		body td{
			font-size:11px;
		
		}
		</style>
        <script type="text/javascript">
		   var basePath='<%=basePath%>'; 
		   var ying = '<%=request.getParameter("ying") %>';
		   var li = '<%=request.getParameter("li") %>';
		   var mys = '<%=request.getParameter("mys") %>';
		   var tress = '<%=request.getParameter("tress") %>';
		   var idea = '<%=request.getParameter("idea") %>';
		   var shao= '<%=request.getParameter("shao") %>'; 
		   var share= '<%=request.getParameter("share") %>';  
	       var tel='<%=request.getParameter("tel")%>';
	       var qq='<%=request.getParameter("qq")%>';
	       var emal='<%=request.getParameter("emal")%>';
	       var wang='<%=request.getParameter("wang")%>';
	       var wen1='<%=request.getParameter("wen1")%>';
	       var wen2='<%=request.getParameter("wen2")%>';
	       var wen3='<%=request.getParameter("wen3")%>';
	       var wen4='<%=request.getParameter("wen4")%>';
	       var wen5='<%=request.getParameter("wen5")%>';
	       var wen6='<%=request.getParameter("wen6")%>';
	       var wen7='<%=request.getParameter("wen7")%>';
	       var wen8='<%=request.getParameter("wen8")%>';
	       var wen9='<%=request.getParameter("wen9")%>';
	       var wen0='<%=request.getParameter("wen0")%>';
	       var str='<%=request.getParameter("str")%>';
	       
		</script>
		<script type="text/javascript">
		  $(document).ready(function(){
		   	   $("input#ying").attr("value",ying);
		   	   $("input#li").attr("value",li);
		   	   $("input#mys").attr("value",mys);
		   	   $("input#tress").attr("value",tress);
		   	   $("input#idea").attr("value",idea);
		   	   $("input#tel").attr("value",tel);
		   	   $("input#qq").attr("value",qq);
		   	   $("input#emal").attr("value",emal);
		   	   $("input#wang").attr("value",wang);
		   	   $("#shao").attr("value",shao);
		   	   $("#share").attr("value",share);
		   	   $("#wen1").attr("value",wen1);
		   	   $("#wen2").attr("value",wen2);
		   	   $("#wen3").attr("value",wen3);
		   	   $("#wen4").attr("value",wen4);
		   	   $("#wen5").attr("value",wen5);
		   	   $("#wen6").attr("value",wen6);
		   	   $("#wen7").attr("value",wen7);
		   	   $("#wen8").attr("value",wen8);
		   	   $("#wen9").attr("value",wen9);
		   	   $("#wen0").attr("value",wen0);
		   	   $("#str").attr("value",str);
		   })
		  </script>
  </head>
  
  <body>
    <table width="620" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="40" align="center" style="font-size:16px; font-weight:bold; color:#FF0000; line-height:30px">单位需求资料</td>
  </tr>
</table>

<table width="620" border="0" align="center" cellpadding="2" cellspacing="1"  class="table">

  <tr align="center" >
    <td width="100" height="26">单位名称 </td>
    <td height="26" colspan="3">${basic[0][0]}</td>
    <td width="100">单位电话</td>
    <td>${basic[0][1]}</td>
  </tr>
  <tr align="center" >
    <td width="100" height="26">单位负责人</td>
    <td width="100" height="26" >${basic[0][2]}</td>
    <td width="100" height="26">负责人电话</td>
    <td height="26" >${basic[0][3]}</td>
    <td width="100">所属行业</td>
    <td>${basic[0][4]}</td>
  </tr>
  <tr align="center"  height="22" >
    <td height="26">经营地址</td>
    <td colspan="5">${basic[0][5]}</td>
  </tr>
  <tr align="center"  height="22" >
    <td height="26" colspan="6">联系方式</td>
  </tr>
  <tr align="center"  height="22" >
    <td height="26">移动电话</td>
    <td colspan="2"><input type="text" name="" size="30" class="xies" id="tel" readonly></td>
    <td width="100" align="center">Q Q</td>
    <td colspan="2"><input type="text" name="" size="30" class="xies" id="qq" readonly></td>
  </tr>
  <tr align="center"  height="22" >
    <td height="26">E-mail</td>
    <td colspan="2"><input type="text" name="" size="30" class="xies" id="emal" readonly></td>
    <td align="center">网 址</td>
    <td colspan="2"><input type="text" name="" size="30" class="xies" id="wang" readonly></td>
  </tr>
</table>
<table width="620px" border="0" align="center" cellpadding="2" cellspacing="1" class="table">
  <tr align="center"  height="22" >
    <td height="26" colspan="5" >所属企业</td>
  </tr>
  <tr align="center" >
    <td height="26">单位名称</td>
    <td>责任人</td>
    <td>电话</td>
    <td>网 址</td>
    <td>Email</td>
    </tr>
  <tr align="center" >
    <td height="26"><input type="text" name=""  class="xies" id="wen1" readonly size="5"></td>
    <td><input type="text" name=""  class="xies" id="wen2" readonly size="5"></td>
    <td><input type="text" name="" class="xies" id="wen3" readonly size="5"></td>
    <td><input type="text" name="" class="xies" id="wen4" readonly size="5"></td>
    <td><input type="text" name=""  class="xies" id="wen5" readonly size="5"></td>
  </tr>
  <tr align="center" >
    <td height="26"><input type="text" name=""  class="xies" id="wen6" readonly size="5"></td>
    <td><input type="text" name=""  class="xies" id="wen7" readonly size="5"></td>
    <td><input type="text" name=""  class="xies" id="wen8" readonly size="5"></td>
    <td><input type="text" name=""  class="xies" id="wen9" readonly size="5"></td>
    <td><input type="text" name="" class="xies" id="wen0" readonly size="5"></td>
  </tr>
  <tr align="center" >
    <td height="26">今年的营业额</td>
    <td colspan="4"><input type="text" name="" size="60" class="xies" id="ying" readonly></td>
  </tr>
  <tr align="center" >
    <td height="26">今年的利润额</td>
    <td colspan="4"><input type="text" name="" size="60" class="xies"  id="li" readonly></td>
  </tr>
  <tr align="center" >
    <td height="26">我的一年目标</td>
    <td colspan="4"><input type="text" name="" size="60" class="xies" id="mys" readonly></td>
  </tr>
  <tr align="center" >
    <td height="26">我的三年目标</td>
    <td colspan="4"><input type="text" name="" size="60" class="xies" id="tress" readonly></td>
  </tr>
  <tr align="center" >
    <td height="26">资源情况</td>
    <td colspan="2">我所缺少的资源 <br><textarea name="" rows="5" cols="30" id="shao" readonly></textarea></td>
    <td colspan="2">我可以分享的资源 <br><textarea name="" rows="5" cols="30" id="share" readonly></textarea></td>
  </tr>
  <tr align="center" >
    <td height="26">付款方式</td>
    <td colspan="4"><input type="text" name="" class="xies" id="str" readonly></td>
  </tr>
  <tr align="center" >
    <td height="26">客户意见</td>
    <td><input type="text" name=""  class="xies" id="idea" readonly></td>
    <td>客户签名</td>
    <td colspan="2"></td>
  </tr>
</table>
  </body>
</html>
