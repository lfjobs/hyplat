<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
<title>POS收银设备管理</title>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/admin_main111.css" />
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/ea/human/attence/attenceGrouplist.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/common/common.js"></script>
<script type="text/javascript">
            var posID = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
            var notoken = 0;
            var companyID='<%=c.getCompanyID()%>';
            var companyName='<%=c.getCompanyName()%>';
            var select = 0;
</script>
<style type="text/css">
input.input-button {
	margin: 4px;
}

.containerTableStyle {
	position: static;
	overflow: auto;
}

.text_tree {
	width: 220px;
	background: #ffffff;
	overflow: auto;
}

.check {
	border-radius: 30px;
	width: 30px;
	height: 30px;
	line-height: 30px;
	text-align: center;
	display: block;
	background: red;
	float: left;
	margin-right: 3px;
	color: #ffffff;
}
.wcheck{
  background:#ffffff;
  border:1px sold red;
  color:red;
}
</style>
</head>
<body>
	<form name="searchForm" id="searchForm">
		<input type="submit" name="submit" style="display:none" />
		<input name="posDevice.posNum" id="posNum" style="display:none" />
		<input name="posDevice.organizationName" id="organizationName"
			style="display:none" />
		<input name="posDevice.state" id="state" style="display:none" />

		<input type="hidden" name="search" value="search" />

		<s:token />
	</form>

	<table class="flexme11">
		<thead>
			<tr>
				<th width="30" align="center">选择</th>
				<th width="100" align="center">考勤组名称</th>
				<th width="90" align="center">考勤类型</th>
				<th width="100" align="center">考勤时间</th>
				<th width="100" align="center">考勤地点</th>
				<th width="200" align="center">创建人</th>
				<th width="80" align="center">创建时间</th>
				<th width="140" align="center">生效时间</th>
				<th width="90" align="center">使用部门</th>


			</tr>
		</thead>
		<tbody>
			<s:iterator value="pageForm.list">
				<tr id="${atgID}">
					<td><input type="radio" name="a" class="JQuerypersonvalue"
						value="${atgID}" /></td>
					<td><span id="atgkey" style="display:none;">${atgkey}</span> <span
						id="atgID" style="display:none;">${atgID}</span></td>
					<td><span id="attenceName">${attenceName}</span></td>
					<td><span id="attenceType" style="display: none;">${attenceType}</span>
						<c:choose>
							<c:when test="${attenceType=='00'}">固定时间上下班</c:when>
							<c:when test="${attenceType=='01'}">按排班时间上下班</c:when>
							<c:when test="${attenceType=='02'}">不固定时间上下班</c:when>
							<c:otherwise>无</c:otherwise>
						</c:choose></td>
					<td><span id="attenceDate">${attenceDate}</span></td>
					<td><span id="attenceSite">${attenceSite}</span></td>

					<td><span id="effectDate">${fn:substring(effectDate,0,19)}</span>
					</td>
					<td><span id="createDate">${fn:substring(createDate,0,19)}</span>
					</td>
					<td></td>

				</tr>
			</s:iterator>
		</tbody>
	</table>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/pos/ea_getPosList.jspa?search=${search}&pageNumber=${pageNumber}">
		</c:param>
	</c:import>
	<!--添加窗口 -->
	<form name="addForm" id="addForm" method="post">
		<div class="jqmWindow" style="width:500px;right: 30%;top:5%"
			id="jqModeladd">
			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				考勤组
				<div class="close"></div>
			</div>

			<table width="100%" id="addTable" cellspacing="10" cellpadding="10">
				<tr>
					<td align="right">考勤组名称：</td>
					<td><input id="attenceGroup" style="width:195px" name="attenceGroup"
						maxlength="50" /> 
						<input id="posKey" type="hidden"
						name="attenceGroup" /> <input id="posID" type="hidden"
						name="attenceGroup" /></td>

				</tr>

				<tr>
					<td align="right">考勤类型：</td>
					<td><select id="posName" name="attenceGroup.posName">
							<option value="00">固定时间上下班</option>
							<option value="01">按排班时间上下班</option>
							<option value="02">不固定时间上下班</option>
					</select></td>

				</tr>
				<tr>
					<td align="right">考勤时间：</td>
					<td><input id="attenceDate" type="text" style="width:195px"
						value="每周一、二、三、四、五 09:00-12:00 13:30-18:00" class="put3" readonly />

					</td>
				</tr>
				<tr>
					<td colspan="2" class="maintd">
					<table class="guize" id="gzdefault" style="display:none;" cellspacing="10" cellpadding="10">
							<tr>
								<td align="right"><a href="javascript:" onclick="deleteRule(this)">删除</a> 规则1：</td>
								<td><span></span></td>
							</tr>
							<tr>
								<td align="right">星期：</td>
								<td>
									<div class="check wcheck">一</div>
									<div class="check wcheck">二</div>
									<div class="check wcheck">三</div>
									<div class="check wcheck">四</div>
									<div class="check wcheck">五</div>
									<div class="check wcheck">六</div>
									<div class="check wcheck">七</div></td>
							</tr>
							<tr>
								<td align="right">班次（打卡时间段）：</td>
								<td>默认班次 09:00——18:00</td>
							</tr>

						</table>
						
						<table class="guize" cellspacing="10" cellpadding="10">
							<tr>
								<td align="right"><a href="javascript:" onclick="deleteRule(this)">删除</a> 规则1：</td>
								<td><span>每周一、二、三、四、五 09:00-12:00 13:30-18:00</span></td>
							</tr>
							<tr>
								<td align="right">星期：</td>
								<td>
									<div class="check">一</div>
									<div class="check">二</div>
									<div class="check">三</div>
									<div class="check">四</div>
									<div class="check">五</div>
									<div class="check wcheck">六</div>
									<div class="check wcheck">七</div></td>
							</tr>
							<tr>
								<td align="right">班次（打卡时间段）：</td>
								<td>默认班次 09:00——18:00</td>
							</tr>

						</table>
						
						</td>
				</tr>
				<tr class="xzgz">
					<td align="right"><a onclick="createRule()">+新增规则</a>
					</td>

				</tr>

			</table>
			<div align="center">
				<input type="button" class="input-button" id="save" value=" 保存 "
					style="margin: 10px;" />
			</div>
		</div>
	</form>
	<div
		style="display:none;width: 280px; padding:10px;height: 500px;z-index:3001 ; background-color:#e1ecfc; filter : Alpha(opacity=100);"
		id="serdevice">
		<div id="addtree"
			style="overflow:auto;height: 480px;background:#ffffff;width:100%;"></div>


	</div>

	<%------------------------------------选择往来个人------------------------------------%>

	<form name="selectuserForm" id="selectuserForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<input type="submit" name="submit" style="display: none" />
		<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
			id="grjqModel">
			<div class="content1" style="width: 100%; height: 400px;">
				<div class="contentbannb">
					<div class="drag">往来个人</div>
				</div>
				<table width="99%" height="33" id="searchuser" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;">
					<tr>
						<td width="100" align="right">姓名：</td>
						<td width="142"><input name="contactUserID" class="input"
							id="contactUserID" size="10" style="margin-left: 2px;" /></td>
						<td height="33"><input type="button" class="btn02"
							id="searchuu" name="button7" value="查询" /> <input type="button"
							class="btn02" id="qduser" name="button5" value="确定" /> <input
							type="button" class="btn02 xzgr" name="button5" value="新增" /> <input
							type="button" class="btn02 closewr" name="button4" value="关闭" />
							<input type="hidden" name="parms" id="grparms" /></td>
						<td width="80"><a id="grsy" title="0">上一页</a></td>
						<td width="80"><a id="grxy" title="0">下一页</a></td>
						<td width="100"><a id="grzy">共&nbsp;&nbsp; <span
								style="color: red" id="grzycount"></span>&nbsp;&nbsp;页 </a></td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td width="16%">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
									<td>
										<div id="grTree" class="text_tree"
											style="overflow: scroll; z-index: 99; height: 320px;"></div>
									</td>
								</tr>
							</table></td>
						<td width="83%" valign="top" align="left">
							<div id="body_02cu"
								style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
							</div></td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
	</form>

	<%------------------------------------选择商户------------------------------------%>
	<form name="selectcompanyForm" id="selectcompanyForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<input type="submit" name="submit" style="display: none" />
		<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
			id="companyjqModel">
			<div class="content1" style="width: 100%; height: 400px;">
				<div class="contentbannb">
					<div class="drag">商户</div>
				</div>
				<table width="99%" height="33" id="searchcompany" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;">
					<tr>
						<td width="100" align="right">商户名称：</td>
						<td width="142"><input name="ccompanyID" class="input"
							id="ccompanyIDs" size="10" style="margin-left: 2px;" /></td>
						<td height="33"><input type="button" class="btn02"
							id="searchcc" name="button7" value="查询" /> <input type="button"
							class="btn02" id="qdcompany" name="button5" value="确定" /> <input
							type="button" class="btn02 JQueryreturns" name="button4"
							value="关闭" /> <input type="hidden" name="parms" id="dwparms" />

						</td>
						<td width="80"><a id="dwsy" title="0">上一页</a></td>
						<td width="80"><a id="dwxy" title="0">下一页</a></td>
						<td width="100"><a id="dwzy">共&nbsp;&nbsp; <span
								style="color: red" id="dwzycount"></span>&nbsp;&nbsp;页 </a></td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>

						<td width="83%" valign="top" align="left">
							<div id="body_02cc"
								style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
							</div></td>
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
