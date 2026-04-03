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
		<title>办公室-企业战略管理处</title>
	<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
	<body>
	<br/>
	<table style="width:100%;border:0;padding: 0;margin:0">
	<tr>
		<td>
	<table cellpadding="10">
      <tr>
        <td align="center">
        	<div class="na_back_img" ><img src="<%=basePath%>images/sytemicon/r10_c1.gif"></img></div>
          <div class="center_a"><strong> 企业战略规划管理</strong></div>
    	</td>
        <td><div class="na_back_img_jt_hx"></div></td>
   
              <td>
              <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=cg&d=<%=Math.random()%>'">
              <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img>
              </div>
			  <div class="center_a">公司规划管理</div>	
              </td>
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=dg&d=<%=Math.random()%>'">
              	<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
              	</div>
				<div class="center_a">部门规划管理</div>
              </td>
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=pg&d=<%=Math.random()%>'">
              	<img src="<%=basePath%>images/sytemicon/r11_c7.gif"></img>
              	</div>
				<div class="center_a">个人规划管理</div>
              </td> 
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=jg&d=<%=Math.random()%>'" >
              	<img src="<%=basePath%>images/sytemicon/r11_c9.gif"></img>
              	</div>
				<div class="center_a">职业规划管理</div>
              </td>
             <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/productdesign/ea_getListProductdesign.jspa?ghua=ghua&fiveClear=2'">
              	<img src="<%=basePath%>images/sytemicon/r11_c9.gif"></img>
              	</div>
				<div class="center_a">项目规划设计</div>
              </td>
           
      </tr>
    </table>
	</td></tr>
	<tr>
			<td>
				<!--项目预算招标管理-->
				<table cellpadding="10">
					<tr>
					<td align="center">
        	<div class="na_back_img" ><img src="<%=basePath%>images/sytemicon/r16_c1.gif"></img></div>
          <div class="center_a"><strong> 项目管理</strong></div>
    	</td>           
    	 <td><div class="na_back_img_jt_hx"></div></td>
						<td align="center">
							<div  class="na_back_img" onclick="document.location.href='<%=basePath%>page/ea/main/navigation/strategic_plan_budget_sx.jsp'"> <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img></div>
							<div class="center_a">项目预算计划</div>
						</td>
						 
						<td align="center" rowspan="2">
							<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=dc&fgtype=01&zhuangtai=01'"> <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img></div>
							<div class="center_a">项目授权分配</div>
						</td>
						 
						<td align="center" rowspan="2">
							<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=p&fgtype=02&zhuangtai=01'"> <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img></div>
							<div class="center_a">项目跟踪</div>
						</td>
						 
						<td align="center" rowspan="2">
							<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cofipunish/ea_findItem.jspa'"> <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img></div>
							<div class="center_a">项目质量考评</div>
						</td>
						
						<td align="center" rowspan="2">
							<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/payroll/ea_getThreePay.jspa'"> <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img></div>
							<div class="center_a">项目成果</div>
						</td>
						<%--<td><div class="na_back_img_jt_hx"></div></td>
					--%><!-- 	<td ><div class="na_back_img_jt_xs"></div></td>		 -->
							<td><table width="100%" >
      <%--<tr><td><table border="0"  width="100%" cellspacing="10" cellpadding="10">
            <tr>
            <td>
              <div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/promanage/ea_getProjectList.jspa'">
              <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img>
              </div>
			  <div class="center_a">项目管理</div>	
              </td>
              <td>
              <div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&summary=bm'">
              <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img>
              </div>
			  <div class="center_a">部门项目计划预算</div>	
              </td>
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=zbqsh'" >
              	<img src="<%=basePath%>images/sytemicon/r11_c9.gif"></img>
              	</div>
				<div class="center_a">未审核项目管理</div>
              </td>
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=zbqysh'" >
              	<img src="<%=basePath%>images/sytemicon/r11_c9.gif"></img>
              	</div>
				<div class="center_a">已审核项目管理</div>
              </td>
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=bjlb'">
              	<img src="<%=basePath%>images/sytemicon/r11_c7.gif"></img>
              	</div>
				<div class="center_a">未招标比价项目管理</div>
              </td> 
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/product/ea_getZbPriceedList.jspa'">
              	<img src="<%=basePath%>images/sytemicon/r11_c7.gif"></img>
              	</div>
				<div class="center_a">已比价物品管理</div>
              </td> 
             
            </tr>
            <tr>
              
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=xmmx'">
              	<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
              	</div>
				<div class="center_a">项目明细列表</div>
              </td>
              
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=srmx'">
              	<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
              	</div>
				<div class="center_a">项目收入明细列表</div>
              </td>
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=zcmx'">
              	<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
              	</div>
				<div class="center_a">项目支出明细列表</div>
              </td>
              
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=clmx'">
              	<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
              	</div>
				<div class="center_a">项目车辆明细列表</div>
              </td>
              <td>
              	&nbsp;
              </td>
              
             
            </tr>
        --%></table></td>
					<!-- </tr>
					<tr> -->
						<!-- <td >
							<div class="na_back_img_jt_xx"></div></td> -->
						<%--<td><table  class="d">
								<tr>
									<td >
										<div class="na_back_dh"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=03&type=08'">
											<img src="<%=basePath%>images/sytemicon/r17_c3.gif"></img>
											</div>
										<div class="center_a">项目已审批未审批</div></td>
									 <td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=04&type=08'"></div>
										<div class="center_a">招标物品费用录入</div></td> 
									<td >
										<div class="na_back_dh"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=01&type=01'">
											<img src="<%=basePath%>images/sytemicon/r17_c5.gif"></img>
											</div>
										<div class="center_a">项目物品费用未审批</div></td>
									<td >
										<div class="na_back_dh" onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=10&type=10'">
										<img src="<%=basePath%>images/sytemicon/r17_c7.gif"></img>
										</div>
										<div class="center_a">项目物品费用已审批</div></td>
								 	<td >
										<div class="na_back_img"></div>
										<div class="center_a">项目物品费用已审批</div></td>
										<td>
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=03&type=08'"></div>
										<div class="center_a">项目已审批未审批</div></td> 
									 <td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=08'"></div>
										<div class="center_a">产品清单表</div></td> 
								</tr>
							</table></td>
					--%></tr>
				</table></td>
		</tr>
	</table>
	</body>
</html>
