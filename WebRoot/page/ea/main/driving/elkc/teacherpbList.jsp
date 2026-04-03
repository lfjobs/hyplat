<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>教练排班</title>

	<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
	<script
			src="<%=basePath%>js/ea/driving/elkc/teacherpbList.js"></script>

	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/ea/driving/elkc/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" />
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerMessageBox.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/custom/window.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/ligerui.min.js"></script>

	<script type="text/javascript">
         var basePath="<%=basePath%>";
         var trsId = "";
         var token = 0;
         var search = "${search}";
         var pNumber = "${pageNumber}";

		</script>

</head>
<body>
<div>
	<table class="mode">
		<thead>
		<tr>
			<th width="60" align="center">
				选择
			</th>
			<th width="30" align="center">
				序号
			</th>
			<th width="70" align="center">
				教练员名称
			</th>
			<th width="200" align="center">
				身份证号
			</th>
			<th width="150" align="center">
				手机号
			</th>
			<th width="150" align="center">
				班次
			</th>
			<th width="150" align="center">
				排班时间
			</th>



		</tr>
		</thead>
		<tbody>
		<%
			int number = 1;
		%>
		<s:iterator  value="pageForm.list" var="list">
			<tr id="${list[0]}">
				<td class="td_bg01">
					<input type="checkbox" name="checkinput" class="JQuerypersonvalue"
						   value="${list[0]}" />
					<span id="trsId" style="display: none;">${list[1]}</span>
					<span id="status" style="display: none;">${list[8]}</span>
				</td>
				<td class="td_bg01">
					<span><%=number%></span>
				</td>
				<td class="td_bg01">
					<span id="teachername">${list[3]}</span>
				</td>
				<td class="td_bg01">
					<span id="idcard">${list[5]}</span>
				</td>
				<td class="td_bg01">
					<span id="mobile">${list[4]}</span>
				</td>
				<td class="td_bg01">
					<c:if test='${list[7]==""||list[7]==null}'>尚未排班</c:if>
					<c:if test='${list[7]!=""&&list[7]!=null}'><span id="className">${list[7]}</span></c:if>

					<span id="classId" style="display:none;">${list[2]}</span>
				</td>
				<td class="td_bg01">
					<span id="update">${fn:substring(list[6],0,19)}</span>
				</td>

			</tr>
			<%
				number++;
			%>
		</s:iterator>
		</tbody>
	</table>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
				 value="ea/teacherpb/ea_findTeacherPbList.jspa?pageNumber=${pageNumber}&search=${search}">
		</c:param>
	</c:import>
</div>

<form name="saveForm" id="saveForm" action="<%=basePath%>ea/teacherpb/sajax_ea_saveTeacherPb.jspa" method="post">
	<div class="jqmWindow"
		 style="width: 400px; height: 300px; right: 50%; top: 10%;"
		 id="jqModel">
		<input type="submit" name="submit" style="display: none"/>
		<div class="drag">
			<div class="tt"></div>
			<div class="close">
			</div>
		</div>
		<div style="height:200px; overflow-y:auto;text-align: center;padding-left:20%;">

			<table width="100%" cellpadding="10" cellspacing="10">
				<s:iterator value="schedullist">
				<tr>
					<td align="left" height="25px;"><input type="radio" name="classId" class="JQuery"
														   value="${classId}" /><span class="bcmc">${className}</span></td>
				</tr>
				</s:iterator>


			</table>

		</div>
		<div style="text-align:center;width:100%;">
			    <input type="hidden"  name="teachers" id="teachers"/>
				<input type="button" value=" 提交 " class="input-button" id="save"/>
		</div>


	</div>
</form>

<iframe name="hidden" width="0" height="0"></iframe>

<script type="text/javascript">
    setTimeout(function () {
        $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 -80 + "px"});
    }, 100);

    $(window).resize(function () {
        setTimeout(function () {
            $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 -80 + "px"});
        }, 100);
    });
</script>

</body>
</html>
