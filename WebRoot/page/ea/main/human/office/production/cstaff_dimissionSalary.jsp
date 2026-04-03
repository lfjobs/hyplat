<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>工资发放汇总列表</title> 
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
        <script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/cstaff_dimissionSalary.js">
        </script>
        
        <script>
        	    var pbasePath ='<%=basePath%>';
   				var ppageNumber = ${pageNumber};
   				var search = '${search}'; 
   				var pstaffName = '${staffName}';
   				var sdate = '${sdate}';
   				var edate = '${edate}'
   				var staffcategoryid ='${staffcategoryid}'
   				var arg='${arg}';
   				var pageCount='${pageForm.pageCount}';
        </script>
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                         <th width="40" >
                            请选择
                        </th>
                        <s:if test="arg==1">
	                         <th width="90" align="center">
	                            日期月份
	                        </th>
                        </s:if>
                         <th width="180">
                            公司名称
                        </th>
                        <th width="60">
                            员工姓名
                        </th>
                        <th width="60">
                            工种类别
                        </th>
                         <th width="70">
                            职务职责积分
                        </th>
                        <th width="52">
                            基本积分
                        </th>
                        <th width="52">
                            目标任务考核积分
                        </th>
                        <th width="52">
                            周末加班积分
                        </th>
                        <th width="52">
                            晚上加班积分
                        </th>
                        <th width="52">
                            节假日加班积分
                        </th>
                        <th width="52">
                            月考评积分
                        </th>
                        <th width="52">
                            任务积分
                        </th>
                        <th width="52">
                            奖励积分
                        </th>
                        <th width="52">
                            特殊人才积分
                        </th>
                        <th width="52">
                            保密工资积分
                        </th>
                        <th width="52">
                            安全积分
                        </th>
                       <th width="52">
                           应得积分
                        </th>
                        <th width="52">
                            个人所得税
                        </th>
                        <th width="52">
                            个人应交社保
                        </th>
                        <th width="52">
                            个人应交公积金
                        </th>
                        <th width="52">
                           违规折扣
                        </th>
                        <th width="52">
                            任务折扣
                        </th>
                        <th width="52">
                            暂扣安全积分 
                        </th>
                         <th width="52">
                            考勤折扣
                        </th>
                        <s:iterator value="add" status="i">
                       	<th width="52">
                       		${add[i.index]}
                       	</th>
                       </s:iterator>
                        <s:iterator value="cut" status="i">
                       	<th width="52">
                       		${cut[i.index]}
                       	</th>
                       </s:iterator>
                         <th width="52">
                            实得积分
                        </th>
                        <th width="52">
                            实得工资
                        </th>
                         <th width="52">
                           公司承担积分
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list" id="xx">
                        <tr id="${staffID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${staffID}"/>
                            </td>
                            <s:if test="arg==1">
		                       <td>
                                <span id="logBookKey">${logBookKey}</span>
                           		 </td>
                       		 </s:if>
                            <td>
                                <span id="companyName">${companyName}</span>
                            </td>
                            <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                            <td>
                                <span id="categoryname">离职员工</span>
                            </td>
                             <td>
                                <span id="funzioneIntegral">${funzioneIntegral}</span>
                            </td>
                            <td>
                                <span id="basicIntegral">${basicIntegral}</span>
                            </td>
                            <td>
                                <span id="targetTaskIntegral">${targetTaskIntegral}</span>
                            </td>
                            <td>
                                <span id="weekendIntegral">${weekendIntegral}</span>
                            </td>
                            <td>
                                <span id="workNightIntegral">${workNightIntegral}</span>
                            </td>
                            <td>
                                <span id="workHolidaysIntegral">${workHolidaysIntegral}</span>
                            </td>
                            <td>
                             <span id="appraisalIntegral">${appraisalIntegral}</span>
                            </td>
                            <td>
                                <span id="taskIntegral">${taskIntegral}</span>
                            </td>
                            <td>
                                <span id="rewardIntegral"  >${rewardIntegral}</span>
                            </td>
                            <td>
                                <span id="stPay"  >${stPay}</span>
                            </td>
                            <td>
                                <span id="secrecyPay"  >${secrecyPay}</span>
                            </td>
                            <td>
                                <span id="safetyIntegral">${safetyIntegral}</span>
                            </td>
                            <td>
                                <span id="dueIntegral">${dueIntegral}</span>
                            </td>
                            <td>
                                <span id="personalDiscount">${personalDiscount}</span>
                            </td>
                            <td>
                                <span id="personalSocialSecurity">${personalSocialSecurity}</span>
                            </td>
                            <td>
                                <span id="personalReservedFunds">${personalReservedFunds}</span>
                            </td>
                            <td>
                             <span id="violationDiscount">${violationDiscount}</span>
                            </td>
                            <td>
                                <span id="taskDiscount">${taskDiscount}</span>
                            </td>
                            <td>
                                <span id="safetyDiscount">${safetyDiscount}</span>
                            </td>
                            <td>
                                <span id="attendanceDiscount"  >${attendanceDiscount}</span>
                            </td>
                             <s:iterator value="customWageAdd" status="i">
                            	<td>
                            	<span id="add${i.index}"  >${customWageAdd[i.index]}</span>
                            	</td>
                            </s:iterator>
                              <s:iterator value="customWageCut" status="i">
                            	<td>
                            	<span id="cut${i.index}"  >${customWageCut[i.index]}</span>
                            	</td>
                            </s:iterator>
                            <td>
                                <span id="obtainedIntegral">${obtainedIntegral}</span>
                            </td>
                            <td>
                                <span id="obtainedMenoy">${obtainedMenoy}</span>
                            </td>
                             <td>
                                <span id="holidaysIntegral">${holidaysIntegral}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/cosdimission/ea_getListDimissionSalary.jspa?pageNumber=${pageNumber}&staffName=${staffName}&sdate=${sdate}&edate=${edate}&search=${search}&staffcategoryid=${staffcategoryid}&arg=${arg}">
                </c:param>
            </c:import>
        </div>
 <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
