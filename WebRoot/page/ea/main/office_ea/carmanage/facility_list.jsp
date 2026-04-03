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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设备管理</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/carmanage/facility_list.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
var basePath="<%=basePath%>";
var  ppageNumber = "${pageNumber}";
var equipmentid = "";

</script>
</head>
<body>
    <div style="margin-top:10px;margin-left:10px;display:none;"
		class="query"><form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/><input type="text"
			style="width:250px;height:25px;" placeholder="设备名称/设备编号" name="numberOrName" value="${numberOrName }" id="numberOrName" />
		<select class="whether" style="width:150px;height:20px;margin-left:50px;">
			 <option date-status="">---请选择---</option>
	         <option date-status="01">未使用</option>
	         <option date-status="00">已使用(正常)</option>
	         <option date-status="02">已使用(损坏)</option>       
	    </select>
	    <input type="hidden" value="${hasBeenUnder }"/>
		<input
			type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;" />
		</form>
		
	</div>
	<div style="margin-top:10px;margin-left:10px;display:none;" class="query">
	汇总 
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="200" align="center">所属公司名称</th>
					<th width="80" align="center">设备编号</th>
					<th width="150" align="center">设备名称</th>
					<th width="200" align="center">设备型号</th>
					<th width="100" align="center">生产厂商</th>
					<th width="100" align="center">安装日期</th>
					<th width="100" align="center">使用场地</th>
					<th width="100" align="center">状态</th>
					<th width="100" align="center">出入口</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list" var="c">
					<tr id="${c[0]}" class="">
						<td><input type="radio" name="a" class="JQuerypersonvalue" value="${c[0]}" /></td>

						<td><%=number%></td>
						<td><span id="companyName">${c[1]}</span></td>
						<td><span id="equipmentNumber">${c[3]}</span></td>
						<td><span id="deviceName">${c[4]}</span></td>
						<td><span id="unitType">${c[2]}</span></td>
						<td><span id="manufacturer">${c[5]}</span></td>
						<c:choose>
						       <c:when test="${c[7]!=null}">
						              <td><span id="equipmentDate">${c[7]}</span></td>
						       </c:when>
						       <c:otherwise>  
						       		<td><span id="equipmentDate">未安装</span></td>
   							   </c:otherwise>
						</c:choose>
						
						<c:choose>
						       <c:when test="${c[8]!=null}">
						              <td><span id="sitename">${c[8]}</span></td>
						       </c:when>
						       <c:otherwise>  
						       		<td><span id="sitename">未安装</span></td>
   							   </c:otherwise>
						</c:choose>
                        <c:choose>
						       <c:when test="${c[9] eq '00'}">
						              <td><span id="status">已使用</span></td>
						       </c:when>
						       <c:when test="${c[9] eq '01'}">
						              <td><span id="status">未使用</span></td>
						       </c:when>
						       <c:when test="${c[9] eq '02'}">
						              <td><span id="status">已使用(已损坏)</span></td>
						       </c:when>
						</c:choose>
						<c:choose>
							<c:when test="${c[10] eq '1'}">
								<td><span id="channel">进口</span></td>
							</c:when>
							<c:when test="${c[10] eq '0'}">
								<td><span id="channel">出口</span></td>
							</c:when>
							<c:otherwise>
								<td><span id="channel"></span></td>
							</c:otherwise>
						</c:choose>
                        
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/carmanage/ea_facilityList.jspa?pageNumber=${pageNumber}&numberOrName=${numberOrName}&hasBeenUnder=${hasBeenUnder }">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
	<!--弹框-->
	<div class="fees_" id="fees_">
	    <div class="fees">
	        <h4 class="tit">设备管理<img src="<%=basePath%>/images/ico-delete.png" alt=""></h4>
	        <form method="post" class="con" id="con">
	            <div class="mil">
	                <p>公司名称：</p>
	                <div>
	                    <input type="text" value="" class="companyName" name="companyName">
	                </div>
	            </div>
	            <div class="mil">
	                <p>设备编号：</p>
	                <div>
	                    <input type="text" maxlength="20" value="" class="equipmentNumber" name="ef.equipmentNumber">
	                </div>
	            </div>
	            <div class="mil">
	                <p>设备名称：</p>
	                <div>
	                    <input type="text" maxlength="20" value="" class="put3 deviceName" name="ef.deviceName">
	                </div>
	            </div>
	            <div class="mil">
	                <p>设备型号：</p>
	                <div>
	                    <input type="text" value="" maxlength="20" class="put3 unitType" name="ef.unitType">
	                </div>
	            </div>
	            <div class="mil">
	                <p>生产厂商：</p>
	                <div>
	                    <input type="text" value="" maxlength="20" class="put3 manufacturer" name="ef.manufacturer">
	                </div>
	            </div>
	            <div class="mil">
	                <p>使用场地：</p>
	                <select class="sel site">
	                <!-- js拼接 -->
	                </select>
	            </div>
	            <div class="mil">
				<p>状态：</p>
				<select class="sel select status">
					<option date-status="01" value="01">未使用</option>
					<option date-status="00" value="00">已使用(正常)</option>
					<option date-status="02" value="02">已使用(损坏)</option>
				</select>
			   </div>

				<div class="mil">
					<p>出入口：</p>
					<select class="sel channel" name="ef.channel">
						<option value="">请选择</option>
						<option value="1">入口</option>
						<option value="0">出口</option>

					</select>
				</div>
	            <input type="hidden" class="equipmentkey" name="ef.equipmentkey">
	            <input type="hidden" value="" class="status" name="ef.status">
	            <input type="hidden" value="" class="siteId" name="ef.siteId">
	            <input type="hidden" value="" class="equipmentId" name="ef.equipmentId">
	            <input type="button" value="保存" class="sub">
	        </form>
	    </div>
	</div>	
		
		
</body>
</html>