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
<title>项目预算招标录入</title>
<!-- <link href="<%=basePath%>css/navegate.css" rel="stylesheet" type="text/css" /> -->
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
	<br />
	<table style="width:100%;border:0; padding: 0;margin:0">
		
		<tr>
			<td>
				<!--资金申请管理-->
				<table>
					<tr>
						<td >
							&nbsp;</td>
							<td><div class="na_back_img_jt_xs"></div></td>
						<td><table border="0" cellspacing="10" cellpadding="5" >
								<tr>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_earnBudgetList.jspa?type=00&sztype='" align="center"></div>
										<div class="center_a">收支预算单据管理</div></td>
									 <td>
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_getEarnBudgetDetails.jspa?type=00&sztype='" align="center"></div>
										<div class="center_a">收支预算明细管理</div></td> 
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_earnBudgetList.jspa?type=01&sztype='" align="center"></div>
										<div class="center_a">收支预算调整单据</div></td>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_getEarnBudgetDetails.jspa?type=01&sztype='" align="center"></div>
										<div class="center_a">收支预算调整明细</div></td>
									
								</tr>
							</table></td>
					</tr>
					<tr>
						<td >
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>项目预算招标录入</strong>
							</div></td>
						<td >
							<div class="na_back_img_jt_hx"></div></td>
						<td><table border="0" cellspacing="10" cellpadding="5" >
								<tr>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_earnBudgetList.jspa?type=00&sztype=s'" align="center"></div>
										<div class="center_a">收入预算单据管理</div></td>
									 
									<td>
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_getEarnBudgetDetails.jspa?type=00&sztype=s'" align="center"></div>
										<div class="center_a">收入预算明细管理</div></td>
									<td>
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_earnBudgetList.jspa?type=01&sztype=s'" align="center"></div>
										<div class="center_a">收入预算调整单据</div></td>
									<td>
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_getEarnBudgetDetails.jspa?type=01&sztype=s'" align="center"></div>
										<div class="center_a">收入预算调整明细</div></td>
									
								</tr>
							</table></td>
					</tr>
					<tr>
						<td >
							&nbsp;</td>
						<td><div class="na_back_img_jt_xx"></div></td>
						<td><table border="0" cellspacing="10" cellpadding="5" >
								<tr>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_earnBudgetList.jspa?type=00&sztype=z'" align="center"></div>
										<div class="center_a">支出预算单据管理</div></td>
									 
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_getEarnBudgetDetails.jspa?type=00&sztype=z'" align="center"></div>
										<div class="center_a">支出预算明细管理</div></td>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_earnBudgetList.jspa?type=01&sztype=z'" align="center"></div>
										<div class="center_a">支出预算调整单据</div></td>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_getEarnBudgetDetails.jspa?type=01&sztype=z'" align="center"></div>
										<div class="center_a">支出预算调整明细</div></td>
									
								</tr>
							</table></td>
					</tr>
					<tr>
						<td >
							&nbsp;</td>
						<td><div class="na_back_img_jt_xx"></div></td>
						<td><table border="0" cellspacing="0" cellpadding="0" >
								<tr>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_earnBudgetList.jspa?type=00&sztype=s'" align="center"></div>
										<div class="center_a">收入预算</div></td>
									 <td>
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=00&type=00'"></div>
										<div class="center_a">支出预算</div></td> 
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/earnbudget/ea_earnBudgetList.jspa?type=01'" align="center"></div>
										<div class="center_a">收入预算调整</div></td>
									
								</tr>
							</table></td>
					</tr>
				</table>
			</td>
		</tr>		
		
	</table>
</body>
</html>