<div class="jqmWindow jqmWindowcss" style="width: 600px;top:5%" id="jqModel">
 
       <div class="drag">离职员工工资查看
	    <div class="close"></div>
	    </div>
<input type="submit" name="submit" style="display:none"/>
 <div class="content">
  <div class="contentbannb">
  </div>
 <table width="600" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
 <tr>
   <td>
     <table width="600" height="250" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
      <tr>
        <td width="125"  align="right">员工姓名：</td>
        <td width="175" ><input name="staffName" type="text" id="staffName" size="20"/></td>
         <td width="125"  align="right">职务职责积分：</td>
        <td width="175" ><input name="funzioneIntegral" type="text" id="funzioneIntegral" size="20"/></td>
      </tr>
      <tr>
      		 <td   align="right">工种类别：</td>
	        <td ><input  id="categoryname" type="text" name="categoryname"   size="20"/></td>
	        <td   align="right">目标任务考核积分：</td>
	        <td ><input  id="targetTaskIntegral" type="text" name="targetTaskIntegral"   size="20"/></td>
	    </tr>
      <tr>
        <td  align="right">基本积分：</td>
        <td ><input  id="basicIntegral" type="text" name="basicIntegral"   size="20"/></td>
        <td align="right">周末加班积分：</td>
        <td ><input id="weekendIntegral"   type="text" name="weekendIntegral"   size="20"/></td>
        </tr>
      <tr>
      <tr>
        <td  align="right">晚上加班积分：</td>
        <td ><input  id=workNightIntegral type="text" name="workNightIntegral"   size="20"/></td>
        <td align="right">节假日加班积分：</td>
        <td ><input id="workHolidaysIntegral"   type="text" name="workHolidaysIntegral"   size="20"/></td>
        </tr>
      <tr>
      <tr>
        <td align="right"> 月考评积分：</td>
        <td><input name="appraisalIntegral"  type="text"   id="appraisalIntegral" size="20"/></td>
      </tr>
      <tr>
         <td align="right">任务积分：</td>
        <td><input name="taskIntegral"  type="text"   id="taskIntegral" size="20"/></td>
         <td  align="right">奖励积分：</td>
        <td ><input  id="rewardIntegral" type="text" name="rewardIntegral"   size="20"/></td>
        </tr>
        <tr>
         <td align="right">特殊人才积分：</td>
        <td><input name="stPay"  type="text"   id="stPay" size="20"/></td>
         <td  align="right">保密工资积分：</td>
        <td ><input  id="secrecyPay" type="text" name="secrecyPay"   size="20"/></td>
        </tr>
        <tr>
        <td align="right">安全积分：</td>
        <td ><input id="safetyIntegral"   type="text" name="safetyIntegral"   size="20"/></td>
         <td align="right">应得积分：</td>
        <td ><input  id="dueIntegral" type="text" name="dueIntegral"   size="20"/></td>
        </tr>
        <tr >
        <td align="right">个人所得税：</td>
        <td ><input id="personalDiscount"   type="text" name="personalDiscount"   size="20"/></td>
          <td align="right"> 个人应交社保：</td>
        <td ><input  id="personalSocialSecurity" type="text" name="personalSocialSecurity"   size="20"/></td>
        </tr>
        <tr >
        <td align="right">个人应交公积金：</td>
        <td ><input id="personalReservedFunds"   type="text" name="personalReservedFunds"   size="20"/></td>
        </tr>
        <tr >
       	<td align="right"> 违规折扣：</td>
        <td ><input  id="violationDiscount" type="text" name="violationDiscount"   size="20"/></td>
        </tr>
        <tr>
        <td align="right">任务折扣：</td>
        <td ><input id="taskDiscount"   type="text" name="taskDiscount"   size="20"/></td>
         <td align="right">暂扣安全积分：</td>
        <td ><input  id="safetyDiscount" type="text" name="safetyDiscount"   size="20"/></td>
        </tr>
        <s:iterator value="add" status="i">
        	<s:if test="%{#i.index%2==0}">
        	<tr></s:if>
        		<td align="right">
                	<s:property value="add[#i.index]"/>：
                	</td>
   			    <td ><input id="add${i.index}"  type="text" name="add${i.index}" class="input"  size="20" value=""/></td>
        	<s:if test="%{#i.index%2!=0||add.size==#i.index+1}">
        	</tr>
        	</s:if>
        </s:iterator> 
        <s:iterator value="cut" status="i">
        	<s:if test="%{#i.index%2==0}">
        	<tr></s:if>
        		<td align="right">
                	<s:property value="cut[#i.index]"/>：
                	</td>
   			    <td ><input id="cut${i.index}"  type="text" name="cut${i.index}" class="input"  size="20" value=""/></td>
        	<s:if test="%{#i.index%2!=0||add.size==#i.index+1}">
        	</tr>
        	</s:if>
        </s:iterator>        
        <tr>
        <td align="right">考勤折扣：</td>
        <td ><input id="attendanceDiscount"   type="text" name="attendanceDiscount"   size="20"/></td>
        <td align="right">实得积分：</td>
        <td ><input  id="obtainedIntegral" type="text" name="obtainedIntegral"   size="20"/></td>
        </tr>
        <tr>
         <td align="right" >实得工资：</td>
        <td ><input  id="obtainedMenoy" type="text" name="obtainedMenoy"   size="20"/></td>
         <td align="right">公司承担积分：</td>
        <td><input id="holidaysIntegral"  type="text" name="holidaysIntegral"  size="20"/></td>
        </tr>
    </table>
    </td>
  </tr>
</table>
</div>
</div></form>

        <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 310px;right: 40%;top: 12%" id="jqModelSearch">
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
                            员工姓名：
                        </td>
                        <td>
                            <input name="staffName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                              起始时间：
                        </td>
                        <td>
                            <input name="sdate" class="put3"  onfocus="date(this);"/>
                        </td>
                    </tr>
                     <tr>
                        <td>
                               结束时间：
                        </td>
                        <td>
                            <input name="edate" class="put3"  onfocus="date(this);"/>
                        </td>
                    </tr>
                     <tr>
                        <td>
                               查询方式：
                        </td>
                        <td>
                           <select name="arg" class="put3" >
                           	<option value="0">日期未分组</option>
                           	<option value="1">日期分组</option>
                           </select>
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