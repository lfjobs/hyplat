<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>待人事审核编辑</title>
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
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/human/examine/humanExamine_edit.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ckeditor/ckeditor.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ckeditor/adapters/jquery.js"></script>
		<script language="javascript" type="text/javascript" 
			src="<%=basePath%>js/common/common_word.js"></script>
		
		<style type="text/css">
		#apDiv1 {
			position: absolute;
			left: 800px;
			top: 450px;
			width: 63px;
			height: 32px;
			z-index: 1;
		}
		</style>
		
		<script type="text/javascript">
		var treeID = '<%=session.getAttribute("organizationID")%>';
		$(function(){
			$('#editorTxtare').ckeditor("");
			$('#editorTxtare1').ckeditor("");
		});
		
		var  treeID ="<%=session.getAttribute("organizationID")%>";
		var tokens = 0;
		var basePath = "<%=basePath%>";
		var cashierBillsID="${cashierBills.cashierBillsID}";
		var deptID="${cashierBillsVO.departmentID}";
		var token = 0;
		var pNumber=${pageNumber};
		var search="${search}";
		var pageNumber=<%=request.getParameter("pagepageNumber")%>;
		var myform='';
		var subjectsName="";
		var subjectsnumber="";
		var select=1;
		var notoken = 0;
		var journalNum = "";
		var treeid="";
		var treename="";
		var depotPID="";
		var depotID="";
		var depotName="";
		var selecttr='';
		var sdate="${sdate}";
		var edate="${edate}";
		var hostStatus = "${cashierBillsVO.consultStatus}";
