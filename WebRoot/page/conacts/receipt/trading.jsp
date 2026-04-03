<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=basePath%>css/contacts/trading/jqModal_blue.css" rel="stylesheet"
		type="text/css" />
		
		<link href="<%=basePath%>css/contacts/trading/style12.css" rel="stylesheet"
		type="text/css" />
		<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<title>订单管理</title>
</head>


<body>
	<div class="wfj12_017">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="<%=basePath%>/ea/consignee/ea_receipt.jspa?stype=&sta=00" target="_self"><img src="<%=basePath%>images/contacts/trading/wfj_return_02.png" /></a></li>
            	<li>收货单</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>images/contacts/trading/top_more_02.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_017_content">
        	<div class="wfj12_017_hidden">
            
            
                <div class="wfj12_017_topimg">
				<!--待评价，用“../Images/wfj_gwc_02.png”  交易成功，用“../Images/wfj_gwc_03.png”-->
                    <div class="wfj12_017_timg"><img src="<%=basePath%>images/contacts/trading/wfj_gwc_02.png" /></div>
                    <div class="wfj12_017_top_width">
                        <ul>
                            <li class="wfj12_017_top_font1"><font>待评价</font></li>
                            <li class="wfj12_017_top_font2"><a href="javascript:;">您还没有评价哦</a></li>
                        </ul>
                    </div>
                </div>
                
                <div class="wfj12_017_express">
                	<div class="wfj12_017_width">
                    	<table>
                        	<tr>
                            	<td width="10%" rowspan="2"><img src="<%=basePath%>images/contacts/trading/wfj_express_01.png" /></td>
                            	<td width="80%"><font>【北京市】快件已签收快件已签收快件已签收快件已签收快件已签收</font></td>
                            	<td align="right" width="10%" rowspan="2"><img src="<%=basePath%>images/contacts/trading/wfj_return_03.png" /></td>
                            </tr>
                            <tr>
                            	<td>2011-11-11 11:11:11</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="wfj12_017_readdress">
                	<div class="wfj12_017_width">
                    	<table>
                        	<tr>
                            	<td width="10%" rowspan="2"><img width="75%" src="<%=basePath%>images/contacts/trading/wfj11_address_02.png" /></td>
                            	<td width="25%">收货人：</td>
                            	<td width="65%">${param.consigneeName}</td>
                            </tr>
                            <tr>
                            	<td>收货地址：</td>
                            	<td id="consingneeAddress">${param.consingneeAddress}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="wfj12_017_con_title">
                	<div class="wfj12_017_width">
                    	<ul>
                        	<li><img src="<%=basePath%>images/contacts/trading/aboutusimg_01.png" /></li>
                        	<li>公司名称</li>
                        	<li><img src="<%=basePath%>images/contacts/trading/wfj_return_03.png" /></li>
                        </ul>
                    </div>
                </div>
                <div class="wfj12_017_con_product">
                	<div class="wfj12_017_width" id="cp">
                    	<table>
                        	<tr>
                            	<td width="30%" rowspan="3"><img width="80%" src="${param.image}" /></td>
                            	<td width="50%"><span>${param.nuuk}</span></td>
                            	
                            	<td width="20%" align="right"><span>￥111.11</span></td>
                            </tr>
                        	<tr>
                            	<td>颜色：粉红色</td>
                            </tr>
                        	<tr>
                            	<td>尺码：XL</td>
                            	<td align="right" id="">X${param.price}</td>
                            </tr>
                        	<tr>
                            	<td colspan="4" align="right"><div class="wfj12_017_commit">申请售后</div></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="wfj12_017_fee">
                	<div class="wfj12_017_width">
                    	<table>
                        	<tr>
                            	<td>运费</td>
                            	<td align="right">￥${param.priceSub}</td>
                            </tr>
                        	<tr>
                            	<td>实付款（含运费）</td>
                            	<td align="right"><font>￥${param.wlyf}</font></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="wfj12_017_call">
                	<div class="wfj12_017_width">
                    	<div class="left">
                        	<ul>
                            	<li><img src="<%=basePath%>images/contacts/trading/wfj_call_01.png" />联系卖家</li>
                            </ul>
                        </div>
                    	<div class="right">
                        	<ul>
                            	<li><img src="<%=basePath%>images/contacts/trading/wfj_call_02.png" />拨打电话</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="wfj12_017_payinfo">
                	<div class="wfj12_017_width">
                    	<table>
                        	<tr>
                            	<td width="32%">订单编号：</td>
                            	<td width="53%">${param.number}</td>
                            	<td width="53%" id="orderCode">${param.order}</td>
                            	<td width="15%" align="right"><div>复制</div></td>
                            </tr>
                        	
                        	<tr>
                            	<td>创建时间：</td>
                            	<td colspan="2" id="orderDate">${param.orderDate}</td>
                            </tr>
                        	<tr>
                            	<td>付款时间：</td>
                            	<td colspan="2">${param.consignneDate}</td>
                            </tr>
                        	<tr>
                            	<td>发货时间：</td>
                            	<td colspan="2" id="senDate">${param.senDate}</td>
                            </tr>
                        	<tr>
                            	<td>成交时间：</td>
                            	<td colspan="2" id="consignneDate">${param.consignneDate}</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="wfj12_017_bottom">
        	<div class="wfj12_017_width">
            	<div class="wfj12_017_btall">
                	<ul>
                    	<li class="wfj12_017_btsearch" id="view"><div>查看物流</div></li>
                    	<!-- <li class="wfj12_017_btestimate" id="commits"><div >评价</div></li> -->
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
     var basePath='<%=basePath%>';
    	$(document).ready(function(e) {
    		$("#commits").click(function() {
    		var image=$("#cp").find("img").attr("src");
				document.location.href=basePath+"page/conacts/receipt/comments.jsp?image="+image;
				});
				
    	
    	
    	
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
            $(".wfj12_017_top_width").attr("style","top:"+$(window).height()*0.02+"px;");
            $(".wfj12_017_top_font2").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
            $(".wfj12_017_top_font2").find("a").attr("style","font-size:"+$(window).height()*0.02+"px;padding:"+$(window).height()*0.01+"px "+$(window).height()*0.02+"px;letter-spacing:"+$(window).height()*0.01+"px;border-bottom:"+$(window).height()*0.002+"px solid #FFF; border-top:"+$(window).height()*0.002+"px solid #FFF;");
            $(".wfj12_017_top_font1").attr("style","width:"+$(".wfj12_017_top_font2").find("a").width()+"px;text-align:center;font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;");
            $(".wfj12_017_top_width").find("li").find("font").attr("style","font-size:"+$(window).height()*0.03+"px;");
			
            $(".wfj12_017_express").attr("style","padding:"+$(window).height()*0.015+"px 0; margin-bottom:"+$(window).height()*0.01+"px;");
            $(".wfj12_017_express").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_017_express").find("td").find("img").attr("style","height:"+$(window).height()*0.02+"px;");
            $(".wfj12_017_express").find("td").find("img").eq(0).attr("style","height:"+$(window).height()*0.04+"px;");
			
            $(".wfj12_017_readdress").attr("style"," margin-bottom:"+$(window).height()*0.01+"px;");
            $(".wfj12_017_readdress").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			
            $(".wfj12_017_con_title").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px;");
            $(".wfj12_017_con_title").find("li").attr("style","font-size:"+$(window).height()*0.02+"px; padding-right:"+$(window).height()*0.01+"px;");
            $(".wfj12_017_con_title").find("li").find("img").attr("style","height:"+$(window).height()*0.02+"px;");
			
            $(".wfj12_017_con_product").find("table").attr("style"," padding:"+$(window).height()*0.015+"px 0;");
            $(".wfj12_017_con_product").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_017_con_product").find("table").find(".wfj12_017_commit").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.045+"px; line-height:"+$(window).height()*0.045+"px; border-radius:"+$(window).height()*0.01+"px;");
			
			
            $(".wfj12_017_fee").attr("style"," margin-bottom:"+$(window).height()*0.005+"px;");
            $(".wfj12_017_fee").find("td").attr("style","font-size:"+$(window).height()*0.025+"px;");
			
            $(".wfj12_017_call").attr("style"," padding:"+$(window).height()*0.015+"px 0; height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px; margin-bottom:"+$(window).height()*0.025+"px;");
            $(".wfj12_017_call").find(".wfj12_017_width").find("div").attr("style"," border:"+$(window).height()*0.003+"px solid #ccc;");
            $(".wfj12_017_call").find("div").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_017_call").find("div").find("img").attr("style"," height:"+$(window).height()*0.03+"px;margin:"+$(window).height()*0.005+"px auto;padding-right:"+$(window).height()*0.005+"px;");
			
			
			
            $(".wfj12_017_payinfo").attr("style"," padding:"+$(window).height()*0.02+"px 0; margin-bottom:"+$(window).height()*0.02+"px; ");
            $(".wfj12_017_payinfo").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_017_payinfo").find("table").find("div").attr("style"," border:"+$(window).height()*0.003+"px solid #CCC; border-radius:"+$(window).height()*0.01+"px; font-size:"+$(window).height()*0.02+"px;");
			
			
			
			
			
            $(".wfj12_017_bottom").find("li").attr("style","font-size:"+$(window).height()*0.025+"px;");
            $(".wfj12_017_bottom").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
            $(".wfj12_017_bottom").find(".wfj12_017_btall").find("div").attr("style","height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;margin:"+$(window).height()*0.02+"px auto;font-size:"+$(window).height()*0.02+"px; border:"+$(window).height()*0.002+"px solid #CCC;");
            $(".wfj12_017_bottom").find(".wfj12_017_btestimate").find("div").attr("style","height:"+$(window).height()*0.04+"px;line-height:"+$(window).height()*0.04+"px;margin:"+$(window).height()*0.02+"px auto;font-size:"+$(window).height()*0.02+"px; border:none;");
			
			
			
            $(".wfj12_017_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj12_017_bottom").height())+"px;");
            $(".wfj12_017_hidden").attr("style","width:"+parseInt($(window).width()+17)+"px;height:"+parseInt($(".wfj12_017_content").height())+"px;");
			
        });
    </script>
</body>
</html>
