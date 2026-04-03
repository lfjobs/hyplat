<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/salary/salaryLevelAdd.css"/>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/human/salaryNew/salaryLevelAdd.js" type="text/javascript" charset="utf-8"></script>
	<title>&lrm;</title>
</head>
<body>
<header>
	<ul class="clearfix">
		<li>
			<a onclick="javascript: window.history.go(-1);return false;"  target="_self">

				<img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
			</a>
		</li>
		<li>
			薪级设置添加
		</li>
		<li>
			保存
		</li>
	</ul>
</header>
<form name="form" id="form" method="post">
	<input type="submit" name="submit" style="display: none;"/>

	<div class="content">

		<ul class="main-ul">
			<%--<li><div><label>级别序号</label><input type="text" /></div></li>
			<li><div><label>级别编码</label><input type="text" /></div></li>
			<li><div class="div-1"><label>基本保障工资</label></div></li>
			<li><div><label>基本工资</label><input type="text" /></div></li>
			<li><div><label>职能工资</label><input type="text" /></div></li>
			<li><div><label>职责工资</label><input type="text" /></div></li>
			<li><div><label>竞职金工资</label><input type="text" /></div></li>
			<li><div><label>保密工资</label><input type="text" /></div></li>
			<li><div><label>调平金额</label><input type="text" /></div></li>
			<li><div class="div-0"><label>签到考勤加班</label></div></li>
			<li><div class="div-1"><label>签到考勤</label></div></li>
			<li><div><label>迟到</label><input type="text" /></div></li>
			<li><div><label>早退</label><input type="text" /></div></li>
			<li><div class="div-1"><label>加班</label></div></li>
			<li><div><label>8小时外加班</label><input type="text" /></div></li>
			<li><div><label>节假日加班</label><input type="text" /></div></li>
			<li><div class="div-1"><label>请假</label></div></li>
			<li><div><label>病假</label><input type="text" /></div></li>
			<li><div><label>事假</label><input type="text" /></div></li>--%>
		</ul>





		<%--<div class="div-bottom first-bottom">--%>
			<%--<p class="saveDraft">--%>
				<%--保存草稿--%>
			<%--</p>--%>

		<%--</div>--%>

	</div>

	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
</form>






<%--<!--表单提示-->--%>
<%--<div class="div-tingyong div-dqd">--%>
	<%--<div class="box">--%>
		<%--<p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>--%>
		<%--<div class="div-box">--%>
			<%--<p class="titlep">更换模板会清空内容确定更换么？</p>--%>
			<%--<div class="clearfix">--%>
				<%--<p class="left close-tingyong">取消</p>--%>
				<%--<p class="right close-confirm">确定</p>--%>
			<%--</div>--%>
		<%--</div>--%>
	<%--</div>--%>
<%--</div>--%>
<%--<div class="loading">--%>
	<%--<div class="div-box">--%>
		<%--<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt="" />--%>
		<%--<p>保存中...</p>--%>
	<%--</div>--%>
<%--</div>--%>
</body>
<script type="text/javascript">
	var basePath = "<%=basePath%>";




</script>
</html>
