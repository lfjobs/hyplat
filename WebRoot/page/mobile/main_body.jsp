<%@ page language="java"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>main</title>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"
			type="text/css"/>
</head>
<body leftmargin="8" topmargin="8">
<table width="320px;"  border="0" cellpadding="3" cellspacing="1" bgcolor="#C1DBE6" style="margin-bottom:8px;margin-top:8px;">
		  <tr>
            <td bgcolor="#ccdcf3" class="title"><span>消息</span></td>
  </tr>
          <tr bgcolor="#FFFFFF">
            <td>&nbsp;</td>
          </tr>
      </table>
<table width="320px;" border="0"  cellpadding="4" cellspacing="1" bgcolor="#C1DBE6" style="margin-bottom:8px">
<tr bgcolor="#EEF4EA">
            <td colspan="2" bgcolor="#ccdcf3" class="title"><span>系统基本信息</span></td>
    </tr>
          <tr bgcolor="#FFFFFF">
            <td width="35%" bgcolor="#FFFFFF">您的级别：</td>
            <td width="65%" bgcolor="#FFFFFF">${sessionScope.status}</td>
          </tr>
          <tr bgcolor="#FFFFFF">
            <td>软件版本信息：</td>
            <td>Cms_2010_UTF8</td>
  </tr>
      </table>
<table width="320px;"  border="0" cellpadding="4" cellspacing="1" bgcolor="#ccdcf3">
        <tr bgcolor="#ccdcf3">
           <td colspan="2" bgcolor="#ccdcf3" class="title"><span>使用帮助</span></td>
        </tr>
        <tr bgcolor="#FFFFFF">
           <td height="32">天太网站：</td>
           <td><a href="#"><u>http://www.ttsterp.com</u></a></td>
        </tr>
      </table>
</body>
</html>