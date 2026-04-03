<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>考评分类设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../../../../page/common/base.jsp" %>
<link href="${basePath}css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="${basePath}js/ea/validate.js" type="text/javascript"></script>
<script src="${basePath}js/common/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/ea/human/wage/wageConfig.js"></script>
<script>
var basePath = "${basePath}";
var pageNumber = ${pageNumber};
var cid = "";
</script>
</head>
<body>
	<table id="item" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
			 	    <th width="25" align="center">序号</th>
		            <th width="200" align="center">考评分类</th>
		            <th width="250" align="center" >备注</th>
	      		</tr>
	    </thead>
	    <s:iterator value="pageForm.list" var="item">
	    	<tr>
	    		<td><input type="radio" value="${item.codeID }" name="cid" id="cid"/></td>
	    		<td>${item.codeNumber }</td>
	    		<td>${item.codeValue }</td>
	    		<td>${item.codeDesc }</td>
	    	</tr>
	    </s:iterator>
  </table>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/assort/ea_findItem.jspa?pageNumber=${pageNumber}">
	</c:param>
</c:import>

<form name="addform" id="addform" method="post">
	<input type="hidden" id="opeType" name="opeType"></input>
	<input type="hidden" id="editId" name="codeID"></input>
	<div class="jqmWindow" style="width: 365px; top: 15%;left: 15%; " id="jqmadd">
	
		<div id="metitle" class="drag">
			分类信息
			<div class="omax" onclick="oMax(jqmadd)"></div>
			<div class="omin" onclick="oRevert(jqmadd,'15%','15%','367px','170px')" style="display:none"></div>
			<div class="close"></div>
		</div>
		<table>
			<tr>
				<td align="right" width="60">
					序号：
				</td>
				<td>
					<input type="text" class="put3" name="codeNumber" id="cnumber" maxlength="10" size="25" onkeyup='this.value=this.value.replace(/[^\d]/g,"")'/>
				</td>
			</tr>
			<tr>
				<td align="right" width="60">
					考评分类：
				</td>
				<td>
					<input type="text" class="put3" name="codeValue" id="cname" maxlength="10" size="25"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="60">
					备注：
				</td>
				<td>
					<input type="text" name="codeDesc" id="cdesc" maxlength="25" size="25"/>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value="&nbsp;保存 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>
</body>
</html>
