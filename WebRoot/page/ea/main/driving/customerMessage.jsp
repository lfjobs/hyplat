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
<title>客户信息管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/customerMessage.js"></script>
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
					<th width="100" align="center">人员姓名</th>
					<th width="100" align="center">
						<s:if test="status1 == 'con'">联系号码</s:if>
						<s:elseif test="status1 == 'com'">单位名称</s:elseif>
						<s:elseif test="status1 == 'tra'">录入时间</s:elseif>
						<s:elseif test="status1 == 'tel'">访问电话</s:elseif>
						<s:else>有效起时间</s:else>
					</th>
					<th width="100" align="center">
						<s:if test="status1 == 'con'">联系姓名</s:if>
						<s:elseif test="status1 == 'com'">单位地址</s:elseif>
						<s:elseif test="status1 == 'tra'">咨询起日期</s:elseif>
						<s:elseif test="status1 == 'tel'">客户名</s:elseif>
						<s:else>有效止时间</s:else>
					</th>
					<th width="100" align="center">
						<s:if test="status1 == 'con'">登记日期</s:if>
						<s:elseif test="status1 == 'com'">单位电话</s:elseif>
						<s:elseif test="status1 == 'tra'">咨询止日期</s:elseif>
						<s:elseif test="status1 == 'tel'">通话内容</s:elseif>
						<s:else>证件名称</s:else>
					</th>
					<th width="100" align="center">
						<s:if test="status1 == 'con'">联系类型</s:if>
						<s:elseif test="status1 == 'com'">单位负责人</s:elseif>
						<s:elseif test="status1 == 'tra'">工作地点</s:elseif>
						<s:elseif test="status1 == 'tel'">是否处理</s:elseif>
						<s:else>证件类型</s:else>
					</th>
					<th width="100" align="center">
						<s:if test="status1 == 'con'">审核人</s:if>
						<s:elseif test="status1 == 'com'">单位负责人电话</s:elseif>
						<s:elseif test="status1 == 'tra'">服务方式</s:elseif>
						<s:elseif test="status1 == 'tel'">处理人所在公司</s:elseif>
						<s:else>证件编号</s:else>
					</th>
					<th width="100" align="center">
						<s:if test="status1 == 'con'">审核人编号</s:if>
						<s:elseif test="status1 == 'com'">行业类别</s:elseif>
						<s:elseif test="status1 == 'tra'">咨询跟踪内容</s:elseif>
						<s:elseif test="status1 == 'tel'">处理人</s:elseif>
						<s:else>档案编号</s:else>
					</th>
					<th width="100" align="center">
						<s:if test="status1 == 'con'">审核时间</s:if>
						<s:elseif test="status1 == 'com'">单位备注</s:elseif>
						<s:elseif test="status1 == 'tra'">跟踪原因</s:elseif>
						<s:elseif test="status1 == 'tel'">处理时间</s:elseif>
						<s:else>住址</s:else>
					</th>
					<th width="100" align="center">
						<s:if test="status1 == 'con'">备注</s:if>
						<s:elseif test="status1 == 'com'">单位状态</s:elseif>
						<s:elseif test="status1 == 'tra'">处理状态</s:elseif>
						<s:elseif test="status1 == 'tel'">处理意见</s:elseif>
						<s:else>发证机关(单位)</s:else>
					</th>
					
					<s:if test="status1 == 'cer'">
						<th width="100" align="center">证件资料文号</th>
						<th width="100" align="center">附件编号</th>
						<th width="100" align="center">审核人</th>
						<th width="100" align="center">审核人员编号</th>
						<th width="100" align="center">审核时间</th>
						<th width="100" align="center">备注</th>
						<th width="100" align="center">附件</th>
					</s:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageForm.list}" var="arr">
					<tr class="trclass">
						<td>
							<span>${arr[0]}</span>
						</td>
						<td>
							<span>${arr[1]}</span>
						</td>
						<td>
							<span>${arr[2]}</span>
						</td>
						<td>
							<span>${arr[3]}</span>
						</td>
						<td>
							<span>${arr[4]}</span>
						</td>
						<td>
							<span>${arr[5]}</span>
						</td>
						<td>
							<span>${arr[6]}</span>
						</td>
						<td>
							<span>${arr[7]}</span>
						</td>
						<td>
							<span>${arr[8]}</span>
						</td>
						
						<s:if test="status1 == 'cer'">
							<td>
								<span>${arr[9]}</span>
							</td>
							<td>
								<span>${arr[10]}</span>
							</td>
							<td>
								<span>${arr[11]}</span>
							</td>
							<td>
								<span>${arr[12]}</span>
							</td>
							<td>
								<span>${arr[13]}</span>
							</td>
							<td>
								<span>${arr[14]}</span>
							</td>
							<td>
								<c:if test="${arr[15] == null}">无</c:if>
								<c:if test="${arr[15] != null}">
									<span onclick="lookImage('${photo}');"><a href="#">查看</a></span>
								</c:if>
							</td>
						</s:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			<c:import url="../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/customermanage/ea_getMessageList.jspa?frameid=${param.frameid}&pageNumber=${pageNumber}&search=${search}&status1=${status1}&goodsid=${goodsid}"></c:param>
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
					<td align="right">
						人员姓名：
					</td>
					<td>
						<input name="staff.staffName"/>
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