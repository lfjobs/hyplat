<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工资设定管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/163css/css/163css.css"/>
<script type="text/javascript" src="<%=basePath %>js/163css/js/163css.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/human/salary/threePay.js"></script>
<script>
var basePath = '<%=basePath%>';
var pageNumber = ${pageNumber};
var cid = '';
var select = 0 ;
var notoken = 0;
var search='${search}';
var startdate = '${startdate }';
var enddate = '${enddate }';
</script>
</head>
<body>
<form  name="threepayForm" id="threepayForm" method="post">
	<table id="item" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
		            <th width="200" align="center">公司名称</th>
		            <th width="100" align="center">员工姓名</th>
		            <th width="100" align="center">金额</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
	    <s:iterator value="pageForm.list" var="item">
	    	<tr id="${item[1] }">
	    		
	    		<td>
	    			<input type="radio" value="${item[1] }" name="cid" class="cid"/>
	    		</td>
	    		<td>
	    			<span>${item[0] }</span>
	    		</td>
	    		<td>
	    			<span>${item[2] }</span>
	    		</td>
	    		<td>
	    			<span>${item[3] }</span>
	    		</td>
	    	</tr>
	    </s:iterator>
	    </tbody>
  </table>
  </form>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/payroll/ea_getThreePay.jspa?pageNumber=${pageNumber}&search=${search}&startdate=${startdate }&enddate=${enddate }">
	</c:param>
</c:import>
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		       $(".bDiv").css({"height": _height - 150 + "px"});
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		        $(".bDiv").css({"height": _height - 150 + "px"});
		},100);
	    }); 
   });
</script> 	
</body>
</html>
