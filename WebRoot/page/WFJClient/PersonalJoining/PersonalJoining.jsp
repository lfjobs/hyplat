<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人加盟商城业主会员</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/WFJClient/style2.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
</head>
<body>
	<div class="wfj10_001">
				<iframe id="indexTop" align="center" width="100%" height="46px"
					src="<%=basePath%>page/WFJClient/Template/Top/Label.jsp"
					frameborder="no" border="0" marginwidth="0" marginheight="0"
					scrolling="no"></iframe>
		<div class="wfj10_001_content">
			<div class="wfj10_001_width">
				<div class="wfj10_001_border">
					<div class="wfj10_001_img">
						<img
							src="<%=basePath%>images/WFJClient/PersonalJoining/wfj10_001_01.jpg" />
					</div>
					<div class="wfj10_001_info">
						<table>
							<tr>
								<td align="left"><a href="javascript:;">合伙创业商城业主会员</a></td>
								<td align="right"><a href="javascript:;">查看详情</a></td>
							</tr>
							<tr>
								<td colspan="2" align="right"><a href="<%=basePath%>/ea/wfjshop/ea_getgrxx.jspa?indus=1">我要加入</a></td>
							</tr>
							<tr>
								<td colspan="2" align="left"><a href="<%=basePath%>/ea/wfjshop/ea_getgrxx.jspa?indus=1">￥1万</a></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="wfj10_001_border">
					<div class="wfj10_001_img">
						<img
							src="<%=basePath%>images/WFJClient/PersonalJoining/wfj10_001_02.jpg" />
					</div>
					<div class="wfj10_001_info">
						<table>
							<tr>
								<td align="left"><a href="javascript:;">微分金店商城业主会员</a></td>
								<td align="right"><a href="javascript:;">查看详情</a></td>
							</tr>
							<tr>
								<td colspan="2" align="right"><a href="<%=basePath%>/ea/wfjshop/ea_getgrxx.jspa?indus=2">我要加入</a></td>
							</tr>
							<tr>
								<td colspan="2" align="left"><a href="<%=basePath%>/ea/wfjshop/ea_getgrxx.jspa?indus=2">￥1680元</a></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="wfj10_001_border">
					<div class="wfj10_001_img">
						<img
							src="<%=basePath%>images/WFJClient/PersonalJoining/wfj10_001_03.jpg" />
					</div>
					<div class="wfj10_001_info">
						<table>
							<tr>
								<td align="left"><a href="javascript:;">代理商商城业主会员</a></td>
								<td align="right"><a href="javascript:;">查看详情</a></td>
							</tr>
							<tr>
								<td colspan="2" align="right"><a href="<%=basePath%>/ea/wfjshop/ea_getgrxx.jspa?indus=3">我要加入</a></td>
							</tr>
							<tr>
								<td colspan="2" align="left"><a href="<%=basePath%>/ea/wfjshop/ea_getgrxx.jspa?indus=3">￥100元</a></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="wfj10_001_border">
					<div class="wfj10_001_img">
						<img
							src="<%=basePath%>images/WFJClient/PersonalJoining/wfj10_001_04.jpg" />
					</div>
					<div class="wfj10_001_info">
						<table>
							<tr>
								<td align="left"><a href="javascript:;">消费者商城业主会员</a></td>
								<td align="right"><a href="javascript:;">查看详情</a></td>
							</tr>
							<tr>
								<td colspan="2" align="right"><a href="<%=basePath%>/ea/wfjshop/ea_getgrxx.jspa?indus=4">我要加入</a></td>
							</tr>
							<tr>
								<td colspan="2" align="left"><a href="<%=basePath%>/ea/wfjshop/ea_getgrxx.jspa?indus=4">￥10元</a></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#indexTop')
				.load(
						function() {
							var doc = document.getElementById("indexTop").contentWindow.document;
							doc.getElementById("topbar_title").innerHTML = "个人加盟商城业主会员（购买）";
							doc.getElementById("return").onclick = function() {
								window.history.go(0);
							}
						});
	</script>
</body>
</html>
