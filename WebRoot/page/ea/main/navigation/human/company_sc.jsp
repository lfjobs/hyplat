<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公司人事生产</title>
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
						&nbsp; <span id="frametitle">公司人事管理</span>
					</div> <!--左边的树 -->
					<ul id="navigation" style="width: 180px;height:300px;" class="filetree">
						<li><span class="folder">公司机构</span>
							<ul>
								<li><span class="folder">机构管理</span>
									<ul>
										<li><a target="mainframe" href="<%=basePath%>ea/corganization/ea_getCompanyMessage.jspa?type=p&mold=m"><span class="file">机构设置</span> </a>
										</li>
										<li><a target="mainframe"  href="<%=basePath%>page/ea/main/human/organization/orgchart.jsp?comy=comy"><span class="file">组织机构图</span> </a>
										</li>
										<li><a target="mainframe"  href="<%=basePath%>ea/roster/ea_getRosterAll.jspa"><span class="file">花名册</span> </a>
										</li>
										<li><a target="mainframe"  href="<%=basePath%>page/ea/main/human/organization/orgcharall.jsp?comy=comy"><span class="file">机构图</span> </a>
										</li>
									</ul>
								</li>
								<li><span class="folder">部门职能描述</span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>/ea/departmentpost/ea_getDepartmentPostList.jspa' ><span class="file">职责汇总</span></a></li>
										<li><span class="file">部门职能战区图</span></li>
										<li><span class="file">部门工作流程</span></li>
										<li><span class="file">部门工作量化成果指标管理</span></li>
										<li><span class="file">部门职能要求</span></li>
									</ul></li>

								<li><span class="folder">岗位设置管理</span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>/ea/corganization/ea_getCompanyMessage.jspa?type=e&mold=o'><span class="file"> 岗位说明书及要求</span></a></li>
										<li><span class="file">岗位战区图</span></li>
										<li><span class="file">岗位工作流程</span></li>
										<li><span class="file">岗位工作量化成果指标管理</span></li>
										<li><span class="file">岗位安全责任书</span></li>
										<li><span class="file">岗位授权管理</span></li>
									</ul>
								</li>
								<li><span class="folder">人员配备管理</span>
									<ul>
										<li><a target="mainframe"  href='<%=basePath%>/ea/corganization/ea_getCompanyMessage.jspa?type=e&mold=m'><span class="file">微型企业人员配备</span></a></li>
										<li><span class="file">小型企业人员配备</span></li>
										<li><span class="file">中型企业人员配备</span></li>
										<li><span class="file">大型企业人员配备</span></li>
										<li><span class="file">集团人员配备</span></li>
									</ul>
								</li>
								<li><span class="folder">机构资料管理</span>
									<ul>
										<li><span class="file">机构资料入库</span></li>
										<li><span class="file">机构资料出库</span></li>
										<li><span class="file">机构资料库存</span></li>
									</ul>
								</li>

							</ul>
						</li>
						<li><span class="folder">公司招聘管理</span>
							<ul>
								<li><span class="folder">公司招聘规划</span>
									<ul>
										<li><span class="folder">公司招聘岗位规划</span>
											<ul>
												<li><a target="mainframe" href='<%=basePath%>ea/recruitment/ea_getRecruitmentList.jspa'><span class="file">岗位统记</span></a></li>
												<li><span class="file">岗位编制</span></li>
												<li><span class="file">个人规划管理</span></li>
											</ul>
										</li>
										<li><span class="folder">公司薪酬规划</span>
											<ul>
												<li><span class="file">招聘薪酬调查</span></li>
												<li><span class="file">薪酬极差设定</span></li>
											</ul>
										</li>
										<li><span class="folder">公司人力投资规划</span>
											<ul>
												<li><span class="file">人力成本图</span></li>
												<li><span class="file">人力成本收入百分比</span></li>
											</ul>
										</li>
									</ul>
								</li>
								<li><span class="folder">公司招聘宣传</span>
									<ul>
										<li><span class="folder">公司招聘资料</span>
											<ul>
												<li><span class="file">文字设计</span></li>
												<li><span class="file">图片设计</span></li>
												<li><span class="file">视频设计</span></li>
											</ul>
										</li>
										<li><span class="folder">公司宣传方式</span>
											<ul>
												<li><span class="file">招聘薪酬调查</span></li>
												<li><span class="file">薪酬极差设定</span></li>
											</ul>
										</li>
									</ul>
								</li>

								<li><span class="folder">公司招聘登记管理</span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>/ea/tresume/ea_getListPage.jspa?aa=aa&cstaff.status=99'><span class="file">人才简历库</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?aa=aa&cstaff.status=99'><span class="file">公司社会人力</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=3'><span class="file">公司招聘登记管理</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=0'><span class="file">公司选择通知面试</span></a></li>
									</ul>
								</li>
								<li><span class="folder">公司招聘面试</span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=1'><span class="file">公司面试登记管理</span></a></li>
										<li><span class="folder">公司面试考试结果</span>
											<ul>
												<li><span class="file">面试老师管理</span></li>
												<li><span class="file">面试题库管理</span></li>
												<li><span class="file">面试考试管理</span></li>
												<li><span class="file">面试结果分数管理</span></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/saudition/ea_getAuditionkb.jspa?start=3'><span class="file">面试结果管理</span></a></li>
											</ul>
										</li>
										<li><span class="folder">公司入职管理</span>
											<ul>
												<li><span class="folder">参观期管理</span>
													<ul>
														<li><span class="file">参观人员登记</span></li>
														<li><span class="file">上传参观人员</span></li>
														<li><span class="file">打印参观登记</span></li>
														<li><span class="file">签订参观协议</span></li>
													</ul>
												</li>
												<li><span class="folder">培训期管理</span>
													<ul>
													<li><span class="file">培训人员登记</span></li>
													<li><span class="file">上传培训人员</span></li>
													<li><span class="file">打印培训登记</span></li>
													<li><span class="file">签订培训协议</span></li>
													</ul>
												</li>
												<li><span class="folder">正式入职管理</span>
													<ul>
													<li><a target="mainframe" href='<%=basePath%>ea/corganization/ea_getCompanyMessage.jspa?type=e&mold=m'><span class="file">正式入职人员</span></a></li>
													<li><span class="file">上传正式人员</span></li>
													<li><span class="file">打印正式登记</span></li>
													<li><span class="file">正式入职协议</span></li>
													</ul>
												</li>
											</ul>
										</li>
									</ul>
								</li>
							</ul>
						</li>
						<li><span class="folder">公司在职员工</span>
							<ul>
								<li><span class="folder">公司入职管理</span>
									<ul>
										<li><span class="folder">公司入职登记管理</span>
											<ul>
												<li><a target="mainframe" href='<%=basePath%>ea/corganization/ea_getCompanyMessage.jspa?type=e&mold=m'><span class="file">人员分配管理</span></a></li>
												<li><span class="file">人员分配报表</span></li>
												<li><span class="file">移交帐号管理</span></li>
												<li><span class="file">职责管理</span></li>
											</ul>
										</li>
										<li><span class="file">公司岗位分配变动</span></li>
										<li><span class="file">公司工种类别管理</span></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/soincumbent/ea_getStaffListForIncumbent.jspa?aa=aa'><span class="file">公司员工汇总</span></a></li>
										<li><span class="folder">公司人事报表</span>
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
								<li><span class="folder">公司培训职业规划管理</span>
									<ul>
										<li><span class="file">公司职业生涯规划</span></li>
										<li><span class="folder">公司入职培训</span>
											<ul>
												<li><span class="file">入职培训项目</span></li>
												<li><span class="file">入职培训教案</span></li>
												<li><span class="file">入职培训教师</span></li>
												<li><span class="file">入职培训学员</span></li>
												<li><span class="file">入职培训设备</span></li>
												<li><span class="file">入职培训进度</span></li>
											</ul>
										</li>
										<li><span class="folder">公司入职考试管理</span>
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
								<li><span class="folder">公司劳资管理</span>
									<ul>
										<li><span class="file">公司保险管理</span></li>
										<li><span class="file">公司福利管理</span></li>
										<li><span class="folder">公司人事合同管理</span>
											<ul>
												<li><span class="folder">劳动关系管理</span>
													<ul>
														<li><a target="mainframe" href='<%=basePath%>/ea/archive/ea_getArchiveList.jspa?catemodule=02&type=1'><span class="file">实习协议</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>/ea/archive/ea_getArchiveList.jspa?catemodule=01&type=1'><span class="file">劳动合同</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>/ea/archive/ea_getArchiveList.jspa?catemodule=03&type=1'><span class="file">劳务协议</span></a></li>
													</ul>
												</li>
												<li><span class="folder">合同台账</span>
													<ul>
														<li><a target="mainframe" href='<%=basePath%>ea/archive/ea_getContractParamList.jspa?date=Math.random()&type=formal'><span class="file">在职员工合同台账</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>ea/archive/ea_getContractParamList.jspa?date=Math.random()&type=dimis'><span class="file">离职员工合同台账</span></a></li>
													</ul>
												</li>
												<li><a target="mainframe" href='<%=basePath%>ea/agreement/ea_getListEXC.jspa?date=Math.random()'><span class="file">合同报表</span></a></li>
											</ul>
										</li>
										<li><span class="file">公司劳资纠纷管理</span></li>
									</ul>
								</li>
								<li><span class="folder">公司工资薪级管理</span>
									<ul>
										<%-- <li><a target="mainframe" href='<%=basePath%>/ea/jobplan/ea_getjobPlanListdate.jspa?jobPlan.jobstatus=00&jobPlan.entry=待办事项'><span class="folder"> 公司项目计划预算</span></a></li>--%>
										<li><span class="folder">公司项目计划</span></a>
											<ul>
													<li><a target="mainframe" href='<%=basePath%>ea/productdesign/ea_getListProductdesign.jspa?'><span class="file">人事产品设计</span></a></li>
													<li><a target="mainframe" href='<%=basePath%>/ea/cofi/ea_findItem.jspa?codeID=scode20150105fbdz4z3zer0000000004&wstate=0&xmtype=01341'><span class="file">工资结构设置</span></a></li>
													<li><a target="mainframe" href='<%=basePath%>/ea/cofra/ea_findItem.jspa?&xmtype=013416'><span class="file">级差设置</span></a></li>										
													<li><a target="mainframe" href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=y&fgtype=00&zhuangtai=01'><span class="file">工资预算设定</span></a></li>
													</ul>
										</li>
										<li><a target="mainframe" href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=dc&fgtype=01&zhuangtai=01"><span class="folder">公司授权分配</span></a>
											
										</li>
										<li><a target="mainframe" href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=t&fgtype=02&zhuangtai=01"><span class="folder">公司项目跟踪进度</span></a>
											
										</li>
												
										<li><a target="mainframe" href='<%=basePath%>/ea/cofipunish/ea_findItem.jspa'><span class="folder">公司工作质量考评</span></a>
										
										</li>
										<li><span class="folder">公司工资查询报表</span>
											<ul>
												<li><a target="mainframe" href='<%=basePath%>/ea/payscale/ea_getListPayScale.jspa'><span class="file">薪级设置</span></a></li>
												<li><span class="folder">员工级别管理</span>
													<ul>
														<li><a target="mainframe" href='<%=basePath%>/ea/staffrankcompany/ea_getRankCompany.jspa?'><span class="file">员工级别变更</span></a></li>
														<li><a target="mainframe" href='<%=basePath%>/ea/payscale/ea_getStaffPayScaleSummaryList.jspa?'><span class="file">员工级别汇总</span></a></li>
													</ul>
												</li>
												<li><a target="mainframe" href='<%=basePath%>/page/ea/main/human/office/production/SalaryIntergral_search.jsp'><span class="file">工资管理</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/pkgoldpool/ea_getList.jspa'><span class="file">PK金汇总管理</span></a></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/payroll/ea_getThreePay.jspa'><span class="file">预算工资查询</span></a></li>
											</ul>
										</li>
									</ul>
								</li>
								<li><span class="folder">公司离职交接管理</span>
									<ul>
										<li><span class="file">公司岗位责任变动</span></li>
										<li><span class="file">公司物品移交管理</span></li>
										<li><span class="file">公司财务移交管理</span></li>
										<li><span class="file">公司资料移交管理</span></li>
										<li><span class="file">公司移交报表</span></li>
									</ul>
								</li>
							</ul>
						</li>
						<li><span class="folder">公司离职员工</span>
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
												<li><span class="file">工种类别管理</span></li>
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
						<li><span class="folder">公司社会人力 </span>
							<ul>
								<li><span class="folder">社会人力资源管理 </span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>ea/resume/ea_getList.jspa?'><span class="file">网站人力</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=99'><span class="file">社会人力 </span></a></li>
									</ul>
								</li>
								<li><span class="folder">现代人力管理 </span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=01'><span class="file">政界人物</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=02'><span class="file">商界人物 </span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=03'><span class="file">学术界人物</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=04'><span class="file">艺术界人物</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=05'><span class="file">科学界人物</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=00'><span class="file">一般人物</span></a></li>
									</ul>
								</li>
								<li><span class="folder">历史人力管理</span>
									<ul>
										<li><span class="file">政界人物</span></li>
										<li><span class="file">商界人物</span></li>
										<li><span class="file">学术界人物</span></li>
										<li><span class="file">艺术界人物</span></li>
										<li><span class="file">科学界人物</span></li>
										<li><span class="file">一般历史人物</span></li>
									</ul>
								</li>
							</ul>
						</li>
					</ul>
				</td>
				<td style="width: 85%;" valign="top"><iframe id="mainframe" name="mainframe"
						    src="" frameborder="0" style="width: 100%;height:100%"></iframe></td>

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
