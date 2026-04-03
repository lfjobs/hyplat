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
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<link href="<%=basePath%>css/ea/production/stylezt.css"
	rel="stylesheet" type="text/css" />

<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js" type="text/javascript"></script>

<title>自我评价</title>

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var resumeID="${param.resumeID}";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
    var back = "${param.back}";

</script>
</head>
<body class="body">
    <div class="res_top">

    <input type="hidden" id="staffid" value="${param.staffid}">
        <ul>
            <li><a><img src="<%=basePath%>images/resume/left_jt.png" id="returnClick"></a></li>
            <li>自我评价</li>
            <!--个人简历.html  -->
            <li><a id="resume">保存</a></li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot self-ass">
        <div>
            <textarea rows="5" id="evaluate">${persion.evaluate }</textarea>
            <p>还可以输入<span>400</span>个字</p>
        </div>

    </div>
<script type="text/javascript">
        $(document).ready(function(){
        
      //返回
        $("#returnClick").click(function() {
        var staffid = $("#staffid").val();
		        	
		    		document.location.href=basePath+"ea/resumes/ea_queryPersion.jspa?staffid="+staffid+"&resumeID="+resumeID+"&jitype="+jitype+"&back="+back;
		        
        
        });
              //跳转求职意向
       $("#resume").click(function() {
       var evaluate = $("#evaluate").val();
         var staffid = $("#staffid").val();
       var url = basePath + "ea/resumes/ea_savePersion.jspa?evaluate="+evaluate+"&type=ren"+"&staffid="+staffid+"&resumeID="+resumeID+"&jitype="+jitype+"&back="+back;
		document.location.href = url;
      
      });
            
        });
    </script>
</body>
</html>