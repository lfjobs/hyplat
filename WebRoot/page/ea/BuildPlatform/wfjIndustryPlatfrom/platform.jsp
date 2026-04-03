
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<jsp:include page="/page/ea/BuildPlatform/wfjIndustryPlatfrom/coom.jsp"/>
<html>    
<script>

var ppid ="${param.ppid}";

</script>

<body>

<header class="head">
   <ul>
       <li style="width: 10%;">
           <a href="javascript:history.go(-1)"><img src="<%=basePath%>images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">添加经济平台</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden content_hidden_">
    <div class="content content_">
       
        <div class="con_">
        <div class="gjpt_zzy gjpt_tjpt" >
        
        </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<%=basePath %>/js/BuildPlatform/Industry.js"></script>
<script type="text/javascript">

function dj(name){
	
	var url = basePath+"/ea/WfjIndustryPlatform/ea_getAddPlatform.jspa?goodsname="+name;
	document.location.href = url;
}
</script>
</body>
</html>