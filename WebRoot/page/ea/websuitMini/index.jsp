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
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/websuitMini/websuit.css" />
  	<!-- Attach our CSS -->
	  	<link rel="stylesheet" href="<%=basePath%>css/reveal.css">	
	  	
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jquery.reveal.js"></script>
		
		<style type="text/css">
			body{ background-color:#b2ebff;}
.contents_nav_bg{width:350px; margin:auto; background-image:url(<%=basePath%>images/websuitMini/index_bg.jpg); background-size:100%; }
.contents_nav{ width:310px; margin:auto;}
.nav{ height:140px; width:100%;}
.nav a,.nav img{ width:70px; height:70px; display:block; float:left;}
.nav .a_left{ margin-left:60px; margin-top:5px;}
.nav .a_right{ margin-left:60px; margin-top:5px;}
		</style>
		<script type="text/javascript">
$(document).ready(function(){ 
  var arrayObj = new Array();
  arrayObj[0]= "<%=basePath%>images/websuitMini/5l5c_01.png";
  arrayObj[1]= "<%=basePath%>images/websuitMini/5l5c_02.png";
  arrayObj[2]= "<%=basePath%>images/websuitMini/5l5c_03.png";
  arrayObj[3]= "<%=basePath%>images/websuitMini/5l5c_04.png";
  arrayObj[4]= "<%=basePath%>images/websuitMini/5l5c_05.png";
  arrayObj[5]= "<%=basePath%>images/websuitMini/5l5c_06.png";
  arrayObj[6]= "<%=basePath%>images/websuitMini/5L5C_07.png";
  var arrayObj2 = new Array();
  arrayObj2[0]= "<%=basePath%>images/websuitMini/5l5c_001.png";
  arrayObj2[1]= "<%=basePath%>images/websuitMini/5l5c_002.png";
  arrayObj2[2]= "<%=basePath%>images/websuitMini/5l5c_003.png";
  arrayObj2[3]= "<%=basePath%>images/websuitMini/5l5c_004.png";
  arrayObj2[4]= "<%=basePath%>images/websuitMini/5l5c_005.png";
  arrayObj2[5]= "<%=basePath%>images/websuitMini/5l5c_006.png";
  arrayObj2[6]= "<%=basePath%>images/websuitMini/5L5C_007.png";
  
    var index = 0;
    $(".nav img").each(function(index, element) {
		$(this).attr("index",index);
		index++;
		$(this).hover(function(){
		   $(this).attr("src",arrayObj2[$(this).attr("index")]);
		},function(){
		   $(this).attr("src",arrayObj[$(this).attr("index")]);
			});
    });
    $("#log").click(function(){
		window.location.href= "<%=basePath%>ea/logbook/ea_getListLogBook.jspa?serachType=websuit";
		});
		$("#phone").click(function(){
		window.location.href= "<%=basePath%>ea/soincumbent/ea_getStaffListForIncumbent.jspa?aa=aa&serachType=websuit";
		});
		$("#askforleave").click(function(){
		window.location.href= "<%=basePath%>page/ea/websuitMini/Ask_for_leave.jsp";
		});
		$("#workoverTime").click(function(){
		window.location.href= "<%=basePath%>page/ea/websuitMini/work_overTime.jsp";
		});
		$("#gongwen").click(function(){
		window.location.href= "<%=basePath%>page/ea/websuitMini/documentmain1.jsp";
		});
		
});
function changeColor(){
var colors = ["#1B8DFD", "#1B8DFD", "#1B8DFD", "#1B8DFD", "#1B8DFD", "#1B8DFD"];
var index = 0;
 var color = colors[index++];
    // 更改文档的背景色


   document.body.style.background = color
    if (index == colors.length) {
        index = 0;
    }
}
</script>
  </head>

 <body style="margin-top: 63px;">
	
		
		<div id="myModal" class="reveal-modal"></br>
			<h1>&nbsp;&nbsp;用户设置</h1></br>
			<p>颜色: <div style="width:20px;height:20px;background-color:#1B8DFD" onclick="changeColor()"> </div></p>
			<a class="close-reveal-modal"><img src="<%=basePath%>images/websuitMini/close.png"/></a>
		</div>
		
		<div id="topbar" style="display: block;">
   <div id="topbar_title" class="topbar_title">5L5C办公系统</div>
   <div id="topbar_back" ontouchstart="" style="display: block;"><a data-reveal-id="myModal" ><img src="<%=basePath%>images/websuitMini/Set.png"></a>	</div>
   <div id="topbar_menu" class="topbar_menu" ontouchstart=""><img src="<%=basePath%>images/websuitMini/Up.png"/></div>
</div>
<div class="contents_nav_bg">
 <div class="contents_nav">
     <div class="nav fl">
       <a class="a_left"  id="log"><img alt="" title="" src="<%=basePath%>images/websuitMini/5l5c_01.png"/></a>
       <a class="a_right" href="javascript:void(0);"><img alt="" title="" src="<%=basePath%>images/websuitMini/5l5c_02.png"/></a>
     </div>
     <div class="clear"></div>
     <div class="nav fl">
       <a class="a_left" id = "phone" href="javascript:void(0);"><img alt="" title="" src="<%=basePath%>images/websuitMini/5l5c_03.png"/></a>
       <a class="a_right" href="javascript:void(0);"><img alt="" title="" src="<%=basePath%>images/websuitMini/5l5c_04.png"/></a>
     </div>
     <div class="clear"></div>
     <div class="nav fl">
       <a class="a_left" id="askforleave"  href="javascript:void(0);"><img alt="" title="" src="<%=basePath%>images/websuitMini/5l5c_05.png"/></a>
       <a class="a_right" id="workoverTime" href="javascript:void(0);"><img alt="" title="" src="<%=basePath%>images/websuitMini/5l5c_06.png"/></a>
     </div>
     <div class="clear"></div>
     <div class="nav fl">
       <a class="a_left" id="gongwen"  href="javascript:void(0);"><img alt="" title="" src="<%=basePath%>images/websuitMini/5L5C_07.png"/></a>
     </div>
     <div class="clear"></div>
 </div>
</div>
</body>
</html>
