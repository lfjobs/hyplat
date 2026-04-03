<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/digitalmall.css" />
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>

<title>地球数字商城搜索</title>
</head>

<body>
	<div class="search">
          <div class="search_width">
          	<div>
                <input id="sea_word" type="text" placeholder="请输入关键字" name="sea_word"/>
                <img onClick="openHtml()" src="<%=basePath %>images/WFJClient/DigitalMall/search.png">
            </div>
            <a href="<%=basePath%>ea/digitalmall/ea_DigitalMall.jspa?"><button>取消</button></a>
          </div>
                     
     </div>
     
<script type="text/javascript">
var basePath='<%=basePath%>';
$(document).ready(function(){
	$("#sea_word").keyup(function(){
		var proName = $("#sea_word").val();
		if( proName == ""){
			alert("请输入关键字！");
		}else{
				window.open(basePath+"ea/digitalmall/ea_DigitalMall.jspa?flag=1&proName="+proName,"_self");
		}
	});
});
	
function openHtml(){
	var proName = document.getElementById("sea_word").value;
	if( proName == ""){
		alert("请输入关键字！");
	}else{
			window.open(basePath+"ea/digitalmall/ea_DigitalMall.jspa?flag=1&proName="+proName,"_self");
	}
}
</script>
</body>
</html>
