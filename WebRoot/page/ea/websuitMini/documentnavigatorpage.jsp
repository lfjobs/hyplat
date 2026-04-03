<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公文流转左侧导航页</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/websuitMini/documentNavControl.js" type="text/javascript"></script>
		<style type="text/css">
body{

font-style:normal;
font-size:12px;
font-family:微软雅黑;
background-color:#B2EBFF;
overflow-y:scroll;
}
.viewnav{
width:80;
height:40px;
border-radius:10px;
background-color:#72CC55;
font-weight:bold;
font-size:16px;
color:#FFFFFF;
line-height:40px;
}
.buttonstyle{

background-color:#2980B9;
width:100%;
height:30px;
color:#FFFFFF;
border-radius:12px;
margin:auto;
cursor:pointer;
line-height:30px;
}
span{cursor:pointer;}
.buttonstylechild{
 line-height:25px;
 color:#FFFFFF;
 cursor: pointer;
}
p{
margin-top:20px;
}
.org_box{width:auto;  line-height:20px; margin-top:20px; padding-left:0em; background:#F3961C; position:relative; border-radius:10px;display:none; }
.org_box_cor{ width:0; height:0; font-size:0;border-style:solid;overflow:hidden; position:absolute; }
.cor3{border-width:20px;border-color:transparent transparent #f3961c transparent;left:18px; top:-40px;}
</style>
<script type="text/javascript">
var basePath = '<%=basePath%>';
function closeAll(){
$("#mainbody").find(".org_box").each(function(){
			$(this).hide();
			});
}
	function showcontext(val){
	if(val=="nigao"){
			$("#mainbody").find(".org_box").each(function(){
			$(this).hide();
			$("#"+ val +"div").show(200);
		});
	}if(val=="shenpi"){
	
		$("#mainbody").find(".org_box").each(function(){
			$(this).hide();
			$("#"+ val +"div").show(200);
		});
	}
	if(val=="gaizhang"){
	
		$("#mainbody").find(".org_box").each(function(){
			$(this).hide();
			$("#"+ val +"div").show(200);
		});
	}
	if(val=="guanli"){
	
		$("#mainbody").find(".org_box").each(function(){
			$(this).hide();
			$("#"+ val +"div").show(200);
		});
	}
	if(val=="yuedu"){
	
		$("#mainbody").find(".org_box").each(function(){
			$(this).hide();
			$("#"+ val +"div").show(200);
		});
	}
	if(val=="guidang"){
	
		$("#mainbody").find(".org_box").each(function(){
			$(this).hide();
			$("#"+ val +"div").show(200);
		});
	}
		
	}
</script>
</head>

<body style="margin-top: 23px;">
<div class="viewnav" onclick="closeAll()">公文流转</div>
<div id="mainbody">
<p>
  <div class="buttonstyle" id="nigao"  onclick = "showcontext(id)"  align="center">拟    稿</div>
<div class="org_box nigao"  style = "height:75px" id="nigaodiv" align="left"> <span class="org_box_cor cor3" ></span>
<div class="buttonstylechild" id="drafter" align="center">起草上传</div>
<div class="buttonstylechild" align="center">收件箱</div>
<div class="buttonstylechild" align="center">已发送</div>
</div>
</p>
<p>
  <div class="buttonstyle"  id="shenpi" onclick = "showcontext(id)" align="center">审    批</div>
  <div class="org_box shenpi" style = "height:50px" id="shenpidiv"  align="left"> <span class="org_box_cor cor3" ></span>
  <div class="buttonstylechild" align="center">未审批公文</div>
  <div class="buttonstylechild" align="center">已审批公文</div>
  </div>
</p>
<p>
  <div class="buttonstyle" id="gaizhang" onclick = "showcontext(id)" align="center">盖    章</div>
  <div class="org_box shenpi" style = "height:50px" id="gaizhangdiv"  align="left"> <span class="org_box_cor cor3" ></span>
  <div class="buttonstylechild" align="center">未盖章公文</div>
  <div class="buttonstylechild" align="center">已盖章公文</div>
  </div>
</p>
<p>
  <div class="buttonstyle" id="guanli" onclick = "showcontext(id)" align="center">发文管理</div>
  <div class="org_box shenpi" style = "height:50px" id="guanlidiv" align="left" > <span class="org_box_cor cor3" ></span>
  <div class="buttonstylechild" align="center">未发公文</div>
  <div class="buttonstylechild" align="center">已发公文</div>
  </div>
</p>
<p>
  <div class="buttonstyle" id="yuedu" onclick = "showcontext(id)" align="center">阅读管理</div>
  <div class="org_box shenpi" style = "height:50px" id="yuedudiv" align="left"> <span class="org_box_cor cor3" ></span>
  <div class="buttonstylechild" align="center">未阅读</div>
  <div class="buttonstylechild" align="center">已阅读</div>
  </div>
</p>
<p>
  <div class="buttonstyle"  id="guidang"  onclick = "showcontext(id)" align="center">公文归档</div>
  <div class="org_box shenpi" style = "height:30px" id="guidangdiv" align="left"> <span class="org_box_cor cor3" ></span>
  <div class="buttonstylechild" align="center">已归档</div>
  </div>
</p>
</div>
</body>
</html>
