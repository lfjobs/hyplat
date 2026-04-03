<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作目标任务</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/human/cstaff/cstaff_jobtask.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript">
   var token = 0;
   var select = 1;
   var jobTaskID='';
   var pbasePath = '<%=basePath%>';
   var ppageNumber = ${pageNumber};
   var pstaffID = '${staffID}';
   var notoken = 0;
   var pserch='${search}';
</SCRIPT>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form  name="jobTaskForm" id="jobTaskForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
<div id="main_main" class="main_main">
  <table   class="jobTask">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">选择</th>
            <th width="100" align="center" >任务时间</th>
            <th width="100" align="center" >任务编号</th>
            <th width="180" align="center" >任务名称</th>
            <th width="100" align="center" >任务完成起时间</th>
            <th width="100" align="center" >任务完成止时间</th>
			<th width="100" align="center" >完成奖分设定</th>
			<th width="100" align="center" >未完成扣分设定</th>
			<th width="100" align="center" >检查任务时间</th>
			<th width="100" align="center" >检查任务完成情况</th>
			<th width="100" align="center" >完成奖分</th>
			<th width="100" align="center" >未完成扣分</th>
      </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
            <td >
             <input type="radio" name="a" class="JQuerypersonvalue" />
             </td>
			<td class="td_bg01"><input  name="neededTime" id="neededTime" size="10"/> </td>
			<td class="td_bg01"><input  name="taskNumber" id="taskNumber" size="10"/> </td>
			<td class="td_bg01"><input  name="taskName" id="taskName" class="ckTextLength" maxlength="250" size="10"/> </td>
            <td class="td_bg01"><input  name="startDate" id="startDate" class="aaaa" onfocus="date(this);" size="10"/></td>
            <td class="td_bg01"><input  name="endDate" id="endDate" class="aaaa" onfocus="date(this);" size="10"/></td>
            <td class="td_bg01"><input  name="bonusPoint" id="bonusPoint" size="10"/> </td>
            <td class="td_bg01"><input  name="penaltyPoint" id="penaltyPoint" size="10"></td>
			<td class="td_bg01"><input  name="checkUpTime" id="checkUpTime" onfocus="date(this);" size="10"/></td>
			<td class="td_bg01"><input  name="actualperformance" id="actualperformance" size="10"></td>
			<td class="td_bg01"><input  name="actualBonusPoint" id="actualBonusPoint" size="10"></td>
            <td class="td_bg01"><input  name="actualPenaltyPoint" id="actualPenaltyPoint" size="10"/>
					            <input type="hidden" name="staffID" value="${staffID}" id="staffID" />
			</td> 
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${jobTaskID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue"/>
            </td>
			 <td class="td_bg01">
               <span id="neededTime" class="datas">${neededTime}</span>
				<input class="model1" value="${neededTime}" name="neededTime"   size="10"/></td>
		 <td class="td_bg01">
        	   <span id="taskNumber" class="datas">${taskNumber}</span>
				<input class="model1" value="${taskNumber}" name="taskNumber"  size="10"/></td>
			<td class="td_bg01">
           		<span id="taskName" class="datas">${taskName}</span>
			<input class="model1" value="${taskName}" name="taskName"   size="10"/></td>
            <td class="td_bg01">
                <span id="startDate" class="datas">${startDate}</span>
				<input class="model1" value="${startDate}" name="startDate"  onfocus="date(this);" size="10"/></td>
            <td class="td_bg01">
               <span id="endDate" class="datas">${endDate}</span>
				<input class="model1" value="${endDate}" name="endDate"  onfocus="date(this);" size="10"/></td>
            <td class="td_bg01">
               <span id="bonusPoint" class="datas">${bonusPoint}</span>
				<input class="model1" value="${bonusPoint}" name="bonusPoint"   size="10"/></td>
			<td class="td_bg01">
               <span id="penaltyPoint" class="datas">${penaltyPoint}</span>
				<input class="model1" value="${penaltyPoint}" name="penaltyPoint"   size="10"/></td>
            <td class="td_bg01">
             <span id="checkUpTime" class="datas">${checkUpTime}</span>
            <input class="model1" value="${checkUpTime}" name="checkUpTime"  onfocus="date(this);" size="10"/></td>
			 <td class="td_bg01">
             <span id="actualperformance">${actualperformance}</span>
            <input class="model1"  name="actualperformance" value="${actualperformance}" size="10"/></td>
			<td class="td_bg01">
             <span id="actualBonusPoint">${actualBonusPoint}</span>
            <input class="model1"  name="actualBonusPoint" value="${actualBonusPoint}" size="10"/></td>
            <td class="td_bg01">
             <span id="actualPenaltyPoint">${actualPenaltyPoint}</span>
            <input class="model1"  name="actualPenaltyPoint" value="${actualPenaltyPoint}" size="10"/>
					            <input type="hidden" name="jobTaskKey" value="${jobTaskKey}"/>
					            <input type="hidden" name="jobTaskID" value="${jobTaskID}"/>
					            <input type="hidden" name="staffID" value="${staffID}" />
								<input type="hidden" name="status" value="${status}"/>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/jobtask/ea_getJobTaskList.jspa?staffID=${staffID}&search=${search}&pageNumber=${pageNumber}"></c:param>
</c:import>                
</div>
</form>

 <!--搜索窗口 -->
 <form name="postSearchForm" id="postSearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 25%;top: 10%;" id="jqModelSearch">
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
                            计划起始日期：
                        </td>
                        <td>
                           <input   name="jobTask.startDate" class="put3" onfocus="date(this);" />至 <input  name="jobTask.endDate" class="put3" onfocus="date(this);" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" /><input type="hidden" name="staffID" value="${staffID}" />
                </div>
        </div>
         </form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	<script type="text/javascript">
    $(function(){       
        $(window).resize(function(){ 
			setTimeout(function(){ 				    			    
			    if($("#mainframe").height() > 0){
			        $(".bDiv").css({"height":( $("#mainframe").height() / 2 - 31 - 30 - 26 -50) + "px"});
			    }
			},100);
        }); 
    });
</script>
</body>
</html>
