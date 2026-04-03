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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>读卡器管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
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
		<script src="<%=basePath%>js/ea/office_ea/CardReader/CardReader.js"></script>
		<script  type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
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
						<th width="150" align="center">
							编号
						</th>
						<th width="80" align="center">
							位置编号
						</th>
						<th width="80" align="center">
							位置名称
						</th>
						<th width="60" align="center">
							进出类型
						</th>

					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${cardReaderID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${code}" />
								 <span id="code" style="display:none">${code}</span>
                                 <span id="cardReaderKey" style="display:none">${cardReaderKey}</span>
							</td>
							<td>
								<span id="code">${code}</span>
							</td>
							<td>
								<span id="positionCode">${positionCode}</span>
							</td>
							<td>
								<span id="positionName">${positionName}</span>
							</td>
							<td>
							  <c:choose>  
                             <c:when test="${orienType=='1'}"><span id="orienType">进</span></c:when>
                              <c:otherwise><span id="orienType">出</span></c:otherwise>
                              </c:choose>
								
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
					value="ea/cardreader/ea_getcardReaderList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 500px; height: 250px; right: 25%; top: 10%"
			id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							读卡器
							<div class="close"></div>
						</div>
					</div>
					<table width="490px" height="250px" border="0" align="center"
						cellpadding="3" cellspacing="0" style="margin-top: 5px;">
						<tr>

							<td align="right" width="100">
								读卡器编号：
							</td>
							<td width="160">
								<input name="cardReader.code" class="input put3"
									id="code" size="10" style="margin-left: 2px;" />
							</td>
							<td align="center">
								进出类型：
							</td>
							<td>
								<select name="cardReader.orienType" style="width:50px;"><option value="">清选择进出类型<option value="1">进<option value="2">出</select>
							</td>

						</tr>
						<tr>
							<td align="right">
								位置编号：
							</td>
							<td>
								<input name="cardReader.positionCode" class="input" id="positionCode"
									size="10" />
							</td>
							<td align="center">
								位置名称：
							</td>
							<td>
								<input name="cardReader.positionName" class="input" id="positionName"
									size="10" />
							</td>

						</tr>

						<tr>
							<td colspan="4" align="center">
								<input type="hidden" name="cardReader.code" id="code1" />
								<input type="hidden" name="cardReader.cardReaderKey"
									id="cardReaderKey" />
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>

				</div>
				<s:token></s:token>
			</form>
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
				<table width="396" id="cataffSearchTable" cellpadding="10" cellspacing="10">
					<tr>
						<td width="123" align="right">
							读卡器编号：
						</td>
						<td width="261">
							<input name="cardReader.code" />
						</td>
					</tr>
					<tr>
						<td align="right">
							进出类型：
						</td>
						<td>
							<select name="cardReader.orienType" style="width:50px;"><option value=""><option value="1">进<option value="2">出</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							位置编号：
						</td>
						<td>
							<input name="cardReader.positionCode" class="input" id="positionCode"
									size="10" />
						</td>
					</tr>
					<tr>
						<td align="right">
								位置名称：
							</td>
							<td>
								<input name="cardReader.positionName" class="input" id="positionName"
									size="10" />
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