<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="docCommon.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>草稿箱</title>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/doc.css" />
		<script
			src="<%=basePath%>js/ea/office_ea/document/documentDraftList.js?version=20210220"></script>

	<script type="text/javascript">
     var  journalNum = "${param.journalNum}";
     var projectName = "${param.projectName}";
	
	</script>

	</head>
	<body>

		<!--拟稿 -->

		<div id="draft">
			<table class="draft0">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="30" align="center">
							序号
						</th>
						<th class="thnum" width="70" align="center">
							<span>公文编号</span>
						</th>
						<th class="thtitle" width="200" align="center">
							<span>文件标题</span>
						</th>
						<th width="70" align="center">
							主题词
						</th>
						<th width="130" align="center">
							公文类型
						</th>
						<th width="70" align="center">
							缓急
						</th>
						<th class="trtip" width="170" align="center">
							合同有效期
						</th>	
						<th class="trtip" width="300" align="center">
							甲方
						</th>
						<th class="trtip" width="300" align="center">
							乙方
						</th>	
						<th width="70" align="center">
							申报人
						</th>
						<th width="70" align="center">
							申报人部门
						</th>
						<th width="170" align="center">
							申报单位名称
						</th>
						<th width="150" align="center">
							起草时间
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator id="docList" value="pageForm.list">
						<tr class="docs" id="${docId}">
							<td class="td_bg01">
								<input type="checkbox" name="checkinput" class="JQuerypersonvalue" id="${docId}"
									value="${docId}" />
								<input type="hidden" value="${receiverID}" id="receiverID">
							</td>
							<td class="td_bg01">
								<span><%=number%></span>
							</td>
							<td class="td_bg01">
								<span id="docNum">${docNum}</span>
							</td>
							<td class="td_bg01">
								<span id="title">${title}</span>
							</td>
							<td class="td_bg01">
								<span id="theme">${theme}</span>
							</td>

							<td class="td_bg01">
								<c:choose>
									<c:when test='${docType=="aa"}'>董事会会议决定文件</c:when>
									<c:when test='${docType=="bb"}'>董事长办公室文件</c:when>
									<c:when test='${docType=="cc"}'>总裁办公室文件</c:when>
									<c:when test='${docType=="dd"}'>总部人事处文件</c:when>
									<c:when test='${docType=="ee"}'>总部办公室文件</c:when>
									<c:when test='${docType=="ff"}'>总部财务处文件</c:when>
									<c:when test='${docType=="gg"}'>总部教务(生产)处文件</c:when>
									<c:when test='${docType=="hh"}'>总部营销处文件</c:when>
									<c:when test='${docType=="jj"}'>总部教务部文件</c:when>
									<c:otherwise>总部服务(创收)平台</c:otherwise>
								</c:choose>
							</td>
							<td class="td_bg01">
								<c:choose>
									<c:when test='${emergencyType=="p"}'>普通</c:when>
									<c:when test='${emergencyType=="j"}'>急件</c:when>
									<c:when test='${emergencyType=="t"}'>特急</c:when>
									<c:otherwise>${emergencyType}</c:otherwise>
								</c:choose>
								<span style="display:none;" id="emergencyType">${emergencyType}</span>

							</td>
							<td class="trtip">
								<s:if test="startValidity==null||startValidity==''">
							   无指定
							 </s:if>
								<s:else>
									<span>${fn:substring(startValidity,
										0,10)}至${fn:substring(endValidity, 0, 10)}</span>
								</s:else>

							</td>	
							<td class="trtip">
								
							   <span id="partyAName">${partyAName}</span>
                               <span id="partyAstaffnames">${partyAstaffnames}</span>
                               <s:if test="staffIdentityCardA!=null">
							      <span id="staffIdentityCardA">(${staffIdentityCardA})</span>
							   </s:if>

							</td>
							<td class="trtip">
								
							   <span id="partyBName">${partyBName}</span>
							   <span id="partyBstaffnames">${partyBstaffnames}</span>
							   <s:if test="staffIdentityCardB!=null">
							      <span id="staffIdentityCardB">(${staffIdentityCardB})</span>
							   </s:if>
							   

							</td>
							
							<td class="td_bg01">
								<span id="drafterID">${drafterName}</span>
							</td>
							<td class="td_bg01">
								<span id="deptNameOfDraft">${deptNameOfDraft}</span>
							</td>
							<td class="td_bg01">
								<span id="companyName">${companyName}</span>
							</td>
							<td class="td_bg01">
								<span id="draftTime">${fn:substring(draftTime,0,19)}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/documentinfo/ea_getDocDraftList.jspa?pageNumber=${pageNumber}&search=${search}&searchType=${searchType}">
				</c:param>
			</c:import>
		</div>





		<form name="SugForm" id="SugForm">
			<div class="jqmWindow"
				style="width: 360px; height: 210px; right: 15%; top: 10%;"
				id="jqModelSend23">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查看评论
					<div class="close">
					</div>
				</div>
				<div
					style="width: 360px; height: 380px; overflow: auto; margin-top: 10px;">
					<table width="300" id="tablesug">
					</table>
					<input type="text" id="htt" />
				</div>

			</div>
		</form>



		<form name="CommonForm" id="CommonForm">
			<div class="jqmWindow" id="jqModelCommon">
				<input type="submit" name="submit" style="display: none" />
				<div>
					<input type="hidden" name="docId" value="" id="docId" />
					<input type="hidden" name="type" value="" id="type" />
				</div>

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
					<table cellspacing="8" cellpadding="2">
						<tr>
							<td align="right" style="width: 30%;">
								社会接收人：
							</td>
							<td align="left">
								<input id="socialName" type="text" value="" readonly="readonly" />
								<input type="hidden" name="document.social" id='socials'
									value="" />
								<input type="hidden" name="interactDocInfo.smId" id="social2"
									value="" />
								<input type="hidden" name="document.docId" id="docIds" value="" />
								<input type="button" class="input-button" value=" 系统中选择 "
									onclick="importGY('ea/documentcommon/ea_getSocialInfoList.jspa');" />
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
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 5%"
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
					<div  style="padding-left: 40%;">
						<input type="button" class="input-button" id="addsubmit" value=" 确定 " />
					</div>
				</div>

				<div id="addform" style="display: none;">
					<span style="padding-left: 40%;">填写社会接收人信息</span>
					<center>
						<table cellspacing="5" cellpadding="2">
							<tr>
								<td align="right" style="width:35%;">
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

		<div id="socialJqm" class="jqmWindow">
			<div class="socialnext">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 " />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 " />
			</div>
		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
		<iframe name="hidden" width="100%" height="0"></iframe>

		<script type="text/javascript">
    setTimeout(function(){ 
	    $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
    },100);
    
    $(window).resize(function(){ 
		setTimeout(function(){ 					    
		   $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
		},100);
    }); 
</script>
	</body>
</html>
