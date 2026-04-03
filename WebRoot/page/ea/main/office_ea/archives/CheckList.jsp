<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>盘点</title>
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
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/ea/office_ea/archives/CheckList.js"></script>
		<script src="<%=basePath%>js/common/organizationTree.js"></script>

		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
         var checkid= "";
         var type = '${type}';
         var  catemodule= '<%=session.getAttribute("module")%>';
		</script>

		<style type="text/css">
.table td {
	white-space: nowrap;
	border-right: none;
}

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
							序号
						</th>
						<th width="150" align="center">
							盘点档案分类
						</th>
						<th width="200" align="center">
							盘点结果
						</th>
						<th width="150" align="center">
							盘点时间
						</th>
						<th width="150" align="center">
							所属员工
						</th>
						<th width="150" align="center">
							所属部门
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${checkid}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${checkid}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="cataloguename">${cataloguename}</span>

							</td>
							<td>
								<span id="results">${results}</span>
							</td>
							<td>
								<span id="checktime">${fn:substring(checktime,0,19)}</span>
							</td>
							<td>
								<span id="checkusername">${checkusername}</span>
							</td>
							<td>
								<span id="departmentname">${departmentname}</span>

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
					value="/ea/check/ea_getCheckList.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}">
				</c:param>
			</c:import>
		</div>

		<!--添加窗口 -->
		<div class="jqmWindow" style="width: 500px; right: 25%; top: 10%"
			id="jqModelAdd">
			<form name="postAddForm" id="postAddForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					<div id="title">
						添加
					</div>
					<div class="close">
					</div>
				</div>
				<table width="500" cellpadding="5" cellspacing="10" id="addTable">
					<tr>
						<td align="right" style="width:30%;">
							所属员工：
						</td>
						<td>
							<input type="text" name="checkusername" id="checkusername" 
								readonly class="put3" style="width: 150px;"/>
							<input name="archivecheck.checkuser" id="checkuser" type="hidden" />
							<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
								onclick="importGY('ea/stamplog/ea_getStaffformalList.jspa')" />
						</td>
					</tr>
					<tr>
						<td align="right">
							所属部门：
						</td>
						<td>
							<select name="archivecheck.departmentid" id="departmentid"
								class="put3" style="width: 155px;">
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							盘点档案分类：
						</td>
						<td>
							<select name="archivecheck.categoryid" id="categoryid"
								style="width: 155px;" class="put3">

							</select>
							<input type="hidden" value="" id="checkid"
								name="archivecheck.checkid" />

						</td>
					</tr>
					<tr>
						<td align="right">
							盘点结果：
						</td>
						<td>
							<textarea name="archivecheck.results" id="results" cols="30"
								rows="3" class="put3"></textarea>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button" id="tosubmit"
								value=" 确定 " />
							<input type="button" class="input-button" id="toCancel"
								value=" 关闭 " />
						</td>

					</tr>

				</table>
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
				<table width="396" cellpadding="10" cellspacing="10" id="searchtbl">
					<tr>
						<td align="right">
							所属员工：
						</td>
						<td>
							<input type="text" name="checkusername" id="checkusername"
								style="width: 150px;" readonly />
							<input name="archivecheck.checkuser" id="checkuser" type="hidden" />
							<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
								onclick="importGY('ea/stamplog/ea_getStaffformalList.jspa')" />
						</td>
					</tr>
					<tr>
						<td align="right">
							所属部门：
						</td>
						<td>
							<select name="archivecheck.departmentid" id="departmentid"
								style="width: 155px;">
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							盘点档案分类：
						</td>
						<td>
							<select name="archivecheck.categoryid" id="categoryid"
								style="width: 155px;">
								<option>
									滚
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							起时间：
						</td>
						<td align="left">
							<input type="text" name="archivecheck.startDate" id="startDate"
								onfocus="var endTime=$dp.$('endDate');WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', onpicked:function(){endDate.focus();}})"
								readonly style="width: 150px;" />

						</td>
					<tr>
						<td align="right">
							止时间：
						</td>
						<td align="left">
							<input type="text" name="archivecheck.endDate" id="endDate"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly style="width: 150px;" />

						</td>
					</tr>
					<tr>
						<td align="right">
							盘点结果：
						</td>
						<td>
							<textarea name="archivecheck.results" id="results" cols="30"
								rows="3"></textarea>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button" id="tosearch"
								value=" 查询 " />
							<input name="search" type="hidden" value="search" />
							<input name="type" type="hidden" value="${type}" />
						</td>

					</tr>

				</table>
			</form>
		</div>

		<div id="socialJqm" class="jqmWindow"
			style="width: 75%; height: 250px; absolute; display: none; left: 15%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="210px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 28px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>