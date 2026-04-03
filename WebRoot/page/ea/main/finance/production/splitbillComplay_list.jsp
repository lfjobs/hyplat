<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="hy.ea.bo.Company"%>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany");%>
        <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>单据拆分公司汇总</title>
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
		
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/splitbillCompany_list.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
		    var accname="${ManStaffName}";
		    var companyid="<%=c.getCompanyID()%>";
       		var treeID = '<%=session.getAttribute("organizationID")%>';
       		var treeNames="<%=session.getAttribute("organizationName")%>";
        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var status="";
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var  differenceStyle="${differenceStyle}";
        	var bill="${BType }";
        	var zz="${zz}";
        	var billsTypess="${billsType}";
        	var notoken = 0; 
        	var sztype = "${sztype}";
        	/* $(function() {
        		if(billsTypess=="[]"){
        			alert("无此类单据！不能执行操作！");
        			document.location.href=basePath+"page/ea/main/navigation/finace_c.jsp";
        		}
        	}) */
        	document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
        			$("form#SearchForm").find("input#journalNum").each(function(){
					if($(this).val()==""){
						return false;
					}else{
						$("#SearchForm").attr("action", basePath+"/ea/cashierbills/ea_toSearch.jspa?pageNumber="+pNumber);
                    	document.SearchForm.submit.click();
					}
				});
    			}
			}
        </script>
	</head>
	<body >
		<form name="CashierBillsform" id="CashierBillsform">

			<input type="submit" name="submit" style="display: none" />
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
					<th width="150" align="center">
						黏贴单编号
					</th>
					<th width="100" align="center">
						单据类别
					</th>
					<th width="100" align="center">
						部门
					</th>
					<th width="80" align="center">
						责任人
					</th>
					<th width="90" align="center">
						制单日期
					</th>
					<th width="80" align="center">
						实物仓库
					</th>
					<th width="80" align="center">
						财务仓库
					</th>
					<th width="80" align="center">
						资料仓库
					</th>
					<th width="180" align="center">
						往来单位
					</th>
					<th width="90" align="center">
						单位银行账号
					</th>
					<th width="90" align="center">
						单位往来关系
					</th>
					<th width="90" align="center">
						往来个人
					</th>
					<th width="90" align="center">
						个人银行账号
					</th>
					<th width="90" align="center">
						个人往来关系
					</th>
					<th width="90" align="center">
						主管审核人
					</th>
					<th width="90" align="center">
						会计审核人
					</th>
					<th width="90" align="center">
						出纳审核人
					</th>
					<th width="90" align="center">
						单据状态
					</th>
					<th width="90" align="center">
						附件管理
					</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list">
					<tr id="${cashierBillsID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${cashierBillsID}" />
						</td>
						   <td>
                                <span id="companyname">${companyname}</span>
                            </td>
                            <td>
                                <span id="journalNum">${journalNum}</span>
                            </td>
                            <td>
                                <span id="billsType">${billsType}</span>
                            </td>
                            <td>
                                <span id="departmentname">${departmentname}</span>
                            </td>
                            <td>
                                <span id="staffname">${staffname}</span>
                            </td>
                              <td>
                                <span id="cashierDate">${fn:substring(cashierDate, 0, 10)}</span>
                            </td>
                             <td>
                                <span id="afterDiscount">${afterDiscount}</span>
                            </td>
                              <td>
                                <span id="bankDepotName">${bankDepotName}</span>
                            </td>
                             <td>
                                <span id="dataDepotName">${dataDepotName}</span>
                            </td>
                            <td>
                                <span id="ccompanyname">${ccompanyname}</span>
                            </td>
                            <td>
                                <span id="accountNum">${accountNum}</span>
                            </td>
                            <td>
                                <span id="contactConnections">${contactConnections}</span>
                            </td>
                            <td>
                                <span id="contactUserName">${contactUserName}</span>
                            </td> 
                             <td>
                                <span id="userAccountNum">${userAccountNum}</span>
                            </td>
                             <td>
                                <span id="phone">${phone}</span>
                            </td>
                            <td>
                                <span id="responsible">${responsible}</span>
                            </td>
                            <td>
                                <span id="accountant">${accountant}</span>
                            </td>
                             <td>
                                <span id="cashier">${cashier}</span>
                            </td>
                            <td>
                                <span id="statuss">${status=='04'?"待主管审核":
                                status=='05'?"待会计审核":status=='06'?"待出纳审核":status=='07'?"已审核":
                                status=='20'?"税务单据":status=='30'?"未审核作废":status=='11'?"驳回待修改":status=='09'?"待确认收款":status=='40'?"预算收款单":status==null?"":"驳回作废"}</span>
                                <input type="hidden" value="${status}" id="status"/>
                            </td>
                            <td>
                               <a href="#" onclick="fj('${cashierBillsID}')" class="fj">附件</a>
                            </td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/splitbillcompany/ea_getCashierBillsList.jspa?zz=${zz}&search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
			</c:param>
		</c:import>
		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
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
							单据类别：
						</td>
						<td>
							<s:select list="%{#request.billsType}" id="types"
								headerKey="" headerValue="请选择" name="cashierBillsVO.billsType"
								theme="simple" listKey="codeValue" listValue="codeValue"></s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							黏贴单编号：
						</td>
						<td>
							<input id="journalNum" style="width: 195px"
								name="cashierBillsVO.journalNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							部门：
						</td>
						<td>
							<select name="cashierBillsVO.departmentID" style="width: 200px"
								id="orgID">
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							 <select name="cashierBillsVO.staffID" id='person'>
                                <option value="">
                                    请选择部门
                                </option>
                            </select>
						</td>
					</tr>
					<tr>
						<td align="right">
							单位往来关系：
						</td>
						<td>
							<s:select list="%{#request.connectionlist}"
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="cashierBillsVO.contactConnections" listValue="codeValue"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							个人往来关系：
						</td>
						<td>
							<s:select list="%{#request.codeRelationList}"
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="cashierBillsVO.phone" listValue="codeValue" theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							制单日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />至<input id="edate" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来单位：
						</td>
						<td>
							<input id="sdate" name="cashierBillsVO.ccompanyname"
								style="width: 188px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来个人：
						</td>
						<td>
							<input id="sdate" name="cashierBillsVO.contactUserName"
								style="width: 188px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单据状态：
						</td>
						<td>
						<select name="cashierBillsVO.status" style="width: 190px">
								<option value="" selected="selected">
									全部
								</option>
								<option value="04">
									待主管审核
								</option>
								<option value="05">
									待会计审核
								</option>
								<option value="06">
									待出纳审核
								</option>
								<option value="07">
									已审核
								</option>
								<option value="11">
									驳回待修改
								</option>
								<option value="09">
									未确认收款
								</option>
								<option value="30">
									未审核作废
								</option>
						</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 "/>
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		
		<!--搜索窗口 -->
		<form name="ExcelForm" id="ExcelForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
				id="jqModelExcel">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					
					<tr>
						<td align="right">
							报账日期：
						</td>
						<td style="width: 200px">
							<input id="sdates" name="sdate" onfocus="date(this);"
								style="width: 85px" />至<input id="edates" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearchExcel"
						value=" 查询 "/>
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
	<!-- 审核记录 -->
	<form method="post">
		<div class="jqmWindow" style="width: 600px;right: 25%;top:15%"
			id="jqModelCheck">
			<div class="drag">
				审核记录
				<div class="close"></div>
			</div>
			<table width="596" id="CheckTable"  cellspacing='5px' cellpadding='5px'>
				<tr id="checkTitle">
					<th width="200" align="center">审核阶段</th>
					<th width="200" align="center">审核时间</th>
					<th width="70" align="center">审核人</th>
					<th width="123" align="center">审核状态</th>
					<th width="123" align="center">审核意见</th>
				</tr>
			</table>
		</div>
	</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
