<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="hy.ea.bo.CAccount" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>社保公积金管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../../../../page/common/base.jsp" %>
<link href="${basePath}css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="${basePath}js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/ea/human/wage/socialfund.js"></script>
<script src="${basePath}js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script>
var basePath = "${basePath}";
var pageNumber = ${pageNumber};
var cid = "";
var staffname="";
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
		            <th width="100" align="center" >日期</th>
		            <s:iterator value="#socialBase" var="base">
		            	<th width="100" align="center" >${base.value }</th>
		            </s:iterator>
		            <s:iterator value="#socialType" var="type">
		            	<th width="100" align="center" >${type.value }</th>
		            </s:iterator>
	      		</tr>
	    </thead>
		<s:if test="#fundBase.size()>0">
		  <c:set var="gz1" value="${fundBase[0] }"></c:set>
		  <c:set var="gz2" value="${fundBase[1] }"></c:set>
		  <c:set var="gz3" value="${fundBase[2] }"></c:set>
		  <c:set var="gz4" value="${fundBase[3] }"></c:set>
		  <c:set var="gz5" value="${fundBase[4] }"></c:set>
		  <c:set var="gz6" value="${fundBase[5] }"></c:set>
		</s:if>
	    <s:iterator value="pageForm.list" var="item" status="idx">
	    <c:set value="${item[4]}" var="obj1" />
	    <c:if test="${obj1 ne '' }"><c:set value="${ fn:split(obj1, ',') }" var="obj2" /></c:if>
	    	<tr id="tr${item[0] }">
	    		<td><input type="radio" value="${item[0] }" name="cid" id="cid"/></td>
	    		<td>${idx.index+1 }</td>
	    		<td>${item[1] }</td>
	    		<td><span id="staffname">${item[2] }</span></td>
	    		<td>${item[3] }</td>
	    		<td>${fn:substring(item[6],0,7 )}</td>
	    		<c:if test="${obj1 ne null }">
	    			<td><span id="sb1">${obj2[0] }</span></td>
		    		<td><span id="sb2">${obj2[1] }</span></td>
		    		<td>个人:${obj2[0]*gz1[0]/100 }</br>公司:${obj2[0]*gz1[1]/100 }</td>
		    		<td>个人:${obj2[0]*gz2[0]/100 }</br>公司:${obj2[0]*gz2[1]/100 }</td>
		    		<td>个人:${obj2[0]*gz3[0]/100+gz3[2] }</br>公司:${obj2[0]*gz3[1]/100 }</td>
		    		<td>个人:${obj2[0]*gz4[0]/100 }</br>公司:${obj2[0]*gz4[1]/100 }</td>
		    		<td>个人:${obj2[0]*gz5[0]/100 }</br>公司:${obj2[0]*gz5[1]/100 }</td>
		    		<td>个人:${obj2[1]*gz6[0]/100 }</br> 公司:${obj2[1]*gz6[1]/100 }</td>
	    		</c:if>
	    		<c:if test="${obj1 == null }">
	    			<td><span>${item[5] }</span></td>
		    		<td><span>${item[5] }</span></td>
		    		<td>个人:${item[5]*gz1[0]/100 }</br>公司:${item[5]*gz1[1]/100 }</td>
		    		<td>个人:${item[5]*gz2[0]/100 }</br>公司:${item[5]*gz2[1]/100 }</td>
		    		<td>个人:${item[5]*gz3[0]/100+gz3[2] }</br>公司:${item[5]*gz3[1]/100 }</td>
		    		<td>个人:${item[5]*gz4[0]/100 }</br>公司:${item[5]*gz4[1]/100 }</td>
		    		<td>个人:${item[5]*gz5[0]/100 }</br>公司:${item[5]*gz5[1]/100 }</td>
		    		<td>个人:${item[5]*gz6[0]/100 }</br> 公司:${item[5]*gz6[1]/100 }</td>
	    		</c:if>
	    	</tr>
	    </s:iterator>
  </table>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/socialfund/ea_findItem.jspa?staffname=${staffname }&pageNumber=${pageNumber}">
	</c:param>
</c:import>

