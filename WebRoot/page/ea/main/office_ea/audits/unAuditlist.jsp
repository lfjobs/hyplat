<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>审核管理</title>
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
         <script type="text/javascript" src="<%=basePath %>js/ea/mysl/CheckManage-right.js"></script>
        <script type="text/javascript">
            var basePath = "<%=basePath%>";
            var auditid = "";
            var ppageNumber = ${pageNumber};
            var token=0;
            var auditorstatus="${auditorstatus}";
            var search="${search}";
            var sdate="${sdate}";
            var edate="${edate}";
            var token=0;
</script>
	</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="100" align="center">单位</th>
					<th width="100" align="center">项目名称</th>
					<th width="50" align="center">任务编号</th>
					<th width="200" align="center">任务标题</th>
					<th width="50" align="center">申请人</th>
					<th width="120" align="center">申请日期</th>
					<th width="50" align="center">执行人</th>
					<th width="100" align="center">部门</th>
					<th width="120" align="center">任务类型</th>
					<th width="30" align="center">缓急</th>
					<th width="120" align="center">开始时间</th>
					<th width="120" align="center">计划完成时间</th>
					<th width="120" align="center">实际完成时间</th>
					<th width="50" align="center">派发人</th>
					<th width="120" align="center">审核状态</th>
			</thead>
			<tbody>
				<c:forEach var="arr" items="${pageForm.list}">
					<tr id="${arr[0]}">
						<td><input type="checkbox" name="chbox" class="chx" value="${arr[0]}" />
						<input type="hidden" id="taskid" value="${arr[1]}"/></td>
						<td><span id="applycompanyName">${arr[2]}</span></td>
						<td><span id="proname">${arr[3]}</span></td>
						<td><span id="taskcode">${arr[4]}</span></td>
						<td><span id="taskname">${arr[5]}</span></td>
						<td><span id="applyerName">${arr[6]}</span></td>
						<td><span id="applytime">${arr[7]}</span></td>
						<td><span id="staffname">${arr[8]}</span></td>
						<td><span id="orgname">${arr[9]}</span></td>
						<td><span id="tasktype">${arr[10]}</span></td>
						<td><span id="emergency">${arr[11]}</span></td>
						<td><span id="startdate">${fn:substring(arr[12],0,10)}</span>
						</td>
						<td><span id="planfinishdate">${fn:substring(arr[13],0,10)}</span>
						</td>
						<td><span id="factfinishdate">${arr[14]==null?'未完成':arr[14]}</span></td>
						<td><span id="distributeName">${arr[15]}</span></td>
						<td><span id="taskstatus">
						<c:if test='${arr[16]=="00"}'>生产设计</c:if>
							<c:if test='${arr[16]=="01"}'>设计完成</c:if>
							<c:if test='${arr[16]=="02"}'>申请修改</c:if>
							<span>-</span>
							<c:if test='${arr[17]=="01"}'>未审核</c:if>
							<c:if test='${arr[17]=="02"}'>已审核</c:if>
							<c:if test='${arr[17]=="03"}'>驳回</c:if></span><input type="hidden" value="${arr[17]}" id="astatus"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:import url="../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/checkmanage/ea_getDtMyauditList.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}&auditorstatus=${auditorstatus}">
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
						审核信息
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
									<td width="20" height="32" align="right">项目名称：</td>
									<td width="50"><input type="text" id="proname"
										name="myaudit.proname" />
									</td>
								</tr>
								<tr>
								<td width="50" align="right" height="32">任务名称：</td>
									<td width="50"><input type="text" id="taskname"
										name="myaudit.taskname" />
									</td>
								</tr>
								<tr>
									<td width="50" height="32" align="right">申请人：</td>
									<td><input type="text" id="applyerName"
										name="myaudit.applyerName" />
									</td>
								</tr>
								<tr>
									<td width="50" height="32" align="right">申请时间：</td>
									<td><input type="text" id="sdate" onfocus="date(this)" size="10"
										name="sdate" />到<input type="text" id="edate" onfocus="date(this)" size="10"
										name="edate" />
									</td>
								</tr>
								<tr>
									<td height="30" colspan="2" align="center">
									<input type="hidden" name="search" value="search"/>
									<input type="button"
										class="input-button JQuerySubmit" id="JQuerySubmit"
										style="cursor:pointer;width:80px;" value="查询" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
	</form>
	
	<!-- -----------------------------------审核并继续审核-------------------------------- -->
	<form name="SendForm" id="SendForm" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 290px; right: 20%; top: 10%;"
			id="jqModelSend">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核通过并继续
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="10"
				cellpadding="20">
				<tr class="shows">
					<td align="right">审核人公司：</td>
					<td align="left"><select id="auditorcompanyid"
						name="myaudit.auditorcompanyid" onchange="changeCompany(this);"
						style="width:200px;">
							<option value="">请选择审核人公司</option>
					</select></td>
				</tr>
				<tr class="shows">
					<td align="right">审核人部门：</td>
					<td align="left"><select id="auditororgID"
						name="myaudit.auditororgID" onchange="changeDept(this);"
						style="width: 200px;">
							<option value="">请选择审核人部门</option>
					</select></td>
				</tr>
				<tr class="shows">
					<td align="right">审核人姓名：</td>
					<td align="left"><select name="myaudit.auditorid"
						id='auditorid' style="width: 200px;">
							<option value="">请选择审核人</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" name="myaudit.comments" id="comments"></textarea>
					
					</td>
				</tr>
			</table>

			<div align="center">
				<input type="button" class="input-button" id="submitResult" value=" 提交 " />
			</div>
			</center>
		</div>
	</form>
	
	<!-- -----------------------------------审核-------------------------------- -->
	<form name="SendForm2" id="SendForm2" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 230px; right: 20%; top: 10%;"
			id="jqModelSend2">
			<input type="submit" name="submit2" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right">审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" name="myaudit.comments" id="comments"></textarea>
					
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="submitResult2" value=" 提交 " />
					<input type="hidden" name="myaudit.auditorstatus" id="auditorstatus" value=""/>
			</div>
			</center>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>