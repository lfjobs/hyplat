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
		<title>收入预算管理</title>
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
		<script  type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/earnBudget_list.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

		<script type="text/javascript">
		    var accname="${ManStaffName}";
       		var treeID = '<%=session.getAttribute("organizationID")%>';
       		var treeNames="<%=session.getAttribute("organizationName")%>";
        	var ebbID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var status="";
        	var type="${type}";
        	var sztype="${sztype}";
        	
        	
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
	<body>
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
						部门
					</th>
					<th width="120" align="center">
						责任人
					</th>
					<th width="140" align="center">
						制单日期
					</th>
					<th width="90" align="center">
						预算年份
					</th>
					<th width="170" align="center">
						预算名称
					</th>
					<th width="180" align="center">
						往来单位
					</th>
					<th width="130" align="center">
						单位银行账号
					</th>
					<th width="90" align="center">
						单位往来关系
					</th>
					<th width="90" align="center">
						往来个人
					</th>
					<th width="130" align="center">
						个人银行账号
					</th>
					<th width="90" align="center">
						个人往来关系
					</th>
					<th width="90" align="center">
						状态
					</th>

				</tr>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list">
					<tr id="${ebbID }">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${ebbID}" />
						</td>
						<td>
							<span id="companyName">${companyName}</span>
						</td>
						<td>
							<span id="billNum">${billNum}</span>
						</td>
						<td>
							<span id="organizationName">${organizationName}</span>
						</td>
						<td>
							<span id="staffName">${staffName}</span>
						</td>
						<td>
							<span id="billsDate">${fn:substring(billsDate,0,19)}</span>
						</td>
						<td>
							<span id="year">${year}</span>
						</td>
						<td>
							<span id="budgetName">${budgetName}</span>
						</td>
						<td>
							<span id="ccompanyName">${ccompanyName}</span>
						</td>
						<td>
							<span id="accountNum">${accountNum}</span>
						</td>
						<td>
							<span id="ccompanyRelationship">${ccompanyRelationship}</span>
						</td>
						<td>
							<span id="cstaffName">${cstaffName}</span>
						</td>
						<td>
							<span id="userAccountNum">${userAccountNum}</span>
						</td>
						<td>
							<span id="cstaffRelationship">${cstaffRelationship}</span>
						</td>
						<td>
							<span id="billStatus" style="display:none;">${billStatus}</span>
							<s:if test="billStatus==01">
							   已确认预算
							</s:if>
							<s:else>草稿</s:else>
						</td>

					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/earnbudget/ea_earnBudgetList.jspa?search=${search}&pageNumber=${pageNumber}&type=${type}&sztype=${sztype}">
			</c:param>
		</c:import>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>

      	<form name="EarnBudgetBillform" id="EarnBudgetBillform">
			<input type="submit" name="submit" style="display: none" />
			<input name="earnBudgetBill.ebbID" id="ebbIDs"
				style="display: none" />
			<input name="type" value="${type}"  type="hidden"/>
			<s:token />
		</form>
		
		
	<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%" id="jqModelSearch">
		<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<input name="type" style="display: none" value="${type}"/>
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396px"  >
					<tr>
						<td align="right" width="80px">
							黏贴单编号：
						</td>
						<td>
							<input id="billNum" style="width: 195px"
								name="earnBudgetBill.billNum" />
						</td>
					</tr>
					<tr>
						<td align="right" width="80px">
							制单人：
						</td>
						<td>
							<input id="billStaffName" style="width: 195px"
								name="earnBudgetBill.billStaffName" />
						</td>
					</tr>
					<tr>
						<td align="right" width="80px">
							责任人：
						</td>
						<td>
						<s:select list="%{#request.staffList}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="staffID"
								name="earnBudgetBill.staffID" listValue="staffName"
								theme="simple">
							</s:select>
						</td>
					</tr>
					
					<tr>
						<td align="right" width="80px">
							制单日期：
						</td>
						<td style="width: 200px">
							<input type="text"  id="sdate"
								class="input" name="earnBudgetBill.sdate" style="width:85px;"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', onpicked:function(){sdate.focus();}})"
								readonly /> 至<input type="text" name="earnBudgetBill.edate" id="edate" style="width:85px;"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'sdate\')}'})"
								readonly />
						</td>
					</tr>
					
					<tr>
						<td align="right" width="80px">
							单据状态：
						</td>
						<td>
							<select id="taxstatus" style="width:200px"  name="earnBudgetBill.billStatus">
								<option value="" selected="selected" >全部</option>
					    		<option value="00">草稿</option>
					    		<option value="10">已确认</option>
					    	</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 "/>
					<input name="search" type="hidden" value="search" />
					<input name="sztype" type="hidden" value="${sztype}" />
				</div>
				</form>
			</div>
			
			
	</body>
</html>
