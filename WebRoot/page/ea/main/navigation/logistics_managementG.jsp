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
		<title>办公室(生产)-集团后勤管理</title>
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
        <td  rowspan="2"><div class="na_back_img_ks"></div><div class="center_a"><strong>后勤管理</strong></div></td>
        <td><div class="na_back_img_jt_xs"></div></td>
        <td><table>
            <tr>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_Contactuser.jsp'"></div><div class="center_a"> 个人接待管理</div></td>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/contactConnection/ea_getListContactConnection.jspa?'"></div><div class="center_a">单位接待管理</div></td>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_CarG.jsp'"></div><div class="center_a">车辆管理</div></td>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_Camp.jsp'"></div><div class="center_a">住宿管理</div></td>
              <td ><div class="na_back_img"></div><div class="center_a">食堂管理</div></td>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/electricity/ea_getListForPage.jspa?'"></div><div class="center_a">用电管理</div></td>
              <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/water/ea_getListForPage.jspa?'"></div><div class="center_a">用水管理</div></td>
            </tr>
        </table></td>
      </tr>
      <tr>
          <td><div class="na_back_img_jt_xx"></div></td>
        <td><table >
            <tr>
            <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/healthadministration/ea_getListForPage.jspa?'"></div><div class="center_a">卫生管理&nbsp;&nbsp;&nbsp;</div></td>
              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/onduty/ea_getListForPage.jspa?'"></div><div class="center_a">值班管理</div></td>
              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_management_a.jsp?'"></div><div class="center_a">安全管理</div></td>
              <td ><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/logistics/ea_getLogisticsList.jspa'"></div><div class="center_a">物流管理</div></td>
              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/afforest/ea_getListForPage.jspa?'"></div><div class="center_a">绿化管理</div></td>
              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/afforest/ea_getListForPage.jspa?'"></div><div class="center_a">基建管理</div></td>
            </tr>
        </table></td>
      </tr>
    </table>
		</td>
	</tr>
	
	<tr>
		<td>
		<!--资金申请管理-->
	<table>
      <tr>
        <td><div class="na_back_img_ks"></div><div class="center_a"><strong>资金申请管理</strong></div></td>
        <td><div class="na_back_img_jt_hx"></div></td>
        <td><table>
            <tr>
              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/cashierbillsclassify/ea_getCashierBillsList.jspa?BType=10&level=organization'"></div><div class="center_a">现金申请</div></td>
              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/cashapplybills/ea_toCash.jspa?weibokuan=weibokuan&level=organization'"></div><div class="center_a">未拨现金申请</div></td>
              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/cashapplybills/ea_toCash.jspa?other=other&level=organization'"></div><div class="center_a">已拨现金申请</div></td>
              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/cashapplybills/ea_toCash.jspa?other=other&level=organization'"></div><div class="center_a">现金申转对账管理</div></td>
			  <td>
			  <!--现金使用管理-->
    <table >
      <tr>
        <td><div class="na_back_img_ks"></div><div class="center_a"><strong>现金使用管理</strong></div></td>
        <td>
              <table >
                <tr>
                  <td><div class="na_back_img_jt_xs"></div></td>
                  <td><table>
                    <tr>
                      <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getPurchaseList.jspa?type=00'"></div><div class="center_a">项目物品采购管理</div></td>
                      <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getinspectList.jspa'"></div><div class="center_a">验货管理</div></td>
                      <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_storage.jsp'"></div><div class="center_a">入库管理</div></td>
                      <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_out.jsp'"></div><div class="center_a">出库管理</div></td>
                      <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/warehousing/ea_getInventoryManagementList.jspa?'"></div><div class="center_a">库存管理</div></td>
                      <td ><div class="na_back_img" ></div><div class="center_a">报损管理</div></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><div class="na_back_img_jt_xx"></div></td>
                  <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><div class="na_back_img" ></div><div class="center_a">项目费用管理</span></td>
                      <td><div class="na_back_img" ></div><div class="center_a">项目费用<br/>审核管理</span></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
        </tr>
    </table>
			  </td>
         </tr>
        </table></td>
      </tr>
    </table>
		</td>
	</tr>
	<tr>
		<td>
		
		</td>
	</tr>
	
	
	
	<tr>
		<td>
		<!--资料库房-->
	<table >
      <tr>
        <td><div class="na_back_img_ks"></div><div class="center_a"><strong>资料库房</strong></div></td>
        <td><div class="na_back_img_jt_hx"></div></td>
        <td><table>
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
	</tr>
</table>
</body>
</html>
