<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>企业印章管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/validate.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>

		<script
			src="<%=basePath%>js/ea/office_ea/corporationcode/EnterpriseStamp.js"></script>
		<script type="text/javascript">
		var companyIDs='${document.companyID}';
   var select = '01';
   var  enterpriseStampID = '';
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
   var  search='${search}';
   var  token=0;
   var  personurl='';
   var   stampName='';
   var type='${type}';
</script>

<style type="text/css">
body,div,ul,li {
	margin: 0;
	padding: 0;
}

ul {
	list-style-type: none;
	overflow: hidden;
	zoom: 1;
}

ul li {
	float: left;
	border: 0px red solid;
	border-bottom: none;
	width: 100px;
	height: 50px;
	text-align: center;
	line-height: 50px;
}

.type {
	background-color: grey;
}

td
  {
  white-space: nowrap;
  }

</style>
	</head>
	<body>
		<div id="main_main" class="main_main">
			<table class="address">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="30" align="center">
							序号
						</th>
						<th width="93" align="center">
							编号
						</th>
						<th width="200" align="center">
							名称
						</th>
						<th width="100" align="center">
							内容
						</th>
						<th width="100" align="center">
							授予年度
						</th>
						<th width="100" align="center">
							注册年度
						</th>
						<th width="100" align="center">
							责任人
						</th>
						<th width="100" align="center">
							类型
						</th>
						<th width="150" align="center">
							创建时间
						</th>
						<th width="200" align="center">
							备注
						</th>
						<th width="200" align="center">
							使用状态
						</th>
						<th width="90" align="center">
							部门
						</th>
						<th width="170" align="center">
							公司
						</th>


					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${enterpriseStampID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${enterpriseStampID}" />
							</td>
							<td class="td_bg01">
								<span><%=number%></span>
							</td>
							<td class="td_bg01">
								<span id="enterpriseStampCode" class="datas">${enterpriseStampCode}</span>
							</td>
							<td class="td_bg01">
								<span id="stampName">${stampName}</span>
							</td>
							<td class="td_bg01">
								<span id="stampContent">${stampContent}</span>
							</td>
							<td class="td_bg01">
								<span id="awardedAnnual" class="datas">${fn:substring(awardedAnnual,
									0, 10)}</span>
							</td>
							<td class="td_bg01">
								<span id="registeredAnnual" class="datas">${fn:substring(registeredAnnual,
									0, 10)}</span>
							</td>
							<td class="td_bg01">
								<span id="responsibleName">${responsibleName}</span>
								<span style="display: none;" id="responsibleID">${responsibleID}</span>

							</td>
							<td class="td_bg01">
								<span id="type3">${type}</span>

							</td>
							<td class="td_bg01">

								<span id="createTime">${createTime}</span>

							</td>
							<td class="td_bg01">
								<span id="stampNote">${stampNote}</span>
								<span id="enterpriseStampID" style="display: none">${enterpriseStampID}</span>
								<span id="enterpriseStampKey" style="display: none">${enterpriseStampKey}</span>
								<span id="scanningAccessories" style="display: none">${scanningAccessories}</span>
							</td>
							<td class="td_bg01">
								<c:choose>
									<c:when test="${useStatus=='use'}">使用中</c:when>
									<c:otherwise>已停用</c:otherwise>
								</c:choose>
								<input type="hidden" id="useStatus" value="${useStatus}" />


							</td>
							<td class="td_bg01">

								<span id="organizationName">${organizationName}</span>

							</td>
							<td class="td_bg01">

								<span id="companyName">${companyName}</span>

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
					value="ea/enterprisestamp/ea_getListEnterpriseStamp.jspa?pageNumber=${pageNumber}&search=${search}&gore=e&type=${type}"></c:param>
			</c:import>
		</div>




		<!--搜索窗口 -->
		<div class="jqmWindow " style="width: 300px; right: 35%; top: 20%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<center>
					<table id="cataffSearchTable" cellspacing="5">
						<tr>
							<td style="width: 20%;" align="right">
								名称：
							</td>
							<td>
								<input name="enterpriseStamp.gore" type="hidden" value="e" />
								<input name="type" type="hidden" value="${type}"
									id="typesummary" />
								<input name="enterpriseStamp.stampName" style="width: 150px;" />
							</td>
						</tr>

						<tr class="company">
							<td style="width: 20%;" align="right">
								公司：
							</td>
							<td>
								<select name="enterpriseStamp.companyID" id="companyID"
									onchange="changeCompany(this)" style="width: 155px;">

								</select>

							</td>
						</tr>
						<tr class="org">
							<td style="width: 20%;" align="right">
								部门：
							</td>
							<td>
								<select name="enterpriseStamp.organizationID"
									id="organizationID" style="width: 155px;">
									<option value="">
										请先选择公司
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="button" class="input-button" id="tosearch"
									value=" 查询 " />
								<input name="search" type="hidden" value="search" />
							</td>
						</tr>
					</table>
				</center>

			</form>
		</div>

		<!--添加修改印章 -->

		<div class="jqmWindow" style="top: 10%; right: 25%; width: 600px;"
			id="jqModel">
			<div class="drag">
				企业印章管理
				<div class="close" id="close"></div>
			</div>
			<div id="butdiv">
				<ul>
					<li id='tab_1' class='t_1' onmouseover='stamp()'>
						<span class="type" id="stamptype">印章</span>
					</li>
					<li onmouseover='sign();'>
						<span id="signtype">签名</span>
					</li>
				</ul>
			</div>
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<center>
					<div class="content">
						<div class="contentbannb">
						</div>
						<table width="100%" id="stafftable" cellpadding="0"
							cellspacing="0" style="margin-top: 0px; margin-bottom: 5px;">
							<tr>
								<td align="center">
									<table width="100%" cellpadding="0" cellspacing="5"
										id="stafftable2" style="margin-top: 0px; margin-bottom: 5px;">
										<tr>
											<td style="width: 20%;" align="right">
												编号：
											</td>
											<td align="left" style="width: 30%;">
												<input name="enterpriseStamp.enterpriseStampCode"
													type="text" id="enterpriseStampCode" style="width: 140px;" />
											</td>
											<td align="right" style="width: 20%;">
												印章名称：
											</td>
											<td align="left" style="width: 30%;">
												<input name="enterpriseStamp.stampName" type="text"
													id="stampName" style="width: 140px;" />
											</td>
										</tr>
										<tr>
											<td align="right">
												印章类型：
											</td>
											<td align="left">
												<input id="stampType" type="text" class="input"
													name="enterpriseStamp.stampType" style="width: 140px;" />
											</td>
											<td align="right">
												授予年度：
											</td>
											<td align="left">
												<input id="awardedAnnual" type="text" class="input"
													name="enterpriseStamp.awardedAnnual" style="width: 140px;"
													onfocus="date(this);" />
											</td>
										</tr>
										<tr>
											<td align="right">
												注册年度：
											</td>
											<td style="width: 100px;" align="left">
												<input name="enterpriseStamp.registeredAnnual" type="text"
													id="registeredAnnual" style="width: 140px;"
													onfocus="date(this);" />
											</td>
											<td align="right">
												扫描附件：
											</td>
											<td align="left">
												<input name="photo" type="file" class="input"
													id="UploadFile" style="width: 150px;" />
												<input name="enterpriseStamp.scanningAccessories"
													type="hidden" class="fileNum" id="scanningAccessories" />
											</td>
										</tr>
										<tr style="display: none;" id="psw">
											<td colspan="4" align="right">

												<input type="button" value="修改印章密码" id="correctpsw"
													onclick="updatePsw();" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td align="right">
												责任人：
											</td>
											<td align="left">
											 <nobr>
												<input type="text" id="responsibleName"
													name="responsibleName" style="width: 140px;" readonly />
												<input type="hidden" name="enterpriseStamp.responsibleID"
													id="responsibleID" />
												&nbsp;
												<img src="<%=basePath%>images/r_8_12.gif"
													style="cursor: hand;"
													onclick="importGY('ea/documentcommon/ea_getStaffformalList.jspa','seal')" />
											</nobr>
											</td>
										</tr>
										<tr>
											<td align="right">
												印章内容：
											</td>
											<td colspan="3" align="left">
												<input id="stampContent" maxlength="50" type="text"
													class="input ckTextLength"
													name="enterpriseStamp.stampContent" style="width: 340px" />
												<input name="enterpriseStamp.enterpriseStampID"
													type="hidden" id="enterpriseStampID" />
												<input type="hidden"
													name="enterpriseStamp.enterpriseStampKey"
													id="enterpriseStampKey" />
												<input type="hidden" name="enterpriseStamp.type" id="type"
													value="stamp" />
												<input name="enterpriseStamp.useStatus" type="hidden"
													id="useStatus2" />
											</td>

										</tr>
										<tr>
											<td align="right">
												备注：
											</td>
											<td colspan="3" align="left">
												<input id="stampNote" maxlength="250" type="text"
													class="input ckTextLength" name="enterpriseStamp.stampNote"
													style="width: 340px" />
										</tr>
										<tr>
											<td colspan="4" align="center">
												<input name="enterpriseStamp.gore" type="hidden" value="e" />
												<input type="button" class="input-button"
													style="cursor: pointer; width: 80px;" id="tosavestamp"
													value="" onclick="updateUseStatus();" />
												<input type="button" class="input-button"
													style="cursor: pointer; width: 80px;" id="tosave"
													value=" 保存 " />
												<input type="button" class="input-button JQueryreturn"
													style="cursor: pointer; width: 80px;" value="取消" />

											</td>


										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>

				</center>
			</form>

			<form name="cstaffFormsign" id="cstaffFormsign" method="post"
				enctype="multipart/form-data" style="display: none;">


				<input type="submit" name="submit" style="display: none" />
				<center>
					<div class="content">

						<div class="contentbannb">
						</div>

						<table id="stafftable" cellpadding="0" cellspacing="0"
							style="width: 100%; margin-top: 0px; margin-bottom: 5px;">
							<tr>
								<td align="center">
									<table width="100%" cellpadding="0" cellspacing="5"
										id="stafftable2" style="margin-top: 0px; margin-bottom: 5px;">
										<tr>
											<td style="width: 20%;" align="right">
												编号：
											</td>
											<td align="left" style="width: 30%;">
												<input name="enterpriseStamp.enterpriseStampCode"
													type="text" id="enterpriseStampCode" style="width: 140px;" />

											</td>
											<td align="right" style="width: 20%;">
												签名名称：
											</td>
											<td align="left" style="width: 30%;">
												<input name="enterpriseStamp.stampName" type="text"
													id="stampName" style="width: 140px;" />
											</td>
										<tr>
											<td align="right">
												签名类型：
											</td>
											<td align="left">
												<input id="stampType" type="text" class="input"
													name="enterpriseStamp.stampType" style="width: 140px;" />

											</td>
											<td align="right">
												授予年度：

											</td>
											<td align="left">
												<input id="awardedAnnual" type="text" class="input"
													name="enterpriseStamp.awardedAnnual" onfocus="date(this);"
													style="width: 140px;" />
											</td>
										</tr>
										<tr>
											<td align="right">
												注册年度：

											</td>
											<td align="left">
												<input name="enterpriseStamp.registeredAnnual" type="text"
													id="registeredAnnual" onfocus="date(this);"
													style="width: 140px;" />
											</td>
											<td align="right">
												扫描附件：
											</td>
											<td style="width: 180px;" align="left">
												<input name="photo" type="file" class="input"
													id="UploadFile" style="width: 150px;" />
												<input name="enterpriseStamp.scanningAccessories"
													type="hidden" class="fileNum" id="scanningAccessories" />
											</td>
										</tr>
										<tr style="display: none;" id="psw2">
											<td colspan="4" align="right">

												<input type="button" value="修改签名密码" id="correctpsw"
													onclick="updatePsw();" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td align="right">
												责任人：
											</td>
											<td align="left">
											<nobr>
												<input type="text" id="responsibleName"
													name="responsibleName" style="width: 140px;" readonly />
												<input type="hidden" name="enterpriseStamp.responsibleID"
													id="responsibleID" />
												&nbsp;
												<img src="<%=basePath%>images/r_8_12.gif"
													style="cursor: hand;"
													onclick="importGY('ea/documentcommon/ea_getStaffformalList.jspa','sign')" />
											</nobr>
											</td>
										</tr>
										<tr>
											<td align="right">
												签名内容：
											</td>
											<td colspan="3" align="left">
												<input id="stampContent" maxlength="50" type="text"
													class="input ckTextLength"
													name="enterpriseStamp.stampContent" style="width: 340px" />
												<input name="enterpriseStamp.enterpriseStampID"
													type="hidden" id="enterpriseStampID" />
												<input type="hidden"
													name="enterpriseStamp.enterpriseStampKey"
													id="enterpriseStampKey" />
												<input type="hidden" name="enterpriseStamp.type" id="type2"
													value="sign" />
											</td>
										</tr>
										<tr>
											<td align="right">
												备注：
											</td>
											<td colspan="3" align="left">
												<input id="stampNote" maxlength="250" type="text"
													class="input ckTextLength" name="enterpriseStamp.stampNote"
													style="width: 340px" />
										</tr>
										<tr>
											<td colspan="4" align="center">
												<input name="enterpriseStamp.gore" type="hidden" value="e" />
												<input type="button" class="input-button"
													style="cursor: pointer; width: 80px;" id="tosavesign"
													value=" " onclick="updateUseStatus();" />
												<input type="button" class="input-button"
													style="cursor: pointer; width: 80px;" id="tosave3"
													value=" 保存 " />
												<input type="button" class="input-button JQueryreturn"
													style="cursor: pointer; width: 80px;" value="取消" />

											</td>


										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</center>
			</form>
		</div>

		<!--密码修改 -->
		<div class="jqmWindow"
			style="top: 20%; right: 35%; width: 300px; height: 200px;"
			id="jqModelpsw">
			<form name="cstaffFormpsw" id="cstaffFormpsw" method="post"
				enctype="multipart/form-data">
				<div class="drag">
					密码修改
				</div>
				<input type="submit" name="submit" style="display: none" />
				<div>
					<div class="contentbannb">
					</div>
					<table border="0" id="stafftable" align="center" cellpadding="0"
						cellspacing="5" style="margin-top: 10px; margin-bottom: 5px;">
						<tr>
							<td align="right">
								旧密码：
							</td>
							<td>
								<input type="password" name="oldpsw" id="oldpsw" value="" />
							</td>
						</tr>
						<tr>
							<td align="right">
								新密码：
							</td>
							<td>
								<input type="password" name="newpsw" id="newpsw" value="" />
							</td>
						</tr>

						<tr>
							<td align="right">
								确认新密码：
							</td>
							<td>
								<input type="password" name="confirmnewpsw" id="confirmnewpsw"
									value="" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input type="button" class="input-button"
									style="cursor: pointer; width: 80px;" id="tosave2" value=" 确定 " />
								<input type="button" class="input-button"
									style="cursor: pointer; width: 80px;" value="关闭"
									onclick="cancelpsw();" />
							</td>
						</tr>
					</table>
				</div>
			</form>

		</div>
		<iframe name="hidden" width="0" height="0"></iframe>
		<iframe src="" name="main" marginwidth="0"
			style="height: 0; width: 100%;" scrolling="no" marginheight="0"
			frameborder="0" id="mainframe" border="0" framespacing="0"
			noresize="noResize" vspale="0">
		</iframe>

		<div id="socialJqm" class="jqmWindow"
			style="width: 60%; height: 250px; absolute; display: none; left: 20%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="type" value="" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="210px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 28px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 300px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
		<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 30 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			$("#mainframe").css({"height": _height / 2 - 30 + "px"});
		    }else{		    
			$(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
			$("#mainframe").css({"height": 0 + "px"});
		    }
		},100);
	    }); 
   });
</script>
	</body>
</html>
