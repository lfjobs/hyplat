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
<title>场地管理</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/carmanage/siteManagement.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
var basePath="<%=basePath%>";
var  ppageNumber = "${pageNumber}";
var siteId = "";

</script>
</head>
<body>
    <div style="margin-top:10px;margin-left:10px;display:none;"
		class="query"><form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/><input type="text"
			style="width:250px;height:25px;" placeholder="场地名称/场地编号" name="numberOrName" value="${numberOrName }" id="numberOrName" />
		<select class="whether" style="width:150px;height:20px;margin-left:50px;">
			 <option date-name="">---请选择---</option>
	         <option date-name="已满">已满</option>
	         <option date-name="未满">未满</option>       
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
					<th width="80" align="center">场地编号</th>
					<th width="150" align="center">场地名称</th>
					<th width="200" align="center">地址</th>
					<th width="100" align="center">总面积</th>
					<th width="100" align="center">可容纳数量</th>
                    <th width="200" align="center">东经</th>
                    <th width="100" align="center">北纬</th>
					<th width="100" align="center">已投放车辆</th>
					<th width="100" align="center">状态</th>
					<th width="100" align="center">人工审核</th>
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
						<td><span id="siteNumber">${c[2]}</span></td>
						<td><span id="siteName">${c[3]}</span></td>
						<td><span id="ItsLocation">${c[4]}</span></td>
						<td><span id="siteArea">${c[5]}</span></td>
						<td><span id="fieldCapacity">${c[6]}</span></td>
                        <td><span id="eastLongitude">${c[8]}</span></td>
                        <td><span id="northLatitude">${c[9]}</span></td>
                        <td><span id="putIn">${c[10]}</span></td>
						<td>
						    <span id="whether">${c[11]}</span>
						    <span style="color:#F00">
						    	<c:choose>  
                                   <c:when test="${c[11] eq '已满' }">
								   </c:when>  
								   <c:when test="${c[11] eq '未满' }">
								       (剩余${c[12]})
								   </c:when>  
								</c:choose>
						    </span>
						</td>
						<td><span id="isAudit" style="display:none;">${c[13]}</span>
							<span style="color:#F00">
						    	<c:choose>
									<c:when test="${c[13] eq '00'||c[13] eq ''||c[13] eq null }">
										 已开启
									</c:when>
									<c:when test="${c[13] eq '01'}">
										已关闭
									</c:when>
								</c:choose>
						    </span>

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
				value="ea/carmanage/ea_siteList.jspa?pageNumber=${pageNumber}&numberOrName=${numberOrName}&hasBeenUnder=${hasBeenUnder }">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
	<!--弹框-->
	<div class="fees_" id="fees_">
	    <div class="fees" style="height: 500px;">
	        <h4 class="tit">场地管理<img src="<%=basePath%>/images/ico-delete.png" alt=""></h4>
	        <form  action="" method="post" class="con" id="con">
	            <div class="mil">
	                <p>公司名称：</p>
	                <div>
	                    <input type="text" value="" class="companyName" name="companyName">
	                </div>
	            </div>
	            <div class="mil">
	                <p>场地编号：</p>
	                <div>
	                    <input type="text" maxlength="20" value="" class="siteNumber" name="vf.siteNumber">
	                </div>
	            </div>
	            <div class="mil">
	                <p>场地名称：</p>
	                <div>
	                    <input type="text" maxlength="20" value="" class="put3 siteName" name="vf.siteName">
	                </div>
	            </div>
	            <div class="mil">
	                <p>地址：</p>
	                <div>
	                    <input type="text" maxlength="20" value="" class="put3 ItsLocation" name="vf.ItsLocation">
	                </div>
	            </div>
	            <div class="mil">
	                <p>总面积：</p>
	                <div>
	                    <input type="text" maxlength="20" value="" class="put3 siteArea" name="vf.siteArea">
	                </div>
	            </div>
	            <div class="mil">
	                <p>可容纳车辆：</p>
	                <div>
	                    <input type='text' maxlength="20" class="put3 fieldCapacity" name="vf.fieldCapacity" value="" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();" />
	                </div>
	            </div>
	            <div class="mil">
	                <p>责任人：</p>
	                <select class="sel staff">
	                <!-- js拼接 -->
	                </select>
	            </div>
				<div class="mil">
					<p>经度：</p>
					<div>
						<input type='text' maxlength="20" class="put3 eastLongitude" name="vf.eastLongitude" value="" />
					</div>
				</div>
				<div class="mil">
					<p>纬度：</p>
					<div>
						<input type='text' maxlength="20" class="put3 northLatitude" name="vf.northLatitude" value="" />
					</div>
				</div>
				<div class="mil">
					<p>是否开启人工审核：</p>
					<div>
						<select name="vf.isAudit" class="sel" id="isAudit">
							<option value="00">开启人工审核</option>
							<option value="01">关闭人工审核</option>
						</select>
					</div>
				</div>
	            <input type="hidden" class="sitekey" name="vf.sitekey">
	            <input type="hidden" value="" class="siteId" name="vf.siteId">
	            <input type="hidden" value="" class="companyId" name="vf.companyId">
	            <input type="hidden" value="" class="staffId" name="vf.staffId">
	            <input type="button" value="保存" class="sub">
	        </form>
	    </div>
	</div>	
		
		
</body>
</html>