</script>

	</head>
	<body>
		<div id="apDiv1"></div>
		<form name="cashierTallyForm" id="cashierTallyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width: 100%;">
				<div class="contentbannb">
					<div class="divtx">
						人事审核管理
					</div>
				</div>
				<br />
				<table width="99%" border="0" id="table3" align="center"
								cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
								class="table">
			  	 <tr>
			      <td height="20" align="right">粘贴单编号：</td>
			      <td>
			      <input type="hidden"  name="cashierBills.cashierBillsID" id="cashierID" value="${cashierBillsVO.cashierBillsID}"/>
			       <input type="hidden" name="cashierBills.cashierBillsKey" id="goodsBillsKey" value="${cashierBillsVO.cashierBillsKey}"/>
			       ${cashierBillsVO.journalNum}</td>
			      <td align="right">单据类别：</td>
			      <td style="width: 20%;">
										咨询跟踪单
										<input name="BType" id="billsType" value="咨询跟踪单"
											style="display: none;" />
									</td>
			       <td align="right">制单日期：</td>
			      <td>${fn:substring(cashierBillsVO.cashierDate, 0, 10)}</td>
			     </tr>
			  
			     <tr>
			       <td height="20" width="7%" align="right"><span class="STYLE1">公司：</span></td>
			       <td >${cashierBillsVO.companyname}</td>
			       <td align="right">部门：</td>
			       <td align="left" >${cashierBillsVO.departmentname}</td>
			      <td align="right"><div id="u1170_rtf">责任人：</div></td>
			       <td width="15%" align="left" id="staff">
			       <c:if test="${cashierBillsVO.staffname!=null}">${cashierBillsVO.staffname}---${cashierBillsVO.recordcode}</c:if></td>
			     </tr>
			     <tr>
			       <td height="20" width="7%" align="right"><span class="STYLE1">银行账号：</span></td>
			       <td >${cashierBillsVO.companyBankNum}</td>
			        <td align="right">票据支票管理:</td>
      				 <td align="left" >${cashierBillsVO.billCheck}</td>
			      <td align="right"></td>
			       <td width="15%" align="left" ></td>
			     </tr>
			    </table>
				
				<table width="99%" height="250px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 249px; overflow: scroll;">
								<table width="3000" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th align="center" width="100" bgcolor="#E4F1FA">
											录入时间
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											咨询起日期
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											咨询止日期
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											工作地点
										</th>
										<th align="center" width="100" bgcolor="#E4F1FA">
											服务方式
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											咨询跟踪内容
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											跟踪原因
										</th>
										<th align="center" width="100" bgcolor="#E4F1FA">
											处理状态
										</th>
										<th align="center" width="120" bgcolor="#E4F1FA">
											品名编号
										</th>
										<th align="center" width="160" bgcolor="#E4F1FA">
											统一分类条码
										</th>
										<th align="center" width="140" bgcolor="#E4F1FA">
											世标条码
										</th>
										<th align="center" width="160" bgcolor="#E4F1FA">
											费用或品名名称
										</th>
										<th align="center" width="160" bgcolor="#E4F1FA">
											类型
										</th>
										<th align="center" width="160" bgcolor="#E4F1FA">
											附件
										</th>
									</tr>
									<s:iterator value="pageForm.list">
										<tr class="xggoods" id="${goodsBillsID}">
											<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="etime">${fn:substring(entryTime, 0, 19)}</span>
											</td>
											<td height="20" align="center" bgcolor="#FFFFFF">
												<span id="sdate">${fn:substring(startDate, 0, 19)}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="edate">${fn:substring(endDate, 0, 19)}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="workSite">${workSite}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="serviceWay">${serviceWay}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="serviceContent">${serviceContent}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="serviceReason">${serviceReason}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="dealStatus">${dealStatus}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span class="bhide" id="goodsCoding">${goodsCoding}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="defaultStorage" class="bhide">${defaultStorage}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="identifyingCode">${identifyingCode}</span>
											</td>

											<td align="center" bgcolor="#FFFFFF">
												<span id="goodsName" class="bhide">${goodsName}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="typeID" class="bhide">${typeID}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<a href="#" class="accessoriesUrl1">文档编辑</a>
												<span><a href="#" class="jqedit"
													id="childedit" onclick="OpenWord('${attachmentPath}','2')"></a></span>
												<input id="attachmentPath1" type='hidden' value="${attachmentPath }" />
											</td>
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
					</tr>
				</table>
				
				<table width="99%" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来单位：</span>
						</td>
						<td width="15%">
							<span id="ccompanyname" class="qk">${cashierBillsVO.ccompanyname}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="15%">
							<span id="companyTel" class="qk">${cashierBillsVO.companyTel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="15%">
							<span id="cresponsible" class="qk">${cashierBillsVO.cresponsible}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td width="15%">
							<span id="accountNum" class="qk">${cashierBillsVO.accountNum}</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">单位负责人电话：</span>
						</td>
						<td>
							<span id="responsibleTel" class="qk">${cashierBillsVO.responsibleTel}</span>
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
							<span id="companyAddr" class="qk">${cashierBillsVO.companyAddr}</span>
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
							<span id="industryType" class="qk">${cashierBillsVO.industryType}</span>
						</td>
						<td height="30" align="right">
							<span class="STYLE1">单位往来关系：</span>
						</td>
						<td>
							<span id="contactConnections" class="qk">${cashierBillsVO.contactConnections}</span>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" id="table5"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来个人：</span>
						</td>
						<td width="15%">
							<span id="contactUserName" class="qk">${cashierBillsVO.contactUserName}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${cashierBillsVO.tel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${cashierBillsVO.staffIdentityCard}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${cashierBillsVO.userAccountNum}</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">QQ：</span>
						</td>
						<td>
							<span id="userQq" class="qk">${cashierBillsVO.userQq}</span>
						</td>
						<td align="right">
							<span class="STYLE1">邮箱：</span>
						</td>
						<td>
							<span id="email" class="qk">${cashierBillsVO.email}</span>
						</td>
						<td align="right">
							<span class="STYLE1">地址：</span>
						</td>
						<td>
							<span id="userAddr" class="qk">${cashierBillsVO.userAddr}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人往来关系：</span>
						</td>
						<td width="15%">
							<span id="phone" class="qk">${cashierBillsVO.phone}</span>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">
							<input type="hidden" name="cashierBills.consultStatus"
								id="cashierstatus" value="${cashierBillsVO.consultStatus}" />
							<input type="hidden" name="cashierBills.depStatue"
								id="cashierstatusService" value="${cashierBillsVO.depStatue}" />
							<input type="button" class="btn001 JQuerySubmitbh" name="button4" value="驳回" />  
       						<input type="button" class="btn001 JQuerySubmit" name="button4" value="审核通过" />  
       						<input type="button" class="btn001 JQueryClose" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
