<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
	String path = request.getContextPath();	
String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<base href="<%=basePath%>">
<!-- 判断传入的值点击添加按钮 就会到此页面但是GOODSID里面 是空的SO 就是增加 有值就是修改 -->
<title>物品
<s:if test="#goodsID!=null">
		修改
</s:if>
<s:if test="#goodsID==null">
添加
</s:if>

</title>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
			
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ckeditor/ckeditor.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<script type = "text/javascript" language = "javascript">
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/goodsmanage/goodsmanage_listadd.js"  type="text/javascript"></script>
 <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>

<link rel="stylesheet" href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath%>/css/css.css" />

<script src="<%=basePath%>js/ea/wechat/wechatMenuList.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ckeditor/ckeditor.js"></script>
<script src="<%=basePath%>swfupload/swfupload.js"></script>
<script src="<%=basePath%>swfupload/files_uploadauto.js"></script>

<style type="text/css">
body {
	overflow-y: scroll;
}
.xx{color: black;}
</style>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var chejiahao;
	var goodsID;
	var treeName='${treeName}';
	$(document).ready(function(){
	var tree = treeName.split(",");
	if(tree!=null&&tree!=""){
		$("#bigClass").val(tree[2]);
		$("#tradeCode").val(tree[0]);
		$("#typeID").val(tree[1]);
		$("#bigClas").html(tree[2]);
		$("#tradeCod").html(tree[0]);
		$("#typeI").html(tree[1]);
	}
	});
