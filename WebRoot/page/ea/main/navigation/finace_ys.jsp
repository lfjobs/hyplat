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
		<title>企业管理-</title>
		<link href="<%=basePath %>css/navegate.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
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
		<!--   OBJECT标签，客户端控件引用    -->
		<OBJECT id="SOAOfficeCtrl"
			codeBase="<%=basePath%>js/cabs/ZSOffice.ocx#version=2,0,0,1"
			height="0" width="0"
			classid="clsid:AD06827C-D92F-4648-B880-138AF11E8A13" data=""
			VIEWASTEXT>
			<div align=center STYLE="color: red;">
				本机尚未安装卓正OFFICE组件，请安装浏览器上方黄色提示条或弹出提示框中的卓正OFFICE组件。
			</div>
		</OBJECT>
	<table align="left">
	
	<tr>
	<td>
	<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><img width="50" height="50" border="0" src="<%=basePath%>images/04.gif" /> <br /> 
      物品管理</td>
    <td width="80" height="62" align="center"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /></td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer"  onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=00&cStaus=jt'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">预算前调查
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        <%--<td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              出纳记帐</div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        --%><td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=01&cStaus=jt'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">预算管理
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=02&cStaus=jt'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">确定预算
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=03&cStaus=jt'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"><font style="font-size: 11px;Line-height:normal">预算市场调查</font>
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=04&cStaus=jt'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"><font style="font-size: 11px;Line-height:normal">采购招标管理</font>
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=05&cStaus=jt'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"><font style="font-size: 11px;Line-height:normal">采购比价管理</font>
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=06&cStaus=jt'" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">采购物品申批管理
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=07&cStaus=jt'" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">采购前验货
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=08&cStaus=jt'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">采购管理
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  onClick="document.location.href='<%=basePath%>/ea/cashierbillsclassret/ea_getCashierList.jspa?BType=09&cStaus=jt'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"><font style="font-size: 11px;Line-height:normal">采购收获验货</font>
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
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
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
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
	</td>
	</tr>
	<tr>
	<td>
	<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img width="50" height="50" border="0" src="<%=basePath%>images/04.gif" /> <br /> 
      现金管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" valign="top"><div style="margin-left: 5px">预算前调查资金管理</div>
              </td>

            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        <%--<td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              出纳记帐</div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        --%><td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">预算资金
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">确定预算
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">市场调查资金管理
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">采购招标资金管理
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">采购比价资金管理</td>
            </tr>
            <tr>
              <td height="24" align="center">FM06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">采购资金申批管理
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">采购前申批拨现金对账</td>
            </tr>
            <tr>
              <td height="24" align="center">FM08</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"><font style="font-size: 11px;Line-height:normal">采购资金管理</font>
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;"><font style="font-size: 11px;Line-height:normal">收获资金验收</font>
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM10</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" valign="top"><div style="margin-left: 5px"></div>
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM11</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px"  valign="top"><div style="margin-left: 5px"></div>
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM12</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" valign="top"><div style="margin-left: 5px"></div>
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM13</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" valign="top"><div style="margin-left: 5px"></div>
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM14</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">
              </td>
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
	</td>
	</tr>
	
	<tr>
	<td>
	<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img width="50" height="50" border="0" src="<%=basePath%>images/04.gif" /> <br /> 
      会计管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" valign="top"><div style="margin-left: 5px">现金银行流水账</div>
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        <%--<td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              出纳记帐</div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        --%><td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">现金日记账
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">银行日记账
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">现金银行总账
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" 
        onClick="document.location.href='<%=basePath%>/page/ea/main/finance/production/csubejsts/csubejst_manger.jsp'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">会计科目处
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"
         onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=finace&date=Math.random()'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">财务报表
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
	</td>
	</tr>
	<tr>
	<td>
	<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img width="50" height="50" border="0" src="<%=basePath%>images/04.gif" /> <br /> 
      财务审核</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px"  valign="top"><div style="margin-left: 5px">现金银行流水账</div>
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        <%--<td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              出纳记帐</div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        --%><td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">现金日记账
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">银行日记账
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">现金银行总账
              </td>
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
	</td>
	</tr>
	<tr>
	<td>
	<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img width="50" height="50" border="0" src="<%=basePath%>images/04.gif" /> <br /> 
      出纳管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px"  valign="top"><div style="margin-left: 5px">现金银行流水账</div>
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        <%--<td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" onClick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa?'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">
              出纳记帐</div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
        --%><td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">现金日记账
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">银行日记账
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">现金银行总账
              </td>
            </tr>
            <tr>
              <td height="24" align="center">FM04</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" 
        onClick="document.location.href='<%=basePath%>/ea/cashiersummarycompany/ea_getCashierList.jspa'"><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">单据汇总
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM05</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">物品明细
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM06</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">公司单据归档</td>
            </tr>
            <tr>
              <td height="24" align="center">FM07</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top">财务基本信息管理</td>
            </tr>
            <tr>
              <td height="24" align="center">FM08</td>
            </tr>
        </table></td>
        <td  width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"></td>
            </tr>
            <tr>
              <td height="24" align="center">FM09</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"></td>
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
	</td></tr>
	<tr>
	<td>
	<table height="90" border="0" align="left" cellpadding="0" cellspacing="0" class="table03" style="margin-top: 30px">
  <tr>
    <td width="98" rowspan="2" align="center"><a><img width="50" height="50" border="0" src="<%=basePath%>images/04.gif" /> <br /> 
      资产管理</a> </td>
    <td width="80" height="62" align="center"><a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
								width="56" height="51" border="0" /> </a> </td>
    <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0" height="132px">
      <tr>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/dl01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">入库管理
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM01</td>
            </tr>
        </table></td>
       <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"  ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">出库管理
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM02</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer" ><table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">库存管理
              </div></td>
            </tr>
            <tr>
              <td height="24" align="center">FM03</td>
            </tr>
        </table></td>
        <td width="40" align="center" valign="top" style="background-image:url(<%=basePath%>images/lv01.gif); padding-top:11px; cursor:pointer"> 
        <table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="85px" align="center" valign="top"><div style="width:15px; margin:auto;">资产流水表
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
	</td></tr>
	</table>
	</body>
</html>
