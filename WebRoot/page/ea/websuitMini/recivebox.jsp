<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page  import="hy.ea.bo.CAccount" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
CAccount M = (CAccount)session.getAttribute("account"); //从session里把a拿出来，并赋值给M
String  organizationID = (String)session.getAttribute("organizationID");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>公文收件箱</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

<meta name="author" content="FamousThemes" />
<meta name="description" content="GoMobile - A next generation web app theme" />
<meta name="keywords" content="mobile web app, mobile template, mobile design, mobile app design, mobile app theme, mobile wordpress theme, my mobile app" />
 <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/flexslider.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/jquery.mmenu.all.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/style.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/stylemain.css">
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/personal.css">
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/swipebox.css">
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/qz_home.css">
		<link type="text/css" rel="stylesheet" href="<%=basePath%>js/jquery-easyui-1.4/themes/metro/easyui.css">
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js" ></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/jquery.mmenu.min.all.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/o-script.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/recivebox.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<style>
	#menu img{
	width:40px;
	height:40px;
	}
	.close{cursor:pointer;padding:2px 3px;cursor:pointer;padding:2px 3px;position:absolute;right:10px;top:25px;font-size: 25px;}
.close:hover{background-color:grey;color:#ffffff}
.navigationbar{
	width:100%;
	position:fixed;  /* border-top:1px solid #587EAC;  */left:0px; bottom:0px; _position:absolute;
	}
	div .li{
	width: 25%;
	float: left;
	line-height: 40px;
	background-color: #587EAC;
	color: white;
	border: 1px solid white;
	font-size: 14px;
	font-family: 微软雅黑;
	cursor: pointer;
		}
		
	/* circularizedialog */
#circularizedialog{font-size: 12px;
	color: #333;
	font-family: "微软雅黑", Tahoma, Arial, sans-serif;}
</style>
<script type="text/javascript">
var basePath='<%=basePath%>';  
var searchType = "${searchType}";
var module = '<%=session.getAttribute("module")%>'; 

