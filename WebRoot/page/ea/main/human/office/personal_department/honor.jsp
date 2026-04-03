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
		<title>荣誉名称模块</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />    
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>

		<script type="text/javascript">
            var basePath = "<%=basePath%>";
            var ppageNumber=${pageNumber};
			var comID = '';      
			var token = 0;	
			var honorID = '';	
			var search = '${search}';
		
			
        </script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/office/personal_department/honor.js"></script>
	</head>
	<body>
		<form name="honorForm" id="honorForm" method="post">
		<input type="submit" name="submit" style="display:none" />
		<s:token></s:token>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							选择 
						</th>
						<th width="200" align="center">
							代码
						</th>
						<th width="100" align="center">
							类型
						</th>
						<th width="100" align="center">
							荣誉名称
						</th>
						<th width="80" align="center">
							奖励金额
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" >
						<tr id="${honorID}" >
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue" value="${honorID}" />
							</td>
							<td>
								<span id="honorCode">${honorCode}</span>
							</td>
							<td>
								<span id="">
								<c:if test="${honorType == '00'}">月度奖</c:if>
								<c:if test="${honorType == '01'}">季度奖</c:if>
								<c:if test="${honorType == '02'}">年度奖</c:if>
								</span>
							</td>
							<td>
								<span id="honorName">${honorName}</span>
							</td>
							<td>
								<span id="honorMoney">${honorMoney}</span>
								<span id="honorKey" style="display: none;">${honorKey}</span>
								<span id="honorID" style="display: none;">${honorID}</span>
								<span id="companyID" style="display: none;">${companyID}</span>
								<span id="honorType" style="display: none;">${honorType}</span>
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
					value="ea/honor/ea_getHonorList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		</form>
		<form name="AddForm" id="AddForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow" style="width: 350px; right: 35%; top: 10%"
				id="jqModel">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					荣誉名称功能
					<div class="close">
					</div>
				</div>
				<table width="380px"  border="0" align="center"
					cellpadding="0" cellspacing="0" id="addtable">
					<tr>
						<td width="80"  height="27" align="right">
							代码：
						</td>
						<td >
							<input size="20" name="honor.honorCode" id="honorCode" class="put3"/>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							类型：
						</td>
						<td>
							<select name="honor.honorType" id="honorType" style="width:140px"/>
								<option value="00">月度奖</option>
								<option value="01">季度奖</option>
								<option value="02">年度奖</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							荣誉名称：
						</td>
						<td>
							<input size="20" name="honor.honorName" id="honorName" class="put3"/>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							奖励金额：
						</td>
						<td>
							<input size="20" name="honor.honorMoney" id="honorMoney" class="put3"/>
							<input type="hidden" name="honor.honorKey" id="honorKey"/>
							<input type="hidden" name="honor.honorID" id="honorID"/>
							<input type="hidden" name="honor.companyID" id="companyID"/>
						</td>
					</tr>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="save" value=" 保存 "
						style="cursor: hand" />
				</div>
				<div align="center">
				</div>
			</div>
		</form>
		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td width="123" align="center">
							荣誉名称：
						</td>
						<td>
							<input name="honor.honorName" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="search"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>