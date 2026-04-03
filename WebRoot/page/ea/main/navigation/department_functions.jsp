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
<title> 人事管理</title>
<link href="<%=basePath %>css/navegate.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
    <div>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="98" class="table03"><table>
            <tr>
              <td width="98" rowspan="2" align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                部门职能描述</td>
                <td width="80" height="62" align="center"><table><tr><td><img src="<%=basePath%>images/jiatou_01.gif"
											width="56" height="51" border="0" /></td></tr><tr><td><img src="<%=basePath%>images/jiatou_02.gif" width="56"
										height="51" border="0" /></td></tr></table> </td>
            </tr>
          </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0" height="132px" style="font-size:12px; font-weight:bold;">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">部门职能说明书</td>
            </tr>
            <tr>
              <td height="24" align="center">OM01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/exit_management_a.jsp?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">部门职能战略图</td>
            </tr>
            <tr>
              <td height="24" align="center">OM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/exit_management_b.jsp?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; ">部门工作流</div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/exit_management_c.jsp?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top" style="font-size:11px">部门五工作量化成果指标管理</td>
            </tr>
            <tr>
              <td height="24" align="center">OM04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; "><font style="font-size: 11px;Line-height:normal">部门职能要求</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">部门安全责任书</td>
            </tr>
            <tr>
              <td height="24" align="center">OM06</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">部门目标任务设定</td>
            </tr>
            <tr>
              <td height="24" align="center">OM07</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; "></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; "></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM9</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM11</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM12</td>
            </tr>
        </table></td>
        
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; "></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM13</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he01.gif); padding-top:11px;cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; "></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM14</td>
            </tr>
        </table></td>
         <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; "></div></td>
            </tr>
            <tr>
              <td height="24" align="center">OM15</td>
            </tr>
        </table></td>
      </tr>
    </table></td>
        </tr>
      </table>
    </div>
</body>
</html>