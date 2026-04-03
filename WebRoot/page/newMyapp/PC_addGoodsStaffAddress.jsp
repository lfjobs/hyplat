<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="hy.ea.bo.human.Staff" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>立即购买-新增收货地址</title>
    <link href="<%=basePath%>page/newMyapp/css/myStyle.css" rel="stylesheet" type="text/css">
   	<script type="text/javascript" src="<%=basePath%>page/newMyapp/js/jquery-1.9.1.min.js" ></script>
   <%--  <script type="text/javascript" src="<%=basePath%>page/newMyapp/js/jsAddress.js"></script>  --%>
    <script type="text/javascript" src="<%=basePath%>js/ea/pcwfj/PC_addGoodsStaffAddress.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var ppID="${param.ppID}";
    	var standard="${param.standard}";
    	var count="${param.count}";
    	var showParam = "${param.showParam}";
    	var alter="${alter}"
    </script>
</head>
<body>
    <div class="content">
        <!--搜索-->
        <div class="mil search_mil sc_search">
            <a href="#;" class="logo"><img src="<%=basePath%>page/newMyapp/images/logo.png"></a>
            <h1>数字地球</h1>
            <img src="<%=basePath%>page/newMyapp/images/top1.png" class="top">
            <img src="<%=basePath%>page/newMyapp/images/top2.png" class="top" style="margin-right: 25px;">
        </div>
        <h5 class="gradient"></h5>
        <div class="mil address_mil">
            <div class="title">
                <h4><i></i>已保存的收货地址</h4>
                <h5>您已创建<span class="addressCount"></span>个收货地址，最多添加<span>10</span>个收货地址</h5>
            </div>
            <ul class="add_con">
                <!-- js拼接 -->
            </ul>
            <button id="add_address">新增收货地址</button>
        </div>
        <div class="mil address_mil add_address">
            <div class="title">
                <h4><i></i>新增收货地址</h4>
            </div>
            <form id="mainForm" method="post">
            	<div class="add_add_con">
                	<ul class="det">
                    	<li>
                        	<h5>收货人：</h5>
                        	<div class="right">
                            	<input name="staffAddress.consignee" id="consignee" type="text" value="${map.staffAddress.consignee}" placeholder="收货人姓名" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')" />
                        	</div>
                    	</li>
                    	<li>
                        	<h5>地址：</h5>
                        	<div class="right2">
                            	<select id="Select1" placeholder="--请选择--"></select>
                            	<select id="Select2" placeholder="--请选择--"></select>
                            	<select id="Select3" placeholder="--请选择--"></select>
                            	<input name="staffAddress.area" id="area" type="hidden" value="${map.staffAddress.area}">
                        	</div>
                        	<div class="address_right">
                            	<input name="staffAddress.addressDetailed" id="addressDetailed" type="text" value="${map.staffAddress.addressDetailed}" placeholder="详细地址" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')" />
                        	</div>
                    	</li>
                    	<li>
                        	<h5>手机：</h5>
                        	<div class="right">
                            	<input name="staffAddress.phone" id="phone" type="text" value="${map.staffAddress.phone}" placeholder="常用手机号" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')" />
                        	</div>
                    	</li>
                	</ul>
                	<div class="default_address">
                    	<p>是否设置为默认地址</p>
                    	<div class="right">
                        	<input type="button">
                        	<input name="staffAddress.isDefault" id="isDefault" type="hidden" value="${map.staffAddress.isDefault}" />
                    	</div>
                	</div>
                	<button type="button" class="button" onclick="ajaxAddStaffAddress();">保存收货地址</button>
            	</div>
            	<input type="hidden" name="staffAddress.addressID" id="addressID" value="${map.staffAddress.addressID}" />
            	<input type="hidden" id="formLimit" value="false" />
            </form>
        </div>
        <c:if test="${param.showParam eq 'payShoppingCart'}">
			<c:forEach items="${paramValues.cartIds}" var="cartIds" varStatus="status">
	        	<div class="cartParams">
		        	<input type="hidden" class="cartIds" value="${cartIds}">
		        	<input type="hidden" class="counts"  value="${paramValues.counts[status.index]}">
	        	</div>
			</c:forEach>
        </c:if>
    </div>
    <div class="det-add_alert_">
        <div class="det-add_alert">
            <h2>确认是否删除此收货地址？</h2>
            <div class="btn">
                <button id="abolish">取消</button>
                <button id="sure" style="background-color: #ff6600;">确认</button>
            </div>
        </div>
    </div>
    <!--尾部-->
    <div id="footer">
        <div class="text footer_txt">
          <!--   <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
<script type="text/javascript">
    /*省市县三级联动*/
   /*  addressInit('Select1', 'Select2', 'Select3','省/直辖市', '市', '区/县');  */
</script>
</body>
</html>