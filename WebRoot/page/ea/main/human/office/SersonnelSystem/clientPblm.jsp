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
<title>客户问题解决办</title>
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
<script type="text/javascript" src="<%=basePath%>js/ea/human/office/SersonnelSystem/clientPblm.js"></script>

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
            <th width="130" align="center" >详细地址</th>
            <th width="130" align="center" >联系人</th>
			<th width="130" align="center" >联系电话</th>
            <th width="130" align="center" >联系邮箱</th>
            <th width="130" align="center" >QQ</th>
            <th width="130" align="center" >产品名称</th>
            <th width="130" align="center" >产品型号</th>
            <th width="130" align="center" >产品品牌</th>
            <th width="130" align="center" >产品序列号</th>
            <th width="130" align="center" >设置配制说明</th>
            <th width="130" align="center" >现场问题说明</th>
            <th width="130" align="center" >采取的步骤和结果</th>
            <th width="130" align="center" >原因分析</th>
            <th width="130" align="center" >处理结果</th>
            <th width="130" align="center" >客户意见</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${clientPblmID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" value="${clientPblmID}"/>
            </td>
            <td class="td_bg01">
                <span id="clientCode" class="datas">${clientCode}</span>
            <td class="td_bg01">
               <span id="clientName" class="datas">${clientName}</span>
            <td class="td_bg01">
               <span id="clientAddress" class="datas">${clientAddress}</span>
				</td>
			<td class="td_bg01">
                <span id="clientLinkman" class="datas">${clientLinkman}</span>
            <td class="td_bg01">
               <span id="clientTelephone" class="datas">${clientTelephone}</span>
            <td class="td_bg01">
               <span id="clientEmail" class="datas">${clientEmail}</span>
				</td>
			<td class="td_bg01">
               <span id="clientQq" class="datas">${clientQq}</span>
			</td>
			<td class="td_bg01">
               <span id="productName" class="datas">${productName}</span>
			</td>
			<td class="td_bg01">
				<span id="productType" class="datas">${productType}</span>
			</td>
			<td class="td_bg01">
				<span id="productBrand" class="datas">${productBrand}</span>
			</td>
			<td class="td_bg01">
				<span id="productSerial" class="datas">${productSerial}</span>
			</td>
			<td class="td_bg01">
				<span id="productSay" class="datas">${productSay}</span>
			</td>
			<td class="td_bg01">
				<span id="productPblmSay" class="datas">${productPblmSay}</span>
			</td>
			<td class="td_bg01">
				<span id="moveAndResult" class="datas">${moveAndResult}</span>
			</td>
			<td class="td_bg01">
				<%--<span id="causeAnalysis" class="datas">${causeAnalysis}</span>
				 --%>
				 <span style="display:none" id="causeAnalysis" class="causeAnalysis">${causeAnalysis}</span>
	             <s:if test="causeAnalysis=='00'">请选择</s:if> 
	             <s:if test="causeAnalysis=='01'">返修</s:if>
	             <s:if test="causeAnalysis=='02'">完全解决</s:if>
	             <s:if test="causeAnalysis=='03'">跟踪服务</s:if>
	             <s:if test="causeAnalysis=='04'">其他</s:if>
			</td>
			<td class="td_bg01">
				<span id="processingResult" class="datas">${processingResult}</span>
			</td>
            <td class="td_bg01">
             <span id="clientConceit">${clientConceit}</span>
                             <span style="display:none" id="address">${address}</span>
                             <span style="display:none" id="clientPblmID">${clientPblmID}</span>
                             <span style="display:none" id="clientPblmKey">${clientPblmKey}</span>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/clientPblm/ea_getClientPblmList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>
		<div class="jqmWindow jqmWindowcss" style="top:10%"  id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" action="<%=basePath%>/ea/clienttracking/t_ea_save.jspa?pageNumber=${pageNumber}">
			       <div class="drag">客户问题解决办
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
			        <td height="30" width="150"  align="right">客户编号：</td>
			        <td width="195" ><input name="clientPblm.clientCode" type="text" id="clientCode" size="20"/></td>
					<td width="150"  align="right">客户名称：</td>
			        <td width="195" ><input name="clientPblm.clientName" type="text" id="clientName" size="20"/></td>
		           </tr>
				   <tr>
			        <td height="30" width="150"  align="right">详细地址：</td>
			        <td width="195" ><input name="clientPblm.clientAddress" type="text" id="clientAddress" size="20"/></td>
					<td width="150"  align="right">联系人：</td>
			        <td width="195" ><input name="clientPblm.clientLinkman" type="text" id="clientLinkman" size="20"/></td>
		           </tr>
				   <tr>
			        <td height="30" width="150"  align="right">联系电话：</td>
			        <td width="195" ><input name="clientPblm.clientTelephone" type="text" id="clientTelephone" size="20"/></td>
					<td width="150"  align="right">联系邮箱：</td>
			        <td width="195" ><input name="clientPblm.clientEmail" type="text" id="clientEmail" size="20"/></td>
		           </tr>
		           <tr>
			        <td height="30" width="150"  align="right">QQ：</td>
			        <td width="195" ><input name="clientPblm.clientQq" type="text" id="clientQq" size="20"/></td>
					<td width="150"  align="right">产品名称：</td>
			        <td width="195" ><input name="clientPblm.productName" type="text" id="productName" size="20"/></td>
		           </tr>
		           <tr>
			        <td height="30" width="150"  align="right">产品型号：</td>
			        <td width="195" ><input name="clientPblm.productType" type="text" id="productType" size="20"/></td>
					<td width="150"  align="right">产品品牌：</td>
			        <td width="195" ><input name="clientPblm.productBrand" type="text" id="productBrand" size="20"/></td>
		           </tr>
		           <tr>
			        <td height="30" width="150"  align="right">产品序列号：</td>
			        <td width="195" ><input name="clientPblm.productSerial" type="text" id="productSerial" size="20"/></td>
					<td width="150"  align="right">设置配制说明：</td>
			        <td width="195" ><input name="clientPblm.productSay" type="text" id="productSay" size="20"/></td>
		           </tr>
		           <tr>
			        <td height="30" width="150"  align="right">现场问题说明：</td>
			        <td width="195" ><input name="clientPblm.productPblmSay" type="text" id="productPblmSay" size="20"/></td>
					<td width="150"  align="right">采取的步骤和结果：</td>
			        <td width="195" ><input name="clientPblm.moveAndResult" type="text" id="moveAndResult" size="20"/></td>
		           </tr>
		           <tr>
			        <td height="30" width="150"  align="right">原因分析：</td>
			        <%--
			        <td width="195" ><input name="clientPblm.causeAnalysis" type="text" id="causeAnalysis" size="20"/></td>
					--%>
					<td width="195" >
					<select name="clientPblm.causeAnalysis" id="causeAnalysis">
						    <option value="00">请选择</option>
							<option value="01">返修</option>
							<option value="02">完全解决</option>
							<option value="03">跟踪服务</option>
							<option value="04">其他</option>
						</select>
					</td>
					<td width="150"  align="right">处理结果：</td>
			        <td width="195" ><input name="clientPblm.processingResult" type="text" id="processingResult" size="20"/></td>
		           </tr>
				   <tr>
					<td height="30" width="150"  align="right">客户意见：</td>
			        <td width="195" colspan="4"><input name="clientPblm.clientConceit" type="text" id="clientConceit" size="20" style="width:460px; height:40px"/>
									 <input type="hidden" name="clientPblm.clientPblmKey" id="clientPblmKey" />
					          	     <input type="hidden" name="clientPblm.clientPblmID" id="clientPblmID" />
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
