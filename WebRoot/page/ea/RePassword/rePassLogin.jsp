<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" /> 
<meta http-equiv="Cache-Control" content="no-cache" /> 
<title>密码找回</title>
<link href="<%=basePath%>css/ea/register.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
 

<style type="text/css">
<!--
.roundedCorners{
width:935px;
height:540px;
padding: 10px;
background-color: #FFF;
border:1px solid #83A2C6;
margin:0px auto;
margin-top:5px;
 
/* Do rounding (native in Safari, Firefox and Chrome) */
-webkit-border-radius: 6px;
-moz-border-radius: 6px;
 
}

.contenspan1{
 width:260px;
 float:left; 
 text-align:right;
 font-size:12px; 
 color:#6B6B6B;
 line-height:20px;
 }
 
 .conn{ 
 padding:10px 0 0 350px; 
 display:block; 
 float:left; 
 }
</style>


<script type="text/javascript">
  var basePath="<%=basePath%>";
  var long = 1;
  var message = '${message}' ;
  
   //错误提示
  	if(message!=""){
  		alert(message);
  	}
  	
	document.onkeydown = function(evt){//捕捉回车   
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13&&long == 1) { //判断是否是回车事件。
        next();
    }
}
  function next()
  {
      pass = '1';
      $("form :input").trigger("blur");
      $(".validate").trigger("blur");
          if($("form .error").length)
          { 
            return;
          }     
     $('#Loginform').attr('action', "<%=basePath%>ea/repassword/ea_retrieve.jspa?");
     document.Loginform.submit.click();
  }
  
 function gxtp(){
		$("img[name='validateImage']").trigger("click"); //获取图片"看不清，换一张"点击事件
}
  
  function repass(){
		window.location.href="<%=basePath%>page/ea/index.jsp";
  }
  
  $(document).ready(function(){
  		$("input#companyIdentifier").blur(function(){
    		var companyIdentifier = $("#Loginform").find("input#companyIdentifier").attr("value");
			var url = basePath
						+ "ea/repassword/ajax_repass_ea_isCompanyIdentifier.jspa?companyIdentifier="
						+ companyIdentifier+ "&date=" + new Date();
			var succtatus = 0;
			$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var result = member.log;
						var companyID = member.companyID;
						var count = member.count;
						if(companyIdentifier==""){
							$("input#companyIdentifier").after("<span class=\"error\"><a  class=\"tex\">不能为空</a></span>");
							succtatus = 1;
						}
						if (companyIdentifier!="" && result ==1) {
							$("input#companyIdentifier").after("<span class=\"error\"><a  class=\"tex\">该组织机构不存在</a></span>");
							succtatus = 1;
						}
						if (result ==2) {
							$("input#companyIdentifier").after("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
							$("input#companyID").val(companyID);
							succtatus = 1;
						}
					},
					error : function cbf(data) {
						alert("获取数据失败!");
						succtatus = 1;
					}
			});
    	})
    	
    	$("input#accountEmail").blur(function(){
    		var accountEmail = $("#Loginform").find("input#accountEmail").attr("value");
    		var companyID = $("#Loginform").find("input#companyID").attr("value");
			var url = basePath
						+ "ea/repassword/ajax_repass_ea_isAccountEmail.jspa?accountEmail="
						+ accountEmail +"&companyID="+companyID + "&date=" + new Date();
			var succtatus = 0;
			$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var result = member.log;
						if(accountEmail==""){
							$("input#accountEmail").after("<span class=\"error\"><a  class=\"tex\">不能为空</a></span>");
							succtatus = 1;
						}
						if (accountEmail!="" && result ==1) {
							$("input#accountEmail").after("<span class=\"error\"><a  class=\"tex\">该用户名不存在</a></span>");
							succtatus = 1;
						}
						if (result ==2) {
							$("input#accountEmail").after("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
							succtatus = 1;
						}
					},
					error : function cbf(data) {
						alert("获取数据失败!");
						succtatus = 1;
					}
			});
    	})
  })
  
</script>
</head>
<body>
<div class="main">
	<div class="conter">
        <div class="logo_zc"></div>
        <div class="logo_right"></div>
        <div class="logo_rightwz">密码</div>
    </div>
    <div class="roundedCorners">
    	<div class="titlenav1"><span class="td21">密码找回</span>
        <span class="td22">(以下<span class="txt05"> * </span>为必填项)</span></div>
        	 <form name="Loginform" id="Loginform"  method="post" >
        	 <input type="text" name="companyID" id="companyID" style="display:none"/> 
        <div class="conten"><input type="submit" name="submit" style="display:none"/>
          <ul>
              <li>
              	<span class="contenspan1"><span class="txt05"> * </span>组织机构名：</span>
                <input type="text" name="companyIdentifier"  class="yname" id="companyIdentifier"/>   
              </li>
              <li>
              	<span class="contenspan1"><span class="txt05"> * </span>用户名：</span>
              	<input type="text" name="accountEmail"  class="yname" id="accountEmail"/>
              </li>
              <li>
               	  <span class="contenspan1">图片：</span>
                   <img border="0" name="validateImage" onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abc='+Math.random()" src="<%=basePath%>page/ea/security_code.jsp"/>
                   <a href="#" onclick="javascript:gxtp();" style="height: 22px">看不清,换一张</a>          
                </li>
                <li>
               	  <span class="contenspan1">输入验证码:</span>
                  <input type="text"  class="yname validate" name="validateCode"/>
                  <s:token/>
                </li>
           </ul>
           </div>
		    </form>
		        <div class="conn">
		          <ul>
		            <li>
		              <input  type="button" onclick="next()" class="button" value="下一步" />
		              <input  type="button"  onclick="repass()" class="button01" value="返回登录" />
		            </li>
		          </ul>
		   </div>
  </div>
    </div>
     <div class="footer">北京天太世统科技有限公司 版权所有   服务电话：010-64167113</div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>



