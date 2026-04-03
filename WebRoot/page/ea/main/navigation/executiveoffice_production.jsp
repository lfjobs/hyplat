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
<title>总经理室生产管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
	<div>
		<table width="100%" >
			<tr>
				<td><table>
				<tr>
							<td><table>
							
							
									<tr>
										<td rowspan="2">
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>生产合同管理</strong>
											</div></td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td>
											<table>
												<tr>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">生产合同流转</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">生产合同查询</span>
														</div></td>
													
												</tr>
											</table>
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td><table>
							
							
									<tr>
										<td rowspan="2">
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>集团人事管理</strong>
											</div></td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td>
											<table>
												<tr>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团人事机构</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团招聘管理</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团在职员工管理</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团离职员工管理</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团社会人力资源管理</span>
														</div></td>
												</tr>
											</table>
										</td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td><table>
									<tr>
										<td>
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>集团办公室管理</strong>
											</div>
										</td>
										<td><div class="na_back_img_jt_hx"></div></td>
										<td><table>
												<tr>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团规划管理</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团行政管理</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团信息管理</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团后勤管理</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团督查管理</span>
														</div></td>
												</tr>
											</table></td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td><table>
									<tr>
										<td><div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>集团财务管理</strong>
											</div>
										</td>
										<td><div class="na_back_img_jt_hx"></div></td>
										<td><table>
												<tr>
													<td width="130">
														<div class="na_back_img"
															onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_l.jsp'"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团财务管理</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团税务管理</span>
														</div></td>
												</tr>
											</table></td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td><table>
									<tr>
										<td>
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>集团教务管理</strong>
											</div>
										</td>
										<td><div class="na_back_img_jt_hx"></div></td>
										<td><table>
												<tr>
													<td width="130">
														<div class="na_back_img"
															onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/teachingAffairsDepartment-a.jsp'"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团教务(生产)一</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团教务(生产)二项</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团教务(生产)三项</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团教务(生产)四项</span>
														</div></td>
													<td width="130">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span style="font-weight: normal; font-size: 12px;">集团教务(生产)五项</span>
														</div></td>
												</tr>
											</table></td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td>
								<table>
									<tr>
										<td>
											<table>
												<tr>
													<td>
														<table>
															<tr>
																<td rowspan="2">
																	<div class="na_back_img_ks"></div>
																	<div class="center_a">
																		<strong>集团营销管理</strong>
																	</div>
																</td>
																<td width="50">&nbsp;</td>
																<td rowspan="2">
																	<table>
																		<tr>
																			<td>
																				<table>
																					<tr>
																						<td rowspan="2">
																							<div class="na_back_img_ks"></div>
																							<div class="center_a">
																								<strong>集团售前服</strong>
																							</div>
																						</td>
																						<td width="50">&nbsp;</td>
																						<td rowspan="2">
																							<table>
																								<tr>
																									<td>
																										<table>
																											<tr>

																												<td>
																													<table>
																														<tr>
																															<td>
																																<div class="na_back_img_ks"></div>
																																<div class="center_a">
																																	<strong>集团市场调查管理</strong>
																																</div>
																															</td>
																															<td><div class="na_back_img_jt_hx"></div>
																															</td>
																															<td>
																																<table cellspacing="0" cellpadding="0">
																																	<tr>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团市场调查</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团地域调查</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团价格调查</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团社会人力资源</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团社会单位管理</span>
																																			</div></td>
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
																															<td>
																																<div class="na_back_img_ks"></div>
																																<div class="center_a">
																																	<strong>集团包装产品管理</strong>
																																</div>
																															</td>
																															<td><div class="na_back_img_jt_hx"></div>
																															</td>
																															<td>
																																<table border="0" cellspacing="0"
																																	cellpadding="0">
																																	<tr>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团产品设计</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团产品定位</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团差评价格</span>
																																			</div></td>
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
																															<td>
																																<div class="na_back_img_ks"></div>
																																<div class="center_a">
																																	<strong>集团宣传产品管理</strong>
																																</div>
																															</td>
																															<td><div class="na_back_img_jt_hx"></div>
																															</td>
																															<td>
																																<table border="0" cellspacing="0"
																																	cellpadding="0">
																																	<tr>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团网络推广</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团户外广告</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团媒体宣传</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团会议宣传</span>
																																			</div></td>
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
																															<td>
																																<div class="na_back_img_ks"></div>
																																<div class="center_a">
																																	<strong>集团收集客户管理</strong>
																																</div>
																															</td>
																															<td><div class="na_back_img_jt_hx"></div>
																															</td>
																															<td>
																																<table border="0" cellspacing="0"
																																	cellpadding="0">
																																	<tr>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团个人服务</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团单位服务</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团服务单位客户</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团企业注册</span>
																																			</div></td>
																																	</tr>
																																</table>
																															</td>
																														</tr>
																													</table>
																												</td>
																											</tr>
																										</table>
																									</td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																					<tr>
																						<td width="50">&nbsp;</td>
																					</tr>
																				</table>
																				<table>
																					<tr>
																						<td>
																							<div class="na_back_img_ks"></div>
																							<div class="center_a">
																								<strong>集团售中服务</strong>
																							</div>
																						</td>
																						<td width="50">&nbsp;</td>
																						<td>
																							<table>
																								<tr>
																									<td>
																										<table>
																											<tr>
																												<td>
																													<table>
																														<tr>
																															<td>
																																<div class="na_back_img_ks"></div>
																																<div class="center_a">
																																	<strong>集团成交产品服务</strong>
																																</div>
																															</td>
																															<td><div class="na_back_img_jt_hx"></div>
																															</td>
																															<td>
																																<table>
																																	<tr>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团成交客户</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团预定产品</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团产品成交</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团指导客户科</span>
																																			</div></td>
																																	</tr>
																																</table>
																															</td>
																														</tr>
																													</table>
																												</td>
																											</tr>
																										</table>
																									</td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																				</table>
																				<table>
																					<tr>
																						<td rowspan="2">
																							<div class="na_back_img_ks"></div>
																							<div class="center_a">
																								<strong>集团售后服务</strong>
																							</div>
																						</td>
																						<td width="50">&nbsp;</td>
																						<td rowspan="2">
																							<table>
																								<tr>
																									<td>
																										<table>
																											<tr>
																												<td>
																													<table>
																														<tr>
																															<td rowspan="2">
																																<div class="na_back_img_ks"></div>
																																<div class="center_a">
																																	<strong>集团跟踪产品客户服务</strong>
																																</div>
																															</td>
																															<td><div class="na_back_img_jt_xs"></div>
																															</td>
																															<td>
																																<table>
																																	<tr>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团跟踪服务</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团问题解决</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团增值服务</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团成交增值</span>
																																			</div></td>
																																	</tr>
																																</table>
																															</td>
																														</tr>
																														<tr>
																															<td><div class="na_back_img_jt_xx"></div>
																															</td>
																															<td>
																																<table>
																																	<tr>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团投诉处理</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团内部纠纷</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团外部纠纷</span>
																																			</div></td>
																																		<td width="110">
																																			<div class="na_back_img"></div>
																																			<div class="center_a">
																																				<span
																																					style="font-weight: normal; font-size: 12px;">集团网站投诉</span>
																																			</div></td>
																																	</tr>
																																</table></td>

																														</tr>
																													</table>
																												</td>
																											</tr>
																										</table>
																									</td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																					<tr>
																						<td width="50">&nbsp;</td>
																					</tr>
																				</table>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td width="50">&nbsp;</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>
</body>
</html>