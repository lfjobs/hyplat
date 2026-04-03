<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.Date"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生产管理系统</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"
	type="text/javascript"></script>

<script type="text/javascript">
   
var basePath="<%=basePath%>";
</script>


</head>
<body>
	<div class="main_main">
	  
		<table width="100%" cellspacing="0" cellpadding="0" "border="2">
			<tr>
				<td id="qh_sw" style="width: 20%;" valign="top">
					<div class="qh_gg_nav">
						&nbsp; <span id="frametitle">生产管理系统</span>

					</div> <!--左边的树 -->

					<ul id="navigation" style="overflow:auto;" class="filetree" >
						<li style="display: none;"><span class="folder">生产管理系统</span>
							<ul>

								<li><a  target="admin1"><span
										class="folder">生产设计管理</span> </a>
									<ul>
									<% String f=request.getParameter("fiveClear"); %>
										<li><a target="admin1" href="<%=basePath%>ea/gooddesign/ea_getGoodDesignList.jspa?fiveClear=<%=f%>"><span class="file">生产物品设计</span>
										</a></li>
										<li><a target="admin1" href="<%=basePath%>ea/prodesign/ea_getProductDesignList.jspa?type=01&fiveClear=<%=f%>"><span class="file">生产产品设计</span>
										</a></li>
										<li><a target="_blank" href="<%=basePath%>ea/prodesign/ea_getProductPage.jspa?fiveClear=<%=f%>"><span class="file">项目产品流程设计</span>
										</a></li>
										<li><a target="admin1" href="<%=basePath%>ea/budgetplan/ea_getBudgetPlanList.jspa?type=01&fiveClear=<%=f%>"><span class="file">产品生产量预算</span>
										</a></li>
									</ul>
								</li>

								<li><a target="admin1"><span class="folder" id="unex">模拟测试管理</span>
								</a>
									<ul>
										<li><a href="<%=basePath%>ea/bsimtest/ea_getBsimtestList.jspa?type=00&status=00" target="admin1"><span class="file">模拟测试</span>

										<li><a href="<%=basePath%>ea/bsimtest/ea_getBsimtestList.jspa?status=02" target="admin1"><span class="file">模拟测试合格</span>
										</a></li>

										<li><a href="<%=basePath%>ea/bsimtest/ea_getBsimtestList.jspa?status=03" target="admin1"><span class="file">模拟测试不合格</span>
										</a></li>
									</ul>
								</li>
								<li><a target="admin1"><span class="folder" id="unex">生产过程管理</span>
								</a>
									<ul>
										<li><a target="admin1" href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=1"><span class="file">生产订单</span>
										</a></li>
										<li><a target="admin1"><span class="folder">生产计划</span>
										</a>
											<ul>
												<li><a target="admin1" href="<%=basePath%>ea/budgetplan/ea_getBudgetPlanList.jspa?type=02&fiveClear=<%=f%>"><span class="file">生产量日周月季年计划</span>
												</a></li>
												<li><a target="admin1" href="<%=basePath%>ea/amountplan/ea_getAmountPlanList.jspa?fiveClear=<%=f%>"><span class="file">生产项目产品计划量设置</span>
												</a></li>
											</ul></li>
										<li><a target="admin1"><span class="folder">生产分配</span>
										</a>
											<ul>
												<li><a target="admin1" href="<%=basePath %>ea/member/ea_getMemberList.jspa?fiveClear=<%=f%>"><span class="file" >人员分配管理</span>
												</a></li>
												<li><a target="admin1" href="<%=basePath %>ea/fielddistr/ea_getHomePage.jspa"><span class="file">场地分配管理</span>
												</a></li>
												<li><a href="<%=basePath%>ea/proedpdist/ea_findList.jspa?fiveClear=<%=f%>" target="admin1"><span class="file">设备分配管理</span>
												</a></li>
												<li><a target="admin1"  href="<%=basePath%>ea/duty/ea_getPutyList.jspa?fiveClear=<%=f%>"><span class="file">班值分配管理</span>
												</a></li>
											</ul>
										</li>
										<li><a target="admin1"><span class="folder">生产采购</span>
										</a>

											<ul>
												<li><a target="admin1" href="<%=basePath %>ea/purchase1/ea_getPurchaseList.jspa?type=00&xmtype=04"><span class="file">采购单</span>
												</a></li>
												<li><a target="admin1" href="<%=basePath %>ea/purchase1/ea_getPurchaseList.jspa?type=01&xmtype=04"><span class="file">收货单</span>
												</a></li>
												<li><a target="admin1" href="<%=basePath %>ea/purchase1/ea_getinspectList.jspa?xmtype=04"><span class="file">验货单</span>
												</a></li>
												<li><a target="admin1" href="<%=basePath %>ea/newstorage/ea_toExamineGoodsBillList.jspa?xmtype=04"><span class="file">采购入库</span>
												</a></li>
											</ul>
										</li>
										<li><a target="admin1"><span class="folder">批量生产</span>
										</a>
											<ul>
												<li><a target="admin1"><span class="folder">半成品生产</span>
												</a>

													<ul>
														<li><a target="admin1"  href="<%=basePath %>ea/quantity/ea_getTheProductionOfTheHomePageInformation.jspa?statusbill=10"><span class="file">物品出库</span>
														</a></li>


													</ul></li>
												<li><a target="admin1"><span class="folder">成品生产</span>
												</a>

													<ul>
														<li><a target="admin1"  href="<%=basePath %>ea/quantity/ea_getTheProductionOfTheHomePageInformation.jspa?statusbill=11"><span class="file">物品出库</span>
														</a></li>


													</ul></li>

											</ul>
										</li>
										<li><a target="admin1"><span class="folder">生产跟踪</span>
										</a>
											<ul>
												<li><a href="<%=basePath%>ea/ptrack/ea_getPtrackList.jspa?type=00&fiveClear=<%=f%>" target="admin1"><span class="file">产品生产跟踪</span>
												</a></li>

											<!-- 	<li><a href="<%=basePath%>ea/vmptrack/ea_getVMPtrackList.jspa?" target="admin1"><span class="file">批量生产跟踪</span>
												</a></li> -->


											</ul></li>
								</li>
							</ul>
						</li>
                   <ul>
						<li ><a target="admin1"><span class="folder" id="unex">考核检验管理</span>
						</a>
							<ul>
								<li><a href="<%=basePath%>ea/dcheck/ea_getDCheckList.jspa?type=00&status=00&show=00" target="admin1"><span class="file">考核检验</span> </a>
								</li>
								<li><a href="<%=basePath%>ea/dcheck/ea_getDCheckList.jspa?status=01&show=00" target="admin1"><span class="file">考核检验合格</span>
								</a></li>
								<li><a href="<%=basePath%>ea/dcheck/ea_getDCheckList.jspa?status=02&show=00" target="admin1"><span class="file">考核检验不合格</span>
								</a></li>
								<li><a href="<%=basePath%>ea/dcheck/ea_getDCheckyieldList.jspa?type=01&show=00" target="admin1"><span class="file">合格率</span> </a></li>
							</ul>
						</li>


						<li><a target="admin1"><span class="folder" id="unex">合格成品管理</span>
						</a>
							<ul>
								<li><a target="admin1"  href="<%=basePath%>ea/production/ea_getAccessToProductInformation.jspa"><span class="file">产品入库</span> </a>
								</li>
								<li><a target="admin1"  href="<%=basePath%>ea/finished/ea_getHomePageData.jspa"><span class="file">成品出库</span> </a>
								</li>
							</ul>
						</li>

					</ul>
					</li>
					</ul>
					<li   ><span class="folder">生产管理系统</span>
						<ul>
							<li><a target="admin1"><span class="folder">生产设计管理</span> </a>
								<ul>
										<li><a target="admin1" href="<%=basePath%>ea/gooddesign/ea_getGoodDesignList.jspa?fiveClear=<%=f%>"><span class="file">生产物品设计</span>
										</a></li>
										<li><a target="admin1" href="<%=basePath%>ea/prodesign/ea_getProductDesignList.jspa?type=01&fiveClear=<%=f%>"><span class="file">生产产品设计</span>
										</a></li>
										<li><a target="_blank" href="<%=basePath%>ea/prodesign/ea_getProductPage.jspa?fiveClear=<%=f%>"><span class="file">项目产品流程设计</span>
										</a></li>
										<li><a target="admin1" href="<%=basePath%>ea/budgetplan/ea_getBudgetPlanList.jspa?type=01&fiveClear=<%=f%>"><span class="file">产品生产量预算</span>
										</a></li>
									</ul>
							</li>
							<li><a target="admin1"><span class="folder" id="unex">模拟测试管理</span></a>
								<ul>
										<li><a href="<%=basePath%>ea/bsimtest/ea_getBsimtestList.jspa?type=00&status=00" target="admin1"><span class="file">模拟测试</span>

										<li><a href="<%=basePath%>ea/bsimtest/ea_getBsimtestList.jspa?status=02" target="admin1"><span class="file">模拟测试合格</span>
										</a></li>

										<li><a href="<%=basePath%>ea/bsimtest/ea_getBsimtestList.jspa?status=03" target="admin1"><span class="file">模拟测试不合格</span>
										</a></li>
									</ul>
							</li>
							<li><a target="admin1"><span class="folder" id="unex">生产过程管理</span></a>
								<ul>
									<li><a target="admin1" href="<%=basePath%>ea/setpro/ea_listPage.jspa"><span class="folder">单产品生产</span></a>
											<ul>
												<li><a target="admin1" href="javascript:;"><span class="folder">订单生产</span></a>
													<ul>
														<li><a target="admin1" href="<%=basePath %>ea/assembly/ea_getProductOrderList.jspa?category=00"><span class="file">订单管理</span>
														<li><a target="admin1" href="<%=basePath%>ea/purchasebids/ea_getPurcBugetSheetList.jspa?fiveClear=<%=f%>&type=00"><span class="file">采购预算申请</span></a></li>
														<li><a target="admin1" href="<%=basePath%>ea/purchasebids/ea_findAuditPurSheetList.jspa?fiveClear=<%=f%>&type=00&category=00"><span class="file">采购审批</span></a></li>
														<li><a target="admin1" href="<%=basePath%>ea/purchasebids/ea_findInviteBidsList.jspa?fiveClear=<%=f%>&type=00&category=00"><span class="file">发布招标</span></a></li>
														<li><a target="admin1" href="<%=basePath%>ea/purchasebids/ea_findSelectBidsList.jspa?fiveClear=<%=f%>&type=00&category=00"><span class="file">比价选标</span></a></li>
														<li><a target="admin1" href="javascript:;"><span class="folder">生产采购</span></a>
															<ul>
																<li><a target="admin1" href="<%=basePath%>/ea/sourcingsto/ea_inspectionList.jspa?type=00&category=00"><span class="file">采购验货管理</span></a></li>
																<li><a target="admin1" href="<%=basePath%>ea/sourcingsto/ea_goodsReceiptList.jspa?type=00&category=00"><span class="file">采购收货管理</span></a></li>
																<li><a target="admin1" href="<%=basePath%>ea/sourcingsto/ea_storageList.jspa?type=00&category=00"><span class="file">采购入库管理</span></a></li>
															</ul>
														</li>
														<li><a target="admin1" href="javascript:;"><span class="folder">生产分配</span></a>
															<ul>
																<li><a target="admin1" href="<%=basePath %>ea/member/ea_getMemberList.jspa?fiveClear=<%=f%>&type=00&category=00"><span class="file" >人员分配管理</span></a></li>
																<li><a target="admin1" href="<%=basePath %>ea/fielddistr/ea_getHomePage.jspa?type=00&category=00"><span class="file">场地分配管理</span></a></li>
																<li><a href="<%=basePath%>ea/proedpdist/ea_findList.jspa?fiveClear=<%=f%>&type=00&category=00" target="admin1"><span class="file">设备分配管理</span></a></li>
																<li><a target="admin1"  href="<%=basePath%>ea/duty/ea_getPutyList.jspa?fiveClear=<%=f%>&type=00&category=00"><span class="file">班值分配管理</span></a></li>
															</ul>
														</li>
														<li><a target="admin1"  href="<%=basePath %>ea/assembly/ea_getProductAssemblyList.jspa?type=00&category=00"><span class="file">订单生产组装</span>													
													</ul>
												</li>
												<li><a target="admin1" href="jvascript:;"><span class="folder">计划生产</span></a>
													<ul>
														<li><a target="admin1" href="<%=basePath%>ea/setpro/ea_listPage.jspa?type=01&category=00"><span class="file">设置生产量</span></a></li>														
														<li><a target="admin1" href="<%=basePath%>ea/purchasebids/ea_getPurcBugetSheetList.jspa?fiveClear=<%=f%>&type=01"><span class="file">采购预算申请</span></a></li>
														<li><a target="admin1" href="<%=basePath%>ea/purchasebids/ea_findAuditPurSheetList.jspa?fiveClear=<%=f%>&type=01&category=00"><span class="file">采购审批</span></a></li>
														<li><a target="admin1" href="<%=basePath%>ea/purchasebids/ea_findInviteBidsList.jspa?fiveClear=<%=f%>&type=01&category=00"><span class="file">发布招标</span></a></li>
														<li><a target="admin1" href="<%=basePath%>ea/purchasebids/ea_findSelectBidsList.jspa?fiveClear=<%=f%>&type=01&category=00"><span class="file">比价选标</span></a></li>
														<li><a target="admin1" href="javascript:;"><span class="folder">生产采购</span></a>
															<ul>	
																<li><a target="admin1" href="<%=basePath%>/ea/sourcingsto/ea_inspectionList.jspa?type=01&category=00"><span class="file">采购验货管理</span></a></li>
																<li><a target="admin1" href="<%=basePath%>ea/sourcingsto/ea_goodsReceiptList.jspa?type=01&category=00"><span class="file">采购收货管理</span></a></li>
																<li><a target="admin1" href="<%=basePath%>ea/sourcingsto/ea_storageList.jspa?type=01&category=00"><span class="file">采购入库管理</span></a></li>
															</ul>
														</li>
														<li><a target="admin1" href="javascript:;"><span class="folder">生产分配</span></a>
															<ul>
																<li><a target="admin1" href="<%=basePath %>ea/member/ea_getMemberList.jspa?fiveClear=<%=f%>&type=01&category=00"><span class="file" >人员分配管理</span></a></li>
																<li><a target="admin1" href="<%=basePath %>ea/fielddistr/ea_getHomePage.jspa?type=01&category=00"><span class="file">场地分配管理</span></a></li>
																<li><a href="<%=basePath%>ea/proedpdist/ea_findList.jspa?fiveClear=<%=f%>&type=01&category=00" target="admin1"><span class="file">设备分配管理</span></a></li>
																<li><a target="admin1"  href="<%=basePath%>ea/duty/ea_getPutyList.jspa?fiveClear=<%=f%>&type=01&category=00"><span class="file">班值分配管理</span></a></li>
															</ul>
														</li>
														<li><a target="admin1"  href="<%=basePath %>ea/assembly/ea_getProductAssemblyList.jspa?type=01&category=00"><span class="file">计划生产组装</span></a></li>													
													</ul>
												</li>
											</ul>
									</li>
									<li><a target="admin1"><span class="folder" id="unex">项目产品组装生产</span></a>
										<ul>
												<li><a target="admin1" href="javascript:;"><span class="folder">订单生产</span></a>
													<ul>
														<li><a target="admin1" href="<%=basePath %>ea/assembly/ea_getProductOrderList.jspa?type=00&category=01"><span class="file">订单管理</span>
														<li><a target="admin1" href="javascript:;"><span class="folder">生产分配</span></a>
															<ul>
																<li><a target="admin1" href="<%=basePath %>ea/member/ea_getMemberList.jspa?fiveClear=<%=f%>&type=00&category=01"><span class="file" >人员分配管理</span></a></li>
																<li><a target="admin1" href="<%=basePath %>ea/fielddistr/ea_getHomePage.jspa?type=00&category=01"><span class="file">场地分配管理</span></a></li>
																<li><a href="<%=basePath%>ea/proedpdist/ea_findList.jspa?fiveClear=<%=f%>&type=00&category=01" target="admin1"><span class="file">设备分配管理</span></a></li>
																<li><a target="admin1"  href="<%=basePath%>ea/duty/ea_getPutyList.jspa?fiveClear=<%=f%>&type=00&category=01"><span class="file">班值分配管理</span></a></li>
															</ul>
														</li>
														<li><a target="admin1"  href="<%=basePath %>ea/assembly/ea_getProductAssemblyList.jspa?type=00&category=01"><span class="file">订单生产组装</span>													
													</ul>
												</li>
												<li><a target="admin1" href="jvascript:;"><span class="folder">计划生产</span></a>
													<ul>
														<li><a target="admin1" href="<%=basePath%>ea/setpro/ea_listPage.jspa?type=01&category=01"><span class="file">设置生产量</span></a></li>														
														<li><a target="admin1" href="javascript:;"><span class="folder">生产分配</span></a>
															<ul>
																<li><a target="admin1" href="<%=basePath %>ea/member/ea_getMemberList.jspa?fiveClear=<%=f%>&type=01&category=01"><span class="file" >人员分配管理</span></a></li>
																<li><a target="admin1" href="<%=basePath %>ea/fielddistr/ea_getHomePage.jspa?type=01&category=01"><span class="file">场地分配管理</span></a></li>
																<li><a href="<%=basePath%>ea/proedpdist/ea_findList.jspa?fiveClear=<%=f%>&type=01&category=01" target="admin1"><span class="file">设备分配管理</span></a></li>
																<li><a target="admin1"  href="<%=basePath%>ea/duty/ea_getPutyList.jspa?fiveClear=<%=f%>&type=01&category=01"><span class="file">班值分配管理</span></a></li>
															</ul>
														</li>
														<li><a target="admin1"  href="<%=basePath %>ea/assembly/ea_getProductAssemblyList.jspa?type=01&category=01"><span class="file">计划生产组装</span></a></li>													
													</ul>
												</li>
											</ul>
									</li>
								</ul>
							</li>
							<li><a target="admin1"><span class="folder" id="unex">考核检验管理</span></a>
								<ul>
									<li><a target="admin1" href="<%=basePath %>ea/inspection/ea_inspectionList.jspa?type=01"><span class="file" id="unex">未检验产品管理</span></a></li>
									<li><a target="admin1" href="<%=basePath %>ea/inspection/ea_inspectionList.jspa?type=02"><span class="file" id="unex">检验合格产品管理</span></a></li>
									<li><a target="admin1" href="<%=basePath %>ea/inspection/ea_inspectionList.jspa?type=03"><span class="file" id="unex">检验不合格产品管理</span></a></li>
								</ul>
							</li>
							<li><a target="admin1"><span class="folder" id="unex">合格成品管理</span></a>
								<ul>
									<li><a target="admin1"  href="<%=basePath%>ea/production/ea_getAccessToProductInformation.jspa"><span class="file">产品入库</span> </a></li>
									<li><a target="admin1"  href="<%=basePath%>ea/finished/ea_getHomePageData.jspa"><span class="file">成品出库</span> </a></li>
								</ul>
							</li>
						</ul>
						</li>
					</li>
					</td>
				<td style="width: 80%;" valign="top"><iframe src=""
						id="mainframe" style="margin:0px;width:100%;height:560px;"
						name="admin1" scrolling="no" frameBorder="0"></iframe></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
	 $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#navigation").height($(window).height()- 30);
                
             },100);
         });
         $("#navigation").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height()+ "px"}); 
     });

		$(document).ready(function() {
			$("#navigation").treeview({
				persist : "location",
				collapsed : false,
				unique : false
			});

		});
	</script>


</body>
</html>