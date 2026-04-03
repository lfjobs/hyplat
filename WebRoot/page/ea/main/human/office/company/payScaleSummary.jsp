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
        <title>工资级别汇总列表</title>
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
         <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <script src="<%=basePath%>js/ea/human/staff_info.js">
        </script>
        <script src="<%=basePath%>js/ea/human/cstaff.js">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
          <script type="text/javascript" src="<%=basePath%>js/ea/human/office/company/payScaleSummary.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        
        <script type="text/javascript">
       		var treeID = '<%=session.getAttribute("organizationID")%>';
       		var comID = '<%=c.getCompanyID()%>';
			var comName = '<%=c.getCompanyName()%>';
        	var token = 0;
            var payScaleID = "";
            var basePath = "<%=basePath%>";
            var ppageNumber=${pageNumber};    
            var search="${search}";
            
        </script>
    </head>
    <body>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                        <th width="200" align="center"> 
                            公司名称
                        </th>
                        <th width="150" align="center">
                            职务
                        </th>
                        <th width="100" align="center">
                            岗位级差
                        </th>
						<th width="100" align="center">
                            职务职责级别
                        </th>
                        <th width="100" align="center">
                            基本工资
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
                           月考评
                        </th>
                        <th width="100" align="center">
                            任务考评
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
                           特殊人才工资
                        </th>
                        <th width="100" align="center">
                            保密工资
                        </th>
                        
                         <th width="100" align="center">
                            安全奖
                        </th>
                        <th width="100" align="center">
                            津贴费用
                        </th>
                        <th width="100" align="center">
                            折扣费用
                        </th>
                         <th width="100" align="center">
                            保险费
                        </th>
                        <th width="100" align="center">
                            安全费
                        </th>
                        <th width="100" align="center">
                            小计
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="pageForm.list">
                        <tr id="${payScaleID}">
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue" value="${payScaleID}"/>
                            </td>
                            <td>
                            	<span id="companyName">${companyName}</span>
                            </td>
                            <td>
                                <span id="position">${position}</span>
                            </td>
                            <td>
                                <span id="scale">${scale}</span>
                            </td>
							<td>
                                <span id="positionPay">${positionPay}</span>
                            </td>
                            <td>
                                <span id="basePay">${basePay}</span>
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
                                <span id="pushMoney">${pushMoney}</span>
                            </td>
                            <td>
                                <span id="timingMoney">${timingMoney}</span>
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
                                <span id="stPay">${stPay}</span>
                            </td>
                            <td>
                                <span id="secrecyPay">${secrecyPay}</span>
                            </td>
                            <td>
                                <span id="safetyAward">${safetyAward}</span>
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
                                <span id="alimony">${alimony}</span>
                            </td>
                            <td>
                                <span id="totalPay">${totalPay}</span>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/payscale/ea_getPayScaleList.jspa?search=${search}&pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post">
                <div class="drag">
                        工资级别详细信息 
                    <div class="close">
                    </div>
                </div>
                <table width="690" height="211" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable">
                    <tr>
                        <td width="147" align="right">岗位级差： </td>
                        <td ><input type="text"  class="model3" readonly="readonly" id="scale" size="20"/></td>
                        <td align="right"> 职务：</td>
                        <td><input class="model3" id="position" readonly="readonly" size="20"/></td>
                    </tr>
                     <tr>
                        <td align="right">职务职责级别：</td>
                        <td><input  class="model3" id="positionPay" readonly="readonly" size="20"/></td>
                        <td height="27" align="right">基本工资：</td>
                        <td><input  class="model3" id="basePay" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                        <td align="right">周末加班：</td>
                        <td><input  class="model3 " id="workWeek" readonly="readonly" size="20"/></td>
                         <td height="27" align="right">晚上加班： </td>
                        <td><input  class="model3 " id="workNight" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                        <td height="27" align="right">节假日加班：</td>
                        <td><input  class="model3 " id="workHolidays" readonly="readonly" size="20" /></td>
                        <td align="right">月考评：</td>
                        <td><input class="model3 " id="pushMoney" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                        <td width="147" align="right">任务考核：</td>
                        <td ><input  class="model3 " id="timingMoney" readonly="readonly" size="20"/></td>
                        <td align="right">计件工资：</td>
                        <td><input  class="model3 " id="piecerate" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                        <td align="right">全勤奖：</td>
                        <td><input class="model3 " id="paAward" readonly="readonly" size="20"/></td>
                         <td height="27" align="right">奖励工资：</td>
                        <td><input class="model3 " id="awardPay" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                         <td height="27" align="right">特殊人才工资：</td>
                        <td><input class="model3 " id="stPay" readonly="readonly" size="20"/></td>
                        <td height="27" align="right">保密工资：</td>
                        <td><input class="model3 " id="secrecyPay" readonly="readonly" size="20" /></td>
                    </tr>
                       <tr>
                        <td width="147" align="right">津贴费用：</td>
                        <td ><input class="model3" id="expenseAllowances" readonly="readonly" size="20"/></td>
                        <td align="right">折扣费用： </td>
                        <td><input class="model3" id="discountedCost" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                        <td align="right">保险费：</td>
                        <td><input class="model3" id="premium" readonly="readonly" size="20"/></td>
                         <td height="27" align="right">生活费：</td>
                        <td><input class="model3" id="alimony" readonly="readonly" size="20"/></td>
                    </tr>
                    <tr>
                        <td align="right">安全奖：</td>
                        <td><input class="model3" id="safetyAward" readonly="readonly" size="20"/></td>
                        <td align="right">公司名称：</td>
                    	<td><input type="text"  class="model3" readonly="readonly" id="companyName" size="30"/></td>
                    </tr>
                    <tr>
                        <td height="27" align="right">小计：</td>
                        <td><input  class="model3" id="totalPay" readonly="readonly" size="20" /></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
               <div align="center">  
                     <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="取消" />  
               </div> 
    <s:token></s:token>
            </form>
        </div>
        
        <!-- 收索窗口 -->
		<form name="appraisalForm" id="appraisalForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">

				<input type="submit" name="submit" style="display: none" />
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
                             公司名称：
                        </td>
                        <td>
                          <select id="companyID" name="payscale.companyID">
								<option value="">
									请选择公司
								</option>
							</select>
                        </td>
                    </tr>
                    <tr>
                    	<td>
                    		职务：
                    	</td>
                    	<td>
                    		<input name="payscale.position" type="text" />
                    	</td>
                    </tr>
                     <tr>
                    	<td>
                    		岗位级差：
                    	</td>
                    	<td>
                    		<input name="payscale.scale" type="text" />
                    	</td>
                    </tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch" value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div/>
			</div>
		</form>
    </body>
</html>