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

<title>比价选标</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
			<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />


<script type="text/javascript">

var basePath="<%=basePath%>";
var fiveClear="${fiveClear}";

$(function(){

//提交资金申请
$(".save").click(function(){
	$("#mainForm").attr("target", "hidden").attr("action",
				basePath + "ea/purchasebids/ea_submitFundApply.jspa?ppID="+$(".ppIDR").attr("id"));

		document.mainForm.submit.click();
		token = 2;

});

});


	//查看投标信息
	function viewbidsinfo(cashierBillsID, goodsID) {
		window
				.open(basePath
						+ "ea/purchasebids/ea_productPriceMatch.jspa?inviteBids.cashierBillsID="
						+ cashierBillsID + "&bidsinfo.goodsID=" + goodsID);
	}
	
</script>
<style type="text/css">
 th,td {
    white-space: nowrap;
}
</style>


</head>
<body>
<div id="apDiv1"></div>
	<form id="mainForm" name="mainForm" method="post">
		<input type="submit" name="submit" style="display:none;">
		<div class="main" id="tableprint">
			<div class="top" style="min-width:700px;">比价选标</div>
			<div class="body" style="min-width:700px;">

				<div class="showinfo show">
					<table id="productbl" cellspacing="5px">
						<tr>
							<td align="right">公司：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom" name="cashierBills.companyName" readonly
								value="${cashierBills.companyName}" /></td>
							<td align="right">部门：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom" name="cashierBills.departmentName" readonly
								value="${cashierBills.departmentName}" />
								</td>
							<td align="right">责任人：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom" readonly
								value="${cashierBills.staffName}(${cashierBills.staffCode})" /> 
								
							</td>

						</tr>
						<tr>
							<td align="right">产品编号：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom productCode" name="cashierBills.projectCode"
								readonly value='${cashierBills.projectCode}'/>
							</td>
							<td align="right">产品名称：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom goodsName" name="cashierBills.projectName"
								readonly value="${cashierBills.projectName}"/> <input type="hidden" name="cashierBills.proID"
								class="ppID" readonly /> </td>
							<td align="right">凭证号：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom" name="cashierBills.journalNum" readonly
								value="${cashierBills.journalNum}" />
								<input type="hidden" name="inviteBids.ibId" value="${inviteBids.ibId}" />
								<input type="hidden" name="inviteBids.cashierBillsID" value="${inviteBids.cashierBillsID}" />
								<input type="hidden" name="inviteBids.status" id="cstatus" />
								<input type="hidden" name="fiveClear" 
								value="${fiveClear}" />

							</td>

						</tr>
						<tr>
						<td align="right">有效日期：</td>
							<td colspan="5"><input type="text" style="width:140px;"  name="startTime"
								readonly value='${fn:substring(inviteBids.startDate,0,19)}' class="inputbottom" />
							   &nbsp;至&nbsp;&nbsp;<input type="text" style="width:140px;"  name="endTime"
								readonly value='${fn:substring(inviteBids.endDate,0,19)}' class="inputbottom" />
							</td>
							</tr>

					</table>


					<table class="table" style="margin-top:20px;">
					<thead>
						<tr>
							<th align="center">序号</th>
							<th align="center">物品编号</th>
							<th align="center">物品名称</th>
							<th align="center">型号</th>
							<th align="center">单位</th>
							<th align="center">预算单价</th>
							<th align="center">预算数量</th>
							<th align="center">预算金额</th>
							<th align="center">备注</th>
							<th align="center">招标状态</th>
							<th align="center">操作</th>
						</tr>
						</thead>
						<tbody id="sublist">
						<%
					    int number = 1;
				       %>
						<s:iterator  value="list">
                        <tr class="ppIDR" id="${goodsBillsID}">
							<td align="center"><%=number%></td>
							<td align="center">${goodsNum}</td>
							<td align="center">${goodsName}</td>
							<td align="center">${boxStandard};</td>
							<td align="center">${goodsVariableID}</td>
							<td align="center">${price}</td>
							<td align="center">${quantity}</td>
							<td align="center">${money}</td>
							<td align="center">${remark}</td>
							<td align="center"><c:if test="${mapbid[goodsID]!=null}">${mapbid[goodsID]}</c:if><c:if test="${mapbid[goodsID]==null}">未选标</c:if></td>
							<td align="center"><a href="javascript:viewbidsinfo('${cashierBillsID}','${goodsID}');">查看投标</a></td>
		
						</tr>
						<%
						number++;
					   %>
						</s:iterator>
						</tbody>
						
					</table>

					<table  cellspacing="10px" style="margin-top:15px;">
					    	<tr>
							<td align="right" >招标说明：</td>
							<td colspan="5"><input type="text" class="inputbottom" name="inviteBids.remark"  readonly value="${inviteBids.remark}" style="width:500px;"/>
							</td>
						</tr>
                        <tr>
							<td align="right">发布部门：</td>
							<td align="left"><input type="text" style="width:150px;" class="inputbottom" 
								readonly value="${inviteBids.organizationName}"/> 
							<td align="right">发布人：</td>
							<td align="left">
							   <input type="text" style="width:150px;" class="inputbottom" 
								readonly value=<s:if test='inviteBids.status=="00"'>"${staff.staffName}"</s:if><s:else>"${inviteBids.publishName}"</s:else>/> 
							<td align="right">发布时间：</td>
							<td align="left"><input type="text" class="inputbottom" style="width:150px;"  readonly value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />" /> 
						</tr>
					</table>
					
				<div style="margin-bottom:20px;margin-top:50px;text-align:center;" class="botoom">
				<input type="button" class="btn save"   style="width:90px;" value="提交资金申请"  />
			    </div>
					

				</div>
				
			</div>
			

		</div>
		
		</div>
	</form>



	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>