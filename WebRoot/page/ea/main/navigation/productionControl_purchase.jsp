<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产系统- 采购管理</title>
    <link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />

  </head>
  
  <body>
		<table>
	      <tr>
	        <td  align="center" rowspan="2">
	          <div style="margin-top: 20px;" class="na_back_img" onclick="javascript:;"></div>
	          <div class="center_a">生产采购管理</div>
	        </td>
	        <td><div class="na_back_img_jt_hx"></div></td>
	        <td><table border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td>
	                <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/researchManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
	                <div class="center_a">订单管理</div>
	                </td>
	              <td>
	              <div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/simulationManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
	                <div class="center_a">采购申请</div> 
	               </td>
	              <td> 
	                <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/certificateManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
	                <div class="center_a">采购审批</div>
	                </td>
	              <td> 
	                <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/trainingManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
	                <div class="center_a">发布招标</div>
	                </td>
	              <td> 
	              <%-- /page/ea/main/navigation/driving_management_ksgd.jsp?companyGroupLogo=${param.companyGroupLogo} --%>
	                <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/archivesManagement.jsp?'"></div>
	                <div class="center_a">招标选录</div>
	               </td>
	            </tr>
	        </table></td>
	      </tr>
	      <tr>
	      	<td><div class="na_back_img_jt_hx"></div></td>
	      	<td>
	     			<table border="0" cellspacing="0" cellpadding="0">
		            <tr>
		              <td>
		                <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/researchManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
		                <div class="center_a">确认订单</div>
		                </td>
		              <td>
		              <div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/simulationManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
		                <div class="center_a">采购单管理</div> 
		               </td>
		              <td> 
		                <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/sourcingsto/ea_inspectionList.jspa'"></div>
		                <div class="center_a">验货管理</div>
		                </td>
		              <td> 
		                <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/sourcingsto/ea_goodsReceiptList.jspa'"></div>
		                <div class="center_a">收货管理</div>
		                </td>
		              <td> 
		                <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/sourcingsto/ea_storageList.jspa'"></div>
		                <div class="center_a">采购入库</div>
		               </td>
		            </tr>
		        </table>
	      	</td>
	      </tr>
	    </table>
  </body>
</html>
