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
		<title>现场会议</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/ea/office_ea/corporationcode/Meeting.js"></script>
		<script src="<%=basePath%>js/common/organizationTree.js" ></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  meetingID = '';
         var  token=0;
            var treeID = parent.frames["leftFrame"].tree.getSelectedItemId();
	        var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
	        var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
	        var treePName =parent.frames["leftFrame"].tree.getItemText(treePID);
		</script>


	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="20" align="center">
							选择
						</th>
						<th width="20" align="center">
							序号
						</th>
						<th width="150" align="center">
							会议名称
						</th>
						<th width="100" align="center">
							会议主题
						</th>
						<th width="100" align="center">
							发起部门
						</th>
						<th width="70" align="center">
							开会时间
						</th>
						<th width="100" align="center">
							参会领导
						</th>
						<th width="100" align="center">
							参会人员
						</th>
						<th width="100" align="center">
							会议室（地点）
						</th>
						<th width="100" align="center">
							会议室布置
						</th>
						<th width="100" align="center">
							会议记录
						</th>
						<th width="100" align="center">
							会议是否完成
						</th>
						<th width="100" align="center">
							会议通知
						</th>
						<th width="100" align="center">
							会议文件
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" status="number">
						<tr id="${meetingID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${meetingID}" />
							</td>
							<td>
								<s:property value="%{#number.index+1}" />
							</td>
							<td>
								<span id="mtName">${mtName}</span>
							</td>
							<td>
								<span id="mtSubject">${mtSubject}</span>
							</td>
							<td>
								<span id="mtOrganizationID" style="display:none">${mtOrganizationID}</span>${mtOName }
							</td>
							<td>
								<span id="mtDate">${fn:substring(mtDate,0,10)}</span>
							</td>
							<td>
								<span id="mtLeader">${mtLeader}</span>
							</td>
							<td>
								<span id="mtStaff">${mtStaff}</span>
							</td>
							<td>
								<span id="mtAddr">${mtAddr}</span>
							</td>
							<td>
								<span id="mtPlace">${mtPlace}</span>
							</td>
							<td>
								<span id="mtLog">${mtLog}</span>
							</td>
							<td>
								<span id="mtStatus" style="display:none">${mtStatus}</span>
								<s:if test="mtStatus=='00'">未完成</s:if>
								<s:if test="mtStatus=='01'">已完成</s:if>
							</td>
							<td>
								<span id="mtNotice">${mtNotice}</span>
							</td> 
							<td>
								<span style="display:none" id="mtFilePath">${mtFilePath}</span><s:if test="mtFilePath==null||mtFilePath==''">无</s:if>
                            	<s:else><a onclick="lookImage('${mtFilePath}')" href="#">查看</a></s:else>
								<span id="meetingKey" style="display: none">${meetingKey}</span>
								<span id="meetingID" style="display: none">${meetingID}</span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/meeting/ea_getMeetingList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="top: 4%; width: 800px;" id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					详细信息
					<div class="close">
					</div>
				</div>
				<table cellpadding="5px" cellspacing="10px" name="stafftable" style="margin-left: 42px"
					id="stafftable">
					<tr>
						<td align="right">
							会议名称：
						</td>
						<td>
							<input name="meeting.mtName" id="mtName" />
						</td>
						<td valign="top"><br /></td><td align="right">
							会议主题：
						</td>
						<td>
							<input name="meeting.mtSubject" id="mtSubject" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发起部门：
						</td>
						<td>
							<select id="mtOrganizationID" name="meeting.mtOrganizationID"></select>
						</td>
						<td valign="top"><br /></td><td align="right">
							开会时间：
						</td>
						<td>
							<input name="meeting.mtDate" id="mtDate" onfocus="date(this)"/>
						</td>
					</tr>
					<tr>
						<td valign="top">
							会议是否完成：
						</td>
						<td> 
							<select name="meeting.mtStatus" id="mtStatus" >
								<option value="00">未完成</option>
								<option value="01">已完成</option>
							</select>
						</td>
						<td valign="top"><br /></td><td align="right">
							会议文件
						</td>
						<td>
							<input type="file" name="meeting.mtFile" contentEditable="false"/><input type="text" style="display:none" name="meeting.mtFilePath" id="mtFilePath"/>
						</td>
					</tr>
					<tr>
					<td align="right">
							参会领导：
						</td>
						<td  colspan="6">
							<input name="meeting.mtLeader" id="mtLeader" size="110"/>
						</td>
					
					</tr>
					<tr>
					<td align="right">
							会议记录：
						</td>
						<td  colspan="6">
							<input name="meeting.mtLog" id="mtLog" size="110"/>
						</td>
					
					</tr>
					
					<tr>
						<td align="right" >
							会议通知：
						</td>
						<td colspan="6">
							<input name="meeting.mtNotice" id="mtNotice" size="110"/>
						</td>
					</tr>
					<tr>
						<td align="right" >
							会议室地点：
						</td>
						<td colspan="6">
							<input name="meeting.mtAddr" id="mtAddr" size="110"/>
						</td>
					</tr>
					<tr>
					<td align="right">
							会议室布置：
						</td>
						<td colspan="5">
							<textarea rows="5" cols="80" name="meeting.mtPlace" id="mtPlace"></textarea>
						</td>
					</tr>
					<tr>
						<td align="right">
							参会人员：
						</td>
						<td colspan="5">
							<textarea rows="5" cols="80" name="meeting.mtStaff" id="mtStaff"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="5" align="center">
							<input type="hidden" name="meeting.meetingID" id="meetingID" />
							<input type="hidden" name="meeting.meetingKey" id="meetingKey" />
							<input type="button" class="input-button JQuerySubmit"
								style="cursor: pointer; width: 80px;" value="提交" />
							<input type="button" class="input-button JQueryreturn"
								style="cursor: pointer; width: 80px;" value="取消" />
						</td>
					</tr>
				</table>
				<s:token></s:token>
			</form>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 500px; right: 25%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="460px" id="cataffSearchTable">
				<tr>
						<td align="right">
							部门：
						</td>
						<td>
							<select id="deptID" name="meeting.mtOrganizationID">
								<option value="">全部</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							会议名称：
						</td>
						<td>
							<input name="meeting.mtName" id="mtName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							合同状态：
						</td>
						<td>
							<select name="meeting.mtStatus" id="mtStatus">
								<option value="">全部</option>
								<option value="00">未完成</option>
								<option value="01">已完成</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							会议时间：
						</td>
						<td>
							<input name="sDate" id="sDate" onfocus="date(this)"/>到<input name="eDate" id="eDate" onfocus="date(this)"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>