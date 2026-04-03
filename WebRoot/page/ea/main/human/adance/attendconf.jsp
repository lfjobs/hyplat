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
<title>考勤预设项目</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/ea/human/adance/attendconf.js"></script>
<style type="text/css">
	.select{
		width:80px;
	}
	.input{
		width:60px;
	}
</style>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var confID = "";
	var search = '${search}';
	
</script>
</head>
<body>
	<form name="attendconfForm" id="attendconfForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="attendconf">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="50" align="center">序号</th>
						<th width="165" align="center">考勤项目</th>
						<th width="150" align="center">发生时间</th>
						<th width="150" align="center">最小单位</th>
						<th width="150" align="center">最大单位</th>
						<th width="180" align="center">奖惩</th>
						<th width="300" align="center">备注</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<s:iterator value="pageForm.list" status="number">
						<tr id="${attendConfId}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"
								value="${attendConfId}" /></td>
							<td>${number.index+1}</td>
							<td><span id="confname">${confname}</span></td>
							<td>
								<s:if test="happents == '00'">不限</s:if>
								${playtime} - 
								<s:if test="stopstus == '01'">次日</s:if>
								${stoptime }</td>
							<td>${minnum }
								<s:if test="minstus == '00'">不限</s:if>
								<s:if test="minstus == '01'"> - 天</s:if>
								<s:if test="minstus == '02'"> - 小时</s:if>
								<s:if test="minstus == '03'"> - 分钟</s:if>
								</td>
							<td>${maxnum }
								<s:if test="maxstus == '00'">不限</s:if>
								<s:if test="maxstus == '01'"> - 天</s:if>
								<s:if test="maxstus == '02'"> - 小时</s:if>
								<s:if test="maxstus == '03'"> - 分钟</s:if>
								</td>
							<td>${stusnum }
								<s:if test="stus == '00'">仅作统计</s:if>
								<s:if test="stus == '01'"> /次&nbsp;- 按次奖励</s:if>
								<s:if test="stus == '02'"> /次&nbsp;- 按次扣除</s:if>
								<s:if test="stus == '03'">% X 时间 &nbsp;- 按时间奖励</s:if>
								<s:if test="stus == '04'">% X 时间 &nbsp;- 按时间扣除</s:if>
								</td>
							<td><span id="remarks">${remarks}</span>
								<span style="display: none" id="attendConfKey" >${attendConfKey }</span>
								<span style="display: none" id="attendConfId" >${attendConfId }</span>
								<span style="display: none" id="companyId" >${companyId }</span>
								<span style="display: none" id="happents" >${happents }</span>
								<span style="display: none" id="playtime" >${playtime }</span>
								<span style="display: none" id="stopstus" >${stopstus }</span>
								<span style="display: none" id="stoptime" >${stoptime }</span>
								<span style="display: none" id="minstus" >${minstus }</span>
								<span style="display: none" id="minnum" >${minnum }</span>
								<span style="display: none" id="maxstus" >${maxstus }</span>
								<span style="display: none" id="maxnum" >${maxnum }</span>
								<span style="display: none" id="stus" >${stus }</span>
								<span style="display: none" id="stusnum" >${stusnum }</span>
								<span style="display: none" id="ctime" >${ctime }</span>
								<span style="display: none" id="cname" >${cname }</span>
								<span style="display: none" id="groupCompanySn" >${groupCompanySn }</span>
								<span style="display: none" id="confstus" >${confstus }</span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/attendconf/ea_getAttendConf.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
	<!--添加-->
	<div class="jqmWindow" style="width: 500px; right: 30%; top: 15%"
			id="jqModelAdd">
			<form name="jqModelAddForm" id="jqModelAddForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					考勤项目
					<div class="close">
					</div>
				</div>
				<table width="490px" id="adaTable">
					<tr>
						<td align="right" width="100px" style="height: 30px;">
							<font color="red">*</font>项目名称：
						</td>
						<td colspan="2">
							<input name="confname" id="confname" class="put3 ckTextLength" maxlength="250"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							发生时间：
						</td>
						<td style="width:115px;height: 30px;">
							<select name="happents" id="happents" class="select">
								<option value="00">--不限--</option>
								<option value="01">时间</option>
							</select>
						</td>
						<td align="left">
							<div id="happents" style="display: none;">							
							当日<input name="playtime" id="playtime" style="width:40px;" onfocus="WdatePicker({dateFmt:'HH:mm'})"/> -- 
							<select name="stopstus" id="stopstus"  style="width:50px;">
								<option value="00">当日</option>
								<option value="01">次日</option>
							</select>
							<input name="stoptime" id="stoptime" style="width:40px;" onfocus="WdatePicker({dateFmt:'HH:mm'})"/>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" style="height: 30px;">
							最小单位：
						</td>
						<td>
							<select name="minstus" id="minstus" class="select">
								<option value="00">--不限--</option>
								<option value="01">天</option>
								<option value="02">小时</option>
								<option value="03">分钟</option>
							</select>
						</td>
						<td id="minstus" align="left">
							<input name="minnum" id="minnum" class="input isNaN" style="display: none"/>
							<span id="minnum"></span>
						</td>
					</tr>
					<tr>
						<td align="right" style="height: 30px;">
							最大单位：
						</td>
						<td>
							<select name="maxstus" id="maxstus" class="select">
								<option value="00">--不限--</option>
								<option value="01">天</option>
								<option value="02">小时</option>
								<option value="03">分钟</option>
							</select>
						</td>
						<td >
							<input name="maxnum" id="maxnum" class="input isNaN" style="display: none"/>
							<span id="maxnum"></span>
						</td>
					</tr>
					<tr>
						<td align="right" style="height: 30px;">
							奖惩：
						</td>
						<td>
							<select name="stus" id="stus" class="select">
								<option value="00">--统计--</option>
								<option value="01">按次奖励</option>
								<option value="02">按次扣除</option>
								<option value="03">时间奖励</option>
								<option value="04">时间扣除</option>
							</select>
						</td>
						<td align="left">
							<span id="stusnum0"></span>
							<input name="stusnum" id="stusnum" class="input isNaN" style="display: none"/>
							<span id="stusnum"></span>
						</td>
					</tr>
					<tr>
						<td align="right" style="height: 30px;">
							备注：
						</td>
						<td colspan="2">
							<input name="remarks" id="remarks" style="width:300px;" class="ckTextLength" maxlength="250"/>
							<input type="hidden" name="attendConfId" id="attendConfId"/>
							<input type="hidden" name="attendConfKey" id="attendConfKey"/>
							<input type="hidden" name="companyId" id="companyId"/>
							<input type="hidden" name="ctime" id="ctime"/>
							<input type="hidden" name="cname" id="cname"/>
							<input type="hidden" name="groupCompanySn" id="groupCompanySn"/>
							<input type="hidden" name="confstus" id="confstus"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="addbut"
						value="  保存  " />
				</div>
			</form>
		</div>
</body>
</html>
