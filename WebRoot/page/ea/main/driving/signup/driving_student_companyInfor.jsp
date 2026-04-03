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
<title>报名单位信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/driving/signup/driving_student_companyInfor.js"></script>
<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
<script type="text/javascript">
   var companyID='${account.companyID}';
   var companyName='${companyName}';
   var organizationID='${organizationID}';	
   var organizationName='${organizationName}'; 
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
	      <th width="210" align="center" title="公司下子部门">分校/报名点</th>
	      <th width="90" align="center" title="报名时间">报名时间</th>
	      <th width="90" align="center" title="报名电话">报名电话</th>
	      <th width="100" align="center" title="当前公司在职人员">推荐人\招生员</th>
	      <th width="100" align="center" title="推荐人联系方式">推荐人联系方式</th>
	      <th width="150" align="center" title="推荐人身份证号">推荐人身份证号</th>
	      <th width="60" align="center" title="当前帐号责任人">受理人</th>
	    </tr>
    </thead>
	<tbody id="tbwid">
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td>
		          <input type="radio" name="a"  class="JQuerypersonvalue"  />
		      </td>
		      <td>
		      	  <select  id="organizationID" name="organizationID"></select>	
		      	   <input type="hidden" name="organizationName" id="organizationName"/>
		      </td>
		      <td class="td_bg01">
		          <input name="signUpDate" id="signUpDate" onfocus="date()"/>
		      </td>
		      <td class="td_bg01">
		          <input name="registrationPhone" id="registrationPhone" size="10"/>
		      </td>
		      <td class="td_bg01">
		          <input name="referrer" id="referrer"  size="10"/>
		           <a href="#" id="xzry" class="xzry">选择</a>
		          <input name="referrerID" id="referrerID"  type="hidden"/>		
		      </td>
		      <td class="td_bg01"><input name="referrerPhone" id="referrerPhone" size="10"/>
			  </td>
			  <td class="td_bg01"><input name="referrerIdentityCard" id="referrerIdentityCard" size="10"/></td>
		      <td class="td_bg01">
		      	  <input name="acceptPeople" id="acceptPeople"  size="10"/>
		      	  <input name="acceptPeopleID" id="acceptPeopleID" type="hidden"/>
		      	  <input type="hidden" name="drivingAllInformationKey" id="drivingAllInformationKey"/>
				  <input type="hidden" name="drivingAllInformationID" id="drivingAllInformationID"/>
				  <input type="hidden" name="staffID" value="${dtDrivingAllInformation.staffID}" id="staffID"/>
				  <input type="hidden" name="relationID" value="${dtDrivingAllInformation.relationID}" id="relationID"/>
				  <input type="hidden" name="dataTitle" id="dataTitle" value="04"/>
		      </td>
	    </tr>
    <s:iterator value="beanList">
          <tr class="td_bg01 saveAjax trclass" id="${drivingAllInformationID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingAllInformationID}"/>
	            	</td>
		            <td class="td_bg01"><span id="organizationName">${organizationName}</span>
      					<select class="model1" id="organizationID" name="organizationID" ></select>	
      					<input type="hidden"  value="${organizationID}" size="10" id="organizationID"/>
      					<input type="hidden" name="organizationName" id="organizationName"/>
      				</td>
      				<td class="td_bg01"><span id="signUpDate" >${fn:substring(signUpDate,0,11)}</span>
      					  <input class="model1" name="signUpDate" value="${fn:substring(signUpDate,0,11)}" onfocus="date()" /></td>
      				 <td class="td_bg01"><span id="registrationPhone" >${registrationPhone}</span>
      					  <input class="model1" name="registrationPhone" value="${registrationPhone}" size="10"/></td>
      				 <td class="td_bg01"><span id="referrer" >${referrer}</span>
      				      <input class="model1"  value="${referrer}" name="referrer"  size="10" id="referrer"/>
      				      <a href="#" id="xzry" class="xzry model1">选择</a>
      				      <input type="hidden" name="referrerID"  value="${referrerID}" size="10" id="referrerID" />
      				      </td>
     				 <td class="td_bg01"><span id="referrerPhone">${referrerPhone}</span>
     					  <input class="model1" name="referrerPhone" value="${referrerPhone}" size="10" id="referrerPhone"/>
	 </td>
	 
	 <td class="td_bg01">
	 <span id="referrerIdentityCard">${referrerIdentityCard}</span>
	 <input class="model1" name="referrerIdentityCard" value="${referrerIdentityCard}" id="referrerIdentityCard" size="10"/></td>
      <td class="td_bg01">
      <span id="acceptPeople" >${acceptPeople}</span>
      <input class="model1" name="acceptPeople" value="${acceptPeople}" id="acceptPeople" size="10"/>
      <input type="hidden" name="acceptPeopleID" value="${acceptPeopleID}"/>
      
      <input type="hidden"  name="drivingAllInformationKey" value="${drivingAllInformationKey}"  size="10"/>
      <input type="hidden" name="drivingAllInformationID" value="${drivingAllInformationID}"/>
	   <input type="hidden" name="staffID" id="staffID" value="${staffID}" />
	   <input type="hidden" name="relationID" id="relationID" value="${relationID}"/>
	   <input type="hidden" name="dataTitle" id="dataTitle" value="04"/>
      </td>
    </tr>
    </s:iterator>
    </tbody>
  </table>
</div>
</form>

<form name="Staffform" id="Staffform" method="post">
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 320px; absolute; display: none; left: 2.5%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<iframe name="daoRu" id="daoRu" width="100%" height="270px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
			
		</div>
</form>

<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){   
	setTimeout(function(){ 
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe53").offsetHeight+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe53").offsetHeight+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>