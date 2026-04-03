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
        <title>打印信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/office_ea/printinformation.js"></script>
         <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';  
         var carNums="";
         var personurl = "";
         var printInfoID = "";
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
                            人员姓名
                        </th>
                        <th width="100" align="center">
                            人员编号
                        </th>
                        <th width="150" align="center">
                            身份证
                        </th>
                        <th width="100" align="center">
                            证件名称
                        </th>
                        <th width="100" align="center">
                            职务
                        </th>
                        <th width="100" align="center">
                            组别
                        </th>
                        <th width="100" align="center">
                            证件时间
                        </th>
                        <th width="100" align="center">
                            创建时间
                        </th>
                        <th width="100" align="center">
                            地址
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${printInfoID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${printInfoID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                             <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                               
                            <td>
                                <span id="staffCode">${staffCode}</span>
                            </td>
                            <td>
                                <span id="staffIdentityCard">${staffIdentityCard}</span>
                            </td>
                            <td>
                                <span id="credentialsName">${credentialsName}</span>
                            </td>
                             <td>
                                <span id="credentialsCode">${credentialsCode}</span>
                            </td>
                             <td>
                                <span id="credentialsType">${credentialsType}</span>
                            </td>
                            <td>
                                <span id="credentialsDate">${fn:substring(credentialsDate, 0, 10)}</span>
                            </td>
                            <td>
                              <span id="createDate" class="datas">${fn:substring(createDate, 0, 10)}</span>
                            </td><td>
                                 <span id="address">${address}</span>
                                 <span id="printInfoID" style="display:none">${printInfoID}</span>
                                  <span id="photo" style="display:none">${photo}</span>
                                 <span id="printInfoKey"  style="display:none">${printInfoKey}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/printInfo/ea_getPrintInList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
             <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
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
				  	<div class="drag">打印信息
				    <div class="close"></div>
				  </div>
				  </div>
				   <table width="642" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699" height="263" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="82" height="46" align="right">人员姓名：</td>
                         <td width="168"><input type="text" id="staffName" name="printInfo.staffName"/></td>
                         <td width="109" align="right">人员编号：</td>
                         <td width="181"><input type="text" id="staffCode" name="printInfo.staffCode"/></td>
                         <td width="159" rowspan="3" align="center"><img id="pic" width="99" height="135"  /></td>
                       </tr>
                       <tr>
                         <td height="46"  align="right">身份证：</td>
                         <td ><input name="printInfo.staffIdentityCard" id="staffIdentityCard" type="text" class="input"  size="20"/></td>
                         <td  align="right">证件名称：</td>
                         <td ><input name="printInfo.credentialsName" id="credentialsName" type="text" class="input"  size="20"/></td>
                       </tr>
                        <tr>
                         <td height="46" align="right">证件号：</td>
                         <td><input name="printInfo.credentialsCode" id="credentialsCode"  type="text" class="input"  size="20"/></td>
                         <td align="right">组别：</td>
                         <td><input name="printInfo.credentialsType" id="credentialsType"   type="text" class="input" size="20"/></td>
                       </tr>
                       <tr>
                         <td height="46" align="right">证件时间：</td>
                         <td><input name="printInfo.credentialsDate" id="credentialsDate"  type="text" class="input"  size="20"/></td>
                         <td align="right">创建时间：</td>
                         <td><input name="printInfo.createDate" id="createDate"   type="text" class="input" size="20"/></td>
                       </tr>
                       <tr>
                         <td align="right">地址：</td>
                         <td><input name="printInfo.address" type="text" class="input" id="address"  size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30" colspan="5" align="center"><input name="printInfo.printInfoKey" id="printInfoKey" type="hidden" class="input"  size="20"/>
                             <input name="printInfo.printInfoID" id="printInfoID" type="hidden" class="input"  size="20"/>
                             </td>
                       </tr>
                     </table></td>
				   </tr>
				</table>
				</div>
    
				<s:token></s:token>
            </form>
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
                <table width="396" id="cataffSearchTable">
                     <tr>
                        <td width="123" align="right">
                            人员姓名：        </td>
						<td width="261">
							<input name="printInfo.staffName" />
                        </td>
                     </tr>
                    <tr>
                        <td align="right">
                            人员编号：                      </td>
                        <td>
                            <input name="printInfo.staffCode" />
                        </td>
                     </tr>
					 <tr>
                        <td align="right">
                            身份证：                        </td>
                  <td>
                            <input name="printInfo.staffIdentityCard" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            证件名称：                        </td>
                        <td>
                        <input name="printInfo.credentialsName" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            职务：                        </td>
                        <td>
                        <input name="printInfo.credentialsCode" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            组别：                        </td>
                        <td>
                        <input name="printInfo.credentialsType" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            证件时间：                        </td>
                        <td>
                        <input name="printInfo.credentialsDate" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            创建时间：                        </td>
                        <td>
                        <input name="printInfoo.createDate" onfocus="date(this);"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            地址：                        </td>
                        <td>
                        <input name="printInfo.addresse" />
                        </td>
                    </tr>
                    
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
    </body>
</html>