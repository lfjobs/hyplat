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
		<title>出入库管理</title>
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
			src="<%=basePath%>js/ea/finance/invoicing/wareManagement_list.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var journalNum="";
        	var financialbillID="";
        	var financialgoodid="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var notoken = 0;
        	var billStatus='${billStatus}';
			document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
    				if($("input#journalNum").val()==''){
						return false;
    				}
        			if($("input#journalNum").val()!=''){
			        	$("#SearchForm").attr("action", basePath+"/ea/warehousing/ea_toSearchWare.jspa?pageNumber="+pNumber+"&billStatus="+billStatus);
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
					<!-- 
					<th width="30" align="center">
						选择
					</th>
					<th width="200" align="center">
						公司名称
					</th>
					<th width="150" align="center">
						凭证号
					</th>
					<th width="100" align="center">
						单据类型
					</th>
					<th width="100" align="center">
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
					<th width="90" align="center">
						公司银行账号
					</th>
					<th width="90" align="center">
						往来单位
					</th>					
					<th width="90" align="center">
						单位往来关系
					</th>
					<th width="90" align="center">
						单位往来电话
					</th>
					<th width="90" align="center">
						往来个人
					</th>
					<th width="90" align="center">
						单据状态
					</th>
					 -->
					 <th width="30" align="center">
						选择
					</th>
					<th align="center" width="90">
						费用或品名名称
					</th>
					<th align="center" width="50">
						类型
					</th>
					<th align="center" width="75">
						品名编号
					</th>
					<th align="center" width="90">
						统一分类条码
					</th>
					<th align="center" width="50">
						出库数量
					</th>					
					<th align="center" width="50">
						出库单价
					</th>
					<th align="center" width="50">
						出库金额
					</th>
					<th align="center" width="75">
						库存状态
					</th>
					<th align="center" width="75">
						物品状态
					</th>
					<th align="center" width="75">
						责任人
					</th>
					<th align="center" width="75">
						凭证号
					</th>
						<th width="100" align="center">
						单据类型
					</th>
					<th width="90" align="center">
						单据状态
					</th>
				</tr>
			</thead>
			<tbody>	
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" />
						</td>
						<td>
							<span id="goodsName">${arr[3]}</span>
						</td>
						<td>
							<span id="type">${arr[4]}</span>
						</td>
						<td>
							<span id="goodsNum">${arr[1]}</span>
						</td>
						<td>
							<span id="sortCode">${arr[2]}</span>
						</td>
						<td>
							<span id="quantity">${arr[5]}</span>
						</td>
						
						<td>
							<span id="unitPrice">${arr[6]}</span>
						</td>
						<td>
							<span id="amount">${arr[7]}</span>
						</td>
					
						<td>
							<span id="status" >${arr[8]}</span>
						</td>
						
						<td>
							<span id="goodstatus" >${arr[9]}</span>
						</td>
						<td>
							<span id="StaffName" >${arr[12]}</span>
						</td>
						<td>
							<span id="journalNum" >${arr[10]}</span>
						</td>
						<td>
							<span id="billsType">${arr[11]}</span>
						</td>
						<td>
							<span id="billStatus">${arr[13]}</span>
						</td>
					</tr>
				</c:forEach>
				<!-- 		
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
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
							<span id="ccompanyName">${arr[9]}</span>
						</td>
						<td>
							<span id="ccompanyRelationship">${arr[10]}</span>
						</td>
						<td>
							<span id="ccompanyTel">${arr[11]}</span>
						</td>
						<td>
							<span id="cstaffName">${arr[12]}</span>
						</td>
						<td>
							<span id="billStatus">${arr[13]}</span>
						</td>
					</tr>
				</c:forEach>
				 -->
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/warehousing/ea_getWareManagementList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&billStatus=${billStatus}">
			</c:param>
		</c:import>
		<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
				id="jqModelSearch">
				<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396px"  >
					<tr >
						<td align="right" width="100px">
							黏贴单编号：
						</td>
						<td>
							<input id="journalNum" style="width: 200px"
								name="financialBill.journalNum" />
						</td>
					</tr>
					<tr>
						<td align="right" width="100px">
							责任人：
						</td>
						<td>
							<s:select list="%{#request.stafflist}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="staffID" id="staffID"	
								name="financialBill.staffID" listValue="staffName"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right" width="100px">
							单位往来关系：
						</td>
						<td>
							<s:select list="%{#request.connectionlist}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="codeValue" id="ccompanyRelationship"								
								name="financialBill.ccompanyRelationship" listValue="codeValue"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right" width="100px">
							个人往来关系：
						</td>
						<td>
							<s:select list="%{#request.codeRelationList}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="codeValue" id="cstaffRelationship"
								name="financialBill.cstaffRelationship" listValue="codeValue" theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right" width="100px">
							制单日期：
						</td>
						<td >
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 92px" />至<input id="edate" name="edate" onfocus="date(this);"
								style="width: 92px" />
						</td>
					</tr>
					<tr>
						<td align="right" width="100px">
							往来单位：
						</td>
						<td>
							<input id="sdate" name="financialBill.ccompanyName"
								style="width: 200px" />
						</td>
					</tr>
					<tr>
						<td align="right" width="100px">
							往来个人：
						</td>
						<td>
							<input id="sdate" name="financialBill.cstaffName"
								style="width: 200px" />
						</td>
					</tr>
					<tr>
						<td align="right" width="100px">
							品名编号：
						</td>
						<td>
							<input id="sdate" name="financialBillsGood.goodsNum"
								style="width: 200px" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 "/>
					<input name="search" type="hidden" value="search" />
				</div>
				</form>
			</div>
			
		
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
