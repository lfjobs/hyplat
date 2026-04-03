<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.tiantai.nwa.tbank.bo.BankAccount"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>银行账${inout}管理</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/overlayer.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>js/jqModal/css/jqModal_blue.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />

		<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>		

		<script type="text/javascript">
			var basePath = "<%=basePath%>";	
			var pNumber = ${pageNumber};  
            var search = '${search}';            
			$(function(){
				 $("#tosearch").click(function(){
					var _account = $("#accountlist").find("option:selected").text().split("|");														
					$("#accountno").val(_account[0]);
					$("#banksx").val(_account[1]);
					$("#pkey").val($("#accountlist").find("option:selected").val());
					$("#pageNumber").val("0");
					$(".put3").trigger("blur");
					if ($("#SearchForm .error").length) {
						alert("请填完所有必填项");
						return;
					}
					$("#SearchForm").attr("action",basePath+"/ea/bankaccountInOutManager/ea_showBankAccountList.jspa");
					document.SearchForm.submit.click();
					});									
				});
		</script>	
	</head>
	<%
		List accountList = (List) request.getAttribute("accountList");
		Iterator iter = accountList.iterator();
		String pkey = (String)request.getAttribute("pkey"); 
		if (null==pkey) pkey = "";
	%>
	<body style="background-image: url(<%=basePath%>/images/bg01.jpg)">
		<form name="SearchForm" method="post" id="SearchForm">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" name="banksx" id="banksx" value="${banksx}"/>
			<input type="hidden" name="accountno" id="accountno" value="${accountno}"/>	
			<input type="hidden" name="pageNumber" id="pageNumber" value="${pageNumber}"/>
			<input type="hidden" name="innerAction" id="innerAction" value="${innerAction}"/>	
			<input type="hidden" name="inout" id="inout" value="${inout}"/>	
			<input type="hidden" name="actionFlag" id="actionFlag" value="queryInoutList"/>
			<input type="hidden" name="pkey" id="pkey" value="${pkey}"/>
			<div class="main">
				<table width="98%" border="0" align="center" cellpadding="2"
					cellspacing="1" bgcolor="#D1DDE9" style="margin-top: 8px">
					<tr bgcolor="#E7E7E7">
						<td height="24" width="20%" align="left" bgcolor="#d8e6f4">
							&nbsp;
							<span class="txt">银行${inout}明细</span>&nbsp;
						</td>
						<td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;
							
						</td>
						<td width="20%" height="24" align="right" bgcolor="#d8e6f4">
						</td>
					</tr>
				</table>
				<table id="cataffSearchTable" border="0" width="100%" cellspacing="10" style="border: #D1DDE9 1px solid;">
					<tr>
						<td align="right" class="txt02">
							选择银行账号：
						</td>
						<td colspan="2">							
							<select name="accountlist" id="accountlist">
								<%
									while (iter.hasNext()) {
										BankAccount bankAccount = (BankAccount) iter.next();
								%>
								<option value="<%=bankAccount.getPkey()%>" <% if ( bankAccount.getPkey().equals(pkey) ) out.print("selected"); %>   ><%=bankAccount.getAccount()%>|<%=bankAccount.getBanksx()%>|<%=bankAccount.getProvcode()%>|<%=bankAccount.getCurrency()%></option>
								<%
									}
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							开始时间：
						</td>
						<td colspan="2">
							<input type="text" name="startDate" id="startDate"  class="put3" value="${startDate}"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();},maxDate:'%y-%M-{%d-1}'})"
								readonly size="25" />
						</td>
					</tr>
					<tr>
						<td align="right" style="width: 30%;">
							截止时间：
						</td>
						<td style="width: 18%;">
							<input type="text" name="endDate" id="endDate" class="put3"  value="${endDate}"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-{%d-1}', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly size="25" />
						</td>
						<td>
							<input type="button" class="input-button" name="tosearch"
								id="tosearch" value=" 查询 " />
							<input name="search" type="hidden" value="search" />
						</td>
					</tr>
				</table>
				<table id="resultTable" border="0" width="100%" cellspacing="10" style="border: #D1DDE9 1px solid;">
					<tr>
						<td>
							<div style="position: absolute; width: 100%; height: 400px; overflow: scroll;">
								<table class="table" cellspacing="0" width="2240" >
									<thead>
										<tr class="tablewith">											
											<th width="10px" align="center">序号</th>
											<th width="25px" align="center">省市代码</th>
											<th width="30px" align="center">账号</th>
											<th width="20px" align="center">货币码</th>
											<th width="40px" align="center">交易日期</th>
											<th width="30px" align="center">交易时间</th>
											<th width="50px" align="center">日志号</th>
											<th width="25px" align="center">交易类型</th>
											<th width="30px" align="center">交易行号</th>
											<th width="30px" align="center">户名</th>
											<th width="20px" align="center">发生额标志</th>
											<th width="50px" align="center">对方账号省市代码</th>
											<th width="30px" align="center">对方账号</th>
											<th width="50px" align="center">对方账号货币码</th>
											<th width="50px" align="center">对方账号户名</th>
											<th width="50px" align="center">对方账号开户行</th>
											<th width="20px" align="center">现转标志</th>
											<th width="20px" align="center">错账日期</th>
											<th width="30px" align="center">错账传票号</th>
											<th width="20px" align="center">交易金额</th>
											<th width="20px" align="center">账户余额</th>
											<th width="20px" align="center">上笔余额</th>
											<th width="30px" align="center">手续费总额</th>
											<th width="20px" align="center">凭证种类</th>
											<th width="40px" align="center">凭证省市代号</th>
											<th width="30px" align="center">凭证批次号</th>
											<th width="20px" align="center">凭证号</th>
											<th width="30px" align="center">客户参考号</th>											
											<th width="20px" align="center">交易码</th>
											<th width="20px" align="center">柜员号</th>
											<th width="20px" align="center">传票号</th>
											<th width="20px" align="center">摘要</th>
											<th width="20px" align="center">附言</th>
											<th width="20px" align="center">交易来源</th>
										</tr>
									</thead>
									<tbody> 
										<c:forEach var="arr" items="${pageForm.list}">
											<tr>												
												<td><span>${arr[0]}</span></td>												
												<td><span>${arr[5]}</span></td>
												<td><span>${arr[6]}</span></td>
												<td><span>${arr[7]}</span></td>
												<td><span>${arr[8]}</span></td>
												<td><span>${arr[9]}</span></td>
												<td><span>${arr[10]}</span></td>
												<td><span>${arr[11]}</span></td>
												<td><span>${arr[12]}</span></td>
												<td><span>${arr[13]}</span></td>
												<td><span>${arr[14]}</span></td>
												<td><span>${arr[15]}</span></td>
												<td><span>${arr[16]}</span></td>
												<td><span>${arr[17]}</span></td>
												<td><span>${arr[18]}</span></td>
												<td><span>${arr[19]}</span></td>
												<td><span>${arr[20]}</span></td>
												<td><span>${arr[21]}</span></td>
												<td><span>${arr[22]}</span></td>
												<td><span>${arr[23]}</span></td>
												<td><span>${arr[24]}</span></td>
												<td><span>${arr[25]}</span></td>
												<td><span>${arr[26]}</span></td>
												<td><span>${arr[27]}</span></td>
												<td><span>${arr[28]}</span></td>
												<td><span>${arr[29]}</span></td>
												<td><span>${arr[30]}</span></td>
												<td><span>${arr[31]}</span></td>
												<td><span>${arr[32]}</span></td>
												<td><span>${arr[33]}</span></td>
												<td><span>${arr[34]}</span></td>
												<td><span>${arr[35]}</span></td>
												<td><span>${arr[36]}</span></td>
												<td><span>${arr[37]}</span></td>
											</tr> 
										</c:forEach>
									</tbody>
								</table>								
								<c:import url="/page/ea/page_navigator.jsp">
									<c:param name="actionPath" value="/ea/bankaccountInOutManager/ea_showBankAccountList.jspa?actionFlag=queryInoutList&pageNumber=${pageNumber}&innerAction=${innerAction}&inout=${inout}&pkey=${pkey}">
									</c:param>
								</c:import>								
							</div>							
						</td>
					</tr>
				</table>
			</div>
		</form>		
	</body>
</html>