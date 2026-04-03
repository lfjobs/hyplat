<!DOCTYPE>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>css/contacts/style12.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
<title>会员中心>>订单管理>>订单详情</title>
<style type="text/css">
.wfj12_014 .wfj12_014_product .wfj12_014_pay .td_btn{flost:right;width:100px;margin-left:2%}
</style>
<script type="text/javascript">
	var cashid="${pb.cashierBillsID }";
	var staid="${pb.contactUserID }";
	var basePath="<%=basePath%>";
	var pl="01"; // 未付款状态
	var zffs=1;//选择的支付方式   默认为支付宝
	var jum="${pb.journalNum}";
	var morre="${pb.priceSub }";
	var goodsName="";
	var status="${pb.fkStatus}";
	var token=0;
</script>



</head>

<body>
	<div class="wfj12_014">

		<!--中联园区头部-->
		<div class="wfj_top">
			<ul>
				<li><a href="javascript:history.go(-1);" target="_self"><img
						src="<%=basePath %>images/ea/finance/BenDis/wfj_return_02.png" />
				</a>
				</li>
				<li>订单管理</li>
				<li><a href="javascript:;"><img
						src="<%=basePath %>images/ea/finance/BenDis/top_more_02.png" />
				</a>
				</li>
			</ul>
		</div>
		<!--中联园区头部 end-->
		<div class="wfj12_014_content">
			<div class="wfj12_014_hidden">
				<div class="wfj12_014_topimg">
					<div class="wfj12_014_timg">
						<img src="<%=basePath %>images/ea/finance/BenDis/wfj_gwc_01.png" />
					</div>
					<div class="wfj12_014_top_width">
						<ul>
							<input type="hidden" id="id" value="${pf.cashierBillsID}" />
							<c:if test="${pb.fkStatus=='01'}">
								<li class="wfj12_014_top_font1">我的<font>购物</font>车</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">专属你的购物需求</a>
								</li>
							</c:if>
							<c:if test="${pb.fkStatus=='00'}">
								<li class="wfj12_014_top_font1"><font>待发货</font>
								</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">亲·准备发货咯！</a>
								</li>
							</c:if>
							<c:if test="${pb.fkStatus=='02'}">
								<li class="wfj12_014_top_font1"><font>待收货</font>
								</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">您的商品将要到达</a>
								</li>
							</c:if>
							<c:if test="${pb.fkStatus=='05'||pb.fkStatus=='06'}">
								<li class="wfj12_014_top_font1"><font>待退货</font>
								</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">您的商品待退货</a>
								</li>
							</c:if>
							<c:if test="${pb.fkStatus=='07'}">
								<li class="wfj12_014_top_font1"><font>退货中</font>
								</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">您的商品正在退货</a>
								</li>
							</c:if>
							<c:if test="${pb.fkStatus=='08'}">
								<li class="wfj12_014_top_font1"><font>确认收货</font>
								</li>
								<li class="wfj12_014_top_font2"><a href="javascript:;">您的商品已确认收货</a>
								</li>
							</c:if>
							
						</ul>
					</div>
				</div>
				<div class="wfj12_014_title">
					<div class="wfj12_014_width">
						<ul>
							<li><img
								src="<%=basePath %>images/ea/finance/BenDis/aboutusimg_01.png" />
							</li>
							<li>${pb.companyName }</li>
							<li><img
								src="<%=basePath %>images/ea/finance/BenDis/wfj_return_03.png" />
							</li>
						</ul>
					</div>
				</div>

				<div class="wfj12_014_product">
					<div class="wfj12_014_width">
							<c:forEach items="${pb.goodsList}" var="gl">
						<table class="wfj12_014_pro">
							   
								<a href="<%=basePath %>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${gl[7] }&goodsid=${gl[10] }&companyId=${gl[12]}">
									<tr>
									    <input type="hidden" value="${gl[7] }" id="ppid"/>
									    <input type="hidden"  value="${gl[6] }" id="ppid1" />
									    <input type="hidden"  value="${gl[14] }" id="projectname" />
										<td width="30%" rowspan="3"><img
											src="<%=basePath %>${gl[6]}"/>
										</td>
										<td colspan="2"><font>${gl[5]}</font>
										</td>
										<td class="pdright" align="right"><span>￥${gl[3]}</span>
										</td>
									</tr>
									<tr>
										<td width="50%" colspan="2">${gl[11]}</td>
										<td class="pdright" width="20%" align="right">X${gl[2]}</td>
									</tr>
									<tr>
										<td colspan="3">
											<%-- <span>金币：</span> --%>
										</td>
										
									</tr></table></a>
									
									<!-- 促销品 -->
									<c:forEach items="${pb.ptgoodsList }" var="pt">
									<a>
								<%-- 	href="<%=basePath %>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${pt[7] }&goodsid=${pt[8] }&companyId=${pt[9]}"> --%>
									<table class="wfj12_014_pro">
									<tr>
									    <input type="hidden" value="${pt[7] }" id="ppid"/>
									    <input type="hidden"  value="${pt[6] }" id="ppid1" />
										<td width="30%" rowspan="3"><img
											src="<%=basePath %>${pt[6]}"/>
										</td>
										<td colspan="2"><font>${pt[5]}</font>
										</td>
										<td class="pdright" align="right"><img src="<%=basePath %>/images/WFJClient/Newjspim/cuxiao.png" style="width: 55%;"/>
										</td>
									</tr>
									<tr>
										<td width="50%" colspan="2">${pt[11]}</td>
										<td class="pdright" width="20%" align="right">X${pt[2]}</td>
									</tr>
									<tr>
										<td colspan="3">
											<%-- <span>金币：</span> --%>
										</td>
									</tr>
									</table> 
									</a></c:forEach>
									
								<script type="text/javascript">
		                           // goodsName=goodsName+"${gl[5]} ";
		                           goodsName = "${gl[14]}";
		                            var count="${gl[2]}";
	                            </script>
	                            <table>
	                            <tr id="pp">
								<td></td>
								<td class="pdright" colspan="3">
									<c:if test="${pb.fkStatus=='00'}">
									 
										<div class="wfj12_014_commit agree" id="agree">申请退款</div>
										
										
									</c:if>
									 <c:if test="${pb.fkStatus=='02'}">									 										
										<div class="wfj12_014_commit wfj_sh" id="wfj_sh">确认收货</div>										
									</c:if>
								 
									<c:if test="${pb.fkStatus=='05'||pb.fkStatus=='06'||pb.fkStatus=='07'||pb.fkStatus=='08'}">
									 
										<div class="wfj12_014_commit agree" id="agree">退货详情</div>
										
										
									</c:if>
									
									<c:if test="${pb.fkStatus=='10'||pb.fkStatus=='11'||pb.fkStatus=='12'||pb.fkStatus=='13'}">					 
										<div class="wfj12_014_commit agree" >售后详情</div>																		
									</c:if>
									<c:if test="${pb.fkStatus=='03'}">
									 
										<div class="wfj12_014_commit agree" id="agree">申请售后</div>																				
									</c:if>
									</td>
							</tr>
						</table>
							</c:forEach>
							
					</div>
					
					<div class="wfj12_014_pays">
						<div class="wfj12_014_width">
							<table class="wfj12_014_pay">
								<tr>
									<td>运费</td>
									<td class="pdright" align="right">￥0.00</td>
								</tr>
								<tr>
									<td>实付款（含运费）</td>${pb.fkStatus }${pb.wfStatus4 }
									<td class="pdright" align="right"><span>￥${pb.priceSub}</span>
									</td>
								</tr>
								<tr>
									
									<td  align="right" colspan="2">																																										
									<c:choose>
										<c:when test="${pb.fkStatus=='01'}">
											<c:choose>
												<c:when test="${pb.wfStatus4=='03' }">
													<div style="float:right;width:100px" class="wfj12_014_commit td_btn" onclick="update()">修改学员</div>
													<button onclick="zzqr()" id="zzqr">转账确认</button>
												</c:when>
												<c:otherwise>
													<div style="float:right;width:100px" class="wfj12_014_commit td_btn" onclick="del()">删除订单</div>
													<div style="float:right;width:100px" class="wfj12_014_commit td_btn"  id="zf">立即支付</div>																			
												</c:otherwise>
											</c:choose>										
										</c:when>
										<c:when test="${pb.fkStatus=='02'}">
											<div style="float:right;width:100px" class="wfj12_014_commit td_btn" onclick="update()">修改学员</div>									 										
											
											<div class="wfj12_014_commit wfj_sh" id="wfj_sh">确认收货</div>
											<c:if test="${deliverDate>=0}">
												<div  class="wfj12_014_deliverDate">自动收货失败</div>
											</c:if>
										
										</c:when>
                                        <c:otherwise>
											<div style="float:right;width:100px" class="wfj12_014_commit td_btn" onclick="update()">修改学员</div>																														
										</c:otherwise>									
									</c:choose>																																								
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
               
				<div class="wfj12_014_payinfo">
					<div class="wfj12_014_width">
						<table>
							<tr>
								<td>订单编号：${pb.journalNum}</td>
								<td align="right"><div>复制</div>
								</td>
							</tr>
							<s:if test="pb.fkStatus!=01">
								<tr>
									<td colspan="2">支付宝交易号：${pb.trade_no}</td>
								</tr>
							</s:if>
							<tr>
								<td colspan="2">创建时间：${fn:substring(pb.cashierdate, 0, 19)}</td>
							</tr>
							<c:forEach items="${dateMap}" var="c">
								<tr>
									<td colspan="2">${c.key}：${fn:substring(c.value, 0, 19)}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				
				
			<!-- 学员搜索信息 -->
			<form name="addForm" id="addForm" method="post">
				<div class="wfj12_014_hidden_buy" style="display:none;" id="jqModeladd">			
				<table id="SearchTable" width="100%">
				<tr>
					<td width="50%" style="padding-left:6%;"><span><font color="#660000" size="8%">学员信息</font></span></td>
					<td width="50%" style="padding-right:6%;" align="right" id="back">
						<span><a href="javascript:"><img style="height: 40%" src="<%=basePath %>images/ea/finance/BenDis/x.png"></a></span></td>
				</tr>
			</table>
			
			<div class="wfj12_014_buy_width wfj12_014_buy_width2">
				<table>
					<tr class="wfj12_014_choice">
						<td class="wfj12_014_pay" align="right" >订单编号：</td>
						<td>
							<input type="hidden" id="joinput" style="width: 195px" name="jo" />							
							<span>${pb.journalNum}</span>
						</td>
					</tr>
					
					<tr class="wfj12_014_choice">
						<td class="wfj12_014_pay" align="right">
							学员姓名：
						</td>
						<td>
							<input id="noviceName" class="novice" style="width: 195px" name="noviceName" />
						</td>					
					</tr>
					
					<tr class="wfj12_014_choice">
						<td class="wfj12_014_pay" align="right">
							学员电话：	
						</td>
						<td>
							<input id="novicePhone" class="novice" style="width: 195px" name="novicePhone" />
						</td>
					</tr>
					<tr class="wfj12_014_choice">
						<td class="wfj12_014_pay" align="right">
							学员身份证号：
						</td>
						<td>
							<input id="noviceCode" class="novice" style="width: 195px" name="noviceCode" />
						</td>
					</tr>
					<tr class="wfj12_014_choice">
						<td class="wfj12_014_pay" align="right">
							学员地址：
						</td>
						<td>							
							<input id="noviceAddress" class="novice" style="width: 195px" name="noviceAddress" />
						</td>
					</tr>					
				</table>
			</div>
			<div class="wfj12_014_bottom_commit wfj12_014_bottom_commit2">
				<div  id="tosave">保存</div>
			</div>			
		</div>
		</form>
										
				<div class="wfj12_014_other">

					<c:forEach items="${cplist}" var="cp" varStatus="p">
						<div
							class="wfj12_014_otherpro <c:if test="${p.count%2 != '0'}">left</c:if><c:if test="${p.count%2 == '0'}">right</c:if>">
							<a
								href="<%=basePath %>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${cp[0] }&goodsid=${cp[16] }&companyId=${pb.companyid}">
								<ul>
									<li><img src="<%=basePath %>${cp[5]}" />
									</li>
								</ul>
								<ul>
									<div class="wfj12_014_width">
										<ul>
											<li>${cp[2]}</li>
											<li><span>￥${cp[4]}</span>
											</li>
										</ul>
									</div>
								</ul> </a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="wfj12_014_hidden_buy" style="display:none;">
			<table id="pays" width="100%">
				<tr>
					<td width="50%" style="padding-left:4%;"><span>需支付：</span>
					</td>
					<td align="right" style="padding-right:4%;" width="50%"><span
						id="money" style="color:#F74C31;">￥${pb.priceSub }</span>
					</td>
				</tr>
			</table>
			<div class="wfj12_014_buy_width">
				<table>
					<tr>
						<td colspan="2">选择支付方式</td>
					</tr>
					<tr class="wfj12_014_choice">
						<td class="wfj12_014_pay"><img
							src="<%=basePath %>images/ea/finance/BenDis/all_pay_01.png" />
						</td>
						<td class="second" align="right"><img width="24" name="1"
							height="24"
							src="<%=basePath %>images/ea/finance/BenDis/choice_01.png"
							name="1" />
						</td>
					</tr>
					<tr class="wfj12_014_choice wechat" >
						<td class="wfj12_014_pay"><img
							src="<%=basePath %>images/ea/finance/BenDis/all_pay_03.png" />
						</td>
						<td class="second" align="right"><img width="24" name="3"
							height="24"
							src="<%=basePath %>images/ea/finance/BenDis/choice_02.png"
							name="3" />
						</td>
					</tr>
					<tr class="wfj12_014_choice">
						  <td class="wfj12_014_pay"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="4" />
							</td>
						</tr>
				    <tr class="wfj12_014_choice">
						<td class="wfj12_014_pay"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/moneybox.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="5" />
							</td>
				  </tr>
				</table>
			</div>
			<div class="wfj12_014_bottom_commit">
				<div id="paycommit" onclick="zf()">确认支付</div>
			</div>
		</div>
	</div>
	<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
