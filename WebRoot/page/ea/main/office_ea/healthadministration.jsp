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
<title>卫生管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/office_ea/healthadministration.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<SCRIPT type="text/javascript">
   var select = '01';
   var healthAdministrationID = '';
	var ppageNumber = ${pageNumber};
	var basePath = '<%=basePath %>';
	var search = '${search}';
	var token=  0;
</SCRIPT>
</head>
<body>
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">选择</th>
            <th width="150" align="center" >管理范围</th>
            <th width="150" align="center" >范围名称</th>
            <th width="150" align="center" >卫生负责人</th>
            <th width="150" align="center" >卫生检查人</th>
            <th width="150" align="center" >卫生评估</th>
			<th width="300" align="center" >备注</th>
         
      </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
            <td >
             <input type="radio" name="a" class="JQuerypersonvalue" />
             </td>
            <td class="td_bg01"><input   name="healthAdministration.scope" id="scope"   /></td>
            <td class="td_bg01"><input   name="healthAdministration.scopeName" id="scopeName"  /></td>
            <td class="td_bg01"><s:select list="staffList" listKey="staffName" id="xxx" listValue="staffName" name="healthAdministration.principal" theme="simple"></s:select></td>
			<td class="td_bg01"><s:select list="staffList" listKey="staffName" id="xxx" listValue="staffName" name="healthAdministration.examiner" theme="simple"></s:select></td>
            <td class="td_bg01"><input  name="healthAdministration.assessment" id="assessment"></td>
            <td class="td_bg01"><input  name="healthAdministration.remark" id="remark"/>
			</td> 
            <td class="td_bg01">
                     <span class="list_changes" style="cursor: pointer;display:block;" title="保存"><img src="<%=basePath%>js/jqModal/css/images_gray/tj.png" width="15" height="15"></span>
			</td>
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${healthAdministrationID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue"/>
            </td>
            <td class="td_bg01">
                <span id="scope" class="datas">${scope}</span>
				<input class="model1" value="${scope}" name="healthAdministration.scope"  /></td>
            <td class="td_bg01">
               <span id="scopeName" class="datas">${scopeName}</span>
				<input class="model1" value="${scopeName}" name="healthAdministration.scopeName"   /></td>
            <td class="td_bg01">
               <span id="principal" class="datas">${principal}</span>
			   <s:select list="staffList" listKey="staffName" id="principal" listValue="staffName" name="principal" theme="simple"></s:select>
				</td>
			 <td class="td_bg01">
             <span id="examiner">${examiner}</span>
			 <s:select list="staffList" listKey="staffName" id="examiner" listValue="staffName" name="examiner" theme="simple"></s:select>
           			</td>
            <td class="td_bg01">
             <span id="assessment">${assessment}</span>
            <input class="model1"  name="healthAdministration.assessment" value="${assessment}"></td>
            <td class="td_bg01">
             <span id="remark">${remark}</span>
            <input class="model1"  name="healthAdministration.remark" value="${remark}"/>
					      <span id="healthAdministrationKey" style="display:none">${healthAdministrationKey}</span>       <input type="hidden" name="healthAdministration.healthAdministrationKey" value="${healthAdministrationKey}"/>
					      <span id="healthAdministrationID" style="display:none">${healthAdministrationID}</span>       <input type="hidden" name="healthAdministration.healthAdministrationID" value="${healthAdministrationID}"/>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/healthadministration/ea_getListForPage.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                            查询条件
                        </td>
                    </tr>
                    <tr>
                        <td>
                           管理范围：
                        </td>
                        <td>
                           <input   name="healthAdministration.scope" />
                        </td>
                    </tr>
					<tr>
                        <td>
                           卫生负责人：
                        </td>
                        <td>
                           <input   name="healthAdministration.principal" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="width: 600px;top:10%" id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
			       <div class="drag">卫生管理
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table width="600" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:0px;margin-bottom:0px;">
			 <tr>
			   <td>
			     <table width="600"  border="0" align="center" cellpadding="0" cellspacing="5" id="stafftable2" style="margin-top:0px;margin-bottom:0px;">
			      <tr>
			        <td width="114"  align="right">管理范围：</td>
			        <td width="181" ><input name="healthAdministration.scope" type="text" id="scope" size="20"/></td>
			        <td width="122"  align="right">范围名称：</td>
			        <td width="183" ><input name="healthAdministration.scopeName" type="text" id="scopeName" size="20"/></td>
			      </tr>
			      <tr>
			        <td  align="right">卫生负责人：</td>
			        <td ><input name="healthAdministration.principal" id="principal" type="text"  class="input"  size="20"/></td>
			        <td align="right">卫生检查人：</td>
			        <td ><input name="healthAdministration.examiner" id="examiner"   type="text"  class="input"  size="20"/></td>
			        </tr>
			      <tr>
			        <td align="right">卫生评估：</td>
			        <td><input  id="assessment" type="text" name="healthAdministration.assessment" class="input"  size="20"/></td>
			        </tr>
			         <tr>
			        <td  align="right">备注：</td>
			        <td colspan="3"><textarea name="healthAdministration.remark" cols="54" rows="3" class="input" id="remark"></textarea></td>
			        </tr>
			    </table>
			    </td>
					<input type="hidden" name="healthAdministration.healthAdministrationKey" value="${healthAdministrationKey}" id="healthAdministrationKey"/>
					 <input type="hidden" name="healthAdministration.healthAdministrationID" value="${healthAdministrationID}" id="healthAdministrationID"/>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
				<div align="center">
                    <input type="button" class="input-button" style="cursor:pointer;width:80px;" id="tosave" value=" 保存 " />
                    <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value=" 取消 " />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
