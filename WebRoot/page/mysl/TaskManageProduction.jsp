<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="hy.ea.bo.Company"%>
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
<meta http-equiv="cache-control" content="no-cache" />
<title>生产设计阶段</title>
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
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
<%--树形机构--%>
<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
<script type="text/javascript"
			src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>        
<script type="text/javascript"
	src="<%=basePath %>js/ea/mysl/TaskManageProduction.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
	var taskid = "";
	var pageNumber = ${pageNumber};
	var token = 0;
	var treeid = "<%=c.getCompanyID()%>";
    var companyName = "<%=c.getCompanyName()%>";
    var result='';
    var search="${search}";
    var  proid="${myproject.proid}";
</script>
</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="50" align="center">任务旗帜</th>
					<th width="40" align="center">序号</th>
					<th width="50" align="center">任务编号</th>
					<th width="200" align="center">任务标题</th>
					<th width="120" align="center">执行人</th>
					<th width="100" align="center">部门</th>
					<th width="120" align="center">任务类型</th>
					<th width="120" align="center">缓急</th>
					<th width="120" align="center">开始时间</th>
					<th width="120" align="center">计划完成时间</th>
					<th width="120" align="center">实际完成时间</th>
					<th width="120" align="center">是否下达</th>
					<th width="120" align="center">审核状态</th>
					<th width="120" align="center">派发人</th>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${taskid}">
						<td><input type="checkbox" name="chbox" class="chx"
							value="${taskid}" /></td>
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
						<td><span><%=number%></span></td>
						<td><span id="taskcode">${taskcode}</span></td>
						<td><span id="taskname">${taskname}</span></td>
						<td><span id="staffname">${staffname}</span></td>
						<td><span id="orgname">${orgname}</span></td>

						<td><span id="tasktype">${tasktype}</span>
							<s:if test='tasktype=="htzd"'>合同制定</s:if> <s:if
								test='tasktype=="scsj"'>生产计划通知书</s:if> <s:if
								test='tasktype=="sjdg"'>设计大纲</s:if> <s:if
								test='tasktype=="htrw"'>绘图任务</s:if></td>
						<td><span id="emergency" style="display: none;">${emergency}</span>
							<s:if test='emergency=="pt"'>普通</s:if> <s:if
								test='emergency=="jj"'>紧急</s:if> <s:if test='emergency=="tj"'>特急</s:if>

						</td>
						<td><span id="startdate">${fn:substring(startdate,0,10)}</span>
						</td>
						<td><span id="planfinishdate">${fn:substring(planfinishdate,0,10)}</span>
						</td>
						<td><span id="factfinishdate">${factfinishdate}</span> <span
							id="taskkey" style="display: none">${taskkey}</span> <span
							id="taskid" style="display: none">${taskid}</span> <span
							id="proid" style="display: none">${proid}</span></td>
						<td><span id="phasestatus" style="display:none;">${phasestatus}</span>
							<s:if test='phasestatus=="00"'>未下达</s:if>
							<s:else>已下达</s:else></td>
						<td><span id="auditstatus" style="display:none;">${auditstatus}</span>
							<s:if test='phasestatus=="00"'>任务未下达</s:if>
							<s:elseif test='phasestatus=="01"'>生产设计</s:elseif>
							<s:elseif test='phasestatus=="02"'>设计完成</s:elseif>
							<s:elseif test='phasestatus=="03"'>提交成果</s:elseif>
							<s:elseif test='phasestatus=="04"'>项目档案</s:elseif>
							<s:else>任务未下达</s:else>
							<span>-</span>
							<s:if test='auditstatus=="01"'>审核中</s:if>
							<s:elseif test='auditstatus=="02"'>已审核</s:elseif>
							<s:elseif test='auditstatus=="03"'>驳回</s:elseif>
							<s:elseif test='auditstatus=="00"'>未审核</s:elseif>
							<s:else>未审核</s:else>
							</td>
						<td><span id="distributeName">${distributeName}</span></td>
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/taskmanage/ea_getListByTaskManageProduction.jspa?pageNumber=${pageNumber}&myproject.proid=${myproject.proid}&search=${search}">
			</c:param>
		</c:import>
	</div>
	<!--选择审核人员窗口 -->
	<form name="SendForm" id="SendForm" method="post">
		<div class="jqmWindow"
			style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;"
			id="jqModelSend">
			<input type="submit" name="submit" style="display: none" />
			<div class="drag">
				报送审批 <div class="close"></div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right">收件人公司：</td>
					<td align="left"><select id="receiverCompanyID"
						name="document.receiverCompanyID" onchange="changeCompany(this);"
						style="width:200px;">
							<option value="">请选择收件人公司</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">收件人部门：</td>
					<td align="left"><select id="receiverDeptID"
						name="document.receiverDeptID" onchange="changeDept(this);"
						style="width: 200px;">
							<option value="">请选择收件人部门</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">收件人姓名：</td>
					<td align="left"><select name="document.receiverID"
						id='receiverID' style="width: 200px;">
							<option value="">请选择收件人</option>
					</select></td>
				</tr>
			</table>

			<div align="center" style="margin-top: 25px;">
				<input type="button" class="input-button" id="submitResult"
					value=" 提交 " /> <input type="button" class="input-button close"
					id="submitResult" value=" 关闭 " /> <input type="hidden"
					id="submitType" value="" name="submitType" /> <input type="hidden"
					id="docId" name="document.docId" value="" /> <input type="hidden"
					id="jump" name="jump" value="" /> <input type="hidden"
					name="comment" id="comment" value="" />
			</div>
			</center>
		</div>
	</form>
	
	<!--选择传阅人员窗口 -->
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
	<!--查询窗口 -->	
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
								<input name="myproject.proid" id="proid" type="hidden" value="${myproject.proid}"/>
								<input name="mytask.phasestatus" id="phasestatus" type="hidden" />

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