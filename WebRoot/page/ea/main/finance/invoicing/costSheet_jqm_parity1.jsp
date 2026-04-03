<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目比价-展示单据</title>
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
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/admin_main111.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css"
	type="text/css" media="screen" />
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/organizationTree.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/common/common_word.js"></script>
<style type="text/css">
.hide {
	border-left: 0px;
	border-right: 0px;
	border-top: 0px;
	border-bottom: 0px;
	background: transparent;
}

.classhide {
	display: none;
}

#table input {
	width: 180px;
}

#goodstable input {
	width: 180px;
}

#contactcompany input {
	width: 180px;
}

#apDiv1 {
	position: absolute;
	left: 707px;
	top: 407px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>
<script type="text/javascript">
	var parityStr="<%=request.getParameter("parityStr") %>";;
	var basePath="<%=basePath%>";
	$(document).ready(function() {    //获取凭证号
    var url = 	basePath + "ea/costsheet/sajax_ea_parityCostSheet.jspa?parityStr="+parityStr;	
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath
						+ "page/ea/not_login.jsp";
			}
			//追加单据
			var csblist = member.csbs;
			if(csblist != null){
				for(var i = 0; i <csblist.length ; i++){
					$("#journalNum"+i).append(csblist[i].journalNum);
					$("#projectName"+i).append(csblist[i].projectName);
					$("#staffName"+i).append(csblist[i].staffName);
					$("#actualAmount"+i).append(csblist[i].budgetAmount+"元");
				}
			}
			//追加公司名称
			var comList = member.coms;
			if(comList != null){
				for(var i = 0; i <comList.length ; i++){
					$("#companyID"+i).append(comList[i]);
				}
			}
			//追加部门名称
			var orgList = member.orgs;
			if(orgList != null){
				for(var i = 0; i <orgList.length ; i++){
					$("#organizationName"+i).append(orgList[i]);
				}
			}
			//追加单据子类
			var pfList = member.pfs;
			if(pfList != null){
				for(var i = 0; i <pfList.length ; i++){
					var pf = pfList[i];
					var tabletit = "<table>";
					for(var j = 0 ; j < pf.list.length ; j++){
						tabletit += "<tr><td><input type='radio' class='chx' name='chx"+i+"' value = '"+pf.list[j].csdID+"'/></td><td id='td"+pf.list[j].csdID+"'>"+pf.list[j].goodsName+"</td></tr>";
					}
					tabletit += "</table>";
					$("#csbd"+i).html(tabletit);
				}
			}
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
    });
</script>
</head>
<body>
	<div id="apDiv1"></div>
	<form name="CostSheetForm" id="CostSheetForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<div class="content1" style="width: 100%;">
			<div class="contentbannb">
				<div class="divtx">项目比价</div>
			</div>
			<table width="99%" border="0" id="table3" align="center"
				cellpadding="0" cellspacing="0" style="background: #FFFFFF;" class="table">
				<tr id="tr0">
					<td height="30"align="right" width="85">项目编号：</td>
					<td id="journalNum0"  width="170px"></td>
					<td id="journalNum1"  width="170px"></td>
					<td id="journalNum2"  width="170px"></td>
					<td id="journalNum3"  width="170px"></td>
				</tr>
				<tr id="tr0">
					<td height="30"align="right" >项目名称：</td>
					<td id="projectName0"></td>
					<td id="projectName1"></td>
					<td id="projectName2"></td>
					<td id="projectName3"></td>
				</tr>
				<tr id="tr0">
					<td height="30"align="right" >公司名称：</td>
					<td id="companyID0"></td>
					<td id="companyID1"></td>
					<td id="companyID2"></td>
					<td id="companyID3"></td>
				</tr>
				<tr id="tr0">
					<td height="30"align="right" >部门名称：</td>
					<td id="organizationName0"></td>
					<td id="organizationName1"></td>
					<td id="organizationName2"></td>
					<td id="organizationName3"></td>
				</tr>
				<tr id="tr0">
					<td height="30"align="right" >责任人：</td>
					<td id="staffName0"></td>
					<td id="staffName1"></td>
					<td id="staffName2"></td>
					<td id="staffName3"></td>
				</tr>
				<tr id="tr0" >
					<td height="150"align="right" >包含产品：</td>
					<td id="csbd0"></td>
					<td id="csbd1"></td>
					<td id="csbd2"></td>
					<td id="csbd3"></td>
				</tr>
				<tr id="tr0">
					<td height="30"align="right" >项目实际报价：</td>
					<td id="actualAmount0"></td>
					<td id="actualAmount1"></td>
					<td id="actualAmount2"></td>
					<td id="actualAmount3"></td>
				</tr>
				
			</table>
			
			
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
				framespacing="0" height="0"></iframe>
</body>
</html>

