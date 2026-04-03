<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<title>财务--财务树</title>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
	type="text/css" />

<script src="<%=basePath%>js/ea/finance/invoicing/left_frame.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
	type="text/javascript"></script>
<script type="text/javascript">
   var basePath="<%=basePath%>";
	var jumptype = "${param.jumptype}";
	var type = "${param.type}";
</script>

<style type="text/css">
#qh_sw {
	width: 15%;
	
}
.bk{
 width:250px;
 height:550px;
 overflow:auto;
 border: 1px solid #DAE7F6;
 

}

</style>
</head>
<body>
	<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
		<tr>

			<td id="qh_sw" style="width: 18%;margin-left: 5px;" valign="top">
				<div style="width:252px;" class="qh_gg_nav">&nbsp;项目树管理</div> <!--左边的树 -->
            <div  class="bk">
				<ul id="tree" class="filetree" style="width:100%;">
					
						<ul>
							<li><span class="folder">项目拟定</span>
								<ul><%--
									<li id="rs"><span class="folder"><a
											>(01)人事项目</a></span>
										<ul>


										<li><span class="folder">(011)公司机构</span>
											<ul>
												<li><span class="folder">(0111)机构管理</span>
													<ul>
														<li><a target="mainframe11"
															><span
																class="file">(01111)机构设置</span> </a></li>
														<li><a target="mainframe11"><span
																class="file">(01112)组织机构图</span> </a></li>
														<li><a target="mainframe11"
															><span
																class="file">(01113)花名册</span> </a></li>
														<li><a target="mainframe11"
															><span
																class="file">(01114)机构图</span> </a></li>
													</ul></li>
												<li><span class="folder">(0112)部门职能描述</span>
													<ul>
														<li><a target="mainframe11"
															><span
																class="file">(01121)职责汇总</span>
														</a>
														</li>
														<li><span class="file">(01122)部门职能战区图</span>
														</li>
														<li><span class="file">(01123)部门工作流程</span>
														</li>
														<li><span class="file">(01124)部门工作量化成果指标管理</span>
														</li>
														<li><span class="file">(01125)部门职能要求</span>
														</li>
													</ul></li>

												<li><span class="folder">(0113)岗位设置管理</span>
													<ul>
														<li><a target="mainframe11"
															><span
																class="file">(01131)岗位说明书及要求</span>
														</a>
														</li>
														<li><span class="file">(01132)岗位战区图</span>
														</li>
														<li><span class="file">(01133)岗位工作流程</span>
														</li>
														<li><span class="file">(01134)岗位工作量化成果指标管理</span>
														</li>
														<li><span class="file">(01135)岗位安全责任书</span>
														</li>
														<li><span class="file">(01136)岗位授权管理</span>
														</li>
													</ul></li>
												<li><span class="folder">(0114)人员配备管理</span>
													<ul>
														<li><a target="mainframe11"
															><span
																class="file">(01141)微型企业人员配备</span>
														</a>
														</li>
														<li><span class="file">(01142)小型企业人员配备</span>
														</li>
														<li><span class="file">(01143)中型企业人员配备</span>
														</li>
														<li><span class="file">(01144)大型企业人员配备</span>
														</li>
														<li><span class="file">(01145)集团人员配备</span>
														</li>
													</ul></li>
												<li><span class="folder">(0115)机构资料管理</span>
													<ul>
														<li><span class="file">(01151)机构资料入库</span>
														</li>
														<li><span class="file">(01152)机构资料出库</span>
														</li>
														<li><span class="file">(01153)机构资料库存</span>
														</li>
													</ul></li>

											</ul></li>
										<li><span class="folder">(012)公司招聘管理</span>
											<ul>
												<li><span class="folder">(0121)公司招聘规划</span>
													<ul>
														<li><span class="folder">(01211)公司招聘岗位规划</span>
															<ul>
																<li><a target="mainframe11"
																	><span
																		class="file">(012111)岗位统记</span>
																</a>
																</li>
																<li><span class="file">(012112)岗位编制</span>
																</li>
																<li><span class="file">(012113)个人规划管理</span>
																</li>
															</ul></li>
														<li><span class="folder">(01212)公司薪酬规划</span>
															<ul>
																<li><span class="file">(012121)招聘薪酬调查</span>
																</li>
																<li><span class="file">(012122)薪酬极差设定</span>
																</li>
															</ul></li>
														<li><span class="folder">(01213)公司人力投资规划</span>
															<ul>
																<li><span class="file">(012131)人力成本图</span>
																</li>
																<li><span class="file">(012132)人力成本收入百分比</span>
																</li>
															</ul></li>
													</ul></li>
												<li><span class="folder">(0122)公司招聘宣传</span>
													<ul>
														<li><span class="folder">(01221)公司招聘资料</span>
															<ul>
																<li><span class="file">(012211)文字设计</span>
																</li>
																<li><span class="file">(012212)图片设计</span>
																</li>
																<li><span class="file">(012213)视频设计</span>
																</li>
															</ul></li>
														<li><span class="folder">(01222)公司宣传方式</span>
															<ul>
																<li><span class="file">(012221)招聘薪酬调查</span>
																</li>
																<li><span class="file">(012222)薪酬极差设定</span>
																</li>
															</ul></li>
													</ul></li>

												<li><span class="folder">(0123)公司招聘登记管理</span>
													<ul>
														<li><a target="mainframe11"
															><span
																class="file">(01231)公司社会人力</span>
														</a>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01232)公司招聘登记管理</span>
														</a>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01233)公司选择通知面试</span>
														</a>
														</li>
													</ul></li>
												<li><span class="folder">(0124)公司招聘面试</span>
													<ul>
														<li><a target="mainframe11"
															><span
																class="file">(01241)公司面试登记管理</span>
														</a>
														</li>
														<li><span class="folder">(01242)公司面试考试结果</span>
															<ul>
																<li><span class="file">(012421)面试老师管理</span>
																</li>
																<li><span class="file">(012422)面试题库管理</span>
																</li>
																<li><span class="file">(012423)面试考试管理</span>
																</li>
																<li><span class="file">(012424)面试结果分数管理</span>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(012425)面试结果管理</span>
																</a>
																</li>
															</ul></li>
														<li><span class="folder">(01243)公司入职管理</span>
															<ul>
																<li><span class="folder">(012431)参观期管理</span>
																	<ul>
																		<li><span class="file">(0124311)参观人员登记</span>
																		</li>
																		<li><span class="file">(0124312)上传参观人员</span>
																		</li>
																		<li><span class="file">(0124313)打印参观登记</span>
																		</li>
																		<li><span class="file">(0124314)签订参观协议</span>
																		</li>
																	</ul></li>
																<li><span class="folder">(012432)培训期管理</span>
																	<ul>
																		<li><span class="file">(0124321)培训人员登记</span>
																		</li>
																		<li><span class="file">(0124322)上传培训人员</span>
																		</li>
																		<li><span class="file">(0124323)打印培训登记</span>
																		</li>
																		<li><span class="file">(0124324)签订培训协议</span>
																		</li>
																	</ul></li>
																<li><span class="folder">(012433)正式入职管理</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0124331)正式入职人员</span>
																		</a>
																		</li>
																		<li><span class="file">(0124332)上传正式人员</span>
																		</li>
																		<li><span class="file">(0124333)打印正式登记</span>
																		</li>
																		<li><span class="file">(0124334)正式入职协议</span>
																		</li>
																	</ul></li>
															</ul></li>
													</ul></li>
											</ul></li>
										<li><span class="folder">(013)公司在职员工</span>
											<ul>
												<li><span class="folder">(0131)公司入职管理</span>
													<ul>
														<li><span class="folder">(01311)公司入职登记管理</span>
															<ul>
																<li><a target="mainframe11"
																	><span
																		class="file">(013111)人员分配管理</span>
																</a>
																</li>
																<li><span class="file">(013112)人员分配报表</span>
																</li>
																<li><span class="file">(013113)移交帐号管理</span>
																</li>
																<li><span class="file">(013114)职责管理</span>
																</li>
															</ul></li>
														<li><span class="file">(01312)公司岗位分配变动</span>
														</li>
														<li><span class="file">(01313)公司工种类别管理</span>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01314)公司员工汇总</span>
														</a>
														</li>
														<li><span class="folder">(01315)公司人事报表</span>
															<ul>
																<li><span class="folder">(013151)员工信息汇总报表</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315101)学历统计表</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315102)年龄统计表</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315103)职工名册</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315104)地址汇总</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315105)联系方式</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315106)学位学历</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315107)个人履历</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315108)家庭成员</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315109)健康状况</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315110)政治面貌</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315111)奖励汇总</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315112)处分汇总</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315113)调查汇总</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315114)证件汇总</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315115)资料汇总</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315116)人事档案</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315117)银行帐号</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(01315118)合同管理</span>
																		</a>
																		</li>
																	</ul></li>
																<li><span class="folder">(013152)资料管理</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0131521)员工资料入库</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0131522)员工资料出库</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0131523)员工资料库存</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0131524)员工资料单据</span>
																		</a>
																		</li>
																	</ul></li>
															</ul></li>
													</ul></li>
												<li><span class="folder">(0132)公司培训职业规划管理</span>
													<ul>
														<li><span class="file">(01321)公司职业生涯规划</span>
														</li>
														<li><span class="folder">(01322)公司入职培训</span>
															<ul>
																<li><span class="file">(013221)入职培训项目</span>
																</li>
																<li><span class="file">(013222)入职培训教案</span>
																</li>
																<li><span class="file">(013223)入职培训教师</span>
																</li>
																<li><span class="file">(013224)入职培训学员</span>
																</li>
																<li><span class="file">(013225)入职培训设备</span>
																</li>
																<li><span class="file">(013226)入职培训进度</span>
																</li>
															</ul></li>
														<li><span class="folder">(01323)公司入职考试管理</span>
															<ul>
																<li><span class="file">(013231)入职考试题库</span>
																</li>
																<li><span class="file">(013232)入职考试标准</span>
																</li>
																<li><span class="file">(013233)入职考官资格认定</span>
																</li>
																<li><span class="file">(013234)入职考试管理</span>
																</li>
																<li><span class="file">(013235)入职考试结果</span>
																</li>
																<li><span class="file">(013236)入职考试排行</span>
																</li>
																<li><span class="file">(013237)入职奖惩管理</span>
																</li>
															</ul></li>
														<li><span class="folder">(01324)公司在职培训</span>
															<ul>
																<li><span class="file">(013241)在职培训项目</span>
																</li>
																<li><span class="file">(013242)在职培训教案</span>
																</li>
																<li><span class="file">(013243)在职培训教师</span>
																</li>
																<li><span class="file">(013244)在职培训学员</span>
																</li>
																<li><span class="file">(013245)在职培训设备</span>
																</li>
																<li><span class="file">(013246)在职培训进度</span>
																</li>
															</ul></li>
														<li><span class="folder">(01325)公司在职考试管理</span>
															<ul>
																<li><span class="file">(013251)在职考试题库</span>
																</li>
																<li><span class="file">(013252)在职考试标准</span>
																</li>
																<li><span class="file">(013253)在职考官资格认定</span>
																</li>
																<li><span class="file">(013254)在职考试管理</span>
																</li>
																<li><span class="file">(013255)在职考试结果</span>
																</li>
																<li><span class="file">(013256)在职考试排行</span>
																</li>
																<li><span class="file">(013257)在职奖惩管理</span>
																</li>
															</ul></li>
													</ul></li>
												<li><span class="folder">(0133)公司劳资管理</span>
													<ul>
														<li><span class="file">(01331)公司保险管理</span>
														</li>
														<li><span class="file">(01332)公司福利管理</span>
														</li>
														<li><span class="folder">(01333)公司人事合同管理</span>
															<ul>
																<li><span class="folder">(013331)劳动关系管理</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0133311)实习协议</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0133312)劳动合同</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0133313)劳务协议</span>
																		</a>
																		</li>
																	</ul></li>
																<li><span class="folder">(013332)合同台账</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0133321)在职员工合同台账</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0133322)离职员工合同台账</span>
																		</a>
																		</li>
																	</ul></li>
																<li><a target="mainframe11"
																	><span
																		class="file">(0133323)合同报表</span>
																</a>
																</li>
															</ul></li>
														<li><span class="file">(01334)公司劳资纠纷管理</span>
														</li>
													</ul></li>
												<li><span class="folder">(0134)公司工资薪级管理</span>
													<ul>
														<li><span class="folder">(01341)公司项目计划预算</span>
															<ul>
																<li><a target="mainframe11"
																	><span
																		class="file">(013411)日计划工作</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013412)周计划工作</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013413)月计划工作</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013414)季计划工作</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013415)年计划工作</span>
																</a>
																</li>
															</ul></li>
														<li><span class="folder">(01342)公司项目确立目标</span>
															<ul>
																<li><a target="mainframe11"
																	><span
																		class="file">(013421)日目标工作</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013422)周目标工作</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013423)月目标工作</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013424)季目标工作</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013425)年目标工作</span>
																</a>
																</li>
															</ul></li>
														<li><span class="folder">(01343)公司项目工作进度</span>
															<ul>
																<li><span class="folder">(013431)目标工作进度管理</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134311)日目标工作进度</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			
																			><span
																				class="file">(0134312)周目标工作进度</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134313)月目标工作进度</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134314)季目标工作进度</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134315)年目标工作进度</span>
																		</a>
																		</li>
																	</ul></li>
																<li><span class="folder">(013432)项目工作进度管理</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134321)日志汇总</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134322)日志加锁</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134323)计划汇总</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134324)任务汇总</span>
																		</a>
																		</li>
																		<li><span class="folder">(0134325)考勤管理</span>
																			<ul>
																				<li><a target="mainframe11"
																					><span
																						class="file">(01343251)加班</span>
																				</a>
																				</li>
																				<li><a target="mainframe11"
																					><span
																						class="file">(01343252)请假</span>
																				</a>
																				</li>
																				<li><a target="mainframe11"
																					><span
																						class="file">(01343253)考勤报表</span>
																				</a>
																				</li>
																				<li><a target="mainframe11"
																					><span
																						class="file">(01343254)考勤记录</span>
																				</a>
																				</li>
																				<li><a target="mainframe11"
																					><span
																						class="file">(01343255)考勤号码</span>
																				</a>
																				</li>
																				<li><a target="mainframe11"
																					><span
																						class="file">(01343256)勤序号</span>
																				</a>
																				</li>
																			</ul></li>
																	</ul></li>
															</ul></li>
														<li><span class="folder">(01344)公司工作质量考评</span>
															<ul>
																<li><a target="mainframe11"
																	><span
																		class="file">(013441)综合考评</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013442)奖惩管理</span>
																</a>
																</li>
															</ul></li>
														<li><span class="folder">(01345)公司工资查询报表</span>
															<ul>
																<li><a target="mainframe11"
																	><span
																		class="file">(013451)薪级设置</span>
																</a>
																</li>
																<li><span class="folder">(013452)员工级别管理</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134521)员工级别变更</span>
																		</a>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0134522)员工级别汇总</span>
																		</a>
																		</li>
																	</ul></li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013453)工资管理</span>
																</a>
																</li>
																<li><a target="mainframe11"
																	><span
																		class="file">(013454)PK金汇总管理</span>
																</a>
																</li>
															</ul></li>
													</ul></li>
												<li><span class="folder">(0135)公司离职交接管理</span>
													<ul>
														<li><span class="file">(01351)公司岗位责任变动</span>
														</li>
														<li><span class="file">(01352)公司物品移交管理</span>
														</li>
														<li><span class="file">(01353)公司财务移交管理</span>
														</li>
														<li><span class="file">(01354)公司资料移交管理</span>
														</li>
														<li><span class="file">(01355)公司移交报表</span>
														</li>
													</ul></li>
											</ul></li>
										<li><span class="folder">(014)公司离职员工</span>
											<ul>
												<li><span class="folder">(0141)正常离职员工</span>
													<ul>
														<li><span class="folder">(01411)离职员工招聘管理</span>
															<ul>
																<li><span class="file">(014111)职业生涯规划</span>
																</li>
																<li><span class="folder">(014112)入职培训</span>
																	<ul>
																		<li><span class="file">(0141121)入职培训项目</span>
																		</li>
																		<li><span class="file">(0141122)入职培训教案</span>
																		</li>
																		<li><span class="file">(0141123)入职培训教师</span>
																		</li>
																		<li><span class="file">(0141124)入职培训学员</span>
																		</li>
																		<li><span class="file">(0141125)入职培训设备</span>
																		</li>
																		<li><span class="file">(0141126)入职培训进度</span>
																		</li>
																	</ul></li>
																<li><span class="folder">(014113)入职考试管理</span>
																	<ul>
																		<li><span class="file">(0141131)入职考试题库</span>
																		</li>
																		<li><span class="file">(0141132)入职考试标准</span>
																		</li>
																		<li><span class="file">(0141133)入职考官资格认定</span>
																		</li>
																		<li><span class="file">(0141134)入职考试管理</span>
																		</li>
																		<li><span class="file">(0141135)入职考试结果</span>
																		</li>
																		<li><span class="file">(0141136)入职考试排行</span>
																		</li>
																		<li><span class="file">(0141137)入职奖惩管理</span>
																		</li>
																	</ul></li>
																<li><span class="folder">(014114)在职培训</span>
																	<ul>
																		<li><span class="file">(0141141)在职培训项目</span>
																		</li>
																		<li><span class="file">(0141142)在职培训教案</span>
																		</li>
																		<li><span class="file">(0141143)在职培训教师</span>
																		</li>
																		<li><span class="file">(0141144)在职培训学员</span>
																		</li>
																		<li><span class="file">(0141145)在职培训设备</span>
																		</li>
																		<li><span class="file">(0141146)在职培训进度</span>
																		</li>
																	</ul></li>
																<li><span class="folder">(014115)在职考试管理</span>
																	<ul>
																		<li><span class="file">(0141151)在职考试题库</span>
																		</li>
																		<li><span class="file">(0141152)在职考试标准</span>
																		</li>
																		<li><span class="file">(0141153)在职考官资格认定</span>
																		</li>
																		<li><span class="file">(0141154)在职考试管理</span>
																		</li>
																		<li><span class="file">(0141155)在职考试结果</span>
																		</li>
																		<li><span class="file">(0141156)在职考试排行</span>
																		</li>
																		<li><span class="file">(0141157)在职奖惩管理</span>
																		</li>
																	</ul></li>
															</ul></li>
														<li><span class="folder">(01412)离职员工在职管理</span>
															<ul>
																<li><span class="file">(014121)保险管理</span>
																</li>
																<li><span class="file">(014122)福利管理</span>
																</li>
																<li><span class="folder">(014123)人事合同管理</span>
																	<ul>
																		<li><span class="file">(0141231)实习协议</span>
																		</li>
																		<li><span class="file">(0141232)培训协议</span>
																		</li>
																		<li><span class="file">(0141233)劳务协议</span>
																		</li>
																		<li><span class="file">(0141234)保密协议</span>
																		</li>
																		<li><span class="file">(0141235)竟业限制协议</span>
																		</li>
																		<li><span class="file">(0141236)岗位协议</span>
																		</li>
																		<li><span class="file">(0141237)安全协议管理</span>
																		</li>
																	</ul></li>
																<li><span class="file">(014124)劳资纠纷管理</span>
																</li>
															</ul></li>
														<li><span class="folder">(01413)离职员工交接管理</span>
															<ul>
																<li><span class="file">(014131)入职登记管理</span>
																</li>
																<li><span class="file">(014132)岗位分配变动</span>
																</li>
																<li><span class="file">(014133)工种类别管理</span>
																</li>
																<li><span class="folder">(014134)员工汇总</span>
																	<ul>
																		<li><span class="folder">(0141341)基本信息管理</span>
																			<ul>
																				<li><a target="mainframe11"
																					><span
																						class="file">(01413411)员工汇总</span>
																				</a>
																				</li>
																				<li><a target="mainframe11"
																					><span
																						class="file">(01413412)职责汇总</span>
																				</a>
																				</li>
																				<li><span class="file">(01413413)计划汇总</span>
																				</li>
																				<li><span class="file">(01413414)任务汇总</span>
																				</li>
																				<li><span class="file">(01413415)考评汇总</span>
																				</li>
																				<li><span class="file">(01413416)信息分类统计</span>
																				</li>
																				<li><span class="file">(01413417)人事报表</span>
																				</li>
																				<li><span class="file">(01413418)报表传输</span>
																				</li>
																			</ul></li>
																		<li><span class="folder">(0141342)离职管理</span>
																			<ul>
																				<li><span class="file">(01413421)离职变动管理</span>
																				</li>
																				<li><span class="folder">(01413422)工种类别管理</span>
																					<ul>
																						<li><span class="file">(014134221)培训补助管理</span>
																						</li>
																						<li><span class="file">(014134222)试用期员工</span>
																						</li>
																						<li><span class="file">(014134223)正式员工</span>
																						</li>
																						<li><span class="file">(014134224)临时员工</span>
																						</li>
																						<li><span class="file">(014134225)中介人员</span>
																						</li>
																					</ul></li>
																				<li><span class="folder">(01413423)人事合同管理</span>
																					<ul>
																						<li><span class="file">(014134231)实习协议</span>
																						</li>
																						<li><span class="file">(014134232)培训协议</span>
																						</li>
																						<li><span class="file">(014134233)劳务协议</span>
																						</li>
																						<li><span class="file">(014134234)保密协议</span>
																						</li>
																						<li><span class="file">(014134235)竟业限制协议</span>
																						</li>
																						<li><span class="file">(014134236)岗位协议</span>
																						</li>
																						<li><span class="file">(014134237)安全协议管理</span>
																						</li>
																					</ul></li>
																				<li><span class="folder">(01413424)离职移交管理</span>
																					<ul>
																						<li><span class="file">(014134241)岗位职责变动管理</span>
																						</li>
																						<li><span class="file">(014134242)物品移交管理</span>
																						</li>
																						<li><span class="file">(014134243)财务移交管理</span>
																						</li>
																						<li><span class="file">(014134244)资料移交管理</span>
																						</li>
																						<li><span class="file">(014134245)移交报表</span>
																						</li>
																					</ul></li>
																			</ul></li>
																		<li><span class="file">(0141343)离职员工登记表</span>
																		</li>
																		<li><span class="file">(0141344)离职员工报表管理</span>
																		</li>
																	</ul></li>
																<li><span class="file">(014135)人事报表</span>
																</li>
															</ul></li>
														<li><span class="folder">(01414)离职员工再服务管理</span>
															<ul>
																<li><span class="file">(014141)项目计划预算</span>
																</li>
																<li><span class="file">(014142)项目确立目标</span>
																</li>
																<li><span class="file">(014143)项目工作进度</span>
																</li>
																<li><span class="file">(014144)工作质量考评</span>
																</li>
																<li><span class="folder">(014145)工资查询报表</span>
																	<ul>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0141451)岗位能力薪酬级设定</span>
																		</a>
																		</li>
																		<li><span class="file">(0141452)级差级别报表设定</span>
																		</li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0141453)级差级别月设定 </span>
																		</a>
																		</li>
																		<li><span class="folder">(0141454)工资报表管理</span>
																			<ul>
																				<li><span class="folder">(01414541)发放工资报表</span>
																					<ul>
																						<li><span class="file">(014145411)试用期员工工资报表</span>
																						</li>
																						<li><span class="file">(014145412)正式员工工资报表</span>
																						</li>
																						<li><span class="file">(014145413)临时员工工资报表</span>
																						</li>
																						<li><span class="file">(014145414)中介人员工资报表</span>
																						</li>
																						<li><span class="file">(014145415)离职人员工资报表</span>
																						</li>
																						<li><span class="file">(014145416)正式员工工资报表</span>
																						</li>
																						<li><span class="file">(014145417)正式员工工资报表</span>
																						</li>
																						<li><span class="file">(014145418)正式员工工资报表</span>
																						</li>
																					</ul></li>
																				<li><span class="folder">(01414542)相应工资报表</span>
																					<ul>
																						<li><span class="file">(0141454201)考勤报表</span>
																						</li>
																						<li><span class="file">(0141454202)安全奖报表</span>
																						</li>
																						<li><span class="file">(0141454203)计件报表</span>
																						</li>
																						<li><span class="file">(0141454204)本日任务报表</span>
																						</li>
																						<li><span class="file">(0141454205)本周任务报表</span>
																						</li>
																						<li><span class="file">(0141454206)本月任务报表</span>
																						</li>
																						<li><span class="file">(0141454207)本季任务报表</span>
																						</li>
																						<li><span class="file">(0141454208)本年任务报表</span>
																						</li>
																						<li><span class="file">(0141454209)采购误差报表</span>
																						</li>
																						<li><span class="file">(0141454210)设备维修报表</span>
																						</li>
																						<li><span class="file">(0141454211)油耗报表</span>
																						</li>
																					</ul></li>
																			</ul></li>
																		<li><a target="mainframe11"
																			><span
																				class="file">(0141455)工资管理</span>
																		</a>
																		</li>
																	</ul></li>
															</ul></li>
														<li><span class="folder">(01415)离职员工增值管理</span>
															<ul>
																<li><span class="file">(014151)岗位责任变动</span>
																</li>
																<li><span class="file">(014152)物品移交管理</span>
																</li>
																<li><span class="file">(014153)财务移交管理 </span>
																</li>
																<li><span class="file">(014154)资料移交管理</span>
																</li>
																<li><span class="file">(014155)移交报表</span>
																</li>
															</ul></li>
													</ul></li>
												<li><span class="folder">(0142)非正常离职员工</span>
													<ul>
														<li><span class="file">(01421)离职员工交接管理</span>
														</li>
														<li><span class="file">(01422)离职员工招聘管理</span>
														</li>
														<li><span class="file">(01423)离职员工在职管理</span>
														</li>
														<li><span class="file">(01424)离职员工问题追诉管理</span>
														</li>
													</ul></li>
											</ul></li>
										<li><span class="folder">(015)公司社会人力 </span>
											<ul>
												<li><span class="folder">(0151)社会人力资源管理 </span>
													<ul>
														<li><a target="mainframe11"
															><span
																class="file">(01511)网站人力</span>
														</a>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01512)社会人力 </span>
														</a>
														</li>
													</ul></li>
												<li><span class="folder">(0152)现代人力管理 </span>
													<ul>
														<li><a target="mainframe11"
															><span
																class="file">(01521)政界人物</span>
														</a>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01522)商界人物 </span>
														</a>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01523)学术界人物</span>
														</a>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01524)艺术界人物</span>
														</a>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01525)科学界人物</span>
														</a>
														</li>
														<li><a target="mainframe11"
															><span
																class="file">(01526)一般人物</span>
														</a>
														</li>
													</ul></li>
												<li><span class="folder">(0153)历史人力管理</span>
													<ul>
														<li><span class="file">(01531)政界人物</span>
														</li>
														<li><span class="file">(01532)商界人物</span>
														</li>
														<li><span class="file">(01533)学术界人物</span>
														</li>
														<li><span class="file">(01534)艺术界人物</span>
														</li>
														<li><span class="file">(01535)科学界人物</span>
														</li>
														<li><span class="file">(01536)一般历史人物</span>
														</li>
													</ul></li>
											</ul></li>
										<li><span class="folder">(016)工资管理 </span>
											<ul>
												<li><span class="folder">(0161)工资管理 </span>
													<ul>
														<li><span class="file">(01611)基础数据维护</span>
														</li>
														<li><span class="file">(01612)工资构成管理 </span>
														</li>
														<li><span class="file">(01613)工资等级管理 </span>
														</li>
														<li><span class="file">(01614)工资关联管理 </span>
														</li>
													</ul></li>
												<li><span class="folder">(0162)考勤管理 </span>
													<ul>
														<li><span class="file">(01621)工作日历</span>
														</li>
													</ul></li>
											</ul></li>

									</ul></li>
									<li><span class="folder" id="bg"><a
											>(02)办公室项目</a> </span>
										<ul>
											<li id="bg1"><a
												><span
													class="file">(021)公司规划项目</span> </a>
											</li>
											<li id="bg2"><a
												><span
													class="folder">(022)行政管理项目</span> </a>
												<ul>
													<li id="bg1"><a
														><span
															class="folder">(0221)单位管理</span> </a>
														<ul>
															<li id="bg1"><a
																><span
																	class="file">(02211)企业所属单位管理</span> </a>
															</li>
															<li id="bg1"><a
																><span
																	class="file">(02212)经营范围</span> </a>
															</li>
															<li id="bg1"><a
																><span
																	class="file">(02213)企业证件管理</span> </a>
															</li>
															<li id="bg1"><a
																><span
																	class="file">(02214)企业资质评定管理</span> </a>
															</li>
														</ul></li>
													<li id="bg1"><a
														><span
															class="folder">(0222)企业文化建设</span> </a>

														<ul>
															<li><a
																
																><span class="file">(022201)企业形象</span>
															</a></li>
															<li><a ><span class="file">(022202)品牌建设</span>
															</a></li>
															<li><a
																
																><span class="file">(022203)办公室形象管理</span>
															</a></li>
															<li><a><span class="file">(022204)员工形象管理</span>
															</a></li>

															<li><a
																
																target="admin1"><span class="file" id="unex">(022205)企业文化管理</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(022206)企业精神</span>
															</a></li>
															<li><span
																	class="file"><a>(022207)企业历程</a></span></li>
															<li><span
																	class="folder"><a>(022208)员工活动</a></span> 
																<ul>
																	<li><a target="admin1"><span class="file">(0222081)企业公益组</span>
																	</a></li>
																	<li><a target="admin1"><span class="file">(0222082)社会公益组</span>
																	</a></li>
																</ul></li>

															<li><a target="admin1"><span class="folder"
																	id="unex">(022209)优秀员工奖励</span> </a>
																<ul>
																	<li><a target="admin1"><span class="file">(0222091)企业公益组</span>
																	</a></li>
																	<li><a target="admin1"><span class="file">(0222092)社会公益组</span>
																	</a></li>
																</ul></li>
															<li><a
																
																target="admin1"><span class="file">(022210)企业员工排行榜</span>
															</a></li>
														</ul></li>
													<li id="bg1"><a
														onclick="getList('fxlb','bg1','企业战略规划项目');"><span
															class="folder">(0223)行政建设管理</span> </a>
														<ul>
															<li><a><span class="folder" id="unex">(022301)现场会议(会议管理)</span>
															</a>
																<ul>
																	<li><a
																		
																		><span class="file">(0223011)会务机构人员配备</span>
																	</a></li>
																	<li><a
																		
																		target="admin1"><span class="file">(0223012)会议准备阶段</span>
																	</a>
																	</li>
																	<li><a
																		
																		target="admin1"><span class="file">(0223013)正式会议阶段</span>
																	</a>
																	</li>
																	<li><a
																		
																		target="admin1"><span class="file">(0223014)会议闭幕阶段</span>
																	</a>
																	</li>
																</ul></li>
															<li><a
																
																target="admin1"><span class="file">(022302)视频会议</span>
															</a></li>
															<li><a target="admin1"><span class="file">(022303)会议记录</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(022304)会议室预约管理</span>
															</a></li>

															<li><a
																
																target="admin1"><span class="file" id="unex">(022305)员工会议管理</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(022306)办公室合同流转</span>
															</a></li>
															<li><a target="admin1"><span class="file">(022307)办公室合同查询</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(022308)电子印章管理</span>
															</a></li>

															<li><a target="admin1"><span class="file"
																	id="unex">(022309)电子记录管理</span> </a></li>
															<li><a target="admin1"><span class="file">(022310)企业章程管理</span>
															</a></li>
															<li><a target="admin1"><span class="file">(022311)企业制度</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(022312)行政法规</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(022313)国家法规</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(022314)文书文件管理</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(022315)公文流转管理</span>
															</a></li>
														</ul></li>
													<li id="bg1"><a
														onclick="getList('fxlb','bg1','企业战略规划项目');"><span
															class="folder">(0224)办公资料管理</span> </a>
														<ul>
															<li><a
															
																target="admin1"><span class="file">(02241)个人资料管理</span>
															</a></li>
															<li><a
															
																target="admin1"><span class="file">(02242)公共资料管理</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(02243)图片管理</span>
															</a></li>

															<li><a target="admin1"><span class="file"
																	id="unex">(02244)视频管理</span> </a></li>
															<li><a target="admin1"><span class="file">(02245)ppt管理</span>
															</a></li>

															<li><a target="admin1"><span class="file"
																	id="unex">(02246)设计图管理</span> </a></li>

														</ul></li>
													<li id="bg1"><a
														onclick="getList('fxlb','bg1','企业战略规划项目');"><span
															class="folder">(0225)资料库公共查询管理</span> </a>

														<ul>
															<li><a
															
																target="admin1"><span class="file">(02251)Word文档查询</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(02252)Excel文档查询</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(02253)图片管理查询</span>
															</a></li>

															<li><a target="admin1"><span class="file"
																	id="unex">(02254)视频管理查询</span> </a></li>
															<li><a target="admin1"><span class="file">(02255)设计图管理查询</span>
															</a></li>

														</ul></li>
												</ul></li>
											<li id="bg3"><a
												onclick="getList('fxlb','bg3','信息管理项目');"><span
													class="folder">(023)信息管理项目</span> </span> </a>

												<ul>
													<li><span class="folder" id="bg31">(0231)网络管理</span>
														<ul>
															<li><a target="admin1"><span class="file">(02311)软件系统管理</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(02312)IP地址管理</span> </a></li>
															<li><a
																
																target="admin1"><span class="file">(02313)短信管理</span> </a></li>

															<li><a
																
																target="admin1"><span class="file" id="unex">(02314)微信管理</span>
															</a></li>
															<li><a href="<%=basePath%>ea/qq/ea_getQqList.jspa?"
																target="admin1"><span class="file">(02315)QQ管理</span> </a></li>

															<li><a
																
																target="admin1"><span class="file" id="unex">(02316)网络硬盘管理</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file" id="unex">(02317)域名管理</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file" id="unex">(02318)网络传真管理</span>
															</a></li>
															<li><a
																href="<%=basePath%>page/ea/main/telrec/telMain.jsp"
																target="admin1"><span class="file" id="unex">(02319)呼叫中心管理</span>
															</a></li>

														</ul></li>
													<li><span class="folder" id="bg32">(0232)公共信息管理</span>

														<ul>
															<li><a
																
																target="admin1"><span class="file">(02321)公告通知管理</span> </a></li>
															<li><a
															
																target="admin1"><span class="file">(02322)新闻管理</span> </a></li>
															<li><a target="admin1"><span class="file">(02323)投票管理</span>
															</a></li>

															<li><a
																
																target="admin1"><span class="file" id="unex">(02324)公共电话薄</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file">(02325)简报管理</span> </a></li>

															<li><a target="admin1"><span class="file"
																	id="unex">(02326)数据提醒管理</span>
															</a></li>
															<li><a
																
																target="admin1"><span class="file" id="unex">(02327)公共日程管理</span>
															</a></li>
															<li><a target="admin1"><span class="file"
																	id="unex">(02328)公共日志管理</span>
															</a></li>

														</ul></li>
													<li><span class="folder" id="bg33">(0233)企业信息管理</span>

														<ul>
															<li><a target="admin1"><span class="file">(02331)企业信息管理</span>
															</a></li>
															<li><a target="admin1"><span class="file">(02332)企业信息推广</span>
															</a></li>
														</ul></li>
													<li><span class="folder" id="bg34">(0234)网络安全管理</span>

														<ul>
															<li><a
																
																target="admin1"><span class="file">(02341)网络加密管理</span> </a></li>
															<li><a
																
																target="admin1"><span class="file">(02342)网络杀毒管理</span> </a></li>
															<li><a target="admin1"><span class="file">(02343)网络建设1</span>
															</a></li>
															<li><a target="admin1"><span class="file">(02344)带宽建设2</span>
															</a></li>
															<li><a target="admin1"><span class="file">(02345)网络安全建设3</span>
															</a></li>
															<li><a target="admin1"><span class="file">(02346)服务器建设4</span>
															</a></li>
															<li><a target="admin1"><span class="file">(02347)终端建设</span>
															</a></li>
														</ul></li>

												</ul></li>
											<li id="bg4"><span class="folder"><a
													onclick="getList('fxlb','bg4');">(024)后勤管理项目</a> </span>

												<ul>
													<li><span class="folder">(02401)接待管理</span>
														<ul>
															<li><span class="folder">(024011)往来个人管理</span>

																<ul>
																	<li><a target="mainframe11"
																		><span
																			class="file">(0240111)个人接待信息管理</span> </a>
																	</li>
																	<li><a target="mainframe11"
																		><span
																			class="file">(0240112)个人接待信息报表</span> </a>
																	</li>

																</ul>
															</li>
															<li><a target="mainframe11"
																><span
																	class="file">(024012)往来单位管理</span> </a>
															</li>

														</ul>
													</li>
													<li><a target="mainframe11" href="javascript:"><span
															class="file">(02402)场地管理</span> </a>
													</li>

													<li><span class="file">(02403)资产库管</span>
													</li>
													<li><span class="folder">(02404)安全管理</span>
														<ul>
															<li><span class="folder">(024041)安全管理</span>
																<ul>
																	<li><a target="mainframe11"
																		><span
																			class="file">(0240411)钥匙管理</span> </a></li>
																	<li><a target="mainframe11"
																		><span
																			class="file">(0240412)安全类别</span> </a></li>
																	<li><a target="mainframe11"
																		><span
																			class="file">(0240413)安全单据</span> </a></li>

																</ul></li>
															<li><span class="folder">(024042)安全防范管理</span>
																<ul>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404201)火灾预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404202)防盗管理</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404203)防霉管理</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404204)防毒管理</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404205)污染预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404206)雪灾预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404207)冰雹预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404208)冻害预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404209)垮塌预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404210)地震预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404211)洪涝预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404212)防泥石流</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404213)虫害预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404214)疾病预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404215)安全用电</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404216)雷雨预防</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404217)防龙卷风</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404218)食品安全</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(02404219)车辆设备</span> </a></li>
																</ul></li>

														</ul></li>
													<li><span class="folder">(02405)设备管理</span>
														<ul>
															<li><span class="folder">(024051)车管设备</span>
																<ul>
																	<li><span class="folder">(0240511)汽车</span>
																		<ul>
																			<li><span class="folder">(02405111)车辆管理办</span>
																				<ul>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511101)完善车辆信息</span> </a>
																					</li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511102)车辆基本信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511103)车辆门禁</span> </a></li>
																					<li><span class="folder">(0240511104)安全巡检</span>
																						<ul>
																							<li><a target="mainframe11"
																								><span
																									class="file">(02405111041)检查点</span> </a></li>
																							<li><a target="mainframe11"
																								><span
																									class="file">(02405111042)检查项</span> </a></li>
																							<li><a target="mainframe11"
																								><span
																									class="file">(02405111043)巡检计划</span> </a></li>
																							<li><a target="mainframe11"
																								><span
																									class="file">(02405111044)巡检任务</span> </a></li>
																							<li><a target="mainframe11"
																								><span
																									class="file">(02405111045)巡检结果</span> </a></li>
																						</ul></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511105)车牌号维护</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511106)购车发票</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511107)购置税发票</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511108)购置保险信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511109)购置年检信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511110)车辆CNG信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511111)车辆安全信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511112)车辆资产信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511113)车辆使用信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511114)车辆维护信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511115)相关证件子集</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511116)机动车行驶证</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511117)道路运输证</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511118)车用瓶使用证</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511119)车辆购置税证</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511120)车辆违章信息</span> </a></li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511121)车辆准载座位</span> </a></li>

																					<li><a target="mainframe11"
																						><span
																							class="file">(0240511122)安全卫生检查</span> </a></li>


																				</ul></li>

																			<li><span class="folder">(02405112)接送预约接送到达管理</span>
																				<ul>
																					<li><a target="mainframe11"
																						><span
																							class="file">(024051121)接送预约信息管理 </span> </a>
																					</li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(024051122)接送预约信息报表管理 </span> </a>
																					</li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(024051123)接送基本信息管理 </span> </a>
																					</li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(024051124)接送信息报表管理 </span> </a>
																					</li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(024051125)接送到达基本信息管理</span> </a>
																					</li>
																					<li><a target="mainframe11"
																						><span
																							class="file">(024051126)接送到达信息报表管理</span> </a>
																					</li>

																				</ul>
																			</li>
																		</ul>
																	</li>
																</ul></li>
															<li><a target="mainframe11"
																><span
																	class="folder">(024052)工程机械</span> </a>
																<ul>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(0240521)挖掘机</span> </a></li>
																	<li><a target="mainframe11" href="javascript:"><span
																			class="file">(0240522)装载机</span> </a></li>

																</ul>
															</li>
															<li><a target="mainframe11"
																><span
																	class="folder">(024053)办公室设备</span> </a>
																<ul>
																	<li><a target="mainframe11" ><span
																			class="file">(0240531)电脑</span> </a></li>
																	<li><a target="mainframe11" ><span
																			class="file">(0240532)打印机</span> </a></li>
																	<li><a target="mainframe11" ><span
																			class="file">(0240533)通讯设备</span> </a></li>

																</ul></li>
															<li><a target="mainframe11" href="javascript:"><span
																	class="file">(024054)弱电设备</span> </a>
															</li>

														</ul></li>
													<li><a target="mainframe11"
														><span
															class="file">(02406)物流管理</span> </a>
													</li>
													<li><a target="mainframe11"
														><span
															class="file">(02407)用水管理</span> </a>
													</li>

													<li><a target="mainframe11"
														><span
															class="file">(02408)用电管理</span> </a>
													</li>

													<li><a target="mainframe11"
														><span
															class="file">(02409)值班管理</span> </a>
													</li>

													<li><a target="mainframe11"
														><span
															class="file">(02410)绿化管理</span> </a>
													</li>
													<li><a target="mainframe11" href="javascript:"><span
															class="file">(02411)基建管理</span> </a>
													</li>

													<li><a target="mainframe11" href="javascript:"><span
															class="file">(02412)食堂管理</span> </a>
													</li>

													<li><span class="folder">(02413)住宿管理</span>

														<ul>
															<li><a target="mainframe11"
																href="<%=basePath%>/ea/accommod/ea_getAllList.jspa"><span
																	class="file">(024131)单位住宿</span> </a>
															</li>
															<li><a target="mainframe11" href="javascript:"><span
																	class="file">(024132)单位住宿报表</span> </a>
															</li>
															<li><a target="mainframe11" href="javascript:"><span
																	class="file">(024133)住宿分配</span> </a>
															</li>
															<li><a target="mainframe11" href="javascript:"><span
																	class="file">(024134)住宿分配报表</span> </a>
															</li>

														</ul>
													</li>

													<li><span class="folder">(02414)票务管理</span>
														<ul>
															<li><a target="mainframe11"
																><span
																	class="file">(024141)票务管理</span> </a>
															</li>
															<li><a target="mainframe11"
																><span
																	class="file">(024142)票务报表管理</span> </a>
															</li>

														</ul>
													</li>

													<li><span class="folder">(02415)资产库存管理</span>
														<ul>
															<li><a target="mainframe11"
																><span
																	class="file">(024151)费用采购明细账</span> </a></li>
															<li><a target="mainframe11"
																><span
																	class="file">(024152)验货管理</span> </a></li>
															<li><span class="folder">(024153)入库管理</span>
																<ul>

																	<li><a target="mainframe11"
																		><span
																			class="file">(0241531)采购入库</span> </a>
																	</li>
																</ul>
															</li>
															<li><a target="mainframe11"
																><span
																	class="file">(024154)出库管理</span> </a>

																<ul>

																	<li><a target="mainframe11"
																		><span
																			class="file">(0241541)销售出库</span> </a>
																	</li>
																</ul></li>
															<li><span class="folder">(024155)库存管理</span>
																<ul>

																	<li><a target="mainframe11"
																		><span
																			class="file">(0241551)库存管理</span> </a>
																	</li>
																	<li><a target="mainframe11"
																		><span
																			class="file">(0241552)进销存明细</span> </a>
																	</li>
																	<li><a target="mainframe11"
																		><span
																			class="file">(0241553)进销存汇总</span> </a>
																	</li>
																</ul></li>
															<li><a target="mainframe11"
																><span
																	class="file">(024156)报损管理</span> </a></li>
															<li><a target="mainframe11"
																><span
																	class="file">(024157)存货核算 </span> </a></li>

														</ul>
														<li><span class="file">(02416)物品物料管理</span>
													</li>
												</ul></li>
											<li id="bg5"><span class="folder"><a
													onclick="getList('fxlb','bg5','督察管理项目');">(025)督察管理项目 </a>
											</span>

												<ul>
													<li><span class="folder">(0251)人事督查审核管理</span>
														<ul>
															<li><a target="mainframe"
																><span
																	class="file">(02511)人事督查管理</span> </a></li>
															<li><a target="mainframe"
																><span
																	class="file">(02512)主管审核管理</span> </a></li>
															<li><a target="mainframe"
																><span
																	class="file">(02513)人事审核管理</span> </a></li>
														</ul></li>
													<li><span class="folder">(0252)办公室督查审核管理</span>
														<ul>
															<li><a target="mainframe"
															><span
																	class="file">(02521)办公室督查管理</span> </a></li>

														</ul>
													</li>

													<li><span class="folder">(0253)财务督查审核管理</span>
														<ul>
															<li><a target="mainframe"
																><span
																	class="file">(02531)会计审核管理</span> </a></li>
															<li><a target="mainframe"
																><span
																	class="file">(02532)出纳审核管理</span> </a></li>
														</ul></li>
													<li><span class="folder">(0254)生产督查审核管理</span>
														<ul>
															<li><a target="mainframe"><span class="file">(02541)生产督查管理</span>
															</a></li>

														</ul></li>
													<li><span class="folder">(0255)营销督查审核管理</span>
														<ul>
															<li><a target="mainframe"
																><span
																	class="file">(02551)营销督查管理</span> </a></li>

														</ul></li>

												</ul></li>

										</ul>
									</li>

									<li state="closed" id="cw"><span class="folder" id="cw"><a
											onclick="getList('fxlb','cw','财务项目');">(03)财务项目</a> </span>
										<ul>
											<li id="cw1"><a
												onclick="getList('fxlb','cw1','项目预算招标项目');"><span
													class="file">(031)项目计划预算管理</span> </a>
											</li>
											<li id="cw2"><a
												onclick="getList('fxlb','cw2','资金申请项目');"><span
													class="file">(032)资金申请管理</span> </a>
											</li>
											<li id="cw3"><a
												onclick="getList('fxlb','cw3','资金使用项目');"><span
													class="file">(033)资金收支管理</span> </a>
											</li>
											<li id="cw4"><a
												onclick="getList('fxlb','cw4','凭证管理项目');"><span
													class="file">(034)凭证管理</span> </a>
											</li>
											<li id="cw5"><a
												onclick="getList('fxlb','cw5','总账管理项目');"><span
													class="file">(035)总账明细账 </span> </a>
											</li>

										</ul></li>
									<li><span class="folder" id="sc"><a
											onclick="getList('fxlb','sc','生产项目');">(04)生产项目</a> </span>
										<ul>
											<li><span class="folder">(041)教研管理</span>
												<ul>
													<li><span class="file">(0411)普通教学服务方案研究</span></li>
													<li><span class="folder">(0412)会员教学服务方案研究</span>
														<ul>
															<li><span class="file">(04121)普通会员服务方案研究</span>
															</li>
															<li><span class="file">(04122)VlP会员服务方案研究</span>
															</li>
														</ul></li>
												</ul></li>
											<li><span class="folder">(042)模拟管理</span>
												<ul>
													<li><span class="folder">(0421)入学模拟培训</span>
														<ul>
															<li><span class="file">(04211)模拟培训进度</span></li>
															<li><span class="file">(04212)报表管理</span></li>
															<li><span class="file">(04213)数据导入</span></li>
														</ul></li>
													<li><span class="folder">(0422)科一模拟培训</span>
														<ul>
															<li><span class="file">(04221)入学通知书</span></li>
															<li><span class="file">(04222)登记表报表</span></li>
															<li><span class="file">(04223)短息电话通知</span></li>
															<li><span class="file">(04224)培训</span></li>
															<li><span class="file">(04225)毕业</span></li>
														</ul></li>
													<li><span class="folder">(0423)科二模拟培训</span>
														<ul>
															<li><span class="file">(04231)入学通知书</span></li>
															<li><span class="file">(04232)登记表报表</span></li>
															<li><span class="file">(04233)短息电话通知</span></li>
															<li><span class="file">(04234)培训</span></li>
															<li><span class="file">(04235)毕业</span></li>
														</ul></li>
													<li><span class="folder">(0424)科三模拟培训</span>
														<ul>
															<li><span class="file">(04241)入学通知书</span></li>
															<li><span class="file">(04242)登记表报表</span></li>
															<li><span class="file">(04243)短息电话通知</span></li>
															<li><span class="file">(04244)培训</span></li>
															<li><span class="file">(04245)毕业</span></li>
														</ul></li>
													<li><span class="folder">(0425)科四模拟培训</span>
														<ul>
															<li><span class="file">(04251)入学通知书</span></li>
															<li><span class="file">(04252)登记表报表</span></li>
															<li><span class="file">(04253)短息电话通知</span></li>
															<li><span class="file">(04254)培训</span></li>
															<li><span class="file">(04255)毕业</span></li>
														</ul></li>
													<li><span class="folder">(0426)模拟培训毕业</span>
														<ul>
															<li><span class="file">(04261)毕业通知书</span></li>
															<li><span class="file">(04262)登记表报表</span></li>
															<li><span class="file">(04263)短息电话通知</span></li>
															<li><span class="file">(04264)毕业</span></li>
														</ul></li>
												</ul></li>
											<li><span class="folder">(043)综合办证管理</span>
												<ul>
													<li><span class="folder">(0431)车管管理</span>
														<ul>
															<li><span class="folder">(04311)入学培训考试进度</span>
																<ul>
																	<li><span class="file">(043111)入学培训考试进度</span></li>
																	<li><span class="file">(043112)有效期提示报表</span></li>
																</ul></li>
															<li><span class="folder">(04312)科一培训考试</span>
																<ul>
																	<li><span class="file">(043121)入考通知</span></li>
																	<li><span class="file">(043122)结果通知</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(043123)合格率</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(043124)成绩报表</a>
																	</span></li>
																</ul></li>
															<li><span class="folder">(04313)科二培训考试</span>
																<ul>
																	<li><span class="file">(043131)入考通知</span></li>
																	<li><span class="file">(043132)结果通知</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(043133)合格率</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(043134)成绩报表</a>
																	</span></li>
																</ul></li>
															<li><span class="folder">(04314)科三培训考试</span>
																<ul>
																	<li><span class="file">(043141)入考通知</span></li>
																	<li><span class="file">(043142)结果通知</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(043143)合格率</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(043144)成绩报表</a>
																	</span></li>
																</ul></li>
															<li><span class="folder">(04315)科四培训考试</span>
																<ul>
																	<li><span class="file">(043151)入考通知</span></li>
																	<li><span class="file">(043152)结果通知</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(043153)合格率</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(043154)成绩报表</a>
																	</span></li>
																</ul></li>
															<li><span class="folder">(04316)出证管理</span>
																<ul>
																	<li><span class="file">(043161)出证通知</span></li>
																</ul></li>
														</ul></li>

													<li><span class="folder">(0432)运管管理</span>
														<ul>
															<li><span class="folder">(04321)入学培训考试档案</span>
																<ul>
																	<li><span class="file">(043211)入学考试档案进度</span></li>
																	<li><span class="file">(043212)有效期提示报表</span></li>
																</ul></li>
															<li><span class="folder">(04322)科一培训考试档案</span>
																<ul>
																	<li><span class="file">(043221)档案管理通知</span></li>
																</ul></li>
															<li><span class="folder">(04323)科二培训考试档案</span>
																<ul>
																	<li><span class="file">(043231)科三培训考试档案</span></li>
																</ul></li>
															<li><span class="folder">(04324)科三培训考试档案</span>
																<ul>
																	<li><span class="file">(043241)档案管理通知</span></li>
																</ul></li>
															<li><span class="folder">(04325)科四培训考试档案</span>
																<ul>
																	<li><span class="file">(043251)档案管理通知</span></li>
																</ul></li>
															<li><span class="folder">(04326)毕业证档案</span>
																<ul>
																	<li><span class="file">(043261)毕业证档案通知</span></li>
																</ul></li>
														</ul></li>

												</ul></li>
											<li><span class="folder">(044)培训管理</span>
												<ul>
													<li><span class="folder">(04401)汇总培训管理</span>
														<ul>
															<li><span class="file">(044011)进度跟踪</span></li>
															<li><span class="file">(044012)入学通知</span></li>
															<li><span class="file">(044013)登记表</span></li>
															<li><span class="file">(044014)学员手续进度</span></li>
															<li><span class="file">(044015)有效期提示报表</span></li>
														</ul></li>
													<li><span class="folder">(04402)科一培训管理</span>
														<ul>
															<li><span class="folder">(044021)预约跟踪</span>
																<ul>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440211)教练预约</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440212)学员预约</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			href="<%=basePath%>/ea/coachreservationrecordcontent/ea_getListReservationRecordSummary.jspa?dssrsubject.subjType=10">(0440213)预约汇总</a>
																	</span></li>
																</ul></li>
															<li><span class="file"><a target="mainframe"
																	>(044022)培训进度</a>
															</span></li>
														</ul></li>
													<li><span class="folder">(04403)科二培训管理</span>
														<ul>
															<li><span class="folder">(044031)预约跟踪</span>
																<ul>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440311)教练预约</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440312)学员预约</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440313)预约汇总</a>
																	</span></li>
																</ul></li>
															<li><span class="file"><a target="mainframe"
																	>(044032)培训进度</a>
															</span></li>
														</ul></li>
													<li><span class="folder">(04404)科三培训管理</span>
														<ul>
															<li><span class="folder">(044041)预约跟踪</span>
																<ul>
																	<li><span class="file"><a
																			target="mainframe"
																		>(0440411)教练预约</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440412)学员预约</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440413)预约汇总</a>
																	</span></li>
																</ul></li>
															<li><span class="file"><a target="mainframe"
																>(044042)培训进度</a>
															</span></li>
														</ul></li>
													<li><span class="folder">(04405)科四培训管理</span>
														<ul>
															<li><span class="folder">(044051)预约跟踪</span>
																<ul>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440511)教练预约</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440512)学员预约</a>
																	</span></li>
																	<li><span class="file"><a
																			target="mainframe"
																			>(0440513)预约汇总</a>
																	</span></li>
																</ul></li>
															<li><span class="file"><a target="mainframe"
																	>(044052)培训进度</a>
															</span></li>
														</ul></li>
													<li><span class="file"><a target="mainframe"
															>(04406)科目信息</a>
													</span></li>
													<li><span class="file"><a target="mainframe"
															>(04407)教练信息</a>
													</span></li>
													<li><span class="file"><a target="mainframe"
															>(04408)学员信息</a>
													</span></li>
													<li><span class="file">(04409)文明学习</span>
													</li>
													<li><span class="file">(04410)接送管理</span>
													</li>
													<li><span class="file">(04411)车辆设备管理</span>
													</li>


												</ul></li>
											<li><span class="folder">(045)考试归档管理</span>
												<ul>
													<li><span class="folder">(0451)学员档案</span>
														<ul>
															<li><span class="file"><a target="mainframe"
																	>(04511)学员档案</a>
															</span>
															</li>
														</ul></li>
													<li><span class="folder">(0452)测试考试</span>
														<ul>
															<li><span class="file">(04521)预约测试</span>
															</li>
															<li><span class="file">(04522)测试</span>
															</li>
															<li><span class="file">(04523)预约考试</span>
															</li>
															<li><span class="file">(04524)考试</span>
															</li>
															<li><span class="file">(04525)合格归档</span>
															</li>
														</ul></li>
													<li><span class="folder">(0453)科一测试考试</span>
														<ul>
															<li><span class="file">(04531)预约测试</span>
															</li>
															<li><span class="file">(04532)测试</span>
															</li>
															<li><span class="file">(04533)预约考试</span>
															</li>
															<li><span class="file">(04534)考试</span>
															</li>
															<li><span class="file">(04535)合格归档</span>
															</li>
														</ul></li>
													<li><span class="folder">(0454)科二测试考试</span>
														<ul>
															<li><span class="file">(04541)预约测试</span>
															</li>
															<li><span class="file">(04542)测试</span>
															</li>
															<li><span class="file">(04543)预约考试</span>
															</li>
															<li><span class="file">(04544)考试</span>
															</li>
															<li><span class="file">(04545)合格归档</span>
															</li>
														</ul></li>
													<li><span class="folder">(0455)科三测试考试</span>
														<ul>
															<li><span class="file">(04551)预约测试</span>
															</li>
															<li><span class="file">(04552)测试</span>
															</li>
															<li><span class="file">(04553)预约考试</span>
															</li>
															<li><span class="file">(04554)考试</span>
															</li>
															<li><span class="file">(04555)合格归档</span>
															</li>
														</ul></li>
													<li><span class="folder">(0456)科四测试考试</span>
														<ul>
															<li><span class="file">(04561)预约测试</span>
															</li>
															<li><span class="file">(04562)测试</span>
															</li>
															<li><span class="file">(04563)预约考试</span>
															</li>
															<li><span class="file">(04564)考试</span>
															</li>
															<li><span class="file">(04565)合格归档</span>
															</li>
														</ul></li>
													<li><span class="folder">(0457)合格归档</span>
														<ul>
															<li><span class="file">(04571)合格归档</span>
															</li>
														</ul></li>
												</ul></li>
										</ul></li>
									<li id="yx"><span class="folder"><a
											onclick="getList('fxlb','yx','营销项目');">(05)营销项目</a> </span>

										<ul>
											<li id="yx1"><a onclick="getList('fxlb','yx1','市场调查');"><span
													class="folder">(051)市场调查管理</span> </a>

												<ul>
													<li><a
														
														target="admin1"><span class="file">(0511)社会人力管理</span>
													</a>
													</li>
													<li><a
														href="<%=basePath%>/ea/contactcompany/ea_getListContactCompany.jspa"
														target="admin1"><span class="file">(0512)社会往来单位</span>
													</a>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0513)市场调查</span> </a>
													</li>
													<li><span class="folder">(0514)客户调查</span>
														<ul>
															<li><a
																
																target="admin1"><span class="file" id="unex">(05141)个人客户</span>
															</a>
															</li>
															<li><span class="file" id="ex">(05142)单位客户</span>
															</li>
														</ul>
													</li>
													<li><span class="folder">(0515)供应商调查</span>
														<ul>
															<li><a
																
																target="admin1"><span class="file" id="unex">(05151)个人供应商</span>
															</a>
															</li>
															<li><span class="file" id="ex">(05152)单位供应商</span>
															</li>

														</ul>
													</li>
													<li><span class="folder">(0516)地域调查</span>
														<ul>
															<li><a
																
																target="admin1"><span class="file" id="unex">(05161)社会人力调查</span>
															</a>
															</li>
															<li><a
																
																target="admin1"><span class="file" id="ex">(05162)往来单位调查</span>
															</a>
															</li>

														</ul>
													</li>
													<li><span class="file" id="ex">(0517)竞争对手调查</span>
													</li>
													<li><span class="file" id="ex">(0518)市场价格调查</span>
													</li>
												</ul>
											</li>
											<li id="yx2"><a
												onclick="getList('fxlb','yx2','产品包装推广项目');"><span
													class="folder">(052)产品设计推广管理</span> </a>

												<ul>
													<li><a
														
														target="admin1"><span class="file">(0521)产品设计推广管理</span>
													</a></li>
													<li><span class="folder">(0522)网络推广</span>
														<ul>
															<li><a
																
																target="admin1"><span class="file" id="unex">(05221)短信推广管理</span>
															</a>
															</li>
															<li><span class="file" id="ex">(05222)邮件推广管理</span>
															</li>
															<li><span class="file" id="ex">(05223)博客推广管理</span>
															</li>
															<li>
																<!-- <a href="<%=basePath%>/page/ea/main/microletter/microletter_tree.jsp" target="admin1"> 微信导航菜单  已不用-->
																<span class="folder" id="ex">(05224)微信推广管理</span> <!-- </a>  -->
																<ul>
																	<li><a
																		
																		target="admin1"><span class="file" id="unex">(052241)微信活动管理</span>
																	</a>
																	</li>
																</ul>
															</li>
															<li><span class="file" id="ex">(05225)QQ推广管理</span>
															</li>
															<li><span class="file" id="ex">(05226)网站推广管理</span>
															</li>
														</ul>
													</li>
													<li><span class="file">(0523)户外广告</span>
													</li>
													<li><span class="folder">(0524)媒体宣传</span>
														<ul>
															<li><span class="file" id="unex">(05241)电视宣传组</span>
															</li>
															<li><span class="file" id="ex">(05242)报纸宣传组</span>
															</li>

														</ul>
													</li>
													<li><span class="file">(0525)会议宣传</span>
													</li>
												</ul>
											</li>
											<li id="yx3"><a
												onclick="getList('fxlb','yx3','咨询收集客户项目');"><span
													class="folder">(053)客户咨询管理</span> </a>

												<ul>
													<li><a
														
														target="admin1"><span class="file">(0531)单位客户咨询管理</span>
													</a></li>

													<li><span class="folder">(0532)个人客户咨询管理</span>
														<ul>
															<li><a
																
																target="admin1"><span class="file" id="unex">(05321)个人客户基本信息</span>
															</a>
															</li>
															<!-- <li>
												<a href="<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?identifier=identifier" target="admin1"><span
													class="file" id="unex">产品销售报表</span></a>
											</li> -->
															<li><a
																
																target="admin1"><span class="file">(05322)潜在客户需求报表</span>
															</a>
															</li>
															<li><a
																
																target="admin1"><span class="file">(05323)客户来源管理报表</span>
															</a>
															</li>
															<li><a
																
																target="admin1"><span class="file">(05324)客户产品兴趣报表</span>
															</a>
															</li>

														</ul>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0533)客户注册单位</span>
													</a>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0534)内部企业注册单位</span>
													</a></li>
												</ul>
											</li>
											<li id="yx4"><a
												onclick="getList('fxlb','yx4','成交客户项目');"><span
													class="folder">(054)成交产品服务</span> </a>

												<ul>
													<li><span class="folder">(0541)客户建档管理</span>
														<ul>
															<li><a
																
																target="admin1"><span class="file" id="unex">(05411)学员档案管理</span>
															</a>
															</li>
															<li><a
																href="<%=basePath%>/ea/clinch/ea_getSArchiveSheetList.jspa"
																target="admin1"><span class="file" id="ex">(05412)学员档案出库单据</span>
															</a>
															</li>
														</ul>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0542)预定产品</span> </a>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0543)成交产品管理</span>
													</a>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0544)指导客户</span> </a>
													</li>


													<li><span class="folder">(0545)成交客户网络服务</span>
														<ul>
															<li><a
																
																target="admin1"><span class="file" id="unex">(05451)短信推广管理</span>
															</a>
															</li>
															<li><span class="file" id="ex">(05452)邮件推广管理</span>
															</li>
															<li><span class="file" id="ex">(05453)博客推广管理</span>
															</li>
															<li>
																<!-- <a href="<%=basePath%>/page/ea/main/microletter/microletter_tree.jsp" target="admin1"> 微信导航菜单  已不用-->
																<span class="folder" id="ex">(05454)微信推广管理</span> <!-- </a>  -->
																<ul>
																	<li><a
																		
																		target="admin1"><span class="file" id="unex">(054541)微信活动管理</span>
																	</a>
																	</li>
																</ul>
															</li>
															<li><span class="file" id="ex">(05455)QQ推广管理</span>
															</li>
															<li><span class="file" id="ex">(05456)网站推广管理</span>
															</li>
														</ul>
													</li>
													<li><span class="file">(0546)教务生产跟踪服务</span>
													</li>
													<li><span class="file">(0547)营销合同流转</span>
													</li>
													<li><span class="file">(0548)营销合同查询</span>
													</li>
												</ul>
											</li>
											<li id="yx5"><a
												onclick="getList('fxlb','yx5','售后跟踪项目');"><span
													class="folder">(055)跟踪产品客户服</span> </a>

												<ul>
													<li><a
							
														target="admin1"><span class="file">(0551)跟踪服务</span> </a>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0552)问题解决</span> </a>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0553)增值服务</span> </a>
													</li>
													<li><span class="file">(0554)成交增值</span>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0555)投诉处理</span> </a>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0556)内部纠纷</span> </a>
													</li>
													<li><a
														
														target="admin1"><span class="file">(0557)外部纠纷</span> </a>
													</li>
													<li><span class="folder">(0558)售后跟踪客户网络服务</span>
														<ul>
															<li><a
																
																target="admin1"><span class="file" id="unex">(05581)短信推广管理</span>
															</a>
															</li>
															<li><span class="file" id="ex">(05582)邮件推广管理</span>
															</li>
															<li><span class="file" id="ex">(05583)博客推广管理</span>
															</li>
															<li>
																<!-- <a href="<%=basePath%>/page/ea/main/microletter/microletter_tree.jsp" target="admin1"> 微信导航菜单  已不用-->
																<span class="folder" id="ex">(05584)微信推广管理</span> <!-- </a>  -->
																<ul>
																	<li><a
																		
																		target="admin1"><span class="file" id="unex">(055841)微信活动管理</span>
																	</a>
																	</li>
																</ul>
															</li>
															<li><span class="file" id="ex">(05585)QQ推广管理</span>
															</li>
															<li><span class="file" id="ex">(05586)网站推广管理</span>
															</li>
														</ul>
													</li>
												</ul>
											</li>

										</ul>
									 --%>
									 <li><span class="file"><a>拟定</a></span></li>
									 <li><span class="file"><a>尚未分类历史项目单据</a></span></li>

								</ul>
							</li>
							<li><span class="folder" id="bbb">项目审核</span>
								<ul>
									<li><span class="file" id="unex"><a
											onclick="getList('zbqsh','','');">未审核项目</a> </span>
											
											
										
									</li>
									<li><span class="file"><a
											onclick="getList('zbqysh','','');">已审核项目</a> </span>
										
											

										
									</li>
								</ul>
							</li>
							<li><span class="folder">招标比价</span>
								<ul>
									<li><a><span class="file">未招标比价项目</span>
									</a></li>
									<li><a><span  class="file">已招标比价物品</span>
									</a></li>
								</ul>
							</li>
					</ul>
				</ul>
				</div></td>
			<td style="width: 82%;" valign="top"><iframe id="mainframe11"
					style="margin:0px;width:100%;height:550px;" name="admin" src=""
					frameBorder="0"></iframe>
			</td>
		</tr>
	</table>

	<script type="text/javascript">
		$(function() {
			$("#tree").treeview({
				  persist: "location",
				  collapsed: false
		
			});
			$(window).resize(function() {
				setTimeout(function() {
					$("#aadTree").height($(window).height() - 30);
					$("#mainframe11").css({
						"height" : $(window).height() - 5 + "px"
					});
				}, 100);
			});
			$("#aadTree").height($(window).height() - 30);
			$("#mainframe11").css({
				"height" : $(window).height() - 5 + "px"
			});
		});
	</script>
</body>
</html>