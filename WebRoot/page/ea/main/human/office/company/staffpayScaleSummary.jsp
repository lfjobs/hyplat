<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
        <title>员工级别列表——总公司、子公司</title>
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
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/staff_info.js">
        </script>
        <script src="<%=basePath%>js/ea/human/cstaff.js"></script>
        <script src="<%=basePath%>js/ea/human/office/company/staffpayScaleSummary.js"></script>
    </head>
    <body>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script LANGUAGE="JavaScript">
         var treeID = '<%=session.getAttribute("organizationID")%>';
         var comID = '<%=c.getCompanyID()%>';
		 var comName = '<%=c.getCompanyName()%>';
         var staffID="";
         var basePath="<%=basePath%>";
         var search="${search}";
         var pNumber='${pageNumber}';
 
        </script>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                         <th width="200" align="center">
                            公司姓名
                        </th>
                         <th width="100" align="center">
                            员工姓名
                        </th>
                        <th width="100" align="center">
                            员工编号
                        </th>
						<th width="100" align="center">
                            性别
                        </th>
                        <th width="150" align="center">
                            身份证
                        </th>
                        <th width="100" align="center">
                            岗位级差
                        </th>
                         <th width="100" align="center">
                            基本工资
                        </th>
						<th width="100" align="center">
                            职务职责级别
                        </th>
                        <th width="100" align="center">
                           月考评
                        </th>
                        <th width="100" align="center">
                            月度任务
                        </th>
                        <th width="100" align="center">
                            月季年目标
                        </th>
                        <th width="100" align="center">
                            计件工资
                        </th>
                        <th width="100" align="center">
                            全勤奖
                        </th>
                        <th width="100" align="center">
                            奖励工资
                        </th>
                        <th width="100" align="center">
                            孝道金
                        </th>
                        <th width="100" align="center">
                            竞职金
                        </th>
                        <th width="100" align="center">
                            通讯补助
                        </th>
                        <th width="100" align="center">
                            生活补助
                        </th>
                        <th width="100" align="center">
                           特殊人才工资
                        </th>
                        <th width="100" align="center">
                            保密工资
                        </th>
                        <th width=100 align="center">
                            安全奖
                        </th>
                         <th width=100 align="center">
           PK金
                        </th>
                     <th width="100" align="center">
         	  全部都计
                        </th>
                        <th width="100" align="center">
                            周末加班
                        </th>
                        <th width="100" align="center">
                            晚上加班
                        </th>
                        <th width="100" align="center">
                            节假日加班
                        </th>   
                        <th width="100" align="center">
                            考勤工资
                        </th>
                        <th width="100" align="center">
                            津贴费用
                        </th>
                        <th width="100" align="center">
                            折扣费用
                        </th>
                         <th width="100" align="center">
                            个人保险费
                        </th>
                        <th width="100" align="center">
                           单位保险费
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="pageForm.list">
                        <tr id="${staffID}">
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/>
                            </td>
                             <td>
                                <span id="companyname">${companyname}</span>
                            </td>
                            <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                            <td>
                                <span id="staffCode">${staffCode}</span>
                            </td>
							<td>
                                <span id="sex">${sex}</span>
                            </td>
                            <td>
                                <span id="staffIdentityCard">${staffIdentityCard}</span>
                            </td>
                            <td>
                                <span id="scale">${scale}</span>
                            </td>
                            <td>
                                <span id="basePay">${basePay}</span>
                            </td>
                            <td>
                                <span id="positionPay">${positionPay}</span>
                            </td>
                             <td>
                                <span id="pushMoney">${pushMoney}</span>
                            </td>
                             <td>
                                <span id="timingMoney">${timingMoney}</span>
                            </td>
                             <td>
                                <span id="goaltasks">${goaltasks}</span>
                            </td>
                             <td>
                                <span id="piecerate">${piecerate}</span>
                            </td>
                            <td>
                                <span id="paAward">${paAward}</span>
                            </td>
                             <td>
                                <span id="awardPay">${awardPay}</span>
                            </td>
                             <td>
                                <span id="pietypay">${pietypay}</span>
                            </td>
                             <td>
                                <span id="campaignpay">${campaignpay}</span>
                            </td>
                             <td>
                                <span id="telecompay">${telecompay}</span>
                            </td> 
                            <td>
                                <span id="living">${living}</span>
                            </td>
                            <td>
                                <span id="stPay">${stPay}</span>
                            </td>
                             <td>
                                <span id="secrecyPay">${secrecyPay}</span>
                            </td>
                             <td>
                                <span id="safetyAward">${safetyAward}</span>
                            </td>
                             <td>
                                <span id="pkpay">${pkpay}</span>
                            </td>
                            <td>
                                <span id="totalPay">${totalPay}</span>
                            </td>
                            <td>
                                <span id="workWeek">${workWeek}</span>
                            </td>
                            <td>
                                <span id="workNight">${workNight}</span>
                            </td>
                            <td>
                                <span id="workHolidays">${workHolidays}</span>
                            </td>
                           <td>
                                <span id="kkqnpay">${kkqnpay}</span>
                            </td>
                           <td>
                                <span id="expenseAllowances">${expenseAllowances}</span>
                            </td>
                            <td>
                                <span id="discountedCost">${discountedCost}</span>
                            </td>
                             <td>
                                <span id="premium">${premium}</span>
                            </td>
                            <td>
                                <span id="unpremium">${unpremium}</span>
                                 <span style="display:none" id="payScaleID">${payScaleID}</span>
                                <span style="display:none" id="payScalekey">${payScalekey}</span>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/payscale/ea_getStaffPayScaleSummaryList.jspa?search=${search}&pageNumber=${pageNumber}">
                </c:param>
            </c:import>
            <div>
                <iframe src="" name="main" width="100%" marginwidth="0" height="500px" marginheight="0" scrolling="no" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
        </div>
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                        员工工资级别详细信息 
                    <div class="close">
                    </div>
                </div>
                <table width="690" height="211" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable">
                    <tr>
                        <td width="147" align="right">员工姓名：</td>
                        <td ><input type="text"  class="model3" readonly="readonly"  id="staffName" size="20"/></td>
                        <td align="right">员工编号：</td>
                        <td><input  class="model3" id="staffCode" readonly="readonly" size="20"/> </td>
                    </tr>
                    <tr>
                        <td width="147" align="right">性别：</td>
                        <td ><input type="text"  class="model3" readonly="readonly" id="sex" size="20"/></td>
                        <td align="right">身份证号：</td>
                        <td><input  class="model3" readonly="readonly" id="staffIdentityCard" size="20"/> </td>
                    </tr>
                    <tr>
                         <td height="27" align="right">基本工资：</td>
                        <td><input name="payscale.basePay" class="model3 isNaN" id="basePay" size="20"/></td>
                        <td align="right">职务职责级别：</td>
                        <td><input name="payscale.positionPay" class="model3 isNaN" id="positionPay" size="20"/></td>
                    </tr>
                    
                     <tr>
                        <td align="right">月考评：</td>
                        <td><input name="payscale.pushMoney" class="model3  isNaN" id="pushMoney" size="20"/></td>
                        <td align="right">月度任务：</td>
                        <td><input name="payscale.timingMoney" class="model3  isNaN" id="timingMoney" size="20"/></td>                    </tr>
                    <tr>
                        
                        <td align="right">月季年目标：</td>
                        <td><input name="payscale.goaltasks" class="model3  isNaN" id="goaltasks" size="20"/></td>
                         <td align="right">计件工资：</td>
                        <td><input name="payscale.piecerate" class="model3  isNaN" id="piecerate" size="20"/></td>
                    </tr>
                     <tr>
                        <td align="right">全勤奖：</td>
                        <td><input name="payscale.paAward" class="model3  isNaN" id="paAward" size="20"/></td>
                         <td height="27" align="right">奖励工资：</td>
                        <td><input name="payscale.awardPay" class="model3  isNaN" id="awardPay" size="20"/></td>
                    </tr>
                      <tr>
                        <td align="right">孝道金：</td>
                        <td><input name="payscale.pietypay" class="model3  isNaN" id="pietypay" size="20"/></td>
                         <td height="27" align="right">竞职金：</td>
                        <td><input name="payscale.campaignpay" class="model3  isNaN" id="campaignpay" size="20"/></td>
                    </tr>
                     <tr>
                        <td align="right">通讯补助：</td>
                        <td><input name="payscale.telecompay" class="model3  isNaN" id="telecompay" size="20"/></td>
                         <td height="27" align="right">生活补助：</td>
                        <td><input name="payscale.living" class="model3  isNaN" id="living" size="20"/></td>
                    </tr>
                     <tr>
                    	<td height="27" align="right">特殊人才工资：</td>
                        <td><input name="payscale.stPay" class="model3  isNaN" id="stPay" size="20"/></td>
                        <td height="27" align="right">保密工资：</td>
                        <td><input name="payscale.secrecyPay" class="model3  isNaN" id="secrecyPay" size="20" /></td>
                    </tr>
                    <tr>
                        <td align="right">安全奖：</td>
                        <td><input name="payscale.safetyAward" class="model3 isNaN" id="safetyAward" size="20"/></td>
                         <td height="27" align="right">PK金：</td>
                        <td><input name="payscale.pkpay" class="model3 isNaN" id="pkpay" size="20"/></td>
                    </tr>
                    <tr>
                    	<td height="27" align="right">全部都计：</td>
                        <td><input  class="model3 isNaN" id="totalPay" size="20" readonly="readonly"/></td>
                         <td align="right">周末加班：</td>
                        <td><input name="payscale.workWeek" class="model3  isNaN" id="workWeek" size="20"/></td>
                    </tr>
                    <tr>
                         <td height="27" align="right">晚上加班： </td>
                        <td><input name="payscale.workNight" class="model3  isNaN" id="workNight" size="20"/></td>
                        <td height="27" align="right">节假日加班：</td>
                        <td><input name="payscale.workHolidays" class="model3  isNaN" id="workHolidays" size="20" /></td>
                    </tr>
                    <tr>
                        <td align="right">考勤工资：</td>
                        <td><input name="payscale.kkqnpay" class="model3 isNaN" id="kkqnpay" size="20"/></td>
                        <td width="147" align="right">津贴费用：</td>
                        <td ><input name="payscale.expenseAllowances" class="model3 isNaN" id="expenseAllowances" size="20"/></td>
                    </tr>
                    <tr>
                         <td align="right">折扣费用： </td>
                        <td><input name="payscale.discountedCost" class="model3 isNaN" id="discountedCost" size="20"/></td>
                         <td align="right">个人保险费：</td>
                        <td><input name="payscale.premium" class="model3 isNaN" id="premium" size="20"/></td>
                    </tr>
                    <tr>
                    	<td align="right">单位保险费：</td>
                        <td><input name="payscale.unpremium" class="model3 isNaN" id="unpremium" size="20"/></td>
                      	<td align="right">公司名称：</td>
                    	<td><input type="text"  class="model3" readonly="readonly" id="companyname" size="30"/></td>
                    </tr>
                </table>
               <div align="center">  
                     <input type="button"   class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="返回" />  
    </div> 
    <s:token></s:token>
            </form>
        </div>
        <!--搜索窗口 -->
         <form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
        <div class="jqmWindow jqmWindowcss3" style="width: 500px;;top: 10%" id="jqModelSearch">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
              <table width="395" id="cataffSearchTable">
          <tr>
                        <td width="122" align="right">
                            人员编号：                        </td>
				<td width="261">
				<input name="payvo.staffCode" /></td>
                </tr>
                    <tr>
                        <td align="right">
                            人员姓名：                        </td>
                  <td>
                            <input name="payvo.staffName" />
                        </td>
                    </tr>
                <tr>
                  <td align="right">
                            身份证：                        </td>
                  <td>
                            <input name="payvo.staffIdentityCard" />
                        </td>
                    </tr>
                <tr>
                  <td align="right">
                            岗位级差：                        </td>
                  <td>
                            <input name="payvo.scale" />
                        </td>
                    </tr>
                 <tr>
                  <td align="right">
                            职务：                        </td>
                  <td>
                            <input name="payvo.position" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            公司名称：
                        </td>
                        <td>
                          <select id="companyID" name="payvo.cspcompanyid">
								<option value="">
									请选择公司
								</option>
							</select>
                        </td>
                    </tr>
                </table>
                <div align="center">
                  <input type="button" class="input-button" id="searchStaff" value="查询" /><input name="search" type="hidden" value="search" />
                </div>
        </div></form>
    </body>
</html>