<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>岗位帐号汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<SCRIPT type="text/javascript">
   var pbasePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var pserch = '${search}';
	var notoken = 0;
	var token = 0;
$(function(){ 
	//$(".jqmWindow").jqm({
	//	modal : true, 
	//	overlay : 20 
	//	}).jqmAddClose('.close'); 
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector 
    $('.aop').flexigrid({
		height:'auto',
		width: 'auto',
		minwidth: 30,
		title: '岗位帐号汇总',
		minheight: 80 , 
		 buttons: [ {
            name:'查询',
            bclass:'mysearch',
            onpress : action //当点击调用方法
        },{
             // 设置分割线  
            separator: true
        },{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }]
	});
    function action(com, grid){
        switch (com) {
            case '查询':
	            $("#jqModelSearch").jqmShow();
	            break;
            
            case '设置每页显示条数':
				var url="<%=basePath%>ea/caccount/ea_getListAOP.jspa?search=${search}";
				numback(url);
				break;
        }
    }
     $("#tosearch").click(function() {
		$("#SearchForm").attr("action","<%=basePath%>/ea/caccount/ea_toSearchAOP.jspa?pageNumber=${pageNumber}");
		document.SearchForm.submit.click();
	});
    $(".aop").find("select").each(function(){
                    $s = $(this).hide();
                    $o = $("<span/>").text($s.find("option:selected").text()).attr('id',this.id);
                    $o.insertAfter($s);
                });
     })
</SCRIPT>
</head>
<body>
	<form name="jobPlanForm" id="jobPlanForm" method="post">
		<s:token></s:token>
		<input type="submit" name="submit" style="display:none"/>
	<table class="aop">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="70" align="center">机构编号</th>
						<th width="100" align="center">部门名称</th>
						<th width="70" align="center">岗位编号</th>
						<th width="100" align="center">岗位名称</th>
						<th width="70" align="center">职务编号</th>
						<th width="100" align="center">职务名称</th>
						<th width="100" align="center">帐号名称</th>
						<th width="100" align="center">登录帐号</th>
						<th width="110" align="center">角色授权</th>
						<th width="70" align="center">帐号状态</th>
						<th width="70" align="center">在线情况</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" var="arr">
						<tr id="${arr[0]}" >
							<td class="td_bg01"><input type="radio" name="a"
								class="JQuerypersonvalue" id="${arr[0] }" /></td>
							<td class="td_bg01"><span id="">${arr[1]}</span>
							</td>
							<td class="td_bg01"><span id="">${arr[2]}</span>
							</td>
							<td class="td_bg01"><span id="">${arr[3]}</span>
							</td>
							<td class="td_bg01"><span id="">${arr[4]}</span>
							</td>
							<td class="td_bg01"><span id="">${arr[5]}</span>
							</td>
							<td class="td_bg01"><span id="">${arr[6]}</span>
							</td>
							<td class="td_bg01"><span id="">${arr[7]}</span>
							</td>
							<td class="td_bg01"><span id="">${arr[8]}</span>
							</td>
							<td><s:select list="roleList" listKey="roleID"
									listValue="roleName" theme="simple" value="#arr[11]"
									disabled="true"> 
								</s:select></td>
							<td class="td_bg01"><span id="">
								<c:if test="${arr[9] == '00'}">正常</c:if> <c:if
									test="${arr[9] == '02'}">停用</c:if></span>
							</td>
							<td class="td_bg01"><span id="">
								<c:if test="${arr[10] == '00'}">离线</c:if> <c:if
									test="${arr[10] == '01'}">在线</c:if></span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		<div id="main_main" class="main_main">
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/caccount/ea_getListAOP.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
		</div>
	</form>

	<!--搜索窗口 -->
	<form name="SearchForm" id="SearchForm" method="post">
		<div class="jqmWindow" style="width: 300px;right: 25%;top: 10%; " align="center"
			id="jqModelSearch">

			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				查询信息
				<div class="close"></div>
			</div>
			<table id="SearchTable" >
				<tr>
					<td>查询条件</td>
				</tr>
				<tr>
					<td >部门：</td>
					<td><input name="cacUtil.orgName"></td>
				</tr>
				<tr>
					<td>岗位：</td>
					<td><input name="cacUtil.opoName"></td>
				</tr>
				<tr>
					<td>职务：</td>
					<td><input name="cacUtil.depName"></td>
				</tr>
				<tr>
					<td>帐号：</td>
					<td><input name="cacUtil.cacName"></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select name="cacUtil.cacstauts"><option value="">请选择</option><option value="00">正常</option><option value="02">停用</option></select></td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch" value=" 查询 " />

				<input name="search" type="hidden" value="search" /> <input
					type="hidden" name="staffID" value="${staffID}" />
			</div>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
