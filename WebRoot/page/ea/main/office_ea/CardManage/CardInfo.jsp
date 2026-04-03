<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>卡信息管理</title>
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/ea/office_ea/CardManage/CardInfo.js"></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
         var cardReaderID = "";
		</script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>
		<style type="text/css">
a {
	text-decoration: none;
}
</style>

	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							卡号
						</th>
						<th width="80" align="center">
							有效期
						</th>
						<th width="80" align="center">
							使用状态
						</th>
						<th width="60" align="center">
							卡使用类型
						</th>
						<th width="80" align="center">
							卡类型
						</th>
						<th width="80" align="center">
							车牌号
						</th>
						<th width="150" align="center">
							发卡时间
						</th>

					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${cardInfoID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${cardInfoID}" />
								<span id="cardInfoID" style="display: none">${cardInfoID}</span>
								<span id="cardInfoKey" style="display: none">${cardInfoKey}</span>
							</td>
							<td>
								<span id="cardCode">${cardCode}</span>
							</td>
							<td>
								<span id="validityTime">${validityTime}天</span>
							</td>
							<td>
								<c:choose>
									<c:when test="${status=='1'}">正常</c:when>
									<c:when test="${status=='-1'}">销卡</c:when>
									<c:otherwise>挂失</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${statesType=='1'}">正式卡</c:when>
									<c:otherwise>临时卡</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${cardType=='01'}">epc</c:when>
									<c:when test="${cardType=='02'}">12.4G</c:when>
									<c:otherwise>mifare</c:otherwise>
								</c:choose>
							</td>
							<td>
								<span id="carNum">${carNum}</span>
							</td>
							<td>
								<span id="createTime">${fn:substring(createTime,0,19)}</span>
							</td>

						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/cardmanage/ea_getCardInfoList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" cellpadding="10" cellspacing="10"
					id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							卡号：
						</td>
						<td width="261">
							<input name="cardInfo.cardCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							有效期：
						</td>
						<td>
							<input name="cardInfo.validityTime" />
							天
						</td>
					</tr>
					<tr>
						<td align="right">
							使用状态：
						</td>
						<td>
							<select name="cardInfo.status" style="width: 125px;"
								id="status">
								<option value="">
									选择卡使用状态
								</option>
								<option value="1">
									正常
								</option>
								<option value="0">
									挂失
								</option>
								<option value="-1">
									销卡
								</option>

							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							卡类型：
						</td>
						<td>
							<select name="cardInfo.statesType" style="width: 125px;"
								id="cardType">
								<option value="">
									选择卡使用类型
								</option>
								<option value="1">
									正式卡
								</option>
								<option value="2">
									临时卡
								</option>

							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							卡种类：
						</td>
						<td>
							<select name="cardInfo.cardType" style="width: 125px;"
								id="cardType">
								<option value="">
									选择卡类型
								</option>
								<option value="01">
									epc
								</option>
								<option value="02">
									12.4G
								</option>
								<option value="03">
									mifare
								</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>