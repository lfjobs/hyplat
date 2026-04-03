<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>产品设计推广管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ page import="hy.ea.bo.Company"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany");
			
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		

		<script type="text/javascript">
   		 	 var comid='<%=c.getCompanyID()%>';
             var basePath='<%=basePath%>';  
             var module = '<%=session.getAttribute("module")%>'; 
             var journalNum = "${param.journalNum}";
             var projectName = "${param.projectName}";
        </script>
		<style type="text/css">
#doctree {
	position: absoute;
	padding-top: 0cm;
	margin-top: 0px;
	width: 100%;
	background-color: #FFFFFF;
	float: left;
}

#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}

.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
}

.numcss {
	color: red;
}
</style>
	</head>
	<body>
		<div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
					<td id="qh_sw" style="width: 15%" valign="top">
						<div class="qh_gg_nav">
							&nbsp;
							<span id="frametitle">产品发布管理</span>

						</div>
						<!--左边的树 -->
						<div style="overflow-y:scroll;height: 95%">
						<ul id="navigation" style="width: 220px;" class="filetree">
							<li><span class="folder" id="tit">产品发布管理</span>
								<ul>
									<li><a
										href="<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?fiveClear=5"
										target="admin1"> <span class="file">营销处产品设计</span> </a></li>
                                    <li><a
										href="<%=basePath%>/ea/bidrecruit/ea_findRecruitInfoList.jspa"
										target="admin1"> <span class="file">招聘信息发布</span> </a></li>

									<li>
									<li><a
										href="<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?flexbutton=type&pptype=1&no=0&isCheck=check"
										target="admin1"> <span class="file">产品发布</span> </a></li>

									<li><a
										href="<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?flexbutton=chanphuizong&pptype=0&no=1&isCheck=check"
										target="admin1"> <span class="file">已发布产品信息汇总</span> </a></li>
									<li><a
											href="<%=basePath%>/ea/devicebind/ea_selDeviceBind.jspa?"
											target="admin1"> <span class="file">投资设备绑定设置</span> </a></li>
									<li><a
											href="<%=basePath%>ea/percentage/ea_selectPPercentage.jspa?"
											target="admin1"> <span class="file">价格及业务佣金百分比设置</span> </a></li>
									<li><a
											href="<%=basePath%>ea/percentage/ea_selectBPercentage.jspa?"
											target="admin1"> <span class="file">业务佣金百分比设置</span> </a></li>
									 <li><a target="admin1"
                                                                      href="<%=basePath%>ea/retail/ea_selectRetailList.jspa"><span
                                                                       class="file">零售产品佣金设计</span> </a>
                                                               </li>
									<li><a target="admin1"
										   href="<%=basePath%>ea/wholesale/ea_selectWholesaleList.jspa"><span
											class="file">批发产品佣金设计</span> </a>
									</li>
									<li><a target="admin1"
										   href="<%=basePath%>ea/vip/ea_selectVipList.jspa"><span
											class="file">VIP产品佣金设计</span> </a>
									</li>
									<li><a target="admin1"
										   href="<%=basePath%>ea/activityPrice/ea_selectActivityList.jspa?activityType=00"><span
											class="file">普通活动产品佣金设计</span> </a>
									</li>
									<li><a target="admin1"
										   href="<%=basePath%>ea/activityPrice/ea_selectActivityList.jspa?activityType=01"><span
											class="file">特价活动产品佣金设计</span> </a>
									</li>

									<li><span class="folder">网络推广</span>
										<ul>
											<li><a
												href="<%=basePath%>ea/telmessage/ea_goMessageIndex.jspa?type=1&orgDetail=market"
												target="admin1"><span class="file" id="unex">短信推广管理</span>
											</a></li>
											<li><span class="file" id="ex">邮件推广管理</span></li>
											<li><span class="file" id="ex">博客推广管理</span></li>
											<li>
												<!-- <a href="<%=basePath%>/page/ea/main/microletter/microletter_tree.jsp" target="admin1"> 微信导航菜单  已不用-->
												<span class="folder" id="ex">微信推广管理</span> <!-- </a>  -->
												<ul>
													<li><a
														href="<%=basePath%>ea/activity/ea_activityList.jspa?inforType=00"
														target="admin1"><span class="file" id="unex">微信活动管理</span>
													</a></li>
													<%--<li>
															<a href="<%=basePath%>ea/activity/ea_activityList.jspa?inforType=01" target="admin1"><span
																class="file" id="unex">微信公共传媒</span></a>
														</li>--%>
													<li><a
														href="<%=basePath%>ea/wechatmenu/getMenuList.jspa"
														target="admin1"><span class="file" id="unex">微信菜单管理</span>
													</a></li>
													<li><a
														href="<%=basePath%>ea/wxrecruit/ea_findItem.jspa"
														target="admin1"><span class="file" id="unex">微信招聘管理</span>
													</a></li>
												</ul></li>
											<li><span class="file" id="ex">QQ推广管理</span></li>
											<li><span class="file" id="ex">网站推广管理</span></li>
										</ul></li>
									<li><span class="folder">户外广告</span></li>
									<li><span class="folder">媒体宣传</span>
										<ul>
											<li><span class="file" id="unex">电视宣传组</span></li>
											<li><span class="file" id="ex">报纸宣传组</span></li>

										</ul></li>
									<li><span class="folder">会议宣传</span></li>
									<%-- <li><span class="folder">会员佣金分配</span>
										<ul>
											<li id="wd"><span class="folder">我的商城收支管理</span>
												<ul>
													<li><span class="folder">完善会员基本信息</span></li>
													<li><span class="folder">商城会员人脉财源</span></li>
													<li><span class="folder">订单管理</span>

														<ul>

															<li><span class="file"><a
																	href="<%=basePath%>ea/bdbill/ea_getcomporder.jspa?&hylb=wd&type=pc"
																	target="admin1">我的订单</a>
															</span>
															</li>
														</ul></li>
													<li><span class="folder">现金管理</span>
														<ul>
															<li><a
																href="<%=basePath%>ea/bdbill/ea_getskd.jspa?hylb=wd&pl=sk&iisnull=1"
																target='admin1'><span class='file'>现金收款单管理</span>
															</a></li>
															<li><a
																href="<%=basePath%>ea/bdbill/ea_getskd.jspa?hylb=wd&pl=zk&iisnull=2"
																target='admin1'><span class='file'>现金支款单管理</span>
															</a></li>
															<li><span class="file">现金流水</span></li>
															<li><span class="file">佣金收支</span></li>
															<li><span class="file">金币兑换现金</span></li>
															<li><span class="file">库存现金</span></li>
														</ul></li>
													<li><span class="folder">收货管理</span>
														<ul>
															<li><span class="file"><a
																	href="<%=basePath%>ea/consignee/ea_getConsigneeSheetList.jspa?stype=01"
																	target="admin1">收货单</a>
															</span></li>
														</ul></li>
													<li>
												<a href="<%=basePath%>/ea/bdbill/ea_getjbfpmx.jspa?hylb=hy&pl=fl" target="admin1"><span class="file" >金币分配明细管理</span></a>		
											</li>
													<li><span class="folder">聚宝盆金币池</span>
														<ul>
															<li><span class="file">分享收金币</span></li>
															<li><span class="file">充值金币</span></li>
															<li><span class="file">消费产品收到馈赠金币</span></li>
															<li><span class="file">充值金币收到馈赠金币</span></li>
															<li><span class="file">好运当头收到馈赠金币</span></li>
															<li><span class="file">金币兑换现金</span></li>
															<li><span class="file">库存金币</span></li>
															<li><span class="file">金币购物</span></li>
															<li><a
																href="<%=basePath%>/ea/goldwater/ea_getHomePageInformation.jspa"
																target="_Blank"><span class="file">金币流水</span>
															</a></li>
														</ul></li>
												</ul></li>
											<li><span class="folder">供应商商城收支管理</span>
												<ul>
													<li><span class="folder">订单管理</span>
														<ul>
															<li><span class="file"><a
																	href="<%=basePath%>ea/bdbill/ea_getcomporder.jspa?hylb=gys&pl=dd"
																	target="admin1">订单</a>
															</span>
															</li>
														</ul></li>
													<li><span class="folder">收款单管理</span>
														<ul>
															<li><span class="file"><a
																	href="<%=basePath%>ea/bdbill/ea_getskd.jspa??hylb=gys&pl=sk"
																	target="admin1">现金收款单管理</a>
															</span>
															</li>
														</ul></li>
													<li><span class="file"><a
															href="<%=basePath%>ea/bdbill/ea_getcomporder.jspa?hylb=gys&pl=pl"
															target="admin1">批量发货管理</a>
													</span></li>
													<li><a
														href="<%=basePath%>ea/salesman/ea_getHomePageInformationList.jspa?"
														target="admin1"><span class='file'>出库单管理</span>
													</a>
													</li>
													<li><span class="folder">物流管理</span>
														<ul>
															<li><a target="admin1"
																href="<%=basePath%>/ea/logstor/ea_getHomePageInformationList.jspa"><span
																	class="file">物流入库</span> </a></li>
															<li><a target="admin1"
																href="<%=basePath%>/ea/logware/ea_getHomePageInformationList.jspa"><span
																	class="file">物流出库</span> </a></li>
														</ul></li>
													<li><span class="folder">收货管理</span>
														<ul>
															<li><span class="file"><a
																	href="<%=basePath%>ea/consignee/ea_getConsigneeSheetList.jspa?stype=02"
																	target="admin1">收货单</a>
															</span></li>
														</ul></li>
													<li><span class="file"><a
															href="<%=basePath%>ea/refund/ea_getRefundSheetList.jspa?stype=01&type=pc"
															target="admin1">退货管理</a>
													</span></li>
													<li>
												<span class="file"><a href="<%=basePath%>ea/refund/ea_getRefundSheetList.jspa?stype=01&type=mobile" target="admin1">手机端退货管理</a></span>
											</li>
													<li><span class="file"><a
															href="<%=basePath%>ea/refund/ea_findRefundCashList.jspa?stype=01"
															target="admin1">退款管理</a>
													</span></li>
													<li>
												<a href="<%=basePath%>/ea/bdbill/ea_getjbfpmx.jspa?hylb=gys&pl=fl" target="admin1"><span class="file" >金币分配明细管理</span></a>		
											</li>
												</ul></li>
											<li id="xj"><span class="folder">我的下级商城收支管理</span>
												<ul>
													<li><span class="folder">订单管理</span>
														<ul id="dd">
														</ul></li>
													<li><span class="folder">现金管理</span>
														<ul id="hy"></ul></li>
													<li><span class="folder">收货管理</span>
														<ul id="sh"></ul></li>
													<li>
												<a href="<%=basePath%>/ea/bdbill/ea_getjbfpmx.jspa?hylb=hy&pl=fl" target="admin1"><span class="file" >金币分配明细管理</span></a>		
											</li>
													<li><span class="folder">聚宝盆金币池</span>
														<ul>
															<li><span class="file">分享收金币</span></li>
															<li><span class="file">充值金币</span></li>
															<li><span class="file">消费产品收到馈赠金币</span></li>
															<li><span class="file">充值金币收到馈赠金币</span></li>
															<li><span class="file">好运当头收到馈赠金币</span></li>
															<li><span class="file">金币兑换现金</span></li>
															<li><span class="file">库存金币</span></li>
															<li><span class="file">金币购物</span></li>
															<li><a
																href="<%=basePath%>ea/goldwater/ea_getLowerMemberType.jspa"
																target="admin1"><span class="file">金币流水</span>
															</a></li>
														</ul></li>
												</ul></li>
										</ul></li> --%>
								</ul></li>
						</ul>
					</div></td>
					<td style="width: 85%;" valign="top">
						 <iframe src="<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?flexbutton=publish" id="mainframe1" style="margin:0px;width:100%;height:560px;" name="admin1" scrolling="no" frameBorder="0"></iframe>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
			$(function() {
				$(window).resize(function() {
					setTimeout(function() {
						$("#navigation").height($(window).height() - 30);
						$("#mainframe1").css({
							"height" : $(window).height() - 5 + "px"
						});
					}, 100);
				});
				$("#navigation").height($(window).height() - 30);
				$("#mainframe1").css({
					"height" : $(window).height() + "px"
				});
				$("#navigation").treeview();
				//if(comid=="company201009046vxdyzy4wg0000000025"){
				//$("#wd").hide();
				//}
				var url = basePath + "/ea/bdbill/sajax_gethylb.jspa";
				$.ajax({
							url : url,
							type : "get",
							async : false,
							dataType : "json",
							success : function(data) {
								var member = eval("(" + data + ")");
								var nologin = member.nologin;
								if (nologin) {
									document.location.href = basePath
											+ "page/ea/not_login.jsp";
								}
								var a = member.a;//当前用户权限表
								var b = member.b;//下级代理
								var c = member.c;//会员类别
								var t = "";

								//if(a!=null){
								//t = a.cusType;
								//if(a.companyId==null&&a.companyId==""&&a.companyId==" "){
								//$("#plfh").hide();
								//}
								//}else{
								$("#plfh").hide();
								//}
								var ddhtml = "";
								var skdhtml = "";
								var ckhtml = "";
								var zkthml = "";
								var shhtml = "";
								var e = "";
								var d = "";
								var staffid = "";

								//if(b.length<=0){
								//$("#xj").hide();
								//}
								/*for(var i=0;i<b.length;i++){
									var f=b[i];
									if(f[1]==2){
										d+=f[0]+",";
									}
								}
								if(t<2){
									lihtml+="<li><span class='file'>公司企业商城会员订单管理</span></li>";
								} */
								if (t < 3) {
									d = "";
									for ( var i = 0; i < b.length; i++) {
										var f = b[i];
										if (f[1] == 3) {
											d += f[0] + ",";
											staffid += f[2] + ",";
										}
									}
									//if(d.length>0){
									ddhtml += "<li><a href='"
											+ basePath
											+ "ea/bdbill/ea_getcomporder.jspa?hylb="
											+ d
											+ "&pl=dd' target='admin1'><span class='file'>"
											+ c[2][1] + "订单管理</span></a></li>";
									shhtml += "<li><a href='"
											+ basePath
											+ "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType="
											+ c[2][0]
											+ "' target='admin1'><span class='file'>"
											+ c[2][1] + "收货单</span></a></li>";
									//skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[2][1]+"收款单管理</span></a></li>";
									//ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?&hylb="+d+"' target='admin1'><span class='file'>"+c[2][1]+"出库单管理</span></a></li>";
									//zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[2][1]+"支款单管理</span></a></li>";
									e += "<li><span class='folder'>" + c[2][1]
											+ "现金收支单据管理</span><ul>"
											+ xj(staffid) + "</ul></li>";
									//}
								}
								if (t < 4) {
									d = "";
									for ( var i = 0; i < b.length; i++) {
										var f = b[i];
										if (f[1] == 4) {
											d += f[0] + ",";
											staffid += f[2] + ",";
										}
									}
									//if(d.length>0){
									ddhtml += "<li><a href='"
											+ basePath
											+ "ea/bdbill/ea_getcomporder.jspa?hylb="
											+ d
											+ "&pl=dd' target='admin1'><span class='file'>"
											+ c[3][1] + "订单管理</span></a></li>";
									shhtml += "<li><a href='"
											+ basePath
											+ "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType="
											+ c[3][0]
											+ "' target='admin1'><span class='file'>"
											+ c[3][1] + "收货单</span></a></li>";
									//skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[3][1]+"收款单管理</span></a></li>";
									//ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?&hylb="+d+"' target='admin1'><span class='file'>"+c[3][1]+"出库单管理</span></a></li>";
									//zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[3][1]+"支款单管理</span></a></li>";
									e += "<li><span class='folder'>" + c[3][1]
											+ "现金收支单据管理</span><ul>"
											+ xj(staffid) + "</ul></li>";
									//}
								}
								if (t < 5) {
									d = "";
									for ( var i = 0; i < b.length; i++) {
										var f = b[i];
										if (f[1] == 5) {
											d += f[0] + ",";
											staffid += f[2] + ",";
										}
									}
									//if(d.length>0){
									ddhtml += "<li><a href='"
											+ basePath
											+ "ea/bdbill/ea_getcomporder.jspa?&hylb="
											+ d
											+ "&pl=dd' target='admin1'><span class='file'>"
											+ c[4][1] + "订单管理</span></a></li>";
									shhtml += "<li><a href='"
											+ basePath
											+ "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType="
											+ c[4][0]
											+ "' target='admin1'><span class='file'>"
											+ c[4][1] + "收货单</span></a></li>";
									//skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[4][1]+"收款单管理</span></a></li>";
									//ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?hylb="+d+"' target='admin1'><span class='file'>"+c[4][1]+"出库单管理</span></a></li>";
									//zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[4][1]+"支款单管理</span></a></li>";
									e += "<li><span class='folder'>" + c[4][1]
											+ "现金收支单据管理</span><ul>"
											+ xj(staffid) + "</ul></li>";
									//}
								}
								if (t < 6) {
									d = "";
									for ( var i = 0; i < b.length; i++) {
										var f = b[i];
										if (f[1] == 6) {
											d += f[0] + ",";
											staffid += f[2] + ",";
										}
									}
									//if(d.length>0){
									ddhtml += "<li><a href='"
											+ basePath
											+ "ea/bdbill/ea_getcomporder.jspa?&hylb="
											+ d
											+ "&pl=dd' target='admin1'><span class='file'>"
											+ c[5][1] + "订单管理</span></a></li>";
									shhtml += "<li><a href='"
											+ basePath
											+ "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType="
											+ c[5][0]
											+ "' target='admin1'><span class='file'>"
											+ c[5][1] + "收货单</span></a></li>";
									//skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[5][1]+"收款单管理</span></a></li>";
									//ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?&hylb="+d+"' target='admin1'><span class='file'>"+c[5][1]+"出库单管理</span></a></li>";
									//zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[5][1]+"支款单管理</span></a></li>";
									e += "<li><span class='folder'>" + c[5][1]
											+ "现金收支单据管理</span><ul>"
											+ xj(staffid) + "</ul></li>";
									//}
								}
								$("#dd").append(ddhtml);
								//$("#sk").append(skdhtml);
								$("#ck").append(ckhtml);
								//$("#zk").append(zkthml);
								$("#hy").append(e);

								$("#sh").append(shhtml);

							},
							error : function(data) {
								alert("操作失败！");
							}

						});
				//$("#dd").prepend("<li><span class='file'>代理商企业商城会员订单管理</span></li>");
				$("#sjdd")
						.click(
								function() {
									window
											.open(basePath
													+ "/ea/hypb/ea_getcomporder.jspa?staid=cstaff20151104IF8I49HSDR0000000001");
								});
				$("#mjdd")
						.click(
								function() {
									window
											.open(basePath
													+ "/ea/ghspb/ea_getcomporder.jspa?staid=company201009046vxdyzy4wg0000000025");
								});
			});
			function xj(staffid) {
				return "<li><span class='file'><a href='" + basePath
						+ "ea/bdbill/ea_getskd.jspa?iisnull=1&pl=sk&hylb="
						+ staffid + "' target='admin1'>现金收款单管理</a></span></li>"
						+ "<li><span class='file'><a href='" + basePath
						+ "ea/bdbill/ea_getskd.jspa?iisnull=2&pl=zk&hylb="
						+ staffid + "' target='admin1'>现金支款单管理</a></span></li>"
						+ "<li><span class='file'>现金流水</span></li>"
						+ "<li><span class='file'>佣金收支</span></li>"
						+ "<li><span class='file'>金币兑换现金</span></li>"
						+ "<li><span class='file'>库存现金</span></li>";
			}
		</script>
	</body>

</html>
