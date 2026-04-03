<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>网络传真管理集团汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/company/office_ea/networkFaxGroup.js"></script>
         <script type="text/javascript">
		 var basePath = '<%=basePath%>';           
         var pNumber = '${pageNumber}';  
         var search = '${search}';  
		</script>  

	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                         <th width="40" align="center">
                            序号
                        </th>
                        <th width="100" align="center">
                           传真编号
                        </th>
                        <th width="80" align="center">
                            单位名称
                        </th>
                        <th width="120" align="center">
                            传真号
                        </th>
                        <th width="80" align="center">
                           日期
                        </th>
                        <th width="80" align="center">
                           操作人员
                        </th>
                        <th width="60" align="center">
                            传真类别
                        </th>
                        <th width="100" align="center">
                          传真附件
                        </th>
                        <th width="100" align="center">
                          备注
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="pageForm.list" status="number">
                        <tr id="${networkFaxID}"  >
                            <td>
                                <s:property value="%{#number.index+1}"/>
                            </td>
                             <td>
                                <span id="faxCode">${faxCode}</span>
                            </td>
                               
                            <td>
                                <span id="faxCompanyID">${faxCompanyID}</span>
                            </td>
                            <td>
                                <span id="faxNum">${faxNum}</span>
                            </td>
                            <td>
                              <span id="faxDate" class="datas">${fn:substring(faxDate, 0, 10)}</span>
                            </td>
                            <td>
                                <span id="operator">${operator}</span>
                            </td>
                              <td>
                                <span id="faxCategory">${faxCategory}</span>
                            </td>
                             <td >
                              <s:if test="faxPhoto==null||faxPhoto==''">无</s:if>
                             <s:else>
                                <span id="look"  onclick="lookImage('${faxPhoto}');"><a href="#">查看</a></span>
                            </s:else>
                            </td>
                             <td>
                                 <span id="faxNote">${faxNote}</span>
                                 <span id="networkFaxID" style="display:none">${networkFaxID}</span>
                                  <span id="faxPhoto" style="display:none">${faxPhoto}</span>
                                 <span id="networkFaxKey"  style="display:none">${networkFaxKey}</span>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/networkfaxgroup/ea_getListNetworkFaxGroup.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
       
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 300px;top: 15%;right: 40%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            <div class="drag">
            <input type="submit" name="submit" style="display:none"/>
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="296" id="cataffSearchTable">
<tr>
                        <td style="width: 70px" align="right">
                            传真号：        </td>
          <td style="width: 70px">
          <input name="networkFax.faxNum" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">
                            单位名称：                      </td>
                  <td>
                            <input name="networkFax.faxCompanyID" />
                        </td>
                    </tr>
                </table>
            <div style="text-align: center;">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
    </body>
</html>