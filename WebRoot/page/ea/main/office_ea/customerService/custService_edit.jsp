<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客户服务编辑</title>
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
			src="<%=basePath%>js/ea/office_ea/customerService/custService_edit.js"></script>
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
		<script language="javascript" type="text/javascript" 
			src="<%=basePath%>js/common/common_word.js"></script>
			
		<style type="text/css">
		#apDiv1 {
			position: absolute;
			left: 600px;
			top: 320px;
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
		var pbasePath = '<%=basePath%>';
		var basePath = "<%=basePath%>";
		var cashierBillsID="${cashierBills.cashierBillsID}";
		var deptID="${cashierBillsVO.departmentID}";
		var goodsBillsID="";
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
		
		$(document).ready(function(){
			if(cashierBillsID == ''){
				var url = basePath + "ea/custService/sajax_ea_getToStaffID.jspa?";
			}else{
				var partnerID = $("#partnerID").val();
				var url = basePath + "ea/custService/sajax_ea_getToStaffID.jspa?cashierBillsVO.staffID=" + partnerID;
			}
			$.ajax({
	            url: encodeURI(url),
	            type: "get",
	            async: true,
	            dataType: "json",
	            success: function cbf(data){
	                var member = eval("(" + data + ")");
	                var staffName = member.staffName;
	                if(cashierBillsID == ''){
	                	var staffID = member.staffID;
	                	$("#partnerID").val(staffID);
	                	$("#partnerName").text(staffName);
	                }else{
	                	var recordcode = '${cashierBillsVO.recordcode}';
	                	$("#partnerName").text(staffName+"---"+ recordcode);
	                }
	            },
	            error: function cbf(data){
	                notoken = 0;
	                alert("数据获取失败！")
	           }
	        });
		})
		
		function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
			 if(checkopertionName=="bankNum"){
			 	var departmentID =  $("#departmentID").attr("value");
			 	url = url + "?departmentID="+departmentID;
			 }
			 $("#checkopertionID",$("#bankJqm")).attr("value",checkopertionID);
			 $("#checkform",$("#bankJqm")).attr("value",attachSearchTable);
			 $("#checkopertionName",$("#bankJqm")).attr("value",checkopertionName);
			 $("#childopertionName",$("#bankJqm")).attr("value",childopertionName);
		  	 $("#daoRu").attr("src",basePath+url);
		  	 $("#bankJqm").jqmShow();
		}
		
		$(document).ready(function() {//销售单FORM
			$("#DaoRuFan").click(function(){// 返回
		       $("#bankJqm").jqmHide();
			}); 
			$("#DaoRuFanqd").click(function(){// 选择确定
				var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");
				var checkform =$("#checkform",$("#bankJqm")).attr("value");
				var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
				var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");
				var childopertionID = window.frames["daoRu"].opertionID;
				if(childopertionID == ""){
					alert("请选择")
					return;
				}
				var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
				var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();
				if(checkopertionID != "")
					$("#"+checkopertionID,$("#"+checkform)).attr("value",childopertionID).trigger("blur");
				if(checkopertionName != ""){
					$("#"+checkopertionName,$("#"+checkform)).attr("value",childopertionName).trigger("blur");
				}
				if(checkopertionName =="partnerName"){
					var final = no + "---" + childopertionName;
					$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
				}
				 $("#daoRu").attr("src","");
		         $("#bankJqm").jqmHide();
		   });
		});
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
						客户服务编辑
					</div>
				</div>
				<br />
				<table width="99%" border="0" id="table3" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td height="30" align="right" style="width: 8%;">
							粘贴单编号：
						</td>
						<td style="width: 15%;">
							<input type="hidden" name="cashierBills.cashierBillsID"
								id="cashierID" value="${cashierBillsVO.cashierBillsID}" />
							<input type="hidden" name="cashierBills.cashierBillsKey"
								id="goodsBillsKey" value="${cashierBillsVO.cashierBillsKey}" />
							<input type="text" style="width: 200px" class="input"
								value="<c:if test="${cashierBillsVO.journalNum!=null}">${cashierBillsVO.journalNum }</c:if><c:if test="${cashierBillsVO.journalNum==null}">自动生成</c:if>"
								id="journalNum" name="cashierBills.journalNum"
								readonly="readonly" />
						</td>
						<td align="right" style="width: 5%;">
							单据类别：
						</td>
						<td style="width: 20%;">
							咨询跟踪单
							<input name="BType" id="billsType" value="咨询跟踪单"
								style="display: none;" />
						</td>
						<td align="right" style="width: 5%;">
							责任人：
						</td>
						<td style="">
							<span id="partnerName"></span>
							<input type="hidden" id="partnerID" name="cashierBills.staffID"
								readonly="readonly" value="${cashierBillsVO.staffID}" />
						</td>
					</tr>

					<tr>
						<td height="30" width="7%" align="right">
							<span class="STYLE1">公司：</span>
						</td>
						<td>
							<span id="companyNames"></span>
						</td>
						<td align="right">
							部门：
						</td>
						<td align="left" id="dept">
							<input id="depText" class="classhide hide input" type="text"
								value="${cashierBillsVO.departmentname}" readonly="readonly" />
							<input type="hidden" name="cashierBills.departmentID"
								value="${cashierBillsVO.departmentID}" />
							<a href="#" title="departmentID" class="classhide update">修改</a>
							<select name="departmentID" id="departmentID"
								style="width: 180px;"></select>
						</td>
						<td align="right">
							<div id="u1170_rtf">
								银行账号：
							</div>
						</td>
						<td width="25%" align="left">
							<input type="text" id="bankNum" class="input"
								name="cashierBills.companyBankNum" readonly="readonly"
								value="${cashierBillsVO.companyBankNum}" size="35" />
							<a href="#" id="bankChoose"
								onclick="importGY('table3','bankID','bankNum','childbankName','ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa')">选择</a>
						</td>
					</tr>
				</table>

				<table width="99%" height="125px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 124px; overflow: scroll;">
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
										<th align="center" width="200" bgcolor="#E4F1FA">
											咨询跟踪内容
										</th>
										<th align="center" width="200" bgcolor="#E4F1FA">
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
										<th align="center" width="140" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<s:iterator value="pageForm.list">
										<tr class="xggoods" id="${goodsBillsID}">
											<td height="30" align="center" bgcolor="#FFFFFF">
												<span id="etime">${fn:substring(entryTime, 0, 19)}</span>
												<input name="etime" type="text" class="input model1"
												style="margin-left: 2px;" id="etime" size="10"
												readonly="readonly" value="系统自动添加" />
											</td>
											<td height="20" align="center" bgcolor="#FFFFFF">
												<span id="sdate">${fn:substring(startDate, 0, 19)}</span>
												<input name="sdate" class="input model1"
													onfocus="daytime(this);" value="${startDate}"
													style="margin-left: 2px;" size="18" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="edate">${fn:substring(endDate, 0, 19)}</span>
												<input name="edate" class="model1 input"
													onfocus="daytime(this);" value="${endDate}" size="20" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="workSite">${workSite}</span>
												<input class="input model1" name="workSite"
													value="${workSite}" id="workSite1" size="30"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="serviceWay">${serviceWay}</span>
												<s:select
													list="#{'':'请选择','面谈':'面谈','电话':'电话','邮件':'邮件','登门拜访':'登门拜访','暗访':'暗访'}"
													class="input model1" name="serviceWay" id="serviceWay"
													theme="simple"></s:select>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="serviceContent">${serviceContent}</span>
												<input class="input model1" name="serviceContent"
													value="${serviceContent}" id="serviceContent1" size="30"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="serviceReason">${serviceReason}</span>	
												<input class="input model1" name="serviceReason"
													value="${serviceReason}" id="serviceReason1" size="30"
													style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="dealStatus">${dealStatus}</span>
												<s:select list="#{'':'请选择','处理':'处理','未处理':'未处理'}"
													class="input model1" name="dealStatus" id="dealStatus"
													theme="simple"></s:select>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="goodsID" value="${goodsID}"
													id="goodsID" />
												<span class="bhide" id="goodsCoding">${goodsCoding}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="defaultStorage" class="bhide">${defaultStorage}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="identifyingCode">${identifyingCode}</span>
												<input name="identifyingCode" value="${identifyingCode}"
													id="identifyingCode" class="model1 input" />
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
											<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="goodsBillsID"
													value="${goodsBillsID}" id="goodsBillsID" />
												<input type="hidden" name="goodsBillsKey"
													value="${goodsBillsKey}" id="goodsBillsKey" />
												<a href="#" class="ajaxxg"><img
														src="<%=basePath%>images/admin_images/edit.gif" width="16"
														height="16" title="修҉改҉" border="0" /> </a>
												<a href="#" class="ajaxsc"><img
														src="<%=basePath%>images/admin_images/gtk-del.png"
														width="16" height="16" title="删除" border="0" /> </a>
											</td>
										</tr>
									</s:iterator>

									<tr id="kelong" style="display: none">
										<td align="center" bgcolor="#FFFFFF">
											<input name="etime" type="text" class="input"
												style="margin-left: 2px;" id="etime" size="10"
												readonly="readonly" value="系统自动添加" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="sdate" class="input " id="sdate"
												onfocus="daytime(this);" style="margin-left: 2px;" size="18" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="edate" class="input " id="edate"
												onfocus="daytime(this);" style="margin-left: 2px;" size="18" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="workSite" id="workSite" size="30"
												style="margin-left: 2px;" class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select
												list="#{'':'请选择','面谈':'面谈','电话':'电话','邮件':'邮件','登门拜访':'登门拜访','暗访':'暗访'}"
												name="serviceWay" id="serviceWay" theme="simple"></s:select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="serviceContent" id="serviceContent" size="30"
												style="margin-left: 2px;" class="input ckTextLength" maxlength="250"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="serviceReason" id="serviceReason" size="30"
												style="margin-left: 2px;" class="input ckTextLength" maxlength="250"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<s:select list="#{'':'请选择','处理':'处理','未处理':'未处理'}"
												name="dealStatus" id="dealStatus" theme="simple"></s:select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input type="hidden" name="goodsID" id="goodsID" />
											<span id="goodsCoding"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="defaultStorage"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="identifyingCode"></span>
											<input name="identifyingCode" id="identifyingCode"
												class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="goodsName"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<span id="typeID"></span>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<a href="#" class="accessoriesUrl">文档编辑</a>
											<span><a href="#" class="jqedit"
												id="childedit" onclick="OpenWord('${attachmentPath}','2')"></a></span>
											<input type="hidden" name="attachmentPath" id="attachmentPath" value="${attachmentPath}"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<a href="#" class="klsc"><img
													src="<%=basePath%>images/admin_images/gtk-del.png"
													width="16" height="16" title="删除" border="0" /> </a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<div id="otherChoose">
				<table width="99%" height="30" border="0" align="center"
					cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
					style="margin-bottom: 5px;">
					<tr>
						<td bgcolor="#FFFFFF">
							<input type="button" class="ACT_btn" name="button4" value="选择物品"
								id="shuju" />
							<input type="button" class="ACT_btn" name="button5"
								value="选择往来单位" id="xzwlaw" />
							<input type="button" class="ACT_btn" name="button4"
								value="选择往来个人" id="xzwlgr" />
						</td>
					</tr>
				</table>
				</div>
				<table width="99%" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来单位：</span>
						</td>
						<td width="15%">
							<span id="ccompanyname" class="qk">${cashierBillsVO.ccompanyname}</span>
							<input type="hidden" id="ccompanyID"
								name="cashierBills.ccompanyID"
								value="${cashierBillsVO.ccompanyID}" />
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
							<span id="accountNum">${cashierBillsVO.accountNum}</span>
							<input id="accountNum" type="hidden"
								name="cashierBills.accountNum"
								value="${cashierBillsVO.accountNum}" />
							<select style="display: none" id="aNum" name="accountNum">
								<option value="">
									请选择
								</option>
							</select>
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
							<div align="left">
								<span id="contactConnections" class="qk">${cashierBillsVO.contactConnections}</span>
							</div>
							<s:select list="connectionlist" listKey="codeValue"
								style="display:none" id="contactConnections"
								listValue="codeValue" headerKey="" headerValue="请选择"
								name="cashierBillsVO.contactConnections" theme="simple"></s:select>
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
							<input type="hidden" id="contactUserID"
								name="cashierBills.contactUserID"
								value="${cashierBillsVO.contactUserID}" />
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
							<input id="userAccountNum" type="hidden"
								name="cashierBills.userAccountNum"
								value="${cashierBillsVO.userAccountNum}" />
							<select style="display: none" id="userNum" name="userAccountNum">
								<option value="">
									请选择
								</option>
							</select>
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
							<s:select list="codeRelationList" listKey="codeValue"
								style="display:none" listValue="codeValue" id="phone"
								headerKey="" headerValue="请选择" name="cashierBillsVO.phone"
								theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">
							<input type="hidden" name="cashierBills.consultStatus"
								id="consultStatus" value="${cashierBillsVO.consultStatus}" />
							<input type="hidden" name="cashierBills.depStatue"
								id="cashierstatusService" value="${cashierBillsVO.depStatue}" />
							<input type="button" class="btn001 JQueryprint" name="button"
								value="打印预览" />
							<input type="button" class="btn001 JQueryunitret" name="button"
								value="重置往来单位" />
							<input type="button" class="btn001 JQuerypersonret" name="button"
								value="重置往来个人" />
							<input type="button" style="display: none;"
								class="btn001 JQuerySubmitgd" name="button3" value="提交审核" />
							<input type="button" class="btn001 sub_bill" style="display: none;" 
								name="button4" value="草稿保存" />
							<input type="button" class="btn001 JQueryClose" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss4" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%">
					<div class="contentbannb">
						<div class="drag">
							选择物品
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								物品编码或名称：
							</td>
							<td width="142">
								<input name="typeID" class="input" id="typeID" size="18"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn05" ID="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn05" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn05 xzwp" name="button" value="新增" />
								<input type="button" class="btn05 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="60">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="60">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="80">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="16%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="aadTree" class="text_tree"
												style="overflow: auto; z-index: 99; height: 450px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 450px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="70" align="right">
								单位名称：
							</td>
							<td width="60">
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="85">
								<s:select list="connectionlist" listKey="codeValue"
									id="contactConnections" listValue="codeValue" headerKey=""
									headerValue="--全部--" name="contactConnections" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; height: 450px; width: 100%; overflow: scroll; height: 450px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<form name="selectuserForm" id="selectuserForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="userjqModel">
				<div class="content1" style="width: 100%">
					<div class="contentbannb">
						<div class="drag">
							选择往来个人
						</div>
					</div>
					<table width="99%" height="33" id="searchuser" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="40" align="right">
								姓名：
							</td>
							<td width="50">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="100">
								<s:select list="codeRelationList" listKey="codeValue"
									listValue="codeValue" headerKey="" headerValue="--全部--"
									id="relation" name="relation" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchuu" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qduser" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzgr" name="button5"
									value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="grsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="grxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; display: none; height: 450px; width: 100%; overflow: scroll; height: 450px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<div class="jqmWindow jqmWindowcss3" style="width: 300px; top: 10%;"
			id="newccode">
			<div class="drag">
				添加
			</div>
			<table>
				<tr>
					<td>
						代码名字：
					</td>
					<td>
						<input id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
		</div>
	</body>
</html>