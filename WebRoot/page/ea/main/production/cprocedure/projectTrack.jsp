<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title>项目跟踪</title>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/projectTrack.js"></script>

	
<script type="text/javascript">

var basePath="<%=basePath%>";
var fiveClear="${fiveClear}";
var search = "${search}";
var count = "${pageForm.pageCount}";
var search = "${search}";


</script>
<style type="text/css">
 th,td {
    white-space: nowrap;
}
</style>


</head>
<body>
      <form name="SearchForm" id="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;">
		<input type="hidden" name="search" value="search" />
		<input type="hidden" name="fiveClear" value="${fiveClear}" />
		
		<div class="main" style="margin-bottom:50px;">
		<div class="top" style="width:100%;min-width:980px;text-align:center;">项目跟踪</div>
		<div
			style="width:99%;text-align:left;padding-left:20px;margin-top:20px;margin-bottom:10px;">
				<table>
					<tr>
						<td>主项目：</td>
						<td><input type='text' size="15" name="staffTrack.ppName" />
						</td>
						<td>子项目：</td>
						<td><input type='text' size="15" name="staffTrack.subName" />
						</td>
						<td>客户/身份证：</td>
						<td><input type='text' size="15" name="staffTrack.staffName" />
						</td>
					</tr>
					<tr>
						<td>日&nbsp;期：</td>
						<td><input type='text' size="15" name="staffTrack.startDate"
							onfocus="date(this)" />
						</td>
						<td>&nbsp;至&nbsp;</td>
						<td><input type='text' size="15" name="staffTrack.endDate"
							onfocus="date(this)" />
						</td>
						<td>负责人：</td>
						<td><input type='text' size="15" name="staffTrack.responName" />
							<input type="button" value="查询" onclick="toSearch();" />&nbsp;<input
							type="button" value="导出" onclick="toExcel();" /></td>

					</tr>

				</table>

			</div>
		<center>
					<table class="table"  width="98%"  cellpadding="10px" style="margin-top:5px;cellpadding:50px;">
					<thead>
						<tr>
						    <th align="center">选择</th>
						  	<th align="center">序号</th>
						  	<th align="center">报名时间</th>
							<th align="center">客户</th>
							<th align="center">性别</th>
							<th align="center">电话</th>
							<th align="center">身份证</th>
							<th align="center">负责人</th>
							<th align="center">起止时间</th>
							<th align="center">主项目</th>
							<th align="center">子项目</th>
							<th align="center">项目总数</th>
							<th align="center">完成率</th>
							<th align="center">备注</th>
							<th align="center">操作</th>
			
						
						</tr>
						</thead>
						<tbody id="sublist">
						<%
					    int number = 1;
				       %>
						<s:iterator  value="pageForm.list">
                        <tr>
                            <td align="center"><input type="radio" name="a" class="JQuerypersonvalue"
								value="${stId}" /></td>
							<td align="center"><%=number%></td>
							<td align="center">${fn:substring(enrollDate,0,19)}</td>
							<td align="center">${staffName}</td>
							<td align="center">${sex}</td>
							<td align="center">${tel}</td>
							<td align="center">${identiCard}</td>
							<td align="center">${responName}</td>
							<td align="center">${fn:substring(startDate,0,10)}-${fn:substring(endDate,0,10)}</td>
							<td align="center">${ppName}</td>
							<td align="center">${subName}</td>
							<td align="center">${pronum}</td>
							<td align="center">${completerate}</td>
							<td align="center">${remark}</td>
							<td align="center"><a href="javascript:viewDetail('${stId}')">查看详情</a></td>
						</tr>
						<%
						number++;
					   %>
						</s:iterator>
						</tbody>
						
					</table>
				</center>
			</div>
				<table style="margin:auto;">
				<tr>
					<td width="100"><a id="wpsyp" onclick="page(${pageForm.pageNumber-1})" title="${pageForm.pageNumber-1}" style="cursor:pointer;">上一页</a>
					</td>
					<td width="100"><a id="wpxyp" onclick="page(${pageForm.pageNumber+1})" title="${pageForm.pageNumber+1}" style="cursor:pointer;">下一页</a>
					</td>
					<td width="150"><a id="wpzyp">共&nbsp;&nbsp;
					 <span style="color: red" id="wpzycountp">${pageForm.pageCount}</span>&nbsp;&nbsp;页 </a></td>
				</tr>
			</table>

	</form>

</body>
</html>