<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>human-工资项管理</title>
<%@include file="../../../../../page/common/base.jsp" %>
<link href="${basePath}css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="${basePath}js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/ea/human/wage/wageItem.js"></script>
<script type="text/javascript" >
	var basePath = '${basePath}';
	var cid = '';
	var pageNumber = ${pageNumber};
</script>
</head>
<body>
<form  name="witemform" id="witemform" method="post">
<input type="submit" name="submit" style="display:none"/>
<div>
  <table id="wageItem" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
			 	    <th width="25" align="center">序号</th>
		            <th width="200" align="center">工资构成</th>
		            <th width="200" align="center">统计分类</th>
		            <th width="250" align="center" >备注</th>
	      		</tr>
	    </thead>
	    <s:iterator value="pageForm.list" var="item">
	    	<tr>
	    		<td><input type="radio" value="${item[0] }" name="cid" id="cid"/></td>
	    		<td>${item[1] }</td>
	    		<td>${item[2] }</td>
	    		<td>${item[3] }</td>
	    		<td>${item[4] }</td>
	    	</tr>
	    </s:iterator>
  </table>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/witem/ea_findWageItem.jspa?pageNumber=${pageNumber}">
	</c:param>
</c:import>
</div>
</form>

<form name="addform" id="addform" method="post">
	<input type="hidden" id="opeType" name="opeType"></input>
	<input type="hidden" id="editId" name="wageItemId"></input>
	<div class="jqmWindow" style="width: 365px; right: 40%; top: 15%"
		id="jqmadd">
		<div class="drag">
			工资构成信息
			<div class="close" onclick="closeM()">
			</div>
		</div>
		<table>
			<tr>
				<td align="right" width="60">
					序号：
				</td>
				<td>
					<input type="text" class="put3" name="wageItemSn" id="wageItemSn" maxlength="10" size="25" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onblur="this.value=this.value.replace(/[^\d]/g,'')"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="60">
					工资构成：
				</td>
				<td>
					<input type="text" class="put3" name="wageItemName" id="wageItemName" maxlength="10" size="25"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="60">
					统计类型：
				</td>
				<td>
					<select name="wageEvalState" id="wageEvalState" onchange="javascript:changeEval(this.value)">
					<option value="03">仅作统计</option>
					<option value="00">考评项</option>
					<option value="01">考评分类</option>
					<option value="02">考评总分</option>
					</select>
					<select name="wageFkEval" id="evalType" style="display:none" disabled="disabled">
					<c:forEach var="entity" items="${codeList }">
						<option value="${entity.codeID }">${entity.codeValue }</option>
					</c:forEach>
					</select>
					<select name="wageFkEval" id="evalItem" style="display:none" disabled="disabled">
					<c:forEach var="entity" items="${evalList }">
						<option value="${entity.wageEvalId }">${entity.wageEvalName }</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" width="60">
					备注：
				</td>
				<td>
					<input type="text" name="wiRemark" id="wiRemark" maxlength="25" size="25"/>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value=" 保存"/>
			<input type="button" class="input-button" onclick="closeM()" value="取消" />
		</div>
	</div>
</form>
</body>
</html>
