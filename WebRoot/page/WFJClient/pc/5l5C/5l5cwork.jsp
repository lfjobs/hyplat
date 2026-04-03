<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>工作应用</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/5l5cwork.css">
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/5l5C/5l5cwork.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			var companyID="${param.companyID}";
			var staffID="${param.staffID}";
		</script>
	</head>
	<body id="">
		<div class="pc-box">
			<div class="div-box">
				<header>
					<ul class="clearfix">
						<li>
							<a onclick="javascript: window.history.go(-1);return false;" target="_self" >
						
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
							</a>
						</li>
						<li>
							5L5C办公
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="content">
					<div class="div-name clearfix">
					<img  src="<%=basePath%>${userinfo[3]}" onerror='this.src="<%=basePath%>images/WFJClient/pc/my/pcimg_07.png"'  alt=''/>
					
					<%-- 	<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_02.png"  alt="" /> --%>
						<p>
							${userinfo[1]}
						</p>
					</div>
					<div class="div-nav">
						<ul class="clearfix">
							<li>
								<p>
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_07.png"/>
								</p>
								<p>
									工作应用
								</p>
							</li>
							<li>
								<p>
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_09.png"/>
								</p>
								<p>
									项目进度
								</p>
							</li>
							<li>
								<p>
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_11.png"/>
								</p>
								<p>
									任务消息
								</p>
							</li>
						</ul>
					</div>
					<div class="div-con pc-bottom">
						<ul class="ul-list">
							<li class="li-list clearfix">
								<p>策划</p>
								<div class="div-img">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_01.png"/>
								</div>
								<div class="div-con">
									<ul class="clearfix">
										<li>
											项目注册
										</li>
										<li>
											股东会
										</li>
										<li>
											董事会
										</li>
										<li>
											监事会
										</li>
										<li>
											顾问会
										</li>
										<li>
											工会
										</li>
									</ul>
								</div>
								
							</li>
							<li class="li-list clearfix">
								<p>决策</p>
								<div class="div-img">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_01.png"/>
								</div>
								<div class="div-con">
									<ul class="clearfix">
										<li>
											董事长室
										</li>
										<li>
											人事
										</li>
										<li>
											办公
										</li>
										<li>
											财务
										</li>
										<li>
											生产
										</li>
										<li>
											营销
										</li>
									</ul>
								</div>
								
							</li>
							<li class="li-list clearfix">
								<p>执行</p>
								<div class="div-img">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_01.png"/>
								</div>
								<div class="div-con">
									<ul class="clearfix">
										<li>
											总经理室
										</li>
										<li>
											人事
										</li>
										<li>
											办公
										</li>
										<li>
											财务
										</li>
										<li>
											生产
										</li>
										<li>
											营销
										</li>
									</ul>
								</div>
								
							</li>
							<li class="li-list clearfix fun">
								<a href="<%=basePath%>page/WFJClient/pc/5l5C/funclayer.jsp?companyID=${param.companyID}&staffID=${param.staffID}">
								<p>功能</p>
								<div class="div-img">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_01.png"/>
								</div>
								<div class="div-con">
									<ul class="clearfix">
										<li>
											人事
										</li>
										<li>
											办公
										</li>
										<li>
										<%-- <a href="<%=basePath%>page/WFJClient/pc/5l5C/finance/finance_index.jsp"> --%>
										
											财务
											<!-- </a> -->
										</li>
										<li>
											生产
										</li>
										<li>
										<%-- <a href="<%=basePath%>page/WFJClient/pc/5l5C/market/market_index.jsp"> --%>
											营销
										<!-- 	</a> -->
										</li>
									</ul>
								</div>
							  </a>
							</li>
							<li class="li-list clearfix">
								<p>创收</p>
								<div class="div-img">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_01.png"/>
								</div>
								<div class="div-con">
									<ul class="clearfix">
										<li>
											市场调查
										</li>
										<li>
											商品管理
										</li>
										<li>
											客户管理
										</li>
										<li>
											推广管理
										</li>
										<li>
											售后服务
										</li>
									</ul>
								</div>
								
							</li>
						</ul>
					</div>
				</div>
				<div class="footer div-bottom">
					<ul class="clearfix">
						<li>
							<div>
								<img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
							</div>
							<p>
								消息
							</p>
						</li>
						<li>
							<div>
								<img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
							</div>
							<p>
								通讯
							</p>
						</li>
						<li>
							<div>
								<img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
							</div>
							<p>
								数字
							</p>
						</li>
						<li class="active">
							<div>
								<img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
							</div>
							<p>
								5L5C
							</p>
						</li>
						<li>
							<div>
								<img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
							</div>
							<p>
								我的
							</p>
						</li>
					</ul>
				</div>
			</div>
		</div>
		
		
		   <!--公司认证状态弹窗 开始-->
    <div class="popup_rz" style="display:none;">
        <div class="rz_state">
              <!--公司未认证弹窗 开始-->
               <div class="rz_con">
                   <a href="javascript:void(0)" class="rz_close"></a>
                    <div class="bg_top"></div>
                    <img src="<%=basePath %>images/BuildPlatform/rz_img.png" class="rz_img" alt="">
                    <div class="rz_infotop"></div>         
                    <div class="rz_info">为了给您提供更好的服务请选择立即认证</div> 
                    <a href="javascript:void(0)" class="rz_btn">立即认证</a>    
               </div>
        </div>
    </div>
    <!--公司认证状态弹窗 结束-->
		<script type="text/javascript">
			var  basePath = "<%=basePath%>";
			var authState = "${concom.authState}";
			var  companyID = "${param.companyID}";
	    	var  staffID = "${param.staffID}";
		</script>
	</body>
</html>
