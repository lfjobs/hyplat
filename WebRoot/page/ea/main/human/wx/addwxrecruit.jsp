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
<title>微信招聘添加修改管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/human/wx/addwxrecruit.js"></script>
<style type="">
html,body {
	-moz-user-select: none;
	-khtml-user-select: none;
	user-select: none;
}

body {
	margin: 0 auto;
	background-color: #f0f0f0;
	text-align: center;
}
.divsty{
	width:900px;
}
html,body,div,p,ul,li,dl,dt,dd,h1,h2,h3,h4,h5,h6,form,input,select,button,textarea,iframe,th,td
	{
	margin: 0 0;
	padding: 0px 0px;
	font-size: 14px;
	color: #666;
}
table{
	
}
.frame {
	width: 100%;
	border: 1px solid #000000;
	margin-left: 0px;
	margin-top: 20px;
}

table .rleft {
	height:30px;
	text-align: right;
	width:180px;
}
table .modtd{
	width:50px;
}
table .lleft {
	text-align: left;
}

.btn {
	border-style: none;
	height: 25px;
	width: 83px;
	background-color: #CCE7F5;
	color: #528BB5;
}

.txtarea {
	width: 500px;
	height: 100px;
	padding-bottom: 20px;
}

div {
	margin: auto;
}

.input_text {
	width: 40%;
	height: 35px;
	border-color: #FFFFFF #FFFFFF #000000 #FFFFFF;
	border-style: none none solid none;
	text-align:center;
	background-color: #F0F0F0;
}

.input_text_Other {
	width: 10%;
	height: 35px;
	border-color: #FFFFFF #FFFFFF #000000 #FFFFFF;
	border-style: none none solid none;
	text-align:center;
	background-color: #F0F0F0;
}

.tops {
	padding-bottom: 120px;
}
</style>
<script>
	var basePath = '<%=basePath%>';
	var cid = '${wxRecruit.wxRecID}';
	var notoken = 0;
	var token = 0 ;
	var search = '${search}';
	var select = 0;