</script>
</head>
<body class="o-page">
<div id="page">
			<!-- Header -->
			<div id="header" >
				<a href="#menu"><img src="<%=basePath%>images/websuitMini/menu.png"  alt=""/></a><a class="backBtn" href="javascript:history.back();"><img src="<%=basePath%>images/websuitMini/history.png"  alt=""/></a>公 文 流 转(收件箱)</div>
      <div class="swiper-slide sliderbg">

      <div class="swiper-container swiper-nested2">
               <div class="swiper-wrapper">
                    <div class="swiper-slide">
                              <div class="slide-inner">
                                        <div class="pages_container">
                                        <h2 class="page_title">收件箱</h2>
                                        <blockquote>公司企业级个人公文查看、预览、审批，请严格按照需求操作执行，注意文件的保密性！<span class="quote_author">责任人-Steve Jobs</span></blockquote>
                                        
                                        <h3>个人公文收件箱</h3>
                                        <%
										int number = 1;
										%>
										<s:iterator id="docList" var="arr" value="pageForm.list">
											 <div class="service_box">
                                          <div class="services_icon"><img src="<%=basePath%>images/websuitMini/wenjian.png" alt="" title="" /></div>
                                          <div class="service_content">
                                          <h4> <input type="checkbox" id="${arr[1]}"/><strong><lable  for="${arr[1]}">${arr[4]}.${fn:substringAfter(arr[75], ".")}</lable></strong>&nbsp;<img src="<%=basePath%>images/ea/office/document/attach1.png" style="height:20px;width:20px;"/>&nbsp;<a onclick="seedoc()">查看附件</a></h4>
                                          		<p>公文编号:&nbsp;&nbsp;&nbsp;${arr[2]} </p>
                                              <p>报送时间:&nbsp;&nbsp;&nbsp;${fn:substring(arr[48],0,19)} </p>
                                              <p>申报单位名称:&nbsp;&nbsp;&nbsp;<%=M.getCompanyName()%> </p>
                                              <p>附件真实路径:&nbsp; <span class="filepathandtype">${fn:substring(arr[75],0,20)}</span></p>
                                          </div>
                                        </div>
										<%
											number++;
										%>
										</s:iterator>
                                        <div class="clearfix"></div>
                                        </div>
                              </div>
                    </div>
              </div>
              <div class="swiper-scrollbar2"></div>
              
     </div>
     </div>
	
	
	  <div class="navigationbar">
	  	  <div class="li" style="text-align: center" id="toDrafts">转个人草稿箱</div>
		  <div class="li" style="text-align: center" id="circularize">传阅</div>
		  <div class="li" style="text-align: center" id="">至领导审批</div>
		  <div class="li" style="text-align: center" id="">放入回收站</div>
	  
	  </div>
	  <!-- 公文预览dialog 层 -->
	  <div id="documentpreview" title="公文预览" style="width:100%;height:100%;">  
         <%--  <img src="<%=basePath%>images/websuitMini/loading.gif" style="margin-top: 20%;margin-left: 50%"/></br> --%>
	  </div>
	  <!-- 公文传阅至某人dialog 层 -->
	  <div  id="circularizedialog" title="公文传阅" style="width:100%;height:100%;">
		
	</div>
	  </div>
	  <div id="chuanyhue" style="width:100%;height:100%;display: none"> 
	  
	  <section class="s_reg s_login">
			<div></div><div></div>
			<form  id="form1" >
					<span>接收人公司:</span>
					<select id="selectrecivecom">
						<option selected="selected" >请选择接收人公司</option>
						
					</select></br>
					<span>接收人部门: </span>
					<select id="selectrecivedep">
						<option selected="selected">请选择接收人部门</option>
					</select></br>
					<span>接收人:</span>
					<select id="selectrecivepeople">
						<option selected="selected" >请选择接收人</option>
						<option>cf</option>
						<option>cf</option>
					</select>
				<p class="wrong_tip" id="password_tip"></p>
				<input type="submit" id="submit" value="提交">
				<p class="wrong_tip" id="submit_tip"></p>
				<input type="button" id="submit" value="提交" style="width: 50px;height: 20px;display: none" >
			</form>
		</section>
	  
	  </div>
	  <div id = "message" style="display:none">
  <p>信息提醒功能 </p>
  <p><strong>1.</strong><strong>业务背景 </strong><br>
         目前了解到系统的提醒功能是各模块独自开发的提醒功能，建议将消息提醒功能调整为公共功能模块，供其他模块进行调用。 <br>
<strong>2</strong><strong>．功能描述 </strong><br>
         消息提醒功能模块开发接口接收其他模块传递的数据，由消息提醒功能模块进行统一记录，并进行处理下发。 <br>
