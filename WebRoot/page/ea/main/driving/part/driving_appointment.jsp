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
<title>预约记录管理</title>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var drivingappointmentrecordid = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var staffID='';
   var notoken = 0;
   var drivingprincipalid='${dtDrivingAppointmentRecord.drivingprincipalid}';
   var docstatus='${dtDrivingAppointmentRecord.docstatus}'	
</script>
<script src="<%=basePath%>js/ea/driving/part/driving_appointment.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form  name="DtDrivingAppointmentRecordForm" id="DtDrivingAppointmentRecordForm" method="post"><s:token></s:token>
<input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table   class="DtDrivingAppointmentRecordTable">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
		            <th width="75" align="center" >预约日期</th>
		            <th width="75" align="center" >起时间</th>
		            <th width="75" align="center" >止时间</th>
		            <th width="75" align="center" >时长</th>
		            <th width="75" align="center" >预约教练</th>
		            <th width="100" align="center" >教练编号</th>
		            <th width="100" align="center" >教练联系方式</th>
		             <th width="100" align="center" >学习项目</th>
		            <th width="75" align="center" >车牌号</th>
		            <th width="200" align="center" >培训地点</th>
		            <th width="75" align="center" >是否接送</th>
		            <th width="75" align="center" >接时间</th>
		            <th width="75" align="center" >送时间</th>
		            <th width="200" align="center" >接送地点</th>
		            <th width="75" align="center" >接送责任人</th>
		            <th width="100" align="center" >接送责任人电话</th>
		            <th width="100" align="center" >接送车牌号</th>
		            <th width="75" align="center" >接送车型号</th>
		            <th width="75" align="center" >总学时</th>
		            <th width="75" align="center" >已预约学时</th>
		            <th width="75" align="center" >未预约学时</th>
		            <%--<th width="75" align="center" >短信通知</th>
		            --%><th width="75" align="center" >预约状态</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
				<input type="hidden" id="start"/>
				<input type="hidden" id="end"/>
				
				<input type="hidden" id="startHour"/>
				<input type="hidden" id="endHour"/>
	           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
		            <td><input type="radio" name="a" class="JQuerypersonvalue" value="${drivingAppointmentRecordID}"/></td>
		            
		            <td class="td_bg01"><input name="appointmentdate" id="appointmentdate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" size="10" /></td>
		            
		            <td class="td_bg01"><input name="startdate" id="startdate"  class=""  onfocus="WdatePicker({dateFmt:'HH:00', maxDate:'#F{$dp.$D(\'endHour\',{H:-1})||\'23:59\'}',minDate:'#F{$dp.$D(\'endHour\',{H:-4})||\'00:00\'}'})" size="10" /></td>
		            
		            <td class="td_bg01"><input name="enddate" id="enddate"  class=""	 onfocus="WdatePicker({dateFmt:'HH:00',minDate:'#F{$dp.$D(\'startHour\',{H:1})||\'00:00\'}',maxDate:'#F{$dp.$D(\'startHour\',{H:4})||\'23:59\'}'})" size="10" /></td>
		            
		            <td class="td_bg01"><input name="timelength" id="timelength"   size="10" readonly="readonly" /></td>
		            
		            <td class="td_bg01"><input name="coach" id="coach"   size="10" /></td>
		            
		            <td class="td_bg01"><input name="coachcode" id="coachcode"   size="10" />
		            	<a href="#"  id="xuanze">选择</a>	
		            </td>
		            <td class="td_bg01"><input name="coachphone" id="coachphone"   size="10" /></td>
		            
		            <td class="td_bg01"><s:select list="ddsrsyllabusList" listKey="id" id="xxx" listValue="program" name="ddsrsyllabusid"  theme="simple"></s:select></td>
		            
		            <td class="td_bg01"><input name="carcode" id="carcode"   size="10" /></td>
		            
		            <td class="td_bg01"><input name="trainingaddress" id="trainingaddress"   size="10" />
		            </td>
		            
		            <td class="td_bg01">
		            <s:select list="#{'00':'否','01':'是'}" listKey="key" id="xxx" listValue="value" name="whether"  theme="simple"></s:select>
		            </td>
		            
		            <td class="td_bg01"><input name="startshuttledate" id="startshuttledate"  onfocus="WdatePicker({dateFmt:'HH:mm', maxDate:'#F{$dp.$D(\'endHour\'||\'23:59\')}'})" size="10" /></td>
		            
		            <td class="td_bg01"><input name="endshuttledate" id="endshuttledate"  onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'startHour\')||\'00:00\'}'})" size="10" /></td>
		            
		            <td class="td_bg01"><input name="shuttleaddress" id="shuttleaddress"   size="10" /></td>
		            
		            <td class="td_bg01"><input name="shuttlestaff" id="shuttlestaff"   size="10" /><a  href="#" onclick="getValueForParm()">选择</a>
		            					<input name="shuttlestaffid" id="shuttlestaffid"  type="hidden"  />
		            </td>
		            
		            <td class="td_bg01"><input name="shuttlestaffphone" id="shuttlestaffphone"   size="10" /></td>
		            
		            <td class="td_bg01"><input name="shuttlecarnumber" id="shuttlecarnumber"  />
		            <a   id="newG" href="#" >选择</a>
		            </td>
		            
		            <td class="td_bg01"><input name="shuttlecarxinhao" id="shuttlecarxinhao"  /></td>
		            
		            <td class="td_bg01"><input name="sumtimelength" id="sumtimelength"  readonly="readonly" /></td>
		           
		            <td class="td_bg01"><input name="haveschooltime" id="haveschooltime" readonly="readonly" /></td>
		            
		            <td class="td_bg01"><input name="noschooltime" id="noschooltime"  readonly="readonly" /></td><%--
		           
		            <td class="td_bg01"><input name="issendmessage" id="issendmessage" /></td>
		           
		            --%><td class="td_bg01">
		            <s:select list="#{'00':'预约'}" listKey="key" id="xxx" listValue="value" name="isappointmentstatus"  theme="simple"></s:select>
							            <input type="hidden" name="drivingappointmentrecordkey" id="drivingappointmentrecordkey"/>
							            <input type="hidden" name="drivingappointmentrecordid" id="drivingappointmentrecordid" />
							            <input type="hidden" name="staffid" value="" id="staffid" />
							            <input type="hidden" name="drivingprincipalid" value="${dtDrivingAppointmentRecord.drivingprincipalid}" id="drivingprincipalid" />
							            <input type="hidden" name="docstatus" value="${dtDrivingAppointmentRecord.docstatus}" id="docstatus" />
					</td> 
	          </tr>
          <s:iterator value="pageForm.list">
	          <tr class="td_bg01 saveAjax trclass" id="${drivingappointmentrecordid}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingappointmentrecordid}"/>
	            	</td>
		            <td class="td_bg01">
		                <span id="appointmentdate" class="datas">${appointmentdate}</span>
						<input class="model1" value="${appointmentdate}" name="appointmentdate"  onfocus="date(this);"  size="10"/></td>
					<td class="td_bg01">
					<span id="startdate">${startdate}</span>
	            		 <input class="model1" id="startdate"  name="startdate" value="${startdate}" onfocus="WdatePicker({dateFmt:'HH:00', maxDate:'#F{$dp.$D(\'endHour\',{H:-1})||\'23:59\'}',minDate:'#F{$dp.$D(\'endHour\',{H:-4})||\'00:00\'}'})" size="40"/></td>
	            	<td class="td_bg01">
					<span id="enddate">${enddate}</span>
	            		 <input class="model1"  name="enddate" id="enddate" value="${enddate}" onfocus="WdatePicker({dateFmt:'HH:00',minDate:'#F{$dp.$D(\'startHour\',{H:1})||\'00:00\'}',maxDate:'#F{$dp.$D(\'startHour\',{H:4})||\'23:59\'}'})" size="40"/></td>
	            	<td class="td_bg01">
					<span id="timelength">${timelength}</span>
	            		 <input class="model1"  name="timelength" value="${timelength}" size="40" readonly="readonly" /></td>
	            	
	            	<td class="td_bg01">
					<span id="coach">${coach}</span>
	            		 <input class="model1" id="coach" name="coach" value="${coach}" size="40"/></td>
	            	<td class="td_bg01">
					<span id="coachcode">${coachcode}</span>
	            		 <input class="model1" id="coachcode" name="coachcode" value="${coachcode}" size="40"/>
	            		 <a class="model1" href="#"  id="xuanze">选择</a>	
	            		 </td>
	            	<td class="td_bg01">
					<span id="coachphone">${coachphone}</span>
	            		 <input class="model1" id="coachphone" name="coachphone" value="${coachphone}" size="40"/></td>	
	            
	            		 
	            	<td class="td_bg01">
					<span id="ddsrsyllabusid" style="display: none;" >${ddsrsyllabusid}</span>
	                	 <span id="ddsrsyllabusprogram" >${ddsrsyllabusprogram}</span>
	            		 <s:select list="ddsrsyllabusList" listKey="id" id="xxx" listValue="program" name="ddsrsyllabusid"  theme="simple" cssClass="model1"></s:select></td>	
	            	  
	            	<td class="td_bg01">
					<span id="carcode">${carcode}</span>
	            		 <input class="model1"  name="carcode" value="${carcode}" id="carcode" size="40"/></td>	 	 
					<td class="td_bg01">
					<span id="trainingaddress">${trainingaddress}</span>
	            		 <input class="model1"  name="trainingaddress" value="${trainingaddress}" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="whether">${whether=='01'?'是':'否'}</span>
	            		 <s:select list="#{'00':'否','01':'是'}" listKey="key" id="xxx" listValue="value" name="whether"  theme="simple" cssClass="model1"></s:select>
	            		 </td>
	            		 <td class="td_bg01">
					<span id="startshuttledate">${startshuttledate}</span>
	            		 <input class="model1" id="startshuttledate" name="startshuttledate" value="${startshuttledate}"  onfocus="WdatePicker({dateFmt:'HH:mm', maxDate:'#F{$dp.$D(\'endHour\'||\'23:59\')}'})" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="endshuttledate">${endshuttledate}</span>
	            		 <input class="model1" id="endshuttledate" name="endshuttledate" value="${endshuttledate}" onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'startHour\')||\'00:00\'}'})" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="shuttleaddress">${shuttleaddress}</span>
	            		 <input class="model1"  name="shuttleaddress" value="${shuttleaddress}" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="shuttlestaff">${shuttlestaff}</span>
	            		 <input class="model1" id="shuttlestaff" name="shuttlestaff" value="${shuttlestaff}" size="40"/>
	            		 <input name="shuttlestaffid" id="shuttlestaffid" value="${shuttlestaffid}" type="hidden"  />
	            		 <a class="model1" href="#" onclick="getValueForParm()">选择</a>
	            		 </td>
	            	<td class="td_bg01">
					<span id="shuttlestaffphone">${shuttlestaffphone}</span>
	            		 <input class="model1" id="shuttlestaffphone"  name="shuttlestaffphone" value="${shuttlestaffphone}" size="40"/></td>
	            	<td class="td_bg01">
					<span id="shuttlecarnumber">${shuttlecarnumber}</span>
	            		 <input class="model1" id="shuttlecarnumber" name="shuttlecarnumber" value="${shuttlecarnumber}" size="40"/>
	            		 <a class="model1"  id="newG" href="#" >选择</a>
	            		 </td>	
		            <td class="td_bg01">
		                <span id="shuttlecarxinhao" class="datas">${shuttlecarxinhao}</span>
						<input class="model1" id="shuttlecarxinhao" value="${shuttlecarxinhao}" name="shuttlecarxinhao"  size="10" /></td>
	           		<td class="td_bg01">
		                <span id="sumtimelength" class="datas">${sumtimelength}</span>
						<input name="sumtimelength" id="sumtimelength" value="${sumtimelength}" readonly="readonly"  class="model1"/></td> 
						
					<td class="td_bg01">
		                <span id="haveschooltime" class="datas">${haveschooltime}</span>
						<input name="haveschooltime" id="haveschooltime" value="${haveschooltime}" readonly="readonly"   class="model1"/></td>
						
					<td class="td_bg01">
		                <span id="noschooltime" class="datas">${noschooltime}</span>
						<input name="noschooltime" id="noschooltime" value="${noschooltime}" readonly="readonly"  class="model1"/></td>		    
	                
	                <%--<td class="td_bg01">
		                <span id="issendmessage" class="datas">${issendmessage}</span>
						<input name="issendmessage" id="issendmessage" value="${issendmessage}" readonly="readonly"   class="model1"/></td>
	                --%><td class="td_bg01">
	                		<span id="isappointmentstatusHidden" style="display: none;" >${isappointmentstatus}</span>
	                	 <span id="isappointmentstatus" class="datas">${isappointmentstatus=='00'?'预约':'成功'}</span>	
	                	 <s:select list="#{'00':'预约','01':'成功'}" listKey="key" id="xxx" listValue="value" name="isappointmentstatus"  theme="simple" cssClass="model1"></s:select>
						            <input type="hidden" name="drivingappointmentrecordkey" value="${drivingappointmentrecordkey}"/>
						            <input type="hidden" name="drivingappointmentrecordid" value="${drivingappointmentrecordid}"/>
						            <input type="hidden" name="staffid" value=""/>
						            <input type="hidden" name="drivingprincipalid" value="${drivingprincipalid}"  />
						            <input type="hidden" name="docstatus" value="${docstatus}" id="docstatus" />
					</td>
	          </tr>
          </s:iterator>
    	</tbody>
  </table>
