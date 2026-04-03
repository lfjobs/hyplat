<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>地址管理</title>
<!-- 此页面在2014-12-18被humanResource.jsp替代。三个月后没有用到可以删除 -->
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var addressID = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var staffID = '${address.staffID}';
   var notoken = 0;

</script>
<script src="<%=basePath%>js/ea/human/cstaff/cstaff_address.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form  name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table   class="address">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
		            <th width="75" align="center" >开始居住日期</th>
		            <th width="75" align="center" >结束居住日期</th>
		            <th width="80" align="center" >地址类别</th>
		            <th width="260" align="center" >详细地址</th>
		            <th width="253" align="center" >备注</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
	           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
		            <td><input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/></td>
		            <td class="td_bg01"><input name="livestartDate" id="livestartDate"  onfocus="date();" size="10" /></td>
		            <td class="td_bg01"><input name="liveendDate" id="liveendDate" onfocus="date(this);"  size="10"/></td>
		            <td class="td_bg01"><s:select list="addressTypelist" listKey="codeID" id="xxx" listValue="codeValue" name="addressType"  theme="simple"></s:select></td>
		            <td class="td_bg01"><input name="addressDetailed" id="addressDetailed" size="40" class="xxdz"/></td>
		            <td class="td_bg01"><input name="addressDesc" id="addressDesc" size="40" class="xxdz"/>
							            <input type="hidden" name="addressKey" id="addressKey"/>
							            <input type="hidden" name="addressID" id="addressID" />
							            <input type="hidden" name="staffID" value="${address.staffID}" id="staffID" />
					</td> 
	          </tr>
          <s:iterator value="addressList">
	          <tr class="td_bg01 saveAjax trclass" id="${addressID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${addressID}"/>
	            	</td>
		            <td class="td_bg01">
		                <span id="livestartDate" class="datas">${livestartDate}</span>
						<input class="model1" value="${livestartDate}" name="livestartDate"  onfocus="date(this);"  size="10"/></td>
		            <td class="td_bg01">
		                <span id="liveendDate" class="datas">${liveendDate}</span>
						<input class="model1" value="${liveendDate}" name="liveendDate"  onfocus="date(this);" size="10"/></td>
		            <td class="td_bg01">
	           			 <span id="addressType" >${addressTypeName}</span>
	                     <s:select list="addressTypelist" listKey="codeID" listValue="codeValue" id="addressType" name="addressType" theme="simple" class="model1"></s:select></td>
	            	<td class="td_bg01">
	             		 <span id="addressDetailed">${addressDetailed}</span>
	           		     <input class="xxdz model1"  name="addressDetailed" id="xxdz" value="${addressDetailed}" size="20"  onblur="checklength()"/></td>
	                <td class="td_bg01">
	             		 <span id="addressDesc">${addressDesc}</span>
	            		 <input class="put4 model1"  name="addressDesc" value="${addressDesc}" size="40"/>
						            <input type="hidden" name="addressKey" value="${addressKey}"/>
						            <input type="hidden" name="addressID" value="${addressID}"/>
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
  	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe2").offsetHeight-57+"px"});
    },100);
	 $(window).resize(function(){ 
		      setTimeout(function(){ 			
		    	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe2").offsetHeight-57+"px"});
		      },100);
	 }); 	
});
</script>
</body>
</html>
