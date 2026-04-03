<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title>产品比较</title>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/priceMatch.js"></script>
<script type="text/javascript">

var basePath="<%=basePath%>";
var fiveClear="${fiveClear}";


</script>
<style type="text/css">
 th,td {
    white-space: nowrap;
}
</style>



</head>
<body>
	<form id="mainForm" name="mainForm" method="post">
		<input type="submit" name="submit" style="display:none;">
		<input type="hidden" name="bidsinfo.bfId" id="bfids" />
		<input type="hidden" name="inviteBids.cashierBillsID"  value="${inviteBids.cashierBillsID}" />
		<div class="main" style="margin-bottom:50px;">
			<div class="top" style="width:99%;text-align:center;min-width:1024px;">基本信息对比</div>
			  <center>
					<table class="table"  width="99%"  cellpadding="10px" style="margin-top:5px;cellpadding:50px;">
					<thead>
						<tr>
						    <th align="center">选择</th>
						  	<th align="center">序号</th>
							<th align="center">主图</th>
							<th align="center">产品名称</th>
							<th align="center">型号</th>
							<th align="center">单位</th>
							<th align="center">微分金单价</th>
							<th align="center">数量</th>
							<th align="center">金额</th>
							<th align="center">投标方</th>
							<th align="center">投标方类型</th>
							<th align="center">投标日期</th>
							<th align="center">投标说明</th>
						    <th align="center">选标责任人</th>
						
						</tr>
						</thead>
						<tbody id="sublist">
						<%
					    int number = 1;
				       %>
						<s:iterator  value="list">
                        <tr>
                            <td align="center">
                              <s:if test="iszbid==00">
                              <input type="checkbox" value="${bfId}" name="JQuerypersonvalue"/>
                              </s:if>
                              <s:else>
                                                                                     已中标
                              </s:else>
                            
                            
                            </td>
							<td align="center"><%=number%></td>
							<td align="center"><img src="<%=basePath%>${mainpicurl}" width="80" height="80"></td>
							<td align="center">${tgoodsName}</td>
							<td align="center" class="boxStandard">${boxStandard}</td>
							<td align="center">${goodsVariableID}</td>
							<td align="center" class="tprice">${tprice}</td>
							<td align="center">${tquantity}</td>
							<td align="center">${tmoney}</td>
							<td align="center">${bidperson}</td>
							<td align="center"><s:if test='bidtype=="00"'>公司</s:if><s:else>个人</s:else></td>
							<td align="center">${fn:substring(bidDate,0,19)}</td>
							<td align="center">${bidremark}</td>
							<td align="center">${selectName}</td>
		
						</tr>
						<%
						number++;
					   %>
						</s:iterator>
						</tbody>
						
					</table>
				</center>
			</div>

	</form>

      <div class="bottomgl">
      <input type="button"  value="高亮显示不同项"  class="light" />
      <input type="button"  value="取消高亮不同项" class="cancelight" style="display:none;"/>
        <input type="button"  value="确定中标"  class="selectbid" />
      </div>

	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>