</div>
</form>

<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/appointment/ea_getListDtDrivingAppointmentRecord.jspa?pageNumber=${pageNumber}&dtDrivingAppointmentRecord.drivingprincipalid=${dtDrivingAppointmentRecord.drivingprincipalid}&dtDrivingAppointmentRecord.docstatus=${dtDrivingAppointmentRecord.docstatus}">
				</c:param>
			</c:import>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

<%-- 选择车辆信息  --%>
<div id="bankJqm" class="jqmWindow"
			style="width: 90%; height: 255px; absolute; display: none; left: 5%; top: 3%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="230px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" " />
			</div>
</div>

<!-- 从当前部门的员工中选择责任人 -->
<div id="jqmWindow2" class="jqmWindow"
	style="width: 90%; height: 255px; absolute; display: none; left: 5%; top: 3%; background: #eff;overflow-x: hidden; overflow-y: auto;"">
	<div align="center">
		<iframe name="ifr" id="ifr" width="100%" height="230px"
		frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
		<input type="button" class="input-button" id="isSubmit" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack" value=" 关闭 "
			style="cursor: hand" />
	</div>
 </div>
 
 <%-- 选择车辆信息  --%>
<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow " style="width: 90%; left: 5%; top: 3%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%;height: 230px;">
					<div class="contentbannb">
						<div class="drag">
							选择车辆
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td align="right">
								车型：
							</td>
							<td >
								<input name="carType" class="input" id="carType" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="chaxun"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="qdcar"
									name="button5" value="确定" />
								<input type="button" class="btn02 JQueryreturngoods" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="80">
								<a id="wpsy" title="0" style="cursor: pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0" style="cursor: pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02"
									style="display: none; height: 100%; width: 100%; overflow: hidden;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		
</body>
</html>
