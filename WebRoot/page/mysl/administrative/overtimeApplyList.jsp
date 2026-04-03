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
<title>加班申请</title>
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
	var pageNumber = "${pageNumber}";
	var search = '${search}';
	var vouch = ''; //凭证号传值
	var times = '0';
	 var type="${type}";
</script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/mysl/administrative/overtimeApply.js"></script>
</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				      <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                        <th width="150" align="center">
                            凭证号
                        </th>
                        <th width="170" align="center">
                            公司
                        </th>
                        <th width="80" align="center">
                            部门
                        </th>
                       
                        <th width="110" align="center">
                             责任人
                        </th>
                       
                        <th width="110" align="center">
                             申请日期   
                        </th>
                       
                        <th width="110" align="center">
                             岗位
                        </th>
                        <th width="110" align="center">
                             加班类别
                        </th>
                        <th width="110" align="center">
                             起时间
                        </th>
                        <th width="110" align="center">
                             止时间
                        </th>
                        <th width="110" align="center">
                             加班天数
                        </th>
                        <th width="110" align="center">
                             加班小时
                        </th>
                       
                        <th width="110" align="center">
                             加班事由
                        </th>
                        <th width="110" align="center">
                             加班内容
                        </th>
                         <th width="110" align="center">
                             制单时间
                        </th>
                        <th width="110" align="center">
                             单据状态
                        </th>
                        <th width="110" align="center">
                             附件
                        </th>
                    </tr>
                
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${id}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${id}" />
						</td>
						<td><span id="serialnumber">${serialnumber}</span>
						</td>
						<td><span id="companyname">${companyname}</span>
						</td>
						<td><span id="organizationname">${organizationname}</span>
						</td>
						<td><span id="staffname">${staffname}</span>
						</td>
						<td><span id="addtime">${fn:substring(addtime,0, 10)}</span>
						</td>
						<td><span id="overTimePostName">${overTimePostName}</span>
						</td>
						<td><span id="overTimeSort">${overTimeSort}</span>
						</td>
						<td><span id="overTimeStartDate">${overTimeStartDate}</span></td>
						<td><span id="overTimeEndDate">${overTimeEndDate}</span></td>
						<td><span id="overTimeDays">${overTimeDays}</span></td>
						<td><span id="overTimeHour">${overTimeHour}</span></td>
					
						<td><span id="overTimeReason">${overTimeReason}</span></td>
						<td><span id="overTimeContent">${overTimeContent}</span></td>
						<td><span id="addtime">${fn:substring(addtime,0,19)}</span></td>
						<td><span><s:if test="status=='01'">未审核</s:if> <s:elseif
									test="status=='02'">已审核</s:elseif> <s:elseif
									test="status=='03'">驳回</s:elseif> <s:elseif test="status=='04'">办理中</s:elseif>
								<s:elseif test="status=='05'">已办理</s:elseif> <s:else>草稿</s:else>
						</span>
						<span id="status" style="display:none;">${status}</span>
						<span id="id" style="display:none;">${id}</span>
						<span id="key" style="display:none;">${key}</span>
						</td>
						<td >
                             	<span id="attachPath" style="display:none">${attachPath}</span>
                            	<s:if test="attachPath==null">无</s:if><s:else>
                            	
                            	<span id="look" onclick="lookImage('${attachPath}');"><a href="#">查看</a></span>
                             	<span id="load" ><a href='<%=basePath%>ea/overtime/ea_downFile.jspa?downLoadPath=${attachPath}'>下载</a></span>
                            	</s:else>
                            	
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
				value="ea/overtime/ea_getListByOvertime.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}">
			</c:param>
		</c:import>
	</div>

	<div class="contentbannb jqmWindow windowJqm" style="top: 5%"
		id="jqModel">
		<form name="overtimeForm" id="overtimeForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display:none" />
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						员工加班申请单
						<div class="close"></div>
					</div>
				</div>
				<table width="870 " height="46" border="0" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top:5px; margin-bottom:5px;">
					<tr>
						<td width="80" align="right">公司：</td>
						<td width="220"><input name="dtMyovertime.companyname"
							class="input yincang" id="companyName" readonly="readonly"
							value="<%=c.getCompanyName()%>" size="30" /> <span class="xianshi"
							id="companyName1"></span>
							<input type="hidden" name="dtMyovertime.companyid"
							class="input yincang" id="companyid" readonly="readonly"
							value="<%=c.getCompanyID()%>"size="30" />
							</td>
						<td align="right"><span class="xx">*</span>部门：</td>
						<td align="left"><input
							name="dtMyovertime.organizationname"
							id="organizationname" readonly="readonly"
							value="${department[1]}" /> <input type="hidden"
							name="dtMyovertime.organizationid" id="organizationid"
							readonly="readonly" value="${department[0]}" />
						</td>
						<td align="left">附件：<input name="dtMyovertime.attachPath"
							class="fileNum" type="hidden" id="attachPath" size="15" value="${attachPath}" /> 
							<input name="photo" type="file" id="accessoryName" class="input yincang"
							size="20" contentEditable="false" /> 
						</span>
					</tr>
					<tr>
						<td align="right">凭证号：</td>
						<td><input readonly="readonly"
							name="dtMyovertime.serialnumber" id="serialnumber" class="yincang"
							size="25" /> <span id="voucherNum" class="xianshi"></span></td>
						<td align="right"><span class="xx">*</span>责任人：</td>
						<td align="left"><input
							name="dtMyovertime.staffname" id="staffname"
							readonly="readonly" value="${staff.staffName}" /> <input
							type="hidden" id="staffid" name="dtMyovertime.staffid"
							readonly="readonly" value="${staff.staffID}" />
						</td>
						<td align="left">岗位： <input
							name="dtMyovertime.overTimePostName" class="yincang"
							id="overTimePostName" readonly="readonly" value="${department[2]}" size="12" /> <span
							class="xianshi" id="overTimePostName1"></span></td>

					</tr>
				</table>
				<table width="870" height="244" border="0" align="center"
					cellpadding="0" cellspacing="0" class="table"
					style="background:#FFFFFF;">
					<tr>
						<td height="30" width="106" align="right"><span class="xx">*</span>加班类别：</td>
						<td width="760" id="overTimeSorts"><input type="radio"
							name="dtMyovertime.overTimeSort" class="yincang"
							value="工作日加班" checked/> <font class="sort yincang">工作日加班</font> <input
							type="radio" name="dtMyovertime.overTimeSort"
							class="yincang" value="休息日加班" /> <font class="sort yincang">休息日加班</font>
							<input type="radio" name="dtMyovertime.overTimeSort"
							class="yincang" value="法定假日加班" /> <font class="sort yincang">法定假日加班</font>
							<span id="overTimeSort1" class="xianshi sty"></span></td>
					</tr>
					<tr>
						<td height="30" align="right"><span class="xx">*</span>加班时间：</td>
						<td class="errortime"><input
							name="dtMyovertime.overTimeStartDate"
							onfocus="daytime(this);" id="overTimeStartDate" 
							class="input yincang put3 overTimeStartDate" size="18" style="margin-left:2px;" />  至 <input
							name="dtMyovertime.overTimeEndDate"
							onfocus="daytime(this);" id="overTimeEndDate"
							class="input yincang put3 overTimeEndDate" size="18" style="margin-left:2px;" />  共： <input
							name="dtMyovertime.overTimeDays" class="input yincang overTimeDays"
							id="overTimeDays"   disabled="disabled" size="3" />  天 <input
							name="dtMyovertime.overTimeHour" class="input yincang overTimeHour"
							id="overTimeHour" disabled="disabled" size="3" /> 小时 <font
							class="dis"></td>
					</tr>
					<tr>
						<td height="40" align="right"><span class="xx">*</span>加班事由：</td>
						<td><textarea name="dtMyovertime.overTimeReason"
								cols="93" rows="5" class="input ckTextLength put3"
								maxlength="250" id="overTimeReason" style="margin-left:2px;"></textarea>
							
						</td>
					</tr>
					<tr>
						<td height="40" align="right"><span class="xx">*</span>加班内容：</td>
						<td><textarea name="dtMyovertime.overTimeContent"
								cols="93" rows="5" class="input put3 ckTextLength"
								maxlength="250" id="overTimeContent" style="margin-left:2px;"></textarea>
					
						</td>
					</tr>
				</table>
				<table width="870" height="40" border="0" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top:5px; margin-bottom:5px;">
					<tr>
						<td colspan="10" align="center"><input type="hidden"
							name="dtMyovertime.id" id="id" /> <input type="hidden"
							name="dtMyovertime.key" id="key" /> <input type="button"
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
		<div class="drag">报送审批</div>
		<center>
		<table width="100%" id="SearchTable2" cellspacing="20"
			cellpadding="20">
			<tr>
				<td align="right">审核人公司：</td>
				<td align="left"><select id="receiverCompanyID"
					name="document.receiverCompanyID" onchange="changeCompany(this);"
					style="width:200px;">
						<option value="">请选择审核人公司</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">审核人部门：</td>
				<td align="left"><select id="receiverDeptID"
					name="document.receiverDeptID" onchange="changeDept(this);"
					style="width: 200px;">
						<option value="">请选择审核人部门</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">审核人姓名：</td>
				<td align="left"><select name="document.receiverID"
					id='receiverID' style="width: 200px;">
						<option value="">请选择审核人</option>
				</select>
				</td>
			</tr>
		</table>

		<div align="center" style="margin-top: 25px;">
			<input type="button" class="input-button" id="submitResult"
				value=" 提交 " /> <input type="button" class="input-button"
				id="submitColsed" value=" 关闭 " />
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
					<td align="right">凭证号：</td>
					<td><input name="dtMyovertime.serialnumber" />
					</td>
				</tr>
				<tr>
					<td align="right">制单时间：</td>
					<td><input name="dtMyovertime.addtime"
						onfocus="date()" />
					</td>
				</tr>
				<tr>
					<td align="right">单据状态：</td>
					<td><select name="dtMyovertime.status">
							<option value="">全部</option>
							<option value="01">未审核</option>
							<option value="02">已审核</option>
							<option value="03">驳回</option>
							<option value="04">办理中</option>
							<option value="05">已办理</option>
							<option value="00">草稿</option>
					</select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch" value=" 查询 " /><input
					name="search" type="hidden" value="search" />
					<input name="type" type="hidden" value="${type}" />
			</div>
		</form>
	</div>

	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>