<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String staffId=request.getParameter("staffId");
	String editType=request.getParameter("editType");
	String backurl=request.getParameter("backurl");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/region_select.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_address_add.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/add_address.css"/>
<title>添加收货地址</title>
<script type="text/javascript">
var basePath="<%=basePath%>";
var staffId="<%=staffId%>";
var editType="<%=editType%>";
var backurl="<%=backurl%>";
var source = "${param.source}";
var inventoryID = "${param.inventoryID}";
var ppid = "${param.ppid}";
var count = "${param.count}";
var intf = "${param.intf}";
</script>
<style type="text/css">
#prompt div{
		width: 70%;
		background: rgba(0,0,0, 0.5);
	}
</style>
</head>

<body>
<div class="main">
	<div class="top">
        	<ul>
            	<li class="arrow"><a href="javascript:;" target="_self"><img src="<%=basePath%>images/ea/websitemall/card/arrow.png"/></a></li>
            	<li>新建收货地址</li>
            	<li style=" visibility:hidden;"><a href="javascript:;"></a></li>
                <div class="clear"></div>
            </ul>
    </div>
    <div class="add"><div align="right">
    	<button type="button" id="subs">保存</button></div>
    </div>
     <div id="prompt" style="width: 100%;display: none;	">
        <center>
        	<div>
        		<span style="position: relative;top: 19.8%;"></span>
        	</div>
        	</center>
        </div>
    <div class="line"></div>
    <form class="content" id="form" name="form" method="post">
    	<input type="submit" id="submit" name="submit" style="display: none;">
    	<iframe name="hidden"  style="display: none;"></iframe>
        <input type="hidden" name="staffVo.address['0'].area" value="" id="str">
          <input type="hidden" name="staffId" value="<%=staffId%>" id="staffId">
		<div class="new_"></div>
    	<ul class="ul">
        	<li><h5>收货人</h5><input placeholder="请填写收货人" name="staffVo.address['0'].consignee"/></li><div class="xt"></div>
            <li><h5>手机号码</h5><input placeholder="请填写手机号码"  name="staffVo.address['0'].phone" type="tel" /></li><div class="xt"></div>
            <li id="location_p"><h5>省</h5></li><div class="xt"></div>
            <li id="location_c"><h5>市</h5><select><option>请选择</option></select></li><div class="xt"></div>
            <li id="location_a"><h5>区</h5><select><option>请选择</option></select></li><div class="xt"></div>
            <li><h5>详细地址</h5><input placeholder="请填写详细地址" name="staffVo.address['0'].addressDetailed" id="address" /></li><div class="xt"></div>
<!--             <li><h5>邮政编码</h5><select id="postcode" name="staffVo.receiptAddress['0'].postcode"><option>请选择</option></select></li><div class="xt"></div> -->

            <div class="clear"></div>
        </ul>
    </form>
</div>

 <script type="text/javascript">
	 var flag="${param.flag}";
	</script>
</body>
</html>
