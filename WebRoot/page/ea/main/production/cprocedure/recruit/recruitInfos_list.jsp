<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<title>招聘信息</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitInfo_list.js"></script>




<script type="text/javascript">

    var basePath="<%=basePath%>";
	var search = "${search}";
	var ppageNumber = "${pageNumber}";
	var token = 1;
	var  riId = "";

</script>

</head>
<body>

	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">

		<form id="SearchForm" name="SearchForm" method="post">
			<input type="submit" name="submit" style="display:none;" />
			<table>
				<tr>
					<td><strong>招聘信息</strong>&nbsp;&nbsp;&nbsp;职位：</td>
					<td><input type="text" style="width:90px;height:18px;"
						name="recruitInfo.jobTitle" /></td>
					
					<td>&nbsp;状态：</td>
					<td><select name="recruitInfo.status">
					            <option value='' selected="selected">全部</option>
					            <option value='00'>草稿</option>
					            <option value='01'>已发布</option>
					
					</select></td>
					<td><input type="button" class="input-button" value="  查询  " style="margin:0px;margin-left:5px;"
						id="tosearch" /> <input type="hidden" value="search" name="search" />
						<input type="hidden" id="riId" name="recruitInfo.riId" />
						
						</td>
				</tr>
			</table>
		</form>

	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="30" align="center">
						选择
					</th>
					<th width="30" align="center">
						序号
					</th>
					<th width="200" align="center">
						公司名称
					</th>
					<th width="150" align="center">
						职位名称
					</th>
					<th width="150" align="center">
						职位类别
					</th>
					<th width="150" align="center">
					        薪金要求
					</th>
					<th width="150" align="center">
						工作年限要求
					</th>
					<th width="150" align="center">
						学历要求
					</th>
					<th width="100" align="center">
						兼职/全职
					</th>
					<th width="70" align="center">
						责任人
					</th>
					<th width="130" align="center">
						发布日期
					</th>
				   
					<th width="90" align="center">
						状态
					</th>
					 <th width="70" align="center">
						任职要求
					</th>
					<th width="90" align="center">
						备注
					</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${riId}">
					     <td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${riId}" />
						</td>
						<td>
							<span><%=number%></span>
						</td>
						<td>
							<span id="companyName">${companyName}</span>
						</td>
						<td>
							<span id="jobTitle">${jobTitle}</span>
						</td>
						<td>
							<span id="positionName">${positionName}</span>
						</td>
						<td>
							<span id="salary">${salary}</span>
						</td>
						
						<td>
							<span id="workYears">${workYears}</span>
						</td>
						<td>
							<span id="education">${education}</span>
						</td>
						<td>
							<span id="partorfull">${partorfull}</span>
						</td>
						<td>
							<span id="staffName">${staffName}</span>
						</td>
						<td>
							<span id="publishDate">${fn:substring(publishDate,0,19)}</span>
						</td>
						
						<td>
						   <span id="status" style="display:none;">${status}</span>
						
						   <s:if test="status=='00'">草稿</s:if>
						   <s:if test="status=='01'">已发布</s:if>
						
						</td>
						<td>
							<span id="JobRequire">${fn:replace(JobRequire,'<br/>',' ')}</span>
						</td>
						<td>
							<span id="remark">${remark}</span>
						</td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/bidrecruit/ea_findRecruitInfoList.jspa?pageNumber=${pageNumber}&search=${search}">
			</c:param>
		</c:import>
	</div>
	<div class="jqmWindow" id="jqModel"
		style="display: none; width: 250px; height: 100px; right: 30%; top: 10%;"
		id="jqModelPosition">
		<input type="submit" name="submit" style="display: none" />
		<div class="drag">
			提示
			<div class="close"></div>
		</div>
		<center>
		<p class="tip">tip</p>
		<input type="button" class="confirm input-button" value=" 确定 " /> <input
			type="button" class="input-button close" value=" 取消   " /> </center>

	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>