<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany");
		%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>考核检验管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<style type="text/css">
.windowJqm {
	left: 55%;
	width: 850px;
	margin-left: -450px;;
}

.underline {
	text-decoration: underline;
}

.sty {
	padding-left: 5px;
}

#industryClassification {
	width: 150px;
}
</style>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/admin_main.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
       		
       		var token = 0;
            var id = ""; 
            var basePath='<%=basePath%>';
            var ppageNumber='${pageNumber}';
            var search='${search}';
            var acceName = '';  //附件查看赋值
            var times = '0';
            var vouch = '';  //凭证号传值
       	    var Type="${type}";
       	    var status="${status}";
	        var show="${show}";
	        var dcheckTime="${dcheckTime}";
	       
     
		</script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/production/dcheck/DCheck.js"></script>

</head>
<body>
	<div>
		<table class="flexigrid">

			<thead>
				<tr>
					<th width="40" align="center">请选择</th>
					<th width="40" align="center">序号</th>
					<th width="100" align="center">产品行业</th>
					<th width="110" align="center">产品编号</th>
					<th width="80" align="center">产品条码</th>
					<th width="70" align="center">产品名称</th>
					<th width="60" align="center">产品规格</th>
					<th width="40" align="center">单价</th>
					<th width="40" align="center">
					<c:if test="${status=='00'}">考核数量</c:if>
					<c:if test="${status=='01'}">合格数量</c:if>
					<c:if test="${status=='02'}">不合格数量</c:if>
					 </th>
					<th width="40" align="center">审核人</th>
					<th width="80" align="center">审核人部门</th>
					<th width="80" align="center">审核时间</th>
					<th width="80" align="center">审核状态</th>
				</tr>

			</thead>
			<tbody class="JQueryflexme">

				<%
						int number = 1;
					%>
				<s:iterator value="pageForm.list" var="list">
					<tr id="${list[0]}">
						<td><input type="radio" name="a" class="JQuerypersonvalue" />
						</td>
						<td><span id="number"><%=number%></span></td>
						<td><span id="tradecode">${list[1]}</span>
						</td>
						<td><span id="itemNumber">${list[2]}</span></td>
						<td><span id="goodBar">${list[3]}</span></td>

						<td><span id="goodName">${list[4]}</span></td>
						<td><span id="goodStandard">${list[5]}</span></td>
						<td><span id="price">${list[6]}</span></td>
						<td><s:if test="status=='00'"><span id="btnumber">${list[7]}</span></s:if>
						        <s:if test="status=='01'"><span id="btnumber">${list[8]}</span></s:if>
						        <s:if test="status=='02'"><span id="btnumber">${list[9]}</span></s:if>
						        </td>
						<td><span id="auditor">${list[10]}</span></td>
						<td><span id="organizationName">${list[11]}</span></td>
						<td><span id="dcheckTime">${list[12]}</span></td>
						<td><span id="status" style="display:none;">${status}</span>
						     <s:if test="status=='00'">待考核</s:if>
						       <s:if test="status=='01'">通过</s:if>
						       <s:if test="status=='02'">未通过</s:if>
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
				value="ea/dcheck/ea_getDCheckList.jspa?pageNumber=${pageNumber}&type=${type}&status=${status}&show=${show}">
			</c:param>
		</c:import>
	</div>

	<!-- 合格 与 不合格 -->
	<div class="jqmWindow "
		style="display: none; width: 450px; height: 450px; right: 20%; top: 10%;"
		id="jqModelSend">
		<form name="dcheckForm" id="dcheckForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
        
			<div class="drag">考核检验单</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">

				<tr>

					<td align="right">审核意见：</td>
					<td>
					
					<textarea style="width:200px;height: 100px;"
							name="dcheck.auditoroption" id="auditoroption" class="put3"></textarea>
							
							</td>
				</tr>
				<tr id="yieldnumber" style="display: none;" >
					<td align="right">合格数：</td>
					<td align="left"><input  id="yield" name="dcheck.yieldnumber" class="puttt positiveNum" 
						 /></td>
				</tr>
				<tr >
					<td align="right">审核人部门：</td>
					<td align="left"><input name="dcheck.organizationName" 
						value="${department}" readonly="readonly"/></td>
				</tr>
				<tr >
					<td align="right">审核人姓名：</td>
					<td align="left"><input  name="dcheck.auditor"
						value="${account.staffName}" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">审核时间：</td>
					<td align="left"><input name="dcheck.dcheckTime" id="time"
						value="${dcheckTime}" readonly="readonly" /></td>
				</tr>
			</table>

			<div align="center" style="margin-top: 25px;">
				<input type="button" class="submitResult" id="pass" value="合格" /> <input
					type="button" class="submitResult" id="nopass" value="不合格" /> <input
					type="button" id="submitColsed" value="返回"  />
			</div>
			</center>
		</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>