<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<link href="<%=basePath%>css/contacts/Restaurant/style11.css" rel="stylesheet"
		type="text/css" />
	<link href="<%=basePath%>css/contacts/Restaurant/jqModal_blue.css" rel="stylesheet"
		type="text/css" />
		 <script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
	 <script type="text/javascript" src="<%=basePath%>js/fontscroll.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript"  src="<%=basePath%>js/restaurant/restaurant.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/ea/unmannedsupermarket/font-size.js"></script>

	<title>餐饮确认订单</title>
</head>
<style>

*{ margin:0; padding:0; list-style:none;}
    #FontScroll{background:#fff;width:90%;height:50px;line-height:50px;overflow:Hidden;padding:5px 0;margin:5px auto;}
    #FontScroll .line{text-align:left;width:78%;}
    #FontScroll .fontColor a{color:red;}
    #FontScroll ul li span{
        font-size: 16px;
        word-wrap:break-word;
    }
    #FontScroll ul{
        float: right;
        width:78%;
    }
    @media screen and (min-width: 765px){
        #FontScroll{
            height:100px;line-height:100px;
        }
        #FontScroll ul li span{
            font-size: 30px;
        }
    }
</style>
<script type="text/javascript">
	var adrss='${staffaddress.addressDetailed}';
	var companyId='${companyId}';
    var ccompanyId='${ccompanyId}';
    var  companyName = "${param.companyName}";
	var basePath='<%=basePath%>';
	var ddid;
	var zffs = 3;//选择的支付方式   默认为支付宝
	var producttype = "${productDesign.type}";
	var model = "${productDesign.model}";
	var token=0;
	var morre=$("#morre").val();
	var staffID = "${staffID}";
	var sccid = "${sccid}";
	var selecton="${param.selecton}";
	var scandc = "${param.scandc}";
    var addressID = "${staffAddress.addressID}";
    var consignee = "${staffAddress.consignee}";
    var phone = "${staffAddress.phone}";
    var noviceAddress = "${staffAddress.area}${staffAddress.addressDetailed }";
    var mode = "${param.mode}";
    var journalNum = "${param.journalNum}";
    var totalMoney = "${param.totalMoney}";
    var totalNum = "${param.totalNum}";
    var cardNum = "${param.cardNum}";
    var paymentCode = "${param.paymentCode}";
    var vipmoney = "${param.vipmoney}";
    var sccId = "${param.sccId}";
    var posNum = "${param.posNum}";
    var postype = "${param.postype}";
    var user = "${user}";
    var openid = "${param.openid}";
    $(function(){
        var pl = "${param.pl}";
        var zh = "<%=session.getAttribute("canzuo")%>";
        if(pl==""||pl=="null")
        {
            if(scandc=="scandc") {
                if (zh != "" && zh != "null") {
                    $("#privateRoom").val(zh);

                }
            }
       }

       if(scandc=="scandc"){
            $(".bk").html("&nbsp;");

       }

	});



	

	
</script>
<script type="text/javascript">
$(function(){
	var ua = navigator.userAgent.toLowerCase();
	var isWeixin = ua.indexOf('micromessenger') != -1;
	if (!isWeixin) {
	   if(ua.indexOf("browser")!=-1){
		$(".wechat").hide();
		
	    }else{
		   $(".wechat").show();
		
	   }
	}
	
	
});

</script>

<body>
<div class="loading" style="display:none;">
	<img src="<%=basePath%>images/WFJClient/Newjspim/loading.gif"/>
	<p><span>加载中...</span></p>
