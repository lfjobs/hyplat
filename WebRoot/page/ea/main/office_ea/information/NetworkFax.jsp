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
        <title>网络传真管理</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script src="<%=basePath%>js/ea/office_ea/information/NetworkFax.js"></script>
         <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';  
         var networkFaxID = "";
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
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${networkFaxID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${networkFaxID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
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
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/networkfax/ea_getListNetworkFax.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
            <div style="width: 100%">
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="268px" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
        </div>
        <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">网络传真管理
				    <div class="close"></div>
				  </div>
				  </div>
				   <table width="642" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699" height="263" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="124" height="46" align="right">传真编号：</td>
                         <td width="168"><input type="text" id="faxCode" name="networkFax.faxCode" class="ckTextLength" maxlength="30"/></td>
                         <td width="109" align="right">单位名称：</td>
                         <td width="181"><input type="text" id="faxCom panyID" name="networkFax.faxCompanyID" class="ckTextLength" maxlength="30"/></td>
                         <td width="159" rowspan="3" align="center"><img id="pic" width="99" height="135"  /></td>
                       </tr>
                       <tr>
                         <td height="46"  align="right">传真号：</td>
                         <td ><input name="networkFax.faxNum" id="faxNum" type="text" class="input phone"  size="20"/></td>
                          <td height="46" align="right">传真类别：</td>
                         <td><input name="networkFax.faxCategory" id="faxCategory"   type="text" class="ckTextLength" maxlength="30" size="20"/></td>
                       </tr>
                       <tr>
                         <td height="46" align="right">日期：</td>
                         <td><input name="networkFax.faxDate" id="faxDate" onfocus="date(this);" type="text" class="input"  size="20"/></td>
                         <td align="right">操作人员：</td>
                         <td><input name="networkFax.operator" id="operator"   type="text" class="input username" size="20"/></td>
                       </tr>
                       <tr>
                         <td align="right">备注：</td>
                         <td colspan="3" ><input name="networkFax.faxNote" type="text" class="input" id="faxNote"  size="65"/></td>
                         <td width="159" align="center"><input name="photo" type="file" class="input"  size="10"  contentEditable="false"/>
                          <input name="networkFax.faxPhoto" type="hidden" class="fileNum" id="faxPhoto"/>    </td>
                       </tr>
                       <tr>
                         <td height="30" colspan="5" align="center"><input name="networkFax.networkFaxKey" id="networkFaxKey" type="hidden" class="input"  size="20"/>
                             <input name="networkFax.networkFaxID" id="networkFaxID" type="hidden" class="input"  size="20"/>
                             <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
                            <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                             <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />                         </td>
                       </tr>
                     </table></td>
				   </tr>
				</table>
				</div>
    
				<s:token></s:token>
            </form>
        </div>
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;top: 15%;right: 35%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="396" id="cataffSearchTable">
<tr>
                        <td width="123" align="right">
                            传真号：        </td>
          <td width="261">
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
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>