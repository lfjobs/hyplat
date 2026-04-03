<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath %>css/contacts/style12.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/slideUpDownRefresh_files/iscroll.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap-theme.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/iscroll.css"/>

<title>订单管理-买家</title>
</head>
<script type="text/javascript">
	var basePath="<%=basePath%>";
  	var pl="${pl}";
  	var staid="${staid}";
  	var zffs="1";
  	var jum="";
	var morre="";
	var goodsName="";
	var pagenumber="${pageForm.pageNumber}";
	var ljly="${ljly}";
	var cashierID="";
	var notocen=1;
	var token=0;
	var clientPositioningID="";
	var staffID = "${param.staid}";
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
<body onload="loaded()">
	<div class="wfj12_010">
		<s:if test="ljly==html">
		<!--中联园区头部-->
		<div class="wfj_top">
			<ul>
				<li><a href="javascript:history.go(-1);" target="_self">
					<img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png" />
				</a>
				</li>
				<li>订单管理</li>
				<li><a href="javascript:;"><img	src="<%=basePath %>images/ea/finance/BenDis/top_more.png" />
				</a>
				</li>
			</ul>
		</div>
		<!--中联园区头部 end-->
		</s:if>

		<!--选择状态-->
		<div class="wfj_state2">
			<ul>
				<li id="wfj_dd_">全部订单</li>
				<li id="wfj_dd_01">未付款</li>
				<li id="wfj_dd_00">待发货</li>
				<li id="wfj_dd_02">待收货</li>
			</ul>
		</div>
		<!--选择状态 end-->
	
		<div class="wfj12_010_content">
			<div class="wfj12_010_hidden">
	<div id="wrapper">
	<div id="scroller">
		<div id="scroller-pullDown"><img id="wfj_sx" src="<%=basePath %>images/ea/finance/BenDis/fej_jiazai.gif" width="20px" height="20px">
			<span id="down-icon" class="icon-double-angle-down pull-down-icon"></span>
			<span id="pullDown-msg" class="pull-down-msg sx">下拉刷新</span>
		</div>
				<div class="wfj12_010_con">
					<s:iterator value="pageForm.list" var="pf">
						<div class="wfj12_010_title">
							<div class="wfj12_010_width">
								<ul>
									<li>${pf.companyName }&nbsp;&nbsp;<img
										src="<%=basePath %>images/ea/finance/BenDis/wfj_return_03.png" />
									</li>
									<li><span><%-- 订单编号：${pf.journalNum} --%><fmt:formatDate value="${pf.cashierdate}" pattern="yyyy-MM-dd" /></span></li> 
								</ul>
							</div>
						</div>
						<div class="wfj12_010_product">
							<div class="wfj12_010_width">
								<div class="xq" onclick="javascript:xq('${pf.cashierBillsID }');" id="${pf.cashierBillsID }">
									<input id="jnum" type="hidden" value="${pf.journalNum}">
									<s:iterator value="#pf.goodsList" var="gl" status="gln">
									   <input id="ppid" type="hidden" value="${gl[7]}">
										<table width="94%" class="wfj_goods">
											<tr>
												<td width="30%" rowspan="4"><img width="95%"
													src="<%=basePath %>${gl[6]}" />
												</td>
												<td colspan="3"><span>产品编号：${gl[4]}</span>
												</td>
											</tr>
											<tr>
												<td colspan="2" class="productInfo">${gl[5]}</td>
												<td class="pdright" align="right">￥<span id="price">${gl[3]}</span>
												</td>
											</tr>
											<tr>
												<td width="50%" colspan="2">${gl[11]}</td>
												<td class="pdright" width="20%" align="right">X${gl[2]}</td>
											</tr>
											<tr>
												<td colspan="3"><span>
												</td>
											</tr>
										</table>
										<!-- 促销品 -->
										<s:iterator value="#pf.ptgoodsList" var="pt">
										<table width="94%" class="wfj_goods">
											<tr>
												<td width="30%" rowspan="4"><img width="95%"
													src="<%=basePath %>${pt[6]}" />
												</td>
												<td colspan="3"><span>产品编号：${pt[4]}</span>
												</td>
											</tr>
											<tr>
												<td colspan="2" class="productInfo">${pt[5]}</td>
												<td class="pdright" align="right"><img src="<%=basePath %>/images/WFJClient/Newjspim/cuxiao.png" style="width: 33px;"/></span>
												</td>
											</tr>
											<tr>
												<td width="50%" colspan="2">${pt[10]}</td>
												<td class="pdright" width="20%" align="right"><c:if test="${pt[13] eq '00'}">卖家已付款</c:if></td>
											</tr>
											<tr>
												<td colspan="3"><span><%-- <img
														src="<%=basePath %>images/ea/finance/BenDis/xuanshang.png" />金币：${gl[9]} --%></span>
												</td>
											</tr>
										</table></s:iterator>
									</s:iterator>
								</div>
								<table class="wfj12_010_height">
									<tr>
										<td colspan="2"><span id="price">总计：￥${pf.priceSub}</span>
										</td>
									</tr>
									<tr>
										<td width="30%"></td>
										<td>
											<div class="wfj12_010_button">
									              <input type="hidden" id="id" value="${pf.cashierBillsID}" />
									              <input type="hidden" id="status" value="${pf.fkStatus}" />
									             									             									          							             									              
												<ul>													
													<!--如果只有一个按钮，请给“li”添加添加一个样式“class='right'”-->
													<s:if test="#pf.fkStatus=='00'">
														<li class="" style="float:left;" onclick="update('${pf.journalNum }')"><a href="javascript:;">修改学员</a></li>
														 <li class="pb" style="float:right;" value="00" ><a href="javascript:;">申请退款</a></li>
														 <li class="pb" style="float:right;" value="01" id="cpq"><a onclick="Reminder(this)">提醒发货
														 	<input type="hidden" id="casid" value="${pf.cashierBillsID}" /></a></li>
													</s:if>
													
													<s:if test="#pf.fkStatus=='11'||#pf.fkStatus=='10'||#pf.fkStatus=='12'||#pf.fkStatus=='13'">
														<li class="" style="float:left;" onclick="update('${pf.journalNum }')"><a href="javascript:;">修改学员</a></li>
													 	<li class="pb" style="float:right;" value="00" ><a href="javascript:;">售后详情</a></li>												
													</s:if>
													
													<s:if test="#pf.fkStatus=='05'||#pf.fkStatus=='06'||#pf.fkStatus=='07'||#pf.fkStatus=='08'">
														<li class="" style="float:left;" onclick="update('${pf.journalNum }')"><a href="javascript:;">修改学员</a></li>
													    <li class="pb" style="float:right;" value="00" ><a href="javascript:;">退货详情</a></li>											
													</s:if>
													
													
													<s:if test="#pf.fkStatus=='03'"><!-- 用户收货 -->
														<li class="" style="float:left;" onclick="update('${pf.journalNum }')"><a href="javascript:;">修改学员</a></li>
														<li class="pb" style="float:right;" value="00" ><a href="javascript:;">申请售后</a></li>
																												
													</s:if>																															
													<s:elseif test="#pf.fkStatus=='01'"><!-- 未付款 -->
														<s:if test="#pf.wfStatus4 == '03' ">
															<li class="" style="float:left;" onclick="update('${pf.journalNum }')"><a href="javascript:;">修改学员</a></li>
															<li style="float:right;"><button id="${pf.journalNum }" class="zzqr" onclick="zzqr('${pf.journalNum }')">转账确认</button></li>
														</s:if>														
														<s:else>
															<li style="float:right;" class="ljzf" title="${pf.priceSub}" onclick="ljzf(this)" >
																<input type="hidden" id="journalNum" value="${pf.journalNum}" />
																<input type="hidden" id="priceSub" value="${pf.priceSub}" />
																<input id="projectname" type='hidden' value="${pf.projectName}">
																<a href="javascript:;">立即支付</a> 
															</li>
															<li style="float:right;" class="del" onclick="del('${pf.cashierBillsID }')">
																<a href="javascript:;">删除订单</a>
															</li>
														</s:else>
													</s:elseif>
													
													<s:elseif test="#pf.fkStatus=='02'"><!-- 已经出库，正在物流 -->
														<li class="" style="float:left;" onclick="update('${pf.journalNum }')"><a href="javascript:;">修改学员</a></li>
														<li class="ycsh"><a href="javascript:ycsh('${pf.cashierBillsID}');">延长收货</a></li>
														<li><a href="javascript:;">查看物流</a></li>													
														<li  onclick="wfj_sh('${pf.cashierBillsID }')"><a href="javascript:;">确认收货</a></li>
													</s:elseif>
													<s:else>
														<li class="" style="float:left;" onclick="update('${pf.journalNum }')"><a href="javascript:;">修改学员</a></li>
													</s:else>
												</ul>
											</div></td>
									</tr>
								</table>
							</div>
						</div>
					</s:iterator>
				</div>
															
	<div id="scroller-pullUp"><img src="<%=basePath %>images/ea/finance/BenDis/fej_jiazai.gif" width="20px" height="20px">
		<span id="up-icon" class="icon-double-angle-up pull-up-icon"></span>
		<span id="pullUp-msg" class="pull-up-msg sx">上拉刷新</span>
	</div>
	</div> 
	</div>
			</div>
			
	</div>
	
		<div class="wfj12_010_jqm">
			<div class="wfj12_010_btnum">
				<ul>
					<li id="jian">-</li>
					<li><input type="text" value="1" id="zhi" />
					</li>
					<li id="jia">+</li>
				</ul>
			</div>
			<div class="wfj12_010_bttit">
				<ul>
					<li><span>最多延长三天</span>
					</li>
				</ul>
				<ul>
					<li id="estimatedTime"></li>
				</ul>
			</div>
			<div class="wfj12_010_btcommit">
				<ul>
					<li id="jqmOverlay">取消</li>
					<li id="qr">确认</li>
				</ul>
			</div>
		</div>
		
		<div class="wfj12_014_hidden_buy" style="display:none;">
			<table id="pays" width="100%">
				<tr>
					<td width="50%" style="padding-left:4%;"><span>需支付：</span>
					</td>
					<td align="right" style="padding-right:4%;" width="50%"><span
						id="money" style="color:#F74C31;"></span>
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
							src="<%=basePath %>images/ea/finance/BenDis/choice_01.png" />
						</td>
					</tr>
					<tr class="wfj12_014_choice wechat" >
						<td class="wfj12_014_pay"><img
							src="<%=basePath %>images/ea/finance/BenDis/all_pay_03.png" />
						</td>
						<td class="second" align="right"><img width="24" name="3"
							height="24"
							src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
						</td>
					</tr>
					<tr class="wfj12_014_choice">
						<td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png" />
							</td>
							<td class="second" align="right" ><img width="24" name="4"
							height="24"
							src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
							</td>
						</tr>
					<tr class="wfj12_014_choice">
						<td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/moneybox.png" />
							</td>
							<td class="second" align="right" ><img width="24" name="5"
							height="24"
							src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
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
	<div id="occlusion3" class="jqmWindow jqmWindowcss1"></div>
