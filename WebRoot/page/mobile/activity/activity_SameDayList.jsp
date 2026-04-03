<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<title><s:if test="inforType=='00'">活动列表</s:if>
   <s:if test="inforType=='01'">公共信息</s:if>
</title>
 <style>
html{overflow-x:hidden;}
body{background-color:#fff;font-family:Helvetica, STHeiti STXihei, Microsoft JhengHei, Microsoft YaHei, Tohoma, Arial;font-size:10px;line-height:20px;color:#000000;margin:15px 12px;min-width:296px;-webkit-text-size-adjust:none;}
img{vertical-align:top;border:0;}
div,h1{word-break:break-all;}
div,h1,p,ul,li,label,textarea,input,button,form{margin:0;padding:0;-webkit-tap-highlight-color:rgba(0, 0, 0, 0);}
input,textarea{border:0;}
ul{list-style:none;}
.left{float:left;}.right{float:right;}
.clear{clear:both;height:0;font-size:0;line-height:0;overflow:hidden;}
body{
	background-color: #eeeeee;
	font-family: Helvetica, STHeiti STXihei, Microsoft JhengHei, Microsoft YaHei, Tohoma, Arial;
	font-size: 10px;
	line-height: 20px;
	color: #000000;
	margin: 15px 12px;
	min-width: 296px;
	-webkit-text-size-adjust: none;
}
#topbar{
	display: none;
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 46px;
	background-color: #42ABE1;
	border-bottom: 2px solid #2da9bf;
	z-index: 500;
}
.topbar_title{
	font-size: 20px;
color: #ffffff;

line-height: 20px;
text-align: center;
text-overflow: ellipsis;
-o-text-overflow: ellipsis;
white-space: nowrap;
overflow: hidden;
margin: 0 auto;
width: 200px;
padding: 13px 0;
	}
	#topbar_back {
display: none;
position: absolute;
left: 0;
top: 0;
width: 55px;
height: 33px;
overflow: hidden;
padding-top: 13px;
background-color: #3fbbd1;
text-align: center;
}
#topbar_menu {
position: absolute;
right: 0;
top: 0;
width: 65px;
height: 35px;
overflow: hidden;
padding-top: 11px;
background-color: #42ABE1;
text-align: center;
font-size: 15px;
color: #ffffff;
line-height: 25px;
}
#topbar_back img {
width: 14px;
height: 21px;
}
.content {
border: 0px solid #cccccc;
background-color: #ffffff;
margin-bottom: 15px;
}

.content .input {
font-size: 15px;
line-height: 20px;
padding: 10px 0;
margin: -10px 0;
}
.content .input_s{ width:78%; margin-top:10px; height:40px; border:1px solid #ccc; margin-left:10px; -moz-border-radius: 7px; /* Firefox */
  -webkit-border-radius: 7px; /* Safari 和 Chrome */
  border-radius: 7px; /* Opera 10.5+, 以及使用了IE-CSS3的IE浏览器 */}
.content input, .form_textarea textarea, .form_button button {
width: 100%;
}
.content textarea, .form_button button {
width: 100%;font-size: 15px;resize:none;
}
.content{ width:100%; height:670px; background:#f0f0f0;;}
.content .search{ width:98%; height:52px; background-color:#fff; margin:auto;}
.content .search .search_img{ width:40px; height:38px; margin-top:10px; margin-left:5px;}
.con_nav{ width:98%; height:600px; margin:auto; font-size:16px; font-family : 微软雅黑,宋体;color: black;}
.con_nav .con_nav_singular{ width:96%; height:40px; margin:auto; margin-top:10px; background-color:#fff; border:1px solid #ccc;}
.con_nav .con_nav_dual{ width:96%; height:40px; margin:auto; margin-top:10px; border:0px solid #fff; background-color:#fff;}
.release_people{ width:20%; height:40px; line-height:40px; text-align:center; display:block;}
.hd_name{ width:79%; height:40px; line-height:40px; text-align:center; display:block;overflow: hidden; }
.bor_l{ border-right:1px solid #ccc;}

a:link {
text-decoration: none;
color:#333
}
</style>
<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css"
			charset="utf-8" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	var suc=<%=request.getParameter("enrollsuc")%>;
	if(suc==1){
		alert("报名成功！");
		}
	
});
	function getActivity(){
		$("#submit").submit();
		}
	
</script>

</head>
<body style="margin-top:50px;">
<div id="topbar" style="display: block;" class="left">
   <div id="topbar_title" class="topbar_title">
   <s:if test="inforType=='00'">活动</s:if>
   <s:if test="inforType=='01'">公共信息</s:if>
  </div> 
   <div id="topbar_menu" class="topbar_menu" ontouchstart=""></div>
</div>
<div class="clear"></div>
<div class="content left">
    <div class="search">
    	<form action="<%=basePath %>ea/activity/ea_getSameDayList.jspa?search=byname&inforType=${inforType}&weixinCompanyId=${weixinCompanyId}" id="submit" method="post">
        	<input type="text" id="staffName" name="staffName" class="input input_s left"  placeholder="输入发布人姓名" maxlength="70">
        	<img class="left search_img" src="<%=basePath %>images/activity/search.png" onclick="getActivity()"/>
        </form>
    </div>
    <div class="clear">
    </div>
    <div class="con_nav">
        <div class="clear"></div>
        <div class="con_nav_singular">
           <div class="release_people left bor_l">发布人</div>
           <div class="hd_name left">
            <s:if test="inforType=='00'">活动名称</s:if>
   		    <s:if test="inforType=='01'">公共信息</s:if>
 
         </div>
        </div>
   
         <c:forEach var="objs" items="${addressBean}">
    
          <div class="clear"></div>
         <div class="con_nav_dual">
         	<a href="<%=basePath%>/ea/activity/ea_activityDetails.jspa?activityId=${objs[0]}&weixinCompanyId=${weixinCompanyId}">
           	<div class="release_people left">▷ ${objs[3]}</div></a>
           	<a href="<%=basePath%>/ea/activity/ea_activityDetails.jspa?activityId=${objs[0]}&weixinCompanyId=${weixinCompanyId}">
          	<div class="hd_name left" style="text-align: left">${objs[1]}</div></a>
          	
       	 </div>
       	 
 
		</c:forEach>
    </div>
</div>
  
</body>
</html>