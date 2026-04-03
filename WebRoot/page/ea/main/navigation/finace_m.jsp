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
<title>总账管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<script>
function clickAction(action,parater){
if(parater == '1'){
var treeID = '<%=session.getAttribute("organizationID")%>';
 window.location.href= action+treeID;
 return;
 }
 window.location.href= action;
}
</script>
</head>
<body>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="120" rowspan="2">
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong>总账管理</strong>
				</div>
				<td width="55"><div class="na_back_img_jt_hx"></div></td>
			</td>
				<td><table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="110">
								<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/vaccount/ea_getVaccountList.jspa?zz=00'"></div>
								<div class="center_a">总账管理</div></td>
							<td width="110">
								<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/vaccount/ea_getVaccountList.jspa?zz=01'"></div>
								<div class="center_a">明细账管理</div></td>
							<td width="110">
								<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/vsequence/ea_getVsequenceList.jspa'"></div>
								<div class="center_a">序时账</div></td>
							<td width="110">
								<div class="na_back_img"
									onclick="document.location.href='<%=basePath%>/ea/csbjects/ea_toPage.jspa'"></div>
								<div class="center_a">科目余额表</div></td>
						</tr>
					</table>
			</td>
		</tr>
	</table>
</body>
</html>
