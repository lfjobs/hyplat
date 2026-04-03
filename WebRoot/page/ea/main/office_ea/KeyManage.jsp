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
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>钥匙管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
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
		<script type="text/javascript">
			var token = 0;
            var keyManageID = "";
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
		</script>
		
		<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/KeyManage.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							序号
						</th>
						<th width="150" align="center">
							部门名称
						</th>
						<th width="150" align="center">
							房间号
						</th>
						<th width="150" align="center">
							钥匙数量
						</th>
						<th width="150" align="center">
							保管人
						</th>
						<th width="150" align="center">
							借出时间
						</th>
						<th width="150" align="center">
							归还时间
						</th>
						<th width="300" align="center">
							备忘录
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${keyManageID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${keyManageID}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="deptName">${deptName}</span>
							</td>

							<td>
								<span id="roomNum">${roomNum}</span>
							</td>
							<td>
								<span id="keyCount">${keyCount}</span>
							</td>
							<td>
								<span id="custodian">${custodian}</span>
							</td>
							<td>
								<span id="borrowDate" class="datas">${fn:substring(borrowDate,0, 10)}</span>
							</td>
							<td>
								<span id="returnDate" class="datas">${fn:substring(returnDate,0, 10)}</span>
							</td>
							<td>
								<span id="memorandum">${memorandum}</span>
								<span id="keyManageID" style="display: none">${keyManageID}</span>
								<span id="keyManageKey" style="display: none">${keyManageKey}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/keyManage/ea_getKeyManageList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss" style="top: 10%"
			 id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							钥匙管理
							<div class="close"></div>
						</div>
					</div>
					<table width="550" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="550" height="135" border="0" align="center"
									cellpadding="0" cellspacing="0" id="stafftable2"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td width="100" height="30" align="right">
											部门名称：
										</td>
										<td width="148">
											<input type="text" id="deptName" name="keyManage.deptName" />
										</td>
										<td width="90" align="right">
											房间号：
										</td>
										<td width="212">
											<input type="text" id="roomNum"
												name="keyManage.roomNum" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											钥匙数量：
										</td>
										<td>
											<input name="keyManage.keyCount" id="keyCount" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											保管人：
										</td>
										<td>
											<input name="keyManage.custodian" id="custodian" type="text"
												class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											借出时间：
										</td>
										<td>
											<input name="keyManage.borrowDate" id="borrowDate"
												onfocus="date(this);" type="text" class="input" size="20" />
										</td>
										<td align="right">
											归还时间：
										</td>
										<td>
											<input name="keyManage.returnDate" id="returnDate"
												onfocus="date(this);" type="text" class="input" size="20" />
										</td>
									</tr>
									<tr>
									<td height="30" align="right">
											备忘录：
										</td>
										<td colspan="4"  width="500">
											<input name="keyManage.memorandum" type="text" class="input" id="memorandum" style="width: 420px"/>
										</td>
									</tr>
									<tr>
										<td colspan="5" align="center">
											<input name="keyManage.keyManageKey" id="keyManageKey"type="hidden" class="input" size="20" />
											<input name="keyManage.keyManageID" id="keyManageID" type="hidden" class="input" size="20" />
											<input type="button" class="input-button JQuerySubmit"  style="cursor: pointer; width: 80px;" value="提交" />
											<input type="button" class="input-button JQueryreturn"  style="cursor: pointer; width: 80px;" value="取消" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>







				</div>
				<s:token></s:token>
			</form>
		</div>
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%;top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							部门名称：
						</td>
						<td width="261">
							<input name="keyManage.deptName" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<iframe name = "hidden" height="0" width ="100%"></iframe>
	</body>
</html>