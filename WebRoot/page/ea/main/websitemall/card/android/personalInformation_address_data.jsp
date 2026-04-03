<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <%--<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/keyboard/zepto.min.js"></script>--%>
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/region_select.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/supermarket/font-size.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_address_data.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/dialog.js"></script>--%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/default_address.css"/>
<%--<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/dialog.css"/>--%>
<title>收货地址详细信息</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var editType="${editType}";
	var backurl="<%=backurl%>";
	var flag="${param.flag}";
	var area = "${addres.area}"
    var source = "${param.source}";
    var inventoryID = "${param.inventoryID}";
    var ppid = "${param.ppid}";
    var count = "${param.count}";
    var intf = "${param.intf}";
    var backurls = "${param.backurls}";//mz
    var staffid = "${staffId}";
</script>
<style type="text/css">
#prompt div{
		width: 70%;
		background: rgba(0,0,0, 0.5);
	}
</style>
</head>

<body>
<div class="del_c">
    <section>
        <p class="ctip">是否删除该收货地址</p>
        <div>
            <p id="no">否</p>
            <p id="yes">是</p>
        </div>
    </section>
</div>
<div class="del_d">
    <section>
        <p>操作成功</p>
        <p>确定</p>
    </section>
</div>

<form  id="form" name="form" method="post">
    	<input type="submit" id="submit" name="submit" style="display: none;">
    <input type="hidden" value="11" name="message">

    	<iframe name="hidden"  style="display: none;"></iframe>
<div class="main">
	<div class="top">
        	<ul>
            	<li class="arrow"><a href="javascript:;" target="_self"><img src="<%=basePath%>images/ea/websitemall/card/arrow.png"/></a></li>
            	<li>收货地址</li>
            	<a href="javascript:;" id="edit"><li>修改</li></a>
                <div class="clear"></div>
            </ul>
    </div>
    <div class="add">
    	<button type="button" class="default">设置默认收货地址</button>
    </div>
    <div id="prompt" style="width: 100%;display: none;	">
        <center>
        	<div>
        		<span style="position: relative;top: 19.8%;"></span>
        	</div>
        	</center>
        </div>
    <div class="line"></div>
    <div class="content">       
    	<ul class="data ul">
        	<li><h5>收货人</h5><input readonly="readonly" value="${addres.consignee}" name="staffVo.address['0'].consignee"/></li><div class="xt"></div>
            <li><h5>手机号码</h5><input readonly="readonly" value="${addres.phone}" name="staffVo.address['0'].phone"/></li><div class="xt"></div>
            <li id="p" class="address"><h5>省</h5><input readonly="readonly" value="" /></li><div class="xt"></div>
            <li id="c" class="address"><h5>市</h5><input readonly="readonly" value="" /></li><div class="xt"></div>
            <li id="a" class="address"><h5>区</h5><input readonly="readonly" value="" /></li><div class="xt"></div>
            <li><h5>详细地址</h5><input readonly="readonly" value="${addres.addressDetailed}" name="staffVo.address['0'].addressDetailed" id="address"/></li><div class="xt"></div>
           <%-- <li id="postcode" style="display: none;"><h5>邮政编码</h5><input readonly="readonly" value="${addres.postcode}"name="staffVo.receiptAddress['0'].postcode" type="tel" /></li><div class="xt" ></div>--%>
            <div class="clear"></div>
            <input type="hidden" name="staffId" value="${staffId}" id="staffId">
        	<input type="hidden" name="addressID" value="${addres.addressID}">
        </ul>
        <div class="del"><p>删除收货地址</p></div>
    </div>
</div>


<div class="alert">
    	<div class="alert_bg"></div>
        <div class="alert_con">
        	<p id="jg">是否确定删除</p>
        </div>
    </div>
</form>
</body>
</html>