</script>
</head>
<body>
	<div id="top" style="width: 100%; height: 40px;background-color: red;">
		<div
			style="font-size: 18;color:white;padding-top: 10px; padding-left: 5px;">物品管理</div>
	</div>
	<div id="tupiandiv" class="div tupian"
		style="margin-top: 10px;margin: auto auto; background-color: gray; width: 400px; height:150px; border-bottom: 2px;">
		<img
			<s:if test="#goodsManage.photoPath!=null&&#goodsManage.photoPath!=''">
			      	      src="<%=basePath%>/${goodsManage.photoPath}"
		</s:if>
			<s:else>
			      	     	src="<%=basePath%>/images/zanwutup.png"
			      	    </s:else>
			style="width: 100%; height: 100%" title="logo" id="dazhuti"
			onclick="chuan('zhuti')" />
	</div>
	<!-- 物品添加.开始  -->
	<div id="shuju" class="shuju"
		style="margin-top: 21px;background-color:#f0f0f0 ; margin :auto auto;width:800px;  border-bottom: 2px; margin-top: 5px;">
		<table width="800" id="stafftable2" cellpadding="0" cellspacing="0"
			style="height:500px;">
			<form id="shujufrom" method="post" enctype="multipart/form-data" name="shujufrom">
				<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
					framespacing="0" height="0"></iframe>
				<!-- 隐藏域-->
				<div style="display:none;">
					
					<input type="file" name="goodsManage.fileLogo" id="tuplogo" accept="image/gif, image/jpeg,image/BMP,image/png"/> 
					<input type="file" name="goodsManage.filePhoto" id="tupphoto" accept="image/gif, image/jpeg,image/BMP,image/png"/> 
					<input type="file" name="goodsManage.fileship" id="tuppship" accept="audio/mp4"/> 
					<input type="submit" id="qijiao"  onclick=""/>	
						<input
						type="text" name="goodsManage.goodsID" id="id"
						value="${goodsManage.goodsID}" /> <input type="text"
						name="goodsManage.logoPath" value="${goodsManage.logoPath}" /> <input
						type="text" name="goodsManage.shippath"
						value="${goodsManage.shippath}" /> 
						<input
						type="text" name="goodsManage.photoPath"
						value="${goodsManage.photoPath}" />
						<input
						name="goodsManage.goodsvariable" id="goodsvariable" type="hidden"
						size="20" /> <input type="text"
						name="goodsManage.goodsKey" id="key"
						value="${goodsManage.goodsKey}" />					
						 <input type="reset"
						id="qingchu" /> <a href="javascript:window.close();"
						id="closejsp">close</a>
				</div>

				<%-- <tr>
					<td height="40" align="right">行业分类管理:</td>
					<td><input name="goodsManage.tradeCode" type="text"
						maxlength="30" id="tradeCode" size="20"
						class="put3 isremove" value="${goodsManage.tradeCode}" readonly="readonly" /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td height="40" align="right">物品类别管理:</td>
					<td><input name="goodsManage.typeID" type="text"
						maxlength="30" id="typeID" size="20""
						class="put3" value="${goodsManage.typeID}" readonly="readonly" /></td>
					<td></td>
					<td></td>
				</tr> --%>
				<input type="hidden" name="goodsManage.bigClass" id="bigClass" value="${bigClass }"/>
				<input type="hidden" name="goodsManage.tradeCode" id="tradeCode" value="${tradeCode }"/>
				<input type="hidden" name="goodsManage.typeID" id="typeID" value="${typeID }"/>
				<tr>
					<td height="40" align="right"></td>
					<td>分类：<span id="bigClas">${goodsManage.bigClass }</span></td>
					<td>行业分类：<span id="typeI">${goodsManage.typeID }</span></td>
					<td>物品分类：<span id="tradeCod">${ goodsManage.tradeCode}</span></td>
				</tr>
				<tr>
					<td height="40" align="right">物品编号管理:</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4">
						<table id="wupguanli" width="600" height="100"
							style="margin: auto;color: black;">
							<tr>
								<td height="40" align="right">物品序号管理:</td>
								<td><input name="goodsManage.wupxuhao" type="text"
									size="20" maxlength="30"  class="put3" value="${goodsManage.wupxuhao}" /><span></span>
								</td>
								<td height="40" align="right">国际条码管理:</td>
								<td><input name="goodsManage.guojiStorage" type="text"
									size="20" maxlength="30" value="${goodsManage.guojiStorage}" /><span></span>
								</td>
							</tr>
							<tr>
								<td heigth="40" align="right" style="color: black;">公司物品条码:</td>
								<td><input type="text" size="20" maxlength="30"
									name="goodsManage.barCode" value="${goodsManage.barCode}" /><span></span>
								</td>
								<td heigth="40" align="right">二维码管理</td>
								<td><input type="text" size="20" maxlength="30"
									name="goodsManage.twoCode" value="${goodsManage.twoCode }" /><span></span>
								</td>
							</tr>
							<tr>
								<td height="40" align="right">芯片号管理:</td>
								<td><input type="text" size="20" maxlength="30"
									name="goodsManage.defaultStorage"
									value="${goodsManage.defaultStorage}" /><span></span></td>
								<td height="40" align="right">品名编码:</td>
								<td><span>系统自动生成</span>
								</td>
							</tr>
							<tr>
								<td height="40" align="right">车架号/机壳号:</td>
								<td><input type="text" size="20" maxlength="30"
									name="goodsManage.manufacturers"
									value="${goodsManage.manufacturers }" /><span></span></td>
								<td heigth="40" align="right">主板号/发动机号:</td>
								<td><input type="text" size="20" maxlength="30"
									name="goodsManage.mnemonicCode"
									value="${goodsManage.mnemonicCode}" /><span></span></td>
							</tr>
						</table></td>
					<td></td>
				</tr>
				<tr>
					<td height="40" align="right">*物品品牌名称:</td>
					<td><input type="text" size="20" maxlength="30" class="put3"
						name="goodsManage.goodsName" id="wupname"
						value="${goodsManage.goodsName}" /><span></span></td>
					<td heigth="40" align="right">型号管理:</td>
					<td><input type="text" size="20" maxlength="30"
						name="goodsManage.model" value="${goodsManage.model }" /><span></span>
					</td>
				</tr>
				<tr>
					<td height="40" align="right">物品规格管理:</td>
					<td>
					 <select id="sel" name="goodsManage.standard">
							<option value="">---请选择---</option>
							<c:forEach var="lis" items="${request.CCodes}">
                                <c:if test="${lis.codePID=='scode20101216zgkfwy4y8p0000000002'}">
                             
								<option value="${lis.codeValue }"
									<c:if test="${lis.codeValue==goodsManage.standard}">
									  selected = "selected"
									</c:if>>${lis.codeValue}</option>
									</c:if>
							</c:forEach>
					</select> <a
						onclick="toCCode('scode20101216zgkfwy4y8p0000000002','#variableID','#cstaffForm')">新添</a>
					</td>
					<td></td>
					<Td></Td>
				</tr>
			<tr>
				<td heigth="40" align="right">单位换算:</td>
				<td class="variables" style="width: 200px" colspan="3"><span
					id="num"> <input name="goodsManage.num" type="text"
						number="0" class="input isremove" id="num" style="width: 30px;"
						value="${goodsManage.num}"/> <select
						name="goodsManage.variableID" theme="simple" style="width: 70px; "
						number="0" id="variableID">
							<option>---请选择---</option>
							<c:forEach var="lis" items="${request.CCodes}">
							 <c:if test="${lis.codePID=='scode20101014v5zed7cukk0000000003'}">
                             
							<option value="${lis.codeValue}"
								<c:if test="${lis.codeValue==goodsManage.variableID}"> 
								selected = "selected"
								</c:if>>			
									${lis.codeValue}</option>
									</c:if>
							</c:forEach>
					</select> </span> <span id="num1"
					<s:if test="#goodsManage==null||#goodsManage.variable1ID==null">
								 style="display:none"
								</s:if>>=
						<input name="goodsManage.num1" type="text" number="1"
						class="input" value="${goodsManage.num1}" id="num1"
						style="width: 30px" /> <select name="goodsManage.variable1ID"
						theme="simple" style="width: 70px; " number="1" id="variable1ID">
							<option >---请选择---</option>
							<c:forEach var="lis" items="${request.CCodes}">
							 <c:if test="${lis.codePID=='scode20101014v5zed7cukk0000000003'}">
                             
								<option value="${lis.codeValue}"
									<c:if test="${lis.codeValue==goodsManage.variable1ID}">
									  selected="selected"
									</c:if>>${lis.codeValue}</option>
									</c:if>
							</c:forEach>
					</select> </span> <span id="num2" 
					<s:if test="#goodsManage==null||#goodsManage.variable2ID==null">
					style="display:none"</s:if>>=
						<input name="goodsManage.num2" type="text" number="2"
						class="input" value="${goodsManage.num2}" id="num2"
						style="width: 30px;" /> <select name="goodsManage.variable2ID"
						theme="simple" style="width: 70px; " number="2" id="variable2ID">
							<option value="">---请选择---</option>
							<c:forEach var="lis" items="${request.CCodes}">
							 <c:if test="${lis.codePID=='scode20101014v5zed7cukk0000000003'}">
                             
								<option value="${lis.codeValue }"
									<c:if test="${lis.codeValue==goodsManage.variable2ID}">
									  selected = "selected"
									</c:if>>${lis.codeValue
									}</option>
									</c:if>
							</c:forEach>
					</select> </span> <span id="num3"
					<s:if test="#goodsManage==null||#goodsManage.variable3ID==null">
						 style="display:none"
						</s:if>>=
						<input name="goodsManage.num3" type="text" number="3"
						class="input" value="${goodsManage.num3}" id="num3"
						style="width: 30px;" /> <select id="sel"
						name="goodsManage.variable3ID" theme="simple"
						style="width: 70px; " number="3" id="variable3ID">
							<option value="">---请选择---</option>
							<c:forEach var="lis" items="${request.CCodes}">
							 <c:if test="${lis.codePID=='scode20101014v5zed7cukk0000000003'}">
                             
								<option value="${lis.codeValue }"
									<c:if test="${lis.codeValue==goodsManage.variable3ID}">
									  selected = "selected"
									</c:if>>${lis.codeValue
									}</option>
									</c:if>
							</c:forEach>
					</select> </span> <span id="num4"
					<s:if test="#goodsManage==null||#goodsManage.variable4ID==null">
						 style="display:none"
						</s:if>>=
						<input name="goodsManage.num4" type="text" number="4"
						class="input" value="${goodsManage.num4}" id="num4"
						style="width: 30px;" /> <select name="goodsManage.variable4ID"
						theme="simple" style="width: 70px; " number="4" id="variable4ID">
							<option >---请选择---</option>
							<c:forEach var="lis" items="${request.CCodes}">
							 <c:if test="${lis.codePID=='scode20101014v5zed7cukk0000000003'}">
                             
								<option value="${lis.codeValue}"
									<c:if test="${lis.codeValue==goodsManage.variable4ID}">
									  selected = "selected"
									</c:if>>${lis.codeValue
									}</option>
									</c:if>
							</c:forEach>
					</select> </span> <a
					onclick="toCCode('scode20101014v5zed7cukk0000000003','#variableID','#cstaffForm')">新添</a>
				</td>
			</tr>
			<tr id="isShow" 	
			<s:if test="#CarInformation==null||#CarInformation.carKey==null||#goodsManage.typeID!='车辆'">			
				style="display:none"
			</s:if>
			>	
				<td colspan="5">
					<table width="100%" border="0" id="stafftable2" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; ">
						<tr>
							<td width="95" height="30" align="right">车牌号：</td>
							<td><input name="carInformation.carNum" id="carNum"
								class="carNum isremove" style="width:100px;"
								value="${CarInformation.carNum}"/></td>
							<td width="90" align="right"><span class="xx">*</span>车辆类型：
							</td>
							<td><input name="carInformation.carType" id="carType"
								class="carType isremove" style="width:100px;" value="${CarInformation.carType}"/>
							</td>
							<td width="110" align="right">地点：</td>
							<td><input name="carInformation.carPlace" id="carPlace"
								class="isremove" style="width:100px;"
								value="${CarInformation.buyDate}" /></td>
							<td align="right">购买日期</td>
							<td><input name="carInformation.buyDate" id="buyDate"
								onfocus="date(this);" class="isremove" style="width:100px;"
								value="${CarInformation.releaseDate }" /></td>
							<td width="90" align="right">出厂日期：</td>
							<td><input name="carInformation.releaseDate"
								id="releaseDate" class="isremove" onfocus="date(this);"
								style="width:100px;" value="${CarInformation.releaseDate }" />
							</td>
						</tr>
						<tr>
							<td width="95" height="30" align="right">运行日期：</td>
							<td><input name="carInformation.operationDate"
								id="operationDate" class="isremove" onfocus="date(this);"
								style="width:100px;" value="${CarInformation.operationDate }" />
							</td>
							<td width="90" align="right">注册登记日期</td>
							<td><input name="carInformation.registrationDate"
								id="registrationDate" onfocus="date(this);" class="isremove"
								style="width:100px;" value="${CarInformation.registrationDate }" />
							</td>
							<td width="95" height="30" align="right">车辆厂牌型号：</td>
							<td><input name="carInformation.brandModel" id="brandModel"
								class="isremove" style="width:100px;"
								value="${CarInformation.brandModel }" /></td>
							<td width="90" align="right">发动机型号：</td>
							<td><input name="carInformation.engineType" id="engineType"
								class="isremove" style="width:100px;"
								value="${CarInformation.engineType }" /></td>
							<td width="110" align="right">货箱内部尺寸：</td>
							<td><input name="carInformation.containerInSize"
								id="containerInSize" class="isremove" style="width:100px;"
								value="${CarInformation.containerInSize}" /></td>
						</tr>
						<tr>				
							
							<td align="right">外廊尺寸：</td>
							<td><input name="carInformation.outerSize" id="outerSize"
								class="isremove" style="width:100px;"
								value="${CarInformation.outerSize }" /></td>
							<td height="30" align="right">驱动形式：</td>
							<td><input name="carInformation.driveType" id="driveType"
								class="isremove" style="width:100px;"
								value="${CarInformation.driveType }" /></td>
							<td align="right">排量/功率：</td>
							<td><input name="carInformation.power" id="power"
								class="isremove" style="width:100px;"
								value="${CarInformation.power }" /></td>

							<td align="right">燃油类别：</td>
							<td><input name="carInformation.fuelType" id="fuelType"
								class="isremove" style="width:100px;"
								value="${CarInformation.fuelType }" /></td>
							<td align="right">外观颜色及漆号：</td>
							<td><input name="carInformation.colorPaintNum"
								id="colorPaintNum" class="isremove" style="width:100px;"
								value="${CarInformation.colorPaintNum }" /></td>
						</tr>
						<tr>
							<td height="30" align="right"><span class="xx">*</span>车辆品牌：
							</td>
							<td><input name="carInformation.vehicleBrand"
								id="vehicleBrand" class="isremove vehicleBrand"
								style="width:100px;" value="${CarInformation.vehicleBrand }" />
							</td>
							<td align="right">制造厂名称：</td>
							<td><input name="carInformation.factoryName"
								id="factoryName" class="isremove" style="width:100px;"
								value="${CarInformation.factoryName }" /></td>

							<td align="right">准牵引总质量：</td>
							<td><input name="carInformation.tractionTotal"
								id="tractionTotal" class="isremove" style="width:100px;"
								value="${CarInformation.tractionTotal }" /></td>

							<td align="right">轮距：</td>
							<td><input name="carInformation.wheelTead" id="wheelTead"
								class="isremove" style="width:100px;"
								value="${CarInformation.wheelTead}" /></td>
							<td height="30" align="right">核定载客(人)：</td>
							<td><input name="carInformation.ratifyPeople"
								id="ratifyPeople" class="isremove" style="width:100px;"
								value="${CarInformation.ratifyPeople }" /></td>
						</tr>
						<tr>
							<td align="right">核定载质量：</td>
							<td><input name="carInformation.ratifyQuality"
								id="ratifyQuality" class="isremove" style="width:100px;"
								value="${CarInformation.ratifyQuality }" /></td>

							<td align="right">国产/进口：</td>
							<td><input name="carInformation.domestic" id="domestic"
								class="isremove" style="width:100px;"
								value="${CarInformation.domestic }" /></td>

							<td align="right">驾驶室载客：</td>
							<td><input name="carInformation.bridgePeople"
								id="bridgePeople" class="isremove" style="width:100px;"
								value="${CarInformation.bridgePeople }" /></td>
							<td height="30" align="right">钢板弹簧片数：</td>
							<td><input name="carInformation.springNum" id="springNum"
								class="isremove" style="width:100px;"
								value="${CarInformation.springNum}" /></td>
							<td align="right">车辆获得方式：</td>
							<td><input name="carInformation.vehicleGet" id="vehicleGet"
								class="isremove" style="width:100px;"
								value="${CarInformation.vehicleGet }" /></td>
						</tr>
						<tr>
							<td align="right">使用性质：</td>
							<td><input name="carInformation.useProp" id="useProp"
								class="isremove" style="width:100px;"
								value="${CarInformation.useProp }" /></td>

							<td align="right">轴数：</td>
							<td><input name="carInformation.shaftNum" id="shaftNum"
								class="isremove" style="width:100px;"
								value="${CarInformation.shaftNum}" /></td>
							<td height="30" align="right">轮胎数：</td>
							<td><input name="carInformation.tireNum" id="tireNum"
								class="isremove" style="width:100px;"
								value="${CarInformation.tireNum }" /></td>

							<td align="right">轴距(mm)：</td>
							<td><input name="carInformation.wheelbase" id="wheelbase"
								class="isremove" style="width:100px;"
								value="${CarInformation.wheelbase}" /></td>

							<td align="right">百公里耗油：</td>
							<td><input name="carInformation.kmFuel" id="kmFuel"
								class="isremove" style="width:100px;"
								value="${CarInformation.kmFuel}" /></td>
						</tr>
						<tr>
							<td align="right">轮胎规格：</td>
							<td><input name="carInformation.tireSpecifications"
								id="tireSpecifications" class="isremove" style="width:100px;"
								value="${CarInformation.tireSpecifications }" /></td>
							<td height="30" align="right">整备质量(kg)：</td>
							<td><input name="carInformation.serviceQuality"
								id="serviceQuality" class="isremove" style="width:100px;"
								value="${CarInformation.serviceQuality}" /></td>
							<td align="right">转向形式：</td>
							<td><input name="carInformation.steeringType"
								id="steeringType" class="isremove" style="width:100px;"
								value="${CarInformation.steeringType }" /> <input type="hidden"
								name="carInformation.carKey" id="carKey" class="isremove"
								style="width:100px;"value="${CarInformation.carKey}"/> <input type="hidden"
								name="carInformation.carID" id="carID" class="isremove"
								style="width:100px;" /></td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td height="40" align="right">物品简介管理:</td>
					
				<td colspan="3">
				
				<textarea size="50" id="content" name="goodsManage.wupjj"
						style="width: 500px; height: 60px;resize: none;margin-top: 10px;max-width: 800px;max-height: 100""><c:if test="${goodsManage.wupjj!=null}"><jsp:include page="../../../../upload_files/wechatmenu/${goodsManage.wupjj}"  flush="true" /></c:if></textarea>
				<script type="text/javascript">
                   			 var editor = CKEDITOR.replace( 'content',
                                  {
                                  skin : 'kama',
                                  language : 'zh-cn',
                                  height:130 
                                  });
                       </script>
				</td>
			</tr>
			<tr>
				<td height="40" align="right">物品功能管理:</td>
				<td colspan="3"><textarea size="50"  id="content1" name="goodsManage.wupgn"
						style="width: 500px; height: 60px;resize: none;margin-top: 10px;max-width: 800px;max-height: 100"><c:if test="${goodsManage.wupgn!=null}"><jsp:include page="../../../../upload_files/wechatmenu/${goodsManage.wupgn}"  flush="true" /></c:if></textarea>
			<script type="text/javascript">
                   			 var editor = CKEDITOR.replace( 'content1',
                                  {
                                  skin : 'kama',
                                  language : 'zh-cn',
                                  height:130                                                        
                                  });
                       </script>
				</td>
			</tr>
			<tr>
				<td height="40" align="right">物品用途管理:</td>
				<td colspan="3"><textarea size="50"  id="content2" name="goodsManage.wupyt"
						style="width: 500px; height: 60px;resize: none;margin-top: 10px;max-width: 800px;max-height: 100""><c:if test="${goodsManage.wupyt!=null}"><jsp:include page="../../../../upload_files/wechatmenu/${goodsManage.wupyt}"  flush="true" /></c:if></textarea>
				</td>
				<script type="text/javascript">
                   			 var editor = CKEDITOR.replace( 'content2',
                                  {
                                  skin : 'kama',
                                  language : 'zh-cn',
                                  height:130
                                  });
                       </script>
			</tr>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
				framespacing="0" height="0"></iframe>

			</form>
			<tr>
				<td height="40" align="right">物品照片管理:</td>
				<%-- <td><img type="image"
					<s:if test="#goodsManage.logoPath!=null&& #goodsManage.logoPath!=''">
			     src="<%=basePath%>/${goodsManage.logoPath}"
			      </s:if>
					<s:else >
			 		 src="<%=basePath%>/images/xiaozanwuxianshi.png"
			      </s:else>
					id="logo" width="200px" height="150px;" onclick="chuan('logo')"style="margin-top: 15px;" title="logo" />
					<br> 
					<input type="image" src="<%=basePath%>/images/wup_02.png"
						onclick="chuan('logo')" style="margin-right: 50px;"/> <input type="image"
						src="<%=basePath%>/images/wup_01.png" onclick="chuan('logo')" />
				</td> --%>
				<td>
						<div contenteditable="" id="post_article_content" style="border:0px solid #000; margin-top: 18px;width: 200px;height: 150px;overflow: hidden;">
						<!-- <input type="hidden" name="goodsManage.fileLogo" id="logoPath"/> -->
						<s:if test="#goodsManage.logoPath!=null&& #goodsManage.logoPath!=''">
			    		<img type="image" id="log1" src="<%=basePath%>/${goodsManage.logoPath}" style="width: 200px;height: 150px;"/>
			      </s:if>
					<s:else >
			 		<img type="image" id="log1" src="<%=basePath%>/images/xiaozanwuxianshi.png" style="width: 200px;height: 150px;"/>
			      </s:else> 
						</div>
						<table id="upload_content" class="swfupload_main"   style="display:none;" width="100%">
							<tbody id="divFileProgressContainer">
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4">
										<input type="hidden" id="hidIdList"/>
									</td>
								</tr>
							</tfoot><br/>
							<div style="margin-top:-15px;"><span id="uploads" ></span>
							<input type="image"	src="<%=basePath%>/images/wup_01.png" onclick="chuan('logo')" style="margin-left:50px;"/>
							</div>
						</table>
						
					</div>
					</td>
				<%-- <td><img
					<s:if test="#goodsManage.photoPath!=null&& #goodsManage.photoPath!=''">
			      	      src="<%=basePath%>/${goodsManage.photoPath}"
			      	    </s:if>
					<s:else >
			      	     	 src="<%=basePath%>/images/xiaozanwuxianshi.png"
			      	      </s:else >
					id="zhuti" width="200px" height="150px" onclick="chuan('zhuti')"  style="margin-top: 15px;" title="主题" /><br><input
						type="image" src="<%=basePath%>/images/wup_02.png"
						onclick="chuan('zhuti')"style="margin-right: 50px;" /> <input type="image"
						src="<%=basePath%>/images/wup_01.png" onclick="chuan('zhuti')" />
				</td> --%>
				<td>
						<div contenteditable="" id="post_article_content2" style="border:0px solid #000; margin-top: 18px;width: 200px;height: 150px;overflow: hidden;">
						<!-- <input type="hidden" name="goodsManage.fileLogo" id="logoPath"/> -->
						<s:if test="#goodsManage.photoPath!=null&& #goodsManage.photoPath!=''">
			    		<img type="image" id="log2" src="<%=basePath%>/${goodsManage.photoPath}" style="width: 200px;height: 150px;"/>
			      </s:if>
					<s:else >
			 		<img type="image" id="log2" src="<%=basePath%>/images/xiaozanwuxianshi.png" style="width: 200px;height: 150px;"/>
			      </s:else> 
						</div>
						<table id="upload_content" class="swfupload_main"   style="display:none;" width="100%">
							<tbody id="divFileProgressContainer2">
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4">
										<input type="hidden" id="hidIdList2"/>
									</td>
								</tr>
							</tfoot><br/>
							<div style="margin-top:-15px;"><span id="uploads2" ></span>
							<input type="image"
						src="<%=basePath%>/images/wup_01.png" onclick="chuan('zhuti')" style="margin-left:50px;"/>
							</div>
						</table>
						
					</div>
					</td>
				<%-- <td><img
					<s:if test="#goodsManage.shippath!=null&& #goodsManage.shippath!=''">
			         src="<%=basePath%>/${goodsManage.shippath}"
			        </s:if>
					<s:else >
			          	 src="<%=basePath%>/images/xiaozanwuxianshi.png"
			          </s:else >
					id="ship" width="200px" height="150px; " onclick="chuan('ship')"  style="margin-top: 15px;"title="视频未完成" /><br><input
						type="image" src="<%=basePath%>/images/wup_02.png"
						onclick="chuan('ship')"  style="margin-right: 50px;"/> <input type="image"
						src="<%=basePath%>/images/wup_01.png" onclick="chuan('ship')" />
				</td> --%>
				
				<td>
						<div contenteditable="" id="post_article_content3" style="border:0px solid #000; margin-top: 18px;width: 200px;height: 150px;overflow: hidden;">
						<!-- <input type="hidden" name="goodsManage.fileLogo" id="logoPath"/> -->
						<s:if test="#goodsManage.shippath!=null&& #goodsManage.shippath!=''">
			    		<img type="image" id="log3" src="<%=basePath%>/${goodsManage.shippath}" style="width: 200px;height: 150px;"/>
			      </s:if>
					<s:else >
			 		<img type="image" id="log3" src="<%=basePath%>/images/xiaozanwuxianshi.png" style="width: 200px;height: 150px;"/>
			      </s:else> 
						</div>
						<table id="upload_content" class="swfupload_main"   style="display:none;" width="100%">
							<tbody id="divFileProgressContainer3">
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4">
										<input type="hidden" id="hidIdList3"/>
									</td>
								</tr>
							</tfoot><br/>
							<div style="margin-top:-15px;"><span id="uploads3" ></span>
							<input type="image"
						src="<%=basePath%>/images/wup_01.png" onclick="chuan('ship')" style="margin-left:50px;"/>
							</div>
						</table>
						
					</div>
					</td>
			</tr>
			<tr>
				<td colspan="4" style="padding-left: 260px;"><input
					type="image" src="<%=basePath%>/images/wup_03.png"
					onclick="shujufromtijiao()" style="margin-right: 150px;" /> <input type="image"
					src="<%=basePath%>/images/wup_04.png" onclick = "document.shujufrom.reset()" />
				</td>
			</tr>
		</table>
