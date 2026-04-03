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
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人合同管理</title>
<!-- 此页面在2014-12-18被humanResource.jsp替代。三个月后没有用到可以删除 -->
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel	="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff/cstaff_agreement.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var agreementID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${agreement.staffID}'; 
   var notoken = 0; 
</script>
</head>

  <body>
 		<form  name="agreementForm" id="agreementForm" method="post">
 		<input type="submit" name="submit" style="display:none"/>
		<s:token></s:token>
<div id="main_main" class="main_main"> 
  <table class="agreement">
  	<thead>
	    <tr>
	    <th width="30" align="center">选择</th>
	      <th width="100" align="center" >合同类别</th>
	      <th width="100" align="center" >合同编号</th>
	      <th width="100" align="center" >签订日期</th>
	      <th width="100" align="center" >终止日期</th>
	      <th width="100" align="center" >解除日期</th>
	      <th width="100" align="center" >附件</th>
	      <th width="150" align="center" >备注</th>
	    </tr>
    </thead>
    <tbody id="tbwid">
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td><input type="radio" name="a"  class="JQuerypersonvalue" value="${agreementID}" /></td>
		      <td class="td_bg01"><s:select list="#{'00':'参观期协议','01':'培训期合同','02':'劳动合同','03':'竞职协议','04':'保密协议','05':'安全责任协议'}"  name="status" id="status" theme="simple"></s:select></td>
		      <td class="td_bg01"><input name="contractCode" id="contractCode" size="10"/></td>
		      <td class="td_bg01"><input name="contractSignDate" id="contractSignDate" onfocus="date(this);" size="10"/></td>
		      <td class="td_bg01"><input name="renewalDate" id="renewalDate" onfocus="date(this);" size="10"/></td>
		      <td class="td_bg01"><input name="relieveContractDate" id="relieveContractDate" onfocus="date(this);" size="10"/></td>
		      <td align="center">
		      	<a href="#" class="aUrl" style="text-decoration:none;">附件</a>
		      	<input type="hidden" name="attachUrl" id="attachUrl" size="10" value=""/></td>
		      <td class="td_bg01"><input name="remark" id="remark" size="30"/>
							      <input type="hidden" name="agreementKey" id="agreementKey"/>
							      <input type="hidden" name="companyID" id="companyID"/>
							      <input type="hidden" name="staffID" value="${staffID}" id="staffID"/>
			  </td>
	    </tr>
    <s:iterator value="agreementList">
          <tr class="td_bg01 saveAjax trclass" id="${agreementID}">
        	 	<td class="td_bg01">
           	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${agreementID}"/>
           	 </td>
             <td class="td_bg01">
            	 <span id="status">
             	<c:if test="${status == '00' }">参观期协议</c:if>
             	<c:if test="${status == '01' }">培训期合同</c:if>
             	<c:if test="${status == '02' }">劳动合同</c:if>
             	<c:if test="${status == '03' }">竞职协议</c:if>
             	<c:if test="${status == '04' }">保密协议</c:if>
             	<c:if test="${status == '05' }">安全责任协议</c:if>
             	</span>
             	<s:select  cssClass="model1" class="model1" list="#{'00':'参观期协议','01':'培训期合同','02':'劳动合同','03':'竞职协议','04':'保密协议','05':'安全责任协议'}"  name="status" id="status" theme="simple" ></s:select>
             	</td>
			 <td class="td_bg01">
			 	<span id="contractCode">${contractCode}</span>
				<input class="model1" name="contractCode" id="contractCode" value="${contractCode }"/>
				</td>
			 <td class="td_bg01">
			 	<span id="contractSignDate">${fn:substring(contractSignDate,0,10 )}</span>
			 	<input class="model1" name="contractSignDate" id="contractSignDate" onfocus="date(this);" value="${fn:substring(contractSignDate,0,10 ) }"/>
			 	 </td>
			 <td class="td_bg01">
			 	<span id="renewalDate" >${fn:substring(renewalDate,0,10 )}</span>
			 	<input class="model1" name="renewalDate" id="renewalDate" onfocus="date(this);" value="${fn:substring(renewalDate,0,10 )}"/>
			 	 </td>
			 <td class="td_bg01">
			 	<span id="relieveContractDate">${fn:substring(relieveContractDate,0,10 )}</span>
			 	<input class="model1" name="relieveContractDate" id="relieveContractDate" onfocus="date(this);" value="${fn:substring(relieveContractDate,0,10 ) }"/>
			 	 </td>
			 <td class="td_bg01">
			 	<s:if test="attachUrl!=null"><a href="#" class="aUrl">查看</a></s:if><s:else>无</s:else>
			 	<input type="hidden"  name="attachUrl" id="attachUrl" value="${attachUrl }"/>
			  </td>
			 <td class="td_bg01">
			 	<span id="remark">${remark}</span>
			 	<input class="model1" name="remark" id="remark" value="${remark }"/>
			 	<input type="hidden"  name="agreementID" id="agreementID" value="${agreementID }"/>
			 	<input type="hidden"  name="agreementKey" id="agreementKey" value="${agreementKey }"/>
			 	<input type="hidden"  name="companyID" id="companyID" value="${companyID }"/>
			 	<input type="hidden"  name="staffID" id="staffID" value="${staffID }"/>
			 </td>
      <td class="td_bg01">
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
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe17").offsetHeight-57+"px"});
 },100);
	 $(window).resize(function(){ 	
		 setTimeout(function(){ 	
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe17").offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>

