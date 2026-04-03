<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>职代会生产管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
<script type="text/javascript">
	addEvent(window, 'load', initCorners);
	function initCorners() {
		var setting = {
			tl : {
				radius : 6
			},
			tr : {
				radius : 6
			},
			bl : {
				radius : 6
			},
			br : {
				radius : 6
			},
			antiAlias : true
		};
		curvyCorners(setting, ".roundedCorners");
	}

</script>
</head>
<body>

	<div>
		<table>
			<tr>
				<td><table>
				<tr>
							<td><table>
									<tr>
										<td>
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>生产合同管理</strong>
											</div>
										</td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td><table>
												<tr>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>生产合同流转</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>生产合同查询</span>
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
							<td><table>
									<tr>
										<td>
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>公司职代会管理</strong>
											</div>
										</td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td><table>
												<tr>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>公司职代会(人事)管理</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>公司职代会(办公室)管理</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>公司职代会(财务)管理</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>公司职代会(生产)管理</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>公司职代会(营销)管理</span>
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
							<td><table>
									<tr>
										<td>
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>集团职代会管理</strong>
											</div></td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td><table>
												<tr>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>集团职代会(人事)管理</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>集团职代会(办公室)管理</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>集团职代会(财务)管理</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>集团职代会(生产)管理</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>集团职代会(营销)管理</span>
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
				</td>
			</tr>
		</table>
	</div>
</body>
</html>