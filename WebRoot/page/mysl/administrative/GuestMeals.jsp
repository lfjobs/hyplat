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
<title>客餐申请表</title>
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
<script type="text/javascript" src="<%=basePath%>js/ea/mysl/administrative/GuestMeals.js"></script>
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
					<th width="110" align="center">接待人数</th>
					<th width="110" align="center">接待对象及事由</th>
					<th width="110" align="center">作陪人员</th>
					<th width="110" align="center">办理结果</th>
					<th width="110" align="center">责任人名称</th>
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
						<td><span id="recequantity">${recequantity}</span></td>
						<td><span id="recereason">${recereason}</span></td>
						<td><span id="compersonnel">${compersonnel}</span></td>
						<td><span id="results">${results}</span></td>
						<td><span id="proposername">${proposername}</span></td>
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
				value="ea/guestmeals/ea_getListByGuestMeals.jspa?pageNumber=${pageNumber}&search=${search}">
			</c:param>
		</c:import>
	</div>
	<div class="contentbannb jqmWindow windowJqm" style="top: 5%"
		id="jqModel">
		<form name="GuestMeals" id="GuestMeals" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display:none" />
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						客餐申请表
						<div class="close"></div>
					</div>
				</div>
				<table width="870 " height="46" border="0" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top:5px; margin-bottom:5px;">
					<tr>
						<td align="right"><span class="xx">*</span>公司：</td>
						<td align="left"><input name="dtMyguestmeals.companyname" id="companyname"
							readonly="readonly" value="<%=c.getCompanyName()%>" /> <input
							type="hidden" name="dtMyguestmeals.companyid" id="companyid" readonly="readonly"
							value="<%=c.getCompanyID()%>" /></td>
						<td align="right"><span class="xx">*</span>部门：</td>
						<td align="left"><input name="dtMyguestmeals.organizationname"
							id="organizationname" readonly="readonly"
							value="${department[1]}" /> <input type="hidden"
							name="dtMyguestmeals.organizationid" id="organizationid" readonly="readonly"
							value="${department[0]}" /></td>
						<td align="right"><span class="xx">*</span>责任人：</td>
						<td align="left"><input name="dtMyguestmeals.staffname" id="staffname"
							readonly="readonly" value="${staff.staffName}" /> <input
							type="hidden" id="staffid" name="dtMyguestmeals.staffid" readonly="readonly"
							value="${staff.staffID}" /></td>		
					</tr>
					<tr>
						<td align="right"><span class="xx">*</span>单据编号：</td>
						<td align="left"><input name="dtMyguestmeals.serialnumber" id="serialnumber"
							readonly="readonly" /></td>
						<td align="right"><span class="xx">*</span>申请人：</td>
						<td align="left" colspan="3"><input name="dtMyguestmeals.proposername" id="proposername"
							value="${staff.staffName}" /> <input type="hidden"
							id="proposerid" name="dtMyguestmeals.proposerid" readonly="readonly"
							value="${staff.staffID}" /></td>
					</tr>
				</table>
				<table width="870" height="244" border="0" align="center"
					cellpadding="0" cellspacing="0" class="table"
					style="background:#FFFFFF;">

					<tr>
						<td align="right"><span class="xx">*</span>接待人数：</td>
						<td align="left" ><input name="dtMyguestmeals.recequantity"
							id="recequantity"  size="10" class="put3"/></td>
						<td align="right"><span class="xx">*</span>处理结果：</td>
						<td align="left" ><input name="dtMyguestmeals.results"
							id="results"  size="10" class="put3"/></td>	
						<td align="right"><span class="xx">*</span>制单时间：</td>
						<td align="left"><input name="dtMyguestmeals.addtime"
							id="addtime" readonly="readonly" value="自动获得" size="10" /></td>
					</tr>
					<tr>
						<td align="right"><span class="xx">*</span>作陪人员：</td>
						<td align="left" colspan="5"><input name="dtMyguestmeals.compersonnel"
							id="compersonnel"  size="66" class="put3"/></td>
					</tr>
					<tr>
					<td align="right"><span class="xx">*</span>接待对象及事由：</td>
						<td align="left" colspan="5"><input name="dtMyguestmeals.recereason"
							id="recereason"  size="66" class="put3"/></td>
					</tr>		
					<tr>
						<td align="right"><span class="xx">*</span>备注：</td>
						<td colspan="5"><textarea
								name="dtMyguestmeals.note" cols="93" rows="5"
								class="put3 ckTextLength" maxlength="250"
								id="note" style="margin-left:2px;"></textarea></td>
					</tr>
				</table>
				<table width="870" height="40" border="0" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top:5px; margin-bottom:5px;">
					<tr>
						<td colspan="10" align="center"><input type="hidden"
							name="dtMyguestmeals.id" id="id" /> <input type="hidden"
							name="dtMyguestmeals.key" id="key" /> <input type="button"
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
						name="document.receiverCompanyID" onchange="changeCompany(this);"
						style="width:200px;">
							<option value="">请选择审核人公司</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核人部门：</td>
					<td align="left"><select id="receiverDeptID"
						name="document.receiverDeptID" onchange="changeDept(this);"
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
					<td><input name="dtMyguestmeals.serialnumber" /></td>
				</tr>
				<tr>
					<td align="right">制单时间：</td>
					<td><input name="dtMyguestmeals.addtime"  onfocus="date()"/></td>
				</tr>
				<tr>
					<td align="right">单据状态：</td>
					<td><select name="dtMyguestmeals.status">
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
	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>