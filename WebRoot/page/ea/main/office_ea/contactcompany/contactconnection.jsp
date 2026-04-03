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
<title>往来单位管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/contactcompany/contactconnection.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
<script type="text/javascript">
 var  contactConnectionID = '';
 var  companyName = '';
 var  basePath='<%=basePath%>';           
 var  pNumber =${pageNumber};  
 var search='${search}';
 var  token = 0 ;  
 var  personurl='';
var notoken = 0;
var retoken=0;
var type = "${type}";//用于区分是短信平台查询
var title="${title}";//显示短信平台列表标题的；
var companyID = "${cview.companyID}";
var contactConnections="${cview.contactConnections}";
var typemes = "${typemes}";

</script>
</head>
<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                         <th width="40" align="center">
                           序号
                        </th>
                        <th width="250" align="center">
                          单位名称		
                        </th>
                         <th width="100" align="center">
                         单位往来关系
                        </th>
                        <th width="250" align="center">
                         单位地址
                        </th>
                        <th width="100" align="center">
                           单位电话
                        </th>
                        <th width="100" align="center">
                          单位负责人
                        </th>
                        <th width="100" align="center">
                          单位负责人电话
                        </th>
                          <th width="100" align="center">
                          行业类别
                        </th>
                        <th width="100" align="center">
                         单位备注
                        </th> 
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${contactConnectionID}"  >
                            <td>
                                <input type="checkbox" name="chbox"  class="JQuerypersonvalue chx" value="${contactConnectionID}" />
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                             <td>
                                <span id="companyName">${companyName}</span>
                            </td>
                            <td>
                                <span id="contactConnections">${contactConnections}</span>
                            </td>
                            <td>
                                <span id="companyAddr">${companyAddr}</span>
                            </td>
                            <td>
                                <span id="companyTel">${companyTel}</span>
                            </td>
                            <td>
                                <span id="cresponsible">${cresponsible}</span>
                            </td>
                            <td>
                                <span id="responsibleTel">${responsibleTel}</span>
                            </td>
                           <td>
                                <span id="industryType">${industryType}</span>
                            </td>
                           <td>
                                <span id="remark">${remark}</span>
                                 <span id="ccompanyID" style="display:none">${ccompanyID}</span>
                                   <span id="contactConnectionID" style="display:none">${contactConnectionID}</span>
                                   <span id="contactConnectionKey" style="display:none">${contactConnectionKey}</span>
                                   <span id="dealIn" style="display:none">${dealIn}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/contactConnection/ea_getListContactConnection.jspa?search=${search}&pageNumber=${pageNumber}&type=${type}&title=${title}&typemes=${typemes}&cview.companyID=${cview.companyID}">
                </c:param>
            </c:import>
        </div>
        <span id="Relation" style="display: none"> <s:property
				value="#session.tablesearch.contactConnections" /> </span>
		<span id="IndustryType" style="display: none"> <s:property
				value="#session.tablesearch.industryType" /> </span>
		<span id="RecordCount" style="display: none"> <s:property
				value="#session.RecordCount" /> </span>
        <form name="cstaffForm" id="cstaffForm" method="post"> 
         <input type="submit" name="submit" style="display:none"/>
        <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%;height: 350px" id="jqModel">     
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">单位往来单位
    <div class="close"></div>
    </div>
  </div>
   <table width="600" height="300" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;"><tr><td>
     <tr >
       <td width="15%" align="right">单位名称：</td>
       <td width="35%"><input type="text" id="companyName" name="contactCompany.companyName"/></td>
       <td width="20%" align="right">单位往来关系：</td>
       <td><input name="cview.contactConnections" id="contactConnections"  type="text"  class="input" size="20"/>
       </td>
     </tr>
     <tr>
		<td align="right">单位电话：</td>
		<td><input name="cview.companyTel" id="companyTel" type="text" class="input"  size="20"/></td>
		<td align="right">行业类别：</td>
		<td><input name="cview.industryType" id="industryType" type="text" class="input"  size="20"/></td>
     </tr>
     <tr>
		<td align="right">单位负责人：</td>
		<td><input name="cview.cresponsible" id="cresponsible" type="text" class="input"  size="20"/></td>
		<td align="right">单位负责人电话：</td>
		<td><input name="cview.responsibleTel" id="responsibleTel" type="text" class="input"  size="20"/></td>
     </tr>
      <tr>
       <td align="right">公司地址：</td>
       <td colspan="3"><input name="cview.companyAddr" id="companyAddr" type="text"  class="input" size="74"/>
           </td>
     </tr>
     <tr>
        <td align="right">备注：</td>
       <td colspan="3"><input name="cview.remark" id="remark"  type="text"  class="input" size="74"/><input name="cview.contactConnectionKey" id="contactConnectionKey" type="hidden" class="input" />
           <input name="cview.contactConnectionID" id="contactConnectionID" type="hidden" class="input" />
            <input name="cview.ccompanyID" id="ccompanyID" type="hidden" class="input"  /></td>
        </tr>
     <tr>
       <td align="right">经营范围：</td>
       <td colspan="3"><textarea   style="width:475px; height:50px" name="cview.dealIn" id="dealIn"></textarea>       </td>
     </tr>
     
</table>
</div>
        </div><s:token></s:token>
            </form>
              <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 40%;;top: 10%" id="jqModelSearch">
          <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询往来单位
                    <div class="close">
                    </div>
            </div>
                <table width="396" id="cataffSearchTable">
                     <tr>
	                          <td width="123" align="right">
	                                单位名称：        </td>
							  <td width="261">
								<input name="cview.companyName" />
	                          </td>
                     </tr>
                     <tr>
	                           <td align="right">单位责任人：</td>
	                           <td><input name="cview.cresponsible" /></td>
                    </tr>
                    <tr>
	                           <td width="123" align="right">
	                               单位地址：        </td>
							   <td width="261">
							   <input name="cview.companyAddr" />
	                        </td>
                   </tr>
                    <tr>
	                           <td width="123" align="right">
	                               单位往来关系：      </td>
							   <td width="261">
							   <select id="contactConnections"></select>
							   </td>
                   </tr>
                    <tr>
	                           <td width="123" align="right">
	                               行业类别：        </td>
							   <td width="261">
							 <s:select list="%{#request.typelist}" id="industryType"
												listKey="codeValue" listValue="codeValue"
												name="cview.industryType" theme="simple" headerKey="" headerValue="全部"></s:select>
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