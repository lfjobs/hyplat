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
<title>往来单位联系方式</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/office_ea/ContactType.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
 <script type="text/javascript">
 		 var  token=0;
         var  select = 1;
         var  contactTypeID = '';
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  ccompanyID='${contactType.ccompanyID}';
         var  contactTypeID = '';
         var  notoken=0;
 </script>   

</head>

<script type="text/css" src="<%=basePath%>js/ea/human/cstaff.js" ></script>
<body>
	<form  name="lForm" id="lForm" method="post"><input type="submit" name="submit" style="display:none"/>
		<input type="hidden" value="${contactType.ccompanyID}" name="contactType.ccompanyID" id="ccompanyID" />
<div id="main_main" class="main_main">
  <table   class="staffappraisal">
  <thead>
	 	    <tr>
	 	    <th width="30" align="center">选择</th>
	 	    <th width="70" align="center" >联系方式</th>
	 	    <th width="85" align="center" >联系号码</th>
            <th width="70" align="center" >联系类别</th>
            <th width="60" align="center" >联系人</th>
            <th width="300" align="center" >备注</th> 
      </tr>
    </thead>
		<tbody  id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2 trclass">
            <td ><input type="radio" name="a" class="JQuerypersonvalue" value="${ccompanyID}"/></td>
            <td class="td_bg01"><s:select list="conTypeList" listKey="codeID" listValue="codeValue" name="conType" id = "xxx" theme="simple" class="ctype"></s:select></td>
            <td class="td_bg01"><input   name="conNum"  id="conNum" size="10"/></td>
            <td class="td_bg01"> <s:select  list="{'无','电信', '移动', '联通'}" class="model1" name="conSort" id = "xxx"></s:select></td>
            <td class="td_bg01"><input   name="conPerson" id="conPerson" size="10"/></td> 
            <td class="td_bg01"><input   name="mark" id="mark" size="30"/>
					            <input   type="hidden" name="contactTypeKey" id="contactTypeKey"/>
					            <input   type="hidden" name="contactTypeID" id="contactTypeID" />
					            <input   type="hidden" name="ccompanyID" value="${contactType.ccompanyID}" id="ccompanyID" />
			</td> 
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${contactTypeID}">
		           <td class="td_bg01">
		             <input type="radio" name="a" class="JQuerypersonvalue" value="${contactTypeID}"/>
		           </td>
		           <td class="td_bg01">
		                <span id="conType"  class="datas" style="display:none">${conType}</span>
						<s:select list="conTypeList" listKey="codeID" listValue="codeValue" name="conType"></s:select></td>
		          <td class="td_bg01">
		                <span id="conNum">${conNum}</span>
						<input class="model1" value="${conNum}" name="conNum" size="10"/></td>
		          <td class="td_bg01">
		               <span id="conSort" style="display:none">${conSort}</span> 
		               <s:select  list="{'无','电信', '移动', '联通'}" class="model1" name="conSort" ></s:select></td>
				  <td class="td_bg01">
		               <span id="conPerson">${conPerson}</span>
						<input class="model1" value="${conPerson}" name="conPerson" size="10"/></td> 
		         <td class="td_bg01">
		              <span id="mark">${mark}</span>
		                <input class="model1"  name="mark" value="${mark}" size="30"/>
						<input type="hidden" name="contactTypeKey" value="${contactTypeKey}"/>
						<input type="hidden" name="contactTypeID"  value="${contactTypeID}"/>
						<input type="hidden" name="ccompanyID" value="${ccompanyID}"/>
				 </td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/contacttype/ea_getContactTypeList.jspa?pageNumber=${pageNumber}&contactType.ccompanyID=${contactType.ccompanyID}"></c:param>
</c:import>
</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	<script type="text/javascript">
    $(function(){   
    	setTimeout(function(){ 			
    	  	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe3").offsetHeight-80+"px"});
    	    },100);
    	$(window).resize(function(){ 
		      setTimeout(function(){ 			
		    	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe3").offsetHeight-80+"px"});
		      },100);
	 }); 	
});
</script> 
</body>
</html>
