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
        <title>人员面试管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css"/>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/staff_info.js">
        </script>
        <script src="<%=basePath%>js/ea/human/cstaff.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/SersonnelSystem/cstaff_audition_all.js"></script>
        
        <script>
               var basePath = "<%=basePath%>";
			   var ppageNumber =${pageNumber};
			   var auditionID="";
			   var token = 0;
			   var notoken = 0;
			   var start='${start}';
			   var staffID = '';
        </script>
    </head>
    <body>
        <script LANGUAGE="JavaScript">
        </script>
        <form  name="auditionForm" id="auditionForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
        <div class="main_main" >
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            选择
                        </th>
                         <th width="80" align="center">
                            状态
                        </th>
                        <th width="80" align="center">
                            人员姓名
                        </th>
                        <th width="150" align="center">
                            身份证
                        </th>
                        <th width="100" align="center">
                            应聘方向
                        </th>
                        <th width="100" align="center">
                           应聘岗位
                        </th>
                        <th width="150" align="center">
                            工作经验
                        </th>
                         <th width="100" align="center">
                            面试地点
                        </th>
                        <th width="100" align="center">
                            面试考官
                        </th>
                        <th width="80" align="center">
                           面试时间
                        </th>
                        <th width="100" align="center">
                          面试部门
                        </th>
                       
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${auditionID}">
                            <td>
                                <input type="radio" name="a" class="JQueryaudition" value="${auditionID}"/>
                            </td>
                             <td>
                                 <span id="status">
                                 <s:if test="status == '21'">面试通过</s:if>
                                 <s:if test="status == '33'">面试未通过</s:if>
                                 </span>
                                <span style="display:none" id="auditionID">${auditionID}</span>
                                <span style="display:none" id="auditionKey">${auditionKey}</span>
                                <span style="display:none" id="staffID">${staffID}</span>
                                 
                            </td>
                            <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                            <td>
                                <span id="staffIdentityCard">${staffIdentityCard}</span>
                            </td>
                            <td>
                                <span id="auditionDirection">${auditionDirection}</span>
                            </td>
                            <td>
                                <span id="auditionPost" >${auditionPost}</span>
                            </td>
                            <td>
                                <span id="experience">${experience}</span>
                            </td>
                            
                            <td>
                                <span id="place">${place}</span>
                            </td>
                            <td>
                                <span id="examiner">${examiner}</span>
                            </td>
                            <td>
                                <span id="auditionDate">${fn:substring(auditionDate, 0, 10)}</span>
                            </td>
                            <td>
                                <span id="auditionDept">${auditionDept}</span>
                                <span style="display: none;" id ="registerDate">${registerDate }</span>	
                            </td>
                        </tr>
                        <% number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/saudition/ea_getAuditionkb.jspa?start=${start}&pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
        </form>
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" action="">
                <div class="drag">
                    员工面试信息 
                    <div class="close">
                    </div>
                </div>
				   <table width="642" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="699" height="263" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="90" height="30" align="right">员工姓名：</td>
                         <td width="189"><input type="text" id="staffName" readonly="readonly"/></td>
                         	
                         <td width="80" align="right">身份证号：</td>
                         <td width="189"><input type="text" id="staffIdentityCard" readonly="readonly"/></td>
                        
                       </tr>
                       <tr>
                         <td height="30"  align="right">应聘方向：</td>
                         <td ><input id="auditionDirection"  type="text" class="input"  size="20"/></td>
                         <td  align="right">应聘岗位：</td>
                         <td ><input id="auditionPost" type="text" class="input"  size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30"  align="right">面试地点：</td>
                         <td ><input id="place" type="text" class="input"   size="20"/></td>
                         <td  align="right">面试部门：</td>
                         <td ><input id="auditionDept" type="text" class="input"  size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30"  align="right">面试时间：</td>
                         <td ><input id="auditionDate" type="text" onfocus="date(this);" class="input"  size="20"/></td>
                         <td  align="right">面试考官：</td>
                         <td ><input id="examiner" type="text" class="input"   size="20"/></td>
                       </tr>
                       <tr>
                         <td height="27" align="right"> 工作经验： </td>
                         <td colspan="4"><textarea cols="45" rows="5" class="ckTextLength" maxlength="50" id="experience"></textarea>
                         </td>
                       </tr>
                     </table></td>
				   </tr>
				</table><s:token></s:token>
                <div align="center">
                </div>
            </form>
        </div>
        <form name="employmentForm" id="employmentForm" method="post" action="">
        <div class="jqmWindow jqmWindowcss2" style="width: 750px;top:10%" id="jqMode2">
            
                <div class="drag">
                      入职管理
                    <div class="close">
                    </div>
                </div>
				   <table width="642" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"><tr>
				     <td><table width="749" height="300" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                       <tr>
                         <td width="90" height="30" align="right">员工姓名：</td>
                         <td width="239"><input type="text" id="staffName" readonly="readonly"/></td>
                         <td width="80" align="right">身份证号：</td>
                         <td width="189"><input type="text" id="staffIdentityCard" readonly="readonly"/></td>
                         
                       </tr>
                       <tr>
                         <td height="30"  align="right">应聘方向：</td>
                         <td ><input id="auditionDirection" name="audition.auditionDirection" type="text" class="input"  size="20"/></td>
                         <td  align="right">应聘岗位：</td>
                         <td ><input id="auditionPost" type="text" name="audition.auditionPost" class="input"  size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30"  align="right">面试地点：</td>
                         <td ><input id="place" type="text" class="input" name="audition.place"  size="20"/></td>
                         <td  align="right">面试部门：</td>
                         <td ><input id="auditionDept" type="text" class="input" name="audition.auditionDept" size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30"  align="right">面试时间：</td>
                         <td ><input id="auditionDate" type="text" onfocus="date(this);" class="input" name="audition.auditionDate" size="20"/></td>
                         <td  align="right">面试考官：</td>
                         <td ><input id="examiner" type="text" class="input"  name="audition.examiner" size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30"  align="right">评语：</td>
                         <td ><input id="commention" type="text" class="input" name="audition.commention"  size="20"/></td>
                         <td  align="right">分数：</td>
                         <td ><input id="auditionPoint" type="text" class="input" name="audition.auditionPoint" size="20"/></td>
                       </tr>
                       <tr>
                         <td height="30"  align="right">入职时间：</td>
                         <td ><input id="registerDate" type="text" onfocus="date(this);" class="input put3" name="audition.registerDate" size="20" readonly="readonly"/></td>
                         <td  align="right">报到地点：</td>
                         <td colspan="2" ><input id="auditionPlace" type="text" class="input"  name="audition.auditionPlace" size="20"/></td>
                       </tr>
                        <tr>
                         <td height="30"  align="right">转正时间：</td>
                         <td ><input id="becomesDate" type="text" onfocus="date(this);" class="input put3" name="audition.becomesDate" size="20" readonly="readonly" /></td>
                       </tr>
                       <tr>
                         <td height="27" align="right"> 工作经验： </td>
                         <td colspan="4"><textarea cols="45" rows="3" class="ckTextLength" maxlength="50" name="audition.experience" id="experience"></textarea>
                         </td>
                       </tr>
                       <tr>
                         <td height="27" align="right"> 备注： </td>
                         <td colspan="4"><textarea cols="45" rows="3" class="ckTextLength" maxlength="50" name="audition.remark" id="remark"></textarea>
                            </td>
                       </tr>
                     </table></td>
</tr>
				</table><s:token></s:token>
                <div align="center">               
                 <input type="button" class="input-button" id="auditionBC" value=" 保存 " />
                 <input type="button" class="input-button JQueryreturn" value="取消" />
                 	<input type="hidden" name="audition.staffID" id="staffID"/>
                  <input type="hidden" name="audition.auditionID" id="auditionID"/>
                  <input type="hidden" name="audition.auditionKey" id="auditionKey"/>
                  <input type="hidden" name="audition.status" value="21"/>
                </div>
           
        </div>
         </form>
        <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;" id="jqModelSearch">
            <form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
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
                            人员编号：
                        </td>
                        <td>
                            <input name="searchCStaff.staffCode" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            人员姓名：
                        </td>
                        <td>
                            <input name="searchCStaff.staffName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            身份证：
                        </td>
                        <td>
                            <input name="searchCStaff.staffIdentityCard" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="searchStaff" value=" 查询 " /><input type="button" class="input-button JQueryreturn" value="取消" /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>