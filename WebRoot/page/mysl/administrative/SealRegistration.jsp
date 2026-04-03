<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="hy.ea.bo.Company"%>
<%@ page import="hy.ea.bo.CAccount"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company) session.getAttribute("currentcompany");
	CAccount ac = (CAccount) session.getAttribute("account");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>图书借阅表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<style type="text/css">
.windowJqm {
	left: 55%;
	width: 870px;
	margin-left: -450px;;
}

.sort {
	padding-right: 80px;
}

.underline {
	text-decoration: underline;
}

.sty {
	padding-left: 5px;
}
</style>

<script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/admin_main.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />
<script type="text/javascript"
	src="<%=basePath%>js/common/organizationTree.js"></script>

<script type="text/javascript">
 			var token = 0;
 			var notoken= 0;
 			var id = "";
        	var b=true;
        	var basePath='<%=basePath%>';
       		var pageNumber=${pageNumber};
        	var search='${search}';
        	var vouch = '';  //凭证号传值
</script>
<script type="text/javascript" src="<%=basePath%>js/ea/mysl/administrative/SealRegistration.js"></script>
</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">请选择</th>
					<th width="150" align="center">单据编号</th>
					<th width="170" align="center">公司</th>
					<th width="80" align="center">部门</th>
					<th width="110" align="center">制单人名称</th>
					<th width="110" align="center">制单时间</th>
					<th width="110" align="center">工程名称</th>
					<th width="110" align="center">经办人</th>
					<th width="110" align="center">盖章类型</th>
					<th width="110" align="center">资料类型</th>
					<th width="110" align="center">数量/份</th>
					<th width="110" align="center">办理情况</th>
					<th width="110" align="center">接收人</th>
					<th width="110" align="center">接收数量</th>
					<th width="110" align="center">接收时间</th>
					<th width="110" align="center">领导人</th>
					<th width="110" align="center">联系电话</th>
					<th width="110" align="center">状态</th>
					<th width="110" align="center">备注</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${id}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${id}" /></td>
						<td><span id="serialnumber">${serialnumber}</span></td>
						<td><span id="companyname">${companyname}</span></td>
						<td><span id="organizationname">${organizationname}</span></td>
						<td><span id="staffname">${staffname}</span></td>
						<td><span id="addtime">${fn:substring(addtime,0, 10)}</span></td>
						
						<td><span id="projectname">${projectname}</span></td>
						<td><span id="agentpersonname">${agentpersonname}</span></td>
						<td><span id="sealtype">${sealtype}</span></td>
						<td><span id="datatype">${datatype}</span></td>
						
						<td><span id="quantity">${quantity}</span></td>
						<td><span id="dealsituation">${dealsituation}</span></td>
						<td><span id="recipientpersonname">${recipientpersonname}</span></td>
						<td><span id="recipientquantity">${recipientquantity}</span></td>
						<td><span id="recipienttime">${recipienttime}</span></td>
						<td><span id="leadersperson">${leadersperson}</span></td>
						<td><span id="phone">${phone}</span></td>
						
						
						<td><span><s:if test="status=='01'">未审核</s:if>
								  <s:elseif test="status=='02'">已审核</s:elseif>
								  <s:elseif test="status=='03'">驳回</s:elseif>
								  <s:elseif test="status=='04'">办理中</s:elseif>
								  <s:elseif test="status=='05'">已办理</s:elseif>
								  <s:else>草稿</s:else>	
						</span></td>
						<td><span id="note">${note}</span>
						<span id="id" style="display:none;">${id}</span>
						<span id="key" style="display:none;">${key}</span>
						<span id="proposerid" style="display:none;">${proposerid}</span>
						<span id="agentpersonid" style="display:none;">${agentpersonid}</span>
						<span id="recipientpersonid" style="display:none;">${recipientpersonid}</span>
						<span id="companyid" style="display:none;">${companyid}</span>
						<span id="organizationid" style="display:none;">${organizationid}</span>
						<span id="staffid" style="display:none;">${staffid}</span>
						<span id="status" style="display:none;">${status}</span>
						</td>
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/sealregistration/ea_getListBySealRegistration.jspa?pageNumber=${pageNumber}&search=${search}">
			</c:param>
		</c:import>
	</div>
	<div class="contentbannb jqmWindow windowJqm" style="top: 5%"
		id="jqModel">
		<form name="SealRegistration" id="SealRegistration" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display:none" />
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						盖章申请表
						<div class="close"></div>
					</div>
				</div>
				<table width="870 " height="46" border="0" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top:5px; margin-bottom:5px;">
					<tr>
						<td align="right"><span class="xx">*</span>公司：</td>
						<td align="left"><input name="dtMysealregistration.companyname" id="companyname"
							readonly="readonly" value="<%=c.getCompanyName()%>" /> <input
							type="hidden" name="dtMysealregistration.companyid" id="companyid" readonly="readonly"
							value="<%=c.getCompanyID()%>" /></td>
						<td align="right"><span class="xx">*</span>部门：</td>
						<td align="left"><input name="dtMysealregistration.organizationname"
							id="organizationname" readonly="readonly"
							value="${department[1]}" /> <input type="hidden"
							name="dtMysealregistration.organizationid" id="organizationid" readonly="readonly"
							value="${department[0]}" /></td>
						<td align="right"><span class="xx">*</span>责任人：</td>
						<td align="left"><input name="dtMysealregistration.staffname" id="staffname"
							readonly="readonly" value="${staff.staffName}" /> <input
							type="hidden" id="staffid" name="dtMysealregistration.staffid" readonly="readonly"
							value="${staff.staffID}" /></td>		
					</tr>
					<tr>
						<td align="right"><span class="xx">*</span>单据编号：</td>
						<td align="left"><input name="dtMysealregistration.serialnumber" id="serialnumber"
							readonly="readonly" /></td>
						<td align="right"><span class="xx">*</span>申请人：</td>
						<td align="left" colspan="3"><input name="dtMysealregistration.proposername" id="proposername"
							value="${staff.staffName}" /> <input type="hidden"
							id="proposerid" name="dtMysealregistration.proposerid" readonly="readonly"
							value="${staff.staffID}" /></td>
					</tr>
				</table>
				<table width="870" height="244" border="0" align="center"
					cellpadding="0" cellspacing="0" class="table"
					style="background:#FFFFFF;">

					<tr>
						<td align="right" width="80" ><span class="xx">*</span>工程名称：</td>
						<td align="left" ><input name="dtMysealregistration.projectname"
							id="projectname"  size="10" class="put3"/></td>
						<td align="right" width="80"><span class="xx">*</span>经办人：</td>
						<td align="left"><input name="dtMysealregistration.agentpersonname"
							id="agentpersonname"  size="12" class="put3" readonly="readonly"/><span><a href="#" class="agent">选择</a></span>
							<input type="hidden" name="dtMysealregistration.agentpersonid" id="agentpersonid" readonly="readonly"/>
						</td>
						<td align="right" width="80"><span class="xx">*</span>接收人：</td>
						<td align="left"><input name="dtMysealregistration.recipientpersonname"
							id="recipientpersonname" size="12" class="put3" readonly="readonly"/><span><a href="#" class="recipient">选择</a></span>
						<input type="hidden" name="dtMysealregistration.recipientpersonid" id="recipientpersonid" readonly="readonly"/>	
						</td>
					</tr>
					<tr>
						<td align="right" ><span class="xx">*</span>盖章类型：</td>
						<td align="left" ><input name="dtMysealregistration.sealtype"
							id="sealtype"  size="10" class="put3"/></td>
						<td align="right" ><span class="xx">*</span>资料类型：</td>
						<td align="left" ><input name="dtMysealregistration.datatype"
							id="datatype"  size="10" class="put3"/></td>
						<td align="right" ><span class="xx">*</span>数量/份：</td>
						<td align="left" ><input name="dtMysealregistration.quantity"
							id="quantity"  size="10" class="put3"/></td>		
					</tr>
					<tr>
						<td align="right"><span class="xx">*</span>办理情况：</td>
						<td align="left" ><input name="dtMysealregistration.dealsituation"
							id="dealsituation"  size="10" class="put3"/></td>
						<td align="right"><span class="xx">*</span>接收数量：</td>
						<td align="left" ><input name="dtMysealregistration.recipientquantity"
							id="recipientquantity"  size="10" class="put3"/></td>
						<td align="right"><span class="xx">*</span>接收时间：</td>
						<td align="left" ><input name="dtMysealregistration.recipienttime" onfocus="date()"
							id="recipienttime"  size="10" class="put3"/></td>		
					</tr>
					<tr>
						<td align="right">领导人：</td>
						<td align="left" ><input name="dtMysealregistration.leadersperson"
							id="leadersperson"  size="10" /></td>
						<td align="right">联系电话：</td>
						<td align="left" ><input name="dtMysealregistration.phone"
							id="phone"  size="10" /></td>
						<td align="right"><span class="xx">*</span>制单时间：</td>
						<td align="left"><input name="dtMysealregistration.addtime"
							id="addtime" readonly="readonly" value="自动获得" size="10" /></td>		
					</tr>
					<tr>
						<td align="right">备注：</td>
						<td colspan="5"><textarea
								name="dtMysealregistration.note" cols="93" rows="5"
								class="ckTextLength" maxlength="250"
								id="note" style="margin-left:2px;"></textarea></td>
					</tr>
				</table>
				<table width="870" height="40" border="0" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top:5px; margin-bottom:5px;">
					<tr>
						<td colspan="10" align="center"><input type="hidden"
							name="dtMysealregistration.id" id="id" /> <input type="hidden"
							name="dtMysealregistration.key" id="key" /> <input type="button"
							class="input-button JQuerySubmitPrint xianshi"
							style="cursor:pointer;width:80px; display:none;" value="打印预览" />
							<input type="button" class="input-button JQuerySave"
							style="cursor:pointer;width:80px;" value="保存草稿" title="0"/>
							<input type="button" class="input-button JQuerySubmit"
							style="cursor:pointer;width:80px;" value="提交审核" title="1"/> <input
							type="button" class="input-button JQueryreturn"
							style="cursor:pointer;width:80px;" value="返回" />
							<input type="hidden" id="buttonType" name="buttonType"/>
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>
	</div>
	<!--选择审核人员窗口 -->
	<div class="jqmWindow"
			style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;"
			id="jqModelSend">
			<div class="drag">
				报送审批 
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right">审核人公司：</td>
					<td align="left"><select id="receiverCompanyID"
						name="document.receiverCompanyID" onchange="changeCompany(this,'jqModelSend');"
						style="width:200px;">
							<option value="">请选择审核人公司</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核人部门：</td>
					<td align="left"><select id="receiverDeptID"
						name="document.receiverDeptID" onchange="changeDept(this,'jqModelSend');"
						style="width: 200px;">
							<option value="">请选择审核人部门</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核人姓名：</td>
					<td align="left"><select name="document.receiverID"
						id='receiverID' style="width: 200px;">
							<option value="">请选择审核人</option>
					</select></td>
				</tr>
			</table>

			<div align="center" style="margin-top: 25px;">
				<input type="button" class="input-button" id="submitResult" value=" 提交 " /> 
				<input type="button" class="input-button" id="submitColsed" value=" 关闭 " />
			</div>
			</center>
		</div>
	
	<!--搜索窗口 -->
	<div class="jqmWindow" style="width: 400px;right: 25%;;top: 10%"
		id="jqModelSearch">
		<form name="postSearchForm" id="postSearchForm" method="post">
			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				查询信息
				<div class="close"></div>
			</div>
			<table width="396" id="cataffSearchTable">
				<tr>
					<td align="right">单据编号：</td>
					<td><input name="dtMysealregistration.serialnumber" /></td>
				</tr>
				<tr>
					<td align="right">制单时间：</td>
					<td><input name="dtMysealregistration.addtime"  onfocus="date()"/></td>
				</tr>
				<tr>
					<td align="right">单据状态：</td>
					<td><select name="dtMysealregistration.status">
							<option value="">全部</option>
							<option value="01">未审核</option>
							<option value="02">已审核</option>
							<option value="03">驳回</option>
							<option value="04">办理中</option>
							<option value="05">已办理</option>
							<option value="00">草稿</option>
					</select></td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch" value=" 查询 " /><input
					name="search" type="hidden" value="search" />
			</div>
		</form>
	</div>
	
	<!--选择人员窗口 -->
	<div class="jqmWindow"
			style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;"
			id="jqModelSelect">
			<div class="drag">
				选择人员
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right">公司：</td>
					<td align="left"><select id="receiverCompanyID"
						name="document.receiverCompanyID" onchange="changeCompany(this,'jqModelSelect');"
						style="width:200px;">
							<option value="">请选择公司</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">部门：</td>
					<td align="left"><select id="receiverDeptID"
						name="document.receiverDeptID" onchange="changeDept(this,'jqModelSelect');"
						style="width: 200px;">
							<option value="">请选择部门</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">姓名：</td>
					<td align="left"><select name="document.receiverID"
						id='receiverID' style="width: 200px;">
							<option value="">请选择人</option>
					</select></td>
				</tr>
			</table>

			<div align="center" style="margin-top: 25px;">
				<input type="button" class="input-button" id="SelectResult" value=" 确定 " /> 
				<input type="button" class="input-button" id="SelectColsed" value=" 取消 " />
			</div>
			</center>
		</div>
	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>