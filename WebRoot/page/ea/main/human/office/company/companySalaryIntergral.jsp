<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@page import="hy.ea.bo.Company"%>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>公司工资发放汇总列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
         <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/company/companySalaryIntergral.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        
        <script type="text/javascript">
           var treeID = '<%=session.getAttribute("organizationID")%>';
           var comID = '<%=c.getCompanyID()%>';
           var comName = '<%=c.getCompanyName()%>';
           var basePath='<%=basePath%>';           
           var search='${search}';          
           var startdate='${sdate}';
           var enddate='${edate}';
           var staffName='${staffName}';
           var pNumber = '${pageNumber}';
           var pcompanyID = '${companyID}';
           var arg='${arg}';
           var staffcategoryid ='${staffcategoryid}';
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
                        <s:if test="arg==1">
	                         <th width="90" align="center">
	                            日期月份
	                        </th>
                        </s:if>
                         <th width="200" align="center">
                            公司名称
                        </th>
                         <th width="100" align="center">
                            员工姓名
                        </th>
                        <th width="100" align="center">
                            工种类别
                        </th>
                         <th width="100" align="center">
                            岗位名称
                        </th>
                         <th width="100" align="center">
                            职务职责积分
                        </th>
                        <th width="100" align="center">
                            基本积分
                        </th>
                        <th width="100" align="center">
                            目标任务考核积分
                        </th>
                        <th width="52" align="center">
                            周末加班积分
                        </th>
                        <th width="52" align="center">
                            晚上加班积分
                        </th>
                        <th width="52" align="center">
                            节假日加班积分
                        </th>
                        <th width="100" align="center">
                            月考评积分
                        </th><%--
                        <th width="100" align="center">
                            计件积分 
                        </th>
                         --%><th width="100" align="center">
                            任务积分
                        </th>
                        <th width="52" align="center">
                            奖励积分
                        </th>
                        <th width="52" align="center">
                            特殊人才积分
                        </th>
                        <th width="52" align="center">
                            保密工资积分
                        </th>
                        <th width="100" align="center">
                            安全积分
                        </th><%--
                        <th width="100" align="center">
                            补助话费
                        </th>
                        <th width="100" align="center">
                            生活补助
                        </th>
                        --%>
                         <th width="52">
                          孝道金积分
                        </th>
                         <th width="52">
                           竞职金积分
                        </th>
                         <th width="52">
                           话费补助积分
                        </th>
                         <th width="52">
                           生活补助积分
                        </th>
                         <th width="52">
           PK金积分
                        </th>
                        <th width="100" align="center">
                           应得积分
                        </th>
                        <th width="52" align="center">
                            个人所得税
                        </th>
                        <th width="52" align="center">
                            个人应交社保
                        </th>
                        <th width="52" align="center">
                            个人应交公积金
                        </th><%--
                        <th width="52" align="center">
                            扣电话费
                        </th>
                        <th width="52" align="center">
                            扣生活费
                        </th>
                        --%>
                        <th width="100" align="center">
                           违规折扣
                        </th>
                        <th width="100" align="center">
                            任务折扣
                        </th>
                        <th width="100" align="center">
                            暂扣安全积分 
                        </th>
                         <th width="100" align="center">
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
                         <th width="100" align="center">
                            实得积分
                        </th>
                        <th width="100" align="center">
                            实得工资
                        </th>
                        <th width="100" align="center">
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
                                <span id="categoryname">${categoryname}</span>
                            </td>
                            <td>
                                <span id="postname">${postname}</span>
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
                            </td><%--
                            <td>
                                <span id="pieceIntegral">${pieceIntegral}</span>
                            </td>
                            --%><td>
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
                            </td><%--
                            <td>
                                <span id="phoneSubsidy">${phoneSubsidy}</span>
                            </td>
                            <td>
                                <span id="livingSubsidy">${livingSubsidy}</span>
                            </td>
                              --%>
                              <td>
                                <span id="dueIntegral">${pietypay}</span>
                            </td>
                              <td>
                                <span id="dueIntegral">${campaignpay}</span>
                            </td>
                              <td>
                                <span id="dueIntegral">${telecompay}</span>
                            </td>
                              <td>
                                <span id="dueIntegral">${living}</span>
                            </td>
                              <td>
                                <span id="dueIntegral">${pkpay}</span>
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
                            </td><%--
                            <td>
                                <span id="phoneDiscount">${phoneDiscount}</span>
                            </td>
                            <td>
                                <span id="liveDiscount">${liveDiscount}</span>
                            </td>
                            --%><td>
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
                <c:param name="actionPath" value="ea/logbooksummary/ea_getcompanyIntegral.jspa?pageNumber=${pageNumber}&companyID=${companyID}&staffName=${staffName}&sdate=${sdate}&edate=${edate}&search=${search}&arg=${arg}&staffcategoryid=${staffcategoryid}">
                </c:param>
            </c:import>
        </div>
