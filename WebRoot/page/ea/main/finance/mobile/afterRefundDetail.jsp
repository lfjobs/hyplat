<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ page import="hy.ea.bo.Company"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany");
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />         
        <title>卖家添加退货地址</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
        <link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/returnedGoods.css" />

        
        <script>
        var basePath="<%=basePath%>";
        var id="${id}";
        var companyId="${companyId}";
        var rsid = "${refundSheet.rsid}";
        var key="${key}";
        var photo="${photo}";
        var param2="${param2}";
        var param4="${param4}";
        var param3="${param3}";
        var param1="${param1}";
        </script>
        <style >
        </style>
	</head>

<body>
<div class="main">
		<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li class="wfj_return"><a href="#" target="_self"><img src="<%=basePath%>js/jqModal/css/images_blue/return.png"/></a></li>
            	<li>退货单详情</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png"/></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        
        <div class="content">
        	<div class="con_auto">
            	<section class="one" >
                	<ul >
                    	<li class="one1"><span class="one_left" style="color:#939393;">订 单 号:${cashierBills.jNumOrder}</span>
                        	<span class="one_right" style="color:#939393;">${fn:substring(cashierBills.cashierDate, 0, 19)}</span>
                           </li>
                            
                        <li class="one1"><span class="one_left" style="color:#939393;">订单状态:</span>
                        	<span class="one_right" style="color:#939393; display:none;" >${cashierBills.fkStatus}</span>
                        	<s:if test="cashierBills.fkStatus=='11'||cashierBills.fkStatus=='10'||cashierBills.fkStatus=='12'||#pf.fkStatus=='13'">
													 <span class="pb" style="float:right;" value="00" >售后详情
														</span>
														
													</s:if>
                            </li>
                        <li class="one1""><span class="one_left" style="color:#939393;">退款金额:</span>
                        	<span class="one_right" style="color:#ff4747;">${rs.refundMoney}</span>
                            </li>
                        <div class="clear"></div>
                    </ul>
            	</section>
                <div class="boder1"></div>
                <section class="two">
                	<div class="width">
                        <ul class="width1">收货地址</ul>
                        <ul>
                            <li class="sign"><img src="<%=basePath%>js/jqModal/css/images_blue/sign.png" /></li>
                            <li class="address" style="color:#75b87e">【北京市】快件已签收，感谢您使用中通快递，期待下次再为您服务</li>
                            <li class="arrow"><img src="<%=basePath%>js/jqModal/css/images_blue/arrow.png" /></li>
                            <div class="clear"></div>
                        </ul>
                    </div>
                </section>
                <div class="boder2"></div>
                <section class="three">
                	<div class="width">
                        <ul class="width1">发货人信息</ul>
                        <ul class="img1">
                            <li class="sign"  ><img src="<%=basePath%>js/jqModal/css/images_blue/sign.png" class="img2"/></li>
                            
                            <div class="clear"></div>
                        </ul>
                        <ul>
                            <li class="send" style="color:#75b87e">
                            	发货人：&nbsp;胡伟
                                                                
                            </li>
                            <li class="send" style="color:#75b87e">
                            	
                                                                发货地址：北京市东城区东直门外大街宇飞大厦801北京天太世统科技有限公司
                            </li>
                            <div class="clear"></div>
                        </ul>
                    </div>
                </section>
                <div class="boder2"></div>
                <section class="four">
                	<div class="width" >
                    	<ul class="width1">商品信息</ul>
                        <ul class="four_con">
                            <li class="goods"><img src="<%=basePath%>${productPackaging.image}" /></li>
                            <li class="price">
                                <ul>
                                    <li class="xx" >
                                        <span class="pri_left" style="color:#5b5b5b;float:left;">${productPackaging.goodsName}</span>
                                        <span class="pri_right" style="float:right;">￥${productPackaging.price}</span>
                                     </li>
                                     <li  class="xx">
                                        <span class="pri_left">颜色分类：15118纯绿色</span>
                                        <span class="pri_right">×${count}</span>
                                     </li>
                                     <li class="xx" >
                                        <span class="pri_left">尺码：20cm(百分百纯绿色)</span>
                                     </li>
                                    
                                </ul>
                            </li>
                        	<div class="clear"></div>
                        </ul>
                    </div>
                </section>
            </div>
        </div>
</div>

<script type="text/javascript">
	$(document).ready(function(e){
		$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;")
			//中联园区头部
        $(".wfj_top").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;");
        $(".wfj_top").find("li").attr("style","width:10%;");
        $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
        $(".wfj_top").find("li").eq(1).attr("style","width:80%;font-size:"+$(window).height()*0.03+"px; text-align:left;");
		//内容
        $(".con_auto").attr("style","height:"+$(window).height()*0.97+"px;font-size:"+$(window).height()*0.02+"px;");
        $("li.xx").attr("style","line-height:"+$(window).height()*0.04+"px; height:"+$(window).height()*0.04+"px;");
        $("li.one1").attr("style","line-height:"+$(window).height()*0.04+"px; height:"+$(window).height()*0.04+"px;background-color:#E6E5E5; color:#666;");
		$(".img1").attr("style","float:left;width:"+$(window).width()*0.15+"px;height:"+$(window).height()*0.12+"px;");
		$(".img2").attr("style","width:"+$(window).width()*0.08+"px;height:"+$(window).height()*0.05+"px;margin:0px "+$(window).height()*0.018+"px;");
		$(".two ul li").attr("style","float:left; height:"+$(window).width()*0.2+"px; line-height:"+$(window).width()*0.1+"px;color:#75b87e;");
		$(".width1").attr("style","margin:"+$(window).width()*0.02+"px");
	});
</script>
</body>
</html>
 