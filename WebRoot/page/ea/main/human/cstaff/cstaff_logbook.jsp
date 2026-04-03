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
<title>工作日志</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript">
	   var select =1;
	   var token = 0;
	   var logBookID = '';
	   var today=new Date();
	   var month = today.getMonth()+1;
	   var toda=today.getYear()+"-"+month+"-"+today.getDate();
	   var pbasePath='<%=basePath%>';
	   var ppageNumber='${pageNumber}';
	   var logbookstaffID='${logbook.staffID}';
	   var psdate='${sdate}';
	   var pedate='${edate}';
	   var staffName='${staffName}';
	   var scoreSort = '${scoreSort}';
	   var status = '${status}';
	   var notoken = 0;
	   var basePath = "<%=basePath%>";
</script>

<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/cstaff_logbook.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>


<div id="main_main" class="main_main">
<form action="" name="logFrom" id="logFrom" method="post"><s:token></s:token>
   <input type="submit" name="submit" style="display:none"/></form>
 <form  name="logbookForm" enctype="multipart/form-data" id="logbookForm" method="post"><s:token></s:token>
   <input type="submit" name="submit" style="display:none"/>
  <table   class="logbook">
  <thead>
	 	    <tr>
		 	    <th width="20" align="center">选择</th>
		 	    <th width="100" align="center" >日期</th>
		 	    <th width="100" align="center" >录入时间</th>
	            <th width="80" align="center" >起时间</th>
	            <th width="80" align="center" >止时间</th>
	            <th width="70" align="center" >工作地点</th>
	            <th width="150" align="center" >完成工作内容</th>
	            <th width="115" align="center" >得分类别</th>
	            <th width="40" align="center" >得分</th>
	            <th width="120" align="center" >附件</th>
	            <th width="70" align="center" >主管签字</th>
	            <th width="70" align="center" >人事主管管理</th>
	            <th width="70" align="center" >公司经理</th>  
	            <th width="60" align="center" >状态</th>
            </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
            <td >
             <input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/>
             </td>
             <td class="td_bg01"><input  name="todaydate" id="todaydate" onfocus="date(this);" size="10"/></td>
             <td class="td_bg01">
     		 <span id="inputDate">${fn:substring(inputDate, 0, 19)}系统自动生成</span>
     			<input name="inputDate" class="model1" id="inputDate"   readonly="readonly"  size="10"/>
    		</td>
            <td class="td_bg01"><input   name="startdate" id="startdate" size="5"/></td>
            <td class="td_bg01"><input   name="enddate" id="enddate" size="5"/></td>
            <td class="td_bg01"><input   name="jobLocation" id="jobLocation" class="jobLocation"/></td>
            <td class="td_bg01"><input   name="jobContent" id="jobContent" maxlength="120" size="50"/></td>
            <td class="td_bg01">
            <s:select list="scoreSortlist" listKey="codeID" listValue="codeValue" name="scoreSort" id="xxx" theme="simple"></s:select></td>
            <td class="td_bg01"><input  name="bisect" id="bisect" size="3" class="baseScore" onkeyup="this.value=this.value.replace(/[^\d.]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d.]/g,'') "/>
					            <input type="hidden" name="staffID" value="${logbook.staffID}" id="staffID" />
					            <input type="hidden" name="logBookKey" id="logBookKey"/>
					            <input type="hidden" name="logBookID" id="logBookID" />
			</td> 
			<td class="td_bg01">
              <input name="photo" size="10" id="photo" type="file" contentEditable="false"/> 
			</td>
            <td class="td_bg01"><input   name="supervisor" id="supervisor" size="10"/></td>
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
				<input class="model1" value="${todaydate}" name="todaydate" id="todaydate"  onfocus="date(this);" size="10"/></td>
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
                <span id="startdate">${jobLocation}</span>
				<input class="model1 jobLocation" value="${jobLocation}" name="jobLocation" /></td>
			<td class="td_bg01">
               <span id="jobContent">${jobContent}</span>
				<input class="model1" value="${jobContent}"  name="jobContent" maxlength="120" size="50"/></td>
            <td class="td_bg01">
             <span id="scoreSort"></span>
               <s:select list="scoreSortlist" class="model1" listKey="codeID" listValue="codeValue" name="scoreSort"  id="scoreSort" theme="simple" disabled="true"></s:select></td>
            <td class="td_bg01">
              <span id="bisect">${bisect}</span>
              <input class="model1"  name="bisect" value="${bisect}" id="bisect" size="3" onkeyup="this.value=this.value.replace(/[^\d.]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d.]/g,'') "/>
					            <input type="hidden" name="status" value="${status}"/>
					             <input type="hidden" name="staffID" value="${staffID}"/>
					            <input type="hidden" name="logBookKey"  value="${logBookKey}"/>
					            <input type="hidden" name="logBookID" value="${logBookID}"/>
					            <input type="hidden" id="scoreSorts" value="${scoreSort}"/>
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
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/logbook/ea_getListLogBook.jspa?logbook.staffID=${logbook.staffID}&sdate=${sdate}&edate=${edate}&pageNumber=${pageNumber}&scoreSort=${scoreSort}&status=${status}"></c:param>
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
                <input type="hidden" value="${logbook.staffID}" name="logbook.staffID" id="staffID"/>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
                 </div>
            </form>
</div>
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