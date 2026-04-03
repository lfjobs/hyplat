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
<title>集团营销管理</title>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		
</head>
<body>
    <div>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table height="90" border="0" align="left" cellpadding="0"
										cellspacing="0" class="table03" style="margin-top: 5px">
										<tr>
											<td width="98" rowspan="2" align="center">
												<div class="na_back_img_ks"></div>
                      							<div class="center_a"><strong>集团营销管理</strong></div>
											</td>
											<td width="50" height="165" align="center">&nbsp;
												

											</td>
											<td rowspan="2" align="center">
												<table cellspacing="0" cellpadding="0" height="302"
													style="border: #003399; border-bottom-style: ">
													<tr>
														<td>
															<table height="550">
																<tr>
																	<td width="98" rowspan="2" align="center">
																		<div class="na_back_img_ks"></div>
                      													<div class="center_a"><strong>售前服务处</strong></div>
																	</td>
																	<td width="50" height="165" align="center">&nbsp;
																		

																	</td>
																	<td rowspan="2" align="center">
																		<table cellspacing="0" cellpadding="0" height="302"
																			style="border: #003399; border-bottom-style: ">
																			<tr>
																				<td>
																					<table>
																						<tr>

																							<td>
																								<table height="90" border="0" align="left"
																									cellpadding="0" cellspacing="0" class="table03"
																									style="margin-top: 5px">
																									<tr>
																										<td width="98" rowspan="2" align="center">
																											<div class="na_back_img_ks"></div>
                      																						<div class="center_a"><strong>市场调查管理</strong></div>
																										</td>
																										<td width="80" height="62" align="center">
																											<div class="na_back_img_jt_hx"></div>
																										</td>
																										<td rowspan="2" align="center">
																											<table cellspacing="0" cellpadding="0"
																												height="132px">
																												<tr>
																													<td width="96" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/marketsurvey/ea_getListMarketSurvey.jspa'"></div>
                             																							<div class="center_a"><span>市场调查</span></div>
																													</td>
																													<td width="96" align="center">
																														<div class="na_back_img"></div>
                             																							<div class="center_a"><span>地域调查</span></div>
																													</td>
																													<td width="96" align="center">
																														<div class="na_back_img"></div>
                             																							<div class="center_a"><span>价格调查</span></div>
																													</td>
																													<td width="96" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa'"></div>
                             																							<div class="center_a"><span>社会人力资源</span></div>
																													</td>
																													<td width="96" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/contactcompany/ea_getListContactCompany.jspa'"></div>
                             																							<div class="center_a"><span>社会单位管理</span></div>
																													</td>
																												</tr>
																											</table>
																										</td>
																									</tr>
																									<tr>
																										<td width="80" align="center">
																											<img src="<%=basePath%>images/jiatou_02.gif"
																												width="56" height="51" border="0" />
																										</td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																						<tr>
																							<td>
																								<table height="90" border="0" align="left"
																									cellpadding="0" cellspacing="0" class="table03"
																									style="margin-top: 5px">
																									<tr>
																										<td width="98" rowspan="2" align="center">
																											<div class="na_back_img_ks"></div>
                      																						<div class="center_a"><strong>包装产品管理</strong></div>
																										</td>
																										<td width="80" height="62" align="center">
																											<div class="na_back_img_jt_hx"></div>
																										</td>
																										<td rowspan="2" align="center">
																											<table border="0" cellspacing="0"
																												cellpadding="0" height="132px">
																												<tr>
																													<td width="160" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?'"></div>
                             																							<div class="center_a"><span>产品设计</span></div>
																													</td>
																													<td width="160" align="center">
																														<div class="na_back_img"></div>
                             																							<div class="center_a"><span>产品定位</span></div>
																													</td>
																													<td width="160" align="center">
																														<div class="na_back_img"></div>
                             																							<div class="center_a"><span>差评价格</span></div>
																													</td>
																												</tr>
																											</table>
																										</td>
																									</tr>
																									<tr>
																										<td width="80" align="center">
																											<img src="<%=basePath%>images/jiatou_02.gif"
																												width="56" height="51" border="0" />
																										</td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																						<tr>
																							<td>
																								<table height="90" border="0" align="left"
																									cellpadding="0" cellspacing="0" class="table03"
																									style="margin-top: 5px">
																									<tr>
																										<td width="98" rowspan="2" align="center">
																											<div class="na_back_img_ks"></div>
                      																						<div class="center_a"><strong>收集客户管理</strong></div>
																										</td>
																										<td width="80" height="62" align="center">
																											<div class="na_back_img_jt_hx"></div>
																										</td>
																										<td rowspan="2" align="center">
																											<table border="0" cellspacing="0"
																												cellpadding="0" height="132px">
																												<tr>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/stafftrack/ea_getStaffList.jspa'"></div>
                             																							<div class="center_a"><span>个人服务</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/companytrack/ea_getCompanyList.jspa'"></div>
                             																							<div class="center_a"><span>单位服务</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/custdata/ea_getCustomerDataList.jspa'"></div>
                             																							<div class="center_a"><span>服务单位客户</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/comregist/ea_getCompanyRegistList.jspa'"></div>
                             																							<div class="center_a"><span>企业注册</span></div>
																													</td>
																												</tr>
																											</table>
																										</td>
																									</tr>
																									<tr>
																										<td width="80" align="center">
																											<img src="<%=basePath%>images/jiatou_02.gif"
																												width="56" height="51" border="0" />
																										</td>
																									</tr>
																								</table>
																							</td>
																						</tr>
																						<tr>
																							<td>
																								<table height="90" border="0" align="left"
																									cellpadding="0" cellspacing="0" class="table03"
																									style="margin-top: 5px">
																									<tr>
																										<td width="98" rowspan="2" align="center">
																											<div class="na_back_img_ks"></div>
                      																						<div class="center_a"><strong>宣传产品管理</strong></div>
																										</td>
																										<td width="80" height="62" align="center">
																											<div class="na_back_img_jt_hx"></div>
																										</td>
																										<td rowspan="2" align="center">
																											<table border="0" cellspacing="0"
																												cellpadding="0" height="132px">
																												<tr>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/SersonnelSystem_a_network.jsp'"></div>
                             																							<div class="center_a"><span>网络推广</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img"></div>
                             																							<div class="center_a"><span>户外广告</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/SersonnelSystem_a_yuanchuan.jsp'"></div>
                             																							<div class="center_a"><span>媒体宣传</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img"></div>
                             																							<div class="center_a"><span>会议宣传</span></div>
																													</td>
																												</tr>
																											</table>
																										</td>
																									</tr>
																									<tr>
																										<td width="80" align="center">
																											<img src="<%=basePath%>images/jiatou_02.gif"
																												width="56" height="51" border="0" />
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
																	<td width="50" align="center">&nbsp;
																		
																	</td>
																</tr>
															</table>
															<table>
																<tr>
																	<td width="98" rowspan="2" align="center">
																		<div class="na_back_img_ks"></div>
                      													<div class="center_a"><strong>售中服务处</strong></div>
																	</td>
																	<td width="50" align="center" height="70">&nbsp;
																		

																	</td>
																	<td rowspan="2" align="center">
																		<table cellspacing="0" cellpadding="0"
																			style="border: #003399; border-bottom-style: ">
																			<tr>
																				<td>
																					<table>
																						<tr>
																							<td>
																								<table height="90" border="0" align="left"
																									cellpadding="0" cellspacing="0" class="table03"
																									style="margin-top: 5px">
																									<tr>
																									  <td width="98" rowspan="2" align="center">
																											<div class="na_back_img_ks"></div>
									                      												</td>
																										<td width="80" height="62" align="center">
																											<div class="na_back_img_jt_hx"></div>
																										</td>
																										<td rowspan="2" align="center">
																											<table border="0" cellspacing="0"
																												cellpadding="0" height="132px">
																												<tr>
																													<td width="120" align="center">
																														<div class="na_back_img"></div>
                             																							<div class="center_a"><span>成交客户科</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>page/ea/ccompany/scheduledproduct/scheduledproduct_main.jsp'"></div>
                             																							<div class="center_a"><span>预定产品</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/transactionservice/ea_getListTransactionService.jspa?'"></div>
                             																							<div class="center_a"><span>产品成交</span></div>
																													</td>
																													<td width="120" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/advisingclients/ea_getListAdvisingClients.jspa?'"></div>
                             																							<div class="center_a"><span>指导客户科</span></div>
																													</td>
																												</tr>
																											</table>
																										</td>
																									</tr>
																									<tr>
																										<td width="80" align="center">
																											<img src="<%=basePath%>images/jiatou_02.gif"
																												width="56" height="51" border="0" />
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
																	<td width="50" align="center">&nbsp;
																		
																	</td>
																</tr>
															</table>
															<table>
																<tr>
																	<td width="98" rowspan="2" align="center">
																		<div class="na_back_img_ks"></div>
                      													<div class="center_a"><strong>售后服务处</strong></div>
																	</td>
																	<td width="50" height="70" align="center">&nbsp;
																		

																	</td>
																	<td rowspan="2" align="center">
																		<table cellspacing="0" cellpadding="0"
																			style="border: #003399;">
																			<tr>
																				<td>
																					<table>
																						<tr>
																							<td>
																								<table border="0" align="left"
																									cellpadding="0" cellspacing="0" class="table03"
																									style="margin-top: 5px">
																									<tr>
																									  <td width="98" rowspan="2" align="center">
																									  <div class="na_back_img_ks"></div>
																									  </td>
																						  			  <td width="80" height="62" align="center">
																											<div class="na_back_img_jt_hx"></div>
																									  </td>																								
																										<td valign="top"><br /></td><td rowspan="2" align="center">
																											<table border="0" cellspacing="0"
																												cellpadding="0" height="132px">
																												<tr>
																													<td width="60" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/clientTracking/ea_getClientTrackingList.jspa'"></div>
                             																							<div class="center_a"><span>跟踪服务</span></div>
                             																						</td>
																													<td width="60" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/clientPblm/ea_getClientPblmList.jspa'"></div>
                             																							<div class="center_a"><span>问题解决</span></div>
                             																						</td>
																													<td width="60" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/clientIncrement/ea_getClientIncrementList.jspa'"></div>
                             																							<div class="center_a"><span>增值服务</span></div>
                             																						</td>
																													<td width="60" align="center">
																														<div class="na_back_img"></div>
                             																							<div class="center_a"><span>成交增值</span></div>
																													</td>																											
																													<td width="60" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=complaint'"></div>
                             																							<div class="center_a"><span>投诉处理</span></div>
                             																						</td>
																													<td width="60" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=InterDis'"></div>
                             																							<div class="center_a"><span>内部纠纷</span></div>
                             																						</td>
																													<td width="60" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=ExterDis'"></div>
                             																							<div class="center_a"><span>外部纠纷</span></div>
                             																						</td>
																													<td width="60" align="center">
																														<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/extralflow/ea_showExtralDocModule.jspa'"></div>
                             																							<div class="center_a"><span>网站投诉</span></div>
																													</td>																										
																												</tr>
																											</table>																										</td>
																									</tr>
																									<tr>
																										<td width="80" align="center">
																											<img src="<%=basePath%>images/jiatou_02.gif"
																												width="56" height="51" border="0" />																										</td>
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
																	<td width="50" height="81" align="center">&nbsp;																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td width="50" align="center">&nbsp;	
											</td>
										</tr>
									</table></td>
        </tr>
      </table>
    </div>
</body>
</html>