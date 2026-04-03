<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作目标任务汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/human/office/production/jobtasksummary.js"></script>

<SCRIPT type="text/javascript">
   var addressID = '';
   var pbasePath ='<%=basePath%>';
   var ppageNumber = ${pageNumber};
   var psearch = '${search}'; 
</SCRIPT>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">选择</th>
			<th width="100" align="center">姓名</th>
			<th width="130" align="center">编号</th>
            <th width="100" align="center" >任务时间</th>
            <th width="130" align="center" >任务编号</th>
            <th width="130" align="center" >任务名称</th>
            <th width="100" align="center" >任务完成起时间</th>
            <th width="130" align="center" >任务完成止时间</th>
            <th width="100" align="center" >任务类别</th>
			<th width="130" align="center" >完成奖分设定</th>
			<th width="130" align="center" >未完成扣分设定</th>
			<th width="100" align="center" >检查任务时间</th>
			<th width="150" align="center" >检查任务完成情况</th>
			<th width="100" align="center" >完成奖分</th>
			<th width="100" align="center" >未完成扣分</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list">
        <tr class="td_bg01 saveAjax" id="${jobTaskID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue"/>
            </td>
		<td class="td_bg01">
               <span id="staffName" >${staffName}</span>
				</td>
		<td class="td_bg01">
               <span id="staffCode">${staffCode}</span>
				</td>
		<td class="td_bg01">
               <span id="neededTime">${neededTime}</span></td>
		 <td class="td_bg01">
        	   <span id="taskNumber">${taskNumber}</span></td>
			<td class="td_bg01">
           		<span id="taskName" class="datas">${taskName}</span></td>
            <td class="td_bg01">
                <span id="startDate" class="datas">${startDate}</span></td>
            <td class="td_bg01">
               <span id="endDate" class="datas">${endDate}</span></td>
            <td class="td_bg01">
               <span id="codeValue" class="datas">${codeValue}</span></td>
            <td class="td_bg01">
               <span id="bonusPoint" class="datas">${bonusPoint}</span></td>
			<td class="td_bg01">
               <span id="penaltyPoint" class="datas">${penaltyPoint}</span></td>
            <td class="td_bg01">
             <span id="checkUpTime" class="datas">${checkUpTime}</span></td>
			 <td class="td_bg01">
             <span id="actualperformance">${actualperformance}</span></td>
			<td class="td_bg01">
             <span id="actualBonusPoint">${actualBonusPoint}</span></td>
            <td class="td_bg01">
             <span id="actualPenaltyPoint">${actualPenaltyPoint}</span>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/jobtask/ea_getJobTaskListSummary.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>

</div>
  <!--搜索窗口 -->
  <form name="postSearchForm" id="postSearchForm" method="post">
     <div class="jqmWindow" style="width:500px;right: 25%;top: 10%;" id="jqModelSearch">
          
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
            <tr>
                        <td width="126" align="right">
                            人员姓名：                        </td>
                   <td width="359">
                  <input   name="jobTaskVO.staffName"   />
                        </td>
                  </tr>
              <tr>
                        <td align="right">
                            计划起始日期：                  </td>
                        <td>
                        	
                          <input   name="jobTaskVO.startDate"  onfocus="date(this);" />至 <input  name="jobTaskVO.endDate"  onfocus="date(this);" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
           
        </div>
		 </form>
		<!--查看 -->
		 <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
		<div class="jqmWindow jqmWindowcss" style="width: 600px;top:10%" id="jqModel">
			       <div class="drag">工作任务
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
			        <td width="114"  align="right">员工编号：</td>
			        <td width="181" ><input name="staffCode" type="text" id="staffCode" size="20"/></td>
			        <td width="122"  align="right">员工姓名：</td>
			        <td width="183" ><input name="staffName" type="text" id="staffName" size="20"/></td>
			      </tr>
			      <tr>
			        <td  align="right">任务时间：</td>
			        <td ><input  id="neededTime" type="text"  class="input"  size="20"/></td>
			        <td align="right">任务编号：</td>
			        <td ><input id="taskNumber"   type="text"  class="input"  size="20"/></td>
			        </tr>
			      <tr>
			        <td align="right">任务名称：</td>
			        <td><input  id="taskName" type="text" name="supervisor" class="input"  size="20"/></td>
			          <td  align="right">任务完成起时间：</td>
			        <td ><input  id="startDate" type="text"  class="input"  size="20"/></td>
			        </tr>
					 <tr>
			        <td align="right">任务完成止时间：</td>
			        <td ><input id="endDate"   type="text"  class="input"  size="20"/></td>
			           <td  align="right">完成奖分设定：</td>
			        <td ><input  id="bonusPoint" type="text"  class="input"  size="20"/></td>
			        </tr>
				<tr>
			        <td align="right">未完成扣分设定：</td>
			        <td ><input id="penaltyPoint"   type="text"  class="input"  size="20"/></td>
			        <td  align="right">检查任务时间：</td>
			        <td ><input  id="checkUpTime" type="text"  class="input"  size="20"/></td>
			        </tr>
				<tr>
			        <td align="right">检查任务完成情况：</td>
			        <td ><input id="actualperformance"   type="text"  class="input"  size="20"/></td>
			         <td  align="right">完成奖分：</td>
			        <td ><input  id="actualBonusPoint" type="text"  class="input"  size="20"/></td>
			        </tr>
				<tr>
			        <td align="right">未完成扣分：</td>
			        <td ><input id="actualPenaltyPoint"   type="text"  class="input"  size="20"/></td>
			        </tr>
			    </table>
			    </td>
			  </tr>
			</table>
			</div>
		</div>
			</form>
</body>
</html>
