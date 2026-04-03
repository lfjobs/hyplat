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
		<title>公司企业管理-</title>
			<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
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
	<br />
		<table
			style="width: 100%; border: 0; text-align: left; padding: 0; margin: 0"
			class="table03">
			<tr>
				<td>
					<table>
						<tr>
							<td width="120" align="center">
								<div class="na_back_img_ks"></div>
            					<div class="center_a"><strong>会计管理</strong></div>
							</td>
							<td width="55" align="center">
								<div class="na_back_img_jt_hx"></div>
							</td>
							
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td width="120" align="center">
								<div class="na_back_img_ks"></div>
            					<div class="center_a"><strong>出纳管理</strong></div>
							</td>
							<td width="55" align="center">
								<div class="na_back_img_jt_hx"></div>
							</td>
							<td>
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="110" align="center">
											<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cashiersummary/ea_getCashierList.jspa'"></div>
                 							<div class="center_a"><span>出纳单据</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/goodscashier/ea_getCashierList.jspa'"></div>
                 							<div class="center_a"><span>物品明细</span></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td width="120" align="center">
								<div class="na_back_img_ks"></div>
            					<div class="center_a"><strong>资产管理</strong></div>
							</td>
							<td width="55" align="center">
								<div class="na_back_img_jt_hx"></div>
							</td>
							
						</tr>
					</table>
				</td>
				</tr>
		</table>
	</body>
</html>
