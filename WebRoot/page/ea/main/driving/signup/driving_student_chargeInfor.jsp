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
<title>收费信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/signup/driving_student_chargeInfor.js"></script>
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
	    	<th width="70" align="center" >收费时间</th>
	      <th width="85" align="center" >收费名称</th>
	      <th width="90" align="center" >应收金额</th>
	      <th width="70" align="center" >实收金额</th>
	      <th width="70" align="center" >欠款金额</th>
	    </tr>
    </thead>
	<tbody id="tbwid">
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td>
		          <input type="radio" name="a"  class="JQuerypersonvalue"  />
		      </td>
		      <td>
		      	  <input name="chargeTime" id="chargeTime" size="10" onfocus="date()"/>
		      </td>
		      <td>
		      	  <input name="chargeName" id="chargeName" size="10"/>
		      </td>
		      <td class="td_bg01">
		      <%--<s:select list="standardlist"  listKey="codeID" listValue="codeValue" name="codeID" id="xxx" theme="simple" cssClass="biaozhun"></s:select>
		          --%><input name="codeValue" id="codeValue" size="10"/>
		      	 </td>
		      <td class="td_bg01"><input name="chargeMoney" id="chargeMoney" size="10"/>
			  </td>
		      <td class="td_bg01">
		      	  <input name="arrearsMoney" id="arrearsMoney"  size="10" readonly="readonly"/>
		      	  <input type="hidden" name="drivingAllInformationKey" id="drivingAllInformationKey"/>
				  <input type="hidden" name="drivingAllInformationID" id="drivingAllInformationID"/>
				  <input type="hidden" name="staffID" value="${dtDrivingAllInformation.staffID}" id="staffID"/>
				  <input type="hidden" name="relationID" value="${dtDrivingAllInformation.relationID}" id="relationID"/>
				  <input type="hidden" name="dataTitle" id="dataTitle" value="05"/>
		      </td>
	    </tr>
    <s:iterator value="beanList">
          <tr class="td_bg01 saveAjax trclass" id="${drivingAllInformationID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingAllInformationID}"/>
	            	</td>
		            <td class="td_bg01"><span id="chargeTime">${fn:substring(chargeTime,0,11)}</span>
      					<input class="model1" name="chargeTime"  id="chargeTime"  value="${chargeTime}" size="10" onfocus="date()" />
      				</td>
      				 <td class="td_bg01"><span id="chargeName">${chargeName}</span>
      					<input class="model1" name="chargeName"  value="${chargeName}" size="10" />
      				</td>
      				 <td class="td_bg01">
      				 <%--<s:select list="standardlist" id="codeID" listKey="codeID" listValue="codeValue" class="model1" cssClass="biaozhun" disabled="true" name="codeID"  theme="simple"></s:select>
      				 --%><span id="codeValue" >${codeValue}</span>
      					  <input class="model1" name="codeValue" value="${codeValue}" size="10" id="codeValue"/>
      				 </td>
					 <td class="td_bg01">
					 <span id="chargeMoney">${chargeMoney}</span>
					 <input class="model1" name="chargeMoney" value="${chargeMoney}" id="chargeMoney" size="10"/></td>
				      
				      <td class="td_bg01">
				      <span id="arrearsMoney" >${arrearsMoney}</span>
				      <input class="model1" name="arrearsMoney" value="${arrearsMoney}" id="arrearsMoney" size="10"  readonly="readonly"/>
				      
				      <input type="hidden"  name="drivingAllInformationKey" value="${drivingAllInformationKey}"  size="10"/>
				      <input type="hidden" name="drivingAllInformationID" value="${drivingAllInformationID}"/>
					   <input type="hidden" name="staffID" id="staffID" value="${staffID}" />
					   <input type="hidden" name="relationID" id="relationID" value="${relationID}"/>
					   <input type="hidden" name="dataTitle" id="dataTitle" value="05"/>
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
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe5").offsetHeight-57+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe5").offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>