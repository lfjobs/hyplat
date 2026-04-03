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
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/zzmanage.css">
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
							入在离管理
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-title">入职管理</p>

							<p class="p-height clearfix">
								<a>入职人员</a>
								<a>岗位变动</a>
								<a>薪酬计算</a>
								<a>入职合同</a></br>
								<a>在职人员</a>
								<a>专兼岗</a>
								<a>专岗</a>
								<a>兼岗</a>
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title1">在职管理</p>
							<ul class="ul-con2">
							<li  class="clearfix">
							<p class="p-title2">项目工作</p>
							<p  class="p-height2 clearfix">
								<a>本日</a>
								<a>昨日</a>
								<a>本周</a>
								<a>本月</a>
								<a>上月</a></br>
								<a>本季</a>
								<a>上季</a>
								<a>本年</a>
								<a>上年</a>
							</p>
							</li>
								<li  class="clearfix">
								<p class="p-title2">工资管理</p>
								<p  class="p-height2 clearfix">
									<a>人员工资</a>
									<a>级别确定</a>
									<a>基本保障</a></br>
									<a>福利保障</a>
									<a>激励绩效</a>
									<a>保险</a>
									<a>收付账</a>

								</p>
								</li>
								<li  class="clearfix">
								<p class="p-title2">工资设置</p>
								<p  class="p-height2 clearfix">
									<a>级别设定</a>
									<a>基本保障</a>
									<a>福利保障</a></br>
									<a>激励绩效</a>
									<a>保险</a>

								</p>

								</li>
							</ul>
							<%--<div class="div-more">--%>
								<%--<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>--%>
							<%--</div>--%>
						</li>
						<li class="clearfix">
							<p class="p-title dp-p">离职管理</p>
							<p class="p-height">
								工作交接   手续办理   增值服务   黑名单
							</p>
							<%--<div class="div-more">--%>
								<%--<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>--%>
							<%--</div>--%>
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
            $(function(){
                //计算中间区域宽度
                $(".p-height").each(function(){
                    var pWth=$(".pc-box").width()-$(this).prev().width()-60;
                    $(this).width(pWth+"px")
                })


                //计算列表高度
                $(".p-height").each(function(){

                    var pHei=$(this).parent().outerHeight()-51;
                    $(this).parent().find(".p-title").css('line-height',pHei/2+50+"px");

                    $(this).parent().find(".dp-p").css('line-height',pHei/2+20+"px");
                })
                $(".ul-con2").each(function(){
                    var pWth=$(".pc-box").width()-$(this).prev().width()-60;
                    $(this).width(pWth+"px")
                })

                $(".p-height2").each(function(){
                    var pHei=$(this).parent().outerHeight()-51;
                    $(this).parent().find(".p-title2").css('line-height',pHei/2+50+"px");
                    $(this).parent().parent().parent().find(".p-title1").css('line-height',pHei+150+"px");
                    var pWth=$(".pc-box").width()-$(this).prev().width()-150;
                    $(this).width(pWth+"px")
                })
                //判断页面是否有底部导航
                if($("*").is(".div-bottom")){
                    $(".container").addClass("pc-bottom");
                }

			})

		</script>
	</body>
</html>