<form name="addform" id="addform" method="post">
	<div class="jqmWindow" style="width: 465px; right: 40%; top: 15%"
		id="jqmadd">
		<div class="drag">
			规则信息
			<div class="close" onclick="closeM()">
			</div>
		</div>
		<div>
			  <table style="text-align: center;width: 100%;">
				  <tr>
					<td>项目</td>
					<td style="text-align: left">个人缴费比例</td>
					<td style="text-align: left">公司缴费比例</td>
					<td>附加缴费</td>
				  </tr>
				  <s:if test="#fundBase.size()>0">
				  	<s:iterator value="#socialType" var="type" status="idx">
	            	<tr>
						<td>${type.value }<input type="hidden" name="items[${idx.index }].paymentName" value="${type.key }"></input></td>
						<td style="text-align: left"><font color="red">*</font><input class="notnull" type="text" size="5" maxlength='5' name="items[${idx.index }].payManRate" value="${fundBase[idx.index][0] }" onkeyup='this.value=this.value.replace(/[^\-\d.]/g,"")'/>%</td>
						<td style="text-align: left"><font color="red">*</font><input class="notnull" type="text" size="5" maxlength='5' name="items[${idx.index }].payCompanyRate" value="${fundBase[idx.index][1] }" onkeyup='this.value=this.value.replace(/[^\-\d.]/g,"")'/>%</td>
						<s:if test="#idx.index==2"><td><input maxlength='5' type="text" name="items[${idx.index }].payMoreMoney" value="${fundBase[idx.index][2] }" size="3" onkeyup='this.value=this.value.replace(/[^\-\d.]/g,"")'/>&nbsp;元</td></s:if>
						<s:else><td>—&nbsp;元</td></s:else>
					 </tr>
		          	</s:iterator>
				  </s:if>
				  <s:else>
				  	<s:iterator value="#socialType" var="type" status="idx">
	            	<tr>
						<td>${type.value }<input type="hidden" name="items[${idx.index }].paymentName" value="${type.key }"></input></td>
						<td><font color="red">*</font><input class="notnull" type="text" size="5" maxlength='5' name="items[${idx.index }].payManRate" onkeyup='this.value=this.value.replace(/[^\-\d.]/g,"")'/>%</td>
						<td><font color="red">*</font><input class="notnull" type="text" size="5" maxlength='5' name="items[${idx.index }].payCompanyRate" onkeyup='this.value=this.value.replace(/[^\-\d.]/g,"")'/>%</td>
						<s:if test="#idx.index==2"><td><input maxlength='5' type="text" name="items[${idx.index }].payMoreMoney" size="3" onkeyup='this.value=this.value.replace(/[^\-\d.]/g,"")'/>&nbsp;元</td></s:if>
						<s:else><td>—&nbsp;元</td></s:else>
					 </tr>
		          </s:iterator>
				  </s:else>
				  
			  </table>
		</div>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value="&nbsp;保存 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>

<form name="baseform" id="baseform" method="post">
	<input type="hidden" id="editId" name="staffId"></input>
	<div class="jqmWindow" style="width: 365px; right: 40%; top: 15%"
		id="jqmbase">
		<div class="drag">
			<span id="showstaffname"></span>
			<div class="close" onclick="closeM()">
			</div>
		</div>
		<div>
			  <table style="width: 100%;">
				  <s:iterator value="#socialBase" var="base" status="idx">
	            	<tr id='s${idx.index }'>
						<td>${base.value }<input type="hidden" name="baseType[${idx.index }].wbName" value="${base.key }"/></td>
						<td width="43%">
						<input id="r0" type="radio" name="baseType[${idx.index }].wbType" value='1' onclick="changeBase(this)" checked="checked" />匹配工资
						<input id="r1" type="radio" name="baseType[${idx.index }].wbType" value='2' onclick="changeBase(this)"/>自定义
						</td>
						<td width="33%"><input class="notnull" id='showauto' maxlength='5' type='text' size='8' disabled="disabled" name="baseType[${idx.index }].wbMoney" onkeyup='this.value=this.value.replace(/[^\-\d.]/g,"")'/>元</td>
					 </tr>
		          </s:iterator>
			  </table>
		</div>
		<div align="center">
			<input type="button" class="input-button" onclick="saveBase()" value="&nbsp;保存 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>

<form name="searchform" id="searchform" method="post">
<s:hidden name="search" value="search"></s:hidden>
<div class="jqmWindow" style="width: 365px; right: 40%; top: 15%"
		id="jqmsearch">
		<div class="drag">
			查询信息
			<div class="close" onclick="closeM()">
			</div>
		</div>
		<div>
			<table>
				<tr>
					<td align="right" style="height: 50px; width: 70px;">
						姓名：
					</td>
					<td align="left">
						<input name="staffname" class="ckTextLength" maxlength="10"/>
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
		</div>
		<div align="center">
			<input type="button" class="input-button" onclick="searchM()" value="&nbsp;查询 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>
</body>
</html>
