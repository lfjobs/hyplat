<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<%@ include file="/page/ea/BuildPlatform/wfjIndustryPlatfrom/coom.jsp"%>
 
<script>
var ppid ="${param.ppid}";
var param ="";
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
        <div class="gjpt_search">
            <input type="search" id="content">
            <div class="gjpt_search_">
                <img src="<%=basePath%>images/BuildPlatform/ico-search.png" alt="">
                <p>搜索</p>
            </div>
        </div>
        <div class="con_">
        <div class="gjpt_zzy gjpt_tjpt" >
        
        </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<%=basePath %>/js/BuildPlatform/IndustryPlatAjax.js"></script>
<script type="text/javascript">

function dj(this_obj,name){
	
	
	var url = "";
	if(name=="行业经济平台"){
		url = basePath+"/ea/WfjIndustryPlatform/ea_industry.jspa";
		
	}else{
		
		if(name=="省级"){
			param ="a";
			
		}else if(name=="县级"){
			param ="b";
			
		}else if(name=="村级"){
			param ="c";
			
		}
		url = basePath+"/ea/WfjIndustryPlatform/ea_getQuery.jspa?type=04&ppid="+this_obj+"&param="+param;
	}
	
	
	document.location.href = url;
	
	
	
}
</script>
</body>
</html>