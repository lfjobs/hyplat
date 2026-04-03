<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>培训计时</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/signup/driving_student_timingInfor.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<link   href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var drivingAllInformationID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${dtDrivingAllInformation.staffID}'; 
   var notoken = 0;
   var relationID='${dtDrivingAllInformation.relationID}';
   var subjectStatus='${dtDrivingAllInformation.subjectStatus}';
   var state ="${dtDrivingAllInformation.subjectStatus=='01'?'32':dtDrivingAllInformation.subjectStatus=='02'?'36':dtDrivingAllInformation.subjectStatus=='03'?'40':'44'}";
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>

  <body>
 		<form  name="contactForm" id="contactForm" method="post"><input type="submit" name="submit" style="display:none"/>
		<s:token></s:token>
<div id="main_main" class="main_main"> 
  <table class="contact">
  	<thead>
	    <tr>
	    <th width="30" align="center">选择</th>
	      <th width="80" align="center" >培训开始时间</th>
	      <th width="80" align="center" >培训结束时间</th>
	       <th width="110" align="center" >培训时间(自动计算)</th>
	      <%--<th width="80" align="center" >培训总时间</th>
	       --%><th width="200" align="center" >培训备注</th>
	    </tr>
    </thead>
	<tbody id="tbwid">
				<input type="hidden" id="start"/>
				<input type="hidden" id="end"/>
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td>
		          <input type="radio" name="a"  class="JQuerypersonvalue"  />
		      </td>
		      <td>
		      	  <input name="timingStartTime" id="timingStartTime" size="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" />
		      </td>
		       <td>
		      	  <input name="timingEndTime" id="timingEndTime" size="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})"/>
		      </td>
		       <td>
		      	  <input name="timingTime" id="timingTime" readonly="readonly"/>
		      </td><%--
		       <td>
		      	  <input name="timingAllTime" id="timingAllTime" />
		      </td>
		      --%><td class="td_bg01">
		      	  <input name="timingNote" id="timingNote"  />
		      	  <input type="hidden" name="drivingAllInformationKey" id="drivingAllInformationKey"/>
				  <input type="hidden" name="drivingAllInformationID" id="drivingAllInformationID"/>
				  <input type="hidden" name="staffID" value="${dtDrivingAllInformation.staffID}" id="staffID"/>
				  <input type="hidden" name="relationID" value="${dtDrivingAllInformation.relationID}" id="relationID"/>
				  <input type="hidden" name="subjectStatus" id="subjectStatus" value="${dtDrivingAllInformation.subjectStatus}"/>
				  <input type="hidden" name="dataTitle" id="dataTitle" value="08"/>
		      </td>
	    </tr>
    <s:iterator value="beanList">
          <tr class="td_bg01 saveAjax trclass" id="${drivingAllInformationID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingAllInformationID}"/>
	            	</td>
		            <td class="td_bg01"><span id="timingStartTime">${fn:substring(timingStartTime,0,11)}</span>
      					<input class="model1" name="timingStartTime"  value="${fn:substring(timingStartTime,0,11)}" size="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" />
      				</td>
      				<td class="td_bg01"><span id="timingEndTime">${fn:substring(timingEndTime,0,11)}</span>
      					<input class="model1" name="timingEndTime"  value="${fn:substring(timingEndTime,0,11)}" size="10" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})"/>
      				</td>
      				<td class="td_bg01"><span id="timingTime">${timingTime}${timingTime==null?'':'天'}</span>
      					<input class="model1" name="timingTime"  value="${timingTime}${timingTime==null?'':'天'}" size="10" readonly="readonly"/>
      				</td>
      				<%--<td class="td_bg01"><span id="timingAllTime">${timingAllTime}</span>
      					<input class="model1" name="timingAllTime"  value="${timingAllTime}" size="10" />
      				</td>
				      --%><td class="td_bg01">
				      <span id="shuttleDate" >${timingNote}</span>
				      <input class="model1" name="timingNote" value="${timingNote}" id="timingNote"  />
				      
				      <input type="hidden"  name="drivingAllInformationKey" value="${drivingAllInformationKey}"  size="10"/>
				      <input type="hidden" name="drivingAllInformationID" value="${drivingAllInformationID}"/>
					   <input type="hidden" name="staffID" id="staffID" value="${staffID}" />
					   <input type="hidden" name="relationID" id="relationID" value="${relationID}"/>
					   <input type="hidden" name="subjectStatus" id="subjectStatus" value="${subjectStatus}"/>
					   <input type="hidden" name="dataTitle" id="dataTitle" value="08"/>
				      </td>
    </tr>
    </s:iterator>
    </tbody>
  </table>
</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){   
	setTimeout(function(){ 
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe"+state).offsetHeight-57+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe"+state).offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>