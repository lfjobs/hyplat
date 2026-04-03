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
<title>分车管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/signup/driving_student_fenCheInfor.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var drivingAllInformationID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${dtDrivingAllInformation.staffID}'; 
   var notoken = 0;
   var relationID='${dtDrivingAllInformation.relationID}';
   var mainheught = 0; //框架高度
   
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
	      <th width="100" align="center" >车辆自编号</th>
	      <th width="80" align="center" >分车时间</th>
	      <th width="80" align="center" >教练员</th>
	      <th width="120" align="center" >车辆牌号</th>
	      <th width="100" align="center" >车型</th>
	    </tr>
    </thead>
	<tbody id="tbwid">
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td>
		          <input type="radio" name="a"  class="JQuerypersonvalue"  />
		      </td>
		      <td>
		      	  <input name="carNumber" id="carNumber" size="10"/>
		      </td>
		      <td class="td_bg01">
		      	  <input name="carDate" id="carDate"  size="10" onfocus="date(this);"/>
		      </td>
		      <td class="td_bg01">
		      	  <input name="carCoach" id="carCoach"  size="10" />
		      </td>
		      <td>
		      	  <input name="carIdentifier" id="carIdentifier" />
		      	  <input name="carID" id="carID" size="10" 	type="hidden"/>
		      	 <a href="#"  id="xuanze">选择</a>	
		      </td>
		      <td>
		      	  <input name="carType" id="carType" size="10" />
		      	  
		      	  <input type="hidden" name="drivingAllInformationKey" id="drivingAllInformationKey"/>
				  <input type="hidden" name="drivingAllInformationID" id="drivingAllInformationID"/>
				  <input type="hidden" name="staffID" value="${dtDrivingAllInformation.staffID}" id="staffID"/>
				  <input type="hidden" name="relationID" value="${dtDrivingAllInformation.relationID}" id="relationID"/>
				  <input type="hidden" name="dataTitle" id="dataTitle" value="09"/>
		      </td>
		      
	    </tr>
    <s:iterator value="beanList">
          <tr class="td_bg01 saveAjax trclass" id="${drivingAllInformationID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingAllInformationID}"/>
	            	</td>
		            <td class="td_bg01"><span id="carNumber">${carNumber}</span>
      					<input class="model1" name="carNumber"  id="carNumber" value="${carNumber}" size="10" />
      				</td>
      				  <td class="td_bg01">
				      <span id="carDate" >${fn:substring(carDate,0,11)}</span>
				      <input class="model1" name="carDate" value="${carDate}" id="carDate" size="10" onfocus="date(this);"/>
      				</td>
				     <td class="td_bg01">
				      <span id="carCoach" >${carCoach}</span>
				      <input class="model1" name="carCoach" value="${carCoach}" id="carCoach" size="10" />
				     </td>
				     
				     <td class="td_bg01"><span id="carIdentifier">${carIdentifier}</span>
      					<input class="model1" name="carIdentifier" id="carIdentifier" value="${carIdentifier}"   />
      					<a class="model1" href="#"  id="xuanze">选择</a>	
      					<input class="model1" name="carID"  id="carID" value="${carID}"  type="hidden"/>
      				</td>
				    <td class="td_bg01">
				      <span id="carType">${carType}</span>
      					<input class="model1" name="carType" id="carType"  value="${carType}" size="10" />
				      
				      <input type="hidden"  name="drivingAllInformationKey" value="${drivingAllInformationKey}"  size="10"/>
				      <input type="hidden" name="drivingAllInformationID" value="${drivingAllInformationID}"/>
					   <input type="hidden" name="staffID" id="staffID" value="${staffID}" />
					   <input type="hidden" name="relationID" id="relationID" value="${relationID}"/>
					   <input type="hidden" name="dataTitle" id="dataTitle" value="09"/>
				      </td>
    </tr>
    </s:iterator>
    </tbody>
  </table>
</div>
</form>

<%-- 选择车辆信息  --%>
<div id="bankJqm" class="jqmWindow"
			style="width: 90%; height: 300px; absolute; display: none; left: 5%; top: 10%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="260px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" " />
			</div>
</div>

<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){   
	setTimeout(function(){ 
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe55").offsetHeight-57+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe55").offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>