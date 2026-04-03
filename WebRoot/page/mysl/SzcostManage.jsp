<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<title>收支费用管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/validate.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/mysl/SzcostManage.js"></script>
<script>
    var basePath = "<%=basePath%>";
	var szid = "";
	var ppageNumber = "${pageNumber}";
	var token = 0;
	var proid = "${myproject.proid}";
	var cost1="${cost1}";
	var cost2="${cost2}";
	var cost3="${cost3}";
	

</script>
</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="40" align="center">序号</th>
					<th width="50" align="center">类型</th>
					<th width="200" align="center">金额</th>
					<th width="120" align="center">发生日期</th>
					<th width="200" align="center">付款人</th>
					<th width="200" align="center">收款人</th>
					<th width="250" align="center">用途</th>
					<th width="250" align="center">备注</th>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${szid}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${szid}" /></td>
						<td><span><%=number%></span></td>
						<td><span id="costType" style="display:none;">${costtype}</span>
							<s:if test='costtype=="00"'>应收款</s:if> <s:if
								test='costtype=="01"'>已收款</s:if></td>
						<td><span id="moneystr">${moneystr}</span>
						<span id="money" style="display:none;">${money}</span>
						</td>
						<td><span id="happenDate">${fn:substring(happenDate,0,10)}</span></td>
						<td><span id="payer">${payer}</span></td>

						<td><span id="payee">${payee}</span></td>
						<td><span id="purpose">${purpose}</span></td>
						<td><span id="remark">${remark}</span>
						<span  style="display:none;" id="szid">${szid}</span>
						<span  style="display:none;" id="szkey">${szkey}</span>
						<span  style="display:none;" id="proid">${proid}</span>
						
						</td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/taskmanage/ea_getSzcostList.jspa?pageNumber=${pageNumber}&search=${search}&myproject.proid=${myproject.proid}">
			</c:param>
		</c:import>
	</div>


	<form name="payForm" id="payForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%"
			id="jqModelpay">
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						<div id="titles"></div>
						<div class="close"></div>
					</div>
				</div>


				<table width="100%" border="0" id="paytbl" align="center"
					cellpadding="10" cellspacing="10"
					style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td align="right" style="width:15%;">费用金额(元)：</td>
						<td align="left"><input type="text" name="myszcost.money"
							id="money" class="put3 isNaN" />
						</td>
						<td align="left">发生日期：</td>
						<td align="left"><input type="text" onfocus="date()"
							name="myszcost.happenDate" id="happenDate" class="put3" readonly/></td>
					</tr>
					<tr>
						<td align="right">付款人：</td>
						<td align="left" colspan="3"><input type="text" maxlength="50" 
							name="myszcost.payer" style="width:60%;" id="payer" class="put3 ckTextLength" />
						</td>

					</tr>

					<tr>
						<td align="right">收款人：</td>
						<td align="left" colspan="3"><input type="text" maxlength="50" 
							name="myszcost.payee" style="width:60%;" id="payee" class="put3 ckTextLength" />
						</td>

					</tr>

					<tr>
						<td align="right">用途：</td>
						<td align="left" colspan="3"><input type="text" maxlength="250" 
							name="myszcost.purpose" style="width:60%;" id="purpose" 
							class="put3 ckTextLength" />
						</td>

					</tr>

					<tr>
						<td align="right">备注：</td>
						<td align="left" colspan="3"><input type="text" class="ckTextLength" maxlength="250" 
							name="myszcost.remark" style="width:60%;" id="remark" />
						</td>

					</tr>

					<tr>
						<td height="30" colspan="5" align="center"><input
							type="button" class="input-button" id="submitpay"
							style="cursor: pointer; width: 80px;" value="保存" /> <input
							type="button" class="input-button close"
							style="cursor: pointer; width: 80px;" value="取消" /> <input
							type="hidden" name="myszcost.costtype" id="costType1" /> <input
							type="hidden" name="myszcost.szid" id="szid" /> <input
							type="hidden" name="myszcost.szkey" id="szkey" />
							<input type="hidden" name="myszcost.proid" id="proid1" />
							</td>
					</tr>
				</table>


			</div>
		</div>
		<s:token></s:token>
	</form>





	<form name="searchForm" id="searchForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 300px; top: 10%" id="jqModel">
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						查询
						<div class="close"></div>
					</div>
				</div>


				<table width="100%" border="0" id="stafftable2" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td align="right">费用类型：</td>
						<td align="left">
						<select><option value="">全部</option><option value="00">应收款</option><option value="01">已收款</option></select>
						</td>
					</tr>

					<tr>
						<td height="30" colspan="5" align="center"><input
							type="button" class="input-button" id="search"
							style="cursor: pointer; width: 80px;" value="查询" /> <input
							name="search" value="search" type="hidden" /> <input
							name="mytask.taskid" id="taskids" type="hidden" />
							<input name="myproject.proid"  value="${myproject.proid}"  type="hidden" />
						</td>
					</tr>
				</table>


			</div>
		</div>
		<s:token></s:token>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>