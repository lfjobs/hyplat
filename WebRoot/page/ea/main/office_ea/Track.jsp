<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加咨询跟踪信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/office_ea/Track.js"></script>
 <script type="text/javascript">
 		 var  token=0;
         var  select = 1;
         var  trackID = '';
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  foreignKeyID="${foreignKeyID}";
         var  trackrelationID="${trackrelationID}"
         var  notoken=0;
         var  status="${status}"
         var  search="${search}"
         var sdate="${sdate}";
		 var edate="${edate}";
		 var mainheight = 0; //框架高度
 </script>
</head>
<body>
	
		<form name="lForm" id="lForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main" >
			<table class="staffappraisal">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="70" align="center">录入时间</th>
						<th width="70" align="center">咨询起日期</th>
						<th width="70" align="center">咨询止日期</th>
						<th width="200" align="center">工作地点</th>
						<th width="78" align="center">服务方式</th>
						<th width="200" align="center">咨询跟踪内容</th>
						<th width="200" align="center">跟踪原因</th>
						<th width="65" align="center">处理状态</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<input type="hidden" id="start" />
					<input type="hidden" id="end" />
					<tr id="sa" style="display: none" class="td_bg01 saveAjax model2 ">
						<td><input type="radio" name="a" class="JQuerypersonvalue "
							value="${ccompanyID}" /></td>
						<td class="td_bg01"><input name="inputDate" id="inputDate"
							size="7" readonly="readonly" value="系统生成" /></td>
						<td class="td_bg01"><input class="aaaa err"
							name="TrackStartdate" id="startDate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})"
							size="7" /></td>
						<td class="td_bg01"><input class="aaaa err"
							name="TarckEnddate" id="endDate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})"
							size="7" /></td>
						<td class="td_bg01"><input name="workAddr" id="workAddr"
							size="20" /></td>
						<td class="td_bg01"><s:select
								list="#{'00':'面谈','01':'电话','02':'邮件','03':'登门拜访','04':'暗访'}"
								class="dis" name="serviceMode" id="serviceMode" theme="simple"></s:select>
							<input type="hidden" name="trackrelationID"
							value="${trackrelationID}" id="foreignKeyID" /></td>
						<td class="td_bg01"><input name=trackContent
							id="trackContent" class="err" /></td>
						<td class="td_bg01"><input name="trackReason"
							id="trackReason" /></td>
						<td class="td_bg01"><s:select list="#{'00':'处理','01':'未处理'}"
								class="dis" name="handleStatus" id="handleStatus" theme="simple"
								disabled="false"></s:select></td>
					</tr>
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax trclass staffappraisal2 " id="${trackID}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"
								value="${trackID}" /></td>
							<td class="td_bg01"><span id="inputDate">${fn:substring(inputDate,0,10)}</span>
								<input name="inputDate" id="inputDate" size="7" class="model1"
								readonly="readonly" value="系统生成" /></td>
							<td class="td_bg01"><span id="startDate">${fn:substring(trackStartdate,0,10)}</span>
								<input class="aaaa model1 err" name="TrackStartdate"
								id="startDate" value="${fn:substring(trackStartdate,0,10)}"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})"
								size="7" /></td>
							<td class="td_bg01"><span id="endDate">${fn:substring(tarckEnddate,0,10)}</span>
								<input class="aaaa model1 err" name="TarckEnddate" id="endDate"
								value="${fn:substring(tarckEnddate,0,10)}"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})"
								size="7" /></td>
							<td class="td_bg01"><span id="workAddr">${workAddr}</span> <input
								name="workAddr" id="workAddr" size="20" class="model1"
								value="${workAddr}" /></td>
							<td class="td_bg01"><span id="serviceMode">${serviceMode=='00'?'面谈':serviceMode=='01'?'电话':serviceMode=='02'?'邮件':serviceMode=='03'?'登门拜访':'暗访'}</span>
								<s:select
									list="#{'00':'面谈','01':'电话','02':'邮件','03':'登门拜访','04':'暗访'}"
									name="serviceMode" id="serviceMode" class="model1"
									style="display:none"></s:select></td>
							<s:elseif test=""></s:elseif>
							<td class="td_bg01"><span id="trackContent">${trackContent}</span>
								<input name=trackContent id="trackContent" class="model1 err"
								value="${trackContent}" /></td>
							<td class="td_bg01"><span id="trackReason">${trackReason}</span>
								<input name="trackReason" id="trackReason" class="model1"
								value="${trackReason}" /></td>
							<td class="td_bg01"><span id="handleStatus">${handleStatus=='00'?'处理':'未处理'}</span>
								<s:select list="#{'00':'处理','01':'未处理'}" class="dis model1"
									name="handleStatus" id="handleStatus" theme="simple"
									style="display:none"></s:select> <input type="hidden"
								name="trackKey" id="trackKey" value="${trackKey}" /> <input
								type="hidden" name="trackID" id="trackID" value="${trackID}" />
								<input type="hidden" name="trackrelationID"
								value="${trackrelationID}" id="trackrelationID" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
				<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/track/ea_getTrackById.jspa?pageNumber=${pageNumber}&foreignKeyID=${foreignKeyID}&search=${search}&sdate=${sdate}&edate=${edate}&status=${status}&trackrelationID=${trackrelationID}"></c:param>
			</c:import>
			<s:token></s:token>
			</div>
		</form>
	
	<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 350px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="300" id="SearchTable">
					<tr>
						<td align="right">
							咨询起时间：
						</td>
						<td>
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							咨询止时间：
						</td>
						<td>
							<input id="edate" name="edate" onfocus="date(this);"
								style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							服务方式：
						</td>
						<td style="width: 200px">
							<s:select list="#{'':'请选择','00':'面谈','01':'电话','02':'邮件','03':'登门拜访','04':'暗访'}"
									class="dis" name="track.serviceMode" id="serviceMode" theme="simple"></s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							处理状态：
						</td>
						<td style="width: 200px">
							<s:select list="#{'':'请选择','00':'处理','01':'未处理'}" class="dis"
									name="track.handleStatus" id="handleStatus" theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<script type="text/javascript">
    $(function(){   
    	setTimeout(function(){ 			
    	  	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe51").offsetHeight-122+"px"});
    	    },100);
    		 $(window).resize(function(){ 
    			      setTimeout(function(){ 			
    			    	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe51").offsetHeight-122+"px"});
    			      },100);
    		 }); 
});
</script> 
	</body>
</html>