</script>
</head>
<body>
	<div id="container" class="divsty">
		<form name="addrecruitForm" id="addrecruitForm" method="post">
			<table width="100%">
				<tr><th style="font-size: 20px;">微信招聘添加修改管理</th></tr>
			</table>
			<table class="frame">
				<tr>
					<td class="rleft">职位名称：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft"><input id="Text1" type="text" class="input_text put3 ckTextLength" maxlength="250"
					 name="wxRecruit.recName" value="${wxRecruit.recName }"/></td>
				</tr>
				<tr>
					<td class="rleft">职位性质：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
						<input type="hidden" value="${wxRecruit.recNature }" class="nature" id="recNature" name="wxRecruit.recNature" />
						<input type="hidden" value="${wxRecruit.recNaturei }" class="nature" id="recNaturei" name="wxRecruit.recNaturei" />
						<input type="hidden" value="${wxRecruit.recNatureii }" class="nature" id="recNatureii" name="wxRecruit.recNatureii" />
						<input id="btnProfession" class="btn" onclick="changePro(this,'btnProfession')" type="button" value="全职招聘" />
						<input id="btnPost" class="btn" onclick="changePro(this,'btnPost')" type="button" value="兼职招聘" />
						<input id="btnPractice" class="btn" onclick="changePro(this,'btnPractice')" type="button" value="实习招聘" />
					</td>
				</tr>
				<tr>
					<td class="rleft">招聘人数：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft"><input id="Text2" type="text" class="input_text_Other put3 isNaN"  name="wxRecruit.recNumT" value="${wxRecruit.recNum }"/> 人</td>
				</tr>
				<tr>
					<td class="rleft">学历：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
						<s:select list="#request.codeEdu" listKey="codeValue" listValue="codeValue" name="wxRecruit.recEdu" style="width:265px;height:35px;"></s:select>
					</td>
				</tr>
				<tr>
					<td class="rleft">工作经验：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
						<s:select list="#request.codeExp" listKey="codeValue" listValue="codeValue" name="wxRecruit.recExp" style="width:265px;height:35px;"></s:select>
					</td>
				</tr>
				<tr>
					<td class="rleft">职位月薪：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
						<s:select list="#request.codePay" listKey="codeValue" listValue="codeValue" name="wxRecruit.recPay" style="width:265px;height:35px;"></s:select>
					</td>
				</tr>
				<tr>
					<td class="rleft">行业类别：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
						<s:select list="#request.codeInd" listKey="codeValue" listValue="codeValue" name="wxRecruit.recInd" style="width:265px;height:35px;"></s:select>
					</td>
				</tr>
				<tr>
					<td class="rleft">工作地址：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft"><input id="Text3" type="text" class="input_text put3" name="wxRecruit.recAdd" value="${wxRecruit.recAdd }"/></td>
				</tr>
				<tr>
					<td class="rleft">发布时间：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft"><input id="Text4" type="text" class="input_text put3" name="wxRecruit.recIssue" onfocus="date(this)" value="${fn:substring(wxRecruit.recIssue,0,10) }"/></td>
				</tr>
				<tr>
					<td class="rleft">截止时间：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
						<input id="Text4" type="text" class="input_text put3" name="wxRecruit.recUp" onfocus="date(this)" value="${fn:substring(wxRecruit.recUp,0,10) }"/>
						<input type="hidden" name="wxRecruit.wxRecKey" value="${wxRecruit.wxRecKey }"/>
						<input type="hidden" name="wxRecruit.wxRecID" value="${wxRecruit.wxRecID }"/>
						<input type="hidden" name="wxRecruit.companyID" value="${wxRecruit.companyID }"/>
						<input type="hidden" name="wxRecruit.groupCompanySn" value="${wxRecruit.groupCompanySn }"/>
						<input type="hidden" name="wxRecruit.recAdate" value="${wxRecruit.recAdate }"/>
						<input type="hidden" name="wxRecruit.recAname" value="${wxRecruit.recAname }"/>
					</td>
				</tr>
			</table>
			<div style="width: 100%;text-align: left;" id="divadd">
				<input style="margin-top: 20px;" type="button" value="增加职位描述" onclick="addtab()" />
			</div>
			<table class="frame" id="tbTitle" style="display: none;">
				<tr>
					<td class="rleft">标题：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
						<input type="text" name="describeName" class="input_text ckTextLength addt addput3" maxlength="50"/>
						<input type="hidden" class="addt" name="wxDesKey"/>
						<input type="hidden" class="addt" name="wxDesID"/>
						<input type="hidden" class="addt" name="wxRecID"/>
						<input type="hidden" class="addt" name="companyID"/>
						<input type="hidden" class="addt" name="groupCompanySn"/>
					</td>
				</tr>
				<tr>
					<td class="rleft tops">内容：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft"><textarea id="taContent" name="describeContent" class="txtarea ckTextLength addt" maxlength="4000" cols="20" rows="2" name="S1"></textarea></td>
				</tr>
			</table>
			<s:iterator value="#request.beans"  var="item">
				<table class="frame">
				<tr>
					<td class="rleft">标题：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
						<input type="text" name="describeName" value="${item.describeName }" class="input_text put3 ckTextLength fmap" maxlength="50"/>
						<input type="hidden" class="fmap" name="wxDesKey" value="${item.wxDesKey }"/>
						<input type="hidden" class="fmap" name="wxDesID" value="${item.wxDesID }"/>
						<input type="hidden" class="fmap" name="wxRecID" value="${item.wxRecID }"/>
						<input type="hidden" class="fmap" name="companyID" value="${item.companyID }"/>
						<input type="hidden" class="fmap" name="groupCompanySn" value="${item.groupCompanySn }"/>
					</td>
				</tr>
				<tr>
					<td class="rleft tops">内容：</td>
					<td class="modtd">&nbsp;</td>
					<td class="lleft">
					<textarea id="taContent" name="describeContent" value="" class="txtarea ckTextLength fmap" maxlength="3000" cols="20" rows="2" name="S1">${item.describeContent }</textarea></td>
				</tr>
			</table>
			</s:iterator>
			<div align="center">
				<input type="button" class="input-button" style="margin-top: 20px;"  onclick="saveM()" value="&nbsp;保存 &nbsp;"/>
				<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
			</div>
		</form>
	</div>
</body>
</html>
