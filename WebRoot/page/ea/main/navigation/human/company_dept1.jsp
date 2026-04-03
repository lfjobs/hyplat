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
		<title>公司部门人事1</title>
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
						<li><span class="folder">部门招聘管理</span>
							<ul>
								<li><span class="folder">部门招聘规划</span>
									<ul>
										<li><span class="folder">部门招聘岗位规划</span>
											<ul>
												<li><a target="mainframe" href='<%=basePath%>ea/recruitment/ea_getRecruitmentList.jspa'><span class="file">岗位统记</span></a></li>
												<li><span class="file">岗位编制</span></li>
												<li><span class="file">个人规划管理</span></li>
											</ul>
										</li>
										<li><span class="folder">部门薪酬规划</span>
											<ul>
												<li><span class="file">招聘薪酬调查</span></li>
												<li><span class="file">薪酬极差设定</span></li>
											</ul>
										</li>
										<li><span class="folder">部门人力投资规划</span>
											<ul>
												<li><span class="file">人力成本图</span></li>
												<li><span class="file">人力成本收入百分比</span></li>
											</ul>
										</li>
									</ul>
								</li>
								<li><span class="folder">部门招聘宣传</span>
									<ul>
										<li><span class="folder">部门招聘资料</span>
											<ul>
												<li><span class="file">文字设计</span></li>
												<li><span class="file">图片设计</span></li>
												<li><span class="file">视频设计</span></li>
											</ul>
										</li>
										<li><span class="folder">部门宣传方式</span>
											<ul>
												<li><span class="folder">网络宣传</span>
													<ul>
														<li><span class="file">招聘网站宣传</span></li>
														<li><span class="file">企业网站宣传</span></li>
														<li><span class="file">校园网站宣传</span></li>
													</ul>
												</li>
												<li><span class="file">户外广告宣传</span></li>
												<li><span class="folder">媒体宣传</span>
													<ul>
														<li><span class="file">电视</span></li>
														<li><span class="file">报纸</span></li>
													</ul>
												</li>
											</ul>
										</li>
									</ul>
								</li>

								<li><span class="folder">部门招聘登记管理</span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>/ea/tresume/ea_getListPage.jspa?aa=aa&cstaff.status=99'><span class="file">人才简历库</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?aa=aa&cstaff.status=99'><span class="file">部门社会人力</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?aa=bb&cstaff.status=99'><span class="file">部门招聘登记管理</span></a></li>
										<li><a target="mainframe" href='<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=0'><span class="file">部门选择通知面试</span></a></li>
									</ul>
								</li>
								<li><span class="folder">部门招聘面试</span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>/ea/saudition/ea_getauditionList.jspa?status=1'><span class="file">部门面试登记管理</span></a></li>
										<li><span class="folder">部门面试考试结果</span>
											<ul>
												<li><span class="file">面试老师管理</span></li>
												<li><span class="file">面试题库管理</span></li>
												<li><span class="file">面试考试管理</span></li>
												<li><span class="file">面试结果分数管理</span></li>
												<li><a target="mainframe" href='<%=basePath%>/ea/saudition/ea_getAuditionkb.jspa?start=3'><span class="file">面试结果管理</span></a></li>
											</ul>
										</li>
										<li><span class="folder">部门入职管理</span>
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
													<li><a target="mainframe" href='<%=basePath%>ea/corganization/ea_getCompanyMessage.jspa?type=t&mold=m'><span class="file">正式入职人员</span></a></li>
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
