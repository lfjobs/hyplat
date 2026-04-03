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
		<title>时间段模式维护</title>

		<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
		<script
				src="<%=basePath%>js/ea/driving/elkc/reservationModeList.js"></script>

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
         var reservationModeId = "";
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
							模式名称
						</th>
						<th width="70" align="center">
							状态
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
					<s:iterator  value="pageForm.list">
						<tr id="${reservationModeId}">
							<td class="td_bg01">
								<input type="radio" name="radioGroup" class="JQuerypersonvalue"
									   value="${reservationModeId}" />
							</td>
							<td class="td_bg01">
								<span><%=number%></span>
							</td>
							<td class="td_bg01">
								<span id="modeName">${modeName}</span>
							</td>
							<td class="td_bg01">
                                   <s:if test ='status=="00"'>
									   未启用
								   </s:if>
								   <s:else>
									已启用
								   </s:else>
								<span id="status" style="display:none;">${status}</span>
							</td>
							<td class="td_bg01">
								<span id="createdate">${fn:substring(createdate,0,19)}</span>
							</td>
							<td class="td_bg01">
								<span id="updatedate">${fn:substring(updatedate,0,19)}</span>
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
					value="ea/reservmode/ea_findReservationModeList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>



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
