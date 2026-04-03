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
        <title>企业功臣-集团汇总</title>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
         <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script type="text/javascript" src="<%=basePath %>js/ea/company/office_ea/EnterpriseMeritoriousGroup.js"></script>
        <script>
           var token = 0;
           var basePath = "<%=basePath%>";
          	var ppageNumber = ${pageNumber};
          	var  search = '${search}';
</script>
	</head>
  
  <body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
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
                        <tr>
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
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/meritoriousgroup/ea_getEnterpriseMeritoriousList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
        
             <!--搜索窗口 -->
        <div class="jqmWindow " style="width:300px;right: 45%; top:10%" id="jqModelSearch">
            <form class="postSearchForm"  id="postSearchForm"  method="post" name="postSearchForm">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                          奖励名称：
                        </td>
                        <td>         
                           <input name="enterpriseMeritorious.rewardName" />  
                        </td>
                    </tr>
					<tr>
                        <td>
                        收于年度：
                        </td>
                        <td>
                           <input name="enterpriseMeritorious.rewardYear" />
                        </td>
                    </tr>
                </table>
                <div style="text-align:center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                    <input type="submit" name="submit" style="display:none"/>
                </div>
            </form>
        </div>
        <s:token></s:token>
    </body>
</html>
