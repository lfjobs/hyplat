<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>卡记录</title>
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
		<script src="<%=basePath%>js/ea/office_ea/CardManage/CardRecord.js"></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
         var cardCode = '${cardCode}';
         var cardReaderID = "";
		</script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="100" align="center">
							卡号
						</th>
						<th width="100" align="center">
							车牌号
						</th>
						<th width="150" align="center">
							出入时间
						</th>
						<th width="150" align="center">
							读卡器
						</th>
						<th width="80" align="center">
							操作人员
						</th>
						<th width="150" align="center">
							创建时间
						</th>
						<th width="150" align="center">
							照片
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
							</td>
							<td>
								<span id="cardCode">${cardCode}</span>
							</td>
							<td>
								<span id="carNum">${carNum}</span>
							</td>
							<td>
								<span id="enterTime">${fn:substring(enterTime,0,19)}</span>
							</td>
							<td>
								<span id="readerEnterName">${readerEnterName}</span>
							</td>
							<td>
								<span id="createUser">${fn:substring(createUser,0,19)}</span>
							</td>
							<td>
								<span id="createDate">${fn:substring(createDate,0,19)}</span>
							</td>
							<td>
								<s:if test="videoPic==null||videoPic==''">无</s:if>
								<s:else>
									<span id="look" onclick="lookImage('${videoPic}');"><a
										href="#">查看</a>
									</span>
								</s:else>
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
					value="ea/cardmanage/ea_getCardRecordList.jspa?pageNumber=${pageNumber}&search=${search}&cardCode=${cardCode}">
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
						<td align="right">
							读卡器：
						</td>
						<td>
							<select id="readerEnter" name="cardRecord.readerEnter" style="width: 127px;">
							</select>
						</td>
					</tr>

					<tr>
						<td align="right">
							操作人员：
						</td>
						<td>
							<input name="cardRecord.createUser" class="input" id="createUser"
								size="18" />
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