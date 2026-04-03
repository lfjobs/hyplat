<%@ page language="java" pageEncoding="UTF-8"%>
 <%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>五层五清(5L5C）孵化管理平台客户管理系�?/title>
		<link href="<%=basePath%>css/ea/login.css" rel="stylesheet"	type="text/css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript">
var basePath="<%=basePath%>";
var long = 1;
var message = '${message}';

//错误提示
 	if(message!=""){
 		alert(message);
 	}
 	
$(document).ready(function(){
	$("input.strj1").focus();//设ID值为strj的文本框为默认焦点事�?
});

document.onkeydown = function(evt){//捕捉回车   
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键�?
    if (key == 13&&long == 1) { //判断是否是回车事件�?
        login();
    }
}
function gxtp(){
		$("img[name='validateImage']").trigger("click"); //获取图片"看不清，换一�?点击事件
}
function register(){
    window.location.href = "<%=basePath%>page/ea/register.jsp?imgLog=1"; //注册跳转路径
}

function login(){
	var flag = true;
	var notnull1 = "";
	var notnull2 = "";
	var notnull3 = "";
	var notnull4 = "";
	
	$("form#Login").find("input#strjName").each(function(i){
		if($(this).val()==""){
			if(i==0){
				notnull1 = "组织机构不能为空!\t\n";
				flag = false;
			}
			if(i==1){
				notnull2 = "用户名不能为�?\t\n";
				 flag = false;
			}
			if(i==2){
				notnull3 = "密码不能为空!\t\n";
				flag = false;
			}
			if(i==3){
				notnull4 = "验证码不能为�?\t\n";
				flag = false;
			}
		}
	})
	var notnull = notnull1+ notnull2+ notnull3+ notnull4;

	if(flag==false){
		if($("input.strj4").val()==""){
			$("input.strj4").focus();
		}
		if($("input.strj3").val()==""){
			$("input.strj3").focus();
		}
		if($("input.strj2").val()==""){
			$("input.strj2").focus();
		}
		if($("input.strj1").val()==""){
			$("input.strj1").focus();
		}
		alert(notnull);
		return false;
	}
	
	long=0;
    $('#Login').attr('action', "<%=basePath%>ea/login.jspa?"); //当long=0�?走action路径
    document.Login.submit.click(); //点击login提交事件
}
function repass(){
    window.location.href="<%=basePath%>page/ea/RePassword/rePassLogin.jsp";
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.txt{
color:#004f80;
font-size:12px;
font-weight:bold;
}
.txt07{
color:#000;
font-size:13px;
line-height:30px;
text-decoration:none;
}
.txt08{
color:#000;
font-size:12px;
line-height:20px;
text-decoration:none;
}

.table {
	border-collapse:collapse;
	border:1px solid #f38901;
	font-size:12px;
}


.table td {
	border:1px solid #f38901;
	color:#333;
	height:30px;
}
body,td,th {
	font-size: 12px;
}
a:link {
	text-decoration: none;
	color: #333333;
}
a:visited {
	text-decoration: none;
	color: #333333;
}
a:hover {
	text-decoration: none;
	color: #FF0000;
}
a:active {
	text-decoration: none;
	color: #FF0000;
}
-->

</style>
</head>

<body>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28,0" width="1000" height="170">
      <param name="movie" value="<%=basePath%>images/banner.swf" />
      <param name="quality" value="high" />
      <embed src="<%=basePath%>images/banner.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="1000" height="170"></embed>
    </object></td>
  </tr>
</table>
<form name="Login" method="post" id="Login" action=""><input type="submit" name="submit" style="display:none"/><input type="text" value="01" name="companyType" style="display:none"/>
<s:token/>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:8px">
  <tr>
    <td width="430"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="27" style="background-image:url(<%=basePath%>images/b_0222.gif); padding-left:14px; color:#FFFFFF"><strong>用户登录</strong></td>
      </tr>
      <tr>
        		<td height="122" align="left" valign="top" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4; background-image:url(<%=basePath%>images/fl_bg.gif)"><table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="23%" height="34" align="right">组织机构名：</td>
                      <td colspan="3"><label>
                        <input type="text" name="companyIdentifier" class="strj1" id="strjName" tabindex="1"/>
                      </label>
                      <a href="<%=basePath%>page/ea/index.jsp" tabindex="7">平台选择</a>
                      </td>
                    </tr>
                    <tr>
                      <td height="35" align="right">用户名：</td>
                      <td colspan="3"><input type="text" name="account.accountEmail" class="strj2" id="strjName" tabindex="2"/>
                      <%-- <a href="#" onclick="register()" tabindex="8">注册用户</a> --%>
                      </td>
                    </tr>
                    <tr>
                      <td height="32" align="right">密码�?/td>
                      <td colspan="3"><input type="password" name="account.accountPassword" size="22" class="strj3" id="strjName" tabindex="3"/>
                      <a href="#" onclick="repass()" tabindex="9">找回密码</a>
                      </td>
                    </tr>
                    <tr>
                      <td height="32" align="right">图片�?/td>
                      <td colspan="3"><img border="0" name="validateImage"
											onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abcd='+Math.random()"
											src="<%=basePath%>page/ea/security_code.jsp" />&nbsp;&nbsp;<a href="#" onclick="javascript:gxtp();" style="height: 16px" tabindex="4">看不清，换一�?/a></td>
                    </tr>
                    <tr>
                      <td height="30" align="right">输入验证码：</td>
                      <td colspan="3"><input type="text" name="validateCode" class="strj4" id="strjName" tabindex="5"/>
                      </td>
                      <td colspan="3"><a href="#" onclick="login()" class="link03" tabindex="6">登录</a>
                      </td>
                    </tr>	
                   </table>
        </td>
      </tr>
    </table></td>
    <td align="right" valign="top"><table width="560" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="26" align="left" style="background-image:url(<%=basePath%>images/k_bg.gif); padding-top:2px; padding-left:15px; font-weight:bold; color:#FFFFFF">关于我们</td>
      </tr>
      <tr>
        <td align="left" valign="top" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4; line-height:20px"><img src="<%=basePath%>images/left_01_pic.gif" align="left" style="margin-top:5px"/>&nbsp;&nbsp;&nbsp;&nbsp;北京天太世统科技有限责任公司隶属于天太胜威集团，是天太胜威集团软件开发的核心职能主体。我们致力于打造国内领先的管理信息化服务提供商，为国内社区、乡村及中小型企业提供高效、优秀、便捷的数字管理方案，提升综合管理能力。公司在发展过程中，经过不断的探索与努力，天太世统已经形成了具有独特精神的软件开发模式，而天太世统也一直致力将我们的成功模式推向社会，与您一同走向更加美好的未来�?br />
            <br />
          <br /></td>
      </tr>
      
    </table></td>
  </tr>
</table>
</form>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:8px">
  <tr>
    <td><img src="<%=basePath%>images/banner1.gif" width="1001" height="92" /></td>
  </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px">
  <tr>
    <td width="195"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="27" style="background-image:url(<%=basePath%>images/b_195.gif); padding-left:14px; color:#FFFFFF"><strong>客户服务</strong></td>
        </tr>
        <tr>
          <td height="150" align="left" valign="top" bgcolor="f1faf7" style="padding-left:3px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4; line-height:20px"><img src="<%=basePath%>images/lxdh.jpg" style="margin-bottom:8px"/><br />
            地址：北京市东城区东直门外大�?br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            宇飞大厦801�?br />
            网址：www.ttst2010.com<br />
            邮箱：swjx1998@163.com</td>
        </tr>
      </table></td>
    <td><table width="560" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
       <td height="26" style="background-image:url(<%=basePath%>images/k_bg.gif); padding-top:2px; padding-left:15px; font-weight:bold; color:#FFFFFF">产品</td>
      </tr>
      <tr>
        <td height="160" align="left" valign="top" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4"><table width="100%" height="139" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="145" height="90" valign="top"><span class="txt">产品展示</span><br />
                <span class="txt07">自主开发产�?br />
                企业定制产品</span> <img src="<%=basePath%>images/go.gif" width="16" height="16" border="0" /></td>
            <td width="147" height="90" valign="top"><span class="txt">电话信息管理系统</span><br />
                <span class="txt07">电话监控�?/span>　<br />                <img src="<%=basePath%>images/go.gif" width="16" height="16" border="0" /></td>
            <td width="162" height="90" valign="top"><span class="txt">孵化管理平台（OA�?/span><br />
                <span class="txt08">人事处（人事�?br />
                  人事处（办公室）<br />
                  人事处（财务）…　<img src="<%=basePath%>images/go.gif" width="16" height="16" border="0" /></span></td>
            <td width="146" height="90" valign="top"><span class="txt">硬件集成</span><br />
                <span class="txt08">电话<br />
                  网络监控系统<br />
                  GPS车辆导航系统　<img src="<%=basePath%>images/go.gif" width="16" height="16" border="0" /></span></td>
          </tr>
          <tr>
            <td><img src="<%=basePath%>images/cp_01.gif" width="109" height="54" /></td>
            <td><img src="<%=basePath%>images/cp_02.gif" width="109" height="54" /></td>
            <td><img src="<%=basePath%>images/cp_03.gif" width="104" height="55" /></td>
            <td><img src="<%=basePath%>images/cp_04.gif" width="110" height="54" /></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td width="230"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="27" style="background-image:url(<%=basePath%>images/b_02.gif); padding-left:14px; color:#FFFFFF"><strong>软件定制</strong></td>
      </tr>
      <tr>
        <td height="160" align="left" valign="top" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4;line-height:22px"><img src="<%=basePath%>images/jituanshu.gif" align="left" style="margin-right:5px" border="0"/>&nbsp;&nbsp;&nbsp;&nbsp;定制软件是根据用户的要求设计软件，开发过程遵循软件工程的规范，提供新建系统的方案设想，并进行可行性分析。在程序编码前进行系统的概要设计和详细设计，在程序编制结束后进行软件测试，交付使用时�?/td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px">
  <tr>
    <td width="195"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="27" style="background-image:url(<%=basePath%>images/b_195.gif); padding-left:14px; color:#FFFFFF"><strong>传承·创新</strong></td>
      </tr>
      <tr>
        <td height="150" align="left" valign="top" bgcolor="f1faf7" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4; line-height:24px">·天太世统网站上线啦！<br />
          ·天太世统发布电话信息管理系统<br />
          ·天太世统发布孵化管理平台<br />
          ·天太世统发布硬件集成系统<br />
          ·高烽厅长在全省交通运输系<br />
          ·[四川]宜宾�?010年上半年</td>
      </tr>
    </table></td>
    <td><table width="797" border="0" align="right" cellpadding="0" cellspacing="0">
      <tr>
        <td height="26" style="background-image:url(<%=basePath%>images/k_797.gif); padding-top:2px; padding-left:15px; font-weight:bold; color:#FFFFFF">精品工程</td>
      </tr>
      <tr>
        <td height="145" align="left" valign="top" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4"><table width="100%" height="130" border="0" cellpadding="0" cellspacing="0">
          <tbody>
            <tr>
              <td align="center"><img src="<%=basePath%>images/jsjq2.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/ksmj.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/xclc.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/jsjq.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/jxsp.gif" width="91" height="91" border="0"/></td>
              </tr>
            <tr>
              <td align="center">自主开发产�?/td>
              <td align="center">电话信息管理系统</td>
              <td height="20" align="center">孵化管理平台（OA�?/td>
              <td height="20" align="center">硬件集成系统</td>
              <td align="center">操作视频</td>
              </tr>
          </tbody>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; background-image:url(<%=basePath%>images/bottombg.gif)" height="112">
  <tr>
    <td height="90" align="center" valign="top" style="line-height:26px; color:#FFFFFF; padding-top:15px">京ICP�?0034132�?2 版权所�?北京天太世统科技有限公司<br />
Copyright 2009-2010 www.ttst2010.cn Corporation, All Rights Reserved <br />
公司地址：北京市东直门外大街宇飞大厦801�?服务热线�?4164005 </td>
  </tr>
</table>
</body>
	<script type="text/javascript">
		var result = "${result}";
		var error = "";
		if(result == "1")
		{
			error = "登录企业不存�?";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}
		else if (result == "2")
		{
			error = "登录所在公司状态不正常";
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
			error = "登录账号密码不正�?";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}else if(result == "99")
		{
			error = "验证码不正确!";
			alert(error);
			document.location.href = "<%=basePath%>page/ea/index.jsp";
		}else if(result == "98")
		{
			error = "验证码不能为�?";
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
