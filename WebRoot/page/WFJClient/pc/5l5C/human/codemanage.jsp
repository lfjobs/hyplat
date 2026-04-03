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
		<title>代码管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/codemanage.css">
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			var companyName="${param.companyName}";
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
							代码管理
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="container">
					<ul class="ul-con">
						<a href="<%=basePath%>page/WFJClient/pc/5l5C/office/inventory/depotManageMobile.jsp?companyName=${param.companyName}">
						<li class="clearfix">
							<p class="p-title">库房管理</p>
							 <p class="p-height clearfix">
							
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						</a>
						<li class="clearfix">
							<p class="p-title">大数据库管理</p>
							<p class="p-height">
								大数据管理   人事大数据   办公室大数据   财务大数据 生产大数据   营销大数据
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">编程代码管理</p>
							<p class="p-height">
								编程代码管理   人事编程代码   办公室编程代码<br />财务编程代码   生产编程代码   营销编程代码
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">代码元素管理</p>
							<p class="p-height">
								代码元素管理   人事代码元素   办公室代码元素<br />财务代码元素   生产代码元素   营销代码元素<br />接口管理代码元素
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">物品元素管理</p>
							<p class="p-height">
								物品元素管理   人事物品元素   办公室物品元素<br />财务物品元素   生产物品元素   营销物品元素
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">项目元素管理</p>
							<p class="p-height">
								项目元素管理   人事项目元素   办公室项目元素<br />财务项目元素   生产项目元素   营销项目元素
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">菜单导航管理</p>
							<p class="p-height">
								菜单导航管理   人事菜单导航   办公室菜单导航<br />财务菜单导航   生产菜单导航   营销菜单导航
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">权限密码管理</p>
							<p class="p-height">
								权限密码管理   人事权限密码   办公室权限密码<br />财务权限密码   生产权限密码   营销权限密码
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<!--<li class="clearfix">
							<p class="p-title">销售报表</p>
							<p class="p-height">
								毛利润报表  商品销售成本报表   销售 订单报表  销售收入报表毛利润报表  商品销售成本报表
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>-->
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
			//计算列表高度
			$(".p-height").each(function(){
//				console.log($(this).outerHeight())
//				console.log($(this).parent().outerHeight())
				var pHei=$(this).parent().outerHeight()-51;
				$(this).parent().find(".p-title").css('line-height',pHei+"px");
				$(this).parent().find(".div-more").css('line-height',pHei+50+"px");
			})
			//判断页面是否有底部导航
			if($("*").is(".div-bottom")){
				$(".container").addClass("pc-bottom");
			}
		</script>
	</body>
</html>
