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
		<title>公司部门人事2</title>
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
						<li><span class="folder">部门在职员工</span>
							<ul>
								<li><span class="folder">部门入职管理</span>
									<ul>
										<li><span class="folder">部门入职登记管理</span>
											<ul>
												<li><a target="mainframe" href='<%=basePath%>ea/corganization/ea_getCompanyMessage.jspa?type=t&mold=m'><span class="file">人员分配管理</span></a></li>
												<li><span class="file">人员分配报表</span></li>
												<li><span class="file">移交帐号管理</span></li>
												<li><span class="file">职责管理</span></li>
											</ul>
										</li>
										<li><span class="file">部门岗位分配变动</span></li>
										<li><span class="file">部门工种类别管理</span></li>
										<li><a target="mainframe" href='<%=basePath%>ea/cosincumbent/ea_getStaffList.jspa?'><span class="file">部门员工汇总</span></a></li>
										<li><span class="folder">部门人事基本信息</span>
											<ul>
												<li><span class="folder">员工信息汇总报表</span>
													<ul>
														<li><a target="mainframe" href='<%=basePath%>/ea/statements/ea_getStaffStatements.jspa'><span class="file">学历统计表</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>/ea/statements/ea_getStaffStatementsAge.jspa'><span class="file">年龄统计表</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumRoster.jspa?basicInfo=职工名册'><span class="file">职工名册</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumAddress.jspa?basicInfo=地址管理'><span class="file">地址汇总</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumContact.jspa?basicInfo=联系方式'><span class="file">联系方式</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumEducation.jspa?basicInfo=学历学位'><span class="file">学位学历</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumResume.jspa?basicInfo=个人履历'><span class="file">个人履历</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumFamilyMember.jspa?basicInfo=家庭成员'><span class="file">家庭成员</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumPhysicalCondition.jspa?basicInfo=健康状况'><span class="file">健康状况</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumPoliticalStatus.jspa?basicInfo=政治面貌'><span class="file">政治面貌</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumEncourage.jspa?basicInfo=奖励汇总'><span class="file">奖励汇总</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumPunishment.jspa?basicInfo=处分汇总'><span class="file">处分汇总</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumInvestigation.jspa?basicInfo=调查汇总'><span class="file">调查汇总</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumCertificate.jspa?basicInfo=证件汇总'><span class="file">证件汇总</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumDocumentation.jspa?basicInfo=资料汇总'><span class="file">资料汇总</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumPersonalFile.jspa?basicInfo=人事档案'><span class="file">人事档案</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumBankAccount.jspa?basicInfo=银行帐号'><span class="file">银行帐号</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/suminfo/ea_getSumAgreement.jspa?basicInfo=合同管理'><span class="file">合同管理</span></a></li>
													</ul>
												</li>
												<li><span class="folder">资料管理</span>
													<ul>
														<li><a target="mainframe" href='<%=basePath%>ea/personalarchive/ea_getListPersonalInfo.jspa?type=in'><span class="file">员工资料入库</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/personalarchive/ea_getListPersonalInfo.jspa?type=out'><span class="file">员工资料出库</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/personalarchive/ea_getListPersonalInfo.jspa?type=archive'><span class="file">员工资料库存</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/personalarchive/ea_getPArchiveSheetList.jspa'><span class="file">员工资料单据</span></a></li>
													</ul>
												</li>
											</ul>
										</li>
									</ul>
								</li>
								<li><span class="folder">部门培训职业规划管理</span>
									<ul>
										<li><span class="file">部门职业生涯规划</span></li>
										<li><span class="folder">部门入职培训</span>
											<ul>
												<li><span class="file">入职培训项目</span></li>
												<li><span class="file">入职培训教案</span></li>
												<li><span class="file">入职培训教师</span></li>
												<li><span class="file">入职培训学员</span></li>
												<li><span class="file">入职培训设备</span></li>
												<li><span class="file">入职培训进度</span></li>
											</ul>
										</li>
										<li><span class="folder">部门入职考试管理</span>
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
										<li><span class="folder">公司在职培训</span>
											<ul>
												<li><span class="file">在职培训项目</span></li>
												<li><span class="file">在职培训教案</span></li>
												<li><span class="file">在职培训教师</span></li>
												<li><span class="file">在职培训学员</span></li>
												<li><span class="file">在职培训设备</span></li>
												<li><span class="file">在职培训进度</span></li>
											</ul>
										</li>
										<li><span class="folder">公司在职考试管理</span>
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
								<li><span class="folder">部门劳资管理</span>
									<ul>
										<li><span class="file">部门保险管理</span></li>
										<li><span class="file">部门福利管理</span></li>
										<li><span class="folder">部门人事合同</span>
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
										<li><span class="file">部门劳资纠纷管理</span></li>
									</ul>
								</li>
								<li><span class="folder">部门工资薪级管理</span>
									<ul>
										<li><a target="mainframe" href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=cg"><span class="file">部门项目计划预算</span></a></li>
										<li><a target="mainframe" href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=cg&fgtype=00"><span class="file">部门项目确立目标</span></a></li>
										<li><a target="mainframe" href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=cg&fgtype=01"><span class="folder">部门项目工作进度</span></a>
											<ul>
												<li><a target="mainframe" href='<%=basePath%>/ea/logbooksummary/ea_getListLogBook.jspa?aa=aa'><span class="file">日志汇总</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/jobplan/ea_getJobPlanListSummary.jspa?aa=aa'><span class="file">计划汇总</span></a></li>
												<li><a target="_blank" href='<%=basePath%>/ea/jobtask/s_ea_getTaskTrack.jspa?summarytype=dept'><span class="file">任务汇总</span></a></li>
											</ul>
										</li>
										<li><span class="folder">部门工作质量考评</span>
											<ul>
												<li><a target="mainframe" href='<%=basePath%>ea/cofipunish/ea_findItem.jspa?contype=00'><span class="file">部门基本工资</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>ea/cofipunish/ea_findItem.jspa?contype=01'><span class="file">部门职务工资</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>ea/cofipunish/ea_findItem.jspa?contype=03'><span class="file">部门考评工资</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>ea/cofipunish/ea_findItem.jspa?contype=02'><span class="file">部门考勤工资</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>ea/cofjjm/ea_findItem.jspa?'><span class="file">部门计件工资</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>ea/cofipunish/ea_findItem.jspa?contype=04'><span class="file">部门奖惩工资</span></a></li>
												<li><span class="file">部门级差工资</span></li>
											</ul>
										</li>
										<li><span class="file">部门工资查询报表</span></li>
									</ul>
								</li>
								<li><span class="folder">部门离职交接管理</span>
									<ul>
										<li><span class="file">部门岗位责任变动</span></li>
										<li><span class="file">部门物品移交管理</span></li>
										<li><span class="file">部门财务移交管理</span></li>
										<li><span class="file">部门资料移交管理</span></li>
										<li><span class="file">部门移交报表</span></li>
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
