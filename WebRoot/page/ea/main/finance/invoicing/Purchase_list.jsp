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
		<title>采购单管理</title>
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
		<%--<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/Purchase_list.js"></script>
		--%>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/Purchase_list1.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var journalNum="";
        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var sdate="${sdate}";
        	var xmtype="${xmtype}";
        	var edate="${edate}";
        	var notoken = 0;
			var type="${type}";
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
					<th width="200" align="center">
						项目名称
					</th>
					<th width="150" align="center">
						单据编号
					</th>
					<th width="100" align="center">
						单据类型
					</th>
					<th width="90" align="center">
						单据状态
					</th>
					<th width="100" align="center">
						部门
					</th>
					<th width="80" align="center">
						制单人
					</th>
					<th width="90" align="center">
						制单日期
					</th>
				</tr>
			</thead>
			<tbody>			
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr.cashierBillsID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr.cashierBillsID}" />
						</td>
						<td>
							<span id="companyname">${arr.companyname}</span>
						</td>
						<td>
							<span id="projectName">${arr.projectName}</span>
						</td>
						<td>
							<span id="journalNum">${arr.journalNum}</span>
						</td>
						<td>
							<span id="billsType">${arr.billsType}</span>
						</td>
						<td>
							<span id="status">${arr.status=='22'?"草稿":arr.status=='12'?"未收货":arr.status=='13'?"已收货":arr.status=='14'?"已关联":arr.status=='05'?"审核中":arr.status==null?"":"驳回作废"}</span>
						</td>
						<td>
							<span id="departmentname">${arr.departmentname}</span>
						</td>
						<td>
							<span id="inputName">${arr.inputName}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(arr.cashierDate, 0, 10)}</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/purchase1/ea_getPurchaseList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&type=${type}">
			</c:param>
		</c:import>
		
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
