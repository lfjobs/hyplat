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
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/finishproduct/resultInventory_invPrint.js"></script>
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
	var str="";
</script>
</head>
<body>
 <form  name="form" id="form" method="post" enctype="multipartform-data">
 	<s:token></s:token>
 	<input type="submit" name="submit" id="submit" style="display:none"/>

<center>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
			<div class="content" style="border: 1px solid;width: 900px;height: 520px;">
				<div class="contentbannb">
					<div class="drag">
						员工加班申请单
						<div class="close"></div>
					</div>
				</div>
				<input  type="hidden" name="cashierBillsID" value="${cashierBillsID}" id="cashierBillsID">
				<input type="hidden" name="ppID" value="${ppID}">
				<div style="width:100%;height:80px;position: relative;top: 20px;">
					<table>
						<tr style="height: 40px;">
							<td style="text-align: right;">公司名称：
								<input type="hidden" name="fieldConStors[0].fieldPpID" value="${map['公司名称'].fieldPpID}" >
								<input type="hidden" name="fieldConStors[0].textID" value="${map['公司名称'].textID}" >
							</td>
							<td>
								<input type="text" name="fieldConStors[0].content" style="width: 180px;"
									 value="${map['公司名称'].content}" class="inputbottom">
							</td>
							<td style="width: 50px;"></td>
							<td  style="text-align: right;">部&nbsp;&nbsp;&nbsp;门：
								<input type="hidden" name="fieldConStors[1].fieldPpID" value="${map['部门名称'].fieldPpID}" >
								<input type="hidden" name="fieldConStors[1].textID" value="${map['部门名称'].textID}" >
							</td>
							<td>
								 <input type="text" name="fieldConStors[1].content" style="width: 180px;"
								 value="${map['部门名称'].content}" class="inputbottom">
							</td>
							<td style="width: 50px;"></td>
							<td  style="text-align: right;">附&nbsp;&nbsp;件：
								<input type="hidden" name="fieldConStors[2].fieldPpID" value="${map['附件'].fieldPpID}" >
							</td>
							<td>
								<input name="fieldConStors[2].content" type="file"   style="width: 180px;"
									class="inputbottom " contentEditable="false" value="${map['附件'].content}"  />
							</td>
						</tr>
						<tr  style="height: 40px;">
							<td  style="text-align: right;">凭&nbsp;证&nbsp;号：
								<input type="hidden" name="fieldConStors[3].fieldPpID" value="${map['凭证号'].fieldPpID}" >
							</td>
							<td>
								<input type="text" name="fieldConStors[3].content" style="width: 180px;"
									 value="${map['凭证号'].content}" class="inputbottom">
							</td>
							<td style="width: 50px;"></td>
							<td  style="text-align: right;">责任人：
								<input type="hidden" name="fieldConStors[4].fieldPpID" value="${map['责任人'].fieldPpID}" >
								<input type="hidden" name="fieldConStors[4].textID" value="${map['责任人'].textID}" >
							</td>
							<td>
								<input type="text" name="fieldConStors[4].content" style="width: 180px;"
									 value="${map['责任人'].content}" class="inputbottom">
							</td>
							<td style="width: 50px;"></td>
							<td  style="text-align: right;">岗位：
								<input type="hidden" name="fieldConStors[5].fieldPpID" value="${map['岗位'].fieldPpID}" >
							</td>
							<td>
								<input name="fieldConStors[5].content" type="text"   style="width: 180px;"  
									class="inputbottom " value="${map['岗位'].content}" />
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
								<input type="hidden" class="ppID" name="fieldConStors[6].fieldPpID" value="${map['加班类别'].fieldPpID}"  ppid="${map['加班类别'].textID}">	
							</td>
							<td width="760" id="overTimeSorts"  class="radioButtonTw">
								<span id="overTimeSort1" class="xianshi sty"></span></td>
						</tr>
						<tr>
							<td height="30" align="right">
								<span class="xx">*</span>加班时间：
								<input type="hidden" name="fieldConStors[7].fieldPpID" value="${map['加班时间'].fieldPpID}" >	
							</td>
							<td class="errortime startAndEndTime">
								<input type="hidden" class="dateContent" value="${map['加班时间'].content}">
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
								<input type="hidden" name="fieldConStors[8].fieldPpID" value="${map['加班事由'].fieldPpID}" >	
							</td>
							<td><textarea name="fieldConStors[8].content" cols="93" rows="5" class="input ckTextLength put3"
										maxlength="250" id="overTimeReason" style="margin-left:2px;">${map['加班事由'].content}</textarea>
							</td>
						</tr>
						<tr>
							<td height="40" align="right">
								<span class="xx">*</span>加班内容：
								<input type="hidden" name="fieldConStors[9].fieldPpID" value="${map['加班内容'].fieldPpID}" >	
							</td>
							<td><textarea name="fieldConStors[9].content" cols="93" rows="5" class="input put3 ckTextLength"
									maxlength="250" id="overTimeContent" style="margin-left:2px;">${map['加班内容'].content}</textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
   	<div style="position:relative;top:10px;" id="examine">
   		 <table width="99%" border="0" style="margin-left: 15px;" align="center" cellpadding="0" cellspacing="0">
			   <tr><td colspan="2">&nbsp;</td></tr>
			   <tr>
			   <td align="left" width="50px">备注：</td>
			   <td align="left" colspan="9">
			   <input type="text" id="remark" class="inputbottom" style="width:80%;" readonly="readonly" value="${str}"/>
			   </td>
			   </tr>
			</table>
			<table width="99%" border="0" cellpadding="0" cellspacing="0" id="audittbl">
			<tr><td>
			<input type="hidden" id="staffauditname" 
			value="${ManStaffName}">
			<input type="hidden" id="staffauditcode" 
			value="${ManStaffCode}">
			<input type="hidden" id="staffauditid" 
			value="${ManStaffId}">
			</td></tr>
			<tr class="aduittr">
				<td height="25" align="right">公司经理：</td>
				<td><input type="text" readonly="readonly" class="inputbottom gsjl" value='${billcheckmap["gsjl"]}    '/>
				<c:if test='${billcheckmap["gsjl"]==null||billcheckmap["gsjl"]==""}'>
					<input type="button" class="btncon verify" id="gsjl" />
				</c:if >
				<c:if test='${billcheckmap["gsjl"]!=null&&billcheckmap["gsjl"]!=""}'>
					${billcheckmap["gsjlzt"]==02?"通过":"驳回"}
				</c:if>
				<td align="right">部门主管：</td>
				<td><input type="text" readonly="readonly" class="inputbottom bmzg" value='${billcheckmap["bmzg"] }    '/>
				
				<c:if test='${billcheckmap["bmzg"]==null||billcheckmap["bmzg"]==""}'>		
					<input type="button" class="btncon verify" id="bmzg"/>
				</c:if>
				<c:if test='${billcheckmap["bmzg"]!=null&&billcheckmap["bmzg"]!=""}'>		
					${billcheckmap["bmzgzt"]==02?"通过":"驳回"}
				</c:if>
				</td>
				<td align="right">人事处：</td>
				<td><input type="text" readonly="readonly" class="inputbottom rsc" value="${billcheckmap['rsc'] }"/>
				<c:if test='${billcheckmap["rsc"]==null||billcheckmap["rsc"]==""}'>
					<input type="button" class="btncon verify" id="rsc" />
				</c:if>
				<c:if test='${billcheckmap["rsc"]!=null&&billcheckmap["rsc"]!=""}'>
					${billcheckmap['rsczt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="right">财务审核：</td>
				<td><input type="text" readonly="readonly" class="inputbottom cwsh" value="${billcheckmap['cwsh'] }"/>
				<c:if test='${billcheckmap["cwsh"]==null||billcheckmap["cwsh"]==""}'>
					<input type="button" class="btncon verify" id="cwsh" />
				</c:if>
				<c:if test='${billcheckmap["cwsh"]!=null&&billcheckmap["cwsh"]!=""}'>
					${billcheckmap['cwshzt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="center">收款人确认：</td>
				<td><input type="text" readonly="readonly" class="inputbottom skr" value="${billcheckmap['skr'] }"/>
				<c:if test='${billcheckmap["skr"]==null||billcheckmap["skr"]==""}'>
					<input type="button" class="btncon verify " id="skr" />
				</c:if>
				<c:if test='${billcheckmap["skr"]!=null&&billcheckmap["skr"]!=""}'>
					${billcheckmap['skrzt']==02?'通过':'驳回'}
				</c:if>
				</td>
			</tr>
			<tr class="aduittr">
				<td height="25" align="right">总部总经理：</td>
				<td><input type="text" readonly="readonly" class="inputbottom zjl" value='${billcheckmap["zjl"] }'/>
				<c:if test='${billcheckmap["zjl"]==null||billcheckmap["zjl"]==""}'>
					<input type="button" class="btncon verify" id="zjl" />
				</c:if>
				<c:if test='${billcheckmap["zjl"]!=null&&billcheckmap["zjl"]!=""}'>
					 ${billcheckmap["zjlzt"]==02?"通过":"驳回"}
				</c:if>
				</td>
				<td align="right">总部部门主管：</td>
				<td><input type="text" readonly="readonly" class="inputbottom zg" value="${billcheckmap['zg'] }"/>
				<c:if test='${billcheckmap["zg"]==null||billcheckmap["zg"]==""}'>
					<input type="button" class="btncon verify" id="zg" />
				</c:if>
				<c:if test='${billcheckmap["zg"]!=null&&billcheckmap["zg"]!=""}'>
					${billcheckmap['zgzt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="right">总部人事处：</td>
				<td><input type="text" readonly="readonly" class="inputbottom zbrsc" value="${billcheckmap['zbrsc'] }"/>
				<c:if test='${billcheckmap["zbrsc"]==null||billcheckmap["zbrsc"]==""}'>
					<input type="button" class="btncon verify" id="zbrsc" />
				</c:if>
				<c:if test='${billcheckmap["zbrsc"]!=null&&billcheckmap["zbrsc"]!=""}'>
					${billcheckmap['zbrsczt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="right">总财务审核：</td>
				<td><input type="text" readonly="readonly" class="inputbottom zbcw" value="${billcheckmap['zbcw'] }"/>
				<c:if test='${billcheckmap["zbcw"]==null||billcheckmap["zbcw"]==""}'>
					<input type="button" class="btncon verify" id="zbcw" />
				</c:if>
				<c:if test='${billcheckmap["zbcw"]!=null&&billcheckmap["zbcw"]!=""}'>
					${billcheckmap['zbcwzt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="center">交款人确认：</td>
				<td><input type="text" readonly="readonly" class="inputbottom jkr" value="${billcheckmap['jkr'] }"/>
				<c:if test='${billcheckmap["jkr"]==null||billcheckmap["jkr"]==""}'>
					<input type="button" class="btncon verify" id="jkr" />
				</c:if>
				<c:if test='${billcheckmap["jkr"]!=null&&billcheckmap["jkr"]!=""}'>
					 ${billcheckmap['jkrzt']==02?'通过':'驳回'}
				</c:if>
				</td>
			</tr>
		</table>
   	</div>
			</form>
				<div style="width: 350px;height:220px;border: 1px solid #ffffff;background-color: #DBFAF8;
			position: absolute;top:209px;left: 860px;" class="jqmWindow jqmWindowcss2" id="single">
    	<div style="background-color: #C5C1AA;height: 28px;text-align: center;">
    		<font size="3" color="#008B00" style="position: relative;top: 4px;">物品审核</font>
    	</div>
    	<div style="position: relative;top: 10px;height: 120px;">
    		<div style="position: relative;left: -50px;"><span>审核意见：</span>
    			<span style="position: relative;left: 80px;"><input type="radio" name="radio" class="radio" value="yes" checked="checked">通过</span>
    			<span style="position: relative;left: 110px;"><input type="radio" name="radio" class="radio" value="no">驳回</span></div>
    		<div><textarea id="textarea" style="width: 250px;height: 100px;position: relative;top: 2px;"></textarea></div>
    	</div>
    	<div style="position:relative;top:10px;">
    		<input type="button" class="but" value="提交" style="width: 45px;height: 25px;position: relative;top: 18px;left: -30px;">
    		<input type="button" class="but" value="关闭" style="width: 45px;height: 25px;position: relative;top: 18px;left: 30px;">
    	</div>
    </div>
</body>
</html>
