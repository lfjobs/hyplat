<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">

	<link href="<%=basePath%>css/ea/production/styleresume.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js"
	type="text/javascript"></script>

<title>添加简历名称</title>

</head>
<body>
    <div class="res_top res_top2">
        <ul>
            <li><a id="returnClick"><img src="<%=basePath%>images/resume/left_jt.png" alt=""></a></li>
            <li>简历名称</li>
            <li></li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="resume_name">
        <div>
            <input type="text" placeholder="给简历起个名字吧~" id="resunme" value="${rep}">
        </div>
        <div>
            <h3 id="tijiao"><img src="<%=basePath%>images/resume/dui.png" >提交保存</h3>
        </div>
    </div>

</body>
<script type="text/javascript">
var resumeID = "${resumeID}";//每个人的id
	var basePath="<%=basePath%>";
var staffid = "${param.staffid}";
var jitype="${param.jitype}";
var sccId="${param.sccId}";
var back = "${param.back}";
$(document).ready(function(){
		//返回
				$("#returnClick").click(function() {
						history.go(-1)
				});
				
			$("#tijiao").click(function() { 
			
			var resunme = $("#resunme").val();
			var url = basePath+"ea/resumes/sajax_ea_savePersion.jspa?staffid="+staffid+"&type=ajaxtype&resumeName="+resunme+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;    		
			    $.ajax({
				url : url,
				type : "post",
				success: function(){
					var url = basePath+"ea/resumes/ea_queryPersion.jspa?staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
					document.location.href = url; 	
				}	
				});
		});
	});
</script>
</html>