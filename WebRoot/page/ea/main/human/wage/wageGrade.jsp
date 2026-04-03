<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>human-工资等级管理 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../../../../page/common/base.jsp" %>
<link href="${basePath}css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="${basePath}js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/ea/human/wage/wageGrade.js"></script>
<script type="text/javascript" >
	var basePath = '${basePath}';
	var operatorId = '';
	var pageNumber = ${pageNumber};
	var workDay=${workDay};
	var workHour=${workHour};
</script>
</head>
<body>
<div>
  <table id="wageGrade" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
			 	    <th width="25" align="center">序号</th>
		            <th width="100" align="center">工资等级</th>
		            <th width="100" align="center" >工资构成部分</th>
		            <th width="100" align="center">月工资</th>
		            <th width="100" align="center">日工资</th>
		            <th width="100" align="center">小时工资</th>
		            <th width="200" align="center">备注</th>
	      		</tr>
	    </thead>
	    <s:iterator value="pageForm.list" var="entity" status="idx">
	    	<tr>
	    		<td><input type="radio" value="${entity.wageGradeId }" name="operatorId" id="operatorId"/></td>
	    		<td>${idx.index+1 }</td>
	    		<td>${entity.wageGradeSn }</td>
	    		<td><a href="#" onclick="sdetail('${entity.wageGradeId }')">工资构成明细</a></td>
	    		<td>${entity.wageGradeMonth }</td>
	    		<td>${entity.wageGradeDay }</td>
	    		<td>${entity.wageGradeHour }</td>
	    		<td>${entity.wgRemark }</td>
	    	</tr>
	    </s:iterator>
  </table>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/wgrade/ea_findWageGrade.jspa?pageNumber=${pageNumber}">
	</c:param>
</c:import>
</div>
<form name="addform" id="addform" method="post">
<input type="submit" name="submit" style="display:none"/>
	<div class="jqmWindow" style="width: 500px; right: 40%; top: 15%;overflow-y: auto;height:400px;"
		id="jqmadd">
		<div class="drag">
			添加
			<div class="close">
			</div>
		</div>
		<table width="100%" style="text-align: center;">
			<tr>
				<td style="text-align: left">
					等级前缀：<input name="pre" maxlength="8" size="8" class="notnull"/>
					+编号：<input name="sn" class="notnull" maxlength="3" size="8" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
				</td>
				<td>
					<input type="checkbox" id="ckall" onclick="ckbulk('ckall')" value="0"/>批量创建 <input disabled type="text" name="blukNo" id="bulkNo" size="4" maxlength="2" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>级
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table id="items" width="100%" style="text-align: center;">
						<tr><td style="text-align: left;">工资构成</td><td>数额</td><td>递进规则</td></tr>
					</table>
					
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value=" 保存"/>
			<input type="button" class="input-button" onclick="closeM()" value="取消" />
		</div>
	</div>
</form>
<form name="editform" id="editform" method="post">
<input type="submit" name="submit" style="display:none"/>
	<s:hidden id="editId" name="gradeId"></s:hidden>
	<div class="jqmWindow" style="right: 40%; top: 15%"
		id="jqmedit">
		<div class="drag">
			修改
			<div class="close">
			</div>
		</div>
		<table width="100%" style="text-align: center;">
			<tr>
				<td style="text-align: left">
					等级编号：<input id="pre" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td>
					<table id="edtTable" width="100%">
						<tr><td style="text-align: right;">工资构成</td><td style="text-align: left;"></td></tr>
					</table>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="editM()" value=" 保存"/>
			<input type="button" class="input-button" onclick="closeM()" value="取消" />
		</div>
	</div>
</form>
<div class="jqmWindow" style="right: 40%; top: 15%"
		id="jqmdetail">
		<div class="drag">
			明细
			<div class="close" onclick="closeM()">
			</div>
		</div>
		<table id="detailTable" >
		</table>
	</div>
</body>
</html>
