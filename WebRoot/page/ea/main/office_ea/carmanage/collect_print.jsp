<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/carmanage/collect_print.css" />
</head>
<body>
	<div class="collect">
		<div class="stair">
			<div class="second">
				<div class="three-level-one">
					<div class="pyatyi-one">
						<span>${obj[5] }</span>
					</div>
					<div class="pyatyi-two">
						<span>（</span><span>${obj[11] }</span><span>）</span>
					</div>
				</div>
				<div class="three-level-two">
					<div style="width: 20%; float:left;">
						<span>---</span>
					</div>
					<div style="width: 59%;float:left;">
						<span>出门收费单</span>
					</div>
					<div style="width: 20%;float:right;">
						<span>---</span>
					</div>
				</div>
				<div class="three-level-three">
					<table>
						<tr>
							<td>编号:</td>
							<td>${obj[1] }</td>
						</tr>
						<tr>
							<td>车牌号:</td>
							<td>${obj[9] }</td>
						</tr>
						<tr>
							<td>进门时间:</td>
							<td>${obj[2] }</td>
						</tr>
						<tr>
							<td>出门时间:</td>
							<td>${obj[3] }</td>
						</tr>
						<tr>
							<td>停留时间:</td>
							<td>${obj[10] }</td>
						</tr>
						<tr>
							<td>支付类型:</td>
							<td>现金支付</td>
						</tr>
						<tr>
							<td>交易金额(元):</td>
							<td>
								<span>${obj[12] }</span>
								<span>元</span>
							</td>
						</tr>
						<tr>
							<td>停车流水号:</td>
							<td>${obj[13] }</td>
						</tr>
					</table>
				</div>
				<div class="three-level-four">
					<span>温馨提示：仅供收取车辆停放使用，车辆及车内物品自理。</span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>