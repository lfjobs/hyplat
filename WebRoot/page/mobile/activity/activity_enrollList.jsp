<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>活动报名列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript">
	var basePath='<%=basePath%>';           
	var pNumber ='${pageNumber}';  
	var activityId="";
	var frameid = '<%=request.getParameter("frameid")%>';
	var mainheight = 0; //框架高度
</script>
</head>
<body>
	<form name="lForm" id="lForm" method="post">
		<div id="main_main" class="main_main" >
		<table class="JQueryflexme">
			
			<thead>
				<tr>
					<th width="30" align="center">
						序号
					</th>
					<th width="120" align="center">
						姓名
					</th>
					<th width="120" align="center">
						电话
					</th>
					<th width="120" align="center">
						活动时间
					</th>
					<th width="120" align="center">
						活动地点
					</th>
					<th width="120" align="center">
						单位
					</th>
					<%--<th width="180" align="center">
						身份证
					</th>
					<th width="250" align="center">
						地址
					</th>
					--%>
					<th width="200" align="center">
						微分金店
					</th>
					<th width="200" align="center">
						店主
					</th>
					<th width="200" align="center">
						电话
					</th>
					<th width="200" align="center">
						备注
					</th>
				</tr>			
				</thead>
			
				<tbody>
					<% int number = 1; %>
					<c:forEach var='arr' items="${pageForm.list}">
						<tr>
							<td>
								<span id="number"><%=number%></span>
							</td>
							<td>
								<span id="theme">${arr[0]}</span>
							</td>
							<td>
								<span id="content">${arr[1]}</span>
							</td>
							<td>
								<span id="content">${arr[9]}</span>
							</td>
							<td>
								<span id="content">${arr[7]}</span>
							</td>
							<td>
								<span id="content">${arr[8]}</span>
							</td>
							<%--<td>
								<span id="content">${arr[2]}</span>
							</td>
							<td>
								<span id="content">${arr[4]}</span>
							</td>
							--%>
							<td>
								<span id="endtime">${arr[4]}</span>
							</td>
							<td>
								<span id="endtime">${arr[5]}</span>
							</td>
							<td>
								<span id="endtime">${arr[6]}</span>
							</td>
							<td>
								<span id="endtime">${arr[3]}</span>
							</td>
						</tr>
						<% number++; %>
					</c:forEach>
				</tbody>
		</table>
		<c:import url="../../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/activity/ea_listByActivityId.jspa?activityId=${activityId}&pageNumber=${pageNumber}"></c:param>
		</c:import>
		<s:token></s:token>
		</div>
	</form>

	<script type="text/javascript">
	$(document).ready(function() {
		$('.JQueryflexme').flexigrid({
			title:"活动报名列表",
	        height: 200,
	        allDouble:true,
	        width: 'auto',
	        minwidth: 30,
	        minheight: 80,
			 buttons: [{
	            name: '设置每页显示条数',
	            bclass: 'mysearch',
				onpress : action//当点击调用方法
	        },{
	            separator: true
	        },{
				name : '导出',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
			}, {
				separator : true
			}]
	    });
	    function action(com, grid){
	        switch (com) {
			     case '设置每页显示条数':
				   	var url = basePath
							+ "ea/activity/ea_listByActivityId.jspa?activityId=${activityId}";
					numback(url);
					break; 
			     case '导出':
			    	var url = basePath + "ea/activity/ea_showStaffExcel.jspa?activityId=${activityId}";
					open(url);
					break;
	        }
	    }
	});
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