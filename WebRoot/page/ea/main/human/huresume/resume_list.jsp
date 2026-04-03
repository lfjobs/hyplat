<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
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
<title>网站人才</title>
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
<script src="<%=basePath%>js/ea/human/huresume/resume_list.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var resumekey = "";
	var inputError= '${inputError}';
	var search = '${search}';
	
</script>
</head>
<body>
	<form name="adanceForm" id="adanceForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="resume">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="80" align="center">姓名</th>
						<th width="120" align="center">联系方式</th>
						<th width="80" align="center">性别</th>
						<th width="60" align="center">出生日期</th>
						<th width="60" align="center">身份证号</th>
						<th width="60" align="center">最高学历</th>
						<th width="200" align="center">民族</th>
						<th width="60" align="center">政治面貌</th>
						<th width="50" align="center">身高</th>
						<th width="80" align="center">体重</th>
						<th width="50" align="center">健康状况</th>
						<th width="100" align="center">籍贯</th>
						<th width="80" align="center">现居住地</th>
						<th width="80" align="center">自我评价</th>
						<th width="80" align="center">应聘职位</th>
						<th width="80" align="center">信息来源</th>
						<th width="80" align="center">创建时间</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<s:iterator value="pageForm.list">
						<tr id="${resumekey }">
							<td><input type="radio" name="a" class="JQuerypersonvalue" value="${resumekey}" /></td>
							<td><span id="cname">${cname}</span></td>
							<td><span id="communicate">${communicate}</span></td>
							<td><span id="sex">
								<c:if test="${sex == 1 }">男</c:if>
								<c:if test="${sex == 0}">女</c:if>
							</span></td>
							<td><span id="birthday">${birthday}</span></td>
							<td><span id="identity">${identity}</span></td>
							<td><span id="degree">
								<c:if test="${degree == 00 }">博士后</c:if>
								<c:if test="${degree == 01 }">博士</c:if>
								<c:if test="${degree == 02 }">硕士</c:if>
								<c:if test="${degree == 03 }">本科</c:if>
								<c:if test="${degree == 04 }">专科</c:if>
								<c:if test="${degree == 05 }">中专</c:if>
								<c:if test="${degree == 06 }">职高</c:if>
								<c:if test="${degree == 07 }">高中</c:if>
								<c:if test="${degree == 08 }">初中</c:if>
								<c:if test="${degree == 09 }">小学</c:if>
								<c:if test="${degree == 10 }">小学以下</c:if>
							</span></td>
							<td><span id="nation">${nation}</span></td>
							<td><span id="politics">${politics}</span></td>
							<td><span id="height">${height}</span></td>
							<td><span id="weight">${weight}</span></td>
							<td><span id="health">${health}</span></td>
							<td><span id="nativeplace">${nativeplace}</span></td>
							<td><span id="residenceplace">${residenceplace}</span></td>
							<td><span id="selfassessment">${selfassessment}</span></td>
							<td><span id="jobobjective">${jobobjective}</span></td>
							<td><span id="source">${source}</span></td>
							<td><span id="cdate">${cdate}</span></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/resume/ea_getList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
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
							姓名：
						</td>
						<td>
							<input name="resume.cname" id="cname" />
						</td>
					</tr>
					<tr>
						<td align="right">
							性别：
						</td>
						<td>
							<input name="resume.sex" id="sex" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="submit" name="submit" style="display: none" />
					<input type="button" class="input-button" id="searchAda"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
			<!--查看窗口 -->
		<div class="jqmWindow" style="width: 500px; right: 45%; top: 10%"
			id="jqModelSee">
			<form name="jqModelSeeForm" id="jqModelSeeForm" method="post">
				
				<div class="drag">
					查看信息
					<div class="close">
					</div>
				</div>
				<table width="460px" id="resTable">
					<tr>
						<td align="right" width="100px">
							姓名：
						</td>
						<td>
							<input  id="cname" />
						</td>
						<td align="right" width="100px">
							性别：
						</td>
						<td>
							<input id="sex" />
						</td>
					</tr>
					<tr>
						<td align="right">
							出生日期：
						</td>
						<td>
							<input id="birthday" />
						</td>
						<td align="right">
							身份证号：
						</td>
						<td>
							<input id="identity" />
						</td>
					</tr>
					<tr>
						<td align="right">
							民族：
						</td>
						<td>
							<input id="nation" />
						</td>
						<td align="right">
							联系电话：
						</td>
						<td>
							<input id="communicate" />
						</td>
					</tr>
					<tr>
						<td align="right">
							最高学历：
						</td>
						<td>
							<input id="degree" />
						</td>
						<td align="right">
							政治面貌：
						</td>
						<td>
							<input id="politics" />
						</td>
					</tr>
					<tr>
						<td align="right">
							身高：
						</td>
						<td>
							<input id="height" />
						</td>
						<td align="right">
							体重：
						</td>
						<td>
							<input id="weight" />
						</td>
					</tr>
					<tr>
						<td align="right">
							籍贯：
						</td>
						<td>
							<input id="nativeplace" />
						</td>
						<td align="right">
							健康状况：
						</td>
						<td>
							<input id="health" />
						</td>
					</tr>
					<tr>
						<td align="right">
							应聘职务：
						</td>
						<td>
							<input id="jobobjective" />
						</td>
						<td align="right">
							信息来源：
						</td>
						<td>
							<input id="source" />
						</td>
					</tr>
					<tr>
						<td align="right">
							现居住地：
						</td>
						<td>
							<input id="residenceplace" />
						</td>
					</tr>
					<tr>
						<td align="right">
							自我评价：
						</td>
						<td>
							<input id="selfassessment" />
						</td>
					</tr>
				</table>
			</form>
		</div>
</body>
</html>
