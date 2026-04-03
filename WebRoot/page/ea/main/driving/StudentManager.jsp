<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String filepath = request.getSession().getServletContext().getRealPath("/");
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>教务学员管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/cstaff/studentManager.js"></script>
<script type="text/javascript">
	var basePath = '<%=basePath%>';
	var pageNumber = '${pageNumber}';
	var pbasePath = '<%=basePath%>';
	var psearch = '${search}';
	var staffID = '${staffID}';
	var personvalue = "";
	var personurl = "";
	var staffName;
	var staffsize = 0 ;//后台验证身份证时应该查到的人数sssddd
	var token =0;
	var retoken = 0;
	var notoken = 0;
	var photosizes = 0;
	var maxNum = 0;
	var opaNum = 0; //往来关系传值number  
	
	//判断flexigrid中的button
	var flexbutton = '<%=request.getParameter("flexbutton") %>';
	
    function gotoLogin(){
	    document.location="<%=basePath%> /page/ea/not_login.jsp";
	}

</script>
</head>
<body>
		<div class="main_main">
			<table class="JQueryflexme" cellpadding="0" cellspacing="0">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							序号
						</th>

						<th width="100" align="center">
							人员编号
						</th>

						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="80" align="center">
							往来关系
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${staffID}">
							<td>
								<input type="checkbox" class="chx JQuerypersonvalue"
									value="${staffID}" title="${staffID}" name="chbox"/>
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="staffCode">${staffCode}</span>
							</td>
							<td>
								<span id="recordCode">${recordCode}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="relation">${relation}</span>
							</td>
							<td>
								<span id="usedNmae">${usedNmae}</span>
							</td>
							<td>
								<span id="sex">${sex}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${birthday}</span>
							</td>
							<td>
								<span id="nativePlace">${nativePlace}</span>
							</td>
							<td>
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffIdentityCard}</span>
								<span id="staffID" style="display: none" name="editid">${staffID}</span>
								<span id="relationID" style="display: none">${relationID}</span>
								<span id="relationKey" style="display: none">${relationKey}</span>
								<span style="display: none" id="photo">${photo}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/studentManager/ea_getStudentListCStaffByCompanyID.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import> 
			
		</div>
		<iframe src="" name="main"  id="mainframe"  frameborder="0" border="0"  framespacing="0" style="height:0;"></iframe>
		
		<!--导入社会人力 -->
			<div id="bankJqm" class="jqmWindow"
			style="width: 60%; height: 410px; display:none; absolute; left: 20%; top: 20%; background: #eff; overflow-x: hidden;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
		</div>
		<!-- 吊牌打印 -->
		<div class="jqmWindow" style="width: 400px; right: 40%; top: 10%"
			id="jqModelprintIn">
			<form name="cstaffPrintForm" id="cstaffPrintForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					打印信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="printstaff">
					<tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<input size="30" maxlength="40" value="" name="printInfo.credentialsTitle" class="put3"/>
						</td>
					</tr>
					<tr>
						<td width="100" align="right">
							证件名称：
						</td>
						<td width="296">
							<input size="30" name="printInfo.credentialsName" onfocus="if(this.value=='学员证'){this.style.color='black';this.value='';}" 
					onblur="if(this.value==''){this.value='学员证';this.style.color='gray';}" value="学员证" style="color:gray;" class="ckTextLength" maxlength="50"/>
						</td>
					</tr>
					<tr>
						<td width="100" align="right">
							服务方式：
						</td>
						<td width="296">
							<input size="30" name="printInfo.serveWay" maxlength="50" class="ckTextLength" />
						</td>
					</tr>
					<tr>
						<td width="100" align="right">
							发证日期：
						</td>
						<td width="296">
							<input size="16" name="printInfo.dateofissue" onfocus="date(this)"/>
						</td>
					</tr>
					<tr>
						<td width="100" align="right">
							选择尺寸：
						</td>
						<td width="296">
						<label><input type="radio" name="IDSize" value="身份证大小" id="IDSize"/>身份证大小</label>
    					<label><input type="radio" name="IDSize" value="100mmX73mm" id="custersize"/>100mmX73mm</label>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="queding" value=" 打印 " />
				</div>
			</form>
		</div>	
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 40%; top: 15%"
				id="jqModelSearch">
				<div class="drag">
					查询信息
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td colspan="2">
							查询条件
						</td>
					</tr>
					<tr>
						<td width="60">
							人员姓名：
						</td>
						<td>
							<s:textfield name="contactuser.staffName" />
						</td>
					</tr>
					<tr>
						<td width="60">
							身份证：
						</td>
						<td>
							<s:textfield name="contactuser.staffIdentityCard" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff" value=" 查询 " />
					<input type="button" class="input-button JQueryreturn" value="取消" />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
			
	</body>
</html>