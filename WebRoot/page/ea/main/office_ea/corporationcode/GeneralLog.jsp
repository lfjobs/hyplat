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
		<title>普通印章日志管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/corporationcode/GeneralLog.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/common_word.js"></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search ='${search}';  
         var  stampLogID = "";
         var  token=0;
         var  enterpriseStampID='${stampLog.enterpriseStampID}';
         var  select =1;
         var notoken=0;
         var type = '${type}';
	</script>
		<style type="text/css">
a {
	text-decoration: none;
}
</style>

	</head>



	<body>
		<form name="staffappraisalForm" enctype="multipart/form-data"
			id="staffappraisalForm" method="post">
			<s:token></s:token>
			<input type="hidden" name="stampLog.enterpriseStampID"
				value="${stampLog.enterpriseStampID}" />

			<input type="submit" name="submit" style="display: none" />



			<div id="main_main" class="main_main">
				<table class="registration">
					<thead>
						<tr>
							<th width="35" align="center">
								选择
							</th>
							<th width="35" align="center">
								序号
							</th>
							<th width="150" align="center">
								盖章日期
							</th>
							<th width="100" align="center">
								盖章人编号
							</th>
							<th width="130" align="center">
								盖章人
							</th>
							<th width="130" align="center">
								签署文件
							</th>
							<th width="140" align="center">
								扫描附件
							</th>
							<th width="150" align="center">
								创建时间
							</th>
							<th width="90" align="center">
								创建人
							</th>

						</tr>
					</thead>
					<tbody>

						<tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${stampLogID}" />
							</td>
							<td class="td_bg01">

							</td>
							<td class="td_bg01">
								<input name="stampDateStr" id="stampDate"
									onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d %H:%m'})"
									size="12" />
							</td>
							<td class="td_bg01">

							</td>
							<td class="td_bg01">
								<input type="hidden" name="contractorsId" value="" />
								<input type="text" value="" name="contractors" id="contractors"
									size="7" readonly />
								<a href="#" onclick="importGY();">选择人员</a>
							</td>
							<td class="td_bg01">
								<input name="fileName" id="fileName2" size="15" />
							</td>
							<td class="td_bg01">
								<input type="hidden" name="scanAttach" id="scanAttach" value="" />
								<input class="ACT_btn scanAttach" type="button" value="扫描附件" />
								<input type="hidden" name="stampLogKey" id="stampLogKey" />
								<input type="hidden" name="stampLogID" id="stampLogID" />
							</td>
							<td class="td_bg01">

							</td>
							<td class="td_bg01">

							</td>

						</tr>
						<%
							int number = 1;
						%>
						<s:iterator value="pageForm.list">
							<tr class="td_bg01 saveAjax" id="${stampLogID}">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${stampLogID}" />
								</td>
								<td class="td_bg01">
									<span><%=number%></span>
								</td>
								<td class="td_bg01">
									<span id="stampDate">${fn:substring(stampDate, 0, 16)}</span>
									<input class="model1" value="${fn:substring(stampDate, 0, 16)}"
										name="stampDateStr" size="12"
										onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d %H:%m'})" />
								</td>
								<td class="td_bg01">
									<span id="contractorsCode">${contractorsCode}</span>
								</td>
								<td class="td_bg01">
									<span id="contractors">${contractors}</span>
									<input type="hidden" name="contractorsId"
										value="${contractorsId}" />
									<input class="model1" type="text" value="${contractors}"
										name="contractors" id="contractors" size="7" readonly />
									<a href="#" class="model1" onclick="importGY();">选择人员</a>
								</td>
								<td class="td_bg01">
									<span id="fileName">${fileName}</span>
									<input class="model1" name="fileName" id="fileName2" size="15"
										value="${fileName}" />

								</td>
								<td class="td_bg01">
									<s:if test="scanAttach==null||scanAttach==''">
										<span> 无</span>
									</s:if>
									<s:else>
										<span id="scanAttachs"><a
											href="javascript:OpenWord2('${scanAttach}', 2,'W','manual')">查看</a>
										</span>
									</s:else>

									<input type="hidden" name="scanAttach" id="scanAttach"
										value="${scanAttach}" />
									<input class="model1 ACT_btn scanAttach" type="button"
										value="扫描附件" />
									<input type="hidden" name="stampLogKey" value="${stampLogKey}" />
									<input type="hidden" name="stampLogID" value="${stampLogID}" />
									<input type="hidden" name="enterpriseStampID"
										value="${enterpriseStampID}" id="enterpriseStampID" />
								</td>
								<td class="td_bg01">
									<span id="createTime">${createTime}</span>

								</td>
								<td class="td_bg01">
									<span id="creator">${creator}</span>

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
						value="ea/stamplog/ea_getListStampLog.jspa?stampLog.enterpriseStampID=${stampLog.enterpriseStampID}&pageNumber=${pageNumber}&search=${search}&gore=g&type=${type}">
					</c:param>
				</c:import>
			</div>
		</form>
		<!--搜索窗口 -->
		<form name="appraisalForm" id="appraisalForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
				id="jqModelSearch">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<center>
				<table id="cataffSearchTable">
					<tr>
						<td>
							盖章人：
						</td>
						<td>
							<input name="stampLog.gore" type="hidden" value="g" />
							<input name="type" type="hidden" value="${type}" />
							<input name="stampLog.contractors" class="put3" />
						</td>
					</tr>
				</table>
				<div style="text-align: center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input type="hidden" name="stampLog.enterpriseStampID"
						value="${stampLog.enterpriseStampID}"></input>
				</div>
				</center>
			</div>
		</form>

		<div id="socialJqm" class="jqmWindow"
			style="width: 80%; height: 250px; absolute; display: none; left: 5%; top: 1%; z-index: 9999; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="210px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 30px;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" " />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
		<iframe name="hidden" width="0" height="0"></iframe>
	</body>
</html>