</div>
	<div class="wfj11_015">
    	<div class="wfj11_015_top">
        	<ul id="tops">
				<c:if test="${param.backurls ne null}">
					<li  class="bk" ><a href="javascript: window.history.go(-1);" target="_self"><img src="<%=basePath%>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" /></a></li>

				</c:if>
				<c:if test="${param.backurls eq null}">
					<li  class="bk" ><a href="<%=basePath%>ea/industry/ea_CompanyProducts.jspa?companyId=${companyId}&ccompanyId=${ccompanyId}" target="_self"><img src="<%=basePath%>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" /></a></li>

				</c:if>

            	<li>确认订单</li>
            </ul>
        </div>
        <div class="wfj11_015_top_hide"></div>
		<!-- 选择模式-->
		<div class="wfj11_015_mo">
			<ul>
				<li class="on">外卖送货</li>
				<li>餐厅就餐</li>

			</ul>
		</div>
		
		<form  id="formsutm" name="formsutm" method="post">
		
		<input type="submit" name="submit" style="display:none;">
		<input type="hidden"   id="sortid" name="sort"/>
		<%-- 
		 <input type="hidden" name="companyId" class="companyId" value="${companyId}"/>
       <input type="hidden" class="ccompanyId" value="${ccompanyId}"/> --%>
           <input type="hidden" class="pid" name="pid"  value="${pid}"/>
           <input type="hidden" class="companyId" name="companyId"  value="${companyId}"/>

			<div class="wfj11_015_jiucan" style="padding: 5px 0;height: 40px;">
				<span class="wfj11_015_jiucan_moren" style="display: inline-block;width: 15%;vertical-align: middle;white-space: nowrap">桌号：</span>
				<input type="text" name="privateRoom" id="privateRoom" value="${param.pl}" style="display: inline-block;width: 25%;vertical-align: middle;padding: 5px 0;"/>
				<span onclick="saoyisao()" style="display: inline-block;width: 20%;vertical-align: middle;text-align: right">扫桌号</span>
				<img style="vertical-align: middle;" src="<%=basePath%>images/contacts/restaurant/scanCodePay/saosao.png" width="18px" height="18px"onclick="saoyisao()"/>
				<%--<span class="wfj11_015_jiucan_zhi1">餐桌：${params.pl}</span>--%>
				<%--<input type="hidden" name="privateRoom" id="privateRoom" value="${privateRoom}"/>--%>
				<%--<span class="wfj11_015_jiucan_zhi2">扫一扫：${waiter}</span>--%>
				<input type="hidden" name="waiter" id="waiter" value="${waiter}"/>


			</div>

		<div class="wfj11_015_hide">
        <a href="javascript:addAddress();" target="_self">
            <div class="wfj11_015_consignee">
                <div class="wfj11_015_width">
                    <table style="position:relative;">
						<tr>
							<td width="13%"><input type="hidden"
								value="${staffAddress.addressID}" name="staffAddress.addressID"
								id="addressDetailed" /></td>
							<td width="40%">收货人：<span>${staffAddress.consignee}</span>
							</td>
							<td width="40%">${staffAddress.phone}</td>
							<td width="10%"></td>
						</tr>
						<tr>
							<td>
							</td>
							<td colspan="2">收货地址：${staffAddress.area}${staffAddress.addressDetailed }</td>
							<td align="right">
							</td>
						</tr>
					</table>
                </div>
					<div style="position:absolute;top:0;left:5px;" class="img_ico img_ico1">
						<p style="display:table-cell;vertical-align:middle;">
							<img style="width:60%;" class="left"
										src="<%=basePath%>/images/WFJClient/Newjspim/wfj11_address_01.png" />
						</p>
					</div>
					<div style="position:absolute;top:0;right:10px;" class="img_ico">
						<p style="display:table-cell;vertical-align:middle;">
							<img class="right" style="width:50%;"
										src="<%=basePath%>/images/WFJClient/Newjspim/wfj_return_03.png" />
						</p>
				</div>
            </div>
        </a>


				<%--<div id="FontScroll">--%>
					<%--<div class="gonggao_tu"--%>
						<%--style="float: left; width: 20%;height: 100%;text-align: left;border-right: 1px solid #e3e3e3;">--%>
						<%--<p style="display: block;line-height: 50px;height: 100%;">--%>
							<%--<img src="<%=basePath%>images/ea/finance/BenDis/jinbi_03.jpg"--%>
								<%--alt="" style="width: 70%;height: 90%;display:display;margin-top:3px;" />--%>
						<%--</p>--%>
					<%--</div>--%>
					<%--<ul>--%>
						<%--<c:forEach items="${jf}" var="d">--%>
							<%--<li><img--%>
								<%--src="<%=basePath%>images/ea/finance/BenDis/hongdian_06.png"--%>
								<%--alt="" style="width: 12px;" /> <span>恭喜${d[1]}获得${d[0]}枚金币</span>--%>
							<%--</li>--%>
						<%--</c:forEach>--%>
					<%--</ul>--%>
				<%--</div>--%>
		<div  class="wfj11_015_kuang">
        <div class="wfj11_015_com">
        	<div class="wfj11_015_width">
            	<a href="<%=basePath%>/ea/industry/ea_getCompanyHome.jspa?ccompanyId=${ccompanyId}">
            				${company.companyName}
            				<input type="hidden" value="${companyId}" class="comid"/>

            	</a>
            </div>
        </div>
        
        <div class="wfj11_015_proinfo">
        <c:forEach items="${ppklist}" var="lis">
        	<div class="wfj11_015_width">
            	<table width="100%"  class="ptbl">
                	<tr>
                    	<td width="30%" rowspan="3">
							<c:choose>
								<c:when test="${lis[10]!=null&& lis[8]!=null}">
									<%--活动价--%>
									<c:if test="${lis[10]=='00'}"><%--促销活动--%>
										<span class="cx"><i></i></span>
									</c:if>
									<c:if test="${lis[10]=='01'}"><%--特价活动--%>
										<span class="tj"><i></i></span>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${lis[9]!=null}">
											<span class="vip"><i></i></span><%--VIP价--%>
										</c:when>
										<c:otherwise>
											<%--零售价--%>
										</c:otherwise>
									</c:choose>

								</c:otherwise>
							</c:choose>

							<img style="width:90%;" id="ind" src="<%=basePath%>${lis[2]}" />
						</td>
						<td width="35%"  colspan="2" >${lis[1]}
                    	<input type="hidden"  value="${lis[1]}"  class="goodsname"/>
                    	<input type="hidden" id="ppid" class="ppid" value="${lis[0]}"/></td>
                    </tr>

                	<tr>
                    	<td>${lis[7]}</td>
                    	<input type="hidden" class="stardard" id="stardard" name="stardard" value="${lis[7]}"/></span></td>
                    </tr>
					<tr>
						<td>￥

							<c:choose>
								<c:when test="${lis[10]!=null&& lis[8]!=null}">
									<fmt:formatNumber value="${lis[8]*pct}" type="NUMBER" maxFractionDigits="2"/><%--活动价--%>
									<input type="hidden" name="priceid" class="priceid" value="${pklist[13]}"/>
									<input type="hidden" name="prt" class="prt" value="3"/>
									<input type="hidden" name="ccj" class="ccj" value="${pklist[14]}"/>
									<input type="hidden" name="price" class="price" value="<fmt:formatNumber pattern="0.00" value="${pklist[8]*pct}"/>"/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${lis[9]!=null}">
											<fmt:formatNumber value="${lis[9]*pct}" type="NUMBER" maxFractionDigits="2"/><%--VIP价--%>
											<input type="hidden" name="priceid" class="priceid" value="${lis[15]}"/>
											<input type="hidden" name="prt" class="prt" value="2"/>
											<input type="hidden" name="ccj" class="ccj" value="${lis[16]}"/>
											<input type="hidden" name="price" class="price" value="<fmt:formatNumber pattern="0.00" value="${lis[9]*pct}"/>"/>
										</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${lis[4]*pct}" type="NUMBER" maxFractionDigits="2"/><%--零售价--%>
											<input type="hidden" name="priceid" class="priceid" value="${lis[11]}"/>
											<input type="hidden" name="prt" class="prt" value="0"/>
											<input type="hidden" name="ccj" class="ccj" value="${lis[12]}"/>
											<input type="hidden" name="price" class="price" value="<fmt:formatNumber pattern="0.00" value="${lis[4]*pct}"/>"/>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</td>
						<td width="35%" align="right" >×<span class="number1">${lis[6]}
                    	<input type="hidden" class="number" id="number" value="${lis[6]}"/></span></td>
					</tr>
                </table>
            </div>
           </c:forEach>
        </div>
        
          <div class="wfj11_015_delivery">
        	<div class="wfj11_015_width">
            	<table>
                	<tr>
                    	<td>买家留言</td>
                    	<td><input type="text"  placeholder="选填" class="leavemessage"  />
                    	<input type="hidden"  class="leave" name="leavemessage"  />
                    	</td>
                    </tr>
           
                </table>
            </div>
        </div>
        </div>
     
        <div class="wfj11_015_delivery_hide"></div>
        </div>
        <div class="wfj11_015_bottom">
        	<ul>
            	<li id="footing">合计：<span>${total}</span><input type="hidden" id="morre" name="morre" value="${total}"/></li>
            	<li id="commit"><a href="javascript:;">确认订单</a></li>
            	
            </ul>
        </div>
        </form>
        
        <div class="wfj11_015_buy_commit" style="display:none;">
        	<div class="wfj11_015_need">
            	<div class="wfj11_015_width">
                	<ul>
                    	<li class="left">需支付：</li>
                    	<li class="right"><span>￥${total}</span></li>
                    </ul>
                </div>
            </div>
           <div class="wfj11_015_allbay">
				<div class="wfj11_015_width">
					<table>
						<tr>
							<td colspan="2">选择支付方式</td>
						</tr>
						<tr class="wfj11_015_choice wechat">
							<td align="left"><img class="all_pay"
												  src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_03.png" />
							</td>
							<td class="second" align="right"><img
									src="<%=basePath%>/images/WFJClient/Newjspim/choice_01.png"
									width="24" height="24" name="3" />
							</td>
						</tr>
						<tr class="wfj11_015_choice">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_01.png" />
							</td>
							<td  class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="1" />
							</td>
						</tr>
						<%--金币--%>
						<tr class="wfj11_015_choice gold">
							<td align="left"><img class="all_pay"
												  src="<%=basePath%>/images/WFJClient/Newjspim/gold.png"/>
								<span style="font-size: 12px"></span>
							</td>
							<td class="second" align="right"><img
									src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
									width="24" height="24" name="6"/>
							</td>
						</tr>
						<%--积分--%>
						<tr class="wfj11_015_choice integral_">
							<td align="left"><img class="all_pay"
												  src="<%=basePath%>/images/WFJClient/Newjspim/jifen.png"/>
								<span style="font-size: 12px"></span>
							</td>
							<td class="second" align="right"><img
									src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
									width="24" height="24" name="7"/>
							</td>
						</tr>
						<%--<tr class="wfj11_015_choice">--%>
							<%--<td align="left"><img class="all_pay"--%>
								<%--src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_02.png" />--%>
							<%--</td>--%>
							<%--<td class="second" align="right"><img--%>
								<%--src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"--%>
								<%--width="24" height="24" name="2" />--%>
							<%--</td>--%>
						<%--</tr>--%>

						<%--<tr class="wfj11_015_choice">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="4" />
							</td>
						</tr>
						<tr class="wfj11_015_choice">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/moneybox.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="5" />
							</td>
						</tr>--%>
						<tr>
							<td colspan="2" align="center"><div id="paycommit"
									onclick="zf()">确认支付</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
        </div>
        
        
	</div>
    
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
<%@ include file="/page/WFJClient/ShopOwner/payCodeCommon.jsp"%>

   <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe> 
    
    
	<script type="text/javascript">
      var callurl = "${callurl}";



        function fanhui(){
	 var morre=$("#morre").val();
	var privateRoom=$("#privateRoom").val();
	var waiter=$("#waiter").val();
			var url = basePath+ "ea/restaurant/ea_getChoice.jspa?ajax=pp&companyId="+companyId+"&ccompanyId="+ccompanyId+"&morre="+morre+"&waiter="+waiter+"&privateRoom="+privateRoom;
			
			document.location.href = url;
          
          }

        $(document).ready(function(e) {
             
          
            $("body").css({"height":$(window).height(),"overflow":"auto"}) ;
			//修改字体大小
				$("#tops").find("li").attr("style","float:left;");
				$("#tops").find("li").eq(0).attr("style","width:15%;");
				$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.04+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
				$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#666");
				$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
				$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
				$(".wfj11_015").css("overflow","auto")
				
			$(".wfj11_015_top").css({"height":$(window).height()*0.08+"px","position":"fixed","width":"100%","z-index":"1000"});
			$(".wfj11_015_top").css({"lineHeight":$(window).height()*0.08+"px","border-bottom":"1px solid #e3e3e3","box-sizing":"contont-box"});
			$(".wfj11_015_top li").css({"font-size":"18px"})
			$(".wfj11_015_top_hide").css({"height":$(window).height()*0.08+"px"})
			$("#FontScroll").css({"padding-left":$(window).height()*0.02+"px","width":$(window).width()-$(window).height()*0.02+"px"})
			//$(".wfj11_015_width a").css({"border-radius":"0","font-size":"14px"})
//			if($(".wfj11_015_jiucan_zhi1").html()=="餐桌："||$(".wfj11_015_jiucan_zhi2").html()=="服务员："){
//				$(".wfj11_015_jiucan_zhi1").hide();
//				$(".wfj11_015_jiucan_zhi2").hide()
//				$(".wfj11_015_jiucan_moren").show()
//			$(".wfj11_015_jiucan p.wfj11_015_jiucan_img").css({"top":$(".wfj11_015_jiucan").height()*0.25+"px","right":"10px"})
//			}else{
//				$(".wfj11_015_jiucan_zhi1").show();
//				$(".wfj11_015_jiucan_zhi2").show()
//				$(".wfj11_015_jiucan_moren").hide()
//			$(".wfj11_015_jiucan p.wfj11_015_jiucan_img").css({"top":$(".wfj11_015_jiucan").height()*0.3+"px"})
//			}
			$(".wfj11_015_consignee").find("td").attr("style","font-size:"+$(window).height()*0.027+"px;line-height:"+$(window).height()*0.03+"px;");
			$(".wfj11_015_consignee").attr("style","padding:"+$(window).height()*0.02+"px 0; margin-bottom:"+$(window).height()*0.01+"px;");
			$(".wfj11_015_consignee").css("position","relative")
			$(".wfj11_015_com").attr("style"," padding:"+$(window).height()*0.03+"px 0;");
			if($(window).width()=="320"){
				$(".wfj11_015_consignee img.left").css({"width":"20px"})
				//$(".wfj11_015_consignee img.right").css({"width":"25px","right":"0"})
			}else if($(window).width()>="375"){
				$("#FontScroll p img").css({"width":"60%"})
			}
			$(".img_ico").css({"top":$(window).height()*0.04+"px"})
			$(".img_ico p").css("height",$(".wfj11_015_consignee .wfj11_015_width").height()+"px")
			$(".img_ico>p").css({"height":$(".img_ico").height()+"px"})
			$(".img_ico1").css("left",$(window).height()*0.02+"px")
			//$(".wfj11_015_com").css({"padding-left":$(window).height()*0.02+"px"})
			$(".wfj11_015_com").find("a").attr("style","padding:"+$(window).height()*0.008+"px "+$(window).height()*0.01+"px;font-size:"+$(window).height()*0.03+"px;");
			//$(".wfj11_015_proinfo").attr("style"," padding:"+$(window).height()*0.01+"px 0;");
			$(".wfj11_015_proinfo").find("td").attr("style"," font-size:"+$(window).height()*0.025+"px;line-height:"+$(window).height()*0.03+"px;padding-right:"+$(window).height()*0.01+"px;");
			//$(".wfj11_015_proinfo").find("wfj11_015_width").find("tr").find("td").css({"font-size":$(window).height()*0.06+"px"})
			$(".wfj11_015_delivery").find("td").attr("style"," height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.025+"px;");
			$(".wfj11_015_delivery").find("tr").eq(1).find("span").attr("style","padding-left:"+$(window).height()*0.01+"px;");
			$(".wfj11_015_delivery").find("tr").eq(1).find("span").find("img").attr("style","width:"+$(window).height()*0.01+"px;");
			$(".wfj11_015_delivery").css({"padding-left":$(window).height()*0.02+"px","font-size":"16px"})
			$(".wfj11_015_delivery").find("tr").eq(2).find("span").attr("style","padding-left:"+$(window).height()*0.01+"px;");
			$(".wfj11_015_delivery").find("tr").eq(2).find("span").find("img").attr("style","width:"+$(window).height()*0.01+"px;");
			$(".wfj11_015_delivery").find("input").attr("style"," height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.03+"px;");
			$(".wfj11_015_bottom").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px; font-size:"+$(window).height()*0.025+"px;");
			$(".wfj11_015_bottom").find("li").attr("style"," font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_015_bottom").find("li").find("span").attr("style"," font-size:"+$(window).height()*0.03+"px;");
			$(".wfj11_015_bottom").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.03+"px;");
			$(".wfj11_015_delivery_hide").css({"height":$(".wfj11_015_bottom").height()+"px"})
			
			
			$(".wfj11_015_need").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj11_015_need").find("li").attr("style","font-size:"+$(window).height()*0.03+"px;color:#000;");
			$(".wfj11_015_need").find("li").find("span").attr("style","font-size:"+$(window).height()*0.03+"px;color:#F74C31;");
			$(".wfj11_015_allbay").find("td").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj11_015_allbay").find("td").eq(0).css("height",$(window).height()*0.05+"px")
			$(".wfj11_015_allbay").find("tr").eq(5).css("height",$(window).height()*0.1+"px")
			$(".wfj11_015_allbay").find("td").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto");
			$(".wfj11_015_allbay").find("td").find(".all_pay").attr("style","height:"+$(window).height()*0.04+"px;");
			$(".wfj11_015_allbay").find("td").find("#paycommit").attr("style"," width:50%; background-color:#F74C31; color:#FFF; cursor:pointer; border-radius:"+$(window).height()*0.006+"px; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
			$(".wfj11_015_consignee1").css({"width":$(window).width()*0.07+"px","left":$(window).height()*0.02+"px"})
			//$(".wfj11_015_consignee2").css({"top":"7px"})
	
			
                if($("#privateRoom").val()!=null&&$("#privateRoom").val()!=""){
                    selecton = 1;
				}
			 if(selecton=="1"){
				 $(".wfj11_015_mo").find("li").eq(1).addClass("on");
				 $(".wfj11_015_mo").find("li").eq(0).removeClass("on");
				 $(".wfj11_015_consignee").css("display","none")
				 $(".wfj11_015_jiucan").css("display","block")
				 $(".wfj11_015_mo ul li").css("color","")
				 $(".wfj11_015_mo").find("li").eq(1).css("color","#ea5a5a");
			 }
			 if(selecton=="2"){
				 $(".wfj11_015_mo").find("li").eq(0).addClass("on");
				 $(".wfj11_015_mo").find("li").eq(1).removeClass("on");
				 $(".wfj11_015_consignee").css("display","block")
				 $(".wfj11_015_jiucan").css("display","none")
				 $(".wfj11_015_mo ul li").css("color","")
				 $(".wfj11_015_mo").find("li").eq(0).css("color","#ea5a5a")
			 }
		 
		 
			$(".wfj11_015_mo ul li").click(function(){
				$(".wfj11_015_mo ul li").removeClass("on")
				$(this).addClass("on")
				if($(this).text()=="外卖送货"){
					$(".wfj11_015_consignee").css("display","block")
					$(".wfj11_015_jiucan").css("display","none")
					$(".wfj11_015_mo ul li").css("color","")
					$(this).css("color","#ea5a5a")
				}else if($(this).text()=="餐厅就餐"){
					$(".wfj11_015_consignee").css("display","none")
					$(".wfj11_015_jiucan").css("display","block")
					$(".wfj11_015_mo ul li").css("color","")
					$(this).css("color","#ea5a5a")
				}
			})
//切换支付方式
			$(".wfj11_015_choice").click(function(){
		$(".wfj11_015_choice").find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_02.png");
		$(this).find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_01.png");
		zffs=$(this).find(".second").find("img").attr("name");
});
			
			
			
			$(".wfj11_015_bottom").click(function(){
				$(".wfj11_015_bottom").css("display","none");
				
				$("#occlusion2").css("z-index",$(".wfj11_015").css("z-index")+1);
				$("#occlusion2").jqmShow();
				$(".wfj11_015_buy_commit").css("z-index",$("#occlusion2").css("z-index")+1);
				$(".wfj11_015_buy_commit").fadeIn(1000);
			});
			$(".jqmOverlay").live("click",function(){
				$(".wfj11_015_buy_commit").fadeOut();
				$("#occlusion2").jqmHide();
				$(".wfj11_015_bottom").css("display","");
			});
			
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
	
			
        });
        
        
    </script>
    <script>
    (function($){
        $.fn.FontScroll = function(options){
            var d = {time: 3000,s: 'fontColor',num: 1}
            var o = $.extend(d,options);
            this.children('ul').addClass('line');
            var _con = $('.line').eq(0);
            var _conH = _con.height(); //滚动总高度
            var _conChildH = _con.children().eq(0).height();//一次滚动高度
            var _temp = _conChildH;  //临时变量
            var _time = d.time;  //滚动间隔
            var _s = d.s;  //滚动间隔
            _con.clone().insertAfter(_con);//初始化克隆

            //样式控制
            var num = d.num;
            var _p = this.find('li');
            var allNum = _p.length;

            _p.eq(num).addClass(_s);


            var timeID = setInterval(Up,_time);
            this.hover(function(){clearInterval(timeID)},function(){timeID = setInterval(Up,_time);});

            function Up(){
                _con.animate({marginTop: '-'+_conChildH});
                //样式控制
                _p.removeClass(_s);
                num += 1;
                _p.eq(num).addClass(_s);

                if(_conH == _conChildH){
                    _con.animate({marginTop: '-'+_conChildH},"normal",over);
                } else {
                    _conChildH += _temp;
                }
            }
            function over(){
                _con.attr("style",'margin-top:0');
                _conChildH = _temp;
                num = 1;
                _p.removeClass(_s);
                _p.eq(num).addClass(_s);
            }
        }
    })(jQuery);
    $('#FontScroll').FontScroll({time: 2000,num: 1});
    $(".gonggao_tu p").css({"line-height":$("#FontScroll").height()+"px"})

    
    </script>
</body>
</html>
