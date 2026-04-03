<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学习进度</title>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/css/ea/jx.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery.js"
	type="text/javascript"></script>

<script type="text/javascript">
var basePath = "<%=basePath%>";

	$(function() {
		$(".tab").find("li").click(function() {
			$(".selected").removeClass("selected");
			$(this).addClass("selected");
			var id = $(this).attr("id");
			if (id == "showrate") {
				$(".showpic").addClass("hidmodel");

			} else {
				$(".showrate").addClass("hidmodel");
			}
			$("." + id).removeClass("hidmodel");

		});

	});
</script>
</head>

<body>
	<div class="main">
		<div class="top" style="text-align:center;min-width:680px;">项目跟踪</div>

		<div class="jx2 body" style="min-width:680px;height:auto;">
			<div class="ttab">
				<ul class="tab">
					<li id="showrate" class="selected left info" style="width:50%;">项目完成率</li>
					<li id="showpic" style="width:49.7%;">项目完成图</li>
				</ul>
			</div>

			<div class="showrate">
				<table class="ratetable" style="border-top:none;">
					<tr>
						<td align="right">客户：</td>
						<td>${staffTrack.staffName}</td>
						<td rowspan="3" align="center"
							style="border-left:1px solid #ccc;padding-top:10px;padding-bottom:10px;">
							<s:if test='staffTrack.headimage!=null'>
								<img src="<%=basePath%>${staffTrack.headimage}" width="150px"
									height="150px" />
							</s:if> <s:else>
								<img src="<%=basePath%>images/head.png" width="150px"
									height="150px" />

							</s:else></td>
					</tr>

					<tr>
						<td align="right">电话：</td>
						<td>${staffTrack.tel}</td>
					</tr>
					<tr>
						<td align="right">身份证：</td>
						<td>${staffTrack.identiCard}</td>

					</tr>
					<tr>
						<td align="right">报名时间：</td>
						<td colspan="2">${fn:substring(staffTrack.enrollDate,0,19)}</td>
					</tr>


					<tr>
						<td align="right">主项目：</td>
						<td colspan="2">${staffTrack.ppName}</td>
					</tr>

					<tr>
						<td align="right">子项目：</td>
						<td colspan="2">${staffTrack.subName}</td>
					</tr>
					<tr>
						<td align="right">项目总数：</td>
						<td colspan="2">${staffTrack.pronum}</td>
					</tr>
					<tr>
						<td align="right">完成率：</td>
						<td colspan="2">${staffTrack.completerate}</td>
					</tr>

				</table>
			</div>
			<div class="showpic hidmodel">
				<article>

					<ul class="con">

						<c:forEach items="${list}" var="item">
							<li class="big">
								<h3 class="title">${item.goodsName}</h3> <c:forEach
									items="${listsub}" var="subitem">
									<c:if test="${subitem.parentId eq item.ppID}">
										<dl>
											<c:if test="${mapselect[subitem.ppID]==1}">
												<dt class="con_01 red"></dt>
											</c:if>
											<c:if test="${mapselect[subitem.ppID]==0}">
												<dt class="con_01 grey"></dt>

											</c:if>

											<dd>${subitem.goodsName}</dd>
										</dl>
									</c:if>
								</c:forEach>
								<div class="clear"></div></li>

						</c:forEach>


					</ul>
				</article>
			</div>
		</div>
	</div>
</body>
</html>
