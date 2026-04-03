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
		<title>库存预警</title>
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
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/warning_list.js"></script>
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
        	var token = 0;
        	var notoken = 0;
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
					<th width="80" align="center">
						库房
					</th>
					<th width="100" align="center">
						物品名称
					</th>
					<th width="80" align="center">
						库存数量
					</th>
					<th width="60" align="center">
						上限值
					</th>
					<th width="60" align="center">
						下限值
					</th>
				</tr>
			</thead>
			<tbody>			
				<c:forEach var='arr' items="${pageForm.list}">
				<c:if test="${arr[6]>=arr[7]}">
					<tr id="${arr[0]}" style="color: red">
					<td>
						<input type="radio" name="a" class="JQuerypersonvalue"
							value="${arr[0]}"/>
						</td>
						<td>
							<span style="display:none" id="goodsid">${arr[0]}</span>
							<span style="display:none" id="wareid">${arr[1]}</span>
							<span style="display:none" id="groupCompanySn">${arr[2]}</span>
							<span style="display:none" id="unit">${arr[5]}</span>
							<span id="warename">${arr[3]}</span>
						</td>
						<td>
							<span id="goodsname">${arr[4]}</span>
						</td>
						<td>
							<span id="invenquantity">${arr[6]}</span>
						</td>
						<td>
							<span id="invenonline">${arr[7]}</span>
						</td>
						<td>
							<span id="invenunderline">${arr[8]}</span>
						</td>
					</tr>
				</c:if>
				<c:if test="${arr[6]<arr[7]}">
					<tr id="${arr[0]}">
					<td>
						<input type="radio" name="a" class="JQuerypersonvalue"
							value="${arr[0]}"/>
						</td>
						<td>
							<span style="display:none" id="goodsid">${arr[0]}</span>
							<span style="display:none" id="wareid">${arr[1]}</span>
							<span style="display:none" id="groupCompanySn">${arr[2]}</span>
							<span style="display:none" id="unit">${arr[5]}</span>
							<span id="warename">${arr[3]}</span>
						</td>
						<td>
							<span id="goodsname">${arr[4]}</span>
						</td>
						<td>
							<span id="invenquantity">${arr[6]}</span>
						</td>
						<td>
							<span id="invenonline">${arr[7]}</span>
						</td>
						<td>
							<span id="invenunderline">${arr[8]}</span>
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/dutiable/ea_getDutiableList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&tsdate=${tsdate}&tedate=${tedate}">
			</c:param>
		</c:import>
		
		<form name="warnForm" id="warnForm" method="post">
			<div class="jqmWindow" style="width: 220px; right: 45%; top: 20%"
				id="setWarn">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					设置上下限
					<div class="close">
					</div>
				</div>
				<table width="200" id="warnTable">
					<tr align="center">
						<td width="200" align="center">
							<span style="display:none;">
								<input name="inventory.groupCompanySn" id="groupCompanySn"/>
								<input name="inventory.warehouse" id="wareid"/>
								<input name="inventory.goodsID" id="goodsid"/>
								<input name="inventory.unit" id="unit"/>
							</span>
							上限值：<input name="inventory.invenOnline" id="invenonline" size="10"/>
						</td>
					</tr>
					<tr align="center">
						<td width="100" align="center">
							下限值：<input name="inventory.invenUnderline" id="invenunderline" size="10"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="toWarn"
						value=" 确定 " />
				</div>
			</div>
		</form>
		
		<!--搜索窗口 -->
		<%--<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					<tr>
						
						<td width="123" align="right">
							库房:
						</td>
						<td>
							<s:select list="warehouseList"></s:select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>--%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
