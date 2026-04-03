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
		<title>生产管理系统</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
		<link rel="stylesheet" type="text/css" href="css/生产管理系统.css">
		<link rel="stylesheet" href="css/l_zoom.css">
		<link rel="stylesheet" type="text/css" href="package/css/swiper.min.css"/>
		<script src="js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body id="">
		<div class="pc-box">
			<div class="div-box">
				<div class="div-header">
					5L5C管理系统
				</div>
				<ul class="ul-header clearfix">
					<li>
						<img src="img/return.png"/>
					</li>
					<li>
						生产管理系统
					</li>
					<li>
						
					</li>
				</ul>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-title">生产设计管理</p>
							<p class="p-height">
								生产物品设计  生产产品设计  项目产 品设计  产品生产量预算
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">模拟测试管理</p>
							<p class="p-height">
								模拟测试  模拟测试合格  模拟测试不合格
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">生产过程管理</p>
							<p class="p-height">
								生产订单  生产计划  生产分配  生产采购 批量生产  生产跟踪
							</p>
							<div class="div-more">
								<img src="img/more.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">考核检验管理</p>
							<p class="p-height">
								考核检验  考核检验合格  考核检验不 合格  合格率 
							</p>
						</li>
						<li class="clearfix">
							<p class="p-title">合格成品管理</p>
							<p class="p-height">
								产品入库  产品出库
							</p>
						</li>
						<!--<li class="clearfix">
							<p class="p-title">销售报表</p>
							<p class="p-height">
								毛利润报表  商品销售成本报表   销售 订单报表  销售收入报表毛利润报表  商品销售成本报表
							</p>
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
			//计算列表高度
			$(".p-height").each(function(){
				$(this).parent().find(".p-title").css('line-height',$(this).height()+"px");
				$(this).parent().find(".div-more").css('line-height',$(this).height()+"px");
			})
		</script>
	</body>
</html>
