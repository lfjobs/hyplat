<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>客户地址管理</title>  
     <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<style type="text/css">
		.hid{ position: absolute; display:none; top:0px; left:0px; z-index:200;height: 45px; line-height:40px; width: 100%; border: 1px solid #ccc; outline: none; resize: none; border-radius: 4px;-moz-border-radius: 4px 4px 4px 4px;-webkit-border-radius: 4px;}
		.add1{display:none;}
		.addressadd{display:none;}
	</style>
  </head>  
  <body>
  <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/cusLabel.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
<div class="con">
    <div class="addressManagement">
        <h1><d class="fl">${staff.staffName }</d><d class="fl">${staff.reference}</d></h1>
        <% int number=1; %>
        <c:forEach var='arr' items="${listAddress}">
	        <div class="clear"></div>
	        <div class="address fl">
	            <div class="addressCon fl" id="${arr.addressID }">
	               <c:if test="${arr.isDefault=='是' }"><span class="dis">默认</span></c:if> 
	               <d>${arr.addressDetailed }</d>
	               <input type="text" class="hid" value="${arr.addressDetailed }" style=""/>
	            </div>
	            <div class="addressOper fl">
	                <a class="up" href="javascript:void(0);" >修改</a>
	                <c:if test="${arr.isDefault!='是'}"><a class="def" href="<%=basePath%>/ea/wfjcustomer/ea_defaultAddressEdit.jspa?addressId=${arr.addressID }">默认</a></c:if>
	            </div>
	        </div>
	        <% number++; %>
        </c:forEach>
       
    </div>
    <div class="txtAddress">
     <div class="add1" id="add1">
  	   <form action="<%=basePath%>/ea/wfjcustomer/ea_staffAddressEdit.jspa" method="post" id="addresssub" name="addresssub" >
  	   	 <input type="submit" name="submit" style="display:none"/>
         <textarea role="5" class="txt" id="txt" name="staffAddress.addressDetailed"></textarea>
         <input type="hidden" name="staffAddress.addressID"/>
         <div class="sub" id="reg">确定</div>
       </form>
       </div>
       <div class="sub addressadd" id="editaddress">确定</div>
       <div class="sub" id="addbut">增加</div>
    </div>
</div>
<form action="<%=basePath%>/ea/wfjcustomer/ea_staffAddressEdit.jspa" method="post" name="editaddr" id="editaddr">
	<input type="submit" name="editSubmit" style="display:none"/>
	<input type="hidden" name="staffAddress.addressDetailed" id="addressDetaileds"/>
	<input type="hidden" name="staffAddress.addressID" id="addressIds"/>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
<script type="text/javascript">
var token=0;
$(document).ready(function() {
	$("#txt").val("");
});
$('#indexTop').load(function () {
	var doc=document.getElementById("indexTop").contentWindow.document;
	doc.getElementById("topbar_title").innerHTML="地址管理";
	doc.getElementById("return").onclick=function(){
		window.history.go(-1);
	}
});
var upId="0";
var value="0";
//修改地址
$(".up").click(function () {
	var oTxt=$(this).parent().parent().find(".addressCon input[type=text]");
	if(this.innerHTML=="确定"){
		$(this).parent().parent().find(".addressCon d").text(oTxt.val());
		
		oTxt.css("display","none");
		this.innerHTML="修改";
		var temp=$(this).parent().parent().find(".addressCon").attr("id");		
		var reg=new RegExp(temp,'i');
		//if(!reg.test(upId))
			upId+=","+temp;
			value+=","+oTxt.val();
			$("#addressDetaileds").val(value);
			$("#addressIds").val(upId);
		console.log(upId);
		console.log(value);
	}else{
		$("#editaddress").attr("style","display:block");
		$("#addbut").attr("style","display:none");
		$("#add1").attr("style","display:none");
		oTxt.css("display","block");
		this.innerHTML="确定";
	}
});
//修改提交
$("#editaddress").click(function () {
	 $("#editaddr").attr("target", "hidden");
	 document.editaddr.editSubmit.click();
	 token = 13;
});
//添加页面显示
$("#addbut").click(function () {
	$("#add1").attr("style","display:block");
	$("#addbut").attr("style","display:none");
});
//提交添加信息
$("#reg").click(function () {
	   var txt=$("#txt").val().trim();
	   if(txt==""){
		   alert("请添加地址！");
		   return;
	   }
	   $("#addresssub").attr("target", "hidden");
	   document.addresssub.submit.click();
	   token = 13;
 	});
function re_load() {
	if (token)
		document.location.href = "<%=basePath%>ea/wfjcustomer/ea_getAddress.jspa";
}
</script>
</html>
