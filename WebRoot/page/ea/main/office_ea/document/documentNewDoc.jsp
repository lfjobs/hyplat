<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新建公文</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ include file="docCommon.jsp"%>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/validate.css" />
		<link href="<%=basePath%>css/ea/document/admin_main.css"
			rel="stylesheet" type="text/css" />

		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/document/documentNewDoc.js?version=20210220"></script>

		<script type="text/javascript"
			src="<%=basePath%>js/ckeditor/ckeditor.js"></script>

		<script type="text/javascript">
		 var basePath='<%=basePath%>';    
     	var docId = ""; 
        var token = 2;

     	var pNumber = "";      	
     	
     	var docTypeSel='${document.docType}';	
     	var emergencyTypeSel='${document.emergencyType}';
     	var template = '${document.specificTemplate}';
     	var fileId ="${docFileAttach.fileId}";
     	var module ='${document.module}'; 
     	var status = '${document.status}';
     	var type = '${type}';
     	var themes = '${document.theme}';
     	var commonComment = '${document.commonComment}';
     	
        var fileType = '${docFileAttach.fileType}';
        
        var receiverID = '${document.receiverID}';
        var receiverDeptID = '${document.receiverDeptID}';
        var receiverCompanyID = '${document.receiverCompanyID}';
        var numTime = "${document.numTime}";
        
        var  docType = "${document.docType}";
        var journalNum = "${param.journalNum}";
        var projectName = "${param.projectName}";
        

           
       
        
     	</script>
   

	</head>
	<body>
		<div id="main">
			<table width="100%" height="100%" border="0" align="center"
				cellpadding="0" cellspacing="2" >
				<tr>
					<td width="87%" align="left" valign="top">
						<div id="body_02">
							<table width="100%" height="26" align="center" cellspacing="0"
								cellpadding="1" style="font-size: 12px;" class="bannb_01">
								<tr>
									<td height="24" align="left" valign="top" class="txt01">
										&nbsp;新 建
									</td>
								</tr>
							</table>
							<form name="newForm" method="post" action="" id="newForm">
								<input type="submit" name="submit" style="display: none" />
								<table width="98%" height="80%" border="0" align="center" cellpadding="0"
									cellspacing="0" class="table"
									style="background: #FFFFFF; margin: 5px">
									<tr>
										<td height="35" colspan="4" align="left"
											style="padding-left: 10%;">

                                           <nobr>
											<input type="button" value="<<返回" class="anniu02"
												id="noapprove" onclick="history.back();" />
											&nbsp;|&nbsp;
											<input type="button" class="anniu02" id="senddoc"
												value="&nbsp;&nbsp; 至领导审批" />
											<input type="button" class="anniu02" id="topass" value="传阅草稿" />
											<input type="button" class="anniu02" id="toplate"
												value="&nbsp; &nbsp; 至信息平台" />
											&nbsp;|&nbsp;
											<input type="button" class="anniu02" id="storedoc" value="保存"
												onclick="storeDraft();" />
											<input type="hidden" name="type" value="${type}" id="type" />
											<input type="hidden"  id="receiverCompanyID2"
												name="document.receiverCompanyID"/>
											<input type="hidden" id="receiverDeptID2" name="document.receiverDeptID"/>
											<input type="hidden" name="document.receiverID" id='receiverID2'/>
											
										  </nobr>
										</td>
									</tr>
									<tr class="g">
										<td align="right" style="width: 14%;">
											<span>文件标题</span>：
										</td>
										<td colspan="3">
											<input name="document.title" type="text" class="input borderStyle2"
												id="title" title="文件标题无需手动输入" value="${document.title}"
												size="70" readonly />
											<input type="hidden" name="document.docId"
												value="${document.docId}" />
										</td>
									</tr>

									<tr class="g">
										<td align="right">
											主&nbsp;题&nbsp;&nbsp;词：
										</td>
										<td align="left" colspan="3">
										<input type="text"  id="theme"  name="document.theme" value="${document.theme}"class="input borderStyle2" title="主题词无需手动输入" size="70" readonly/></td>
									</tr>
									<tr class="g">

										<td align="right">
											文件缓急：
										</td>
										<td colspan="3">
										<input type="text"  id="emergencyType"   name="document.emergencyType" value="${document.emergencyType}" class="input borderStyle2" title="文件缓急无需手动输入"  readonly/>
										</td>
									</tr>

									<tr class="g"> 
										<td align="right">
											正式编号：
										</td>
										<td colspan="3">
											<input type="hidden" id="docNum" name="document.docNum"
												value="${document.docNum}" />
											<input type="hidden" id="numTime" name="document.numTime"
												value="${document.numTime}" />

											<nobr>
												<input type="text" id="numWord" class="input borderStyle"
													title="无需手动输入" name="document.numWord"
													value="${document.numWord}" />
												字【
												<span id="numTime">${document.numTime}</span>】第
												<input type="text" id="numCode" class="input borderStyle"
													name="document.numCode" value="${document.numCode}"
													title="无需输入,自动生成" readonly />
												号
												<input type="hidden" value="${document.numTime}" />
											</nobr>
										</td>
									</tr>
									<tr class="trtip g">
										<td align="right">
											甲&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;方：
										</td>
										<td colspan="3">
											<div>
												&nbsp;&nbsp;公司&nbsp; 
												<input type="text" name="document.partyAName" id="partyAName" title="无需手动输入"
													value="${document.partyAName}" class="input borderStyle" readonly  style="width:150px;"/>
												<input type="hidden" name="document.partyA" id="partyA"
													value="${document.partyA}" />
												
											&nbsp;责任人
												<input type="text" name="document.partyAstaffnames" title="无需手动输入"
													id="partyAstaffnames" value="${document.partyAstaffnames}"
													class="input borderStyle" readonly />
											&nbsp;身份证号
												<input type="text" name="document.staffIdentityCardA"  title="无需手动输入"
													id="staffIdentityCardA" value="${document.staffIdentityCardA}" style="width:150px;"
													class="input borderStyle" readonly  /></div>
											<input type="hidden" name="document.partyAstaff"
												id="partyAstaff" value="${document.partyAstaff}" />
										</td>
									</tr>
									<tr class="trtip g">
										<td align="right">
											乙&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;方：
										</td>
										<td colspan="3">
											<div>
												&nbsp;&nbsp;公司&nbsp;
												<input type="text" name="document.partyBName" id="partyBName" title="无需手动输入"
													value="${document.partyBName}" class="input borderStyle" readonly style="width:150px;"/>
												<input type="hidden" name="document.partyB" id="partyB"
													value="${document.partyB}" />
												
												&nbsp;责任人
												<input type="text" name="document.partyBstaffnames" title="无需手动输入"
													id="partyBstaffnames" value="${document.partyBstaffnames}"
													class="input borderStyle" readonly />
												&nbsp;身份证号
												<input type="text" name="document.staffIdentityCardB" title="无需手动输入"
													id="staffIdentityCardB" value="${document.staffIdentityCardB}" style="width:150px;"
													class="input borderStyle" readonly  /></div>
											<input type="hidden" name="document.partyBstaff"
												id="partyBstaff" value="${document.partyBstaff}" />
										</td>
									</tr>

									<tr class="trtip g">
										<td align="right">
											有&nbsp;效&nbsp;&nbsp;期：
										</td>
										<td align="left" colspan="3">
											从
											<input type="text" name="document.startValidity"
												id="startDate" class="input borderStyle"
												readonly
												value="${fn:substring(document.startValidity, 0, 10)}" title="无需手动输入"/>

											至
											<input type="text" name="document.endValidity" id="endDate"
												readonly class="input borderStyle"
												value="${fn:substring(document.endValidity, 0, 10)}" title="无需手动输入"/>
										</td>
									</tr>
									<tr class="trtip g">
										<td align="right">
											所属项目预算：
										</td>
										<td align="left" colspan="3">
										<input type="text"  id="projectName"  name="document.projectName" value="${document.projectName}" class="input borderStyle2"  size="70" readonly/>
										
										<input type="hidden"  id="journalNum"  name="document.journalNum" value="${document.journalNum}"/>
										</td>
									</tr>
									<tr class="g">
										<td align="right">
											公文类型：
										</td>
										<td>
										
										 <input  type="text" id="docTypeText"   title="无需手动输入" size="20"  style="border:0px" readonly  value=""/>
										 <input  type="hidden" id="docType" name="document.docType" value="${document.docType}" />
											</td>
										<td align="left" class="filetype">
											文件格式：
											<select id="fileType" name="docFileAttach.fileType"
												style="width: 155px;">
												<option value="" selected="selected" />
													请选择文件格式
												<option value="W" />
													Word
												<option value="E" />
													Excel
											</select>
										</td>
										<td align="left" class="filetype">
											具体模板：
											<select id="specificTemplate"
												name="document.specificTemplate" style="width: 155px;">
												<option value="" selected="selected" />
													请先选择文件格式
											</select>
										</td>
									</tr>

									<tr class="temptr">
										<td align="right">
											正&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文：
										</td>

										<td align="left" colspan="3">

											<input type="button" value="起草公文" class="anniu01"
												onclick="DraftDocument('draft');" id="draft" />
										</td>
									</tr>

									<tr style="display: none;" id="view">
										<td align="right">
											<img src="<%=basePath%>images/ea/office/document/attach1.png"
												width="16" height="16" />
											附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件：
										</td>
										<td colspan="3">
											<div>
												<a href="#" id="filename">${docFileAttach.fileShowName}.${docFileAttach.ext}</a>
												<input type="button" title="移除" id="move" class="deleteBtn" />
												<input type="hidden" name="docFileAttach.filePath"
													value="${docFileAttach.filePath}" id="filepath" />
												<!-- 附件的ID -->
												<input type="hidden" name="docFileAttach.fileId" id="fileId"
													value="${docFileAttach.fileId}" />
											</div>
										</td>
									</tr>

									<tr class="ccment">
										<td height="30" align="right">
											交互意见：
										</td>

										<td align="left" colspan="3">

											<div style="height: auto; width: 100%; overflow: auto;">
												${document.commonComment}
											</div>
										</td>
									</tr>
									<tr class="subcoment">
										<td height="30" align="right">
											审批人意见：
										</td>
										<td colspan="3">
											<span id="subscriberComment">${document.subscriberComment}</span>
										</td>
									</tr>
									<tr>
										<td align="right">
											附加意见：
										</td>

										<td align="left" colspan="3">
											<input type="hidden" name="document.social" id='social2'
												value="" />
											<input type="hidden" name="interactDocInfo.smId" id='social3'
												value="" />
											<input type="hidden" name="interactDocInfo.web" id='web2'
												value="" />
											<input type="hidden" name="interactDocInfo.visitType"
												id='visitType2' value="" />
											<textarea cols="70" id="appendComment"
												name="document.appendComment" rows="3">
                     ${document.drafterComment}
                    </textarea>
											<script type="text/javascript">
                    var editor = CKEDITOR.replace( 'appendComment',
                                   {
                                  skin : 'kama',
                                 language : 'zh-cn',
                                 height:100
                                 
                                  });
                     </script>
										</td>
									</tr>

								</table>


							</form>
						</div>
					</td>
				</tr>
			</table>
		</div>

		<!-- 添加主题窗口-->
		<form id="themeForm" name="themeForm">
			<div class="jqmWindow" style="width: 250px; right: 25%; top: 10%"
				id="jqModeltheme">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					添加主题词
					<div class="close">
					</div>
				</div>
				<table cellspacing="10" id="templatetable">
					<tr>
						<td>
							主题名称：
						</td>
						<td>
							<input type="text" id="addtheme" />
						</td>
					</tr>
					<tr>

						<td colspan="2" align="center">
							<input type="button" value="  确定 " class="input-button"
								id="addThemeSubmit" onclick="submitTheme();" />
							<input type="button" value="  关闭 " class="input-button close" />
						</td>
					</tr>
				</table>
			</div>
		</form>

		<!-- 选择社会接收人员 -->
		<form name="InfoForm" id="InfoForm" method="post">
			<div class="jqmWindow" style="display: none; right: 25%; top: 5%"
				id="jqModelInfo">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					选择社会人员
					<div class="close">
					</div>
				</div>
				<center>
					<table id="SearchTable" cellspacing="8" cellpadding="2">
						<tr>
							<td align="right" style="width: 30%;">
								社会接收人：
							</td>
							<td align="left">
								<input id="socialName" type="text" value="" readonly="readonly" />
								<input type="hidden" name="document.social" id='socials'
									value="" />
								<input type="button" class="input-button" value=" 系统中选择 "
									onclick="importGY2('ea/documentcommon/ea_getSocialInfoList.jspa','');" />
								<input type="button" class="input-button" value=" 临时添加 "
									onclick="addSociety();" />
							</td>
						</tr>
						<tr>
							<td align="right">
								选择交互平台：
							</td>
							<td align="left">
								<input type="checkbox" name="checkinput" value="1">
								北京天太世统科技团网站
								<br>
								<input type="checkbox" name="checkinput" value="2">
								北京天太胜威管理团网站
								<br>
								<input type="checkbox" name="checkinput" value="3">
								孵龙国际教育集团
								<br>
								<input type="checkbox" name="checkinput" value="4">
								四川省胜威驾校有限公司
								<br>
								<input type="checkbox" name="checkinput" value="5">
								驾校联盟网
								<br>
								<input type="checkbox" name="checkinput" value="6">
								天太胜威国际投资团
								<br>
								<input type="checkbox" name="checkinput" value="7">
								天太胜威协会服务团
								<input type="hidden" name="interactDocInfo.web" id="webs"
									value="" />
							</td>
						</tr>
						<tr>
							<td align="right">
								对方访问类型：
							</td>
							<td align="left">
								<input type="radio" name="inputRadio" value="writable" checked />
								可修改
								<input type="radio" name="inputRadio" value="onlyread" />
								只读
								<input type="hidden" name="interactDocInfo.visitType"
									id="visitTypes" value="" />
							</td>
						</tr>
					</table>

					<div align="center" style="margin-bottom: 20px;">
						<input type="button" class="input-button" value="   传递   "
							onclick="passDraftToComPlat();" />
					</div>

				</center>
			</div>
		</form>

		<!-- 临时添加 -->
		<form name="addInfoForm" id="addInfoForm" method="post" action="">
			<div class="jqmWindow" style="width: 380px; right: 25%; top: 5%"
				id="jqModeladdInfo">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					社会接收人信息
					<div class="close">
					</div>
				</div>


				<div style="padding-left: 20px; padding-bottom: 5px;">
					<p>
						方式一：从历史记录中选择：
						<select id="historyinfo" style="width: 160px;">


						</select>
					</p>
					<br>
					<p>
						方式二：
						<a id="rightadd" href="#">马上添加</a>
					</p>
					<div style="padding-left: 40%;">
						<input type="button" class="input-button" id="addsubmit"
							value=" 确定 " />
					</div>
				</div>

				<div id="addform" style="display: none;">
					<span style="padding-left: 40%;">填写社会接收人信息</span>
					<center>
						<table cellspacing="5" cellpadding="2">
							<tr>
								<td align="right" style="width: 35%;">
									<span class="txt05"> * </span>收件人公司名称：
								</td>
								<td align="left">
									<input class="put3" id="scompany"
										name="socialMemberInfo.companyName" type="text" value="" />
								</td>
							</tr>
							<tr>
								<td align="right">
									<span class="txt05"> * </span>收件人姓名：
								</td>
								<td align="left">
									<input class="put3" id="smName" name="socialMemberInfo.smName"
										type="text" value="" />
								</td>
							</tr>
							<tr>
								<td align="right">
									<span class="txt05"> * </span> 收件人手机号码：
								</td>
								<td align="left">
									<input class="tel" id="stelphone"
										name="socialMemberInfo.telphone" type="text" value="" />
								</td>
							</tr>
						</table>


						<div align="center">
							<input type="button" class="input-button" value=" 添加 "
								onclick="addInfo();" />
							<input type="button" value=" 关闭 " class="input-button close" />
						</div>

					</center>
				</div>
			</div>
		</form>
		
		
		<%--<form name="SendForm" id="SendForm" method="post">--%>
			<%--<div class="jqmWindow"--%>
				<%--style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;"--%>
				<%--id="jqModelSend">--%>
				<%--<input type="submit" name="submit" style="display: none" />--%>
				<%--<div class="drag">--%>
					<%--<div id="titlem"></div>--%>
					<%--<div class="close"></div>--%>
				<%--</div>--%>
				<%--<center>--%>
					<%--<table width="100%" id="SearchTable2" cellspacing="20"--%>
						<%--cellpadding="20">--%>
						<%--<tr>--%>
							<%--<td align="right">--%>
								<%--收件人公司：--%>
							<%--</td>--%>
							<%--<td align="left">--%>
								<%--<select id="receiverCompanyID"--%>
									<%--onchange="changeCompany(this);" style="width: 200px;">--%>
									<%--<option value="">--%>

										<%--请选择收件人公司--%>
									<%--</option>--%>
								<%--</select>--%>

							<%--</td>--%>
						<%--</tr>--%>
						<%--<tr>--%>
							<%--<td align="right">--%>
								<%--收件人部门：--%>
							<%--</td>--%>
							<%--<td align="left">--%>
								<%--<select id="receiverDeptID" --%>
									<%--onchange="changeDept(this);" style="width: 200px;">--%>
									<%--<option value="">--%>

										<%--请选择收件人部门--%>
									<%--</option>--%>
								<%--</select>--%>
							<%--</td>--%>
						<%--</tr>--%>
						<%--<tr>--%>
							<%--<td align="right">--%>
								<%--收件人姓名：--%>
							<%--</td>--%>
							<%--<td align="left">--%>
								<%--<select id='receiverID'--%>
									<%--style="width: 200px;">--%>
									<%--<option value="">--%>

										<%--请选择收件人--%>
									<%--</option>--%>
								<%--</select>--%>
							<%--</td>--%>
						<%--</tr>--%>
					<%--</table>--%>

					<%--<div align="center" style="margin-top: 25px;">--%>
						<%--<input type="button" class="input-button" id="submitResult"--%>
							<%--value=" 提交 " />--%>
							<%----%>
						<%--<input type="button" class="input-button close" --%>
							<%--value=" 关闭 " />--%>
					<%--</div>--%>
				<%--</center>--%>
			<%--</div>--%>
		<%--</form>--%>
		<iframe name="hidden" width="0" height="0"></iframe>
		<!-- 显示人员 -->
		
		<div id="socialJqm2" class="jqmWindow"
			style="width: 75%; height: 350px; absolute; display: none; left: 20%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="type2" value="" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="300px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 28px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm2();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 300px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm2();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>

  <script type="text/javascript">
     $(function(){
         
$("#main").height($(window).height() - 5);

     });
</script>

	</body>
</html>
