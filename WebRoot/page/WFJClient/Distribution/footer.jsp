<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<div class="wfj_bottom left">
            <div class="wfj_link">
                <ul>
                    <li><a href="<%=basePath %>/ea/wfjshop/ea_getWFJshops.jspa">微分金店首页</a></li>
                    <li><a href="<%=basePath %>/ea/wfjcustomer/ea_getlist.jspa">所有商品</a></li>
                    <li><a href="<%=basePath %>/ea/buyproducts/ea_getOrders.jspa">会员中心</a></li>
                    <li><a href="<%=basePath %>/ea/buyproducts/ea_putInCart.jspa">购物车</a></li>
                </ul>
            </div>
            <div>
                <ul>
                    <li><a href="javascript:void(0);">京ICP备10034132号-2版权所有</a></li>
                </ul>
            </div>
            <div>
                <ul>
                    <li>北京天太世统科技有限公司</li>
                </ul>
            </div>
        </div>
