<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>档案类别管理</title>
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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />

		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>

		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script src="<%=basePath%>js/ea/office_ea/archives/CatalogueList.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>

		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
         var archiveid = "";
         var hid='${hid}';
         var opertionID = "";
         var type='${type}';
		</script>

		<style type="text/css">
.table td {
	white-space: nowrap;
	border-right: none;
}

a {
	text-decoration: none;
	cursor: hand;
	color: #000;
}
</style>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme" id="menu">
				<thead>
					<tr class="tablewith">
						<th class="show" width="60" align="center">
							选择
						</th>
						<th width="60" align="center">
							序号
						</th>
						<th width="200" align="center">
							类别名称
						</th>
						<th class="hid" width="90" align="center">
							建档人
						</th>
						<th class="hid" width="150" align="center">
							建档时间
						</th>
						<th class="hid" width="90" align="center">
							修改人
						</th>
						<th class="hid" width="150" align="center">
							修改时间
						</th>
						<th class="operate" width="200" align="center">
							操作
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="parentCata">

						<tr id="${archiveid}" class="level1" align="left">
							<td class="show">
								<input type="radio" name="checkinput" class="JQuerypersonvalue"
									value="${archiveid}" />

							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td class="lev_titl">
								<div style="text-align: left;">
									<img src="<%=basePath%>images/add_s.png" class="add" />
									<span id="name">${name}</span>
								</div>
							</td>
							<td class="hid">
								<span id="createuser">${createuser}</span>
							</td>
							<td class="hid">
								<span id="createtime">${fn:substring(createtime,0,19)}</span>
							</td>
							<td class="hid">
								<span id="modifyuser">${modifyuser}</span>
							</td>
							<td class="hid">
								<span id="modifytime">${fn:substring(modifytime,0,19)}</span>
							</td>
							<td class="operate">
								<span id=""><a
									href="javascript:addSecondLevel('${archiveid}');">添加二级类别</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
									href="javascript:edit('${archiveid}','first')">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
									href="javascript:deleteLevel();">删除</a> </span>
							</td>

						</tr>

						<%
							int number2 = 1;
						%>
						<s:iterator value="childlist" var="child">
							<s:if test="#child.parent==#parentCata.archiveid">
								<tr class="level2" align="left" id="${archiveid}">
									<td class="show">
										<input type="radio" name="checkinput"
											class="JQuerypersonvalue" value="${archiveid}" />

									</td>
									<td>
										<span></span>
										<br />
									</td>
									<td>
										<div style="text-align: left; margin-left: 15px;">
											<img src="<%=basePath%>images/sub_s.png" class="add" />
											<span id="name">${name}</span>
										</div>
									</td>
									<td class="hid">
										<span id="createuser">${createuser}</span>
									</td>
									<td class="hid">
										<span id="createtime">${fn:substring(createtime,0,19)}</span>
									</td>
									<td class="hid">
										<span id="modifyuser">${modifyuser}</span>
									</td>
									<td class="hid">
										<span id="modifytime">${fn:substring(modifytime,0,19)}</span>
									</td>
									<td class="operate">
										<span id="">&nbsp;&nbsp;<a
											href="javascript:edit('${archiveid}','second','${parentCata.name}')">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
											href="javascript:deleteLevel();">删除</a> </span>
									</td>

								</tr>
							</s:if>
							<%
								number2++;
							%>


						</s:iterator>


						<%
							number++;
						%>

					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/catalogue/ea_getCatalogueList.jspa?hid=${hid}&pageNumber=${pageNumber}&type=${type}">
				</c:param>
			</c:import>
		</div>

		<!--添加窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
			id="jqModelAdd">
			<form name="postAddForm" id="postAddForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					添加一级类别
					<div class="close">
					</div>
				</div>
				<table width="100%" cellpadding="5" cellspacing="10" id="addTable">
					<tr>
						<td align="right">
							类别名称：
						</td>
						<td align="left">
							<input type="text" name="archiveCatalogue.name" id="name" />
							<input type="hidden" name="archiveCatalogue.archiveid"
								id="archiveid" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button JQuerySubmit"
								id="toSubmit" value=" 确定 " />
							<input type="button" class="input-button close" 
								value=" 关闭 " />
						</td>

					</tr>
				</table>
			</form>
		</div>



		<!--添加二级窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
			id="jqModelAddSecond">
			<form name="postAddSecondForm" id="postAddSecondForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					添加二级类别
					<div class="close">
					</div>
				</div>
				<table width="100%" cellpadding="10" cellspacing="10"
					id="addSecondTable">
					<tr>
						<td align="right">
							上级类别名称：
						</td>
						<td align="left">
							<input type="text" name="parentname" id="parentname" value=""
								readonly />
							<input type="hidden" name="archiveCatalogue.parent" id="parent" />
						</td>
					</tr>
					<tr>
						<td align="right">
							类别名称：
						</td>
						<td align="left">
							<input type="text" name="archiveCatalogue.name" id="name" />
							<input type="hidden" name="archiveCatalogue.archiveid"
								id="archiveid" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button JQuerySubmit"
								id="toSubmit2" value=" 确定 " />
							<input type="button" class="input-button close" 
								value=" 关闭 " />
						</td>
					</tr>
				</table>
			</form>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="100%" cellpadding="10" cellspacing="10"
					id="cataffSearchTable">
					<tr>
						<td align="right">
							类别名称：
						</td>
						<td align="left">
							<input type="text" name="archivecatalogue.name" />
						</td>
					</tr>
					<tr>
						<td align="left" colspan="2">
							<input type="button" class="input-button" id="tosearch"
								value=" 查询 " />
							<input name="search" type="hidden" value="search" />
							<input name="type" type="hidden" value="${type}" />
						</td>
					</tr>
				</table>

			</form>
		</div>
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>