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
<script type="text/javascript" src="<%=basePath %>js/ea/person/cstaff_jobtask.js"></script>
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
   var personName='${personName}';
   var scoreSortlist = '${scoreSortlist}';
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
            <th width="80" align="center" >任务编号</th>
            <th width="150" align="center" >项目计划</th>
            <th width="180" align="center" >任务名称</th>
            <th width="100" align="center" >任务完成起时间</th>
            <th width="100" align="center" >任务完成止时间</th>
            <th width="100" align="center" >任务类别</th>
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
            <td class="td_bg01" id="coval"><s:select list="scoreSortlist" id="codeID" name="codeID" listKey="codeID" listValue="codeValue" ></s:select>
            </td>
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
               <span id="neededTime" class="datas">${datemonth}</span>
				<input class="model1" value="${datemonth}" name="datemonth"   size="10"/></td>
		 <td class="td_bg01">
        	   <span id="taskNumber" class="datas">${taskNumber}</span>
				<input class="model1" value="${taskNumber}" name="taskNumber"  size="10"/></td>
				<td class="td_bg01">
           		<span id="taskName" class="datas">${projectName}</span>
			<input class="model1" value="${projectName}" name="projectName"   size="10"/></td>
			<td class="td_bg01">
           		<span id="taskName" class="datas">${taskName}</span>
			<input class="model1" value="${taskName}" name="taskName"   size="10"/></td>
            <td class="td_bg01">
                <span id="startDate" class="datas">${startDate}</span>
				<input class="model1" value="${startDate}" name="startDate"  onfocus="date(this);" size="10"/></td>
            <td class="td_bg01">
               <span id="endDate" class="datas">${endDate}</span>
				<input class="model1" value="${endDate}" name="endDate"  onfocus="date(this);" size="10"/></td>
			  <td class="td_bg01" >
              	<s:select  class="model1"  list="scoreSortlist" id="codeID" name="codeID" listKey="codeID" listValue="codeValue" ></s:select>
				</td>
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
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/jobtask/ea_getJobTaskList_a.jspa?staffID=${staffID}&search=${search}&pageNumber=${pageNumber}"></c:param>
</c:import>                
</div>
</form>

 <!--搜索窗口 -->
 <form name="postSearchForm" id="postSearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 35%;top: 10%;" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    
                    <tr>
                        <td>
                            计划起始日期：
                        </td>
                        <td>
                           <input   name="jobTask.startDate" class="put3" onfocus="date(this);"  style="width: 80px;"/>至 <input  name="jobTask.endDate" class="put3" onfocus="date(this);"  style="width: 80px;"/>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" /><input type="hidden" name="staffID" value="${staffID}" />
                </div>
        </div>
         </form>
         
    
	
		<!--添加修改工作计划窗口 -->
	<form name="addtaskForm" id="addtaskForm" method="post">
		<div class="jqmWindow" style="width: 700px;right: 25%;top: 10%;"
			id="jqModeltask">

			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				添加任务目标<font style="color:red;font-size:11px;margin-left:300px;">注：带红色*号为必填项</font>
				<div class="close"></div>
			</div>
			<table id="monthjobplan">
				<tr>
					<td>
						<table id="cataffaddplanTable">
							<tr>
								<td>项目计划：<font style="color:red">*</font></td>
								<td><input name="jobPlan.jobName" id="jobName" class="aaaa" />
								</td>
								<td>任务名称：</td>
								<td><input name="jobPlan.personName" id="personName"
									value="${personName}" readonly="readonly" /></td>
								<td>任务类别：<font style="color:red">*</font></td>
								<td><s:select list="scoreSortlist" id="codeID" class="sel"
										name="jobPlan.codeID" listKey="codeID" listValue="codeValue"></s:select></td>
								<td style="display: none">录入时间：</td>
								<td style="display: none"><input name="jobPlan.entryDate"
									value="自动生成" readonly="readonly" id="entryDate" /></td>
							</tr>
							<tr>
								<td colspan="6">任务计划时间：</td>
							</tr>
							
							<tr>
								<td colspan="6"><input type="checkbox" />1<input type="checkbox" />2</td>
							</tr>
							<tr>
								<td colspan="6">项目工作计划内容</td>
							</tr>
							<tr>
								<td colspan="6"><input name="jobPlan.jobContent" id="jobContent"
									style="width: 600px; height: 50px;" type="text" /></td>
							</tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
								<td>工作状态:</td>
								<td><input name="jobPlan.entry" id="entry" value="待办事项" readonly="readonly"/>
								</td>
								<td>工作时间状态</td>
								<td>
									<s:select list="#{'02':'月工作计划','00':'日工作计划','01':'周工作计划','03':'季工作计划','04':'年工作计划'}" name="jobPlan.jobstatus" id="jobstatus" class="model1" width="30" theme="simple"></s:select>
								</td>
								<td>发起人：</td>
								<td><input name="jobPlan.staffNameS" id="staffNameS" readonly="readonly"/></td>
							</tr>
							<tr>
								<td>主管签字：</td>
								<td><input type="text" name="jobPlan.supervisor" readonly="supervisor" /></td>
								<td>人事主管：</td>
								<td><input type="text" name="jobPlan.humanSupervisor" readonly="humanSupervisormanager" /></td>
								<td>公司经理：</td>
								<td><input type="text" name="jobPlan.manager" readonly="manager" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button JQuerySubmitgd" name="addProjectPlan" style="cursor:pointer;width:80px;" value="保存" />
				<input type="button" class="input-button JQueryClose" name="button2" style="cursor:pointer;width:80px;" value="返回" /> 
				<input type="hidden" name="jobPlan.jobPlanKey" id="jobPlanKey" /> 
				<input type="hidden" name="jobPlan.jobPlanID" id="jobPlanID" />
				<input type="hidden" name="jobPlan.staffID" id="staffID" />
				<input type="hidden" name="jobPlan.staffIDS" id="staffIDS" />
				<input type="hidden" name="jobPlan.companyID" id="companyID" />
				<input type="hidden" name="jobPlan.companyIDS" id="companyIDS" />
				<input type="hidden" name="jobPlan.organizationID" id="organizationID" />
				<input type="hidden" name="jobPlan.staffName" id="staffName" />
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
