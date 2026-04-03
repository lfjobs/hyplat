<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>5L5C微办公主页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/websuitMini/pub.css" />
  	<!-- Attach our CSS -->
	  	
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.min.js"></script>
		<style>
body{ background-color:#b2ebff;}
.contents{width:360px; margin:auto;}
.c_left{ width:40%; height:180px; border-top:1px solid #fff;}
.c_left .l_nav{ width:100%; height:35px; line-height:35px; border-bottom:1px solid #fff; border-right:1px solid #fff; border-left:1px solid #fff; cursor:pointer;}
.c_left .l_nav span{ color:#2fb0c8; display:block; line-height:35px; margin-left:20px;}
.c_left .nav_sel{  position:relative;}
.c_left .nav_sel img{ width:12px; height:12px; position:absolute; top:11px; left:115px;}
.c_right{ width:60%; height:450px;}
.r_nav{ width:100%; height:80px; line-height:80px; margin-top:10px;}
.r_nav img { float:left; width:60px; height:60px; margin-left:20px;}
.r_nav p { float:left; width:100%; height:20px; line-height:20px; color:#2fb0c8; text-align:center;}
.r_nav_left{ width:100px; height:80px; margin-left:35px;}
.r_nav_right{ width:100px; height:80px; margin-left:105px;}
 span{
font-size: 12px;
}
p{
font-size: 14px;
} 
</style>
<script type="text/javascript">
$(document).ready(function(){

})
function  choosetask(val){
if(val=="perssion"){
$("#"+ val).css("background-color","white");
window.location.href="<%=basePath%>page/ea/websuitMini/index.jsp"

}
if(val=="dept"){
window.location.href="<%=basePath%>page/ea/websuitMini/company_information.jsp"
}
}

</script>
  </head>

 <body style="margin-top: 63px;">
 <div id="topbar" style="display: block;">
   <div id="topbar_title" class="topbar_title">5L5C移动办公系统</div>
   <div id="topbar_back" ontouchstart="" style="display: block;"><img src="<%=basePath%>images/websuitMini/Set.png"></div>
   <div id="topbar_menu" class="topbar_menu" ontouchstart=""><img src="<%=basePath%>images/websuitMini/Up.png"/></div>
</div>
<div class="contents">
   <div class="c_left fl">
      <div class="l_nav nav_sel fl" id="dept" onclick="choosetask(id)">
          <span>进入部门办公</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav nav_sel fl" id="perssion" onclick="choosetask(id)">
          <span>进入个人办公</span>
         <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>进入客户查询</span>
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>进入政府查询</span>
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>进入微企业</span>
      </div>
      <div class="clear">
      </div>
   </div>
   <div class="c_right fl">
        <div class="r_nav fl">
           <div class="r_nav_left fl">
               <img alt="" title="" src="<%=basePath%>images/websuitMini/5L5C1 (1).png" />
               <p>人事公共信息</p>
           </div>
        </div>
        <div class="clear"></div>
        <div class="r_nav fl">
           <div class="r_nav_right fl">
               <img alt="" title="" src="<%=basePath%>images/websuitMini/5L5C1 (2).png" />
               <p>办公室公共信息</p>
           </div>
        </div>
        <div class="clear"></div>
        <div class="r_nav fl">
           <div class="r_nav_right fl">
               <img alt="" title="" src="<%=basePath%>images/websuitMini/5L5C1 (3).png" />
               <p>财务公共信息</p>
           </div>
        </div>
        <div class="clear"></div>
        <div class="r_nav fl">
           <div class="r_nav_right fl">
               <img alt="" title="" src="<%=basePath%>images/websuitMini/5L5C1 (4).png" />
               <p>生产公共信息</p>
           </div>
        </div>
        <div class="clear"></div>
        <div class="r_nav fl">
           <div class="r_nav_left fl">
               <img alt="" title="" src="<%=basePath%>images/websuitMini/5L5C1 (5).png" />
               <p>营销公共信息</p>
           </div>
        </div>
        <div class="clear"></div>
   </div>
</div>
 
</body>
</html>
