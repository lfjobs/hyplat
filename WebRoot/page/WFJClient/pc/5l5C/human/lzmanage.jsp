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
		<title>部门离职员工</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	   <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/lzmanage.css">
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
							离职员工
						</li>
						<li>
							
						</li>
					</ul>
				</header>
				<div class="container">
					<ul class="ul-con">
						<li class="clearfix">
							<p class="p-title">入职培训</p>
							<p class="p-height clearfix">
								职业生涯规划   入职培训项目   入职培训教案   入职培训教师  入职培训学员   入职培训设备   入职培训进度
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">入职考试管理</p>
							<p class="p-height">
								入职考试题库   入职考试标准   入职考官资格认定<br />
								入职考试管理   入职考试结果   入职考试排行<br />
								入职奖惩管理
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">在职培训</p>
							<p class="p-height">
								在职培训项目   在职培训教案   在职培训教师<br />在职培训学员   在职培训设备   在职培训进度
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">在职考试管理</p>
							<p class="p-height">
								在职考试题库   在职考试标准   在职考官资格认定<br />
								在职考试管理   在职考试结果   在职考试排行<br />
								在职奖惩管理
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">离职员工在职管理</p>
							<p class="p-height">
								保险管理   福利管理   劳资纠纷管理   实习协议<br />
								培训协议   劳务协议   保密协议   竟业限制协议<br />
								岗位协议   安全协议管理
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">离职员工交接管理</p>
							<p class="p-height">
								入职登记管理   岗位分配变动   工种类别管理 员工汇总   人事报表
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">基本信息管理</p>
							<p class="p-height">
								员工汇总   职责汇总   计划汇总   任务汇总   考评汇总 信息分类统计   人事报表   报表传输
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">离职管理</p>
							<p class="p-height">
								离职变动管理   离职员工登记表   离职员工报表管理<br />
								培训补助管理   试用期员工   正式员工   临时员工<br />
								中介人员   实习协议   培训协议   劳务协议   保密协议<br />
								竟业限制协议   岗位协议   安全协议管理   资料移交管理<br />
								岗位职责变动管理   物品移交管理   财务移交管理   移交报表
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">离职员工再服务管理</p>
							<p class="p-height">
								项目计划预算   项目确立目标   项目工作进度 工作质量考评
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">工资查询报表</p>
							<p class="p-height">
								岗位能力薪酬级设定   级差级别报表设定<br />级差级别月设定   工资报表管理   工资管理
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">发放工资报表</p>
							<p class="p-height">
								试用期员工工资报表   正式员工工资报表<br />
								临时员工工资报表   中介人员工资报表<br />
								离职人员工资报表   正式员工工资报表<br />
								正式员工工资报表   正式员工工资报表
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">相应工资报表</p>
							<p class="p-height">
								考勤报表   安全奖报表   计件报表   本日任务报表<br />
								本周任务报表   本月任务报表   本季任务报表<br />
								本年任务报表   采购误差报表   设备维修报表   油耗报表
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">离职员工增值管理</p>
							<p class="p-height">
								岗位责任变动   物品移交管理   财务移交管理 资料移交管理   移交报表
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>
						<li class="clearfix">
							<p class="p-title">非正常离职员工</p>
							<p class="p-height">
								离职员工交接管理   离职员工招聘管理 离职员工在职管理   离职员工问题追诉管理
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
