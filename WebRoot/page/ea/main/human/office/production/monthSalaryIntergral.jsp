<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="hy.ea.bo.human.MonthSalary"%>
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
        <title>月工资发放汇总列表</title> 
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
        <script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/monthSalaryIntergral.js">
        </script>
        
        <script>
        	    var pbasePath ='<%=basePath%>';
   				var ppageNumber = '${pageNumber}';
   				var search = '${search}'; 
   				var pageCount='${pageForm.pageCount}';
   				var aa = 2;
        </script>
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme" style="border: 1">
                <thead>
                    <tr class="tablewith">
                         <th width="40" >
                            请选择
                        </th>
	                    <th width="90" align="center">
	                 日期月份
	                    </th>
                        <th width="180">
                            公司名称
                        </th>
                        <th width="60">
                            员工姓名
                        </th>
                        <th width="60">
                            工种类别
                        </th>
                          <th width="70" align="center">
                            岗位名称
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
                    <s:iterator value="pageForm.list" id="xx">
                        <tr id="${staffid}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${staffID}"/>
                            </td>
		                       <td>
                                <span id="months">${months}</span>
                           		 </td>
                            <td>
                                <span id="companyname">${companyname}</span>
                            </td>
                            <td>
                                <span id="staffname">${staffname}</span>
                            </td>
                            <td>
                                <span id="categoryname">${categoryname}</span>
                            </td>
                            <td>
                                <span id="postname">${postname}</span>
                            </td>
                             <td>
                                <span id="funzioneintegral">${funzioneintegral}</span>
                            </td>
                            <td>
                                <span id="basicintegral">${basicintegral}</span>
                            </td>
                            <td>
                                <span id="targettaskintegral">${targettaskintegral}</span>
                            </td>
                            <td>
                                <span id="weekendintegral">${weekendintegral}</span>
                            </td>
                            <td>
                                <span id="worknightintegral">${worknightintegral}</span>
                            </td>
                            <td>
                                <span id="workholidaysintegral">${workholidaysintegral}</span>
                            </td>
                            <td>
                             <span id="appraisalintegral">${appraisalintegral}</span>
                            </td>
                           <td>
                                <span id="taskintegral">${taskintegral}</span>
                            </td>
                            <td>
                                <span id="rewardintegral"  >${rewardintegral}</span>
                            </td>
                            <td>
                                <span id="stpay"  >${stpay}</span>
                            </td>
                            <td>
                                <span id="secrecypay"  >${secrecypay}</span>
                            </td>
                            <td>
                                <span id="safetyintegral">${safetyintegral}</span>
                            </td>
                            <td>
                                <span id="dueintegral">${dueintegral}</span>
                            </td>
                            <td>
                                <span id="personaldiscount">${personaldiscount}</span>
                            </td>
                            <td>
                                <span id="personalsocialsecurity">${personalsocialsecurity}</span>
                            </td>
                            <td>
                                <span id="personalreservedfunds">${personalreservedfunds}</span>
                            </td>
                            <td>
                             <span id="violationdiscount">${violationdiscount}</span>
                            </td>
                            <td>
                                <span id="taskdiscount">${taskdiscount}</span>
                            </td>
                            <td>
                                <span id="safetydiscount">${safetydiscount}</span>
                            </td>
                            <td>
                                <span id="attendancediscount"  >${attendancediscount}</span>
                            </td>
                            
                           <c:if test="${addlength != 0}">
                           <c:forEach var="i" begin="0" end="${addlength}" step="1">
                           	<td>
                            	<span id="add${i}">${fn:split(fn:substring(customwageadd,1,fn:length(customwageadd)-1),',')[i]}</span>
                            </td>
						   </c:forEach>
						   </c:if>
						   <c:if test="${cutlength != 0}">
						   <c:forEach var="i" begin="0" end="${cutlength}" step="1">
                           	<td>
                            	<span id="cut${i}">${fn:split(fn:substring(customwagecut,1,fn:length(customwagecut)-1),',')[i]}</span>
                            </td>
						   </c:forEach>
						   </c:if>
						   
                            <td>
                                <span id="obtainedintegral">${obtainedintegral}</span>
                            </td>
                            <td>
                                <span id="obtainedmenoy">${obtainedmenoy}</span>
                            </td>
                             <td>
                                <span id="holidaysintegral">${holidaysintegral}</span>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/monthSalary/ea_getMonthSalaryList.jspa?pageNumber=${pageNumber}&staffName=${staffName}&search=${search}">
                </c:param>
            </c:import>
        </div>
 <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
