<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工资关联管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../../../../page/common/base.jsp" %>
<script src="${basePath}js/ea/human/wage/wageStaff.js" type="text/javascript"></script>
<script>
var basePath = "${basePath}";
var pageNumber = ${pageNumber};
var cid = "";
var workDay=${workDay};
var workHour=${workHour};
</script>
</head>
<body>
	<table id="item" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
			 	    <th width="25" align="center">序号</th>
		            <th width="100" align="center">员工编号</th>
		            <th width="100" align="center" >姓名</th>
		            <th width="100" align="center">部门</th>
		            <th width="100" align="center">工资等级</th>
		            <th width="100" align="center">工资构成</th>
		            <th width="100" align="center">月工资</th>
		            <th width="100" align="center">日工资</th>
		            <th width="100" align="center">小时工资</th>
	      		</tr>
	    </thead>
	    <s:iterator value="pageForm.list" var="item" status="idx">
	    	<tr id='${item[7] }'>
	    		<td><input type="radio" value="${item[7] }" name="cid" id="cid"/></td>
	    		<td>${idx.index+1 }</td>
	    		<td>${item[0] }</td>
	    		<td id="datastaffname">${item[1] }</td>
	    		<td>${item[2] }</td>
	    		<td id="gradesn">${item[3] }</td>
	    		<td><a href="#" onclick="javascript:gzgc('${item[10] }','${item[11] }','${item[7] }')">工资构成明细</a></td>
	    		<td id="mmoney">${item[4] }</td>
	    		<td id="dmoney">${item[5] }</td>
	    		<td id="hmoney">${item[6] }</td>
	    		<input type="hidden" id="datastaffid" value="${item[8] }"></input>
	    		<input type="hidden" id="xymoney" value="${item[9] }" ></input>
	    	</tr>
	    </s:iterator>
  </table>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/wagestaff/ea_findItem.jspa?pageNumber=${pageNumber}">
	</c:param>
</c:import>

<form name="addform" id="addform" method="post">
	<input type="hidden" id="editId" name="wageStaffId"></input>
	<input type="hidden" id="showstaffid" name="staffId"></input>
	
	<div class="jqmWindow" style="width: 465px; right: 40%; top: 15%"
		id="jqmadd">
		<div id="metitle" class="drag">
			基本信息
			<div class="close">
			</div>
		</div>
		<table id="edtTable" style="width:100%">
			<tr>
				<td style="text-align: right">
					姓名:
				</td>
				<td style="text-align: left">
					<span id="showstaffname"></input>
				</td>
				<td style="text-align: right">
					工资等级:
				</td>
				<td style="text-align: left">
					<select id="changeGrade" name="wageGradeId">
						<option value="-1">请选择</option>
						<c:forEach items="${gradeList }" var="grade">
							<option value="${grade.wageGradeId }">${grade.wageGradeSn }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value="&nbsp;保存 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
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
