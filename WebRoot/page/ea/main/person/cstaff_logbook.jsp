<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人工作日志</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/common/ui.css">

<script type="text/javascript" src="<%=basePath%>js/common/jquery-ui-1.11.1.custom/jquery-ui.js"></script>
<script type="text/javascript">
	   var select =1;
	   var selects =1;
	   var token = 0;
	   var logBookID = '';
	   var today=new Date();
	   var month = today.getMonth()+1;
	   var toda=today.getFullYear()+"-"+month+"-"+today.getDate();
	   var basePath='<%=basePath%>';
	   var ppageNumber='${pageNumber}';
	   var logbookstaffID='${logbook.staffID}';
	   var psdate='${sdate}';
	   var pedate='${edate}';
	   var staffName='${staffName}';
	   var scoreSort = '${scoreSort}';
	   var status = '${status}';
	   var notoken = 0;
</script>

<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
 <script>
  $(function() {

    $( "#slider1" ).slider({
      range: "min",
      value: 0,
      min: 0,
      max: 100,
      slide: function( event, ui ) {
        $( "#amount1" ).val(ui.value+"%");
      }
    });
    $( "#amount1" ).val($("#slider1" ).slider( "value" )+"%");
    
    
 
    
  });

</script>


</head>
<body>


<div id="main_main" class="main_main">
<form action="" name="logFrom" id="logFrom" method="post"><s:token></s:token>
   <input type="submit" name="submit" style="display:none"/></form>
 <form  name="logbookForm" enctype="multipart/form-data" id="logbookForm" method="post"><s:token></s:token>
  <table   class="logbook" id="tab0">
  <thead>
	 	    <tr>
 	        <th width="20" align="center">选择</th>
 	        <th width="100" align="center" >日期</th>
 	        <th width="160" align="center" >录入时间</th>
            <th width="80" align="center" >起时间</th>
            <th width="80" align="center" >止时间</th>
            <th width="170" align="center" >工作地点</th>
            <th width="350" align="center" >完成工作内容</th>
            <th width="114" align="center" >得分类别</th>
            <th width="60" align="center" >得分</th>
            <th width="154" align="center" >附件</th>
            <th width="100" align="center" >主管签字</th>
            <th width="100" align="center" >人事主管管理</th>
            <th width="100" align="center" >公司经理</th>  
            <th width="60" align="center" >状态</th>
      </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
            <td >
             <input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/>
             </td>
             <td class="td_bg01"><input  name="todaydate" id="todaydate" onfocus="WdatePicker({maxDate:'%y-%M-%d'})" size="10" /></td>
             <td class="td_bg01">
     		 <span id="inputDate">${fn:substring(inputDate, 0, 19)}系统自动生成</span>
     			<input name="inputDate" class="model1" id="inputDate"   readonly="readonly"  size="10"/>
    		</td>
            <td class="td_bg01"><input   name="startdate" id="startdate" size="5"/></td>
            
            <td class="td_bg01"><input   name="enddate" id="enddate" size="5"/></td>
            <td class="td_bg01"><input   name="jobLocation" id="jobLocation"/></td>
            <td class="td_bg01"><input   name="jobContent" id="jobContent" maxlength="120" size="50"/></td>
            <td class="td_bg01"> 基本积分
            <input name="scoreSort" type="hidden" id="scoreSort"  value="scode201007306kdf8m76me0000000002"/>
             </td>
            <td class="td_bg01"><input  name="bisect" id="bisect" size="3" class="baseScore"/>
					            <input type="hidden" name="staffID" value="${logbook.staffID}" id="staffID" />
					            <input type="hidden" name="logBookKey" id="logBookKey"/>
					            <input type="hidden" name="logBookID" id="logBookID" />
			</td> 
			<td class="td_bg01">
              <input name="photo" size="10" id="photo" type="file" contentEditable="false"/> 
			</td>
            <td class="td_bg01"><input   name="supervisor" id="supervisor" size="10" /></td>
            <td class="td_bg01"><input  name="staffingManage" id="staffingManage" size="10"/></td>
            <td class="td_bg01"><input  name="manager" id="manager" size="10"/></td>
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${logBookID}">
           <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" value="${logBookID}"/>
           </td>
           <td class="td_bg01">
                <span id="todaydate"  class="datas">${todaydate}</span>
				<input class="model1" value="${todaydate}" name="todaydate" id="todaydate"  onfocus="WdatePicker({maxDate:'%y-%M-%d'})" size="10"/></td>
		   <td class="td_bg01">
			<span id="inputDate">${fn:substring(inputDate, 0, 19)}</span>
			<input class="model1" value="${fn:substring(inputDate,0,19)}" name="inputDate" readonly="readonly"  size="16"/>
			</td>
          <td class="td_bg01">
                <span id="startdate">${startdate}</span>
				<input class="model1" value="${startdate}" name="startdate" size="5"/></td>
          <td class="td_bg01">
               <span id="enddate">${enddate}</span>
				<input class="model1" value="${enddate}" name="enddate" size="5"/></td>
		  <td class="td_bg01">
                <span id="jobLocation">${jobLocation}</span>
				<input class="model1" value="${jobLocation}" name="jobLocation" /></td>
			<td class="td_bg01">
               <span id="jobContent">${jobContent}</span>
				<input class="model1" value="${jobContent}"  name="jobContent" maxlength="120"  size="50"/></td>
            <td class="td_bg01">
               ${scoreSortName}
             </td>
            <td class="td_bg01">
              <span id="bisect">${bisect}</span>
              <input class="model1"  name="bisect" value="${bisect}" id="bisect" size="3" />
					            <input type="hidden" name="status" value="${status}"/>
					            <input type="hidden" name="staffID" value="${staffID}"/>
					            <input type="hidden" name="logBookKey"  value="${logBookKey}" id="logBookKey"/>
					            <input type="hidden" name="logBookID" value="${logBookID}" id="logBookID" />
					            <input type="hidden" name="logbook.organizationID" value="${logbook.organizationID}" id="organizationID" />
					            <input type="hidden" id="scoreSorts" value="${scoreSort}" name="scoreSort" />
			</td> 
			 <td class="td_bg01">
			           <span><s:if test="attachment==null||attachment==''">无</s:if></span>
                             <s:else>
                                <span id="photo"  onclick="lookImage('${attachment}');"><a href="#">查看</a></span>
                            </s:else>
			    <input name="photo" type="file" size="10" class="model1" size="10" contentEditable="false"/>
			    <input name="attachment" type="hidden" value="${attachment}" class="model1"/>
			</td>
            <td class="td_bg01">
               <span id="supervisor">${supervisor}</span>
				<input class="model1" value="${supervisor}" name="supervisor" size="10"/></td>
            <td class="td_bg01">
               <span id="staffingManage">${staffingManage}</span>
				<input class="model1" value="${staffingManage}" name="staffingManage" size="10"/>
            </td>
            <td class="td_bg01">
                <span id="manager">${manager}</span>
				<input class="model1" value="${manager}" name="manager" size="10"/>
			</td>
            <td class="td_bg01">
                       <input type="hidden" class="model1" id="status" value="${status}" />
			           <span>
			           		${status=='01'?'已加锁':'未加锁'}
			           </span>
			</td>
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/logbook/ea_getListLogBook.jspa?sdate=${sdate}&edate=${edate}&pageNumber=${pageNumber}&scoreSort=${scoreSort}&status=${status}"></c:param>
</c:import>
</form>
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
                            得分类别：
                        </td>
                        <td> 
                            	 <s:select list="scoreSortlist"  listKey="codeID" listValue="codeValue" name="scoreSort"  id="scoreSort" theme="simple" headerKey="" headerValue="全部"></s:select>
                          
                        </td>
                    </tr>
                    <tr>
                        <td>
                            时间：
                        </td>
                        <td>
                            <input name="sdate"   onfocus="date(this);"/>至<input name="edate"  onfocus="date(this);"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            状态：
                        </td>
                        <td>
                            <select name="status">
                            	<option value="">全部</option>
                            	<option value="01">已加锁</option>
                            	<option value="00">未加锁</option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
                 </div>
            </form>
            
      <form name="logbookaddForm" id="logbookaddForm" method="post" enctype="multipart/form-data">
      <div class="contentbannb jqmWindow jqmWindowcss1" style="top: 15%" id="jqModel">
        <input type="submit" name="submit" style="display:none"/>
        <div class="content">
	  	<div class="contentbannb">
	  	<div class="drag">个人工作日志
	    </div>
      </div>
  
	  <table width="900" height="46" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
	    <tr>
			<td width="80" height="30" align="right">
				<span class="STYLE1">公司：</span>
			</td>
			<td width="220" align="left">
				<span id="companyname">${companyname}</span>
			</td>
			<td width="140" align="right">
				部门：
			</td>
			<td width="120" align="left">
				<span id="organizationname">${organizationname}</span>
			</td>
			<td width="160" align="right">
				岗位：
			</td>
			<td align="left">
				<span id="postname">${postname}</span>
			</td>
		</tr>
		<tr height="30">
			<td align="right">
				责任人：
			</td>
			<td align="left">
				<span id="staffName">${staffName}</span>
			</td>
			<td align="right">
				人员编号：
			</td>
			<td align="left">
				<span id="staffCode">${staffCode}</span>
			</td>
			<td align="right">
				<span class="xx">*</span>日期：
			</td>
			<td align="left"><!--  onchange="toconten()" -->
				<input name="logbook.todaydate" id="todaydate" class="put3" onfocus="WdatePicker({maxDate:'%y-%M-%d'})" onchange="getjobplan()" size="10"/>
				<input type="hidden" name="logbook.logBookKey"  value="${logbook.logBookKey}" id="logBookKey"/>
				<input type="hidden" name="logbook.logBookID" value="${logbook.logBookID}" id="logBookID"/>
				<input type="hidden" name="logbook.organizationID" value="${logbook.organizationID}" id="organizationID"/>
				<input type="hidden" name="logbook.staffID" value="${logbook.staffID}" id="staffID" />
				<input type="hidden" name="logbook.status" value="${logbook.status}" id="status" />
				<input type="hidden" name="logbook.inputDate" value="${logbook.inputDate}" id="inputDate" />
			</td>
		</tr>
	  </table>
	  <table width="900" height="180"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
	    <tr>
			<td width="120" height="30" align="right">
				<span class="xx">*</span>起时间：
			</td>
			<td width="180" align="left">
				<input name="logbook.startdate" class="errdate timeformat" id="startdate" size="5" style="margin-left:2px;"/>
			</td>
			<td width="120" align="right">
				<span class="xx">*</span>止时间：
			</td>
			<td width="180" align="left">
				<input name="logbook.enddate" class="errdate timeformat" id="enddate" size="5" style="margin-left:2px;"/>
			</td>
			<td width="115" align="right">
				<span class="xx">*</span>基本得分：
			</td>
			<td align="left">
				<input name="logbook.bisect" id="bisect" size="10" class="baseScore posnum" style="margin-left:2px;"/>
				<input name="logbook.scoreSort" type="hidden" id="scoreSort"  value="scode201007306kdf8m76me0000000002"/>
			</td>
		</tr>
		<tr>
			<td width="120" height="30" align="right" >
				工作地点：
			</td>
			<td colspan="5" align="left" id="gggg">
			
				<input  name="logbook.jobLocation" id="jobLocation" size="45"></input> 
			
			 
			</td>
		</tr>
		<tr><td align="right">工作内容：</td><td align="left" colspan="5"><input type="button"  id="newline" class="input-button" value=" 新增一行 "/></td></tr>
						<tr>
							<td colspan="6">
								<input  style="display: none" name="logbook.jobContent" id="jobContent" ></input> 
								<table width="100%" id="ms" style="border:1px;border-collapse:collapse;">
									<thead>
									
										<th align="center">项目名称</th>
										<th align="center">任务目标</th>
										<%--<th align="center">任务类别</th>
										--%><th align="center">工作描述</th>
										<th align="center">完成度</th>
										<th align="center">进度条</th>
										<th align="center">操作</th>
									</thead>
									<tbody>
									<tr class="checkgoods" id="kelong1">
	
										<td align="center"><s:select list="%{#request.beans}" class="sel" name="logcontenmap[1].projectID"
								listKey="csbid" listValue="projectname"  onchange="getTask(this)" headerKey="其他" headerValue="其他"
							theme="simple"></s:select>
											
										</td>
										<td align="center"><select  style="width:130px;" onchange="getBaifen(this)"  name="logcontenmap[1].jobTaskID"
											><option value="其他">其他</option>
										</select>
										</td><%--
										<td align="center"><select name="logcontenmap[1].jobstatus"><option
													value="基本任务">基本任务</option>
												<option value="额外任务">额外任务</option>
										</select>
										</td>
										--%><td align="center"><input type="text" name="logcontenmap[1].remark" size="55" class="rem"/></td>
										<td align="center"><input type="text" id="amount1" name="logcontenmap[1].contactcom" size="3" readonly/></td>
										<td align="center"><div id="slider1" class="sld" style="width:150px;"></div></td>
                                        <td align="center"><img src="<%=basePath%>images/gtk-del.png"  class="removeline" style="cursor:pointer;" /></td>
									</tr>
									<tr  id="kelong" style="display:none;">
									   
										<td align="center"><s:select list="%{#request.beans}"  onchange="getTask(this)" class="sel" name="projectID"
								listKey="csbid" listValue="projectname" headerKey="其他" headerValue="其他"
							theme="simple"></s:select>
										</td>
										<td align="center"><select  name="jobTaskID" style="width:130px;" onchange="getBaifen(this)" ><option value="其他">其他</option>
										</select>
										</td>
										<%--<td align="center"><select name="jobstatus"><option
													value="基本任务">基本任务</option>
												<option value="额外任务">额外任务</option>
										</select>
										</td>
										--%><td align="center"><input type="text" name="remark" size="55" class="rem"/></td>
										<td align="center"><input type="text" id="amount" name="contactcom" size="3" readonly/></td>
										<td align="center"><div id="slider" class="sld" style="width:150px;"></div></td>
                                        <td align="center"><img src="<%=basePath%>images/gtk-del.png"  class="removeline" style="cursor:pointer;"/></td>
									    
									</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<%--<tr>
			<td width="120"  align="right">
				<span class="xx">*</span>工作内容：
				<a href="#" id="" onclick="add()" ><img src="<%=basePath%>images/u15.png"width="16" height="16"   border="0"/></a>
				&nbsp;
			</td>
			<td colspan="5" align="left">
				
				<div id="slider" style="width:200px;"></div>
				<input  style="display: none" name="logbook.jobContent" id="jobContent" ></input> 
				<table id="tab1" width="780" border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
					<tr id="sa1" style="display: none" class="td_bg01 saveAjax model2">
						<td>&nbsp;
							<a href="#" id=""  ><img src="<%=basePath%>images/u16.png"  style="margin-top: 5px"  width="16" height="16"   border="0"/></a>
							项目名称:<select class="sel" name="jpbname" style="width:130px"><option value="其他">其他</option></select>
						    任务目标:<select class="sel" name="jpbname" style="width:130px"><option value="其他">其他</option></select>
							计划类别:<select name="jobstatus"><option value="基本任务">基本任务</option><option value="额外任务">额外任务</option></select>
							</br>
							
							
							描述:<input type="text" name="remark" size="45" class="rem"/>
							完成度:<input type="text" name="contactcom" size="5"/>
							
							</td>
					</tr>
						
				</table>
			</td>
		</tr> --%>
	 </table>
	  <table  width="900" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 1px; margin-bottom: 1px;">
					<tr>
						<td height="30" align="center">
							<input type="button" class="input-button JQuerySubmitgd" name="button3"
								style="cursor:pointer;width:80px;" value="保存" />
							<input type="button" class="input-button JQueryClose" name="button2"
								style="cursor:pointer;width:80px;" value="返回" />
						</td>
					</tr>
				</table>
	  
	  </div>
	  
	  <table style="width: 100%;">
									<tr><td>
									<font color="red">注意:</font><br/>
										1：每日在下午6点至次日8：30前填写本日工作日志<br/>
2：每日志必须真实，工作内容必须要工作计划，填写当日个工作进度<br/>
3：造假扣每项目5分，主管10分  经理15分当日无业绩计算<br/>
4：超时24小时填写，超时一日扣1分<br/>
5：不填写，工资无法计算，按无业绩和未上班计算<br/>
6：填写日志，无工作业绩，按无业绩计算<br/>
7：部门主管按日必须审核，每日审核必在本日上午11点至下午3点，下午6点至次日8：30间审核，延时审核主管扣2分，管理5分<br/>
8：人事办公室每日工作日清，主要人事工作，办公室工作汇总 各部门日清未清按日清，财务部要做账目资金日清，不清每日扣1分，主管扣2分  经理5分 <br/>
									</td></tr>
								</table>
	  </div>
	</form>
</div>


   <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
   <script type="text/javascript" src="<%=basePath%>js/ea/person/cstaff_logbook.js"></script>
</body>
</html>
