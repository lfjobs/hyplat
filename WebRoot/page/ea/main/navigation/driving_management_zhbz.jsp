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
<title>教务管理科</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
	<table
		style="width: 100%; border: 0; text-align: left; padding: 0; margin: 0"
		class="table03">
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>综合查询</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?total=total'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">综合信息中心</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>科一（理论）</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='http://localhost:8080/hyplat//ea/clinch/ea_getListContactUser.jspa?'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">基本信息录入</span>
										</div></td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">收集管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/jiaowutheory_list.jsp?docstatusRequest=01'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/cultivateManager.jsp'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">考试管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=01'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">合格管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/archive/ea_getArchiveList.jspa?catemodule=theory&type=1'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">档案管理</span>
										</div></td>

								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>科二（桩考）</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='http://localhost:8080/hyplat//ea/clinch/ea_getListContactUser.jspa?'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">基本信息录入</span>
										</div></td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=02&title=06'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">收集管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/jiaowutheory_list.jsp?docstatusRequest=02'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/examzhuangkao.jsp'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">考试管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=02'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">合格管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/archive/ea_getArchiveList.jspa?catemodule=piletest&type=1'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">档案管理</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>科二（场地）</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='http://localhost:8080/hyplat//ea/clinch/ea_getListContactUser.jspa?'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">基本信息录入</span>
										</div></td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=02&title=09'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">收集管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/jiaowutheory_list.jsp?docstatusRequest=03'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/examchangdi.jsp'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">考试管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=03'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">合格管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/archive/ea_getArchiveList.jspa?catemodule=yard&type=1'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">档案管理</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>科三（路考）</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
								    <td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='http://localhost:8080/hyplat//ea/clinch/ea_getListContactUser.jspa?'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">基本信息录入</span>
										</div></td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=02&title=12'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">收集管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/jiaowutheory_list.jsp?docstatusRequest=04'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/examlukao.jsp'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">考试管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=04'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">合格管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>ea/archive/ea_getArchiveList.jspa?catemodule=roadtest&type=1'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">档案管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">报表管理</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
