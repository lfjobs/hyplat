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
		<title>登录</title>
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

function login(){
    window.location.href="<%=basePath%>page/ea/index.jsp";
}

</script>
	</head>
	<body>
	<form name="Login" method="post" id="Login" action=""><input type="submit" name="submit" style="display:none"/><input type="text" value="00" name="companyType" style="display:none"/>
	    <div class="main">
		<div class="top">
            <div class="top01">
            	<div class="conter">
            	  <table width="90%" height="252" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td height="85" colspan="4" align="center" class="td01">邮箱发送信息</td>
                    </tr>
                    <tr>
                      <td width="25%" height="70" align="right"></td>
                      <td colspan="3"><h3><font color="green"><label>
                            用户信息已发送到邮箱${companyEmail}中!
                      </label></font></h3>
                      </td>
                    </tr>
                  </table>
            	  <table width="99%" height="81" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td height="35" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="40%" align="center">&nbsp;</td>
                      <td width="60%" align="center">
                       <div class="button01"><a href="#" onclick="login()" class="link03">进入登陆</a></div>
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
</html>
