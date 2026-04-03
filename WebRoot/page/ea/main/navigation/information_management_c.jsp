<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <!-- 未用到c -->
    <title>My JSP 'information_management_a.jsp' starting page</title>
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
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
    <table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />
      实物库房管理办</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">采购管理组</div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">入库管理组</div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">出库管理组</div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">库存管理组</div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM04</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; line-height:17px"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; line-height:17px"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; line-height:17px"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; line-height:17px"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; line-height:17px"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; line-height:17px"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; line-height:17px"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM13</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM15</td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="80" align="center"><img src="<%=basePath%>images/jiatou_02.gif"
								width="56" height="51" border="0" /></td>
  </tr>
</table>	
  </body>
</html>
