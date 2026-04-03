<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>个人中心收货地址列表</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/wfjapp.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
</head>
<body>
	<div class="wfj11_016">
        <div class="wfj11_016_content">
        	<div class="wfj11_016_hidden">
        	<c:forEach items="${ staffAddressList}" var="list">
                 <c:if test="${list.consignee!=null &&list.phone!=null &&list.addressDetailed!=''&&list.area!=null}">
                    <div class="wfj11_016_consignee">
                        <div class="wfj11_016_width">
                            <table onclick="detial('${list.addressID }')">
                                <tr>
                                    <td><input type="hidden" value="${list.addressKey }"/></td>
                                    <td width="40%">收货人：<span>${list.consignee }</span></td>
                                    <td width="40%">${list.phone }</td>
                                    <td width="20%">
                                    <c:if test="${list.isDefault eq '是' }"><font color="red">默认</font></c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td><img style="width:60%;" src="<%=basePath %>images/WFJClient/PersonalJoining/wfj11_address_01.png" /></td>
                                    <td colspan="2">收货地址：${ list.area}${list.addressDetailed }</td>
                                    <td align="right">
                                    
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div></c:if>
                </a></c:forEach>
        	</div>
        </div>
        
        <div class="wfj11_016_bottom_address">
        	<div>添加收货地址</div>
        </div>
    
	<script type="text/javascript">
	var basePath='<%=basePath%>';
	var user="${user}";
        $(document).ready(function(e) {
            $("body").css("height",$(window).height()) ;
			$(".wfj11_016_content").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()*0.9+"px;overflow:hidden;");
			$(".wfj11_016_hidden").attr("style","width:"+parseInt($(".wfj11_016_content").width()+17)+"px;height:"+parseInt($(".wfj11_016_content").height()*0.94+17)+"px;overflow:auto;");
			//修改字体大小
				$("#tops").find("li").attr("style","float:left;");
				$("#tops").find("li").eq(0).attr("style","width:15%;");
				$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
				$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
				$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
				$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
				
			$(".wfj11_016_top").css("height",$(window).height()*0.08+"px");
			$(".wfj11_016_top").css("lineHeight",$(window).height()*0.08+"px");

			$(".wfj11_016_bottom_address").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			$(".wfj11_016_bottom_address").find("div").attr("style","font-size:"+$(window).height()*0.03+"px;border-radius:"+$(window).height()*0.01+"px;cursor:pointer;");

			$(".wfj11_016_consignee").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.03+"px;");
			$(".wfj11_016_consignee").attr("style","padding:"+$(window).height()*0.02+"px 0; margin-bottom:"+$(window).height()*0.01+"px;");
			$(".wfj11_016_bottom_address").find("div").click(function(){
				var parm=new Array();
				parm.push(basePath);
				parm.push("ea/wfjshop/ea_toaddAddress.jspa?");
				parm.push("user="+user);
				parm.push("&flag=recive");
				open(parm.join(""),"_self")
			});
			
			$(".change").click(function(){
				$(".change").find(".changeimg").attr("src",basePath+"images/WFJClient/PersonalJoining/choice_02.png");
				$(this).find(".changeimg").attr("src",basePath+"images/WFJClient/PersonalJoining/choice_01.png");
			});
			
        });
        function detial(adid){
        	var param=new Array();
        	param.push("user="+user);
        	param.push("&staffAddress.addressID="+adid);
        	document.location.href=basePath+"ea/wfjshop/ea_toDetail.jspa?"+param.join("");
        }
    </script>
</body>
</html>