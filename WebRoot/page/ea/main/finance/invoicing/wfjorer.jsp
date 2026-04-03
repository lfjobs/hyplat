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
		<title>预算管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script><script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
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
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>/js/ea/finance/invoicing/wfjorer.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var journalNum="";
        	var financialbillID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var notoken = 0;
			document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
    				if($("input#journalNum").val()==''){
						return false;
    				}
        			if($("input#journalNum").val()!=''){
			        	$("#SearchForm").attr("action", basePath+"ea/dutiable/ea_toSearch.jspa?pageNumber="+pNumber+"&taxstatusDu="+taxstatusDu);
                    	document.SearchForm.submit.click();
					}
    			}
			}      
        </script>
	</head>
	<body>
	<form name="CashierBillsform" id="CashierBillsform">
			<input type="submit" name="submit" style="display: none" />
			<input name="cashierBills.taxstatus" id="taxstatus" style="display: none" />
			<input name="cashierBills.cashierBillsID" id="cashierBillsID"
		
				style="display: none" />
				
		
		</form>
		<table class="flexme11">
			<thead>
				<tr>
					<th width="50" align="center">
						选择
					</th>
					<th width="230" align="center">
						公司名称
					</th>
					<th width="200" align="center">
						凭证号
					</th>
					<th width="130" align="center">
						单据类型
					</th>
					<th width="130" align="center">
						部门
					</th>
					<th width="80" align="center">
						责任人
					</th>
					<th width="80" align="center">
						制单人
					</th>
					<th width="90" align="center">
						制单日期
					</th>
					<th width="150" align="center">
						公司银行账号
					</th>
				</tr>
			</thead>
			<tbody>
		
		   
		   <form id="cstaffForm"   method="post">
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }">
						<td>
							<input type="radio" name="orderID" class="JQuerypersonvalue"
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
							<span id="StaffName">${arr[5]}</span>
						</td>
						<td>
							<span id="billStaffName">${arr[6]}</span>
						</td>
						<td>
							<span id="billsDate">${arr[7]}</span>
						</td>
						<td>
							<span id="companyBankNum">${arr[8]}</span>
						</td>
						<td>
							<span id="billStatus">${arr[13]}</span>
						</td>
					</tr>
					  
				</c:forEach>
				   </form>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/costsheet/ea_getlisorer.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&type=05">
			</c:param>
		</c:import>	
		
		<iframe name="hidden" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
