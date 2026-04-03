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
<title>社会人力信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/ea/human/adance/adance.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var adanceid = "";
	var inputError= '${inputError}';
	var search = '${search}';
	
</script>
</head>
<body>
	<form name="adanceForm" id="adanceForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="adance">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="80" align="center">导入时间</th>
						<th width="165" align="center">所属公司</th>
						<th width="80" align="center">部门</th>
						<th width="60" align="center">人员姓名</th>
						<th width="60" align="center">考勤号码</th>
						<th width="200" align="center">日期时间</th>
						<th width="60" align="center">记录状态</th>
						<th width="50" align="center">机器号</th>
						<th width="80" align="center">人员编号</th>
						<th width="50" align="center">工种代码</th>
						<th width="100" align="center">对比方式</th>
						<th width="80" align="center">卡号</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[0]}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" /></td>
							<td><span id="">${arr[1]}</span></td>
							<td><span id="">${arr[2]}</span></td>
							<td><span id="">${arr[3]}</span></td>
							<td><span id="">${arr[4]}</span></td>
							<td><span id="">${arr[5]}</span></td>
							<td><span id="">${arr[6]}</span></td>
							<td><span id="">${arr[7]}</span></td>
							<td><span id="">${arr[8]}</span></td>
							<td><span id="">${arr[9]}</span></td>
							<td><span id="">${arr[10]}</span></td>
							<td><span id="">${arr[11]}</span></td>
							<td><span id="">${arr[12]}</span></td>
							<td><span id="">${arr[13]}</span></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/adance/ea_getAdanceList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
	<!--导入-->
	<div class="jqmWindow jqmWindowcss4" style="width: 500px; top: 10%"
		id="jqModelDaoRu">
			<form name="daoRuForm" id="daoRuForm" method="post"
				enctype="multipart/form-data">
				<div class="drag">
					考勤记录导入
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTableDaoRu">
					<tr>
						<td colspan="3" align="center">
							<font color="red">*  考勤记录工作时间必须为一天的记录</font>
						</td>
					</tr>
					<tr>
						<td width="100px">
							导入文件：
						</td>
						<td>
							<input type="file" name="excelImport.excelFile" id="DaoRu" />
						</td>
						<td>
							<input type="button" class="input-button JQueryDaoRu"
								style="cursor: pointer; width: 80px;" value="预览" />
							<input type="submit" name="submit" style="display: none" />
						</td>
					</tr>
				</table>
				
			</form>
		</div>
	<!-- 预览 -->	
	<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div style="background: #efg; margin-right: 400px;">
				<a id="DaoRuFan" href="#" style="height: 23px; width: 60px">返回</a>
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="400px"
				frameborder="0"></iframe>
		</div>
	
	<!--搜索窗口 -->
	<div class="jqmWindow" style="width: 300px; right: 45%; top: 30%"
			id="jqModelSerch">
			<form name="jqModelSerchForm" id="jqModelSerchForm" method="post">
				
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="260px" id="adaSearchTable">
					<tr>
						<td align="right" width="100px">
							导入时间：
						</td>
						<td>
						<input type="submit" name="submit" style="display: none" />
							<input name="adance.importdate" id="importdate" onfocus="date(this);" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							人员编号：
						</td>
						<td>
							<input name="adance.staffCode" id="staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							日期时间：
						</td>
						<td>
							<input name="adance.workdate" id="workdate" onfocus="date(this);" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							记录状态：
						</td>
						<td>
							<select name="adance.opposite" id="opposite">
								<option value="">--选择--</option>
								<option value="上班签到">上班签到</option>
								<option value="下班签退">下班签退</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchAda"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
</body>
</html>
