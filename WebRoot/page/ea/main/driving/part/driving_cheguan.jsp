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
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车管管理</title>
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
   var drivingDealCheGuanid = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var staffID='${drivingDealCheGuan.staffID}';
   var notoken = 0;

</script>
<script src="<%=basePath%>js/ea/driving/part/driving_cheguan.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form  name="addressForm" id="addressForm" method="post"><s:token></s:token>
<input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table   class="address">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
		            <th width="75" align="center" >报车管日期</th>
		            <th width="150" align="center" >录入日期</th>
		            <th width="80" align="center" >是否报车管</th>
		            <th width="80" align="center" >车管资料审核</th>
		            <th width="80" align="center" >责任人</th>
		            <th width="335" align="center" >备注</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
	           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
		            <td><input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/></td>
		            <td class="td_bg01"><input name="submitCheGuanDate" id="submitCheGuanDate"  onfocus="date();" size="10" /></td>
		            <td class="td_bg01"><input name="inputDate" id="inputDate" value="系统自动录入" disabled="disabled"/></td>
		            <td class="td_bg01"><s:select list="#{'01':'已报车管','00':'未报车管'}" listKey="key" id="xxx" listValue="value" name="theCheGuanStates"  theme="simple"></s:select></td>
		            <td class="td_bg01"><s:select list="#{'01':'已合格','00':'不合格'}" listKey="key" id="xxx" listValue="value" name="checkCheGuanStates"  theme="simple"></s:select></td>
		            <td class="td_bg01"><input name="operationResponsibleName" id="operationResponsibleName" value="系统自动录入" disabled="disabled"/></td>
		            <td class="td_bg01"><input name="checkCheGuanStatesReason" id="checkCheGuanStatesReason" size="40"/>
							            <input type="hidden" name="drivingDealCheGuankey" id="drivingDealCheGuankey"/>
							            <input type="hidden" name="drivingDealCheGuanid" id="drivingDealCheGuanid" />
							            <input type="hidden" name="staffID" value="${drivingDealCheGuan.staffID }" id="staffID" />
					</td> 
	          </tr>
          <s:iterator value="drivingDealCheGuanList">
	          <tr class="td_bg01 saveAjax trclass" id="${drivingDealCheGuanid}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingDealCheGuanid}"/>
	            	</td>
		            <td class="td_bg01">
		                <span id="submitCheGuanDate" class="datas">${submitCheGuanDate}</span>
						<input class="model1" value="${submitCheGuanDate}" name="submitCheGuanDate"  onfocus="date(this);"  size="10"/></td>
		            <td class="td_bg01">
		                <span id="inputDate" class="datas">${fn:substring(inputDate,0,11)}</span>
						<input class="model1" value="${fn:substring(inputDate,0,11)}" name="inputDate"  size="10" disabled="disabled"/></td>
		            <td class="td_bg01">
	           			 <span id="theCheGuanStates" class="datas">${theCheGuanStates=='00'?'未报车管':'已报车管'}</span>
	            		<s:select list="#{'00':'未报车管','01':'已报车管'}" listKey="key" id="xxx" listValue="value" name="theCheGuanStates"   cssClass="model1"></s:select></td>	
	            	<td class="td_bg01">
	             		 <span id="checkCheGuanStates" class="datas">${checkCheGuanStates=='00'?'未合格':'已合格'}</span>
	                     <s:select list="#{'00':'未合格','01':'已合格'}" listKey="key" id="xxx" listValue="value" name="checkCheGuanStates"   cssClass="model1"></s:select></td>
	           		<td class="td_bg01">
		                <span id="operationResponsibleName" class="datas">${operationResponsibleName}</span>
						<input name="operationResponsibleName" id="operationResponsibleName" value="${operationResponsibleName}" disabled="disabled" class="model1"/></td>     
	                <td class="td_bg01">
	             		 <span id="checkCheGuanStatesReason">${checkCheGuanStatesReason}</span>
	            		 <input class="model1" id="checkCheGuanStatesReason"  name="checkCheGuanStatesReason" value="${checkCheGuanStatesReason}" size="40"/>
						            <input type="hidden" name="drivingDealCheGuankey" value="${drivingDealCheGuankey}"/>
						            <input type="hidden" name="drivingDealCheGuanid" value="${drivingDealCheGuanid}"/>
						            <input type="hidden" name="staffID" value="${staffID}"/>
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
  	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe56").offsetHeight-57+"px"});
    },100);
	 $(window).resize(function(){ 
		      setTimeout(function(){ 			
		    	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe56").offsetHeight-57+"px"});
		      },100);
	 }); 	
});
</script>
</body>
</html>
