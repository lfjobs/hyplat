<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<link href="<%=basePath%>css/ea/production/stylezt.css"
	rel="stylesheet" type="text/css" />

<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js" type="text/javascript"></script>


<title>期望薪资</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
    var back = "${param.back}";
</script>
</head>
<body>
    <div class="res_top">
    <input type="hidden" id="typep" value="${param.type}">
    	<input type="hidden" id="resumeID" value="${param.resumeID}">
		<input type="hidden" id="work" value="${param.work}">
		<input type="hidden" id="region" value="${param.region}">
		<input type="hidden" id="position" value="${param.position}">
		<input type="hidden" id="industry" value="${param.industry}">
		<input type="hidden" id="status" value="${param.status}">
		<input type="hidden" id="staffid" value="${param.staffid}">
		<input type="hidden" id="staffIDs" value="${param.staffIDs}">
		
        <ul>
            <li><a><img src="<%=basePath%>images/resume/left_jt.png" id="returnClick"></a></li>
            <li>期望薪资</li>
           
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot sal">
      
        <p>1000元/月以下</p>
        <p>1000元-2000元/月</p>
        <p>2001元-4000元/月</p>
        <p>4001元-6000元/月</p>
        <p>6001元-8000元/月</p>
        <p>8001元-10000元/月</p>
        <p>10001元-15000元/月</p>
        <p>15001元-25000元/月</p>
        <p>25000元/月以上</p>
        <p>面议</p>
    </div>
<script type="text/javascript">

        $(document).ready(function(){
      //返回
        $("#returnClick").click(function() {history.go(-1)});
         $(".res_bot p").click(function(){
         var moneyid=$(this).text();
         var work = $("#work").val();//工作性质
		 var region = $("#region").val();//工作地区
		 var position = $("#position").val().replace(/\|/g,'%7C');//职位类别
		 var industry =$("#industry").val();//行业类别
		 var status = $("#status").val();//求职状态
		 var staffid = $("#staffid").val();
		 var staffIDs=$("#staffIDs").val();
		 var resumeID=$("#resumeID").val();
		  var type=$("#typep").val();
		var url =  basePath+"ea/resumes/ea_querySearch.jspa?salary="+moneyid+
         "&work="+work+"&region="+region+"&position="+position+"&industrys="
         +industry+"&status="+status
         +"&staffid="+staffid+"&staffIDs="+staffIDs+"&resumeID="+resumeID+"&type="+type+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
		document.location.href = url;
      
      });  
        });
    </script>
    <script src="<%=basePath%>js/ea/production/resume/javascript.js" type="text/javascript"></script>
</body>
</html>