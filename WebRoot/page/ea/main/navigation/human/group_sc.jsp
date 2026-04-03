<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>集团人事生产</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<script type="text/javascript">
   
             var basePath='<%=basePath%>';  
             
             $(document).ready(function(){
            	 
            	 $("#navigation").treeview({
					 persist: "location",
				      collapsed: true,
				      unique: true
				}); 

            	});
  
        </script>
		<style type="text/css">
#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}

.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
}


</style>
	</head>
	<body>
		<div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
				<td id="qh_sw" style="width: 15%" valign="top">
					<div class="qh_gg_nav">
						&nbsp; <span id="frametitle">集团人事管理</span>
					</div>
					<ul id="navigation" style="width: 180px;height:300px;" class="filetree">
						<li><span class="folder">集团人事机构</span></li>
						<li><span class="folder">集团招聘管理</span></li>
						<li><span class="folder">集团在职员工管理</span>
							<ul>
								<li><span class="folder">集团入职管理</span>
									<ul>
										<li><span class="file">集团入职登记管理</span></li>
										<li><span class="file">集团岗位分配变动</span></li>
										<li><span class="file">集团工种类别管理</span></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/staffcoscompany/ea_getStaffCosList.jspa?'><span class="file">集团员工汇总</span></a></li>
										<li><span class="folder">集团人事报表</span>
											<ul>
												<li><span class="folder">员工信息汇总报表</span>
													<ul>
														<li><span class="file">学历统计表</span></li>
														<li><span class="file">年龄统计表</span></li>
														<li><span class="file">职工名册</span></li>
														<li><span class="file">地址汇总</span></li>
														<li><span class="file">联系方式</span></li>
														<li><span class="file">学位学历</span></li>
														<li><span class="file">个人履历</span></li>
														<li><span class="file">家庭成员</span></li>
														<li><span class="file">健康状况</span></li>
														<li><span class="file">政治面貌</span></li>
														<li><span class="file">奖励汇总</span></li>
														<li><span class="file">处分汇总</span></li>
														<li><span class="file">调查汇总</span></li>
														<li><span class="file">证件汇总</span></li>
														<li><span class="file">资料汇总</span></li>
														<li><span class="file">人事档案</span></li>
														<li><span class="file">银行帐号</span></li>
													</ul>
												</li>
												<li><span class="folder">资料管理</span>
													<ul>
														<li><span class="file">员工资料入库</span></li>
														<li><span class="file">员工资料出库</span></li>
														<li><span class="file">员工资料库存</span></li>
														<li><span class="file">员工资料单据</span></li>
													</ul>
												</li>
											</ul>
										</li>
									</ul>
								</li>
								<li><span class="folder">集团培训职业规划管理</span>
									<ul>
										<li><span class="file">集团职业生涯规划</span></li>
										<li><span class="file">集团入职培训</span></li>
										<li><span class="file">集团入职考试管理</span></li>
										<li><span class="file">集团在职培训</span></li>
										<li><span class="file">集团在职考试管理</span></li>
									</ul>
								</li>
								<li><span class="folder">集团劳资管理</span>
									<ul>
										<li><span class="file">集团保险管理</span></li>
										<li><span class="file">集团福利管理</span></li>
										<li><span class="file">集团人事合同管理</span></li>
										<li><span class="file">集团劳资纠纷管理</span></li>
									</ul>
								</li>
								<li><span class="folder">集团工资目标管理</span>
									<ul>
										<li><span class="file">集团项目计划预算</span></li>
										<li><span class="file">集团项目确立目标</span></li>
										<li><span class="folder">集团项目工作进度</span>
											<ul>
												<li><span class="file">劳动关系汇总</span></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/logbookcompany/ea_getLogBookList.jspa?'><span class="file">日志汇总</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/jobplancompany/ea_getJobPlanList.jspa?'><span class="file">计划汇总</span></a></li>
												<li><a target="_blank" href='<%=basePath%>/ea/jobtask/s_ea_getTaskTrack.jspa?summarytype=group'><span class="file">任务汇总</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/appraisalcompany/ea_getAppraisalList.jspa?'><span class="file">综合考评汇总</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/payscaleacompany/ea_getPayScaleaList.jspa?'><span class="file">薪级汇总</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/payscalecompany/ea_getPayScaleList.jspa?'><span class="file">工资级别汇总</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/page/ea/main/human/office/company/SalaryIntergral_search.jsp?result=<%=session.getAttribute("organizationID")%>'><span class="file">工资汇总</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/gamjeomscompany/ea_getGamJeomsList.jspa?'><span class="file">奖惩汇总</span></a></li>
											</ul>
										</li>
										<li><span class="file">集团工作质量考评</span></li>
										<li><span class="file">集团工资查询报表</span></li>
									</ul>
								</li>
								<li><span class="folder">集团离职交接管理</span>
									<ul>
										<li><span class="file">集团岗位责任变动</span></li>
										<li><span class="file">集团物品移交管理</span></li>
										<li><span class="file">集团财务移交管理</span></li>
										<li><span class="file">集团资料移交管理</span></li>
										<li><span class="file">集团移交报表</span></li>
									</ul>
								</li>
							</ul>
						</li>
						<li><span class="folder">集团离职员工管理</span>
							<ul>
								<li><a target="mainframe" href='<%=basePath%>/ea/staffdimissioncompany/ea_getStaffDimissionList.jspa?'><span class="file">员工汇总</span></a></li>
								<li><a target="mainframe" href='<%=basePath%>/page/ea/main/company/human/staffdimpaysearch_company.jsp'><span class="file">工资查询</span></a></li>
							</ul>
						</li>
						<li><span class="folder">集团社会人力资源管理</span></li>
					</ul>
				</td>
				<td style="width: 85%;" valign="top"><iframe id="mainframe" name="mainframe"
						    src=""
							frameborder="0" style="width: 100%;height:100%"></iframe></td>

			</tr>
			</table>
		</div>
		<script type="text/javascript">
			$(function() {
				$(window).resize(function() {
					setTimeout(function() {
						$("#navigation").height($(window).height() - 30);
						// $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
					}, 100);
				});
				$("#navigation").height($(window).height() - 30);
				$("#mainframe").css({
					"height" : $(window).height() + "px"
				});
			});
		</script>
	</body>


</html>
