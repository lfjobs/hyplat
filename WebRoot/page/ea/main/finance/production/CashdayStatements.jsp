<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>部门拆分单据报表</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

		<style type="text/css">
html {
	overflow: hidden;
}
</style>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/css/admin_main2.css" />
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/CashdayStatements.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var journalNum="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var csbID="";
        	var pNumber=${pageNumber};
        	var token=0;
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var sfdate="${sfdate}";
        	var efdate="${efdate}";
        	var notoken = 0;
        	var type="${type}";
			var status="${status}";
			var number=0;
        	var uu=0;
        	var docNull=0;
        	var showDocument=false;
        	var cashierBillsID="";
			var tt="${tt}"
			var zz="${zz}"
        </script>
	</head>
	<body>
		<form name="CashierBillsform" id="CashierBillsform">
			<input type="submit" name="submit" style="display: none" />
			<input type="text" name="text1" style="display: none" />
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
					<th width="150" align="center">
						黏贴单编号
					</th>
					<th width="100" align="center">
						公司归档号
					</th>
					<th width="100" align="center">
						公司归档日期
					</th>
					<th width="100" align="center">
						单据类别
					</th>
					<th width="100" align="center">
						部门
					</th>
					<th width="80" align="center">
						责任人
					</th>
					<th width="90" align="center">
						制单日期
					</th>
					<th width="80" align="center">
						实物仓库
					</th>
					<th width="80" align="center">
						财务仓库
					</th>
					<th width="80" align="center">
						资料仓库
					</th>
					<th width="180" align="center">
						往来单位
					</th>
					<th width="90" align="center">
						单位银行账号
					</th>
					<th width="90" align="center">
						单位往来关系
					</th>
					<th width="90" align="center">
						往来个人
					</th>
					<th width="90" align="center">
						个人银行账号
					</th>
					<th width="90" align="center">
						个人往来关系
					</th>
					<th width="90" align="center">
						主管审核人
					</th>
					<th width="90" align="center">
						会计审核人
					</th>
					<th width="90" align="center">
						出纳审核人
					</th>
					<th width="90" align="center">
						学员状态
					</th>
					<th width="90" align="center">
						附件管理
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
							<span id="deparchivesnum">${arr[4]}</span>
						</td>
						<td>
							<span id="deparchivesdate">${arr[5]}</span>
						</td>
						<td>
							<span id="departmentname">${arr[6]}</span>
						</td>
						<td>
							<span id="staffname">${arr[7]}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(arr[8], 0, 10)}</span>
						</td>
						<td>
							<span id="afterDiscount">${arr[9]}</span>
						</td>
						<td>
							<span id="bankDepotName">${arr[10]}</span>
						</td>
						<td>
							<span id="dataDepotName">${arr[11]}</span>
						</td>
						<td>
							<span id="ccompanyname">${arr[12]}</span>
						</td>
						<td>
							<span id="accountNum">${arr[13]}</span>
						</td>
						<td>
							<span id="contactConnections">${arr[14]}</span>
						</td>
						<td>
							<span id="contactUserName">${arr[15]}</span>
						</td>
						<td>
							<span id="userAccountNum">${arr[16]}</span>
						</td>
						<td>
							<span id="phone">${arr[17]}</span>
						</td>
						<td>
							<span id="responsible">${arr[18]}</span>
						</td>
						<td>
							<span id="accountant">${arr[19]}</span>
						</td>
						<td>
							<span id="cashier">${arr[20]}</span>
						</td>

						<td>
							<span id="depStatue">${arr[21]}</span>
						</td>
						<td>
							<a href="#" onclick="fj('${arr[0]}')" class="fj">附件</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/statement/ea_getArchivesList.jspa?tt=${tt}&zz=${zz}&search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
			</c:param>
		</c:import>

		<!-- 查询 -->
		<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<input name="type" style="display: none" value="${type }" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396px" id="SearchTable">
					<tr>
						<td align="right" width="80px">
							入账日期：
						</td>
						<td style="width: 200px">
							<input id="sfdate" name="sfdate" onfocus="date(this);"
								style="width: 85px" />
							至
							<input id="efdate" name="efdate" onfocus="date(this);"
								style="width: 85px" />
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

		<!-- 报表查询 -->
		<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%" id="FormsSearch">
				<div class="drag">
					报表查询信息
					<div class="close">
					</div>
				</div>
				<table width="396px" id="bb">
					<tr>
						<td align="right" width="80px">
							入账日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="sfdate" onfocus="date(this);"
								style="width: 85px" />
							至
							<input id="edate" name="efdate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
				</table>
				<div align="center">
						<input type="button" class="input-button" id="toFormsSearch" value=" 查询 " />
					</div>
		</div>
		<iframe name="hidden" width="100%" height="0"></iframe>
	</body>
</html>
