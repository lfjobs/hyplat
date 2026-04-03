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

<title>采购申请单编辑</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
			<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />

<script src="<%=basePath%>js/ea/production/cprocedure/purBudSheet_add.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/popLayer/css/popstyle.css" />
<script src="<%=basePath%>js/popLayer/js/popLayer.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />


<script type="text/javascript">

var basePath="<%=basePath%>";
var notoken = 0;
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
		<div class="main">
			<div class="top" style="min-width:700px;">采购申请单</div>
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
								 <input type="button" class="btncon deptbtn" onclick="pop('members')" id="selectme" />
							</td>

						</tr>
						<tr>
							<td align="right">产品编号：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom productCode" name="cashierBills.projectCode"
								readonly value='${cashierBills.projectCode}'/> <input type="button" class="btncon deptbtn selectpr"
								onclick="pop('products')" />
							</td>
							<td align="right">产品名称：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom goodsName" name="cashierBills.projectName"
								readonly value="${cashierBills.projectName}"/> <input type="hidden" name="cashierBills.proID"
								class="ppID" value="${cashierBills.proID}" readonly /> <input type="button"
								class="btncon deptbtn selectpr" onclick="pop('products')" /></td>
							<td align="right">凭证号：</td>
							<td><input type="text" style="width:140px;"
								class="inputbottom" name="cashierBills.journalNum" readonly
								value="${cashierBills.journalNum}" />
								<input type="hidden" name="cashierBills.cashierBillsID" value="${cashierBills.cashierBillsID}" />
								<input type="hidden" name="cashierBills.cashierBillsKey" value="${cashierBills.cashierBillsKey}" />
									<input type="hidden" name="fiveClear" 
								value="${fiveClear}" />
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
						</tr>
						</thead>
						<tbody id="sublist">
						<%
					    int number = 1;
				       %>
						<s:iterator  value="list">
                        <tr>
							<td align="center"  width="3%"><%=number%></td>
							<td align="center" width="3%">${goodsNum}</td>
							<td align="center" width="10%">${goodsName}</td>
							<td align="center" width="3%">${boxStandard};</td>
							<td align="center" width="3%">${goodsVariableID}</td>
							<td align="center" width="3%"><input class='jisuan price' style='border:none;width:99%;'  type="text" name="logbookmap[<%=number%>].price" value="${price}"/></td>
							<td align="center" width="3%"><input class='jisuan quantity' style='border:none;width:99%;' type="text" name="logbookmap[<%=number%>].quantity" value="${quantity}"/></td>
							<td align="center" width="3%"><input class='money' style='border:none;width:99%;' tyle='border:none;width:99%;' type="text" name="logbookmap[<%=number%>].money" value="${money}"/></td>
							<td align="center" width="3%"><input style='border:none;width:99%;' type="text" name="logbookmap[<%=number%>].remark" value="${remark}"/>
							<input type="hidden" name="logbookmap[<%=number%>].goodsID" value="${goodsID}"/>
							</td>
		
						</tr>
						<%
						number++;
					   %>
						</s:iterator>
						</tbody>
						
					</table>
						
						
						<table  cellspacing="10px">
                        <tr>
							<td align="right">制单人：</td>
							<td align="left"><input type="text" style="width:150px;" class="inputbottom" name="plan.tradeCode"
								readonly value="${cashierBills.staffName}(${cashierBills.staffCode})"/> 
							<td align="right">制单时间：</td>
							<td align="left"><input type="text" class="inputbottom" style="width:150px;"  readonly value="${fn:substring(cashierBills.cashierDate,0,19)}" /> 
						</tr>
					</table>
					

				</div>
			</div>

		</div>
		<div class="bottom">
			<input type="button" class="btn save" value="提交保存" />
		</div>
		</div>
	</form>



	<!-- 选择产品 -->

	<div id="products" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择项目产品</span>
			</div>
			<div class="chooseborder">
		        <table width="99%" height="33" id="searchpro"     border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								产品编码或名称：
							</td>
							<td width="110">
								<input name="parameter" class="input" id="parameter" size="10"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn01" id="searchProduct"
									name="button7" value="查询"/>
								<input type="button" class="btn01" id="selectProduct"
									name="button5" value="确定" />
			
								
								
							</td>
							<td width="80">
								<a id="wpsyp" title="0" style="cursor:pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="wpxyp" title="0" style="cursor:pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycountp"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="83%" valign="top" align="left">
								<div id="body_03"
									style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>

			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('products')" value="关闭" />
			</div>
		</div>
	</div>
	
	
		
	<!-- 选择人员 -->
	<div id="members" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择人员</span>
			</div>
			<div class="chooseborder">
				<div id="goodsTree" style=" border: 0px solid #000000;"></div>
		        <table width="99%" height="33" id="searchuser"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								姓名：
							</td>
							<td width="110">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn01" id="searchuu"
									name="button7" value="查询"/>
								<input type="button" class="btn01" id="qduser"
									name="button5" value="确定" />
								<input type="button" class="btn01 xzgr" name="button" value="新增" />
			
							   <input type="hidden" name="parms" id="grparms" />
								
							</td>
							<td width="80">
								<a id="grsy" title="0" style="cursor:pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="grxy" title="0" style="cursor:pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="16%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="grTree" 
												style="overflow: scroll; z-index: 99; width:180px;height: 340px;border:1px solid #ccc;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc;border-left:none; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>

			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('members')" value="关闭" />
			</div>
		</div>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>