<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title>公文上传页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

<meta name="author" content="FamousThemes" />
<meta name="description" content="GoMobile - A next generation web app theme" />
<meta name="keywords" content="mobile web app, mobile template, mobile design, mobile app design, mobile app theme, mobile wordpress theme, my mobile app" />
 <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js" ></script>
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/flexslider.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/jquery.mmenu.all.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/style.css" />
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/stylemain.css"/>
		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/personal.css"/>
<%-- 		<link type="text/css" rel="stylesheet" href="<%=basePath%>css/websuitMini/idangerous.swiper.css"/>
 --%>		
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/jquery.mmenu.min.all.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/o-script.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/websuitMini/documentNavControl.js"></script>
		
		<style>
	#menu img{
	width:40px;
	height:40px;
	}
	.close{cursor:pointer;padding:2px 3px;cursor:pointer;padding:2px 3px;position:absolute;right:10px;top:25px;font-size: 25px;}
.close:hover{background-color:grey;color:#ffffff}
#uploading{
width:200px;/**宽度**/
height:100px;/**高度**/
position:fixed;/**绝对定位**/

left:50%;/**左边50%**/
top:50%;/**顶部50%**/
margin-top:-50px;/**上移-50%**/
margin-left:-100px;/**左移-50%**/
text-align:center;
margin:0, 20%,50%,;
}
</style>
<script type="text/javascript">
var token = 2;
var basePath='<%=basePath%>';  
var searchType = "${searchType}";
var module = '<%=session.getAttribute("module")%>'; 
function seedoc(){
$("#login-modal").show(1000);
}
function closesee(){
$("#login-modal").hide();
}
</script>
</head>
<body class="o-page">
<div id="page">
			<!-- Header -->
			<div id="header">
				<a href="#menu"><img src="<%=basePath%>images/websuitMini/menu.png"  alt=""/></a><a class="backBtn" href="javascript:history.back();"><img src="<%=basePath%>images/websuitMini/history.png"  alt=""/></a>公 文 流 转(上传/拟稿)</div>
			 <!--Page 1 content-->
      <div class="swiper-slide sliderbg">
      <div class="swiper-container swiper-nested2">
               <div class="swiper-wrapper">
                    <div class="swiper-slide">
                             <div class="slide-inner">
                                        <div class="slide-inner">
                                        <div class="pages_container">
                                        <h2 class="page_title">公文上传/拟稿</h2>
                                        <blockquote>Design is the fundamental soul of a human-made creation that ends up expressing itself in successive outer layers of the product or service. <span class="quote_author">-Steve Jobs</span></blockquote>
                                     
                                       <form method="post" name="uploadFiles" id = "uploadFiles" <%-- action="<%=basePath%>ea/documentcommon/ea_uploadFiles.jspa" --%> enctype= "multipart/form-data" >
                                        <input type="submit" name="submit" style="display: none"/>
                                        <div class="service_box">
                                          <div class="services_icon"><img src="<%=basePath%>images/websuitMini/travel.png" alt="" title="" /></div>
                                          <div class="service_content">
                                          <h4>上传</h4>
                                          <p>由于手机版本无法在线编辑，故只能上传提前编写好的公文文件
                                          </br>注:只限office文件
                                          </p>
                                          <p><input type="file" name="files"  id="filevalue" value="浏览"/>
                                          </p>
                                          
										
                                          </div>
                                        </div>
                                        <div class="clearfix" ></div>
                                        <div class="form">
                                       
                                <label>文件标题:</label>
                                <input class="form_input radius4" name="document.title" id="title" value=""  type="text">
                                <label>主&nbsp;题&nbsp;词</label>
                                <input class="form_input radius4"  name="document.theme" id="themes" type="text">
                                 <label>正式编号</label>
                                <input class="form_input radius4" id="numWord" name="document.numWord"  type="text" placeholder="系统默认填写" readonly="readonly">
                                <label>文件缓急</label>
                               <!--  <textarea class="form_textarea radius4" rows="" cols=""></textarea> -->
                                <select class="form_input radius4" name="document.emergencyType" style="width:100%;height:35px;" id="emergencyType"><option>普通</option><option>急件</option><option>特急</option></select>
                                <label>公文类型</label>       

                                <select class="form_input radius4" id="docType" name="document.docType" style="width:100%;height:35px;">
                                <option value="ss" selected="selected">
													请选择公文类型
												</option>
												<option value="aa">
													董事会会议决定文件
												</option>
												<option value="bb">
													董事长办公室文件
												</option>
												<option value="cc">
													总裁办公室文件
												</option>
												<option value="dd">
													总部人事处文件
												</option>
												<option value="ee">
													总部办公室文件
												</option>
												<option value="ff">
													总部财务处文件
												</option>
												<option value="gg">
													总部教务(生产)处文件
												</option>
												<option value="hh">
													总部营销处文件
												</option>
												<option value="ii">
													总部服务(创收)平台
												</option>
												<option value="jj">
													总部教务部文件
												</option> 
                                </select><br/><br/>
                                <input class="form_submit radius4 red red_borderbottom" value="确认上传" id = "upload" type="button">
                                </form>
                                        </div>
                                        </div>
                                        <!--End of page container-->
                              </div>
                    </div>
              </div>
              <div class="swiper-scrollbar2"></div>
              
     </div>
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
		<div id="uploading" style="display:none;">
			<img src="<%=basePath%>images/websuitMini/loading.gif" alt="" />
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		
		
										
</html>
