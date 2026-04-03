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
<title>人事售前、售中、售后服务</title>
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
<%--<div style="width:100%; margin:0 auto; margin-top:30px;">
<table width="816" border="0" cellpadding="0" cellspacing="0" style="margin:0 auto;">
  <tr>
    <td width="108" height="71" align="center"><a href="<%=basePath%>/ea/payresearch/ea_getCOPayResearchList.jspa"><img src="<%=basePath%>images/004.gif" width="50" height="50" border="0" /><br />
    岗位要求薪酬调查</a></td>
    <td width="60" align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td width="108" align="center"><a href="<%=basePath%>/ea/intermediaryresearch/ea_getIntermediaryResearchList.jspa"><img src="<%=basePath%>images/002.gif" width="50" height="50" border="0" /><br />
    中介单位调查管理</a></td>
    <td width="58">&nbsp;</td>
    <td width="108" align="center"><a href="#"><br />
    </a></td>
    <td width="55" align="center">&nbsp;</td>
    <td width="108" align="center">&nbsp;</td>
    <td width="87" align="center">&nbsp;</td>
    <td width="113" align="center">&nbsp;</td>
    </tr>
  <tr>
    <td height="88" align="center"><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa"><img src="<%=basePath%>images/04.gif" width="50" height="50" border="0" /><br />
      社会人力资源管理</a></td>
    <td align="center"><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=0"><img src="<%=basePath%>images/02.gif" width="50" height="50" border="0" /><br />
      人员招聘登记管理</a></td>
    <td><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=1"><img src="<%=basePath%>images/06.gif" width="50" height="50" border="0" /><br />
      人员面试管理</a></td>
    <td><img src="<%=basePath%>images/01.gif" width="50" height="25" /></td>
    <td align="center"><a href="<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=2"><img src="<%=basePath%>images/13.gif" width="50" height="50" border="0" /><br />
      人员入职管理</a></td>
   </tr>
</table>
</div>
--%>


<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />
      售前服务管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/payresearch/ea_getCOPayResearchList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">薪酬调查</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/intermediaryresearch/ea_getIntermediaryResearchList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">中介调查</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?aa=aa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">社会人力</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=0'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">人员登记</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=1'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">面试管理</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=2'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">入职管理</div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK13</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK15</td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="80" align="center"><img src="<%=basePath%>images/jiatou_02.gif"
								width="56" height="51" border="0" /></td>
  </tr>
  
  <tr height="30"></tr>
  <!-- 售中服务管理科 -->
  <tr>
    <td width="98" rowspan="2" align="center"><a><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />
      售中服务管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">
              <font style="font-size: 11px;Line-height:normal">成交客户管理</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onclick="document.location.href='<%=basePath%>page/ea/ccompany/scheduledproduct/scheduledproduct_main.jsp'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">
              <font style="font-size: 11px;Line-height:normal">预定产品管理</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/transactionservice/ea_getListTransactionService.jspa?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">
              <font style="font-size: 11px;Line-height:normal">产品成交管理</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/advisingclients/ea_getListAdvisingClients.jspa?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              <font style="font-size: 11px;Line-height:normal">指导客户管理</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK13</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK15</td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="80" align="center"><img src="<%=basePath%>images/jiatou_02.gif"
								width="56" height="51" border="0" /></td>
  </tr>
  
  <tr height="30"></tr>
  <!-- 售后服务管理科 -->
  <tr>
    <td width="98" rowspan="2" align="center"><a><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />
      售后服务管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>ea/clientTracking/ea_getClientTrackingList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">
              <font style="font-size: 11px;Line-height:normal">跟踪服务管理</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>ea/clientPblm/ea_getClientPblmList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">
              <font style="font-size: 11px;Line-height:normal">问题解决管理</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>ea/clientIncrement/ea_getClientIncrementList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto">
              <font style="font-size: 11px;Line-height:normal">增值服务管理</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              <font style="font-size: 11px;Line-height:normal">成交增值管理</font></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK13</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl02.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto"></div></td>
            </tr>
            <tr>
              <td height="24" align="center">MK15</td>
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
