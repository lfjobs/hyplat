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
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery-1.3.1.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var drivingtestid = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var notoken = 0;
   var drivingprincipalid='${dtDrivingPrincipal.drivingprincipalid}'
   var docstatus='${docstatus}';//单据状态  		
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/driving/driving_list_kaoshiinfors.js"></script></head>
<body>
<form  name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table   class="address">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
			 	    <th width="30" align="center"  >序号</th>
		            <th width="100" align="center" >约考时间</th>
		            <th width="100" align="center" >考试类型</th>
		            <th width="80" align="center" >考试结果</th>
	      		</tr>
	    </thead>
		<tbody>
			<% int i=1; %>
          <s:iterator value="pageForm.list">
	          <tr class="td_bg01 saveAjax" id="${drivingtestid}" class="trclass">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingtestid}"/>
	            	</td>
	            	<td class="td_bg01">
		                <span> <%=i%></span></td>
		            <td class="td_bg01">
		                <span id="testdate" class="datas">${testdate}</span>
						<input class="model1" value="${testdate}" name="testdate"  onfocus="date(this);"  size="10"/></td>
		            <td class="td_bg01">
		                <span id="liveendDate" >${examtype=='01'?'科一考试':examtype=='02'?'科二桩考':examtype=='03'?'科二场地':'科三考试'}</span></td>
	                <td class="td_bg01">
	             		 <span id="examresult">${examresult=='00'?'合格':examresult=='01'?'不合格':examresult=='02'?'缺考':examresult=='03'?'缺考':'暂无结果'}</span>
				         <input type="hidden" name="drivingtestkey" value="${drivingtestkey}"/>
				         <input type="hidden" name="drivingtestid" value="${drivingtestid}"/>
				         <input type="hidden" name="drivingprincipalid" value="${drivingprincipalid}"/>
					</td>
	          </tr>
	          <%  i++; %>
          </s:iterator>
    	</tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/driving/ea_getDtDrivingTestList.jspa?pageNumber=${pageNumber}&dtDrivingPrincipal.drivingprincipalid=${dtDrivingPrincipal.drivingprincipalid}"></c:param>
</c:import>
</div>
</form>
<div class="jqmWindow" style="width: 400px; right: 40%; top: 15%;height: 100px"
				id="jqModelSearchss">
				<form name="kaoshiForm" id="kaishiForm" method="post">
					<input type="submit" name="submit" style="display: none" />
					<div class="drag">
						设置结果
						<div class="close">
						</div>
					</div>
					<table width="400" style="font-size: 13px;font-weight: bolder">
						<tr>
							<td align="right">
							考试结果：
							</td>
							<td align="left"">&nbsp;&nbsp;&nbsp;
							<input type="radio"  name="dtDrivingTest.examresult" class="ksjg" value="00"/>
							合格<input type="radio"  name="dtDrivingTest.examresult" class="ksjg" value="01"/>
							不合格<input type="radio"  name="dtDrivingTest.examresult" class="ksjg" value="02"/>
							缺考<input type="radio"  name="dtDrivingTest.examresult" class="ksjg" value="03"/>误报
								<input type="hidden" name="dtDrivingTest.drivingtestid" id="drivingtestid"/>	
								<input type="hidden" name="dtDrivingPrincipal.drivingprincipalid" id="drivingprincipalid" value="${dtDrivingPrincipal.drivingprincipalid}"/>
							<input type="hidden" id="docstatus" name="docstatus"
									value="${docstatus}" />
							</td>
						</tr>
					</table>
					<div align="center">
						<input type="button" class="JQuerySubmit" 
							value=" 确定 " />
					</div>
				</form>
			</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
