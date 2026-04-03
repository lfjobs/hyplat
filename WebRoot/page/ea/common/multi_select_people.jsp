<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>多选人员</title>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/multi_select_people.js"></script>

		<script type="text/javascript">
		var basePath='<%=basePath%>';
		var type=window.dialogArguments.type;
		var docId=window.dialogArguments.docID;
        </script>

		<style type="text/css">
.dialogWindow {
	font-size: 12px;
	position: absolute;
	background: #DAE7F6 repeat top;
	color: #333;
	border: 1px solid #99bbe8;
	width: 100%;
}

.containerTableStyle {
	position: static;
	overflow: auto;
}

#addTreess {
	height: 380px;
	width: 250px;
	overflow: auto;
	background:#FFFFFF;
	white-space:nowrap;
}
</style>
	</head>

	<body>
		<div class="dialogWindow">
			<div class="code_bannb">
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td class="txt01">
							<nobr>
							组织结构部门
							</nobr>
						</td>
					</tr>
				</table>
			</div>
			<table class="code_bannb" style="width:100%;height:450px;"
				cellpadding="0" cellspacing="0" style="margin-top: 2px;">
				<tr>
					<td width="30%" align="left" valign="top">
						<div id="addTreess" style="width:330px;"></div>
					</td>
					<td width="70%" align="left" valign="top">
						<table style="width:450px;height:350px;" align="center"
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
										<input type="button" class="ACT_btn" id="query_add" value="添加" />
									</div>
									<div>
										<input type="button" class="ACT_btn" id="query_delete"
											value="删除" />
									</div>
								</td>
								<td>
									<select name="rightfields" multiple="multiple" id="rightfields"
										style="height: 300px; width: 150px;font-size: 9pt">
									</select>
								</td>
								<td width="100">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td width="100" align="center" colspan="4">

									<nobr>
									<input type="checkbox" id="zfStatus" checked />
									允许转发
									<input type="checkbox" id="loadStatus" checked />
									允许下载
									<input type="checkbox" id="printStatus" checked />
									允许打印
									<input type="checkbox" id="shareStatus" checked />
									允许共享
									<input type="checkbox" id="pubStatus" checked />
									允许发布到网站
									</nobr>
								</td>
							</tr>


							<tr>
								<td height="30" colspan="3" align="center">
																 <br/>
									<input type="button" class="ACT_btn" id="confirm" value="确定" onclick = "submit();" />
									<a href="#" id="ttttt" target="_self"></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>

		<form id="pubForm" name="pubForm">
			<div class="jqmWindow" id="jqModelpub">
				<input type="submit" name="submit" style="display: none" />
				<input type="hidden" name="document.docId" id="docID" value="" />
				<input type="hidden" value=""
					name="document.transfer" id="transfer" />
				<input type="hidden" value="" name="document.load"
					id="load" />
				<input type="hidden" value="" name="document.print"
					id="print" />
				<input type="hidden" value="" name="document.share"
					id="share" />
				<input type="hidden" value="" name="document.pub"
					id="pub" />
				<input type="hidden" name="readers" id="readers" value="" />

			</div>
		</form>
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>
