<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
<title>天太世统科技团</title>
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
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px">
  <tr>
    <td width="430"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="27" style="background-image:url(<%=basePath%>images/b_0222.gif); padding-left:14px; color:#FFFFFF"><strong>用户登录</strong></td>
      </tr>
      <tr>
        <td height="122" align="left" valign="top" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4; background-image:url(<%=basePath%>images/fl_bg.gif)"><table width="400" height="26" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image:url(<%=basePath%>images/jgbg.gif); margin-top:5px">
          <tr>
            <td align="center"><a href="<%=basePath%>page/ea/login.jsp" style="font-size:16px; color:#38608e; font-weight:bold">五层五清(5L5C）孵化管理平台管理系统1</a></td>
          </tr>
        </table>
          <%--<table width="400" height="26" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image:url(<%=basePath%>images/jgbg.gif); margin-top:5px">
            <tr>
              <td align="center"><a href="<%=basePath%>page/ea/login1.jsp" style="font-size:16px; color:#0c63c7; font-weight:bold">中国农业统一管理质量跟踪（5L5C）体系平台</a></td>
            </tr>
          </table>
          --%><table width="400" height="26" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image:url(<%=basePath%>images/jgbg.gif); margin-top:5px">
            <tr>
              <td align="center"><a href="<%=basePath%>page/ea/loginMobilemy.jsp" style="font-size:16px; color:#0c63c7; font-weight:bold">五层五清(5L5C）孵化管理平台移动管理系统</a></td>
            </tr>
          </table>
          <table width="400" height="26" border="0" align="center" cellpadding="0" cellspacing="0" style="background-image:url(<%=basePath%>images/jgbg.gif); margin-top:5px">
            <tr>
              <td align="center"><a href="<%=basePath%>page/ea/loginData.jsp" style="font-size:16px; color:#0c63c7; font-weight:bold">五层五清(5L5C）孵化管理平台客户管理系统</a></td>
            </tr>
          </table></td>
      </tr>
    </table></td>
    <td align="right" valign="top"><table width="560" border="0" cellpadding="0" cellspacing="0" onclick="javascript:window.open('http://www.ttst2010.com/about_us.html')" style=" cursor: pointer">
      <tr>
        <td height="26" align="left" style="background-image:url(<%=basePath%>images/k_bg.gif); padding-top:2px; padding-left:15px; font-weight:bold; color:#FFFFFF">关于我们</td>
      </tr>
      <tr>
        <td align="left" valign="top" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4; line-height:20px"><img src="<%=basePath%>images/left_01_pic.gif" align="left" style="margin-top:5px"/>&nbsp;&nbsp;&nbsp;&nbsp;北京天太世统科技团简介 北京天太世统科技有限责任公司是天太胜威集团旗下9家子公司之一，公司的发展战略是：以软件开发为核心，以为客户定制个性化软件为特色，以为政府、企业、社区和乡村提供高效、优质、便捷的信息化服务为重点，以打造国内领先的企业管理信息化、数字化服务最佳平台为目标。公司研发人员实力雄厚、经验丰富，均具有5年以上国内著名IT企业工作经验，员工20%以上具有研究生学历。目前，已成功研发出了“五层五清（5L5C）”孵化管理体系ERP企业管理软件、电话信息管理系统、GPS车辆全面管理系统，这些产品均可为企业、客户提供优质高效的服务。<br /></td>
      </tr>
      
    </table></td>
  </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px">
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
            地址：北京市东城区东直门外大街<br />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            宇飞大厦801室<br />
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
                <span class="txt07">自主开发产品<br />
                企业定制产品</span> <img src="<%=basePath%>images/go.gif" width="16" height="16" border="0" /></td>
            <td width="147" height="90" valign="top"><span class="txt">电话信息管理系统</span><br />
                <span class="txt07">电话监控…</span>　<br />                <img src="<%=basePath%>images/go.gif" width="16" height="16" border="0" /></td>
            <td width="162" height="90" valign="top"><span class="txt">孵化管理平台（OA）</span><br />
                <span class="txt08">人事处（人事）<br />
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
        <td height="160" align="left" valign="top" style="padding:5px; border-bottom:1px solid #4a90c4; border-left:1px solid #4a90c4; border-right:1px solid #4a90c4;line-height:22px"><img src="../../images/jituanshu.gif" align="left" style="margin-right:5px" border="0"/>&nbsp;&nbsp;&nbsp;&nbsp;定制软件是根据用户的要求设计软件，开发过程遵循软件工程的规范，提供新建系统的方案设想，并进行可行性分析。在程序编码前进行系统的概要设计和详细设计，在程序编制结束后进行软件测试，交付使用时。</td>
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
          ·[四川]宜宾市2010年上半年</td>
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
            <td align="center"><img src="<%=basePath%>images/ksmj.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/jsjq2.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/ksmj.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/xclc.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/jsjq.gif" width="91" height="91" border="0"/></td>
              <td align="center"><img src="<%=basePath%>images/jxsp.gif" width="91" height="91" border="0"/></td>
              </tr>
            <tr>
             <td align="center"><a href="<%=basePath%>onlineExam/online_login.jsp">在线考试系统</a></td>
              <td align="center">自主开发产品</td>
              <td align="center">电话信息管理系统</td>
              <td height="20" align="center">孵化管理平台（OA）</td>
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
    <td height="90" align="center" valign="top" style="line-height:26px; color:#FFFFFF; padding-top:15px">京ICP备10034132号-2 版权所有 北京天太世统科技有限公司<br />
Copyright 2009-2010 www.ttst2010.cn Corporation, All Rights Reserved <br />
公司地址：北京市东直门外大街宇飞大厦801室 服务热线：64164005 </td>
  </tr>
</table>
</body>
</html>