<form name="formsutm" id="formsutm" method="post">
<s:hidden name="ddid" id="ddid"></s:hidden>
<s:hidden name="baseUrl" id="baseUrl"></s:hidden>
<s:hidden name="morre" id="morre"></s:hidden>
<input type="submit" style="display: none" name="submit" id="submit"/>
</form>

<!-- <div id="alert2" style="width: 100%;position: absolute;top: 0;background: rgba(0, 0, 0, 0.3);display:none;z-index: 99;"></div>
 -->

	<!--搜索窗口 -->
				<form name="addForm" id="addForm" method="post">
				<div id="alert" style="width: 100%;height: 100%;position: absolute;top: 0;background: rgba(0, 0, 0, 0.3);display:none;z-index: 99;"></div>
				
				<div class="wfj12_014_hidden_buy" style="display:none;" id="jqModeladd">			
				<table id="SearchTable" width="100%">
				<tr>
					<td width="50%" style="padding-left:6%;"><span><font color="#660000" size="5%">学员信息</font></span></td>
					<td width="50%" style="padding-right:6%;" align="right" id="back"><span><a href="javascript:"><img  style="height: 40%" src="<%=basePath %>images/ea/finance/BenDis/x.png"></a></span></td>
				</tr>
			</table>
			
			<div class="wfj12_014_buy_width wfj12_014_buy_width2">
			
				<table>
					<tr  class="wfj12_014_choice">
						<td class="wfj12_014_pay" align="right" >订单编号：</td>
						<td>
							<input type="hidden" id="joinput" style="width: 195px" name="jo" />																				
							<span id="nu"></span>
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
				<div onclick="tosave()">保存
					<input type="hidden" id="prices"/>
				</div>
			</div>			
		</div>
		</form>					






	<script type="text/javascript">
		var myScroll,
			upIcon = $("#up-icon"),
			downIcon = $("#down-icon");
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		function loaded () {
			$("#wrapper").css("top",$(".wfj_top").height()+$(".wfj_state2").height()+$(window).height()*0.03+"px");
            //preventDefault为false这行就是解决onclick失效问题
            //为true就是阻止事件冒泡,所以onclick没用
			myScroll = new IScroll('#wrapper', { scrollX: false, scrollY: true, mouseWheel: true,probeType: 3,preventDefault:false});
	
			myScroll.on("scroll",function(){
				var y = this.y,	maxY = this.maxScrollY - y,downHasClass = downIcon.hasClass("reverse_icon"),
					upHasClass = upIcon.hasClass("reverse_icon");
				if(y >= 40){
					!downHasClass && downIcon.addClass("reverse_icon");
					return "";
				}else if(y < 40 && y > 0){
					
					downHasClass && downIcon.removeClass("reverse_icon");
					return "";
				}
				
				if(maxY >= 40){
					!upHasClass && upIcon.addClass("reverse_icon");
					return "";
				}else if(maxY < 40 && maxY >=0){
					upHasClass && upIcon.removeClass("reverse_icon");
					return "";
				}
			});
			
			myScroll.on("slideDown",function(){
				if(this.y > 40){
					console.log("down");
					document.location.href=basePath+"/ea/hypb/ea_getcomporder.jspa?staid="+staid+"&pl="+pl+"&pageForm.pageNumber=1";
					myScroll.refresh();
				}
			});
			
			myScroll.on("slideUp",function(){
				if(this.maxScrollY - this.y > 40){
					console.log("up");
					var pn=parseInt(pagenumber)+1;
					 if(pn<=parseInt("${pageForm.pageCount}")){
						pn=(pn>parseInt("${pageForm.pageCount}")?parseInt("${pageForm.pageCount}"):pn);
						var url=basePath+"/ea/hypb/sajax_getAjax.jspa?";
						$.ajax({
							url : url,
							type : "get",
							async : false,
							dataType : "json",
							data:{
							  "staid":staid,
							  "pl":pl,
							  "pageForm.pageNumber":pn
							},
							success : function (data) {
								var member = eval("(" + data + ")");
								var pageForm = member.pageForm;
								//pn=pageForm.pageNumber;
								var cashlist=pageForm.list;
								var goodstr="";
								for ( var i = 0; i < cashlist.length; i++) {
									var cash=cashlist[i];
									goodstr+= "<div class='wfj12_010_title'><div class='wfj12_010_width'><ul><li>"+cash.companyName+"&nbsp;&nbsp;";
									goodstr+="<img src='"+basePath+"images/ea/finance/BenDis/wfj_return_03.png' /></li><input type='hidden' id='id' value='"+cash.cashierBillsID+"' /></ul></div></div>";
									
									goodstr+="<div class='wfj12_010_product'><div class='wfj12_010_width'><div class='xq' onclick='javascript:xq(\""+cash.cashierBillsID+"\");' id='"+cash.cashierBillsID+"'><input id='jnum' type='hidden' value='"+cash.journalNum+"'>";
									var goodsList=cash.goodsList;
									for ( var j = 0; j < goodsList.length; j++) {
										var goodbill=goodsList[j];
										goodstr+="<table width='94%' class='wfj_goods'><tr>";
										goodstr+="<td width='30%' rowspan='4'><img width='95%' src='"+basePath+goodbill[6]+"' /></td>";
										goodstr+="<td colspan='3'><span>产品编号："+goodbill[4]+"</span></td>";
										goodstr+="</tr><tr>";
										goodstr+="<td colspan='2' class='productInfo'>"+goodbill[5]+"</td>";
										goodstr+="<td class='pdright' align='right'><span>￥"+goodbill[3]+"</span></td>";
										goodstr+="</tr><tr>";
										goodstr+="<td width='25%'>颜色：黑色</td>";
										goodstr+="<td width='25%'>尺码：42</td>";
										goodstr+="<td class='pdright' width='20%' align='right'>X"+goodbill[2]+"</td>";
										goodstr+="</tr><tr>";
										//goodstr+="<td colspan='3'><span><img src='"+basePath+"images/ea/finance/BenDis/xuanshang.png' />金币："+goodbill[9]+"</span></td>";
										goodstr+="</tr></table>";
									}
									goodstr+="</div>";
									
									goodstr+="<table class='wfj12_010_height'><tr><td colspan='2'><span>总计：￥"+cash.priceSub+"</span></td></tr>";
									goodstr+="<tr><td width='30%'></td><td><div class='wfj12_010_button'><ul>";
									if(cash.fkStatus=="00"){//已付款
										goodstr+="<li style='float:right;'><a href='javascript:;'>提醒发货</a></li>";
									}else if(cash.fkStatus=="01"){//未付款
										goodstr+="<li style='float:right;' class='ljzf' onclick='ljzf(this)'  title='"+cash.priceSub+"'><input id='projectname' type='hidden' value='"+cash.projectName+"'><a href='javascript:;'>立即支付</a></li>";						
									}else if(cash.fkStatus=="02"){//已出库，正在物流
										goodstr+="<li class='ycsh' ><a href='javascript:ycsh('"+cash.cashierBillsID+"');'>延长收货</a></li>";
										goodstr+="<li><a href='javascript:;'>查看物流</a></li>";
										goodstr+="<li onclick='wfj_sh('"+cash.cashierBillsID+"')'><a href='javascript:;'>确认收货</a></li>";
									}
									goodstr+="</ul></div></td></tr></table>";
									goodstr+="</div></div>";
									
								}
								$("div.wfj12_010_con").append(goodstr);
								console.log($("div.wfj12_010_con").height());
								$(".wfj12_010_title").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px;");
					            $(".wfj12_010_title").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
					            $(".wfj12_010_title").find("li").find("img").attr("style","height:"+$(window).height()*0.015+"px;");
								$(".wfj12_010_product").find("table").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #DEDEDE;padding-top:"+$(window).height()*0.01+"px;");
					            $(".wfj12_010_product").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
					            $(".wfj12_010_product").find("table").find("span").attr("style","font-size:"+$(window).height()*0.021+"px;");
					            $(".wfj12_010_product").find("table").find("span").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto; vertical-align:middle");
					            $(".wfj12_010_product").find("table").find("a").attr("style","font-size:"+$(window).height()*0.02+"px; padding:"+$(window).height()*0.002+"px "+$(window).height()*0.01+"px; border:"+$(window).height()*0.002+"px solid #666; border-radius:"+$(window).height()*0.005+"px;");
								$(".wfj12_010_content").attr("style","width:"+$(window).width()+"px;");
								$(".wfj12_010_hidden").attr("style","overflow-y: auto;");
								myScroll.refresh();
							},
							error:function(data){
								alert("获取单据失败");
							}
						}); 
					}else{
						if(pn>0){
							alert("已无更多单据");
						}
					}
					pagenumber=pn;
				}
			});
		}
	</script>
	<script type="text/javascript">
    	$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			$("#wrapper").find(".sx").attr("style","font-size:"+$(window).height()*0.018+"px;");
            $(".wfj_state2").attr("style","height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_state2").find("li").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #dedede; font-size:"+$(window).height()*0.025+"px;");
            $(".wfj_state2").find("#wfj_dd_"+pl).attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #F74C31; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;color:#F74C31;");
			
			$(".wfj_state2").find("li").click(function(){
				var a=$(this).attr("id").substring(7);
				$(".wfj_state").find("li").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #dedede; font-size:"+$(window).height()*0.025+"px;");
				$(this).attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #F74C31; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;color:#F74C31;");
				document.location.href=basePath+"ea/hypb/ea_getcomporder.jspa?staid="+staid+"&pl="+a;
			});
			
            $(".wfj12_010_title").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px;");
            $(".wfj12_010_title").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_010_title").find("li").find("img").attr("style","height:"+$(window).height()*0.015+"px;");
            $(".wfj12_010_product").find("table").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #DEDEDE;padding-top:"+$(window).height()*0.01+"px;");
            $(".wfj12_010_product").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_010_product").find("table").find("span").attr("style","font-size:"+$(window).height()*0.021+"px;");
            $(".wfj12_010_product").find("table").find("span").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto; vertical-align:middle");
            $(".wfj12_010_product").find("table").find("a").attr("style","font-size:"+$(window).height()*0.02+"px; padding:"+$(window).height()*0.002+"px "+$(window).height()*0.01+"px; border:"+$(window).height()*0.002+"px solid #666; border-radius:"+$(window).height()*0.005+"px;");
            $(".wfj12_010_height").find("tr").attr("style","height:"+$(window).height()*0.03+"px;line-height:"+$(window).height()*0.03+"px;font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_010_height").find("tr").find("span").css("fontSize",$(window).height()*0.025+"px");
			
            $(".pdright").css("paddingRight",$(window).height()*0.01+"px");
			//隐藏滚动条
			$(".wfj12_010_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj12_010").height())+"px;");
			$(".wfj12_010_hidden").attr("style","height:"+parseInt($(".wfj12_010_content").height())+"px;");
			
			
		    if($(".wfj12_010_content").css("height").split("px")[0]<$(window).height()){
				$(".wfj12_010_content").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()*0.89+"px;");
			} 

			var h = $(".wfj12_010_title").height()*$(".wfj12_010_title").length+$(".wfj12_010_product").height()*$(".wfj12_010_product").length;
			if(h < $(".wfj12_010_content").height()){
				$(".wfj12_010_hidden").css("width",$(".wfj12_010_content").width()+"px");
				$("#scroller").css("height",$(".wfj12_010_hidden").height()+"px");
			}else{
				$(".wfj12_010_hidden").css("width",parseInt($(".wfj12_010_content").width()+17)+"px");
			}

			$(".wfj12_010_jqm").attr("style","top:"+$(window).height()*0.4+"px;bottom:"+$(window).height()*0.4+"px; border-radius:"+$(window).height()*0.03+"px;position:absolute;");
			$(".wfj12_010_btnum").find("li").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; margin-top:"+$(window).height()*0.02+"px;font-size:"+$(window).height()*0.03+"px;");
			$(".wfj12_010_btnum").find("li").find("input").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px;");
			$(".wfj12_010_bttit").find("li").attr("style","height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px;font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_010_btcommit").attr("style"," border-top:"+$(window).height()*0.002+"px solid #CCC;");
			//alert($(window).width()*0.5-$(window).height()*0.001)
			$(".wfj12_010_btcommit").find("li").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px;");
			$(".wfj12_010_btcommit").find("li").eq(0).css("borderRightStyle","solid");
			$(".wfj12_010_btcommit").find("li").eq(0).css("borderRightColor","#CCC");
			$(".wfj12_010_btcommit").find("li").eq(0).css("borderRightWidth",$(window).height()*0.002+"px");
			$(".wfj12_014_bottom_commit").attr("style","height:"+$(window).height()*0.05+"px; padding:"+$(window).height()*0.01+"px 0;");
			$(".wfj12_014_bottom_commit").css({"display":"table-cell","vertical-align":"middle","width":$(window).width()+"px"})
			$(".wfj12_014_bottom_commit").find("div").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");
			
			$(".wfj11_015_need").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj11_015_need").find("li").attr("style","font-size:"+$(window).height()*0.03+"px;color:#000;");
			$(".wfj11_015_need").find("li").find("span").attr("style","font-size:"+$(window).height()*0.03+"px;color:#F74C31;");
			$(".wfj11_015_allbay").find("td").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj11_015_allbay").find("td").eq(0).css("height",$(window).height()*0.05+"px")
			$(".wfj11_015_allbay").find("tr").eq(5).css("height",$(window).height()*0.1+"px")
			$(".wfj11_015_allbay").find("td").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto");
			$(".wfj11_015_allbay").find("td").find(".all_pay").attr("style","height:"+$(window).height()*0.04+"px;");
			$(".wfj11_015_allbay").find("td").find("#paycommit").attr("style"," width:50%; background-color:#F74C31; color:#FFF; cursor:pointer; border-radius:"+$(window).height()*0.006+"px; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
            
			
			
            $(".wfj12_014_hidden_buy").css({"bottom":"0"})
			$(".wfj12_014_hidden_buy").find("td").attr("style"," height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.025+"px; color:#660000;");
			$(".wfj12_014_buy_width").find("td").attr("style"," height:"+$(window).height()*0.1+"px;font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_014_buy_width").find("td").eq(0).css("height",$(window).height()*0.06+"px")
			$(".wfj12_014_pay").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
			$(".wfj12_014_choice").find("img").attr("style"," height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
			$("#money").attr("style","color:#F74C31;");
			$("#money").css("color","#F74C31");
			
			$("#pays").find("span").eq(0).attr("style","color:#000; font-size:"+$(window).height()*0.025+"px;padding-left:20px;");
			$("#pays").find("span").eq(1).attr("style","color:#F74C31; font-size:"+$(window).height()*0.03+"px;padding-right:20px;");
			
			$(".wfj12_010_jqm").css("display","none");

			/* lx 9.8*/
			$(".wfj12_014_buy_width2").find("td").attr("style"," height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px");
			$(".wfj12_014_bottom_commit2").find("div").attr("style"," width:25%; height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px; background-color:#F74C31; border-radius:"+$(window).height()*0.01+"px; color:#FFF; text-align:center; font-size:"+$(window).height()*0.02+"px; font-weight:bold; margin:0 auto; cursor:pointer;");
			$("#alert2").css("height",$(window).height()*0.1495+"px");
			/* end */
			
			$(".wfj12_014_choice").click(function(){
		$(".wfj12_014_choice").find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_02.png");
		$(this).find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_01.png");
		zffs=$(this).find(".second").find("img").attr("name");
});
			
			
			var value="";
		    $(".wfj12_010_button li a").click(function(){
		    	var ppid=$(this).parents("div").find("input#ppid").val();
		    	var cashid=$(this).parents("div").find("input#id").val();
		    	var status=$(this).parents("div").find("input#status").val();
		    	 value=$(this).text();
		    	 if(value=="退货详情"){
		    		 if(status=="05"){
		    			 alert("退货详情--申请退货中");
		    		 }else if(status=="07"){
		    			 alert("退货详情--退货中");
		    		 }else if(status=="08"){
		    			 alert("退货详情--退货成功");
		    		 }else{
		    			 alert("退货地址");
		    			 window.location.href=basePath+"ea/hypb/ea_refundMobileDetails.jspa?staid="+staid+"&id="+cashid+"&ppid="+ppid+"&status="+status;
		    		 }
		    	 }

		    	 if(value=="申请退款"){
		    	window.location.href=basePath+"ea/bdbill/ea_applyMobile.jspa?type=00&staid="+staid+"&id="+cashid+"&count=1&ppid="+ppid;
		      }
		       if(value=="申请售后"){
		    	   var price =$(this).parents("table").find("span#price").text().substr(4,6);
			    	window.location.href=basePath+"ea/refund/ea_afterSale.jspa?type=00&staid="+staid+"&id="+cashid+"&count=1&ppid="+ppid+"&rs.refundMoney="+price;
			      }
		       if(value=="售后详情"){
		    	   var price =$(this).parents("table").find("span#price").text().substr(4,6);		    	   
	 	    	    if(status=="10"){
		    	    	alert("申请售后中...");
		    	    	window.location.href=basePath+"ea/refund/ea_AfterRefundDetail.jspa?type=00&staid="+staid+"&id="+cashid+"&count=1&ppid="+ppid+"&rs.refundMoney="+price;
		    	    }else if(status=="12"){
		    	    	alert("售后中...");
		    	    }else if(status=="13"){
		    	    	alert("售后成功...");
		    	    }else{
				    	window.location.href=basePath+"ea/hypb/ea_refundMobileDetails.jspa?staid="+staid+"&id="+cashid+"&ppid="+ppid+"&status="+status;
		    	    }
			      }
		    });
					
					
				
			$(".jqmOverlay").live("click",function(){
				$(".wfj12_010_jqm").fadeOut();
				$("#occlusion2").jqmHide();
			});
			
			$("#jqmOverlay").live("click",function(){
				$(".wfj12_010_jqm").fadeOut();
				$("#occlusion2").jqmHide();
			});
									
			$(".jqmOverlay").live("click",function(){
				$(".wfj12_014_hidden_buy").fadeOut();
				$("#occlusion3").jqmHide();
			});
			
			$("#qr").live("click",function(){
				$(".wfj12_014_hidden_buy").fadeOut();
				$("#occlusion3").jqmHide();
			});
			
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
			
			$("#jia").click(function(){
				var zhi=parseInt($("#zhi").val())+1;
				$("#zhi").val(zhi>=3?3:zhi);
			});
			$("#jian").click(function(){
				var zhi=parseInt($("#zhi").val())-1;
				$("#zhi").val(zhi<=1?1:zhi);
			});
			$("#qr").click(function(){
				if(confirm("是否延长收获时间")){
						 $.ajax({
					        	url:basePath+"/ea/hypb/sajax_extendedReceipt.jspa?cashierbillsID="+cashierID+"&zhi="+$("#zhi").val(),
					        	type:"post",
					        	success:function(data){
						        		$("#occlusion2").css("z-index",$(".wfj12_010").css("z-index")+1);
										$("#occlusion2").jqmHide();
										$(".wfj12_010_jqm").css("z-index",$("#occlusion2").css("z-index")+1);
										$(".wfj12_010_jqm").fadeOut(1000);
					        			alert("操作成功")
					        	},error:function(data){
					        		alert("数据获取失败");
					        	}
				        });
					}
				});						       	 
        });
        
       function wfj_sh(cid){
       		if(confirm("收货之后将不能退货，确定要确定收货？")){
				if(notocen==1){
					notocen=2;
					document.location.href=basePath+"/ea/hypb/ea_userConfirmationReceipt.jspa?staid="+staid+"&id="+cid;
				}
			}
       	};
       	
        //显示修改学员信息界面
		function update(num,price){		
			$("#nu").text(num);
			$("#prices").val(price);
			var url=basePath+"/ea/bdbill/sajax_getNovice.jspa?jo="+num;
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
		         }
	         });					
			 $("#jqModeladd").show();
			 $("#alert").show();			
		};
       	      	
       	 	//立即支付的方法
        function ljzf(el){       	 		
        	if(confirm("是否要修改学员的信息？")){
        		var num = $(el).find("#journalNum").val();
        		var price = $(el).find("#priceSub").val();       			
        		update(num,price); //调   修改学员    界面
        	}else{
        		var mo=$(el).attr("title");
    			console.log($(el));
    			$("#money").text("￥"+mo);
    			$("#occlusion3").css("z-index",100);
    			$("#occlusion3").jqmShow();
    			$(".wfj12_014_hidden_buy").css("z-index",101);
    			$(".wfj12_014_hidden_buy").fadeIn(1000);
    			var $tb=$(el).parent().parent().parent().parent().parent().parent().parent();
    			jum=$tb.find(".xq").attr("id");
    			morre=mo;
    			/* $tb.find("table.wfj_goods").each(function (){
    				goodsName+=" "+$(this).find("tr").eq(1).find("td").eq(0).text();
    			}); */ 
    			goodsName=$(el).find("#projectname").val();
    			 $("#jqModeladd").hide();
        	}    					
		}; 
				
		 //保存修改后学员信息		 
		 function tosave(){			 
			    var num = $("#nu").text();	
	    		var noviceName = $("#noviceName").val();
	    		var novicePhone = $("#novicePhone").val();        		
	    		var noviceCode = $("#noviceCode").val();
	    		var noviceAddress = $("#noviceAddress").val();
	    		
	    		var url = basePath +"ea/bdbill/sajax_updateNovice.jspa?jo="+num+"&noviceName="+noviceName+"&novicePhone="+novicePhone+"&noviceCode="+noviceCode+"&noviceAddress="+noviceAddress;
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
	    				}
	    				$("#jqModeladd").hide();
						$("#alert").hide(); 
	    			}  			
	    		});	        		
	    		//调出支付页面 
	    		var status = "${pf.fkStatus }";
	    		var status4 = "${pf.wfStatus4 }";
	    			    		
	    		if(status=="01" && status4=="03" ){
	    			$("#jqModeladd").hide();
					$("#alert").hide(); 
	    		}else if(status !="01"){
	    			$("#jqModeladd").hide();
					$("#alert").hide();
	    		}else{
	    			var mo = $("#prices").val();       	
    				$("#money").text("￥"+mo);
    				$("#occlusion3").css("z-index",100);
    				$("#occlusion3").jqmShow();
    				$(".wfj12_014_hidden_buy").css("z-index",101);
    				$(".wfj12_014_hidden_buy").fadeIn(1000);
    				var $tb=$(el).parent().parent().parent().parent().parent().parent().parent();
    				jum=$tb.find(".xq").attr("id");
    				morre=mo;
    				$tb.find("table.wfj_goods").each(function (){
    					   //goodsName+=" "+$(this).find("tr").eq(1).find("td").eq(0).text();
    					    goodsName+=$(this).find("tr").eq(1).find("td").eq(0).text()+",";
    						goodsName=goodsname.substring(0,goodsname.length-1);
    					
    				});
	    		}	    			    			    	
		 };
		 		 		 		    					
		//删除订单
		function del(cbid){
			var del = confirm("确定删除该订单吗？");
			if(del == true){
				document.location.href=basePath+"/ea/hypb/ea_delBill.jspa?id="+cbid+"&staid="+staid+"&pl="+pl;
		       }else{
		        	 return false; 	
		       }  	    			
		};
						
		//返回
		$("#back").click(function(){
			$("#jqModeladd").hide();
			$("#alert").hide();		
		});
						
        function xq(cashid){
			document.location.href=basePath+"/ea/hypb/ea_getCashBill.jspa?staid="+cashid+"&id="+$("#id").val();
		}
        
        function ycsh(obj){
	        $.ajax({
	        	url:basePath+"/ea/hypb/sajax_QueryWhetherToExtendTheReceipt.jspa?cashierbillsID="+obj,
	        	type:"post",
	        	success:function(data){
	        		var member=eval("("+data+")");
	        		var sst=member.status;
	        		if(sst=="00"){
	        		var time=member.time;
	        			cashierID=obj;
		        		$("#occlusion2").css("z-index",$(".wfj12_010").css("z-index")+1);
						$("#occlusion2").jqmShow();
						$(".wfj12_010_jqm").css("z-index",$("#occlusion2").css("z-index")+1);
						$(".wfj12_010_jqm").fadeIn(1000);
						$(".wfj12_010_jqm").find("#estimatedTime").text("预计将在"+time+"收货哦");
	        		}else{
	        			alert("收货时间已延长")
	        		}
	        	},error:function(data){
	        		alert("数据获取失败");
	        	}
	        });
		}
        function zf(){
	         if(zffs==null){
                alert("请选择支付方式");
                return false;
         	 }else if($("#addressDetailed").val()==""){
	            alert("请选择收货地址");
	          	return false;
	         }else{
	          	if(zffs=='1'){
  					// window.location.href=  encodeURI(basePath+"page/WFJClient/GoodsShow/cg.jsp");
	 				var par=new Array();
	 				par.push(basePath);
	 				par.push("page/WFJClient/GoodsShow/wfjAlipay.jsp?");
	 				par.push("WIDout_trade_no="+$("#"+jum).find("#jnum").val());
	 				par.push("&WIDtotal_fee="+morre);
	 				par.push("&WIDsubject="+goodsName);
					par.push("&WIDbody=''");//订单描述
					par.push("&WIDit_b_pay=''");//超时时间
					par.push("&WIDextern_token=''");//钱包
					_AP.pay(encodeURI(par.join("")));
    			}else if(zffs=='3'){
     				var ddid=$("#"+jum).find("#jnum").val();
     	 			window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+ddid+"-"+goodsName+"-"+morre+"-"+staffID+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";
     	 			
	       	 	}else if(zffs=='5'){
	       	 	//钱盒子支付
			      document.location.href = basePath+"ea/wfjshop/ea_moneyBoxPay.jspa?ddid="+$("#"+jum).find("#jnum").val()+"&morre="+morre;
	       	 	}else{
	       	 	
	       	 		if(token!=0){
				     return;
			        }
			         token=1;
			$.ajax({
				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
				type:"get",
				data:"fenlei=03&ddid="+$("#"+jum).find("#jnum").val(),
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
		function zzqr(jnum){
			$("button#"+jnum).attr("disabled","disabled");
			$.ajax({
				url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
				type:"get",
				data:"ddid="+jnum,
				success:function suc(data){
				var member=eval("("+data+")");
				var suc=member.succ;
					if(suc=="success"){
					$("button#"+jnum).attr("style","display:none");
						alert("确认成功");
						window.location.reload();
					}
				}
			});
		}
		function Reminder(obj){
		var ddid = $(obj).parents(".wfj12_010_width").find("#jnum").val();
			var casid = $(obj).find("#casid").val();
			 $.ajax({
					url:basePath+"ea/wfjshop/sajax_ea_ReminderDelivery.jspa?ddid="+ddid+"&cashierBillsID="+casid,
					type:"get",
					async : false,
					dataType : "json",
					success:function suc(data){
					var member=eval("("+data+")");
					var suc=member.txin;
						
							alert(suc);
							window.location.reload();
					
				}
		
		});
		$("#cpq").css("display","none");
		}		
		
    </script>
</body>
</html>