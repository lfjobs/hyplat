<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>车辆信息管理修改</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        </style>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"/>
     
		   <script type="text/javascript">
         
   		
		</script>  
</head>
	<body >
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	 <div class="contentbannb" style="top: 10%;background:#DAE7F6 repeat top;" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">车辆详细信息
				  </div>
				  </div>
			<table width="99%" cellpadding="0" cellspacing="0"  border="0" align="center" class="table">
			  <tr>
			  	<td align="center" height="40" >责任人:</td><td ><span>${carInformation.staffName}</span></td>
				<td align="center" height="40" >车牌号:</td><td ><span>${carInformation.carNum}</span></td>
				<td align="center" height="40" >发动机号:</td><td ><span>${carInformation.engineNum}</span></td>
				<td align="center" height="40">车辆类型:</td><td ><span>${carInformation.carType}</span></td>
				<td align="center" height="40">注册登记日期:</td><td  width="60"><span>${carInformation.registrationDate}</span></td>
			  </tr>
			  <tr>
			  	<td align="center">购买价格:</td><td ><span>${carInformation.carPrice}</span></td>
				<td align="center">购买日期:</td><td ><span>${fn:substring(carInformation.buyDate,0, 10)}</span></td>
				<td align="center">购买单位:</td><td ><span>${carInformation.companyName}</span></td> 
				<td align="center" height="40">车架号:</td><td ><span>${carInformation.carFrameNum}</span></td>
				<td align="center" height="40">车辆厂牌型号:</td><td><span>${carInformation.brandModel}</span></td>
			</tr>
			  <tr>
				<td align="center" height="40">发动机型号:</td><td ><span>${carInformation.engineType}</span></td>
				<td align="center" height="40">货箱内部尺寸:</td><td ><span>${carInformation.containerInSize}</span></td>
				<td align="center" height="40">外廓尺寸:</td><td ><span>${carInformation.outerSize}</span></td>
				<td align="center" height="40">驱动形式:</td><td ><span>${carInformation.driveType}</span></td>
				<td align="center" height="40">排量:</td><td ><span>${carInformation.power}</span></td>
			</tr>
			  <tr>
				<td align="center" height="40">燃油类别:</td><td><span>${carInformation.fuelType}</span></td>
				<td align="center" height="40">外观颜色及漆号:</td><td ><span>${carInformation.colorPaintNum}</span></td>
				<td align="center" height="40">车辆品牌:</td><td ><span>${carInformation.vehicleBrand}</span></td>
				<td align="center" height="40">制造厂名称:</td><td ><span>${carInformation.factoryName}</span></td>
				<td align="center" height="40">准牵引总质量:</td><td ><span>${carInformation.tractionTotal}</span></td>
			 </tr>
			  <tr>
				<td align="center" height="40">轮距:</td><td ><span>${carInformation.wheelTead}</span></td>
				<td align="center" height="40">核定载客:</td><td ><span>${carInformation.ratifyPeople}</span></td>
				<td align="center" height="40">核定载质量:</td><td ><span>${carInformation.ratifyQuality}</span></td>
				<td align="center" height="40">国产进口:</td><td ><span>${carInformation.domestic}</span></td>
				<td align="center" height="40">驾驶室载客:</td><td ><span>${carInformation.bridgePeople}</span></td>
			</tr>
			  <tr>
				<td align="center" height="40">钢板弹簧片数:</td><td ><span>${carInformation.springNum}</span></td>
				<td align="center" height="40">车辆获得方式:</td><td><span>${carInformation.vehicleGet}</span></td>
				<td align="center" height="40">使用性质:</td><td ><span>${carInformation.useProp}</span></td>
				<td align="center" height="40">出厂日期:</td><td><span>${fn:substring(carInformation.releaseDate,0, 10)}</span></td>
				<td align="center" height="40">运行日期:</td><td ><span>${fn:substring(carInformation.operationDate,0, 10)}</span></td>
			  </tr>
			  <tr>
				<td align="center" height="40">轴距:</td><td><span>${carInformation.wheelbase}</span></td>
				<td align="center" height="40">百公里耗油:</td><td ><span>${carInformation.kmFuel}</span></td>
				<td align="center" height="40">轴数:</td><td ><span>${carInformation.shaftNum}</span></td>
				<td align="center" height="40">轮胎数:</td><td ><span>${carInformation.tireNum}</span></td>
				<td align="center" height="40">轮胎规格:</td><td ><span>${carInformation.tireSpecifications}</span></td>
			  </tr>
			  <tr>
				<td align="center" height="40">整备质量:</td><td ><span>${carInformation.serviceQuality}</span></td>
				<td align="center" height="40">转向形式:</td><td ><span>${carInformation.steeringType}</span></td>
				<td align="center" height="40">车辆使用:</td><td ><span>${carInformation.state3=='00'?'未使用':carInformation.state3=='01'?'已使用':'下线'}</span></td>
				<td align="center" height="40">车辆状态:</td><td ><span>${carInformation.state1=='00'?'加盟车':'本校车'}</span></td>
			</tr>
			  <tr>
			  	<td colspan="10"  align="center"><input type="button" class="btn001" onclick="javascript:window.history.go(-1);" name="button2" value="返回" /></td>
			  </tr>
			</table>		   
		<s:token></s:token>
            </form>
           </div>
</body>
 
</html>