<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>个税管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../../../../page/common/base.jsp" %>
<script src="${basePath}js/ea/human/wage/wageTax.js" type="text/javascript"></script>
<script src="${basePath}js/ea/validate.js" type="text/javascript"></script>
<script src="${basePath}js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script>
var basePath = "${basePath}";
var pageNumber = ${pageNumber};
var itemSn=${itemSize};
var search = '${search}';
</script>
</head>
<body>
	<table id="item" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">序号</th>
		            <th width="100" align="center">员工编号</th>
		            <th width="100" align="center" >姓名</th>
		            <th width="100" align="center">部门</th>
		            <th width="100" align="center">时间</th>
		            <th width="100" align="center">税前工资</th>
		            <th width="100" align="center">应缴税金</th>
	      		</tr>
	    </thead>
	    <s:iterator value="wrvo" >
	    	<tr id='${resultId }'>
	    		<td><input type="radio" value="${resultId }" name="cid" id="cid"/></td>
	    		<td>${staffCode }</td>
	    		<td>${staffName }</td>
	    		<td>${deptName }</td>
	    		<td>${fn:substring(moneyDate,0,7 )}</td>
	    		<td>${realMoney }</td>
	    		<td>${taxMoney }</td>
	    		<input type="hidden" id="datastaffid" value=""></input>
	    	</tr>
	    </s:iterator>
  </table>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/tax/ea_findItem.jspa?pageNumber=${pageNumber}&search=${search }">
	</c:param>
</c:import>

<form name="addform" id="addform" method="post">
	<input type="hidden" id="showstaffid" name="staffId"></input>
	
	<div class="jqmWindow" style="width: 365px; right: 40%; top: 15%"
		id="jqmadd">
		<div id="metitle" class="drag">
			基本信息
			<div class="close">
			</div>
		</div>
		<table id="edtTable" style="width:100%;text-align: center;">
			<tr>
				<td>
					级数
				</td>
				<td colspan="2">
					含税级距(元)
				</td>
				<td >
					税率(%)
				</td>
				<td style="text-align: right">
				<input type="button" value="+" onclick="addItem('edtTable')"/>
				</td>
			</tr>
			<s:if test="#taxList.size()>0">
				<s:iterator value="#taxList" var="tax" status="idx">
					<tr>
						<td><span id="sn">${tax.wageTaxLevel }</span></td>
						<td colspan="2">&gt;<input type="text" size="8" maxlength="8" name="titem[${idx.index }].wageTaxLow" 
						 value="${tax.wageTaxLow }" <s:if test="#idx.index==0">readonly="readonly"</s:if>
						 />&lt;=<input type="text" size="8" maxlength="8" name="titem[${idx.index }].wageTaxHigh" value="${tax.wageTaxHigh }" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onblur="this.value=this.value.replace(/[^\d]/g,'')"/></td>
						<td ><input type="text" size="5" maxlength="5" name="titem[${idx.index }].wageTaxRate" value="${tax.wageTaxRate }" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onblur="this.value=this.value.replace(/[^\d]/g,'')"/></td>
						<td style="text-align: right"><s:if test="#idx.index > 1"><input type="button" value="-" onclick="javascript:delItem(this)"/></s:if></td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td><span id="sn">1</span></td>
					<td colspan="2">
					&gt;<input type="text" size="8" maxlength="8" name="titem[0].wageTaxLow" value="0" readonly="readonly"/>
					&lt;=<input type="text" size="8" name="titem[0].wageTaxHigh" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onblur="this.value=this.value.replace(/[^\d]/g,'')"/></td>
					<td ><input type="text" size="5" maxlength="5" name="titem[0].wageTaxRate" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onblur="this.value=this.value.replace(/[^\d]/g,'')"/></td>
					<td style="text-align: right"></td>
				</tr>
				<tr>
					<td><span id="sn">2</span></td>
					<td colspan="2">&gt;<input type="text" size="8" name="titem[1].wageTaxLow" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onblur="this.value=this.value.replace(/[^\d]/g,'')"/>&lt;=<input type="text" size="8" name="titem[1].wageTaxHigh" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onblur="this.value=this.value.replace(/[^\d]/g,'')"/></td>
					<td ><input type="text" size="5" maxlength="5" name="titem[1].wageTaxRate" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onblur="this.value=this.value.replace(/[^\d]/g,'')"/></td>
					<td style="text-align: right"></td>
				</tr>
			</s:else>
			
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value="&nbsp;保存 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>
<form name="searchform" id="searchform" method="post">
	<div class="jqmWindow" style="width: 365px; right: 40%; top: 15%"
		id="jqmsearch">
		<div id="metitle" class="drag">
			查询信息
			<div class="close">
			</div>
		</div>
		<table id="searchTable" style="width:100%;text-align: center;">
			<tr>
				<td align="right" style="height: 50px; width: 70px;">
					姓名：
				</td>
				<td align="left">
					<input name="wageResult.staffName" class="ckTextLength" maxlength="10"/>
				</td>
			</tr>
			<tr>
				<td align="right" style="height: 50px;">
					月份：
				</td>
				<td align="left">
					<input name="moneyDate" id="moneyDate" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="searchM()" value="&nbsp;查询 &nbsp;"/>
			<input type="hidden" name="search" value="search"/>
		</div>
	</div>
</body>
</html>
