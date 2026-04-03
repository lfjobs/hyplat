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
		<title>行政建设管理</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/xzbuild.css">
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

		<script type="text/javascript">
			var  basePath = "<%=basePath%>";

			function flow(module){
				document.location.href = basePath+"ea/documentcommon/ea_showDocumentModule.jspa?module="+module+"&d=<%=Math.random()%>&bb=new";
			//	document.location.href = basePath+"ea/contract/ea_getMyFileList.jspa?module="+module;
				<%--var u = navigator.userAgent;--%>
				<%--var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端--%>
				<%--var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端--%>


				<%--if(isAndroid==true||isiOS==true){--%>
					<%--document.location.href = basePath+"ea/contract/ea_getMyFileList.jspa?module="+module;--%>
				<%--}else{--%>
					<%--window.open(basePath+"ea/documentcommon/ea_showDocumentModule.jspa?module="+module+"&d=<%=Math.random()%>");--%>
				<%--}--%>
			}



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
							行政建设管理
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-title">办公文书</p>
							<p class="p-height clearfix">
								<a onclick="flow('doc')">文书流程</a>
								<a onclick="flow('contract')">合同签约</a>
								<a href="">通知</a>
								<a href="">通报</a>
								<a href="">请示</a>
								<a href="">批复</a>
								<a href="">书函</a>
								<a href="<%=basePath%>ea/enterprisestamp/ea_getStampList.jspa">印章</a>
								<a href="">章程</a>
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">行政文书</p>
							<p class="p-height">
								工资级别升降级   人事调令管理 通知单管理  派车管理  奖罚管理  金币折扣单
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">加班管理</p>
							<p class="p-height">
								加班申请   请假管理   出差管理
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title"> 商务文书</p>
							<p class="p-height">
								活动策划   调查报告   可行性分析   计划书  招标书  投标书
								商务书函   商务法律   仲裁诉讼 索赔理赔  授权委托
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>

						
					<li class="clearfix">
						<p class="p-title">礼仪文书</p>
						<p class="p-height">欢迎词 欢送词 答谢词 祝贺词 请柬词 邀请信 感谢信 慰问信 介绍信 证明信 推荐信</p>
						<div class="div-more">
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png" />
						</div></li>



					<li class="clearfix">
						<p class="p-title">演讲文书</p>
						<p class="p-height">竞聘演讲 就职演讲 即兴演讲  发言稿 开幕词 闭幕词</p>
						<div class="div-more">
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png" />
						</div>
					</li>

					<li class="clearfix">
						<p class="p-title">法规文书</p>
						<p class="p-height">投诉管理 制度管理 内部纠纷 外部纠纷 国家法规 行业法规 规则管理</p>
						<div class="div-more">
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png" />
						</div>
					</li>


					<li class="clearfix">
						<p class="p-title">现场会议(会议管理)</p>
						<p class="p-height">现场会议(会议管理) 会务机构人员配备 会议准备阶段 正式会议阶段 会议闭幕阶段
							视频会议 会议记录 会议室预约管理 员工会议管理</p>
						<div class="div-more">
							<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png" />
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
