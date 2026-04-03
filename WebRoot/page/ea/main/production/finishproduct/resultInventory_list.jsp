<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="hy.ea.bo.Company"%>
<%@ page import="hy.ea.bo.CAccount"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company) session.getAttribute("currentcompany");
	CAccount ac = (CAccount) session.getAttribute("account");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>库房管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/finishproduct/resultInventory_list.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
.xx1{
	color:#3300CC;
	margin-right:2px;}
</style>
<script  type="text/javascript">
	var basePath="<%=basePath%>";
	var fiveClear="${fiveClear}";
	var category="${category}";
	var fiveClearName="${fiveClearName}";
	var type="${type}";
	var invID="";
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>

</head>
<body>
 <form style="display: none;" name="addressForm" id="addressForm" method="post">
 	<s:token></s:token>
 	<input type="submit" name="submit" style="display:none"/>
</form>
  <table   class="address" >
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">请选择</th>
            <th width="100" align="center" >物品名称</th>
			<th width="100" align="center" >物品类别</th>
			<th width="100" align="center" >条码</th>
			<th width="100" align="center" >库存数量</th>
			<th width="100" align="center" >单价</th>
			<th width="100" align="center" >总价</th>
			<th width="100" align="center" >物品状态</th>
			<th width="100" align="center" >单位</th>
            <th width="100" align="center" >库存预警值</th>
            <th width="100" align="center" >库房位置</th>
      </tr>
    </thead>
		<tbody>
          <c:forEach items="${pageForm.list}" var="invList">
				<tr class="td_bg01 saveAjax" name="add" id="${invList.inventoryID}">
					<td class="td_bg01"><input type="radio" name="invRadio"
						class="invRadio" value="${invList.inventoryID}" /> </a></td>
					<td class="td_bg01"><span id="classes" class="datas">${invList.goodsName}</span>
					</td>
					<td class="td_bg01"><span id="area" class="datas">${invList.goodsType}</span>
					</td>
					<td class="td_bg01"><span id="place">${invList.barcode}</span>
					</td>
					<td class="td_bg01"><span id="amount">${invList.invenQuantity}</span>
					</td>
					<td class="td_bg01"><span id="principal" class="datas">${invList.unitPrice}</span>
					</td>
					<td class="td_bg01"><span id="afforestDate" class="datas">${invList.sumPrice}</span>
					</td>
					<td class="td_bg01"><span id="goodstatus" class="datas">${invList.goodstatus}</span>

					</td>
					<td class="td_bg01"><span id="afforestDate" class="datas">${invList.unit}</span>
					</td>
					<td class="td_bg01"><c:if
							test="${invList.invenUnderline=='红色'}">
							<span id="suan" class="xx"><font size="" color="#FF0000">${invList.barcode}</font>
							</span>
						</c:if> <c:if test="${invList.invenUnderline =='蓝色'}">
							<span id="suan" class="xx1"><font size="" color="#0033FF">${invList.barcode}</font>
							</span>
						</c:if> <c:if test="${invList.invenUnderline =='黑色'}">
							<span id="suan" class="datas">${invList.barcode}</span>
						</c:if> <c:if test="${invList.invenUnderline ==''}">
							<span id="suan" class="datas"></span>
						</c:if></td>
					<td class="td_bg01"><span>${invList.warehouseName}</span></td>
				</tr>
			</c:forEach>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/warehousing/ea_getInventoryManagementList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
