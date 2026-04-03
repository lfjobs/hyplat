<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>个人中心</title>  
     <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link href="<%=basePath%>/css/shengwei/style05.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  </head>
  
 <body>
    <div class="MyWfjNav">
        <div class="wfj_top">
            <div class="my_width">
                <ul>
                    <li class="w_left"><a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa" style="font-weight:bold;color:#fff;"><</a></li>
                    <li class="w_center">我的微分金</li>
                </ul>
            </div>
        </div>
        <a id="basicinfo" href="<%=basePath%>/ea/wfjcustomer/ea_personalInfor.jspa">
	        <div class="basicinfo">
	            <div class="my_width">
	                <ul class="left">
	                    <li><img src="<%=basePath%>/images/WFJClient/info_avatar.png" /></li>
	                </ul>
	                <ul class="left wfj07_name">
	                    <li class="color000">${result }</li>
	                    <li class="color000">金牌用户</li>
	                </ul>
	                <ul style="line-height:120px;">
	                    <li class="w_right" style=" font-weight:bold; font-size:18px;">></li>
	                </ul>
	            </div>
	            <%--<div class="wfj07_pro">
	            	<div>
	                	<ul>
	                    	<li>3</li>
	                    	<li>关注的商品</li>
	                    </ul>
	                </div>
	            	<div>
	                	<ul>
	                    	<li>2</li>
	                    	<li>关注的店铺</li>
	                    </ul>
	                </div>
	            	<div>
	                	<ul>
	                    	<li>1</li>
	                    	<li>浏览记录</li>
	                    </ul>
	                </div>
	            </div>
	        --%></div>
	    </a>
        <div class="MyWfjNav">
        	<div class="my_width">
            	<div class="wfj07_order">
                  <div>
                      <ul style=" padding-bottom:10px;">
	                      <a href="javascript:void(0);">
	                          <li><img src="<%=basePath%>/images/WFJClient/info_yi.png"></li>
	                          <li>已付款</li>
	                      </a>
                      </ul>
                      <ul>
	                      <a href="javascript:void(0);">
		                          <li><img src="<%=basePath%>/images/WFJClient/info_wei.png"></li>
		                          <li>未付款</li>
	                      </a>
                      </ul>
                  </div>
                  <a id="order" href="javascript:void(0);">
	                  <div class="wfj07_orders">
	                      <ul>
	                          <li><img src="<%=basePath%>/images/WFJClient/wfj07_01.png"></li>
	                          <li >订单信息</li>
	                          <li style="float:right; padding-right:5px;" >查看全部已购买宝贝 <span style="font-weight:bold;">></span></li>
	                      </ul>
	                  </div>
	              </a>
                </div>
                
                
                <div class="wfj07_007_order">
                	<a id="product" href="javascript:void(0);">
	                	<ul>
	                    	<li><img src="<%=basePath%>/images/WFJClient/wfj07_02.png"></li>
	                    	<li>个人产品</li>
	                    	<li style=" float:right; padding-right:5px;">查看产品详情 <span style="font-weight:bold;">></span></li>
	                    </ul>
                    </a>
                    <a id="dlorder" href="javascript:void(0);">
	                	<ul>
	                    	<li><img src="<%=basePath%>/images/WFJClient/wfj07_03.png"></li>
	                    	<li>代理产品</li>
	                    	<li style=" float:right; padding-right:5px;">查看产品详情 <span style="font-weight:bold;">></span></li>
	                    </ul>
	                </a>
	                <a href="<%=basePath %>ea/jifen/ea_toPage.jspa?staffId=${key_customer.staffid}">
	                	<ul>
	                    	<li><img src="<%=basePath%>/images/WFJClient/wfj07_04.png"></li>
	                    	<li>积分管理</li>
	                    	<li style=" float:right; padding-right:5px;">查看积分详情 <span style="font-weight:bold;">></span></li>
	                    </ul>
                    </a>
                    <a id="FX" href="<%=basePath %>/ea/distribution/ea_getwfjHome.jspa">
	                	<ul>
	                    	<li><img src="<%=basePath%>/images/WFJClient/wfj07_05.png"></li>
	                    	<li>分销管理</li>
	                    	<li style=" float:right; padding-right:5px;">查看分销详情 <span style="font-weight:bold;">></span></li>
	                    </ul>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" id="loginType"  value="<s:property value='#session.key_shop_cus_com.cusType' />"/>
</body>
<script type="text/javascript">
	$(function(){
		var loginType=$("#loginType").val();
		//普通客户
		if(loginType=="6"){
				$("#order").attr("href","<%=basePath%>/ea/buyproducts/ea_getComOrders.jspa?fkstatus=00");
			$("#yifuk").attr("href","<%=basePath%>/ea/buyproducts/ea_getOrders.jspa?fkstatus=00");
			$("#weifuk").attr("href","<%=basePath%>/ea/buyproducts/ea_getOrders.jspa?fkstatus=01");
			//$("#product").attr("href","#");
			//$("#dlorder").attr("href","#");
		}
		//公司
		if(loginType=="2"){	
			$("#product").attr("href","<%=basePath%>/ea/wfjcustomer/ea_getlist.jspa");
			$("#dlorder").attr("href","<%=basePath%>/ea/wfjcustomer/ea_getProductAgency.jspa");
			$("#order").attr("href","<%=basePath%>/ea/buyproducts/ea_getComOrders.jspa?fkstatus=00");
		    $("#FX").attr("href","<%=basePath%>/ea/distribution/ea_getwfjHome.jspa");		

		}
		//店铺
		if(loginType=="4"){
			$("#order").attr("href","<%=basePath%>/ea/buyproducts/ea_getComOrders.jspa?fkstatus=00");
			$("#product").attr("href","<%=basePath%>/ea/wfjcustomer/ea_getlist.jspa");
			$("#dlorder").attr("href","<%=basePath%>/ea/wfjcustomer/ea_getProductAgency.jspa");
		}
		
	})

</script>
 </html>
