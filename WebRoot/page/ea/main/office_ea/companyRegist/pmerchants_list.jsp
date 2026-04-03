<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="cache-control" content="no-cache"/>
	<title>个人商户</title>

	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>

	<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
	<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

	<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/companyRegist/pmerchants_list.js"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	<script type="text/javascript">
        var out_request_no = "";
        var search="${search}";
        var basePath="<%=basePath%>";
        var pNumber=${pageNumber};
        var organization_type = "${applyParam.organization_type}";
        var applyment_state = "${applyResult.applyment_state}";



	</script>

</head>
<body>
<form  name="searchForm" id="searchForm">
	<input type="submit" name="submit" style="display:none"/>
	<input  name="applyParam.merchant_shortname" class="merchant_shortname" style="display:none"/>
	<input  name="applyResult.applyment_state" value="${applyResult.applyment_state}"  style="display:none"/>

	<input type="hidden" name="search" value="search" />

	<s:token/>
</form>

<table class="flexme11">
	<thead>
	<tr>
		<th width="30" align="center">
			选择
		</th>
		<th width="100" align="center">
			申请编号
		</th>
		<th width="90" align="center">
			主体类型
		</th>
		<th width="200" align="center">
			商户名称
		</th>
		<th width="100" align="center">
			商户简称
		</th>
		<th width="90" align="center">
			经营者姓名
		</th>
		<th width="150" align="center">
			申请时间
		</th>
		<th width="80" align="center">
			审核状态
		</th>
		<th width="140" align="center">
			微信支付申请单号
		</th>
		<th width="140" align="center">
			微信同步状态
		</th>
		<th width="140" align="center">
			微信同步操作
		</th>
		<th width="90" align="center">
			君子签同步操作
		</th>
		<th width="300" align="center">
			君子签同步状态
		</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${pageForm.list}" var="item" >

		<tr id="${item[2]}">
			<td>
				<input type="radio" name="a" class="JQuerypersonvalue"
					   value="${item[2]}" />
			</td>
			<td>

				<span id="out_request_no">${item[2]}</span>
			</td>
			<td>
				<span id="organization_type" style="display: none;">${item[3]}</span>
				<c:choose>
					<c:when test="${item[3]=='2401'}">个人卖家</c:when>
					<c:when test="${item[3]=='4'}">个体工商户</c:when>
					<c:when test="${item[3]=='2'}">企业</c:when>
					<c:when test="${item[3]=='3'}">党政、机关及事业单位</c:when>
					<c:when test="${item[3]=='1708'}">其他组织</c:when>
				</c:choose>
			</td>
			<td>
				<span id="companyname">${item[1]}</span>

			</td>
			<td>
				<span id="merchant_shortname">${item[4]}</span>

			</td>
			<td>
				<span id="id_card_name">${item[5]}</span>

			</td>
			<td>
				<span id="submitdate">${fn:substring(item[6],0,19)}</span>
			</td>
			<td>
				<span id="ccompanyid" style="display: none;">${item[0]}</span>
				<span id="auid" style="display: none;">${item[12]}</span>
				<span id="state" style="display: none;">${item[7]}</span>

				<c:choose>
					<c:when test="${item[7]=='01'}">审核中</c:when>
					<c:when test="${item[7]=='02'}">审核通过</c:when>
					<c:when test="${item[7]=='03'}">已驳回(${item[13]})</c:when>
					<c:when test="${item[7]=='04'}">已转交</c:when>
					<c:when test="${item[7]=='05'}">发起人已撤销</c:when>

				</c:choose>

			</td>
			<td>
				<span id="applyment_id">${item[10]}</span>

			</td>
			<td>
				<span id="applyment_state_desc">
				 <c:choose>
					<c:when test="${item[8]=='REJECTED'}">${item[9]}(${item[11]})</c:when>

						<c:when test="${item[8]=='FROZEN'}">${item[9]}(${item[11]})</c:when>

					<c:otherwise>
						${item[9]}
					</c:otherwise>
                 </c:choose>
				</span>

			</td>
			<td>
				<img title="同步数据" src="<%=basePath%>images/ea/office/aync.png"  class="syn" style="height:15px;cursor: pointer"/>
				<img title="查询结果" src="<%=basePath%>images/ea/office/down4.png"  class="synsearch" style="height:15px;cursor: pointer"/>
			</td>
			<td>
				<img title="同步数据" src="<%=basePath%>images/ea/office/aync.png"  class="synJzq" style="height:15px;cursor: pointer"/>
				<img title="查询结果" src="<%=basePath%>images/ea/office/down4.png"  class="synJzqsearch" style="height:15px;cursor: pointer"/>
			</td>

			<td>
				<span id="jzqmsg">
						${item[14]}
				</span>
				<span id="jzqresultCode">
						${item[15]}
				</span>
				<span id="emailOrMobile">
						${item[16]}
				</span>

			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath" value="ea/merch/ea_getMerchanetsRegistList.jspa?search=${search}&pageNumber=${pageNumber}&applyParam.organization_type=${applyParam.organization_type}&applyResult.applyment_state=${applyResult.applyment_state}">
	</c:param>
</c:import>
<!--添加窗口 -->
<form name="addForm" id="addForm" method="post">
	<div class="jqmWindow" style="width:550px;right: 30%;top:10%" id="jqModeladd">
		<input type="submit" name="submit" style="display:none"/>
		<div class="drag">
			驳回
			<div class="close">
			</div>
		</div>

		<table width="100%" id="addTable">
			<tr>
				<td align="right">驳回原因：</td>
				<td>
					<textarea rows="10" cols="60" name="auditRecord.AuditComment" autofocus="autofocus"></textarea>
					<input type="hidden" name="auditRecord.state"  class="state"/>
					<input type="hidden" name="auditRecord.auid"  class="auid"/>
					<input type="hidden" name="contactCompany.ccompanyID"  class="ccompanyID"/>

				</td>

			</tr>


		</table>
		<div align="center">
			<input type="button" class="input-button" id="save" value=" 提交 "  style="margin: 10px;" />
		</div>
	</div>
</form>


<%------------------------------------选择往来个人------------------------------------%>




<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
