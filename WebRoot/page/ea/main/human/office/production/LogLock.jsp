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
<title>时间加锁管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script type="text/javascript" >
   var select = 1;
   var logLockID = '';
   var basePath = '<%=basePath%>';
   var pNumber = '${pageNumber}';
   var notoken = 0;
   var loglockID = '';
   var search = '${search}';

</script>
<script src="<%=basePath%>js/ea/human/office/production/loglock.js"></script>
</head>
<body>
<form  name="loglockForm" id="loglockForm" method="post"><s:token></s:token>
<input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table   class="loglock">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
		            <th width="80" align="center" >开始日期</th>
		            <th width="80" align="center" >结束日期</th>
		            <th width="80" align="center" >人员姓名</th>
		            <th width="150" align="center" >操作时间</th>
		            <th width="60" align="center" >状态</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
	  <!--    <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
		            <td><input type="radio" name="a" class="JQuerypersonvalue" /></td>
		            <td class="td_bg01"><input name="startDate" id="startDate" size="10"  onfocus="date();" /></td>
		            <td class="td_bg01"><input name="endDate" id="endDate"  size="10" onfocus="date(this);" />
							            <input type="hidden" name="logLockkey" id="logLockkey"/>
							            <input type="hidden" name="logLockID" id="logLockID" />
					</td> 
					<td class="td_bg01"></td>
					<td class="td_bg01"></td>
	          </tr>-->  
          <s:iterator value="pageForm.list">
	          <tr class="td_bg01 saveAjax" id="${logLockID}" class="trclass">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${logLockID}"/>
	            	</td>
		            <td class="td_bg01">
		                <span id="startDate" class="datas">${fn:substring(startDate,0,10)}</span>
		            <td class="td_bg01">
		                <span id="endDate" class="datas">${fn:substring(endDate,0,10)}</span>
						            <input type="hidden" name="logLockkey" value="${logLockkey}"/>
						            <input type="hidden" name="logLockID" value="${logLockID}"/>
					</td>
					<td class="td_bg01">
		                <span id="accountName">${staffName}</span>
						</td>
				    <td class="td_bg01">
		                <span id="lockDate" >${fn:substring(lockDate,0,19)}</span>
						</td>
				     <td class="td_bg01">
		                <span id="st" >${status=='00'?'已加锁':'已解锁'}</span>
		                <span id="status" style="display:none">${status}</span>
						</td>
	          </tr>
          </s:iterator>
    	</tbody>
  </table>
 <c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/loglock/ea_getListLogLock.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>
</form>

<!--搜索窗口 -->
 <div class="jqmWindow" style="width: 280px;right: 40%;;top: 20%" id="jqModelSearch">
     <form name="lockSearchForm" id="lockSearchForm" method="post">
     	<input type="submit" name="submit" style="display:none"/>
         <div class="drag">
             查询信息
             <div class="close">
             </div>
         </div>
         <table id="monthSalaryTable">
             <tr>
                 <td width="40%" align="right">
                     人员姓名：
                 </td>
                 <td>
                     <input name="loglock.staffName" />
                 </td>
             </tr>
                      <tr>
                 <td align="right">
                     月份：
                 </td>
                 <td>
                     <input name="months" onfocus="daymonth(this);"/>
                 </td>
             </tr>
             <tr >
                 <td  align="right" >
                     加锁状态：
                 </td>
                 <td ><select name="loglock.status">
                 		<option value="">请选择</option>
                 		<option value="00">已加锁</option>
                 		<option value="01">已解锁</option>
                 	  </select> 
                 </td>
                 
             </tr>
         </table>
         <div align="center">
             <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
         </div>
     </form>
 </div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>