</div>
	<!-- 物品添加.结束 -->
	<!-- 物品类别 配合上面添加或者修改时候物品类别查询使用 -->
	<div div class="jqmWindow"
		style="width: 350px; height:350px; right: 45%;top:10%; display: none;"
		id="jqModelType">
		<div class="drag">
			物品类别
			<!-- <div class="close" id="closes"></div> -->
		</div>
		<table height="100px" border="0" align="center" cellpadding="0"
			cellspacing="0"  >
			<tr>
				<td width="45%" align="left" valign="top"  >
					<div id="aadTree" class="text_tree"
						style="overflow: auto; z-index: 99; width: 150px;height:320px; " ></div></td>
				<!-- <td width="11%" align="center">
						<table>
						<tr><td><div class="right_dan" id="rightdan"></div></td></tr>
						<tr><td><div class="left_dan" id="leftdan"></div></td></tr>
						</table>
					</td> -->
				<td width="44%" align="left" valign="top">
					<div id="text_tree" class="text_tree"
						style="overflow: auto; z-index: 99; width: 150px"></div></td>
			</tr>
		</table>
	</div>
	<!-- 物品类别结束 -->
	<!-- 选择行业分类管理时出发的窗口 -->
	<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"
		id="JQueryaddress">
		<form name="SearchForm1" id="SearchForm1" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="drag">请选择行业分类</div>
			<table width="400" id="cataffSearchTable">
				<tr class="trheight">
					<td colspan="2" class="JQueryaddress"><select
						name="addressProvince" id="province" number='0'
						style="width: 150px;">
					</select> <select name="addressCity" id="city" number='1'
						style="width: 150px;">
					</select></td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch1"
					onclick="getAddress()" value="确定" /> <input type="button"
					class="input-button JQueryreturn2" value="取消" />
			</div>
		</form>
	</div>
	<div></div>
	<!--类型单位换算 添加 -->
	<div class="jqmWindow" style="width: 400px; right: 25%;top:10%"
		id="newccode">
		<div class="drag">添加</div>
		<table>
			<tr>
				<td>代码名字：</td>
				<td><input id="ccodevalue" /> <input id="codePID"
					type="hidden" /> <input id="selectID" type="hidden" /> <input
					id="formID" type="hidden" /></td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveCCodes()"
				value="确定" /> <input type="button"
				class="input-button JQueryreturn1" value="取消" />
		</div>
	</div>
	<!-- 物品管理 开始 -->
	<div></div>
