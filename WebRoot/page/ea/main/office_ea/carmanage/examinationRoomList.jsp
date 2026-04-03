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
<title>考场管理</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/carmanage/examinationRoomList.js" typ e="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
var basePath="<%=basePath%>";
var  ppageNumber = "${pageNumber}";
var erId = "";

</script>
</head>
<body>
    <div style="margin-top:10px;margin-left:10px;display:none;"
		class="query"><form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/><input type="text"
			style="width:250px;height:25px;" placeholder="场地名称/场地编号" name="numberOrName" value="${numberOrName }" id="numberOrName" />
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
					<th width="80" align="center">场地编号</th>
					<th width="150" align="center">场地名称</th>
					<th width="150" align="center">地址</th>
					<th width="200" align="center">所属公司</th>
					<th width="100" align="center">监管人员</th>
					<th width="200" align="center">监管人员微分金账号</th>
					<th width="100" align="center">是否启用</th>
					<th width="100" align="center">考场/练习场</th>
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
						<td><span id="ernumber">${c[1]}</span></td>
						<td><span id="ername">${c[2]}</span></td>
						<td><span id="ItsLocation">${c[7]}</span></td>
						<td><span id="companyname">${c[3]}</span></td>
						<td><span id="staffname">${c[4]}</span></td>
						<td><span id="account">${c[5]}</span></td>
                        <c:choose>
                           <c:when test="${c[6] eq '00'}">
							   <td><span id="start" class="enable" style="color:#F00" >使用中</span></td>
                            </c:when>
                            <c:when test="${c[6] eq '01'}">
                                <td><span id="start" class="enable" style="color:#F00" >点击启用</span></td>
                            </c:when>
                        </c:choose>
						<c:choose>
							<c:when test="${c[8] eq '0'}">
								<td><span class="siteType" style="color:#F00" >考场</span></td>
							</c:when>
							<c:when test="${c[8] eq '1'}">
								<td><span class="siteType" style="color:#F00" >练习场</span></td>
							</c:when>
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
				value="ea/carmanage/ea_examinationRoomList.jspa?pageNumber=${pageNumber}&numberOrName=${numberOrName}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
	<!--弹框-->
	<div class="fees_" id="fees_">
	    <div class="fees">
	        <h4 class="tit">考场管理<img src="<%=basePath%>/images/ico-delete.png" alt=""></h4>
	        <form  action="" method="post" class="con" id="con">
                <div class="mil">
                    <p>场地编号：</p>
                    <div>
                        <input type="text" maxlength="20" value="" class="erNumber" name="exRoom.erNumber">
                    </div>
                </div>
                <div class="mil">
                    <p>场地名称：</p>
                    <div>
                        <input type="text" maxlength="20" value="" class="put3 erName" name="exRoom.erName">
                    </div>
                </div>
				<div class="mil">
					<p>地址：</p>
					<div>
						<input type="text" maxlength="20" value="" class="put3 ItsLocation" name="exRoom.ItsLocation">
					</div>
				</div>
	            <div class="mil">
	                <p>所属公司：</p>
	                <div>
	                    <input type="text" value="" class="companyName" name="companyName">
	                </div>
	            </div>
                <div class="mil">
                    <p>监管人员：</p>
                    <select class="sel regulators">
                        <!-- js拼接 -->
                    </select>
                </div>
	            <div class="mil">
	                <p>责任人：</p>
	                <select class="sel staff">
	                <!-- js拼接 -->
	                </select>
	            </div>
				<%--<div class="mil">
					<p>监管账号：</p>
					<div>
						<input type="text" value="" class="wfjzh">
					</div>
				</div>--%>
                <div class="mil">
                    <p>类型：</p>
                    <select class="sel sType">
					<!-- js拼接 -->
                    </select>
                </div>
	            <input type="hidden" class="erkey" name="exRoom.erkey">
	            <input type="hidden" value="" class="erId" name="exRoom.erId">
	            <input type="hidden" value="" class="companyId" name="exRoom.companyId">
	            <input type="hidden" value="" class="staffId" name="exRoom.staffId">
                <input type="hidden" value="" class="reviewerStaffId" name="exRoom.reviewerStaffId">
                <input type="hidden" value="" class="siteType" name="exRoom.siteType">
	            <input type="button" value="保存" class="sub">
	        </form>
	    </div>
	</div>	
		
		
</body>
</html>