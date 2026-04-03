<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <title>凭证类别</title>
    	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" 
			type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" 
			type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />		
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		
		<style type="text/css">
			.subspan{
				display:-moz-inline-box;
				width:150px;
				height:200px; 
			}
			.ftitle{
			 	height: 23px;}
			.spans{
			text-align:left;display:block;}
		</style>
			
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			var select=1;	
			var Id="";
			var PId="";
			var status=""
		</script>
		<script src="<%=basePath%>js/ea/finance/invoicing/voucher/category_list.js" 
			type="text/javascript" ></script>
  </head>
  
  <body>
  <div  id="main_main" class="main_main">
 	<form method="post" action="" id="form" name="form">
 	<div id="fexdiv">
  	<table class="fexlist">
  	  <thead>
  	  	<tr>
	  	  <th width="35" align="center">选择</th>
	  	  <th width="129" align="center">凭证类别代号</th>
	  	  <th width="129" align="center">凭证类别简称</th>
	  	  <th width="129" align="center">凭证类别名称</th>
	  	  <th width="155" align="center">借方必有科目</th>
	  	  <th width="155" align="center">贷方必有科目</th>
	  	  <th width="76" align="center">预设值</th>
	  	  <th width="129" align="center">备注说明</th>
  		</tr>
  	  </thead>
  	  <tbody id="tbwid">
  	   <c:forEach items="${pageForm.list}" var="list" varStatus="a">
  	   
  	  <tr id="${list.VTId}">
  	  	  <td><input type="radio" id="${list.VTId}" class="check" name="check" >
  	  	  	  <input type="hidden" name="VTId" value="${list.VTId}"></td>
  	  	  <td><span class="none spans">${list.VTDh}</span>
  	  	  	  <input type="text" value="${list.VTDh}" name="VTDh" class="model1"
  	  	  		readonly="readonly" style="border: 0px;width: 100%" /></td>
  	  	  <td><span class="none spans">${list.VTJc}</span>
  	  	  	  <input type="text" value="${list.VTJc}" name="VTJc" class="model1"
  	  	  		readonly="readonly" style="border: 0px;width: 100%"/></td>
  	  	  <td><span class="none spans">${list.VTName}</span>
  	  	  	  <input type="text" value="${list.VTName}" name="VTName" class="model1"
  	  	 		readonly="readonly" style="border: 0px;width: 100%"/></td>
  	  	  <td><input type="text" value="${list.VTDs}" name="VTDs" id="a${a.index}" class="subjecks a${a.index}"
  	  	  		readonly="readonly" style="border: 0px;width: 100%"/></td>
  	  	  <td><input type="text" value="${list.VTCs}" name="VTCs" id="b${a.index}" class="subjecks b${a.index}"
  	  		  	readonly="readonly" style="border: 0px;width: 100%"/></td>
  	  	  <td><span class="spans">${list.VTPd}<span></td>
  	  	  <td><span class="none spans">${list.VTR}</span>
  	  	  	  <input type="text" value="${list.VTR}" name="VTR" class="model1"
  	  	  		readonly="readonly" style="border: 0px;width: 100%"/></td>
  	  	</tr> 	  	
  	   </c:forEach>
  	   <s:token></s:token>
  	   <tr id="tr" style="display: none">
  	  		<td><input type="radio" class="invc" name="check"></td>
  	  		<td><input type='text' name="VTDh" style="border: 0px;width: 100%"/></td>
  	  		<td><input type='text' name="VTJc" style="border: 0px;width: 100%"/></td>
  	  		<td><input type='text' name="VTName" style="border: 0px;width: 100%"/></td>
  	  		<td><input type='text' name="VTDs" style="border: 0px;width: 100%" readonly="readonly"
  	  			 	id="vtds" class="subjecks vtds"/></td>
  	  		<td><input type='text' name="VTCs" style="border: 0px;width: 100%" readonly="readonly"
  	  				 id="vtcs" class="subjecks vtcs"/></td>
  	  		<td><select class="sel" name="VTPd">
  	  		<option value="N">N</option><option value="Y">Y</option>
  	  		</select></td>
  	  		<td><input type='text' id="vtr" name="VTR" style="border: 0px;width: 100%"/></td>
  	  	</tr>
  	   </tbody>
  	</table>
  	</div>
  	<input type="submit" style="display: none" id="submit" name="submit">
  	</form>
  </div>
  <!--科目管理-->
  <div id="subject" class="jqmWindow jqmWindowcss2" 
  	 style="width: 600px; top:10%; height: 400px;">
  <table height="100" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td width="45%" align="left" valign="top">
					<div class="text_tree" style="overflow: auto;height: 350px; width: 300px;">
						<div id="aadTree" style="z-index: 99; width: 279px;"></div>
					</div>
					</td>
					<td width="11%" align="center">
						<table>
						<tr><td><div class="right_dan" id="rightdan"></div></td></tr>
						<tr><td><div class="left_dan" id="leftdan"></div></td></tr>
						<tr><td><div class="left_suang" id="leftsuang"></div></td></tr>
						</table>
					</td>
					<td width="44%" align="left" valign="top">
						<div id="text_tree" class="text_tree"
							style="overflow: auto; z-index: 99; width: 300px;height: 350px;"></div>
					</td>
				</tr>
				<tr>	
					<td align="center" colspan="3"><input class="button jecks" type="button" value="确定">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="button jecks" type="button" value="取消"></td>
				</tr>
	</table>
	
  </div>
  <c:import url="../../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/category/ea_getVoucherCategory.jspa?pageNumber=${pageNumber}&search=${search}">
		</c:param>
	</c:import>
	<iframe name="hidden"border="0"  height="0" frameborder="0"></iframe>
	
  </body>
</html>
