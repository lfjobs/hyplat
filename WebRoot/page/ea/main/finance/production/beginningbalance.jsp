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
		<title>设置期初余额</title>
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
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/beginningbalance.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript">
       		var treeID = '<%=session.getAttribute("organizationID")%>';
        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber="${pageNumber}";
        	var token=0;
        	var sdate="${sdate}";
        	var zz="${zz}";
        	var level="${level}";
        </script>
	</head>
	<body>
		<form name="CashierBillsform" id="CashierBillsform">

			<input type="submit" name="submit" style="display: none" />
			<input name="beginningBalance.BeginningBalanceID" id="BeginningBalanceID" style="display: none" />
			<s:token />
		</form>
		<table class="flexme11">
			<thead>
				<tr>
					<th width="30" align="center">
						选择
					</th>
					<th width="150" align="center">
						设置时间
					</th>
					<th width="100" align="center">
						现金余额
					</th>
					<th width="100" align="center">
						银行余额
					</th>
					<th width="80" align="center">
						应付款余额
					</th>
					<th width="90" align="center">
						应收款余额
					</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list">
                        <tr id="${BeginningBalanceID}">
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue" value="${BeginningBalanceID}"/>
                            </td>
                            <td>
                                <span id="Customizedate1">${fn:substring(Customizedate, 0,10)}</span>
                            </td>
                            <td>
                                <span id="cashBalance">${cashBalance}</span>
                            </td>
                            <td>
                                <span id="bankBalance">${bankBalance}</span>
                            </td>
                            <td>
                                <span id="payablesBalance" class="datas">${payablesBalance}</span>
                            </td>
                            <td>
                                <span id="receivablesBalance">${receivablesBalance}</span>
                            </td>
                            </tr>
                    </s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/beginningbalance/ea_getListIPaddress.jspa?zz=${zz}&level=${level}&search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
			</c:param>
		</c:import>
		<!--添加窗口 -->
		<form name="addForm" id="addForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
				id="jqModeladd">
				<input type="submit" name="submit" style="display: none" />
				<input id="BeginningBalanceID" name="beginningBalance.BeginningBalanceID"  style="display: none;" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					<tr>
						<td width="100" align="right">
							设置时间：
						</td>
						<td>
							<input id="Customizedate1" name="sdate" onfocus="daymonth(this);"  class="put3"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							现金余额：
						</td>
						<td>
							<input id="cashBalance" style="width: 158px" class="isNaN put3"
								name="beginningBalance.cashBalance" />
						</td>
					</tr>
					<tr>
						<td align="right">
							银行余额：
						</td>
						<td>
							<input id="bankBalance" name="beginningBalance.bankBalance" class="isNaN put3"
								style="width: 158px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							应收款余额：
						</td>
						<td>
							<input id="payablesBalance" name="beginningBalance.receivablesBalance" class="isNaN put3" 
								style="width: 158px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							应付款余额：
						</td>
						<td>
							<input id="receivablesBalance" name="beginningBalance.payablesBalance" class="isNaN put3"
								style="width: 158px" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="toadd"
						value="添加 "/>
				</div>
			</div>
			<s:token />
		</form>
		
		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
		<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
				id="jqModelSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					<tr>
						<td width="123" align="right">
							设置时间：
						</td>
						<td>
							<input id="Customizedate2" name="sdate" onfocus="daymonth(this);"
								style="width: 85px" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 "/>
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		<iframe name="hidden" width="100%" height="0"></iframe>
	</body>
</html>
