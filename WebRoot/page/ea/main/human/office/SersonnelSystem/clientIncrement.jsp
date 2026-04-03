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
<title>客户增值服务办</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript">
    var select = '01';
    var address = '';
	var basePath='<%=basePath%>';
    var ppageNumber=${pageNumber};
    var psearch='${search}';
    var token = 0;
</SCRIPT>
<script type="text/javascript" src="<%=basePath%>js/ea/human/office/SersonnelSystem/clientIncrement.js"></script>

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
            <th width="100" align="center" >客户编号</th>
            <th width="130" align="center" >客户名称</th>
            <th width="130" align="center" >客户服务</th>
            <th width="130" align="center" >客户投诉</th>
			<th width="130" align="center" >客户评价</th>
            <th width="130" align="center" >客户服务反馈</th>
            <th width="130" align="center" >提供附加值</th>
            <th width="130" align="center" >备注</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${clientIncrementID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" value="${clientIncrementID}"/>
            </td>
            <td class="td_bg01">
                <span id="clientCode" class="datas">${clientCode}</span>
            <td class="td_bg01">
               <span id="clientName" class="datas">${clientName}</span>
            <td class="td_bg01">
               <span id="clientService" class="datas">${clientService}</span>
				</td>
			<td class="td_bg01">
                <span id="clientComplaint" class="datas">${clientComplaint}</span>
            <td class="td_bg01">
               <span id="clientAppraise" class="datas">${clientAppraise}</span>
            <td class="td_bg01">
               <span id="clientFeedback" class="datas">${clientFeedback}</span>
				</td>
			<td class="td_bg01">
               <span id="tack" class="datas">${tack}</span>
			</td>
            <td class="td_bg01">
             <span id="remark">${remark}</span>
                             <span style="display:none" id="address">${address}</span>
                             <span style="display:none" id="clientIncrementID">${clientIncrementID}</span>
                             <span style="display:none" id="clientIncrementKey">${clientIncrementKey}</span>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/clientIncrement/ea_getClientIncrementList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>
		<div class="jqmWindow jqmWindowcss" style="top:10%"  id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" action="<%=basePath%>/ea/clienttracking/t_ea_save.jspa?pageNumber=${pageNumber}">
			       <div class="drag">客户增值服务办
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
			        <td width="105"  align="right">客户编号：</td>
			        <td width="195" ><input name="clientIncrement.clientCode" type="text" id="clientCode" size="20"/></td>
					<td width="105"  align="right">客户名称：</td>
			        <td width="195" ><input name="clientIncrement.clientName" type="text" id="clientName" size="20"/></td>
		           </tr>
				   <tr>
			        <td width="105"  align="right">客户服务：</td>
			        <td width="195" ><input name="clientIncrement.clientService" type="text" id="clientService" size="20"/></td>
					<td width="105"  align="right">客户投诉：</td>
			        <td width="195" ><input name="clientIncrement.clientComplaint" type="text" id="clientComplaint" size="20"/></td>
		           </tr>
				   <tr>
			        <td width="105"  align="right">客户评价：</td>
			        <td width="195" ><input name="clientIncrement.clientAppraise" type="text" id="clientAppraise" size="20"/></td>
					<td width="105"  align="right">客户服务反馈：</td>
			        <td width="195" ><input name="clientIncrement.clientFeedback" type="text" id="clientFeedback" size="20"/></td>
		           </tr>
				   <tr>
				   <td width="105"  align="right">提供附加值：</td>
			        <td width="195" ><input name="clientIncrement.tack" type="text" id="tack" size="20"/></td>
					<td width="105"  align="right">备注：</td>
			        <td width="195" ><input name="clientIncrement.remark" type="text" id="remark" size="20"/>
									 <input type="hidden" name="clientIncrement.clientIncrementKey" id="clientIncrementKey" />
					          	     <input type="hidden" name="clientIncrement.clientIncrementID" id="clientIncrementID" />
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
