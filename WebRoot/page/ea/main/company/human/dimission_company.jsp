<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@page import="hy.ea.bo.Company"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany");
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>集团--离职员工管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script> 
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
            <script src="<%=basePath%>js/ea/human/staff_info.js">
        </script>
        <script src="<%=basePath%>js/ea/human/cstaff.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/company/human/dimission_company.js"></script>
        <script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
        <script type="text/javascript">
            var basePath = "<%=basePath%>";
            var personvalue = "${staffID}";
            var personurl = "";
            var staffName="";
            var ppageNumber = '${pageNumber}';
            var session_val = '${session_value}';
            var psearch = '${search}';
            var codimission = '${dimission}';  //离职员工原因
            var dimissionStaffID="${dimissionStaffID}";
            var retoken = 0;
            var token = 0;
            var notoken = 0;
             var comID = '<%=c.getCompanyID()%>';
		 	var comName = '<%=c.getCompanyName()%>';    
        </script>     
         
    </head>
    <body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            选择
                        </th>
                        <th width="200" align="center">
                            公司名称
                        </th>
                        <th width="100" align="center">
                            人员编号
                        </th>
                        <th width="100" align="center">
                            档案编号
                        </th>
                        <th width="100" align="center">
                            人员姓名
                        </th>
                        <th width="100" align="center">
                            离职时间
                        </th>
                        <th width="100" align="center">
                            离职原因
                        </th>
                        <th width="100" align="center">
                            经手人
                        </th>
                        <th width="100" align="center">
                            曾用名
                        </th>
                        <th width="80" align="center">
                            性别
                        </th>
                        <th width="100" align="center">
                            出生日期
                        </th>
                        <th width="80" align="center">
                            国籍 
                        </th>
                        <th width="80" align="center">
                            籍贯
                        </th>
                        <th width="80" align="center">
                            民族
                        </th>
                        <th width="200" align="center">
                            身份证
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${staffID}" class="${staffName}">
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/>
                            </td>
                            <td>
                                <span>${companyName}</span>
                            </td>
                            <td>
                                <span id="staffCode">${staffCode}</span>
                            </td>
                            <td>
                                <span id="recordCode">${recordCode}</span>
                            </td>
                            <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                            <td>
                                <span id="dimissionDate" class="datas">${dimissionDate}</span>
                            </td>
                            <td>
                                <span id="dimissionCause">${dimissionCause}</span>
                            </td>
                            <td>
                                <span id="issued">${issued}</span>
                            </td>
                            
                            <td>
                                <span id="usedNmae">${usedNmae}</span>
                            </td>
                            <td>
                                <span id="sex">${sex}</span>
                            </td>
                            <td>
                                <span id="birthday" class="datas">${birthday}</span>
                            </td>
                             <td>
                                <span id="nationality">${nationality}</span>
                            </td>
                            <td>
                                <span id="nativePlace">${nativePlace}</span>
                            </td>
                            <td>
                                <span id="nation">${nation}</span>
                            </td>
                            <td>
                                <span id="staffIdentityCard">${staffIdentityCard}</span>
                                <span style="display:none" id="schedulingID">${schedulingID}</span>
                                <span style="display:none" id="staffKey">${staffKey}</span>
                                <span style="display:none" id="staffID">${staffID}</span>
                                <span style="display:none" id="photo">${photo}</span>
                                
                                <span style="display:none" id="dimissionStaffID">${dimissionStaffID}</span>
                                <span style="display:none" id="dimissionStaffKey">${dimissionStaffKey}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/staffdimissioncompany/ea_getStaffDimissionList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
            <div style="200px;overflow: ">
                <iframe src="" name="main" width="100%" marginwidth="0" scrolling="no"  height="258px" marginheight="0"  frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
        </div>
        <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqModel">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    员工详细信息 
                    <div class="close">
                    </div>
                </div>
                <table width="690" height="211" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable">
                    <tr>
                        <td width="103" height="27" align="right">
                            人员编号：
                        </td>
                        <td width="140">
                            <input name="cstaff.staffCode"  id="staffCode" size="20"/>
                        </td>
                        <td width="147" align="right">
                            档案编号：
                        </td>
                        <td width="148">
                            <input name="cstaff.recordCode" id="recordCode" size="20"/>
                        </td>
                        <td width="144" rowspan="6" align="center">
                            <img width="99" height="135" id="photo" />
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            姓名：
                        </td>
                        <td>
                            <input name="cstaff.staffName" id="staffName" size="20"/>
                        </td>
                        <td height="27" align="right">
                            离职时间：
                        </td>
                        <td>
                            <input name="cstaff.dimissionDate" id="dimissionDate" size="20"/>
                        </td>
                    </tr>
                       <tr>
                        <td height="27" align="right">
                            离职原因：
                        </td>
                        <td>
                            <input name="cstaff.dimissionCause" id="dimissionCause" size="20"/>
                        </td>
                        <td height="27" align="right">
                            经手人：
                        </td>
                        <td>
                            <input name="cstaff.issued" id="issued" size="20"/>
                        </td>
                       </tr>
                       <tr>
                        <td align="right">
                            曾用名：
                        </td>
                        <td>
                            <input name="cstaff.usedNmae" id="usedNmae" size="20"/>
                        </td>
                        </tr>
                        <tr>
                        <td height="27" align="right">
                            性别：
                        </td>
                        <td>
                            <select id='sex' name="cstaff.sex">
                            </select>
                        </td>
                        <td align="right">
                            出生日期：
                        </td>
                        <td>
                            <input name="cstaff.birthday" id="birthday" size="20" />
                        </td>
                        <td width="8">
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            民族：
                        </td>
                        <td>
                            <select id="nation" name="cstaff.nation">
                            </select>
                        </td>
                        <td align="right">
                            籍贯：
                        </td>
                        <td>
                            <select id="nativePlace" name="cstaff.nativePlace">
                            </select>
                        </td>
                        <td>
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            身份证号码：
                        </td>
                        <td>
                            <input name="cstaff.staffIdentityCard" id="staffIdentityCard" size="20"/>
                        </td>
                        <td align="right">
                            国籍：
                        </td>
                        <td>
                            <select id="nationality" name="cstaff.nationality">
                            </select>
                        </td>
                        <td>
                        </td>
                    </tr>
                </table>
                <div align="center">
                </div>
           
        </div> </form>
        <!--搜索窗口 -->
         <form name="cstaffDimForm" id="cstaffDimForm" method="post">
         <input type="submit" name="submit" style="display:none"/>
        <div class="jqmWindow" style="width: 400px;right: 25%;top: 10%;" id="jqModelSearch">
           
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                            人员编号：
                        </td>
                        <td>
                            <input name="staffdimission.staffCode" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            人员姓名：
                        </td>
                        <td>
                            <input name="staffdimission.staffName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            身份证：
                        </td>
                        <td>
                            <input name="staffdimission.staffIdentityCard" />
                        </td>
                    </tr>
          			<tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="companyID" name="staffdimission.companyID">
								<option value="">
									请选择公司
								</option>
							</select>
						</td>
					</tr>	
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="searchDim" value="查询" /><input type="button" class="input-button JQueryreturn" value="取消" /><input name="search" type="hidden" value="search" />
                </div>
        </div> 
        </form>
    </body>
</html>