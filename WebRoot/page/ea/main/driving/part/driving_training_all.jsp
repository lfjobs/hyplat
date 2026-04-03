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
<title>培训记录管理</title>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 

<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var drivingtraininginforid = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var staffID='';
   var notoken = 0;
   var drivingprincipalid='${dtDrivingAppointmentRecord.drivingprincipalid}';
   var docstatus='${dtDrivingAppointmentRecord.docstatus}';
   var module_title='${param.module_title}';
   var search='${search}';
</script>
<script src="<%=basePath%>js/ea/driving/part/driving_training_all.js"></script>
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
			 	    <th width="75" align="center" >培训状态</th>
		            <th width="75" align="center" >培训日期</th>
		            <th width="75" align="center" >起时间</th>
		            <th width="75" align="center" >止时间</th>
		            <th width="75" align="center" >时长</th>
		            <th width="75" align="center" >预约教练</th>
		            <th width="75" align="center" >教练编号</th>
		            <th width="75" align="center" >车牌号</th>
		            <th width="75" align="center" >培训地点</th>
		            <th width="75" align="center" >是否接送</th>
		            <th width="75" align="center" >接时间</th>
		            <th width="75" align="center" >送时间</th>
		            <th width="75" align="center" >接送地点</th>
		            <th width="75" align="center" >接送责任人</th>
		            <th width="75" align="center" >接送责任人电话</th>
		            <th width="75" align="center" >接送车牌号</th>
		            <th width="75" align="center" >接送车型号</th>
		            <th width="75" align="center" >总学时</th>
		            <th width="75" align="center" >已预约学时</th>
		            <th width="75" align="center" >未预约学时</th>
		            <th width="75" align="center" >短信通知</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
          <s:iterator value="pageForm.list">
	          <tr class="td_bg01 saveAjax trclass" id="${drivingtraininginforid}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${drivingtraininginforid}"/>
	            	</td>
	            	<td class="td_bg01">
	            	 <span id="istruestatus" class="datas">${istruestatus=='00'?'未培训':'已培训'}</span>	
	                	 <s:select list="#{'00':'未培训','01':'已培训'}" listKey="key" id="xxx" listValue="value" name="istruestatus"  theme="simple" cssClass="model1"></s:select>
		            </td>
		            <td class="td_bg01">
		                <span id="appointmentdate" class="datas">${dtDrivingAppointmentRecord.appointmentdate}</span>
						<input class="model1" value="${dtDrivingAppointmentRecord.appointmentdate}" name="dtDrivingAppointmentRecord.appointmentdate"  disabled="disabled" size="10"/></td>
					<td class="td_bg01">
					<span id="startdate">${dtDrivingAppointmentRecord.startdate}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.startdate" value="${dtDrivingAppointmentRecord.startdate}" disabled="disabled" size="40"/></td>
	            	<td class="td_bg01">
					<span id="enddate">${dtDrivingAppointmentRecord.enddate}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.enddate" value="${dtDrivingAppointmentRecord.enddate}" disabled="disabled" size="40"/></td>
	            	<td class="td_bg01">
					<span id="timelength">${dtDrivingAppointmentRecord.timelength}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.timelength" value="${dtDrivingAppointmentRecord.timelength}" disabled="disabled" size="40"/></td>
	            	
	            	<td class="td_bg01">
					<span id="coach">${dtDrivingAppointmentRecord.coach}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.coach" value="${dtDrivingAppointmentRecord.coach}" disabled="disabled" size="40"/></td>
	            	<td class="td_bg01">
					<span id="coachcode">${dtDrivingAppointmentRecord.coachcode}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.coachcode" value="${dtDrivingAppointmentRecord.coachcode}" disabled="disabled" size="40"/></td>
	            	<td class="td_bg01">
					<span id="carcode">${dtDrivingAppointmentRecord.carcode}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.carcode" value="${dtDrivingAppointmentRecord.carcode}" disabled="disabled" size="40"/></td>	 	 
					<td class="td_bg01">
					<span id="trainingaddress">${dtDrivingAppointmentRecord.trainingaddress}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.trainingaddress" value="${dtDrivingAppointmentRecord.trainingaddress}" disabled="disabled" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="whether">${dtDrivingAppointmentRecord.whether}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.whether" value="${dtDrivingAppointmentRecord.whether}" disabled="disabled" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="startshuttledate">${dtDrivingAppointmentRecord.startshuttledate}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.startshuttledate" value="${dtDrivingAppointmentRecord.startshuttledate}" disabled="disabled" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="endshuttledate">${dtDrivingAppointmentRecord.endshuttledate}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.endshuttledate" value="${dtDrivingAppointmentRecord.endshuttledate}" disabled="disabled" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="shuttleaddress">${dtDrivingAppointmentRecord.shuttleaddress}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.shuttleaddress" value="${dtDrivingAppointmentRecord.shuttleaddress}" disabled="disabled" size="40"/></td>
	            		 <td class="td_bg01">
					<span id="shuttlestaff">${dtDrivingAppointmentRecord.shuttlestaff}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.shuttlestaff" value="${dtDrivingAppointmentRecord.shuttlestaff}" disabled="disabled" size="40"/></td>
	            	<td class="td_bg01">
					<span id="shuttlestaffphone">${dtDrivingAppointmentRecord.shuttlestaffphone}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.shuttlestaffphone" value="${dtDrivingAppointmentRecord.shuttlestaffphone}" disabled="disabled" size="40"/></td>
	            	<td class="td_bg01">
					<span id="shuttlecarnumber">${dtDrivingAppointmentRecord.shuttlecarnumber}</span>
	            		 <input class="model1"  name="dtDrivingAppointmentRecord.shuttlecarnumber" value="${dtDrivingAppointmentRecord.shuttlecarnumber}" disabled="disabled" size="40"/></td>	
		            <td class="td_bg01">
		                <span id="shuttlecarxinhao" class="datas">${dtDrivingAppointmentRecord.shuttlecarxinhao}</span>
						<input class="model1" value="${dtDrivingAppointmentRecord.shuttlecarxinhao}" name="dtDrivingAppointmentRecord.shuttlecarxinhao" disabled="disabled" size="10" /></td>
	           		<td class="td_bg01">
		                <span id="sumtimelength" class="datas">${dtDrivingAppointmentRecord.sumtimelength}</span>
						<input name="dtDrivingAppointmentRecord.sumtimelength" id="sumtimelength" value="${dtDrivingAppointmentRecord.sumtimelength}" disabled="disabled" class="model1"/></td> 
						
					<td class="td_bg01">
		                <span id="haveschooltime" class="datas">${dtDrivingAppointmentRecord.haveschooltime}</span>
						<input name="dtDrivingAppointmentRecord.haveschooltime" id="haveschooltime" value="${dtDrivingAppointmentRecord.haveschooltime}" disabled="disabled" class="model1"/></td>
						
					<td class="td_bg01">
		                <span id="noschooltime" class="datas">${dtDrivingAppointmentRecord.noschooltime}</span>
						<input name="dtDrivingAppointmentRecord.noschooltime" id="noschooltime" value="${dtDrivingAppointmentRecord.noschooltime}" disabled="disabled" class="model1"/></td>		    
	                
	                <td class="td_bg01">
		                <span id="issendmessage" class="datas">${dtDrivingAppointmentRecord.issendmessage}</span>
						<input name="dtDrivingAppointmentRecord.issendmessage" id="issendmessage" value="${dtDrivingAppointmentRecord.issendmessage}" disabled="disabled" class="model1"/>
					            <input type="hidden" name="dtDrivingAppointmentRecord.drivingappointmentrecordkey" value="${dtDrivingAppointmentRecord.drivingappointmentrecordkey}"/>
					            <input type="hidden" name="dtDrivingAppointmentRecord.drivingappointmentrecordid" value="${dtDrivingAppointmentRecord.drivingappointmentrecordid}"/>
					            <input type="hidden" name="staffid" value=""/>
					            <input type="hidden" name="dtDrivingAppointmentRecord.dtDrivingAppointmentRecord.drivingprincipalid" value="${dtDrivingAppointmentRecord.drivingprincipalid}"  />
					            <input type="hidden" name="drivingtraininginforid" value="${drivingtraininginforid}"  />
					            <input type="hidden" name="drivingtraininginforkey" value="${drivingtraininginforkey}"  />
				</td>
	          </tr>
          </s:iterator>
    	</tbody>
  </table>
</div>
</form>

<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/training/ea_getListDtDrivingTrainingInforAll.jspa?pageNumber=${pageNumber}&search=${search}&module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}&companyGroupLogo=${param.companyGroupLogo}">
				</c:param>
			</c:import>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<div class="jqmWindow" style="width: 350px; right: 45%;; top: 10%" 
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post" action="">
				<div class="drag">
					查询培训记录
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							培训日期：
						</td>
						<td width="261">
							<input name="dtDrivingAppointmentRecord.appointmentdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							教练名称：
						</td>
						<td width="261">
							<input name="dtDrivingAppointmentRecord.coach" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							培训状态：
						</td>
						<td width="261">
							<s:select list="#{'':'全部','00':'未培训','01':'已培训'}" listKey="key"  listValue="value" name="dtDrivingAppointmentRecord.isappointmentstatus"  theme="simple" ></s:select>
							<input name="module_title" type="hidden" value="${param.module_title}"/>
                        </td>
              </tr>
                </table>
            <div align="center">
            <input type="submit" name="submit" style="display: none" />
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
</body>
</html>
