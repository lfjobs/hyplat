<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@page import="hy.ea.bo.Company"%>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
        Company c = (Company) session.getAttribute("currentcompany");
         %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>个人待办</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script> 
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <%--树形机构--%>
		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
			
         <script type="text/javascript" src="<%=basePath %>js/ea/mysl/administrative/PersonalToDo.js"></script>
        <script type="text/javascript">
            var basePath = "<%=basePath%>";
            var treeid = "<%=c.getCompanyID()%>";
            var companyName = "<%=c.getCompanyName()%>";
            var result='';
            var doid = "";
            var ppageNumber = '${pageNumber}';
            var token=0;
            var complystatus="${complystatus}";
            var search="${search}";
            var sdate="${sdate}";
            var edate="${edate}";
            var token=0;
            var str = "";
</script>
	</head>
<body>
	<div class="main_main">
	<form name="ToDoForm" id="ToDoForm" method="post">
	<input type="submit" id="submit" name="submit" style="display: none;"/>
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="150" align="center">交办单据编号</th>
					<th width="50" align="center">指派人</th>
					<th width="150" align="center">待办事项</th>
					<th width="120" align="center">交办日期</th>
					<th width="50" align="center">完成日期</th>
					<th width="120" align="center">状态</th>
			</thead>
			<tbody>
				<s:iterator value="pageForm.list">
					<tr id="${doid}">
						<td><input type="checkbox" name="chbox" class="chx"
							value="${doid}" /><input type="hidden" id="id" value="${id}" />
						</td>
						<td><span id="serialnumber">${serialnumber}</span>
						</td>
						<td><span id="paymentname">${paymentname}</span>
						</td>
						<td><span id="receiptType">交办完成${receiptType}</span>
						</td>
						<td><span id="paymentTime">${fn:substring(paymentTime,0,10)}</span>
						</td>
						<td><span id="audittime">${fn:substring(audittime,0,10)}</span>
						</td>
						<td><span id="complystatus"> <c:if
									test='${complystatus=="01"}'>待办</c:if> <c:if
									test='${complystatus=="02"}'>已办</c:if> <c:if
									test='${complystatus=="03"}'>已派发</c:if>
						</span><input type="hidden" value="${lookOverurl}" id="lookOverurl" />
						<input type="hidden" value="${printurl}" id="printurl" />
						<input type="hidden" value="${complystatus}" id="complystatuss" />
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<s:token></s:token>
		</form>
		<c:import url="../../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/personaltodo/ea_getDtMydoList.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}&auditorstatus=${auditorstatus}">
			</c:param>
		</c:import>
	</div>

	<!-- -------------------------------------查询------------------------------------ -->
	<form name="cstaffForm" id="cstaffForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display:none" />
		<div class="contentbannb jqmWindow jqmWindowcss3" style="top: 10%"
			id="jqModelsubmit">
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						查询信息
						<div class="close"></div>
					</div>
				</div>
				<table width="350" border="0" id="stafftable" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td><table width="400" height="196" border="0"
								id="stafftable2" align="center" cellpadding="0" cellspacing="0"
								style="margin-top: 5px; margin-bottom: 5px;">
								<tr>
									<td width="20" height="32" align="right">交办单据编号：</td>
									<td width="50"><input type="text" id="serialnumber"
										name="mydo.serialnumber" /></td>
								</tr>
								<tr>
									<td width="50" align="right" height="32">交办类别：</td>
									<td width="50"><select id="receiptType"
										name="mydo.receiptType">
											<option value="">请选择</option>
											<option value="耗材领用申请单">耗材领用申请单</option>
											<option value="设备借用单">设备借用单</option>
											<option value="设备维修单">设备维修单</option>
											<option value="证书领用单">证书领用单</option>
											<option value="图书借阅单">图书借阅单</option>
											<option value="盖章登记单">盖章登记单</option>
											<option value="出差审批单">出差审批单</option>
											<option value="请假审批单">请假审批单</option>
											<option value="加班审批单">加班审批单</option>
											<option value="汽车维护检查单">汽车维护检查单</option>
											<option value="卫生检查单">卫生检查单</option>
											<option value="用车申请单">用车申请单</option>
											<option value="客餐申请单">客餐申请单</option>
									</select></td>
								</tr>
								<tr>
									<td width="50" align="right" height="32">交办状态：</td>
									<td width="50"><select id="complystatus"
										name="mydo.complystatus">
											<option value="">请选择</option>
											<option value="01">未办</option>
											<option value="02">已办</option>
											<option value="03">已指派</option>
									</select></td>
								</tr>
								<tr>
									<td width="50" height="32" align="right">指派人：</td>
									<td><input type="text" id="paymentname"
										name="mydo.paymentname" /></td>
								</tr>
								<tr>
									<td width="50" height="32" align="right">交办时间：</td>
									<td><input type="text" id="sdate" onfocus="date(this)"
										size="10" name="sdate" />到<input type="text" id="edate"
										onfocus="date(this)" size="10" name="edate" /></td>
								</tr>
								<tr>
									<td height="30" colspan="2" align="center"><input
										type="hidden" name="search" value="search" /> <input
										type="button" class="input-button JQuerySubmit"
										id="JQuerySubmit" style="cursor:pointer;width:80px;"
										value="查询" />
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
	</form>
	
 <!--选择人员窗口 -->
    <div class="jqmWindow" id="zj"
		style="width: 600px; height: 450px; left: 40%; top: 5%">
		<div>
			<div class="contentbannb">
				<div class="drag">
					组织机构树
				</div>
			</div>
		</div>
		<table style="width: 100%; height: 450px;" cellpadding="0"
			cellspacing="0" style="margin-top: 2px;">
			<tr>
				<td width="30%" align="left" valign="top">
					<div id="tree1"></div>
				</td>
				<td width="70%" align="left" valign="top">
					<table style="width: 450px; height: 350px;" align="center"
						cellpadding="0" cellspacing="2"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="200" height="20" class="txt01">
								备选人员
							</td>
							<td width="50">
								&nbsp;
							</td>
							<td width="200" align="left" class="txt01">
								已选人员
							</td>
						</tr>
						<tr>
							<td height="137">
								<select name="leftfields" multiple="multiple" id="leftfields"
									style="height: 300px; width: 150px; font-size: 9pt">
								</select>

							</td>
							<td width="250" align="center">
								<div>
									<input type="button" class="input-button" id="query_add"
										value=" 添加 " />
								</div>
								<div>
									<input type="button" class="input-button" id="query_delete"
										value=" 删除 " />
								</div>
							</td>
							<td>
								<select name="rightfields" multiple="multiple" id="rightfields"
									style="height: 300px; width: 150px; font-size: 9pt">
								</select>
							</td>
							<td width="100">
								&nbsp;
							</td>
						</tr>


						<tr>
							<td height="30" colspan="3" align="center">
								<br />
								<input type="button" class="input-button" id="confirm"
									value=" 确定 " onclick="submit();"/>
								<input type="button" class="input-button" id="closed"
									value=" 关闭 " onclick="closed();" />
								<a href="#" id="ttttt" target="_self"></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>