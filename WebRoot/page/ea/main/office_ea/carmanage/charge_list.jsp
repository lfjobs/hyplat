
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="hy.ea.bo.Company" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收费标准</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>/js/jquery-1.8.3.min.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/carmanage/charge_list.js"
	type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />
<style>
	div.main_img{
		padding-bottom: 21px;
		margin: 0 auto;
		height: 100px;
		position: relative;
		width: 100px;
	}
	div.main_img img{
		position: absolute;
		width: 100px;
		left: 0;
	}
	div.main_img input{
		left: 0;
		z-index: 99;
		position: absolute;
		width: 100%;
		height: 100px;
		opacity: 0;
	}
	.fees .con .mil{
		margin: 2px auto;

	}
</style>


<script type="text/javascript">
var companyID = '<%=c.getCompanyID()%>';
var basePath="<%=basePath%>";
var search = "${search}";
var  ppageNumber = "${pageNumber}";
var ercId = "";
var  fiveClear="${fiveClear}";
var erNumber = "";

</script>
	<%--<script src="<%=basePath %>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>--%>
	<script type="text/javascript">
        //图片上传
        var reader = new FileReader();
        function f_change(file){
            $("#imgPoint").hide();
            var img = document.getElementById('imgSdf');
            //读取File对象的数据
            reader.onload = function(evt){
                //data:img base64 编码数据显示
                img.width  =  "100";
                img.height =  "100";
                img.src = evt.target.result;
            }
            reader.readAsDataURL(file.files[0]);
        }
	</script>
</head>
<body>
<div style="margin-top:10px;margin-left:10px;display:none;"
	 class="query"><form id="SearchForm" name="SearchForm" method="post">
	<input type="submit" name="submit" style="display:none;"/>根据场地编号查询：<input type="text"
																			  style="width:90px;height:18px;" name="exRoom.erNumber" value="${exRoom.erNumber }" id="erNumber"/>
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
			<th width="100" align="center">每小时费用</th>
			<th width="150" align="center">场地编号</th>
			<th width="150" align="center">场地名称</th>
			<th width="150" align="center">责任人</th>
			<th width="150" align="center">是否启用</th>
		</tr>
		</thead>
		<tbody>
		<% int number = 1; %>
		<s:iterator value="pageForm.list" var="f">
			<tr id="${f[0]}">
				<td><input type="radio" name="a" class="JQuerypersonvalue" value="${f[0]}" /></td>
				<td><%=number%></td>
				<td><span id="chargeNumber">${f[1]}</span></td>
				<td><span id="price">${f[2]}</span></td>
				<td><span id="RerNumber">${f[4]}</span></td>
				<td><span id="erName">${f[5]}</span></td>
				<td><span id="staffName">${f[7]}</span></td>
				<c:choose>
					<c:when test="${f[8] eq '00'}">
						<td><span id="startusing" >使用中</span></td>
					</c:when>
					<c:when test="${f[8] eq '01'}">
						<td><span class="start"  style="color:#F00" >点击启用</span></td>
					</c:when>
				</c:choose>
			</tr>
			<% number++; %>
		</s:iterator>
		</tbody>
	</table>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
				 value="/ea/carmanage/ea_chargeList.jspa?pageNumber=${pageNumber}&exRoom.erNumber=${exRoom.erNumber }">
		</c:param>
	</c:import>
</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>


<!--弹框-->
<div class="fees_" id="fees_">
	<div class="fees">
		<h4 class="tit">收费标准<img src="<%=basePath%>/images/ico-delete.png" alt=""></h4>
		<form  action="" method="post" class="con" id="launchForm" enctype="multipart/form-data" >
			<div class="main_img">
				<input type="file" name="photo" id="sdfFile" value="" onchange="f_change(this);" >
				<img src="<%=basePath%>images/WFJClient/PersonalJoining/shangchuan_07.png" id="imgSdf" />
				<span id="imgPoint" style="color:blue;position: relative;top: 100px;display: none;" >*必须上传图片</span>
			</div>
			<div class="mil">
				<p>编号：</p>
				<div>
					<input type="text" maxlength="20" class="chargeNumber" name="erCharge.chargeNumber" value="">
					<input type="hidden" maxlength="20" class="goodsID" name="erCharge.goodsID" value="">
				</div>
			</div>
			<div class="mil">
				<p>时(元)：</p>
				<div>
					<input type='text' maxlength="20" class="posnum price" name="ppk.price" value="" />
				</div>
			</div>
			<div class="mil">
				<p>场地编号：</p>
				<select class="sel exRoom">
					<!-- js拼接 -->
				</select>
			</div>
			<div class="mil">
				<p>场地名称：</p>
				<div>
					<input type="text" class="erName" value="">
				</div>
			</div>
			<div class="mil">
				<p>责任人：</p>
				<select class="sel staff">
					<!-- js拼接 -->
				</select>
			</div>
			<div class="yj">
				<!-- js拼接 -->
			</div>

			<div class="mil">
				<p >业务佣金(元)：</p>
				<div>
					<input   class='ver dlyj' type='text' name="brokerage"/>
				</div>
			</div>
			<input type="hidden" class="ercID" name="erCharge.ercID" value="">
			<input type="hidden" class="erId" name="erCharge.erId" value="">
			<input type="hidden" class="staffID" name="erCharge.staffID" value="">
			<input type="hidden" class="staffName" name="erCharge.staffName" value="">
			<input type="hidden" class="CompanyID" name="erCharge.CompanyID" value="">
			<input type="button" value="保存" class="sub">
		</form>
	</div>
</div>


</body>
</html>