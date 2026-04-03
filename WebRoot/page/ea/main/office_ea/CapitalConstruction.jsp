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
		<title>基建管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>  
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
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
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/ea/office_ea/CapitalConstruction.js"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript">
		 var  token = 0;
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
		</script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="40" align="center">
							序号
						</th>
						<th width="170" align="center">
							单位名称
						</th>
						<th width="100" align="center">
							单位负责人
						</th>
						<th width="100" align="center">
							项目
						</th>
						<th width="100" align="center">
							预算费用
						</th>
						<th width="170" align="center">
							承建单位
						</th>
						<th width="100" align="center">
							承建负责人
						</th>

						<th width="100" align="center">
							开工时间
						</th>
						<th width="100" align="center">
							竣工时间
						</th>
						<th width="80" align="center">
							基建类型
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${capitalID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${capitalID}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="unitName">${unitName}</span>
							</td>

							<td>
								<span id="unitprincipal">${unitprincipal}</span>
							</td>
							<td>
								<span id="item">${item}</span>
							</td>
							<td>
								<span id="budgetCost">${budgetCost}</span>
							</td>
							<td>
								<span id="buildUnit">${buildUnit}</span>
							</td>
							<td>
								<span id="buildprincipal">${buildprincipal}</span>
							</td>
							<td>
								<span id="startDate" class="datas">${fn:substring(startDate,
									0, 10)}</span>
							</td>
							<td>
								<span id="endDate" class="datas">${fn:substring(endDate,
									0, 10)}</span>
							</td>
							<td>
								<span id="capitalType">${capitalType=="00"?"基建":"维修"}</span>
								<span id="capitalID" style="display: none">${capitalID}</span>
								<span id="capitalKey" style="display: none">${capitalKey}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/capital/ea_getCapitalConstructionList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss" style="top: 10%"
			 id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							基建管理
							<div class="close"></div>
						</div>
					</div>
					<table width="550" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="550" height="117" border="0" align="center"
									cellpadding="0" cellspacing="0" id="stafftable2"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td width="100" height="30" align="right">
											单位名称：
										</td>
										<td width="148">
											<input type="text" id="unitName" name="capital.unitName" />
										</td>
										<td width="90" align="right">
											单位负责人：
										</td>
										<td width="212">
											<input type="text" id="unitprincipal"
												name="capital.unitprincipal" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											项目：
										</td>
										<td>
											<input name="capital.item" id="item" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											预算费用：
										</td>
										<td>
											<input name="capital.budgetCost" id="budgetCost" type="text"
												class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											承建单位：
										</td>
										<td>
											<input name="capital.buildUnit" id="buildUnit" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											承建负责人：
										</td>
										<td>
											<input name="capital.buildprincipal" id="buildprincipal"
												type="text" class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											开工时间：
										</td>
										<td>
											<input name="capital.startDate" id="startDate"
												onfocus="date(this);" type="text" class="input" size="20" />
										</td>
										<td align="right">
											竣工时间：
										</td>
										<td>
											<input name="capital.endDate" type="text"
												onfocus="date(this);" class="input" id="endDate" size="20" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											基建类型：
										</td>
										<td colspan="3">
											<select name="capital.capitalType" id="capitalType"
												class="select">
												<option value="00">
													基建
												</option>
												<option value="01">
													维修
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="5" align="center">
											<input name="capital.capitalKey" id="capitalKey"
												type="hidden" class="input" size="20" />
											<input name="capital.capitalID" id="capitalID" type="hidden"
												class="input" size="20" />
											<input type="button" class="input-button JQuerySubmit"
												style="cursor: pointer; width: 80px;" value="提交" />
											<input type="button" class="input-button JQueryreturn"
												style="cursor: pointer; width: 80px;" value="取消" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>







				</div>
				<s:token></s:token>
			</form>
		</div>
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%;"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							单位名称：
						</td>
						<td width="261">
							<input name="capital.unitName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							项目：
						</td>
						<td>
							<input name="capital.item" />
						</td>
					</tr>
					<tr>
						<td align="right">
							基建类型：
						</td>
						<td>
							<select name="capital.capitalType" id="capitalType"
								class="select">
								<option value="00">
									基建
								</option>
								<option value="01">
									维修
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
		<iframe name="hidden" width = "100%" height="0"></iframe>
	</body>
</html>