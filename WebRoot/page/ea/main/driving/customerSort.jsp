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
<title>客户分类管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/customerSort.js"></script>
<script type="text/javascript">
	var basePath='<%=basePath%>';           
	var pNumber ='${pageNumber}';  
	var goodsid = "${goodsid}";
	var status1 = "${status1}";
	var search = "${search}";
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
						<s:if test="status1 == '03'">
							产品兴趣分类
						</s:if>
						<s:else>
							<s:if test="status1 == '02'">客户来源分类</s:if>
							<s:else>客户分类</s:else>
						</s:else>
					</th>
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
				<c:forEach items="${pageForm.list}" var="arr">
					<tr class="trclass">
						<td>
							<s:if test="status1 != '03'">
								<span id="codeName">${arr[0]}</span>
							</s:if>
							<s:else>
								<span id="interest">
									<c:if test="${arr[1] == '00'}">有兴趣</c:if>
									<c:if test="${arr[1] == '01'}">特别有兴趣</c:if>
									<c:if test="${arr[1] == '02'}">一般有兴趣</c:if>
								</span>
							</s:else>
						</td>
						<td>
							<span id="staffCode">${arr[2]}</span>
						</td>
						<td>
							<span id="recordCode">${arr[3]}</span>
						</td>
						<td>
							<span id="staffName">${arr[4]}</span>
						</td>
						<td>
							<span id="usedNmae">${arr[5]}</span>
						</td>
						<td>
							<span id="sex">${arr[6]}</span>
						</td>
						<td>
							<span id="birthday">${arr[7]}</span>
						</td>
						<td>
							<span id="nativePlace">${arr[8]}</span>
						</td>
						<td>
							<span id="nationality">${arr[9]}</span>
						</td>
						<td>
							<span id="nation">${arr[10]}</span>
						</td>
						<td>
							<span id="staffIdentityCard">${arr[11]}</span>
						</td>
						<td>
							<span id="reference">${arr[12]}</span>
						</td>
						<td>
							<span id="passportNum">${arr[13]}</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			<c:import url="../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/customermanage/ea_getSortList.jspa?frameid=${param.frameid}&pageNumber=${pageNumber}&search=${search}&status1=${status1}&goodsid=${goodsid}"></c:param>
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
					<s:if test="status1 != '03'">
						<td width="35%" align="right">
							<s:if test="status1 == '02'">客户来源分类</s:if>
							<s:else>客户分类</s:else>
						</td>
						<td>
							<s:select list="customersList" listKey="codeID" listValue="codeValue" 
								headerKey="" headerValue="请选择" name="staff.staffDesc" theme="simple">
							</s:select>
						</td>
					</s:if>
					<s:else>
						<td width="40%" align="right">
							产品兴趣分类：
						</td>
						<td>
							<select name="staff.staffDesc">
								<option value="">请选择</option>
								<option value="00">有兴趣</option>
								<option value="01">特别有兴趣</option>
								<option value="02">一般有兴趣</option>
							</select>
						</td>
					</s:else>
				</tr>
				<tr>
					<td align="right">
						人员姓名：
					</td>
					<td>
						<input name="staff.staffName"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						身份证：
					</td>
					<td>
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