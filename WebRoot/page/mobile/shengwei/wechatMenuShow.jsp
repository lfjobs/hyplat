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
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script id="jsID" src="<%=basePath%>js/jquery-1.3.2.js" type="text/javascript"></script>
	
	<title>${beans[0].menuName }</title>
	<style>
	*{margin:0;padding:0;font-size:12px;line-height:16px;}
	li{ list-style:none;}
	img{boeder:none 0; cursor:pointer;}
	body{ background-color:#e6e6e6;}
	.bj{ border:#c8c8c8 1px solid; background-color:#fff;margin:auto; margin-top:20px;width:90%;}
	.banner{ border:#fff 12px solid; position:relative;}
	.banner p{font-family:"微软雅黑";color:white; font-size:16px;position:absolute; left:15px; bottom:20px;}
	.banner img{width:100%;height:30%;}
	.list{width:100%;height:60px;}
	.line{width:100%;height:1px; background-color:#c8c8c8;}
	.box{width:100%;}
	.box a{ font-family:"微软雅黑";color:#323232; font-size:16px; line-height:60px;margin-left:35px;float:left;text-decoration:none;}
	.box .pic{ display:block; background-repeat:no-repeat;float:right;margin-top:5px;margin-right:12px;}
	</style>
</head>
<body>
<div class="bj">
	<div class="banner">
		<c:if test="${beans[0].menuName !=null}">
	    	<p>${beans[0].menuName }</p>
	    	<a href="<%=basePath%>ea/wechatmenu/getMenuById.jspa?menuId=${beans[0].menuId }&parameter=front"><img src="<%=basePath %>${beans[0].image }"></a>
	 	</c:if>
	</div>
    <s:iterator value="listmenu" >
	    <div class="list">
	    	<div class="line"></div>
	    	<c:if test="${menuId  !=null}">
		        <div class="box">
		            <a href="<%=basePath%>ea/wechatmenu/getMenuById.jspa?menuId=${menuId }&parameter=front">${menuName }</a>
		            <div class="pic">
		            	<a href="<%=basePath%>ea/wechatmenu/getMenuById.jspa?menuId=${menuId }&parameter=front"><img src="<%=basePath %>${image}" style="width: 50px;height: 50px;"/></a>
		            </div>
		        </div>
	        </c:if>
	    </div>
    </s:iterator>
   
</div>
</body>
</html>