<iframe name="hidden" height="0" width="0"></iframe>
<script type="text/javascript">
 var basePath = "<%=basePath%>";
   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load(){
    String.prototype.trim = function(){
             return this.replace(/(^\s*)|(\s*$)/g, "");
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/office/filemanage/",
		                    fn:"",
		                    small:"false",
		                    sw:"30",
		                    sh:"80",
		                    wm:"True",
		                    data:""
                        },
            file_size_limit: "2 MB",
		    file_types: "*.jpg;*.gif;*.png;*.bmp",
		    file_types_description: "只能上传.jpg;.gif;.png;.bmp 格式的图片文件",
		    file_upload_limit: 1,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"<%=basePath%>/images/wup_02.png",
            button_width:73,
            button_height:23,
            button_placeholder_id:"uploads", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer",//上传图片在页面中的显示位置
                submitBtnId: "BtnSave",//save按钮
                serverDataId: "hidIdList",//隐藏域
                uploadMode: "LIST"//?
            },
           
        }
        SWFLoad(SWFUpload1_id,LoadSettings);
    }
    addLoadEvent(SWFUpload1_load);
    function SWFUpload1_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
        var hidIdList=$("#hidIdList").val();
    	var result=hidIdList.split(",");
    	var str="";
    	log1.parentNode.removeChild(log1); 
    	for(var i=0;i<result.length-1;i++){
    		str+="<center><image src='<%=basePath%>"+result[i]+"' style='width: 200px;height:150px;'/></center>";
    		$("#logoPath").val(result[i]);
    	}
    	 $("#post_article_content").append(str);
    	 
    }
    function uploads(){
    	var swf = SWFUpload1_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		}else{
		 alert("请添加文件");
		 return;
		}
    }
    function dialogOpen() {
    }
    </script>
    
    <script type="text/javascript">
   var SWFUpload2_id={SWFObj:new Object()}
    function SWFUpload2_load(){
    String.prototype.trim = function(){
             return this.replace(/(^\s*)|(\s*$)/g, "");
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/office/filemanage/",
		                    fn:"",
		                    small:"false",
		                    sw:"30",
		                    sh:"80",
		                    wm:"True",
		                    data:""
                        },
            file_size_limit: "2 MB",
		    file_types: "*.jpg;*.gif;*.png;*.bmp",
		    file_types_description: "只能上传.jpg;.gif;.png;.bmp 格式的图片文件",
		    file_upload_limit: 1,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload2_uploadSuccess,
            button_image:"<%=basePath%>/images/wup_02.png",
            button_width:73,
            button_height:23,
            button_placeholder_id:"uploads2", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer2",//上传图片在页面中的显示位置
                submitBtnId: "BtnSave",//save按钮
                serverDataId: "hidIdList2",//隐藏域
                uploadMode: "LIST"//?
            },
           
        }
        SWFLoad(SWFUpload2_id,LoadSettings);
    }
    addLoadEvent(SWFUpload2_load);
    function SWFUpload2_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
        var hidIdList=$("#hidIdList2").val();
    	var result=hidIdList.split(",");
    	var str="";
    	log2.parentNode.removeChild(log2); 
    	for(var i=0;i<result.length-1;i++){
    		str+="<center><image src='<%=basePath%>"+result[i]+"' style='width: 200px;height:150px;'/></center>";
    	}
    	 $("#post_article_content2").append(str);
    	 
    }
    function uploads2(){
    	var swf = SWFUpload1_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		}else{
		 alert("请添加文件");
		 return;
		}
    }
    function dialogOpen() {
    }
    </script>
    
    <script type="text/javascript">
   var SWFUpload3_id={SWFObj:new Object()}
    function SWFUpload3_load(){
    String.prototype.trim = function(){
             return this.replace(/(^\s*)|(\s*$)/g, "");
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/office/filemanage/",
		                    fn:"",
		                    small:"false",
		                    sw:"30",
		                    sh:"80",
		                    wm:"True",
		                    data:""
                        },
            file_size_limit: "2 MB",
		    file_types: "*.mp4;",
		    file_types_description: "只能上传.mp4; 格式的图片文件",
		    file_upload_limit: 1,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload3_uploadSuccess,
            button_image:"<%=basePath%>/images/wup_02.png",
            button_width:73,
            button_height:23,
            button_placeholder_id:"uploads3", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer3",//上传图片在页面中的显示位置
                submitBtnId: "BtnSave",//save按钮
                serverDataId: "hidIdList3",//隐藏域
                uploadMode: "LIST"//?
            },
           
        }
        SWFLoad(SWFUpload3_id,LoadSettings);
    }
    addLoadEvent(SWFUpload3_load);
    function SWFUpload3_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
        var hidIdList=$("#hidIdList3").val();
    	var result=hidIdList.split(",");
    	var str="";
    	log3.parentNode.removeChild(log3); 
    	for(var i=0;i<result.length-1;i++){
    		str+="<center><image src='<%=basePath%>"+result[i]+"' style='width: 200px;height:150px;'/></center>";
    	}
    	 $("#post_article_content3").append(str);
    	 
    }
    function uploads3(){
    	var swf = SWFUpload1_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		}else{
		 alert("请添加文件");
		 return;
		}
    }
    function dialogOpen() {
    }
    </script>

</body>
</html>
