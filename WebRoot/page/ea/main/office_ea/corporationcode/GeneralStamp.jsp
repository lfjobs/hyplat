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
		<title>企业普通印章管理</title>

		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/validate.css" />
		<script
			src="<%=basePath%>js/ea/office_ea/corporationcode/GeneralStamp.js"></script>
		<script type="text/javascript">
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
							印章名称
						</th>
						<th width="100" align="center">
							印章内容
						</th>
						<th width="100" align="center">
							授予年度
						</th>
						<th width="100" align="center">
							注册年度
						</th>
						<th width="100" align="center">
							扫描附件
						</th>
						<th width="100" align="center">
							责任人
						</th>
						<th width="100" align="center">
							印章类型
						</th>
						<th width="200" align="center">
							备注
						</th>
						<th width="90" align="center">
							部门
						</th>
						<th width="170" align="center">
							公司
						</th>
						<th width="170" align="center">
							是否用于学员证
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
								<s:if test="scanningAccessories==null||scanningAccessories==''">无</s:if>
								<s:else>
									<span id="look" onclick="lookImage('${scanningAccessories}');"><a
										href="#">查看</a> </span>
								</s:else>
							</td>
							<td class="td_bg01">
								<span id="responsibleName">${responsibleName}</span>
								<span style="display: none;" id="responsibleID">${responsibleID}</span>

							</td>
							<td class="td_bg01">
								<span id="stampType">${stampType}</span>
							</td>
							<td class="td_bg01">
								<span id="stampNote">${stampNote}</span>
								<span id="enterpriseStampID" style="display: none">${enterpriseStampID}</span>
								<span id="enterpriseStampKey" style="display: none">${enterpriseStampKey}</span>
								<span id="scanningAccessories" style="display: none">${scanningAccessories}</span>
							</td>
							<td class="td_bg01">

								<span id="organizationName">${organizationName}</span>

							</td>
							<td class="td_bg01">

								<span id="companyName">${companyName}</span>

							</td>
							
							<td class="td_bg01">

								<c:choose>
									<c:when test="${isStuStamp=='1'}">是</c:when>
									<c:otherwise>否</c:otherwise>
								</c:choose>
								<input type="hidden" id="isStuStamp" value="${isStuStamp}" />

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
					value="ea/enterprisestamp/ea_getListGeneralStamp.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}"></c:param>
			</c:import>

		</div>
		<iframe src="" name="main" marginwidth="0"
			style="width: 100%; height: 0;" scrolling="no" marginheight="0"
			frameborder="0" id="mainframe" border="0" framespacing="0"
			noresize="noResize" vspale="0">
		</iframe>

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
								<input name="enterpriseStamp.gore" type="hidden" value="g" />
								<input name="type" type="hidden" value="${type}" />
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


		<!--查看 -->
		<div class="jqmWindow" style="top: 10%; right: 25%; width: 650px;"
			id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<div class="drag">
					普通印章
					<div class="close"></div>
				</div>
				<input type="submit" name="submit" style="display: none" />
				<center>
					<div class="content">
						<table width="100%" id="stafftable" cellpadding="0"
							cellspacing="10" style="margin-top: 0px; margin-bottom: 5px;">
							<tr>
								<td align="right" style="width: 20%;">
									编号：
								</td>
								<td align="left">
									<input name="enterpriseStamp.enterpriseStampCode" type="text"
										id="enterpriseStampCode" size="20" />
								</td>
								<td align="right">
									印章名称：
								</td>
								<td align="left">
									<input name="enterpriseStamp.stampName" type="text"
										id="stampName" size="20" />
								</td>
							</tr>

							<tr>
								<td align="right">
									印章类型：
								</td>
								<td align="left">
									<input id="stampType" type="text" class="input"
										name="enterpriseStamp.stampType" size="20" />
								</td>
								<td align="right">
									授予年度：
								</td>
								<td align="left">
									<input id="awardedAnnual" type="text" class="input"
										name="enterpriseStamp.awardedAnnual" size="20"
										onfocus="date(this);" />
								</td>
							</tr>
							<tr>
								<td align="right">
									注册年度：
								</td>
								<td align="left">
									<input name="enterpriseStamp.registeredAnnual" type="text"
										id="registeredAnnual" size="20" onfocus="date(this);" />
								</td>
								<td align="right">
									扫描附件：
								</td>
								<td align="left">
									<input name="photo" type="file" class="input" id="UploadFile"
										size="10" />
									<input name="enterpriseStamp.scanningAccessories" type="hidden"
										class="fileNum" id="scanningAccessories" />
								</td>
							</tr>
							<tr>
								<td align="right">
									责任人：
								</td>
								<td align="left">
									<input type="text" id="responsibleName" name="responsibleName"
										size="20" readonly />
									<input type="hidden" name="enterpriseStamp.responsibleID"
										id="responsibleID" />
									&nbsp;
									<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
										onclick="importGY('ea/documentcommon/ea_getStaffformalList.jspa')" />
								</td>
							</tr>
							<tr>
								<td wigth="124" align="right">
									印章内容：
								</td>
								<td align="left" colspan="4">
									<input id="stampContent" maxlength="50" type="text"
										class="input ckTextLength" name="enterpriseStamp.stampContent"
										style="width: 380px" />
									<input name="enterpriseStamp.enterpriseStampID" type="hidden"
										id="enterpriseStampID" size="20" />
									<input type="hidden" name="enterpriseStamp.enterpriseStampKey"
										id="enterpriseStampKey" />
								</td>
							</tr>
							<tr>
								<td align="right">
									备注：
								</td>
								<td colspan="4" align="left">
									<input id="stampNote" maxlength="250" type="text"
										class="input ckTextLength" name="enterpriseStamp.stampNote"
										style="width: 380px" />
							</tr>
							<tr>
								<td align="center" colspan="4">
									<input name="enterpriseStamp.gore" type="hidden" value="g" />
									<input type="button" class="input-button"
										style="cursor: pointer; width: 80px;" id="tosave" value=" 保存 " />
									<input type="button" class="input-button JQueryreturn"
										style="cursor: pointer; width: 80px;" value="取消" />


								</td>
							</tr>
						</table>
					</div>
				</center>

			</form>
		</div>
		<iframe name="hidden" width="0" height="0"></iframe>
		<div id="socialJqm" class="jqmWindow"
			style="width: 60%; height: 250px; absolute; display: none; left: 20%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
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
