<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
        <title>工资级别列表</title>
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
        <script>
        	var token = 0;
            var payScaleID = "";
            var basePath = "<%=basePath%>";
            var ppageNumber=${pageNumber};
        </script>
        <script type="text/javascript" src="<%=basePath%>js/ea/human/office/SersonnelSystem/payScale.js"></script>
        
    </head>
    <body>
        <script LANGUAGE="JavaScript">
        </script>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                        <th width="80" align="center">
                              序号
                        </th>
                        <th width="150" align="center">
                             录入时间
                        </th>
                        <th width="100" align="center">
                            级别序号
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
         	  全部统计
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
                <%
                        int number = 1;
                    %>
                    <s:iterator value="pageForm.list">
                        <tr id="${payScaleID}">
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue" value="${payScaleID}"/>
                            </td>
                                   <td>
                                <span id="companyName"><%=number%></span>
                            </td>
                             <td>
                                <span id="impotdate">${impotdate}</span>
                            </td>
                            <td>
                                <span id="num">${num} </span>&nbsp;级
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
                        <%
                            number++;
                        %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/payscale/ea_getListPayScale.jspa?pageNumber=${pageNumber}">
                </c:param>
            </c:import> 
        </div>
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                   工资级别详细信息 
                    <div class="close">
                    </div>
                </div>
                <table width="690" height="211" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable">
                      <tr>
                        <td width="147" align="right">
                          	 级别序号：
                        </td>
                        <td >
                            <input type="text" name="payscale.num"  class="model3 put3" value='' id="num" size="20"/>
                        </td>
                        <td width="147" align="right">
                          	  岗位级差：
                        </td>
                        <td >
                            <input type="text" name="payscale.scale"  class="model3 isUniquenessScale put3" value='' id="scale" size="20"/>
                        </td>
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
                        <td align="right">&nbsp;</td>
                        <td><input name="payscale.payScaleID" type="hidden" class="model3" id="payScaleID" size="20" />
                            <input name="payscale.payScalekey" type="hidden" class="model3" id="payScalekey" size="20" />
                        </td>
                    </tr>
                </table>
               <div align="center">  
               		
                     <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />  
                     <input type="button"   class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="取消" />  
    </div> 
    <s:token></s:token>
            </form>
        </div>
        <iframe src="" name="main" width="100%" id="mainframe"  frameborder="0" noresize="noresize" border="0" framespacing="0" height="0">
        </iframe>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>