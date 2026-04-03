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
        <title>公共日程安排</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
                <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
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
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script src="<%=basePath%>js/ea/office_ea/information/Schedule.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  scheduleID = '';
         var  token=0;
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
                         <th width="180" align="center">
                           所在部门	
                        </th>
                         <th width="150" align="center">
                            员工姓名
                        </th>
                         <th width="180" align="center">
                            工作内容
                        </th>
                         <th width="180" align="center">
                           日程安排
                        </th>
                         <th width="180" align="center">
                           工作状态
                        </th>
                         <th width="180" align="center">
                           完成评价
                        </th> 
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number">
                        <tr id="${scheduleID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${scheduleID}"/>
                            </td>
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
                            <td>
                                <span id="corganizationID" style="display:none">${corganizationID}</span>${corganizationName }
                            </td>
                            <td>
                               <span id="staffID" style="display:none">${staffID}</span>${staffName }
                            </td>
                            <td>
                                <span id="workContent">${workContent}</span>
                            </td>
                            <td>
                                <span id="workSchedule">${workSchedule}</span>
                            </td>
                            <td>
                                <span id="workStatus">${workStatus}</span>
                            </td> 
                            
                            <td>
                                <span id="appraise">${appraise}</span>
                                <span id="scheduleKey" style="display:none">${scheduleKey}</span>
                                <span id="scheduleID" style="display:none">${scheduleID}</span>
                                <span id="companyID" style="display:none">${companyID}</span>
                                <span id="organizationID" style="display:none">${organizationID}</span> 
                            </td>  
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/schedule/ea_getScheduleList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div> 
 	<div class="contentbannb jqmWindow jqmWindowcss3" style="top: 10%;width: 450px;" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    详细信息
                    <div class="close">
                    </div>
                </div>
  				<table cellpadding="5px" cellspacing="10px" name="stafftable" id="stafftable" style="margin-left: 30px"> 
				    <tr>
				    	<td>
				    		所在部门:
				    	</td>
				    	<td>
				    		<s:select list="orgnizationList" headerKey="" headerValue="请选择" listKey="organizationID" listValue="organizationName" id="corganizationID" name="schedule.corganizationID"></s:select>
				    	</td>
				    	<td>
				    		员工姓名:
				    	</td>
				    	<td> 
				    		 <s:select list="%{#request.staffList}" id="staffID" headerKey="" headerValue="请选择" listKey="staffID" listValue="staffName" name="schedule.staffID" theme="simple"></s:select>
				    	</td>
				    </tr>
				    <tr>
				    	<td>
				    		工作内容:
				    	</td>
				    	<td>
				    		<input name="schedule.workContent" id="workContent" />
				    	</td>
				    	<td>
				    		日程安排:
				    	</td>
				    	<td>
				    		 <input name="schedule.workSchedule" id="workSchedule" />
				    	</td>
				    </tr>
				    <tr>
				    	<td>
				    		完成状态:
				    	</td>
				    	<td>
				    		<input name="schedule.workStatus" id="workStatus" />
				    	</td>
				    	<td>
				    		完成评价:
				    	</td>
				    	<td>
				    		 <input name="schedule.appraise" id="appraise" />
				    	</td>
				    </tr>
				    <tr>
				    	<td colspan="4" align="center">
				    		 <input type="hidden" name="schedule.scheduleID" id="scheduleID" />
				            <input type="hidden" name="schedule.scheduleKey" id="scheduleKey" />
				            <input type="hidden" name="schedule.companyID" id="companyID" />
				            <input type="hidden" name="schedule.organizationID" id="organizationID" />
						    <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
				    	</td>
				    </tr>
				  </table>
				   <s:token></s:token> 
			</form>
	</div> 
	
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 300px;right: 35%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="260px" id="cataffSearchTable"> 
                    <tr>
                        <td align="right">
                            工作状态：
						</td>
                        <td>
                        	 <input name="schedule.workStatus" id="workStatus" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            人员姓名：
						</td>
                        <td>
                        	<s:select list="%{#request.staffList}" id="staffID" headerKey="" headerValue="全部" listKey="staffID" listValue="staffName" name="schedule.staffID" theme="simple"></s:select>
                        </td>
                    </tr>
                     <tr>
                        <td align="right">
                            所在部门：
						</td>
                        <td>
                        	<s:select list="orgnizationList" headerKey="" headerValue="全部" listKey="organizationID" listValue="organizationName" id="corganizationID" name="schedule.corganizationID"></s:select>
                        </td>
                    </tr>
                </table> 
            <div align="center"> 
              <input type="button" class="input-button" id="searchStaff" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>