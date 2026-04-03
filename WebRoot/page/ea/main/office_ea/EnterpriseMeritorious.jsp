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
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>企业功臣管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath %>js/ea/office_ea/EnterpriseMeritorious.js"></script>
        <script>
             var basePath = "<%=basePath%>";
                var meritoriouID = "";
            var ppageNumber = ${pageNumber};
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
                            姓名
                        </th>
                        <th width="120" align="center">
                            职务
                        </th>
                        <th width="120" align="center">
                            获奖名称
                        </th>
                        <th width="200" align="center">
                             获奖主题或内容
                        </th>
                        <th width="120" align="center">
                            津贴
                        </th>
                        <th width="120" align="center">
                            收于年度
                        </th>
                        <th width="120" align="center">
                             奖品证书或图片
                        </th>
                        <th width="300" align="center">
                            备注
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${meritoriouID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${meritoriouID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                             <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                               
                            <td>
                                <span id="post">${post}</span>
                            </td>
                            <td>
                                <span id="rewardName">${rewardName}</span>
                            </td>
                            <td>
                                <span id="rewardContent">${rewardContent}</span>
                            </td>
                            <td>
                              <span id="allowance" >${allowance}</span>
                            </td>
                            <td>
                                <span id="rewardYear">${rewardYear}</span>
                            </td>
                             <td >
                              <s:if test="picture==null||picture==''">无</s:if>
                             <s:else>
                               <span id="look"  onclick="lookImage('${picture}');"><a href="#">查看</a></span>
                            </s:else>
                            </td>
                             <td>
                                <span id="remark">${remark}</span>
                                 <span id="meritoriouID" style="display:none">${meritoriouID}</span>
                                  <span id="picture" style="display:none">${picture}</span>
                                 <span id="meritoriouKey"  style="display:none">${meritoriouKey}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/meritorious/ea_getEnterpriseMeritoriousList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
            <div style="width: 100%">
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="268px" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
        </div>
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                 <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
               
				  <div class="contentbannb">
				  	<div class="drag">企业功臣管理
				    <div class="close"></div>
				  </div>
				  </div>
				    <table width="730" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td>
                     <table width="699" height="229" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="124"  align="right">功臣名称：</td>
                         <td width="144"><input name="enterpriseMeritorious.staffName" type="text" id="staffName" size="20"/></td>
                         <td width="144" align="right">职务：</td>
                         <td width="144"><input name="enterpriseMeritorious.post" type="text" id="post" size="20"/></td>
                         <td width="170" rowspan="3" align="center"><img id="pic" width="99" height="135"  /></td>
                       </tr>
                       <tr>
                         <td width="124"  align="right">奖励名称：</td>
                         <td ><input name="enterpriseMeritorious.rewardName" id="rewardName" type="text" class="input"  size="20"/></td>
                         <td width="144"  align="right">授予年度：</td>
                         <td ><input name="enterpriseMeritorious.rewardYear" id="rewardYear"   type="text" class="input" size="20"/></td>
                       </tr>
                       <tr>
                         <td width="124" align="right">津贴：</td>
                         <td><input name="enterpriseMeritorious.allowance" id="allowance"  type="text" class="input"  size="20"/></td>
                         <td width="144" align="right"></td>
                         <td>&nbsp;</td>
                       </tr>
                       <tr>
                         <td align="right">奖励主题内容：</td>
                         <td colspan="3"><textarea name="enterpriseMeritorious.rewardContent" cols="35" rows="3" class="input ckTextLength" maxlength="250" id="rewardContent"></textarea></td>
                         <td align="left"><input name="photo" type="file" class="input"  size="7" contenteditable="false" /><input name="enterpriseMeritorious.picture" type="hidden" class="fileNum" id="picture"/></td>
                       </tr>
                       <tr>
                         <td width="124" align="right">备注：</td>
                         <td colspan="4"><textarea style="margin-top:10px;" name="enterpriseMeritorious.remark" cols="53" rows="5" class="input ckTextLength" maxlength="250" id="remark"></textarea></td>
                       </tr>
                       <tr>
                         <td height="30" colspan="5" align="center"><input name="enterpriseMeritorious.meritoriouKey" id="meritoriouKey" type="hidden" class="input"  size="20"/>
                             <input name="enterpriseMeritorious.meritoriouID" id="meritoriouID" type="hidden" class="input"  size="20"/>
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