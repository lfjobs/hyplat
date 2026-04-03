<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>潜在客户管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/potentialCustomer.js"></script>
<script type="text/javascript">
	var basePath='<%=basePath%>';           
    var pNumber ='${pageNumber}';  
    var goodsid = "${goodsid}";
    var status1 = "${status1}";
    var search = " ${search}";
    var frameid = '<%=request.getParameter("frameid")%>';
	var mainheight = 0; //框架高度
 </script>
</head>
<body>
	
		<form name="lForm" id="lForm" method="post">
			<div id="main_main" class="main_main" >
			<table class="staffappraisal">
				<thead>
					<tr>
						<th width="100" align="center">
							人员编号
						</th>
						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<th width="200" align="center">
							最常用联系方式
						</th>
						<th width="200" align="center">
							护照号
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list">
						<tr id="${staffID}" class="trclass">
							<td>
								<span id="staffCode">${staffCode}</span>
							</td>
							<td>
								<span id="recordCode">${recordCode}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="usedNmae">${usedNmae}</span>
							</td>
							<td>
								<span id="sex">${sex}</span>
							</td>
							<td>
								<span id="birthday">${birthday}</span>
							</td>
							<td>
								<span id="nativePlace">${nativePlace}</span>
							</td>
							<td>
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffIdentityCard}</span>
							</td>
							<td>
								<span id="reference">${reference}</span>
							</td>
							<td>
								<span id="passportNum">${passportNum}</span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
				<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/customermanage/ea_getPotentialList.jspa?frameid=${param.frameid}&pageNumber=${pageNumber}&search=${search}&status1=${status1}&goodsid=${goodsid}"></c:param>
			</c:import>
			<s:token></s:token>
			</div>
		</form>
	
	<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="280" id="SearchTable">
					<tr>
						<td width="35%" align="right">
							人员姓名：
						</td>
						<td style="width: 200px">
							<input name="staff.staffName"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							身份证：
						</td>
						<td style="width: 200px">
							<input name="staff.staffIdentityCard" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		
		<script type="text/javascript">
    	$(function(){   
    		setTimeout(function(){ 			
    	  	    $("div.bDiv").css({"height":parent.document.getElementById(frameid).offsetHeight-97+"px"});
    	    },100);
    		 $(window).resize(function(){ 
   			      setTimeout(function(){ 			
   			    	  $("div.bDiv").css({"height":parent.document.getElementById(frameid).offsetHeight-97+"px"});
   			      },100);
    		 }); 
		});
		</script> 
	</body>
</html>