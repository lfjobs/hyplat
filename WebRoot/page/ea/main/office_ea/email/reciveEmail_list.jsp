<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();	
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发件箱管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/plat/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>

<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script  src="<%=basePath%>js/ea/office_ea/email/reciveEmail_list.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}
-->
  a:link   {text-decration:none;font-size:12px}   

</style>
<script type="text/javascript">
   var basePath="<%=basePath%>";
   var emailID="";

</script>
</head>
<body >    
<form method="post" id="emailForm" action="" name="emailForm"><%-- 不告诉你--%>
			<input id="reciveID" name="reciveID" type="hidden"/>
			<input id="emailStatus" name="emailStatus" value="addresseeStatus" type="hidden"/>
			<input id="listStatus" name="listStatus" value="reciveEmail" type="hidden"/>
			 <input type="submit" name="submit" style="display:none"/>
	</form>
  <div class="main_main">
  <table class="JQueryflexme">
        				<thead>
							    	   	<tr>
							             	<th width="90" align="center">
												<input type="checkbox" id="xz"/>全选/反选
											</th>
											<th width="80" align="center">
												发件人
											</th>
											<th width="120" align="center">
												主题
											</th>
											<th width="400" align="center">
												内容
											</th>
											<th width="114" align="center">
												日期
											</th>
											<th width="114" align="center">
												状态
											</th>
							            </tr>
						</thead>
								 <tbody>
							         	<s:iterator value="pageForm.list">
								    		<tr id="${emailID}" title="${addresseeStatus}">
									            <td><input name="chss" type="checkbox" value="${emailID}"/></td>
												<td><span id="addresserName">${addresserName}</span></td>
												<td><span id="title">${title}</span></td>
												<td><span id="content">${content}</span></td>
												<td><span id="emailDate">${emailDate}</span></td>
												<td><span id="addresseeStatus">${addresseeStatus=='11'?'未读':'已读'}</span>
												<span id="emailID" style="display:none"> ${emailID}</span></td>
								            </tr>
							            </s:iterator>
							            </tbody>
							</table>
								<c:import url="../../../page_navigator.jsp">
							<c:param name="actionPath"
								value="ea/email/ea_toSearch.jspa?emailStatus=${emailStatus}"></c:param>
						</c:import>	
			</div>
			 <form name="cstaffForm" id="cstaffForm" method="post"> 
         <input type="submit" name="submit" style="display:none"/>
        <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">     
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">收件箱
    <div class="close"></div>
    </div>
  </div>
				  <table width="450" border="0"  id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				  <tr>
				     <td>
				     <table width="430" height="340" border="0" id="stafftable2" align="left" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="100" height="60" align="right">发件人：</td>
                         <td width="330"><input type="text" id="addresserName" readonly="readonly"  name="addresserName" size="30" id="reciveName"/>
                         </td>
                       </tr>
                        <tr>
                         <td width="100" height="60" align="right">主题：</td>
                         <td width="330"><input type="text"   id="title" readonly="readonly" size="30" name="title"/></td>
                       </tr>
                        <tr>
                         <td width="100" height="60" align="right">附件：</td>
                         <td  colspan="5"><div style="width:300px;"id="attach"></div></td>
                           
                       </tr>
                       <tr>
                         <td width="100" height="200" align="right">正文：</td>
                         <td colspan="5"><textarea  name="content" readonly="readonly" cols="40" rows="8" id="content"></textarea></td>
                       </tr>
                     </table>
                     </td>
				   </tr>
				</table>
				</div>
        </div>
        	<s:token></s:token>
            </form>
	</body>
</html>	    