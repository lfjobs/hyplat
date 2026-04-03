<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工资核算</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../../../../page/common/base.jsp" %>
<script  src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
<script src="${basePath}js/ea/human/wage/payroll.js" type="text/javascript"></script>
<script>
var basePath = "${basePath}";
var pageNumber = ${pageNumber};
</script>
</head>
<body>
	<table id="item" >
	  	<thead>
		 	    <tr>
		            <th width="80" align="center">员工编号</th>
		            <th width="50" align="center" >姓名</th>
		            <th width="100" align="center">岗位名称</th>
		            <th width="50" align="center">日期</th>
		            <th width="100" align="center">应得工资</th>
		            <th width="100" align="center">加班工资</th>
		            <th width="100" align="center">请假扣除工资</th>
		            <th width="100" align="center">考勤奖励工资(次)</th>
		            <th width="100" align="center">考勤扣除工资(次)</th>
		            <th width="100" align="center">工资项构成</th>
		            <th width="100" align="center">实得工资</th>
	      		</tr>
	    </thead>
	    <s:iterator value="pageForm.list" var="item" status="idx">
	    	<tr id='${item.resultId }'>
	    		<td>${item.staffCode }</td>
	    		<td>${item.staffName }</td>
	    		<td>${item.deptName }</td>
	    		<td>${fn:substring(item.moneyDate,0,7) }</td>
	    		<td>${item.deserveMoney }</td>
	    		<td>${item.addTimeMoney }</td>
	    		<td>${item.miTimeMoney }</td>
	    		<td>${item.addCMoney }</td>
	    		<td>${item.miCMoney }</td>
	    		<td><a href="#" onclick="findMX('${item.resultId}')" style="color:red">${item.itemMoney }</a></td>
	    		<td>${item.realMoney }</td>
	    	</tr>
	    </s:iterator>
  </table>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/payroll/ea_findItem.jspa?pageNumber=${pageNumber}">
	</c:param>
</c:import>
<form name="searchform" id="searchform" method="post">
<div class="jqmWindow" style="width: 265px; right: 40%; top: 15%"
		id="jqmsearch">
		<div class="drag">
			查询信息
			<div class="close">
			</div>
		</div>
		<div align="center">
			<table>
				<tr><td>姓名:</td><td><input type="text" name="staffname"/></td></tr>
				<tr><td>时间:</td><td><input type="text" name="searchdate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" readonly="readonly"/></td></tr>
			</table>
		</div>
		<div align="center">
			<input type="button" class="input-button" onclick="searchM()" value="&nbsp;查询 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>
<form name="hesuanform" id="hesuanform" method="post">
<input type="hidden" id="sdate" name="searchdate"/>
<div class="jqmWindow" style="width: 265px; right: 40%; top: 15%"
		id="jqmhesuan">
		<div class="drag">
			核算工资
			<div class="close">
			</div>
		</div>
		<div align="center">
			<table>
				<tr><td>核算月:</td><td><input type="text" name="pdate" id="pdate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" readonly="readonly"/></td></tr>
			</table>
		</div>
		<div align="center">
			<input type="button" class="input-button" onclick="hesuanM()" value="&nbsp;核算 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>
<div class="jqmWindow" style="width: 265px; right: 40%; top: 15%"
		id="jqmdetail">
		<div class="drag">
			工资构成项明细
			<div class="close">
			</div>
		</div>
		<div id="contents">
		</div>
	</div>

</body>
</html>
