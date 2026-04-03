<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公司</title>
<%@include file="/page/common1.jsp"%>

<link rel="stylesheet" href="<%=basePath%>/css/dropdown/droplinebar.css" type="text/css"></link>
<script src="<%=basePath%>/js/dropdown/droplinemenu.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/dropdown/extendMenu.js" type="text/javascript"></script>

<script type="text/javascript">
	var basePath="<%=basePath%>";
</script> 
</head>
<body >
	<!-- 无限级导航菜单div -->
	<div id="mydroplinemenu" class="droplinebar">
		<ul class="zero">
			<li><a href="javascript:;">公司(后台)</a>
				<ul class="one"> 
					<li><a href="javascript:;"   onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/corganization/ea_getCompanyMessage.jspa?type=p'" >公司部门管理</a>
					</li>
					<li><a href="javascript:;"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/ccompanyDetil.jsp'" >公司信息管理</a>
					</li>
					<li><a href="javascript:;"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/ccaccount/ea_getListCAccount.jspa'" >账号管理</a>
					</li>
					<li><a href="javascript:;"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/ccrole/ea_getListCRole.jspa'" >角色管理</a>
					</li>
					<li><a href="javascript:;"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/cclogbook/ea_getListCLogBook.jspa'" >系统日志查询</a>
					</li>
					<li><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>ea/permissionupdaterecord/ea_getListPermissionupdateRecord.jspa'" >系统权限修改记录</a>
					</li>
				</ul>
			</li>
			<li><a href="javascript:;">公司(人事)</a>
			</li>
			<li><a href="javascript:;">公司(办公室)</a>
			</li>
			<li><a href="javascript:;" >公司(财务)</a>
				<!-- <ul class="one"> 
				<li><a href="javascript:;"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/finace_a.jsp'">企业财务管理</a>
				</li>
				<li><a href="javascript:;"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/finace_g.jsp'">税务管理</a>
				</li>
				</ul> --></li>
			<li><a href="javascript:;" >公司(生产)</a></li>
			<li><a href="javascript:;" >公司(营销)</a></li>
		</ul>
	</div>
	<!-- 记录菜单位置div -->
	<div id="navigationRecords" class="navigationRecordsClass">
		<ul>
			<li><a href="#">您当前所在位置：</a></li>
			<li><a href="#">&gt;</a></li>
		</ul>
	</div>
  
</body>
</html>
