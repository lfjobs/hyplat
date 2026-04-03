<%@ page language="java" pageEncoding="UTF-8"%>
 <%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中国农业管理质量跟踪[5l5c]平台</title>
		<link href="<%=basePath%>css/ea/login.css" rel="stylesheet"	type="text/css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript">
var basePath="<%=basePath%>";
var long = 1;
document.onkeydown = function(evt){//捕捉回车   
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13&&long == 1) { //判断是否是回车事件。
        login();
    }
}
function gxtp(){
		$("img[name='validateImage']").trigger("click");
}
function register(){
    window.location.href = "<%=basePath%>page/ea/register1.jsp?";
}

function login(){
    $("form :input:.validate").trigger("blur");
    if ($("form .error").length) {
        return false;
    }
     long=0;
    $('#Login').attr('action', "<%=basePath%>ea/login1.jspa?");
    document.Login.submit.click();
}
</script>
	</head>
	<body>
	<form name="Login" method="post" id="Login" action=""><input type="submit" name="submit" style="display:none"/><input type="text" name="companyType" value="10" style="display:none"/>
	    <div class="main">
		<div class="top">
            <div class="top01">
            	<div class="conter">
            	  <table width="89%" height="252" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td height="52" colspan="4" align="center" class="td01">中国农业管理质量跟踪[5l5c]平台</td>
                    </tr>
                    <tr>
                      <td width="23%" height="34" align="right">组织机构名：</td>
                      <td colspan="3"><label>
                        <input type="text" name="companyIdentifier"	class="yname" />
                      </label></td>
                    </tr>
                    <tr>
                      <td height="35" align="right">用户名：</td>
                      <td colspan="3"><input type="text" name="account.accountEmail" class="yname" /></td>
                    </tr>
                    <tr>
                      <td height="32" align="right">密码：</td>
                      <td colspan="3"><input type="password" name="account.accountPassword"	class="yname" size="22" /></td>
                    </tr>
                    <tr>
                      <td height="32" align="right">图片：</td>
                      <td colspan="3"><img border="0" name="validateImage"
											onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abcd='+Math.random()"
											src="<%=basePath%>page/ea/security_code.jsp" />&nbsp;&nbsp;<a href="#" onclick="javascript:gxtp();" style="height: 16px">看不清，换一张</a></td>
                    </tr>
                    <tr>
                      <td height="30" align="right">输入验证码：</td>
                      <td colspan="3"><input type="text" name="validateCode" class="yname validate" /></td>
                    </tr>
                  </table>
            	  <table width="99%" height="81" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td height="35" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="27%" align="center">&nbsp;</td>
                      <td width="73%" align="center">
                       <div class="button01"><a href="#" onclick="login()" class="link03">登录</a></div>
                      <%-- 
                      <div class="button01">
                      <a href="#" onclick="register()" class="link01">马上注册</a></div>
                       --%>
                       <div class="button01">
                      <a href="<%=basePath%>page/ea/index.jsp"  class="link01">平台选择</a></div>
                       </td>
                    </tr>
                  </table>
            	</div>
            </div>
        </div><s:token></s:token>
      <div style="background-color:#FFFFFF; height:3px; width:100%;"></div>
     <div class="footer">北京天太世统科技有限公司 版权所有   服务电话：010-64167113</div>
</div>
</form>
	</body>
	<script type="text/javascript">
		var result = "${result}";
		var error = "";
		if(result == "1")
		{
			error = "登录政府不存在!";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}
		else if (result == "2")
		{
			error = "登录所在公司状态不正常！";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}
		else if (result == "4"){
			error = "登录帐号被停用！";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}
		else if(result == "3" || result == "5")
		{
			error = "登录账号密码不正确!";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}else if(result == "99")
		{
			error = "验证码不正确!";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}else if(result == "98")
		{
			error = "验证码不能为空!";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}else if(result == "97")
		{
			error = "用户名和密码不能为空!";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}else if(result == "6")
		{
			error = "禁止多开登录!";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}
		
	</script>
</html>
