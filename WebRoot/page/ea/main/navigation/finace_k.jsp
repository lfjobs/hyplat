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
		<title>进销存管理-</title>
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
		<table align="left">
			<tr>
				<td height="167">
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br />预算管理</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=00'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>

													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															采购物品
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=01'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															日常支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=02'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															出差支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=03'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															租用支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM04
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=04'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															投入支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM05
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=05'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															应收款
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM06
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=06'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															应付款
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM07
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=07'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															收入
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM08
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>

														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM09
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM10
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM11
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM12
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM13
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM14
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM15
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>

						<tr>
							<td width="80" align="center">
								&nbsp;

							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="167">
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br />确定预算</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=00'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>

													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															采购物品
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=01'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															日常支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=02'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															出差支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=03'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															租用支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM04
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=04'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															投入支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM05
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=05'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															应收款
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM06
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=06'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															应付款
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM07 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=07'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															收入
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM08 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>

														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM09 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM10 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM11 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM12 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM13 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM14 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM15 
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>

						<tr>
							<td width="80" align="center">
								&nbsp;

							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="167">
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br />市场调查</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getMarketResearchList.jspa?type=04'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>

													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															采购物品
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															日常支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=02'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															出差支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															租用支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM04 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															投入支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM05 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															应收款
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM06 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															应付款
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM07 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															收入
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM08 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>

														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM09 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM10 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM11 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM12 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM13 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM14 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM15 
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>

						<tr>
							<td width="80" align="center">
								&nbsp;

							</td>
						</tr>
					</table>
				</td>
			</tr>
			<%--<tr>
				<td height="167">
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br />招标</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>

													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															采购物品
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															日常支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=02'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															出差支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															租用支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM04 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															投入支出
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM05 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															应收款
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM06 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															应付款
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM07 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															收入
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM08 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>

														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM09 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM10 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM11 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM12 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM13 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM14 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM15 
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>

						<tr>
							<td width="80" align="center">
								&nbsp;

							</td>
						</tr>
					</table>
				</td>
			</tr>
			--%><tr>
				<td height="167">
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br />确定采购</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getPurchaseList.jspa?type=00'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>

													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															采购物品
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/costsheet/ea_getCostSheetList.jspa?type=02'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM04 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM05 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM06 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM07 
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM08 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>

														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM09 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM10 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM11 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM12 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM13 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM14 
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center"> 
														FM15 
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>

						<tr>
							<td width="80" align="center">
								&nbsp;

							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="167">
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br />收货验货</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getAccptList.jspa?type=00'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>

													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															收货管理
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
														国家验货
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getinspectList.jspa'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
														
															企业验货
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getDeviationList.jspa?'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															验货误差
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM04
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM05
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM06
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM07
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>

														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM08
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM09
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM10
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM11
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM12
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM13
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM14
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM15
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>

						<tr>
							<td width="80" align="center">
								&nbsp;

							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height="167">
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br />入库管理</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/storage/ea_getChooseWarehousingList.jspa'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>

													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															采购入库
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/storage/ea_getWareManagementList.jspa?'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															成品入库
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															异动入库
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM04
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM05
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM06
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM07
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>

														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM08
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM09
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM10
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM11
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM12
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM13
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM14
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM15
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>

						<tr>
							<td width="80" align="center">
								&nbsp;

							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br /> 出库管理</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/sales/ea_getWareManagementList.jspa?billStatus=07'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															销售出库
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>

											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/warehousing/ea_getWareManagementList.jspa?'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															异动出库
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM04
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM05
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM06
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;

														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM07
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM08
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM09
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM10
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM11
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM12
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM13
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM14
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM15
													</td>
												</tr>
											</table>
										</td>




									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table height="90" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table03" style="margin-top: 30px">
						<tr>
							<td width="98" rowspan="2" align="center">
								<a><img width="50" height="50" border="0"
										src="<%=basePath%>images/04.gif" /> <br /> 库存管理</a>
							</td>
							<td width="80" height="62" align="center">
								<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
										width="56" height="51" border="0" /> </a>
							</td>
							<td rowspan="2" align="center">
								<table border="0" cellspacing="0" cellpadding="0" height="132px">
									<tr>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/warehousing/ea_getInventoryManagementList.jspa?'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															库存管理
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM01
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/warning/ea_getWarningList.jspa'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															库存预警
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM02
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															报损报溢
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM03
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/stock/ea_getStocktakingList.jspa?type=05'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															盘库
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM04
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onclick="document.location.href='<%=basePath%>/ea/stock/ea_getShiftList.jspa?type=06'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															移库
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM05
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															库存误差
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM06
													</td>
												</tr>
											</table>
										</td>

										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer" onClick="document.location.href='<%=basePath%>ea/warehouse/ea_getListWareHouse.jspa?'">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															仓库管理办
														</div>	
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM07
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM08
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM09
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM10
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM11
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM12
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM13
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM14
													</td>
												</tr>
											</table>
										</td>
										<td width="40" align="center" valign="top"
											style="background-image: url(<%=basePath%>images/dl01.gif); padding-top: 11px; cursor: pointer">
											<table width="90%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="85px" align="center" valign="top">
														<p>
															&nbsp;
														</p>
													</td>
												</tr>
												<tr>
													<td height="24" align="center">
														FM15
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="80" align="center">
								<img src="<%=basePath%>images/jiatou_02.gif" width="56"
									height="51" border="0" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>