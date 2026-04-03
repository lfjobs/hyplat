<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />         
        <title>企业形象管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script type="text/javascript" src="<%=basePath %>js/ea/office_ea/EnterpriseLogo.js"></script>
        <script>
             var basePath = "<%=basePath%>";
             var logoID = "";
             var ppageNumber = '${pageNumber}';
             var search = '${search}';
             var token=0;
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
                         <th width="50" align="center">
                            序号
                        </th>
                        <th width="100" align="center">
                           企业徽标图片
                        </th>
                        <th width="100" align="center">
                            徽标类别
                        </th>
                        <th width="100" align="center">
                            作者
                        </th>
                        <th width="100" align="center">
                           徽标注释
                        </th> 
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${logoID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${logoID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                             <td >
                             <s:if test="logoPhoto==null||logoPhoto==''">无</s:if>
                             <s:else>
                             <span id="look"  onclick="lookImage('${logoPhoto}');"><a href="#">查看</a></span>
                             </s:else>
                                
                            </td>   
                            <td>
                                <span id="logoType">${logoType}</span>
                            </td>
                            <td>
                                <span id="author">${author}</span>
                            </td>
                            <td>
                                <span id="logoNote">${logoNote}</span>
                                <span id="logoID" style="display:none">${logoID}</span>
                                  <span id="logoPhoto" style="display:none">${logoPhoto}</span>
                                 <span id="logoKey"  style="display:none">${logoKey}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/logo/ea_getEnterpriseLogoList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                 <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%;width: 520px;height: 306px" id="jqModel">
				  <div class="contentbannb">
				  	<div class="drag">企业徽标信息
				    <div class="close"></div>
				  </div>
				  </div>
				   <table width="500" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="500" height="272" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td  align="right">徽标类型：</td>
                         <td width="24"><input name="enterpriseLogo.logoType" type="text" id="logoType"/></td>
                        
                         <td width="244" rowspan="2" align="center"><img id="pic" width="99" height="135"  /></td>
                       </tr>
                       <tr>
                         <td align="right">作者：</td>
                         <td height="70"><input name="enterpriseLogo.author" type="text" id="author"/></td>
                       </tr>
                       <tr>
                         <td align="right">徽标注释：</td>
                         <td height="76"><input name="enterpriseLogo.logoNote" id="logoNote" type="text" class="input"/></td>
                         <td height="76" align="center"><input name="photo" type="file" class="input"  size="10" contentEditable="false"/>
                          <input name="enterpriseLogo.logoPhoto" type="hidden" class="fileNum" id="logoPhoto"/> </td>
                       </tr>
                       <tr>
                         <td width="104" align="center">&nbsp;</td>
                         <td height="30" colspan="2" align="center"><input name="enterpriseLogo.logoKey" id="logoKey" type="hidden" class="input"  size="20"/>
                             <input name="enterpriseLogo.logoID" id="logoID" type="hidden" class="input"  size="20"/>
                             <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
                             <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />                         </td>
                       </tr>
                     </table></td>
				   </tr>
				</table>				
        </div>
        <s:token></s:token>
            </form>
            <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>         
    </body>
</html>