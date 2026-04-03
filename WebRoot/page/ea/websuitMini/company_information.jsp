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
.c_left{ width:40%; border-top:1px solid #fff;}
.c_left .l_nav{ width:100%; height:35px; line-height:35px; border-bottom:1px solid #fff; border-right:1px solid #fff; border-left:1px solid #fff; cursor:pointer; position:relative;}
.c_left .l_nav span{ color:#2fb0c8; display:block; line-height:35px; margin-left:20px;}
.c_left .nav_sel{ background:#fff; position:relative;}
.c_left img{ width:12px; height:12px; position:absolute; top:11px; left:100px;}
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
<script>
$(document).ready(function(){ 
   var arr= [13,12,11,10,9,8,7,6,5,4,3,2,1];
   $(".l_nav").each(function(index, element) {
        $(this).attr("index",arr[index]);
		$(this).click(function(){
			$(".l_nav img").attr("src","<%=basePath%>images/websuitMini/arrow1.png");
			$(".l_nav").attr("class","l_nav fl");
			$(this).contents("img").attr("src","<%=basePath%>images/websuitMini/arrow.png");
			$(this).attr("class","l_nav nav_sel fl");
			var str = "<%=basePath%>images/websuitMini/5L5C"+arr[parseInt($(this).attr("index"))-1]+" (";
			$(".r_nav img").each(function(i, element) {
                 $(this).attr("src",str+ parseInt(i+1)+").png");
            });
		});
	});
});
</script>
  </head>

 <body style="margin-top: 63px;">
<div id="topbar" style="display: block;">
   <div id="topbar_title" class="topbar_title">5L5C移动办公系统</div>
   <div id="topbar_back" ontouchstart="" style="display: block;"><img src="<%=basePath%>images/websuitMini/button_back.png" onclick="history.back()"></div>
   <div id="topbar_menu" class="topbar_menu" ontouchstart=""></div>
</div>
<div class="contents">
   <div class="c_left fl">
      <div class="l_nav fl">
          <span>股东会</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>董事会</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>监事会</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>职代会</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>常委会</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>董事长室</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>总经理室</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>人事处</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>办公室</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>财务处</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>教务生产处</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav fl">
          <span>营销处</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow1.png" />
      </div>
      <div class="clear">
      </div>
      <div class="l_nav nav_sel fl">
          <span>创收平台</span>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/arrow.png" />
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
