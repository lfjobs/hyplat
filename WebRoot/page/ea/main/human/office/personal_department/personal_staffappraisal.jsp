<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>考评</title>
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
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		
	
	<script type="text/javascript">
		   var select =1;
		   var appraisalID = '';
		   var payScaleID = '';
		   var basePath = '<%=basePath%>';
           var ppageNumber=${pageNumber};
           var pstaffappraisalstaffID='${staffappraisal.staffID}';
           var pstartdate='${startdate}';
           var penddate='${enddate}';
           var token=0;
           var notoken = 0;
	</script>
	<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/human/office/personal_department/personal_staffappraisal.js"></script>
	</head>
	<body>
	
	    <form name="lForm" id="lForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" value="${sdate}" name="sdate" id="sdate" />
			<input type="hidden" value="${edate}" name="edate" id="edate" />
			<input type="hidden" value="${staffID}" name="staffID" id="staffID" />
		</form>
		<form  name="staffappraisalForm"
			enctype="multipart/form-data" id="staffappraisalForm" method="post">
			<s:token></s:token>
			<input type="submit" name="submit" style="display: none" />
		
		

		<div id="main_main" class="main_main">
			<table class="staffappraisal">
				<thead>
					<tr>
						<th width="35" align="center">
							选择
						</th>
						<th width="110" align="center">
							考评时间
						</th>
						<th width="110" align="center">
							参会考评人
						</th>
						<th width="110" align="center">
							工作日饱和度
						</th>
						<th width="110" align="center">
							遵守法律
						</th>
						<th width="110" align="center">
							责任心
						</th>
						<th width="110" align="center">
							原则性
						</th>
						<th width="110" align="center">
							工作完成率
						</th>
						<th width="80" align="center">
							工作量是否饱和
						</th>
						<th width="80" align="center">
							工作质量
						</th>
						<th width="110" align="center">
							任务完成率
						</th>  
						<th width="110" align="center">
							目标是否明确
						</th>  
						<th width="110" align="center">
							任务完成主动性
						</th>  
						<th width="110" align="center">
							专业技术能力
						</th>  
						<th width="110" align="center">
							管理能力
						</th>  
						<th width="110" align="center">
							综合素质能力
						</th>  
						<th width="110" align="center">
							出勤率
						</th>  
						<th width="110" align="center">
							工作主动性
						</th>  
						<th width="110" align="center">
							文明礼貌素质
						</th>  
					</tr>
				</thead>
				<tbody> 
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${appraisalID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${appraisalID}" />
							</td>
							<td class="td_bg01">
								<span id="appraisalDate" class="datas">${appraisalDate}</span> 
							</td>
							<td class="td_bg01">
								<span id="checkPerson">${checkPerson}</span> 
							</td>
							<td class="td_bg01">
								<span id="workDateSaturation">${workDateSaturation}</span> 
							</td>
							<td class="td_bg01">
								<span id="responsibility1">${responsibility1}</span>  
							</td>
							<td class="td_bg01">
								<span id="responsibility2">${responsibility2}</span> 
							</td>
							<td class="td_bg01">
								<span id="responsibility3">${responsibility3}</span> 
							</td>
							
							<td class="td_bg01">
								<span id="achievements1">${achievements1}</span> 
							</td>
							<td class="td_bg01">
								<span id="achievements2">${achievements2}</span> 
							</td>
							<td class="td_bg01">
								<span id="achievements3">${achievements3}</span> 
							</td>
							
							<td class="td_bg01">
								<span id="task1">${task1}</span> 
							</td>
							<td class="td_bg01">
								<span id="task2">${task2}</span> 
							</td>
							<td class="td_bg01">
								<span id="task3">${task3}</span> 
							</td>
							
							<td class="td_bg01">
								<span id="ability1">${ability1}</span> 
							</td>
							<td class="td_bg01">
								<span id="ability2">${ability2}</span> 
							</td>
							<td class="td_bg01">
								<span id="ability3">${ability3}</span> 
							</td>
							
							<td class="td_bg01">
								<span id="manner1">${manner1}</span> 
							</td>
							<td class="td_bg01">
								<span id="manner2">${manner2}</span> 
							</td>
							<td class="td_bg01">
								<span id="manner3">${manner3}</span>
								<span style="display:none" id="staffID">${staffID}</span>
								<span style="display:none" id="companyID">${companyID}</span>
								<span style="display:none" id="appraisalKey">${appraisalKey}</span>
								<span style="display:none" id="appraisalID">${appraisalID}</span> 
								<span style="display:none" id="payScaleID" >${payScaleID}</span> 
							</td> 
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/staffappraisal/ea_getListStaffAppraisal.jspa?staffappraisal.staffID=${staffappraisal.staffID}&startdate=${startdate}&enddate=${enddate}&pageNumber=${pageNumber}"></c:param>
			</c:import>
		</div>
		</form>
		<!--搜索窗口 -->
		<form name="appraisalForm" id="appraisalForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable"><tr>
                    <td> 起始日期： </td>
                    <td><input name="startdate" onFocus="date(this);" size="12" />
                      至
                      <input name="enddate" onFocus="date(this);" size="12" /></td>
                  </tr>
				</table>
               <div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input type="hidden" name="staffappraisal.staffID"
						value="${staffappraisal.staffID}" />
  				</div>
  			</div>
		</form>
		 <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
