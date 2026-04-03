<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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

		<title>收集单位</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
				<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/CollectUnit.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var status="";
        	var staffname="${staffname}";
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var notoken = 0;
        </script>
	</head>

	<body>
	<form name="Auditform" id="Auditform">
			<input type="submit" name="submit" style="display: none" />
			<s:token />
		</form>
		<table class="flexme11">
			<thead>
				<tr>
					<th width="30" align="center">
						选择
					</th>
					<th width="200" align="center">
						公司名称
					</th>
					<th width="100" align="center">
						部门
					</th>
					<th width="80" align="center">
						责任人
					</th>
					<th width="90" align="center">
						单位往来关系
					</th>
					<th width="90" align="center">
						往来单位
					</th>
					<th width="90" align="center">
						电话
					</th>
					<th width="90" align="center">
						地址
					</th>
					<th width="90" align="center">
						录入时间
					</th>
					<th width="90" align="center">
						咨询起日期
					</th>
					<th width="90" align="center">
						咨询止日期
					</th>
					<th width="90" align="center">
						工作地点
					</th>
					<th width="90" align="center">
						服务方式
					</th>
					<th width="200" align="center">
						咨询跟踪内容
					</th>
					<th width="200" align="center">
						跟踪原因
					</th>
					<th width="100" align="center">
						处理状态
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" />
						</td>
						<td>
							<span id="companyname">${arr[1]}</span>
						</td>
						<td>
							<span id="journalNum">${arr[2]}</span>
						</td>
						<td>
							<span id="billsType">${arr[3]}</span>
						</td>
						<td>
							<span id="departmentname">${arr[4]}</span>
						</td>
						<td>
							<span id="staffname">${arr[5]}</span>
						</td>
						<td>
							<span id="staffname">${arr[6]}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(arr[7], 0, 10)}</span>
						</td>
						<td>
							<span id="afterDiscount">${arr[8]}</span>
						</td>
						<td>
							<span id="bankDepotName">${arr[9]}</span>
						</td>
						<td>
							<span id="dataDepotName">${arr[10]}</span>
						</td>
						<td>
							<span id="ccompanyname">${arr[11]}</span>
						</td>
						<td>
							<span id="accountNum">${arr[12]}</span>
						</td>
						<td>
							<span id="contactConnections">${arr[13]}</span>
						</td>
						<td>
							<span id="contactUserName">${arr[14]}</span>
						</td>
						<td>
							<span id="userAccountNum">${arr[15]}</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:import url="../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/collectunit/ea_getTracklist.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&staffname=${staffname}">
			</c:param>
		</c:import>
		
	
		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 350px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="300" id="SearchTable">
					<tr>
						<td width="123" align="right">
							往来单位：
						</td>
						<td>
							<input name="staffname" id="staffname" size="10"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							咨询起时间：
						</td>
						<td>
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							咨询止时间：
						</td>
						<td>
							<input id="edate" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							服务方式：
						</td>
						<td style="width: 200px">
							<s:select list="#{'00':'面谈','01':'电话','02':'邮件','03':'登门拜访','04':'暗访'}"
									class="dis" name="track.serviceMode" id="serviceMode" theme="simple"></s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							处理状态：
						</td>
						<td style="width: 200px">
							<s:select list="#{'00':'处理','01':'未处理'}" class="dis"
									name="track.handleStatus" id="handleStatus" theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
