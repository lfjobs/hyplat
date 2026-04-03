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
		<title>验货管理</title>
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
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/inspect_list1.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var financialbillID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var pageCount='${pageForm.pageCount}';
        	var notoken = 0;
        	var inspectmap="";
        	var select=0;
        	var xmtype="${xmtype}";
        	var inputstatus="${param.taxstatus}";
        </script>
	</head>
	<body>
		<form name="Inspectform" id="Inspectform">
			<input type="submit" name="submit" style="display: none" />
			<s:token />
		<table class="flexme11" id="tt">
			<thead>
				<%--<tr>
					<th align="center" width="150">
						单据编号
					</th>
					<th align="center" width="90">
						品名名称
					</th>
					<th align="center" width="50">
						类别
					</th>
					<th align="center" width="75">
						品名编号
					</th>
					<th align="center" width="90">
						统一分类条码
					</th>
					<th align="center" width="50">
						单位
					</th>
					<th align="center" width="50">
						采购数量
					</th>					
					<th align="center" width="50">
						采购单价
					</th>
					<th align="center" width="50">
						采购金额
					</th>
					<th align="center" width="70">
						收货数量
					</th>
					<th align="center" width="75">
						合格数量
					</th>
					<th align="center" width="75">
						库存状态
					</th>
					<th align="center" width="75">
						物品状态
					</th>
					<th align="center" width="75">
						采购员
					</th>
					<th align="center" width="75">
						供应商
					</th>
				</tr>--%>
				<tr>
					<th width="40" align="center">选择</th>
					<th width="130" align="center">单据编号</th>
					<th width="170" align="center">公司名称</th>
					<th width="120" align="center">采购员</th>
					<th width="120" align="center">部门</th>
                    <th width="120" align="center">项目名称</th>
					<th width="170" align="center">付款方式</th>
					<th width="120" align="center">验货人</th>
					<th width="100" align="center">单据时间</th>
					<th width="90" align="center">单据状态</th>
				</tr>
			</thead>
			<tbody>			
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0]}" class="xggoods">
						<td>
							<input type="radio" name="financialgoodID" class="JQuerypersonvalue" value="${arr[0]}" />
						</td>
						<td>
							<span id="journalNum" >${arr[1]}</span>
						</td>
						<td>
							<span id="companyName">${arr[2]}</span>
						</td>
						<td>
							<span id="staff">${arr[3]}</span>
						</td>
						<td>
							<span id="departmentName">${arr[4]}</span>
						</td>
                        <td>
                            <span id="projectName">${arr[9]}</span>
                        </td>
						<td>
							<span id="paymentType">${arr[5]==00?'请选择':arr[5]==01?'银行转帐':arr[5]==02?'银行支票转账'
                                    :arr[5]==03?'支付宝转帐':arr[5]==04?'App转账':arr[5]==05?'POS机转账'
                                    :arr[5]==06?'现金转账':''}</span>
						</td>
						<td>
							<span id="unit" name="staffsName">${arr[6]}</span>
						</td>
						<td>
							<span id="cashierDate">${arr[7]}</span>
						</td>
						<td>
							<input type="hidden" class="status" value="${arr[8]}"/>
							<c:if test="${arr[8]=='13'}">
								<span>待检验</span>
							</c:if>
							<c:if test="${arr[8]=='14'}">
							    <span>已验货</span>
							</c:if>
							<c:if test="${arr[8]=='15'}">
							    <span>已入库</span>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/purchase1/ea_getinspectList.jspa?search=${search}&pageNumber=${pageNumber}">
			</c:param>
		</c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
