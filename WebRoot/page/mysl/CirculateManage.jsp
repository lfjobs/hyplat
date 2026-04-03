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
<html xmlns="http://ww  w.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>传阅管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
         <script type="text/javascript" src="<%=basePath %>js/ea/mysl/CirculateManage.js"></script>
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
					<th width="100" align="center">项目名称</th>
					<th width="70" align="center">任务编号</th>
					<th width="120" align="center">任务标题</th>
					<th width="100" align="center">接收人</th>
					<th width="100" align="center">接收日期</th>
					<th width="70" align="center">执行人</th>
					<th width="100" align="center">部门</th>
					<th width="70" align="center">任务类型</th>
					<th width="30" align="center">缓急</th>
					<th width="100" align="center">开始时间</th>
					<th width="100" align="center">计划完成时间</th>
					<th width="100" align="center">实际完成时间</th>
					<th width="70" align="center">派发人</th>
			</thead>
			<tbody>
				<c:forEach var="arr" items="${pageForm.list}">
					<tr id="${arr[0]}">
						<td><input type="radio" name="auditid" class="JQuerypersonvalue" value="${arr[0]}" />
						<input type="hidden" id="taskid" value="${arr[1]}"/></td>
						<td><span id="applycompanyName">${arr[2]}</span></td>
						<td><span id="proname">${arr[3]}</span></td>
						<td><span id="taskcode">${arr[4]}</span></td>
						<td><span id="taskname">${arr[5]}</span></td>
						<td><span id="applyerName">${arr[6]}</span></td>
						<td><span id="applytime">${fn:substring(arr[7],0,10)}</span></td>
						<td><span id="staffname">${arr[8]}</span></td>
						<td><span id="orgname">${arr[9]}</span></td>
						<td><span id="tasktype">${arr[10]}</span></td>
						<td><span id="emergency">${arr[11]}</span></td>
						<td><span id="startdate">${fn:substring(arr[12],0,10)}</span>
						</td>
						<td><span id="planfinishdate">${arr[13]==null?'未完成':arr[13]}</span>
						</td>
						<td><span id="factfinishdate">${arr[14]}</span></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:import url="../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/spirit/ea_getEnterpriseSpiritList.jspa?pageNumber=${pageNumber}">
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
									<td width="20" height="32" align="right">项目名称：</td>
									<td width="50"><input type="text" id="projectname"
										name="mypassrecord.projectname" />
									</td>
								</tr>
								<tr>
								<td width="50" align="right" height="32">任务名称：</td>
									<td width="50"><input type="text" id="taskname"
										name="mypassrecord.taskname" />
									</td>
								</tr>
								<tr>
									<td width="50" height="32" align="right">传阅时间：</td>
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
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>