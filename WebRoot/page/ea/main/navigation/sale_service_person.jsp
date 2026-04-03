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
<title>社会人员信息</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size:12px;
}
a:link,a:visited,a:active{
	text-decoration:none;
}
a:hover{
	text-decoration:underline;
}
a {
	color: #333;
	text-decoration:none;
}
a:active{color: #002f76;
font-weight:bold;}
-->
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
<body >
<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />
      社会人员信息</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/cosincumbent/ea_getStaffList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">员工汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumRoster.jspa?basicInfo=职工名册'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">职工名册</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumAddress.jspa?basicInfo=地址管理'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">地址汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumContact.jspa?basicInfo=联系方式'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">联系方式</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumEducation.jspa?basicInfo=学历学位'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">学位学历</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumResume.jspa?basicInfo=个人履历'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">个人履历</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumFamilyMember.jspa?basicInfo=家庭成员'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">家庭成员</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumPhysicalCondition.jspa?basicInfo=健康状况'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">健康状况</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumPoliticalStatus.jspa?basicInfo=政治面貌'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">政治面貌</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumEncourage.jspa?basicInfo=奖励汇总'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">奖励汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumPunishment.jspa?basicInfo=处分汇总'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">处分汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumInvestigation.jspa?basicInfo=调查汇总'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">调查汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumCertificate.jspa?basicInfo=证件汇总'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">证件汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK13</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumDocumentation.jspa?basicInfo=资料汇总'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">资料汇总</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumPersonalFile.jspa?basicInfo=人事档案'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">人事档案</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK15</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/suminfo/ea_getSumBankAccount.jspa?basicInfo=银行帐号'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">银行帐号</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK16</td>
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
