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
		<title>5L5C</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
			<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/humanindex.css">
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
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
							人事管理
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="container">
					<%-- <section class="clearfix">
						<div>
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/img_02.png"/>
						</div>
						<p>
							白静雨
						</p>
					</section> --%>
					<ul class="ul-con">
					<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/orgsystem.jsp">
						<li class="clearfix">
							<p class="p-title">组织系统</p>
							<p class="p-height clearfix">
								企业认证
								组织授权
								密码管理</br>
							    系统日志
								版本升级
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						</a>
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/zpmanage.jsp">
						<li class="clearfix">
							<p class="p-title">招聘管理</p>
							<p class="p-height">
						        招聘规划
								招聘收件
							    通知面试</br>
								面试合格
								通知培训
								培训合格</br>
								通知入职
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						</a>
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/pxmanage.jsp">
							<li class="clearfix">
								<p class="p-title">培训管理</p>
								<p class="p-height">
									培训题库
									教师管理
									场地管理</br>
									办公管理
									管理培训
									合格管理
								</p>
								<div class="div-more">
									<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
								</div>
							</li>
						</a>

						<li class="clearfix">

							<p class="p-title1" onclick="javascript:window.location.href='<%=basePath%>page/WFJClient/pc/5l5C/human/zzmanage.jsp'">在入离管理</p>

							<ul class="ul-con2">
								<li class="clearfix" onclick="javascript:window.location.href='<%=basePath%>page/WFJClient/pc/5l5C/human/zzmanage1.jsp'">
									<p class="p-title2">入职管理</p>
									<p class="p-height2">
										入职人员
										岗位变动</br>
										薪酬级别
										入职合同
									</p>

								</li>

								<li class="clearfix" onclick="javascript:window.location.href='<%=basePath%>page/WFJClient/pc/5l5C/human/zzmanage2.jsp'">

									<p class="p-title2">在职管理</p>
									<p class="p-height2">
										在职人员
										专兼岗
										兼岗</br>
										工作计划
										项目工作</br>
										工资管理
									</p>
									<div class="div-more">
										<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
									</div>

								</li>

								<li class="clearfix" onclick="javascript:window.location.href='<%=basePath%>page/WFJClient/pc/5l5C/human/zzmanage3.jsp'">
									<p class="p-title2">离职管理</p>
									<p class="p-height2">
										工作交接
										手续办理</br>
										增值服务
										黑名单
									</p>

								</li>

							</ul>


						</li>


							<a href="<%=basePath%>page/WFJClient/pc/5l5C/human/societyhr.jsp">
						<li class="clearfix">
							<p class="p-title">社会人力</p>
							<p class="p-height">
							人力管理
							优秀人力
								历史人物
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>

						
					</ul>
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
		<script type="text/javascript">
				var  basePath = "<%=basePath%>";
			//计算中间区域宽度
			$(".p-height").each(function(){
				var pWth=$(".pc-box").width()-$(this).prev().width()-100;
				$(this).width(pWth+"px")
			})

                $(".ul-con2").each(function(){
				var pWth=$(".pc-box").width()-$(this).prev().width()-58;
				$(this).width(pWth+"px")
                })
			//计算列表高度
			$(".p-height").each(function(){
//				console.log($(this).outerHeight())
//				console.log($(this).parent().outerHeight())
				var pHei=$(this).parent().outerHeight()-51;
				$(this).parent().find(".p-title").css('line-height',pHei+"px");
				$(this).parent().find(".div-more").css('line-height',pHei+50+"px");
			})

                $(".p-height2").each(function(){
                    var pHei=$(this).parent().outerHeight()-51;
                    $(this).parent().find(".p-title2").css('line-height',pHei+"px");
                    $(this).parent().parent().parent().find(".p-title1").css('line-height',pHei+150+"px");
                    $(this).parent().find(".div-more").css('line-height',pHei+50+"px");
                    var pWth=$(".pc-box").width()-$(this).prev().width()-180;
                    $(this).width(pWth+"px")
                })
			//判断页面是否有底部导航
			if($("*").is(".div-bottom")){
				$(".container").addClass("pc-bottom");
			}
		</script>
	</body>
</html>
