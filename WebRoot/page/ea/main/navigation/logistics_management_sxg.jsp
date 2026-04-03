<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>办公室后勤-树形集团</title>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>

<script type="text/javascript">
   
             var basePath='<%=basePath%>';

	$(document).ready(function() {

		 $("#navigation").treeview({
			  persist: "location",
			  collapsed: true,
			  unique: true
			 });


	});
</script>
<style type="text/css">
#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}

.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
}

#navigation a:hover{

 color:red;
 
}
</style>


</head>
<body>
	<div class="main_main">
		<table width="100%" cellspacing="0" cellpadding="0" "border="2">
			<tr>
				<td id="qh_sw" style="width: 20%;" valign="top">
					<div class="qh_gg_nav">
						&nbsp; <span id="frametitle">后勤管理集团汇总</span>

					</div> <!--左边的树 -->
					<div style="overflow:auto;">
						<ul id="navigation" style="width: 240px;margin-left:15px;" class="filetree">
							<%--<li ><span class="folder">后勤管理</span>

								--%><ul>
									<li><span class="folder">接待管理</span>
										<ul>
											<li><span class="folder">往来个人管理</span>

												<ul>
													<li><a target="mainframe"
														href="<%=basePath%>ea/contactuser/ea_getListContactUser.jspa"><span
															class="file">个人接待信息管理</span> </a></li>
													<li><a target="mainframe"
														href="<%=basePath%>/ea/publicreceipts/ea_getListPublicreceipts.jspa?labelTag=00"><span
															class="file">个人接待信息报表</span> </a></li>

												</ul></li>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/contactConnection/ea_getListContactConnection.jspa"><span
													class="file">往来单位管理</span> </a></li>

										</ul></li>
									<li><a target="mainframe"
												href="javascript:"><span class="file">场地管理</span></a></li>

									<li><span class="file">资产库管</span></li>
									<li><span class="folder">安全管理</span>
										<ul>
											<li><span class="folder">安全管理</span>
												<ul>
													<li><a target="mainframe"
														href="<%=basePath%>/ea/keyManage/ea_getKeyManageList.jspa"><span
															class="file">钥匙管理</span> </a>
													</li>
													<li><a target="mainframe"
														href="<%=basePath%>page/ea/main/office_ea/safe/safekinds_manager.jsp"><span
															class="file">安全类别</span> </a>
													</li>
													<li><a target="mainframe"
														href="<%=basePath%>ea/safeinspect/ea_getSafeInspectList.jspa"><span
															class="file">安全单据</span> </a>
													</li>

												</ul>
											</li>
											<li><span class="folder">安全防范管理</span>
												<ul>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">火灾预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">防盗管理</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">防霉管理</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">防毒管理</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">污染预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">雪灾预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">冰雹预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">冻害预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">垮塌预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">地震预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">洪涝预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">防泥石流</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">虫害预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">疾病预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">安全用电</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">雷雨预防</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">防龙卷风</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">食品安全</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">车辆设备</span> </a>
													</li>
												</ul>
											</li>

										</ul>
									</li>
									<li><span class="folder">设备管理</span>
										<ul>
											<li><span class="folder">车管设备</span>
												<ul>
													<li><span class="folder">汽车</span>
														<ul>
														<li><span class="folder">车辆管理办</span>
															<ul>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/car/ea_getCarInformationList.jspa"><span
																		class="file">完善车辆信息</span> </a></li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carbaseinfo/ea_getCarBaseInfoList.jspa"><span
																		class="file">车辆基本信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/cardmanage/ea_getCarGooutRecord.jspa"><span
																		class="file">车辆门禁</span> </a>
																</li>
																<li><span class="folder">安全巡检</span>
																	<ul>
																		<li><a target="mainframe"
																			href="<%=basePath%>ea/safetycheck/ea_point.jspa"><span
																				class="file">检查点</span> </a>
																		</li>
																		<li><a target="mainframe"
																			href="<%=basePath%>ea/safetycheck/ea_pointitem.jspa"><span
																				class="file">检查项</span> </a>
																		</li>
																		<li><a target="mainframe"
																			href="<%=basePath%>ea/safetycheck/ea_plan.jspa"><span
																				class="file">巡检计划</span> </a>
																		</li>
																		<li><a target="mainframe"
																			href="<%=basePath%>ea/safetycheck/ea_task.jspa"><span
																				class="file">巡检任务</span> </a>
																		</li>
																		<li><a target="mainframe"
																			href="<%=basePath%>ea/safetycheck/ea_results.jspa"><span
																				class="file">巡检结果</span> </a>
																		</li>
																	</ul>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>ea/carnum/ea_getListCarByCompanyID.jspa?type=1"><span
																		class="file">车牌号维护</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carinvoice/ea_getCarInvoiceList.jspa?type=1"><span
																		class="file">购车发票</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carpurchasetax/ea_getCarPurchaseTaxList.jspa?type=1"><span
																		class="file">购置税发票</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carinsurance/ea_getCarInsuranceList.jspa?type=1"><span
																		class="file">购置保险信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carareview/ea_getCarAReviewList.jspa?type=1"><span
																		class="file">购置年检信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carcng/ea_getCarCNGList.jspa?type=1"><span
																		class="file">车辆CNG信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carsafeinformation/ea_getCarsafeinformationList.jspa?type=1"><span
																		class="file">车辆安全信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carassetcinformation/ea_getCaraffetcinformationList.jspa?type=1"><span
																		class="file">车辆资产信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/employcondition/ea_getemployconditionList.jspa?type=1"><span
																		class="file">车辆使用信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carmaintain/ea_getListCarMaintain.jspa?type=1"><span
																		class="file">车辆维护信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/certificateatable/ea_getCertificateaTableList.jspa?type=1"><span
																		class="file">相关证件子集</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/motorcar/ea_getMotorcarList.jspa?type=1"><span
																		class="file">机动车行驶证</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carroad/ea_getCarRoadList.jspa?type=1"><span
																		class="file">道路运输证</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/bottle/ea_getBottleList.jspa?type=1"><span
																		class="file">车用瓶使用证</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carpurchase/ea_getPurchaseList.jspa?type=1"><span
																		class="file">车辆购置税证</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carviolate/ea_getCarViolateList.jspa?type=1"><span
																		class="file">车辆违章信息</span> </a>
																</li>
																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carquasi/ea_getCarseatList.jspa?type=1"><span
																		class="file">车辆准载座位</span> </a>
																</li>

																<li><a target="mainframe"
																	href="<%=basePath%>/ea/carassectinformation/ea_getSafetyHealthList.jspa?type=1"><span
																		class="file">安全卫生检查</span> </a>
																</li>
																

															</ul>
														</li>

														<li><span class="folder">接送预约接送到达管理</span>
																<ul>
																	<li><a target="mainframe"
																		href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
																			class="file">接送预约信息管理 </span> </a></li>
																	<li><a target="mainframe"
																		href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
																			class="file">接送预约信息报表管理 </span> </a></li>
																	<li><a target="mainframe"
																		href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
																			class="file">接送基本信息管理 </span> </a></li>
																	<li><a target="mainframe"
																		href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
																			class="file">接送信息报表管理 </span> </a></li>
																	<li><a target="mainframe"
																		href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
																			class="file">接送到达基本信息管理</span> </a></li>
																	<li><a target="mainframe"
																		href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
																			class="file">接送到达信息报表管理</span> </a></li>

																</ul></li>
														</ul></li>
												</ul>
											</li>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa"><span
													class="folder">工程机械</span> </a>
												<ul>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">挖掘机</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">装载机</span> </a>
													</li>

												</ul></li>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa"><span
													class="folder">办公室设备</span> </a>
													<ul>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">电脑</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">打印机</span> </a>
													</li>
													<li><a target="mainframe"
														href="javascript:"><span
															class="file">通讯设备</span> </a>
													</li>

												</ul>
													
													
													</li>
											<li><a target="mainframe"
												href="javascript:"><span
													class="file">弱电设备</span> </a></li>

										</ul>
									</li>
									<li><a target="mainframe"
												href="<%=basePath%>/ea/logistics/ea_getLogisticsList.jspa"><span class="file">物流管理</span></a></li>
									<li><a target="mainframe"
												href="<%=basePath%>/ea/water/ea_getListForPage.jspa"><span class="file">用水管理</span></a></li>

									<li><a target="mainframe"
												href="<%=basePath%>/ea/electricity/ea_getListForPage.jspa"><span class="file">用电管理</span></a></li>

									<li><a target="mainframe"
												href="<%=basePath%>/ea/onduty/ea_getListForPage.jspa"><span class="file">值班管理</span></a></li>

									<li><a target="mainframe"
												href="<%=basePath%>/ea/afforest/ea_getListForPage.jspa"><span class="file">绿化管理</span></a></li>
									<li><a target="mainframe"
												href="javascript:"><span class="file">基建管理</span></a></li>

									<li><a target="mainframe"
												href="javascript:"><span class="file">食堂管理</span></a></li>

									<li><span class="folder">住宿管理</span>
									
									<ul>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/accommod/ea_getAllList.jspa"><span
													class="file">单位住宿</span> </a></li>
											<li><a target="mainframe"
												href="javascript:"><span
													class="file">单位住宿报表</span> </a></li>
											<li><a target="mainframe"
												href="javascript:"><span
													class="file">住宿分配</span> </a></li>
											<li><a target="mainframe"
												href="javascript:"><span
													class="file">住宿分配报表</span> </a></li>

										</ul></li>

									<li><span class="folder">票务管理</span>
										<ul>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/piaowuManager/ea_getListpiaowu.jspa?aa=zz"><span
													class="file">票务管理</span> </a></li>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/piaowuManager/ea_getListpiaowu.jspa?aa=bb"><span
													class="file">票务报表管理</span> </a></li>

										</ul></li>

									<li><span class="folder">资产库存管理</span>
										<ul>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/purchase/ea_getPurchaseList.jspa?type=00"><span
													class="file">费用采购明细账</span> </a>
											</li>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/purchase/ea_getinspectList.jspa"><span
													class="file">验货管理</span> </a>
											</li>
											<li><span
													class="folder">入库管理</span> 
												<ul>

													<li><a target="mainframe"
														href="<%=basePath%>/ea/storage/ea_getChooseWarehousingList.jspa"><span
															class="file">采购入库</span> </a></li>
												</ul></li>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa"><span
													class="file">出库管理</span> </a>
													
												<ul>

													<li><a target="mainframe"
														href="<%=basePath%>/ea/sales/ea_getWareManagementList.jspa?billStatus=07"><span
															class="file">销售出库</span> </a></li>
												</ul>
											</li>
											<li><span
													class="folder">库存管理</span> 
												<ul>

													<li><a target="mainframe"
														href="<%=basePath%>/ea/warehousing/ea_getInventoryManagementList.jspa"><span
															class="file">库存管理</span> </a></li>
													<li><a target="mainframe"
														href="<%=basePath%>/ea/warehousing/ea_getInventoryPoolList.jspa"><span
															class="file">进销存明细</span> </a></li>
													<li><a target="mainframe"
														href="<%=basePath%>/ea/warehousing/ea_getInventoryDetailList.jspa"><span
															class="file">进销存汇总</span> </a></li>
												</ul>
											</li>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/break/ea_getbreakList.jspa"><span
													class="file">报损管理</span> </a>
											</li>
											<li><a target="mainframe"
												href="<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa"><span
													class="file">存货核算 </span> </a>
											</li>

										</ul><li><span class="file">物品物料管理</span></li>



								</ul>
								<%--</li>
						--%></ul>
					</div></td>
				<td style="width: 80%;" valign="top"><iframe id="mainframe"
						name="mainframe" src="" frameborder="0" style="width: 100%;"></iframe>
				</td>

			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			$(window).resize(function() {
				setTimeout(function() {
					$("#navigation").height($(window).height() - 30);
					// $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
				}, 100);
			});
			$("#navigation").height($(window).height() - 30);
			$("#mainframe").css({
				"height" : $(window).height() + "px"
			});
		});
	</script>
</body>


</html>
