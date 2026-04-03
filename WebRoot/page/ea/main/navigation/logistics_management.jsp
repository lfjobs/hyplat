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
		<title>办公室-后勤管理</title>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
	<body>
	<br/>
<table>
	<tr>
		<td>
		<!--项目预算招标管理-->
	<table >
      <tr>
        <td  rowspan="2"><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6.png");'></div><div align="center"><strong>后勤管理</strong></div></td>
        <td><div class="na_back_img_jt_xs"></div></td>
        <td><table>
            <tr>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_01.png");' onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_Contactuser.jsp'"></div><div class="center_a"> 往来个人管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_02.png");' onclick="document.location.href='<%=basePath%>/ea/contactConnection/ea_getListContactConnection.jspa?'"></div><div class="center_a">往来单位管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_03.png");' onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_Car.jsp'"></div><div class="center_a">车辆管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_04.png");' onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_Camp.jsp'"></div><div class="center_a">住宿管理</div></td>
              <td ><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_05.png");'></div><div class="center_a">食堂管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_06.png");' onclick="document.location.href='<%=basePath%>/ea/electricity/ea_getListForPage.jspa?'"></div><div class="center_a">用电管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_07.png");' onclick="document.location.href='<%=basePath%>/ea/water/ea_getListForPage.jspa?'"></div><div class="center_a">用水管理</div></td>
            </tr>
        </table></td>
      </tr>
      <tr>
          <td><div class="na_back_img_jt_xx"></div></td>
        <td><table >
            <tr>
            <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_08.png");'  onclick="document.location.href='<%=basePath%>/ea/healthadministration/ea_getListForPage.jspa?'"></div><div class="center_a">卫生管理&nbsp;&nbsp;&nbsp;</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_09.png");'  onclick="document.location.href='<%=basePath%>/ea/onduty/ea_getListForPage.jspa?'"></div><div class="center_a">值班管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_10.png");'  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_management_a.jsp?'"></div><div class="center_a">安全管理</div></td>
              <td ><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_11.png");'  onclick="document.location.href='<%=basePath%>/ea/logistics/ea_getLogisticsList.jspa'"></div><div class="center_a">物流管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_12.png");'  onclick="document.location.href='<%=basePath%>/ea/afforest/ea_getListForPage.jspa?'"></div><div class="center_a">绿化管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_13.png");'  onclick="document.location.href='<%=basePath%>/ea/afforest/ea_getListForPage.jspa?'"></div><div class="center_a">基建管理</div></td>
              <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_6_14.png");'  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/piaowumanager.jsp'"></div><div class="center_a">票务管理</div></td>
            </tr>
        </table></td>
      </tr>
    </table>
		</td>
	</tr>
	<tr>
			<td>
				<!--库存账管理-->
				<table>
				<tr>
			<td><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_kcz.png");'></div>
				<div align="center">
					<strong>库存账管理</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td>
				<div class="na_back_dh"
					onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getPurchaseList.jspa?type=00'"
					align="center">
					<img src='<%=basePath%>images/sytemicon_new/adminicon/icon_8_04_1.png'></img>
				</div>
				<div class="center_a">费用采购明细账</div>
			</td>
			<td>
				<div class="na_back_dh"
					onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getinspectList.jspa'"
					align="center">
					<img src='<%=basePath%>images/sytemicon_new/adminicon/icon_8_04_2.png'></img>
				</div>
				<div class="center_a">验货管理</div>
			</td>
			<td>
				<div class="na_back_dh"
					onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_storage.jsp'"
					align="center">
					<img src='<%=basePath%>images/sytemicon_new/adminicon/icon_8_04_3.png'></img>
				</div>
				<div class="center_a">入库管理</div>
			</td>
			<td>
				<div class="na_back_dh"
					onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_out.jsp'"
					align="center">
					<img src='<%=basePath%>images/sytemicon_new/adminicon/icon_8_04_4.png'></img>
				</div>
				<div class="center_a">出库管理</div>
			</td>
			<td>
				<div class="na_back_dh"
					onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_inventory.jsp'"
					align="center">
					<img src='<%=basePath%>images/sytemicon_new/adminicon/icon_8_04_5.png'></img>
				</div>
				<div class="center_a">库存管理</div>
			</td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_8_04_6.png");' onclick="document.location.href='<%=basePath%>/ea/break/ea_getbreakList.jspa?'"></div>
				<div class="center_a">报损管理</div></td>
			
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_kchs_03.png");'></div>
				<div class="center_a">存货核算</div></td>

		</tr>
		</table>
			</td>
		</tr>
	
	<!-- 
	<tr>
		<td>
		资料库房
	<table >
      <tr>
        <td><div class="na_back_img_ks"></div><div class="center_a"><strong>资料库房</strong></div></td>
        <td><div class="na_back_img_jt_hx"></div></td>
        <td><table width="284" height="28">
            <tr>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa'"></div><div class="center_a">验货管理</div></td>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa'"></div><div class="center_a">入库管理</div></td>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa'"></div><div class="center_a">出库管理</div></td>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cashierbills/ea_getCashierBillsList.jspa'"></div><div class="center_a">库存管理</div></td>
            </tr>
        </table></td>
      </tr>
    </table>
		</td>
	</tr> -->
</table>
</body>
</html>