<img width="554" height="277" src="<%=basePath%>images/websuitMini/1.png"> <br>
<strong>3.</strong><strong>功能要求 </strong><br>
<strong>3.1</strong><strong>接口数据项 </strong><br>
各模块调用消息提醒接口，向消息提醒模块发送数据 </p>
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="111" valign="top"><br>
        <strong>字段名称 </strong></td>
      <td width="111" valign="top"><p align="center"><strong>数据类型 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>是否必填 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>是否多值 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>备注 </strong></p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>通知类型 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>待阅、待办 </p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>通知标题 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>&nbsp;</p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>通知内容 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>&nbsp;</p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>访问链接 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>用户点击链接可直接访问业务处理页面 </p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>接收人 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>&nbsp;</p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>接收时间 </p></td>
      <td width="111" valign="top"><p>日期时间型 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>同一消息发送多次 </p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>提醒方式 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>页面弹窗、短信提醒、客户端提醒。 </p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>提醒状态 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>未发送、已发送。默认为未发送，发送成功后变更为已发送，发送失败 </p></td>
    </tr>
  </table>
  <p><strong>3.2</strong><strong>定时任务 </strong><br>
    消息提醒模块设置定时任务，任务执行时间自动调用消息发送接口，将消息发送到用户端。消息发送接口与提醒方式相关联，每个提醒方式具备一个发送接口。提醒发送后，提醒状态修改为已发送。 </p>
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="111" valign="top"><br>
        <a name="OLE_LINK2"></a><a name="OLE_LINK1"><strong>字段名称 </strong></a></td>
      <td width="111" valign="top"><p align="center"><strong>数据类型 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>是否必填 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>是否多值 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>备注 </strong></p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>通知类型 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>待阅、待办 </p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>通知标题 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>&nbsp;</p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>通知内容 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>&nbsp;</p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>接收人 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>&nbsp;</p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>访问链接 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>用户点击链接可直接访问业务处理页面 </p></td>
    </tr>
  </table>
  <p><strong>3.3</strong><strong>发送接口配置 </strong><br>
    各提醒方式的发送接口应为可配置的，可以增加和删除。配置每个提醒方式 </p>
  <table border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td width="111" valign="top"><br>
        <strong>字段名称 </strong></td>
      <td width="111" valign="top"><p align="center"><strong>数据类型 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>是否必填 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>是否多值 </strong></p></td>
      <td width="111" valign="top"><p align="center"><strong>备注 </strong></p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>提醒方式 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>&nbsp;</p></td>
    </tr>
    <tr>
      <td width="111" valign="top"><p>发送接口地址 </p></td>
      <td width="111" valign="top"><p>文本 </p></td>
      <td width="111" valign="top"><p>是 </p></td>
      <td width="111" valign="top"><p>否 </p></td>
      <td width="111" valign="top"><p>&nbsp;</p></td>
    </tr>
  </table>
  <p><strong>3.4</strong><strong>页面弹窗提醒 </strong><br>
    页面弹窗提醒功能分为两种情况： </p>
  <ol>
    <li>下发通知时用户在线，在页面右下方弹出提示框，提醒状态变更为已发送，用户点击标题/内容跳转到业务处理页面。 </li>
    <li>下发通知时用户未在线，则视为发送失败，提醒状态为发送失败，当用户登录系统后，自动调取发送失败的提醒消息，在页面右下方弹出提示框，用户点击标题/内容跳转到业务处理页面。 </li>
  </ol>
</div>  
			
			<!-- Menu navigation -->
			<nav id="menu">
				<ul>
					<li class="Selected">
						<a>
							<i class="i-home i-small"></i>拟稿
						</a>
                        <ul>
							<li><a  id="uploadfile" onclick="document.location.href='<%=basePath%>page/ea/websuitMini/uploaddocumentFile.jsp'">起草上传</a></li>
							<li>
								<a onclick="document.location.href='<%=basePath%>ea/documentinfo/ea_getReceiveBoxList.jspa?searchType=mobile&data=Math.random()'">收件箱</a>
							</li>
							<li>
								<a href="blog-single-post.html">已发送</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-about i-small"></i>审批
						</a>
                        <ul>
							<li><a href="blog-single-post.html">未审批公文</a></li>
							<li>
								<a href="blog-single-post.html">已审批公文</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-blog i-small"></i>盖章
						</a>
						<ul>
							<li><a href="blog-single-post.html">已盖章</a></li>
							<li>
								<a href="blog-single-post.html">未盖章</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-gallery i-small"></i>发文管理
						</a>
                        <ul>
							<li><a href="blog-single-post.html">未发公文</a></li>
							<li>
								<a href="blog-single-post.html">已发公文</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-shortcodes i-small"></i>阅读管理
						</a>
                        <ul>
							<li><a href="blog-single-post.html">未阅读</a></li>
							<li>
								<a href="blog-single-post.html">已阅读</a>
							</li>
						</ul>
					</li>
					<li>
						<a>
							<i class="i-contact i-small"></i>公文归档
						</a>
                        <ul>
							<li><a href="blog-single-post.html">已归档</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
</html>
