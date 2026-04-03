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
<title>客户跟踪服务办</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script type="text/javascript">
    var select = '01';
    var address = '';
	var basePath='<%=basePath%>';
    var ppageNumber=${pageNumber};
    var psearch='${search}';
    var token = 0;
</SCRIPT>
<script type="text/javascript" src="<%=basePath%>js/ea/human/office/SersonnelSystem/clienttracking.js"></script>

<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body >
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr >
	 	    <th width="40" align="center">请选择</th>
            <th width="100" align="center" >编号</th>
            <th width="130" align="center" >客户名称</th>
            <th width="130" align="center" >跟踪服务请求与预约</th>
            <th width="130" align="center" >客户跟踪服务记录</th>
			<th width="130" align="center" >客户跟踪服务内容</th>
            <th width="130" align="center" >客户跟踪服务日期</th>
            <th width="130" align="center" >客户跟踪服务结果</th>
            <th width="130" align="center" >客户跟踪服务评估</th>
            <th width="130" align="center" >客户跟踪服务反馈</th>
            <th width="130" align="center" >备注</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${clientTrackingID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" value="${clientTrackingID}"/>
            </td>
            <td class="td_bg01">
                <span id="clientCode" class="datas">${clientCode}</span>
            <td class="td_bg01">
               <span id="clientName" class="datas">${clientName}</span>
            <td class="td_bg01">
               <span id="clientBespeak" class="datas">${clientBespeak}</span>
				</td>
			<td class="td_bg01">
                <span id="clientAnnal" class="datas">${clientAnnal}</span>
            <td class="td_bg01">
               <span id="clientContent" class="datas">${clientContent}</span>
            <td class="td_bg01">
               <span id="clientDate" class="datas">${clientDate}</span>
				</td>
			<td class="td_bg01">
               <span id="clientResult" class="datas">${clientResult}</span>
			</td>
			<td class="td_bg01">
               <span id="clientEvaluate" class="datas">${clientEvaluate}</span>
			</td>
			<td class="td_bg01">
				<span id="clientFeedback" class="datas">${clientFeedback}</span>
			</td>
            <td class="td_bg01">
             <span id="remark">${remark}</span>
                             <span style="display:none" id="address">${address}</span>
                             <span style="display:none" id="clientTrackingID">${clientTrackingID}</span>
                             <span style="display:none" id="clientTrackingKey">${clientTrackingKey}</span>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/clientTracking/ea_getClientTrackingList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>
		<div class="jqmWindow jqmWindowcss" style="top:10%"  id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" action="<%=basePath%>/ea/clienttracking/t_ea_save.jspa?pageNumber=${pageNumber}">
			       <div class="drag">客户跟踪服务办
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table  border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table height="152" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="200"  align="right">编号：</td>
			        <td width="200" ><input name="clientTracking.clientCode" type="text" id="clientCode" size="20"/></td>
					<td width="200"  align="right">客户名称：</td>
			        <td width="200" ><input name="clientTracking.clientName" type="text" id="clientName" size="20"/></td>
		           </tr>
				   <tr>
			        <td width="200"  align="right">跟踪服务请求与预约：</td>
			        <td width="200" ><input name="clientTracking.clientBespeak" type="text" id="clientBespeak" size="20"/></td>
					<td width="200"  align="right">客户跟踪服务记录：</td>
			        <td width="200" ><input name="clientTracking.clientAnnal" type="text" id="clientAnnal" size="20"/></td>
		           </tr>
				   <tr>
			        <td width="200"  align="right">客户跟踪服务内容：</td>
			        <td width="200" ><input name="clientTracking.clientContent" type="text" id="clientContent" size="20"/></td>
					<td width="200"  align="right">客户跟踪服务日期：</td>
			        <td width="200" ><input name="clientTracking.clientDate" type="text" id="clientDate" size="20"/ onfocus="date(this);"></td>
		           </tr>
		           <tr>
			        <td width="200"  align="right">客户跟踪服务结果：</td>
			        <td width="200" ><input name="clientTracking.clientResult" type="text" id="clientResult" size="20"/></td>
					<td width="200"  align="right">客户跟踪服务评估：</td>
			        <td width="200" ><input name="clientTracking.clientEvaluate" type="text" id="clientEvaluate" size="20"/></td>
		           </tr>
				   <tr>
				   <td width="200"  align="right">客户跟踪服务反馈：</td>
			        <td width="200" ><input name="clientTracking.clientFeedback" type="text" id="clientFeedback" size="20"/></td>
					<td width="200"  align="right">备注：</td>
			        <td width="200" ><input name="clientTracking.remark" type="text" id="remark" size="20"/>
									 <input type="hidden" name="clientTracking.clientTrackingKey" id="clientTrackingKey" />
					          	     <input type="hidden" name="clientTracking.clientTrackingID" id="clientTrackingID" />
						</td>
		           </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			    <div align="center">
                    <input type="button" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;"  value=" 保存 " />
                    <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value=" 取消 " />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
