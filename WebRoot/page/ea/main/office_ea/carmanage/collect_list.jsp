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
<title>汇总</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/carmanage/collect_list.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var basePath="<%=basePath%>";
var  ppageNumber = "${pageNumber}";
var carmID = "";
var model = "";
</script>
</head>
<body>
    <div style="margin-top:10px;margin-left:10px;display:none;"
		class="query"><form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>请输入车牌号查询：<input type="text"
			style="width:90px;height:18px;" name="cm.carNumber" value="${cm.carNumber }" id="carNumber"/>&nbsp;&nbsp;&nbsp;
			场地编号或名称：<input type="text" value="" style="width:90px;height:18px;" name="vf.siteNumber"  />&nbsp;&nbsp;&nbsp;
			设备编号或名称：<input type="text" value="" style="width:90px;height:18px;" name="ef.equipmentNumber"/>&nbsp;&nbsp;&nbsp;
			状态：<select name="timeType" style="outline: none;"><option value="none" selected>进出</option><option value="in">进入</option><option value="out">离开</option></select>
		时间：<input name="inTime" id="inTime" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" style="width: 90px" autocomplete="off">
		至<input name="outTime" id="outTime" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" style="width: 90px" autocomplete="off">
		<input
			type="button" class="input-button" value="  查询  "  id="tosearch" style="margin:0px;margin-left:5px;" />
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
					<th width="150" align="center">编号</th>
					<th width="80" align="center">车牌号</th>
					<th width="200" align="center">进入时间</th>
					<th width="200" align="center">离开时间</th>
					<th width="200" align="center">总收费(元)</th>
					<th width="200" align="center">共计时间</th>
					<th width="200" align="center">场地编号</th>
					<th width="100" align="center">场地名称</th>
					<th width="150" align="center">设备编号</th>
					<th width="100" align="center">设备名称</th>
					<th width="100" align="center">场地负责人</th>
					<th width="40" align="center">状态</th>
					<th width="200" align="center">收费方式</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list" var="c">
					<tr id="${c[0]}" class="">
						<td><input type="radio" name="a" class="JQuerypersonvalue" value="${carmID}" /></td>

						<td><%=number%></td>
						<td><span id="carmNumber">${c[10]}</span></td>
						<td><span id="carNumber">${c[1]}</span></td>
						<td><span id="indate">${c[2]}</span></td>
						<td><span id="outdate">${c[3]}</span></td>
						<c:choose> 
						  <c:when test="${c[11]!=null}">
						    <td><span id="money">${c[11]}</span></td>
						  </c:when> 
						  <c:otherwise>
						  	<c:choose> 
							  <c:when test="${c[9]=='1'}">
							    <td><span id="money">${c[11]}</span></td>
							  </c:when> 
							  <c:otherwise>
							  	<td><span id="money">未设置收费标准,请前往设置</span></td>
							  </c:otherwise>
							</c:choose>
						  </c:otherwise>
						</c:choose>
						<td><span id="time">${c[12]}</span></td>
						<td><span id="siteNumber">${c[4]}</span></td>
                        <td><span id="sitename">${c[5]}</span></td>
                        <td><span id="equipmentNumber">${c[6]}</span></td>
						<td><span id="deviceName">${c[7]}</span></td>
                        <td><span id="staffname">${c[8]}</span></td>
						<td><span id="status">${c[9]=="1"?'院内':'离开'}</span></td>
						<c:if test="${c[15] eq null}">
						<c:choose> 
						  <c:when test="${c[14]=='00' }">
						    <td><span id="chargeType">现金收费</span></td>
						  </c:when> 
						  <c:when test="${c[14]=='01' }">
						    <td><span id="chargeType">金币/积分收费</span></td>
						  </c:when> 
						  <c:otherwise> 
						  	<td><span id="chargeType">未离开</span></td>
						  </c:otherwise>
						</c:choose>
						</c:if>
					<c:if test="${c[15] ne null}">
						<c:choose> 
						  <c:when test="${c[15]=='00' }">
						    <td><span id="chargeType">未结算</span></td>
						  </c:when> 
						  <c:when test="${c[15]=='01' }">
						    <td><span id="chargeType">包月或包年</span></td>
						  </c:when> 
						   <c:when test="${c[15]=='02' }">
						    <td><span id="chargeType">金币/积分收费</span></td>
						  </c:when> 
						    <c:when test="${c[15]=='03' }">
						    <td><span id="chargeType">扫码支付</span></td>
						  </c:when> 
						    <c:when test="${c[15]=='04' }">
						    <td><span id="chargeType">现金支付</span></td>
						  </c:when> 
						    <c:when test="${c[15]=='05' }">
						    <td><span id="chargeType">消费金额未0无需收费</span></td>
						  </c:when> 
						  <c:otherwise> 
						 <td><span id="chargeType">未离开</span></td>
						</c:otherwise>  
						</c:choose>
						</c:if>
						<input type="hidden" value="${c[13]}" class="model">
					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/carmanage/ea_collectList.jspa?pageNumber=${pageNumber}&cm.carNumber=${cm.carNumber }">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
	<!--弹框-->
	<div class="fees_" id="fees_">
	    <div class="fees">
	        <h4 class="tit">停车管理<img src="<%=basePath%>/images/ico-delete.png" alt=""></h4>
	        <form  action="" method="post" class="con" id="con">
	            <div class="mil">
	                <p>编号：</p>
	                <div>
	                    <input type="text" value="" class="carmNumber" name="cm.carmNumber">
	                </div>
	            </div>
	            <div class="mil">
	                <p>车牌号：</p>
	                <select class="sel carm">
	                <!-- js拼接 -->
	                </select>
	            </div>
	            <div class="mil">
	                <p>开始时间: </p>
	                <div>
	                    <input type="text" value="" class="indate" name="cm.indate">
	                </div>
	            </div>
	            <div class="mil">
	                <p>离开时间: </p>
	                <div>
	                    <input type="text" value="" class="outdate" name="cm.outdate">
	                </div>
	            </div>
	            <div class="mil">
	                <p>场地编号：</p>
	                <div>
	                    <input type="text" value="" class="siteNumber" name="vf.siteNumber">
	                </div>
	            </div>
	            <div class="mil">
	                <p>场地名称：</p>
	                <div>
	                    <input type="text" value="" class="siteName" name="vf.siteName">
	                </div>
	            </div>
	            <div class="mil">
	                <p>设备编号：</p>
	                <div>
	                    <input type="text" value="" class="equipmentNumber" name="cm.equipmentNumber">
	                </div>
	            </div>
	            <div class="mil">
	                <p>场地负责人：</p>
	                <div>
	                    <input type="text" value="" class="staffname">
	                </div>
	            </div> 
	            <input type="hidden" value="" class="carmID" name="cm.carmID">
	            <input type="hidden" value="" class="carNumber" name="cm.carNumber">
	            <input type="submit" value="保存" class="sub">
	        </form>
	    </div>
	</div>	
		
		
</body>
</html>