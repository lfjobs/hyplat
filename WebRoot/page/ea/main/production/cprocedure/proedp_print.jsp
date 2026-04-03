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
<title>设备分配</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.table {
	border: #a8c7ce 1px solid;
	cellpadding: 0;
	cellspacing: 0;
	font-size: 13px;
}

.td {
	border: #cccccc 1px solid;
	white-space:nowrap;
	word-break:keep-all;
}
</style>
<script type="text/javascript">
$(function(){
	/*
	 * zj
	 */
	var pedId="";
	$(".fieClass").each(function(){
		if(pedId=="")
			pedId+=$(this).attr("id");
		else
			pedId+=","+$(this).attr("id");
	});
	$.ajax({
		url:"<%=basePath%>ea/proedpdist/sajax_ea_ajaxGetFieData.jspa?pedId="+pedId,
		type:"post",
		async : true,
		success:function(data){
			var member = eval("(" + data + ")");
			var list=member.list;
			for(var i=0;i<list.length;i++){
				$("#"+list[i][0]).find("span").eq(9).text(list[i][1]);
			}
		},
		error:function(data){
			alert("数据获取失败");
		}
	});
})
</script>
</head>
<body>
	<table width="99%" border="0" align="center" style="margin-top:20px;">
		<tr>
			<td align="center" colspan="6">
				<h2>设备分配</h2></td>
		</tr>
	</table>
	<table width="99%" align="center" class="table" 
		cellpadding="10">
		<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="50" align="center">序号</th>
					<th width="100" align="center">项目产品编号</th>
					<th width="100" align="center">项目名称</th>
					<th width="100" align="center">开始时间</th>
					<th width="100" align="center">结束时间</th>
					<th width="60" align="center">分配责任人</th>
					<th width="60" align="center">职 责</th>
					<th width="180" align="center">设备分配</th>
					<th width="170" align="center">分配时间</th>
					<th width="80" align="center">所属场地</th>
					<th width="100" align="center">分配备注</th>
			</thead>
			<tbody>
		
				<tbody>
				<s:iterator value="list" status="idx">
					<tr id="${pedId}" class="fieClass">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${pedId}" /></td>
						<td><span>${idx.index+1 }</span></td>
						<td><span id="prodctSn">${prodctSn}</span></td>
						<td><span id="goodsName">${goodsName}</span></td>
						<td><span id="pedStartDate">${fn:substring(pedStartDate,0,10)}</span></td>
						<td><span id="pedEndDate">${fn:substring(pedEndDate,0,10)}</span></td>
						<td><span id="staffName">${staffName}</span></td>
						<td><span id="duty">${duty}</span></td>
						<td><span id="devices">${devices}</span></td>
						<td><span id="distDate">${fn:substring(distDate,0,19)}</span></td>
						<td><span></span></td>
						<td><span id="distRemark">${distRemark}</span></td>
					</tr>
		
					
				</s:iterator>
			</tbody>

	</table>
</body>
</html>
