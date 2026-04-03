<%@ page language="java" pageEncoding="UTF-8" %>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<title>个人办公室</title>
<%@include file="/page/common1.jsp" %> 

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
			<li><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/human/office/SersonnelSystem_person.jsp'">个人人事办公</a>
			</li>
			<li><a href="javascript:;">个人办公（办公）</a></li>
			<li><a href="javascript:;" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/finace_ho1.jsp'">个人财务办公</a>
			</li>
			<li><a href="javascript:;" onclick="">个人生产办公</a>
			</li>
			<li><a href="javascript:;" onclick="">个人营销办公</a>
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
</body>
</html>