<div class="jqmWindow jqmWindowcss" style="width: 600px;top:10%" id="jqModel">
 
       <div class="drag">员工月工资查看
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
        <td width="175" ><input type="text" id="staffname" name="staffname" size="20"/></td>
         <td width="125"  align="right">职务职责积分：</td>
        <td width="175" ><input type="text" id="funzioneintegral" name="funzioneintegral" size="20"/></td>
      </tr>
      <tr>
      		 <td   align="right">工种类别：</td>
	        <td ><input type="text" id="categoryname" name="categoryname" size="20"/></td>
	        <td   align="right">目标任务考核积分：</td>
	        <td ><input type="text" id="targettaskintegral" name="targettaskintegral" size="20"/></td>
	    </tr>
      <tr>
        <td  align="right">基本积分：</td>
        <td ><input type="text" id="basicintegral" name="basicintegral" size="20"/></td>
        <td align="right">周末加班积分：</td>
        <td ><input type="text" id="weekendintegral" name="weekendintegral" size="20"/></td>
        </tr>
      <tr>
      <tr>
        <td  align="right">晚上加班积分：</td>
        <td ><input id=worknightintegral type="text" name="worknightintegral" size="20"/></td>
        <td align="right">节假日加班积分：</td>
        <td ><input id="workholidaysintegral" type="text" name="workholidaysintegral" size="20"/></td>
        </tr>
      <tr>
      <tr>
        <td align="right"> 月考评积分：</td>
        <td><input type="text" id="appraisalintegral" name="appraisalintegral" size="20"/></td>
      </tr>
      <tr>
         <td align="right">任务积分：</td>
        <td><input type="text" id="taskintegral" name="taskintegral" size="20"/></td>
         <td  align="right">奖励积分：</td>
        <td ><input  id="rewardintegral" type="text" name="rewardintegral" size="20"/></td>
        </tr>
        <tr>
         <td align="right">特殊人才积分：</td>
        <td><input type="text" id="stpay" name="stpay" size="20"/></td>
         <td  align="right">保密工资积分：</td>
        <td ><input id="secrecypay" name="secrecypay" type="text" size="20"/></td>
        </tr>
        <tr>
        <td align="right">安全积分：</td>
        <td ><input id="safetyintegral" type="text" name="safetyintegral" size="20"/></td>
         <td align="right">应得积分：</td>
        <td ><input id="dueintegral" type="text" name="dueintegral" size="20"/></td>
        </tr>
      <tr >
        <td align="right">个人所得税：</td>
        <td ><input id="personaldiscount" type="text" name="personaldiscount" size="20"/></td>
          <td align="right"> 个人应交社保：</td>
        <td ><input  id="personalsocialsecurity" type="text" name="personalsocialsecurity" size="20"/></td>
        </tr>
        <tr >
        <td align="right">个人应交公积金：</td>
        <td ><input id="personalreservedfunds" name="personalreservedfunds" type="text" size="20"/></td>
        </tr>
        <tr >
        <td align="right"> 违规折扣：</td>
        <td ><input id="violationdiscount" name="violationdiscount" type="text" size="20"/></td>
        </tr>
        <tr>
        <td align="right">任务折扣：</td>
        <td ><input id="taskdiscount" name="taskdiscount" type="text" size="20"/></td>
         <td align="right">暂扣安全积分：</td>
        <td ><input id="safetydiscount" type="text" name="safetydiscount" size="20"/></td>
        </tr>
      <s:iterator value="add" status="i">
        	<s:if test="%{#i.index%2==0}">
        	<tr></s:if>
        		<td align="right">
                	<s:property value="add[#i.index]"/>：
                	</td>
   			    <td ><input id="add${i.index}"  type="text" name="add${i.index}" class="input"  size="20" value=""/></td>
        	<s:if test="%{#i.index%2!=0||add.size==#i.index+1}"></s:if>
        </s:iterator> 
        <s:iterator value="cut" status="i">
        	<s:if test="%{#i.index%2==0}">
        	<tr></s:if>
        		<td align="right">
                	<s:property value="cut[#i.index]"/>：
                	</td>
   			    <td ><input id="cut${i.index}"  type="text" name="cut${i.index}" class="input"  size="20" value=""/></td>
        	<s:if test="%{#i.index%2!=0||add.size==#i.index+1}"></s:if>
        </s:iterator>        
        <tr>
        <td align="right">考勤折扣：</td>
        <td ><input id="attendancediscount" name="attendancediscount" type="text" size="20"/></td>
        <td align="right">实得积分：</td>
        <td ><input  id="obtainedintegral" name="obtainedintegral" type="text" size="20"/></td>
        </tr>
        <tr>
         <td align="right" >实得工资：</td>
        <td ><input  id="obtainedmenoy" name="obtainedmenoy" type="text" size="20"/></td>
         <td align="right">公司承担积分：</td>
        <td><input id="holidaysintegral" name="holidaysintegral" type="text" size="20"/></td>
        </tr>
    </table>
    </td>
  </tr>
</table>
</div>
</div></form>

        <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 280px;right: 35%;;top: 10%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="monthSalaryTable">
                    <tr>
                        <td width="40%" align="right">
                            员工姓名：
                        </td>
                        <td>
                            <input name="monthSalary.staffname" />
                        </td>
                    </tr>
                             <tr>
                        <td align="right">
                            月份：
                        </td>
                        <td>
                            <input name="monthSalary.months" onfocus="maxdaymonth(this);"/>
                        </td>
                    </tr>
                    <tr >
                        <td  align="right" >
                            员工种类：
                        </td>
                        <td ><select id="staffType" name="monthSalary.categoryname"></select> </td>
                        
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
        <script type="text/javascript">
		$(document).ready(function(){
				                   //员工类别
       	var url = "<%=basePath%>ea/saudition/sajax_n_ea_getBillID.jspa?date="+new Date().toLocaleString();
        $.ajax({
                    url: url,
                    type: "get",
                    async: true,
                    dataType: "json",
                    success: function cbf(data){
		           		var member = eval("(" + data + ")");
		           		var nologin = member.nologin;
						if(nologin){
		                	document.location.href ="<%=basePath%>page/ea/not_login.jsp";
		                }
		           		var staffTypeList = member.staffTypeList;
		           		if (null != staffTypeList) {
		           			var htmlStr="<option value=''>请选择</option>";
			               	for (var i = 0; i < staffTypeList.length; i++) {
			               		htmlStr+="<option value='"+staffTypeList[i].codeValue+"'>"+staffTypeList[i].codeValue+"</option>"	
			             	}
			             	}
			             $("#staffType").html(htmlStr);
           			},error: function cbf(data){
                          		alert("数据获取失败！");
                    }
         });	
		});
</script>
    </body>
</html>