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
		<title>公司部门人事</title>
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
						<li><span class="folder">部门机构</span>
							<ul>
								<c:if test="${param.menu eq 'human' }">
								<li><span class="folder">机构管理</span>
									<ul>
										<li><a target="mainframe" href='<%=basePath%>page/ea/main/human/organization/organization_subordinate.jsp'><span class="file">机构设置</span> </a>
										</li>
										<li><a target="mainframe"  href='<%=basePath%>page/ea/main/human/organization/orgchart.jsp'><span class="file">组织机构图</span> </a>
										</li>
										<li><a target="mainframe"  href="<%=basePath%>page/ea/main/human/organization/orgcharall.jsp?comy=org"><span class="file">机构图</span> </a>
										</li>
									</ul>
								</li>
								</c:if>
								<c:if test="${param.menu ne 'human' }">
									<li><a target="mainframe" href='<%=basePath%>page/ea/main/human/organization/organization_subordinate.jsp'><span class="file">机构设置</span> </a>
								</c:if>
								<li><span class="folder">部门职能描述</span>
									<ul>
										<li><span class="file">部门管理</span></li>
										<li><span class="file">部门职能描述说明书</span></li>
										<li><span class="file">部门职能战区图</span></li>
										<li><span class="file">部门工作流程</span></li>
										<li><span class="file">部门工作量化成果指标管理</span></li>
										<li><span class="file">部门职能要求</span></li>
										<li><span class="file">部门安全责任书</span></li>
										<li><span class="file">部门目标任务设定</span></li>
									</ul></li>

								<li><span class="folder">岗位设置管理</span>
									<ul>
										<li><span class="file">岗位管理</span></li>
										<li><span class="file"> 岗位说明书及要求</span></li>
										<li><span class="file">岗位战区图</span></li>
										<li><span class="file">岗位工作流程</span></li>
										<li><span class="file">岗位工作量化成果指标管理</span></li>
										<li><span class="file">岗位安全责任书</span></li>
										<li><span class="file">岗位薪酬设定</span></li>
										<li><span class="file">岗位授权管理</span></li>
									</ul>
								</li>
								<li><span class="folder">人员配备管理</span>
									<ul>
										<li><a target="mainframe"  href='<%=basePath%>/ea/corganization/ea_getCompanyMessage.jspa?type=t&mold=m'><span class="file">微型企业人员配备</span></a></li>
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
								<li><span class="folder">生产管理</span>
									<ul>
										<li><a href="<%=basePath%>page/ea/main/navigation/product_procedure.jsp?fiveClear=1"><span class="file">人事生产</span></a></li>
										
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
