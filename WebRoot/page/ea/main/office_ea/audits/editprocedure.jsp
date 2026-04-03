<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<meta http-equiv="x-ua-compatible" content="ie=8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>审核流程查看修改</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/ea/office_ea/audits/editprocedure.js"></script>

		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>

		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>

		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>

		<script src="<%=basePath%>js/common/tinyTips/jquery.tinyTips.js"></script>

		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.core.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.widget.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.mouse.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/jquery.ui.sortable.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/common/tinyTips/images/tinyTips.css" />

		<style type="text/css">
#sortable {
	list-style-type: none;
	margin-left: 30%;
}

#sortable li {
	vertical-align: middle;
	float: left;
	width: 106px;
	cursor: move;
}
#sortable1 li{
  float:left;

}

li.img {
	padding-top: 50px;
}
</style>

		<script type="text/javascript">
  var basePath='<%=basePath%>';  
  var proId = "${procedure.proId}"; 

  
		$(document).ready(function() {
		
		
		
		$('img.tTip').tinyTips('green', 'title');
		
		
		$("ul li:last").remove();
	
		
		});
		</script>


	</head>
	<body>


		<div id="jqModelSearch">

			<div class="drag">

				审核流程
			</div>


			<div style="text-align: center; height: 200px; padding-top: 30px;">
				<center> <span><strong>${procedure.proName}</strong>
				</span>

				<ul id="sortable">
					<s:iterator value="phaselist" status="status">
						<li class="gg" id="sortable_${procedure.proKey}">
							<table>
								<tr>
									<td><img class="tTip" title="1"  src="<%=basePath%>images/ea/office/audit.png" /></td>
								</tr>
								<tr>
									<td> <a class="show" href="javascript:getPhaseInfo('${phaseId}')">${phaseName}</a></td>
								</tr>
							</table>

						</li>

						<li class="img">
							<img src="<%=basePath%>images/ea/office/redarrow.jpg" />
						</li>

					</s:iterator>


				</ul>
				</center>

			</div>




		</div>



		<div id="jqModel" style="display: none; width: 100%;">
			<form id="updateForm" name="updateForm">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">

					修改
					<div class="close"></div>
				</div>


				<div>

					<table width="800" border="0" align="center" cellpadding="0"
						cellspacing="0" class="table" style="background: #FFFFFF;margin-top:10px;">
						<tr>
							<td width="20%" height="30" align="right">
								<span class="xx">*</span>第
								<span class="numaudit"></span>审核阶段名称：



							</td>
							<td align="left" height="30">
								<input type="text" size="25" name="phase.phaseName"
									id="phaseName" />
							</td>
						</tr>

						<tr>
							<td width="20%" height="30" align="right">
								<span class="xx">*</span>第
								<span class="numaudit">1</span>审核阶段类型：
							</td>
							<td align="left">
								<select id="auditType" name="phase.auditType">
									<option value="01">
										单一审核人
									</option>
									<option value="02">
										多人审核任一人同意/驳回即同意/驳回
									</option>
									<option value="03">
										多人审核全部同意/驳回即同意/驳回
									</option>
								</select>
							</td>
						</tr>

						<tr>
							<td align="right">
								第
								<span class="numaudit">1</span>审核阶段审核人：
							</td>
							<td>


								<textarea style="overflow-y: auto;" cols="80" rows="5"
									id="auditarea" readonly="readonly"></textarea>
								&nbsp;
								<a href="#" onclick="selectAuditor();">选择</a>
							</td>
							<input type="hidden" value="" id="strid" name="strid" />
							<input type="hidden" value="" id="auditorName"
								name="auditor.auditorName" />
						</tr>



					</table>
					<table width="890" height="20" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td colspan="10" align="center">


								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 90px;" value=" 保存 " />
								<input type="button" class="input-button deletePhase"
									style="cursor: pointer; width: 125px;" value="   删除当前审核阶段   " />

								<input type="hidden" name="meetingID" value="${meetingID}" />
							</td>
						</tr>
					</table>
				</div>

			</form>
		</div>

		<div class="jqmWindow" id="zj"
			style="width: 800px; height: 450px; left: 20%; top: 1%">
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
					<td width="40%" align="left" valign="top">
						<div id="tree1"
							style="height: 400px; overflow: auto; background: #ffffff;"></div>
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
										value=" 确定 " onclick="submit();" />
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


	</body>
</html>
