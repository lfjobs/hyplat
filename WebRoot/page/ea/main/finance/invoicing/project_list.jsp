<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@ page language="java" pageEncoding="UTF-8" %>
		<%@ taglib uri="/struts-tags" prefix="s" %>
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
		<title>项目管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/project_list.js"></script>
			<link rel="stylesheet" href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css"/>
<script src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js" type="text/javascript"></script>
			<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
	type="text/css" />
			
		<script type="text/javascript">
        	var basePath="<%=basePath%>";
        	var proID = "";
        	var search="${search}";
   var accname="${ManStaffName}";
        	var treeID = '<%=session.getAttribute("organizationID")%>';
  var groupCompanySn = "${groupCompanySn}";
  var type = "${type}";
        </script>
<style type="text/css">
#jqModel {
	display: none;
	overflow: auto;
	border: 1px solid #a8c7ce;
	width: 300px;
	height: 350px;
	position: absolute;
	top: 31%;
	left: 25%;
	z-index: 999999;
	background-color: #e1ecfc;
	filter: Alpha(opacity = 100);
}
a{
color:#0066CC;
}

</style>

</head>
	<body>
		<form name="Projectform" id="Projectform">
			<input type="submit" name="submit" style="display: none" />
			<s:token />
		</form>
		<table class="flexme11">
			<thead>
				<tr>
					<th width="50" align="center">
						选择
					</th>
					<th width="120" align="center">
						项目编号
					</th>
					<th width="200" align="center">
						项目名称
					</th>
					<th width="150" align="center">
						主项目
					</th>
					<th width="120" align="center">
						项目开始日期
					</th>
					<th width="120" align="center">
						项目结束日期
					</th>

					<th width="200" align="center">
						公司名称
					</th>
					<th width="80" align="center">
						部门
					</th>
					<th width="70" align="center">
						负责人
					</th>
					<th width="90" align="center">
						创建人
					</th>
					<th width="150" align="center">
						更新日期
					</th>
					<th width="70" align="center">
						审核状态
					</th>
				</tr>
			</thead>
			<tbody>			
				
				<s:iterator value="pageForm.list">
					<tr id="${proID}">
						<td>
						   <div style="text-align:left;"><span id="kg"></span>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${proID}" />
						 </div>
						</td>
						<td>
							<div align="left"><span id="projectCode">${projectCode}</span></div>
						</td>
						<td>
							<span id="projectName">${projectName}</span>
						</td>
						<td>
							<span>(${xmtype})${xmtypename}</span>
							<span id="xmtype" style="display:none;">${xmtype}</span>
							<span id="xmtypename" style="display:none;">${xmtypename}</span>
						</td>
						<td>
							<span id="startDate">${fn:substring(startDate,0,10)}</span>
						</td>
						<td>
							<span id="endDate">${fn:substring(endDate,0,10)}</span>
						</td>
						<td>
							<span id="companyName">${companyName}</span>
						</td>
						<td>
							<span id="organizationName">${organizationName}</span>
						</td>
						<td>
							<span id="staffName">${staffName}</span>
						</td>
						<td>
							<span id="createName">${createName}</span>
						</td>

						<td>
							<span id="updateDate">${fn:substring(updateDate,0,19)}</span>
						</td>
						<td>
							<span style="display:none;" id="status">${status}</span>
							<s:if test="status=='01'">待审核</s:if>
							<s:if test="status=='02'">审核通过</s:if>
							<s:if test="status=='03'">驳回</s:if>
							<s:if test="status==null">草稿</s:if>
						</td>
						
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/promanage/ea_getProjectList.jspa?search=${search}&pageNumber=${pageNumber}&type=${type}">
			</c:param>
		</c:import>


		
		<iframe name="hidden" border="0" framespacing="0" height="0"></iframe>




    <!-- 查询项目分类 -->
	<div id="jqModel">
		<div id="treeBoxs"></div>
	</div>


<div class="jqmWindow" id="planjqModelsend"
			style="width: 800px; height: 450px;  right: 12%; top: 1% ">
			<div>
				<div class="contentbannb">
					<div class="drag">
						组织机构树

					</div>
				</div>
			</div>
			<form name="projectplanbudgetForm" id="projectplanbudgetForm" method="post">
				<input type="submit" name="submit" style="display: none" />
			<table style="width: 100%; height: 450px;" cellpadding="0"
				cellspacing="0" style="margin-top: 2px;">
				<tr>
					<td width="30%" align="left" valign="top">
						<div id="grouptree" style="width:250px; height:350px;overflow:auto;background:#FFFFFF;border:1px solid #CCCCCC;">
						
						</div>
					</td>
					<td width="70%" align="left" valign="top">
						<table style="width: 450px; height: 350px;" align="center"
							cellpadding="0" cellspacing="2"
							style="margin-top: 5px; margin-bottom: 5px;">
							<tr>
								<td width="200" height="20" class="txt01">
									备选人员
								</td>
								<td width="50">
									&nbsp;
								</td>
								<td width="200" align="left" class="txt01">
									已选人员
								</td>
							</tr>
							<tr>
								<td height="137">
									<select name="leftfields" multiple="multiple" id="leftfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>

								</td>
								<td width="250" align="center">
									<div>
										<input type="button" class="input-button" id="query_add"
											value=" 添加 " />
									</div>
									<div>
										<input type="button" class="input-button" id="query_delete"
											value=" 删除 " />
									</div>
								</td>
								<td>
									<select name="rightfields" multiple="multiple" id="rightfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>
								</td>
								<td width="100">
									&nbsp;
								</td>
							</tr>


							<tr>
								<td height="30" colspan="3" align="center">
									<br />
									<input type="button" class="input-button" id="tosave"
										value=" 确定 "  />
									<input type="button" class="input-button close" 
										value=" 关闭 "  />
									<input type="text" id="csbID" style="display:none;" name="projectplanbudget.csbid"/>
					<input type="text" id="projectnames" style="display:none;" name="projectplanbudget.projectname"/>
					<input type="text" id="siaffID"  style="display:none;" name="projectplanbudget.staffid"/>
					<input type="hidden" name="xmtypename" id="xmtypenameff" value="${xmtypename}"/>
					<input type="hidden"  name="jumptype" value="${jumptype}"/>
					<input type="hidden"  name="xmtype" value="${xmtype}"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		
		<form id="postform" name="postform" action="post">
		 <input type="submit"  name="submit" style="display:none;"/>
		 <input type="hidden"  name="projectManage.proID"  id="formproid"/>
		 <input type="hidden"  name="audittype"  id="audittype"/>
		 <input type="hidden"  name="type"  value="${type}"/>
		
		</form>
		
</body>
</html>
