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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公司财务汇总-</title>
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 12px;
}

a:link,a:visited,a:active {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

a {
	color: #333;
	text-decoration: none;
}

a:active {
	color: #002f76;
	font-weight: bold;
}

.roundedCorners {
	width: 750px;
	padding: 10px;
	margin: auto;
	background-color: #FFF;
	border: 1px solid #CCE6F1;
	/* Do rounding (native in Safari, Firefox and Chrome) */
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
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
<script>
function clickAction(action,parater){
if(parater == '1'){
	var treeID = '<%=session.getAttribute("organizationID")%>';
 window.location.href= action+treeID;
 return;
 }
 window.location.href= action;
}
</script>
	</head>
	<body>
	   <table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img src="<%=basePath%>images/04.gif" width="50"
								height="50" border="0" /> <br />
     出纳管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
       <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashiersummary/ea_getCashierList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              出纳单据</div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/goodscashier/ea_getCashierList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              物品明细</div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM02</td>
            </tr>
        </table></td>
        
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM13</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM15</td>
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
