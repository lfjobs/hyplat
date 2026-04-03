<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>个人中心收货地址添加页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, count-scalable=yes" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/add_address.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
 <script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/addaddress.js"></script> 
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}

</style>
</head>
<body>
<div class="main"> 
<%-- 	<div class="top">
        	<ul>
            	<li class="arrow"><a href="<%=basePath %>ea/wfjshop/ea_getAddressList.jspa?inventory.inventoryID=${inventory.inventoryID}&ppid=${ppid}&count=${count}&intf=${intf}&companyId=${companyId}" target="_self"><img src="<%=basePath %>images/WFJClient/PersonalJoining/arrow.png"/></a></li>
            	<li>新建收货地址</li>
            	<li style=" visibility:hidden;"><a href="javascript:;"></a></li>
                <div class="clear"></div>
            </ul>
    </div> --%>
    <!-- 提示窗口 -->
    <div id="prompt" style="width: 100%;display: none;">
        			<center>
        				<div>
        					<span style="position: relative;top: 19.8%;"></span>
        				</div>
        			</center>
    </div>
    <div class="add">
    	<button>保存</button>
    </div>
    <div class="line"></div>
    <form class="content" id="addAddress" name="addAddress" method="post">
    <input type="submit" name="submit" style="display:none;"/>
    <input id="address" type="hidden" name="area" value=""/>
    <input type="hidden" name="user" value="${user}">
    <input id="postCode" type="hidden" name="postCode" value=""/>
    	<ul>
        	<li><h5>收货人</h5><input class="verification" value=""  name="consignee"/></li><div class="xt"></div>
            <li><h5>手机号码</h5><input class="verification" value="" name="phone" maxlength="11"/></li><div class="xt"></div>
            <li><h5>省</h5><select name="location_p" id="location_p">
    </select></li><div class="xt"></div>
            <li><h5>市</h5><select name="location_c" id="location_c">
    </select></li><div class="xt"></div>
            <li><h5>区</h5><select name="location_d" id="location_d"></select></li><div class="xt"></div>
<!--             <li><h5>乡/镇/街道</h5><input type="text" id="add1"/></li><div class="xt"></div> -->
            <li><h5>详细地址</h5><input class="verification" id="addressDetailed" value="" name="addressDetailed" /></li><div class="xt"></div>
<%--             <li id="post"><h5>邮政编码</h5><select id="location_code"></select></li><div class="xt"></div> --%>
            <div class="clear"></div>
        </ul>
    </form>
</div>
<script type="text/javascript">
	var basePath='<%=basePath%>';
    var change="";
    var change1="";
	$(document).ready(function(e){
		$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;")
			//头部
        $(".top").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;");
        $(".top").find("li").attr("style","width:10%;");
        $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
        $(".top").find("li").eq(1).attr("style","width:80%;font-size:"+$(window).height()*0.035+"px;");
		//add
		$(".add button").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.03+"px;border-radius:"+$(window).height()*0.01+"px; margin:"+$(window).height()*0.02+"px auto;");
	
		
		$("#prompt").css("position","absolute").css("top",$(window).height()*0.1+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
	});
		//line
		$(".line").attr("style","height:"+$(window).height()*0.01+"px;");
		//content
		$(".content").attr("style","height:"+$(window).height()*0.83+"px;");
		$(".content li h5").attr("style","height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px;");
		$(".content li input").attr("style","height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px;");
		$(".content li select").attr("style","height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px;");
		$(".content .del").attr("style","height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.09+"px;font-size:"+$(window).height()*0.03+"px; margin:"+$(window).height()*0.03+"px 0;");
		$(".add").find("button").click(function(){
			//邮编赋值		
		//	$("#postCode").val($("#location_code").find("option:selected").attr("class"));
		//	$("#addressDetailed").val($("#add1").val()+$("#addressDetailed").val());
			var phone=new RegExp("^1[3|4|5|7|8][0-9]\\d{8}$");
			var postcode=new RegExp("[1-9]\\d{5}(?!\d)");
			if($(".verification").eq(0).val()=="")
				prompt("收货人姓名不能为空");
			else if($(".verification").eq(1).val()=="")
				prompt("手机号码不可为空");
			else if($(".verification").eq(1).val()=="")
				prompt("手机号码格式不对");
			else if($("#location_p").find("option:selected").text()=='--请选择--')
				prompt("请选择省");
			else if($("#location_c").find("option:selected").text()=='--请选择--')
				prompt("请选择市");
			else if($("#location_d").find("option:selected").text()=='--请选择--')
				prompt("请选择区");	
			else if($(".verification").eq(2).val()=="")
				prompt("详细地址不能为空");
			else{
				$("#addAddress").attr("action",basePath+"ea/wfjshop/ea_addPersonAddr.jspa");
				document.addAddress.submit.click();
			}
		});
		function prompt(obj){
			if($("#prompt").css("display")!="none")
				return;
			$("#prompt").find("span").text(obj);
			$("#prompt").fadeIn(500);
			setTimeout(function(){
				$("#prompt").fadeOut(500);
				$("#prompt").find("span").text("");
			}, 2000);
		}
		 
</script>
</body>
</html>
