<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="hy.plat.bo.PageForm"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预约记录展示</title>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var drivingappointmentrecordid = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var staffID='';
   var notoken = 0;
   var drivingprincipalid='${dtDrivingAppointmentRecord.drivingprincipalid}';
   
   window.setInterval(function(){ 
	   var pageNumber=${pageForm==null?1:pageForm.pageNumber};
	   var pageCount=${pageForm==null?1:pageForm.pageCount};
	   //alert(pageNumber+" "+pageCount);
	   //alert(pageNumber==pageCount);
	   if(pageNumber==pageCount){
		   pageNumber=1;
	   }else{
		   pageNumber++;
	   }
	   document.location.href=basePath+ "ea/appointment/ea_getListDtDrivingAppointmentRecordCompany.jspa?pageForm.pageNumber="+pageNumber;
	 },5000); 
</script>
<script src="<%=basePath%>js/ea/driving/part/driving_appointment_preview.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form  name="DtDrivingAppointmentRecordForm" id="DtDrivingAppointmentRecordForm" method="post"><s:token></s:token>
<input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table   class="DtDrivingAppointmentRecordTable">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >序号</th>
		            <th width="75" align="center" >学员姓名</th>
		            <th width="75" align="center" >性别</th>
		            <th width="110" align="center" >联系方式</th>
		            <th width="75" align="center" >起时间</th>
		            <th width="75" align="center" >止时间</th>
		            <th width="100" align="center" >车牌号</th>
		            <th width="75" align="center" >车型</th>
		            <th width="200" align="center" >教练员</th>
		            <th width="110" align="center" >联系方式</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
			<%
				PageForm	 pageForm=(PageForm)request.getAttribute("pageForm");
				int i=pageForm==null?0:pageForm.getPageSize()-1;
			%>
          <s:iterator value="pageForm.list">
	          <tr class="td_bg01 saveAjax trclass" id="${drivingappointmentrecordid}">
	         	 	<td class="td_bg01">
	            	 	 <%= pageForm.getPageNumber()*pageForm.getPageSize()-i%>
	            	</td>
		            <td class="td_bg01">
		                <span id="staffname" class="datas">${staffname}</span>
						</td>
					<td class="td_bg01">
		                <span id="staffsex" class="datas">${staffsex}</span>
						</td>
					<td class="td_bg01">
		                <span id="staffphone" class="datas">${staffphone}</span>
						</td>
					<td class="td_bg01">
					<span id="startdate">${startdate}</span>
	            		</td>
	            	<td class="td_bg01">
					<span id="enddate">${enddate}</span>
	            		 </td>	
					<td class="td_bg01">	
					<span id="carcode">${carcode}</span>
	            		 </td>	
	            	<td class="td_bg01">
		                <span id="sumtimelength" class="datas">${sumtimelength}</span>
						</td> 
	            	<td class="td_bg01">
					<span id="coach">${coach}</span>
	            		 </td>
	            	<td class="td_bg01">
					<span id="coachphone">${coachphone}</span>
	            		
	            		 </td>
	          </tr>
	          <% i--;%>
          </s:iterator>
    	</tbody>
  </table>
</div>
</form>
<c:import url="../../../page_navigator.jsp" >
				<c:param name="actionPath"
					value="ea/appointment/ea_getListDtDrivingAppointmentRecordCompany.jspa?pageNumber=${pageNumber}">
				</c:param>
			</c:import>

		
</body>
</html>
