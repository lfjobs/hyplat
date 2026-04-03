
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>奖罚单打印</title>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        
        <style type="text/css">
        .tables {
			border-collapse:collapse;
			border:1px solid #000000;
			 
		}
		.tables th {
			border:1px solid #000000;
			color:#000000;
		}
		.tables td {
			border:1px solid #000000;
			color:#000000;
		}
		body {
			margin-left: 15px;
		}
		#apDiv1 {
			position:absolute;
			left:507px;
			top:287px;
			width:63px;
			height:32px;
			z-index:1;
		}
		
		body td{
			font-size:11px;
		
		}
		</style>
		
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
               <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
	</head>
	<body>
	<div id="apDiv1"></div>
	<div id="tableprint" align="center">
	<table width="620" border="0" cellpadding="0" cellspacing="0"  style="background:#FFFFFF;">
	  <tr>
	    <td height="25" align="center" style="font-weight:bold;font-size:16px">&nbsp;${listGamJeom[0][6]}</td>
	  </tr>
	</table>
	<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
	  <tr>
	  </tr>
	  <tr>
	    <td width="5%" height="25" align="right">公司：</td>
	    <td width="25%" align="left">${listGamJeom[0][1]}</td>
	    <td width="13.5%" align="right">部门：</td>
	    <td width="10%" align="left">${listGamJeom[0][2]}</td>
	    <td width="10%" align="center">责任人：${listGamJeom[0][4]}</td>
	  </tr>
	  <tr>
	    <td width="12%" height="25" align="right">制单人：</td>
	    <td colspan="2" align="left">${listGamJeom[0][8]}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制单日期：${fn:substring(listGamJeom[0][7], 0, 10)}</td>
	    <td width="11%" align="right" height="10">凭证号：</td>
	    <td width="10%" align="center" height="10"><% String data =request.getAttribute("listGamJeom[0][3]").toString();
											if (data != null) {
												StringBuffer barCode = new StringBuffer();
												barCode.append("<img src='");
												barCode.append(request.getContextPath());
												barCode.append("/CreateBarCode?data=");
												barCode.append(data);
												barCode
														.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
												out.println(barCode.toString());
											} else {
												out.println("no data");
											}
									%><br/>
									${listGamJeom[0][3]}</td>
	  </tr>
	</table>
    <table width="600" border="0" cellpadding="0" cellspacing="0" class="tables" style="background:#FFFFFF;">
    <tr>
      <td width="100" align="right" height="40">奖罚分：</td>
      <td width="200" align="left" style="padding-left:5px;">${listGamJeom[0][9]}</td>
      <td width="100" align="right">奖罚日期：</td>
      <td width="200" align="left" style="padding-left:5px;">${listGamJeom[0][10]}</td>
    </tr>
    <tr>
      <td height="40" align="right" width="100">奖罚金额(大写)：</td>
      <td colspan="3" align="left" style="padding-left:5px;">${listGamJeom[0][11]}</td>
    </tr>
     <tr>
      <td height="60" align="right" width="100">奖罚原因：</td>
      <td colspan="3" align="left" width="500">&nbsp;&nbsp;&nbsp;&nbsp;${listGamJeom[0][12]}</td>   
    <input id="prID" type="hidden" value="${listGamJeom[0][0]}"/>
    </tr>
  </table>
  <table width="620" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="92" height="25" align="right" style="font-size:12px">公司经理：</td>
    <td width="42">&nbsp;</td>
    <td width="90" align="right" style="font-size:12px">部门主管：</td>
    <td width="42">&nbsp;</td>
    <td width="82" align="right" style="font-size:12px">人事处：</td>
    <td width="42">&nbsp;</td>
    <td width="82" align="right" style="font-size:12px">财务审核：</td>
    <td width="42">&nbsp;</td>
    <td width="80" align="right" style="font-size:12px">责任人签字：</td>
    <td width="32">&nbsp;</td>
  </tr>
  <tr>
    <td height="25" align="right" style="font-size:12px">总部总经理：</td>
    <td>&nbsp;</td>
    <td align="right" style="font-size:12px">总部部门主管：</td>
    <td>&nbsp;</td>
    <td align="right" style="font-size:12px">总部人事处：</td>
    <td>&nbsp;</td>
    <td align="right" style="font-size:12px">总财务审核：</td>
    <td>&nbsp;</td>
    <td align="right" style="font-size:12px"></td>
    <td>&nbsp;</td>
  </tr>
</table>
</div>
</body>
</html>