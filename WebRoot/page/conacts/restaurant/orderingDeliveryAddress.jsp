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
<title>订单选择收货地址管理ss</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/address.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
</head>

<body>
<div class="main">
	<div class="top">
        	<ul>
				<c:if test="${param.backurls ne null}">
					<li class="arrow"><a href="javascript: window.history.go(-1);" target="_self">
						<img src="<%=basePath %>images/WFJClient/PersonalJoining/arrow.png"/></a></li>
				</c:if>
               <c:if test="${param.backurls eq null}">
            	<li class="arrow"><a href="<%=basePath %>ea/restaurant/ea_queryLoginName.jspa?companyId=${companyId}&ccompanyId=${ccompanyId}&staffAddress.addressID=${list.addressID}&selecton=2&scandc=${param.scandc}" target="_self">
            	<img src="<%=basePath %>images/WFJClient/PersonalJoining/arrow.png"/></a></li>
			   </c:if>
            	<li>管理收货地址</li>
                <div class="clear"></div>
            </ul>
    </div>
    <div class="add">
    	<a href="#"><button>添加新地址</button></a>
    </div>
    <div class="line"></div>
    <div class="content">
    	<ul>
    		<c:forEach items="${ staffAddressList}" var="list">
				<c:if test="${param.backurls ne null}">
				<a href="${param.backurls}&staffAddress.addressID=${list.addressID}"
				   class="chance" target="_self">
				</c:if>
                <c:if test="${intf!=1&&param.backurls eq null}">
                   <a href="<%=basePath %>ea/restaurant/ea_queryLoginName.jspa?companyId=${companyId}&ccompanyId=${ccompanyId}&staffAddress.addressID=${list.addressID}&selecton=2&scandc=${param.scandc}"
                 	class="chance" target="_self">
                </c:if>
				<li>
				<c:if test="${list.consignee!=null &&list.phone!=null &&list.addressDetailed!=''&&list.area!=null}">
            	<div class="left">
            		<input type="hidden" value="${list.addressKey }"/>
            		<input class="is" type="hidden" value="${list.isDefault }"/>
                	<h3>
                    	<p class="people">收货人：${list.consignee }</p>
                        <p class="tel">${list.phone }</p>
                    </h3>
                    <h6>收货地址：${ list.area}${list.addressDetailed }</h6>
                </div>
                <div class="right"><img src="<%=basePath %>images/WFJClient/PersonalJoining/tick.png" /></div></c:if>
            </li></a>
            <div class="xian"></div></c:forEach>
            <div class="clear"></div>
        </ul>
    </div>
</div>


<script type="text/javascript">
	var basePath='<%=basePath%>';
    var scandc = "${param.scandc}";
	$(document).ready(function(e){

            if(scandc=="scandc"){
                $(".arrow").html("&nbsp;");

            }


		$("body").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;")
			//头部
        $(".top").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;");
        $(".top").find("li").attr("style","width:10%;");
        $(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
        $(".top").find("li").eq(1).attr("style","width:80%;font-size:"+$(window).height()*0.035+"px;");
		//add
		$(".add button").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.03+"px;border-radius:"+$(window).height()*0.01+"px; margin:"+$(window).height()*0.02+"px auto;");
		
		//line
		$(".line").attr("style","height:"+$(window).height()*0.01+"px;");
		//content
		$(".content").attr("style","height:"+$(window).height()*0.83+"px;");
		//li
		 $(".content ul").find("li").attr("style","padding:"+$(window).height()*0.02+"px 0;")
 		 $(".content ul").find("li").each(function(){
			 if($(this).find("input[class='is']").val()=='是'){
				$t=$(this).parent().clone(true);
				$t1=$(".content ul").find("li").eq(0).parent().clone(true);
				$(".content ul").find("a").eq(0).remove();				
				$(".content ul").prepend($t);
				$(this).parent().before($t1);
				$(this).parent().remove();
			 }
		 }); 
		 //$(".content ul").find("li").eq(0).attr("style","background:#5e6b85;color:#fff;padding:"+$(window).height()*0.02+"px 0;");
		 $(".content ul").find("li").eq(0).find("h6").prepend("【默认】");
		 $("h3").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px;");
		 $("h6").attr("style","line-height:"+$(window).height()*0.04+"px;font-size:"+$(window).height()*0.02+"px;");
		 //img
		 $(".right img").attr("style","width:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px; margin:"+$(window).height()*0.03+"px auto;");
		 $(".chance").click(function(){
			 $(this).find("li").attr("style","background:#5e6b85;color:#fff;padding:"+$(window).height()*0.02+"px 0;");
		 });
	});
	$(".add").find("button").click(function(){
	
    		
		var inventoryID= '${inventory.inventoryID}';
		var companyId='${companyId}';
		var ccompanyId = "${ccompanyId}";
		var ppid='${ppid}';
		var count='${count}';
		var intf='${intf}';
		var stype = '${stype}';

        if(intf==31){
            var  backurls = window.location.href;
            open(basePath+"ea/wfjshop/ea_shippingAddress.jspa?inventory.inventoryID="+inventoryID+"&ppid="+ppid+"&count="+count+"&intf="+intf+"&companyId="+companyId+"&ccompanyId="+ccompanyId+"&stype="+stype+"&scandc="+scandc+"&staffid=${param.staffid}&backurls="+encodeURIComponent(backurls),"_self");


        }else{
            open(basePath+"ea/wfjshop/ea_shippingAddress.jspa?inventory.inventoryID="+inventoryID+"&ppid="+ppid+"&count="+count+"&intf="+intf+"&companyId="+companyId+"&ccompanyId="+ccompanyId+"&stype="+stype+"&scandc="+scandc,"_self");

        }

	});
</script>
</body>
</html>
