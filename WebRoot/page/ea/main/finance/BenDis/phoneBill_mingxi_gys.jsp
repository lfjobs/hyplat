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
	String id=request.getParameter("id");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/contacts/style12.css"/>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<title>订单详情_供应商</title>
  </head>
  
  <body>
    <div class="wfj12_014">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="javascript:history.go(-1);" target="_self"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_02.png" /></a></li>
            	<li>订单管理</li>
            	<li><a href="javascript:;"><img src="<%=basePath %>images/ea/finance/BenDis/top_more_02.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_014_content">
        	<div class="wfj12_014_hidden">
                <div class="wfj12_014_topimg">
                    <div class="wfj12_014_timg"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gwc_01.png" /></div>
                    <div class="wfj12_014_top_width">
                        <ul>
                        <input type="hidden" id="id" value="${pf.cashierBillsID}"/>
                        	<c:if test="${pb.fkStatus=='01'}">
                            <li class="wfj12_014_top_font1">我的<font>购物</font>车</li>
                            <li class="wfj12_014_top_font2"><a href="javascript:;">专属你的购物需求</a></li>
                        	</c:if>
							<c:if test="${pb.fkStatus=='00'}">
                            <li class="wfj12_014_top_font1"><font>待发货</font></li>
                            <li class="wfj12_014_top_font2"><a href="javascript:;">亲·准备发货咯！</a></li>
                        	</c:if>
                        	<c:if test="${pb.fkStatus=='02'}">
                            <li class="wfj12_014_top_font1"><font>待收货</font></li>
                            <li class="wfj12_014_top_font2"><a href="javascript:;">您的商品将要到达</a></li>
                        	</c:if>
                        </ul>
                    </div>
                </div>
            	<div class="wfj12_014_title">
                	<div class="wfj12_014_width">
                    	<ul>
                        	<li><img src="<%=basePath %>images/ea/finance/BenDis/aboutusimg_01.png" /></li>
							<li>${pb.companyName }</li>
							<li><img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_03.png" /></li>
                        </ul>
                    </div>
                </div>
             	
                <div class="wfj12_014_product">              
                 <c:forEach items="${pb.goodsList}" var="gl">
                 <a>
               <%--   href="<%=basePath %>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${gl[7] }&goodsid=${gl[10]}&companyId=${gl[11]}" --%>
                	<div class="wfj12_014_width">
                    	<table class="wfj12_014_pro">
                        	<tr>
                            	<td width="30%" rowspan="3"><img src="<%=basePath %>${gl[6]}" /></td>
                            	<td colspan="2"><font>${gl[5]}</font></td>
                            	<td class="pdright" align="right"><span>￥${gl[3]}</span></td>
                            </tr>
                           
                        	<tr>
                            	<td width="70%">${gl[12]}</td>
                            	<td class="pdright" width="20%" align="right">X${gl[2]}</td>
                            </tr>        
                        	<tr>
                            	<td colspan="3"><%-- <span>金币：</span> --%></td>
                            </tr>		
                        </table>
                        <hr style="border-top:1px solid #ddd;margin:0;">
                    </div>
                    </a>
                    </c:forEach>
                    
                    <!-- 促销产品 -->
                    <c:forEach items="${pb.ptgoodsList}" var="pt">
                        <a>
                        <%-- href="<%=basePath %>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${pt[7] }&goodsid=${pt[8]}&companyId=${pt[9]}"> --%>
                	<div class="wfj12_014_width">
                    	<table class="wfj12_014_pro">
                        	<tr>
                            	<td width="30%" rowspan="3"><img src="<%=basePath %>${pt[6]}" /></td>
                            	<td colspan="2"><font>${pt[5]}</font></td>
                            	<td class="pdright" align="right"><img src="<%=basePath %>/images/WFJClient/Newjspim/cuxiao.png" style="width: 33px;"/></td>
                            </tr>                       
                        	<tr>
                            	<td width="70%">${pt[11]}</td>
                            	<td class="pdright" width="20%" align="right">X${pt[2]}</td>
                            </tr>        
                        	<tr>
                            	<td colspan="3"><c:if test="${pt[13] eq '01' }"><div id="${pt[10] }" class="wfj12_014_commit" style="">立即支付</div></c:if>  <%-- <span>金币：</span> --%></td>                          	
                            </tr>                           					 
                        </table>                    
                    </div>
                    </a>
                                                          
                    </c:forEach> 
                  
                    <div class="wfj12_014_pays">
                		<div class="wfj12_014_width">
                            <table class="wfj12_014_pay">
                                   <c:if test='${cash.privateRoom!=null&&cash.privateRoom!=null}'>           
                                                        
                                <tr>
                                    <td colspan="2"> 餐桌包间：${cash.privateRoom}&nbsp;&nbsp;服务员：${cash.waiter}</td>
                                    
                                </tr>
                                </c:if>
                                <tr>
                                    <td>运费:</td>
                                    <td class="pdright" align="right">￥0.00</td>
                                </tr>
                                <tr>
                                    <td>实付款（含运费）:</td>
                                    <td class="pdright" align="right"><span>￥${pb.priceSub }</span></td>
                                </tr>
                            </table>
                            <table>
                                 <tr>
                                    <td>买家留言:</td>
                                    <td align="left">${cash.remark}</td>
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
                <div class="wfj12_014_other">
                
                <c:forEach items="${cplist}" var="cp" varStatus="p">
                <div class="wfj12_014_otherpro <c:if test="${p.count%2 != '0'}">left</c:if><c:if test="${p.count%2 == '0'}">right</c:if>">
                <a href="<%=basePath %>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${cp[0] }&goodsid=${cp[16] }">
                    	<ul>
                        	<li><img src="<%=basePath %>${cp[5]}" /></li>
                        </ul>
                    	<ul>
                        	<div class="wfj12_014_width">
                            	<ul>
                                	<li>${cp[2]}</li>
                                	<li><span>￥${cp[4]}</span></li>
                                </ul>
                            </div>
                        </ul>
                     </a>
                    </div>
                </c:forEach>
                </div>
            </div>
        </div>
    </div>
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
    
    <script type="text/javascript">
    	$(document).ready(function(e) {
    		var cashid="${pb.cashierBillsID }";
    		var staid="${pb.contactUserID }";
    		var basePath="<%=basePath%>";
    		var id="<%=id%>";
    	
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
            $(".wfj12_014_otherpro").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
			$(".wfj12_014_otherpro").find("img").css("height",$(".wfj12_014_otherpro").find("img").css("width"));
			
			$(".wfj12_014").find("table").find("tr").attr("style"," height:"+$(window).height()*0.06+"px; width:100%; border:none; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_014").find("table").find("tr").find("td").eq(0).find("span").attr("style"," font-size:"+$(window).height()*0.015+"px;");
			
			$(".wfj12_014_bottom_commit").attr("style"," height:"+$(window).height()*0.06+"px; background-color:#FFF; width:100%; padding-top:"+$(window).height()*0.01+"px;");
			$(".wfj12_014_bottom_commit").find("div").attr("style"," width:50%; height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.06+"px; background-color:#F74C31; border-radius:"+$(window).height()*0.01+"px; color:#FFF; text-align:center; font-size:"+$(window).height()*0.03+"px; font-weight:bold; margin:0 auto; cursor:pointer;");
			
			$(".wfj12_014_buy_width").find("td").attr("style"," height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_014_pay").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
			$(".wfj12_014_choice").find("img").attr("style"," height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
			$("#money").attr("style","color:#F74C31;");
			$("#money").css("color","#F74C31");
			$("#img2").css("width",$(window).width()*0.1+"px");
			
			
			$("#pays").find("span").eq(0).attr("style","color:#000; font-size:"+$(window).height()*0.025+"px;");
			$("#pays").find("span").eq(1).attr("style","color:#F74C31; font-size:"+$(window).height()*0.03+"px;");
			
			
			$("#ul").find("li").eq(2).find("img").attr("style","max-height:"+$(window).height()*0.03+"px; width:auto;");
			$(".wfj12_014_top").css("lineHeight",$(window).height()*0.08+"px");
			$(".wfj12_014_name").css("height",$(window).height()*0.155+"px");
			
			$(".wfj12_014_choice").find("img").click(function(){
				$(".wfj12_014_choice").find("img").attr("src","<%=basePath %>images/ea/finance/BenDis/choice_02.png");
				$(this).attr("src","<%=basePath %>images/ea/finance/BenDis/choice_01.png");
				
			});
			
			$(".wfj12_014_commit").click(function(){				
				window.location.href=basePath+"ea/hypb/ea_getCashBill.jspa?staid="+$(this).attr("id");
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
        	
        	$("#wfj_sh").click(function(){
        		document.location.href=basePath+"ea/phonebill/ea_userConfirmationReceipt.jspa?staid="+staid+"&id="+cashid;
        	});
        	
        	
        	$("#agree").click(function(){
				window.location.href=basePath+"ea/bdbill/ea_applyMobile.jspa?staid="+id;
			});
        });
    </script>
</body>
</html>
