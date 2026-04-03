<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作计划</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/human/cstaff/cstaff_jobplan.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />

<SCRIPT type="text/javascript">
   var select = 1;
   var jobPlanID = '';
   var pbasePath = '<%=basePath%>';
   var ppageNumber = ${pageNumber};
   var pstaffID = '${staffID}';
   var pserch ='${search}';
   var notoken = 0;
</SCRIPT>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form name="jobPlanForm" id="jobPlanForm" method="post"><s:token></s:token>
<input type="submit" name="submit" style="display:none"/>


<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">选择</th>
	 	     <th width="80" align="center" >录入时间</th>
            <th width="80" align="center" >起始日期</th>
            <th width="80" align="center" >结束日期</th>
            <th width="50" align="center" >项目编码</th>
            <th width="80" align="center" >项目名称</th>
            <th width="60" align="center" >岗位情况管理</th>
            <th width="50" align="center" >明细项目编号</th>
            <th width="200" align="center" >明细项目内容</th>
            <th width="50" align="center" >完成情况</th>
            
            <th width="80" align="center" >项目要求</th>
            <th width="50" align="center" >应得分数</th>
            <th width="50" align="center" >扣分</th>
            <th width="50" align="center" >实际得分</th>
            
            <th width="80" align="center" >主管签字</th>
            <th width="80" align="center" >人事主管</th>
			<th width="80" align="center" >公司经理</th>
      </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
            <td >
             <input type="radio" name="a" class="JQuerypersonvalue" value="${jobPlanID }"/>
             </td>
             <td class="td_bg01"><input   name="entryDate" id="entryDate"   size="10" readonly="readonly" value="系统生成" /></td>
            <td class="td_bg01"><input class="aaaa"  name="strStartDate" id="startDate"   onfocus="date(this)" size="16"/></td>
              <td class="td_bg01"><input  class="aaaa" name="strEndDate" id="endDate"  onfocus="date(this)" size="16"/></td>
             <td class="td_bg01"><input  name="projectcode" id="projectcode" size="10"/> </td>
            <td class="td_bg01"><input  name="jobName" id="jobName" class="ckTextLength" maxlength="250"></td>
             <td class="td_bg01"><s:select  list="{'专职', '兼职'}" class="model1" name="postmanage" id ="xxx"></s:select>
			</td>
			<td class="td_bg01"><input  name="projectDetailsCode" id="projectDetailsCode" size="10"/> </td>
            <td class="td_bg01"><input size="57" name="jobContent" id="jobContent" class="ckTextLength" maxlength="250" size="10"/> </td>
             <td class="td_bg01">
             <s:select list="{'是','否'}" 	 name="entry" id="xxx"  class="model1"></s:select>
             </td>
             
             <td class="td_bg01"><input  name="projectRequirements" id="projectRequirements" size="10"></td>
             <td class="td_bg01"><input  name="fraction" id="fraction" size="10"></td>
             <td class="td_bg01"><input  name="points" id="points" size="10"></td>
             <td class="td_bg01"><input  name="actualScore" id="actualScore" size="10"></td>
             
            <td class="td_bg01"><input  name="supervisor" id="supervisor" size="10"></td>
			<td class="td_bg01"><input  name="humanSupervisor" id="humanSupervisor" size="10"></td>
            <td class="td_bg01"><input  name="manager" id="manager" size="10"/>
					            <input type="hidden" name="staffID" value="${staffID}" id="staffID" />
			</td> 
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${jobPlanID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" id="${jobPlanID }"/>
            </td>
            <td class="td_bg01">
                <span id="entryDate" >${fn:substring(entryDate,0,19)}</span>
				<input class="model1" value="${fn:substring(entryDate,0,19)}" name="entryDate" readonly="readonly"  size="16"/></td>
            <td class="td_bg01">
                <span id="startDate" >${fn:substring(startDate,0,10)}</span>
				<input class="model1" value="${fn:substring(startDate,0,10)}" name="strStartDate"   onclick="daytime(this)"   size="16"/></td>
		    <td class="td_bg01">
               <span id="endDate" >${fn:substring(endDate,0,10)}</span>
				<input class="model1" value="${fn:substring(endDate,0,10)}" name="strEndDate"  onclick="daytime(this)"   size="16"/></td>
		    <td class="td_bg01">
               <span id="projectcode" >${projectcode}</span>
				<input class="model1"  value="${projectcode}" name="projectcode"  size="10" /></td>
		    <td class="td_bg01">
                <span id="jobName" >${jobName}</span>
				<input class="model1" value="${jobName}" name="jobName" size="20" /></td>
			<td class="td_bg01">
                <span id="postmanage" style="display:none">${postmanage}</span>
				 <s:select  list="{'专职', '兼职'}" class="model1" name="postmanage" ></s:select>
				</td>
			<td class="td_bg01">
               <span id="projectDetailsCode" >${projectDetailsCode}</span>
				<input class="model1"  value="${projectDetailsCode}" name="projectDetailsCode"  size="10" /></td>	
            <td class="td_bg01">
               <span id="jobContent" >${jobContent}</span>
				<input class="model1" size="57" value="${jobContent}" name="jobContent"  size="10" /></td>
			<td class="td_bg01">
               <span id="entry" style="display:none">${entry}</span>
				  <s:select list="{'是','否'}" 	 name="entry"  class="model1" value="entry" ></s:select></td>
			
			<td class="td_bg01">
             <span id="projectRequirements">${projectRequirements}</span>
            <input class="model1"  name="projectRequirements" value="${projectRequirements}" size="10"></td>
            <td class="td_bg01">
             <span id="fraction">${fraction}</span>
            <input class="model1"  name="fraction" value="${fraction}" size="10"></td>
            <td class="td_bg01">
             <span id="points">${points}</span>
            <input class="model1"  name="points" value="${points}" size="10"></td>
            <td class="td_bg01">
             <span id="actualScore">${actualScore}</span>
            <input class="model1"  name="actualScore" value="${actualScore}" size="10"></td>	
				
				
            <td class="td_bg01">
             <span id="supervisor">${supervisor}</span>
            <input class="model1"  name="supervisor" value="${supervisor}" size="10"></td>
			 <td class="td_bg01">
             <span id="humanSupervisor">${humanSupervisor}</span>
            <input class="model1"  name="humanSupervisor" value="${humanSupervisor}" size="10"></td>
            <td class="td_bg01">
             <span id="manager">${manager}</span>
            <input class="model1"  name="manager" value="${manager}" size="10"/>
					            <input type="hidden" name="jobPlanKey" value="${jobPlanKey}"/>
					            <input type="hidden" name="jobPlanID" value="${jobPlanID}"/>
					            <input type="hidden" name="staffID" value="${staffID}"/>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/jobplan/ea_getJobPlanList.jspa?pageNumber=${pageNumber}&staffID=${staffID}&search=${search}"></c:param>
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
                           <input   name="jobPlan.startDate" class="put3" onfocus="date(this);" />至 <input  name="jobPlan.endDate" class="put3" onfocus="date(this);" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " />
                    
                    <input name="search" type="hidden" value="search" />
                    <input type="hidden" name="staffID" value="${staffID}" />
                </div>
        </div>   </form>
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
