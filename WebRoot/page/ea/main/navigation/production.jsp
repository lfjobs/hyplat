        <%@ page language="java" pageEncoding="UTF-8" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>人事-生产</title>
<%@ page language="java" pageEncoding="UTF-8"%>
</head>
<body >
 <%--<div style="width:100%; margin:0 auto; margin-top:30px;">
<table width="395" height="314" border="0" cellpadding="0" cellspacing="0" style="margin:0 auto;">
  <tr>
    <td width="100" height="71" align="right"><a href="<%=basePath%>/ea/responsibilitiessummary/ea_getResponsibilitiesList.jspa"><img src="<%=basePath%>images/004.gif" width="50" height="50" border="0" /><br />
      岗位职责汇总</a></td>
    <td width="50" align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td width="91" align="center"><a href="<%=basePath%>/ea/soincumbent/ea_getStaffListForIncumbent.jspa"><img src="<%=basePath%>images/002.gif" width="50" height="50" border="0" /><br />
    正式员工汇总</a></td>
    <td width="50" align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td width="91" align="center"><a href="<%=basePath%>/ea/cosdimission/ea_getListCOSDimission.jspa"><img src="<%=basePath%>images/02.gif" width="50" height="50" border="0" /><br />
    离职员工管理</a></td>
  </tr>
  <tr>
    <td height="88" align="right"><a href="<%=basePath%>/ea/jobplan/ea_getJobPlanListSummary.jspa"><img src="<%=basePath%>images/04.gif" width="50" height="50" border="0" /><br />
      工作计划汇总</a></td>
    <td align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/jobtask/ea_getJobTaskListSummary.jspa"><img src="<%=basePath%>images/02.gif" width="50" height="50" border="0" /><br />
      工作目标任务汇总</a></td>
      <td align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/staffappraisalsummary/ea_getStaffAppraisalList.jspa"><img src="<%=basePath%>images/02.gif" width="50" height="50" border="0" /><br />
      月综合考评汇总</a></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="right"><a href="<%=basePath%>/page/ea/main/human/office/production/SalaryIntergral_search.jsp"><img src="<%=basePath%>images/07.gif" width="50" height="50" border="0" /><br />
      员工工资管理</a></td>
    <td align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/payscale/ea_getListPayScale.jspa"><img src="<%=basePath%>images/06.gif" width="50" height="50" border="0" /><br />
      级别管理</a>
     </td>
     <td align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/payscale/ea_getStaffPayScaleList.jspa"><img src="<%=basePath%>images/06.gif" width="50" height="50" border="0" /><br />
      员工级别管理</a>
     </td>
    <td>&nbsp;</td>
  </tr>
   <tr>
    <td align="right"><a href="#"><img src="<%=basePath%>images/009.gif" width="50" height="50" border="0" /><br />
      员工工资计算方案</a></td>
    <td align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/logbooksummary/ea_getListLogBook.jspa"><img src="<%=basePath%>images/003.gif" width="50" height="50" border="0" /><br />
      工作日志汇总</a></td>
      <td align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/loglock/ea_getListLogLock.jspa"><img src="<%=basePath%>images/06.gif" width="50" height="50" border="0" /><br />
     工作日志加锁</a>
     </td>
    <td>&nbsp;</td>
  </tr>
</table>
</div>
--%>

<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />
    人事管理管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln01.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/responsibilitiessummary/ea_getResponsibilitiesList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; ">职责汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/soincumbent/ea_getStaffListForIncumbent.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto; ">员工汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/cosdimission/ea_getListCOSDimission.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">离职管理</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/jobplan/ea_getJobPlanListSummary.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">计划汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/jobtask/ea_getJobTaskListSummary.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">任务汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/staffappraisalsummary/ea_getStaffAppraisalList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">考评汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/page/ea/main/human/office/production/SalaryIntergral_search.jsp'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">工资管理</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/payscale/ea_getListPayScale.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">级别管理</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/payscale/ea_getStaffPayScaleList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">员工级别</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/logbooksummary/ea_getListLogBook.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">日志汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>/ea/loglock/ea_getListLogLock.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">日志加锁</div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM13</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/ln02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">PM15</td>
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
