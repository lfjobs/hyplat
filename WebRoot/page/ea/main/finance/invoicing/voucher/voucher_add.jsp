<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>凭证添加页面</title>
		 <%@ page import="hy.ea.bo.Company"%>
		<%@ page language="java"  pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany");
		%> 
		 <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
		<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
		<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script src="<%=basePath%>js/ea/finance/invoicing/voucher/voucher_add.js"></script>
		<style type="text/css">
		.hide {
			border-left: 0px;
			border-right: 0px;
			border-top: 0px;
			border-bottom: 0px;
			background: transparent;
		}
		#table input {
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
		.bitian{
			color: red;
		}
		</style>
<script type="text/javascript">
  	var deptID = '<%=session.getAttribute("organizationID")%>';
  	var deptNames="<%=session.getAttribute("organizationName")%>";
  	var companyids = '<%=c.getCompanyID()%>';
    var companynames = '<%=c.getCompanyName()%>';
	var tokens = 0;
	var basePath = "<%=basePath%>";
	var token = 0;
	var pNumber=${pageNumber};
	var search="${search}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var notoken = 0;
	var select=0;
	var selects=0;
	var docNull=0;
	var jnumber='';
	var aa="${aa}";//00 凭证录入 01凭证审核 02 凭证记账
	var myDate =(new Date()).getFullYear()+"-"+((new Date()).getMonth()+1)+"-"+ (new Date()).getDate();
	var stime="${stime}";
	var etime="${etime}";
</script>
	</head>
	<body>
		<div id="apDiv1"></div>
		<form name="voucheraddForm" id="voucheraddForm" method="post" enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">凭证添加管理</div>
				</div>
				<table width="99%" border="0" id="table3" align="center" cellpadding="0" cellspacing="0" style="background: #FFFFFF;" class="table">
					<tr id="aa">
						<td height="30px"><span >公司：</span></td>
						<td><span id="gongsi"></span></td>
						<td >部门：</td>
						<td ><span id="bumen"></span></td>
						<td >凭证号码：</td>
						<td><input type="text"  style="border: 0" id="" name="" readonly="readonly" value="自动生成"/></td>
						<td rowspan="2" align="center"><input type="button" id="chouse" name="button7" value="选择票据" /></td>
					</tr>
					<tr id="bb">
						<td height="30px"><span >业务日期：</span></td>
						<td><span id="xitong"></span></td>
						<td >制单日期：</td>
						<td ><input type="text" id="makeDate" class="Wdate" name="voucher.makeDate" readonly="readonly" onfocus="WdatePicker({skin:'whyGreen',minDate:stime,maxDate:etime.substring(0, 8)+'%ld'})" /></td>
						<td >制单人：</td>
						<td>${sessionScope.account.accountEmail}</td>
					</tr>
				</table>
				<table width="99%" height="350px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1" style="position: absolute; width: 100%; height: 350px;overflow:scroll ">
								<table width="1215" align="center" cellpadding="0" cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" bgcolor="#E4F1FA">序号</th>
										<th align="center" bgcolor="#E4F1FA">凭证科目</th>
										<th align="center" bgcolor="#E4F1FA">摘要</th>
										<th align="center" bgcolor="#E4F1FA">借方金额</th>
										<th align="center" bgcolor="#E4F1FA">贷方金额</th>
									</tr>
									<tr id="kelong" style="display: none" height="40" >
										<!-- 序号 -->
										<td align="center" bgcolor="#FFFFFF">
											<span id="numbers"></span>
											<input type="hidden" id="cashierBillsID" name="cashierBillsID"/>
											<input type="hidden" id="goodsID" name="goodsID"/>
											<input type="hidden" id="goodsBillsID" name="goodsBillsID"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span class="bitian">*</span>
											<input type="hidden" id="subjectsID" name="subjectsID" />
											<input type="text" readonly="readonly" value="${subjectsName}" id="subjectsName" class="panduan" size="8" name="subjectsName" />
											<a href="#" class="tosubjects" >选择科目</a>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="reasonThing" name="reasonThing" size="10" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="loan" name="loan" size="10" readonly="readonly" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input id="forLoan" name="forLoan" size="10"  readonly="readonly" />
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">
							<input type="hidden" name="cashierBills.status" id="cashierstatus" value="${cashierBillsVO.status}" />
							<input type="button" class="btn001 JQuerySubmitgd" name="button3" value="保存" />
							<input type="button" class="btn001 JQueryClose" name="button2" value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>
		<%-------------------------------------科目选择------------------------------------%>
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;" id="selectsubjects">
			<div class="drag">选择</div>
			<table>
				<tr>
					<td>科目名字：</td>
					<td align="left" class="subjects">
						<select id="province" number='0' style="width: 110px;"></select>
						<select id="city" number='1' style="width: 110px;"></select>
						<select id="county" number='2' style="width: 110px; display: none;"></select>
						<select id="addressTown" number='3' style="width: 110px; display: none;"></select>
						<select id="addressVillage" number='4' style="width: 110px; display: none;"></select>
						<select id="addressCommunity" number='5' style="width: 110px; display: none;"></select>
						<select id="addressFloor" number='6' style="width: 110px; display: none;"></select>
						<select id="addressLayer" number='7' style="width: 110px; display: none;"></select>
						<select id="addressSize" number='8' style="width: 110px; display: none;"></select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savesubjects" value="确定" />
				<input type="button" class="input-button JQueryreturns" value="取消" />
			</div>
		</div>
		  <%------------------------------------票据选择------------------------------------%>
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;" id="billjqModel">
				<div class="content1" style="width: 100%;height: 400px;">
					<div class="contentbannb">
						<div class="drag">选择票据</div>
					</div>
					<table width="99%"  id="searchgood" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td align="right">黏贴单编号</td>
							<td ><input id="journalNum" size="15" name="cashierBills.journalNum" /></td>
							<td >
								<input type="button" class="btn02" id="chaxun" name="button7" value="查询" />
								<input type="button" class="btn02" id="qdbill" name="button5" value="确定" />
								<input type="button" class="btn02 JQueryreturnbill" name="button4" value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="50"><a id="wpsy" title="0">上一页</a></td>
							<td width="50"><a id="wpxy" title="0">下一页</a></td>
							<td width="80"><a id="wpzy">共&nbsp;&nbsp; <span style="color: red" id="wpzycount"></span>&nbsp;&nbsp;页</a></td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02" style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	<script>
	document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : ""); //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
        			if($("input#journalNum").val()!=''){
			        	$("input#chaxun").trigger("click");
					}
    			};
			};
	</script>
	</body>
</html>

