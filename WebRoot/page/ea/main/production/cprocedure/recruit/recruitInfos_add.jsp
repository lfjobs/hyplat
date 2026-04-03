<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title>招聘信息</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"
	type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/admin_main111.css" />
<link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">

<script
	src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitInfos_add.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/popLayer/css/popstyle.css" />
<script src="<%=basePath%>js/popLayer/js/popLayer.js"
	type="text/javascript"></script>



<script type="text/javascript">

var basePath="<%=basePath%>";
	var notoken = 0;
	var riId = "${recruitInfo.riId}";

	window.onload = function() {

		document.getElementById('tarea').addEventListener('keydown',
				function(e) {

					if (e.keyCode == 13) {
						this.value += "\r\n";
					
					}
				});

	};
</script>
<style type="text/css">
.min-width {
	min-width: 700px;
}

body {
	font-size: 14px;
}
</style>


</head>
<body>
	<form id="mainForm" name="mainForm" method="post">
		<input type="submit" name="submit" style="display: none;">
			<div class="main min-width">
				<div class="top min-width">招聘信息</div>
				<div class="body min-width">

					<div class="showinfo show">
						<table id="productbl" cellspacing="5" cellpadding="5">
							<tr>
								<td align="right">职位名称：</td>
								<td><input type="text" class="inputtext"
										   name="recruitInfo.jobTitle" value="${recruitInfo.jobTitle}" /></td>
							</tr>
							<tr>
								<td align="right" style="width: 30%;">职位类别：</td>
								<td><input type="text" class="inputtext goodsName input3"
									style="width: 40%;" id="positionName"
									name="recruitInfo.positionName"
									value="${recruitInfo.positionName}" readonly />
							<%--		<input
									type="hidden" value="${recruitInfo.positionID}"
									name="recruitInfo.positionID" id="positionID" />--%>
									<input
									type="button"  id="selectpr"
									onclick="getCodeValueFirst()"
									value="选择" class="btn01" />
									<input type="hidden" id="positionCode"
										   name="recruitInfo.positionCode" value="${recruitInfo.positionCode}" />
								</td>
							</tr>
							<tr>
								<td align="right">工作城市：</td>
								<td><input type="text" class="inputtext"
									name="recruitInfo.workCity" value="${city}" /></td>
							</tr>
							<tr>
								<td align="right">工作详细地址：</td>
								<td><input type="text" class="inputtext"
									name="recruitInfo.workPlace" value="${address}" /></td>
							</tr>
							<tr>
								<td align="right">学历要求：</td>
								<td class="check"><input type="hidden"
									value="${recruitInfo.education}" />

									<input type="radio" name="recruitInfo.education"
										   value="大专"  checked/>大专

									<input type="radio" name="recruitInfo.education"
										   value="本科" />本科

									<input type="radio" name="recruitInfo.education"
										   value="硕士" />硕士
									<input type="radio" name="recruitInfo.education"
										   value="EMBA" />EMBA
									<input type="radio" name="recruitInfo.education"
										   value="MBA" />MBA
									<input type="radio" name="recruitInfo.education"
										   value="博士" />博士
									<input type="radio" name="recruitInfo.education"
										   value="不限" />不限
									<input type="radio" name="recruitInfo.education"
										   value="其他" />其他
									</td>
							</tr>
							<tr>
								<td align="right">工作年限：</td>
								<td class="option"><input type="hidden"
									value="${recruitInfo.workYears}" /> <select
									name="recruitInfo.workYears" style="height: 30px;" class="sel">

									<option value="无经验">无经验</option>
									<option value="1年以下">1年以下</option>
									<option value="1-3年">1-3年</option>
									<option value="3-5年">3-5年</option>
									<option value="5年以上">5年以上</option>


								</select></td>
							</tr>

							<tr>
								<td align="right">职位性质：</td>
								<td class="check"><input type="hidden"
									value="${recruitInfo.partorfull}" />
											<input type="radio" name="recruitInfo.partorfull"
												value="全职" checked="checked" />全职
									<input type="radio" name="recruitInfo.partorfull"
										   value="兼职"  />兼职
									<input type="radio" name="recruitInfo.partorfull"
										   value="实习"  />实习
								</td>

							</tr>


							<tr>
								<td align="right">薪资：</td>
								<td class="option"><input type="hidden"
									value="${recruitInfo.salary}" /> <select
									name="recruitInfo.salary" style="height: 30px;" class="sel"
									id="slary">

										<option value="1千以下">1千以下</option>
										<option value="1千-2千">1千-2千</option>
										<option value="2千-4千">2千-4千</option>
										<option value="4千-6千">4千-6千</option>
										<option value="6千-8千">6千-8千</option>
										<option value="8千-1万">8千-1万</option>
										<option value="1万-1.5万">1万-1.5万</option>
										<option value="1.5万-2万">1.5万-2万</option>
										<option value="2万-3万">2万-3万</option>
										<option value="3万-5万">3万-5万</option>
										<option value="5万以上">5万以上</option>

								</select></td>
							</tr>

							<tr>
								<td align="right">任职要求：</td>
								<td><textarea id="tarea" class="inputtext ckTextLength"
										style="height:200px" name="recruitInfo.JobRequire"
										maxlength="1000">${recruitInfo.jobRequire}</textarea></td>
							</tr>

							<tr>
								<td align="right">备注：</td>
								<td><textarea type="text" class="inputtext ckTextLength"
										style="height: 25px" name="recruitInfo.remark" maxlength="500"
										wrap="hard" value="${recruitInfo.remark}"></textarea></td>
							</tr>
						</table>

					</div>
					<div class="bottom">

						<input type="button" class="btn save" style="width: 80px;"
							value="保存" /> <input type="hidden" name="recruitInfo.status"
							id="status"
							value="${recruitInfo.status!=null?recruitInfo.status:'00'}" /> <input
							type="hidden" name="recruitInfo.riId" value="${recruitInfo.riId}" />
						<input type="hidden" name="recruitInfo.rikey"
							value="${recruitInfo.rikey}" />
					</div>
				</div>

			</div>

			</div>
	</form>







	<!-- 选择职位 -->
		<div class="Certificate_alert_">
			<div class="Certificate_alert">
				<div class="left">
					<ul>
						<!-- js拼接 -->
					</ul>
				</div>
				<div class="right">
					<div id="delete">
						<img src="<%=basePath%>page/newMyapp/images/ico_x.png">
					</div>
					<!-- js拼接 -->
				</div>
			</div>
		</div>
	<%--<div id="products" class="popMain">--%>
		<%--<div class="choose-box">--%>
			<%--<div class="choosetitle">--%>
				<%--<span>选择职位</span>--%>
			<%--</div>--%>
			<%--<div class="chooseborder">--%>
				<%--<table width="99%" height="33" id="searchpro" border="0"--%>
					<%--align="center" cellpadding="0" cellspacing="0"--%>
					<%--style="margin-top: 5px; background: #FFFFFF;">--%>
					<%--<tr>--%>
						<%--<td width="100" align="right">职位名称：</td>--%>
						<%--<td width="110"><input name="parameter" class="input"--%>
							<%--id="parameter" size="10" style="margin-left: 2px;" /></td>--%>
						<%--<td height="33"><input type="button" class="btn01"--%>
							<%--id="searchProduct" name="button7" value="查询" /> <input--%>
							<%--type="button" class="btn01" id="selectProduct" name="button5"--%>
							<%--value="确定" /></td>--%>
						<%--<td width="80"><a id="wpsyp" title="0"--%>
							<%--style="cursor: pointer;">上一页</a></td>--%>
						<%--<td width="80"><a id="wpxyp" title="0"--%>
							<%--style="cursor: pointer;">下一页</a></td>--%>
						<%--<td width="100"><a id="wpzy">共&nbsp;&nbsp; <span--%>
								<%--style="color: red" id="wpzycountp"></span>&nbsp;&nbsp;页--%>
						<%--</a></td>--%>
					<%--</tr>--%>
				<%--</table>--%>
				<%--<table width="99%" border="0" align="center" cellpadding="0"--%>
					<%--cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">--%>
					<%--<tr>--%>
						<%--<td width="83%" valign="top" align="left">--%>
							<%--<div id="body_03"--%>
								<%--style="margin-top: 2px; height: 340px; width: 100%; border: 1px solid #ccc; overflow: auto;">--%>
							<%--</div>--%>
						<%--</td>--%>
					<%--</tr>--%>
				<%--</table>--%>

			<%--</div>--%>
			<%--<div class="choose-box-bottom">--%>
				<%--<input type="botton" onclick="hide('products')" value="关闭" />--%>
			<%--</div>--%>
		<%--</div>--%>
	<%--</div>--%>




	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>


</body>
</html>