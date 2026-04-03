<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>任务下达</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/validate.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/mysl/TaskManage.js"></script>
		<script>
            var basePath = "<%=basePath%>";
            var taskid = "";
            var ppageNumber = "${pageNumber}";
            var token=0;
            var proid = "${myproject.proid}";
            var currentstaffid = "${sessionScope.account.staffID}";
            
</script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="50" align="center">任务旗帜</th>
						<th width="40" align="center">
							序号
						</th>
						<th width="200" align="center">
							任务编号
						</th>
						<th width="200" align="center">
							任务标题
						</th>
						<th width="120" align="center">
							执行人
						</th>
						<th width="100" align="center">
							部门
						</th>
						<th width="120" align="center">
							任务类型

						</th>
						<th width="120" align="center">
							缓急

						</th>
						<th width="120" align="center">
							开始时间

						</th>
						<th width="120" align="center">
							计划完成时间

						</th>
						<th width="120" align="center">
							实际完成时间

						</th>
						<th width="120" align="center">
							派发人
						</th>
						<th width="120" align="center">
							状态

						</th>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${taskid}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${taskid}" />
							</td>
							<td> 
								<s:if test='warningStatues=="01"'>
								<img src="<%=basePath%>images/mysl/y.png" alt="预警"  width="20px" height="14px"/> 
								</s:if>
								<s:if test='warningStatues=="02"'>
								<img src="<%=basePath%>images/mysl/r.png" alt="过期"  width="20px" height="14px"/> 
								</s:if>
								<s:if test='warningStatues=="03"'>
								<img src="<%=basePath%>images/mysl/g.png" alt="完成"  width="20px" height="14px"/> 
								</s:if>
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="taskcode">${taskcode}</span>
							</td>
							<td>
								<span id="taskname">${taskname}</span>
							</td>
							<td>
								<span id="staffname">${staffname}</span>
								<span id="distributeid" style="display:none;">${distributeid}</span>
							</td>
							<td>
								<span id="orgname">${orgname}</span>
							</td>

							<td>
								<span id="tasktype" style="display: none;">${tasktype}</span>
								<s:if test='tasktype=="htzd"'>合同制定</s:if>
								<s:if test='tasktype=="scsj"'>生产计划通知书</s:if>
								<s:if test='tasktype=="scrw"'>生产任务通知书</s:if>
								<s:if test='tasktype=="sjdg"'>设计大纲</s:if>
								<s:if test='tasktype=="htrw"'>绘图任务</s:if>
							</td>
							<td>
								<span id="emergency" style="display: none;">${emergency}</span>
								<s:if test='emergency=="pt"'>普通</s:if>
								<s:if test='emergency=="jj"'>紧急</s:if>
								<s:if test='emergency=="tj"'>特急</s:if>

							</td>
							<td>
								<span id="startdate">${fn:substring(startdate,0,10)}</span>
							</td>
							<td>
								<span id="planfinishdate">${fn:substring(planfinishdate,0,10)}</span>
							</td>
							<td>
								<span id="factfinishdate">${factfinishdate}</span>
								<span id="taskkey" style="display: none">${taskkey}</span>
								<span id="taskid" style="display: none">${taskid}</span>
								<span id="proid" style="display: none">${proid}</span>
							</td>
							<td><span id="distributeName">${distributeName}</span></td>
							<td>
								<span id="phasestatus" style="display: none;">${phasestatus}</span>
								<s:if test='phasestatus=="00"'>未下达</s:if>
								<s:if test='phasestatus=="01"'>生产设计阶段</s:if>
								<s:if test='phasestatus=="02"'>设计完成阶段</s:if>
								<s:if test='phasestatus=="03"'>提交成果阶段</s:if>
								<s:if test='phasestatus=="04"'>项目归档阶段</s:if>
								

							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../ea/page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/taskmanage/ea_getTaskNoticeList.jspa?pageNumber=${pageNumber}&search=${search}&myproject.proid=${myproject.proid}">
				</c:param>
			</c:import>
		</div>

		<form name="searchForm" id="searchForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb jqmWindow jqmWindowcss2"
				style="width: 300px; top: 10%" id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							查询
							<div class="close"></div>
						</div>
					</div>


					<table width="100%" border="0" id="stafftable2" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td align="right">
								任务名称：
							</td>
							<td align="left">
								<input type="text" name="mytask.taskname" />
							</td>
						</tr>

						<tr>
							<td height="30" colspan="5" align="center">

								<input type="button" class="input-button" id="search"
									style="cursor: pointer; width: 80px;" value="查询" />
								<input name="search" value="search" type="hidden" />
								<input name="mytask.taskid" id="taskids" type="hidden" />
								<input name="myproject.proid"  value="${myproject.proid}"  type="hidden" />

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