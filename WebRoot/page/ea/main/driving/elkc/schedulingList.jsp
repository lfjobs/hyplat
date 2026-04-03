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
	<title>班次维护</title>

	<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
	<script
			src="<%=basePath%>js/ea/driving/elkc/schedulingList.js"></script>
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/Validform_v5.3.2.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/ea/driving/elkc/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" />
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/plugins/ligerMessageBox.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/custom/window.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/ligerUI/js/ligerui.min.js"></script>

	<script type="text/javascript">
         var basePath="<%=basePath%>";
         var classId = "";
         var token = 0;
         var search = "${search}";
         var pNumber = "${pageNumber}";



		</script>

</head>
<body>
<div id="mode">
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
				班次名称
			</th>
			<th width="70" align="center">
				状态
			</th>
			<th width="70" align="center">
				时间段模式
			</th>
			<th  width="170" align="center">
				创建时间
			</th>
			<th  width="170" align="center">
				更新时间
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
					<input type="radio" name="radioGroup" class="JQuerypersonvalue"
						   value="${list[0]}" />
				</td>
				<td class="td_bg01">
					<span><%=number%></span>
				</td>
				<td class="td_bg01">
					<span id="className">${list[1]}</span>
				</td>
				<td class="td_bg01">
					<c:if test ='${list[2]=="00"}'>
						未启用
					</c:if>
					<c:if test ='${list[2]=="01"}'>
						已启用
					</c:if>
					<span id="status" style="display:none;">${list[2]}</span>
				</td>
				<td class="td_bg01">
					<span id="modeName">${list[6]}</span>
					<span id="reservationModeId" style="display:none;">${list[5]}</span>
				</td>
				<td class="td_bg01">
					<span id="createdate">${fn:substring(list[3],0,19)}</span>
				</td>
				<td class="td_bg01">
					<span id="updatedate">${fn:substring(list[4],0,19)}</span>
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
				 value="ea/schedul/ea_findSchedulingList.jspa?pageNumber=${pageNumber}&search=${search}">
		</c:param>
	</c:import>
</div>

<form name="saveForm" id="saveForm" action="<%=basePath%>ea/schedul/sajax_ea_saveScheduling.jspa" method="post">
	<div class="jqmWindow"
		 style="width: 400px; height: 250px; right: 50%; top: 10%;"
		 id="jqModel">
		<input type="submit" name="submit" style="display: none"/>
		<div class="drag">
			<div class="tt"></div>
			<div class="close">
			</div>
		</div>
		<div>

			<table width="100%" cellpadding="10" cellspacing="10">
				<tr>
					<td align="right" style="height:50px;">班次名称：</td><td><input type="text" name="scheduling.className" class="className" datatype="z2-20" focusMsg="请输入班次名称" nullmsg="请输入班次名称" errormsg="班次名称应为2至20个没有空格的汉字！"><span class="Validform_checktip err_className"></span></td>
				</tr>
				<tr>
					<td align="right" style="height:50px;">状态：</td><td><select name="scheduling.status" class="status"><option value="00">未启用</option><option value="01">已启用</option></select></td>
				</tr>
				<tr>
					<td align="right" style="height:50px;">时间段模式：</td><td><s:select list="modelist" cssClass="yz" listKey="reservationModeId" listValue="modeName" name="schedulingDetail.reservationModeId" headerKey="" headerValue="请选择时间模式" theme="simple" value=""></s:select></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="hidden" class="classId" name="scheduling.classId"/>
						<input type="submit" value=" 提交 " class="input-button" id="save"/>
					</td>
				</tr>

			</table>

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