<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
<div class="jqmWindow jqmWindowcss" style="width: 600px;top:10%" id="jqModel">
 
       <div class="drag">员工工资查看
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
	        <td  align="right">工种类别：</td>
	        <td ><input  id="categoryname" type="text" name="categoryname" class="input"  size="20"/></td>
	        <td  align="right">目标任务考核积分：</td>
	        <td ><input  id="targetTaskIntegral" type="text" name="targetTaskIntegral" class="input"  size="20"/></td>
	    </tr>
      <tr>
        <td  align="right">基本积分：</td>
        <td ><input  id="basicIntegral" type="text" name="basicIntegral" class="input"  size="20"/></td>
        <td align="right">周末加班积分：</td>
        <td ><input id="weekendIntegral"   type="text" name="weekendIntegral" class="input"  size="20"/></td>
        </tr>
      <tr>
      <tr>
        <td  align="right">晚上加班积分：</td>
        <td ><input  id=workNightIntegral type="text" name="workNightIntegral" class="input"  size="20"/></td>
        <td align="right">节假日加班积分：</td>
        <td ><input id="workHolidaysIntegral"   type="text" name="workHolidaysIntegral" class="input"  size="20"/></td>
        </tr>
      <tr>
      <tr>
        <td align="right"> 月考评积分：</td>
        <td><input name="appraisalIntegral"  type="text" class="input"  id="appraisalIntegral" size="20"/></td><%--
         <td align="right">计件积分：</td>
        <td><input  id="pieceIntegral" type="text" name="pieceIntegral" class="input"  size="20"/></td>
      --%></tr>
      <tr>
         <td align="right">任务积分：</td>
        <td><input name="taskIntegral"  type="text" class="input"  id="taskIntegral" size="20"/></td>
         <td  align="right">奖励积分：</td>
        <td ><input  id="rewardIntegral" type="text" name="rewardIntegral" class="input"  size="20"/></td>
        </tr>
        <tr>
         <td align="right">特殊人才积分：</td>
        <td><input name="stPay"  type="text" class="input"  id="stPay" size="20"/></td>
         <td  align="right">保密工资积分：</td>
        <td ><input  id="secrecyPay" type="text" name="secrecyPay" class="input"  size="20"/></td>
        </tr><%--
        <tr>
         <td align="right">补助话费积分：</td>
        <td><input name="phoneSubsidy"  type="text" class="input"  id="phoneSubsidy" size="20"/></td>
         <td  align="right">生活补助积分：</td>
        <td ><input  id="livingSubsidy" type="text" name="livingSubsidy" class="input"  size="20"/></td>
        </tr>
       --%><tr>
        <td align="right">安全积分：</td>
        <td ><input id="safetyIntegral"   type="text" name="safetyIntegral" class="input"  size="20"/></td>
         <td align="right">应得积分：</td>
        <td ><input  id="dueIntegral" type="text" name="dueIntegral" class="input"  size="20"/></td>
        </tr>
      <tr >
        <td align="right">个人所得税：</td>
        <td ><input id="personalDiscount"   type="text" name="personalDiscount" class="input"  size="20"/></td>
          <td align="right"> 个人应交社保：</td>
        <td ><input  id="personalSocialSecurity" type="text" name="personalSocialSecurity" class="input"  size="20"/></td>
        </tr>
        <tr >
        <td align="right">个人应交公积金：</td>
        <td ><input id="personalReservedFunds"   type="text" name="personalReservedFunds" class="input"  size="20"/></td><%--
          <td align="right">扣电话费：</td>
        <td ><input  id="phoneDiscount" type="text" name="phoneDiscount" class="input"  size="20"/></td>
        --%></tr>
        <tr >
        <%--<td align="right">扣生活费：</td>
        <td ><input id="liveDiscount"   type="text" name="liveDiscount" class="input"  size="20"/></td>
          --%><td align="right"> 违规折扣：</td>
        <td ><input  id="violationDiscount" type="text" name="violationDiscount" class="input"  size="20"/></td>
        </tr>
        <tr>
        <td align="right">任务折扣：</td>
        <td ><input id="taskDiscount"   type="text" name="taskDiscount" class="input"  size="20"/></td>
         <td align="right">暂扣安全积分:</td>
        <td ><input  id="safetyDiscount" type="text" name="safetyDiscount" class="input"  size="20"/></td>
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
        <td ><input id="attendanceDiscount"   type="text" name="attendanceDiscount" class="input"  size="20"/></td>
        <td align="right">实得积分：</td>
        <td ><input  id="obtainedIntegral" type="text" name="obtainedIntegral" class="input"  size="20"/></td>
        </tr>
        <tr>
         <td align="right" >实得工资：</td>
        <td ><input  id="obtainedMenoy" type="text" name="obtainedMenoy" class="input"  size="20"/></td>
         <td align="right">公司承担积分：</td>
        <td><input id="holidaysIntegral"  type="text" name="holidaysIntegral" class="input" size="20"/></td>
        </tr>
    </table>
    </td>
  </tr>
</table>
</div>
</div></form>
        <!--搜索窗口 -->
         <form name="postSearchForm" id="postSearchForm" method="post">
        <div class="jqmWindow jqmWindowcss3" style="width: 500px;top: 10%" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
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
                            员工姓名：
                        </td>
                        <td>
                            <input name="staffName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            公司名称：
                        </td>
                        <td>
                            <select id="companyID" name="companyID">
								<%--<option value="">
									请选择公司
								</option>
							--%></select>
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
                    <tr >
                        <td  align="right" >
                            员工种类：
                        </td>
                        <td ><select id="staffType" name="staffcategoryid"></select> </td>
                        
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
        </div>
         </form>
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
			               		htmlStr+="<option value='"+staffTypeList[i].codeID+"'>"+staffTypeList[i].codeValue+"</option>"	
			             	}
			             	}
			             $("#staffType").html(htmlStr);
           			},error: function cbf(data){
                          		alert("数据获取失败！")
                    }
         });	
		})
</script>
    </body>
</html>