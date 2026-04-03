<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人加盟商城业主会员-注册</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/WFJClient/style2.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
</head>
<body>
	<div class="wfj10_001">
		<iframe id="indexTop" align="center" width="100%" height="46px"
			src="<%=basePath%>page/WFJClient/Template/Top/Label.jsp"
			frameborder="no" border="0" marginwidth="0" marginheight="0"
			scrolling="no"></iframe>
		
		
		<div style="width: 100%;">
			<p class="p1">
				<span>订单信息</span>
			</p>
		</div>
	
		<div class="im1">
			<div>
				<img 
					src="<%=basePath%>images/WFJClient/PersonalJoining/product.png" />
			</div>
			<div class="info1">
				<p>
					<span>${goodname}</span><br /> <span>￥${morre}</span>
				</p>
			</div>
		</div>
		<form id="formsutm" name="formsutm" method="post" >
		<div class="border">
			
			<input type="submit" style="display: none;"id="submit"> 
		 	<input  name="goodname" value="${goodname} " style="display: none;"/>
		    <input name="morre" value="${morre}" style="display: none;"/>
		    <input name="fenlei" value="${fenlei}"style="display: none;"/>
		 	
						<table class="ta1">
					<tr>
						<td>上级代理:</td>
						<td><select id="dl" name="activity">
							<option   value="0">选择上级代理</option>
						</select></td>
					</tr>
					
				
					<tr>
						<td>联系电话</td>
						<td><input type="text" name="staff.reference"/></td>
					</tr>
						<tr>
						<td>个人邮箱:</td>
						<td><input type="text" value="" id="comppahe1"  name="staff.referenceOrganization"/></td>
					</tr>
						<tr>
						<td>详细地址:</td>
						<td><input type="text" value="" name="staff.staffAddress" /></td>
					</tr>
						<tr>
						<td>开户行名:</td>
						<td><input type="text" value="" /></td>
					</tr>
					
						<tr>
						<td>开户行账号:</td>
						<td><input type="text" value=""name="staff.bankNum"/></td>
					</tr>
					
					<tr>
						<td>&nbsp</td>
						<td><input class="bu1" type="button" value="提交订单" onclick="ti()"/></td>
					</tr>
				</table>
		
		</div>
			</form>
	</div>
	<script type="text/javascript">
		$('#indexTop')
				.load(
						function() {
							var doc = document.getElementById("indexTop").contentWindow.document;
							doc.getElementById("topbar_title").innerHTML = "个人加盟商城业主会员（购买）";
							doc.getElementById("return").onclick = function() {
								window.history.go(0);
							}
						});
	</script>
	<script type="text/javascript">
	var  basePath='<%=basePath%>';
	var  where='${fenlei}';
	
	$(function()
	{
		  var   pahe=basePath+"/ea/wfjshop/sajax_ea_getgrlist.jspa?sort="+where;
		         $.ajax({
	             type: "GET",
	             url: pahe,
	             dataType: "json",
	             success: function(data){  
	                     		var member = eval("(" + data + ")");
	                     		
	                        for(var i=0;i<=member.list.length;i++)
	                       {
	                      	 $("#dl").append("<option value='"+member.list[i].ID+"'>"+member.list[i].name+"</option>");
	                       }
	                      }
	         });
	
	})
	function ti()
	{
	
		  if($("#dl").val()==0)
         {
        	 alert("请选择上级");
           return;
         }
	
	   if(!CheckMail($("#comppahe1").val()))
         {
         	alert("您的电子邮件格式不正确");
          return;
         } 
         if(!isnull())
         {
         
            alert("信息不完整");
              return;
         }
          
      	  $("#formsutm").attr("action",basePath + "/ea/wfjshop/ea_grgm.jspa ");
			document.formsutm.submit.click();
	   
	}
	 //电子邮箱验证
  function CheckMail(mail) {
  
		 var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 if (filter.test(mail)) 
		 {		
			 return true;
		 }
		 else {
		 
		     return false;
				}
}
  function  isnull(){
    
     var  s=true;
      var allInputs = document.getElementsByTagName('input');
      for (var i = 0; i < allInputs.length; i++) {
		if (allInputs[i].type === 'text') {
			 if(allInputs[i].value=='')
			 {
    		 		s=false;
					break
		 }		    
		}
	}
	  return s;
  }
 

	
	</script>
</body>
</html>
