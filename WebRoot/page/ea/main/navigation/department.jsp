<%@ page language="java" pageEncoding="UTF-8" %>
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
<title>人事-人事</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/navegate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body >

<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />
      人事管理科</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /></a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">部门规划处</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">招聘管理处</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">部门培训处</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM03</td>
            </tr>
        </table></td>
        <%--<td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">部门考核处</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM05</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>ea/cosincumbent/ea_getStaffList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">在职员工处</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>ea/cosdimission/ea_getListCOSDimission.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">离职员工处</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM07</td>
            </tr>
        </table></td>
         --%>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>ea/contactConnection/ea_getListContactConnection.jspa?'" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">往来单位处</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM04</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>ea/contactuser/ea_getListContactUser.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">往来个人处</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM06</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM9</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM10</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM11</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM12</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM13</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ho02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM15</td>
            </tr>
        </table></td>
        <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
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
