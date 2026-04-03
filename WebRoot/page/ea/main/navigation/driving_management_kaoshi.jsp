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
		<title>教务管理科</title>
<link href="<%=basePath %>css/navegate.css" rel="stylesheet" type="text/css" /> 	
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>	 
</head>
	<body>
		<div>
			<table align="left">
				<tr>
					<td>
						<table height="90" border="0" align="left" cellpadding="0"
							cellspacing="0" class="table03" style="margin-top: 30px">
							<tr>
								<td width="98" rowspan="2" align="center">
									<a><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br /> 科一（理论）</a>
								</td>
								<td width="80" height="62" align="center">
									<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
											width="56" height="51" border="0" /> </a>
								</td>
								<td rowspan="2" align="center">
									<table border="0" cellspacing="0" cellpadding="0"
										height="132px">
										<tr>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=05&title=05&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 15px;Line-height:normal">
																约考管理
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM05
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=01&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
																考试统计
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM06
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=01&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 15px;Line-height:normal">
																合格率分析
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM07
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 11px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM08
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM9
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM10
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM11
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM12
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM13
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM14
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM15
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
																<font style="font-size: 15px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM01
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM02
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
																
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM03
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
																
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM04
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
									<a><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br /> 科二（桩考）</a>
								</td>
								<td width="80" height="62" align="center">
									<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
											width="56" height="51" border="0" /> </a>
								</td>
								<td rowspan="2" align="center">
									<table border="0" cellspacing="0" cellpadding="0"
										height="132px">
										<tr>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=04&title=08&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
																约考管理
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM03
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=02&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
																成绩统计
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM04
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=02&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
																合格率分析
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM05
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 11px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM06
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/schedule/ea_getScheduleList.jspa?'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 11px;Line-height:normal">
																
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM07
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 11px;Line-height:normal">
																
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM08
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM09
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM10
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM11
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM12
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM13
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM14
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM15
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 15px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM01
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM02
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
									<a><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />科二（场地）</a>
								</td>
								<td width="80" height="62" align="center">
									<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
											width="56" height="51" border="0" /> </a>
								</td>
								<td rowspan="2" align="center">
									<table border="0" cellspacing="0" cellpadding="0"
										height="132px">
										<tr>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=04&title=11&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															约考管理
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM03
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=03&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															成绩统计
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM04
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=03&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															合格率分析
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM05
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
															<font style="font-size: 11px;Line-height:normal">
															</font>
														</div>	
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM06
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM07
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 12px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM08
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM09
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM10
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM11
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM12
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM13
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM14
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM15
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 15px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM01
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
																<font style="font-size: 15px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM02
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
									<a><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br /> 科三（路考）</a>
								</td>
								<td width="80" height="62" align="center">
									<a href="#"><img src="<%=basePath%>images/jiatou_01.gif"
											width="56" height="51" border="0" /> </a>
								</td>
								<td rowspan="2" align="center">
									<table border="0" cellspacing="0" cellpadding="0"
										height="132px">
										<tr>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=04&title=14&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															约考管理
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM03
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=04&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															成绩统计
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM04
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=04&other=other'">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
														<div style="width: 15px; margin: auto;">
																合格率分析
																</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM05
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px">
																<font style="font-size: 11px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM06
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px">
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM07
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM08
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM09
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM10
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM11
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM12
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto; line-height: 17px"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM13
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM14
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer">
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;"></div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM15
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he01.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 15px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM01
														</td>
													</tr>
												</table>
											</td>
											<td width="40" align="center" valign="top"
												style="background-image: url(<%=basePath%>images/he02.gif); padding-top: 11px; cursor: pointer"
												>
												<table width="90%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="85px" align="center" valign="top">
															<div style="width: 15px; margin: auto;">
															<font style="font-size: 15px;Line-height:normal">
																</font>
															</div>
														</td>
													</tr>
													<tr>
														<td height="24" align="center">
															OM02
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
		</div>
	</body>
</html>
