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
<title>学员证件信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/signup/driving_student_papersInfor.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var drivingAllInformationID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${dtDrivingAllInformation.staffID}'; 
   var notoken = 0;
   var relationID='${dtDrivingAllInformation.relationID}';
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
	      <th width="120" align="center" title="申领">申领类别</th>
	      <th width="120" align="center" title="准驾车型代号">准驾车型代号</th>
	      <th width="80" align="center" title="申请时间">申请时间</th>
	    </tr>
    </thead>
	<tbody id="tbwid">
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td>
		          <input type="radio" name="a"  class="JQuerypersonvalue"  />
		      </td>
		      <td>
		      <s:select list="applylist"  listKey="codeID" listValue="codeValue" name="applyCertificateID" id="xxx" theme="simple"></s:select>
		      	  <%--<input name="applyCertificate" id="applyCertificate" size="10"/>
		      	  <input name="applyCertificateID" id="applyCertificateID" type="hidden"/>	
		      --%></td>
		      <td class="td_bg01">
		      <s:select list="quasilist"  listKey="codeID" listValue="codeValue" name="drivingCodeID" id="xxx" theme="simple"></s:select><%--
		          <input name="drivingCode" id="drivingCode" size="10"/>
		      	  <input name="drivingCodeID" id="drivingCodeID" type="hidden"/>	
		      --%></td>
		      <td class="td_bg01">
		      	  <input name="applyDate" id="applyDate"  size="10" onfocus="date(this);"/>
		      	  <input type="hidden" name="drivingAllInformationKey" id="drivingAllInformationKey"/>
				  <input type="hidden" name="drivingAllInformationID" id="drivingAllInformationID"/>
				  <input type="hidden" name="staffID" value="${dtDrivingAllInformation.staffID}" id="staffID"/>
				  <input type="hidden" name="relationID" value="${dtDrivingAllInformation.relationID}" id="relationID"/>
				  <input type="hidden" name="dataTitle" id="dataTitle" value="06"/>
		      </td>
	    </tr>
    <s:iterator value="beanList">
          <tr class="td_bg01 saveAjax trclass" id="${drivingAllInformationID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingAllInformationID}"/>
	            	</td>
		            <td class="td_bg01">
		            <s:select list="applylist" id="applyCertificateID" listKey="codeID" listValue="codeValue" class="model1" disabled="true" name="applyCertificateID"  theme="simple"></s:select>
		            <%--<span id="applyCertificate">${applyCertificate}</span>
      					<input class="model1" name="applyCertificate"  value="${applyCertificate}" size="10" />
      					<input type="hidden" name="applyCertificateID"  value="${applyCertificateID}" size="10" />
      				--%></td>
      				 <td class="td_bg01">
      				 <s:select list="quasilist" id="applyCertificateID" listKey="codeID" listValue="codeValue" class="model1" disabled="true" name="drivingCodeID"  theme="simple"></s:select>
      				 <%--<span id="drivingCode" >${drivingCode}</span>
      					  <input class="model1" name="drivingCode" value="${drivingCode}" size="10"/>
      					  <input type="hidden" name="drivingCodeID"  value="${drivingCodeID}" size="10" />
      				 --%></td>
				      <td class="td_bg01">
				      <span id="applyDate" >${fn:substring(applyDate,0,11)}</span>
				      <input class="model1" name="applyDate" value="${applyDate}" id="applyDate" size="10" onfocus="date(this);"/>
				      
				      <input type="hidden"  name="drivingAllInformationKey" value="${drivingAllInformationKey}"  size="10"/>
				      <input type="hidden" name="drivingAllInformationID" value="${drivingAllInformationID}"/>
					   <input type="hidden" name="staffID" id="staffID" value="${staffID}" />
					   <input type="hidden" name="relationID" id="relationID" value="${relationID}"/>
					   <input type="hidden" name="dataTitle" id="dataTitle" value="06"/>
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
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe6").offsetHeight-57+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe6").offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>