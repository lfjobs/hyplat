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
	Company c = (Company) request.getAttribute("currentcompany");
	CAccount ac = (CAccount) request.getAttribute("account");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>库房管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/finishproduct/resultInventory_add.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
.xx1{
	color:#3300CC;
	margin-right:2px;}
</style>
<script  type="text/javascript">
	var basePath="<%=basePath%>";
	var fiveClear="${fiveClear}";
	var category="${category}";
	var fiveClearName="${fiveClearName}";
</script>
</head>
<body>
 <form  name="form" id="form" method="post" enctype="multipartform-data">
 	<s:token></s:token>
 	<input type="submit" name="submit" id="submit" style="display:none"/>

<center>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
			<div class="content" style="border: 1px solid;width: 900px;height: 600px;">
				<div class="contentbannb">
					<div class="drag">
						员工加班申请单
					</div>
				</div>
				<input type="hidden" name="ppID" value="${ppID}">
				<div style="width:100%;height:80px;position: relative;top: 20px;">
					<table>
						<tr style="height: 40px;">
							<td style="text-align: right;">公司名称：
								<input type="hidden" name="fieldConStors[0].FieldPpID" value="${map['公司名称'].ppID}" >
								<input type="hidden" name="fieldConStors[0].textID" value="<%=c.getCompanyID()%>" >
							</td>
							<td>
								<input type="text" name="fieldConStors[0].content" style="width: 180px;"
									 value="<%=c.getCompanyName()%>" class="inputbottom">
							</td>
							<td style="width: 50px;"></td>
							<td  style="text-align: right;">部&nbsp;&nbsp;&nbsp;门：
								<input type="hidden" name="fieldConStors[1].FieldPpID" value="${map['部门名称'].ppID}" >
								<input type="hidden" name="fieldConStors[1].textID" value="${department[0]}" >
							</td>
							<td>
								 <input type="text" name="fieldConStors[1].content" style="width: 180px;"
								 value="${department[1]}" class="inputbottom">
							</td>
							<td style="width: 50px;"></td>
							<td  style="text-align: right;">附&nbsp;&nbsp;件：
								<input type="hidden" name="fieldConStors[2].FieldPpID" value="${map['附件'].ppID}" >
							</td>
							<td>
								<input name="fieldConStors[2].content" type="file"   style="width: 180px;"
									class="inputbottom " contentEditable="false"  />
							</td>
						</tr>
						<tr  style="height: 40px;">
							<td  style="text-align: right;">凭&nbsp;证&nbsp;号：
								<input type="hidden" name="fieldConStors[3].FieldPpID" value="${map['凭证号'].ppID}" >
							</td>
							<td>
								<input type="text" name="fieldConStors[3].content" style="width: 180px;"
									 value="${serialnumber}" class="inputbottom">
							</td>
							<td style="width: 50px;"></td>
							<td  style="text-align: right;">责任人：
								<input type="hidden" name="fieldConStors[4].FieldPpID" value="${map['责任人'].ppID}" >
								<input type="hidden" name="fieldConStors[4].textID" value="${staff.staffID}" >
							</td>
							<td>
								<input type="text" name="fieldConStors[4].content" style="width: 180px;"
									 value="${staff.staffName}" class="inputbottom">
							</td>
							<td style="width: 50px;"></td>
							<td  style="text-align: right;">岗位：
								<input type="hidden" name="fieldConStors[5].FieldPpID" value="${map['岗位'].ppID}" >
							</td>
							<td>
								<input name="fieldConStors[5].content" type="text"   style="width: 180px;"  
									class="inputbottom " value="${department[2]}" />
							</td>
						</tr>
					</table>
				</div>
				<div style="width:100%;height:380px;position: relative;top: 40px;" class="radioAdmin">
					<table width="870" height="344" border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background:#FFFFFF;">
						<tr class="radioButton">
							<td height="30" width="106" align="right">
								<span class="xx">*</span>加班类别：
								<input type="hidden" class="ppID" name="fieldConStors[6].FieldPpID" value="${map['加班类别'].ppID}" >	
							</td>
							<td width="760" id="overTimeSorts" class="radioButtonTw">
								<span id="overTimeSort1" class="xianshi sty"></span></td>
						</tr>
						<tr>
							<td height="30" align="right">
								<span class="xx">*</span>加班时间：
								<input type="hidden" name="fieldConStors[7].FieldPpID" value="${map['加班时间'].ppID}" >	
							</td>
							<td class="errortime">
								<input onfocus="daytime(this);"  class="input start date overTimeStartDate" size="18" style="margin-left:2px;" />  至 
								<input onfocus="daytime(this);"  class="input end date overTimeEndDate" size="18" style="margin-left:2px;" />  共： 
								<input class="input day overTimeDays"   disabled="disabled" size="3" />  天 
								<input class="input hour overTimeHour" disabled="disabled" size="3" /> 小时
								<input type="hidden" name="fieldConStors[7].content" class="transmission" >	
							</td>
						</tr>
						<tr>
							<td height="40" align="right">
								<span class="xx">*</span>加班事由：
								<input type="hidden" name="fieldConStors[8].FieldPpID" value="${map['加班事由'].ppID}" >	
							</td>
							<td><textarea name="fieldConStors[8].content" cols="93" rows="5" class="input ckTextLength put3"
										maxlength="250" id="overTimeReason" style="margin-left:2px;"></textarea>
							</td>
						</tr>
						<tr>
							<td height="40" align="right">
								<span class="xx">*</span>加班内容：
								<input type="hidden" name="fieldConStors[9].FieldPpID" value="${map['加班内容'].ppID}" >	
							</td>
							<td><textarea name="fieldConStors[9].content" cols="93" rows="5" class="input put3 ckTextLength"
									maxlength="250" id="overTimeContent" style="margin-left:2px;"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div style="width: 100%;height:40px;position: relative;top: 40px;">
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
								 <input type="button" class="input-button JQueryreturn"
								style="cursor:pointer;width:80px;" value="关闭" />
								<input type="hidden" id="buttonType" name="buttonType"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			</form>
</body>
</html>