<form name="formsutm" id="formsutm" method="post">
<s:hidden name="ddid" id="ddid"></s:hidden>
<s:hidden name="baseUrl" id="baseUrl"></s:hidden>
<s:hidden name="morre" id="morre"></s:hidden>
<input type="submit" style="display: none" name="submit" id="submit"/>
</form>
<div id="alert" style="width: 100%;height: 100%;position: absolute;top: 0;background: rgba(0, 0, 0, 0.3);display:none;z-index: 10;"></div>
	<script type="text/javascript">
	
    	$(document).ready(function(e) {
 
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;margin:"+$(window).height()*0.015+"px auto;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
            $(".wfj12_014_top_width").attr("style","top:"+$(window).height()*0.02+"px;");
            $(".wfj12_014_top_font2").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
            $(".wfj12_014_top_font2").find("a").attr("style","font-size:"+$(window).height()*0.02+"px;padding:"+$(window).height()*0.01+"px "+$(window).height()*0.02+"px;letter-spacing:"+$(window).height()*0.01+"px;border-bottom:"+$(window).height()*0.002+"px solid #FFF; border-top:"+$(window).height()*0.002+"px solid #FFF;");
            $(".wfj12_014_top_font1").attr("style","width:"+$(".wfj12_014_top_font2").find("a").width()+"px;text-align:center;font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
            $(".wfj12_014_top_width").find("li").find("font").attr("style","font-size:"+$(window).height()*0.03+"px;");
			
			
            $(".wfj12_014_title").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
            $(".wfj12_014_title").find("li").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px; padding-right:"+$(window).height()*0.01+"px;");
            $(".wfj12_014_title").find("li").find("img").attr("style","height:"+$(window).height()*0.025+"px;margin:"+$(window).height()*0.0125+"px auto;");
			
			
            $(".wfj12_014_product").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_014_product").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
            $(".wfj12_014_product").find(".wfj12_014_commit").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
            $(".wfj12_014_pays").find("td").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
            $(".wfj12_014_otherpro").find(".wfj12_014_width").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.03+"px;line-height:"+$(window).height()*0.03+"px;overflow:hidden;");
            $(".wfj12_014_deliverDate").attr("style","font-size:"+$(window).height()*0.02+"px;color:red;");
            $(".wfj12_014_otherpro").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
			$(".wfj12_014_otherpro").find("img").css("height",$(".wfj12_014_otherpro").find("img").css("width"));
			
			$("table.wfj12_014_pro").attr("style","border-bottom:"+window.innerHeight*0.003+"px solid #E6E5E5;");
			$(".wfj12_014").find("table").find("tr").attr("style"," height:"+$(window).height()*0.06+"px; width:100%; border:none; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_014").find("table").find("tr").find("td").eq(0).find("span").attr("style"," font-size:"+$(window).height()*0.015+"px;");
			
			$(".wfj12_014_bottom_commit").attr("style"," height:"+$(window).height()*0.06+"px; background-color:#FFF; width:100%; padding-top:"+$(window).height()*0.01+"px;");
			$(".wfj12_014_bottom_commit").find("div").attr("style"," width:50%; height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.06+"px; background-color:#F74C31; border-radius:"+$(window).height()*0.01+"px; color:#FFF; text-align:center; font-size:"+$(window).height()*0.03+"px; font-weight:bold; margin:0 auto; cursor:pointer;");
			
			$(".wfj12_014_buy_width").find("td").attr("style"," height:"+$(window).height()*0.1+"px;font-size:"+$(window).height()*0.02+"px;");
			
			/* lx 9.8*/
			$(".wfj12_014_buy_width2").find("td").attr("style"," height:"+$(window).height()*0.05+"px;");
			$(".wfj12_014_bottom_commit2").find("div").attr("style"," width:25%; height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px; background-color:#F74C31; border-radius:"+$(window).height()*0.01+"px; color:#FFF; text-align:center; font-size:"+$(window).height()*0.02+"px; font-weight:bold; margin:0 auto; cursor:pointer;");
			/* end */

			
			$(".wfj12_014_buy_width").find("td").eq(0).css("height",$(window).height()*0.06+"px")
			$(".wfj12_014_pay").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
			$(".wfj12_014_choice").find("img").attr("style"," height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
			$("#money").attr("style","color:#F74C31;");
			$("#money").css("color","#F74C31");
			
			
			$("#pays").find("span").eq(0).attr("style","color:#000; font-size:"+$(window).height()*0.025+"px;");
			$("#pays").find("span").eq(1).attr("style","color:#F74C31; font-size:"+$(window).height()*0.03+"px;");
			
			
			$("#ul").find("li").eq(2).find("img").attr("style","max-height:"+$(window).height()*0.03+"px; width:auto;");
			$(".wfj12_014_top").css("lineHeight",$(window).height()*0.08+"px");
			$(".wfj12_014_name").css("height",$(window).height()*0.155+"px");
			
			$(".wfj12_014_choice").click(function(){
		$(".wfj12_014_choice").find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_02.png");
		$(this).find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_01.png");
		zffs=$(this).find(".second").find("img").attr("name");
});
			
			$(".jqmOverlay").live("click",function(){
				$(".wfj12_014_hidden_buy").fadeOut();
				$("#occlusion2").jqmHide();
			});
			
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
			
            $(".wfj12_014_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height())+"px;");
            $(".wfj12_014_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height())+"px;");
            $(".wfj12_014_hidden").attr("style","width:"+parseInt($(".wfj12_014_content").width()+17)+"px;height:"+$(".wfj12_014_content").height()+"px;");
			$(".wfj12_014_payinfo").attr("style"," padding:"+$(window).height()*0.02+"px 0; margin-bottom:"+$(window).height()*0.02+"px; ");
            $(".wfj12_014_payinfo").find("table").find("tr").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj12_014_payinfo").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_014_payinfo").find("table").find("div").attr("style"," border:"+$(window).height()*0.003+"px solid #CCC; border-radius:"+$(window).height()*0.01+"px; font-size:"+$(window).height()*0.02+"px;");
        	/***********************************css样式调整结束**********************************/
        	
        	$(".wfj_sh").click(function(){
        		document.location.href=basePath+"/ea/hypb/ea_userConfirmationReceipt.jspa?staid="+staid+"&id="+cashid;
        	});
        	if(status=="01"||status=="02"){
				   $("#pp").css("display","none");

             }
        	
        	$(".agree").click(function(){
        		var ppid=$(this).parents("table").find("#ppid").val();
        		if(status=="06"){
   				    window.location.href=basePath+"ea/hypb/ea_refundMobileDetails.jspa?staid="+staid+"&id="+cashid+"&ppid="+ppid+"&status="+status;

                 }else if(status=="00"){
                	 window.location.href=basePath+"ea/bdbill/ea_applyMobile.jspa?type=00&staid="+staid+"&id="+cashid+"&count="+count+"&ppid="+ppid;
                 }else if(status=="05"){
                	 alert("退货详情--申请退货中");
                 }else if(status=="07"){
                	 alert("退货详情--同意退货");
                 }else if(status=="08"){
                	 alert("退货详情--退货成功中");
                 }else if(status=="03"){
                	 window.location.href=basePath+"ea/refund/ea_afterSale.jspa?type=00&staid="+staid+"&id="+cashid+"&ppid="+ppid;
                 }else if(status=="10"){
                	 alert("申请售后中");
                 }else if(status=="11"){
                	 window.location.href=basePath+"ea/hypb/ea_refundMobileDetails.jspa?staid="+staid+"&id="+cashid+"&ppid="+ppid+"&status="+status;
                 }else if(status=="12"){
                	 alert("售后中");
                 }else if(status=="13"){
                	 alert("售后成功");
                 }else{
                	 window.location.href=basePath+"ea/bdbill/ea_applyMobile.jspa?type=01&staid="+staid+"&id="+cashid+"&count="+count+"&ppid="+ppid;          
                 }
        		
			});
      	
        	//保存修改后学员信息
        	$("#tosave").click(function(){       		
        		var noviceName = $("#noviceName").val();
        		var novicePhone = $("#novicePhone").val();        		
        		var noviceCode = $("#noviceCode").val();
        		var noviceAddress = $("#noviceAddress").val();
        		
        		var url = basePath +"ea/bdbill/sajax_updateNovice.jspa?jo="+jum+"&noviceName="+noviceName+"&novicePhone="+novicePhone+"&noviceCode="+noviceCode+"&noviceAddress="+noviceAddress;
        		$.ajax({
        			
        			url : url,
        			type: "get",
        			dataType:"json",
        			success:function(data){
        				var member = eval("("+data+")")
        				var type = member.type;
        				if(type == 0){
        					document.location.href = basePath
							+ "page/ea/not_login.jsp";       				
        				}else{
        					$("#jqModeladd").hide();
        					$("#alert").hide();
        				}        			
        			}  			
        		}); 
        		$("#occlusion2").css("z-index",$(".wfj12_014").css("z-index")+1);
				$("#occlusion2").jqmShow();
				$(".wfj12_014_hidden_buy").css("z-index",$("#occlusion2").css("z-index")+1);
				$(".wfj12_014_hidden_buy").fadeIn(1000);
        		
        		
        		
        	}); 
        	
        	//返回
    		$("#back").click(function(){
    			$("#jqModeladd").hide();	
    			$("#alert").hide();	
    		});
        	/* 
        	  //显示修改学员信息界面     	
        	$("#update").click(function(){   
				var url=basePath+"/ea/bdbill/sajax_getNovice.jspa?jo="+jum;
				$.ajax({
			         url:url,
			         type:"get",
			         async:false,
			         dataType:"json",			       
			         success:function(data){
			         	var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin == 1 ) {
							document.location.href = basePath
									+ "page/ea/not_login.jsp";
						}
						var UserName = member.UserName;					
						var Reference = member.Reference;
						var ReferenceCode = member.ReferenceCode;
						var ReferrerAddress = member.ReferrerAddress;
																		
						$("#noviceName").val(UserName);
						$("#novicePhone").val(Reference);
						$("#noviceCode").val(ReferenceCode);
						$("#noviceAddress").val(ReferrerAddress);
			         },error:function(data){
			          	alert("操作失败！");
			         }
		         });					
				 $("#jqModeladd").show();   
				 $("#alert").show();  
        	});
 */

			$("#zf").click(function(){
				if(confirm("是否要修改学员的信息？")){
					update();
				}else{

					$("#occlusion2").css("z-index",$(".wfj12_014").css("z-index")+1);
					$("#occlusion2").jqmShow();
					$(".wfj12_014_hidden_buy").css("z-index",$("#occlusion2").css("z-index")+1);
					$(".wfj12_014_hidden_buy").fadeIn(1000);					
				}							
			});       	       	  
      	       
        });
    	
    	
    	function update(){
    		var url=basePath+"/ea/bdbill/sajax_getNovice.jspa?jo="+jum;
			$.ajax({
		         url:url,
		         type:"get",
		         async:false,
		         dataType:"json",			       
		         success:function(data){
		         	var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin == 1 ) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var UserName = member.UserName;					
					var Reference = member.Reference;
					var ReferenceCode = member.ReferenceCode;
					var ReferrerAddress = member.ReferrerAddress;
																	
					$("#noviceName").val(UserName);
					$("#novicePhone").val(Reference);
					$("#noviceCode").val(ReferenceCode);
					$("#noviceAddress").val(ReferrerAddress);
		         },error:function(data){
		          	alert("操作失败！");
		         }
	         });					
			 $("#jqModeladd").show();   
			 $("#alert").show();  		
    	};
    	
    		       
        function zf(){
	         if(zffs==null){
                alert("请选择支付方式");
                return false;
         	 }else if($("#addressDetailed").val()==""){
	            alert("请选择收货地址");
	          	return false;
	         }else{
	          	if(zffs=='1'){//支付宝
	 				var par=new Array();
	 				par.push(basePath);
	 				par.push("page/WFJClient/GoodsShow/wfjAlipay.jsp?");
	 				par.push("WIDout_trade_no="+jum);
	 				par.push("&WIDtotal_fee="+morre);
	 				par.push("&WIDsubject="+goodsName);
					par.push("&WIDbody=''");//订单描述
					par.push("&WIDit_b_pay=''");//超时时间
					par.push("&WIDextern_token=''");//钱包
					_AP.pay(encodeURI(par.join("")));
    			}else if(zffs=='3'){
     	 		
     	 			window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+jum+"-"+goodsName+"-"+morre+"-"+staid+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";

     	 		}else if(zffs=='5'){
	    			//钱盒子支付
	    			document.location.href = basePath+"ea/wfjshop/ea_moneyBoxPay.jspa?ddid="+jum+"&morre="+morre;
	    			return false;
	    		}else{
	    			if(token!=0){
	    				return;
	    			}
	    			token=1;
	    			$.ajax({
	    				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
	    				type:"get",
	    				data:"fenlei=03&ddid="+jum,
	    				success:function suc(data){
	    					var mb=eval("("+data+")");
	    					var succ=mb.succ;
	    					var threeNo=mb.threeNo;
	    					if(succ=="success"){
	    						window.location.href=basePath+"page/WFJClient/suc/xxzf.jsp?threeNo="+threeNo;
	    							
	    					};
	    				}
	    				
	    			});
	    			
	    		}
	       	 }
		}
        
      ///删除订单
		function del(){
			var del = confirm("确定删除该订单吗？")
			if(del == true){
				document.location.href=basePath+"/ea/hypb/ea_delBill.jspa?id="+cashid+"&staid="+staid+"&pl="+pl;
		       }else{
		        	 return false; 	
		       }  	    			
		};
        
		function zzqr(){
			$("button#zzqr").attr("disabled","disabled");
			$.ajax({
				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
				type:"get",
				data:"ddid="+jum,
				success:function suc(data){
					var mb=eval("("+data+")");
					var succ=mb.succ;
					if(succ=="success"){
					$("button#zzqr").attr("style","display:none");
						alert("确认成功");
					}
				}
			});
			
		}
						
	</script>
	<script type="text/javascript">
$(function(){
	
	var ua = navigator.userAgent.toLowerCase();
	var isWeixin = ua.indexOf('micromessenger') != -1;
	if (!isWeixin) {
	  $(".wechat").hide();
	}	
	
});

</script>
</body>
</html>
