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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>企业合同管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script
			src="<%=basePath%>js/ea/office_ea/corporationcode/EnterpriseAgreement.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ckeditor/ckeditor.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common_word.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ckeditor/adapters/jquery.js"></script>
		<script type="text/javascript">
		 $(function(){
		 	$('#editorTxtare').ckeditor("");
		 });
		 var  basePath='<%=basePath%>';
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  enterpriseAgreementID = '';
         var  token=0;
		</script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="30" align="center">
							选择
						</th>
						<th width="30" align="center">
							序号
						</th>
						<th width="100" align="center">
							合同编号
						</th>
						<th width="150" align="center">
							合同名称
						</th>
						<th width="100" align="center">
							合同类别
						</th>
						<th width="100" align="center">
							合同概要
						</th>
						<th width="100" align="center">
							合同文件
						</th>
						<th width="150" align="center">
							甲方单位名称
						</th>
						<th width="100" align="center">
							甲方法人代表
						</th>
						<th width="100" align="center">
							甲方签约人
						</th>
						<th width="100" align="center">
							甲方签约人电话
						</th>
						<th width="150" align="center">
							乙方单位名称
						</th>
						<th width="100" align="center">
							乙方法人代表
						</th>
						<th width="100" align="center">
							乙方签约人
						</th>
						<th width="100" align="center">
							乙方签约人电话
						</th>
						<th width="100" align="center">
							合同生效日期
						</th>
						<th width="100" align="center">
							合同终止日期
						</th>
						<th width="100" align="center">
							合同到期提醒
						</th>
						<th width="100" align="center">
							编辑
						</th>
						<!-- 
						<th width="100" align="center">
							添加扫描件
						</th><th width="100" align="center">
							添加文件源稿
						</th>
			            <th width="100" align="center">管理起时间</th>
			            <th width="100" align="center">管理止时间</th>
			            <th width="100" align="center">责任人</th>
			            <th width="100" align="center">文件盒名称</th>
			            <th width="100" align="center">文件框</th>
			            <th width="100" align="center">编号</th>
			            -->
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" status="number">
						<tr id="${enterpriseAgreementID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${enterpriseAgreementID}" />
							</td>
							<td>
								<s:property value="%{#number.index+1}" />
							</td>
							<td>
								<span id="serialNumber">${serialNumber}</span>
							</td>
							<td>
								<span id="enName">${enName}</span>
							</td>
							<td>
								<span id="enType">${enType}</span>
							</td>
							<td>
								<span id="subject">${subject}</span>
							</td>
							<td>
								<span id="enFile">${enFile}</span>
							</td>
							<td>
								<span id="firstCompany">${firstCompany}</span>
							</td>
							<td>
								<span id="firstPerson">${firstPerson}</span>
							</td>
							<td>
								<span id="firstSignName">${firstSignName}</span>
							</td>
							<td>
								<span id="firstSignTel">${firstSignTel}</span>
							</td>
							<td>
								<span id="otherCompany">${otherCompany}</span>
							</td>
							<td>
								<span id="otherPerson">${otherPerson}</span>
							</td>
							<td>
								<span id="otherSignName">${otherSignName}</span>
							</td>
							<td>
								<span id="otherSignTel">${otherSignTel}</span>
							</td>
							<td>
								<span id="startDate">${fn:substring(startDate,0,10)}</span>
							</td>
							<td>
								<span id="endDate">${fn:substring(endDate,0,10)}</span>
							</td>
							<td>
								<span id="remind">${remind}</span>
							</td>
							<td>
								<span><a href="#" class="jqedit" id="childedit"
									onclick="opennew('${enterpriseAgreementID}')">编辑</a>
								<input id="enEdit" type='hidden' value="${enEdit}" /> </span>
							</td>
							<!-- 
							<td>
								<span id="enScan" style="display:none">${enScan}</span>
		                             <s:else>
		                                <span id="file"   onclick="lookImage('${enScan}');"><a href="#">查看</a></span>
		                            </s:else>
							</td>
							<td>
								<span id="enSource" style="display: none">${enSource}</span>
								<s:if test="enSource==null">无</s:if>
								<s:else>
									<a
										href='<%=basePath%>ea/enterpriseagreement/ea_downFile.jspa?downLoadPath=<s:property value="enSource"/>'>下载</a>
								</s:else>
							</td> -->
							<td class="td_bg01" style="display: none">
								<span id="managesStartDate">${fn:substring(managesStartDate,0,10)}</span>
							</td>
							<td class="td_bg01" style="display: none">
								<span id="managesEndDate">${fn:substring(managesEndDate,0,10)}</span>
							</td>
							<td class="td_bg01" style="display: none">
								<span id="responsibler">${responsibler}</span>
							</td>
							<td class="td_bg01" style="display: none">
								<span id="fileBoxName">${fileBoxName}</span>
							</td>
							<td class="td_bg01" style="display: none">
								<span id="fileFrame">${fileFrame}</span>
							</td>
							<td class="td_bg01" style="display: none">
								<span id="numbers">${numbers}</span>
								<span id="enterpriseAgreementKey" style="display: none">${enterpriseAgreementKey}</span>
								<span id="enterpriseAgreementID" style="display: none">${enterpriseAgreementID}</span>
								<span id="companyID" style="display: none">${companyID}</span>
								<span id="organizationID" style="display: none">${organizationID}</span>
							</td>

						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/enterpriseagreement/ea_getEnterpriseAgreementList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss3"
			style="top: 12%; width: 600px; left: 35%;" id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					详细信息
					<div class="close">
					</div>
				</div>
				<table cellpadding="5px" cellspacing="10px" name="stafftable"
					style="margin-left: 50px" id="stafftable">
					<tr>
						<td align="left">
							合同编号：
						</td>
						<td>
							<input name="enterpriseAgreement.serialNumber" id="serialNumber" />
						</td>
						<td align="left">
							合同名称：
						</td>
						<td>
							<input name="enterpriseAgreement.enName" id="enName" />
						</td>
						<!-- 
						<td width="159" rowspan="4" align="center">
							<img id="pic" width="99" height="135" />
						</td>
						 -->
					</tr>
					<tr>
						<td align="left">
							合同类型：
						</td>
						<td>
							<input name="enterpriseAgreement.enType" id="enType" />
						</td>
						<td align="left">
							合同概要：
						</td>
						<td>
							<input name="enterpriseAgreement.subject" id="subject" />
						</td>
					</tr>
					<tr>
						<td align="left">
							合同文件：
						</td>
						<td>
							<input name="enterpriseAgreement.enFile" id="enFile" />
						</td>
						<td align="left">
							甲方单位名称：
						</td>
						<td>
							<input name="enterpriseAgreement.firstCompany" id="firstCompany" />
						</td>
					</tr>
					<tr>
						<td align="left">
							甲方法人代表：
						</td>
						<td>
							<input name="enterpriseAgreement.firstPerson" id="firstPerson" />
						</td>
						<td align="left">
							甲方签约人：
						</td>
						<td>
							<input name="enterpriseAgreement.firstSignName"
								id="firstSignName" />
						</td>
					</tr>
					<tr>
						<td align="left">
							甲方签约人电话：
						</td>
						<td>
							<input name="enterpriseAgreement.firstSignTel" id="firstSignTel" />
						</td>
						<td align="left">
							乙方单位名称：
						</td>
						<td>
							<input name="enterpriseAgreement.otherCompany" id="otherCompany" />
						</td>
					</tr>
					<tr>
						<td align="left">
							乙方法人代表：
						</td>
						<td>
							<input name="enterpriseAgreement.otherPerson" id="otherPerson" />
						</td>
						<td align="left">
							乙方签约人：
						</td>
						<td>
							<input name="enterpriseAgreement.otherSignName"
								id="otherSignName" />
						</td>
						<!-- 
						<td width="159" align="center">
							<input name="enterpriseAgreement.photo" type="file" id="photo"
								class="input" size="10" />
							<input name="enterpriseAgreement.enScan" type="hidden"
								class="fileNum" id="enScan" />
						</td> 
						-->
					</tr>
					<tr>
						<td align="left">
							乙方签约人电话：
						</td>
						<td>
							<input name="enterpriseAgreement.otherSignTel" id="otherSignTel" />
						</td>
						<td align="left">
							合同生效日期：
						</td>
						<td>
							<input name="enterpriseAgreement.startDate" id="startDate"
								onfocus="date(this)" />
						</td>
					</tr>
					<tr>
						<td align="left">
							合同终止日期：
						</td>
						<td>
							<input name="enterpriseAgreement.endDate" id="endDate"
								onfocus="date(this)" />
						</td>
						<td align="left">
							合同到期提醒：
						</td>
						<td>
							<input name="enterpriseAgreement.remind" id="remind" />
						</td>
					</tr>
					<tr>
						<!--  
						<td align="left">
							添加源文件：
						</td>
						<td>
							<input name="enterpriseAgreement.sourcePhoto" type="file"
								id="sourcePhoto" class="input" size="10" />
							<input name="enterpriseAgreement.enSource" type="hidden"
								id="enSource" 
								</td>/>
						-->
						<td>
							<input name="enterpriseAgreement.enEdit" id="enEdit"
								class="enEdit"  type="hidden"/>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="hidden"
								name="enterpriseAgreement.enterpriseAgreementID"
								id="enterpriseAgreementID" />
							<input type="hidden"
								name="enterpriseAgreement.enterpriseAgreementKey"
								id="enterpriseAgreementKey" />
							<input type="hidden" name="enterpriseAgreement.companyID"
								id="companyID" />
							<input type="hidden" name="enterpriseAgreement.organizationID"
								id="organizationID" />
							<input type="button" class="input-button JQuerySubmit"
								style="cursor: pointer; width: 80px;" value="提交" />
							<input type="button" class="input-button JQueryreturn"
								style="cursor: pointer; width: 80px;" value="取消" />
						</td>
					</tr>
				</table>
				<s:token></s:token>
			</form>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 35%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="260px" id="cataffSearchTable">
					<tr>
						<td align="right">
							合同编号：
						</td>
						<td>
							<input name="enterpriseAgreement.serialNumber" id="serialNumber" />
						</td>
					</tr>
					<tr>
						<td align="right">
							合同名称：
						</td>
						<td>
							<input name="enterpriseAgreement.enName" id="enName" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>

		<!--添加到文件盒 -->
		<div class="jqmWindow " style="top: 20%; left: 20%;" id="jqModelBox">
			<form name="cstaffBoxForm" id="cstaffBoxForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					添加到文件盒
					<div class="closeBox">
					</div>
				</div>
				<table width="550" height="117" border="0" align="center"
					cellpadding="0" cellspacing="0" id="stafftableBox"
					style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td width="120" align="right" height="41">
							编号：
						</td>
						<td width="212">
							<input id="numbers" type="text" class="input"
								name="enterpriseAgreement.numbers" size="20" />
						</td>
						<td width="120" align="right" height="41">
							文件框：
						</td>
						<td width="212">
							<input id="fileFrame" type="text" class="input"
								name="enterpriseAgreement.fileFrame" size="20" />
					</tr>
					<tr>
						<td width="120" align="right" height="41">
							文件盒名称：
						</td>
						<td width="212">
							<input name="enterpriseAgreement.fileBoxName" type="text"
								id="fileBoxName" size="20" />
						</td>
						<td width="120" align="right" height="41">
							责任人：
						</td>
						<td width="212">
							<input name="enterpriseAgreement.responsibler" type="text"
								id="responsibler" size="20" />
						</td>
					</tr>
					<tr>
						<td width="120" align="right" height="41">
							管理起时间：
						</td>
						<td width="212">
							<input id="managesStartDate" type="text" class="input"
								name="enterpriseAgreement.managesStartDate" size="20"
								onfocus="date(this);" />
						</td>
						<td width="120" align="right" height="41">
							管理止时间：
						</td>
						<td width="150" colspan="3">
							<input id="numbers" type="text" class="input"
								name="enterpriseAgreement.numbers" size="20" />
							<input type="hidden"
								name="enterpriseAgreement.enterpriseAgreementID"
								id="enterpriseAgreementID" />
							<input type="hidden"
								name="enterpriseAgreement.enterpriseAgreementKey"
								id="enterpriseAgreementKey" />
							<input type="hidden" name="enterpriseAgreement.companyID"
								id="companyID" />
							<input type="hidden" name="enterpriseAgreement.organizationID"
								id="organizationID" />
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button"
						style="cursor: pointer; width: 80px;" id="tosavebox" value="保存 " />
					<input type="button" class="input-button JQueryreturn"
						style="cursor: pointer; width: 80px;" value="取消 " />
				</div>
			</form>
		</div>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<form name="editorForm" id="editorForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="editorjqModel">
				<div class="editorjqcontent" style="text-align: center">
					<textarea id="editorTxtare" name="enterpriseAgreement.fileContent"
						cols="100" rows="8">${projectDec }</textarea>
					<br />
					<input type="button" class="btn02" id="subEditor"
						style="cursor: hand" value=" 确定 " />
					&nbsp;&nbsp;
					<input type="button" class="btn02" id="clsEditor"
						style="cursor: hand" value=" 关闭 " />
				</div>
			</div>
			<s:token></s:token>
		</form>
	</body>
</html>