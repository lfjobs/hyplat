<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾校休假</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/ea/driving/elkc/schoolVacation.js"
	type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />




<script type="text/javascript">

var basePath="<%=basePath%>";
var ppageNumber = "${pageNumber}";
var esrId = "";
var status = "";

</script>
</head>
<body> 
     <div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">驾校休假
		 <form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>休假开始时间：
         <input type="text" class="required text" id="holidayBegin" name="beginDate" value="${beginDate}" onfocus="date(this);" placeholder="请选择开始时间">
         休假结束时间:
		 <input type="text" class="required text" id="holidayEnd" name="endDate" value="${endDate}" onfocus="date(this);" placeholder="请选择结束时间">
         <input
                 type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;" />
        </form>
	</div>
    
	<div style="margin-top:10px;margin-left:10px;display:none;" class="query">
	<form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>驾校休假 &nbsp;&nbsp;&nbsp;
	</form>
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="150" align="center">创建人</th>
					<th width="300" align="center">休假开始时间</th>
					<th width="300" align="center">休假结束时间</th>
					<th width="150" align="center">状态</th>
				</tr>
			</thead>
			<tbody>
				<% int number = 1; %>
				<s:iterator value="pageForm.list" var="f">
					<tr id="${f[0]}" data-status="${f[4]}">
						<td><input type="radio" name="a" class="JQuerypersonvalue" value="${f[0]}" /></td>
						<td><%=number%></td>
						<td><span id="staffname">${f[1]}</span></td>
						<td><span id="begin">${f[2]}</span></td>
						<td><span id="end">${f[3]}</span></td>
						<c:choose>
							<c:when test="${f[4] eq '01'}">
								<td><span>已生效</span></td>
							</c:when>
							<c:when test="${f[4] eq '00'}">
								<td><span>未生效</span></td>
							</c:when>
						</c:choose>
					</tr>
					<% number++; %>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/aflovacation/ea_schoolVacation.jspa?pageNumber=${pageNumber}&tbElycSchoolRest.holidayBegin=${tbElycSchoolRest.holidayBegin}&tbElycSchoolRest.holidayEnd=${tbElycSchoolRest.holidayEnd}&beginDate=${beginDate}&endDate=${endDate}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
		
		<!--弹框-->
	<div class="fees_" id="fees_">
	    <div class="fees" style="height: 30%;">
	        <h4 class="tit">驾校休假<img src="<%=basePath%>/images/ico-delete.png" alt=""></h4>
	        <form  action="" method="post" class="con" id="con">
	            <div class="mil">
	                <p>开始时间：</p>
					<div>
						<input type="text" class="put3 beginDate" name="beginDate" onfocus="date(this);" placeholder="请选择开始时间">
					</div>
				</div>
	            <div class="mil">
	                <p>结束时间：</p>
	                <div>
						<input type="text" class="put3 endDate" name="endDate" onfocus="date(this);" placeholder="请选择开始时间">
	                </div>
	            </div>
	            <input type="hidden" class="esrKey" name="tbElycSchoolRest.esrKey" value="">
                <input type="hidden" class="esrId" name="tbElycSchoolRest.esrId" value="">
	            <input type="button" value="保存" class="sub">
	        </form>
	    </div>
	</div>	
		
		
</body>
</html>