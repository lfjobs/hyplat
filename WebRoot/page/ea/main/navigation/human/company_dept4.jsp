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
		<title>公司部门人事4</title>
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
						<li><span class="folder">部门社会人力 </span>
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
