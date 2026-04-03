<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信活动列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/wechat/wechatactivity.js"></script>
<script type="text/javascript">
	var basePath='<%=basePath%>';           
	var pNumber ='${pageNumber}';  
	var goodsid = "${goodsid}";
	var status1 = "${status1}";
	var search = "${search}";
	var activityId="";
	var frameid = '<%=request.getParameter("frameid")%>';
	var mainheight = 0; //框架高度
	var inforType = '<%=request.getParameter("inforType") %>';
</script>
</head>
<body>
	<form style="display: none;" name="addressForm" id="addressForm" method="post">
		<s:token></s:token>
		<input type="submit" name="submit" style="display:none"/>
	</form>
	<div id="main_main" class="main_main" >
		<table class="JQueryflexme">
			<thead>
				<tr>
					<th width="30" align="center">
						选择
					</th>
					<th width="30" align="center">
						序号
					</th>
					<th width="230" align="center">
						${inforType=="00"?"活动主题":"信息主题"}
					</th>
					<s:if test="inforType=='00'">
					<th width="100" align="center">
						活动类型
					</th>
					</s:if>
					<th width="200" align="center">
						${inforType=="00"?"活动时间":"时间"}
					</th>
					<s:if test="inforType=='00'">
					<th width="150" align="center">
						报名时间
					</th></s:if>
					<th width="150" align="center">
						发布时间
					</th>
				</tr>			
				</thead>
			
				<tbody>
					<% int number = 1; %>
					<c:forEach var='arr' items="${pageForm.list}">
						<tr  id="${arr.id}">
							<td>
								<input type="radio" name="chbox" class="JQuerypersonvalue" value="${arr.id}" />
							</td>
							<td>
								<span id="number"><%=number%></span>
							</td>
							<td>
								<span id="theme">${arr.theme}</span>
							</td>
							<td>
								<span id="content">${arr.activityType}</span>
							</td>
							<td>
								<span id="content">${arr.publishTime}</span>
							</td>
							<td>
								<span id="starttime"><fmt:formatDate value="${arr.starttime}"  pattern="yyyy-MM-dd" /> </span>-
								<span id="endtime"><fmt:formatDate value="${arr.endtime}"  pattern="yyyy-MM-dd" /> </span>
							</td>
							<td>
								<span id="endtime"><fmt:formatDate value="${arr.publishTime}"  pattern="yyyy-MM-dd HH:mm" /></span>
							</td>
						</tr>
						<% number++; %>
					</c:forEach>
				</tbody>
		</table>
		<c:import url="../../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/activity/ea_activityList.jspa?frameid=${param.frameid}&pageNumber=${pageNumber}&search=${search}&inforType=${inforType}"></c:param>
		</c:import>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</div>
	<script type="text/javascript">
	 $(function(){
	       setTimeout(function(){ 
			   var _height = $(window).height();	
			   if($("#mainframe").height() > 0){
			       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
			   }else{		    
			   	   $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
			       $("#mainframe").css({"height": 0 + "px"});
			   }
			},100);
	    	
		    $(window).resize(function(){ 
				setTimeout(function(){ 
			    var _height = $(window).height();		
			    if($("#mainframe").height() > 0){
			        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
				    $("#mainframe").css({"height": _height / 2 - 20 + "px"});
			    }else{		 
			   	    $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
					$("#mainframe").css({"height": 0 + "px"});
			    }
			},100);
		    }); 
	   });
	</script> 
</body>
</html>