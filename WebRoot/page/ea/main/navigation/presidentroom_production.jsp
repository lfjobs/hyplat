<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>董事长室生产管理</title>
<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<style>

.colors {
	border-bottom: 1px dashed #FF0000;
}

.center_a{
 
 margin-right:30px;

}
</style>
</head>
<body>
	<div>
		<table >
			<tr>
				<td><table  cellpadding="30">
					<tr>
							<td class="colors"><table>
									<tr>
										<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 生产合同管理</strong></div></td>
										<td><div class="na_back_img_jt_hx"></div></td>
										<td><table >
												<tr>
													 <td>
													 <div class="na_back_img" ></div><div class="center_a">生产合同流转</div>
													</td>
													 <td><div class="na_back_img" ></div><div class="center_a">生产合同查询</div>
													</td>
												
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table>
									<tr>
										<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团股东会管理</strong></div></td>
										<td><div class="na_back_img_jt_hx"></div></td>
										<td><table >
												<tr>
													 <td>
													 <div class="na_back_img" ></div><div class="center_a">集团股东会{人事}管理</div>
													</td>
													 <td><div class="na_back_img" ></div><div class="center_a">集团股东会(办公室)管理</div>
													</td>
													 <td><div class="na_back_img" ></div><div class="center_a">集团股东会(财务)管理</div>
													</td>
													 <td><div class="na_back_img" ></div><div class="center_a">集团股东会(生产)管理</div>
													</td>
													 <td><div class="na_back_img" ></div><div class="center_a">集团股东会(营销)管理</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table >
									<tr>
										<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团董事会管理</strong></div></td>
										<td><div class="na_back_img_jt_hx"></div></td>
										<td><table >
												<tr>
													<td><div class="na_back_img" ></div><div class="center_a">集团董事会{人事}管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团董事会(办公室)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团董事会(财务)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团董事会(生产)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团董事会(营销)管理</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table >
									<tr>
										<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团监事会管理</strong></div></td>
									<td><div class="na_back_img_jt_hx"></div></td>
										<td><table >
												<tr>

													<td><div class="na_back_img" ></div><div class="center_a">集团监事会(人事)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团监事会(办公室)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团监事会(财务)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团监事会(生产)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团监事会(营销)管理</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table >
									<tr>
										<td><div class="na_back_img_ks"></div><div class="center_a"><strong>集团职代会管理</strong></div></td>
    									<td><div class="na_back_img_jt_hx"></div></td>
										<td><table >
												<tr>

													<td><div class="na_back_img" ></div><div class="center_a">集团职代会(人事)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团职代会(办公室)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团职代会(财务)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团职代会(生产)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团职代会(营销)管理</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table >
									<tr>
									  <td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团常委会管理</strong></div></td>
    									<td><div class="na_back_img_jt_hx"></div></td>
										<td><table >
												<tr>

													<td><div class="na_back_img" ></div><div class="center_a">集团常委会(人事)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团常委会(办公室)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团常委会(财务)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团常委会(生产)管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团常委会(营销)管理</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table >
									<tr>
										  <td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团人事管理</strong></div></td>
    										<td><div class="na_back_img_jt_hx"></div></td><td>
											<table >
												<tr>
													<td><div class="na_back_img" ></div><div class="center_a">集团人事机构</div>
													</td>
												<td><div class="na_back_img" ></div><div class="center_a">集团招聘管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团在职员工管理</div>

													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团离职员工管理</div>

													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团社会人力资源管理</div>

													</td>
												</tr>
											</table></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table>
									<tr>
										<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团办公室管理</strong></div></td>
    <td><div class="na_back_img_jt_hx"></div></td>
										<td><table >
												<tr>

													<td><div class="na_back_img" ></div><div class="center_a">集团规划管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团行政管理</div>
													</td>
												<td><div class="na_back_img" ></div><div class="center_a">集团信息管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团后勤管理</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团督查管理</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table >
									<tr>
										<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团财务管理</strong></div></td>
    <td><div class="na_back_img_jt_hx"></div></td>
										<td><table>
												<tr>
													<td><div class="na_back_img" 
														onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_l.jsp'" ></div><div class="center_a">集团财务管理</div>

													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团税务管理</div>

													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="colors"><table >
									<tr>
										<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团教务管理</strong></div></td>
    <td><div class="na_back_img_jt_hx"></div></td>
										<td><table >
												<tr>
													<td><div class="na_back_img"
														onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/teachingAffairsDepartment-a.jsp'">
									 				</div><div class="center_a">集团教务(生产)一项</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团教务(生产)二项</div>
													</td>
													<td><div class="na_back_img" ></div><div class="center_a">集团教务(生产)三项</div>
													</td>

													<td><div class="na_back_img" ></div><div class="center_a">集团教务(生产)四项</div>
													</td>
												<td><div class="na_back_img" ></div><div class="center_a">集团教务(生产)五项</div>
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
								<table >
									<tr>
										<td>
											<table>
												<tr>
													<td>
														<table >
															<tr>
																<td rowspan="2"><div class="na_back_img_ks"></div><div class="center_a"><strong>
																	集团营销管理</strong></div></td>
																<td >&nbsp;</td>
																<td rowspan="2" >
																	<table >
																		<tr>
																			<td>
																				<table class="colors">
																					<tr>
																						<td  rowspan="2"><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团售前服务</strong></div></td>
																						<td >&nbsp;
																						</td>
																						<td rowspan="2" >
																							<table>
																								<tr>
																									<td >
																										<table>
																											<tr>

																												<td>
																													<table >
																														<tr>
																															<td><div class="na_back_img_ks"></div><div class="center_a"><strong>
																																	集团市场调查管理</strong></div></td>
    																																<td><div class="na_back_img_jt_hx"></div></td>
																															<td>
																																<table >
																																	<tr>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团市场调查</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团地域调查</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团价格调查</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团社会人力资源</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团社会单位管理</div>
																																		</td>
																																	</tr>
																																</table></td>
																														</tr>
																													</table></td>
																											</tr>
																											<tr>
																												<td>
																													<table >
																														<tr>
																															<td><div class="na_back_img_ks"></div><div class="center_a"><strong>
																																集团包装产品管理</strong></div></td>
    																														<td><div class="na_back_img_jt_hx"></div></td>
    
																															<td>
																																<table >
																																	<tr>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团产品设计</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团产品定位</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团差评价格</div>
																																		</td>
																																	</tr>
																																</table></td>
																														</tr>
																													</table></td>
																											</tr>
																											<tr>
																												<td>
																													<table >
																														<tr>
																															<td><div class="na_back_img_ks"></div><div class="center_a"><strong>
																																	集团宣传产品管理</strong></div></td>
																															 <td><div class="na_back_img_jt_hx"></div></td>
																															<td>
																																<table >
																																	<tr>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团网络推广</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团户外广告</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团媒体宣传</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团会议宣传</div>
																																		</td>
																																	</tr>
																																</table></td>
																														</tr>
																													</table></td>
																											</tr>
																											<tr>
																												<td>
																													<table >
																														<tr>
																															<td><div class="na_back_img_ks"></div><div class="center_a"><strong>
																																	集团收集客户管理</strong></div></td>
																															 <td><div class="na_back_img_jt_hx"></div></td>
																															<td>
																																<table >
																																	<tr>
																																	
																																			<!-- /ea/collectpersonal/ea_getTracklist.jspa -->
																																			<td><div class="na_back_img" ></div><div class="center_a">集团个人服务</div>
																																		</td>
																																	
																																			<!-- /ea/collectunit/ea_getTracklist.jspa -->
																																			<td><div class="na_back_img" ></div><div class="center_a">集团单位服务</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团服务单位客户</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团企业注册</div>
																																		</td>
																																	</tr>
																																</table></td>
																														</tr>
																													</table></td>
																											</tr>
																										</table></td>
																								</tr>
																							</table></td>
																					</tr>
																					<tr>
																						<td >&nbsp;
																						</td>
																					</tr>
																				</table>
																				<table class="colors">
																					<tr>
																						<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团售中服务</strong></div></td>
																						<td >&nbsp;
																						</td>
																						<td rowspan="2" >
																							<table >
																								<tr>
																									<td>
																										<table>
																											<tr>
																												<td>
																													<table >
																														<tr>
																															<td><div class="na_back_img_ks"></div><div class="center_a"><strong>
																																	集团成交产品服务</strong></div></td>
																														   <td><div class="na_back_img_jt_hx"></div></td>
																															<td>
																																<table >
																																	<tr>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团成交客户</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团预定产品</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团产品成交</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团指导客户科</div>
																																		</td>
																																		<td>
																																		</td>
																																	</tr>
																																</table></td>
																														</tr>
																													</table></td>
																											</tr>
																										</table></td>
																								</tr>
																							</table></td>
																					</tr>
																					<tr>
																						<td >&nbsp;
																						</td>
																					</tr>
																				</table>
																				<table>
																					<tr>
																						<td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团售后服务</strong></div></td>
																						<td >&nbsp;
																						</td>
																						<td rowspan="2">
																							<table style="border: #003399;">
																								<tr>
																									<td>
																										<table>
																											<tr>
																												<td>
																													<table >
																														<tr>
																															<td><div class="na_back_img_ks"></div><div class="center_a"><strong>
																																	集团跟踪产品客户服务</strong></div></td>
																															<td><div class="na_back_img_jt_hx"></div></td>
																															<td>
																																<table >
																																	<tr>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团跟踪服务</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团问题解决</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团增值服务</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团成交增值</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团投诉处理</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团内部纠纷</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团外部纠纷</div>
																																		</td>
																																		<td><div class="na_back_img" ></div><div class="center_a">集团网站投诉</div>
																																		</td>
																																	</tr>
																																</table></td>
																														</tr>
																													</table></td>
																											</tr>
																										</table></td>
																								</tr>
																							</table></td>
																					</tr>
																					<tr>
																						<td >&nbsp;
																						</td>
																					</tr>
																				</table></td>
																		</tr>
																	</table></td>
															</tr>
															<tr>
																<td >&nbsp;</td>
															</tr>
														</table></td>
												</tr>
											</table></td>
									</tr>
								</table></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>