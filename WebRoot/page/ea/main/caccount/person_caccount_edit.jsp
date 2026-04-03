<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人密码修改</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"type="text/css"/>
<script type="text/javascript">
        var basePath="<%=basePath%>/";
        var pass = '0';
		$(function(){
			  $("#tosave").click(function(){
			        pass = '1';
			        $(".input01").trigger("blur");
				          if($("form .error").length)
				          { 
				            return;
				          }     
					$("#pcaccountform").attr("action","<%=basePath%>//ea/caccount/t_ea_changePassword.jspa?pageNumber=${pageNumber}")
					document.pcaccountform.submit.click();
		        });
		})
</script>
<script src="<%=basePath%>/js/ea/validate.js"  type="text/javascript"></script>
	</head>

<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form name="pcaccountform" method="post" id="pcaccountform"><input type="submit" name="submit" style="display:none"/>
<table width="100%" height="68" border="0" align="center" cellpadding="0" cellspacing="2">
  <tr>
    <td align="left" valign="top">
      <div class="right">
    	<div class="qh_gg_nav">&nbsp;密码修改</div> 
          <table width="100%" 　border="0" align="center" cellpadding="0"
						cellspacing="0">
                    <tr>
                      <td width="3%" rowspan="11" align="right">&nbsp;</td>
                      <td width="24%" align="left" class="td_bg"></td>
                    </tr>
                    <tr>
                      <td width="12%" height="30" align="right" class="td_bg"> 原始密码： </td>
                      <td colspan="2" class="td_bg"><input name="password" type="password" maxlength="50"	class="input01 put3" id="password" size="20" />
                      </td>
                    </tr>
                    <tr>
                      <td width="12%" height="30" align="right" class="td_bg"> 新密码： </td>
                      <td colspan="2" class="td_bg"><input name="newPassword" class="input01 password" type="password"  size="20" />
                      </td>
                    </tr>
                    <tr>
                      <td width="12%" height="30" align="right" class="td_bg"> 确认新密码： </td>
                      <td colspan="2" class="td_bg"><input  class="input01 password1" type="password"  size="20"  />
                    </tr>
                    <tr>
                      <td height="39" align="center" class="td_bg">&nbsp;</td>
                      <td height="39" colspan="2" align="left" class="td_bg"><input type="button" class="ACT_btn" id="tosave"
									value=" 保存 " />
                      <td width="16%" height="39" align="left" class="td_bg">&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="18" colspan="5" align="center" class="td_bg">${result}</td>
                    </tr>
                  </table>
				  <s:token></s:token>
				 
        </div>
      </td>
  </tr>
</table>
</form>
</body>
</html>
