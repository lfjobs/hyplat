<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="hy.ea.bo.CAccount" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	CAccount user =(CAccount)session.getAttribute("account");
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>月考评管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../../../../page/common/base.jsp" %>
<link href="${basePath}css/ea/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}js/ea/human/wage/wageScore.js"></script>
<script src="${basePath}js/ea/validate.js" type="text/javascript"></script>
<script  src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
<style>

.fl{ display: inline; float: left; } 
.fr{ display: inline;  float: right; }
.clear {width:100%; diplay: block!important; float: none!important; clear: both; overflow: hidden; width: auto!important; height: 0!important; margin: 0 auto!important; padding: 0!important; font-size: 0; line-height: 0; }
.tab1{ width:100%;border-bottom:1px solid #000;}
.jiben{width:20%; text-align:left; line-height:auto;height:100%;}
.tab1 label{width:135px; display:block; float:left; text-align:left;}
.nav{ height:70%; line-height:25px;}
.option{ width:100%;}
</style>
<script>
var basePath = "${basePath}";
var pageNumber = ${pageNumber};
var cid = "";//staffid
var staffname="<%=user.getStaffName()%>";
var searchdate='${searchdate }';
</script>
</head>
<body>
	<table id="item" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
			 	    <th width="25" align="center">序号</th>
		            <th width="100" align="center">人员编号</th>
		            <th width="100" align="center" >人员姓名</th>
		            <th width="100" align="center" >岗位名称</th>
		            <th width="100" align="center" >月份</th>
		            <s:iterator value="#wageEvalList" var="eval">
		            	<th width="100" align="center" >${eval.wageEvalName }</th>
		            </s:iterator>
	      		</tr>
	    </thead>
	    <s:iterator value="pageForm.list" var="item" status="idx">
	    	<tr id="${item[0] }">
	    		<td><input type="radio" value="${item[0] }" name="cid" id="cid"/></td>
	    		<td>${idx.index+1 }</td>
	    		<td>${item[1] }</td>
	    		<td id="cname">${item[2] }</td>
	    		<td>${item[3] }</td>
	    		<td id="date">${fn:substring(item[4],0,7) }</td>
	    		<s:if test="#wageEvalList.size() > 0">
	    			<s:iterator value="#wageEvalList" status="sdx">
		            	<td>${item[sdx.index+5] }</td>
		            </s:iterator>
	    		</s:if>
	    	</tr>
	    </s:iterator>
  </table>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/score/ea_findItem.jspa?pageNumber=${pageNumber}&staffname=${staffname }&searchdate=${searchdate }">
	</c:param>
</c:import>

<form name="addform" id="addform" method="post">
	<input type="hidden" id="editId" name="staffId"></input>
	<input type="hidden" name="pdate" id="pdate"/>
	<input type="hidden" name="searchdate" id="searchdate"/>
	<div class="jqmWindow" style="width: 465px; right: 40%; top: 3%" id="jqmadd">
		<div class="drag">
			考评信息
			<div class="close">
			</div>
		</div>
		<a href="#" onclick="viewMonthTask()"><font color="red">参考月度任务</font></a>
		考评日期：<span id="showDate"></span>&nbsp;&nbsp;&nbsp;&nbsp;考评人:<span id="parmName"></span>
		<div id="evalcontent">
		</div>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value="&nbsp;保存 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>
<form name="searchform" id="searchform" method="post">
<s:hidden name="search" value="search"></s:hidden>
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
</body>
</html>
