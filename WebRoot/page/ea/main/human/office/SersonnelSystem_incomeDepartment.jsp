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
<title>创收平台</title>
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
			<li><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/Personnel_personnel.jsp'">(人事)</a>
			</li>
			<li><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/Office_officeManager.jsp'">(办公室)</a></li>
			<li><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/finace_c1.jsp'">(财务)</a>
			</li>
			<li><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/incomeDepartment_production.jsp'">(生产)</a>
				</li>
			<li><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/SersonnelSystem_manage_a.jsp'">(营销)</a>
			</li>
		</ul>
	</div>
<!-- 记录菜单位置div -->
	<div id="navigationRecords" class="navigationRecordsClass">
		<ul>
			<li><a href="#">您当前所在位置：</a></li>
			<li><a href="#">&gt;</a></li>
		</ul>
	</div>
<script>
	var topWin= (function (p,c){
		while(p!=c){
		    c = p;
		    p = p.parent;
		}
		return c;
	})(window.parent,window);
	var orgName=$(topWin.frames["leftFrame"].document).find("span#${organizationID}").text();
	$("div#mydroplinemenu").find("a").prepend(orgName);
</script>
</body>
</html>

