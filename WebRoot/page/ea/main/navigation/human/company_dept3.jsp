<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公司部门人事3</title>
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
						&nbsp; <span id="frametitle">部门人事管理</span>
					</div>
					<ul id="navigation" style="width: 180px;height:300px;" class="filetree">
						<li><span class="folder">部门离职员工</span>
							<ul>
								<li><span class="folder">正常离职员工</span>
									<ul>
										<li><span class="folder">离职员工招聘管理</span>
											<ul>
												<li><span class="file">职业生涯规划</span></li>
												<li><span class="folder">入职培训</span>
													<ul>
														<li><span class="file">入职培训项目</span></li>
														<li><span class="file">入职培训教案</span></li>
														<li><span class="file">入职培训教师</span></li>
														<li><span class="file">入职培训学员</span></li>
														<li><span class="file">入职培训设备</span></li>
														<li><span class="file">入职培训进度</span></li>
													</ul>
												</li>
												<li><span class="folder">入职考试管理</span>
													<ul>
														<li><span class="file">入职考试题库</span></li>
														<li><span class="file">入职考试标准</span></li>
														<li><span class="file">入职考官资格认定</span></li>
														<li><span class="file">入职考试管理</span></li>
														<li><span class="file">入职考试结果</span></li>
														<li><span class="file">入职考试排行</span></li>
														<li><span class="file">入职奖惩管理</span></li>
													</ul>
												</li>
												<li><span class="folder">在职培训</span>
													<ul>
														<li><span class="file">在职培训项目</span></li>
														<li><span class="file">在职培训教案</span></li>
														<li><span class="file">在职培训教师</span></li>
														<li><span class="file">在职培训学员</span></li>
														<li><span class="file">在职培训设备</span></li>
														<li><span class="file">在职培训进度</span></li>
													</ul>
												</li>
												<li><span class="folder">在职考试管理</span>
													<ul>
														<li><span class="file">在职考试题库</span></li>
														<li><span class="file">在职考试标准</span></li>
														<li><span class="file">在职考官资格认定</span></li>
														<li><span class="file">在职考试管理</span></li>
														<li><span class="file">在职考试结果</span></li>
														<li><span class="file">在职考试排行</span></li>
														<li><span class="file">在职奖惩管理</span></li>
													</ul>
												</li>
											</ul>
										</li>
										<li><span class="folder">离职员工在职管理</span>
											<ul>
												<li><span class="file">保险管理</span></li>
												<li><span class="file">福利管理</span></li>
												<li><span class="folder">人事合同管理</span>
													<ul>
														<li><span class="file">实习协议</span></li>
														<li><span class="file">培训协议</span></li>
														<li><span class="file">劳务协议</span></li>
														<li><span class="file">保密协议</span></li>
														<li><span class="file">竟业限制协议</span></li>
														<li><span class="file">岗位协议</span></li>
														<li><span class="file">安全协议管理</span></li>
													</ul>
												</li>
												<li><span class="file">劳资纠纷管理</span></li>
											</ul>
										</li>
										<li><span class="folder">离职员工交接管理</span>
											<ul>
												<li><span class="file">入职登记管理</span></li>
												<li><span class="file">岗位分配变动</span></li>
												<li><span class="files">工种类别管理</span></li>
												<li><span class="folder">员工汇总</span>
													<ul>
														<li><span class="folder">基本信息管理</span>
															<ul>
																<li><a target="mainframe" href='<%=basePath%>ea/cosdimission/ea_getListCOSDimission.jspa'><span class="file">员工汇总</span></a></li>
																<li><a target="mainframe" href='<%=basePath%>/ea/departmentpost/ea_getDepartmentPostList.jspa'><span class="file">职责汇总</span></a></li>
																<li><span class="file">计划汇总</span></li>
																<li><span class="file">任务汇总</span></li>
																<li><span class="file">考评汇总</span></li>
																<li><span class="file">信息分类统计</span></li>
																<li><span class="file">人事报表</span></li>
																<li><span class="file">报表传输</span></li>
															</ul>
														</li>
														<li><span class="folder">离职管理</span>
															<ul>
																<li><span class="file">离职变动管理</span></li>
																<li><span class="folder">工种类别管理</span>
																	<ul>
																		<li><span class="file">培训补助管理</span></li>
																		<li><span class="file">试用期员工</span></li>
																		<li><span class="file">正式员工</span></li>
																		<li><span class="file">临时员工</span></li>
																		<li><span class="file">中介人员</span></li>
																	</ul>
																</li>
																<li><span class="folder">人事合同管理</span>
																	<ul>
																		<li><span class="file">实习协议</span></li>
																		<li><span class="file">培训协议</span></li>
																		<li><span class="file">劳务协议</span></li>
																		<li><span class="file">保密协议</span></li>
																		<li><span class="file">竟业限制协议</span></li>
																		<li><span class="file">岗位协议</span></li>
																		<li><span class="file">安全协议管理</span></li>
																	</ul>
																</li>
																<li><span class="folder">离职移交管理</span>
																	<ul>
																		<li><span class="file">岗位职责变动管理</span></li>
																		<li><span class="file">物品移交管理</span></li>
																		<li><span class="file">财务移交管理</span></li>
																		<li><span class="file">资料移交管理</span></li>
																		<li><span class="file">移交报表</span></li>
																	</ul>
																</li>
															</ul>
														</li>
														<li><span class="file">离职员工登记表</span></li>
														<li><span class="file">离职员工报表管理</span></li>
													</ul>
												</li>
												<li><span class="file">人事报表</span></li>
											</ul>
										</li>
										<li><span class="folder">离职员工再服务管理</span>
											<ul>
												<li><span class="file">项目计划预算</span></li>
												<li><span class="file">项目确立目标</span></li>
												<li><span class="file">项目工作进度</span></li>
												<li><span class="file">工作质量考评</span></li>
												<li><span class="folder">工资查询报表</span>
													<ul>
														<li><a target="mainframe" href='<%=basePath%>/page/ea/main/navigation/salary_manage_a.jsp?'><span class="file">岗位能力薪酬级设定</span></a></li>
														<li><span class="file">级差级别报表设定</span></li>
														<li><a target="mainframe" href='<%=basePath%>ea/payscale/ea_getListPayScale.jspa'><span class="file">级差级别月设定 </span></a></li>
														<li><span class="folder">工资报表管理</span>
															<ul>
																<li><span class="folder">发放工资报表</span>
																	<ul>
																		<li><span class="file">试用期员工工资报表</span></li>
																		<li><span class="file">正式员工工资报表</span></li>
																		<li><span class="file">临时员工工资报表</span></li>
																		<li><span class="file">中介人员工资报表</span></li>
																		<li><span class="file">离职人员工资报表</span></li>
																		<li><span class="file">正式员工工资报表</span></li>
																		<li><span class="file">正式员工工资报表</span></li>
																		<li><span class="file">正式员工工资报表</span></li>
																	</ul>
																</li>
																<li><span class="folder">相应工资报表</span>
																	<ul>
																		<li><span class="file">考勤报表</span></li>
																		<li><span class="file">安全奖报表</span></li>
																		<li><span class="file">计件报表</span></li>
																		<li><span class="file">本日任务报表</span></li>
																		<li><span class="file">本周任务报表</span></li>
																		<li><span class="file">本月任务报表</span></li>
																		<li><span class="file">本季任务报表</span></li>
																		<li><span class="file">本年任务报表</span></li>
																		<li><span class="file">采购误差报表</span></li>
																		<li><span class="file">设备维修报表</span></li>
																		<li><span class="file">油耗报表</span></li>
																	</ul>
																</li>
															</ul>
														</li>
														<li><a target="mainframe" href='<%=basePath%>/page/ea/main/human/office/production/SalaryIntergral_search.jsp'><span class="file">工资管理</span></a></li>
													</ul>
												</li>
											</ul>
										</li>
										<li><span class="folder">离职员工增值管理</span>
											<ul>
												<li><span class="file">岗位责任变动</span></li>
												<li><span class="file">物品移交管理</span></li>
												<li><span class="file">财务移交管理 </span></li>
												<li><span class="file">资料移交管理</span></li>
												<li><span class="file">移交报表</span></li>
											</ul>
										</li>
									</ul>
								</li>
								<li><span class="folder">非正常离职员工</span>
									<ul>
										<li><span class="file">离职员工交接管理</span></li>
										<li><span class="file">离职员工招聘管理</span></li>
										<li><span class="file">离职员工在职管理</span></li>
										<li><span class="file">离职员工问题追诉管理</span></li>
									</ul>
								</li>
							</ul>
						</li>
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
