<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>停车场收费标准</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/carmanage/feescale_list.js"
	type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />




<script type="text/javascript">

var basePath="<%=basePath%>";
var search = "${search}";
var  ppageNumber = "${pageNumber}";
var goodsID = "";
var  fiveClear="${fiveClear}";
var totalPct = "${totalPct}";

</script>
</head>
<body> 
     <div style="margin-top:10px;margin-left:10px;display:none;"
		class="query"><form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>根据场地编号查询：<input type="text"
			style="width:90px;height:18px;" name="vf.siteNumber" value="${vf.siteNumber }" id="siteNumber"/>
		<input
			type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;" />
		</form>
		
	</div>
    
	<div style="margin-top:10px;margin-left:10px;display:none;" class="query">
	<form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>收费标准 &nbsp;&nbsp;&nbsp;
	</form>
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="150" align="center">编号</th>
					<th width="90" align="center">单价费用</th>
					<th width="170" align="center">时间单位</th>
					<th width="90" align="center">场地编号</th>
					<th width="150" align="center">场地名称</th>
					<th width="90" align="center">责任人</th>
					<th width="100" align="center">是否启用</th>
					<th width="80" align="center">车类型</th>
					<th width="80" align="center">免费时长(分钟)</th>
				</tr>
			</thead>
			<tbody>
				<% int number = 1; %>
				<s:iterator value="pageForm.list" var="f">
					<tr id="${f[0]}">
						<td><input type="radio" name="a" class="JQuerypersonvalue" value="${f[0]}" /></td>
						<td><%=number%></td>
						<td><span id="chargeNumber">${f[1]}</span></td>
						<td><span id="price" style="display: none">${f[2]}</span>
							<span id="ef_price" style="display: none">${f[12]}</span>
							<span id="re_price"style="display: none">${f[13]}</span>
							<span id="brokerage" style="display: none">${f[14]}</span>
							<span id="zjprice"><fmt:formatNumber type="number" value="${f[13]*(1+totalPct/100)}" maxFractionDigits="2" minFractionDigits="2"/></span>
							<span id="suid" style="display: none">${f[15]}</span>
							<span id="isTotalPct" style="display: none">${f[16]}</span>
						</td>
						<td>
							<span id="timeUnits" style="display: none;">${f[9]}</span>
							<c:choose>
							<c:when test="${f[9] eq '0'}">
								<span>小时</span>
							</c:when>
							<c:when test="${f[9] eq '1'}">
								<span>包天</span>
							</c:when>
							<c:when test="${f[9] eq '2'}">
									<span>包月</span>
							</c:when>
								<c:when test="${f[9] eq '3'}">
									<span>包年</span>
								</c:when>
						</c:choose>

							<c:choose>
								<c:when test="${f[17] eq '0'}">
									<span>-当天(00点结束)</span>
								</c:when>
								<c:when test="${f[17] eq '24'}">
									<span>-24小时制</span>
								</c:when>
								<c:when test="${f[17] eq '8'}">
									<span>-8小时制</span>
								</c:when>
								<c:otherwise>

								</c:otherwise>
							</c:choose>
							<span id="timeType"  style="display: none">${f[17]}</span>
						</td>
						<td><span id="siteNumber" siteid="${f[3]}">${f[4]}</span></td>
						<td><span id="siteName">${f[5]}</span></td>
						<td><span id="staffName" >${f[7]}</span>
							<span id="staffID" style="display: none">${f[6]}</span>
						</td>
						<td>
							<span id="startUsing" style="display: none">${f[8]}</span>
						<c:choose>
						       <c:when test="${f[8] eq '00'}">
						             <span class="start" >点击停用</span>
						       </c:when>
						       <c:when test="${f[8] eq '01'}">
						            <span class="start" style="color:#F00" >点击启用</span>
						       </c:when>
						</c:choose>
						</td>
						<td>
							<span id="carType" style="display: none;">${f[10]}</span>
						<c:choose>
						       <c:when test="${f[10] eq 'p'}">
								   <span>私家车</span>
						</c:when>
						<c:when test="${f[10] eq 'c'}">
							<span>教练车</span>
						</c:when>
						</c:choose>

						</td>
						<td><span id="feeMini">${f[11]}</span></td>
					</tr>
					<% number++; %>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/carmanage/ea_feescale.jspa?pageNumber=${pageNumber}&vf.siteNumber=${vf.siteNumber }">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
		
		<!--弹框-->
	<div class="fees_" id="fees_">
	    <div class="fees">
	        <h4 class="tit">收费标准<img src="<%=basePath%>/images/ico-delete.png" alt=""></h4>
	        <form  action="" method="post" class="con" id="con" name="addForm">
	            <div class="mil">
	                <p>编号：</p>
	                <div>
	                    <input type="text" maxlength="20" class="chargeNumber" name="feeScale.chargeNumber" value="">
	                </div>
	            </div>

				<div class="mil">
					<p>车类型：</p>
					<div>
						<select class="sel" name="feeScale.carType" id="carType">
							<option value="c">教练车</option>
							<option value="p">私家车</option>
						</select>
					</div>
				</div>
				<div class="mil">
					<p>时间单位：</p>
					<div>
						<select class="sel" name="feeScale.timeUnits" id="timeUnits">
							<option value="0">小时</option>
							<option value="1">包天</option>
							<option value="2">包月30天</option>
							<option value="3">包年</option>
						</select>
					</div>
				</div>
				<div class="mil btzd" style="display: none;">
					<p>包天制度：</p>
					<div>
						<select class="sel" name="feeScale.timeType" id="timeType">
							<option value="0">当天(00点结束)</option>
							<option value="24">24小时制</option>
							<option value="8">8小时制</option>
						</select>
					</div>
				</div>
				<div class="mil">
					<p>成本价(元)：</p>
					<div>
						<input  maxlength="20" class="cbprice price" name="proSetup.efPrice" value="0"  id="ef_price"  type="number" step="1"  min="0" onkeyup="this.value= this.value.match(/\d+(\.\d{0,3})?/) ? this.value.match(/\d+(\.\d{0,3})?/)[0] : ''"/>
					</div>
				</div>
				<div class="mil">
					<p>佣金(元)：</p>
					<div>
						<input  maxlength="20" class="yjprice price" name="proSetup.brokerage" value="0"  id="brokerage"  type="number" step="1"  min="0" onkeyup="this.value= this.value.match(/\d+(\.\d{0,3})?/) ? this.value.match(/\d+(\.\d{0,3})?/)[0] : ''"/>
					</div>
				</div>
				<c:forEach items="${dlList}" var="item" varStatus="state">
					<c:if test="${item.goodsName eq '客户积分'}">
				<div class="mil dlyj">
					<p>${item.goodsName}(元)：</p>
					<div>
						<input  id="${item.ppID}" class='jfprice' type="text" name='pssMap[${state.index}].amount' value="0"/>
					    <input type='hidden' value="${item.ppID}" name='pssMap[${state.index}].typePpid'/>
					</div>
				</div>
				</c:if>
					<c:if test="${item.goodsName ne '客户积分'}">
						<div class="mil dlyj" style="display: none;">
							<p>${item.goodsName}(元)：</p>
							<div>
								<input  id="${item.ppID}"  name='pssMap[${state.index}].amount' value="0" type="number" step="1"  min="0" onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''"/>
								<input type='hidden' value="${item.ppID}" name='pssMap[${state.index}].typePpid'/>
							</div>
						</div>
					</c:if>
				</c:forEach>
				<div class="mil">
					<p>系统单价(元)：</p>
					<div>
						<input type='text' maxlength="20" class="sysprice price " name="proSetup.rePrice" value="0" readonly id="re_price"/>
					</div>
				</div>
				<c:if test="${totalPct ne '0'}">
				<div class="mil">
					<p>加消费红包单价(元)：</p>
					<div style="width: 200px;">
						<input type='text' maxlength="20"  class="price zjprice"  value="0" readonly  id="zjprice"/>
					</div>
					<%--<input type="checkbox" id="totalPct" value="1" name="feeScale.isTotalPct" class="check" />--%>
					<label class="check">加(${totalPct}%)</label>
				</div>
				</c:if>
				<div class="mil">
					<p>免费时长(分钟)：</p>
					<div>
						<input  maxlength="20" class="posnum" name="feeScale.feeMini" value="" id="feeMini"  type="number" step="1"  min="0" onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''"/>
					</div>
				</div>

	            <div class="mil">
	                <p>场地编号：</p>
	                <select class="sel site">
	                	<!-- js拼接 -->
	                </select>
	            </div>
	            <div class="mil">
	                <p>场地名称：</p>
	                <div>
	                    <input type="text" class="siteName" value="">
	                </div>
	            </div>
	            <div class="mil">
	                <p>责任人：</p>
	                <select class="sel staff">
	                    <!-- js拼接 -->
	                </select>
	            </div>
	            <input type="hidden" class="feecID" name="feeScale.feecID" value="">
	            <input type="hidden" class="siteId" name="feeScale.siteId" value="">
	            <input type="hidden" class="staffID" name="feeScale.staffID" value="">
	            <input type="hidden" class="staffName" name="feeScale.staffName" value="">
	            <input type="hidden" class="CompanyID" name="feeScale.CompanyID" value="">
	            <input type="button" value="保存" class="sub">
	        </form>
	    </div>
	</div>

</body>
</html>