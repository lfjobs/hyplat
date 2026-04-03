<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>注册访客页面</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style>
.contain{ width:100%px; height:1000px; background:#f0f0f6; margin:0 auto;}
.nav{ width:100%px; height:90px; background:#2bb0c3;}
.nav h3{font-family:"微软雅黑"; font-size:30px; color:#FFF; line-height:90px; text-align:center; background:url(images/j.png) no-repeat 20px;}
.nav h3 span{ display:inline-block; float:right; padding-right:10px;}
.nav h3 span a{ color:#fff;}
a{ text-decoration:none; font-family:"微软雅黑"; font-size:20px; color:#FFF;}
.tup{ text-align:center; margin-top:35px;}
.shur{ width:90%; height:204px; background:#FFF; border:1px solid #999; margin:0 auto; margin-top:70px;}
.yongh{font-family:" 微软雅黑"; font-size:30px; color:#000; line-height:100px; font-weight:600;}

.shur tr td{ padding-left:5%; border:1px solid #999;}


.dengl{ text-align:center; margin-top:30px;}
.zid{ width:50%; float:left;}
label{ font-family:"微软雅黑"; font-size:20px;}
.wangj{ width:50%; float:left;}
.wangj a{font-family:"微软雅黑"; font-size:20px; color:#000; width:100%; margin-left:80px;}
.ann{ text-align:center; margin-top:50px; width:100%;}
 .inputtxt
        {
            border: #c2cbd8 0px solid;
            width: 70%;
            height: 28px;
            margin-top: 8px;
            line-height: 20px;
            margin-left: 3px;
            color: White;
            font-size: 16px;
            font-family: 微软雅黑;
            text-align:left;
        }
</style>
<body>
  <div class="contain">
    <div class="nav">
    <h3>个人注册 <span><a href="<%=basePath%>page\mobile\activity\activity_publishLogin.jsp">登录</a></span></h3>
    </div>
    <div class="tup"><img src="<%=basePath%>images/ea/login/logo.png" /></div>
      <table class="shur" style="border-collapse:collapse; border:1px solid #999;">
      <form name="vLoginForm" id="vLoginForm" method="post" action=""/>
      <input type="submit" name="submit" style="display:none"/>
        <tr class="yongh">
          <td style="border-right:0">账&nbsp;&nbsp;户：
          <input name="customer.account" type="text" id="staffName" class="inputtxt" value="不能为空" style="color:gray;">
       <span style="color:red">*</span>
          </td>
          <td style="border-left:0">&nbsp;<img src="<%=basePath%>images/ea/login/g.png" /></td>
        </tr>
        <tr class="yongh">
          <td style="border-right:0">密&nbsp;&nbsp;码：
          <input name="customer.password" type="text" id="staffName" class="inputtxt" value="不能为空" style="color:gray;">
       <span style="color:red">*</span>
          </td>
          <td style="border-left:0">&nbsp;</td>
        </tr>
           <tr class="yongh">
          <td style="border-right:0">姓&nbsp;&nbsp;名：
          <input name="staff.staffName" type="text" id="staffName" class="inputtxt" value="不能为空" style="color:gray;">
          <span style="color:red">*</span>
          </td>
          <td style="border-left:0">&nbsp;</td>
        </tr>
        <tr class="yongh">
          <td style="border-right:0">电&nbsp;&nbsp;话：                                                                                                                                                                  
          <input name="staff.reference" type="text" id="reference" class="inputtxt" value="不能为空" style="color:gray;">
     <span style="color:red">*</span>
          </td>
          <td style="border-left:0">&nbsp;</td>
        </tr>
        
     </table>
      <div class="ann">
      <a href="javascript:save();" style="margin-left:20px;" class="vlogin">
      <img src="<%=basePath%>images/ea/login/z.png" /></div></a>
       <div class="clear"></div>			
      </form>
  </div>
  
  <script type="text/javascript">
     var basePath = "<%=basePath%>";
    $(document).ready(function () {
		 $(".inputtxt").focus(function () {
		 	if($(this).val()=="不能为空"){
		 		$(this).val("");
		 	}
		 	$(this).css("color", "#000");
		 }).blur(function(){
		 	if($(this).val()==""){
		 		$(this).val("不能为空");
		 		$(this).css("color", "gray");
		 	}
		 });
	});
    	function res(){
         	document.location.href = basePath +"/page/mobile/activity/activity_publishLogin.jsp";
    	}
    	function save(){
    		var str = "" 
    		$(".inputtxt").each(function(){
    			str += $(this).val();
    		});
    		if(str.indexOf("不能为空") > -1){
    			return;
			}
    		 $("#vLoginForm").attr("action", basePath +"ea/vlogin/ea_toSave.jspa");
    		 document.vLoginForm.submit.click();
    	}
    
    </script>
   
</body>

</html>

