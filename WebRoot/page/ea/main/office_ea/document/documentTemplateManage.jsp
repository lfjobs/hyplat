<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<title>公文模板管理</title>

		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>


		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>

		<script
			src="<%=basePath%>js/ea/office_ea/document/documentTamplateManage.js"></script>

		<script type="text/javascript">           
         var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  templateId = '';
         var receiptType='${receiptType}';
        </script>


	</head>
	<body>

		<!--模板列表 -->

		<div id="draft">
			<table class="JQueryflexme">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="200" align="center">
							模板名称
						</th>
						<th width="100" align="center">
							文件格式
						</th>
						<th width="100" align="center">
							文件扩展名
						</th>

						<th width="150" align="center">
							更新时间
						</th>
						<th width="150" align="center">
							创建人
						</th>

					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr class="docs" id="${templateId}">
							<td class="td_bg01">
								<input type="radio" name="radioGroup" class="JQuerypersonvalue"
									value="${templateId}" />
							</td>
							<td class="td_bg01">
								<span id="fileShowName">${fileShowName}</span>
							</td>
							<td class="td_bg01">
								<span id="fileType">${fileType}</span>
							</td>
							<td class="td_bg01">
								<span id="ext">${ext}</span>
							</td>
							<td class="td_bg01">
								<span id="time">${fn:substring(time,0,19)}</span>
							</td>
							<td class="td_bg01">
								<span id="time">${createrName}</span>
								<span style="display: none;" id="templatePath">${templatePath}</span>
								<span style="display: none;" id="templateId">${templateId}</span>
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
					value="ea/documenttemplate/ea_getDocTemplateList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>

		<!-- 新建和更新模板窗口 -->
		<form id="templateForm" name="templateForm">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModel">
				<input type="hidden"  id="receiptType" name="documentTemplate.receiptType" value="${receiptType}"/>
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">${receiptType}
					<span id="winname"></span>
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="10" id="temptable"  style="width: 400px;">
						<tr>
							<td width="30%" align="right">
								模板名称：
							</td>
							<td align="left" width="70%">
								<input type="text" name="documentTemplate.fileShowName"
									class="put3 filename" id="fileShowName" style="width: 150px;" />
								<input type="hidden" name="fileShowName0" id="fileShowName0"/>
							</td>
						</tr>
						<tr>
							<td align="right">
								文件格式：
							</td>
							<td class="new" align="left">
								<select id="fileTypes" name="documentTemplate.fileType"
									style="width: 155px;" class="put3">
									<option value="W" selected="selected">
										Word
									</option>
									<option value="E">
										Excel
									</option>

								</select>
								<input type="hidden" name="fileTypes0" id="fileTypes0" />
							</td>
							<td class="update" style="display: none;" align="left">
								<span id="fileType"></span>
							</td>
						</tr>


						<tr>
							<td align="right">
								模板设置：
							</td>
							<td align="left">
								<input type="button" class="input-button" value="模板设置"
									 id="setTemplate" style="margin:0px;" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="button" class="input-button" id="confirm"
									value="确定" />
								<input type="button" class="input-button close" value="关闭" />
								<input type="hidden" id="templatePaths" value=""
									name="documentTemplate.templatePath" />
								<input type="hidden" id="templateId" value=""
									name="documentTemplate.templateId" />
							</td>

						</tr>
					</table>
				</center>
			</div>
		</form>


		<form id="searchForm" name="searchForm" >
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询模板
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="10" id="templateSearchtab">
						<tr>
							<td align="right">
								模板名称：
							</td>
							<td align="left">
								<input type="text" id="filenamess"
									name="documentTemplate.fileShowName" size="19" />
							</td>
						</tr>

						<tr>
							<td align="right">
								文件格式：
							</td>
							<td align="left">
								<select name="documentTemplate.fileType" id="fileTypess"
									style="width: 150px;">
									<option value="">
										请选择文件格式
									</option>
									<option value="W">
										Word
									</option>
									<option value="E">
										Excel
									</option>

								</select>
							</td>
						</tr>


						<tr>

							<td colspan="2" align="center">
								<input type="button" value="查询" class="input-button"
									id="tosearch" />
								<input name="search" type="hidden" value="search" />
							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>
		<iframe name="hidden" width="100%" height="0"></iframe>

	</body>
</html>
