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
<script src="<%=basePath%>js/ea/production/resume/javascript.js" type="text/javascript"></script>

<title>个人简历</title>

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
</script>
</head>
<body>
    <div class="res_top">
        <ul>
            <li><a><img src="<%=basePath%>images/resume/left_jt.png" id="returnClick"></a></li>
            <li>职位薪资</li>
            <!--添加实习经验.html  -->
            <li><a id="addInternshipExperience">保存</a></li>
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
        //跳转添加实习经验
       $("#addInternshipExperience").click(function() {
       var url = basePath + "/page/ea/main/production/resume/addInternshipExperience.jsp?sccId="+sccId+"&jitype="+jitype;
		document.location.href = url;
      
      });
            
        });
    </script>
</body>
</html>