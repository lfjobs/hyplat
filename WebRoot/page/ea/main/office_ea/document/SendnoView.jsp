<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>未分发公文查看</title>
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		

		
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/document/doc.css" />
		
		<script src="<%=basePath%>js/ea/office_ea/document/SendnoView.js?version=20210220"></script>
	
		
       <link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
			
				<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
					<style type="text/css">

.containerTableStyle {
	position: static;
	overflow: auto;
}

#tree1 {
	height: 380px;
	width: 250px;
	overflow: auto;
	background:#FFFFFF;
	white-space:nowrap;
}
a{text-decoration:none}
</style>

	</head>
	<body>
		<div class="operate">
			<input type="button" value="<<返回" class="greyBtn" id="noapprove"
				onclick="history.back();" />
			&nbsp;|&nbsp;
			<input type="button" value="分发" class="greyBtn" id="publish"
				onclick="Publish();" />
			&nbsp;|&nbsp;
		</div>
		<div id="wspv">

			<form name="wviewForm" method="post" action="" id="wviewForm">
				<div style="height: 1px;"></div>

				<input type="submit" name="submit" style="display: none" />

				<div style="padding-left: 45%; padding-bottom: 10px;">
					${document.title}
				</div>

				<table class="table" cellspacing="0" cellpadding="5" id="wspView">
					<tr>
						<td class="intitle" align="right" style="width: 15%;">
							<span>文件标题</span>：
						</td>
						<td align="left" colspan="2">
							<span id="title">${document.title} </span>
							<input type="hidden" value="${document.docId}"
								name="document.docId" />
						</td>

					</tr>
					<tr>
						<td align="right">
							文件编号：
						</td>
						<td align="left">
							<span id="docNum">${document.docNum}</span>

						</td>
						<td align="left">
							主题词：
							<span id="title">${document.theme}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							公文类型：
						</td>
						<td>
							<c:choose>
								<c:when test='${document.docType=="aa"}'>董事会会议决定文件</c:when>
								<c:when test='${document.docType=="bb"}'>董事长办公室文件</c:when>
								<c:when test='${document.docType=="cc"}'>总裁办公室文件</c:when>
								<c:when test='${document.docType=="dd"}'>总部人事处文件</c:when>
								<c:when test='${document.docType=="ee"}'>总部办公室文件</c:when>
								<c:when test='${document.docType=="ff"}'>总部财务处文件</c:when>
								<c:when test='${document.docType=="gg"}'>总部教务(生产)处文件</c:when>
								<c:when test='${document.docType=="hh"}'>总部营销处文件</c:when>
								<c:when test='${document.docType=="jj"}'>总部教务部文件</c:when>
								<c:otherwise>总部服务(创收)平台</c:otherwise>
							</c:choose>

						</td>
						<td align="left">
							缓&nbsp;&nbsp;&nbsp;&nbsp;急：
							<c:choose>
								<c:when test='${document.emergencyType=="j"}'>急件</c:when>
								<c:when test='${document.emergencyType=="p"}'>普通</c:when>
								<c:otherwise>特急</c:otherwise>
							</c:choose>

						</td>


					</tr>
					<tr class="trtip">
						<td align="right">
							合同有效期：
						</td>
						<td colspan="2" align="left">
							<span>${fn:substring(document.startValidity, 0,
								10)}至${fn:substring(document.endValidity, 0, 10)}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							拟稿人公司：
						</td>
						<td align="left">
							<span id="companyName">${document.companyName}</span>

						</td>

						<td align="left">
							拟稿人部门：
							<span id="drafterID">${document.deptNameOfDraft}</span>

						</td>

					</tr>
					<tr>
						<td align="right">
							拟&nbsp;&nbsp;稿&nbsp;人：
						</td>
						<td align="left">
							<span id="drafterID">${document.drafterName}</span>

						</td>
						<td align="left">
							拟稿人岗位：
							<span id="postName">${document.postName}</span>

						</td>

					</tr>

					<tr>
						<td align="right">
							审批人公司：
						</td>
						<td align="left">
							<span id="comNameofSub">${document.comNameofSub}</span>

						</td>

						<td align="left">
							审批人部门：
							<span id="deptNameofSub">${document.deptNameofSub}</span>

						</td>


					</tr>
					<tr>
						<td align="right">
							审&nbsp;批&nbsp;人：
						</td>
						<td align="left" colspan="2">
							<span id="subscriberName">${document.subscriberName}</span>

						</td>

					</tr>
					<tr>
						<td align="right">
							拟稿人意见：
						</td>
						<td colspan="2">
							<span id="drafterComment">${document.drafterComment}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							审批人意见：
						</td>
						<td colspan="2">
							<span id="subscriberComment">${document.subscriberComment}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							盖章人意见：
						</td>
						<td colspan="2">
							<span id="sealComment">${document.sealerComment}</span>

						</td>
					</tr>
					<tr>
						<td align="right">
							转交分发：
						</td>
						<td colspan="2">
							<div style="height: 22px; line-height: 22px;">
								<img src="<%=basePath%>images/admin_images/cz.gif" width="16"
									height="16" />
								接收人：
								<input type="text" style="width: 40%; height: 80%;"
									id="choosePss" readonly />

								<input type="button" value="选择人员" class="greyBtn"
									onclick="selectPeople();" />
								<input type="button" value="清除" class="greyBtn"
									onclick="clearPeople()" />
								<input type="hidden" value="${document.transfer}"
									name="document.transfer" id="transfer" />
								<input type="hidden" value="${document.load}"
									name="document.load" id="load" />
								<input type="hidden" value="${document.print}"
									name="document.print" id="print" />
								<input type="hidden" value="${document.share}"
									name="document.share" id="share" />
								<input type="hidden" value="${document.pub}" name="document.pub"
									id="pub" />
								<input type="hidden" name="readers" id="readers" value="" />
							</div>
					</tr>

					<tr>
						<td align="right">
							<img src="<%=basePath%>images/ea/office/document/attach1.png"
								width="16" height="16" />
							正文附件：
						</td>
						<td colspan="2">
							<s:iterator id="doc" value="attachlist">
								<a href="#" onclick="OpenOffice2('${filePath}','${fileType}','1','未分发')">${fileShowName}.${ext}</a>
							</s:iterator>
							<br>
						</td>
					</tr>
					<tr>
						<td align="right">
							添加意见：
						</td>
						<td colspan="2">
							<textarea style="width: 80%;" name="comment" id="publishComment"></textarea>

						</td>
					</tr>
				</table>
			</form>
		</div>
		
		
			<div class="jqmWindow" id="zj"
			style="width: 800px; height: 450px; left: 20%; top: 1%">
			<div>
				<div class="contentbannb">
					<div class="drag">
						组织机构树

					</div>
				</div>
			</div>
			<table style="width: 100%; height: 450px;" cellpadding="0"
				cellspacing="0" style="margin-top: 2px;">
				<tr>
					<td width="30%" align="left" valign="top">
						<div id="tree1"></div>
					</td>
					<td width="70%" align="left" valign="top">
						<table style="width: 450px; height: 350px;" align="center"
							cellpadding="0" cellspacing="2"
							style="margin-top: 5px; margin-bottom: 5px;">
							<tr>
								<td width="200" height="20" class="txt01" colspan="3">
									姓名/手机号
									<input type="text" id="searchc"/>
									<input type="button"  value=" 搜索 " class="input-button" id="search"/>
								</td>

							</tr>
							<tr>
								<td width="200" height="20" class="txt01">
									备选人员
								</td>
								<td width="50">
									&nbsp;
								</td>
								<td width="200" align="left" class="txt01">
									已选人员
								</td>
							</tr>
							<tr>
								<td height="137">
									<select name="leftfields" multiple="multiple" id="leftfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>

								</td>
								<td width="250" align="center">
									<div>
										<input type="button" class="input-button" id="query_add"
											value=" 添加 " />
									</div>
									<div>
										<input type="button" class="input-button" id="query_delete"
											value=" 删除 " />
									</div>
								</td>
								<td>
									<select name="rightfields" multiple="multiple" id="rightfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>
								</td>
								<td width="100">
									&nbsp;
								</td>
							</tr>


							<tr>
								<td height="30" colspan="3" align="center">
									<br />
									<input type="button" class="input-button" id="confirm"
										value=" 确定 " onclick="submit();" />
									<input type="button" class="input-button" id="closed"
										value=" 关闭 " onclick="closed();" />
									<a href="#" id="ttttt" target="_self"></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
			<form id="pubForm" name="pubForm">
			<div class="jqmWindow" id="jqModelpub">
				<input type="submit" name="submit" style="display: none" />
				<input type="hidden" name="document.docId" id="docID" value="" />
				<input type="hidden" value=""
					name="document.transfer" id="transfer" />
				<input type="hidden" value="" name="document.load"
					id="load" />
				<input type="hidden" value="" name="document.print"
					id="print" />
				<input type="hidden" value="" name="document.share"
					id="share" />
				<input type="hidden" value="" name="document.pub"
					id="pub" />
				<input type="hidden" name="readers" id="readers" value="" />

			</div>
		</form>
		<iframe name="hidden" width="0" height="0"></iframe>

		<script type="text/javascript">
   var basePath = '<%=basePath%>';
   var module = '<%=session.getAttribute("module")%>';
  
         </script>
	</body>
</html>
