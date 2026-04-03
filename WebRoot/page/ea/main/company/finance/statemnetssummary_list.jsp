<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="hy.ea.bo.Company"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany"); 
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>公司单据归档</title>
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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/css/admin_main2.css" />
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/company/finance/statemnetssummary_list.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var treeNames="<%=session.getAttribute("organizationName")%>";
        	var ids = "<%=c.getCompanyID()%>";
         	var treename = "<%=c.getCompanyName()%>";
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
						单据类别
					</th>
					<th width="100" align="center">
						公司归档号
					</th>
					<th width="100" align="center">
						公司归档日期
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
						附件管理
					</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list">
					<tr id="${cashierBillsID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${cashierBillsID}" />
						</td>
						<td>
							<span id="companyname">${companyname}</span>
						</td>
						<td>
							<span id="journalNum">${journalNum}</span>
						</td>
						<td>
							<span id="billsType">${billsType}</span>
						</td>
						<td>
							<span id="archivesNum">${archivesNum}</span>
						</td>
						<td>
							<span id="archivesDate">${fn:substring(archivesDate, 0, 10)}</span>
						</td>
						<td>
							<span id="departmentname">${departmentname}</span>
						</td>
						<td>
							<span id="staffname">${staffname}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(cashierDate, 0, 10)}</span>
						</td>
						<td>
							<span id="afterDiscount">${afterDiscount}</span>
						</td>
						<td>
							<span id="bankDepotName">${bankDepotName}</span>
						</td>
						<td>
							<span id="dataDepotName">${dataDepotName}</span>
						</td>
						<td>
							<span id="ccompanyname">${ccompanyname}</span>
						</td>
						<td>
							<span id="accountNum">${accountNum}</span>
						</td>
						<td>
							<span id="contactConnections">${contactConnections}</span>
						</td>
						<td>
							<span id="contactUserName">${contactUserName}</span>
						</td>
						<td>
							<span id="userAccountNum">${userAccountNum}</span>
						</td>
						<td>
							<span id="phone">${phone}</span>
						</td>
						<td>
							<span id="responsible">${responsible}</span>
						</td>
						<td>
							<span id="accountant">${accountant}</span>
						</td>
						<td>
							<span id="cashier">${cashier}</span>
						</td>
					<!-- <td><span id="depStatue"><s:if test="depStatue==00">未缴费（未转科一）</s:if>
							<s:elseif test="depStatue==01">已缴费(已转未收集)</s:elseif>
							<s:elseif test="depStatue==02">(已缴费)已转已收集</s:elseif>
							<s:elseif test="depStatue==11">售前客户服务单据</s:elseif>
					</span></td> -->
					<td>
							<a href="#" onclick="fj('${cashierBillsID}')" class="fj">附件</a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/archivessummary/ea_getArchivesList.jspa?tt=x&search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
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
				<table width="396px">
					<tr>
						<td width="123" align="right">
							单据类别：
						</td>
						<td><select name="historyVO.billsType" id="btype"></select>
						</td>
					</tr>
					<tr>
						<td align="right">
							黏贴单编号：
						</td>
						<td>
							<input id="journalNum" style="width: 195px"
								name="historyVO.journalNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							公司归档号：
						</td>
						<td>
							<input id="archivesNum" style="width: 195px"
								name="historyRelation.archivesNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							公司归档日期：
						</td>
						<td>
							<input id="nuvDate" name="nuvDate" onfocus="date(this);"
								style="width: 195px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="deptID" name="historyVO.companyID">
								<option value="" selected="selected">
									全部公司
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td>
							<select id="orgID" name="historyVO.departmentID">
								<option value="">
									请选择公司
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<select name="historyVO.staffID" id='person'>
								<option value="">
									请选择部门
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							单位往来关系：
						</td>
						<td>
							<s:select list="%{#request.connectionlist}" 
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="historyVO.contactConnections" listValue="codeValue"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							个人往来关系：
						</td>
						<td>
							<s:select list="%{#request.codeRelationList}" 
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="historyVO.phone" listValue="codeValue" theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							制单日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />
							至
							<input id="edate" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来单位：
						</td>
						<td>
							<input id="sdate" name="historyVO.ccompanyname"
								style="width: 188px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来个人：
						</td>
						<td>
							<input id="sdate" name="historyVO.contactUserName"
								style="width: 188px" />
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
		<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
			id="FormsSearch">
			<form name="FormsForm" id="FormsForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<input name="type" style="display: none" value="${type }" />
				<div class="drag">
					报表查询信息
					<div class="close">
					</div>
				</div>
				<table width="396px">
					<tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="deptID2" name="companyID">
								<option value="" selected="selected">
									全部公司
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td>
							<select id="orgID2" name="departmentID">
								<option value="">
									请选择公司
								</option>
							</select>
						</td>
					</tr>
					<!-- <tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<select name="historyVO.staffID" id='person2'>
								<option value="">
									请选择部门
								</option>
							</select>
						</td>
					</tr>-->
					<tr>
						<td width="123" align="right">
							单据类别：
						</td>
						<td><select name="historyVO.billsType" id="btypes"></select>
						</td>
					</tr>
					
					<tr>
						<td align="right">
							单位往来关系：
						</td>
						<td>
							<s:select list="%{#request.connectionlist}" 
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="historyVO.contactConnections" listValue="codeValue"
								theme="simple" id="danid">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							个人往来关系：
						</td>
						<td>
							<s:select list="%{#request.codeRelationList}" 
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="historyVO.phone" listValue="codeValue" theme="simple" id="pepid">
							</s:select>
						</td>
					</tr> 
					<tr>
						<td align="right" width="80px">
							入账日期：
						</td>
						<td style="width: 200px">
							<input id="sfdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />
							至
							<input id="efdate" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					 <tr>
						<td align="right">
							往来单位：
						</td>
						<td>
							<input id="danwei" name="historyVO.ccompanyname"
								style="width: 188px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来个人：
						</td>
						<td>
							<input id="peo" name="historyVO.contactUserName"
								style="width: 188px" />
						</td>
					</tr>
				</table>
				<div align="center">
						<input type="button" class="input-button" id="toFormsSearch"
							value=" 查询 " />
						<input name="search" type="hidden" value="search" />
					</div>
			</form>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
