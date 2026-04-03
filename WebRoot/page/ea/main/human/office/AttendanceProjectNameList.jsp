<%@ page language="java" pageEncoding="UTF-8" %>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; 
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考勤项目设置</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/human/adance/attendanceSet.js"></script>
<script type="text/javascript">
   var token = 0;
  // var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	//var notoken = 0;
	//var adanceid = "";
	var search = '${search}';
	
</script>
</head>
<body >
<form name="adanceForm" id="adanceForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="adance">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="80" align="center">序号</th>
						<th width="150" align="center">考勤项目</th>
						<th width="170" align="center">发生时间</th>
						<th width="170" align="center">最小单位</th>
						<th width="170" align="center">最大单位</th>
						<th width="350" align="center">备注</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					  <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${attendanceID}">
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${attendanceID}"/>
                            </td>
                            <td>
                                <span id="number">  <%=number%></span>
                            </td>
                             <td>
                                <span id="projectname">${projectname}</span>
                            </td>
                             <td>
                                <span id="occurrenceTime">${occurrenceTime}<%-- ${fn:substring(occurrenceTime,0,3)} 当日:${fn:substring(occurrenceTime,6,-1)} --%></span>
                            </td>
                            <td>
                                <span id="resolution">${resolution}</span>
                            </td>
                            <td>
                                <span id="maximumUnit">${maximumUnit}</span>
                            </td>
                            <td>
                                <span id="remark">${remark}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/attendanceset/ea_getAttendanceList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
	
	<!--添加考勤项目-->
	<div class="jqmWindow" style="width: 600px;heigh:288px; right: 25%; top: 20%"
			id="jqModelAdd">
			<form name="jqModelAddForm" id="jqModelAddForm" method="post">
				
				<div class="drag">
					考勤项目设置
					<div class="close">
					</div>
				</div>
				<table width=" 598px" height="288px" id="adaSearchTable" style="line-height:15px">
					<tr>
						<td align="right" width=" 70px">
							考勤项目：
						</td>
						<td>
						<input type="submit" name="submit" style="display: none" />
							<input name="attendance.projectname" id="projectname" type="text" maxlength="50" style="width:235px" class="put3 ckTextLength"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							发生时间：
						</td>
						<td>
							<input type="radio" id="keytext" name="attendance.occurrenceTime" value="不限"/><label for="keytext">不限</label>
							 <input type="radio" id="nametime" name="attendance.occurrenceTime" value="按时间"/><label for="nametime">按时间</label> &nbsp;
							当日: <input type="text" class="happendtime" name="attendance.occurrenceTime" style="width:100px"onclick="WdatePicker({dateFmt:'HH:mm'});"/> ——————  <select name="attendance.occurrenceTime" id="select1">
							<option value="当日">当日</option value="次日"><option>次日</option><option value="不限">不限</option>
							</select>&nbsp;&nbsp; <input type="text" class="happendtime" name ="attendance.occurrenceTime" style="width:100px" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							最小单位：
						</td>
						<td>
							<input type="radio" id="keylittle" name="attendance.resolution" value="不限"/><label for="keylittle">不限</label> 
							<input type="radio" id="keynum" name="attendance.resolution" value="按次"/><label for="keynum">按次</label> 
							<input type="radio" id="keytime" name="attendance.resolution" value ="按时间"/><label for="keytime">按时间</label> 
							<input type="text" style="width:100px" name = "attendance.resolution" class="littleTimer"></input>天
							<input type="text" style="width:100px" class="littleTimer" name="attendance.resolution"  onclick="WdatePicker({dateFmt:'HH'});"></input>小时
							<input type="text" style="width:100px" name="attendance.resolution" class="littleTimer" onclick="WdatePicker({dateFmt:'mm'});"></input>分钟
						</td>
					</tr>
					<tr>
						<td align="right">
							最大单位：
						</td>
						<td>
							<input type="radio" id="keybiggest" name="attendance.maximumUnit" value="不限"/><label for="keybiggest">不限</label> 
							<input type="radio" id="biggestnum" name="attendance.maximumUnit" value="按次"/><label for="biggestnum">按次</label> 
							<input type="radio" id="biggesttime" name="attendance.maximumUnit" value="按时间"/><label for="biggesttime">按时间</label> 
							<input type="text" style="width:100px" class="biggestutil" name = "attendance.maximumUnit"></input>天
							<input type="text" class="biggestutil" style="width:100px" name = "attendance.maximumUnit" onclick="WdatePicker({dateFmt:'HH'});"></input>小时
							<input type="text" class="biggestutil" style="width:100px" name = "attendance.maximumUnit" onclick="WdatePicker({dateFmt:'mm'});"></input>分钟
						</td>
					</tr>
					<tr>
						<td align="right">
							奖惩：
						</td>
						<td>
							<select>
								<option value="请选择">请选择</option>
								<option value ="仅做统计">仅做统计</option>
								<option value="奖励">奖励</option>
								<option value="扣除">扣除</option>
							</select>
							<input type="radio" id="forlittle" name="attendance.rewardAndPunishment" value="按次"/><label for="forlittle">按次</label> 
							<input type="text" style="width: 40px;" name="attendance.rewardAndPunishment"/> 
							<input type="radio" id="fortime" name="attendance.rewardAndPunishment" value="按时间比例"/><label for="fortime">按时间比例 &nbsp;X&nbsp;</label> 
							<input type="text" style="width: 50px;" name="attendance.rewardAndPunishment"/> %
						</td>
					</tr>
					<tr>
						<td align="right">
							 备注：
						</td>
						<td>
							<input type="text" id="remark" name="attendance.remark" style="width:480px"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" value="保存"  id="saveAttendanceInfo" />
					<input type="button"  class="input-button JQueryreturn"  value="取消"  />
				</div>
			</form>
		</div>
</body>
</html>
